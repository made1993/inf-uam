#ifndef _FICHEROS_H
#define _FICHEROS_H
#include <sys/types.h>
#include <sys/socket.h>
FILE * abrirArchivos(char * fichero);
int getTamanoFichero(char* fichero);
#endif