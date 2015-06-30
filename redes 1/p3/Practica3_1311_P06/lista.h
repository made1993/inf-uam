#ifndef LISTA_H
#include "aux.h"
typedef struct{
	uint32_t ip;
	int count;
	int tam;
	struct NODE* next; 

}NODE;
typedef struct{
	NODE * first;
}LIST;
LIST * puerto;
LIST * ip;
LIST * new_list();
NODE * create_node( uint32_t ip);
int dstr_node(NODE* nd);
int incr_node(NODE* nd);
int add_element(LIST* ls, uint32_t ip,int tam);
int remove_element(LIST* ls, uint32_t ip);
int incr_element(LIST* ls, uint32_t ip,int tam);
void  dstr_list(LIST* ls);
void top5(LIST * ls,FILE *top5,int tp);

#endif
