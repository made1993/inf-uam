#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main(int argc, char ** argv){
	FILE * s=NULL;
	char *str;
	char * pch;
	printf("1\n");
	s=fopen("w", "case.c");
	printf("1\n");
	
	pch = strtok (argv[1]," ,\n\t");
	printf("1\n");
	while (pch != NULL){
		fprintf(s,"case %s:\n\t printf(\"%s\");\nbreak;\n",pch, pch);
		pch = strtok (NULL, " ,\n\t");
	}

	
}
