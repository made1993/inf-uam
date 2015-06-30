/*%{
#include <stdio.h>
#include "types.h"
#include "tabsim.h"
extern FILE * yyout;
extern int col , ln,err;
hash_table_t * tlocal=NULL;
hash_table_t * tglobal=NULL;
int tipo_actual;
int clase_actual=0;
int tamanio_vector_actual;
int pos_variable_local_actual;
nodo *auxdato;
nodo dato;
int n;
int i;
char ** decvar=NULL;
int l_exp=0;
int num_variables_local_actual =0;
int pos_variable_local_actual=0;
int num_parametros_actual=0;
int pos_parametro_actual=0;
int num_parametros_llamada_actual=0;
int en_explist=0;
int nvar=0;
int etiqueta=0;
int nfunc=0;
char **decfunc=NULL;
%}
%code requires {
	#define VARIABLE 1
	#define PARAM 2
	#define FUNCION 3
	

}*/
%{
#include <stdio.h>
#include "types.h"
#include "tabsim.h"
extern FILE * yyout;
extern int col , ln,err;
hash_table_t * tlocal=NULL;
hash_table_t * tglobal=NULL;
int tipo_actual;
int clase_actual=0;
int tamanio_vector_actual;
int pos_variable_local_actual;
nodo *auxdato;
nodo dato;
int n;
int i,j;
char ** decvar=NULL;
int l_exp=0;
int num_variables_local_actual =0;
int pos_variable_local_actual=0;
int num_parametros_actual=0;
int pos_parametro_actual=0;
int num_parametros_llamada_actual=0;
int en_explist=0;
int nvar=0;
int etiqueta=0;
int nfunc=0;
int *cases=NULL;
int *inits=NULL;
int ncase;
int nexps;
int k=0;
char **decfunc=NULL;
%}
%code requires {
	#define VARIABLE 1
	#define PARAM 2
	#define FUNCION 3
	
}



%union {
    tipo_atributos atributos;
    int num;
    char* nmfun;
}
%token TOK_MAIN                
%token TOK_INT                 
%token TOK_BOOLEAN             
%token TOK_ARRAY               
%token TOK_FUNCTION            
%token TOK_IF                  
%token TOK_ELSE                
%token TOK_WHILE               
%token TOK_SCANF               
%token TOK_PRINTF              
%token TOK_RETURN              
%token TOK_PUNTOYCOMA          
%token TOK_COMA                
%token TOK_PARENTESISIZQUIERDO 
%token TOK_PARENTESISDERECHO   
%token TOK_CORCHETEIZQUIERDO   
%token TOK_CORCHETEDERECHO     
%token TOK_LLAVEIZQUIERDA      
%token TOK_LLAVEDERECHA        
%token TOK_ASIGNACION          
%token TOK_MAS                 
%token TOK_MENOS               
%token TOK_DIVISION            
%token TOK_ASTERISCO           
%token TOK_AND                 
%token TOK_OR                  
%token TOK_NOT                 
%token TOK_IGUAL               
%token TOK_DISTINTO            
%token TOK_MENORIGUAL          
%token TOK_MAYORIGUAL          
%token TOK_MENOR               
%token TOK_MAYOR               
%token <atributos> TOK_IDENTIFICADOR       
%token <atributos>TOK_CONSTANTE_ENTERA    
%token TOK_TRUE                
%token TOK_FALSE
%token TOK_ERROR
%type <atributos> condicional
%type <atributos> bucle
%type <atributos> while_exp
%type <atributos> while
%type <atributos> if_exp_sentencias
%type <atributos> if_exp
%type <atributos> idf_llamada_funcion
%type <atributos> elemento_vector
%type <atributos> comparacion
%type <atributos> constante
%type <atributos> constante_logica
%type <atributos> constante_entera
%type <atributos> exp
%type <atributos> fn_name
%type <atributos> ipdf
%type <atributos> identificador
%type <atributos> decidentificador
%type <atributos> tipo
%type <atributos> clase_escalar
%left TOK_ASIGNACION
%left TOK_OR 
%left TOK_AND
%left TOK_IGUAL TOK_DISTINTO
%left TOK_MAYOR TOK_MENOR TOK_MAYORIGUAL TOK_MENORIGUAL
%left TOK_MAS TOK_MENOS
%left TOK_DIVISION TOK_ASTERISCO 
%left TOK_NOT
%left UMINUS
%left TOK_PARENTESISIZQUIERDO TOK_PARENTESISDERECHO TOK_CORCHETEIZQUIERDO TOK_CORCHETEDERECHO

%start programa
%%
programa: inicio_tabla TOK_MAIN TOK_LLAVEIZQUIERDA declaraciones escritura_TS funciones escritura_main sentencias TOK_LLAVEDERECHA fin_tabla 
		{printf(";R1:\t<programa> ::= main { <declaraciones> <funciones> <sentencias> }\n");}
escritura_TS: {
				printf("escritura_TS\n");
				decvariables(tglobal , decvar, nvar);

				fprintf(nasm, "segment .text\n");
				fprintf(nasm, "global main\n");
				fprintf(nasm, "extern scan_int, scan_boolean\n");
				fprintf(nasm, "extern print_int, print_boolean, print_string,print_blank,print_endofline\n");
			}
escritura_main: {

			fprintf(nasm, "main:\n");

};
inicio_tabla: {
			if (tglobal!=NULL)
				return;
			printf("\t\tSe crea la tabla global");
			tglobal= hash_create_t_local();
			printf(":%d\n",tglobal);
			decvar=(char**)malloc(20);
			decfunc=(char**)malloc(20);
			if(tglobal==NULL){
				return;
			}
		}
