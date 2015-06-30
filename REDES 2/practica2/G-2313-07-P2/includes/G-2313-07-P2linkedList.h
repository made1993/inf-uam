#ifndef LIST_H

#include <stdio.h>
#include <stdlib.h>
#include <syslog.h>

#define TRUE 1
#define FALSE 0
 
typedef int (*comparator)(const void *a, const void *b);
 
typedef struct Node{
    void * data;
    struct Node * next;
} Node;

typedef struct {
    comparator cmp;
    Node * first;
    Node * last;
} LinkedList;


LinkedList * create_list(comparator comparacion);

void *  destroy_node(Node * n);

int delete_elem_list(LinkedList * l, void * elem);

int is_empty_list(LinkedList * l);

void* find(void *clave, LinkedList *l);

int insert_list(LinkedList * l, void * elem);

int destroy_all_nodes (Node * first);

int destroy_list (LinkedList * lista);
 

 
#endif 

