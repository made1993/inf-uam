#ifndef AUX_H
#include <stdio.h>
#include <stdlib.h>
#include <pcap.h>
#include <string.h>
#include <netinet/in.h>
#include <linux/udp.h>
#include <linux/tcp.h>
#include <signal.h>
#include <time.h>
#include <getopt.h>
#include <inttypes.h>

FILE * fcaudal;
FILE * ftpaq;
void escribetam(struct pcap_pkthdr *cabecera,int mf, int df, int offset,FILE *f);
void t_paquetes(struct pcap_pkthdr *cabecera);
void caudal(struct pcap_pkthdr *cabecera);
void caudal_ultimo();
#endif