
int createList();

typedef struct {

	int (*compare)(const void* a, const void* b)
	struct Nodo * nodoInicial;
	
}Lista;


struct Nodo{

	void * valor;
	struct Nodo * next;
	

};


int compareUsuario(void * a, void * b){
	
	return strcmp (((Usuario *)a).nick, ((Usuario *)b).nick);

}

typedef struct {

int socket;
char * nick;
otros...


}Usuario;


typedef struct  {

char * nombre;
otros...

}Canal;


create
insert
delete
free
find
update

semaforos
