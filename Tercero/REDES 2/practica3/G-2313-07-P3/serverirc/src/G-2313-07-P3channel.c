#include "../includes/G-2313-07-P3channel.h"

CHANNEL* createChannel(char * name){
	CHANNEL* ch= NULL;
	ch=(CHANNEL*)malloc(sizeof(CHANNEL));
	if(ch==NULL)
		return NULL;
	ch->name=NULL;
	setNameCh(ch,name);
	ch->users=NULL;
	ch->bannedUsers=NULL;
	ch->invitedUsers=NULL;
	ch->private=false;
	ch->invite=false;
	ch->voice=false;
	ch->hiden=false;
	ch->topicProtect=false;
	return ch;

}
void setNameCh( CHANNEL* channel,char* name){
	free(channel->name);
	channel->name= NULL;
	channel->name=(char*)malloc(strlen(name)+2);
	strcpy(channel->name, name);
}
void setPrivateCh(CHANNEL* channel,bool private){
	channel->private = private;
}

void setInviteCh(CHANNEL* channel,bool invite){
	channel->invite = invite;
}

void setHidenCh( CHANNEL* channel,bool hiden){
	channel->hiden = hiden;
}

void setTopciProtectCh( CHANNEL* channel,bool topicProtect){
	channel->topicProtect = topicProtect;
}

void setVoiceCh( CHANNEL* channel,bool voice){
	channel->voice = voice;
}

char* getNameCh( CHANNEL* channel){
	return channel->name;
}

bool getPrivateCh( CHANNEL* channel){
	return channel->private;
}

bool getInviteCh( CHANNEL* channel){
	return channel->invite;
}

bool gerhidenCh( CHANNEL* channel){
	return channel->hiden;
}

bool getTopciProtectCh( CHANNEL* channel){
	return channel->topicProtect;
}

bool getVoiceCh( CHANNEL* channel){
	return channel->voice;
}

char*getModeCh(CHANNEL* channel){
	char *str=NULL;
	int i=1;
	str=(char*)malloc(5);
	if(channel->invite)
		str[i++]='i';
	if(channel->voice)
		str[i++]='v';
	if(channel->hiden)
		str[i++]='s';
	if(channel->topicProtect)
		str[i++]='t';
	str[0]='+';
	
	return str;

}
int compareChannels( const void* c1, const void* c2){
	CHANNEL* ch=(CHANNEL*) c2;
	char* name=(char*)c1;
	if (strcmp(ch->name,name)==0)
		return 1;
	return 0;
}


bool addUserCh( CHANNEL* channel,  CLIENT * cl){
	CHANNELUSER *chuser;
	char command[100];
	chuser= createChannelUser(cl);
	if(channel==NULL || cl==NULL){
		return false;
	}
	if(channel->users==NULL){
		chuser->op=true;
		channel->users=create_list(compareChUsers);
		channel->bannedUsers=create_list(compareChUsers);
		channel->invitedUsers=create_list(compareChUsers);
		insert_list(channel->users,(void*) chuser);
		return true;
	}
	else if(find((void*) chuser, channel->users)!= NULL){
		////printf("ERROR usuario ya registrado\n");
		return false;
	}
	else{
		if(getInviteCh(channel)){
			if(find((void*) chuser, channel->invitedUsers)== NULL){
				IRC_ErrInviteOnlyChan(command, NULL, getNickClient(cl), getNameCh(channel));
				escribir(getSocketClient(cl), command);
				return false;
			}
			insert_list(channel->users,(void*) chuser);
			return true;
		}
		else if(find((void*) chuser, channel->bannedUsers)!= NULL){
			IRC_ErrBannedFromChan(command, NULL, getNickClient(cl), getNameCh(channel));
			escribir(getSocketClient(cl), command);
			return false;
		}
		else{
			if(find((void*) chuser, channel->bannedUsers)== NULL){
				if(getPrivateCh(channel)){
					if(find((void*) chuser, channel->invitedUsers)== NULL){
						IRC_ErrInviteOnlyChan(command, NULL, getNickClient(cl), getNameCh(channel));
						escribir(getSocketClient(cl), command);
						return false;
					}
				}
				insert_list(channel->users,(void*) chuser);
				return true;
			}
		}

	}
	return false;
}

