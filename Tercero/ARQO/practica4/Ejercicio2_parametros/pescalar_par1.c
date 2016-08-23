#include <stdio.h>
#include <omp.h>
#include <stdlib.h>
#include "arqo4.h"

int main(int argc, char** argv)
{
	float *A=NULL, *B=NULL;
	long long k=0;
	struct timeval fin,ini;
	float sum=0;
	unsigned long long num;
	unsigned long thr;
	
	thr=atoi(argv[1]);
	num=atoi(argv[2]);

	omp_set_num_threads(thr);	

	A = generateVector(num);
	B = generateVector(num);
	if ( !A || !B )
	{
		printf("Error when allocationg matrix\n");
		freeVector(A);
		freeVector(B);
		return -1;
	}
	
	gettimeofday(&ini,NULL);
	/* Bloque de computo */
	sum = 0;
	#pragma omp parallel for
	for(k=0;k<num;k++)
	{
		sum = sum + A[k]*B[k];
	}
	/* Fin del computo */
	gettimeofday(&fin,NULL);

	printf("Resultado: %f\n",sum);
	printf("Tiempo: %f\n", ((fin.tv_sec*1000000+fin.tv_usec)-(ini.tv_sec*1000000+ini.tv_usec))*1.0/1000000.0);
	freeVector(A);
	freeVector(B);

	return 0;
}
