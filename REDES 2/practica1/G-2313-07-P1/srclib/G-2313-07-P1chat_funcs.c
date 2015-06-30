#include <redes2/chat.h>
#include <errno.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <redes2/irc.h>
#include <time.h>
#include <sys/types.h>
#include "../include/G-2313-07-P1conexion.h"
#include "../include/G-2313-07-P1ficheros.h"
void* commandCase(void * args);
void * Ping(void *args);
int sockfd, emision=0;
char nickName[10];
pthread_t h1,h2,h3,h4;
char *archivo=NULL, *direccion=NULL,*puerto=NULL;


/**
 * @page Ping \b Ping
 *
 * @brief Hilo que se ejecuta continuamente mandando PING.
 *
 * @section SYNOPSIS
 * 	\b #include \b <redes2/chat.h>
 *
 *	\b void *\b Ping \b (\b void * args\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Envia PING al servidor cada 30 segundos mientras la conexion este activa.
 *  
 * No tiene parámetros de entrada.
 *
 * @section retorno RETORNO
 * No devuelve ningún valor ni código de error.
 *
 * @section seealso VER TAMBIÉN
 * \b 
 *
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/
 void * Ping(void *args){
 	while(1){
 		char command[10];
 		int user;
		sleep(30);
		IRC_Ping(command, NULL, get_server(), NULL);
		user=escribir(sockfd,command);
		printf("%s, %d\n",command,user );
	}
 }

/**
 * @page connect_client \b connect_client
 *
 * @brief Llamada por el botón de "Conectar".
 *
 * @section SYNOPSIS
 * 	\b #include \b <redes2/chat.h>
 *
 *	\b void \b connect_client \b (\b void\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Es la función llamada cada vez que se pulsa el botón de "Conexion" en el
 * interfaz gráfico.
 * 
 * No tiene parámetros de entrada.
 *
 * @section retorno RETORNO
 * No devuelve ningún valor ni código de error.
 *
 * @section seealso VER TAMBIÉN
 * \b connect_client(3).
 *
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/

void connect_client(void)
{
	struct addrinfo hints, *res;
	int nick, user;
	char port[20];
	char command[256];
	sprintf(port, "%d",get_port());
	printf("%s:%s\n",get_server(),port );
	memset(&hints, 0, sizeof hints);
	hints.ai_family = AF_UNSPEC;
	hints.ai_socktype = SOCK_STREAM;
	strcpy(nickName,get_nick());
	/*Comenzamos la conexion TCP*/
	printf("se obtiene informacion\n");
	if(0!=getaddrinfo(get_server(), port, &hints, &res)){
		error_text(current_page(),"Error al obtener informacion del servidor");
		return;
	}
	printf("socket\n");
	sockfd=abrirSocketTCP();
	if(sockfd==-1){
		error_text(current_page(),"Error al crear el socket");
		return;
	}
	printf("connect\n");
	if(-1==abrirConnect(sockfd, *(res->ai_addr))){
		error_text(current_page(),"Error al conectar");	
		return;
	}
	/*Conexion IRC*/
	printf("IRC\n");
	IRC_Nick(command, NULL, nickName);
	nick=escribir(sockfd,command);
	pthread_create(&h1,NULL, commandCase, (void *)NULL );

	printf("%s, %d\n",command,nick );
	IRC_User(command, NULL, get_name(), "w", nickName);
	user=escribir(sockfd,command);
	printf("%s, %d\n",command,user );
	pthread_create(&h2,NULL, Ping, (void *)NULL );
	message_text(current_page(),"Conectado"); /* Ejemplo de uso */
}

