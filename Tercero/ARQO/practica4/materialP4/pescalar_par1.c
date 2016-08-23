#include <stdio.h>
#include <stdlib.h>
#include "arqo4.h"

int main(void)
{
	float *A=NULL, *B=NULL;
	long long k=0;
	struct timeval fin,ini;
	float sum=0;
	float arr[M]={0}; 
	
	A = generateVector(M);
	B = generateVector(M);
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
	for(k=0;k<M;k++)
	{
		sum = sum + A[k]*B[k];
		arr[k]=sum;
	}
	/* Fin del computo */
	gettimeofday(&fin,NULL);

	printf("Resultado: %f\n",sum);
	printf("Tiempo: %f\n", ((fin.tv_sec*1000000+fin.tv_usec)-(ini.tv_sec*1000000+ini.tv_usec))*1.0/1000000.0);
	for(k=0;k<M;k++){
		printf("sum:%lf\n",arr[k]);
	}
	freeVector(A);
	freeVector(B);

	return 0;
}
