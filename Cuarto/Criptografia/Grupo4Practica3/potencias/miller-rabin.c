#include <stdio.h>
#include <stdlib.h>
#include <gmp.h>
#include <time.h>
#include <math.h>
#include <getopt.h>

FILE *fout;

unsigned long int power(unsigned long base,unsigned long int expo) {
  
  unsigned long int res = 1;
  int i;
  for (i=0; i< expo; i++)
  	res*=base;
  return res;

}
/**
*
* 	1. Find integers k, q, with k > 0, q odd, so that n-1 = 2^kq
*
*	2. Select a random integer a, 1 < a < n - 1
*
*	3. if a^q mod n = 1 then return inconclusive
*
*	4. for j = 0 to k - 1 do
*
*	5. if a^((2^j) * q) mod n = n-q then return inconclusive
*
*	6. return composite
*	
**/


int testMillerRabin(mpz_t numero, int reps, gmp_randstate_t state ){
	
	
	mpz_t q, k, a, aux, aux2, exp, numeroMenos1;
	
	unsigned long int i, k2;
	
	mpz_init_set_ui(q, 0);
	mpz_init(k);
	mpz_init(a);
	mpz_init(aux);
	mpz_init(aux2);
	//mpz_init(i);
	mpz_init(exp);
	mpz_init(numeroMenos1);
	
	
	mpz_set(numeroMenos1, numero);
	mpz_sub_ui(numeroMenos1, numeroMenos1, 1);
	
	if(mpz_even_p(numero)){
		fprintf( fout, "compuesto\n");
		return 1;
	}

	/**
	*	Paso 1
	*	Find integers k, q, with k > 0, q odd, so that n-1 = 2^kq
	**/

	mpz_set(q, numero);
	mpz_sub_ui(q, q, 1);

	mpz_set_ui(k, 0);

	while(mpz_even_p(q)){
		mpz_cdiv_q_ui(q, q, 2);
		mpz_add_ui(k,k, 1);

	}

	
	/**
	*	Paso 2
	*	Select a random integer a, 1 < a < n - 1
	**/
	mpz_urandomm(a, state, numero);
	
	if(mpz_cmp_ui(a, 0) == 0) {
		mpz_add_ui(a, a, 1);
	}
	
	/**
	*	Paso 3
	*	if a^q mod n = 1 then return inconclusive
	**/
	mpz_powm(aux, a, q, numero);

	if(mpz_cmp_ui(aux, 1) == 0){
		return 0;
	}
	if(mpz_cmp(aux, numeroMenos1) == 0){
		return 0;
	}
	

	/**
	*	Se itrea hasta k-1
	*	paso 4
	*	for j = 0 to k - 1 do
	*	Paso 5
	*	if a^((2^j) * q) mod n = n-q then return inconclusive
	**/
	k2=mpz_get_ui(k);	
	mpz_sub(aux2, numero, q);
	for (i=1; i<=k2-1; i++){

		//pow (2.0, i);
		mpz_set_ui(aux, power(2, i));
		mpz_mul(aux, aux, q);
	
		mpz_powm(aux, a, aux, numero);
		if(mpz_cmp(aux, aux2)==0){
		
			return 0;
		
		}
		if(mpz_cmp(aux, numeroMenos1)==0){
			return 0;
		
		}

		if(mpz_cmp_ui(aux, 1)==0){
			return 1;
		
		}
	}
	/**
	*	paso 6
	*
	**/
	
	return 1;
	
}


int testMillerRabinGMP(mpz_t numero, int reps) {
	
	int result;
	result = mpz_probab_prime_p(numero, reps);
	
	if(result == 2) {
		//fprintf( fout, "%d es primo\n", numero);
		gmp_fprintf( fout, "GMP: %Zd es primo\n", numero);
	}
	else if(result == 1) {
		//fprintf( fout, "%d es probablemente primo\n", numero);
		gmp_fprintf( fout, "GMP: %Zd es probablemente primo\n", numero);
	}
	else if(result == 0) {
		gmp_fprintf( fout, "GMP: %Zd es compuesto\n", numero);
		return 1;
	}
	else {
		fprintf( fout, "Algo raro ha pasado. ERROR en testMillerRabinGMP\n");
	}

	return 0;
}/*
Algoritmo:
	1: Generar un N aleatorio 0-2^n-1 
	2: Poner a 1 el bit mas significativo de n para asegurar que su tamano es de n bits
	3: Poner a 1 el bit menos significativo para asegurar que es impar
	4: dividir por la tabla de primos precalculados
	5: test MR
//*/