fin_tabla:{
			gc_msjerr();
			printf("\t\t se destruye la tabla global\n");
			hash_destroy_table(tglobal);
			return;
			}
declaraciones: declaracion
		{printf(";R2:\t<declaraciones> ::= <declaracion>\n");}
	| declaracion  declaraciones
		{printf(";R3:\t<declaraciones> ::= <declaracion> <declaraciones>\n");}
declaracion:  clase identificadores TOK_PUNTOYCOMA
		{printf(";R4:\t<declaracion> ::= <clase> <identificadores> ;\n");
		}
clase: clase_escalar
		{printf(";R5:\t<clase> ::= <clase_escalar>\n");clase_actual = ESCALAR;}
	| clase_vector
		{printf(";R7:\t<clase> ::= <clase_vector>\n");clase_actual = VECTOR;}
clase_escalar: tipo
		{printf(";R9:\t<clase_escalar> ::= <tipo> ==");
			if ($1.tipo==BOOL)
				printf("BOOL\n");

			if ($1.tipo==INT)
				printf("INT\n");
			tipo_actual=$1.tipo;
			printf("\t\ttipoactual:%d\n", tipo_actual);
			}
tipo: TOK_INT
		{printf(";R10:\t<tipo> ::= int\n");
			$$.tipo=INT;
			printf("\t\tint=%d\n",INT);
		}
	|TOK_BOOLEAN
		{printf(";R11:\t<tipo> ::= boolean\n");
			$$.tipo=BOOL;
		}
clase_vector:TOK_ARRAY tipo  TOK_CORCHETEIZQUIERDO constante_entera TOK_CORCHETEDERECHO
		{tamanio_vector_actual=$4.valor_entero;
		if(tamanio_vector_actual<1){
			fprintf(stderr,"****ERROR semantico en lin %d: el tamanyo del vector%s excede los limites permitidos (1,64).\n",ln,$4.lexema);
			return;
			
		}
		if(tamanio_vector_actual>64){
			
			fprintf(stderr,"****ERROR semantico en lin %d: el tamanyo del vector%s excede los limites permitidos (1,64).\n",ln,$4.lexema);
			return;
			
		}
			;printf(";R15:\t<clase_vector> ::= array <tipo> [ <constante_entera> ] == %d\n", tamanio_vector_actual);}
identificadores: decidentificador
		{printf(";R18:\t<identificadores> ::= <identificador>\n");

			/*printf("\t\tlexema:%s  tipo:%d\n", auxdato->lexema, auxdato->tipo);
			if(NULL==hash_insert_table(tlocal,tglobal, $1.lexema,auxdato)){
				fprintf(stderr,"****ERROR semantico en lin %d: Declaracion duplicada.\n",ln);
			return;
			}*/
		}


	|decidentificador TOK_COMA identificadores
		{printf(";R19:\t<identificadores> ::= <identificador> , <identificadores>\n");

			/*printf("\t\tlexema:%s  tipo:%d\n", auxdato->lexema, auxdato->tipo);
			if(NULL==hash_insert_table(tlocal,tglobal, $1.lexema,&dato)){
				printf("\t\tERROR: %s ya se declaro.\n",$1.lexema );
				return;
			}*/
		}


funciones: funcion funciones
		{printf(";R20:\t<funciones> ::= <funcion> <funciones>\n");}
	|
		{printf(";R21:\t<funciones> ::=\n");}
funcion:    fn_declaracion sentencias TOK_LLAVEDERECHA 
		{printf(";R22:\t<funcion> ::= function <tipo> <identificador> ( <parametros_funcion> ) { <declaraciones_funcion> <sentencias> }\n");
			hash_destroy(tlocal);
			tlocal=NULL;
			printf("\t\tSe destruye tabla local\n");
			num_variables_local_actual =0;
			pos_variable_local_actual=1;

		}
fn_declaracion: fn_name TOK_PARENTESISIZQUIERDO parametros_funcion TOK_PARENTESISDERECHO TOK_LLAVEIZQUIERDA declaraciones_funcion {
			auxdato=hash_find_table(tlocal,tglobal,$1.lexema);
			auxdato->nparam= num_parametros_actual;
			auxdato->pparam= pos_parametro_actual;
			fprintf(nasm, "_%s:\n",$1.lexema);
			fprintf(nasm, "push ebp \n");
			fprintf(nasm, "mov ebp,esp\n");
			fprintf(nasm, "sub esp, %d\n",4*num_parametros_actual);
		}
fn_name: TOK_FUNCTION tipo identificador
{			if (tlocal!=NULL){
				printf("no se puede llamar a una funcion dentrode una funcion\n");
				return;
			}
			printf("lexema:%s\n",$3.lexema);
			if(NULL!=(auxdato=hash_find_table(tlocal,tglobal,$3.lexema))){
				printf("%s: ya existe la variable o funcion\n",$3.lexema);
				return;
			}
			clase_actual=0;
			tipo_actual = $2.tipo;
			printf("%d\n",nfunc);
			decfunc[nfunc]=(char*)malloc(sizeof(char*)*strlen($3.lexema));
			strcpy(decfunc[nfunc++],$3.lexema);
			printf("1\n");
			auxdato=(nodo*)malloc(sizeof(nodo));
			auxdato->lexema=$3.lexema;
			auxdato->categoria=FUNCION;
			auxdato->tipo=tipo_actual;
			auxdato->clase = clase_actual;
			auxdato->tam=0;
			auxdato->nparam=0;		
    		auxdato->pparam=0;		
    		auxdato->nvar=0;		
    		auxdato->pvar=0;
			hash_insert_table(tlocal,tglobal, $3.lexema	,auxdato);
			num_variables_local_actual =0;
			pos_variable_local_actual=1;
			num_parametros_actual=0;
			pos_parametro_actual=0;
			tlocal= hash_create_t_local();
			$$.lexema =$3.lexema;
		}	
