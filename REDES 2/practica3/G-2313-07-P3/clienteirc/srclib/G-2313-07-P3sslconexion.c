#include "../includes/G-2313-07-P3sslconexion.h"


/**
 *
 * @brief Convierte una cadena en formato Ip.
 * @page atoIp \b atoIp
 *
 * @section SYNOPSIS
 * 	\b #include \b "conexion.h"
 *
 *	\b char * \b atoIp \b (\b char * str\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *	
 * Convierte una cadena con el valor decimal de la direccion IP
 * En otra con el valor estandar 0.0.0.0
 * 
 * Recibe un array de char con el valor decimal de la IP
 *
 * @section retorno RETORNO
 * Devuelve el valor de la ip en formato estandar 0.0.0.0
 *
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/
char* atoIp(char* str){
	unsigned int intIp;
	char * strIp=NULL;
	if (str==NULL)
		return NULL;
	strIp=(char*)malloc(sizeof(char)*14);
	intIp=(unsigned int)atoi(str);
	sprintf(strIp,"%d.%d.%d.%d",intIp<<24>>24,intIp<<16>>24,intIp<<8>>24,intIp>>24);
	return strIp;
}

/**
 *
 * @brief Obtiene la ip de una interfaz
 * @page obtenerIPInterface \b obtenerIPInterface
 *
 * @section SYNOPSIS
 * 	\b #include \b "conexion.h"
 *
 *	\b uint8_t \b obtenerIPInterface \b (\b char * str char * interface \b, uint8_t* retorno \b )
 * 
 * @section descripcion DESCRIPCIÓN
 *	
 * Devuelve la IP de un interfaz.
 * 
 * Recibe el nombre de la interfaz.
 *
 * Recibe un enetero donde se rellena la direccion.
 *
 * @section retorno RETORNO
 * Devuelve 0 en caso de error y 1 en caso de exito.
 *
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/
uint8_t obtenerIPInterface(char * interface, uint8_t* retorno){
	int fd;
	struct ifreq ifr;
	fd = socket(AF_INET, SOCK_DGRAM, 0);
	if(fd<0) {
		printf("socket_ERROR\n");
		return 0;
	}
	ifr.ifr_addr.sa_family = AF_INET;
	strncpy(ifr.ifr_name, interface, IFNAMSIZ-1);
	if (ioctl(fd, SIOCGIFADDR, &ifr)<0){
		printf("IOCTL_ERROR\n");
		return 0;
	}
	close(fd);
	memcpy(retorno,&(*(struct sockaddr_in *)&ifr.ifr_addr).sin_addr,sizeof(uint8_t)*4);
	printf("Retorno obtenerIPInterface():\n");
	printf("\t%"PRIu8".%"PRIu8".%"PRIu8".%"PRIu8"\n",retorno[0],retorno[1],retorno[2],retorno[3]);
	return 1;
}


/**
 *
 * @brief Abre un socket TCP
 * @page abrirSocketTCP \b abrirSocketTCP
 *
 * @section SYNOPSIS
 * 	\b #include \b "conexion.h"
 *
 *	\b int \b abrirSocketTCP \b ( )
 * 
 * @section descripcion DESCRIPCIÓN
 *	
 * Abre un socket TCP
 *
 * Si ocurre un erro imprime un mensaje.
 *
 * @section retorno RETORNO
 * Devuelve -1 en caso de error y 0 en caso de exito.
 *
 *@section seealso VER TAMBIÉN
 *  \b abrirSocketUDP(3), \b abrirBind(3), \b abrirListen(3), \b abrirConnect(3), \b aceptar(3),\b recibir(3), \b escribir(3).
 *
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/
int abrirSocketTCP(){
	int sockfd=socket(AF_INET,SOCK_STREAM,IPPROTO_TCP);
	if(sockfd < 0)
		return -1;
	return sockfd;
}
/**
 *
 * @brief Abre un socket UDP
 * @page abrirSocketTCP \b abrirSocketTCP
 *
 * @section SYNOPSIS
 * 	\b #include \b "conexion.h"
 *
 *	\b int \b abrirSocketUDP \b ( )
 * 
 * @section descripcion DESCRIPCIÓN
 *	
 * Abre un socket UDP
 *
 * Si ocurre un erro imprime un mensaje.
 *
 * @section retorno RETORNO
 * Devuelve -1 en caso de error y 0 en caso de exito.
 *
 *@section seealso VER TAMBIÉN
 * \b abrirSocketTCP(3), \b abrirBind(3), \b abrirListen(3), \b abrirConnect(3), \b aceptar(3),\b recibir(3), \b escribir(3).
 *
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/
int abrirSocketUDP(){
	int sockfd=socket(AF_INET,SOCK_DGRAM,IPPROTO_UDP);
	if(sockfd < 0)
		return -1;
	return sockfd;

}
/**
 *
 * @brief hace bind
 * @page abrirBind \b abrirBind
 *
 * @section SYNOPSIS
 * 	\b #include \b "conexion.h"
 *
 *	\b int \b abrirBind \b (\b int sockfd, \b int puerto )
 * 
 * @section descripcion DESCRIPCIÓN
 *	
 * Hace bind y gestiona los posibles errores
 *
 * Si ocurre un erro imprime un mensaje.
 *
 * @section retorno RETORNO
 * Devuelve -1 en caso de error y 0 en caso de exito.
 *
 *@section seealso VER TAMBIÉN
 * \b abrirSocketTCP(3), \b abrirSocketUDP(3), \b abrirListen(3), \b abrirConnect(3), \b aceptar(3),\b recibir(3), \b escribir(3).
 *
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/
int abrirBind(int sockfd,int puerto){
	struct sockaddr_in ip4addr;
	ip4addr.sin_family = AF_INET;
	ip4addr.sin_port = htons(puerto);
	ip4addr.sin_addr.s_addr=INADDR_ANY;
	/*bzero((void  *)&(ip4addr.sin_zero), 8);*/
	if(bind(sockfd, (struct sockaddr*)&ip4addr, sizeof(ip4addr))==-1)
		return -1;
	return 0;
}

