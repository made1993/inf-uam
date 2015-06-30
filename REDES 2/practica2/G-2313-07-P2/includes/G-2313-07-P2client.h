#ifndef CLIENT_H
#include "G-2313-07-P2types.h"

CLIENT* createClient(int socket);
void setNickClient(CLIENT* client,char* nick);
void setRealNameClient(CLIENT* client,char* realName);
void setUserNameClient(CLIENT* client,char* name);
void setIpClient(CLIENT* client,char* name);
void setSocketClient(CLIENT* client,int socket);
void setIdClient(CLIENT* client);
void setPthreadClient(CLIENT* client, pthread_t * hilo);
void setLastConnectionClient(CLIENT* client);

char* getNickClient(CLIENT* client);
char* getRealNameClient(CLIENT* client);
char* getUserNameClient(CLIENT* client);
char* getIpClient(CLIENT* client);
int getSocketClient(CLIENT* client);
char* getIdClient(CLIENT* client);
struct tm* getLastConnectionClient(CLIENT* client);
pthread_t* getPthreadClient(CLIENT* client);
int compareClients(const void * c1,const void * c2);
void printClient(CLIENT * cl);






#endif
