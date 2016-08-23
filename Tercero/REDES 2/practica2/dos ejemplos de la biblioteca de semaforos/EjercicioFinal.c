/**
 * @brief EjercicioFinal
 *
 * Simulacion de un parking
 * Se crean dos procesos aparcacoches
 * Un proceso desaparcacoches
 * Cada 30 segundos se genera una alarma que imprime un fichero con los datos de los procesos actuales
 *
 * @file EjercicioFinal.c
 * @author Roberto Garcia & Jorge Guillen
 * @version 1.0
 * @date 25-04-2014
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <time.h>
#include <unistd.h>
#include <signal.h>
#include <fcntl.h>
#include <sys/msg.h>
#include <sys/wait.h> 
#include <sys/stat.h> 
#include <sys/shm.h> 
#include "semaforos.h"

//posiciones del array de semaforos para los distintos semaforos
#define ENTRADA 0
#define APARCANDO 1
#define APARCACOCHES 2
#define DESAPARCACOCHES 3
#define APARCADO 4
#define SALE 5

//variables globales
int semid;
int msqid;
int n_plazas;
int max_ocupacion;

//struct de mensaje
typedef struct _Mensaje {
    long mtype;
    char mtext[2];
    int id;
} mensaje;


/**
* @brief proceso aparcacoches 1
*
*/
int aparcacoches1(){
    mensaje mensaje;
    int i;
    int * aparcamiento;
    int key;
    int shmid;
 
    
    while(1){
        Down_Semaforo(semid, APARCACOCHES, 0);
        Down_Semaforo(semid, APARCANDO, 0);
        
        key = ftok("/bin/ls", 1800);
        shmid = shmget(key, n_plazas * sizeof (int), IPC_CREAT | 0666);
        if (shmid == -1) {
            printf("Error en shmget.\n");
            return -1;
        }
        aparcamiento = shmat(shmid, NULL, 0);
        if ((int) aparcamiento == -1) {
            printf("Error en shmat.\n");
            return -1;
        }
        
        
        msgrcv(msqid, (struct msgbuf *) &mensaje, sizeof(mensaje) - sizeof(long), 1, 0);
        for (i = 0; i < n_plazas; i++) {
            if (aparcamiento[i] == -1) {
                aparcamiento[i] = mensaje.id;
                printf("Coche %d aparcado en plaza %d\n", mensaje.id, i);
                fflush(NULL);
                break;
            }
        }
        
        shmdt(aparcamiento);
        
        Up_Semaforo(semid, APARCANDO, 0);
        Up_Semaforo(semid, APARCADO, 0);  
    }
}
/**
* @brief proceso aparcacoches 2
*
*/
int aparcacoches2(){
    mensaje mensaje;
    int i;
    int * aparcamiento;
    int key;
    int shmid;
    
    while(1){
        Down_Semaforo(semid, APARCACOCHES, 0);
        Down_Semaforo(semid, APARCANDO, 0);
        
        key = ftok("/bin/ls", 1800);
        shmid = shmget(key, n_plazas * sizeof (int), IPC_CREAT | 0666);
        if (shmid == -1) {
            printf("Error en shmget.\n");
            return -1;
        }
        aparcamiento = shmat(shmid, NULL, 0);
        if ((int) aparcamiento == -1) {
            printf("Error en shmat.\n");
            return -1;
        }
        
        
        msgrcv (msqid, (struct msgbuf *) &mensaje, sizeof(mensaje) - sizeof(long), 1, 0);
        for (i = 0; i < n_plazas; i++) {
            if (aparcamiento[i] == -1) {
                aparcamiento[i] = mensaje.id;
                printf("Coche %d aparcado en plaza %d\n", mensaje.id, i);
                fflush(NULL);
                break;
            }
        }
        
        shmdt(aparcamiento);
        
        Up_Semaforo(semid, APARCANDO, 0);
        Up_Semaforo(semid, APARCADO, 0);
    }
}
/**
* @brief proceso desaparcacoches
*
*/
int desaparcacoches(){
    mensaje mensaje;
    int i;
    int * aparcamiento;
    int key;
    int shmid;
    
    while(1){
        Down_Semaforo(semid, DESAPARCACOCHES, 0);
        Down_Semaforo(semid, APARCANDO, 0);
        
        key = ftok("/bin/ls", 1800);
        shmid = shmget(key, n_plazas * sizeof (int), IPC_CREAT | 0666);
        if (shmid == -1) {
            printf("Error en shmget.\n");
            return -1;
        }
        aparcamiento = shmat(shmid, NULL, 0);
        if ((int) aparcamiento == -1) {
            printf("Error en shmat.\n");
            return -1;
        }
        
        msgrcv (msqid, (struct msgbuf *) &mensaje, sizeof(mensaje) - sizeof(long), 2, 0);
        for (i = 0; i < n_plazas; i++) {
            if (aparcamiento[i] == mensaje.id) {
                aparcamiento[i] = -1;
                printf("Coche %d sale de la plaza %d\n", mensaje.id, i);
                fflush(NULL);
                break;
            }
        }
        
        shmdt(aparcamiento);
        
        Up_Semaforo(semid, APARCANDO, 0);
        Up_Semaforo(semid, SALE, 0);
    }
}


