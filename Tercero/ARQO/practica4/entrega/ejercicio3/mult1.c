#include <stdio.h>
#include <omp.h>
#include <stdlib.h>
#include <sys/time.h>
#include "arqo4.h"

int main(int argc, char** argv)
{
	float **A=NULL, **B=NULL, **C=NULL;
	int i=0,j=0,k=0;
	struct timeval fin,ini;
	unsigned long long num;
	unsigned long thr;
	int sum=0; 
	thr=atoi(argv[1]);
	num=atoi(argv[2]);


	A = generateMatrix(num);
	B = generateMatrix(num);
	C = generateEmptyMatrix(num);
	if ( !A || !B || !C)
	{
		printf("Error when allocationg matrix\n");
		freeMatrix(A);
		freeMatrix(B);
		freeMatrix(C);
		return -1;
	}
	
	gettimeofday(&ini,NULL);
	
	omp_set_num_threads(thr);
	/* Bloque de computo */
	#pragma omp parallel for private(k,i,j) firstprivate(A,B) shared(C) schedule(static)
	for (i=0; i<num; i++){
		for (j=0; j<num; j++){
			for (k=0; k<num; k++){
				C[i][j]+= A[i][k] * B[k][j];
			}		
		}
	}

	/* Fin del computo */
	gettimeofday(&fin,NULL);

	//printf("Resultado: %f\t%f\t%f\n",C[0][0],C[1][1],C[2][2]);
	printf("Tiempo: %f\n", ((fin.tv_sec*1000000+fin.tv_usec)-(ini.tv_sec*1000000+ini.tv_usec))*1.0/1000000.0);

	freeMatrix(A);
	freeMatrix(B);
	freeMatrix(C);

	return 0;
}