/**
 * @page disconnect_client \b disconnect_client
 *
 * @brief Llamada por el botón de "Desconexión".
 *
 * @section SYNOPSIS
 * 	\b #include \b <redes2/chat.h>
 *
 *	\b void \b disconnect_client \b (\b void\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Es la función llamada cada vez que se pulsa el botón de "Desconexión" en el
 * interfaz gráfico.
 * 
 * No tiene parámetros de entrada.
 *
 * @section retorno RETORNO
 * No devuelve ningún valor ni código de error.
 *
 * @section seealso VER TAMBIÉN
 * \b connect_client(3).
 *
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/

void disconnect_client()
{
	char command[1024];

	set_current_page(0);
	while(get_name_page(1) != NULL){
		delete_page	(1);
	}
	if (IRC_Quit(command, NULL, "Adios")<0){
		printf("ERROR to tocho\n");
		return;
	}
	printf("se envia==%s\n",command );
	escribir(sockfd, command);
	close(sockfd);
	pthread_cancel(h1);
    pthread_cancel(h2);
	pthread_cancel(h3);
    pthread_cancel(h4);
    gdk_threads_enter();
	message_text(current_page(),"DESCONEXION");
    gdk_threads_leave();
}

/**
 * @page topic_protect \b topic_protect
 *
 * @brief Llamada por el checkbox de "Protección de tópico".
 *
 * @section SYNOPSIS
 * 	\b #include \b <redes2/chat.h>
 *
 *	\b void \b topic_protect \b (\b gboolean\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Es la función llamada cada vez que se selecciona o deselecciona el checkbox
 * de "Protección de tópico".
 * 
 * Recibe como parámetro de entrada el valor TRUE o FALSE según se active o
 * desactive el botón de "Protección de tópico".
 *
 * @section retorno RETORNO
 * No devuelve ningún valor ni código de error.
 *
 * @section seealso VER TAMBIÉN
 * \b extern_msg(3), \b secret(3), \b guests(3), \b privated(3), \b moderated(3).
 *
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/

void topic_protect(gboolean state)
{
	char* command=NULL;
	printf("topic_protect\n");
	command=(char*)malloc(60);
	if(state==TRUE){
		printf("TRUE\n");
		IRC_Mode(command,NULL, get_name_page(current_page()), "+t", NULL);
		escribir(sockfd,command);
		free(command);
		command=NULL;
	}
	else{
		printf("FALSE\n");
		IRC_Mode(command,NULL, get_name_page(current_page()), "-t", NULL);
		escribir(sockfd,command);
		free(command);
		command=NULL;
	}
}

/**
 * @page extern_msg \b extern_msg
 *
 * @brief Llamada por el checkbox de "Mensaje externos".
 *
 * @section SYNOPSIS
 * 	\b #include \b <redes2/chat.h>
 *
 *	\b void \b extern_msg \b (\b gboolean\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Es la función llamada cada vez que se selecciona o deselecciona el checkbox
 * de "Mensaje externos".
 * 
 * Recibe como parámetro de entrada el valor TRUE o FALSE según se active o
 * desactive el botón de "Mensaje externos".
 *
 * @section retorno RETORNO
 * No devuelve ningún valor ni código de error.
 *
 * @section seealso VER TAMBIÉN
 * \b topic_protect(3), \b secret(3), \b guests(3), \b privated(3), \b moderated(3).
 *
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/

void extern_msg(gboolean state)
{
	char* command=NULL;
	printf("extern_msg\n");
	command=(char*)malloc(60);
	if(state==TRUE){
		printf("TRUE\n");
		IRC_Mode(command,NULL, get_name_page(current_page()), "+n", NULL);
		escribir(sockfd,command);
		free(command);
	}
	else{
		printf("FALSE\n");
		IRC_Mode(command,NULL, get_name_page(current_page()), "-n", NULL);
		escribir(sockfd,command);
		free(command);
	}
}

/**
 *
 * @brief Llamada por el checkbox de "Secreto".
 * @page secret \b secret
 *
 * @section SYNOPSIS
 * 	\b #include \b <redes2/chat.h>
 *
 *	\b void \b secret \b (\b gboolean\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Es la función llamada cada vez que se selecciona o deselecciona el checkbox
 * de "Secreto".
 * 
 * Recibe como parámetro de entrada el valor TRUE o FALSE según se active o
 * desactive el botón de "Secreto".
 *
 * @section retorno RETORNO
 * No devuelve ningún valor ni código de error.
 *
 * @section seealso VER TAMBIÉN
 * \b extern_msg(3), \b topic_protect(3), \b guests(3), \b privated(3), \b moderated(3).
 *
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/

void secret(gboolean state){
	char* command=NULL;
	printf("secret\n");
	command=(char*)malloc(60);
	if(state==TRUE){
		printf("TRUE\n");
		IRC_Mode(command,NULL, get_name_page(current_page()), "+s", NULL);
		escribir(sockfd,command);
		free(command);
	}
	else{
		printf("FALSE\n");
		IRC_Mode(command,NULL, get_name_page(current_page()), "-s", NULL);
		escribir(sockfd,command);
		free(command);
	}
}

/**
 *
 * @brief Llamada por el checkbox de "Invitados".
 * @page guests \b guests
 *
 * @section SYNOPSIS
 * 	\b #include \b <redes2/chat.h>
 *
 *	\b void \b guests \b (\b gboolean\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Es la función llamada cada vez que se selecciona o deselecciona el checkbox
 * de "Invitados".
 * 
 * Recibe como parámetro de entrada el valor TRUE o FALSE según se active o
 * desactive el botón de "Invitados".
 *
 * @section retorno RETORNO
 * No devuelve ningún valor ni código de error.
 *
 * @section seealso VER TAMBIÉN
 * \b extern_msg(3), \b topic_protect(3), \b secret(3), \b privated(3), \b moderated(3).
 *
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/

void guests(gboolean state)
{
	char* command=NULL;
	printf("guests\n");
	command=(char*)malloc(60);
	if(state==TRUE){
		printf("TRUE\n");
		IRC_Mode(command,NULL, get_name_page(current_page()), "+i", NULL);
		escribir(sockfd,command);
		free(command);
	}
	else{
		printf("FALSE\n");
		IRC_Mode(command,NULL, get_name_page(current_page()), "-i", NULL);
		escribir(sockfd,command);
		free(command);
	}
}

/**
 *
 * @brief Llamada por el checkbox de "Privado".
 * @page privated \b privated
 *
 * @section SYNOPSIS
 * 	\b #include \b <redes2/chat.h>
 *
 *	\b void \b privated \b (\b gboolean\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Es la función llamada cada vez que se selecciona o deselecciona el checkbox
 * de "Privado".
 * 
 * Recibe como parámetro de entrada el valor TRUE o FALSE según se active o
 * desactive el botón de "Privado".
 *
 * @section retorno RETORNO
 * No devuelve ningún valor ni código de error.
 *
 * @section seealso VER TAMBIÉN
 * \b extern_msg(3), \b topic_protect(3), \b secret(3), \b guests(3), \b moderated(3).
 *
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/

void privated(gboolean state){
	char* command=NULL;
	printf("privated\n");
	command=(char*)malloc(60);
	if(state==TRUE){
		printf("TRUE\n");
		IRC_Mode(command,NULL, get_name_page(current_page()), "+p", NULL);
		escribir(sockfd,command);
		free(command);
	}
	else{
		printf("FALSE\n");
		IRC_Mode(command,NULL, get_name_page(current_page()), "-p", NULL);
		escribir(sockfd,command);
		free(command);
	}
}

/**
 *
 * @brief Llamada por el checkbox de "Moderado".
 * @page moderated \b moderated
 *
 * @section SYNOPSIS
 * 	\b #include \b <redes2/chat.h>
 *
 *	\b void \b moderated \b (\b gboolean\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Es la función llamada cada vez que se selecciona o deselecciona el checkbox
 * de "Moderado".
 * 
 * Recibe como parámetro de entrada el valor TRUE o FALSE según se active o
 * desactive el botón de "Moderado".
 *
 * @section retorno RETORNO
 * No devuelve ningún valor ni código de error.
 *
 * @section seealso VER TAMBIÉN
 * \b extern_msg(3), \b topic_protect(3), \b secret(3), \b guests(3), \b privated(3).
 *
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 *		
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/

void moderated(gboolean state){
	char* command=NULL;
	printf("moderated\n");
	command=(char*)malloc(60);
	if(state==TRUE){
		printf("TRUE\n");
		IRC_Mode(command,NULL, get_name_page(current_page()), "+m", NULL);
		escribir(sockfd,command);
		free(command);
	}
	else{
		printf("FALSE\n");
		IRC_Mode(command,NULL, get_name_page(current_page()), "-m", NULL);
		escribir(sockfd,command);
		free(command);
	}
}


/**
 * @page new_text \b new_text
 *
 * @brief Recibe los mensajes del sevidor.
 *
 * @section SYNOPSIS
 * 	\b #include \b "chat.h"
 *
 *	\b void \b new_text \b (\b char *msg\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *	Recibe el mensaje que ha escrito el usuario en la interfaz.
 * 	Depenediendo de la entrada que haya mandado el usuario
 * 	genera mansajes, comandos y llama a hilos para recibir y  
 * 	mandar ficheros.
 * 
 * 	Recibe el mensaje que manda el cliente.
 *
 * @section retorno RETORNO
 * 
 * No devulve nada.
 *
 * @section seealso VER TAMBIÉN
 * \b commandCase(3)
 *
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/

void new_text (char *msg)
{
	int i=0,j, tam,Spuerto=0;
	char command[1024];
	char **str=NULL;
	char *str2=NULL, *usr=NULL,*ch=NULL,*aux1=NULL,*aux2=NULL,*aux3=NULL,*aux4=NULL;
	int numchannels;
	unsigned int Sdireccion;
	ch=(char*)malloc(sizeof(char)*10);
	usr=(char*)malloc(sizeof(char)*9);
	str2=(char*)malloc(sizeof(char)*128);
	switch(IRCUser_CommandQuery(msg)){
		case UCYCLE:
			printf("UCYCLE\n");
			
			if( IRCUserParse_Cycle(msg, &str, &numchannels) == -1){
				gdk_threads_enter();
				message_text(current_page(), "argumentos erroneos para el comando cycle");
				gdk_threads_leave();
				break;
			}
			
			IRC_Part(command, NULL, get_name_page(current_page()), NULL);
			escribir(sockfd, command);
			
			for(i=0;i<numchannels;i++){
				IRC_Join(command, NULL, str[i], NULL);	
				escribir(sockfd, command);			
			}
			
		break;



		case UUSERHOST:
			printf("UUSERHOST\n");
			
			if(IRCUserParse_Userhost(msg, &str2) == -1){
				gdk_threads_enter();
				message_text(current_page(), "argumentos erroneos para el comando userhost");
				gdk_threads_leave();
				break;
			}
			
			IRC_Userhost(command, NULL, str2, NULL, NULL, NULL, NULL);
			escribir(sockfd, command);
			
		break;

		case UBOTMOTD:
			printf("UBOTMOTD\n");
			if(IRCUserParse_BotMotd( msg, &str2) < 0){
				if(str2 == NULL){
					IRC_Motd(command, NULL, str2);
					escribir(sockfd, command);
				}else{				
					
				gdk_threads_enter();
				message_text(current_page(), "argumentos erroneos para el comando motd");
				gdk_threads_leave();
				break;
				}
			}else{
				IRC_Motd(command, NULL, str2);
				escribir(sockfd, command);
			}
			
		break;


		case UIDENTIFY:
			printf("UIDENTIFY\n");
			if(IRCUserParse_Identify( msg, &str2) < 0){
				gdk_threads_enter();
				message_text(current_page(), "argumentos erroneos para el comando identify");
				gdk_threads_leave();
				break;
			}
			/*TODO*/
			
			
		break;
		case UDNS:
			printf("UDNS\n");
			
			if(IRCUserParse_Dns( msg, &str2) < 0){
				gdk_threads_enter();
				message_text(current_page(), "argumentos erroneos para el comando dns");
				gdk_threads_leave();
				break;			
			}
			
			/*TODO*/
			
		break;

		case UUSERIP:
			printf("UUSERIP\n");
			
			if(IRCUserParse_UserIp( msg, &str2) < 0){
				gdk_threads_enter();
				message_text(current_page(), "argumentos erroneos para el comando userip");
				gdk_threads_leave();
				break;
			}
			IRC_Who(command, NULL, str2, NULL);
			escribir(sockfd, command);	
			/*TODO ????*/
			
		break;

		case USTATS:
			printf("USTATS\n");
			
			if(IRCUserParse_Stats( msg, &str2) < 0){
				gdk_threads_enter();
				message_text(current_page(), "argumentos erroneos para el comando stats");
				gdk_threads_leave();
				break;
			}
			
			IRC_Stats(command, NULL, str2, NULL);
			escribir(sockfd, command);			
			
		break;

		case UCTCP:
			printf("UCTCP\n");
			if(IRCUserParse_CTCP( msg, &str2) < 0){
				gdk_threads_enter();
				message_text(current_page(), "argumentos erroneos para el comando ctcp");
				gdk_threads_leave();
				break;
			}
			/*TODO*/
		break;

		case UDCC:
			printf("UDCC\n");
			
			if(IRCUserParse_DCC( msg, &str2) < 0){
				gdk_threads_enter();
				message_text(current_page(), "argumentos erroneos para el comando dcc");
				gdk_threads_leave();
				break;
			}
			/*TODO*/
		break;

		case UNAMES:
			printf("UNAMES\n");/*TODO preguntar a Eloy*/
			
		break;
		case UHELP:
			printf("UHELP\n");
			IRCUserParse_Help(msg, &str2);
			gdk_threads_enter();
			message_text(current_page(),"le has dado a HELP");
			gdk_threads_leave();
		break;
		case ULIST:
			printf("ULIST\n");
			if(IRCUserParse_List(msg, &str2)<0){

				gdk_threads_enter();
				message_text(current_page(), "argumentos erroneos para el comando list");
				gdk_threads_leave();
				break;
			}
			IRC_List(command, NULL, str2, NULL);
			escribir(sockfd, command);
		break;
		case UJOIN:/*TODO entra en un canal aunque no le pongas el #, pese a que no lo reconoce como canal*/
			printf("UJOIN\n");
			printf("%s\n",msg );
			if(IRCUserParse_Join(msg, &str, &numchannels)<0){
				gdk_threads_enter();
				message_text(current_page(), "argumentos erroneos para el comando JOIN");
				gdk_threads_leave();
				break;
			}
			printf("numchannels =%d\n",numchannels);
			for(i=0;i<numchannels;i++){
				printf("i==%d\n",i );
				IRC_Join(command, NULL, str[i], NULL);
				printf("%s\n",command );
				escribir(sockfd,command);
				printf("escribir\n");
				
			}
			printf("break\n");
			break;
		case ULEAVE:
		case UPART:
			
			printf("UPART\n");
			if(IRCUserParse_Part(msg, &ch,&str2)<0){
				gdk_threads_enter();
				message_text(current_page(), "argumentos erroneos para el comando part");
				gdk_threads_leave();
				break;
			}

			printf("command=%s\n",command);
			if(ch == NULL){
				if(IRC_Part(command, NULL, get_name_page(current_page()),str2)<0){/*TODO si ch es NULL pasar current_page()*/
					printf("mal\n");
					break;
				}
			}else{
				if(IRC_Part(command, NULL, ch,str2)<0){
					printf("mal\n");
					break;
				}
			}
			printf("command=%s\n",command);
			escribir(sockfd,command);
		break;
		
		case UQUIT:
			printf("UQUIT\n");
			if(IRCUserParse_Quit(msg, &str2)<0){
				gdk_threads_enter();
				message_text(current_page(), "argumentos erroneos para el comando quit");
				gdk_threads_leave();
				break;
			}
			disconnect_client();
		break;
		case UNICK:
			printf("UNICK\n");
			if(IRCUserParse_Nick(msg,&usr)<0){
				gdk_threads_enter();
				message_text(current_page(), "argumentos erroneos para el comando nick");
				gdk_threads_leave();
				break;
			}
			IRC_Nick(command,NULL,usr);
			escribir(sockfd,command);

		break;
		case UAWAY:
			printf("UAWAY\n");
			if(IRCUserParse_Away(msg, &str2)<0){
				message_text(current_page(), "argumentos erroneos para el comando away");
				break;
			}
			IRC_Away(command, NULL, str2);
			escribir(sockfd,command);
		break;
		case UWHOIS:
			printf("UWHOIS\n");
			if(IRCUserParse_Whois(msg, &usr)<0){
				message_text(current_page(), "argumentos erroneos para el comando whois");
				break;
			}
			IRC_Whois(command, NULL, usr, NULL);
			escribir(sockfd, command);
		break;
		case UINVITE:
			printf("UINVITE\n");
			if(IRCUserParse_Invite(msg, &usr, &ch)<0){
				message_text(current_page(), "argumentos erroneos para el comando invite");
				break;
			}
			IRC_Invite(command,NULL,usr,ch);
			escribir(sockfd,command);
		break;
		case UKICK:
			printf("UKICK\n");
			if(IRCUserParse_Kick(msg, &usr)<0){
				message_text(current_page(), "argumentos erroneos para el comando kick");
				break;
			}
			IRC_Kick(command,NULL, get_name_page(current_page()), usr, "TU MADRE");
			escribir(sockfd, command);
		break;
		case UTOPIC:
			printf("UTOPIC\n");
			if(IRCUserParse_Topic(msg,&str2)<0){
				message_text(current_page(), "argumentos erroneos para el comando topic");
				break;
			}
			IRC_Topic(command,NULL, get_name_page(current_page()),str2);
			escribir(sockfd,command);
		break;
		case UME:
			printf("UME\n");
			if(IRCUserParse_Me(msg, &str2)<0){
				message_text(current_page(), "argumentos erroneos para el comando me");
				break;
			}
			IRC_Privmsg(command, NULL,get_name_page(current_page()),str2);
			escribir(sockfd, command);
		break;
		case UMSG:
			printf("UMSG\n");
			if(IRCUserParse_Msg(msg, &ch, &str2)<0){
				message_text(current_page(), "argumentos erroneos para el comando msg");
				break;
			}
			IRC_Privmsg(command, NULL,ch,str2);
			escribir(sockfd, command);
		break;
		case UQUERY:
			printf("UQUERY\n");
			if(IRCUserParse_Query(msg, &usr, &str2)<0){
				message_text(current_page(), "argumentos erroneos para el comando query");
				break;
			}
			gdk_threads_leave();
			gdk_threads_enter();
			add_new_page(usr);
			set_current_page(get_index_page(usr));
			gdk_threads_leave();
			IRC_Privmsg(command, NULL, usr, str2);
			escribir(sockfd, command);
		break;
		case UNOTICE:
			printf("UNOTICE\n");
			if(IRCUserParse_Notice(msg, &ch , &str2)<0){
				message_text(current_page(), "argumentos erroneos para el comando notice");
				break;
			}
			IRC_Notice(command, NULL, ch,str2 );
			printf("%s\n",command);
			escribir(sockfd,command);
		break;
		case UNOTIFY:
			printf("UNOTIFY\n");
		break;
		case UIGNORE:
			printf("UIGNORE\n");
		break;
		case UPING:
			printf("UPING\n");

		break;
		case UWHO:
			printf("UWHO\n");
			IRCUserParse_Who(msg, &str2 );
			IRC_Who(command,NULL, str2, NULL);
			escribir(sockfd, command);
		break;
		case UWHOWAS:
			printf("UWHOWAS\n");
			IRCUserParse_WhoWas(msg, &usr, &j);
			IRC_Whowas(command, NULL, usr, j, NULL);
			escribir(sockfd, command);


		break;
		case UISON:
			printf("UISON\n");
			IRCUserParse_Ison(msg, &str, &j);
			for(i=0;i<j;i++){
				IRC_Ison(command, NULL, str[i], NULL);
				escribir(sockfd,command);
			}
		break;
		
		case UMOTD:
			printf("UMOTD\n");
			IRCUserParse_Motd(msg, &str2);
			IRC_Motd(command, NULL,str2);
			escribir(sockfd, command);
			
		break;
		case URULES:
			printf("URULES\n");
			IRCUserParse_Rules( msg, &str2);

		break;
		case ULUSERS:
			printf("ULUSERS\n");
			IRCUserParse_Lusers(msg, &str2);
			IRC_Lusers(command, NULL, NULL,NULL );
			escribir(sockfd, command);
		break;
		case UVERSION:
			printf("UVERSION\n");
			IRCUserParse_Version(msg, &str2);
			IRC_Version(command,NULL, str2);
			escribir(sockfd,command);
		break;
		case UADMIN:
			printf("UADMIN\n");
			IRCUserParse_Admin(msg, &str2);
			IRC_Admin(command,NULL, str2);
			escribir(sockfd,command);
		break;
		
		case UKNOCK:
			printf("UKNOCK\n");
			if(IRCUserParse_Knock( msg, &ch, &str2)<0){
				printf("ERROR KNOCK\n");
				message_text(current_page(), "argumentos erroneos para el comando notice");
				break;
			}
			IRC_Notice(command, NULL, ch,str2 );
			printf("%s\n",command);
			escribir(sockfd,command);
		break;
		case UVHOST:
			printf("UVHOST\n");
		break;
		case UMODE:
			printf("UMODE\n");
			IRCUserParse_Mode(msg,&ch,&str2,&usr);
			IRC_Mode(command,NULL, ch, str2, usr);
			escribir(sockfd,command);

		break;
		case UTIME:
			printf("UTIME\n");
			IRCUserParse_Time(msg, &str2);
			IRC_Time(command,NULL, str2);
			escribir(sockfd,command);			
		break;
		case UMAP:
			printf("UMAP\n");
			/*TODO sin definir*/
		break;
		case ULINKS:
			printf("ULINKS\n");
			//falta el parse de la libreria
			//IRC_Links(command, NULL, char *remoteserver, char *servermask);
			/*TODO*/
		break;
		case USETNAME:
			printf("USETNAME\n");
			/*TODO no existen las funciones*/
		break;
		case ULICENSE:
			printf("ULICENSE\n");
			/*TODO no existen las funciones*/
		break;
		case UMODULE:
			printf("UMODULE\n");
			/*TODO no existen las funciones*/
		break;
		case UPARTALL:
			printf("UPARTALL\n");
		break;
		case UCHAT:
			printf("UCHAT\n");
		break;
		default:/*arreglado el imprimir solo una palabra*/
			printf("DEFAULT\n");
			aux3 = (char*)malloc(sizeof(char)*strlen(msg));
			strcpy(aux3, msg);
			printf("msg %s\n",aux3 );
			aux1=strtok(aux3," ");
			printf("aux1%s\n",aux1);
			aux2=(char*)malloc(sizeof(char)*strlen(aux1));
			strcpy(aux2, aux1);
			if(strcmp (aux2, "/fcancel")==0){
				usr=strtok(NULL, " ");
				aux4=(char *)malloc(sizeof(char)*(8+strlen("DCCCLOSE")+6));
				sprintf(aux4,"%cDCC CLOSE%c",1,1);
				IRC_Privmsg(command, NULL, usr, aux4);
				printf("%s\n",command);
				escribir(sockfd,command);
			}
			if(strcmp (aux2, "/faccept")==0){
				aux1=strtok(NULL, " ");
				printf("aux1==%s\n",aux1 );
				if(emision==0){
					gdk_threads_enter();
					error_text(current_page(), "nadie te te quiere mandar un fichero");
					gdk_threads_leave();
					break;
				}
				args.archivo=(char*)malloc(sizeof(char)*strlen(aux1));
				strcpy(args.direccion,direccion);
				strcpy(args.archivo,aux1);
				strcpy(args.strPuerto,puerto);
				printf("Estableciendo conexion con %s %s por recurso %s\n", atoIp(args.direccion),args.strPuerto,args.archivo);
				pthread_create(&h4,NULL, clienteArchivo,(void *) &args);
				emision=0;
				if(i<0){
					gdk_threads_enter();
					error_text(current_page(), "error al mandar fichero");
					gdk_threads_leave();
					break;
				}

				break;
			}
			else if(strcmp (aux2, "/fsend")==0){
				usr=strtok(NULL," ");
				aux1=strtok(NULL," ");
				printf("usr =%s aux1= %s\n",usr,aux1);
				if(usr==NULL||aux1==NULL){
					gdk_threads_enter();
					error_text(current_page(), "argumentos incorrectos para fsend");
					gdk_threads_leave();
					break;
				}
				srand (time(NULL));
				sprintf(str2,"%d",get_port());
				if(0==obtenerIPInterface("wlan0", (uint8_t *)&Sdireccion)){
					if(0==obtenerIPInterface("eth0", (uint8_t *)&Sdireccion)){
						gdk_threads_enter();
						error_text(current_page(), "tienes que conectarte a traves de eth0 o wlan0");
						gdk_threads_leave();
						break;
					}
				}

				printf("misterio1\n");
				args.archivo=(char*)malloc(sizeof(char)*(strlen(aux1)+5));
				strcpy(args.archivo,aux1);
				Spuerto=rand() % 64536 + 1024;
				args.intPuerto=Spuerto;
				pthread_create(&h3,NULL, servidorArchivo, (void**)&args);
				if(i<0){
					gdk_threads_enter();
					error_text(current_page(), "error al mandar fichero");
					gdk_threads_leave();
					break;
				}
				printf("[%s]\n",aux1);
				if((tam=getTamanoFichero(aux1))<=0){
					error_text(current_page(),"no existe el archivo");
					break;
				}
				aux4=(char *)malloc(sizeof(char)*(8+strlen("DCCSEND")+strlen(aux1)+15));
				sprintf(aux4,"%cDCC SEND %s %u %u %u %c",1,aux1, Sdireccion,Spuerto,tam,1);
				IRC_Privmsg(command, NULL, usr, aux4);
				printf("%s\n",command);
				escribir(sockfd,command);
			}
			else{
				printf("ELSE\n");
				public_text(current_page(),nickName,msg); 
				IRC_Privmsg(command, NULL, get_name_page(current_page()	), msg);
				//IRC_Notice(command, NULL, "#cosas", msg);

				printf("%s\n",command);
				escribir(sockfd,command);
			}
		break;
	}
}

