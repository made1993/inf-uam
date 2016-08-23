#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
#include <string.h>
#include <gmp.h>


mpz_t* inverso(mpz_t a ,mpz_t base){
	mpz_t x , y, d, a1;
	mpz_t *resp;
	mpz_t x2, x1, y2, y1, q, r,aux,auxbase;
  	resp = (mpz_t*) malloc(3*sizeof(mpz_t));
	mpz_init_set(auxbase,base);
	mpz_init_set(a1,a);
	mpz_init(y);
	mpz_init(d);
	mpz_init(x);
	mpz_init(x1);
	mpz_init(x2);
	mpz_init(y2);
	mpz_init(y1);
	mpz_init(q);
	mpz_init(aux);
	mpz_init(r);
	if (mpz_get_si(auxbase)==0) {
    	mpz_init_set(resp[0], a1);
    	mpz_init_set_si(resp[1], 1);
    	mpz_init(resp[2]);
  	}
	else {
		mpz_set_si(x2, 1);
		mpz_set_si(x1, 0);
		mpz_set_si(y2, 0);
		mpz_set_si(y1, 1);
		mpz_set_si(q, 0);
		mpz_set_si(r, 0);
		while(mpz_get_ui(auxbase)>0) {
			mpz_cdiv_q(q,a1,auxbase); //q = (a/b);
			
			mpz_mul(aux,q,auxbase);
			mpz_sub(r,a1,aux);			//r = a - q*b;

			mpz_mul(aux, q,x1);			
			mpz_sub(x,x2,aux);			//x = x2-q*x1;
			
			mpz_mul(aux, q,y1);
			mpz_sub(y,y2,aux);			//y = y2 - q*y1;
			
			mpz_set(a1,auxbase);		//a = b;

			mpz_set(auxbase,r);			//b = r

			mpz_set(x2,x1);				//x2 = x1

			mpz_set(x1,x);				//x1 = x

			mpz_set(y2,y1);				//y2 = y1
			
			mpz_set(y1,y);				//y1 = y
		}
		mpz_init_set(resp[0], a1);
		mpz_init_set(resp[1], x2);
	    mpz_init_set(resp[2], y2);
	}
	mpz_clear(auxbase);
	mpz_clear(a1);
	mpz_clear(y);
	mpz_clear(d);
	mpz_clear(x);
	mpz_clear(x1);
	mpz_clear(x2);
	mpz_clear(y2);
	mpz_clear(y1);
	mpz_clear(q);
	mpz_clear(aux);
	mpz_clear(r);
	return resp;
}

int euclidesExtendidoRec(int a, int b, int mcd, int x, int y) {

  int x2=0,y2=0,x1=0,y1=0;
  if (b == 0)  { 
      mcd = a;
      x2 = 1;
      y2 = 0;
  }
  else {     
      mcd = euclidesExtendidoRec (b,a%b,mcd,x,y);
      x1= x2; y1=y2; x2=y1;
      y2=x1- (a/b)*y1;   
  }
  
  return mcd;
}
/*Devuelve array con inversos multiplicativos posibles para Zm
 * Se debe liberar memoria del retorno
 */
int* inversosMultiplicativos(int m, int* size) {
	int * buffer = (int*) malloc (m * sizeof(int));
	int * inversos;	
	int inv = 0;
	int i;
	int j = 0;
	for (i = 0; i < m; i++) {
		inv = euclidesExtendidoRec(i, m, 0, 0, 0);
		if(inv == 1) {
			buffer[j] = i;
			j++;
		}
	}

	inversos = (int *) malloc (j * sizeof(int));
	for(i = 0; i < j; i++) {
		inversos[i] = buffer[i];
	}
	free(buffer);
	*size = j;
	return inversos;
}