parametros_funcion: parametro_funcion resto_parametros_funcion
		{printf(";R23:\t<parametros_funcion> ::= <parametro_funcion> <resto_parametros_funcion>\n");}
	|
		{printf(";R24:\t<parametros_funcion> ::=\n");}
resto_parametros_funcion :TOK_PUNTOYCOMA parametro_funcion resto_parametros_funcion
		{printf(";R25:\t<resto_parametros_funcion> ::= ; <parametro_funcion> <resto_parametros_funcion>\n");}
 	|
		{printf(";R26:\t<resto_parametros_funcion> ::=\n");}
parametro_funcion : tipo   ipdf
		{printf(";R27:\t<parametro_funcion> ::= <tipo> <identificador>\n");
		} 
declaraciones_funcion : declaraciones 
		{printf(";R28:\t<declaraciones_funcion> ::= <declaraciones>\n");}
	|
		{printf(";R29:\t<declaraciones_funcion> ::= \n");}
sentencias : sentencia
		{printf(";R30:\t<sentencias> ::= <sentencia>\n");}
	| sentencia   sentencias
		{printf(";R31:\t<sentencias> ::= <sentencia> <sentencias>\n");}
sentencia : sentencia_simple TOK_PUNTOYCOMA
		{printf(";R32:\t<sentencia> ::= <sentencia_simple> ;\n");}
	| bloque
		{printf(";R33:\t<sentencia> ::= <bloque> \n");}
sentencia_simple : asignacion
		{printf(";R34:\t<sentencia_simple> ::= <asignacion>\n");}
	| lectura 
		{printf(";R35:\t<sentencia_simple> ::= <lectura>\n");}
	| escritura 
		{printf(";R36:\t<sentencia_simple> ::= <escritura>\n");}
	| retorno_funcion 
		{printf(";R38:\t<sentencia_simple> ::= <retorno_funcion>\n");}
bloque : condicional
		{printf(";R40:\t<bloque> ::= <condicional>\n");}
	| bucle	 
		{printf(";R41:\t<bloque> ::= <bucle>\n");}
asignacion : identificador TOK_ASIGNACION exp
		{printf(";R43:\t<asignacion> ::= <identificador> = <exp>\n");
			if(NULL==(auxdato=hash_find_table(tlocal,tglobal,$1.lexema))){
				fprintf(stderr,"****ERROR semantico en lin %d: Acceso a variable no declarada (%s).\n",ln,$1.lexema);
				return;
			}
			if(auxdato->categoria==FUNCION){
				
				fprintf(stderr,"****ERROR semantico en lin %d: No se esperaba una fucion.\n",ln);
				return;
			}
			if(auxdato->tipo!=$3.tipo){
				
				printf("%d=%d\n",auxdato->tipo,$3.tipo);
				fprintf(stderr,"****ERROR semantico en lin %d: Asignacion incompatible (%s).\n",ln,auxdato->lexema);
				return;
			}
			/*generacion de codigo*/
			if(auxdato->categoria==VARIABLE){
				if(NULL==(auxdato=hash_find_table(NULL,tlocal,$1.lexema))){
					fprintf(nasm," \t\t\t;empieza asignacion\n");
					fprintf(nasm,";Cargar en eax la parte derecha de la asignacion\n");
					fprintf(nasm,"pop dword eax\n");
					if($3.es_direccion==1)
						fprintf(nasm,"mov dword eax, [eax]\n");
					fprintf(nasm,";hacer la asignacion efectiva\n");
					fprintf(nasm,"mov dword [_%s],eax\n",$1.lexema);
					fprintf(nasm," \t\t\t;termina asignacion\n");
				}
				else{
					fprintf(nasm," \t\t\t;empieza asignacion\n");
					fprintf(nasm,";Cargar en eax la parte derecha de la asignacion\n");
					fprintf(nasm,"pop dword eax\n");
					if($3.es_direccion==1)
						fprintf(nasm,"mov dword eax, [eax]\n");
					fprintf(nasm,";hacer la asignacion efectiva\n");
					fprintf(nasm,"mov dword [ebp -%d],eax\n",4*auxdato->pvar);
					fprintf(nasm," \t\t\t;termina asignacion\n");
				}
			}
			else{
				fprintf(nasm," \t\t\t;empieza asignacion\n");
				fprintf(nasm,";Cargar en eax la parte derecha de la asignacion\n");
				fprintf(nasm,"pop dword eax\n");
				if($3.es_direccion==1)
					fprintf(nasm,"mov dword eax, [eax]\n");
				fprintf(nasm,";hacer la asignacion efectiva\n");
				fprintf(nasm,"mov dword [ebp +%d],eax\n",4+4*(auxdato->pparam));
				fprintf(nasm," \t\t\t;termina asignacion\n");
			}
			
		} 
	| elemento_vector TOK_ASIGNACION exp 
		{printf(";R44:\t<asignacion> ::= <elemento_vector> = <exp>\n");
			if($1.tipo!=$3.tipo){
				fprintf(stderr,"****ERROR semantico en lin %d: Asignacion incompatible.\n",ln);
				return;
			}
				/*generacion de codigo*/
			fprintf(nasm," \t\t\t;empieza asignacion\n");
			fprintf(nasm,";Cargar en eax la parte derecha de la asignacion\n");
			fprintf(nasm,"pop dword eax\n");
			if($3.es_direccion==1)
				fprintf(nasm,"mov dword eax, [eax]\n");
			fprintf(nasm,";cargar en edx la parte izquierda de la asignacion\n");
			fprintf(nasm,"pop dword edx\n");
			fprintf(nasm,";Hacer la asignacion efectiva\n");
			fprintf(nasm,"mov dword [edx], eax\n");
			fprintf(nasm," \t\t\t;termina asignacion\n");

		}
