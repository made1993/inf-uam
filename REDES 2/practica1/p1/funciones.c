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
/*anadido return -1*/
int abrirSocketTCP(){
	int sockfd=socket(AF_INET,SOCK_STREAM,IPPROTO_TCP);
	if(sockfd < 0){
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
		return -1;
	}
	return sockfd;

}
/*anadido return -1*/
int abrirSocketUDP(){
	int sockfd=socket(AF_INET,SOCK_DGRAM,IPPROTO_UDP);
	if(sockfd < 0){
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
		return -1;
	}
	return sockfd;

}

/*anadido bzero como en las traspas de teoria*/
int abrirBind(int sockfd){
	struct sockaddr_in ip4addr;
	int aux;
	ip4addr.sin_family = AF_INET;
	ip4addr.sin_port = htons(2020);
	ip4addr.sin_addr.s_addr=INADDR_ANY;
	//bzero((void  *)&(ip4addr.sin_zero), 8);
	if(bind(sockfd, (struct sockaddr*)&ip4addr, sizeof(ip4addr))==-1){
		switch(errno){
			case EACCES:
				printf("The address is protected, and the user is not the superuser.\n");
				break;
	       case EADDRINUSE:
	            printf("The given address is already in use.\n");
	            break;
	       case EBADF:  
	       		printf("sockfd is not a valid descriptor.\n");
	       		break;
	       case EINVAL: 
	       		printf("The socket is already bound to an address.\n");
	       		break;
	       case ENOTSOCK:
	            printf("sockfd is a descriptor for a file, not a socket. The following errors are specific to UNIX domain (AF_UNIX) sockets:\n");
	       		break;
	       /*case EACCES:
	       		printf("Search permission is denied on a component of the path prefix.  (See also path_resolution(7).)\n");
	       		break;*/
	       case EADDRNOTAVAIL:
	            printf("A nonexistent interface was requested or the requested address was not local.\n");
	            break;
	       case EFAULT :
	       		printf("addr points outside the user's accessible address space.\n");
	       		break;
	       /*case EINVAL: 
	       		printf("The addrlen is wrong, or the socket was not in the AF_UNIX family.\n");
	       		break;*/
	       case ELOOP:  
	       		printf("Too many symbolic links were encountered in resolving addr.\n");
				break;
	       case ENAMETOOLONG:
	            printf("addr is too long.\n");
	            break;
	       case	ENOENT :
	       		printf("The file does not exist.\n");
	       		break;
	       case ENOMEM: 
	       		printf("Insufficient kernel memory was available.\n");
	       		break;
	       case ENOTDIR:
	              printf("A component of the path prefix is not a directory.\n");
	              break;
	       case EROFS:  
	       		printf("The socket inode would reside on a read-only filesystem.\n");
				break;
		}
		return -1;
	}
	return 0;
}

/*anadido MAX_CONNECTIONS
 TODO: definir su valor*/
int abrirListen(int sockfd){
	int lstn;
	lstn = listen(sockfd, 3);
	if( lstn == -1){
		printf("Error al conectar\n");
		
		switch(errno){
			case EADDRINUSE:
            	printf("Another socket is already listening on the same port.");
            	break;

			case EBADF:
				printf("The argument sockfd is not a valid descriptor.");
            	break;

			case ENOTSOCK:
            	printf("The argument sockfd is not a socket.");
            	break;

			case EOPNOTSUPP:
            	printf("The socket is not of a type that supports the listen() operation.");
            	break;
		}
		
		return -1;
	}
	return 0;
}