bool rmUserCh( CHANNEL* channel, CHANNELUSER* chuser){
	char* command;
	if(channel==NULL||chuser==NULL){
		return false;
	}
	if(channel->users==NULL){
		return false;
	}
	if(NULL==find((void*) chuser, channel->users)){
		IRC_ErrNotOnChannel(command, getIdClient(chuser->client), getNickClient(chuser->client), NULL, getNameCh(channel));
		escribir(getSocketClient(chuser->client), command);
		////printf ("se manda %s\n",command);
		return false;
	}
	delete_elem_list(channel->users, (void*)chuser);
	if(is_empty_list(channel->users))
		delete_elem_list(channels, getNameCh(channel));
	/*TODO mandar mensaje?? depende de como se llame a esta funcion*/
	return true;

}

bool addBannedUserCh( CHANNEL* channel,  CHANNELUSER * chuser){
	if(channel==NULL || chuser==NULL){
		return false;
	}
	if(channel->users==NULL){
		channel->users=create_list(compareChUsers);
		channel->bannedUsers=create_list(compareChUsers);
		channel->invitedUsers=create_list(compareChUsers);
		insert_list(channel->bannedUsers, chuser);
		/*TODO mandar un mensaje broadcat en el canal diciendo que un 
		usuario se ha unido*/
		return true;
	}
	else{
		if(find(chuser, channel->bannedUsers)!= NULL){
			return false;
		}
		else{
			if(find(chuser, channel->bannedUsers)== NULL){
				insert_list(channel->users, chuser);
				/*TODO mandar un mensaje broadcat en el canal diciendo que un 
				usuario se ha unido*/
				return true;
			}
		}

	}
	return false;
}

bool rmBannedUserCh( CHANNEL* channel, CHANNELUSER* chuser){
	if(channel==NULL||chuser==NULL){
		return false;
	}
	if(channel->bannedUsers==NULL){
		return false;
	}
	if(NULL==find((void*) chuser, channel->bannedUsers)){
		return false;
	}
	delete_elem_list(channel->bannedUsers, (void*)chuser);
	return true;

}
bool addInvitedUserCh( CHANNEL* channel,  CHANNELUSER * chuser){
	if(channel==NULL || chuser==NULL){
		return false;
	}
	if(channel->invitedUsers==NULL){
		channel->users=create_list(compareChUsers);
		channel->bannedUsers=create_list(compareChUsers);
		channel->invitedUsers=create_list(compareChUsers);
		insert_list(channel->invitedUsers, chuser);
		/*TODO mandar un invite*/
		return true;
	}
	else{
		if(find(chuser, channel->invitedUsers)!= NULL){
			return false;
		}
		else{
			if(find(chuser, channel->invitedUsers)== NULL){
				insert_list(channel->invitedUsers, chuser);
				/*TODO mandar un invite*/
				return true;
			}
		}

	}
	return false;
}

bool rmInvitedUserCh( CHANNEL* channel, CHANNELUSER* chuser){
	if(channel==NULL||chuser==NULL){
		return false;
	}
	if(channel->invitedUsers==NULL){
		return false;
	}
	if(NULL==find((void*) chuser, channel->invitedUsers)){
		return false;
	}
	delete_elem_list(channel->invitedUsers, (void*)chuser);
	return true;

}

/*void sendMessage( CHANNEL* chanel,  CHANNELUSER * chuser,char* msg){
	Node *node=NULL;
	if(chanel==NULL||chuser==NULL ||msg ==NULL){
		return;
	}
	if(chanel->voice && (!(chuser->voice) || !(chuser->op) ) ) {
		return;
	}
	if(chanel->users==NULL){
		return;
	}
	for(node=chanel->users->first;node->next!=NULL;node=(Node*)node->next){
		
	}
}*/


