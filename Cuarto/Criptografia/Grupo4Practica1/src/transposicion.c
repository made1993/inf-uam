#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <getopt.h>

#define ERR -1
#define OK (!(ERR))

int swap(int *a, int *b){
	int c;
	if(a == NULL || b == NULL){
		return ERR;
	}
	c = *a;
	*a = *b;
	*b = c;
	return OK;
}

/*******************************************/
/* Funcion: aleat_num	Fecha: 		       */
/* Autores:	Alberto Cabello                */
/*			Iñaki Domínguez                */
/* Rutina que genera un numero aleatorio   */
/* entre dos numeros dados		           */
/*					                       */
/* Entrada:				                   */
/* int inf: limite inferior		           */
/* int sup: limite superior		           */
/* Salida:				                   */
/* int: numero aleatorio		           */
/*******************************************/

int aleat_num(int inf, int sup) {
    if (inf > sup || sup > RAND_MAX || inf < 0) {
        return ERR;
    }
    return ((double) ((double) (sup - inf + 1) / RAND_MAX) * rand()) + inf;
}


/***************************************************/
/* Funcion: genera_perm Fecha:                     */
/* Autores:	Alberto Cabello            	           */
/*			Iñaki Domínguez 	                   */
/* Rutina que genera una permutacion               */
/* aleatoria                                       */
/*                                                 */
/* Entrada:                                        */
/* int n: Numero de elementos de la                */
/* permutacion                                     */
/* Salida:                                         */
/* int *: puntero a un array de enteros            */
/* que contiene a la permutacion                   */
/* o NULL en caso de error                         */
/***************************************************/
int* genera_perm(int n) {
    int i, *perm = NULL, aleat;
    if (n <= 0) {
        return NULL;
    }
    perm = (int *) malloc(n * sizeof(int));
    if (perm == NULL) {
        return NULL;
    }
    for (i = 0; i < n; i++) {
        perm[i] = i;
    }
    for (i = 0; i < n; i++) {
        aleat = aleat_num(0, n - 1);
        if (aleat == ERR) {
            free(perm);
            return NULL;
        }
        if(swap(&perm[i], &perm[aleat]) == ERR){
			free(perm);
			return NULL;
		}
    }
    return perm;
}

/*size devuelve el tamaño*/
int * perm_from_string(char* string, int* size) {
	
	int i;
	int *intbuffer;
	int tokens = 0;
	char *ptr;
	char *buffer = (char*) malloc(strlen(string) * sizeof(char));
	intbuffer = (int * ) malloc(strlen(string) * sizeof(char));

	strcpy(buffer, string);
	ptr = strtok(buffer, " ");
        
	while(ptr != NULL) {
		
		intbuffer[tokens] = atoi(ptr);
		ptr = strtok(NULL, " ");
		tokens++;
	}
    *size = tokens;
	return intbuffer;

}
//					holamundo		2 4 0 1 3
char * transposicionC(char * texto, int* clave, int size, int sizeperm){

	int i, j;
	char * e=(char*)malloc(size); 
	for (i = 0; i < size; i += sizeperm) {
		//printf("%d\n", i);
		for (j = 0; j < sizeperm; j++) {
			if(i+clave[j]>=size){
				texto[i + clave[j]]=(char)aleat_num(97, 122);
			}

			e[i + j] = texto[i + clave[j]];
			//printf("->%c\n", e[i + j]);
		}
	}
	return e;
}

char* transposicionD(char * texto, int* clave, int size, int sizeperm){
	int i=0, j=0, mod;
	char * d=(char*)malloc(size); 
	for (i = 0; i < size; i += sizeperm) {
		
		for (j = 0; j < sizeperm; j++) {
			d[i + clave[j]] = texto[i + j];
		}
	}
	return d;

}
int main (int argc, char **argv){

	int long_index=0,i=0;
	char opt;
	static int flagD=0,flagC=0;
	char *text=NULL,*c=NULL,*d=NULL, *buff=NULL;
	FILE  * fout =stdout, *fin=NULL;
	FILE *permout; 
	char k[512]="", fltr[100]="";
	int fn = 0, fp = 0, end=0;
	int *permutacion;
	int size = 0, sizeperm;
	static struct option options[] = {
	    {"C",no_argument,&flagC,1},
	    {"D",no_argument,&flagD, 1},
	    {"p",required_argument,0,'3'},
	    {"n",required_argument,0,'4'},
	    {"i",required_argument, 0, '6'},
	    {"o",required_argument, 0, '7'},
	    {0,0,0,0}
	};

	srand(time(NULL));
	while ((opt = getopt_long_only(argc, argv,"3:4:6:7:", options, &long_index )) != -1){
		switch(opt){
			case '3':
				printf ("permutacion=  %s\n", optarg);
				permutacion = perm_from_string(optarg, &sizeperm);
				printf("sizeperm %d\n", sizeperm);
				for (i = 0; i < sizeperm; i++) {
					printf("%d ", permutacion[i]);
				}
				fp=1;
				strcpy(k, optarg);
			break;
			case '4':
				printf ("Tamano permutacion=  %s\n", optarg);
				fn=1;
				sizeperm = atoi(optarg);
				permutacion = genera_perm(sizeperm);

				/*Escribir en permutacion.dat*/
				permout = fopen("permutacion.dat", "w");
				for ( i = 0; i < sizeperm; i++) {
					fprintf(permout, "%d ", permutacion[i]);
				}
				fclose(permout);
			break;
			case '6':
				if(flagC){
					strcpy(fltr,"./filtro ");
					strcat(fltr, optarg);
					strcat(fltr, " F");
					strcat(fltr, optarg);
					printf("oreden=%s\n",fltr );
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
				printf("%s {-C|-D} {-p permutacion | -n Nperm} [-i f ile in ] [-o f ile out ]\n", argv[0]);
			break;

		}
	}

	if ((fp==0 && fn==0) || (fp==1 && fn==1) || (flagD==0 && flagC==0) || (flagD==1 && flagC==1)){
		printf("%s {-C|-D} {-p permutacion | -n Nperm} [-i f ile in ] [-o f ile out ]\n", argv[0] );
		return 0;
	}
	if (fin ==NULL)
		fin=stdin;
	
	buff = (char*)malloc(513);
	
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
	//printf("%02X\n",text);

	if(flagC) {
		c =transposicionC(text, permutacion,size!=0 ? size : strlen(text), sizeperm);
		fwrite(c,1,strlen(c),fout);
		free(c);
		c=NULL;
	}

	else if(flagD){
		d = transposicionD(text,permutacion,size!=0 ? size : strlen(text), sizeperm);
		fwrite(d,1,strlen(d),fout);
		free(d);
		d=NULL;
	}
	printf("\n");
	free(buff);
	buff=NULL;
	if(fin !=stdin)
		free(text);
	text=NULL;
	fclose(fin);
	fin=NULL;
	fclose(fout);
	fout=NULL;
	free(permutacion);
	permutacion=NULL;
	return 0;
}