elemento_vector : identificador TOK_CORCHETEIZQUIERDO exp TOK_CORCHETEDERECHO
		{printf(";R48:\t<elemento_vector> ::= <identificador> [ <exp> ]\n");
			if(NULL==(auxdato=hash_find_table(tlocal, tglobal, $3.lexema))){
				fprintf(stderr,"****ERROR semantico en lin %d: Acceso a variable no declarada (%s).\n",ln,$1.lexema);
				return;
			}
			if(($3.tipo==INT)&&(auxdato->clase==VECTOR)&&(auxdato->categoria==FUNCION)){
				$$.tipo=auxdato->tipo;
				$$.es_direccion=1;
			}
			if((auxdato->clase==VECTOR)&&(($3.valor_entero>auxdato->tam)||($3.valor_entero<0))){
				printf("exp=%d,tam=%d\n",$3.valor_entero,auxdato->tam);
				printf("%s\n",auxdato->lexema);
				fprintf(stderr,"****ERROR semantico en lin %d: Se sale del vector.\n",ln);
				return;
			}
			/*generacion de codigo*/
			fprintf(nasm," \t\t\t;empieza elemento_vector\n");
			fprintf(nasm, ";Cargar el valor del indice en eax\n");
			fprintf(nasm, "pop dword eax\n");
			if($3.es_direccion==1){
				fprintf(nasm, "mov dword eax, [eax]\n");
			}
			fprintf(nasm, ";Si el indice es menor que 0, error en tiempo de ejecucion\n");
			fprintf(nasm, "cmp eax, 0\n");
			fprintf(nasm,"jl near mensaje_1");
			fprintf(nasm, ";Si el indice es mayor de lo permitido, error en tiempo de ejecucion\n");
			fprintf(nasm, "cmp eax, %d\n", MAX_TAMANIO_VECTOR);//todo
			fprintf(nasm,"jg near mensaje_1");
			fprintf(nasm, ";EN ESTE PUNTO EL INDICE ES CORRECTO\n");
			fprintf(nasm, ";Y ESTA EN EL REGISTRO eax\n");

			fprintf(nasm, ";Cargar en eax la direccion de inicio del vector\n");
			fprintf(nasm, "mov dword edx, _%s\n",$1.lexema);
			fprintf(nasm, ";Cargar en eax la direccion del elemento indexado\n");
			fprintf(nasm, "lea eax, [edx +eax*4]\n");
			fprintf(nasm, ";Aplicar la direccion del elemento indexado\n");
			fprintf(nasm, "push dword eax\n");
			fprintf(nasm," \t\t\t;termina elemento_vector\n");
			
			

		}
condicional : if_exp  sentencias  TOK_LLAVEDERECHA 
		{printf(";R50:\t<condicional> ::= if ( <exp> ) { <sentencias> }\n");
			/*generacion de codigo*/
			fprintf(nasm," \t\t\t;empieza condicional\n");
			fprintf(nasm, "fin_si%d: ;es $1.etiqueta\n", $1.etiqueta);
			fprintf(nasm," \t\t\t;termina condicional\n");

		}

		| if_exp_sentencias TOK_ELSE TOK_LLAVEIZQUIERDA sentencias  TOK_LLAVEDERECHA
		{printf(";R51:\t<condicional> ::= if ( <exp> ) { <sentencias> } else { <sentencias> }\n");
			/*generacion de codigo*/
			fprintf(nasm," \t\t\t;empieza condicional\n");
			fprintf(nasm,"fin_sino%d: ;es $1.etiqueta\n", $$.etiqueta);
			fprintf(nasm," \t\t\t;termina condicional\n");

		}
if_exp_sentencias: if_exp sentencias TOK_LLAVEDERECHA {
			$$.etiqueta=$1.etiqueta;

			/*generacion de codigo*/
			fprintf(nasm," \t\t\t;empieza if_exp_sentencias\n");
			fprintf(nasm,"jmp near fin_sino%d ;es $1.etiqueta\n", $$.etiqueta);
			fprintf(nasm," fin_si%d: ;es $1.etiqueta\n", $1.etiqueta);
			fprintf(nasm," \t\t\t;termina if_exp_sentencias\n");
		}

if_exp:TOK_IF TOK_PARENTESISIZQUIERDO exp TOK_PARENTESISDERECHO TOK_LLAVEIZQUIERDA
		{
			if($3.tipo!=BOOL){
				fprintf(stderr,"****ERROR semantico en lin %d: Condicional con condicion de tipo int.\n",ln);
				return;
			}
			$$.etiqueta=etiqueta++;
			/*generacion de codigo*/
			fprintf(nasm," \t\t\t;empieza if_exp\n");
			fprintf(nasm,"pop eax\n");
			if($3.es_direccion==1)
				fprintf(nasm,"mov eax,[eax]\n");
			fprintf(nasm,"cmp eax,0\n");
			fprintf(nasm,"je near fin_si%d\n", $$.etiqueta);
			fprintf(nasm," \t\t\t;termina if_exp\n");

		}

bucle:  while_exp  sentencias  TOK_LLAVEDERECHA
		{printf(";R52:\t<bucle> ::= while ( <exp> ) { <sentencias> }\n");
		/*generacion de codigo*/
			fprintf(nasm," \t\t\t;empieza bucle\n");
			fprintf(nasm,"jmp near inicio_while%d\n",$1.etiqueta);
			fprintf(nasm,"fin_while%d:\n",$1.etiqueta);
			fprintf(nasm," \t\t\t;termina bucle\n");
		}

