#include "../includes/G-2313-07-P3channeluser.h"

CHANNELUSER* createChannelUser(CLIENT *client){
	CHANNELUSER* chu;
	chu = (CHANNELUSER*) malloc(sizeof(CHANNELUSER));
	chu->client = client;
	chu->op = false;
	chu->voice = false;
	return chu;
}

void setNickChUsr(CHANNELUSER* chUsr,char* nick){
	setNickClient(chUsr->client, nick);
}

void setOPChUsr(CHANNELUSER* chUsr,bool op){
	chUsr->op = op;
}

void setVoiceChUsr(CHANNELUSER* chUsr,bool voice){
	chUsr->voice = voice;
}

char * getNickChUsr(CHANNELUSER* chUsr){
	return getNickClient(chUsr->client);
}

char * getTypeChUsr(CHANNELUSER* chUsr){
	if(chUsr->op && chUsr->voice)
		return "@+";
	else if(chUsr->op)
		return "@";
	else if(chUsr->voice)
		return "+";
	return "";
}
bool getOpChUsr(CHANNELUSER* chUsr){
	return chUsr->op;
}

bool getVoiceChUsr(CHANNELUSER* chUsr){
	return chUsr->voice;
}

int compareChUsers(const void* chUsr1,const void* chUsr2){
	CHANNELUSER* chusr1=(CHANNELUSER*)chUsr1 ,* chusr2=(CHANNELUSER*)chUsr2;
	return compareClients((void*)chusr1->client, (void*)chusr2->client);
}

void printChUser(CHANNELUSER* chUsr){
	printClient(chUsr->client);
	//printf("op:%d\n",chUsr->op);
	//printf("voice:%d\n",chUsr->voice);
}
