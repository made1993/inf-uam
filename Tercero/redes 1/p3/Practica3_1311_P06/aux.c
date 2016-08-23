#include "aux.h"
int tam=0;
long int instante=0, instante0=0;
double aentero=0,adecimal;
void escribetam(struct pcap_pkthdr *cabecera,int mf, int df, int offset, FILE * f){
//no fragmentado, fragmentado
	if(df==0 && mf==0){
		fprintf(f,"%d\n",cabecera->len); 
		return;	
	}
	else if(df==1){
		fprintf(f,"%d\n",cabecera->len); 
		return;
	}
}
void caudal(struct pcap_pkthdr *cabecera){

	if(instante==0){
		tam=cabecera->len;
		instante= cabecera->ts.tv_sec;
		instante0=instante;
	}
	else if(cabecera->ts.tv_sec>instante){
		if(cabecera->ts.tv_sec==instante+1){
			fprintf(fcaudal, "seg: %ld\ttam: %d\n",instante - instante0,tam *8);
			tam=cabecera->len;
			instante=cabecera->ts.tv_sec;
		}
		else{
			fprintf(fcaudal, "seg: %ld\ttam: %d\n",instante - instante0,tam*8);
			for(instante++;instante<cabecera->ts.tv_sec;instante++){
				fprintf(fcaudal, "seg: %ld\ttam: 0\n",instante - instante0);
			}
			tam=cabecera->len;
			instante=cabecera->ts.tv_sec;
		}
	}
	else if(cabecera->ts.tv_sec==instante){
		tam+=cabecera->len;
	}
}
void caudal_ultimo(){
	fprintf(fcaudal, "seg: %ld\ttam: %d\n",instante - instante0,tam*8);
}
void t_paquetes(struct pcap_pkthdr *cabecera){
	double entero=cabecera->ts.tv_sec;
	double decimal=cabecera->ts.tv_usec;
	if(adecimal==0 && aentero==0){
		aentero=entero;
		adecimal=decimal;
	}
	else{
		
		fprintf(ftpaq, "%lf\n", ((entero*1000000+decimal)-(aentero*1000000+adecimal))*1.0/1000000.0);
		aentero=entero;
		adecimal=decimal;
	}

}