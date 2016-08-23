/***************************************************************************
 practica4.c
 Inicio, funciones auxiliares y modulos de transmision implmentados y a implementar de la practica 4.
Compila con warning pues falta usar variables y modificar funciones
 
 Compila: make
 Autor: Jose Luis Garcia Dorado
 2014 EPS-UAM v2
***************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
#include "interface.h"
#include "practica4.h"

/***************************Variables globales utiles*************************************************/
pcap_t* descr, *descr2; //Descriptores de la interface de red
pcap_dumper_t * pdumper;//y salida a pcap
uint64_t cont=0;	//Contador numero de mensajes enviados
char interface[10];	//Interface donde transmitir por ejemplo "eth0"
uint16_t ID=1;		//Identificador IP


void handleSignal(int nsignal){
	printf("Control C pulsado (%"PRIu64")\n", cont);
	pcap_close(descr);
	exit(OK);
}

int main(int argc, char **argv){	

	char errbuf[PCAP_ERRBUF_SIZE];
	char fichero_pcap_destino[CADENAS];
	uint8_t IP_destino_red[IP_ALEN];
	uint16_t MTU;
	uint16_t datalink;
	uint16_t puerto_destino;
	char data[IP_DATAGRAM_MAX];
	uint16_t pila_protocolos[CADENAS];


	int long_index=0;
	char opt;
	char flag_iface = 0, flag_ip = 0, flag_port = 0, flag_file = 0;

	static struct option options[] = {
		{"if",required_argument,0,'1'},
		{"ip",required_argument,0,'2'},
		{"pd",required_argument,0,'3'},
		{"f",required_argument,0,'4'},
		{"h",no_argument,0,'5'},
		{0,0,0,0}
	};

		//Dos opciones: leer de stdin o de fichero, adicionalmente para pruebas si no se introduce argumento se considera que el mensaje es "Payload "
	while ((opt = getopt_long_only(argc, argv,"1:2:3:4:5", options, &long_index )) != -1) {
		switch (opt) {

			case '1' :

				flag_iface = 1;
					//Por comodidad definimos interface como una variable global
				sprintf(interface,"%s",optarg);
				break;

			case '2' : 

				flag_ip = 1;
					//Leemos la IP a donde transmitir y la almacenamos en orden de red
				if (sscanf(optarg,"%"SCNu8".%"SCNu8".%"SCNu8".%"SCNu8"",
				                   &(IP_destino_red[0]),&(IP_destino_red[1]),&(IP_destino_red[2]),&(IP_destino_red[3])) != IP_ALEN){
					printf("Error: Fallo en la lectura IP destino %s\n", optarg);
					exit(ERROR);
				}

				break;

			case '3' :

				flag_port = 1;
					//Leemos el puerto a donde transmitir y la almacenamos en orden de hardware
				puerto_destino=atoi(optarg);
				break;

			case '4' :

				if(strcmp(optarg,"stdin")==0) {
					if (fgets(data, sizeof data, stdin)==NULL) {
						  	printf("Error leyendo desde stdin: %s %s %d.\n",errbuf,__FILE__,__LINE__);
						return ERROR;
					}
					sprintf(fichero_pcap_destino,"%s%s","stdin",".pcap");
				} else {
					sprintf(fichero_pcap_destino,"%s%s",optarg,".pcap");
					//TODO Leer fichero en data [...]
				}
				flag_file = 1;

				break;

			case '5' : printf("Ayuda. Ejecucion: %s -if interface -ip IP -pd Puerto <-f /ruta/fichero_a_transmitir o stdin>: %d\n",argv[0],argc); exit(ERROR);
				break;

			case '?' : printf("Error. Ejecucion: %s -if interface -ip IP -pd Puerto <-f /ruta/fichero_a_transmitir o stdin>: %d\n",argv[0],argc); exit(ERROR);
				break;

			default: printf("Error. Ejecucion: %s -if interface -ip IP -pd Puerto <-f /ruta/fichero_a_transmitir o stdin>: %d\n",argv[0],argc); exit(ERROR);
				break;
        }
    }

	if ((flag_iface == 0) || (flag_ip == 0) || (flag_port == 0)){
		printf("Error. Ejecucion: %s -if interface -ip IP -pd Puerto <-f /ruta/fichero_a_transmitir o stdin>: %d\n",argv[0],argc);
		exit(ERROR);
	} else {
		printf("Interface:\n\t%s\n",interface);
		printf("IP:\n\t%"PRIu8".%"PRIu8".%"PRIu8".%"PRIu8"\n",IP_destino_red[0],IP_destino_red[1],IP_destino_red[2],IP_destino_red[3]);
		printf("Puerto destino:\n\t%"PRIu16"\n",puerto_destino);
	}

	if (flag_file == 0) {
		sprintf(data,"%s","Payload "); //Deben ser pares!
		sprintf(fichero_pcap_destino,"%s%s","debugging",".pcap");
	}

	if(signal(SIGINT,handleSignal)==SIG_ERR){
		printf("Error: Fallo al capturar la senal SIGINT.\n");
		return ERROR;
	}
		//Inicializamos las tablas de protocolos
	if(inicializarPilaEnviar()==ERROR){
      	printf("Error leyendo desde stdin: %s %s %d.\n",errbuf,__FILE__,__LINE__);
		return ERROR;
	}
		//Leemos el tamano maximo de transmision del nivel de enlace
	if(obtenerMTUInterface(interface, &MTU)==ERROR)
		return ERROR;
		//Descriptor de la interface de red donde inyectar trafico
	if ((descr = pcap_open_live(interface,MTU+ETH_HLEN,0, 0, errbuf)) == NULL){
		printf("Error: pcap_open_live(): %s %s %d.\n",errbuf,__FILE__,__LINE__);
		return ERROR;
	}

	datalink=(uint16_t)pcap_datalink(descr); //DLT_EN10MB==Ethernet

		//Descriptor del fichero de salida pcap para debugging
	descr2=pcap_open_dead(datalink,MTU+ETH_HLEN);
	pdumper=pcap_dump_open(descr2,fichero_pcap_destino);

		//Formamos y enviamos el trafico, debe enviarse un unico segmento por llamada a enviar() aunque luego se traduzca en mas de un datagrama
		//Primero un paquete UDP
		//Definimos la pila de protocolos que queremos seguir
	pila_protocolos[0]=UDP_PROTO; pila_protocolos[1]=IP_PROTO; pila_protocolos[2]=ETH_PROTO;
	printf("pila_protocolos:%02X\n",pila_protocolos[0] );
	printf("pila_protocolos:%02X\n",pila_protocolos[1] );
	printf("pila_protocolos:%02X\n",pila_protocolos[2] );
		//Rellenamos los parametros necesario para enviar el paquete a su destinatario y proceso
	Parametros parametros_udp; memcpy(parametros_udp.IP_destino,IP_destino_red,IP_ALEN); parametros_udp.puerto_destino=puerto_destino;
		//Enviamos
	if(enviar((uint8_t*)data,pila_protocolos,strlen(data),&parametros_udp)==ERROR ){
		printf("Error: enviar(): %s %s %d.\n",errbuf,__FILE__,__LINE__);
		return ERROR;
	}
	else	cont++;

	printf("Enviado mensaje %"PRIu64", almacenado en %s\n\n\n", cont,fichero_pcap_destino);

		//Luego, un paquete ICMP en concreto un ping
	pila_protocolos[0]=ICMP_PROTO; pila_protocolos[1]=IP_PROTO; pila_protocolos[2]=0;
	Parametros parametros_icmp; parametros_icmp.tipo=PING_TIPO; parametros_icmp.codigo=PING_CODE; memcpy(parametros_icmp.IP_destino,IP_destino_red,IP_ALEN);
	if(enviar((uint8_t*)"Probando a hacer un ping",pila_protocolos,strlen("Probando a hacer un ping"),&parametros_icmp)==ERROR ){
		printf("Error: enviar(): %s %s %d.\n",errbuf,__FILE__,__LINE__);
		return ERROR;
	}
	else	cont++;
	printf("Enviado mensaje %"PRIu64", ICMP almacenado en %s\n\n", cont,fichero_pcap_destino);

		//Cerramos descriptores
	pcap_close(descr);
	pcap_dump_close(pdumper);
	pcap_close(descr2);
	return OK;
}


