#include <stdio.h>
#include <string.h>
#include "funciones.h"
/*TODO includes*/


int getTamanoFichero(char* fichero){
	FILE * f=NULL;
	int tam=-1;
	printf("abriendo fichero: %s\n", fichero);
	f = fopen(fichero, "rb");
	if(f==NULL)
			return -1;
	fseek(f, 0L, SEEK_END);
  	tam =ftell(f);
  	printf("tamano: %d\n", tam);
  	fclose(f);
  	return tam;
}


/*int IPCharADecimal(char * ip){
	char * pch;
	int elevado = 3;
	int sumaIP = 0;
	int sumaParcial = 0;
	
	pch = strtok (ip,".");
	while (pch != NULL){
		sumaParcial = atoi(pch);
		for(j= 0; j < elevado; ++j){
			sumaParcial*=256;
		}

		pch = strtok (NULL, ".");
		sumaIP += sumaParcial;
		elevado--;
	}
	return sumaIP;
}*/

/*int conexionPruebaCliente(char * direccion,char * puerto){
	struct addrinfo hints, *res;
	int sockfd, sock_server;
	char msg[1000];
    memset(&hints, 0, sizeof hints);
	hints.ai_family = AF_UNSPEC;  // use IPv4 or IPv6, whichever
	hints.ai_socktype = SOCK_STREAM;
	printf("Abriendo socket\n");
	getaddrinfo("172.16.187.180", "2020", &hints, &res);
	sockfd = abrirSocketTCP();
	if (sockfd==-1){
		printf("ERROR: socket\n");
		return -1;
	}
	
	printf("Conectando\n");
	if(-1==(sock_server==abrirConnect(sockfd,*(res->ai_addr)))){
		printf("ERROR: connect\n");
		return -1;
	}
	
	// convert to network byte order
	// send data normally:
	while (1){
		recibir(sockfd, msg);
		printf("Recibido:%s\n", msg);
		strcpy(msg,"PONG");
		sleep(2);
		//send(sockfd,msg,strlen(msg),0);
		escribir(sockfd, msg);	
	}
	return 0;
}


int conexionPruebaServidor(){
	int sockfd, socketClient, aux = 1;
	struct sockaddr_in ip4addr;
	char buf[1000];
	printf("Abriendo socket\n");
	sockfd = abrirSocketTCP();
	if (sockfd==-1){
		printf("ERROR: socket\n");
		return -1;
	}
	printf("Abriedo bind\n");
	if( -1==abrirBind(sockfd)){
		printf("ERROR: bind\n");
		return -1;	
	}
	printf("Escuchando\n");
	if(-1==abrirListen(sockfd)){
		printf("ERROR: listen\n");
		return -1;			
	}
	printf("Esperando accept\n");
	socketClient = aceptar(sockfd, ip4addr);
	if(socketClient ==-1){
		printf("ERROR: accept\n");
		return -1;	
	}

	printf("sock_client:%d, sockfd:%d\n", socketClient,sockfd);
	while( aux > 0){
		sleep(2);
		strcpy(buf,"PING");
		write(socketClient, buf , strlen(buf));
		aux=recv(socketClient, buf,1000,0);
		if(aux == 0){
			printf("fin de la conexion\n");
			return 0;
		}
		buf[aux]='\0';
		printf("Recibido:%s\n",buf );
	}
	//byte_count = recv(sockfd, buf, sizeof buf, 0);
	//printf("recv()'d %d bytes of data in buf\n", byte_count);
	
	return 0;
}
*/




