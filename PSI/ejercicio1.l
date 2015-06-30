%{
#include <stdio.h> 

#define TOK_FIJO 1
#define TOK_MOVIL 2
#define TOK_ADD 3
#define TOK_INFO 4
#define TOK_INT 5
#define TOK_SP 6
#define TOK_MIN 7
#define INVALID_TOK -1


%}
DIGITO [0-9]

%option noyywrap


%%
9[1-9]({DIGITO}{7}) {return TOK_FIJO;}
6({DIGITO}{8}) {return TOK_MOVIL;}
(803|806|807|901|902|905)({DIGITO}{6}) {return TOK_ADD;}
11({DIGITO}{3}) {return TOK_INFO;}
"+"({DIGITO}{2})(6|9)({DIGITO}{8}) {return TOK_INT;}
(112|091|061|085) {return TOK_SP;}
{DIGITO}+"."{DIGITO}+ {return TOK_MIN;}


[\t] {return INVALID_TOK;}
[\n] {return INVALID_TOK;}
. {return INVALID_TOK;}


%%

int main(int argc, char ** argv){
	int tok, n_fijo=0, n_movil=0, n_add=0, n_int=0, n_info=0, n_sp=0;
	float t_fijo=0, t_movil=0, t_add=0, t_int=0, t_info=0, t_sp=0;
	float p_fijo=0, p_movil=0, p_add=0, p_int=0, p_info=0, p_sp=0;

	yyin = fopen(argv[1], "r");
	
	tok = yylex();
	while(tok != 0){
		switch(tok){
			case TOK_FIJO:
				n_fijo++;
				while ((tok = yylex()) != TOK_MIN);
				t_fijo += atof(yytext);
				break;

			case TOK_MOVIL:
				n_movil++;
				while ((tok = yylex()) != TOK_MIN);
				t_movil += atof(yytext);
				break;

			case TOK_ADD:
				n_add++;
				while ((tok = yylex()) != TOK_MIN);
				t_add += atof(yytext);
				break;

			case TOK_INT:
				n_int++;
				while ((tok = yylex()) != TOK_MIN);
				t_int += atof(yytext);
				break;

			case TOK_INFO:
				n_info++;
				while ((tok = yylex()) != TOK_MIN);
				t_info += atof(yytext);
				break;

			case TOK_SP:
				n_sp++;
				while ((tok = yylex()) != TOK_MIN);
				t_sp += atof(yytext);
				break;

			default:
				break;
		}
		tok = yylex();
	}

	p_fijo = 0.038 * t_fijo;
	p_movil = 0.081 * t_movil;
	p_add = 1.005 * t_add;
	p_int = 0.45 * t_int;
	p_info = 0.16 * t_info;

	printf("Números fijos:  %d llamadas, %f minutos, %f euros.\n", n_fijo, t_fijo, p_fijo);
	printf("Móviles:  %d llamadas, %f minutos, %f euros.\n", n_movil, t_movil, p_movil);
	printf("Llamadas internacionales:  %d llamadas, %f minutos, %f euros.\n", n_int, t_int, p_int);
	printf("Números de informacion:  %d llamadas, %f minutos, %f euros.\n", n_info, t_info, p_info);
	printf("Números cortos:  %d llamadas, %f minutos, %f euros.\n", n_sp, t_sp, p_sp);
	printf("Números de tarificacion adicional:  %d llamadas, %f minutos, %f euros.\n", n_add, t_add, p_add);

	return 0;
}