//La semilla funciona como clave del cifrado.
//Genera los parámetros a y b para cada letra a cifrar.
char* afinmejoradoC(char* text, int seed, int size){
	int i, ninversos;
	int *inversos;
	char* c = NULL;
	mpz_t x, a, b, zm;
	c = (char *) malloc(size);
	srand(seed);
	
	//Obtener un array con los numeros con inverso multiplicativo posibles para z26
	inversos = inversosMultiplicativos(26, &ninversos);
	mpz_init(x);
	mpz_init(a);
	mpz_init(b);
	mpz_init(zm);
	mpz_set_si(zm, 26);
	for (i = 0; i < size; i++){
		//Obtener a, b
		mpz_set_si(a, inversos[rand() % ninversos]);
		mpz_set_si(b, rand() % 26);

		mpz_set_si(x, (long)(text[i] - 97));//x = text[i] - 97;
		mpz_mul(x, x, a); 					//x *= inversos[a];
		mpz_add(x, x, b); 					//x += b;
		mpz_mod(x, x, zm);					//x = x % 26;

		c[i] = (char)(mpz_get_si(x));
		c[i] += 97;
	}
	mpz_clear(x);
	mpz_clear(a);
	mpz_clear(b);
	mpz_clear(zm);
	free(inversos);
	return c;
}
char* afinmejoradoD(char* text, int seed, int size){
	int i, ninversos;
	int *inversos;
	char* d = NULL;
	mpz_t x, a, b, zm, inv;
	mpz_t* mcd= NULL;
	d = (char*) malloc(size);
	srand(seed);
	inversos = inversosMultiplicativos(26, &ninversos);


	mpz_init(x);
	mpz_init(a);
	mpz_init(b);
	mpz_init(zm);
	mpz_set_si(zm, 26);

	for(i = 0; i < size; i++) {

		mpz_init(inv);
		mpz_set_si(a, inversos[rand() % ninversos]);
		mpz_set_si(b, rand() % 26);

		
		//Calcular el inverso de a, para poder multiplicar
		mcd=inverso(a,zm);
		mpz_set(inv, mcd[1]);
		if(mpz_sgn(mcd[0]) == -1){
			mpz_sub(inv, inv, mcd[1]);
			mpz_sub(inv, inv, mcd[1]);
			mpz_add(inv, inv, zm);
		}

		mpz_set_si(x, (long)(text[i] - 97));
		mpz_sub(x, x, b);
		mpz_mod(x, x, zm);
		mpz_mul(x, x, inv);
		mpz_mod(x, x, zm);
		d[i] = mpz_get_si(x);
		d[i] +=97;
		//Limpiar mcd
		mpz_clear(mcd[0]);
		mpz_clear(mcd[1]);
		mpz_clear(mcd[2]);
		mpz_clear(inv);
		free(mcd);
	}
	mpz_clear(x);
	mpz_clear(a);
	mpz_clear(b);
	mpz_clear(zm);
	mpz_clear(inv);
	free(inversos);
	return d;
}


int main (int argc, char **argv){

	int long_index=0, end=0, size=0, seed = 1;
	char opt;
	static int flagD=0,flagC=0;
	char* text=NULL,*c=NULL,*d=NULL,* buff=NULL,fltr[100]="";
	FILE  * fout =stdout, *fin=stdin;
	int fseed=0;
	static struct option options[] = {
	    {"C",no_argument,&flagC,1},
	    {"D",no_argument,&flagD, 1},
	    {"s",required_argument,0,'3'},
	    {"i",required_argument, 0, '6'},
	    {"o",required_argument, 0, '7'},
	    {0,0,0,0}
	};

	optarg =NULL;
	while ((opt = getopt_long_only(argc, argv,"3:4:5:6:7:", options, &long_index )) != -1){
		switch(opt){
			case '3':
				fseed=1;
				seed = atoi(optarg);
				printf("Seed = %d\n", seed);
			break;
			case '6':
				if(flagC){
					strcpy(fltr,"./filtro ");
					strcat(fltr, optarg);
					strcat(fltr, " F");
					strcat(fltr, optarg);
					printf("orden=%s\n",fltr );
					if(!system(fltr)){
						return 0;
					}
					strcpy(fltr,"F");	
				}
				strcat(fltr, optarg);
				printf("archivo=%s\n",fltr );
				fin=fopen (fltr, "r");
				if(fin==NULL){
					printf("\nError: el archivo %s no existe\n", optarg);
					return 0;
				}
			break;
			case '7':
				fout=fopen (optarg, "w");
			break;
			case'?':
				printf("%s {-C|-D} {-s seed} [-i f ile in ] [-o f ile out ]\n", argv[0]);
			break;

		}
	}
	if (fseed==0 || flagD==0 && flagC==0 || flagD==1 && flagC==1){
		printf("%s {-C|-D} {-s seed} [-i f ile in ] [-o f ile out ]\n", argv[0] );
		return 0;
	}

	buff = (char*)malloc(512);
					
	do{
		if(fin==stdin){
			text=buff;
			fscanf(fin,"%s",text);
		}else{
			end=fread(buff,1,512, fin);
			size+=end;
			buff = (char*) realloc(buff,end+1);
			text = (char*) realloc(text,size+1);
			strncat(text, buff,end);
		}
	}while(fin!=stdin && end==512);

	if(flagC){

		c = afinmejoradoC(text, seed, size>0?size:strlen(text));
		fwrite(c,sizeof(char),size>0?size:strlen(text),fout);
		free(c);
	}
	else if(flagD){
		d = afinmejoradoD(text, seed, size>0?size:strlen(text));
		fwrite(d,1,size>0?size:strlen(text),fout);
		free(d);

	}
	if(fout==stdout) printf("\n");
	free(text);
	if(fin !=stdin) free(fin);
	if(fout !=stdout)free(fout);
	return 0;
}