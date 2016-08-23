#ifndef _TYPES_H_
#define _TYPES_H_
#include <stdio.h>
#define ESCALAR 1
#define VECTOR 2
#define BOOL 1
#define INT 2
#define MAX_TAMANIO_VECTOR 64
FILE * nasm;
    typedef struct  {
        char *lexema;
		int tipo;
		int valor_entero;
		int es_direccion;
		int etiqueta;
    }tipo_atributos;

    typedef struct{
    	char * lexema;  //identificador
    	int categoria;	//variable, parametro de funcion y funcion
    	int tipo;		//entero o boleano
    	int clase;		//escalar o vector
    	int tam;		//tamaño, solo para vectores
    	int nparam;		//numero de parametros, para funciones
    	int pparam;		//posicion del parametro, para paramentros de funcion
    	int nvar;		//numero de variables locales, para funciones
    	int pvar;		//posicion de variables locales, para parametros de funcion
    }nodo;
#endif
