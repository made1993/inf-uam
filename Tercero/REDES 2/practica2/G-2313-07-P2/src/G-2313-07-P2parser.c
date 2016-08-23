#include "../includes/G-2313-07-P2parser.h"

void broadcastMsg(char* msg, char* channel, CLIENT * client){
	CHANNEL* auxchannel=NULL;
	Node *nodoaux = NULL;
	CHANNELUSER* auxchannelusr=NULL;
	CHANNELUSER auxchannelusr2;
	CLIENT* auxclient= NULL;
	if(msg==NULL)
		return;
	if(channel==NULL){
		for (nodoaux = clients->first; nodoaux != NULL; nodoaux = nodoaux->next) {
	       auxclient=(CLIENT*)nodoaux->data;
	       escribir(getSocketClient(auxclient), msg);
		}

	}
	else{
		auxchannel=find(channel,channels);

		if (auxchannel!=NULL){
			syslog (LOG_ERR, "1");
			auxchannelusr2.client=client;
			syslog (LOG_ERR, "2");
			auxchannelusr= find((void*)&auxchannelusr2,auxchannel->users);
			syslog (LOG_ERR, "3");
			if(auxchannelusr!=NULL){
				syslog (LOG_ERR, "4");
				if(getVoiceCh(auxchannel)&&(getOpChUsr(auxchannelusr)||getVoiceChUsr(auxchannelusr))){
					syslog (LOG_ERR, "5");
					for (nodoaux = auxchannel->users->first; nodoaux != NULL; nodoaux = nodoaux->next) {
				        auxchannelusr=(CHANNELUSER*)nodoaux->data;
				        escribir(getSocketClient(auxchannelusr->client), msg);
		   		 	}
	   		 	}
	   		}
		}
	}

}

int modulo60(struct tm* t1, struct tm* t2){
	int auxt1=t1->tm_sec;
	int auxt2=t2->tm_sec;
	
	if(t1->tm_min > t2->tm_min){
		auxt1+=60;
	}
	/*
	//printf("modulo60\n");
	//printf("t1:%p\n",t1 );
	//printf("t2:%p\n",t2 );
	//printf("t1min:%d t1sec:%d\n",t1->tm_min,t1->tm_sec );
	//printf("t2min:%d t2sec:%d\n",t2->tm_min,t2->tm_sec );
	//printf("auxt1:%d\n",auxt1 );
	//printf("auxt2:%d\n",auxt2);
	*/
	if(auxt1-auxt2 > 40){
		return 2;
	}
	else if(auxt1-auxt2 > 20){
		return 1;
	}
	return 0;

}
void borrarCliente(CLIENT* cl){
	CHANNEL* auxchannel= NULL;
	CHANNELUSER auxchannelusr; 
	Node* nodoaux = NULL;
	auxchannelusr.client=cl;
	for (nodoaux = channels->first; nodoaux != NULL; nodoaux = nodoaux->next){
		auxchannel=(CHANNEL*)nodoaux->data;
		delete_elem_list(auxchannel->users, (void*)&auxchannelusr);
		delete_elem_list(auxchannel->bannedUsers, (void*)&auxchannelusr);
		delete_elem_list(auxchannel->invitedUsers, (void*)&auxchannelusr);
	}
	delete_elem_list(clients,(void*) getNickClient(cl));
}
void* timeOut(void* args){
	time_t now;
	struct tm* tm=NULL;
	Node* nodoaux = NULL;
	CLIENT* auxclient=NULL;
	char msg[100];
	char command[20];
	while(1){
	
		syslog (LOG_ERR, "TIMEOUT");
		//printf("TIMEOUT\n");
		nodoaux=NULL;
		//printf("%p\n", clients);
		//printf("%p\n",clients->first);
		for (nodoaux = clients->first; nodoaux != NULL; nodoaux = nodoaux->next) {
		   	//printf("%p\n",clients->first->data);
	       	auxclient=(CLIENT*)nodoaux->data;
	       	now=time(0);
	       	tm=localtime(&now);
	       	switch(modulo60(tm, getLastConnectionClient(auxclient))){
		       	case 1:
		       		//printf("caso1\n");
		       		IRC_Ping(command, NULL, "Durruti", NULL);
		       		escribir(getSocketClient(auxclient), command);
		       		//printf("se manda:%s\n",command );
		       	break;
		       	case 2:
		       		//printf("caso2\n");
		       		//printf("%s\n",getNickClient(auxclient));
		       		IRC_Quit(msg, getIdClient(auxclient), "timeout");
		       		//printf("caso2\n");
					broadcastMsg(msg,NULL, NULL);
					syslog (LOG_ERR, "usuario expulsado");
		       		borrarCliente(auxclient);
					pthread_cancel(*getPthreadClient(auxclient));
		       	break;
	       	}
		}
		//printf("TIMEOUT\n");
		sleep(20);
	}
}
bool findNick(){

}


