#include "../includes/G-2313-07-P3linkedList.h"

/**
 * @brief Create a new list. 
 * @param l Method which will be used to compare nodes
 * @return The new list if all was OK, NULL otherwise.
 */
 
LinkedList * create_list(comparator comparacion) {
    if(comparacion == NULL) return NULL;
    
    LinkedList * lista = (LinkedList *) malloc(sizeof(LinkedList));
    if(lista == NULL) return NULL;
 
    lista->cmp = comparacion;
    lista->first = NULL;
    lista->last = NULL;
 
    return lista;
}


void *  destroy_node(Node * n) {
    n->data = NULL;
    n->next = NULL;
    free(n);
    n = NULL;
    
    return (void*) n;
}


/**
 * @page delete_elem_list \b delete_elem_list
 *
 * @brief Elimina el elemento de la lista
 *
 * @section SYNOPSIS
 *  \b #include \b "/includes/lista.h"
 *  \b lib/libLista.a 
 *
 *  int \b delete_elem_list ( Lista * l, void * elem )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Esta funcion elimina el elemento elem de la lista l si este elemento esta en la citada lista.
 * 
 * Recibe:
 *    - la lista de la cual se quiere eliminar el elemento
 *    - el elemento a eliminar
 *
 * @section retorno RETORNO
 * Devuelve un int que corresponde a si se elimino el elemento o no.
 * 1 Si se elimino, 0 si no.
 *
 * @section seealso VER TAMBIÉN
 * \b destroy_node(3), \b destroy_list(3).
 *
 * @section authors AUTOR
 * Manuel Reyes (manuel.reyes@estudiante.uam.es)
 * Jorge Guillen (jorge.guillen@estudiante.uam.es)
*/
 
int delete_elem_list(LinkedList * l, void * elem){
    Node * node;
    Node * node_anterior;
    /*Error en el paso de argumentos*/
    if (l == NULL || elem == NULL){
        return -1;
    }
    /*La lista esta vacia*/
    if (is_empty_list(l)==TRUE){
        return -1;
    }
 
    /*Si el elemento fuera el primero de todos*/
    node = l->first;
    if(l->cmp(elem, node->data)==TRUE){
        l->first = node->next;
        node = destroy_node(node);
        return 1;
    }
    /*Busqueda del elemento*/
    node_anterior = l->first;
    node = l->first->next;
    while(node!=NULL){
        if(l->cmp(elem, node->data)==TRUE){ 
            /*Hemos encontrado el elemento*/
            if(node->next==NULL){
                /*El elemento es el ultimo, redefinimos last*/
                l->last = node_anterior;
            }
            node_anterior->next = node->next;
            node = destroy_node(node);
            return 1;
        }
        /*No era el objetivo, continuamos buscando*/
        node_anterior = node;
        node = node->next;
    }
    /*El elemento no ha sido encontrado*/
    return 0;
}



/**
 * @brief Tells if the list is empty or not
 * @param l List to check
 * @return -1 if an error happened, TRUE if empty or FALSE if not empty
 */
int is_empty_list(LinkedList * l) {
    if(l == NULL){
        return -1;
    }
    if(l->first == NULL){
        return TRUE;
    }
    return FALSE;
}



/**
 * @page find \b find
 *
 * @brief This function is used when you want to find something in the list
 *
 * @section SYNOPSIS
 *  \b #include \b ""
 *
 *  \b void \b find \b (\b void *\b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * When you want to find some element than you introduced in the list
 * 
 * 
 * You have to pass the key of the element that you want to find, and the list where the element is.
 *
 * @section retorno RETORNO
 * Returns the element
 *
 * @section seealso VER TAMBIÉN
 * 
 * @section authors AUTOR
 * Silvia Anguita (silvia.anguita@estudiante.uam.es)
 * Ángel Fuente (angel.fuente@estudiante.uam.es)
*/

void* find(void *clave, LinkedList *l) {
    Node *nodoaux = NULL;

    if (!clave || !l) {
        syslog(LOG_ERR, "Error al buscar un elemento en la lista, debido a un puntero nulo");
        exit(EXIT_FAILURE);
    }


    for (nodoaux = l->first; nodoaux != NULL; nodoaux = nodoaux->next) {
        if (l->cmp(clave, nodoaux->data) == TRUE) {
            return nodoaux->data;
        }
    }
    return NULL;
}


/**
 * @page insert_list \b insert_list
 *
 * @brief This function is used when you want to find something in the list
 *
 * @section SYNOPSIS
 *  \b #include \b ""
 *
 *  \b int \b insert_list \b (\b LinkedList * l, \bvoid * elem \b )
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * When you want to find some element than you introduced in the list
 * 
 * 
 * You have to pass the key of the element that you want to find, and the list where the element is.
 *
 * @section retorno RETORNO
 * Returns -1 on error 1 on succes
 *
 * @section seealso VER TAMBIÉN
 * 
 * @section authors AUTOR
 * Daniel Garduno 
 * Alexandur Costinel 
*/

int insert_list(LinkedList * l, void * elem){
    Node * node;
    if (l == NULL || elem == NULL){
        return -1;
    }
    node = (Node *) malloc(sizeof (Node));
    if (node == NULL){
        return -1;
    }
    node->data = elem;
    node->next = NULL;
    if (is_empty_list(l)==TRUE){
        l->first = node;
        l->last = node;
        return 1;
    }
    l->last->next = node;
    l->last = node;
    return 1;
}




/**
 * @page destroy_all_nodes \b destroy_all_nodes
 *
 * @brief Elimina una lista.
 *
 * @section SYNOPSIS
 *  \b #include \b "/includes/lista.h"
 *  \b lib/libLista.a 
 *
 *  int \b destroy_all_nodes (Nodo * first)
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Elimina el nodo "first" y todos los que le siguen.
 * 
 * Recibe:
 *    - nodo
 *
 * @section retorno RETORNO
 * 0 siempre.
 *
 * @section seealso VER TAMBIÉN
 * \b destroy_list(3)
 *
 * @section authors AUTOR
 * Nestor Campillo (nestor.campillo@estudiante.uam.es)
 * Adrian Bueno (adrian.buenoj@estudiante.uam.es)
*/
int destroy_all_nodes (Node * first){
    if (first != NULL){ 
        destroy_all_nodes(first->next);
        
        first = destroy_node(first);
    }
    return 0;
}


/**
 * @page destroy_list \b destroy_list
 *
 * @brief Elimina una lista.
 *
 * @section SYNOPSIS
 *  \b #include \b "/includes/lista.h"
 *  \b lib/libLista.a 
 *
 *  int \b destroy_list (Lista * lista)
 * 
 * @section descripcion DESCRIPCIÓN
 *
 * Elimina una lista entera.
 * 
 * Recibe:
 *    - lista a eliminar
 *
 * @section retorno RETORNO
 * 0 si se elimino correctamente, -1 si ocurrio un error.
 *
 * @section seealso VER TAMBIÉN
 * \b destroy_all_nodes(3)
 *
 * @section authors AUTOR
 * Nestor Campillo (nestor.campillo@estudiante.uam.es)
 * Adrian Bueno (adrian.buenoj@estudiante.uam.es)
*/
int destroy_list (LinkedList * lista){
 
    if (lista == NULL)
        return -1;
 
    if (is_empty_list(lista) == FALSE){
        destroy_all_nodes(lista->first);
    }
 
    lista->first = NULL;
    lista->last = NULL;
    lista->cmp = NULL;
    free(lista);
 
    return 0;
}