/**
* @brief funciones de los procesos coche
*
*/
void coche(){
    mensaje mensaje;
    Down_Semaforo(semid, ENTRADA, 0);
    
    
    mensaje.mtype = 1;
    mensaje.id = getpid();
    strcpy (mensaje.mtext, ""); 
    msgsnd(msqid, (struct msgbuf *) &mensaje, sizeof(mensaje) - sizeof(long), 0);
    
    printf("Coche %d esperando aparcamiento\n", getpid());
    fflush(NULL);
    Up_Semaforo(semid, APARCACOCHES, 0);
    Down_Semaforo(semid, APARCADO, 0);
    sleep(rand()%15);
    
    
    mensaje.mtype = 2;
    msgsnd(msqid, (struct msgbuf *) &mensaje, sizeof(mensaje) - sizeof(long), 0);
    
    Up_Semaforo(semid, DESAPARCACOCHES, 0);
    Down_Semaforo(semid, SALE, 0);
    Up_Semaforo(semid, ENTRADA, 0);
}

/**
* @brief manejador de la alarma que ejecuta la orden de imprimir el fichero con los datosd e los procesos
*
*/
void manejador_SIGALRM(int sig){
    char * alarma = (char*) malloc(strlen("ps –ef | grep ***** | grep –v grep > SIGHUP_PPID_lista_proc.txt")+1);
    sprintf(alarma, "ps -ef | grep %d | grep -v grep > SIGHUP_PPID_lista_proc.txt", getpid());
    system(alarma);
    
    if (signal(SIGALRM, manejador_SIGALRM) == SIG_ERR) {
        puts("Error en la captura");
        exit(EXIT_FAILURE);
    }
    if (alarm(30))
        fprintf(stderr, "Existe una alarma previa establecida\n");
}

/**
* @brief gestor principal del aparcamiento
*
*/
int main(int argc, char** argv) {
    int key;
    int shmid;
    int pid;
    int pidap1, pidap2, piddesap;
    int * aparcamiento;
    int i;
    int n_coches;
    
    do{
	    printf("\nIntroduce el numero de plazas del aparcamiento: ");
	    scanf("%d", &n_plazas); 
	}while(n_plazas < 2);/*Introducimos el nº de plazas y comprobamos que no sea <=1*/
	
	max_ocupacion = n_plazas*0.85;/*calculamos el numero de plazas efectivas*/
	
	
	do{
	    printf("\nIntroduce el numero de coches de la simulacion (tiene que ser mayor de %d): ", max_ocupacion);
	    scanf("%d", &n_coches);
	}while(n_coches <= max_ocupacion);/*Introducimos el nº de coches y comprobamos que no sea <= la ocupacion maxima*/
    
    key = ftok("/bin/ls", 7000);
    
    
    //Iniciar semaforos
    unsigned short *semaforos = (unsigned short*) malloc(6 * sizeof (unsigned short));
    semaforos[ENTRADA] = max_ocupacion;
    semaforos[APARCANDO] = 1;
    semaforos[APARCACOCHES] = 0;
    semaforos[DESAPARCACOCHES] = 0;
    semaforos[APARCADO] = 0;
    semaforos[SALE] = 0;
    
    Crear_Semaforo(key, 6, &semid);
    Inicializar_Semaforo(semid, semaforos);
    
    /*Crear memoria compartida*/
    key = ftok("/bin/ls", 1800);
    shmid = shmget(key, n_plazas*sizeof(int), IPC_CREAT | 0666);
    if(shmid == -1){
        printf("Error en shmget.\n");
        return -1;
    }
    aparcamiento = shmat(shmid, NULL, 0);
    if((int)aparcamiento == -1){
        printf("Error en shmat.\n");
        return -1;
    }
    
    for(i=0; i<n_plazas; i++){
        aparcamiento[i] = -1;
    }
    
    /*Crea la cola de mensajes*/
    key = ftok("/bin/ls", 1300);
    msqid = msgget(key, IPC_CREAT|0660); 
    
    
    /*Crea los aparcacoches*/
    pidap1 = fork();
    if(pidap1 == 0){
        aparcacoches1();
    }
    pidap2 = fork();
    if(pidap2 == 0){
        aparcacoches2();
    }
    piddesap = fork();
    if(piddesap == 0){
        desaparcacoches();
    }
    
    
    //Crear alarma
    if (signal(SIGALRM, manejador_SIGALRM) == SIG_ERR) {
        puts("Error en la captura");
        exit(EXIT_FAILURE);
    }
    if (alarm(30))
        fprintf(stderr, "Existe una alarma previa establecida\n"); 

    
    srand(time(NULL));
    printf("Comienza la simulacion.\n");
    //Creamos los coches
    for (i = 0; i < n_coches; i++) {
        pid = fork();
        if (pid == -1) {
            exit(EXIT_FAILURE);
        }
        //Proceso Hijo (coche)
        if (pid == 0) {
    		sleep(rand() % 5);
            coche();
            exit(EXIT_SUCCESS);
        }
    }
    

    //Espera por los procesos restantes
    for (i = 0; i < n_coches; i++) {
        wait(0);
    }
    
    kill(pidap1, 9);
    kill(pidap2, 9);
    kill(piddesap, 9);

    if (Borrar_Semaforo(semid) == 0) {
        printf("Error al borrar semaforo.\n");
        return (EXIT_FAILURE);
    }

    printf("Simulacion finalizada correctamente.\n");
    shmdt(aparcamiento);
    shmctl(shmid, IPC_RMID, NULL);
    msgctl (msqid, IPC_RMID, (struct msqid_ds *)NULL);

    return (EXIT_SUCCESS);
}
