#ifndef CHANNELUSER_H
#include "G-2313-07-P3client.h"

CHANNELUSER* createChannelUser(CLIENT *client);
void setNickChUsr(CHANNELUSER* chUsr,char* nick);
void setOPChUsr(CHANNELUSER* chUsr,bool op);
void setVoiceChUsr(CHANNELUSER* chUsr,bool voice);

char * getNickChUsr(CHANNELUSER* chUsr);
bool getOpChUsr(CHANNELUSER* chUsr);
bool getVoiceChUsr(CHANNELUSER* chUsr);
char * getTypeChUsr(CHANNELUSER* chUsr);
int compareChUsers(const void* chUsr1,const void* chUsr2);

void printChUser(CHANNELUSER* chUsr);
#endif
