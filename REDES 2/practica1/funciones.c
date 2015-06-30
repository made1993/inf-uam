#include <errno.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>


int abrirListen(int sockfd){
	int lstn;
	lstn = listen(sockfd, 3/*¿?*/);
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
	acc = accept(sockfd, (struct sockaddr*)&ip4addr, &addrlen);
	
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
int abrirConnect(int sockfd, struct sockaddr_in ip4addr){
	int cnct=0;
	int addrlen =sizeof(ip4addr);
	cnct= connect(sockfd, (struct sockaddr*)&ip4addr, addrlen);
	if (cnct==-1){
		switch(errno){
			case EACCES:
		    	printf("For UNIX domain sockets, which are identified by pathname: Write permission is denied on the socket file, or search permission is denied for one of the directories in the path prefix. (See also path_resolution(7).) \n");
		 		break;
		 	/*case EACCES:
		 		printf("The user tried to connect to a broadcast address without having the socket broadcast flag enabled or the connection request failed because of a local firewall rule.\n" );
				break;*/
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
}
int main(int argc, char const *argv[])
{
	/* code */
	return 0;
}