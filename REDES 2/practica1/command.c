#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main(){
	FILE * f=NULL;
	FILE * s=NULL;
	char str[] ="- This, a sample string.";
	char * pch;
	printf("1\n");
	f=fopen("commandos", "r");
	s=fopen("case.c", "w");
	if(f==NULL){
		return 0;
	}
	
	printf("1\n");
	while(fscanf(f,"%s", str)>=0){
		printf("1\n");
		pch = strtok (str," ,\n\t");
		printf("1\n");
		while (pch != NULL){
			fprintf(s,"case %s:\n\tprintf('%s?');\nbreak;\n",pch,pch);
			pch = strtok (NULL, " ,\n\t");
		}
	}

	
}
