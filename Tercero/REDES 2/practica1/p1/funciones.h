#ifndef _SOCKET_H
#define _SOCKET_H
#include <sys/types.h>
#include <sys/socket.h>
int abrirSocketTCP();
int abrirSocketUDP();
int abrirBind(int sockfd,const struct sockaddr_in *addr);
int aceptar(int sockfd, struct sockaddr_in ip4addr);
int abrirConnect(int sockfd, struct sockaddr ip4addr);
int abrirListen(int sockfd);
int recibir(int sockfd,char *buf);
int escribir(int sockfd,char *msg);
#endif
