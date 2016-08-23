#include <stdio.h>
#include "types.h"
#include "y.tab.h"
#include "hash.h"
void gc_msjerr(){
	fprintf(nasm, "ret\n" );
	fprintf(nasm, "error_1:\n");
	fprintf(nasm, "push dword mensaje_1\n");
	fprintf(nasm, "call print_string\n");
	fprintf(nasm, "add esp, 4\n");
	fprintf(nasm, "jmp near fin\n");

	fprintf(nasm, "error_2:\n");
	fprintf(nasm, "push dword mensaje_2\n");
	fprintf(nasm, "call print_string\n");
	fprintf(nasm, "add esp, 4\n");
	fprintf(nasm, "fin:ret\n");
}


void decvariables(hash_table_t *tglobal , char **declaraciones, int nvar){
	nodo * auxdato;
	int i=0;
	if((tglobal==NULL)||(declaraciones==NULL)||(nasm==NULL)){
		printf("ALGO ES NULL\n");
		return;
	}
	printf("decvariables1\n");
	fprintf(nasm, "segment .bss\n");
	for(i=0;i<nvar;i++){
		auxdato=hash_find_table(NULL,tglobal,declaraciones[i]);
		printf("decvariables2\n");
		if(auxdato==NULL){
			printf("ALGO HA IDO TERRIBLEMENTE MAL\n");
			return;
		}
		if(auxdato->clase==VECTOR){
			fprintf(nasm, "\t_%s resd %d\n",declaraciones[i],auxdato->tam);
		}
		else
			fprintf(nasm, "\t_%s resd 1\n",declaraciones[i]);
		
	}
	fprintf(nasm, "\n");


	fprintf(nasm, "segment .data\n");
	fprintf(nasm, "\tmensaje_1 db	\"Indice fuera de rango\", 0\n");
	fprintf(nasm, "\tmensaje_2	db \"Division por 0\", 0\n");
}

void decDatayFun(hash_table_t *tglobal , char **declaraciones, int nvar){

	fprintf(nasm, "segment .data\n");
	fprintf(nasm, "\tmensaje_1	\"Indice fuera de rango\", 0\n");
	fprintf(nasm, "\tmensaje_2	\"Division por 0\", 0\n");

	fprintf(nasm, "segment .text\n");
	
}
void gc_sumaenteros(int es_dir_1,int es_dir_2){
	fprintf(nasm, "\t\t\t;empieza gc_sumaenteros\n" );
	fprintf(nasm, ";cargar el segundo operando en edx\n");
	fprintf(nasm, "pop dword edx\n");
	if(es_dir_2==1){
		fprintf(nasm, "mov dword edx, [edx]\n");
	}
	fprintf(nasm, ";cargar el primer operando en eax\n");
	fprintf(nasm, "pop dword eax\n");
	if(es_dir_1==1){
		fprintf(nasm, "mov dword eax, [eax]\n");
	}
	fprintf(nasm,";realizar la suma y dejar el reslutado en eax\n");
	fprintf(nasm, "add eax, edx\n" );
	fprintf(nasm, ";apilar reslutado\n");
	fprintf(nasm, "push dword eax\n");
	return;

}
void gc_restaenteros(int es_dir_1,int es_dir_2){
	fprintf(nasm, ";cargar el segundo operando en edx\n");
	fprintf(nasm, "pop dword edx\n");
	if(es_dir_2==1){
		fprintf(nasm, "mov dword edx, [edx]\n");
	}
	fprintf(nasm, ";cargar el primer operando en eax\n");
	fprintf(nasm, "pop dword eax\n");
	if(es_dir_1==1){
		fprintf(nasm, "mov dword eax, [eax]\n");
	}
	fprintf(nasm,";realizar la resta y dejar el reslutado en eax\n");
	fprintf(nasm, "sub eax, edx\n" );
	fprintf(nasm, ";apilar reslutado\n");
	fprintf(nasm, "push dword eax\n");
	return;

}
void gc_diventeros(int es_dir_1,int es_dir_2){
	fprintf(nasm, ";cargar el segundo operando en ecx\n");
	fprintf(nasm, "pop dword ecx\n");
	if(es_dir_2==1){
		fprintf(nasm, "mov dword ecx, [ecx]\n");
	}
	fprintf(nasm, ";se comprueba si divisimos por 0\n");
	fprintf(nasm, "cmp ecx,0\n");
	fprintf(nasm, "je near error_2\n");
	
	fprintf(nasm, ";cargar el primer operando en eax\n");
	fprintf(nasm, "pop dword eax\n");
	if(es_dir_1==1){
		fprintf(nasm, "mov dword eax, [eax]\n");
	}
	fprintf(nasm, "mov dword edx, 0\n");
	fprintf(nasm,";realizar la Division y dejar el reslutado en eax\n");
	fprintf(nasm, "idiv ecx\n" );
	fprintf(nasm, ";apilar reslutado\n");
	fprintf(nasm, "push dword eax\n");
	return;

}
void gc_multenteros(int es_dir_1,int es_dir_2){
	fprintf(nasm, ";cargar el segundo operando en edx\n");
	fprintf(nasm, "pop dword edx\n");
	if(es_dir_2==1){
		fprintf(nasm, "mov dword edx, [edx]\n");
	}
	fprintf(nasm, ";cargar el primer operando en eax\n");
	fprintf(nasm, "pop dword eax\n");
	if(es_dir_1==1){
		fprintf(nasm, "mov dword eax, [eax]\n");
	}
	fprintf(nasm,";realizar la multiplicacion y dejar el reslutado en eax\n");
	fprintf(nasm, "imul eax, edx\n" );
	fprintf(nasm, ";apilar reslutado\n");
	fprintf(nasm, "push dword eax\n");
	return;

}

