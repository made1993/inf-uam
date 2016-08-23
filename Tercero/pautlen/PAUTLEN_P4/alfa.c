
#include "types.h"
#include "tabsim.h"
extern int yyparse();
extern void yyerror();

hash_table_t * tlocal;
hash_table_t * tglobal;

int main(int argc, char **argv){
	extern FILE* yyin;
    extern FILE* yyout;
    extern char* yytext;
    extern int yyleng;
    int tok;
    nasm=NULL;
    nasm =fopen(argv[2],"w");
    yyin=fopen(argv[1],"r");
    printf("hola mundo\n");
    //yyout=fopen(argv[2],"w");

	yyparse();

}