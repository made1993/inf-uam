#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include "p3ej3.h"

int main(int argc, char** argv){
	int tam=0;
	struct timeval t_ini, t_fin;
	num **A=NULL, **B=NULL, **t_matrix=NULL, **B_t=NULL;
	int i=0, j=0;
	tam=atoi(argv[1]);
	
	/*Generar las matrices*/
	A=generateMatrix(tam);
	B=generateMatrix(tam);
	t_matrix=generateEmptyMatrix(tam);
	B_t=generateEmptyMatrix(tam);
	
	/*Multiplicar matrices*/	
	traspuesta(B, B_t, tam);
	gettimeofday(&t_ini, NULL);
	mult_t(A, B_t, t_matrix,tam);
	gettimeofday(&t_fin, NULL);
	printf("%f\n", ((t_fin.tv_sec*1000000+t_fin.tv_usec)-(t_ini.tv_sec*1000000+t_ini.tv_usec))*1.0/1000000.0);
	freeMatrix(A);
	freeMatrix(B);
	freeMatrix(B_t);
	freeMatrix(t_matrix);
	return 0;
}
