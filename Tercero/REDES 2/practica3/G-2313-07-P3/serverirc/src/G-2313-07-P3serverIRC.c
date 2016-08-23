#include "../includes/G-2313-07-P3parser.h"

int nusr=0;
LinkedList * channels=NULL;
LinkedList * clients=NULL;
int semid;
SSL * ssl= NULL;
void getServerConfig(){
	FILE * f=NULL;
	char str[1024];
	char * pch;
	f=fopen("../config/config.dat", "r");
	fgets(str, 1024, f);
	pch = strtok (str,"NUM_USERS=");
	atoi(pch);
	fgets(str, 1024, f);
	pch = strtok (str,"NAME=");
	fclose(f);
}
void serverConexionIRC(){
	struct sockaddr_in ip4addr;
	int sockfd=0;
	CLIENT* client=NULL;
	pthread_t* hilos= NULL;
	pthread_t hTimeOut;
	int i=0;
	unsigned short array[2]={1,1};
	clients=create_list(compareClients);
	channels=create_list(compareChannels);
	Crear_Semaforo(12345,2,&semid);
	Inicializar_Semaforo(semid, array);
	//printf("hola\n");
	//printf("serverIRC\n");
	syslog (LOG_ERR, "empieza");
	if(channels==NULL||clients==NULL){
		//fprintf(stdout, "ERROR\n");
		return;
	}

	sockfd=abrirSocketTCP();
	if(sockfd<=0){
		syslog (LOG_ERR, "fallo en socket");
	}
	abrirBind(sockfd,6667);
	abrirListen(sockfd, nusr);

	//printf("%d\n",sockfd );
	syslog (LOG_ERR, "empieza bucle");
	while(1){
		if (hilos==NULL){
			pthread_create(&hTimeOut,NULL, timeOut, (void *)NULL );
			hilos=(pthread_t*)malloc(sizeof(pthread_t));
		}
		else
			memcpy(hilos,hilos,sizeof(hilos)+sizeof(pthread_t));
		client=createClient(aceptar(sockfd, ip4addr));
		//printf("CLIENTE:%p\n", client);
		setPthreadClient(client,&hilos[i++]);

		insert_list(clients, (void*)client);
		//printf("%d\n",client->socket );
		syslog (LOG_ERR, "nuevo cliente");
		pthread_create(&hilos[i++],NULL, parser, (void*)client);

	}

}

/*TODO revisar*/
void serverConexionIRCSSL(){
	struct sockaddr_in ip4addr;
	int sockfd=0;
	CLIENT* client=NULL;
	pthread_t* hilos= NULL;
	pthread_t hTimeOut;
	int i=0,j=0;
	SSL_CTX * ctx=NULL;
	SSL * ssl=NULL;
	char cert1[50];
	char cert2[50];
	unsigned short array[2]={1,1};
	//printf("serverIRCSSL\n");
	clients=create_list(compareClients);
	channels=create_list(compareChannels);
	Crear_Semaforo(12345,2,&semid);
	Inicializar_Semaforo(semid, array);
	//printf("hola\n");
	syslog (LOG_ERR, "empieza");
	if(channels==NULL||clients==NULL){
		//fprintf(stdout, "ERROR\n");
		return;
	}
	//LO NUEVO
	ctx = inicializar_nivel_SSL(&sockfd); 
	//ERR_print_errors_fp(LOG_ERR);
	strcpy(cert1, getenv("HOME"));
	strcpy(cert2, getenv("HOME"));
	strcat(cert1,"/.cert/root.pem" );
	syslog (LOG_ERR, cert1);
	strcat(cert2,"/.cert/server.pem" );
	syslog (LOG_ERR, cert2);
	fijar_contexto_SSL(ctx, cert1, cert2,cert2); 
	

	//printf("%d\n",sockfd );
	//printf("empieza bucle \n");
	syslog (LOG_ERR, "empieza bucle");
	while(1){
		if (hilos==NULL){
			pthread_create(&hTimeOut,NULL, timeOut, (void *)NULL );
			hilos=(pthread_t*)malloc(sizeof(pthread_t));
		}
		else
			memcpy(hilos,hilos,sizeof(hilos)+sizeof(pthread_t));
		syslog (LOG_ERR, "esperando accept");
		ssl=aceptar_canal_seguro_SSL(ctx,sockfd,6697,80,ip4addr);
		//ERR_print_errors_fp(LOG_ERR);
		//printf("evaluar\n");
		if(!evaluar_post_connectar_SSL(ssl)){
			//ERR_print_errors_fp(LOG_ERR);
			return;
		}
		client=createClient(++j);
		setSSL(client,ssl);

		//printf("CLIENTE:%p\n", client);
		setPthreadClient(client,&hilos[i++]);

		insert_list(clients, (void*)client);
		//printf("%d\n",client->socket );
		
		//printf("nuevo cliente\n");
		syslog (LOG_ERR, "nuevo cliente");
		pthread_create(&hilos[i++],NULL, parser, (void*)client);

	}

}

void main(){
	//pthread_t * irc;
	//pthread_t * ircssl;
	//demonioChat();
	//hilos
	//pthread_create(irc,NULL, serverConexionIRC(), NULL);
	//pthread_create(ircssl,NULL, serverConexionIRCSSL(), NULL);
	
	//Con procesos:	

	int irc;
	int ircssl;
	
	irc = fork();
	if(irc == -1){
		return ;
	}else if(irc == 0){
		demonioChat();
		serverConexionIRC();
	}

	ircssl = fork();
	if(ircssl == -1){
		return ;
	}else if(ircssl == 0){
		demonioChat();
		serverConexionIRCSSL();
	}
	
	


	/*CHANNEL* ch;
	CHANNELUSER* chu;
	CLIENT* cl;
	cl=createClient(1);
	setNickClient(cl,"ccpp");
	chu=createChannelUser(cl);
	ch=createChannel("#a");
	//insert_list(ch->users,chu);
	addUserCh(ch,cl);
	printChannel(ch);*/
}
