#ifndef HASH_H 
#define HASH_H

typedef struct hash_node_s
{
    void* key;
    void* data;
    struct hash_node_s *next;
} hash_node_t;

typedef struct 
{
    hash_node_t *list;
    short count;
} hash_data_t;

typedef struct
{
    hash_data_t *table;
    int max_colitions;
    int size;
    int used;
    unsigned int (*hash_f)(void *k);
    int (*compare)(const void* a, const void* b); 
    void (*destroy_key)(void* a);
    void (*destroy_data)(void* a);
} hash_table_t;

hash_table_t *hash_create(unsigned int (hash_f)(void *k), int (*Compare)(const void* a, const void* b),void (*DestroyKey)(void* a),void (*DestroyInfo)(void* a),int initial_size);
hash_node_t *hash_insert(hash_table_t *table, void *key, void *data);
void hash_delete(hash_table_t *table, void *key);
hash_node_t *hash_find_node(hash_table_t *table, void *key);
void *hash_find_data(hash_table_t *table, void *key);
void hash_destroy(hash_table_t *table);
void hash_rebuild(hash_table_t *table, int new_size);


#endif