void* parser(void* args){	
	CLIENT* client=(CLIENT*) args;
	CLIENT auxclient;
	CLIENT* auxclient2;
	CHANNEL * auxchannel=NULL;
	CHANNELUSER* auxchannelusr=NULL;
	CHANNELUSER auxchannelusr2;
	time_t now;
	char buf[8096]="",* aux11=NULL;
	long lcommand;
	bool bnick=false;
	char* strnick=NULL,* prefix=NULL,* user=NULL,* mode=NULL,* realname=NULL,command[8096],
	* nick=NULL,* nick1=NULL,* nick2=NULL,* host=NULL,* other=NULL,* channel=NULL,* key=NULL,* msg=NULL,
	* mask= NULL,* oppar=NULL,* target=NULL,* channeluser=NULL,* auxstr=NULL,* msgtarget=NULL;
	int pos=0;
	int pipe= 0;
	goto  c1;
	c1:

	while(1){
	
		//printf("lectura\n");
		lcommand =recibir(getSocketClient(client),buf);
		//printf("lectura\n");		
		
		syslog (LOG_ERR, "nuevo mensaje");
		unpipe:

		IRC_UnPipelineCommands(&buf[pos], &aux11);
		if(aux11==NULL){
			pipe=0;	
			pos=0;
			aux11=buf;			
		}
		else{
			pipe=1;
			pos+=strlen(aux11);
			
		}
		if(pos==0){
			continue;
		}
		lcommand=IRC_CommandQuery(aux11);
		////printf("aux11 %d\t[%s]\n\n", pos, aux11);
		switch(lcommand){
			case PASS:
			break;
			case NICK:
				syslog (LOG_ERR, "NICK");
				//printf("NICK\n");
				IRCParse_Nick(aux11, &prefix, &strnick);
				////printf("%s\n",strnick );
				setNickClient(&auxclient,strnick);
				if(find((void*)&auxclient, clients)!=NULL){
					//printf("el nick ya existe\n");
					IRC_ErrNickNameInUse(command, strnick, strnick, "NICK NAME IN USE");
					escribir(client->socket, command);
					break;
				}
				//printf("nick correcto\n");
				setNickClient(client,strnick);
				if(bnick){
					//printf("getIdClient:%s nick:%s\n", getIdClient(client),nick );
					IRC_Nick(command, getIdClient(client), strnick);
					printClient(client);
					broadcastMsg(command, NULL,NULL);
					setIdClient(client);
					//printf("se manda: %s\n",command );
				}
				bnick= true;

			break;
			case USER:
				syslog (LOG_ERR, "USER");
				//printf("USER\n");
				if(bnick){
					 if(-1<=IRCParse_User(aux11, &prefix, &user, &mode, &realname)){
					 	
					}
					else{
						IRCParse_User1459(aux11, &prefix,&user, &other, &host, &realname);
					}
					//printf("prefix:%s\n", prefix);
					//printf("user:%s\n", user);
					setUserNameClient(client,user);
					//printf("other:%s\n", other);
					//printf("host:%s\n", host);
					setIpClient(client,host);
					//printf("realname:%s\n", realname);
					setRealNameClient(client,realname);
					setIdClient(client);
					//printf("USER:%s client:%s\n",user,client->nick);
					//printf("IRC_RplWelcome\n");
			 		IRC_RplWelcome(command, getIdClient(client), client->nick, client->nick, user, getIpClient(client));
			 		escribir(client->socket, command);
			 		//printf("se manda:%s",command );
					//printf("IRC_RplYourHost\n");
			 		IRC_RplYourHost(command, getIdClient(client), client->nick,getIpClient(client), "0.0");
			 		escribir(client->socket, command);
			 		//printf("se manda:%s",command );
					//printf("IRC_RplCreated\n");
			 		IRC_RplCreated(command, getIdClient(client), client->nick);
			 		escribir(client->socket, command);
			 		//printf("se manda:%s",command );
			 		//printf("IRC_RplMyInfo\n");
			 		IRC_RplMyInfo(command, getIdClient(client), client->nick,"Durruti", 
			 			"0.0", "", "");
			 		escribir(client->socket, command);
			 		//printf("se manda:%s",command );
				}
				else{
					//printf("USER error\n");
					/*TODO escribir mensaje de error*/
				}
			break;

			case JOIN:
				//printf("JOIN\n");
				syslog (LOG_ERR, "JOIN");
				if(bnick){
					IRCParse_Join(aux11, &prefix, &channel, &key, &msg);
					//printf("prefix:%s\n", prefix);
					//printf("channel:%s\n", channel);
					//printf("key:%s\n", key);
					//printf("msg:%s\n", msg);
					auxchannel=find(channel,channels);
					if(auxchannel==NULL){
					 	auxchannel=createChannel(channel);
					 	if(auxchannel==NULL){
					 		/*TODO mandar mensaje brodcast*/
					 		//printf("ERROR al crear el canal %s\n",channel );
					 		break;
					 	}
						insert_list(channels, auxchannel);
					}
					if(addUserCh(auxchannel,client)){
						IRC_Join(command,	 getIdClient(client), channel, NULL, NULL);
						//printf("socket:%d\n",getSocketClient(client) );
						broadcastMsg(command, NULL,NULL);
						//printf("se envia:%s\n",command);
						//printf("Se añade el usuario al canal %s\n",channel );
					}
					else{

					}

				}
			break;
			
			case PART:
				syslog (LOG_ERR, "PART");
				if(bnick){
					channel=NULL;
					auxchannel= NULL;
					IRCParse_Part(aux11, &prefix, &channel, &msg);
					if(channel!=NULL){
						auxchannel=find(channel,channels);
						if(auxchannel!=NULL){
							auxchannelusr2.client=client;
							auxchannelusr=find(&auxchannelusr2,auxchannel->users);
							Down_Semaforo(semid,CLIENTES,SEM_UNDO);
							Down_Semaforo(semid,CANALES,SEM_UNDO);
							IRC_Part(command,getIdClient(client) , channel, msg);
							broadcastMsg(command, NULL, NULL);
							rmUserCh( auxchannel, auxchannelusr);
							Up_Semaforo(semid,CLIENTES,SEM_UNDO);
							Up_Semaforo(semid,CANALES,SEM_UNDO);
						}
						else{
							IRC_ErrNoSuchChannel(command, getIdClient(client), getNickClient(client), channel);
							escribir(getSocketClient(client), command);
							//printf ("se manda %s\n",command);
						}
					}
				}
			break;

			case PING:
				//printf("PING\n");
				syslog (LOG_ERR, "PING");
				if(bnick){
					Down_Semaforo(semid,CLIENTES,SEM_UNDO);
					setLastConnectionClient( client);
					IRC_Pong(command, "Durruti", "1", "2");
		       		escribir(getSocketClient(client), command);
					Up_Semaforo(semid,CLIENTES,SEM_UNDO);
				}
			break;

			case PONG:
				//printf("PONG\n");
				syslog (LOG_ERR, "PONG");
				if(bnick){
					Down_Semaforo(semid,CLIENTES,SEM_UNDO);
					setLastConnectionClient( client);
					Up_Semaforo(semid,CLIENTES,SEM_UNDO);
				}

			break;
			case PRIVMSG:
				//printf("PRIVMSG\n");
				syslog (LOG_ERR, "PRIVMSG");
				if(bnick){
					msgtarget=NULL;
					IRCParse_Privmsg(aux11, &prefix, &msgtarget, &msg);
					if(msgtarget[0]=='#'){
						Down_Semaforo(semid,CANALES,SEM_UNDO);
						auxchannel=find(msgtarget,channels);
						if(auxchannel!=NULL){
							IRC_Privmsg(command, getIdClient(client), msgtarget, msg);
							writeMsgCh(auxchannel,client,command);
						}
						Up_Semaforo(semid,CANALES,SEM_UNDO);
					}
					else if (msgtarget!=NULL){
						auxclient2=NULL;
						Down_Semaforo(semid,CLIENTES,SEM_UNDO);
						auxclient2=find(msgtarget,clients);
						if(auxclient2!=NULL){
							IRC_Privmsg(command, getIdClient(auxclient2), msgtarget, msg);
							escribir(getSocketClient(auxclient2), command);
						}
						Up_Semaforo(semid,CLIENTES,SEM_UNDO);
					}
					 
				}
			break;
			
			case QUIT:
				//printf("QUIT\n");
				syslog (LOG_ERR, "QUIT");
				if(bnick){
					msg=NULL;
					IRCParse_Quit(aux11, &prefix, &msg);
					IRC_Quit(command, getIdClient(client), msg);
					broadcastMsg(command,NULL,NULL);
					//printf("se envia broadcast\n");
					borrarCliente(client);
					close(getSocketClient(client));
					pthread_exit(NULL);
					
				}
			break;
			case ERROR:
				//printf("ERROR\n"); 
				syslog (LOG_ERR, "ERROR");
				close(getSocketClient(client));
				pthread_exit(NULL);
				return;
			break;
			case LIST:
				//printf("LIST\n");
				syslog (LOG_ERR, "LIST");
				if(bnick){
					channel=NULL;
					IRCParse_List(aux11, &prefix, &channel, &target);
					if (channel==NULL){

					}
					else{

					}
				}
			break;
			case MODE:
				//printf("MODE\n");
				syslog (LOG_ERR, "MODE");
				if(bnick){
					auxchannel=NULL;
					auxchannelusr=NULL;
					mode=NULL;
					user=NULL;
					IRCParse_Mode(aux11, &prefix,&channeluser,&mode,&user);
					//printf("channeluser:%s\n",channeluser );
					//printf("mode:%s\n",mode );
					//printf("user:%s\n",user );
					if(channeluser[0]=='#' && mode==NULL && user==NULL){
						auxchannel=find(channeluser,channels);
						if(auxchannel!=NULL){
							IRC_RplChannelModeIs(command, "Durruti", getNickClient(client), getNameCh(auxchannel), getModeCh(auxchannel));
							escribir(getSocketClient(client),command);
							break;
						}
						else{

						}
					}
					else if(channeluser[0]=='#' && user==NULL){
						auxchannel=find((void*)channeluser,channels);
						if(auxchannel!=NULL){
							auxchannelusr2.client=client;
							auxchannelusr= find(&auxchannelusr2, auxchannel->users);
							if(auxchannelusr!=NULL){
								if(getOpChUsr(auxchannelusr)){
									if(strpbrk(mode, "+")!=NULL){
										if(strpbrk(mode, "i")!=NULL){
											setInviteCh(auxchannel,true);
										}
										if(strpbrk(mode, "v")!=NULL){
											setVoiceCh(auxchannel,true);	
										}
										if(strpbrk(mode, "s")!=NULL){
											setHidenCh(auxchannel,true);
										}
										if(strpbrk(mode, "t")!=NULL){
											setTopciProtectCh(auxchannel,true);
										}
									}
									else if(strpbrk(mode, "-")!=NULL){
										if(strpbrk(mode, "i")!=NULL){
											setInviteCh(auxchannel,false);
										}
										if(strpbrk(mode, "v")!=NULL){
											setVoiceCh(auxchannel,false);	
										}
										if(strpbrk(mode, "s")!=NULL){
											setHidenCh(auxchannel,false);
										}
										if(strpbrk(mode, "t")!=NULL){
											setTopciProtectCh(auxchannel,false);
										}
									}
									else{

									}
									/*TODO broadcas del mensaje*/
								}
							}
							else{

							}
						}
						else{

						}
					}
					else {

					}
				}
			break;
			case WHO:
				//printf("WHO\n");
				syslog (LOG_ERR, "WHO");
				if(bnick){
					mask=NULL;
					IRCParse_Who(aux11, &prefix, &mask,&oppar);
					//printf("mask:%s\n",mask);
					//printf("oppar:%s\n",oppar);
					if(mask==NULL){

					}
					else if(mask[0]=='#'){
						//printf("caso canal\n");
						auxstr=malloc(strlen(mask)+2);
						strcpy(auxstr,mask);
						//printf("auxstr:%s\n",auxstr );
						auxchannel=find(auxstr, channels);
						if(auxchannel==NULL){
							//printf("no existe el canal\n");
							IRC_RplEndOfWho(command, getIdClient(client), NULL, NULL);
							escribir(getSocketClient(client),command);
							//printf("se manda:%s\n",command );
						}
						else{
							//printf("existe el canal\n");
							rplUsers(auxchannel, client);
						}
						free(auxstr);
					}
					else{

					}
				}
			break;
			default:
				syslog (LOG_ERR, "DEFAULT");
				//printf("DEFAULT\n");
			break;

			
		}
		if(pipe==1){
			aux11=NULL;
			goto unpipe;
		}
	}
}
