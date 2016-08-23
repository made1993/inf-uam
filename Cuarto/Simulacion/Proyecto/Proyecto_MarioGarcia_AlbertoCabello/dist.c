#include <math.h>
#include <stdio.h>
double duCl(double* pobl, int x, int y, int nrow){
	return sqrt( pow(pobl[x] - pobl[y], 2) + pow( pobl[x + nrow] - pobl[y+ nrow], 2));

}

void dist(int* nrow, int* ncol, double* matrix, double* pobl) {
	int i=0;
	int j=0;
	for(i=0; i< *nrow;i++){
		for(j=i; j< *nrow; j++){
			matrix[j+ (*ncol)*i] = matrix[i +(*ncol)*j]= duCl(pobl, i, j, *nrow);	
		}
	}
}



