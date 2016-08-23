#include "lista.h"
LIST * new_list(){
	LIST * ls=NULL;
	ls=(LIST*) malloc(sizeof(LIST));
	ls->first=NULL;
	return ls;
}
NODE* create_node( uint32_t ip){
	NODE * nd=NULL;
	nd=(NODE*)malloc(sizeof(NODE));
	if (nd==NULL){
		return nd;
	}
	nd->ip=ip;
	nd->count=1;
	nd->tam=0;
	nd->next=NULL;
	return nd;
}
int dstr_node(NODE* nd){
	if(nd==NULL){
		return -1;
	}
	nd->ip=0;
	nd->count=0;
	free(nd);
	nd=NULL;
	return 0;
}
int incr_node(NODE* nd){
	if(nd==NULL)
		return -1;
	nd->count++;
	return 0;
}
int add_element(LIST* ls, uint32_t ip, int tam){
	NODE* ndaux=NULL;
	NODE* ndaux1=NULL;
	NODE* ndaux2=NULL;
	if(ls==NULL){
		return -1;
	}
	ndaux=ls->first;
	if (ndaux==NULL){
		ndaux2=create_node(ip);
		if (ndaux2==NULL){
			return-1;
		}
		ls->first=ndaux2;
		return 0;
	}
	else if(ndaux->ip>ip){
		ndaux2=create_node(ip);
		if (ndaux2==NULL){
			return-1;
		}
		ls->first=ndaux2;
		ndaux2->next=(struct NODE*)ndaux;
		return 0;	

	}
	else if((ndaux->next==NULL)&&(ndaux->ip<ip)){
		ndaux2=create_node(ip);
		if (ndaux2==NULL){
			return-1;
		}
		ndaux->next=(struct NODE*)ndaux2;
		return 0;	
	}
	if (ndaux->ip==ip){
		if(incr_element(ls,ip,tam)==0){
			return 0;
		}
	}
	ndaux1=(NODE*)ndaux->next;
	if((ndaux1->next==NULL)&&(ndaux->next!=NULL)){
		if((ndaux->ip<ip)&&(ndaux1->ip>ip)){
			ndaux2=create_node(ip);
			if (ndaux2==NULL){
				return-1;
			}
			ndaux->next=(struct NODE*)ndaux2;
			ndaux2->next=(struct NODE*)ndaux1;
			return 0;
		}	
	}
	for(ndaux1=(NODE*)ndaux->next; ndaux1->next!=NULL ;ndaux=(NODE*)ndaux->next, ndaux1=(NODE*)ndaux1->next){
		if((ndaux->ip<ip)&&(ndaux1->ip>ip)){
			ndaux2=create_node(ip);
			if (ndaux2==NULL){
				return-1;
			}
			ndaux->next=(struct NODE*)ndaux2;
			ndaux2->next=(struct NODE*)ndaux1;
			return 0;
		}
	}
	if(incr_element(ls,ip,tam)==0){
		return 0;
	}
	ndaux2=create_node(ip);
	if (ndaux2==NULL){
		return-1;
	}
	ndaux1->next=(struct NODE*)ndaux2;
	return 0;
}
int remove_element(LIST* ls, uint32_t ip){
	NODE* ndaux=NULL;
	NODE* ndaux1=NULL;
	if(ls==NULL){
		return -1;
	}
	ndaux=ls->first;
	if(ndaux==NULL){
		return-1;
	}
	if(ndaux->next==NULL){
		if(ndaux->ip==ip){
			dstr_node(ndaux);
			return 0;
		}
		return-1;
	}
	else if(ndaux->ip==ip){
		ls->first=(NODE*)ndaux->next;
		dstr_node(ndaux);
	}
	for(ndaux1=(NODE*)ndaux->next;ndaux1!=NULL;ndaux1=(NODE*)ndaux1->next, ndaux=(NODE*)ndaux->next){
		if(ndaux1->ip==ip){
			ndaux->next=ndaux1->next;
			dstr_node(ndaux1);
			return 0;
		}
	}
	return -1;
}
int incr_element(LIST* ls, uint32_t ip,int tam){
	NODE* ndaux=NULL;
	if(ls==NULL){
		return-1;
	}
	ndaux=ls->first;
	if(ndaux==NULL)
		return-1;
	for(;ndaux!=NULL;ndaux=(NODE*)ndaux->next){
		if(ndaux->ip==ip){
			ndaux->count++;
			ndaux->tam+=tam;
			return 0;
		}
	}
	return-1;
}
void  dstr_list(LIST* ls){
	NODE* ndaux=NULL;
	if(ls==NULL)
		return;
	ndaux=ls->first;
	if(ndaux==NULL){
		free(ls);
		return;
	}
	while(ndaux->next!=NULL){
		ls->first=(NODE*)ndaux->next;
		dstr_node(ndaux);
		ndaux=ls->first;
	}
	dstr_node(ndaux);
	free(ls);
	ls=NULL;
}
void top5(LIST * ls, FILE * top5,int tp){
	int top[5][2];
	int toptam[5][2];
	int aux[2],i,auxtam[2];
	NODE * ndaux=NULL;
	if(ls==NULL){
		return;
	}
	else if (ls->first==NULL){
		return;
	}
	if (top5==NULL){
		return;
	}
	for(i=0;i<5;i++){
		top[i][0]=0;
		top[i][1]=0;
	}
	aux[0]=0;
	aux[1]=0;
	auxtam[0]=0;
	auxtam[1]=0;
	for(i=0;i<5;i++){
		if(ls->first==NULL){
			printf("NULL\n");
		}
		for(ndaux=ls->first;ndaux!=NULL;ndaux=(NODE*)ndaux->next){
			if(aux[1]<ndaux->count){
				aux[0]=ndaux->ip;
				aux[1]=ndaux->count;
				
			}
			if(auxtam[1]<ndaux->tam){
				auxtam[0]=ndaux->ip;
				auxtam[1]=ndaux->tam;
				
			}
		}
		top[i][0]=aux[0];
		top[i][1]=aux[1];
		toptam[i][0]=auxtam[0];
		toptam[i][1]=auxtam[1];
		aux[0]=0;
		aux[1]=0;
		auxtam[0]=0;
		auxtam[1]=0;
		remove_element(ls, top[i][0]);
	}
	if (tp==0){ /*TIPO puerto*/
		printf("referencias\n" );
		for(i=0;i<5;i++){
			fprintf(top5, "%dº:%u,\tcount:%d\n",i+1,top[i][0],top[i][1]);
		}
		printf("\ntam:\n");
		for(i=0;i<5;i++){
			fprintf(top5, "%dº:%u,\ttam:%d\n",i+1,toptam[i][0],toptam[i][1]);
		}
	}
	if (tp==1){/*TIPO IP*/
		printf("referencias\n" );
		for(i=0;i<5;i++){
			fprintf(top5, "%dº:%d.%d.%d.%d,\tcount:%d\n",i+1,(top[i][0]&0xFF000000)>>24,(top[i][0]&0x00FF0000)>>16,(top[i][0]&0x0000FF00)>>8,(top[i][0]&0x000000FF), top[i][1]);
		}
		printf("\ntam:\n");
		for(i=0;i<5;i++){
			fprintf(top5, "%dº:%d.%d.%d.%d,\ttam:%d\n",i+1,(toptam[i][0]&0xFF000000)>>24,(toptam[i][0]&0x00FF0000)>>16,(toptam[i][0]&0x0000FF00)>>8,(toptam[i][0]&0x000000FF), toptam[i][1]);
		}
	}
	return;
}
/*int main(){
	LIST * ls;
	ls=new_list();
	add_element(ls,1<<24);
	add_element(ls,1<<24);
	add_element(ls,1<<24);
	add_element(ls,1<<24);
	add_element(ls,1<<24);
	add_element(ls,2<<24);
	add_element(ls,2<<24);
	add_element(ls,2<<24);
	add_element(ls,2<<24);
	add_element(ls,3<<24);
	add_element(ls,3<<24);
	add_element(ls,3<<24);
	add_element(ls,4<<24);
	add_element(ls,4<<24);
	add_element(ls,5<<24);
	top5(ls,stdout,1);
}
*/