/****************************************************************************************
* Nombre: enviar 									*
* Descripcion: Esta funcion envia un mensaje						*
* Argumentos: 										*
*  -mensaje: mensaje a enviar								*
*  -pila_protocolos: conjunto de protocolos a seguir					*
*  -longitud: bytes que componen mensaje						*
*  -parametros: parametros necesario para el envio (struct parametros)			*
* Retorno: OK/ERROR									*
****************************************************************************************/

uint8_t enviar(uint8_t* mensaje, uint16_t* pila_protocolos,uint64_t longitud,void *parametros){
	uint16_t protocolo=pila_protocolos[0];
printf("Enviar(%"PRIu16") %s %d.\n",protocolo,__FILE__,__LINE__);
	if(protocolos_registrados[protocolo]==NULL){
		printf("Protocolo %"PRIu16" desconocido\n",protocolo);
		return ERROR;
	}
	else {
		return protocolos_registrados[protocolo](mensaje,pila_protocolos,longitud,parametros);
	}
	return ERROR;
}


/***************************TODO Pila de protocolos a implementar************************************/

/****************************************************************************************
* Nombre: moduloUDP 									*
* Descripcion: Esta funcion implementa el modulo de envio UDP				*
* Argumentos: 										*
*  -mensaje: mensaje a enviar								*
*  -pila_protocolos: conjunto de protocolos a seguir					*
*  -longitud: bytes que componen mensaje						*
*  -parametros: parametros necesario para el envio este protocolo			*
* Retorno: OK/ERROR									*
****************************************************************************************/