while_exp: while exp TOK_PARENTESISDERECHO TOK_LLAVEIZQUIERDA
		{
			if($2.tipo!=BOOL){
				fprintf(stderr,"****ERROR semantico en lin %d: Bucle con condicion de tipo int.\n",ln);
				return;
			}
			$$.etiqueta=$1.etiqueta;
			fprintf(nasm," \t\t\t;empieza while_exp\n");
			/*generacion codigo*/
			fprintf(nasm,"pop eax\n");
			if($2.es_direccion==1){
				fprintf(nasm,"mov eax,[eax]\n");
			}
			fprintf(nasm,"cmp eax, 0\n");
			fprintf(nasm,"je fin_while%d\n", $$.etiqueta);
			fprintf(nasm," \t\t\t;termina while_exp\n");
			
			
		}


while: TOK_WHILE TOK_PARENTESISIZQUIERDO
		{
			
			$$.etiqueta=etiqueta++;
			fprintf(nasm," \t\t\t;empieza while\n");
			/*generacion codigo*/
			fprintf(nasm, "inicio_while%d: ; es etiqueta $$.etiqueta\n", $$.etiqueta);
			fprintf(nasm," \t\t\t;termina while\n");
		}


lectura :TOK_SCANF identificador
		{printf(";R54:\t<lectura> ::= scanf <identificador>\n");
			printf("42\n");
			if(NULL==(auxdato=hash_find_table(tlocal,tglobal, $2.lexema))){
				fprintf(stderr,"****ERROR semantico en lin %d: Acceso a variable no declarada (%s).\n",ln,$2.lexema);
				return;
			}printf("42\n");
			printf("\t\tlexema:%s  tipo:%d\n", auxdato->lexema, auxdato->tipo);
			if(auxdato->tipo!=INT){
				fprintf(stderr,"****ERROR semantico en lin %d: Lectura de tipo boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			if(auxdato->categoria==VARIABLE){
				if(NULL==(auxdato=hash_find_table(NULL,tlocal, $2.lexema))){
					fprintf(nasm," \t\t\t;empieza scanf\n");
					fprintf(nasm,"push dword _%s\n",$2.lexema);
					if($2.tipo==INT)
						fprintf(nasm,"call scan_int ;identificador de tipo entero\n");
					if($2.tipo==BOOL)
						fprintf(nasm,"call scan_boolean ;identificador de tipo boolean\n");
					fprintf(nasm,"add esp,4\n");
					fprintf(nasm," \t\t\t;termina scanf\n");
				}
				else{
					fprintf(nasm," \t\t\t;empieza scanf\n");
					fprintf(nasm,"lea eax,[ebp-%d]\n",4*auxdato->pvar);
					fprintf(nasm,"push dword eax\n");
					if($2.tipo==INT)
						fprintf(nasm,"call scan_int ;identificador de tipo entero\n");
					if($2.tipo==BOOL)
						fprintf(nasm,"call scan_boolean ;identificador de tipo boolean\n");
					fprintf(nasm,"add esp,4\n");
					fprintf(nasm," \t\t\t;termina scanf\n");
				}
			}
			else if(auxdato->categoria==PARAM){
				fprintf(nasm," \t\t\t;empieza scanf\n");
				fprintf(nasm,"lea eax,[ebp+%d]\n",4+4*(auxdato->pparam));
				fprintf(nasm,"push dword eax\n");
				if($2.tipo==INT)
					fprintf(nasm,"call scan_int ;identificador de tipo entero\n");
				if($2.tipo==BOOL)
					fprintf(nasm,"call scan_boolean ;identificador de tipo boolean\n");
				fprintf(nasm,"add esp,4\n");
				fprintf(nasm," \t\t\t;termina scanf\n");
			}
		
		} 
escritura :TOK_PRINTF  exp 
		{printf(";R56:\t<escritura> ::= printf <exp>\n");
			/*generacion de codigo*/
			fprintf(nasm," \t\t\t;empieza printf\n");
			if($2.es_direccion==1){
				fprintf(nasm,"pop dword eax\n");
				fprintf(nasm,"mov dword eax,[eax]\n");
				fprintf(nasm,"push dword eax\n");
			}
			if($2.tipo==INT){
				fprintf(nasm,";Si la expresion es de tipo entero\n");
				fprintf(nasm,"call print_int\n");
			}
			else if($2.tipo==BOOL){
				fprintf(nasm,";Si la expresion es de tipo boolean\n");
				fprintf(nasm,"call print_boolean\n");
			}
			fprintf(nasm,"add esp, 4\n");
			fprintf(nasm,";Salto de linea\n");
			fprintf(nasm,"call print_endofline\n");
			fprintf(nasm," \t\t\t;termina printf\n");
			
		}

retorno_funcion :TOK_RETURN exp
		{printf(";R61:\t<retorno_funcion> ::= return <exp>\n");
			fprintf(nasm,"pop dword eax\n");
			if($2.es_direccion==1)
				fprintf(nasm, "mov eax, [eax]\n");
			fprintf(nasm,"mov dword esp, ebp\n");
			fprintf(nasm,"pop dword ebp\n");
			fprintf(nasm,"ret\n");
		}
exp : exp TOK_MAS exp 
		{printf(";R72:\t<exp> ::= <exp> + <exp>\n");
			if(($1.tipo==INT)&&($3.tipo==INT)){
				$$.tipo=INT;
				$$.es_direccion=0;
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Operacion aritmetica con operandos boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			gc_sumaenteros($1.es_direccion,$3.es_direccion);
		}
	| exp TOK_MENOS exp 
		{printf(";R73:\t<exp> ::= <exp> - <exp>\n");
			if(($1.tipo==INT)&&($3.tipo==INT)){
				$$.tipo=INT;
				$$.es_direccion=0;
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Operacion aritmetica con operandos boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			gc_restaenteros($1.es_direccion,$3.es_direccion);
		}
	| exp TOK_DIVISION exp 
		{printf(";R74:\t<exp> ::= <exp> / <exp>\n");
			if(($1.tipo==INT)&&($3.tipo==INT)){
				$$.tipo=INT;
				$$.es_direccion=0;
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Operacion aritmetica con operandos boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			gc_diventeros($1.es_direccion,$3.es_direccion);
		}
	| exp TOK_ASTERISCO exp 
		{printf(";R75:\t<exp> ::= <exp> * <exp>\n");
			if(($1.tipo==INT)&&($3.tipo==INT)){
				$$.tipo=INT;
				$$.es_direccion=0;
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Operacion aritmetica con operandos boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			gc_multenteros($1.es_direccion,$3.es_direccion);
		}
	|TOK_MENOS exp %prec UMINUS
		{printf(";R76:\t<exp> ::= - <exp>\n");
			if($2.tipo==INT){
				$$.tipo=INT;
				$$.es_direccion=0;
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Operacion aritmetica con operandos boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			gc_negenteros($2.es_direccion);
		}
	| exp TOK_AND exp 
		{printf(";R77:\t<exp> ::= <exp> && <exp>\n");
			if(($1.tipo==BOOL)&&($3.tipo==BOOL)){
				$$.tipo=BOOL;
				$$.es_direccion=0;
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Operacion logica con operandos int.\n",ln);
				return;
				}
			/*generacion de codigo*/
			gc_andlogica($1.es_direccion,$3.es_direccion);
		}
	| exp TOK_OR exp 
		{printf(";R78:\t<exp> ::= <exp> || <exp>\n");
			if(($1.tipo==BOOL)&&($3.tipo==BOOL)){
				$$.tipo=BOOL;
				$$.es_direccion=0;
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Operacion logica con operandos int.\n",ln);
				return;

			}
			/*generacion de codigo*/
			gc_orlogica($1.es_direccion,$3.es_direccion	);
		}
	|TOK_NOT exp 
		{printf(";R79:\t<exp> ::= ! <exp>\n");
			if($2.tipo==BOOL){
				$$.es_direccion=0;
				if($2.valor_entero==0){
					$$.tipo=BOOL;
					$$.es_direccion=0;
				}
				else{
				fprintf(stderr,"****ERROR semantico en lin %d: Operacion logica con operandos int.\n",ln);
				return;

				}
				/*generacion de codigo*/
				gc_neglogica($2.es_direccion,etiqueta);
			}
		}
	| identificador 
		{printf(";R80:\t<exp> ::= <identificador>\n");
			if(NULL==(auxdato=hash_find_table(tlocal, tglobal, $1.lexema))){
				fprintf(stderr,"****ERROR semantico en lin %d: Acceso a variable no declarada(%s).\n",ln,$1.lexema);
				return;

			}
			printf("tipo:%d\n",auxdato->tipo);
			printf("categoria:%d\n",auxdato->categoria);
			if ((auxdato->categoria==FUNCION)||(auxdato->clase==VECTOR)){
			//if($1.tipo!=INT){
				fprintf(stderr,"****ERROR semantico en lin %d: Categoria o clase invalida.\n",ln);
				return;

			}
			else{
				$$.tipo=auxdato->tipo;
				$$.es_direccion=1;
				if((auxdato->categoria==VARIABLE)&&(l_exp==0)){
					if(NULL==(auxdato=hash_find_table(NULL, tlocal, $1.lexema))){
						fprintf(nasm, "mov eax, _%s\n",$1.lexema);
						fprintf(nasm, "push eax\n");		
					}
					else{
						fprintf(nasm, "lea eax, [ebp-%d]\n",4*auxdato->pvar);
						fprintf(nasm, "push eax\n");
					}
				}
				else if(auxdato->categoria==PARAM){
					fprintf(nasm, "lea eax, [ebp+%d]\n",4+4*( auxdato->pparam));
					fprintf(nasm, "push eax\n");
				}
			}

		}
	| constante 
		{printf(";R81:\t<exp> ::= <constante>\n");
			$$.tipo=$1.tipo;
			$$.es_direccion=$1.es_direccion;
		}
	|TOK_PARENTESISIZQUIERDO exp TOK_PARENTESISDERECHO
		{printf(";R82:\t<exp> ::= ( <exp> )\n");
			$$.tipo=$2.tipo;
			$$.es_direccion=$2.es_direccion;
		}
	|TOK_PARENTESISIZQUIERDO comparacion TOK_PARENTESISDERECHO
		{printf(";R83:\t<exp> ::= ( <comparacion> )\n");
			$$.tipo=$2.tipo;
			$$.es_direccion=$2.es_direccion;
		}
	| elemento_vector 
		{printf(";R85:\t<exp> ::= <elemento_vector>\n");
			$$.tipo=$1.tipo;
			$$.es_direccion=$1.es_direccion;
		}
	| idf_llamada_funcion TOK_PARENTESISIZQUIERDO lista_expresiones TOK_PARENTESISDERECHO
		{printf(";R88:\t<exp> ::= <identificador> ( <lista_expresiones> )\n");
			if(NULL==(auxdato=(hash_find_table(tlocal,tglobal, $1.lexema)))){
				fprintf(stderr,"****ERROR semantico en lin %d: Acceso a variable no declarada(%s).\n",ln,$1.lexema);
				return;

			}
			
			if((auxdato->nparam==num_parametros_llamada_actual)&&(en_explist==1)){
				$$.tipo=auxdato->tipo;
				$$.es_direccion=0;
			}
			en_explist=0;
			fprintf(nasm, "call _%s\n", $1.lexema);
			
			fprintf(nasm, "add esp,%d\n",4*num_parametros_llamada_actual);
			fprintf(nasm, "push dword eax\n");

		}
idf_llamada_funcion: TOK_IDENTIFICADOR {
			if(NULL==(auxdato=(hash_find_table(tlocal,tglobal, $1.lexema)))){
				fprintf(stderr,"****ERROR semantico en lin %d: Acceso a variable no declarada(%s).\n",ln,$1.lexema);
				return;
			}
			if(auxdato->categoria!=FUNCION){
				fprintf(stderr,"****ERROR semantico en lin %d: Se esperaba una funcion.\n",ln);
				return;
			}
			if(en_explist==0){
				en_explist=1;
				num_parametros_llamada_actual =0;
				$$.lexema=$1.lexema;
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Llamada a funcion invalida.\n",ln);
				return;
			}


		}
lista_expresiones : ini_lst_exp exp   resto_lista_expresiones
		{printf(";R89:\t<lista_expresiones> ::= <exp> <resto_lista_expresiones>\n");
			num_parametros_llamada_actual++;
			if(tlocal==NULL){
				fprintf(nasm, "push dword [_%s]\n", $2.lexema);
			}
			else{
				if(NULL==(auxdato=hash_find_table(NULL, tlocal, $2.lexema))){
					fprintf(stderr,"****ERROR semantico en lin %d: Acceso a variable no declarada (%s).\n",ln,$2.lexema);
					return;
				}

				fprintf(nasm, "lea eax, [ebp+%d]\n",4+4*( auxdato->pparam));
				fprintf(nasm, "push eax\n");
			}
			l_exp=0;
		}
		|
ini_lst_exp:{
			l_exp=1;
		}
resto_lista_expresiones :TOK_COMA exp resto_lista_expresiones
		{printf(";R91:\t<resto_lista_expresiones> ::= , <exp> <resto_lista_expresiones>\n");
			num_parametros_llamada_actual++;
			if(tlocal==NULL){
				fprintf(nasm, "push dword [_%s]\n", $2.lexema);
			}
			else{
				if(NULL==(auxdato=hash_find_table(NULL, tlocal, $2.lexema))){
					fprintf(stderr,"****ERROR semantico en lin %d: Acceso a variable no declarada (%s).\n",ln,$2.lexema);
					return;
				}

				fprintf(nasm, "lea eax, [ebp+%d]\n",4+4*( auxdato->pparam));
				fprintf(nasm, "push eax\n");
			}
		}
	|
		{printf(";R92:\t<resto_lista_expresiones> ::=\n");}
comparacion :  exp  TOK_IGUAL  exp
		{printf(";R93:\t<comparacion> ::= <exp> == <exp>\n");
			if(($1.tipo=INT)&&($3.tipo=INT)){
				$$.tipo=BOOL;
				$$.es_direccion=0;
				
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Comparacion con operandos boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			gc_igual($1.es_direccion,$3.es_direccion,etiqueta);
		}
	| exp  TOK_DISTINTO  exp 
		{printf(";R94:\t<comparacion> ::= <exp> != <exp>\n");
			if(($1.tipo=INT)&&($3.tipo=INT)){
				$$.tipo=BOOL;
				$$.es_direccion=0;
				
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Comparacion con operandos boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			gc_distinto($1.es_direccion,$3.es_direccion,etiqueta);

		}
	| exp  TOK_MENORIGUAL  exp 
		{printf(";R95:\t<comparacion> ::= <exp> <= <exp>\n");
			if(($1.tipo=INT)&&($3.tipo=INT)){
				$$.tipo=BOOL;
				$$.es_direccion=0;
				
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Comparacion con operandos boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			gc_menorigual($1.es_direccion,$3.es_direccion,etiqueta);
		}
	| exp  TOK_MAYORIGUAL  exp 
		{printf(";R96:\t<comparacion> ::= <exp> >= <exp>\n");
			if(($1.tipo=INT)&&($3.tipo=INT)){
				$$.tipo=BOOL;
				$$.es_direccion=0;
				
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Comparacion con operandos boolean.\n",ln);
				return;;
			}
			/*generacion de codigo*/
			gc_mayorigual($1.es_direccion,$3.es_direccion,etiqueta);
		}
	| exp  TOK_MENOR  exp 
		{printf(";R97:\t<comparacion> ::= <exp> < <exp>\n");
			if(($1.tipo=INT)&&($3.tipo=INT)){
				$$.tipo=BOOL;
				$$.es_direccion=0;
				
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Comparacion con operandos boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			gc_menor($1.es_direccion,$3.es_direccion,etiqueta);
		}
	| exp  TOK_MAYOR  exp 
		{printf(";R98:\t<comparacion> ::= <exp> > <exp>\n");
			if(($1.tipo=INT)&&($3.tipo=INT)){
				$$.tipo=BOOL;
				$$.es_direccion=0;
				
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Comparacion con operandos boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			gc_mayor($1.es_direccion,$3.es_direccion,etiqueta);
		}
constante : constante_logica
		{printf(";R99:\t<constante> ::= <constante_logica>\n");
			$$.tipo=$1.tipo;
			$$.es_direccion=$1.es_direccion;
		} 
	| constante_entera  
		{printf(";R100:\t<constante> ::= <constante_entera>\n");
			$$.tipo=$1.tipo;
			$$.es_direccion=$1.es_direccion;
		}
constante_logica : TOK_TRUE
		{printf(";R102:\t<constante_logica> ::= true\n");
			$$.tipo=BOOL;
			$$.es_direccion=0;

			/*generacion de codigo*/

			fprintf(nasm," \t\t\t;empieza constante logica\n");
			fprintf(nasm,";numero_linea %d \n",ln);
			fprintf(nasm,"\tpush dword 1 \n");
			fprintf(nasm," \t\t\t;termina constante logica\n");
		}
	| TOK_FALSE
		{printf(";R103:\t<constante_logica> ::= false\n");
			$$.tipo=BOOL;
			$$.es_direccion=0;

			/*generacion de codigo*/
			fprintf(nasm," \t\t\t;empieza constante logica\n");
			fprintf(nasm,";numero_linea %d \n",ln);
			fprintf(nasm,"\tpush dword 0 \n");
			fprintf(nasm," \t\t\t;termina constante logica\n");
		}
constante_entera: TOK_CONSTANTE_ENTERA
		{printf(";R104:\t<constante_entera> ::= TOK_CONSTANTE_ENTERA\n");
			$$.tipo=INT;
			$$.es_direccion=0;

			/*generacion de codigo*/
			$$.valor_entero=$1.valor_entero;
			fprintf(nasm," \t\t\t;empieza constante entera\n");
			if(l_exp==0){
				fprintf(nasm,";numero_linea %d \n",ln);
				fprintf(nasm,"\tpush dword %d\n", $1.valor_entero);
				fprintf(nasm," \t\t\t;termina constante entera\n");
			}
		}
identificador: TOK_IDENTIFICADOR{
			printf(";R108_1:\t<identificador> ::= TOK_IDENTIFICADOR == %s\n", $1.lexema);
				$$.es_direccion=1;
				$$.lexema=$1.lexema;
			}

decidentificador: TOK_IDENTIFICADOR
		{printf(";R108_2:\t<decidentificador> ::= TOK_IDENTIFICADOR == %s\n", $1.lexema);
			if (NULL!=hash_find_table(tlocal,tglobal,$1.lexema)){
				printf ("%s: redefinido\n", $1.lexema);
				return;
			}
			auxdato=(nodo*)malloc(sizeof(nodo));
			auxdato->lexema=$1.lexema;
			auxdato->categoria=VARIABLE;
			auxdato->tipo=tipo_actual;
			printf("\t\ttipoactual:%d\n",tipo_actual);
			auxdato->clase = clase_actual;
			if(clase_actual==VECTOR){
				auxdato->tam=tamanio_vector_actual;
			}
			else{
				auxdato->tam=0;
			}
			auxdato->nparam=0;		
    		auxdato->pparam=0;		
    		auxdato->nvar=0;		
    		auxdato->pvar=0;
    		$$.lexema=$1.lexema;		
			pos_variable_local_actual++;
			num_variables_local_actual++;
			if(NULL==hash_insert_table(tlocal,tglobal, $1.lexema,auxdato)){
				fprintf(stderr,"****ERROR semantico en lin %d: Declaracion duplicada.\n",ln);
				return;
			}
			decvar[nvar]=(char*)malloc(sizeof(char)*strlen($1.lexema));
			strcpy(decvar[nvar],$1.lexema);
			nvar++;
			printf("\t\tse crea: %s\n",$1.lexema);
			printf("\t\tlexema:%s tipo:%d\n",auxdato->lexema, auxdato->tipo);
			//printf("lexema:%s ,categoria:%d ,tipo:%d ,clase:%d ,tam:%d ,nparam:%d ,pparm:%d ,nvar:%d ,pvar:%d \n",dato.lexema,dato.categoria,dato.tipo,dato.clase,dato.tam,dato.nparam,dato.pparam,dato.nvar,dato.pvar);
		}
ipdf: TOK_IDENTIFICADOR 
		{printf(";R108:\t<ipdf> ::= TOK_IDENTIFICADOR == %s\n", $1.lexema);
			if (NULL!=hash_find_table(tlocal,tglobal,$1.lexema)){
				printf ("%s: redefinido\n", $1.lexema);
				return;
			}
			auxdato=(nodo*)malloc(sizeof(nodo));
			auxdato->lexema=$1.lexema;
			auxdato->categoria=PARAM;
			auxdato->tipo=tipo_actual;
			auxdato->clase = clase_actual;
			if(clase_actual==VECTOR){
				fprintf(stderr,"****ERROR semantico en lin %d: no se pudo insertar %s.\n",ln,$1.lexema);
				return;
			}
			else{
				auxdato->tam=0;
			}
			auxdato->nparam=0;		
    		auxdato->pparam=pos_variable_local_actual++;		
    		auxdato->nvar=num_variables_local_actual;		
    		auxdato->pvar=pos_variable_local_actual;
			if(NULL==hash_insert_table(tlocal,tglobal, $1.lexema,auxdato)){
				fprintf(stderr,"****ERROR semantico en lin %d: no se pudo insertar %s.\n",ln,$1.lexema);
				return;
			}
			pos_parametro_actual++;
			num_parametros_actual++;
			fprintf(nasm, ";num_variables_local_actual=%d,pos_variable_local_actual=%d \n",num_variables_local_actual,pos_variable_local_actual);
		}
;
%%

void yyerror(char *s){
	if(err==0)
		printf("\n **** Error sintáctico en [lin %d, col %d] \n",ln,col);
	err=0;
}
