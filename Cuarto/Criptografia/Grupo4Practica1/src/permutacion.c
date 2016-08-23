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


char* permC(char * text, int* ki, int* kj, int st, int si,int sj){
	int j=0,i=0,s=0, k=si;
	char* out=NULL;
	out=(char*)malloc(sizeof(char)*(st + si*sj-st%(si*sj)+1));
	printf("alloc=%d  + %d\n",st ,si*sj-st%(si*sj));
	for(s=0;s<st + si*sj-st%(si*sj);s+=si*sj){
		for(i=0;i<si;i++){
			for(j=0;j<sj;j++){
				//printf("%d > %d \n", s +i+j*k,st);
				if(s +i+j*k>=st){
					//printf("se activa en =%d para %d  %c\n",s +i+j*k,s + kj[j]*k+ki[i],text[s +i+j*k]);	
					out[s + kj[j]*k+ki[i]]=(char)aleat_num(97, 122);
					
				}
				else{
					out[s + kj[j]*k+ki[i]]=text[s +i+j*k];	
				}
				
			}
		}
	}
	out[s]=0;
	return out;

}

char* permD(char * text, int* ki, int* kj, int st, int si,int sj){
	int j=0,i=0,s=0, k=si;
	char* out=NULL;
	out=(char*)malloc(sizeof(char)*(st + st%(si*sj)+1));
	printf("alloc=%d\n",(st + st%(si*sj)+1));
	for(s=0;s<st;s+=si*sj){
		for(i=0;i<si;i++){
			for(j=0;j<sj;j++){ 
				out[s +j*k+i] = text[s +kj[j]*k+ki[i]];
			}
		}
	}
	out[s]='\0';
	return out;
 
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
	int *k1,*k2;
	int size = 0, sizeperm1,sizeperm2;
	static struct option options[] = {
	    {"C",no_argument,&flagC,1},
	    {"D",no_argument,&flagD, 1},
	    {"k1",required_argument,0,'3'},
	    {"k2",required_argument,0,'4'},
	    {"i",required_argument, 0, '6'},
	    {"o",required_argument, 0, '7'},
	    {0,0,0,0}
	};

	srand(time(NULL));
	while ((opt = getopt_long_only(argc, argv,"3:4:6:7:", options, &long_index )) != -1){
		switch(opt){
			case '3':
				printf ("calve 1=  %s\n", optarg);
				k1 = perm_from_string(optarg, &sizeperm1);
				printf("sizeperm %d\n", sizeperm1);
				for (i = 0; i < sizeperm1; i++) {
					printf("%d ", k1[i]);
				}
				fp=1;
			break;
			case '4':
				printf ("calve 2=  %s\n", optarg);
				k2= perm_from_string(optarg, &sizeperm2);
				printf("sizeperm %d\n", sizeperm2);
				for (i = 0; i < sizeperm2; i++) {
					printf("%d ", k2[i]);
				}
				fp=1;
			break;
			case '6':
				printf ("i\n");
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
				printf ("o\n");
				fout=fopen (optarg, "w");
				

			break;
			case'?':
				printf("%s {-C|-D} {-k1 clave 1} {-k2 clave 2} [-i f ile in ] [-o f ile out ]\n", argv[0]);
			break;

		}
	}

	if ((fp==0 && fn==0) || (fp==1 && fn==1) || (flagD==0 && flagC==0) || (flagD==1 && flagC==1)){
		printf("%s {-C|-D} {-k1 clave 1} {-k2 clave 2} [-i f ile in ] [-o f ile out ]\n", argv[0] );
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
	//		printf("bucle size %d\n", size);
	//printf("end %d\n", end);
	}while(fin!=stdin && end==512);
	//printf("al medio\n");

	if(flagC) {
		c =permC(text,k1,k2,size!=0 ? size : strlen(text), sizeperm1,sizeperm2);
		fwrite(c,1,strlen(c),fout);
		free(c);
	}

	else if(flagD){
		d = permD(text,k1,k2,size!=0 ? size : strlen(text), sizeperm1,sizeperm2);
		fwrite(d,1,strlen(d),fout);
		free(d);
	}
	printf("\n");
	printf("1\n");
	free(text);
	printf("2\n");
	fclose(fin);
	printf("3\n");
	fclose(fout);
	printf("4\n");
	free(k1);
	free(k2);
	printf("5\n");
	return 0;
}