uint8_t moduloUDP(uint8_t* mensaje, uint16_t* pila_protocolos,uint64_t longitud,void *parametros){
	uint8_t segmento[UDP_SEG_MAX]={0};
	uint16_t puerto_origen = 0,suma_control=0;
	uint16_t aux16;
	uint32_t pos=0;
	uint16_t protocolo_inferior=pila_protocolos[1];
	printf("modulo UDP(%"PRIu16") %s %d.\n",protocolo_inferior,__FILE__,__LINE__);

	if (longitud>(pow(2,16)-UDP_HLEN)){
		printf("Error: mensaje demasiado grande para UDP (%f).\n",(pow(2,16)-UDP_HLEN));
		return ERROR;
	}
	printf("hola mundo\n");
	Parametros udpdatos=*((Parametros*)parametros);
	uint16_t puerto_destino=udpdatos.puerto_destino;

//TODO
//[...] 
//obtenerPuertoOrigen(·)
	obtenerPuertoOrigen(&puerto_origen);
	aux16=htons(puerto_origen);
	memcpy(segmento+pos,&aux16,sizeof(uint16_t));
	pos+=sizeof(uint16_t);
//puerto destino
	aux16=htons(puerto_destino);
	memcpy(segmento+pos,&aux16,sizeof(uint16_t));
	pos+=sizeof(uint16_t);	
//longitud
	aux16/=8;
	aux16=htons(longitud+ UDP_HLEN);
	memcpy(segmento+pos,&aux16,sizeof(uint16_t));
	pos+=sizeof(uint16_t);	
//checksum
	aux16=0;
	memcpy(segmento+pos,&aux16,sizeof(uint16_t));
	pos+=sizeof(uint16_t);
//mensaje
	printf("\t\tmensaje:%s\n",mensaje );
	memcpy(segmento+pos,mensaje,sizeof(uint8_t)*longitud);
//Se llama al protocolo definido de nivel inferior a traves de los punteros registrados en la tabla de protocolos registrados
	return protocolos_registrados[protocolo_inferior](segmento,pila_protocolos,longitud+pos,parametros);
}


/****************************************************************************************
* Nombre: moduloIP 									*
* Descripcion: Esta funcion implementa el modulo de envio IP				*
* Argumentos: 										*
*  -segmento: segmento a enviar								*
*  -pila_protocolos: conjunto de protocolos a seguir					*
*  -longitud: bytes que componen el segmento						*
*  -parametros: parametros necesario para el envio este protocolo			*
* Retorno: OK/ERROR									*
****************************************************************************************/

	uint8_t datagramaaux[IP_DATAGRAM_MAX]={0};	
