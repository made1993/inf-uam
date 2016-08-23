/***********************************************************
 crearCDF.c	 
 Primeros pasos para implementar y validar la funcion crearCDF(). Est funcion debe devolver
 un fichero con dos columnas, la primera las muestras, la segunda de distribucion de
 probabilidad acumulada. En la version actual la funcion realiza los dos primeros pasos para
 este objetivo, cuenta el numero de muestras y las ordena.
 El alumno debe acabar su implementacion de crearCDF() y usar un main similar para validar su fucionamiento.
 
 Compila: gcc -Wall -o crearCDF crearCDF.c
 Autor: Jose Luis Garcia Dorado
 2014 EPS-UAM 
***************************************************************************/

#include <stdio.h> 
#include <stdlib.h> 
#include <strings.h> 
#include <string.h> 

#define OK 0
#define ERROR 1


typedef struct{
    int * col1;
    int * col2;
}ecfs;

int crearCDF(char* filename_data, char* filename_cdf);

int main(){
	//crearCDF("entrada.txt","salida.txt");
    parse("entrada.txt");
    
    /*int i = 0;
    FILE *f = fopen("entrada.txt", "w");
    for(i = 0; i < 200; i++){
        fprintf(f, "60\n");
    }
    for(i = 0; i < 100; i++){
        fprintf(f, "80\n");
    }
    for(i = 0; i < 60; i++){
        fprintf(f, "100\n");
    }
    for(i = 0; i < 500; i++){
        fprintf(f, "200\n");
    }
    for(i = 0; i < 80; i++){
        fprintf(f, "500\n");
    }
    fclose(f);
    printf("fin");
    */return OK;
}

int parse(char* file){
    FILE *f;
    int valor, npaquetes = 0, max = 0, clasemax;
    double anterior = 0, pacumulada = 0;
    double* tabla;
    int i, t;
    f = fopen(file, "r");
    printf("hey");
    /*Lee el fichero y encuentra el maximo*/
    while(fscanf(f,"%d\n", &valor) == 1){
      
        if(valor > max){
            max = valor;
        }
        npaquetes++;
    }
    /*Vuelve al comienzo del fichero*/
    rewind(f);
    clasemax = max/20;
    if(max % 20 != 0) clasemax++;
    tabla = (double*) malloc((clasemax) * (sizeof(double)));
    while(fscanf(f,"%d\n", &valor) != EOF){
        /*Coloca cada valor en su clase*/
        /*Iterar sobre cada div*/
        for(i = 0, t = 20; i < clasemax; i++, t+= 20){
            if(valor <= t){
                tabla[i]++;
                break;
            }
        }
    }
    fclose(f);
    
    /*Calcular la probabilidad de cada clase*/
    for(i = 0; i < clasemax; i++){
        tabla[i] /= npaquetes;
        pacumulada += tabla[i];
        tabla[i] = pacumulada;
    }
    
    f = fopen("salida.txt", "w");
    fprintf(f, "0\t0\n");
    for(i = 0, t = 20; i < clasemax; i++, t+= 20){
        
        fprintf(f, "%d\t%lf\n", t , anterior);
        anterior = tabla[i];
        fprintf(f, "%d\t%lf\n", t , tabla[i]);
    
    }
    
    fprintf(f, "%d\t%d", max + 20, 1);
    
    free(tabla);
    fclose(f);
    return 0;
}

int crearCDF(char* filename_data, char* filename_cdf) {
	char comando[255]; char linea[255]; char aux[255];
	int num_lines;
        ecfs tabla;
	FILE *f;
//sin control errores
	sprintf(comando,"wc -l %s 2>&1",filename_data,filename_cdf);
	printf("Comando en ejecucion: %s\n",comando);
	f = popen(comando, "r");
	if(f == NULL){
		printf("Error ejecutando el comando\n");
		return ERROR;
	}
	fgets(linea,255,f);
	printf("Retorno: %s\n",linea);
	sscanf(linea,"%d %s",&num_lines,aux);
	pclose(f);

	sprintf(comando,"sort -n < %s > %s 2>&1",filename_data,filename_cdf);
	printf("Comando en ejecucion: %s\n",comando);
	f = popen(comando, "r");
	if(f == NULL){
		printf("Error ejecutando el comando\n");
		return ERROR;
	}
	bzero(linea,255);
	fgets(linea,255,f);
	printf("Retorno: %s\n",linea);
	pclose(f);

//crear CDF
        

	return OK;
}

