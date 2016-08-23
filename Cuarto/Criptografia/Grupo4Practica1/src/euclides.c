#include <stdio.h>
#include <stdlib.h>
//#include <gmp.h>
/*#include "gmp.h"*/


int* euclidesExtendido(int a, int b) {

  int *resp;
  resp = (int*) malloc(3*sizeof(int));
  int x = 0, y = 0, d = 0;
  int x2, x1, y2, y1, q, r;
  if (b==0) {
    resp[0] = a; resp[1] = 1; resp[2] = 0;
  }
  else {
    x2 = 1;
    x1 = 0;
    y2 = 0;
    y1 = 1;
    q = 0;
    r = 0;

    while(b>0) {
      q = (a/b);
      r = a - q*b;
      x = x2-q*x1;
      y = y2 - q*y1;
      a = b;
      b = r;
      x2 = x1;
      x1 = x;
      y2 = y1;
      y1 = y; 
    }

    resp[0] = a;
    resp[1] = x2;
    resp[2] = y2;
  }
  return resp;
}
//mcd=0,x=0,y=0 en un inicio
int euclidesExtendidoRec(int a, int b, int mcd, int x, int y) {

  int x2=0,y2=0,x1=0,y1=0;
  if (b == 0)  { 
      mcd = a;
      x2 = 1;
      y2 = 0;
  }
  else
  {     
      mcd = euclidesExtendidoRec (b,a%b,mcd,x,y);
      x1= x2; y1=y2; x2=y1;
      y2=x1- (a/b)*y1;   
  }
  
  return mcd;
}

/*Devuelve array con inversos multiplicativos posibles para Zm
 * Se debe liberar memoria del retorno
 */
int* inversosMultiplicativos(int m, int* size) {
	int * buffer = (int*) malloc (m * sizeof(int));
	int * inversos;	
	int inv = 0;
	int i;
	int j = 0;
	for (i = 0; i < m; i++) {
		inv = euclidesExtendidoRec(i, m, 0, 0, 0);
		if(inv == 1) {
			buffer[j] = i;
			j++;
		}
	}

	inversos = (int *) malloc (j * sizeof(int));
	for(i = 0; i < j; i++) {
		inversos[i] = buffer[i];
	}
	free(buffer);
	*size = j;
	return inversos;
}


/* PROGRAMA PRINCIPAL */
int main (int argc,char *argv[]) {
	int *inv;  
	int i = 0;
	int size;
int *results;
	char text[20] = "asdasdasdasdasdasd";
	char *cosa;  
  int a = 4;
  int b = 26;
  /*results = euclidesExtendido(a, b);
  printf("mcd = %d\n", results[0]);
  printf("x = %d\n", results[1]);
  printf("y = %d\n", results[2]);

  printf("mcd(%d, %d) = %d = %d*%d + %d*%d\n", a, b, results[0], results[1], a, results[2], b);
  mcd = euclidesExtendidoRec(a, b, 0, 0, 0);
  printf("%d\n", mcd);
  free(results);*/

	int c[5];
	int mcd;
	c[0] = 165;
	c[1] = 235;
	c[2] = 275;
	c[3] = 285;
	mcd = c[0];
	
	cosa = &text[5];
	printf("cosa-text%d\n", cosa - text);
	printf("%d %d\n", cosa, text);
	for(i = 1; i < 5; i++){
			
		results = euclidesExtendido(mcd, c[i]);
		mcd = results[0];
		free(results);
		printf("%d\n", mcd);	
	}
	printf("%d\n", mcd);
}