uint8_t moduloIP(uint8_t* segmento, uint16_t* pila_protocolos,uint64_t longitud,void *parametros){
	
	uint32_t aux32;
	uint16_t aux16;
	uint8_t aux8;
	uint8_t *masc=NULL, *mac =NULL;
	uint32_t pos=0,pos_control=0;
	uint8_t IP_origen[IP_ALEN];
	uint16_t auxeth[6];
	uint16_t protocolo_superior=pila_protocolos[0];
	uint16_t protocolo_inferior=pila_protocolos[2];
	uint8_t checksum[2];
	uint8_t ret1[IP_ALEN], ret2 [IP_ALEN];
	pila_protocolos++;
	uint8_t mascara[IP_ALEN];
	uint16_t MTU;
	int npaq;

	printf("modulo IP(%"PRIu16") %s %d.\n",protocolo_inferior,__FILE__,__LINE__);
	obtenerMTUInterface(interface, &MTU);
	printf("MTU:%d\n",MTU);
	MTU-=24;
	if(longitud>MTU){
		npaq=(int)ceil((double)longitud/MTU);
	}
	else
		npaq=1;	
	printf("npaq:%d\n",npaq);
	Parametros ipdatos=*((Parametros*)parametros);
	uint8_t* IP_destino=ipdatos.IP_destino;

	aux8=4;//version
	aux8<<=4;
	aux8+=5;//longuitud de la cabecera
	memcpy(datagramaaux+pos,&aux8,sizeof(uint8_t));
	pos+=sizeof(uint8_t);

	aux8=0;//tipo de servicio
	memcpy(datagramaaux+pos,&aux8,sizeof(uint8_t));
	pos+=sizeof(uint8_t);


	aux16= htons(longitud +20);//longuitud total
	memcpy(datagramaaux+pos,&aux16,sizeof(uint16_t));
	pos+=sizeof(uint16_t);

	aux16=htons(protocolo_superior);//identificacion
	memcpy(datagramaaux+pos,&aux16,sizeof(uint16_t));
	pos+=sizeof(uint16_t);
	//cambiar para la fragmentacion
	aux16=0;
	aux16<<=13;
	aux16+=0;//posicion
	memcpy(datagramaaux+pos,&aux16,sizeof(uint16_t));
	pos+=sizeof(uint16_t);

	aux8=128;//tiempo de vida
	memcpy(datagramaaux+pos,&aux8,sizeof(uint8_t));
	pos+=sizeof(uint8_t);
	aux8=protocolo_superior;//protocolo udp o icmp
	memcpy(datagramaaux+pos,&aux8,sizeof(uint8_t));
	pos+=sizeof(uint8_t);


	aux16=0;//sumad de control

	memcpy(datagramaaux+pos,&aux16,sizeof(uint16_t));
	pos+=sizeof(uint16_t);

	obtenerMascaraInterface(interface, mascara);
	obtenerIPInterface(interface, IP_origen);
	printf("\t\t\t\tip_origen:%d.%d.%d.%d\n",IP_origen[0],IP_origen[1],IP_origen[2],IP_origen[3]);
	aplicarMascara(IP_origen,mascara, IP_ALEN, ret1);
	aplicarMascara(IP_destino,mascara, IP_ALEN, ret2);
	if((ret1[0]==ret2[0])&&(ret1[1]==ret2[1])&&(ret1[2]==ret2[2])&&(ret1[3]==ret2[3])){
		printf("Misma subred\n");

		printf("IP_origen:%d.%d.%d.%d\n",IP_origen[0],IP_origen[1],IP_origen[2],IP_origen[3] );
		aux8=IP_origen[0];
		memcpy(datagramaaux+pos,&aux8,sizeof(uint8_t));
		pos+=sizeof(uint8_t);

		aux8=IP_origen[1];
		memcpy(datagramaaux+pos,&aux8,sizeof(uint8_t));
		pos+=sizeof(uint8_t);

		aux8=IP_origen[2];
		memcpy(datagramaaux+pos,&aux8,sizeof(uint8_t));
		pos+=sizeof(uint8_t);

		aux8=IP_origen[3];
		memcpy(datagramaaux+pos,&aux8,sizeof(uint8_t));
		pos+=sizeof(uint8_t);
		printf("IP_destino:%d.%d.%d.%d\n",IP_destino[0],IP_destino[1],IP_destino[2],IP_destino[3] );
		ARPrequest(interface,IP_destino,auxeth);

		memcpy(ipdatos.ETH_destino,auxeth,6*sizeof(uint8_t));

		
	}
	else{

		printf("Otrs red, que vale pero es mas fea\n");
		obtenerGateway(interface,mascara);		
		printf("IP_origen:%d.%d.%d.%d\n",IP_origen[0],IP_origen[1],IP_origen[2],IP_origen[3] );
		
		aux8=IP_origen[0];
		memcpy(datagramaaux+pos,&aux8,sizeof(uint8_t));
		pos+=sizeof(uint8_t);

		aux8=IP_origen[1];
		memcpy(datagramaaux+pos,&aux8,sizeof(uint8_t));
		pos+=sizeof(uint8_t);
		
		aux8=IP_origen[2];
		memcpy(datagramaaux+pos,&aux8,sizeof(uint8_t));
		pos+=sizeof(uint8_t);
		
		aux8=IP_origen[3];
		memcpy(datagramaaux+pos,&aux8,sizeof(uint8_t));
		pos+=sizeof(uint8_t);
		
	
		ARPrequest(interface,mascara,auxeth);
		memcpy(ipdatos.ETH_destino,auxeth,6*sizeof(uint8_t));
		printf("penes\n");

	}

	printf("IP_destino:%d.%d.%d.%d\n",IP_destino[0],IP_destino[1],IP_destino[2],IP_destino[3] );
	aux8=IP_destino[0];
	memcpy(datagramaaux+pos,&aux8,sizeof(uint8_t));
	pos+=sizeof(uint8_t);
	
	aux8=IP_destino[1];
	memcpy(datagramaaux+pos,&aux8,sizeof(uint8_t));
	pos+=sizeof(uint8_t);
	
	aux8=IP_destino[2];
	memcpy(datagramaaux+pos,&aux8,sizeof(uint8_t));
	pos+=sizeof(uint8_t);

	aux8=IP_destino[3];
	memcpy(datagramaaux+pos,&aux8,sizeof(uint8_t));
	pos+=sizeof(uint8_t);
	/*aux32<<=8;
	aux32=IP_destino[1];
	aux32<<=8;
	aux32=IP_destino[2];
	aux32<<=8;
	aux32=IP_destino[3];
	aux32<<=8;

	memcpy(datagramaaux+pos,&aux32,sizeof(uint32_t));
	pos+=sizeof(uint32_t);
	aux32=0;*/

	memcpy(datagramaaux+pos,segmento,sizeof(uint8_t)*longitud);
	calcularChecksum(pos, datagramaaux, checksum);

	memcpy(datagramaaux + 10, &checksum[0], sizeof(uint8_t));
	memcpy(datagramaaux + 11, &checksum[1], sizeof(uint8_t));
	printf("penes\n");
	*((Parametros*)parametros)=ipdatos;
	return protocolos_registrados[protocolo_inferior](datagramaaux,pila_protocolos,longitud+20,parametros);
//TODO
//Llamar a ARPrequest(·) adecuadamente y usar ETH_destino de la estructura parametros
//[...] 
//TODO A implementar el datagrama y fragmentación (en caso contrario, control de tamano)
//[...] 
//llamada/s a protocolo de nivel inferior [...]


}


