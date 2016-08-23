#ifndef CHANNEL_H
#include "G-2313-07-P2channeluser.h"

CHANNEL* createChannel(char * name);

void setNameCh( CHANNEL* channel,char* name);
void setPrivateCh( CHANNEL* channel,bool private);
void setInviteCh( CHANNEL* channel,bool invite);
void setHidenCh( CHANNEL* channel,bool hiden);
void setTopciProtectCh( CHANNEL* channel,bool topicProtect);
void setVoiceCh( CHANNEL* channel,bool voice);

char* getNameCh( CHANNEL* channel);
bool getPrivateCh( CHANNEL* channel);
bool getInviteCh( CHANNEL* channel);
bool gerhidenCh( CHANNEL* channel);
bool getTopciProtectCh( CHANNEL* channel);
bool getVoiceCh( CHANNEL* channel);
char*getModeCh(CHANNEL* channel);

bool isUserCh( CHANNEL* channel, char * name);

bool addUserCh( CHANNEL* channel,  CLIENT * cl);
bool rmUserCh( CHANNEL* channel, CHANNELUSER * chuser);

bool addBannedUserCh( CHANNEL* channel,  CHANNELUSER * chuser);
bool rmBannedUserCh( CHANNEL* channel, CHANNELUSER * chuser);

bool addInvitedUserCh( CHANNEL* channel,  CHANNELUSER * chuser);
bool rmInvitedUserCh( CHANNEL* channel, CHANNELUSER * chuser);

int compareChannels( const void* c1, const void* c2);

void rplUsers(CHANNEL * ch, CLIENT * cl);
void printChannel(CHANNEL* channel);

void rplNamReply(CHANNEL * ch, CLIENT* client);
bool writeMsgCh(CHANNEL* ch, CLIENT* client, char* msg);
void printChannels();

//bool sendMessage(char* namech, CLIENT* usr, char* msg);

/*
*	Hace broadcast de un mensaje de un usuario al resto de los usuarios del canal.
*	
*/
//bool sendMessage(CHANNEL * ch,  CHANNELUSER * chuser, char* msg);
#endif