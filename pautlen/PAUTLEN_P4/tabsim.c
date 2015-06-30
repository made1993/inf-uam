#include "tabsim.h"

unsigned int jenkins_one_at_a_time_hash(void *vkey) {
    unsigned hash = 0;
    size_t i;
    unsigned char *key;
    int key_len;


    key = (unsigned char *) vkey;

    key_len = strlen(key);

    for (i = 0; i < key_len; i++) {
        hash += key[i];
        hash += (hash << 10);
        hash ^= (hash >> 6);
    }
    hash += (hash << 3);
    hash ^= (hash >> 11);
    hash += (hash << 15);
    return hash;
}

int scmp(const void *a1, const void *a2) {
    char *s1, *s2;
    s1 = (char *) a1;
    s2 = (char *) a2;

    if (strcmp(s1, s2) < 0) return -1;
    if (strcmp(s1, s2) == 0) return 0;

    return 1;
}

void nulldes(void *a) {

}

void destr_key(void *s) {
    free((char *) s);
}
void* hash_insert_table_global( hash_table_t* t_global, char * key, void* data){
    hash_node_t *node=NULL;
	if ((t_global == NULL) || (key == NULL))
        return NULL;
 	node=hash_find_node(t_global, key);
    if ( (node!= NULL)&&(strcmp((char*)node->key, key)==0)){
        return NULL;
    }
    hash_insert(t_global, key, data);
    return data;
}
void* hash_insert_table(hash_table_t* t_local, hash_table_t* t_global, char * key, void* data) {
    hash_node_t *node=NULL;
    if ((t_global == NULL) || (key == NULL))
        return NULL;
    if (t_local != NULL) {
        if ((hash_find_data(t_local, key)) != NULL) {
            return NULL;
        }
        hash_insert(t_local, key, data);
        return (void*)data;
    }
    node=hash_find_node(t_global, key);
    if ( (node!= NULL)&&(strcmp((char*)node->key, key)==0)){
        return NULL;
    }
    hash_insert(t_global, key, data);
    return (void*) data;

}

void* hash_find_table(hash_table_t* t_local, hash_table_t* t_global, char * key) {
    void *ret;
    if ((t_global == NULL) || (key == NULL)) {
        printf("tglobal o key ==NULL\n" );
        return NULL;
    }
    if (t_local != NULL) {
        if ((ret = (int*) hash_find_data(t_local, key)) != NULL)
            return ret;
    }
    
    ret = (int*) hash_find_data(t_global, key);
    if (ret != NULL) {
        return ret;
    }
    return NULL;
}

hash_table_t* hash_create_t_local() {
    hash_table_t* table = NULL;
    table = hash_create(jenkins_one_at_a_time_hash, scmp, destr_key, nulldes, 1);
    return table;
}

void hash_destroy_table(hash_table_t* table) {
    if (table == NULL)
        return;
    hash_destroy(table);
    table = NULL;

}