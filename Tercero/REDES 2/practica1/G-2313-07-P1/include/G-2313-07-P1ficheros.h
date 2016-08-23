#ifndef _FICHEROS_H
#define _FICHEROS_H #include <stdio.h>
#include <stdlib.h>
#include <pcap.h>
#include <string.h>
#include <netinet/in.h>
#include <linux/udp.h>
#include <linux/tcp.h>
#include <signal.h>
#include <time.h>
#include <sys/ioctl.h>
#include <net/if.h>
#include <unistd.h>
#include <netinet/ether.h>
#include <netinet/if_ether.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <inttypes.h>
#include <math.h>
#include "../include/G-2313-07-P1conexion.h"
FILE * abrirArchivos(char * fichero);
int getTamanoFichero(char* fichero);
void * clienteArchivo(void * in);
void * servidorArchivo(void * inx);
#endif