
#include "p3ej3.h"

/*int main(int argc, char* argv[]){
	int tam=0;
	struct timeval t_ini, t_fin;
	num **A=NULL, **B=NULL, **e_matrix=NULL, **t_matrix=NULL, **B_t=NULL;
	int i=0, j=0;
	tam=atoi(argv[1]);
	
	A=generateMatrix(tam);
	B=generateMatrix(tam);
	e_matrix=generateEmptyMatrix(tam);
	t_matrix=generateEmptyMatrix(tam);
	B_t=generateEmptyMatrix(tam);
	
	gettimeofday(&t_ini,NULL);
	mult(A, B, e_matrix,tam);
	gettimeofday(&t_fin,NULL);
	printf("%f\n", ((t_fin.tv_sec*1000000+t_fin.tv_usec)-(t_ini.tv_sec*1000000+t_ini.tv_usec))*1.0/1000000.0);
	
			
	traspuesta(B, B_t, tam);
	gettimeofday(&t_ini, NULL);
	mult_t(A, B_t, t_matrix,tam);
	gettimeofday(&t_fin, NULL);
	printf("%f\n", ((t_fin.tv_sec*1000000+t_fin.tv_usec)-(t_ini.tv_sec*1000000+t_ini.tv_usec))*1.0/1000000.0);
	
	
	printf("Normal\n");
	for(i=0; i<tam; i++){
		for(j=0; j<tam; j++){
			printf("| %f |", B[i][j]);
		}
		printf("\n");
	}
	printf("\nTraspueta\n");
	for(i=0; i<tam; i++){
		for(j=0; j<tam; j++){
			printf("| %f |", B_t[i][j]);
		}
		printf("\n");
	}
	freeMatrix(A);
	freeMatrix(B);
	freeMatrix(B_t);
	freeMatrix(e_matrix);
	freeMatrix(t_matrix);
	return 0;
} */

void mult(num** a, num** b, num** c, int n){
	int i=0, j=0, k=0;
	
	for (i=0; i<n; i++){
		for (j=0; j<n; j++){
			c[i][j] = 0;
			for (k=0; k<n; k++){
				c[i][j] += a[i][k] * b[k][j];
			}
		}
	}
}

void mult_t(num** a, num** b, num** c, int n){
	int i=0, j=0, k=0;
	
	for (i=0; i<n; i++){
		for (j=0; j<n; j++){
			c[i][j] = 0;
			for (k=0; k<n; k++){
				c[i][j] += a[i][k] * b[j][k];
			}
		}
	}
}

void traspuesta(num**a, num**t, int n){
	int i=0, j=0;
	for(i=0; i<n; i++){
		for(j=0; j<n; j++){
			t[j][i]=a[i][j];
		}
	}	
}