/*
	Funciones auxiliares para generar primos
*/
int randomNBits(mpz_t value, int nbits, gmp_randstate_t state) {

	mpz_urandomb(value, state, nbits);
	//gmp_fprintf( fout, "Random pre fix: %Zd\n", value);
	return 0;
}
/*Prepara el numero number tal que el primer y el ultimo bit son 1*/
int fixRandomNBits(mpz_t number, int nbits) {

	mpz_t mask, mask2;
	mpz_init(mask2);
	mpz_init_set_si(mask, 1);				// mask  =       1
	mpz_mul_2exp(mask2, mask, nbits - 1);	// mask2 = 1______
	mpz_ior(mask2, mask2, mask);			// mask2 = 1_____1

	//gmp_fprintf( fout, "Mask: %Zd\n", mask2);
	mpz_ior(number, number, mask2);
	mpz_clear(mask);
	mpz_clear(mask2);
	return 0;
}
int generadorPrimos(int nbits, int reps) {

	int i, contador = 0;
	int posiblePrimoEncontrado = 0;
	int posiblePrimoEncontrado2 = 0;
	mpz_t value;

	gmp_randstate_t state;

	mpz_init(value);
	/*Inicializar semilla random equivalente srand en gmp*/
	gmp_randinit_default(state);
	gmp_randseed_ui(state, time(NULL));
	while(!posiblePrimoEncontrado2) {
			contador++;
		while(!posiblePrimoEncontrado) {

			randomNBits(value, nbits, state);
			fixRandomNBits(value, nbits);
			//gmp_fprintf( fout, "Value post mask: %Zd\n", value);
			
			/*Dividir por todos los primeros 2000 primos*/
			for(i = 0; i < NUM_PRIMOS; i++) {
				posiblePrimoEncontrado = 1;
				if(mpz_divisible_ui_p(value, primos[i])) {
					// value es divisible por un primo de los 2000 primeros.
					if(mpz_cmp_ui(value, primos[i])) {
						//Comprobar el caso en el que value == primos[i]
						//gmp_fprintf( fout, "%Zd es divisible por %d\n", value, primos[i]);
						posiblePrimoEncontrado = 0;
						break;
					}
					
				}
				//fprintf( fout, "%d\n", primos[i]);
			}
		}
		posiblePrimoEncontrado = 0;
		for (i =0; i< reps; i++){
			
			posiblePrimoEncontrado2 = 1;
			if (testMillerRabin(value,1, state)) {
				
				//gmp_fprintf( fout, "es compuesto %Zd\n", value);
				posiblePrimoEncontrado2 = 0;
				break;
			}
			
		}
	}
	gmp_fprintf( fout, "es posible primo:\n%Zd\n\n", value);
	testMillerRabinGMP(value, reps);
	fprintf( fout, "\nSe probaron %d numeros antes de que se pasase el test de miller rabin para %d bits y %d reps\n\n", contador, nbits, reps);
	return 0;
}

/*Estimacion del error de MR para n bits, sec reps */
double estMR(int bits, int reps){
	int i;

	return 1/
		(1 + power(4, reps)/
			( bits*log (2.0))
			);

}



// primo -b _ -t sec [-o file]
int main(int argc, char ** argv) {

	int bits, reps;
	int long_index=0;
    char opt;
    int size=0;
    int fb = 0, ft = 0;
    static int flagC=0,flagD=0;
    static struct option options[] = {
        {"b",required_argument,0,'3'},
        {"t",required_argument, 0, '6'},
        {"o",required_argument, 0, '7'},
        {0,0,0,0}
    };
 
    fout = stdout;
    while ((opt = getopt_long_only(argc, argv,"3:6:7:", options, &long_index )) != -1){
        switch(opt){
            case '3':
            	bits = atoi(optarg);
            	fb = 1;
 
            break;
            case '6':
            	reps = atoi(optarg);
            	ft = 1;
            break;
            case '7':
                fout=fopen (optarg, "wb");
           
            break;
            case'?':
                printf("%s {-b bits } {-t sec} [-o file out ] \n", argv[0]);
            break;
 
        }
    }
    if (fb == 0 || ft == 0){
        printf("%s {-b bits } {-t sec} [-o file out ]\n", argv[0] );
        return 0;
    }

    printf("Buscando numero primos de %d bits\n", bits);
	generadorPrimos(bits, reps);
    fprintf( fout, "Probabilidad de que el test MR haya fallado: %0.16f\n", estMR(bits, reps));
    return 0;
}