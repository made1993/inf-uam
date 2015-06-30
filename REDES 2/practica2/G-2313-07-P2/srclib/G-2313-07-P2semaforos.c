#include "../includes/G-2313-07-P2semaforos.h"

static int numsem;

int Inicializar_Semaforo(int semid, unsigned short *array){
        int i=0;
    
	arg.array = (unsigned short *)malloc(sizeof(short)* numsem);
        if(arg.array == NULL){
            return ERROR;
        }
        while(i < numsem){
            arg.array [i] = array[i];
            i++;
        }
        semctl (semid, numsem, SETALL, arg);
	return OK;
}

int Borrar_Semaforo(int semid){
	semctl (semid, 0, IPC_RMID, arg);
        free(arg.array);
	return OK;
}

int Crear_Semaforo(key_t key, int size, int *semid){
	*semid = semget(key, size, IPC_CREAT | IPC_EXCL | SHM_R | SHM_W);
	if((*semid == -1) && (errno == EEXIST))
		*semid=semget(key,size,SHM_R|SHM_W);
	if(*semid==-1){
            perror("semget");
            exit(errno);
	}
    numsem = size;
	return OK;
}

int Down_Semaforo(int id, int num_sem, int undo){
    sem_oper.sem_num = num_sem;  /* Actuamos sobre el semáforo num_sem*/ 
    sem_oper.sem_op = -1;        /* Decrementar en 1 el valor del semáforo */ 
    sem_oper.sem_flg = undo;     /* Para evitar interbloqueos si un proceso acaba inesperadamente */ 

    if(semop (id, &sem_oper, 1) == -1){
        perror("semop"); 
        return ERROR;
    }
    return OK;
}

int DownMultiple_Semaforo(int id,int size,int undo,int *active) {
    int i;
    struct sembuf * sem_oper_mul = (struct sembuf*) malloc(sizeof (struct sembuf)*size);

    for (i = 0; i < size; i++) {
        sem_oper_mul[i].sem_num = i;
        sem_oper_mul[i].sem_flg = undo;

        if (active[i] == 1) {
            sem_oper_mul[i].sem_op = -1;
        }
    }

    if (semop(id, sem_oper_mul, size) == -1) {
        perror("semop");
        return ERROR;
    }
    return OK; 
}

int Up_Semaforo(int id, int num_sem, int undo){
    sem_oper.sem_num = num_sem;  /* Actuamos sobre el semáforo num_sem*/ 
    sem_oper.sem_op = 1;         /* Incrementar en 1 el valor del semáforo */ 
    sem_oper.sem_flg = undo;     /* Para evitar interbloqueos si un proceso acaba inesperadamente */ 

    if(semop (id, &sem_oper, 1) == -1){
        perror("semop"); 
        return ERROR;
    }
    return OK;
}

int UpMultiple_Semaforo(int id,int size, int undo, int *active){
    int i;
    struct sembuf * sem_oper_mul = (struct sembuf*) malloc (sizeof(struct sembuf)*size);

    for(i=0; i<size; i++){
        sem_oper_mul[i].sem_num = i;
        sem_oper_mul[i].sem_flg = undo;

        if(active[i] == 1){
            sem_oper_mul[i].sem_op = 1;    
        }    
    }

    if(semop (id, sem_oper_mul, size) == -1){
        perror("semop"); 
        return ERROR;
    }
    return OK; 
}

