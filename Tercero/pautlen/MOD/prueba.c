#include <stdio.h>
#include <string.h>
int main(){
	char ** dec;
	char pr[20]="hola mmmmmmmmmmmmm	";
	dec=malloc(1);
	dec[0]=(char*)malloc(sizeof(char)*strlen(pr));
	strcpy(dec[0],pr);
	printf("%s\n", dec[0]);
	
	dec[1]=(char*)malloc(sizeof(char)*20);
	strcpy(dec[1],pr);
	printf("%s\n", dec[1]);
	
	dec[2]=(char*)malloc(sizeof(char)*20);
	strcpy(dec[2],pr);
	printf("%s\n", dec[2]);
	free(dec[2]);
	free(dec[1]);
	free(dec[0]);
	free(dec);
}