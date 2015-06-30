#include "../includes/G-2313-07-P3client.h"

CLIENT* createClient(int socket){
	CLIENT* c=NULL;
	time_t now;
        struct tm* prueba=NULL;

	c=(CLIENT*)malloc(sizeof(CLIENT)+1);
	if (c==NULL){
		return NULL;
	}
	c->socket = socket;
	c->registeredConnection = false;
	now = time(0);
	c->lastConnection=malloc(sizeof(struct tm));
    setLastConnectionClient(c);
    c->ssl = NULL;
	return c;
}

void setNickClient(CLIENT* client,char* nick){
	if(strlen(nick)>9)
		return;
	strcpy(client->nick, nick);
}

void setRealNameClient(CLIENT* client,char* realName){
	if(strlen(realName)>20)
		return;
	strcpy(client->realName, realName);
}

void setUserNameClient(CLIENT* client,char* name){
	//free(client->userName);
	client->userName=NULL;
	client->userName=(char*)malloc(strlen(name)+3);
	strcpy(client->userName, name);
}
void setIpClient(CLIENT* client,char* ip){
	//free(client->ip);
	client->ip=NULL;
	client->ip=(char*)malloc(strlen(ip)+3);
	strcpy(client->ip, ip);

}
void setSocketClient(CLIENT* client,int socket){
	client->socket = socket;
}

void setIdClient(CLIENT* client){
	//free(client->id);
	client->id=NULL;
	client->id = (char*) malloc(strlen(client->nick) + 4+strlen(client->userName)+strlen(client->ip));
	sprintf(client->id,"%s!~%s@%s",client->nick,client->userName,client->ip);
}

char* getNickClient(CLIENT* client){
	return client->nick;
}

char* getRealNameClient(CLIENT* client){
	return client->realName;
}

char* getUserNameClient(CLIENT* client){
	return client->userName;
}
char* getIpClient(CLIENT* client){
	return client->ip;
}
int getSocketClient(CLIENT* client){
	if(client==NULL)
		return 0;
	return client->socket;
}

char* getIdClient(CLIENT* client){
	return client->id;
}

int compareClients(const void * c1,const void * c2){
	CLIENT* cl1=(CLIENT*) c1,* cl2=(CLIENT*) c2;
	if(strcmp(cl1->nick,cl2->nick)==0)
		return 1;
	return 0;
}


void printClient(CLIENT * cl){
	//printf("nick:%s\n",cl->nick);
	//printf("realName:%s\n",cl->realName);
	//printf("userName:%s\n",cl->userName);
	//printf("ip:%s\n",cl->ip);
	//printf("id:%s\n",cl->id);
	//printf("socket:%d\n",cl->socket);
	//printf("registeredConnection:%d\n",cl->registeredConnection);
}


pthread_t* getPthreadClient(CLIENT* client){
	return client->hilo;
}


void setPthreadClient(CLIENT* client, pthread_t * hilo){
	client->hilo = hilo;
}


void setLastConnectionClient(CLIENT* client){
	time_t now;
	struct tm* prueba=NULL;
	now = time(0);
	memcpy(client->lastConnection, localtime(&now), sizeof(localtime(&now)));
    prueba = localtime(&now);
    client->lastConnection->tm_min = prueba->tm_min;
    client->lastConnection->tm_sec = prueba->tm_min;
}

struct tm* getLastConnectionClient(CLIENT* client){
	return client->lastConnection;
}


SSL * getSSL(CLIENT * client){
	return client->ssl;
}

void setSSL(CLIENT * client, SSL * ssl){
	if(client->ssl==NULL)
		client->ssl=malloc(sizeof(SSL));
	client->ssl = ssl;
}


CLIENT * WhoseIsThisSocket(int socket){
	Node *nodoaux = NULL;
	CLIENT* auxclient=NULL;
	if(clients==NULL)
		return NULL;
	for (nodoaux = clients->first; nodoaux != NULL; nodoaux = nodoaux->next) {
        auxclient=(CLIENT*)nodoaux->data;
    	if(getSocketClient(auxclient)==socket)
			return auxclient;
 	}		
	return NULL;
}
