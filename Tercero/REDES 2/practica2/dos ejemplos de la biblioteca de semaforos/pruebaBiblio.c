/* 
 * File:   main.c
 * Author: jorge
 *
 * Created on March 24, 2014, 10:21 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include "semaforos.h"

/*
 * 
 */
int main(int argc, char** argv) {
    int semid;
    int active[4]= {1,1,1,1};
    unsigned short array[4] = {0,0,1,0};
    
    assert(Crear_Semaforo(12345,4,&semid)!=ERROR);
    
    assert(Inicializar_Semaforo(semid, array)!=ERROR);
    
    semctl(semid, 4, GETALL, arg);  //Ver los valores de los semaforos
    
    assert(arg.array[1]==0);
    
    assert(Up_Semaforo(semid,1,SEM_UNDO)!=ERROR);
    
    assert(Down_Semaforo(semid,2,SEM_UNDO)!=ERROR);
    
    semctl(semid, 4, GETALL, arg);  //Ver los valores de los semaforos
    
    assert(arg.array[1]==1);
    assert(arg.array[2]==0);
    
    assert(UpMultiple_Semaforo(semid,4,SEM_UNDO,active)!=ERROR);
    
    semctl(semid, 4, GETALL, arg);  //Ver los valores de los semaforos
    
    assert(arg.array[0]==1);
    assert(arg.array[1]==2);
    assert(arg.array[2]==1);
    assert(arg.array[3]==1);
    
    assert(DownMultiple_Semaforo(semid,4,SEM_UNDO,active)!=ERROR);
    
    semctl(semid, 4, GETALL, arg);  //Ver los valores de los semaforos
    
    assert(arg.array[0]==0);
    assert(arg.array[1]==1);
    assert(arg.array[2]==0);
    assert(arg.array[3]==0);
    
    assert(Borrar_Semaforo(semid)!=ERROR);

    return (EXIT_SUCCESS);
}

