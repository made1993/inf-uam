#include <stdio.h>
#include "y.tab.h"
extern int yyparse();
extern void yyerror();

int main(int argc, char **argv){
	extern FILE* yyin;
    extern FILE* yyout;
    extern char* yytext;
    extern int yyleng;
    int tok;
    yyin=fopen(argv[1],"r");
    yyout=fopen(argv[2],"w");
	yyparse();

}