int aceptar(int sockfd, struct sockaddr_in ip4addr){
	int acc=0;
	int addrlen =sizeof(ip4addr);
	acc = accept(sockfd, (struct sockaddr*)&ip4addr, (socklen_t*)&addrlen);
	
	if(acc == -1){
		switch(errno){
	
			case EAGAIN:
		        printf("The socket is marked nonblocking and no connections are present to be accepted.");
		        break;
		        
		    /*case EWOULDBLOCK:
		    	printf("The socket is marked nonblocking and no connections are present to be accepted.");
		        break;
				*/
			case EBADF: 
				printf("The descriptor is invalid.");
		        break;

			case ECONNABORTED:
				printf("A connection has been aborted.");
		        break;

			case EFAULT: 
				printf("The addr argument is not in a writable part of the user address space.");
		        break;

			case EINTR: 
				printf("The system call was interrupted by a signal that was caught before a valid connection arrived");
		        break;

			case EINVAL: 
				printf("Socket is not listening for connections, or addrlen is invalid");
		        break;

			case EMFILE: 
				printf("The per-process limit of open file descriptors has been reached.");
		        break;

			case ENFILE: 
				printf("The system limit on the total number of open files has been reached.");
		        break;

			case ENOBUFS:
				printf("Not enough free memory.  This often means that the memory allocation is limited by the socket buffer limits, not by the system memory.");
		        break;
		
			case ENOMEM:
				printf("Not enough free memory.  This often means that the memory allocation is limited by the socket buffer limits, not by the system memory.");
		        break;
		
			case ENOTSOCK: 
				printf("The descriptor references a file, not a socket.");
		        break;

			case EOPNOTSUPP: 
				printf("The referenced socket is not of type SOCK_STREAM.");
		        break;

			case EPROTO: 
				printf("Protocol error.");
		        break;

			case EPERM: 
				printf("Firewall rules forbid connection.");
		        break;
		}
		return -1;
	}
	return acc;
}
int abrirConnect(int sockfd, struct sockaddr res){
	int cnct=0;
	printf("cosas\n");
	cnct= connect(sockfd, &res, sizeof(res));
	printf("cosas\n");
	if (cnct==-1){
		switch(errno){
			case EACCES:
		    	printf("For UNIX domain sockets, which are identified by pathname: Write permission is denied on the socket file, or search permission is denied for one of the directories in the path prefix. (See also path_resolution(7).) \n");
				printf("or The user tried to connect to a broadcast address without having the socket broadcast flag enabled or the connection request failed because of a local firewall rule.\n" );
						 		
				break;
		 	
		 	case EPERM:
		    		printf("The user tried to connect to a broadcast address without having the socket broadcast flag enabled or the connection request failed because of a local firewall rule.\n"); 
				break;
			case EADDRINUSE:
		    	printf("Local address is already in use. \n");
				break;
			case EAFNOSUPPORT:
		    	printf("The passed address didn't have the correct address family in its sa_family field. \n");
				break;
			case EAGAIN:
			    printf("No more free local ports or insufficient entries in the routing cache. For AF_INET see the description of /proc/sys/net/ipv4/ip_local_port_range ip(7) for information on how to increase the number of local ports. \n");
				break;
			case EALREADY:
			    printf("The socket is nonblocking and a previous connection attempt has not yet been completed.\n"); 
				break;
			case EBADF:
			    printf("The file descriptor is not a valid index in the descriptor table.\n"); 
				break;
			case ECONNREFUSED:
			    printf("No-one listening on the remote address. \n");
				break;
			case EFAULT:
			    printf("The socket structure address is outside the user's address space. \n");
				break;
			case EINPROGRESS:
			    printf("The socket is nonblocking and the connection cannot be completed immediately. It is possible to select(2) or poll(2) for completion by selecting the socket for writing. After select(2) indicates writability, use getsockopt(2) to read the SO_ERROR option at level SOL_SOCKET to determine whether connect() completed successfully (SO_ERROR is zero) or unsuccessfully (SO_ERROR is one of the usual error codes listed here, explaining the reason for the failure). \n"); 
				break;
			case EINTR:
			    printf("The system call was interrupted by a signal that was caught; see signal(7).\n"); 
				break;
			case EISCONN:
			    printf("The socket is already connected.\n"); 
				break;
			case ENETUNREACH:
			    printf("Network is unreachable. \n");
				break;
			case ENOTSOCK:
			    printf("The file descriptor is not associated with a socket.\n"); 
				break;
			case ETIMEDOUT:
			    printf("Timeout while attempting connection. The server may be too busy to accept new connections. Note that for IP sockets the timeout may be very long when syncookies are enabled on the server.\n");
			    break;
		}
		return -1;
	}
	return 0;
}

