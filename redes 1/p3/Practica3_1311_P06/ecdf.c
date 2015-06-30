#include <stdio.h> 
#include <stdlib.h> 
#include <strings.h> 
#include <string.h> 
/*Crear un array con los intervalos
Primero con un fscanf y fgets coger el numero de lineas(numero de paquetes) y tambien el valor maximo para calcular cuantos intervalos de 20 hay que coger.*/
int main(int argc, char** argv){
	int numPaquetes=0,valorMaximo=0;
	char valor[42];
	double *tabla;
	int numIntervalos=0;
	int i, tam;
	int tamIntervalo=100;
	double probabilidad=0;
	double intPrevio=0;
	double numero=0;
	FILE *f=fopen(argv[1],"r");

	//Bucle para hallar el numero de paquetes total y el valor maximo del tamano de los paquetes que hay
	while((fgets(valor, 42, f))!= NULL){
		numPaquetes++;
		if(atoi(valor)>valorMaximo){
			valorMaximo=atoi(valor);
		}
		
	}

	printf("Valor maximo= %d\n",valorMaximo);
	printf("Numero de paquetes= %d\n",numPaquetes);
	numIntervalos=valorMaximo/tamIntervalo;
	numIntervalos++;
	printf("Numero de intervalos= %d\n",numIntervalos);
	tabla = (double*) malloc((numIntervalos) * (sizeof(double)));

	rewind(f);
	
	//Bucle para meter en cada elemento de la tabla el numero de paquetes correspondiente
	while((fgets(valor, 42, f))!= NULL){
		for(i = 0, tam = tamIntervalo; i < numIntervalos; i++, tam+= tamIntervalo){
			if(atoi(valor) <= tam){
				tabla[i]++;
				break;
			}
		}
	}
	fclose(f);


	for(i=0;i<numIntervalos;i++){
		printf("%lf\n",tabla[i]);
	}
	f=fopen("salida.txt","w");

	//Bucle para hallar la probabilidad de estar en cada intervalo
	for(i = 0; i < numIntervalos; i++){
		tabla[i] /= numPaquetes;
		probabilidad += tabla[i];
		tabla[i] = probabilidad;
    }
	
	//Bucle para imprimir en el fichero de salida dos columnas. Una con los intervalos y otra con la probabilidad de cada uno
	fprintf(f, "0\t0\n");
	for(i = 0, tam = tamIntervalo; i < numIntervalos; i++, tam+= tamIntervalo){
        
        fprintf(f, "%d\t%lf\n", tam , intPrevio);
        intPrevio = tabla[i];
        fprintf(f, "%d\t%lf\n", tam , tabla[i]);
    
    }
    //Anadir un ultimo caso para el intervalo de 1
    fprintf(f, "%d\t%d", tam +tamIntervalo, 1);
    
    free(tabla);
    fclose(f);
	return 1;
}
