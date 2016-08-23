#include <stdio.h>
#include <stdlib.h>


extern int yylex();

int main(int argc, char** argv)
{
	int fila=1;
	int columna=1;
	int enable=0;
	extern FILE* yyin;
    extern FILE* yyout;
    extern char* yytext;
    extern int yyleng;
    yyin=NULL;
    yyout=NULL;
	yyin=fopen(argv[1],"r");
	if(yyin==NULL){
		printf("Error al abrir el fichero\n"); 
		return 0;
	}
	yyout=fopen(argv[2],"w");
	if(yyout==NULL){
		printf("Error al abrir el fichero\n"); 
		return 0;
	}

	int tok=yylex();
    while(tok!=0){
        switch(tok){
			case TOK_ACENTO:
				switch (yytext[1]){
				case -95:
	 				fprintf(yyout,"%c",'a');
				break;
				case -87:
	 				fprintf(yyout,"%c",'e');
				break;
				case -83:
	 				fprintf(yyout,"%c",'i');
				break;
				case -77:
	 				fprintf(yyout,"%c",'o');
				break;
				case -70:
	 				fprintf(yyout,"%c",'u');
				break;
				case -127:
	 				fprintf(yyout,"%c",'a');
				break;
				case -119:
	 				fprintf(yyout,"%c",'e');
				break;
				case -115:
	 				fprintf(yyout,"%c",'i');
				break;
				case -109:
	 				fprintf(yyout,"%c",'o');
				break;
				case -102:
					fprintf(yyout,"%c",'u');
				break;
			}
			break;
			case TOK_LETRA:
				fprintf(yyout,"%s",yytext);
			break;
			case TOK_MAYUSCULA:
				fprintf(yyout,"%c",yytext[0]+32);
			break;
			
			case TOK_CARACTER:
			break;
		
		}
		if(enable==1){
			enable=0;
		}
		else{
			columna+=yyleng;
		}
		enable=0;
		tok=yylex();
	}
	fclose(yyin);
	fclose(yyout);

	return 1;
	}