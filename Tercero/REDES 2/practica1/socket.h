#ifndef _SOCKET_H
#define _SOCKET_H
#include <sys/types.h>
#include <sys/socket.h>
int abrirSocketTCP();
int abrirSocketUDP();
int abrirBind(int sockfd,const struct sockaddr_in *addr);
#endif