void gc_negenteros(int es_dir_1){
	
	fprintf(nasm, ";cargar el primer operando en eax\n");
	fprintf(nasm, "pop dword eax\n");
	if(es_dir_1==1){
		fprintf(nasm, "mov dword eax, [eax]\n");
	}
	fprintf(nasm,";realizar la negacion y dejar el reslutado en eax\n");
	fprintf(nasm, "neg eax\n" );
	fprintf(nasm, ";apilar reslutado\n");
	fprintf(nasm, "push dword eax\n");
	return;

}

void gc_andlogica(int es_dir_1,int es_dir_2){
	fprintf(nasm, ";cargar el segundo operando en edx\n");
	fprintf(nasm, "pop dword edx\n");
	if(es_dir_2==1){
		fprintf(nasm, "mov dword edx, [edx]\n");
	}
	fprintf(nasm, ";cargar el primer operando en eax\n");
	fprintf(nasm, "pop dword eax\n");
	if(es_dir_1==1){
		fprintf(nasm, "mov dword eax, [eax]\n");
	}
	fprintf(nasm,";realizar la AND y dejar el reslutado en eax\n");
	fprintf(nasm, "and eax, edx\n" );
	fprintf(nasm, ";apilar reslutado\n");
	fprintf(nasm, "push dword eax\n");
	return;
}


void gc_orlogica(int es_dir_1,int es_dir_2){
	fprintf(nasm, ";cargar el segundo operando en edx\n");
	fprintf(nasm, "pop dword edx\n");
	if(es_dir_2==1){
		fprintf(nasm, "mov dword edx, [edx]\n");
	}
	fprintf(nasm, ";cargar el primer operando en eax\n");
	fprintf(nasm, "pop dword eax\n");
	if(es_dir_1==1){
		fprintf(nasm, "mov dword eax, [eax]\n");
	}
	fprintf(nasm,";realizar la OR y dejar el reslutado en eax\n");
	fprintf(nasm, "or eax, edx\n" );
	fprintf(nasm, ";apilar reslutado\n");
	fprintf(nasm, "push dword eax\n");
	return;
}


void gc_neglogica(int es_dir_1, int etiqueta){
	
	fprintf(nasm, ";cargar el primer operando en eax\n");
	fprintf(nasm, "pop dword eax\n");
	if(es_dir_1==1){
		fprintf(nasm, "mov dword eax, [eax]\n");
	}
	fprintf(nasm,";ver si eax es 0 y enese caso salto a negar_falso\n");
	fprintf(nasm, "or eax,eax\n" );
	fprintf(nasm, "jz near negar_falso%d\n",etiqueta );
	fprintf(nasm, ";cargar 0 en eax (negacion de verdadero) y saltar al final\n" );
	fprintf(nasm, "mov dword eax ,0\n");
	fprintf(nasm, "jmp near fin_negacion%d\n",etiqueta);
	fprintf(nasm, ";cargar 1 en eax \n" );
	fprintf(nasm, "negar_fals%d:\t mov dword eax ,1 \n",etiqueta );
	fprintf(nasm, "fin_negacion%d:\t push dword eax\n",etiqueta );

	return;

}

