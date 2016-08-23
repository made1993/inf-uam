#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>

#include "arqo3.h"

num compute(num **matrix,int n);

int main( int argc, char *argv[])
{
	int n;
	num **m=NULL;
	struct timeval fin,ini;
	num res=0;

	if( argc!=2 )
	{
		printf("Error: ./%s <matrix size>\n", argv[0]);
		return -1;
	}
	n=atoi(argv[1]);
	m=generateMatrix(n);
	if( !m )
	{
		return -1;
	}


	
	gettimeofday(&ini,NULL);

	/* Main computation */
	res = compute(m,n);
	/* End of computation */

	gettimeofday(&fin,NULL);
	printf("%f\t", ((fin.tv_sec*1000000+fin.tv_usec)-(ini.tv_sec*1000000+ini.tv_usec))*1.0/1000000.0);/*tiempo de ejecucion*/
	
	free(m);
	return 0;
}


num compute(num **matrix,int n)
{
	num sum;
	int i,j;
	
	for(i=0;i<n;i++)
	{
		for(j=0;j<n;j++)
		{
			sum += matrix[j][i];
		}
	}
	return sum;
}