/**
 * @page commandCase \b commandCase
 *
 * @brief Recibe los mensajes del sevidor.
 *
 * @section SYNOPSIS
 * 	\b #include \b <redes2/chat.h>
 *
 *	\b int \b current_page \b (\b void\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *	Recibe con un bucle los mensajes del servidor, y 
 * 	los parsea y dependiendo del mensaje que reciba realiza 
 * 	opearaciones con lainterfaz. 	
 * 
 * No recibe nada.
 *
 * @section retorno RETORNO
 * 
 * No devulve nada.
 *
 * @section seealso VER TAMBIÉN
 * \b new_text(3), \b delete_page(3), \b get_name_page(3), \b set_current_page(3)
 *
 * @section authors AUTOR
 * Mario Valdemaro Garcia Roque (mariov.garcia@estudiante.uam.es)
 * Roberto Garcia Teodoro (roberto.garciat@estudiante.uam.es)
*/

void * commandCase(void* args){
	long command;
	short pipe=0;
	char buf[8096];
	char *aux1=NULL,*aux11=NULL;
	char *aux2=NULL,*aux12=NULL;
	char *aux3=NULL,*aux13=NULL,*aux14=NULL;
	char *usr=NULL,*prefix=NULL;
	char *ch=NULL;
	char *pch=NULL;
	char *buf2=NULL;
	int  t=0,t2=0, pos=0;
	/*aux1=(char*)malloc(sizeof(char)*1024);
	aux2=(char*)malloc(sizeof(char)*1024);
	aux3=(char*)malloc(sizeof(char)*1024);
	*/while(1){
		command =recibir(sockfd,buf);
		printf("\t\t\t\tcommand:%li\n",command);
		printf("buf==%s\n",&buf[pos] );
		
		unpipe:

		IRC_UnPipelineCommands(&buf[pos], &aux11);
		if(aux11==NULL){
			pipe=0;	
			pos=0;		
		}
		else{
			printf("\t\tpiped command %s\n\n",aux11);
			pipe=1;
			pos+=strlen(aux11);
		}
		command=IRC_CommandQuery(&buf[pos]);
		printf("pos==%d\n",pos );
		switch(command){
			case PASS:
				printf("PASS\n");
			break;
			case NICK:
				printf("NICK\n");
				IRCParse_Nick(&buf[pos], &prefix, &usr);
				pch=strtok(prefix,"!");
				aux2=strtok(usr,":");
				if(strcmp(pch,nickName)==0){
					strcpy(nickName,aux2);
					free(prefix);
					prefix=NULL;
					free(usr);
					usr=NULL;
					break;
				}
				aux1=malloc(strlen(" es ahora conocido como ")+strlen(pch)+strlen(aux2)+5);
				sprintf(aux1,"%s es ahora conocido como %s",pch,aux2);
				gdk_threads_enter();
				message_text(0, aux1);
				gdk_threads_leave();
				free(prefix);
				prefix=NULL;
				free(usr);
				usr=NULL;
				free(aux1);
				aux1=NULL;

			break;
			case USER:
				printf("USER\n");
			break;
			case OPER:
				printf("OPER\n");
			break;
			case MODE:
				printf("MODE\n");
				IRCParse_Mode(&buf[pos], &prefix, &usr, &aux11, &aux12);
				/**/

			break;
			case SERVICE:
				printf("SERVICE\n");
				//IRCParse_Service(buf, &prefix, char **servicenick, char **maskdistribution, char **info);
				/*TODO*/
			break;
			case QUIT:
				printf("QUIT\n");
				IRCParse_Quit(&buf[pos], &prefix, &aux11);
				pch=strtok(prefix,"!");
				if(strcmp(pch,nickName)==0){
					disconnect_client();
					break;
				}
				aux1=malloc(strlen(" se ha marchado ()")+strlen(pch)+strlen(aux11)+5);
				sprintf(aux1,"%s se ha marchado (%s)",pch,aux11);
				gdk_threads_enter();
				message_text(0,aux1);
				gdk_threads_leave();
				free(prefix);
				free(aux11);
				prefix=NULL;
				aux11=NULL;
				free(aux1);
				aux1=NULL;
			break;
			case SQUIT:
				printf("SQUIT\n");
				IRCParse_Squit(&buf[pos], &prefix, &aux11, &aux12);
				/*TODO*/
			break;
			case JOIN:
				printf("JOIN\n");
				IRCParse_Join(&buf[pos], &usr, &ch, &aux2);
				pch = strtok(usr,"!");
				if(strcmp(nickName,pch)==0){
					gdk_threads_enter();
					add_new_page(&ch[1]);
					aux1=(char*)malloc(sizeof(char)*(strlen("te has unido a ")+strlen(ch)));
					sprintf(aux1,"te has unido a %s",ch);
					printf("canal  %s\n",&ch[1] );
					set_current_page(get_index_page(&ch[1]));
					message_text(get_index_page(&ch[1]),aux1);
					gdk_threads_leave();

				}
				else{
					aux1=(char*)malloc(sizeof(char)*(strlen("se ha unido ")+strlen(pch)));
					sprintf(aux1,"se ha unido %s",pch);
					gdk_threads_enter();
					message_text(get_index_page(&ch[1]),aux1);
					gdk_threads_leave();
					printf("fin\n");	
				}
				free(usr);
				free(ch);
				free(aux2);
				aux2=NULL;
				ch=NULL;
				ch=NULL;
				free(aux1);
				aux1=NULL;
			break;
			case PART:
				printf("PART\n");
				IRCParse_Part(&buf[pos], &aux12, &ch, &aux11);
				if(strcmp(nickName,strtok(aux12,"!"))==0){
					gdk_threads_enter();
					delete_page(get_index_page(ch));
					gdk_threads_leave();
					free(aux12);
					free(ch);
					free(aux11);
					aux12=NULL;
					ch=NULL;
					aux11=NULL;
					break;
				}
				pch = strtok(usr,"!");
				aux1=(char*)malloc(sizeof(char)*(strlen("se ha marchado  ")+strlen(pch)+strlen(aux11)));
				sprintf(aux1,"se ha marchado %s %s",pch,aux11);
				gdk_threads_enter();
				message_text(get_index_page(ch),aux1);
				gdk_threads_leave();
				free(aux12);
				free(ch);
				free(aux11);
				aux12=NULL;
				ch=NULL;
				aux11=NULL;
			break;
			case TOPIC:
				printf("TOPIC\n");
				IRCParse_Topic(&buf[pos], &prefix, &ch, &aux11);
				aux1=(char*)malloc(sizeof(char)*(strlen("el topic ahora es :")+strlen(aux11)));
				sprintf(aux1,"el topic ahora es :%s",aux11);
				gdk_threads_enter();
				message_text(get_index_page(ch), aux1);
				gdk_threads_leave();
				free(aux11);
				free(ch);
				free(prefix);
				aux12=NULL;
				ch=NULL;
				aux11=NULL;
				free(aux1);
				aux1=NULL;
			break;
			case NAMES:
				printf("NAMES\n");
				IRCParse_Names(&buf[pos], &prefix, &ch,  &aux11);
				gdk_threads_enter();
				message_text(get_index_page(ch), aux11);
				gdk_threads_leave();
			break;
			case LIST:
				printf("LIST\n");
				IRCParse_List(&buf[pos], &prefix, &ch,  &aux11);
				gdk_threads_enter();
				message_text(get_index_page(ch), aux11);
				gdk_threads_leave();
			break;
			case INVITE:
				printf("INVITE\n");
				IRCParse_Invite(&buf[pos], &prefix, &aux12,&ch);
				printf("%s %s %s\n",prefix,ch,aux12 );
				if(strcmp(nickName,aux12)==0){
					aux1=(char*)malloc(sizeof(char)*(strlen("te han invitado a ")+strlen(ch)+5));
					sprintf(aux1,"te han invitado a %s",ch);
					gdk_threads_enter();
					message_text(current_page(),aux1);
					gdk_threads_leave();
					free(prefix);
					free(aux12);
					free(ch);
					prefix=NULL;
					aux12=NULL;
					ch=NULL;
					free(aux1);
					aux1=NULL;
					printf("fin\n");
					break;
				}
				aux1=(char*)malloc(sizeof(char)*(strlen("han invitado a ")+strlen(aux12)+5));
				sprintf(aux1,"han invitado a %s",aux12);
				gdk_threads_enter();
				message_text(current_page(),aux1);
				gdk_threads_leave();
				free(prefix);
				free(aux12);
				free(ch);
				prefix=NULL;
				aux12=NULL;
				ch=NULL;
				free(aux1);
				aux1=NULL;

			break;
			case KICK:
				printf("KICK\n");
				IRCParse_Kick(&buf[pos], &prefix, &ch, &usr, &aux11);
				//sprintff(aux1,"%s ha sido expulsado por: %s",usr,aux11);
				gdk_threads_enter();
				message_text(get_index_page(ch), aux1);
				gdk_threads_leave();
			break;
			case PRIVMSG:
				printf("\tPRIVMSG:\n");

				IRCParse_Privmsg(&buf[pos], &aux1, &aux2, &aux3);
				printf("%s %s %s\n", aux1, aux2, aux3);
				if(strncmp(&aux3[1],"DCC SEND",8)==0){
					emision=1;
					printf("caso de envio\n" );
					pch=strtok(aux3, " ");
					printf("pch %s\n", pch);

					pch=strtok(NULL, " ");
					printf("pch %s\n", pch);
					
					pch=strtok(NULL, " ");
					archivo=(char*)malloc(sizeof(char)*strlen(pch)+8);
					strcpy(archivo, pch);
					printf("archivo %s\n", archivo);

					pch=strtok(NULL, " ");
					direccion=(char*)malloc(sizeof(char)*strlen(pch)+8);
					strcpy(direccion, pch);
					printf("direccion %s\n", direccion);
					
					pch=strtok(NULL, " ");
					puerto=(char*)malloc(sizeof(char)*strlen(pch)+8);
					strcpy(puerto, pch);
					pch=strtok(aux1,"!");
					aux11=(char*)malloc(strlen("te esta mandando un archivo si aceptas escribe /faccept <archivo>  si no aceptas /fcancel <usuario>")+strlen(pch)+5);
					sprintf(aux11,"%s te esta mandando un archivo si aceptas escribe /faccept <archivo>  si no aceptas /fcancel <usuario>",pch);
					printf("puerto %s\n", puerto);
					gdk_threads_enter();
					message_text(current_page(),aux11);
					gdk_threads_leave();
					free(aux11);
					aux11=NULL;
					free(aux1);
					aux1=NULL;
					free(aux2);
					aux2=NULL;
					free(aux3);
					aux3=NULL;
					break;
				}
				if(strncmp(&aux3[1],"DCC CLOSE",8)==0){
					gdk_threads_enter();
					message_text(current_page(),"se ha cancelado la descarga del archivo");
					gdk_threads_leave();
					pthread_cancel(h3);
					free(aux1);
					aux1=NULL;
					free(aux2);
					aux2=NULL;
					free(aux3);
					aux3=NULL;
					break;
				}
				//get_index_page(aux2);
				pch=strtok(aux1,"!");
				gdk_threads_enter();
				public_text( get_index_page(aux2),pch,aux3);
				gdk_threads_leave();	
				free(aux1);
				free(aux2);
				free(aux3);
				aux1=NULL;
				aux2=NULL;
				aux3=NULL;
			break;
			case NOTICE:
				printf("NOTICE\n");
				IRCParse_Notice(&buf[pos], &aux1, &aux2, &aux3);
				printf("%s %s %s\n",aux1 ,aux2,aux3 );
				gdk_threads_enter();
				public_text( current_page(),aux2,aux3);
				gdk_threads_leave();
				free(aux1);
				free(aux2);
				free(aux3);
				aux1=NULL;
				aux2=NULL;
				aux3=NULL;
			break;
			case MOTD:
				printf("MOTD\n");
				IRCParse_Motd(&buf[pos], &prefix, &buf2);
				gdk_threads_enter();
				message_text(0, buf2);
				gdk_threads_leave();
				free(prefix);
				free(buf2);
				buf2=NULL;
				prefix=NULL;
			break;
			case LUSERS:
				printf("LUSERS\n");
				IRCParse_Lusers(&buf[pos], &prefix, &aux1, &buf2);
				gdk_threads_enter();
				message_text(0,buf2);
				gdk_threads_leave();

				free(aux1);
				free(prefix);
				free(buf2);
				aux1=NULL;
				buf2=NULL;
				prefix=NULL;
			break;
			case VERSION:
				printf("VERSION\n");
				IRCParse_Version(&buf[pos], &prefix,&buf2);
				gdk_threads_enter();
				message_text(0, buf2);
				gdk_threads_leave();
				free(prefix);
				free(buf2);
				buf2=NULL;
				prefix=NULL;

			break;
			case STATS:
				printf("STATS\n");
				//IRCParse_Stats(buf, &prefix, char **query, char **target);
				
			break;
			case LINKS:
				printf("LINKS\n");
				IRCParse_Links(&buf[pos], &prefix, &aux1, &buf2);
				gdk_threads_enter();
				message_text(0,buf2);
				gdk_threads_leave();
				free(aux1);
				free(prefix);
				free(buf2);
				aux1=NULL;
				buf2=NULL;
				prefix=NULL;
			break;
			case TIME:
				printf("TIME\n");
				IRCParse_Time(&buf[pos], &prefix, &buf2);
				gdk_threads_enter();
				message_text(0,buf2);
				gdk_threads_leave();
			break;
			case CONNECT:
				printf("CONNECT\n");
				/*TODO*/
			break;
			case TRACE:
				printf("TRACE\n");
				IRCParse_Trace(&buf[pos], &prefix, &buf2);
				message_text(0, buf2);
			break;
			case ADMIN:
				printf("ADMIN\n");
				IRCParse_Admin(&buf[pos], &prefix, &buf2);
				message_text(0,buf2);
			break;
			case INFO:
				printf("INFO\n");
				IRCParse_Info(&buf[pos], &prefix, &buf2);
				message_text(0,buf2);
			break;
			case SERVLIST:
				printf("SERVLIST\n");
				IRCParse_Servlist(&buf[pos], &prefix, NULL, &buf2);
				/*TODO*/
			break;
			case SQUERY:
				printf("SQUERY\n");
				//IRCParse_Squery(buf, &prefix, char **servicename, char **msg);
				/*TODO*/
			break;
			case WHO:
				printf("WHO\n");
			break;
			case WHOIS:
				printf("WHOIS\n");
			break;
			case WHOWAS:
				printf("WHOWAS\n");
			break;
			case KILL:
				printf("KILL\n");
			break;
			case PING:
				printf("PING\n");
			break;
			case PONG:
				printf("PONG\n");
			break;
			case ERROR:
				printf("ERROR\n");
			break;
			case AWAY:
				printf("AWAY\n");
			break;
			case REHASH:
				printf("REHASH\n");
			break;
			case DIE:
				printf("DIE\n");
			break;
			case RESTART:
				printf("RESTART\n");
			break;
			case SUMMON:
				printf("SUMMON\n");
			break;
			case USERS:
				printf("USERS\n");
			break;
			case WALLOPS:
				printf("WALLOPS\n");
			break;
			case USERHOST:
				printf("USERHOST\n");
			break;
			case ISON:
				printf("ISON\n");
			break;
			case HELP:
				printf("HELP\n");
			break;
			case RULES:
				printf("RULES\n");
			break;
			case SERVER:
				printf("SERVER\n");
			break;
			case ENCAP:
				printf("ENCAP\n");
			break;
			case CNOTICE:
				printf("CNOTICE\n");
				//IRCUserParse_Notice(buf, aux[0], char **msg);
			break;
			case CPRIVMSG:
				printf("CPRIVMSG\n");
			break;
			case NAMESX:
				printf("NAMESX\n");
			break;
			case SILENCE:
				printf("SILENCE\n");
			break;
			case UHNAMES:
				printf("UHNAMES\n");
			break;
			case WATCH:
				printf("WATCH\n");
			break;
			case KNOCK:
				printf("KNOCK\n");
			break;
			case USERIP:
				printf("USERIP\n");
			break;
			case SETNAME:
				printf("SETNAME\n");
			break;
			case ERR_NEEDMOREPARAMS:
				printf("ERR_NEEDMOREPARAMS\n");
				error_text(current_page(), "Mas parametros necesarios");	
			break;
			case ERR_ALREADYREGISTRED:
				printf("ERR_ALREADYREGISTRED\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case ERR_NONICKNAMEGIVEN:
				printf("ERR_NONICKNAMEGIVEN\n");
				error_text(current_page(), "No se ha dado un nick");
			break;
			case ERR_ERRONEUSNICKNAME:
				printf("ERR_ERRONEUSNICKNAME\n");
				error_text(current_page(), "El nombre es erroneo(max. 8 caracteres)");
			break;
			case ERR_NICKNAMEINUSE:
				printf("ERR_NICKNAMEINUSE\n");
				error_text(current_page(), "El nombre ya esta en uso");
			break;
			case ERR_NICKCOLLISION:
				printf("ERR_NICKCOLLISION\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case ERR_UNAVAILRESOURCE:
				printf("ERR_UNAVAILRESOURCE\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case ERR_RESTRICTED:
				printf("ERR_RESTRICTED\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case RPL_YOUREOPER:
				printf("RPL_YOUREOPER\n");
				IRCParse_RplYoureOper(&buf[pos], &prefix, &usr, &aux1);
				//sprintff(aux1,"%s %s",usr, aux11);
				gdk_threads_enter();
				message_text(0, aux1);
				gdk_threads_leave();

				free(aux1);
				free(prefix);
				free(usr);
				aux1=NULL;
				usr=NULL;
				prefix=NULL;
			break;
			case ERR_NOOPERHOST:
				printf("ERR_NOOPERHOST\n");
				IRCParse_ErrNoOperHost(&buf[pos], &prefix, &usr, &aux11);
				//sprintff(aux1,"%s %s",usr,aux11);
				gdk_threads_enter();
				message_text(0, aux11);
				gdk_threads_leave();

				free(aux11);
				free(prefix);
				free(usr);
				aux11=NULL;
				usr=NULL;
				prefix=NULL;
			break;
			case ERR_PASSWDMISMATCH:
				printf("ERR_PASSWDMISMATCH\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case RPL_UMODEIS:
				printf("RPL_UMODEIS\n");
				IRCParse_RplUModeIs(&buf[pos], &prefix, &usr, &aux11);
				//sprintff(aux1,"%s %s",usr,aux11);
				gdk_threads_enter();
				message_text(0, aux11);
				gdk_threads_leave();

				free(aux11);
				free(prefix);
				free(usr);
				aux11=NULL;
				usr=NULL;
				prefix=NULL;
			break;
			case ERR_UMODEUNKNOWNFLAG:
				printf("ERR_UMODEUNKNOWNFLAG\n");
				IRCParse_ErrUModeUnknownFlag(&buf[pos], &prefix, &usr, &aux11);
				//sprintff(aux1,"%s %s",usr,aux11);
				gdk_threads_enter();
				message_text(0, aux11);
				gdk_threads_leave();

				free(aux11);
				free(prefix);
				free(usr);
				aux11=NULL;
				usr=NULL;
				prefix=NULL;
			break;
			case ERR_USERSDONTMATCH:
				printf("ERR_USERSDONTMATCH\n");
				IRCParse_ErrUsersDontMatch	(&buf[pos], &prefix, &usr, &aux11);
				//sprintff(aux1,"%s %s",usr,aux11);
				gdk_threads_enter();
				message_text(0, aux11);
				gdk_threads_leave();

				free(aux11);
				free(prefix);
				free(usr);
				aux11=NULL;
				usr=NULL;
				prefix=NULL;
			break;
			case RPL_YOURESERVICE:
				printf("RPL_YOURESERVICE\n");
				IRCParse_RplYoureService(&buf[pos], &prefix, &usr, &aux11,&aux12);
				//sprintff(aux1,"%s %s %s",usr, aux11,aux12);
				gdk_threads_enter();
				message_text(0,aux11);
				gdk_threads_leave();
			break;
			case RPL_YOURHOST:
				printf("RPL_YOURHOST\n");
				IRCParse_RplYourHost(&buf[pos],&prefix, &usr,&aux11,&aux12,&aux13);
				//sprintff(aux1,"%s %s %s %s",usr,aux11,aux12,aux13);
				aux1=(char*)malloc(sizeof(char)*(strlen(aux11)+strlen(aux12)+strlen(aux13)+2));
				sprintf(aux1,"%s %s %s",aux11,aux12,aux13);
				gdk_threads_enter();
				message_text(0, aux13);
				gdk_threads_leave();

				free(aux11);
				free(aux12);
				free(aux13);
				free(prefix);
				free(usr);
				aux11=NULL;
				aux12=NULL;
				aux13=NULL;
				usr=NULL;
				prefix=NULL;
				free(aux1);
				aux1=NULL;
			break;
			case RPL_MYINFO:
				printf("RPL_MYINFO\n");
				IRCParse_RplMyInfo(&buf[pos], &prefix, &usr, &buf2,&aux11,&aux12,&aux13,&aux14);
				//sprintf(aux1,"%s %s %s %s %s %s ",usr,buf2,aux11,aux12,aux13,aux14);
				gdk_threads_enter();
				message_text(0, aux14);
				gdk_threads_leave();
			break;
			case ERR_NOPRIVILEGES:
				printf("ERR_NOPRIVILEGES\n");
				IRCParse_ErrNoPrivileges(&buf[pos], &prefix, &usr, &aux11);
				//sprintff(aux1,"%s %s",usr,aux11);
				gdk_threads_enter();
				message_text(0, aux11);
				gdk_threads_leave();
			break;
			case ERR_NOSUCHSERVER:
				printf("ERR_NOSUCHSERVER\n");
				IRCParse_ErrNoSuchServer(&buf[pos], &prefix, &usr, &aux11, &aux12);
				//sprintff(aux1,"%s %s %s",usr,aux11,aux12);
				gdk_threads_enter();
				message_text(0, aux11);
				gdk_threads_leave();				

			break;
			case RPL_ENDOFWHO:
				printf("RPL_ENDOFWHO\n");
				//IRCParse_RplEndOfWho(char *strin, char **prefix, char **nick, char **name, char **msg);
				/*TODO*/
			break;
			case RPL_ENDOFWHOIS:
				printf("RPL_ENDOFWHOIS\n");
			break;
			case RPL_ENDOFWHOWAS:
				printf("RPL_ENDOFWHOWAS\n");
			break;
			case ERR_WASNOSUCHNICK:
				printf("ERR_WASNOSUCHNICK\n");
				error_text(current_page(), "No es un nick");
			break;
			case RPL_WHOWASUSER:
				printf("RPL_WHOWASUSER\n");
			break;
			case RPL_WHOISUSER:
				printf("RPL_WHOISUSER\n");
			break;
			case RPL_WHOISCHANNELS:
				printf("RPL_WHOISCHANNELS\n");
			break;
			case RPL_WHOISOPERATOR:
				printf("RPL_WHOISOPERATOR\n");
			break;
			case RPL_WHOISSERVER:
				printf("RPL_WHOISSERVER\n");
			break;
			case RPL_WHOISIDLE:
				printf("RPL_WHOISIDLE\n");
			break;
			case RPL_WHOREPLY:
				printf("RPL_WHOREPLY\n");
			break;
			case ERR_BADMASK:
				printf("ERR_BADMASK\n");
			break;
			case ERR_CANNOTSENDTOCHAN:
				printf("ERR_CANNOTSENDTOCHAN\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case ERR_NOTEXTTOSEND:
				printf("ERR_NOTEXTTOSEND\n");
				error_text(current_page(), "sin texto");
			break;
			case ERR_NOTOPLEVEL:
				printf("ERR_NOTOPLEVEL\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case ERR_WILDTOPLEVEL:
				printf("ERR_WILDTOPLEVEL\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case ERR_BADCHANMASK:
				printf("ERR_BADCHANMASK\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case ERR_BADCHANNELKEY:
				printf("ERR_BADCHANNELKEY\n");
				error_text(current_page(), "key erronea");
			break;
			case RPL_BANLIST:
				printf("RPL_BANLIST\n");
			break;
			case ERR_BANNEDFROMCHAN:
				printf("ERR_BANNEDFROMCHAN\n");
				error_text(current_page(), "Has sido baneado de ese chat");
			break;
			case ERR_CHANNELISFULL:
				printf("ERR_CHANNELISFULL\n");
				error_text(current_page(), "El canal esta lleno");
			break;
			case RPL_CHANNELMODEIS:
				printf("RPL_CHANNELMODEIS\n");
			break;
			case ERR_CHANOPRIVSNEEDED:
				printf("ERR_CHANOPRIVSNEEDED\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case RPL_ENDOFBANLIST:
				printf("RPL_ENDOFBANLIST\n");
			break;
			case RPL_ENDOFEXCEPTLIST:
				printf("RPL_ENDOFEXCEPTLIST\n");
			break;
			case RPL_ENDOFINVITELIST:
				printf("RPL_ENDOFINVITELIST\n");
			break;
			case RPL_ENDOFNAMES:
				printf("RPL_ENDOFNAMES\n");
			break;
			case RPL_EXCEPTLIST:
				printf("RPL_EXCEPTLIST\n");
			break;
			case RPL_INVITELIST:
				printf("RPL_INVITELIST\n");
			break;
			case ERR_INVITEONLYCHAN:
				printf("ERR_INVITEONLYCHAN\n");
				error_text(current_page(), "Necesitas invitacion");
			break;
			case RPL_INVITING:
				printf("RPL_INVITING\n");
			break;
			case ERR_KEYSET:
				printf("ERR_KEYSET\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case RPL_LISTSTART:
				printf("RPL_LISTSTART\n");
			break;
			case RPL_LIST:
				IRCParse_RplList(&buf[pos], &aux11, &aux12, &ch, &aux13,&buf2);
				printf("%s %s %s\n",ch, aux13, buf2 );
				if(ch!=NULL&&aux13!=NULL&&buf2==NULL){
					printf("caso1\n");
					aux1=(char*)malloc(sizeof(char)*(strlen(ch)+strlen(aux13)+3));
					sprintf(aux1,"%s %s",ch, aux13);
				}
				else if( ch!=NULL&&aux13!=NULL&&buf2!=NULL){
					printf("caso2\n");
					aux1=(char*)malloc(sizeof(char)*(strlen(ch)+strlen(aux13)+strlen(buf2)+5));
					sprintf(aux1,"%s %s %s",ch, aux13,buf2);
				}
				else{
					free(ch);
					free(aux13);
					free(buf2);
					ch=NULL;
					aux13=NULL;
					buf2=NULL;	
					break;
				}
				gdk_threads_enter();
				printf("%s\n",aux1 );
				message_text(0,aux1);
				gdk_threads_leave();			
				free(ch);
				free(aux13);
				free(buf2);
				ch=NULL;
				aux13=NULL;
				buf2=NULL;
				free(aux1);
				aux1=NULL;	
				break;
			case RPL_LISTEND:
				printf("RPL_LISTEND\n");
			break;
			case RPL_NAMREPLY:
				printf("RPL_NAMREPLY\n");
			break;
			case ERR_NOCHANMODES:
				printf("ERR_NOCHANMODES\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case ERR_NOSUCHCHANNEL:
				printf("ERR_NOSUCHCHANNEL\n");
				IRCParse_ErrNoSuchChannel(&buf[pos], &aux11, &usr, &ch, &buf2);
				gdk_threads_enter();
				error_text(0, buf2);
				gdk_threads_leave();
			break;
			case ERR_NOTONCHANNEL:
				printf("ERR_NOTONCHANNEL\n");
				error_text(current_page(), "No estas en el canal");
			break;
			case RPL_NOTOPIC:
				printf("RPL_NOTOPIC\n");
			break;
			case ERR_TOOMANYCHANNELS:
				printf("ERR_TOOMANYCHANNELS\n");
				error_text(current_page(), "Demasiados canales");
			break;
			case ERR_TOOMANYTARGETS:
				printf("ERR_TOOMANYTARGETS\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case ERR_UNKNOWNMODE:
				printf("ERR_UNKNOWNMODE\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case ERR_USERNOTINCHANNEL:
				printf("ERR_USERNOTINCHANNEL\n");
				error_text(current_page(), "Usuario no en el canal");
			break;
			case ERR_USERONCHANNEL:
				printf("ERR_USERONCHANNEL\n");
				error_text(current_page(), "Usuario en canal");
			break;
			case RPL_UNIQOPIS:
				printf("RPL_UNIQOPIS\n");
			break;
			case RPL_TOPIC:
				printf("RPL_TOPIC\n");
			break;
			case RPL_ADMINME:
				printf("RPL_ADMINME\n");
			break;
			case RPL_ADMINLOC1:
				printf("RPL_ADMINLOC1\n");
			break;
			case RPL_ADMINLOC2:
				printf("RPL_ADMINLOC2\n");
			break;
			case RPL_ADMINEMAIL:
				printf("RPL_ADMINEMAIL\n");
			break;
			case RPL_INFO:
				printf("RPL_INFO\n");
			break;
			case RPL_ENDOFLINKS:
				printf("RPL_ENDOFLINKS\n");
			break;
			case RPL_ENDOFINFO:
				printf("RPL_ENDOFINFO\n");
			break;
			case RPL_ENDOFMOTD:
				printf("RPL_ENDOFMOTD\n");
				IRCParse_RplEndOfMotd(buf, &prefix, &usr, &aux11);
			
				gdk_threads_enter();
				message_text(0,aux11);
				gdk_threads_leave();
			break;
			case RPL_ENDOFSTATS:
				printf("RPL_ENDOFSTATS\n");
			break;
			case RPL_LINKS:
				printf("RPL_LINKS\n");
			break;
			case RPL_LUSERCHANNELS:
				printf("RPL_LUSERCHANNELS\n");
			break;
			case RPL_LUSERCLIENT:
				printf("RPL_LUSERCLIENT\n");
			break;
			case RPL_LUSERME:
				printf("RPL_LUSERME\n");
			break;
			case RPL_LUSEROP:
				printf("RPL_LUSEROP\n");
			break;
			/*case RPL_LUSERUNKOWN:
				printf("RPL_LUSERUNKOWN\n");
			break;*/
			case RPL_MOTD:
				printf("RPL_MOTD\n");
				IRCParse_RplMotd(&buf[pos],&prefix,&aux11,&aux12);
				gdk_threads_enter();
				message_text(0,aux12);
				gdk_threads_leave();
			break;
			case RPL_MOTDSTART:
				printf("RPL_MOTDSTART\n");
				IRCParse_RplMotdStart(&buf[pos], &prefix, &usr, &aux11, &aux12);
				if(strcmp(usr, nickName)==0){
					gdk_threads_enter();
					message_text(0,aux11);
					gdk_threads_leave();
				}
			break;
			case ERR_NOMOTD:
				printf("ERR_NOMOTD\n");
				error_text(current_page(), "No hay mensaje del dia");
			break;
			case RPL_STATSCOMMANDS:
				printf("RPL_STATSCOMMANDS\n");
			break;
			case RPL_STATSLINKINFO:
				printf("RPL_STATSLINKINFO\n");
			break;
			case RPL_STATSOLINE:
				printf("RPL_STATSOLINE\n");
			break;
			case RPL_STATSUPTIME:
				printf("RPL_STATSUPTIME\n");
			break;
			case RPL_TIME:
				printf("RPL_TIME\n");
			break;
			case RPL_TRACECLASS:
				printf("RPL_TRACECLASS\n");
			break;
			case RPL_TRACECONNECT:
				printf("RPL_TRACECONNECT\n");
			break;
			case RPL_TRACECONNECTING:
				printf("RPL_TRACECONNECTING\n");
			break;
			case RPL_TRACEHANDSHAKE:
				printf("RPL_TRACEHANDSHAKE\n");
			break;
			case RPL_TRACELINK:
				printf("RPL_TRACELINK\n");
			break;
			case RPL_TRACENEWTYPE:
				printf("RPL_TRACENEWTYPE\n");
			break;
			case RPL_TRACEOPERATOR:
				printf("RPL_TRACEOPERATOR\n");
			break;
			case RPL_TRACESERVER:
				printf("RPL_TRACESERVER\n");
			break;
			case RPL_TRACESERVICE:
				printf("RPL_TRACESERVICE\n");
			break;
			case RPL_TRACEUSER:
				printf("RPL_TRACEUSER\n");
			break;
			case RPL_TRACEUNKNOWN:
				printf("RPL_TRACEUNKNOWN\n");
			break;
			case RPL_TRACELOG:
				printf("RPL_TRACELOG\n");
			break;
			case RPL_TRACEEND:
				printf("RPL_TRACEEND\n");
			break;
			case RPL_VERSION:
				printf("RPL_VERSION\n");
			break;
			case ERR_NOSUCHSERVICE:
				printf("ERR_NOSUCHSERVICE\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case RPL_SERVLIST:
				printf("RPL_SERVLIST\n");
			break;
			case RPL_SERVLISTEND:
				printf("RPL_SERVLISTEND\n");
			break;
			case ERR_CANTKILLSERVER:
				printf("ERR_CANTKILLSERVER\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case ERR_NOORIGIN:
				printf("ERR_NOORIGIN\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case RPL_ENDOFUSERS:
				printf("RPL_ENDOFUSERS\n");
			break;
			case ERR_FILEERROR:
				printf("ERR_FILEERROR\n");
				error_text(current_page(), "Error en el archivo");
			break;
			case RPL_ISON:
				printf("RPL_ISON\n");
			break;
			case ERR_NOLOGIN:
				printf("ERR_NOLOGIN\n");
				error_text(current_page(), "No login");
			break;
			case RPL_NOUSERS:
				printf("RPL_NOUSERS\n");
			break;
			case RPL_NOWAWAY:
				printf("RPL_NOWAWAY\n");
			break;
			case RPL_REHASHING:
				printf("RPL_REHASHING\n");
			break;
			case ERR_SUMMONDISABLED:
				printf("ERR_SUMMONDISABLED\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case RPL_SUMMONING:
				printf("RPL_SUMMONING\n");
			break;
			case RPL_UNAWAY:
				printf("RPL_UNAWAY\n");
			break;
			case RPL_USERHOST:
				printf("RPL_USERHOST\n");
			break;
			case RPL_USERS:
				printf("RPL_USERS\n");
			break;
			case ERR_USERSDISABLED:
				printf("ERR_USERSDISABLED\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case RPL_USERSSTART:
				printf("RPL_USERSSTART\n");
			break;
			case RPL_AWAY:
				printf("RPL_AWAY\n");
			break;
			case ERR_NOSUCHNICK:
				printf("ERR_NOSUCHNICK\n");
				error_text(current_page(), "No es un nick");
			break;
			case RPL_WELCOME:
				/*printf("RPL_WELCOME\n");
				IRCParse_RplWellcome(buf, &prefix, &aux[0], &aux[1]);
				printf("asda\n");
				message_text(current_page(), aux[0]);
				message_text(current_page(), aux[1]);*/
			break;
			case RPL_CREATED:
				printf("RPL_CREATED\n");
			break;
			case RPL_BOUNCE:
				printf("RPL_BOUNCE\n");
			break;
			case RPL_TRYAGAIN:
				printf("RPL_TRYAGAIN\n");
			break;
			case ERR_UNKNOWNCOMMAND:
				printf("ERR_UNKNOWNCOMMAND\n");
				error_text(current_page(), "Comando desconocido");				
			break;
			case ERR_NOADMININFO:
				printf("ERR_NOADMININFO\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case ERR_NOTREGISTERED:
				printf("ERR_NOTREGISTERED\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case ERR_NOPERMFORHOST:
				printf("ERR_NOPERMFORHOST\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case ERR_YOUREBANNEDCREEP:
				printf("ERR_YOUREBANNEDCREEP\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case ERR_YOUWILLBEBANNED:
				printf("ERR_YOUWILLBEBANNED\n");
				error_text(current_page(), "Seras baneado");	
			break;
			case ERR_BANLISTFULL:
				printf("ERR_BANLISTFULL\n");
				error_text(current_page(), "Lista de baneados llena");	
			break;
			case ERR_UNIQOPPRIVSNEEDED:
				printf("ERR_UNIQOPPRIVSNEEDED\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case ERR_NORECIPIENT:
				printf("ERR_NORECIPIENT\n");
				/*TODO error_text(current_page(), "");*/
			break;
			case ERR_TOOMANYMATCHES:
				printf("ERR_TOOMANYMATCHES\n");
				error_text(current_page(), "Muchas coincidencias");	
			break;
			case RPL_YOURID:
				printf("RPL_YOURID\n");
				IRCParse_RplYourId (&buf[pos], &prefix, &usr, &aux11, &aux12);
				//sprintff(aux1,"%s %s %s",usr, aux11, aux12);
				gdk_threads_enter();
				message_text(0, aux11);
				gdk_threads_leave();
			break;
			case RPL_CREATIONTIME:
				printf("RPL_CREATIONTIME\n");
				IRCParse_RplCreationTime (&buf[pos], &prefix, &usr, &ch, &t);
				//sprintff(aux1,"%s %s %d ",usr,ch,t);
				gdk_threads_enter();
				//message_text(0, aux1);
				gdk_threads_leave();
			break;
			case RPL_LOCALUSERS:
				printf("RPL_LOCALUSERS\n");
				IRCParse_RplLocalUsers (&buf[pos], &prefix, &usr, &aux11, &t2,&t);
				//sprintf(aux1,"LOCAL USERS total=%d maximum=%d",t2,t);
				gdk_threads_enter();
				message_text(0,aux11);
				gdk_threads_leave();
			break;
			case RPL_GLOBALUSERS:
				printf("RPL_GLOBALUSERS\n");
				IRCParse_RplGlobalUsers (&buf[pos], &prefix, &usr,&aux11, &t,&t2);
				//sprintf(aux1,"GLOBAL USERS total=%d maximum=%d", t,t2);
				gdk_threads_enter();
				message_text(0,aux11);
				gdk_threads_leave();
			break;
			case RPL_TOPICWHOTIME:
				printf("RPL_TOPICWHOTIME\n");
				IRCParse_RplTopicWhoTime (&buf[pos], &prefix, &usr, &ch, &aux11,&t);
				//sprintff(aux1,"%s %s %s %d",usr, ch, aux11,t);
				gdk_threads_enter();
				message_text(0,aux11);
				gdk_threads_leave();

			break;
			case RPL_CHANNELURL:
				printf("RPL_CHANNELURL\n");
				IRCParse_RplChannelUrl (&buf[pos], &prefix, &usr, &ch, &aux11);
				//sprintff(aux1,"%s %s %s",usr,ch,aux11);
				gdk_threads_enter();
				message_text(0,aux11);
				gdk_threads_leave();
			break;
			default:/*TODO*/
			printf("DEFAULT\n");
			/*IRCParse_GeneralCommand(char *strin, char **prefix, char **type, char ***parameters, int *numparame-
			ters, char **msg)
				printf("DEFAULT\n");
				IRCParse_Motd(buf, &prefix, &aux[0]);

				message_text(current_page(),aux[0]);*/
			break;

		}
		printf("libera\n");
		
		/*if(aux1!=NULL)
			printf("1aux1 long=%d\n",strlen(aux1));
		free(aux2);
		free(aux3);
		free(aux11);
		free(aux12);
		free(aux13);
		free(aux14);
		free(usr);
		free(prefix);
		free(ch);
		free(buf2);
		
		free(pch);
		if(aux1!=NULL)
			printf("2aux1 long=%d\n",strlen(aux1));
		aux14=NULL;
		usr=NULL;
		prefix=NULL;
		ch=NULL;
		pch=NULL;
		buf2=NULL;
		aux1=NULL;
		aux2=NULL;
		aux3=NULL;
		aux11=NULL;
		aux12=NULL;
		aux13=NULL;
		if(aux1!=NULL)
			printf("3aux1 long=%d\n",strlen(aux1));
		printf("liberado\n");
		if(aux1!=NULL){
			free(aux1);
			printf("aux1 liberado\n");
		}
		aux1=NULL;*/
		if(pipe==1){
			printf("\t\t piped command\n");
			aux11=NULL;
			/*
			Hace mucho tiempo en un lugar muy lejano dos grandes forjadores de lenguajes
			crearon un sitema de prgramacion que duro eras, fue en ese lenguaje cuando se 
			implementaron funciones legendarias para el lenguaje primeramente basado 
			en ensamblador y que en un futuro miles de lenguajes adoptarian sus reglas.

			De entre todas las grandes funciones del lenguaje implementadas en aquellos 
			años muchas fueron empuñadas por grandes programadores.

			De entre esas funciones una paso desapercibida, muchos dudaban de su valia en
			el campo de la prgramacion abstracta algunos la llegaron a despreciar 
			y con los años cayo en el olvido, se nego a los nuevos programadores su existencia
			y su uso fue casi prohibido	para los que la conocian.

			Tras muchos años un joven grupo de programadores descubrio su existencia, al principio
			sus maestros les desinformaron y les dijeron que su uso era inutil que no volverian a verla
			escrita en ningun lugar.

			La mayoria obedecio a sus maestros y no la uso, pero unos pocos desobedecieron y decidieron ver su uso. 
			En un principio les parecio algo inocente, con la misma utilidad que un bucle, incluso menor
			pero poco a poco la funcion les empezo a corremper por dentro y les parecio cada vez mas util.

			Descubrieron que en efecto se podia usar en un bucle pero ese bucle podia ser mas flexible que los demas
			entonces empuñando al funcion goto su poder supero ampliamente lo que nadie pudo suponer y con ella 
			dominaron su escuela, despues su ciudad, su pais y finalmente el mundo.

			Fueron largos años de una epoca oscura en la que los goto sustituyeron a todos los bucles
			pero entonces llegaron nuevos lenguajes de programacion y el gran lenguaje C se empezo a olvidar.
			Las historias se convirtiron en leyendas, las leyendas en mitos y los mitos se llegaron a olvidar.

			Pero un dia algo que no esta esperado que ocurriese ocurrio, algunos programadores encontraron el lenguaje C y
			lo volvieron a poner en uso y  entre largas horas de descubrimentos en los largos libros de C encontaron una funcion,
			esta funcion era la legenedaria funcion goto.
			*/

			goto unpipe;
		}
			
	}
	
}
