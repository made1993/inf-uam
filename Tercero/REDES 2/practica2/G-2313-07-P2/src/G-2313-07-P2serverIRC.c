#include "../includes/G-2313-07-P2parser.h"

int nusr=0;
LinkedList * channels=NULL;
LinkedList * clients=NULL;
int semid;
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
void main(){
	demonioChat();
	
	
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
