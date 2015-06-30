#ifndef _SOCKET_H
#define _SOCKET_H
 #include <stdio.h>
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
typedef struct argumentos{
	char direccion[20];
	char strPuerto[20];
	char *archivo;
	int intPuerto;
};

struct argumentos args;
int abrirSocketTCP();
int abrirSocketUDP();
int abrirBind(int sockfd,int puerto);
int aceptar(int sockfd, struct sockaddr_in ip4addr);
int abrirConnect(int sockfd, struct sockaddr ip4addr);
int abrirListen(int sockfd);
int recibir(int sockfd,char *buf);
int escribir(int sockfd,char *msg);
void * clienteArchivo(void * in);
void * servidorArchivo(void * inx);
char* atoIp(char* str);
uint8_t obtenerIPInterface(char * interface, uint8_t* retorno);

#endif