void gc_igual(int es_dir_1,int es_dir_2, int etiqueta){
	fprintf(nasm, ";cargar la segunda expresion en edx\n");
	fprintf(nasm, "pop dword edx\n");
	if(es_dir_2==1){
		fprintf(nasm, "mov dword edx, [edx]\n");
	}
	fprintf(nasm, ";cargar la primera expresion en eax\n");
	fprintf(nasm, "pop dword eax\n");
	if(es_dir_1==1){
		fprintf(nasm, "mov dword eax, [eax]\n");
	}
	fprintf(nasm,";comparar y apilar el resultado\n");
	fprintf(nasm, "cmp eax, edx\n" );
	fprintf(nasm, "je near igual%d\n",etiqueta );
	fprintf(nasm, "push dword 0\n");
	fprintf(nasm, "jmp near fin_igual%d\n",etiqueta );
	fprintf(nasm, "igual%d:\tpush dword 1\n",etiqueta );
	fprintf(nasm, "fin_igual%d:\n",etiqueta );
	return;
}
void gc_distinto(int es_dir_1,int es_dir_2, int etiqueta){
	fprintf(nasm, ";cargar la segunda expresion en edx\n");
	fprintf(nasm, "pop dword edx\n");
	if(es_dir_2==1){
		fprintf(nasm, "mov dword edx, [edx]\n");
	}
	fprintf(nasm, ";cargar la primera expresion en eax\n");
	fprintf(nasm, "pop dword eax\n");
	if(es_dir_1==1){
		fprintf(nasm, "mov dword eax, [eax]\n");
	}
	fprintf(nasm,";comparar y apilar el resultado\n");
	fprintf(nasm, "cmp eax, edx\n" );
	fprintf(nasm, "jne near distinto%d\n",etiqueta );
	fprintf(nasm, "push dword 0\n");
	fprintf(nasm, "jmp near fin_distinto%d\n",etiqueta );
	fprintf(nasm, "distinto%d:\tpush dword 1\n",etiqueta );
	fprintf(nasm, "fin_distinto%d:\n",etiqueta );
	return;
}
void gc_menorigual(int es_dir_1,int es_dir_2,int etiqueta){
	fprintf(nasm, ";cargar la segunda expresion en edx\n");
	fprintf(nasm, "pop dword edx\n");
	if(es_dir_2==1){
		fprintf(nasm, "mov dword edx, [edx]\n");
	}
	fprintf(nasm, ";cargar la primera expresion en eax\n");
	fprintf(nasm, "pop dword eax\n");
	if(es_dir_1==1){
		fprintf(nasm, "mov dword eax, [eax]\n");
	}
	fprintf(nasm,";comparar y apilar el resultado\n");
	fprintf(nasm, "cmp eax, edx\n" );
	fprintf(nasm, "jle near menorigual%d\n",etiqueta );
	fprintf(nasm, "push dword 0\n");
	fprintf(nasm, "jmp near fin_menorigual%d\n",etiqueta );
	fprintf(nasm, "menorigual%d:\tpush dword 1\n",etiqueta );
	fprintf(nasm, "fin_menorigual%d:\n",etiqueta );
	return;
}

void gc_mayorigual(int es_dir_1,int es_dir_2, int etiqueta){
	fprintf(nasm, ";cargar la segunda expresion en edx\n");
	fprintf(nasm, "pop dword edx\n");
	if(es_dir_2==1){
		fprintf(nasm, "mov dword edx, [edx]\n");
	}
	fprintf(nasm, ";cargar la primera expresion en eax\n");
	fprintf(nasm, "pop dword eax\n");
	if(es_dir_1==1){
		fprintf(nasm, "mov dword eax, [eax]\n");
	}
	fprintf(nasm,";comparar y apilar el resultado\n");
	fprintf(nasm, "cmp eax, edx\n" );
	fprintf(nasm, "jge near mayorigual%d\n",etiqueta );
	fprintf(nasm, "push dword 0\n");
	fprintf(nasm, "jmp near fin_mayorigual%d\n",etiqueta );
	fprintf(nasm, "mayorigual%d:\tpush dword 1\n",etiqueta );
	fprintf(nasm, "fin_mayorigual%d:\n",etiqueta );
	return;
}
void gc_menor(int es_dir_1,int es_dir_2, int etiqueta){
	fprintf(nasm, ";cargar la segunda expresion en edx\n");
	fprintf(nasm, "pop dword edx\n");
	if(es_dir_2==1){
		fprintf(nasm, "mov dword edx, [edx]\n");
	}
	fprintf(nasm, ";cargar la primera expresion en eax\n");
	fprintf(nasm, "pop dword eax\n");
	if(es_dir_1==1){
		fprintf(nasm, "mov dword eax, [eax]\n");
	}
	fprintf(nasm,";comparar y apilar el resultado\n");
	fprintf(nasm, "cmp eax, edx\n" );
	fprintf(nasm, "jl near menor%d\n",etiqueta );
	fprintf(nasm, "push dword 0\n");
	fprintf(nasm, "jmp near fin_menor%d\n",etiqueta );
	fprintf(nasm, "menor%d:\tpush dword 1\n",etiqueta );
	fprintf(nasm, "fin_menor%d:\n",etiqueta );
	return;
}
void gc_mayor(int es_dir_1,int es_dir_2, int etiqueta){
	fprintf(nasm, ";cargar la segunda expresion en edx\n");
	fprintf(nasm, "pop dword edx\n");
	if(es_dir_2==1){
		fprintf(nasm, "mov dword edx, [edx]\n");
	}
	fprintf(nasm, ";cargar la primera expresion en eax\n");
	fprintf(nasm, "pop dword eax\n");
	if(es_dir_1==1){
		fprintf(nasm, "mov dword eax, [eax]\n");
	}
	fprintf(nasm,";comparar y apilar el resultado\n");
	fprintf(nasm, "cmp eax, edx\n" );
	fprintf(nasm, "jg near mayor%d\n",etiqueta );
	fprintf(nasm, "push dword 0\n");
	fprintf(nasm, "jmp near fin_mayor%d\n",etiqueta );
	fprintf(nasm, "mayor%d:\tpush dword 1\n",etiqueta );
	fprintf(nasm, "fin_mayor%d:\n",etiqueta );
	return;
}