/****************************************************************************************
* Nombre: moduloETH 									*
* Descripcion: Esta funcion implementa el modulo de envio Ethernet			*
* Argumentos: 										*
*  -datagrama: datagrama a enviar							*
*  -pila_protocolos: conjunto de protocolos a seguir					*
*  -longitud: bytes que componen el datagrama						*
*  -parametros: Parametros necesario para el envio este protocolo			*
* Retorno: OK/ERROR									*
****************************************************************************************/

uint8_t moduloETH(uint8_t* datagrama, uint16_t* pila_protocolos,uint64_t longitud,void *parametros){
	uint16_t retornoMTU;
	uint8_t trama[ETH_FRAME_MAX]={0};
	Parametros ethdatos=*((Parametros*)parametros);
	uint8_t* ETH_destino;
	uint8_t ETH_origen[ETH_ALEN];
	uint16_t protocolo_superior=htons(IP_PROTO);
	uint8_t aux = 0;
	struct pcap_pkthdr cabecera;

	printf("modulo ETH(fisica) %s %d.\n",__FILE__,__LINE__);

	obtenerMTUInterface(interface, &retornoMTU); /*Obtener el tamanio maximo que se puede transmitir por ethernet*/
	printf("long:%d\n",longitud );
	printf("retornoMTU:%d\n",retornoMTU );
	if(longitud > retornoMTU){
		printf("Tamanio demasiado grande para transmitir por ETH");
		return ERROR;
	}
	ETH_destino=ethdatos.ETH_destino;
	if (obtenerMACdeInterface(interface, ETH_origen) == ERROR){
        return ERROR;
	}	

	cabecera.caplen =longitud + ETH_HLEN;	
	cabecera.len = longitud + ETH_HLEN;  /*Longitud del datagrama + tamaño de la cabecera*/

	
	memcpy(trama,ETH_destino,sizeof(uint8_t)*ETH_ALEN);
	aux=sizeof(uint8_t)*ETH_ALEN;
	memcpy(trama + aux,ETH_origen,sizeof(uint8_t)*ETH_ALEN);
	
	printf("pene\n");
	aux+=sizeof(uint8_t)*ETH_ALEN;
	printf("protocolo:%d\n",protocolo_superior);
	//memcpy(trama + aux,protocolo_superior,sizeof(uint16_t));
	memcpy(trama+aux,&protocolo_superior,sizeof(uint16_t));
	printf("pene\n");
	aux+=sizeof(uint16_t);
	memcpy(trama + aux, datagrama, sizeof(uint8_t)*longitud);
	/*Aqui ya hemos guardado las direcciones de origen y destino, el tipo y el datagrama*/

	aux+=sizeof(uint8_t)*longitud;

	printf("pene\n");
	if(pcap_inject(descr, trama, aux)==-1){
		return ERROR;
	}
	printf("pene\n");
	pcap_dump(pdumper,&cabecera,trama);
	return OK;
}



