#include <errno.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "../includes/G-2313-07-P3ficheros.h"
/*TODO includes*/


FILE * abrirArchivos(char * fichero){
	FILE * f;
	f = fopen(fichero, "wb");
	return f;
}

/**
 *
 * @brief Devuelve el tamaño de un fichero
 * @page getTamanoFichero \b clienteArchivo
 *
 * @section SYNOPSIS
 * 	\b #include \b "conexion.h"
 *
 *	\b int \b getTamanoFichero \b (\b char * fichero \b )
 * 
 * @section descripcion DESCRIPCIÓN
 *	
 *	Dado el nombre de un fichero devulve su tamaño si este existe.
 *
 *	fichero sera el nombre del fichero a mandar.
 *
 * @section retorno RETORNO
 * Devuelve el tamaño del fichero en caso de exito y -1 en caso de error.
 *	
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/
int getTamanoFichero(char* fichero){
	FILE * f=NULL;
	int tam=-1;
	//printf("abriendo fichero: %s\n", fichero);
	f = fopen(fichero, "rb");
	if(f==NULL)
		return -1;
	fseek(f, 0L, SEEK_END);
  	tam =ftell(f);
  	//printf("tamano: %d\n", tam);
  	fclose(f);
  	return tam;
}

/**
 *
 * @brief Prepara la conexion con el emisor del archivo
 * @page clienteArchivo \b clienteArchivo
 *
 * @section SYNOPSIS
 * 	\b #include \b "conexion.h"
 *
 *	\b void \b clienteArchivo \b (\b void * in \b )
 * 
 * @section descripcion DESCRIPCIÓN
 *	
 * 	Funcion que establece un cliente para solicitar un archivo 
 * 	cuyo nombre se recibira mediante funciones globales.
 *
 * @section retorno RETORNO
 * No devulve nada.
 *	
 * @section seealso VER TAMBIÉN
 * \b servidorArchivo(3).
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/
void* clienteArchivo(void * in){
	struct addrinfo hints, *res;
	int sockfd, aux=1, aux2=0;
	char msg[8096];
	FILE * f=NULL;

	printf("conectando con: %s %s para recibir %s\n",atoIp(args.direccion),args.strPuerto,args.archivo );
	f=abrirArchivos(args.archivo);
	if(0!=getaddrinfo(atoIp(args.direccion), args.strPuerto, &hints, &res)){
		return (void*)-1;
	}
	sockfd = abrirSocketTCP();
	if (sockfd==-1){
		printf("ERROR: socket\n");
		return (void*)-1;
	}
	
	printf("Conectando\n");
	if(-1==abrirConnect(sockfd, *(res->ai_addr))) {
		printf("ERROR: connect\n");
		return (void*)-1;
	}
	printf("Recibida conexion \n");
	while( aux > 0){
		aux=recibirArchivo(sockfd, msg);
		if(aux <8096){
			fwrite(msg,sizeof(char), aux,f);
			printf("tam del fichero actualmente: %d\n",getTamanoFichero(args.archivo));
			aux2+=aux;
			printf("tam que debe tener el fichero: %d\n",aux2);
			printf("fin de la conexion\n");
			break;
		}
		printf("recibendo informacion tam = %d \n",aux);
		fwrite(msg,sizeof(char), aux,f);
		printf("tam del fichero actualmente: %d\n",getTamanoFichero(args.archivo));
		aux2+=aux;
		printf("tam que debe tener el fichero: %d\n",aux2);
	}
	fclose(f);
	printf("tam del fichero al finalizar: %d\n",getTamanoFichero(args.archivo));
	close(sockfd);
	return (void*)0;
}

/**
 *
 * @brief Prepara la conexion con el emisor del archivo
 * @page servidorArchivo \b servidorArchivo
 *
 * @section SYNOPSIS
 * 	\b #include \b "conexion.h"
 *
 *	\b void \b servidorArchivo \b (\b void * in \b )
 * 
 * @section descripcion DESCRIPCIÓN
 *	
 *	Funcion que establece la conexion del lado del servidor
 *	para compartir un archivo que se ontendra mediante funciones
 *	globales ya que esta ideada para que use hilos.
 *
 * @section retorno RETORNO
 * No devulve nada.
 *	
 * @section seealso VER TAMBIÉN
 * \b clienteArchivo(3).
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/
void* servidorArchivo(void * in){
	int sockfd, socketClient, aux = 0, tam = 0;
	struct sockaddr_in ip4addr;
	char buf[8096];
	char buf2[8096];
	FILE *f=NULL;
	
	char puerto[10];
	sprintf(puerto,"%d", args.intPuerto);
	printf("abriendo puerto %d para enviar el archivo %s\n",  args.intPuerto,args.archivo);
	printf("Abriendo socket\n");
	sockfd = abrirSocketTCP();
	if (sockfd==-1){
		printf("ERROR: socket\n");
		return (void*)-1;
	}
	printf("Abriendo bind\n");

	aux = abrirBind(sockfd,args.intPuerto);
	if( -1==aux){
		printf("ERROR: bind\n");
		return (void*)-1;	
	}

	printf("Escuchando\n");
	if(-1==abrirListen(sockfd,1)){
		printf("ERROR: listen\n");
		return (void*)-1;			
	}
	printf("Esperando accept\n");
	socketClient = aceptar(sockfd, ip4addr);
	if(socketClient ==-1){
		printf("ERROR: accept\n");
		return (void*)-1;	
	}
	printf("sock_client:%d, sockfd:%d\n", socketClient,sockfd);
	tam = getTamanoFichero(args.archivo);
	f = fopen(args.archivo, "rb");
	while (aux<tam){
		printf("DEBUG lleva %d de %d\n", aux, tam);
		if(tam-aux < 8096){//tam-aux < 8096 --> petaria
			aux=fread(buf2, sizeof(char), tam-aux,f);
			printf("aux==%d\n",tam-aux);
			escribirArchivo(socketClient, buf2,aux);
			break;
			
		}else{
			aux+=fread(buf, sizeof(char), 8096,f);
			escribirArchivo(socketClient, buf, 8096);
			printf("enviando: %d\n", aux);
		}
		
	}
	fclose(f);
	f=NULL;
	sleep(2);
	close(sockfd);
	printf("fin de la conexion\n");
	//byte_count = recv(sockfd, buf, sizeof buf, 0);
	//printf("recv()'d %d bytes of data in buf\n", byte_count);
	
	return (void*)0;
}
