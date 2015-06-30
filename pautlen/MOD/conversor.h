#ifndef CONVERSOR_H 
#define CONVERSOR_H
void gc_msjerr();
void decvariables(hash_table_t *tglobal , char **declaraciones, int nvar);
void decDatayFun(hash_table_t *tglobal , char **declaraciones, int nvar);
void gc_sumaenteros(int es_dir_1,int es_dir_2);
void gc_restaenteros(int es_dir_1,int es_dir_2);
void gc_diventeros(int es_dir_1,int es_dir_2);
void gc_multenteros(int es_dir_1,int es_dir_2);
void gc_negenteros(int es_dir_1);
void gc_andlogica(int es_dir_1,int es_dir_2);
void gc_orlogica(int es_dir_1,int es_dir_2);
void gc_neglogica(int es_dir_1, int etiqueta);
void gc_igual(int es_dir_1,int es_dir_2, int etiqueta);
void gc_distinto(int es_dir_1,int es_dir_2, int etiqueta);
void gc_menorigual(int es_dir_1,int es_dir_2,int etiqueta);
void gc_mayorigual(int es_dir_1,int es_dir_2, int etiqueta);
void gc_menor(int es_dir_1,int es_dir_2, int etiqueta);
void gc_mayor(int es_dir_1,int es_dir_2, int etiqueta);
#endif