#ifndef SSLCINEXION_H
#define SSLCINEXION_H

#include <string.h>
#include <openssl/bio.h>
#include <openssl/err.h>
#include <openssl/rand.h>
#include <openssl/ssl.h>
#include <openssl/x509v3.h>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netdb.h>
#include <netinet/in.h>
#include <linux/udp.h>
#include <linux/tcp.h>


int abrirSocketTCP();
int abrirSocketUDP();
int abrirBind(int sockfd,int puerto);
int aceptar(int sockfd, struct sockaddr_in ip4addr);
int abrirConnect(int sockfd, struct sockaddr ip4addr);
int abrirListen(int sockfd, int tam);
int recibir(int sockfd,char *buf);
int escribir(int sockfd,char *msg);
char* atoIp(char* str);
uint8_t obtenerIPInterface(char * interface, uint8_t* retorno);


SSL_CTX* inicializar_nivel_SSL(int * sock);
int fijar_contexto_SSL(SSL_CTX* ctx, const char* CAfile, const char* prvKeyFile,const char* certFile);
SSL* conectar_canal_seguro_SSL(SSL_CTX* ctx,int sock,struct sockaddr res);
SSL* aceptar_canal_seguro_SSL(SSL_CTX* ctx,int sockfd,int puerto,int tam,struct sockaddr_in ip4addr);
int evaluar_post_connectar_SSL(SSL * ssl);
int enviar_datos_SSL(SSL * ssl,const void * buf);
int recibir_datos_SSL(SSL * ssl, void * buf);
void cerrar_canal_SSL(SSL *ssl,SSL_CTX *ctx, int sockfd);

void servidor();

void cliente();

#endif