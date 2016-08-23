#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <getopt.h>

char * vigenereC(char * texto, char* clave, int size){

	int i=0, j=0;
	char * e=(char*)malloc(size); 
	for (; i < size; i++, j++, j = j%strlen(clave)) {
		e[i] = (texto[i]-97 + clave[j]-97)%26 + 97;
	}
	return e;
}

char* vigenereD(char * texto, char* clave, int size){
	int i=0, j=0, mod;
	char * d=(char*)malloc(size); 
	for (; i < size; i++, j++, j = j%strlen(clave)){
		mod = (texto[i] - clave[j])%26;
		d[i] = (mod >= 0 ? mod : 26 +mod) + 97;
	}
	return d;

}

int main (int argc, char **argv){

	int long_index=0;
	char opt;
	static int flagD=0,flagC=0,size=0;
	char *text=NULL,*c=NULL,*d=NULL, *buff=NULL;
	FILE  * fout =stdout, *fin=NULL;
	char k[512]="", fltr[100]="";
	int fk = 0, end=0;

	static struct option options[] = {
	    {"C",no_argument,&flagC,1},
	    {"D",no_argument,&flagD, 1},
	    {"k",required_argument,0,'3'},
	    {"i",required_argument, 0, '6'},
	    {"o",required_argument, 0, '7'},
	    {0,0,0,0}
	};

	
	while ((opt = getopt_long_only(argc, argv,"3:6:7:", options, &long_index )) != -1){
		switch(opt){
			case '3':
				printf ("clave=  %s\n", optarg);
				fk=1;
				strcpy(k, optarg);
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
				printf("%s {-C|-D} {-k clave} [-i f ile in ] [-o f ile out ]\n", argv[0]);
			break;

		}
	}
	if (fk==0 || (flagD==0 && flagC==0) || (flagD==1 && flagC==1)){
		printf("%s {-C|-D} {-k clave} [-i f ile in ] [-o f ile out ]\n", argv[0] );
		return 0;
	}
	if (fin ==NULL)
		fin=stdin;
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
		c =vigenereC(text, k,size!=0 ? size : strlen(text));
		fwrite(c,1,size>0?size:strlen(c),fout);
		free(c);
	}

	else if(flagD){
		d = vigenereD(text,k,size!=0 ? size : strlen(text));
		fwrite(d,1,size>0?size:strlen(d),fout);
		free(d);
	}
	printf("\n");
	free(text);
	fclose(fin);
	fclose(fout);
	return 0;
}
