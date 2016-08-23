#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <getopt.h>
#include <gmp.h>
#include <tgmath.h>
#include <limits.h>

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

int metodo(int width){
	double rate=width*1.0;
	int num=width;
	rate/=10.0;
	while(num>=width){
		num=rand();
		num=-rate*log(num*1.0/INT_MAX);
	}
	return num;
}

int ** probs(char* text,int size,char* c, int* inc){
	int** probs=NULL, i=0;
	char* str= text;
	probs=(int**)malloc(sizeof(int*)*26);
	for(i=0;i<26;i++){
		probs[i]=(int*)malloc(sizeof(int)*26);
		memset(probs[i],0,sizeof(int)*26);
	}
	for(i=0;i<26;i++,str=text){
		str=strchr(str,(char)i+97);
		while(str!=NULL){
			probs[i][c[str-text]-97]++;
			str++;		
			str=strchr(str,(char)i+97);
		}
	}
	return probs;
}
char* noEquiprobable(char* text, int size){
	int i, ninversos, seed;
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
		mpz_set_si(a, inversos[metodo(12) % ninversos]);
		mpz_set_si(b, metodo(26) % 26);

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

char* equiprobable(char* text, int size){
	int i, ninversos, seed;
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
int* incidencia(char* text, int size){
	int i=0;
	int* inc=NULL;
	inc= (int*)malloc(sizeof(int)*26);
	memset (inc,0,sizeof(int)*26);

	for(i=0;i<size;i++){
		inc[text[i]-97]++;		
	}
	return inc;
}
int main (int argc, char **argv){

	int long_index=0;
	char opt;
	char *text=NULL,*buff=NULL,fltr[100]="",*c=NULL;
	FILE  * fout =stdout, *fin=NULL;
	int ngramas=0,size=0;
	static int fp = 0,fi=0,k=0;
	int end=0, ic=0,* inc=NULL, i=0,* inversos=NULL,j=0,** incs=NULL;
	double norm=0.0;

	static struct option options[] = {
	    {"P",no_argument,&fp, 1},
	    {"I",no_argument,&fi, 1},
	    {"i",required_argument, 0, '6'},
	    {"o",required_argument, 0, '7'},
	    {0,0,0,0}
	};

	
	while ((opt = getopt_long_only(argc, argv,"3:6:7:", options, &long_index )) != -1){
		switch(opt){
			case '6':
				strcpy(fltr,"./filtro ");
				strcat(fltr, optarg);
				strcat(fltr, " F");
				strcat(fltr, optarg);
				printf("oreden=%s\n",fltr );
				if(!system(fltr)){
					return 0;
				}
				strcpy(fltr,"F");	
				
				strcat(fltr, optarg);
				printf("archivo=%s\n",fltr );
				fin=fopen (fltr, "r");
				if(fin==NULL){
					printf("\nError: el archivo %s no existe\n", optarg);
					return 0;
				}
			break;
			case '7':
				printf ("o\n");
				fout=fopen (optarg, "w");
				

			break;
			case'?':
				printf("%s {-P | -I}[-i file in] [-o file out] \n", argv[0]);
			break;

		}
	}
	if ((fp==0&&fi==0)||(fp==1&&fi==1)){
		printf("%s {-P | -I}[-i file in] [-o file out]\n", argv[0] );
		return 0;
	}
	if(fin==NULL)
		fin=stdin;
	text=(char*)malloc(512);
	buff=(char*)malloc(512);
	do{
		if(fin ==stdin)
			fscanf(fin, "%s", text);
		else{
			end=fread(buff,1,512, fin);
			size+=end;
			buff = (char*) realloc(buff,end);
			text = (char*) realloc(text,size);
			strcat(text, buff);
		}
	}while(fin!=stdin&&end==512);
	text = (char*) realloc(text,size+1);
	size=fin!=stdin?size:strlen(text);
	inc=incidencia(text,size);

	for(i=0;i<26;i++){
		if(inc[i]==0)
			continue;
		fprintf(fout, "Pp(%c)=%lf\n", i+97,inc[i]*1.0/size);

	}
	if(fp){
		c=equiprobable(text,size);
	}
	else{
		c=noEquiprobable(text,size);	
	}

	incs=probs(text, size, c, inc);

	fprintf(fout, "\n");
	for(i=0;i<26;i++){
		
		for(j=0;j<26;j++,norm=0.0){
			for(k=0;k<26;k++)
				norm+=incs[k][j]*1.0/size;
			//printf("Pc(%c)=%lf\t",j+97, norm );
			//printf("p(%c|%c)=%lf\t",j+97,i+97,(incs[i][j]*1.0/inc[i]) );
			fprintf(fout, "Pp(%c|%c) = %lf\t", i+97,j+97,((incs[i][j]*1.0/inc[i])*(inc[i]*1.0/size))/norm);
		}
		
		fprintf(fout,"\n\n");
	}

	return 0;
}
