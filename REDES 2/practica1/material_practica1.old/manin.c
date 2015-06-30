 #include <stdio.h>
#include <stdlib.h>
#include <pcap.h>
#include <string.h>
#include <netinet/in.h>
#include <linux/udp.h>
#include <linux/tcp.h>
#include <signal.h>
#include <time.h>
#include <sys/ioctl.h>
#include <net/if.h>
#include <unistd.h>
#include <netinet/ether.h>
#include <netinet/if_ether.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <inttypes.h>
#include <math.h>

#define ETH_ALEN 6          // Tamano de direccion ethernet        
#define ETH_HLEN 14         // Tamano de cabecera ethernet            
#define ETH_TLEN 2      // Tamano del campo tipo ethernet         
#define ETH_FRAME_MAX 1514  // Tamano maximo trama ethernet    
#define ETH_PROTO 0         // Capa de enlace relativa al interface, le asignamos arbitrariamente el 0
 
#define IP_HLEN 20      // Tamano de la cabecera IP (+4 incluyendo Options y Padding)
#define IP_ALEN 4       // Tamano de direccion IP
#define IP_DATAGRAM_MAX 65536   // Tamano maximo datagrama IP
#define IP_PROTO 0x0800     // Identificador protocolo UDP 
 
#define UDP_HLEN 8          // Tamano de cabecera UDP  
#define UDP_SEG_MAX 65536   // Tamano maximo segmento UDP
#define UDP_PROTO 17        // Identificador protocolo UDP
 
#define ICMP_PROTO 1        // Identificador protocolo ICMP
#define ICMP_HLEN 8     // Tamano de cabecera ICMP
#define ICMP_DATAGRAM_MAX 48    // Tamano maximo ICMP (arbitrario, truncar si es necesario)
 
#define PING_TIPO 8     //Codigo y tipo para PINGs
#define PING_CODE 0     //
 
#define CADENAS 256
 
#define MAX_PROTOCOL 65536  //Numero maximo identificador protocolo IP
 
#define OK 0
#define ERROR 1
#define BUF_SIZE 500

uint8_t obtenerIPInterface(char * interface, uint8_t* retorno){
	int fd;
	struct ifreq ifr;
	fd = socket(AF_INET, SOCK_DGRAM, 0);
	if(fd<0) {
		printf("socket_ERROR\n");
		return 0;
	}
	ifr.ifr_addr.sa_family = AF_INET;
	strncpy(ifr.ifr_name, interface, IFNAMSIZ-1);
	if (ioctl(fd, SIOCGIFADDR, &ifr)<0){
		printf("IOCTL_ERROR\n");
		return 0;
	}
	close(fd);
	memcpy(retorno,&(*(struct sockaddr_in *)&ifr.ifr_addr).sin_addr,sizeof(uint8_t)*IP_ALEN);
	printf("Retorno obtenerIPInterface():\n");
	printf("\t%"PRIu8".%"PRIu8".%"PRIu8".%"PRIu8"\n",retorno[0],retorno[1],retorno[2],retorno[3]);
	return OK;
}

int main(){
	
	unsigned int i;

	obtenerIPInterface("wlan0", &i);
	
	printf("%u\n", i);
	return 0;
}