/****************************************************************************************
* Nombre: moduloICMP 									*
* Descripcion: Esta funcion implementa el modulo de envio ICMP				*
* Argumentos: 										*
*  -mensaje: mensaje a anadir a la cabecera ICMP					*
*  -pila_protocolos: conjunto de protocolos a seguir					*
*  -longitud: bytes que componen el mensaje						*
*  -parametros: parametros necesario para el envio este protocolo			*
* Retorno: OK/ERROR									*
****************************************************************************************/

uint8_t moduloICMP(uint8_t* mensaje, uint16_t* pila_protocolos,uint64_t longitud,void *parametros){

	uint16_t id = 1;
	uint16_t protocolo_inferior = pila_protocolos[1];
	Parametros icmpdatos = *((Parametros *) parametros);
	uint8_t segmento[ICMP_DATAGRAM_MAX];
	uint8_t aux = 0, aux0=0;
	uint8_t checksum[2];

	memcpy(segmento, &icmpdatos.tipo, sizeof(uint8_t));
	aux+=sizeof(uint8_t);
	memcpy(segmento + aux, &icmpdatos.codigo, sizeof(uint8_t));
	aux+=sizeof(uint8_t);
	memcpy(segmento + aux, &aux0, sizeof(uint16_t)); //De momento 0, luego calcularlo
	aux+=sizeof(uint16_t);
	memcpy(segmento + aux, &id, sizeof(uint16_t)); 
	aux+=sizeof(uint16_t);
	memcpy(segmento + aux, &aux0, sizeof(uint16_t)); 
	aux+=sizeof(uint16_t);

	memcpy(segmento + aux, mensaje, longitud*sizeof(uint16_t));

	calcularChecksum(longitud + aux, segmento, checksum);

	memcpy(segmento + 2, &checksum[0], sizeof(uint8_t));
	memcpy(segmento + 3, &checksum[1], sizeof(uint8_t));
	//return OK;


	return protocolos_registrados[protocolo_inferior](segmento,pila_protocolos,longitud+aux,parametros);

}


/***************************Funciones auxiliares a implementar***********************************/

/****************************************************************************************
* Nombre: aplicarMascara 								*
* Descripcion: Esta funcion aplica una mascara a una vector				*
* Argumentos: 										*
*  -IP: IP a la que aplicar la mascara en orden de red					*
*  -mascara: mascara a aplicar en orden de red						*
*  -longitud: bytes que componen la direccion (IPv4 == 4)				*
*  -resultado: Resultados de aplicar mascara en IP en orden red				*
* Retorno: OK/ERROR									*
****************************************************************************************/

