#include <stdio.h>
#include <stdlib.h>

int main (int argc, char **argv){

	int long_index=0;
	char opt;
	char *text=NULL,*buff=NULL;
	FILE  * fout =stdout, *fin=NULL;
	int ngramas=0,size=0;
	int fk = 0, end=0, ic=0;
	static int fLimit=0;

	static struct option options[] = {
	    {"n",required_argument,0,'3'},
	    {"i",required_argument, 0, '6'},
	    {"o",required_argument, 0, '7'},
	    {0,0,0,0}
	};

	
	while ((opt = getopt_long_only(argc, argv,"3:6:7:", options, &long_index )) != -1){
		switch(opt){
			case '3':
				printf ("ngramas=  %s\n", optarg);
				fk=1;
				ngramas=atoi(optarg);
			break;
			case '6':
				printf ("i\n");
				fin=fopen (optarg, "r");
				if(fin==NULL){
					printf("\nError: el archivo %s no existe\n", optarg);
					return;
				}
			break;
			case '7':
				printf ("o\n");
				fout=fopen (optarg, "w");
				

			break;
			case'?':
				printf("%s {-n} [-i file in ] [-o file out ] \n", argv[0]);
			break;

		}
	}
	if (fk==0){
		printf("%s {-n} [-i file in ] [-o file out ]\n", argv[0] );
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
	
}
