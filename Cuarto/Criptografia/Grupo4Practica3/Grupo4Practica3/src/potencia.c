#include <stdio.h>
#include <stdlib.h>
#include <gmp.h>
#include <time.h>

//COMPILAR: gcc potencia.c -lgmp -L.

int testExponenteVSGMP(int base_i, int exponente_i, int modulo_i) {
	mpz_t x, base, exponente, modulo, result;
	mpz_init_set_si(base, base_i);
	mpz_init_set_si(exponente, exponente_i);
	mpz_init_set_si(modulo, modulo_i);
	mpz_init(result);

	mpz_powm(result, base, exponente, modulo);
	gmp_printf("Con GMP:\n%Zd elevado a %Zd mod %Zd  == %Zd\n", base, exponente, modulo, result);

	mpz_clear(base);
	mpz_clear(exponente);
	mpz_clear(modulo);
	mpz_clear(result);

	return 0;
}
// TODO: devolver resultado en return. 
int exponenteGMP(int base_i, int exponente_i, int modulo_i) {

	mpz_t x, base, exponente, modulo, mask;
	mpz_t temp;
	int i, longbits;
	/*Inicializar valores*/
	mpz_init_set_si(base, base_i);
	mpz_init_set_si(exponente, exponente_i);
	mpz_init_set_si(modulo, modulo_i);
	mpz_init_set_si(mask, 1);
	mpz_init(temp);


	mpz_init_set_si(x, 1);

	longbits = mpz_sizeinbase(exponente, 2);
	for(i = longbits - 1; i >= 0; i--) {

		mpz_mul(x, x, x);
		mpz_mod(x, x, modulo); // x = x² mod n

		//Equivalente a un shift right
		mpz_fdiv_q_2exp(temp, exponente, i);
		mpz_and(temp, temp, mask);			// temp = bit i de exponente

		if(mpz_cmp(temp, mask) == 0) {		// bit i == 1
			//printf("bit %d == 1\n", i);
			mpz_mul(x, x, base);
			mpz_mod(x, x, modulo);
		}

		//gmp_printf("%Zd\n", exponente);
		//gmp_printf("%Zd\n", temp);
		//gmp_printf("x == %Zd\n", x);
	}

	gmp_printf("Con nuesto algoritmo:\n%Zd elevado a %Zd mod %Zd  == %Zd\n", base, exponente, modulo, x);

	mpz_clear(base);
	mpz_clear(exponente);
	mpz_clear(modulo);
	mpz_clear(mask);
	mpz_clear(temp);
	mpz_clear(x);
	return 0;
}
// 23, 11, 243

void rendimiento(){
	int* expMod=NULL;
	int* expGMP=NULL;

	int maxBase=400, minBase=300;
	int maxExp=201005, minExp=200000;
	int maxMod=100003, minMod=100000;
	int i, j, k, l=0;
	FILE* fout=NULL;
	char * fname=NULL;
	clock_t time1, time2;

	struct timeval* t1=NULL;
	struct timeval* t2=NULL; 

	clock_t* times1,* times2;
	mpz_t result, exponente, modulo, base;
	mpz_init(result);
	mpz_init(exponente);
	mpz_init(modulo);
	mpz_init(base);

	t1= malloc(sizeof(struct timeval));
	t2= malloc(sizeof(struct timeval));
	expMod= (int*) malloc(sizeof(int)*(maxBase - minBase+1));
	expGMP= (int*) malloc(sizeof(int)*(maxBase - minBase+1));
	
	times1= (clock_t*) malloc(sizeof(clock_t)*(maxBase - minBase+1));
	times2= (clock_t*) malloc(sizeof(clock_t)*(maxBase - minBase+1));

	//gettimeofday(tval, tzone);
	fname =(char*) malloc(sizeof(char)*50);
	printf("size:%d\n", (maxBase - minBase+1)*(maxExp - minExp+1)*(maxMod - minMod+1));
	i=maxBase;
	for(k=minMod; k <= maxMod; k++){
		sprintf(fname, "tiempos/t%d.data", k);
		fout= fopen(fname, "w");
		printf("%s\n",fname );
		for(j=minExp; j <= maxExp; j++){
			//for(i=minBase, l=0; i <= maxBase; i++, l++){
				
				//printf("%d %d %d %d\n", l, i, j, k);
				gettimeofday(t1, NULL);
				expMod[l]= exponenteGMP(i, j, k);
				gettimeofday(t2, NULL);
				/*printf("tv_sec1= %lld , tv_usec1=%lld\n",(long long) t1->tv_sec, (long long) t1->tv_usec );
				printf("tv_sec2= %lld , tv_usec2=%lld\n\n\n",(long long) t2->tv_sec, (long long) t2->tv_usec );
				*/if(t1->tv_sec==t2->tv_sec){
					//times1[l]= t2->tv_usec - t1->tv_usec;
					fprintf(fout, "%lld\t", (long long) t2->tv_usec - t1->tv_usec);
				}
				else{
					//times1[l]= t1->tv_usec - t2->tv_usec;
					fprintf(fout, "%lld\t", (long long) t1->tv_usec - t2->tv_usec);
				}

				mpz_set_ui(base, i);
				mpz_set_ui(exponente, j);
				mpz_set_ui(modulo, k);
				
				gettimeofday(t1, NULL);
				mpz_powm(result, base, exponente, modulo);
				gettimeofday(t2, NULL);

				/*printf("tv_sec1= %lld , tv_usec1=%lld\n",(long long) t1->tv_sec, (long long) t1->tv_usec );
				printf("tv_sec2= %lld , tv_usec2=%lld\n\n\n",(long long) t2->tv_sec, (long long) t2->tv_usec );
				*/
				if(t1->tv_sec==t2->tv_sec){
					//times2[l]= t2->tv_usec - t1->tv_usec;
					fprintf(fout, "%lld\n", (long long) t2->tv_usec - t1->tv_usec);
				}
				else{
					//times2[l]= t1->tv_usec - t2->tv_usec;

					fprintf(fout, "%lld\n", (long long) t1->tv_usec - t2->tv_usec);
				}
				
				expGMP[l]= mpz_get_ui(result); 
			}
			
			/*for(i=0; i<l; i++){

				fprintf(fout, "%u\t%u\n", (unsigned int) times1[i], (unsigned int) times2[i]);

			}*/
			fclose(fout);

		//}

	}
	free(fname);
	free(times1);
	free(times2);

	free(expMod);
	free(expGMP);

}

int main(int argc, char **argv) {
	if(argc != 4) {
		printf("Ejecucion incorrecta. Uso: ./potencia base exponente modulo\n");
		return;
	}
	int base, exponente, modulo;
	base = atoi(argv[1]);
	exponente = atoi(argv[2]);
	modulo = atoi(argv[3]);
	exponenteGMP(base, exponente, modulo);
	testExponenteVSGMP(base, exponente, modulo);


	//rendimiento();
}