uint8_t aplicarMascara(uint8_t* IP, uint8_t* mascara, uint32_t longitud, uint8_t* resultado){
	int i;
	if ((IP==NULL)||(mascara==NULL)){
		return ERROR;
	}
	for (i = 0; i < longitud; ++i)
	{
		resultado[i]=IP[i]&mascara[i];
	}
	return OK;
}


/***************************Funciones auxiliares implementadas**************************************/

/****************************************************************************************
* Nombre: mostrarPaquete 								*
* Descripcion: Esta funcion imprime por pantalla en hexadecimal un vector		*
* Argumentos: 										*
*  -paquete: bytes que conforman un paquete						*
*  -longitud: Bytes que componen el mensaje						*
* Retorno: OK/ERROR									*
****************************************************************************************/

uint8_t mostrarPaquete(uint8_t * paquete, uint32_t longitud){
	uint32_t i;
	printf("Paquete:\n");
	for (i=0;i<longitud;i++){
		printf("%02"PRIx8" ", paquete[i]);
	}
	printf("\n");
	return OK;
}


/****************************************************************************************
* Nombre: calcularChecksum							     	*
* Descripcion: Esta funcion devuelve el ckecksum tal como lo calcula IP/ICMP		*
* Argumentos:										*
*   -longitud: numero de bytes de los datos sobre los que calcular el checksum		*
*   -datos: datos sobre los que calcular el checksum					*
*   -checksum: checksum de los datos (2 bytes) en orden de red! 			*
* Retorno: OK/ERROR									*
****************************************************************************************/

uint8_t calcularChecksum(uint16_t longitud, uint8_t *datos, uint8_t *checksum) {
    uint16_t word16;
    uint32_t sum=0;
    int i;
    // make 16 bit words out of every two adjacent 8 bit words in the packet
    // and add them up
    for (i=0; i<longitud; i=i+2){
        word16 = (datos[i]<<8) + datos[i+1];
        sum += (uint32_t)word16;       
    }
    // take only 16 bits out of the 32 bit sum and add up the carries
    while (sum>>16) {
        sum = (sum & 0xFFFF)+(sum >> 16);
    }
    // one's complement the result
    sum = ~sum;      
    checksum[0] = sum >> 8;
    checksum[1] = sum & 0xFF;
    return OK;
}


/***************************Funciones inicializacion implementadas*********************************/

/****************************************************************************************
* Nombre: inicializarPilaEnviar     							*
* Descripcion: inicializar la pila de red para enviar registrando los distintos modulos *
* Retorno: OK/ERROR									*
****************************************************************************************/

uint8_t inicializarPilaEnviar() {
	bzero(protocolos_registrados,MAX_PROTOCOL*sizeof(pf_notificacion));
	if(registrarProtocolo(ETH_PROTO, moduloETH, protocolos_registrados)==ERROR)
		return ERROR;
	if(registrarProtocolo(IP_PROTO, moduloIP, protocolos_registrados)==ERROR)
		return ERROR;

	if(registrarProtocolo(UDP_PROTO, moduloUDP, protocolos_registrados)==ERROR)
		return ERROR;
	if(registrarProtocolo(ICMP_PROTO, moduloICMP, protocolos_registrados)==ERROR)
		return ERROR;


	return OK;
}


/****************************************************************************************
* Nombre: registrarProtocolo 								*
* Descripcion: Registra un protocolo en la tabla de protocolos 				*
* Argumentos:										*
*  -protocolo: Referencia del protocolo (ver RFC 1700)					*
*  -handleModule: Funcion a llamar con los datos a enviar				*
*  -protocolos_registrados: vector de funciones registradas 				*
* Retorno: OK/ERROR 									*
*****************************************************************************************/

uint8_t registrarProtocolo(uint16_t protocolo, pf_notificacion handleModule, pf_notificacion* protocolos_registrados){
	if(protocolos_registrados==NULL ||  handleModule==NULL){		
		printf("Error: registrarProtocolo(): entradas nulas.\n");
		return ERROR;
	}
	else
		protocolos_registrados[protocolo]=handleModule;
	return OK;
}