/**
 *
 * @brief pone un socket en modo listen
 * @page abrirListen \b abrirListen
 *
 * @section SYNOPSIS
 * 	\b #include \b "conexion.h"
 *
 *	\b int \b abrirListen \b (\b int sockfd \b )
 * 
 * @section descripcion DESCRIPCIÓN
 *	
 * Pone un socket en modo listen y gestiona los errores.
 *
 * @section retorno RETORNO
 * Devuelve -1 en caso de error y 0 en caso de exito.
 *
 *@section seealso VER TAMBIÉN
 * \b abrirSocketTCP(3), \b abrirSocketUDP(3), \b abrirBind(3), \b abrirConnect(3), \b aceptar(3),\b recibir(3), \b escribir(3).
 *
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/
int abrirListen(int sockfd,int tam){
	int lstn;
	lstn = listen(sockfd, tam);
	if( lstn == -1)		
		return -1;
	return 0;
}

/**
 *
 * @brief pone un socket en modo aceptar
 * @page aceptar \b aceptar
 *
 * @section SYNOPSIS
 * 	\b #include \b "conexion.h"
 *
 *	\b int \b aceptar \b (\b int sockfd ,\b struct sockaddr_in ip4addr \b )
 * 
 * @section descripcion DESCRIPCIÓN
 *	
 * Pone un socket en modo aceptar y gestiona los errores.
 *
 * @section retorno RETORNO
 * Devuelve -1 en caso de error y 0 en caso de exito.
 *
 @section seealso VER TAMBIÉN
 * \b abrirSocketTCP(3), \b abrirSocketUDP(3), \b abrirBind(3), \b abrirListen(3), \b abrirConnect(3), \b recibir(3), \b escribir(3).
 *
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/
int aceptar(int sockfd, struct sockaddr_in ip4addr){
	int acc=0;
	int addrlen =sizeof(ip4addr);
	acc = accept(sockfd, (struct sockaddr*)&ip4addr, (socklen_t*)&addrlen);
	
	if(acc == -1)
		return -1;
	return acc;
}

/**
 *
 * @brief pone un Connect en modo aceptar
 * @page abrirConnect \b aceptar
 *
 * @section SYNOPSIS
 * 	\b #include \b "conexion.h"
 *
 *	\b int \b abrirConnect \b (\b int sockfd ,\b struct sockaddr res \b )
 * 
 * @section descripcion DESCRIPCIÓN
 *	
 * Pone un socket en modo connect y gestiona los errores.
 *
 * @section retorno RETORNO
 * Devuelve -1 en caso de error y 0 en caso de exito.
 *
 *@section seealso VER TAMBIÉN
 * \b abrirSocketTCP(3), \b abrirSocketUDP(3), \b abrirBind(3), \b abrirListen(3),  \b aceptar(3),\b recibir(3), \b escribir(3).
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/
int abrirConnect(int sockfd, struct sockaddr res){
	int cnct=0;
	cnct= connect(sockfd, &res, sizeof(res));
	if (cnct==-1)
		return -1;
	return 0;
}

/**
 *
 * @brief pone un socket en modo de recepcion
 * @page recibir \b recibir
 *
 * @section SYNOPSIS
 * 	\b #include \b "conexion.h"
 *
 *	\b int \b recibir \b (\b int sockfd ,\b char * buf \b )
 * 
 * @section descripcion DESCRIPCIÓN
 *	
 * Pone un socket en modo de recepcion, escribe el mensaje en buf y gestiona los errores.
 *
 * @section retorno RETORNO
 * Devuelve -1 en caso de error y 0 en caso de exito.
 *	
 * @section seealso VER TAMBIÉN
 * \b abrirSocketTCP(3), \b abrirSocketUDP(3), \b abrirBind(3), \b abrirListen(3), \b abrirConnect(3), \b aceptar(3), \b escribir(3).
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/
int recibir(int sockfd,char *buf){
	int aux=0;
	if(ssl == NULL){
		printf("RECIBIDO MENSAJE\n");
		aux = recv(sockfd, buf, 8096, 0);
	}else{
		printf("RECIBIDO MENSAJE SSL\n");
		aux = recibir_datos_SSL(ssl, buf);
	}
	buf[aux]='\0';
	if (aux==-1)
		return -1;
	return aux;
}

int recibirArchivo(int sockfd,char *buf){
	int aux=0;
	aux = recv(sockfd, buf, 8096, 0);
	
	if (aux==-1)
		return -1;
	return aux;
}

/**
 *
 * @brief pone un socket en modo de escritura
 * @page escribir \b escribir
 *
 * @section SYNOPSIS
 * 	\b #include \b "conexion.h"
 *
 *	\b int \b escribir \b (\b int sockfd ,\b char * msg \b )
 * 
 * @section descripcion DESCRIPCIÓN
 *	
 * Envia un mensaje y gestiona los errores.
 *
 * @section retorno RETORNO
 * Devuelve -1 en caso de error y 0 en caso de exito.
 *	
 * @section seealso VER TAMBIÉN
 * \b abrirSocketTCP(3), \b abrirSocketUDP(3), \b abrirBind(3), \b abrirListen(3), \b abrirConnect(3), \b aceptar(3).
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/
int escribir(int sockfd,char *msg){
	int aux = -1;
	if(ssl == NULL){
		printf("ESCRIBIR MENSAJE\n");
		aux = send(sockfd,msg,strlen(msg),0);
	}else{
		printf("ESCRIBIR MENSAJE SSL\n");
		aux = enviar_datos_SSL(ssl, msg);
	}
	if(-1==aux)		
		return -1;
	return aux;  
}


int escribirArchivo(int sockfd, char *msg, int longitud){
	int aux = -1;
	aux = send(sockfd,msg,longitud,0);
	if(-1==aux)		
		return -1;
	return aux;
}

/**
 *
 * @brief inicializa el nivel SSL
 * @page inicializar_nivel_SSL \b inicializar_nivel_SSL
 *
 * @section SYNOPSIS
 * 	\b #include \b "conexion.h"
 *
 *	\b SSL_CTX* \b inicializar_nivel_SSL \b (\b int * sock\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *	
 * Esta función se encargará de realizar todas las llamadas necesarias para que la aplicación
 * pueda usar la capa segura SSL.
 *
 * @section retorno RETORNO
 * Devuelve NULL en caso de error o la estructrua SSL_CTX* en caso de exito  .
 *	
 * @section seealso VER TAMBIÉN
 * \b fijar_contexto_SSL(3), \b conectar_canal_seguro_SSL(3), \b aceptar_canal_seguro_SSL(3),
 * \b evaluar_post_connectar_SSL(3), \b enviar_datos_SSL(3), \b  recibir_datos_SSL(3), \b cerrar_canal_SSL(3) \b
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/
SSL_CTX* inicializar_nivel_SSL(int * sock){
	if(sock==NULL){
		return NULL;
	}
	*sock=abrirSocketTCP();
	if(*sock==-1)
		return NULL;
	SSL_load_error_strings();	
	SSL_library_init();
	ERR_print_errors_fp(stdout);

	return SSL_CTX_new(SSLv3_method());
}

/**
 *
 * @brief fija el contexto de una conexion SSL
 * @page fijar_contexto_SSL \b fijar_contexto_SSL
 *
 * @section SYNOPSIS
 * 	\b #include \b "conexion.h"
 *
 *	\b int \b fijar_contexto_SSL \b (\b SSL_CTX* ctx, \b const char* CAfile, 
 * \b const char* prvKeyFile, \b const char* certFile\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *	
 * Esta función se encargará de inicializar correctamente el contexto que será utilizado para
 * la creación de canales seguros mediante SSL. Deberá recibir información sobre las rutas a los certificados y
 * claves con los que vaya a trabajar la aplicación.
 *
 * @section retorno RETORNO
 * Devuelve 0 en caso de error o 1 en caso de exito  .
 *	
 * @section seealso VER TAMBIÉN
 * \b inicializar_nivel_SSL(3), \b conectar_canal_seguro_SSL(3), \b aceptar_canal_seguro_SSL(3),
 * \b evaluar_post_connectar_SSL(3), \b enviar_datos_SSL(3), \b  recibir_datos_SSL(3), \b cerrar_canal_SSL(3) \b
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/

int fijar_contexto_SSL(SSL_CTX* ctx, const char* CAfile, const char* prvKeyFile,const char* certFile){
	char CApath [1024];
	if (ctx==NULL)
		return 0;
	if(getcwd(CApath, sizeof(CApath))==NULL)
		return 0;
	strcat(CApath, "/cert");
	printf("CApath:%s, CAfile:%s, prvKeyFile:%s, certFile:%s\n",CApath, CAfile, prvKeyFile, certFile );
	if (!SSL_CTX_load_verify_locations(ctx,CAfile,CApath)){
		printf("SSL_CTX_load_verify_locations ERROR\n");
		return 0;
	}
	SSL_CTX_set_default_verify_paths(ctx);
	ERR_print_errors_fp(stdout);
	if(!SSL_CTX_use_certificate_chain_file(ctx, certFile)){
		printf("ERROR SSL_CTX_use_certificate_chain_file\n");
		ERR_print_errors_fp(stdout);
		return 0;
	}
	
	SSL_CTX_use_PrivateKey_file(ctx, prvKeyFile, SSL_FILETYPE_PEM);
	ERR_print_errors_fp(stdout);
	
	SSL_CTX_set_verify(ctx, SSL_VERIFY_PEER | SSL_VERIFY_FAIL_IF_NO_PEER_CERT, NULL);
	
	ERR_print_errors_fp(stdout);
	return 1;
}

/**
 *
 * @brief realiza la conexion a un servidor SSL en esquema TCP.
 * @page conectar_canal_seguro_SSL \b conectar_canal_seguro_SSL
 *
 * @section SYNOPSIS
 * 	\b #include \b "conexion.h"
 *
 *	\b SSL* \b conectar_canal_seguro_SSL \b (\b SSL_CTX* ctx, \b int sockfd, 
 * \b struct sockaddr res \b )
 * 
 * @section descripcion DESCRIPCIÓN
 *	
 * Dado un contexto SSL y un descriptor de socket esta función se encargará de
 * obtener un canal seguro SSL inciando el proceso de handshake con el otro extremo.
 *
 * @section retorno RETORNO
 * Devuelve NULL en caso de error o la estructura SSL* inicializada  en caso de exito.
 *	
 * @section seealso VER TAMBIÉN
 * \b inicializar_nivel_SSL(3), \b fijar_contexto_SSL(3), \b aceptar_canal_seguro_SSL(3),
 * \b evaluar_post_connectar_SSL(3), \b enviar_datos_SSL(3), \b  recibir_datos_SSL(3), \b cerrar_canal_SSL(3) \b
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/

SSL* conectar_canal_seguro_SSL(SSL_CTX* ctx,int sockfd,struct sockaddr res){
	SSL * ssl=NULL;
	printf("connect:%d\n",abrirConnect(sockfd,res));
	printf("errno:%d\n",errno );
	ssl=SSL_new(ctx);

	if(ssl==NULL)
		return NULL;
	ERR_print_errors_fp(stdout);
	printf("SSL_set_fd\n");
	if(!SSL_set_fd(ssl, sockfd)){
		return NULL;
	}
	ERR_print_errors_fp(stdout);
	printf("SSL_connect\n");
	if(!SSL_connect(ssl)){
		return NULL;
	}
	return ssl;
}

/**
 *
 * @brief monta un servidor TCP con conexion SSL
 * @page aceptar_canal_seguro_SSL \b aceptar_canal_seguro_SSL
 *
 * @section SYNOPSIS
 * 	\b #include \b "conexion.h"
 *
 *	\b SSL* \b aceptar_canal_seguro_SSL \b (\b SSL_CTX* ctx, \b cint sockfd, \b int puerto, \b int tam,
 *  \b struct sockaddr_in ip4addr\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *	
 * Dado un contexto SSL y un descriptor de socket esta función se encargará de
 * bloquear la aplicación, que se quedará esperando hasta recibir un handshake por parte del cliente.
 *
 * @section retorno RETORNO
 *  Devuelve NULL en caso de error o la estructura SSL* inicializada  en caso de exito.
 *	
 * @section seealso VER TAMBIÉN
 * \b inicializar_nivel_SSL(3), \b fijar_contexto_SSL(3), \bconectar_canal_seguro_SSL(3), \b evaluar_post_connectar_SSL(3),
 * \b enviar_datos_SSL(3), \b  recibir_datos_SSL(3), \b cerrar_canal_SSL(3) \b
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/
SSL* aceptar_canal_seguro_SSL(SSL_CTX* ctx,int sockfd,int puerto,int tam,struct sockaddr_in ip4addr){
	SSL * ssl=NULL;
	int sockclient=-1;
	abrirBind(sockfd,puerto);
	abrirListen(sockfd,tam);
	sockclient=aceptar(sockfd, ip4addr);
	ssl=SSL_new(ctx);
	if(ssl==NULL)
		return NULL;
	ERR_print_errors_fp(stdout);
	printf("SSL_set_fd\n");
	if(!SSL_set_fd(ssl, sockclient))
		return NULL;
	ERR_print_errors_fp(stdout);
	printf("SSL_accept\n");
	if(!SSL_accept(ssl))
		return NULL;
	return ssl;
}

/**
 *
 * @brief fija el contexto de una conexion SSL
 * @page evaluar_post_connectar_SSL \b evaluar_post_connectar_SSL
 *
 * @section SYNOPSIS
 * 	\b #include \b "conexion.h"
 *
 *	\b int \b fijar_contexto_SSL \b (\b SSL_CTX* ctx, \b const char* CAfile, 
 * \b const char* prvKeyFile, \b const char* certFile\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *	
 * Esta función comprobará una vez realizado el handshake que el canal de co-
 * municación se puede considerar seguro.
 *
 * @section retorno RETORNO
 * Devuelve 0 en caso de error o 1 en caso de exito  .
 *	
 * @section seealso VER TAMBIÉN
 * \b inicializar_nivel_SSL(3), \b conectar_canal_seguro_SSL(3), \b aceptar_canal_seguro_SSL(3),
 * \b fijar_contexto_SSL(3), \b enviar_datos_SSL(3), \b  recibir_datos_SSL(3), \b cerrar_canal_SSL(3) \b
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/
int evaluar_post_connectar_SSL(SSL * ssl){
	if(SSL_get_peer_certificate(ssl)==NULL){
		printf("SSL_get_peer_certificate\n");
		return 0;
	}
	printf("%ld\n", SSL_get_verify_result(ssl));
	printf("%d\n", X509_V_OK);
	if(SSL_get_verify_result(ssl)!=X509_V_OK){
		printf("SSL_get_verify_result\n");
		return 0;
	}
	return 1;
}

/**
 *
 * @brief fija el contexto de una conexion SSL
 * @page fijar_contexto_SSL \b fijar_contexto_SSL
 *
 * @section SYNOPSIS
 * 	\b #include \b "conexion.h"
 *
 *	\b int \b fijar_contexto_SSL \b (\b SSL_CTX* ctx, \b const char* CAfile, 
 * \b const char* prvKeyFile, \b const char* certFile\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *	
 * Esta función será el equivalente a la función de envío de mensajes que se realizó en la
 * práctica 1, pero será utilizada para enviar datos a través del canal seguro. Es importante que sea genérica y
 * pueda ser utilizada independientemente de los datos que se vayan a enviar.
 *
 * @section retorno RETORNO
 * Devuelve 0 en caso de error o 1 en caso de exito  .
 *	
 * @section seealso VER TAMBIÉN
 * \b inicializar_nivel_SSL(3), \b conectar_canal_seguro_SSL(3), \b aceptar_canal_seguro_SSL(3),
 * \b evaluar_post_connectar_SSL(3), \b enviar_datos_SSL(3), \b  recibir_datos_SSL(3), \b cerrar_canal_SSL(3) \b
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/

int enviar_datos_SSL(SSL * ssl,const void * buf){
	return SSL_write(ssl, buf, strlen(buf)+1);
}

/**
 *
 * @brief fija el contexto de una conexion SSL
 * @page fijar_contexto_SSL \b fijar_contexto_SSL
 *
 * @section SYNOPSIS
 * 	\b #include \b "conexion.h"
 *
 *	\b int \b fijar_contexto_SSL \b (\b SSL_CTX* ctx, \b const char* CAfile, 
 * \b const char* prvKeyFile, \b const char* certFile\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *	
 * Esta función será el equivalente a la función de lectura de mensajes que se realizó en la
 * práctica 1, pero será utilizada para enviar datos a través del canal seguro. Es importante que sea genérica y
 * pueda ser utilizada independientemente de los datos que se vayan a recibir.
 *
 * @section retorno RETORNO
 * Devuelve 0 en caso de error o 1 en caso de exito  .
 *	
 * @section seealso VER TAMBIÉN
 * \b inicializar_nivel_SSL(3), \b fijar_contexto_SSL(3), \b conectar_canal_seguro_SSL(3), \b aceptar_canal_seguro_SSL(3),
 * \b evaluar_post_connectar_SSL(3), \b enviar_datos_SSL(3), \b   cerrar_canal_SSL(3) \b
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/
int recibir_datos_SSL(SSL * ssl, void * buf){
	return SSL_read(ssl, buf, 8096);
}

/**
 *
 * @brief fija el contexto de una conexion SSL
 * @page fijar_contexto_SSL \b fijar_contexto_SSL
 *
 * @section SYNOPSIS
 * 	\b #include \b "conexion.h"
 *
 *	\b int \b fijar_contexto_SSL \b (\b SSL_CTX* ctx, \b const char* CAfile, 
 * \b const char* prvKeyFile, \b const char* certFile\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *	
 * Esta función liberará todos los recursos y cerrará el canal de comunicación seguro creado
 * previamente.
 *
 * @section retorno RETORNO
 * Devuelve 0 en caso de error o 1 en caso de exito  .
 *	
 * @section seealso VER TAMBIÉN
 * \b inicializar_nivel_SSL(3), \b fijar_contexto_SSL(3), \b conectar_canal_seguro_SSL(3), \b aceptar_canal_seguro_SSL(3),
 * \b evaluar_post_connectar_SSL(3), \b enviar_datos_SSL(3), \b  recibir_datos_SSL(3) \b
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/
void cerrar_canal_SSL(SSL *ssl,SSL_CTX *ctx, int sockfd){
	int check;
	while(!(check=SSL_shutdown(ssl))){
		if(check==-1)
			return;
	}
	SSL_free(ssl);
	ssl= NULL;
	SSL_CTX_free(ctx);
	ctx=NULL;
	close(sockfd);
}

void servidor(){
	int sockfd;
	SSL_CTX* ctx;
	SSL * ssl=NULL;
	char buf[8096]="hola mundo";
	struct sockaddr_in ip4addr;

	//ssl=(SSL*)malloc(sizeof(SSL));
	ctx = inicializar_nivel_SSL(&sockfd); 
	ERR_print_errors_fp(stdout);
	fijar_contexto_SSL(ctx, "cert/root.pem", "cert/server.pem","cert/server.pem");
	ERR_print_errors_fp(stdout);
	printf("aceptar\n");
	ssl=aceptar_canal_seguro_SSL(ctx,sockfd,8080,80,ip4addr);
	ERR_print_errors_fp(stdout);
	printf("evaluar\n");
	if(!evaluar_post_connectar_SSL(ssl)){
		ERR_print_errors_fp(stdout);
		return;
	}
	printf("enviar\n");
	enviar_datos_SSL(ssl,buf);
	ERR_print_errors_fp(stdout);

	recibir_datos_SSL(ssl, buf);
	printf("recibido:[%s]\n",buf );
	cerrar_canal_SSL(ssl,ctx,sockfd);
	
}

void  cliente(){
	int sockfd;
	SSL_CTX* ctx=NULL;
	SSL * ssl=NULL;
	struct addrinfo hints, *res;
	char buf [20];
	//ssl=(SSL*)malloc(sizeof(SSL));
	memset(&hints, 0, sizeof(hints));
	hints.ai_family = AF_UNSPEC;
	hints.ai_socktype = SOCK_STREAM;
	printf("inicializar_nivel_SSL\n");
	ctx = inicializar_nivel_SSL(&sockfd);
	ERR_print_errors_fp(stdout);
	if(ctx==NULL){
		printf("CTX NULL\n");
		return;
	}
	printf("fijar_contexto_SSL\n");
	fijar_contexto_SSL(ctx, "cert/root.pem", "cert/client.pem","cert/client.pem");
	ERR_print_errors_fp(stdout);
	if(0!=getaddrinfo("localhost", "8080", &hints, &res)){
		printf("Error al obtener informacion del servidor\n");
		return;
	}
	printf("%p\n",(void*)res );
	printf("conexion\n");	
	ssl=conectar_canal_seguro_SSL(ctx,sockfd,*(res->ai_addr));
	ERR_print_errors_fp(stdout);
	printf("evaluar\n");
	if(!evaluar_post_connectar_SSL(ssl)){
		ERR_print_errors_fp(stdout);
		return;
	}
	printf("recibir\n");
	recibir_datos_SSL(ssl, buf);
	printf("recibido:[%s]\n",buf );
	enviar_datos_SSL(ssl, buf);
	ERR_print_errors_fp(stdout);
	cerrar_canal_SSL(ssl,ctx,sockfd);
	freeaddrinfo(res);
}
