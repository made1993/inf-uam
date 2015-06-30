#ifndef TABSIM_H 
#define TABSIM_H
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "hash.h"
	unsigned int jenkins_one_at_a_time_hash(void *vkey);
	int scmp(const void *a1, const void *a2);
	void nulldes(void *a);
	void destr_key(void *s);
	void* hash_insert_table(hash_table_t* t_local,hash_table_t* t_global,char * key,void* data);
	void* hash_find_table(hash_table_t* t_local,hash_table_t* t_global,char* key);
	hash_table_t* hash_create_t_local();
	void hash_destroy_table(hash_table_t* table);
#endif