int recibir(int sockfd,char *buf){
	int aux=0;
	aux = recv(sockfd, buf, 1000, 0);
	buf[aux]='\0';
	if (aux==-1){
		switch(errno){
			case (EAGAIN || EWOULDBLOCK):
              printf ("The socket is marked nonblocking and the receive operation would block, or a receive timeout had been set and the timeout" 
              		  "expired before data was received.  POSIX.1-2001 allows either  error  to"
		              "be  returned for this case, and does not require these constants to have the same value, so a portable application  should  check"
		              "for both possibilities.\n");
			break;

	       	case EBADF:  
	       		printf ("The argument sockfd is an invalid descriptor.\n");
			break;
	       	case ECONNREFUSED:
	              printf ("A remote host refused to allow the network connection (typically because it is not running the requested service).\n");
			break;
	       	case EFAULT: 
	       		printf("The  receive  buffer  pointer(s)  point  outside  the  process's address space.\n");
			break;
	       	case EINTR:  
	       		printf("The  receive  was interrupted by delivery of a signal before any data were available; see signal(7).\n");
			break;
	       	case EINVAL: 
	       		printf("Invalid argument passed.\n");
			break;
	       	case ENOMEM: 
	       		printf("Could not allocate memory for recvmsg().\n");
			break;
	       	case ENOTCONN:
	            printf("The socket is associated with a connection-oriented protocol and has not been connected (see connect(2) and accept(2)).\n");
			break;
	       	case ENOTSOCK:
	            printf("The argument sockfd does not refer to a socket.\n");
			break;
		}
		return -1;
	}
	return aux;
}
int escribir(int sockfd,char *msg){
	int aux=send(sockfd,msg,strlen(msg),0);
	if(-1==aux){
		switch(errno){
		 	case EACCES: 
				printf("(For UNIX domain sockets, which are identified by pathname) Write permission is denied on the destination socket file, or search permission  is"
			              "denied for one of the directories the path prefix.  (See path_resolution(7).)"

			              "(For UDP sockets) An attempt was made to send to a network/broadcast address as though it was a unicast address.\n");
				break;

	       case (EAGAIN || EWOULDBLOCK):
	             printf(" The  socket  is marked nonblocking and the requested operation would block.  POSIX.1-2001 allows either error to be returned for this case, and"
	              "does not require these constants to have the same value, so a portable application should check for both possibilities.\n");
	            break;
	       case EBADF:
	         	printf("An invalid descriptor was specified.\n");
	         	break;
	       case ECONNRESET:
	            printf("Connection reset by peer.\n");
	            break;
	       case EDESTADDRREQ:
	            printf("The socket is not connection-mode, and no peer address is set.\n");
	            break;
	       case EFAULT:
	        	printf("An invalid user space address was specified for an argument.\n");
	        	break;
	       case EINTR:
	         	printf("A signal occurred before any data was transmitted; see signal(7).\n");
	         	break;
	       case EINVAL: 
	       		printf("Invalid argument passed.\n");
	       		break;
	       case EISCONN:
	            printf("The connection-mode socket was connected already but a recipient was specified.  (Now either this error is returned, or the recipient  specifi‐"
	              "cation is ignored.)\n");
	            break;
	       case EMSGSIZE:
	            printf("The socket type requires that message be sent atomically, and the size of the message to be sent made this impossible.\n");
	            break;
	       case ENOBUFS:
	            printf("The output queue for a network interface was full.  This generally indicates that the interface has stopped sending, but may be caused by tran‐"
	            "sient congestion.  (Normally, this does not occur in Linux.  Packets are just silently dropped when a device queue overflows.)\n");
	            break;
	       case ENOMEM: 
	       		printf("No memory available.\n");
	       		break;
	       case ENOTCONN:
	            printf("The socket is not connected, and no target has been given.\n");
	        case ENOTSOCK:
	            printf("The argument sockfd is not a socket.\n");
	            break;
	       case EOPNOTSUPP:
	            printf("Some bit in the flags argument is inappropriate for the socket type.\n");
	            break;
	       case EPIPE:
	         	printf("The local end has been shut down on a connection oriented socket.  In this case the process will also receive a SIGPIPE unless MSG_NOSIGNAL  is set.\n");
	         	break;

		}
		return -1;
	}            
	return aux;  
}
int conexionPruebaCliente(char * direccion,char * puerto){
	struct addrinfo hints, *res;
	int sockfd, temp, sock_server;
	char msg[1000];
    memset(&hints, 0, sizeof hints);
	hints.ai_family = AF_UNSPEC;  // use IPv4 or IPv6, whichever
	hints.ai_socktype = SOCK_STREAM;
	printf("Abriendo socket\n");
	getaddrinfo("172.16.187.180", "2020", &hints, &res);
	sockfd = abrirSocketTCP();
	if (sockfd==-1){
		printf("ERROR: socket\n");
		return -1;
	}
	
	printf("Conectando\n");
	if(-1==(sock_server==abrirConnect(sockfd,*(res->ai_addr)))){
		printf("ERROR: connect\n");
		return -1;
	}
	
	printf("sock_server:%d, sockfd:%d\n", sock_server,sockfd);
	// convert to network byte order
	// send data normally:
	while (1){
		recibir(sockfd, msg);
		printf("Recibido:%s\n", msg);
		strcpy(msg,"PONG");
		sleep(2);
		//send(sockfd,msg,strlen(msg),0);
		escribir(sockfd, msg);	
	}
	return 0;
}


int conexionPruebaServidor(){
	int sockfd, socketClient, aux = 1;
	struct sockaddr_in ip4addr;
	char buf[1000];
	printf("Abriendo socket\n");
	sockfd = abrirSocketTCP();
	if (sockfd==-1){
		printf("ERROR: socket\n");
		return -1;
	}
	printf("Abriedo bind\n");
	if( -1==abrirBind(sockfd)){
		printf("ERROR: bind\n");
		return -1;	
	}
	printf("Escuchando\n");
	if(-1==abrirListen(sockfd)){
		printf("ERROR: listen\n");
		return -1;			
	}
	printf("Esperando accept\n");
	socketClient = aceptar(sockfd, ip4addr);
	if(socketClient ==-1){
		printf("ERROR: accept\n");
		return -1;	
	}

	printf("sock_client:%d, sockfd:%d\n", socketClient,sockfd);
	while( aux > 0){
		sleep(2);
		strcpy(buf,"PING");
		write(socketClient, buf , strlen(buf));
		aux=recv(socketClient, buf,1000,0);
		if(aux == 0){
			printf("fin de la conexion\n");
			return 0;
		}
		buf[aux]='\0';
		printf("Recibido:%s\n",buf );
	}
	//byte_count = recv(sockfd, buf, sizeof buf, 0);
	//printf("recv()'d %d bytes of data in buf\n", byte_count);
	
	return 0;
}
