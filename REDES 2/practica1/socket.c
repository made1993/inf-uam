#include <errno.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <redes2/chat.h>

/* posiblemente reparado
	actualizacion1: añadido errno.h y cambiado el switch con errno:
		segun man: "On  success, a file descriptor for the new socket is returned.  On error, -1 is returned, and errno is set appropriately."
*/


int abrirSocketTCP(){
	int sockfd=socket(AF_INET,SOCK_STREAM,0);
	switch(errno){
		case EACCES:
		 printf("Permission to create a socket of the specified type and/or  protocol is denied.\n");
		break;
       	case EAFNOSUPPORT:
              printf("The  implementation  does not support the specified address family.\n");
        break;
       	case EINVAL: 
       		printf("Unknown protocol, or protocol family not available. or Invalid flags in type.\n");
			 break;
       	case EMFILE: 
       		printf("Process file table overflow.\n");
       	break;
       	case ENFILE: 
       		printf("The system limit on the total number  of  open  files  has  been reached.\n");
    	break;
       	case ENOBUFS:
       		printf("Insufficient  memory is available.  The socket cannot be created until sufficient resources are freed.\n");
       	break; 
       	case ENOMEM:
            printf("Insufficient  memory is available.  The socket cannot be created until sufficient resources are freed.\n");
        break;
       	case EPROTONOSUPPORT:
              printf("The protocol type or the specified  protocol  is  not  supportedwithin this domain.\n" );
       break;

	}
	return sockfd;

}

int abrirSocketUDP(){
	int sockfd=socket(AF_INET,SOCK_DGRAM,0);
	printf("%d\n",sockfd );
	switch(errno){
		case EACCES:
		 printf("Permission to create a socket of the specified type and/or  protocol is denied.\n");
		break;
       	case EAFNOSUPPORT:
              printf("The  implementation  does not support the specified address family.\n");
        break;
       	case EINVAL: 
       		printf("Unknown protocol, or protocol family not available. or Invalid flags in type.\n");
			 break;
       	case EMFILE: 
       		printf("Process file table overflow.\n");
       	break;
       	case ENFILE: 
       		printf("The system limit on the total number  of  open  files  has  been reached.\n");
    	break;
       	case ENOBUFS:
       		printf("Insufficient  memory is available.  The socket cannot be created until sufficient resources are freed.\n");
       	break; 
       	case ENOMEM:
            printf("Insufficient  memory is available.  The socket cannot be created until sufficient resources are freed.\n");
        break;
       	case EPROTONOSUPPORT:
              printf("The protocol type or the specified  protocol  is  not  supportedwithin this domain.\n" );
       break;

	}
	return sockfd;

}

int abrirBind(int sockfd){
	struct sockaddr_in ip4addr;
	int aux;
	ip4addr.sin_family = AF_INET;
	ip4addr.sin_port = htons(NFC_SERVER_PORT);
  ip4addr.sin_addr.s_addr=htonl(INADDR_ANY);
	bind(sockfd, (struct sockaddr*)&ip4addr, sizeof ip4addr);
	aux=inet_ntoa(ip4addr);
	printf("%d  %d\n",aux,ip4addr.sin_port );
	return 1;
}
int main(int argc, char const *argv[])
{
	abrirBind(abrirSocketUDP());
  return 0;
}
