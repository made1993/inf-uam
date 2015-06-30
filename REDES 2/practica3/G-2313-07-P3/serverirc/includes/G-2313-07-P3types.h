#ifndef TYPES_H
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <syslog.h>
#include <unistd.h>
#include <string.h>
#include "G-2313-07-P3linkedList.h"
#include <netinet/in.h>
#include <redes2/chat.h>
#include <redes2/irc.h>
#include <time.h>
#include "G-2313-07-P3sslconexion.h"
#include "G-2313-07-P3semaforos.h"

#define REAL 20
#define CLIENTES 0
#define CANALES 1
extern LinkedList* channels;
extern LinkedList* clients;
extern int semid;

typedef struct{
	char nick[9];
	char realName[REAL];
	char* userName;
	char* ip;
	char* id;
	int socket;
	bool registeredConnection;
	struct tm* lastConnection;
	pthread_t * hilo;
	SSL * ssl;
}CLIENT;

typedef struct{
	CLIENT * client;
	bool op;
	bool voice;
} CHANNELUSER;

typedef struct{
	char* name;
	LinkedList * users;
	LinkedList * bannedUsers;
	LinkedList * invitedUsers;
	bool private;
	bool invite;
	bool voice;
	bool hiden;
	bool topicProtect;
}CHANNEL;

void demonioChat();
#endif