/*bool sendMessage(char* namech, CLIENT* usr, char* msg){

	CHANNEL * ch = NULL;
	Node * node = NULL;

	if(namech == NULL || usr == NULL || msg == NULL){ 
		return false;
	}
	if(!ch->voice || ch->users==NULL) {
		return false;
	}

	ch = (CHANNEL*) find((void*)namech, channels);

	if(ch == NULL){	
		return false;
	}
	
	node = ch->users->first;

	while(node != NULL){ //recorre todos los channeluser y manda el mensaje excepto al emisor
		if(compareClients((void*)node->client, (void*) usr) == 1){ //comparando con el client mandado
			continue;
		}
		
		//TODO escribir el mensaje y mandarlo
		
		node = node->next;
	}
	return true;
}*/

	void rplUsers(CHANNEL * ch, CLIENT * cl){
		char command[8096];
		Node *nodoaux = NULL;
		CHANNELUSER * auxchu=NULL;
		if(ch==NULL){
			////printf("es un canala invalido\n");
			IRC_RplEndOfWho(command, "Durruti", NULL, NULL);
			escribir(getSocketClient(cl),command);
			////printf("se manda:%s\n",command);
			return;
		}
		if(ch->users==NULL){
			////printf("es un canala invalido\n");
			IRC_RplEndOfWho(command, "Durruti", NULL, NULL);
			escribir(getSocketClient(cl),command);
			////printf("se manda:%s\n",command);
			return;
		}
		////printf("no es un canala invalido\n");

		rplNamReply(ch, cl);
	    for (nodoaux = ch->users->first; nodoaux != NULL; nodoaux = nodoaux->next) {
	        auxchu= (CHANNELUSER*)nodoaux->data;

	        IRC_RplWhoReply(command, "Durruti", getNickClient(cl), 
	        	getNameCh(ch), getUserNameClient(auxchu->client), getIpClient(auxchu->client),
	        	 "Durruti", getNickClient(auxchu->client), "H@", 0, getRealNameClient(auxchu->client));
	    	escribir(getSocketClient(cl),command);
	    	////printf("se manda:%s\n",command );
	    }
	    IRC_RplEndOfWho(command, "Durruti", getNickClient(cl), getUserNameClient(cl));
		escribir(getSocketClient(cl),command);
		////printf("se manda:%s\n",command);
						
	}



void rplNamReply(CHANNEL * ch,CLIENT* client ){
	Node *nodoaux = NULL;
	CHANNELUSER * chu= NULL;
	char command[8096];
	char type[11];
	if(ch==NULL)
		return;
	for (nodoaux = ch->users->first; nodoaux != NULL; nodoaux = nodoaux->next) {
        chu=(CHANNELUSER*)nodoaux->data;
        sprintf(type, "%s%s",getTypeChUsr(chu),getNickClient(chu->client));
        IRC_RplNamReply(command, "Durruti", getNickClient(client), "=", getNameCh(ch),type);
        escribir(getSocketClient(client),command);
        ////printf("se manda: %s\n",command );
    }
    IRC_RplEndOfNames(command,  "Durruti", getNickClient(client), getNameCh(ch));
    escribir(getSocketClient(client),command);
    ////printf("se manda: %s\n",command );
}


bool writeMsgCh(CHANNEL* ch, CLIENT* client, char* msg){
	CHANNELUSER* auxchu=NULL;
	CHANNELUSER auxchu2;

	Node* nodoaux;
	CLIENT* auxclient=NULL;
	if(ch==NULL || client==NULL|| msg==NULL){
		return false;
	}
	auxchu2.client=client;
	auxchu=find(&auxchu2,ch->users);
	if(auxchu!=NULL){
		if((getVoiceCh(ch)&& getVoiceChUsr(auxchu))||(!getVoiceCh(ch))){
			for (nodoaux = ch->users->first; nodoaux != NULL; nodoaux = nodoaux->next) {
		      	auxchu=(CHANNELUSER*)nodoaux->data;
		      	if(!compareClients(auxchu->client, client)==1)
		       		escribir(getSocketClient(auxchu->client), msg);
			}
		}
	}
}
void printChannel(CHANNEL* channel){
	Node *nodoaux = NULL;
	CHANNELUSER * chu =NULL;
	////printf("---------------------------------------\n");
	////printf("name:%s\n", channel->name);
	////printf("private:%d\n", channel->private);
	////printf("invite:%d\n", channel->invite);
	////printf("voice:%d\n", channel->voice);
	////printf("hiden:%d\n", channel->hiden);
	////printf("topicProtect:%d\n", channel->topicProtect);
	for (nodoaux = channel->users->first; nodoaux != NULL; nodoaux = nodoaux->next) {
        chu=(CHANNELUSER*)nodoaux->data;
        printChUser(chu);
    }
   	////printf("---------------------------------------\n");
}

void printChannels(){
	Node *nodoaux = NULL;
	CHANNEL * ch;
	////printf("---------------------------------------\n");
    for (nodoaux = channels->first; nodoaux != NULL; nodoaux = nodoaux->next) {
        ch=(CHANNEL*)nodoaux->data;
        printChannel(ch);
    }
	////printf("---------------------------------------\n");
    return ;
}



