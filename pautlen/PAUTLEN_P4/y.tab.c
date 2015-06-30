/* A Bison parser, made by GNU Bison 3.0.2.  */

/* Bison implementation for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2013 Free Software Foundation, Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* C LALR(1) parser skeleton written by Richard Stallman, by
   simplifying the original so-called "semantic" parser.  */

/* All symbols defined below should begin with yy or YY, to avoid
   infringing on user name space.  This should be done even for local
   variables, as they might otherwise be expanded by user macros.
   There are some unavoidable exceptions within include files to
   define necessary library symbols; they are noted "INFRINGES ON
   USER NAME SPACE" below.  */

/* Identify Bison output.  */
#define YYBISON 1

/* Bison version.  */
#define YYBISON_VERSION "3.0.2"

/* Skeleton name.  */
#define YYSKELETON_NAME "yacc.c"

/* Pure parsers.  */
#define YYPURE 0

/* Push parsers.  */
#define YYPUSH 0

/* Pull parsers.  */
#define YYPULL 1




/* Copy the first part of user declarations.  */
#line 37 "gramatica.y" /* yacc.c:339  */

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

#line 101 "y.tab.c" /* yacc.c:339  */

# ifndef YY_NULLPTR
#  if defined __cplusplus && 201103L <= __cplusplus
#   define YY_NULLPTR nullptr
#  else
#   define YY_NULLPTR 0
#  endif
# endif

/* Enabling verbose error messages.  */
#ifdef YYERROR_VERBOSE
# undef YYERROR_VERBOSE
# define YYERROR_VERBOSE 1
#else
# define YYERROR_VERBOSE 0
#endif

/* In a future release of Bison, this section will be replaced
   by #include "y.tab.h".  */
#ifndef YY_YY_Y_TAB_H_INCLUDED
# define YY_YY_Y_TAB_H_INCLUDED
/* Debug traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif
/* "%code requires" blocks.  */
#line 71 "gramatica.y" /* yacc.c:355  */

	#define VARIABLE 1
	#define PARAM 2
	#define FUNCION 3
	

#line 138 "y.tab.c" /* yacc.c:355  */

/* Token type.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
  enum yytokentype
  {
    TOK_MAIN = 258,
    TOK_INT = 259,
    TOK_BOOLEAN = 260,
    TOK_ARRAY = 261,
    TOK_FUNCTION = 262,
    TOK_IF = 263,
    TOK_ELSE = 264,
    TOK_WHILE = 265,
    TOK_SCANF = 266,
    TOK_PRINTF = 267,
    TOK_RETURN = 268,
    TOK_PUNTOYCOMA = 269,
    TOK_COMA = 270,
    TOK_PARENTESISIZQUIERDO = 271,
    TOK_PARENTESISDERECHO = 272,
    TOK_CORCHETEIZQUIERDO = 273,
    TOK_CORCHETEDERECHO = 274,
    TOK_LLAVEIZQUIERDA = 275,
    TOK_LLAVEDERECHA = 276,
    TOK_ASIGNACION = 277,
    TOK_MAS = 278,
    TOK_MENOS = 279,
    TOK_DIVISION = 280,
    TOK_ASTERISCO = 281,
    TOK_AND = 282,
    TOK_OR = 283,
    TOK_NOT = 284,
    TOK_IGUAL = 285,
    TOK_DISTINTO = 286,
    TOK_MENORIGUAL = 287,
    TOK_MAYORIGUAL = 288,
    TOK_MENOR = 289,
    TOK_MAYOR = 290,
    TOK_IDENTIFICADOR = 291,
    TOK_CONSTANTE_ENTERA = 292,
    TOK_TRUE = 293,
    TOK_FALSE = 294,
    TOK_ERROR = 295,
    UMINUS = 296
  };
#endif
/* Tokens.  */
#define TOK_MAIN 258
#define TOK_INT 259
#define TOK_BOOLEAN 260
#define TOK_ARRAY 261
#define TOK_FUNCTION 262
#define TOK_IF 263
#define TOK_ELSE 264
#define TOK_WHILE 265
#define TOK_SCANF 266
#define TOK_PRINTF 267
#define TOK_RETURN 268
#define TOK_PUNTOYCOMA 269
#define TOK_COMA 270
#define TOK_PARENTESISIZQUIERDO 271
#define TOK_PARENTESISDERECHO 272
#define TOK_CORCHETEIZQUIERDO 273
#define TOK_CORCHETEDERECHO 274
#define TOK_LLAVEIZQUIERDA 275
#define TOK_LLAVEDERECHA 276
#define TOK_ASIGNACION 277
#define TOK_MAS 278
#define TOK_MENOS 279
#define TOK_DIVISION 280
#define TOK_ASTERISCO 281
#define TOK_AND 282
#define TOK_OR 283
#define TOK_NOT 284
#define TOK_IGUAL 285
#define TOK_DISTINTO 286
#define TOK_MENORIGUAL 287
#define TOK_MAYORIGUAL 288
#define TOK_MENOR 289
#define TOK_MAYOR 290
#define TOK_IDENTIFICADOR 291
#define TOK_CONSTANTE_ENTERA 292
#define TOK_TRUE 293
#define TOK_FALSE 294
#define TOK_ERROR 295
#define UMINUS 296

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef union YYSTYPE YYSTYPE;
union YYSTYPE
{
#line 80 "gramatica.y" /* yacc.c:355  */

    tipo_atributos atributos;
    int num;
    char* nmfun;

#line 238 "y.tab.c" /* yacc.c:355  */
};
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;

int yyparse (void);

#endif /* !YY_YY_Y_TAB_H_INCLUDED  */

/* Copy the second part of user declarations.  */

#line 253 "y.tab.c" /* yacc.c:358  */

#ifdef short
# undef short
#endif

#ifdef YYTYPE_UINT8
typedef YYTYPE_UINT8 yytype_uint8;
#else
typedef unsigned char yytype_uint8;
#endif

#ifdef YYTYPE_INT8
typedef YYTYPE_INT8 yytype_int8;
#else
typedef signed char yytype_int8;
#endif

#ifdef YYTYPE_UINT16
typedef YYTYPE_UINT16 yytype_uint16;
#else
typedef unsigned short int yytype_uint16;
#endif

#ifdef YYTYPE_INT16
typedef YYTYPE_INT16 yytype_int16;
#else
typedef short int yytype_int16;
#endif

#ifndef YYSIZE_T
# ifdef __SIZE_TYPE__
#  define YYSIZE_T __SIZE_TYPE__
# elif defined size_t
#  define YYSIZE_T size_t
# elif ! defined YYSIZE_T
#  include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  define YYSIZE_T size_t
# else
#  define YYSIZE_T unsigned int
# endif
#endif

#define YYSIZE_MAXIMUM ((YYSIZE_T) -1)

#ifndef YY_
# if defined YYENABLE_NLS && YYENABLE_NLS
#  if ENABLE_NLS
#   include <libintl.h> /* INFRINGES ON USER NAME SPACE */
#   define YY_(Msgid) dgettext ("bison-runtime", Msgid)
#  endif
# endif
# ifndef YY_
#  define YY_(Msgid) Msgid
# endif
#endif

#ifndef YY_ATTRIBUTE
# if (defined __GNUC__                                               \
      && (2 < __GNUC__ || (__GNUC__ == 2 && 96 <= __GNUC_MINOR__)))  \
     || defined __SUNPRO_C && 0x5110 <= __SUNPRO_C
#  define YY_ATTRIBUTE(Spec) __attribute__(Spec)
# else
#  define YY_ATTRIBUTE(Spec) /* empty */
# endif
#endif

#ifndef YY_ATTRIBUTE_PURE
# define YY_ATTRIBUTE_PURE   YY_ATTRIBUTE ((__pure__))
#endif

#ifndef YY_ATTRIBUTE_UNUSED
# define YY_ATTRIBUTE_UNUSED YY_ATTRIBUTE ((__unused__))
#endif

#if !defined _Noreturn \
     && (!defined __STDC_VERSION__ || __STDC_VERSION__ < 201112)
# if defined _MSC_VER && 1200 <= _MSC_VER
#  define _Noreturn __declspec (noreturn)
# else
#  define _Noreturn YY_ATTRIBUTE ((__noreturn__))
# endif
#endif

/* Suppress unused-variable warnings by "using" E.  */
#if ! defined lint || defined __GNUC__
# define YYUSE(E) ((void) (E))
#else
# define YYUSE(E) /* empty */
#endif

#if defined __GNUC__ && 407 <= __GNUC__ * 100 + __GNUC_MINOR__
/* Suppress an incorrect diagnostic about yylval being uninitialized.  */
# define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN \
    _Pragma ("GCC diagnostic push") \
    _Pragma ("GCC diagnostic ignored \"-Wuninitialized\"")\
    _Pragma ("GCC diagnostic ignored \"-Wmaybe-uninitialized\"")
# define YY_IGNORE_MAYBE_UNINITIALIZED_END \
    _Pragma ("GCC diagnostic pop")
#else
# define YY_INITIAL_VALUE(Value) Value
#endif
#ifndef YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_END
#endif
#ifndef YY_INITIAL_VALUE
# define YY_INITIAL_VALUE(Value) /* Nothing. */
#endif


#if ! defined yyoverflow || YYERROR_VERBOSE

/* The parser invokes alloca or malloc; define the necessary symbols.  */

# ifdef YYSTACK_USE_ALLOCA
#  if YYSTACK_USE_ALLOCA
#   ifdef __GNUC__
#    define YYSTACK_ALLOC __builtin_alloca
#   elif defined __BUILTIN_VA_ARG_INCR
#    include <alloca.h> /* INFRINGES ON USER NAME SPACE */
#   elif defined _AIX
#    define YYSTACK_ALLOC __alloca
#   elif defined _MSC_VER
#    include <malloc.h> /* INFRINGES ON USER NAME SPACE */
#    define alloca _alloca
#   else
#    define YYSTACK_ALLOC alloca
#    if ! defined _ALLOCA_H && ! defined EXIT_SUCCESS
#     include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
      /* Use EXIT_SUCCESS as a witness for stdlib.h.  */
#     ifndef EXIT_SUCCESS
#      define EXIT_SUCCESS 0
#     endif
#    endif
#   endif
#  endif
# endif

# ifdef YYSTACK_ALLOC
   /* Pacify GCC's 'empty if-body' warning.  */
#  define YYSTACK_FREE(Ptr) do { /* empty */; } while (0)
#  ifndef YYSTACK_ALLOC_MAXIMUM
    /* The OS might guarantee only one guard page at the bottom of the stack,
       and a page size can be as small as 4096 bytes.  So we cannot safely
       invoke alloca (N) if N exceeds 4096.  Use a slightly smaller number
       to allow for a few compiler-allocated temporary stack slots.  */
#   define YYSTACK_ALLOC_MAXIMUM 4032 /* reasonable circa 2006 */
#  endif
# else
#  define YYSTACK_ALLOC YYMALLOC
#  define YYSTACK_FREE YYFREE
#  ifndef YYSTACK_ALLOC_MAXIMUM
#   define YYSTACK_ALLOC_MAXIMUM YYSIZE_MAXIMUM
#  endif
#  if (defined __cplusplus && ! defined EXIT_SUCCESS \
       && ! ((defined YYMALLOC || defined malloc) \
             && (defined YYFREE || defined free)))
#   include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#   ifndef EXIT_SUCCESS
#    define EXIT_SUCCESS 0
#   endif
#  endif
#  ifndef YYMALLOC
#   define YYMALLOC malloc
#   if ! defined malloc && ! defined EXIT_SUCCESS
void *malloc (YYSIZE_T); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
#  ifndef YYFREE
#   define YYFREE free
#   if ! defined free && ! defined EXIT_SUCCESS
void free (void *); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
# endif
#endif /* ! defined yyoverflow || YYERROR_VERBOSE */


#if (! defined yyoverflow \
     && (! defined __cplusplus \
         || (defined YYSTYPE_IS_TRIVIAL && YYSTYPE_IS_TRIVIAL)))

/* A type that is properly aligned for any stack member.  */
union yyalloc
{
  yytype_int16 yyss_alloc;
  YYSTYPE yyvs_alloc;
};

/* The size of the maximum gap between one aligned stack and the next.  */
# define YYSTACK_GAP_MAXIMUM (sizeof (union yyalloc) - 1)

/* The size of an array large to enough to hold all stacks, each with
   N elements.  */
# define YYSTACK_BYTES(N) \
     ((N) * (sizeof (yytype_int16) + sizeof (YYSTYPE)) \
      + YYSTACK_GAP_MAXIMUM)

# define YYCOPY_NEEDED 1

/* Relocate STACK from its old location to the new one.  The
   local variables YYSIZE and YYSTACKSIZE give the old and new number of
   elements in the stack, and YYPTR gives the new location of the
   stack.  Advance YYPTR to a properly aligned location for the next
   stack.  */
# define YYSTACK_RELOCATE(Stack_alloc, Stack)                           \
    do                                                                  \
      {                                                                 \
        YYSIZE_T yynewbytes;                                            \
        YYCOPY (&yyptr->Stack_alloc, Stack, yysize);                    \
        Stack = &yyptr->Stack_alloc;                                    \
        yynewbytes = yystacksize * sizeof (*Stack) + YYSTACK_GAP_MAXIMUM; \
        yyptr += yynewbytes / sizeof (*yyptr);                          \
      }                                                                 \
    while (0)

#endif

#if defined YYCOPY_NEEDED && YYCOPY_NEEDED
/* Copy COUNT objects from SRC to DST.  The source and destination do
   not overlap.  */
# ifndef YYCOPY
#  if defined __GNUC__ && 1 < __GNUC__
#   define YYCOPY(Dst, Src, Count) \
      __builtin_memcpy (Dst, Src, (Count) * sizeof (*(Src)))
#  else
#   define YYCOPY(Dst, Src, Count)              \
      do                                        \
        {                                       \
          YYSIZE_T yyi;                         \
          for (yyi = 0; yyi < (Count); yyi++)   \
            (Dst)[yyi] = (Src)[yyi];            \
        }                                       \
      while (0)
#  endif
# endif
#endif /* !YYCOPY_NEEDED */

/* YYFINAL -- State number of the termination state.  */
#define YYFINAL  3
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   167

/* YYNTOKENS -- Number of terminals.  */
#define YYNTOKENS  42
/* YYNNTS -- Number of nonterminals.  */
#define YYNNTS  48
/* YYNRULES -- Number of rules.  */
#define YYNRULES  86
/* YYNSTATES -- Number of states.  */
#define YYNSTATES  156

/* YYTRANSLATE[YYX] -- Symbol number corresponding to YYX as returned
   by yylex, with out-of-bounds checking.  */
#define YYUNDEFTOK  2
#define YYMAXUTOK   296

#define YYTRANSLATE(YYX)                                                \
  ((unsigned int) (YYX) <= YYMAXUTOK ? yytranslate[YYX] : YYUNDEFTOK)

/* YYTRANSLATE[TOKEN-NUM] -- Symbol number corresponding to TOKEN-NUM
   as returned by yylex, without out-of-bounds checking.  */
static const yytype_uint8 yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41
};

#if YYDEBUG
  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
static const yytype_uint16 yyrline[] =
{
       0,   155,   155,   157,   166,   171,   183,   189,   191,   193,
     196,   198,   200,   210,   215,   219,   233,   244,   255,   258,
     259,   268,   277,   311,   314,   315,   318,   319,   322,   325,
     326,   328,   330,   332,   334,   336,   338,   340,   342,   344,
     346,   398,   417,   460,   469,   477,   487,   505,   514,   535,
     546,   595,   619,   628,   641,   654,   667,   680,   693,   706,
     720,   737,   772,   777,   782,   787,   792,   811,   832,   849,
     850,   853,   870,   871,   885,   900,   914,   928,   942,   956,
     961,   966,   978,   989,  1003,  1009,  1045
};
#endif

#if YYDEBUG || YYERROR_VERBOSE || 0
/* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals.  */
static const char *const yytname[] =
{
  "$end", "error", "$undefined", "TOK_MAIN", "TOK_INT", "TOK_BOOLEAN",
  "TOK_ARRAY", "TOK_FUNCTION", "TOK_IF", "TOK_ELSE", "TOK_WHILE",
  "TOK_SCANF", "TOK_PRINTF", "TOK_RETURN", "TOK_PUNTOYCOMA", "TOK_COMA",
  "TOK_PARENTESISIZQUIERDO", "TOK_PARENTESISDERECHO",
  "TOK_CORCHETEIZQUIERDO", "TOK_CORCHETEDERECHO", "TOK_LLAVEIZQUIERDA",
  "TOK_LLAVEDERECHA", "TOK_ASIGNACION", "TOK_MAS", "TOK_MENOS",
  "TOK_DIVISION", "TOK_ASTERISCO", "TOK_AND", "TOK_OR", "TOK_NOT",
  "TOK_IGUAL", "TOK_DISTINTO", "TOK_MENORIGUAL", "TOK_MAYORIGUAL",
  "TOK_MENOR", "TOK_MAYOR", "TOK_IDENTIFICADOR", "TOK_CONSTANTE_ENTERA",
  "TOK_TRUE", "TOK_FALSE", "TOK_ERROR", "UMINUS", "$accept", "programa",
  "escritura_TS", "escritura_main", "inicio_tabla", "fin_tabla",
  "declaraciones", "declaracion", "clase", "clase_escalar", "tipo",
  "clase_vector", "identificadores", "funciones", "funcion",
  "fn_declaracion", "fn_name", "parametros_funcion",
  "resto_parametros_funcion", "parametro_funcion", "declaraciones_funcion",
  "sentencias", "sentencia", "sentencia_simple", "bloque", "asignacion",
  "elemento_vector", "condicional", "if_exp_sentencias", "if_exp", "bucle",
  "while_exp", "while", "lectura", "escritura", "retorno_funcion", "exp",
  "idf_llamada_funcion", "lista_expresiones", "ini_lst_exp",
  "resto_lista_expresiones", "comparacion", "constante",
  "constante_logica", "constante_entera", "identificador",
  "decidentificador", "ipdf", YY_NULLPTR
};
#endif

# ifdef YYPRINT
/* YYTOKNUM[NUM] -- (External) token number corresponding to the
   (internal) symbol number NUM (which must be that of a token).  */
static const yytype_uint16 yytoknum[] =
{
       0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296
};
# endif

#define YYPACT_NINF -38

#define yypact_value_is_default(Yystate) \
  (!!((Yystate) == (-38)))

#define YYTABLE_NINF -70

#define yytable_value_is_error(Yytable_value) \
  0

  /* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
     STATE-NUM.  */
static const yytype_int16 yypact[] =
{
     -38,     2,    18,   -38,    11,    36,   -38,   -38,    14,   -38,
      36,    -3,   -38,   -38,   -38,    17,    30,   -38,   -38,    29,
      40,     8,    14,   -38,    30,     0,    41,   -38,    -3,   -38,
      48,    23,     0,   -38,    53,    54,    23,    42,    42,   -38,
      67,     0,    76,   -38,   -38,    70,   -38,    84,     0,   -38,
       0,    42,   -38,   -38,   -38,     4,    14,   -38,   -38,   -38,
      73,    42,   -38,   -38,    42,    42,    42,    79,   -38,   -38,
     -38,   130,    88,   -38,   -38,   -38,    87,   130,   -38,   -38,
     -38,    42,    86,    89,    91,    74,    42,    42,    71,    96,
     100,   -38,   124,   105,   106,   -38,   -38,    42,    42,    42,
      42,    42,    42,   107,   130,     0,   116,   -38,   114,    49,
     130,   -38,   -38,   139,    14,   -38,   -38,   140,   -38,    42,
      42,    42,    42,    42,    42,   -38,    27,    27,   -38,   -38,
     -19,   119,   109,    42,   141,   -38,   -38,    36,   100,   -38,
     130,   130,   130,   130,   130,   130,   -38,    93,   -38,   -38,
     -38,   -38,    42,   -38,    93,   -38
};

  /* YYDEFACT[STATE-NUM] -- Default reduction number in state STATE-NUM.
     Performed when YYTABLE does not specify something else to do.  Zero
     means the default is an error.  */
static const yytype_uint8 yydefact[] =
{
       5,     0,     0,     1,     0,     0,    13,    14,     0,     3,
       7,     0,    10,    12,    11,     0,    19,     8,    85,     0,
      16,     0,     0,     4,    19,     0,     0,     9,     0,    83,
       0,     0,     0,    18,     0,     0,     0,     0,     0,    84,
       0,    30,     0,    33,    34,     0,    38,     0,     0,    39,
       0,     0,    35,    36,    37,     0,    24,    17,    15,    22,
       0,     0,    49,    50,     0,     0,     0,    84,    81,    82,
      65,    51,     0,    62,    79,    80,    61,    52,    20,    31,
      32,     0,     0,     0,     0,     0,     0,     0,     0,     0,
      26,     6,     0,     0,     0,    57,    60,     0,     0,     0,
       0,     0,     0,    70,    41,     0,    43,    47,     0,     0,
      40,    86,    27,     0,     0,    23,     2,     0,    63,     0,
       0,     0,     0,     0,     0,    64,    53,    54,    55,    56,
      58,    59,     0,     0,     0,    48,    42,    29,    26,    46,
      73,    74,    75,    76,    77,    78,    66,    72,    44,    28,
      21,    25,     0,    68,    72,    71
};

  /* YYPGOTO[NTERM-NUM].  */
static const yytype_int16 yypgoto[] =
{
     -38,   -38,   -38,   -38,   -38,   -38,   -10,   -38,   -38,   -38,
      -5,   -38,   133,   142,   -38,   -38,   -38,   -38,    25,    50,
     -38,     6,   -38,   -38,   -38,   -38,    -2,   -38,   -38,   -38,
     -38,   -38,   -38,   -38,   -38,   -38,   -37,   -38,   -38,   -38,
      13,   -38,   -38,   -38,   144,   -16,   -38,   -38
};

  /* YYDEFGOTO[NTERM-NUM].  */
static const yytype_int16 yydefgoto[] =
{
      -1,     1,    16,    32,     2,   116,     9,    10,    11,    12,
      13,    14,    19,    23,    24,    25,    26,    89,   115,    90,
     150,    40,    41,    42,    43,    44,    70,    46,    47,    48,
      49,    50,    51,    52,    53,    54,    71,    72,   132,   133,
     153,    94,    73,    74,    75,    76,    20,   112
};

  /* YYTABLE[YYPACT[STATE-NUM]] -- What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule whose
     number is the opposite.  If YYTABLE_NINF, syntax error.  */
static const yytype_int16 yytable[] =
{
      17,    77,     3,    15,    97,    98,    99,   100,    34,    55,
      35,    36,    37,    38,    85,    59,    55,    31,     6,     7,
      63,     4,    86,    45,    92,    55,    87,    93,    95,    96,
      45,     5,    55,    18,    55,    21,    39,    22,    60,    45,
       6,     7,     8,    27,   104,    29,    45,    79,    45,   109,
     110,    88,    99,   100,    83,    28,    84,    56,    64,    39,
     126,   127,   128,   129,   130,   131,    65,    58,   136,    61,
      62,    66,    97,    98,    99,   100,   101,   102,    67,    29,
      68,    69,   140,   141,   142,   143,   144,   145,    78,    55,
      80,   108,    81,    82,    91,   -67,   147,    97,    98,    99,
     100,   101,   102,    45,   103,    86,   105,   111,   152,    88,
     106,   134,   107,   113,   114,   154,    97,    98,    99,   100,
     101,   102,   118,   125,   -69,   -45,   146,   149,    97,    98,
      99,   100,   101,   102,   135,   119,   120,   121,   122,   123,
     124,   117,    97,    98,    99,   100,   101,    97,    98,    99,
     100,   101,   102,    97,    98,    99,   100,   101,   102,   137,
     139,    57,   148,   151,   138,    30,    33,   155
};

static const yytype_uint8 yycheck[] =
{
      10,    38,     0,     8,    23,    24,    25,    26,     8,    25,
      10,    11,    12,    13,    51,    31,    32,    22,     4,     5,
      36,     3,    18,    25,    61,    41,    22,    64,    65,    66,
      32,    20,    48,    36,    50,    18,    36,     7,    32,    41,
       4,     5,     6,    14,    81,    37,    48,    41,    50,    86,
      87,    56,    25,    26,    48,    15,    50,    16,    16,    36,
      97,    98,    99,   100,   101,   102,    24,    19,    19,    16,
      16,    29,    23,    24,    25,    26,    27,    28,    36,    37,
      38,    39,   119,   120,   121,   122,   123,   124,    21,   105,
      14,    17,    22,     9,    21,    16,   133,    23,    24,    25,
      26,    27,    28,   105,    16,    18,    20,    36,    15,   114,
      21,   105,    21,    17,    14,   152,    23,    24,    25,    26,
      27,    28,    17,    17,    17,     9,    17,   137,    23,    24,
      25,    26,    27,    28,    20,    30,    31,    32,    33,    34,
      35,    17,    23,    24,    25,    26,    27,    23,    24,    25,
      26,    27,    28,    23,    24,    25,    26,    27,    28,    20,
      20,    28,    21,   138,   114,    21,    24,   154
};

  /* YYSTOS[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
static const yytype_uint8 yystos[] =
{
       0,    43,    46,     0,     3,    20,     4,     5,     6,    48,
      49,    50,    51,    52,    53,    52,    44,    48,    36,    54,
      88,    18,     7,    55,    56,    57,    58,    14,    15,    37,
      86,    52,    45,    55,     8,    10,    11,    12,    13,    36,
      63,    64,    65,    66,    67,    68,    69,    70,    71,    72,
      73,    74,    75,    76,    77,    87,    16,    54,    19,    87,
      63,    16,    16,    87,    16,    24,    29,    36,    38,    39,
      68,    78,    79,    84,    85,    86,    87,    78,    21,    63,
      14,    22,     9,    63,    63,    78,    18,    22,    52,    59,
      61,    21,    78,    78,    83,    78,    78,    23,    24,    25,
      26,    27,    28,    16,    78,    20,    21,    21,    17,    78,
      78,    36,    89,    17,    14,    60,    47,    17,    17,    30,
      31,    32,    33,    34,    35,    17,    78,    78,    78,    78,
      78,    78,    80,    81,    63,    20,    19,    20,    61,    20,
      78,    78,    78,    78,    78,    78,    17,    78,    21,    48,
      62,    60,    15,    82,    78,    82
};

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
static const yytype_uint8 yyr1[] =
{
       0,    42,    43,    44,    45,    46,    47,    48,    48,    49,
      50,    50,    51,    52,    52,    53,    54,    54,    55,    55,
      56,    57,    58,    59,    59,    60,    60,    61,    62,    62,
      63,    63,    64,    64,    65,    65,    65,    65,    66,    66,
      67,    67,    68,    69,    69,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    78,    78,    78,    78,    78,    78,
      78,    78,    78,    78,    78,    78,    78,    79,    80,    80,
      81,    82,    82,    83,    83,    83,    83,    83,    83,    84,
      84,    85,    85,    86,    87,    88,    89
};

  /* YYR2[YYN] -- Number of symbols on the right hand side of rule YYN.  */
static const yytype_uint8 yyr2[] =
{
       0,     2,    10,     0,     0,     0,     0,     1,     2,     3,
       1,     1,     1,     1,     1,     5,     1,     3,     2,     0,
       3,     6,     3,     2,     0,     3,     0,     2,     1,     0,
       1,     2,     2,     1,     1,     1,     1,     1,     1,     1,
       3,     3,     4,     3,     5,     3,     5,     3,     4,     2,
       2,     2,     2,     3,     3,     3,     3,     2,     3,     3,
       2,     1,     1,     3,     3,     1,     4,     1,     3,     0,
       0,     3,     0,     3,     3,     3,     3,     3,     3,     1,
       1,     1,     1,     1,     1,     1,     1
};


#define yyerrok         (yyerrstatus = 0)
#define yyclearin       (yychar = YYEMPTY)
#define YYEMPTY         (-2)
#define YYEOF           0

#define YYACCEPT        goto yyacceptlab
#define YYABORT         goto yyabortlab
#define YYERROR         goto yyerrorlab


#define YYRECOVERING()  (!!yyerrstatus)

#define YYBACKUP(Token, Value)                                  \
do                                                              \
  if (yychar == YYEMPTY)                                        \
    {                                                           \
      yychar = (Token);                                         \
      yylval = (Value);                                         \
      YYPOPSTACK (yylen);                                       \
      yystate = *yyssp;                                         \
      goto yybackup;                                            \
    }                                                           \
  else                                                          \
    {                                                           \
      yyerror (YY_("syntax error: cannot back up")); \
      YYERROR;                                                  \
    }                                                           \
while (0)

/* Error token number */
#define YYTERROR        1
#define YYERRCODE       256



/* Enable debugging if requested.  */
#if YYDEBUG

# ifndef YYFPRINTF
#  include <stdio.h> /* INFRINGES ON USER NAME SPACE */
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)                        \
do {                                            \
  if (yydebug)                                  \
    YYFPRINTF Args;                             \
} while (0)

/* This macro is provided for backward compatibility. */
#ifndef YY_LOCATION_PRINT
# define YY_LOCATION_PRINT(File, Loc) ((void) 0)
#endif


# define YY_SYMBOL_PRINT(Title, Type, Value, Location)                    \
do {                                                                      \
  if (yydebug)                                                            \
    {                                                                     \
      YYFPRINTF (stderr, "%s ", Title);                                   \
      yy_symbol_print (stderr,                                            \
                  Type, Value); \
      YYFPRINTF (stderr, "\n");                                           \
    }                                                                     \
} while (0)


/*----------------------------------------.
| Print this symbol's value on YYOUTPUT.  |
`----------------------------------------*/

static void
yy_symbol_value_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
{
  FILE *yyo = yyoutput;
  YYUSE (yyo);
  if (!yyvaluep)
    return;
# ifdef YYPRINT
  if (yytype < YYNTOKENS)
    YYPRINT (yyoutput, yytoknum[yytype], *yyvaluep);
# endif
  YYUSE (yytype);
}


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

static void
yy_symbol_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
{
  YYFPRINTF (yyoutput, "%s %s (",
             yytype < YYNTOKENS ? "token" : "nterm", yytname[yytype]);

  yy_symbol_value_print (yyoutput, yytype, yyvaluep);
  YYFPRINTF (yyoutput, ")");
}

/*------------------------------------------------------------------.
| yy_stack_print -- Print the state stack from its BOTTOM up to its |
| TOP (included).                                                   |
`------------------------------------------------------------------*/

static void
yy_stack_print (yytype_int16 *yybottom, yytype_int16 *yytop)
{
  YYFPRINTF (stderr, "Stack now");
  for (; yybottom <= yytop; yybottom++)
    {
      int yybot = *yybottom;
      YYFPRINTF (stderr, " %d", yybot);
    }
  YYFPRINTF (stderr, "\n");
}

# define YY_STACK_PRINT(Bottom, Top)                            \
do {                                                            \
  if (yydebug)                                                  \
    yy_stack_print ((Bottom), (Top));                           \
} while (0)


/*------------------------------------------------.
| Report that the YYRULE is going to be reduced.  |
`------------------------------------------------*/

static void
yy_reduce_print (yytype_int16 *yyssp, YYSTYPE *yyvsp, int yyrule)
{
  unsigned long int yylno = yyrline[yyrule];
  int yynrhs = yyr2[yyrule];
  int yyi;
  YYFPRINTF (stderr, "Reducing stack by rule %d (line %lu):\n",
             yyrule - 1, yylno);
  /* The symbols being reduced.  */
  for (yyi = 0; yyi < yynrhs; yyi++)
    {
      YYFPRINTF (stderr, "   $%d = ", yyi + 1);
      yy_symbol_print (stderr,
                       yystos[yyssp[yyi + 1 - yynrhs]],
                       &(yyvsp[(yyi + 1) - (yynrhs)])
                                              );
      YYFPRINTF (stderr, "\n");
    }
}

# define YY_REDUCE_PRINT(Rule)          \
do {                                    \
  if (yydebug)                          \
    yy_reduce_print (yyssp, yyvsp, Rule); \
} while (0)

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;
#else /* !YYDEBUG */
# define YYDPRINTF(Args)
# define YY_SYMBOL_PRINT(Title, Type, Value, Location)
# define YY_STACK_PRINT(Bottom, Top)
# define YY_REDUCE_PRINT(Rule)
#endif /* !YYDEBUG */


/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef YYINITDEPTH
# define YYINITDEPTH 200
#endif

/* YYMAXDEPTH -- maximum size the stacks can grow to (effective only
   if the built-in stack extension method is used).

   Do not make this value too large; the results are undefined if
   YYSTACK_ALLOC_MAXIMUM < YYSTACK_BYTES (YYMAXDEPTH)
   evaluated with infinite-precision integer arithmetic.  */

#ifndef YYMAXDEPTH
# define YYMAXDEPTH 10000
#endif


#if YYERROR_VERBOSE

# ifndef yystrlen
#  if defined __GLIBC__ && defined _STRING_H
#   define yystrlen strlen
#  else
/* Return the length of YYSTR.  */
static YYSIZE_T
yystrlen (const char *yystr)
{
  YYSIZE_T yylen;
  for (yylen = 0; yystr[yylen]; yylen++)
    continue;
  return yylen;
}
#  endif
# endif

# ifndef yystpcpy
#  if defined __GLIBC__ && defined _STRING_H && defined _GNU_SOURCE
#   define yystpcpy stpcpy
#  else
/* Copy YYSRC to YYDEST, returning the address of the terminating '\0' in
   YYDEST.  */
static char *
yystpcpy (char *yydest, const char *yysrc)
{
  char *yyd = yydest;
  const char *yys = yysrc;

  while ((*yyd++ = *yys++) != '\0')
    continue;

  return yyd - 1;
}
#  endif
# endif

# ifndef yytnamerr
/* Copy to YYRES the contents of YYSTR after stripping away unnecessary
   quotes and backslashes, so that it's suitable for yyerror.  The
   heuristic is that double-quoting is unnecessary unless the string
   contains an apostrophe, a comma, or backslash (other than
   backslash-backslash).  YYSTR is taken from yytname.  If YYRES is
   null, do not copy; instead, return the length of what the result
   would have been.  */
static YYSIZE_T
yytnamerr (char *yyres, const char *yystr)
{
  if (*yystr == '"')
    {
      YYSIZE_T yyn = 0;
      char const *yyp = yystr;

      for (;;)
        switch (*++yyp)
          {
          case '\'':
          case ',':
            goto do_not_strip_quotes;

          case '\\':
            if (*++yyp != '\\')
              goto do_not_strip_quotes;
            /* Fall through.  */
          default:
            if (yyres)
              yyres[yyn] = *yyp;
            yyn++;
            break;

          case '"':
            if (yyres)
              yyres[yyn] = '\0';
            return yyn;
          }
    do_not_strip_quotes: ;
    }

  if (! yyres)
    return yystrlen (yystr);

  return yystpcpy (yyres, yystr) - yyres;
}
# endif

/* Copy into *YYMSG, which is of size *YYMSG_ALLOC, an error message
   about the unexpected token YYTOKEN for the state stack whose top is
   YYSSP.

   Return 0 if *YYMSG was successfully written.  Return 1 if *YYMSG is
   not large enough to hold the message.  In that case, also set
   *YYMSG_ALLOC to the required number of bytes.  Return 2 if the
   required number of bytes is too large to store.  */
static int
yysyntax_error (YYSIZE_T *yymsg_alloc, char **yymsg,
                yytype_int16 *yyssp, int yytoken)
{
  YYSIZE_T yysize0 = yytnamerr (YY_NULLPTR, yytname[yytoken]);
  YYSIZE_T yysize = yysize0;
  enum { YYERROR_VERBOSE_ARGS_MAXIMUM = 5 };
  /* Internationalized format string. */
  const char *yyformat = YY_NULLPTR;
  /* Arguments of yyformat. */
  char const *yyarg[YYERROR_VERBOSE_ARGS_MAXIMUM];
  /* Number of reported tokens (one for the "unexpected", one per
     "expected"). */
  int yycount = 0;

  /* There are many possibilities here to consider:
     - If this state is a consistent state with a default action, then
       the only way this function was invoked is if the default action
       is an error action.  In that case, don't check for expected
       tokens because there are none.
     - The only way there can be no lookahead present (in yychar) is if
       this state is a consistent state with a default action.  Thus,
       detecting the absence of a lookahead is sufficient to determine
       that there is no unexpected or expected token to report.  In that
       case, just report a simple "syntax error".
     - Don't assume there isn't a lookahead just because this state is a
       consistent state with a default action.  There might have been a
       previous inconsistent state, consistent state with a non-default
       action, or user semantic action that manipulated yychar.
     - Of course, the expected token list depends on states to have
       correct lookahead information, and it depends on the parser not
       to perform extra reductions after fetching a lookahead from the
       scanner and before detecting a syntax error.  Thus, state merging
       (from LALR or IELR) and default reductions corrupt the expected
       token list.  However, the list is correct for canonical LR with
       one exception: it will still contain any token that will not be
       accepted due to an error action in a later state.
  */
  if (yytoken != YYEMPTY)
    {
      int yyn = yypact[*yyssp];
      yyarg[yycount++] = yytname[yytoken];
      if (!yypact_value_is_default (yyn))
        {
          /* Start YYX at -YYN if negative to avoid negative indexes in
             YYCHECK.  In other words, skip the first -YYN actions for
             this state because they are default actions.  */
          int yyxbegin = yyn < 0 ? -yyn : 0;
          /* Stay within bounds of both yycheck and yytname.  */
          int yychecklim = YYLAST - yyn + 1;
          int yyxend = yychecklim < YYNTOKENS ? yychecklim : YYNTOKENS;
          int yyx;

          for (yyx = yyxbegin; yyx < yyxend; ++yyx)
            if (yycheck[yyx + yyn] == yyx && yyx != YYTERROR
                && !yytable_value_is_error (yytable[yyx + yyn]))
              {
                if (yycount == YYERROR_VERBOSE_ARGS_MAXIMUM)
                  {
                    yycount = 1;
                    yysize = yysize0;
                    break;
                  }
                yyarg[yycount++] = yytname[yyx];
                {
                  YYSIZE_T yysize1 = yysize + yytnamerr (YY_NULLPTR, yytname[yyx]);
                  if (! (yysize <= yysize1
                         && yysize1 <= YYSTACK_ALLOC_MAXIMUM))
                    return 2;
                  yysize = yysize1;
                }
              }
        }
    }

  switch (yycount)
    {
# define YYCASE_(N, S)                      \
      case N:                               \
        yyformat = S;                       \
      break
      YYCASE_(0, YY_("syntax error"));
      YYCASE_(1, YY_("syntax error, unexpected %s"));
      YYCASE_(2, YY_("syntax error, unexpected %s, expecting %s"));
      YYCASE_(3, YY_("syntax error, unexpected %s, expecting %s or %s"));
      YYCASE_(4, YY_("syntax error, unexpected %s, expecting %s or %s or %s"));
      YYCASE_(5, YY_("syntax error, unexpected %s, expecting %s or %s or %s or %s"));
# undef YYCASE_
    }

  {
    YYSIZE_T yysize1 = yysize + yystrlen (yyformat);
    if (! (yysize <= yysize1 && yysize1 <= YYSTACK_ALLOC_MAXIMUM))
      return 2;
    yysize = yysize1;
  }

  if (*yymsg_alloc < yysize)
    {
      *yymsg_alloc = 2 * yysize;
      if (! (yysize <= *yymsg_alloc
             && *yymsg_alloc <= YYSTACK_ALLOC_MAXIMUM))
        *yymsg_alloc = YYSTACK_ALLOC_MAXIMUM;
      return 1;
    }

  /* Avoid sprintf, as that infringes on the user's name space.
     Don't have undefined behavior even if the translation
     produced a string with the wrong number of "%s"s.  */
  {
    char *yyp = *yymsg;
    int yyi = 0;
    while ((*yyp = *yyformat) != '\0')
      if (*yyp == '%' && yyformat[1] == 's' && yyi < yycount)
        {
          yyp += yytnamerr (yyp, yyarg[yyi++]);
          yyformat += 2;
        }
      else
        {
          yyp++;
          yyformat++;
        }
  }
  return 0;
}
#endif /* YYERROR_VERBOSE */

/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

static void
yydestruct (const char *yymsg, int yytype, YYSTYPE *yyvaluep)
{
  YYUSE (yyvaluep);
  if (!yymsg)
    yymsg = "Deleting";
  YY_SYMBOL_PRINT (yymsg, yytype, yyvaluep, yylocationp);

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  YYUSE (yytype);
  YY_IGNORE_MAYBE_UNINITIALIZED_END
}




/* The lookahead symbol.  */
int yychar;

/* The semantic value of the lookahead symbol.  */
YYSTYPE yylval;
/* Number of syntax errors so far.  */
int yynerrs;


/*----------.
| yyparse.  |
`----------*/

int
yyparse (void)
{
    int yystate;
    /* Number of tokens to shift before error messages enabled.  */
    int yyerrstatus;

    /* The stacks and their tools:
       'yyss': related to states.
       'yyvs': related to semantic values.

       Refer to the stacks through separate pointers, to allow yyoverflow
       to reallocate them elsewhere.  */

    /* The state stack.  */
    yytype_int16 yyssa[YYINITDEPTH];
    yytype_int16 *yyss;
    yytype_int16 *yyssp;

    /* The semantic value stack.  */
    YYSTYPE yyvsa[YYINITDEPTH];
    YYSTYPE *yyvs;
    YYSTYPE *yyvsp;

    YYSIZE_T yystacksize;

  int yyn;
  int yyresult;
  /* Lookahead token as an internal (translated) token number.  */
  int yytoken = 0;
  /* The variables used to return semantic value and location from the
     action routines.  */
  YYSTYPE yyval;

#if YYERROR_VERBOSE
  /* Buffer for error messages, and its allocated size.  */
  char yymsgbuf[128];
  char *yymsg = yymsgbuf;
  YYSIZE_T yymsg_alloc = sizeof yymsgbuf;
#endif

#define YYPOPSTACK(N)   (yyvsp -= (N), yyssp -= (N))

  /* The number of symbols on the RHS of the reduced rule.
     Keep to zero when no symbol should be popped.  */
  int yylen = 0;

  yyssp = yyss = yyssa;
  yyvsp = yyvs = yyvsa;
  yystacksize = YYINITDEPTH;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yystate = 0;
  yyerrstatus = 0;
  yynerrs = 0;
  yychar = YYEMPTY; /* Cause a token to be read.  */
  goto yysetstate;

/*------------------------------------------------------------.
| yynewstate -- Push a new state, which is found in yystate.  |
`------------------------------------------------------------*/
 yynewstate:
  /* In all cases, when you get here, the value and location stacks
     have just been pushed.  So pushing a state here evens the stacks.  */
  yyssp++;

 yysetstate:
  *yyssp = yystate;

  if (yyss + yystacksize - 1 <= yyssp)
    {
      /* Get the current used size of the three stacks, in elements.  */
      YYSIZE_T yysize = yyssp - yyss + 1;

#ifdef yyoverflow
      {
        /* Give user a chance to reallocate the stack.  Use copies of
           these so that the &'s don't force the real ones into
           memory.  */
        YYSTYPE *yyvs1 = yyvs;
        yytype_int16 *yyss1 = yyss;

        /* Each stack pointer address is followed by the size of the
           data in use in that stack, in bytes.  This used to be a
           conditional around just the two extra args, but that might
           be undefined if yyoverflow is a macro.  */
        yyoverflow (YY_("memory exhausted"),
                    &yyss1, yysize * sizeof (*yyssp),
                    &yyvs1, yysize * sizeof (*yyvsp),
                    &yystacksize);

        yyss = yyss1;
        yyvs = yyvs1;
      }
#else /* no yyoverflow */
# ifndef YYSTACK_RELOCATE
      goto yyexhaustedlab;
# else
      /* Extend the stack our own way.  */
      if (YYMAXDEPTH <= yystacksize)
        goto yyexhaustedlab;
      yystacksize *= 2;
      if (YYMAXDEPTH < yystacksize)
        yystacksize = YYMAXDEPTH;

      {
        yytype_int16 *yyss1 = yyss;
        union yyalloc *yyptr =
          (union yyalloc *) YYSTACK_ALLOC (YYSTACK_BYTES (yystacksize));
        if (! yyptr)
          goto yyexhaustedlab;
        YYSTACK_RELOCATE (yyss_alloc, yyss);
        YYSTACK_RELOCATE (yyvs_alloc, yyvs);
#  undef YYSTACK_RELOCATE
        if (yyss1 != yyssa)
          YYSTACK_FREE (yyss1);
      }
# endif
#endif /* no yyoverflow */

      yyssp = yyss + yysize - 1;
      yyvsp = yyvs + yysize - 1;

      YYDPRINTF ((stderr, "Stack size increased to %lu\n",
                  (unsigned long int) yystacksize));

      if (yyss + yystacksize - 1 <= yyssp)
        YYABORT;
    }

  YYDPRINTF ((stderr, "Entering state %d\n", yystate));

  if (yystate == YYFINAL)
    YYACCEPT;

  goto yybackup;

/*-----------.
| yybackup.  |
`-----------*/
yybackup:

  /* Do appropriate processing given the current state.  Read a
     lookahead token if we need one and don't already have one.  */

  /* First try to decide what to do without reference to lookahead token.  */
  yyn = yypact[yystate];
  if (yypact_value_is_default (yyn))
    goto yydefault;

  /* Not known => get a lookahead token if don't already have one.  */

  /* YYCHAR is either YYEMPTY or YYEOF or a valid lookahead symbol.  */
  if (yychar == YYEMPTY)
    {
      YYDPRINTF ((stderr, "Reading a token: "));
      yychar = yylex ();
    }

  if (yychar <= YYEOF)
    {
      yychar = yytoken = YYEOF;
      YYDPRINTF ((stderr, "Now at end of input.\n"));
    }
  else
    {
      yytoken = YYTRANSLATE (yychar);
      YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
    }

  /* If the proper action on seeing token YYTOKEN is to reduce or to
     detect an error, take that action.  */
  yyn += yytoken;
  if (yyn < 0 || YYLAST < yyn || yycheck[yyn] != yytoken)
    goto yydefault;
  yyn = yytable[yyn];
  if (yyn <= 0)
    {
      if (yytable_value_is_error (yyn))
        goto yyerrlab;
      yyn = -yyn;
      goto yyreduce;
    }

  /* Count tokens shifted since error; after three, turn off error
     status.  */
  if (yyerrstatus)
    yyerrstatus--;

  /* Shift the lookahead token.  */
  YY_SYMBOL_PRINT ("Shifting", yytoken, &yylval, &yylloc);

  /* Discard the shifted token.  */
  yychar = YYEMPTY;

  yystate = yyn;
  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END

  goto yynewstate;


/*-----------------------------------------------------------.
| yydefault -- do the default action for the current state.  |
`-----------------------------------------------------------*/
yydefault:
  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;
  goto yyreduce;


/*-----------------------------.
| yyreduce -- Do a reduction.  |
`-----------------------------*/
yyreduce:
  /* yyn is the number of a rule to reduce with.  */
  yylen = yyr2[yyn];

  /* If YYLEN is nonzero, implement the default value of the action:
     '$$ = $1'.

     Otherwise, the following line sets YYVAL to garbage.
     This behavior is undocumented and Bison
     users should not rely upon it.  Assigning to YYVAL
     unconditionally makes the parser a bit smaller, and it avoids a
     GCC warning that YYVAL may be used uninitialized.  */
  yyval = yyvsp[1-yylen];


  YY_REDUCE_PRINT (yyn);
  switch (yyn)
    {
        case 2:
#line 156 "gramatica.y" /* yacc.c:1646  */
    {printf(";R1:\t<programa> ::= main { <declaraciones> <funciones> <sentencias> }\n");}
#line 1455 "y.tab.c" /* yacc.c:1646  */
    break;

  case 3:
#line 157 "gramatica.y" /* yacc.c:1646  */
    {
				printf("escritura_TS\n");
				decvariables(tglobal , decvar, nvar);

				fprintf(nasm, "segment .text\n");
				fprintf(nasm, "global main\n");
				fprintf(nasm, "extern scan_int, scan_boolean\n");
				fprintf(nasm, "extern print_int, print_boolean, print_string,print_blank,print_endofline\n");
			}
#line 1469 "y.tab.c" /* yacc.c:1646  */
    break;

  case 4:
#line 166 "gramatica.y" /* yacc.c:1646  */
    {

			fprintf(nasm, "main:\n");

}
#line 1479 "y.tab.c" /* yacc.c:1646  */
    break;

  case 5:
#line 171 "gramatica.y" /* yacc.c:1646  */
    {
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
#line 1496 "y.tab.c" /* yacc.c:1646  */
    break;

  case 6:
#line 183 "gramatica.y" /* yacc.c:1646  */
    {
			gc_msjerr();
			printf("\t\t se destruye la tabla global\n");
			hash_destroy_table(tglobal);
			return;
			}
#line 1507 "y.tab.c" /* yacc.c:1646  */
    break;

  case 7:
#line 190 "gramatica.y" /* yacc.c:1646  */
    {printf(";R2:\t<declaraciones> ::= <declaracion>\n");}
#line 1513 "y.tab.c" /* yacc.c:1646  */
    break;

  case 8:
#line 192 "gramatica.y" /* yacc.c:1646  */
    {printf(";R3:\t<declaraciones> ::= <declaracion> <declaraciones>\n");}
#line 1519 "y.tab.c" /* yacc.c:1646  */
    break;

  case 9:
#line 194 "gramatica.y" /* yacc.c:1646  */
    {printf(";R4:\t<declaracion> ::= <clase> <identificadores> ;\n");
		}
#line 1526 "y.tab.c" /* yacc.c:1646  */
    break;

  case 10:
#line 197 "gramatica.y" /* yacc.c:1646  */
    {printf(";R5:\t<clase> ::= <clase_escalar>\n");clase_actual = ESCALAR;}
#line 1532 "y.tab.c" /* yacc.c:1646  */
    break;

  case 11:
#line 199 "gramatica.y" /* yacc.c:1646  */
    {printf(";R7:\t<clase> ::= <clase_vector>\n");clase_actual = VECTOR;}
#line 1538 "y.tab.c" /* yacc.c:1646  */
    break;

  case 12:
#line 201 "gramatica.y" /* yacc.c:1646  */
    {printf(";R9:\t<clase_escalar> ::= <tipo> ==");
			if ((yyvsp[0].atributos).tipo==BOOL)
				printf("BOOL\n");

			if ((yyvsp[0].atributos).tipo==INT)
				printf("INT\n");
			tipo_actual=(yyvsp[0].atributos).tipo;
			printf("\t\ttipoactual:%d\n", tipo_actual);
			}
#line 1552 "y.tab.c" /* yacc.c:1646  */
    break;

  case 13:
#line 211 "gramatica.y" /* yacc.c:1646  */
    {printf(";R10:\t<tipo> ::= int\n");
			(yyval.atributos).tipo=INT;
			printf("\t\tint=%d\n",INT);
		}
#line 1561 "y.tab.c" /* yacc.c:1646  */
    break;

  case 14:
#line 216 "gramatica.y" /* yacc.c:1646  */
    {printf(";R11:\t<tipo> ::= boolean\n");
			(yyval.atributos).tipo=BOOL;
		}
#line 1569 "y.tab.c" /* yacc.c:1646  */
    break;

  case 15:
#line 220 "gramatica.y" /* yacc.c:1646  */
    {tamanio_vector_actual=(yyvsp[-1].atributos).valor_entero;
		if(tamanio_vector_actual<1){
			fprintf(stderr,"****ERROR semantico en lin %d: el tamanyo del vector%s excede los limites permitidos (1,64).\n",ln,(yyvsp[-1].atributos).lexema);
			return;
			
		}
		if(tamanio_vector_actual>64){
			
			fprintf(stderr,"****ERROR semantico en lin %d: el tamanyo del vector%s excede los limites permitidos (1,64).\n",ln,(yyvsp[-1].atributos).lexema);
			return;
			
		}
			;printf(";R15:\t<clase_vector> ::= array <tipo> [ <constante_entera> ] == %d\n", tamanio_vector_actual);}
#line 1587 "y.tab.c" /* yacc.c:1646  */
    break;

  case 16:
#line 234 "gramatica.y" /* yacc.c:1646  */
    {printf(";R18:\t<identificadores> ::= <identificador>\n");

			/*printf("\t\tlexema:%s  tipo:%d\n", auxdato->lexema, auxdato->tipo);
			if(NULL==hash_insert_table(tlocal,tglobal, $1.lexema,auxdato)){
				fprintf(stderr,"****ERROR semantico en lin %d: Declaracion duplicada.\n",ln);
			return;
			}*/
		}
#line 1600 "y.tab.c" /* yacc.c:1646  */
    break;

  case 17:
#line 245 "gramatica.y" /* yacc.c:1646  */
    {printf(";R19:\t<identificadores> ::= <identificador> , <identificadores>\n");

			/*printf("\t\tlexema:%s  tipo:%d\n", auxdato->lexema, auxdato->tipo);
			if(NULL==hash_insert_table(tlocal,tglobal, $1.lexema,&dato)){
				printf("\t\tERROR: %s ya se declaro.\n",$1.lexema );
				return;
			}*/
		}
#line 1613 "y.tab.c" /* yacc.c:1646  */
    break;

  case 18:
#line 256 "gramatica.y" /* yacc.c:1646  */
    {printf(";R20:\t<funciones> ::= <funcion> <funciones>\n");}
#line 1619 "y.tab.c" /* yacc.c:1646  */
    break;

  case 19:
#line 258 "gramatica.y" /* yacc.c:1646  */
    {printf(";R21:\t<funciones> ::=\n");}
#line 1625 "y.tab.c" /* yacc.c:1646  */
    break;

  case 20:
#line 260 "gramatica.y" /* yacc.c:1646  */
    {printf(";R22:\t<funcion> ::= function <tipo> <identificador> ( <parametros_funcion> ) { <declaraciones_funcion> <sentencias> }\n");
			hash_destroy(tlocal);
			tlocal=NULL;
			printf("\t\tSe destruye tabla local\n");
			num_variables_local_actual =0;
			pos_variable_local_actual=1;

		}
#line 1638 "y.tab.c" /* yacc.c:1646  */
    break;

  case 21:
#line 268 "gramatica.y" /* yacc.c:1646  */
    {
			auxdato=hash_find_table(tlocal,tglobal,(yyvsp[-5].atributos).lexema);
			auxdato->nparam= num_parametros_actual;
			auxdato->pparam= pos_parametro_actual;
			fprintf(nasm, "_%s:\n",(yyvsp[-5].atributos).lexema);
			fprintf(nasm, "push ebp \n");
			fprintf(nasm, "mov ebp,esp\n");
			fprintf(nasm, "sub esp, %d\n",4*num_parametros_actual);
		}
#line 1652 "y.tab.c" /* yacc.c:1646  */
    break;

  case 22:
#line 278 "gramatica.y" /* yacc.c:1646  */
    {			if (tlocal!=NULL){
				printf("no se puede llamar a una funcion dentrode una funcion\n");
				return;
			}
			printf("lexema:%s\n",(yyvsp[0].atributos).lexema);
			if(NULL!=(auxdato=hash_find_table(tlocal,tglobal,(yyvsp[0].atributos).lexema))){
				printf("%s: ya existe la variable o funcion\n",(yyvsp[0].atributos).lexema);
				return;
			}
			clase_actual=0;
			tipo_actual = (yyvsp[-1].atributos).tipo;
			printf("%d\n",nfunc);
			decfunc[nfunc]=(char*)malloc(sizeof(char*)*strlen((yyvsp[0].atributos).lexema));
			strcpy(decfunc[nfunc++],(yyvsp[0].atributos).lexema);
			printf("1\n");
			auxdato=(nodo*)malloc(sizeof(nodo));
			auxdato->lexema=(yyvsp[0].atributos).lexema;
			auxdato->categoria=FUNCION;
			auxdato->tipo=tipo_actual;
			auxdato->clase = clase_actual;
			auxdato->tam=0;
			auxdato->nparam=0;		
    		auxdato->pparam=0;		
    		auxdato->nvar=0;		
    		auxdato->pvar=0;
			hash_insert_table(tlocal,tglobal, (yyvsp[0].atributos).lexema	,auxdato);
			num_variables_local_actual =0;
			pos_variable_local_actual=1;
			num_parametros_actual=0;
			pos_parametro_actual=0;
			tlocal= hash_create_t_local();
			(yyval.atributos).lexema =(yyvsp[0].atributos).lexema;
		}
#line 1690 "y.tab.c" /* yacc.c:1646  */
    break;

  case 23:
#line 312 "gramatica.y" /* yacc.c:1646  */
    {printf(";R23:\t<parametros_funcion> ::= <parametro_funcion> <resto_parametros_funcion>\n");}
#line 1696 "y.tab.c" /* yacc.c:1646  */
    break;

  case 24:
#line 314 "gramatica.y" /* yacc.c:1646  */
    {printf(";R24:\t<parametros_funcion> ::=\n");}
#line 1702 "y.tab.c" /* yacc.c:1646  */
    break;

  case 25:
#line 316 "gramatica.y" /* yacc.c:1646  */
    {printf(";R25:\t<resto_parametros_funcion> ::= ; <parametro_funcion> <resto_parametros_funcion>\n");}
#line 1708 "y.tab.c" /* yacc.c:1646  */
    break;

  case 26:
#line 318 "gramatica.y" /* yacc.c:1646  */
    {printf(";R26:\t<resto_parametros_funcion> ::=\n");}
#line 1714 "y.tab.c" /* yacc.c:1646  */
    break;

  case 27:
#line 320 "gramatica.y" /* yacc.c:1646  */
    {printf(";R27:\t<parametro_funcion> ::= <tipo> <identificador>\n");
		}
#line 1721 "y.tab.c" /* yacc.c:1646  */
    break;

  case 28:
#line 323 "gramatica.y" /* yacc.c:1646  */
    {printf(";R28:\t<declaraciones_funcion> ::= <declaraciones>\n");}
#line 1727 "y.tab.c" /* yacc.c:1646  */
    break;

  case 29:
#line 325 "gramatica.y" /* yacc.c:1646  */
    {printf(";R29:\t<declaraciones_funcion> ::= \n");}
#line 1733 "y.tab.c" /* yacc.c:1646  */
    break;

  case 30:
#line 327 "gramatica.y" /* yacc.c:1646  */
    {printf(";R30:\t<sentencias> ::= <sentencia>\n");}
#line 1739 "y.tab.c" /* yacc.c:1646  */
    break;

  case 31:
#line 329 "gramatica.y" /* yacc.c:1646  */
    {printf(";R31:\t<sentencias> ::= <sentencia> <sentencias>\n");}
#line 1745 "y.tab.c" /* yacc.c:1646  */
    break;

  case 32:
#line 331 "gramatica.y" /* yacc.c:1646  */
    {printf(";R32:\t<sentencia> ::= <sentencia_simple> ;\n");}
#line 1751 "y.tab.c" /* yacc.c:1646  */
    break;

  case 33:
#line 333 "gramatica.y" /* yacc.c:1646  */
    {printf(";R33:\t<sentencia> ::= <bloque> \n");}
#line 1757 "y.tab.c" /* yacc.c:1646  */
    break;

  case 34:
#line 335 "gramatica.y" /* yacc.c:1646  */
    {printf(";R34:\t<sentencia_simple> ::= <asignacion>\n");}
#line 1763 "y.tab.c" /* yacc.c:1646  */
    break;

  case 35:
#line 337 "gramatica.y" /* yacc.c:1646  */
    {printf(";R35:\t<sentencia_simple> ::= <lectura>\n");}
#line 1769 "y.tab.c" /* yacc.c:1646  */
    break;

  case 36:
#line 339 "gramatica.y" /* yacc.c:1646  */
    {printf(";R36:\t<sentencia_simple> ::= <escritura>\n");}
#line 1775 "y.tab.c" /* yacc.c:1646  */
    break;

  case 37:
#line 341 "gramatica.y" /* yacc.c:1646  */
    {printf(";R38:\t<sentencia_simple> ::= <retorno_funcion>\n");}
#line 1781 "y.tab.c" /* yacc.c:1646  */
    break;

  case 38:
#line 343 "gramatica.y" /* yacc.c:1646  */
    {printf(";R40:\t<bloque> ::= <condicional>\n");}
#line 1787 "y.tab.c" /* yacc.c:1646  */
    break;

  case 39:
#line 345 "gramatica.y" /* yacc.c:1646  */
    {printf(";R41:\t<bloque> ::= <bucle>\n");}
#line 1793 "y.tab.c" /* yacc.c:1646  */
    break;

  case 40:
#line 347 "gramatica.y" /* yacc.c:1646  */
    {printf(";R43:\t<asignacion> ::= <identificador> = <exp>\n");
			if(NULL==(auxdato=hash_find_table(tlocal,tglobal,(yyvsp[-2].atributos).lexema))){
				fprintf(stderr,"****ERROR semantico en lin %d: Acceso a variable no declarada (%s).\n",ln,(yyvsp[-2].atributos).lexema);
				return;
			}
			if(auxdato->categoria==FUNCION){
				
				fprintf(stderr,"****ERROR semantico en lin %d: No se esperaba una fucion.\n",ln);
				return;
			}
			if(auxdato->tipo!=(yyvsp[0].atributos).tipo){
				
				printf("%d=%d\n",auxdato->tipo,(yyvsp[0].atributos).tipo);
				fprintf(stderr,"****ERROR semantico en lin %d: Asignacion incompatible (%s).\n",ln,auxdato->lexema);
				return;
			}
			/*generacion de codigo*/
			if(auxdato->categoria==VARIABLE){
				if(NULL==(auxdato=hash_find_table(NULL,tlocal,(yyvsp[-2].atributos).lexema))){
					fprintf(nasm," \t\t\t;empieza asignacion\n");
					fprintf(nasm,";Cargar en eax la parte derecha de la asignacion\n");
					fprintf(nasm,"pop dword eax\n");
					if((yyvsp[0].atributos).es_direccion==1)
						fprintf(nasm,"mov dword eax, [eax]\n");
					fprintf(nasm,";hacer la asignacion efectiva\n");
					fprintf(nasm,"mov dword [_%s],eax\n",(yyvsp[-2].atributos).lexema);
					fprintf(nasm," \t\t\t;termina asignacion\n");
				}
				else{
					fprintf(nasm," \t\t\t;empieza asignacion\n");
					fprintf(nasm,";Cargar en eax la parte derecha de la asignacion\n");
					fprintf(nasm,"pop dword eax\n");
					if((yyvsp[0].atributos).es_direccion==1)
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
				if((yyvsp[0].atributos).es_direccion==1)
					fprintf(nasm,"mov dword eax, [eax]\n");
				fprintf(nasm,";hacer la asignacion efectiva\n");
				fprintf(nasm,"mov dword [ebp +%d],eax\n",4+4*(auxdato->pparam));
				fprintf(nasm," \t\t\t;termina asignacion\n");
			}
			
		}
#line 1849 "y.tab.c" /* yacc.c:1646  */
    break;

  case 41:
#line 399 "gramatica.y" /* yacc.c:1646  */
    {printf(";R44:\t<asignacion> ::= <elemento_vector> = <exp>\n");
			if((yyvsp[-2].atributos).tipo!=(yyvsp[0].atributos).tipo){
				fprintf(stderr,"****ERROR semantico en lin %d: Asignacion incompatible.\n",ln);
				return;
			}
				/*generacion de codigo*/
			fprintf(nasm," \t\t\t;empieza asignacion\n");
			fprintf(nasm,";Cargar en eax la parte derecha de la asignacion\n");
			fprintf(nasm,"pop dword eax\n");
			if((yyvsp[0].atributos).es_direccion==1)
				fprintf(nasm,"mov dword eax, [eax]\n");
			fprintf(nasm,";cargar en edx la parte izquierda de la asignacion\n");
			fprintf(nasm,"pop dword edx\n");
			fprintf(nasm,";Hacer la asignacion efectiva\n");
			fprintf(nasm,"mov dword [edx], eax\n");
			fprintf(nasm," \t\t\t;termina asignacion\n");

		}
#line 1872 "y.tab.c" /* yacc.c:1646  */
    break;

  case 42:
#line 418 "gramatica.y" /* yacc.c:1646  */
    {printf(";R48:\t<elemento_vector> ::= <identificador> [ <exp> ]\n");
			if(NULL==(auxdato=hash_find_table(tlocal, tglobal, (yyvsp[-1].atributos).lexema))){
				fprintf(stderr,"****ERROR semantico en lin %d: Acceso a variable no declarada (%s).\n",ln,(yyvsp[-3].atributos).lexema);
				return;
			}
			if(((yyvsp[-1].atributos).tipo==INT)&&(auxdato->clase==VECTOR)&&(auxdato->categoria==FUNCION)){
				(yyval.atributos).tipo=auxdato->tipo;
				(yyval.atributos).es_direccion=1;
			}
			if((auxdato->clase==VECTOR)&&(((yyvsp[-1].atributos).valor_entero>auxdato->tam)||((yyvsp[-1].atributos).valor_entero<0))){
				printf("exp=%d,tam=%d\n",(yyvsp[-1].atributos).valor_entero,auxdato->tam);
				printf("%s\n",auxdato->lexema);
				fprintf(stderr,"****ERROR semantico en lin %d: Se sale del vector.\n",ln);
				return;
			}
			/*generacion de codigo*/
			fprintf(nasm," \t\t\t;empieza elemento_vector\n");
			fprintf(nasm, ";Cargar el valor del indice en eax\n");
			fprintf(nasm, "pop dword eax\n");
			if((yyvsp[-1].atributos).es_direccion==1){
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
			fprintf(nasm, "mov dword edx, _%s\n",(yyvsp[-3].atributos).lexema);
			fprintf(nasm, ";Cargar en eax la direccion del elemento indexado\n");
			fprintf(nasm, "lea eax, [edx +eax*4]\n");
			fprintf(nasm, ";Aplicar la direccion del elemento indexado\n");
			fprintf(nasm, "push dword eax\n");
			fprintf(nasm," \t\t\t;termina elemento_vector\n");
			
			

		}
#line 1919 "y.tab.c" /* yacc.c:1646  */
    break;

  case 43:
#line 461 "gramatica.y" /* yacc.c:1646  */
    {printf(";R50:\t<condicional> ::= if ( <exp> ) { <sentencias> }\n");
			/*generacion de codigo*/
			fprintf(nasm," \t\t\t;empieza condicional\n");
			fprintf(nasm, "fin_si%d: ;es $1.etiqueta\n", (yyvsp[-2].atributos).etiqueta);
			fprintf(nasm," \t\t\t;termina condicional\n");

		}
#line 1931 "y.tab.c" /* yacc.c:1646  */
    break;

  case 44:
#line 470 "gramatica.y" /* yacc.c:1646  */
    {printf(";R51:\t<condicional> ::= if ( <exp> ) { <sentencias> } else { <sentencias> }\n");
			/*generacion de codigo*/
			fprintf(nasm," \t\t\t;empieza condicional\n");
			fprintf(nasm,"fin_sino%d: ;es $1.etiqueta\n", (yyval.atributos).etiqueta);
			fprintf(nasm," \t\t\t;termina condicional\n");

		}
#line 1943 "y.tab.c" /* yacc.c:1646  */
    break;

  case 45:
#line 477 "gramatica.y" /* yacc.c:1646  */
    {
			(yyval.atributos).etiqueta=(yyvsp[-2].atributos).etiqueta;

			/*generacion de codigo*/
			fprintf(nasm," \t\t\t;empieza if_exp_sentencias\n");
			fprintf(nasm,"jmp near fin_sino%d ;es $1.etiqueta\n", (yyval.atributos).etiqueta);
			fprintf(nasm," fin_si%d: ;es $1.etiqueta\n", (yyvsp[-2].atributos).etiqueta);
			fprintf(nasm," \t\t\t;termina if_exp_sentencias\n");
		}
#line 1957 "y.tab.c" /* yacc.c:1646  */
    break;

  case 46:
#line 488 "gramatica.y" /* yacc.c:1646  */
    {
			if((yyvsp[-2].atributos).tipo!=BOOL){
				fprintf(stderr,"****ERROR semantico en lin %d: Condicional con condicion de tipo int.\n",ln);
				return;
			}
			(yyval.atributos).etiqueta=etiqueta++;
			/*generacion de codigo*/
			fprintf(nasm," \t\t\t;empieza if_exp\n");
			fprintf(nasm,"pop eax\n");
			if((yyvsp[-2].atributos).es_direccion==1)
				fprintf(nasm,"mov eax,[eax]\n");
			fprintf(nasm,"cmp eax,0\n");
			fprintf(nasm,"je near fin_si%d\n", (yyval.atributos).etiqueta);
			fprintf(nasm," \t\t\t;termina if_exp\n");

		}
#line 1978 "y.tab.c" /* yacc.c:1646  */
    break;

  case 47:
#line 506 "gramatica.y" /* yacc.c:1646  */
    {printf(";R52:\t<bucle> ::= while ( <exp> ) { <sentencias> }\n");
		/*generacion de codigo*/
			fprintf(nasm," \t\t\t;empieza bucle\n");
			fprintf(nasm,"jmp near inicio_while%d\n",(yyvsp[-2].atributos).etiqueta);
			fprintf(nasm,"fin_while%d:\n",(yyvsp[-2].atributos).etiqueta);
			fprintf(nasm," \t\t\t;termina bucle\n");
		}
#line 1990 "y.tab.c" /* yacc.c:1646  */
    break;

  case 48:
#line 515 "gramatica.y" /* yacc.c:1646  */
    {
			if((yyvsp[-2].atributos).tipo!=BOOL){
				fprintf(stderr,"****ERROR semantico en lin %d: Bucle con condicion de tipo int.\n",ln);
				return;
			}
			(yyval.atributos).etiqueta=(yyvsp[-3].atributos).etiqueta;
			fprintf(nasm," \t\t\t;empieza while_exp\n");
			/*generacion codigo*/
			fprintf(nasm,"pop eax\n");
			if((yyvsp[-2].atributos).es_direccion==1){
				fprintf(nasm,"mov eax,[eax]\n");
			}
			fprintf(nasm,"cmp eax, 0\n");
			fprintf(nasm,"je fin_while%d\n", (yyval.atributos).etiqueta);
			fprintf(nasm," \t\t\t;termina while_exp\n");
			
			
		}
#line 2013 "y.tab.c" /* yacc.c:1646  */
    break;

  case 49:
#line 536 "gramatica.y" /* yacc.c:1646  */
    {
			
			(yyval.atributos).etiqueta=etiqueta++;
			fprintf(nasm," \t\t\t;empieza while\n");
			/*generacion codigo*/
			fprintf(nasm, "inicio_while%d: ; es etiqueta $$.etiqueta\n", (yyval.atributos).etiqueta);
			fprintf(nasm," \t\t\t;termina while\n");
		}
#line 2026 "y.tab.c" /* yacc.c:1646  */
    break;

  case 50:
#line 547 "gramatica.y" /* yacc.c:1646  */
    {printf(";R54:\t<lectura> ::= scanf <identificador>\n");
			printf("42\n");
			if(NULL==(auxdato=hash_find_table(tlocal,tglobal, (yyvsp[0].atributos).lexema))){
				fprintf(stderr,"****ERROR semantico en lin %d: Acceso a variable no declarada (%s).\n",ln,(yyvsp[0].atributos).lexema);
				return;
			}printf("42\n");
			printf("\t\tlexema:%s  tipo:%d\n", auxdato->lexema, auxdato->tipo);
			if(auxdato->tipo!=INT){
				fprintf(stderr,"****ERROR semantico en lin %d: Lectura de tipo boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			if(auxdato->categoria==VARIABLE){
				if(NULL==(auxdato=hash_find_table(NULL,tlocal, (yyvsp[0].atributos).lexema))){
					fprintf(nasm," \t\t\t;empieza scanf\n");
					fprintf(nasm,"push dword _%s\n",(yyvsp[0].atributos).lexema);
					if((yyvsp[0].atributos).tipo==INT)
						fprintf(nasm,"call scan_int ;identificador de tipo entero\n");
					if((yyvsp[0].atributos).tipo==BOOL)
						fprintf(nasm,"call scan_boolean ;identificador de tipo boolean\n");
					fprintf(nasm,"add esp,4\n");
					fprintf(nasm," \t\t\t;termina scanf\n");
				}
				else{
					fprintf(nasm," \t\t\t;empieza scanf\n");
					fprintf(nasm,"lea eax,[ebp-%d]\n",4*auxdato->pvar);
					fprintf(nasm,"push dword eax\n");
					if((yyvsp[0].atributos).tipo==INT)
						fprintf(nasm,"call scan_int ;identificador de tipo entero\n");
					if((yyvsp[0].atributos).tipo==BOOL)
						fprintf(nasm,"call scan_boolean ;identificador de tipo boolean\n");
					fprintf(nasm,"add esp,4\n");
					fprintf(nasm," \t\t\t;termina scanf\n");
				}
			}
			else if(auxdato->categoria==PARAM){
				fprintf(nasm," \t\t\t;empieza scanf\n");
				fprintf(nasm,"lea eax,[ebp+%d]\n",4+4*(auxdato->pparam));
				fprintf(nasm,"push dword eax\n");
				if((yyvsp[0].atributos).tipo==INT)
					fprintf(nasm,"call scan_int ;identificador de tipo entero\n");
				if((yyvsp[0].atributos).tipo==BOOL)
					fprintf(nasm,"call scan_boolean ;identificador de tipo boolean\n");
				fprintf(nasm,"add esp,4\n");
				fprintf(nasm," \t\t\t;termina scanf\n");
			}
		
		}
#line 2079 "y.tab.c" /* yacc.c:1646  */
    break;

  case 51:
#line 596 "gramatica.y" /* yacc.c:1646  */
    {printf(";R56:\t<escritura> ::= printf <exp>\n");
			/*generacion de codigo*/
			fprintf(nasm," \t\t\t;empieza printf\n");
			if((yyvsp[0].atributos).es_direccion==1){
				fprintf(nasm,"pop dword eax\n");
				fprintf(nasm,"mov dword eax,[eax]\n");
				fprintf(nasm,"push dword eax\n");
			}
			if((yyvsp[0].atributos).tipo==INT){
				fprintf(nasm,";Si la expresion es de tipo entero\n");
				fprintf(nasm,"call print_int\n");
			}
			else if((yyvsp[0].atributos).tipo==BOOL){
				fprintf(nasm,";Si la expresion es de tipo boolean\n");
				fprintf(nasm,"call print_boolean\n");
			}
			fprintf(nasm,"add esp, 4\n");
			fprintf(nasm,";Salto de linea\n");
			fprintf(nasm,"call print_endofline\n");
			fprintf(nasm," \t\t\t;termina printf\n");
			
		}
#line 2106 "y.tab.c" /* yacc.c:1646  */
    break;

  case 52:
#line 620 "gramatica.y" /* yacc.c:1646  */
    {printf(";R61:\t<retorno_funcion> ::= return <exp>\n");
			fprintf(nasm,"pop dword eax\n");
			if((yyvsp[0].atributos).es_direccion==1)
				fprintf(nasm, "mov eax, [eax]\n");
			fprintf(nasm,"mov dword esp, ebp\n");
			fprintf(nasm,"pop dword ebp\n");
			fprintf(nasm,"ret\n");
		}
#line 2119 "y.tab.c" /* yacc.c:1646  */
    break;

  case 53:
#line 629 "gramatica.y" /* yacc.c:1646  */
    {printf(";R72:\t<exp> ::= <exp> + <exp>\n");
			if(((yyvsp[-2].atributos).tipo==INT)&&((yyvsp[0].atributos).tipo==INT)){
				(yyval.atributos).tipo=INT;
				(yyval.atributos).es_direccion=0;
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Operacion aritmetica con operandos boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			gc_sumaenteros((yyvsp[-2].atributos).es_direccion,(yyvsp[0].atributos).es_direccion);
		}
#line 2136 "y.tab.c" /* yacc.c:1646  */
    break;

  case 54:
#line 642 "gramatica.y" /* yacc.c:1646  */
    {printf(";R73:\t<exp> ::= <exp> - <exp>\n");
			if(((yyvsp[-2].atributos).tipo==INT)&&((yyvsp[0].atributos).tipo==INT)){
				(yyval.atributos).tipo=INT;
				(yyval.atributos).es_direccion=0;
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Operacion aritmetica con operandos boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			gc_restaenteros((yyvsp[-2].atributos).es_direccion,(yyvsp[0].atributos).es_direccion);
		}
#line 2153 "y.tab.c" /* yacc.c:1646  */
    break;

  case 55:
#line 655 "gramatica.y" /* yacc.c:1646  */
    {printf(";R74:\t<exp> ::= <exp> / <exp>\n");
			if(((yyvsp[-2].atributos).tipo==INT)&&((yyvsp[0].atributos).tipo==INT)){
				(yyval.atributos).tipo=INT;
				(yyval.atributos).es_direccion=0;
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Operacion aritmetica con operandos boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			gc_diventeros((yyvsp[-2].atributos).es_direccion,(yyvsp[0].atributos).es_direccion);
		}
#line 2170 "y.tab.c" /* yacc.c:1646  */
    break;

  case 56:
#line 668 "gramatica.y" /* yacc.c:1646  */
    {printf(";R75:\t<exp> ::= <exp> * <exp>\n");
			if(((yyvsp[-2].atributos).tipo==INT)&&((yyvsp[0].atributos).tipo==INT)){
				(yyval.atributos).tipo=INT;
				(yyval.atributos).es_direccion=0;
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Operacion aritmetica con operandos boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			gc_multenteros((yyvsp[-2].atributos).es_direccion,(yyvsp[0].atributos).es_direccion);
		}
#line 2187 "y.tab.c" /* yacc.c:1646  */
    break;

  case 57:
#line 681 "gramatica.y" /* yacc.c:1646  */
    {printf(";R76:\t<exp> ::= - <exp>\n");
			if((yyvsp[0].atributos).tipo==INT){
				(yyval.atributos).tipo=INT;
				(yyval.atributos).es_direccion=0;
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Operacion aritmetica con operandos boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			gc_negenteros((yyvsp[0].atributos).es_direccion);
		}
#line 2204 "y.tab.c" /* yacc.c:1646  */
    break;

  case 58:
#line 694 "gramatica.y" /* yacc.c:1646  */
    {printf(";R77:\t<exp> ::= <exp> && <exp>\n");
			if(((yyvsp[-2].atributos).tipo==BOOL)&&((yyvsp[0].atributos).tipo==BOOL)){
				(yyval.atributos).tipo=BOOL;
				(yyval.atributos).es_direccion=0;
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Operacion logica con operandos int.\n",ln);
				return;
				}
			/*generacion de codigo*/
			gc_andlogica((yyvsp[-2].atributos).es_direccion,(yyvsp[0].atributos).es_direccion);
		}
#line 2221 "y.tab.c" /* yacc.c:1646  */
    break;

  case 59:
#line 707 "gramatica.y" /* yacc.c:1646  */
    {printf(";R78:\t<exp> ::= <exp> || <exp>\n");
			if(((yyvsp[-2].atributos).tipo==BOOL)&&((yyvsp[0].atributos).tipo==BOOL)){
				(yyval.atributos).tipo=BOOL;
				(yyval.atributos).es_direccion=0;
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Operacion logica con operandos int.\n",ln);
				return;

			}
			/*generacion de codigo*/
			gc_orlogica((yyvsp[-2].atributos).es_direccion,(yyvsp[0].atributos).es_direccion	);
		}
#line 2239 "y.tab.c" /* yacc.c:1646  */
    break;

  case 60:
#line 721 "gramatica.y" /* yacc.c:1646  */
    {printf(";R79:\t<exp> ::= ! <exp>\n");
			if((yyvsp[0].atributos).tipo==BOOL){
				(yyval.atributos).es_direccion=0;
				if((yyvsp[0].atributos).valor_entero==0){
					(yyval.atributos).tipo=BOOL;
					(yyval.atributos).es_direccion=0;
				}
				else{
				fprintf(stderr,"****ERROR semantico en lin %d: Operacion logica con operandos int.\n",ln);
				return;

				}
				/*generacion de codigo*/
				gc_neglogica((yyvsp[0].atributos).es_direccion,etiqueta);
			}
		}
#line 2260 "y.tab.c" /* yacc.c:1646  */
    break;

  case 61:
#line 738 "gramatica.y" /* yacc.c:1646  */
    {printf(";R80:\t<exp> ::= <identificador>\n");
			if(NULL==(auxdato=hash_find_table(tlocal, tglobal, (yyvsp[0].atributos).lexema))){
				fprintf(stderr,"****ERROR semantico en lin %d: Acceso a variable no declarada(%s).\n",ln,(yyvsp[0].atributos).lexema);
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
				(yyval.atributos).tipo=auxdato->tipo;
				(yyval.atributos).es_direccion=1;
				if((auxdato->categoria==VARIABLE)&&(l_exp==0)){
					if(NULL==(auxdato=hash_find_table(NULL, tlocal, (yyvsp[0].atributos).lexema))){
						fprintf(nasm, "mov eax, _%s\n",(yyvsp[0].atributos).lexema);
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
#line 2299 "y.tab.c" /* yacc.c:1646  */
    break;

  case 62:
#line 773 "gramatica.y" /* yacc.c:1646  */
    {printf(";R81:\t<exp> ::= <constante>\n");
			(yyval.atributos).tipo=(yyvsp[0].atributos).tipo;
			(yyval.atributos).es_direccion=(yyvsp[0].atributos).es_direccion;
		}
#line 2308 "y.tab.c" /* yacc.c:1646  */
    break;

  case 63:
#line 778 "gramatica.y" /* yacc.c:1646  */
    {printf(";R82:\t<exp> ::= ( <exp> )\n");
			(yyval.atributos).tipo=(yyvsp[-1].atributos).tipo;
			(yyval.atributos).es_direccion=(yyvsp[-1].atributos).es_direccion;
		}
#line 2317 "y.tab.c" /* yacc.c:1646  */
    break;

  case 64:
#line 783 "gramatica.y" /* yacc.c:1646  */
    {printf(";R83:\t<exp> ::= ( <comparacion> )\n");
			(yyval.atributos).tipo=(yyvsp[-1].atributos).tipo;
			(yyval.atributos).es_direccion=(yyvsp[-1].atributos).es_direccion;
		}
#line 2326 "y.tab.c" /* yacc.c:1646  */
    break;

  case 65:
#line 788 "gramatica.y" /* yacc.c:1646  */
    {printf(";R85:\t<exp> ::= <elemento_vector>\n");
			(yyval.atributos).tipo=(yyvsp[0].atributos).tipo;
			(yyval.atributos).es_direccion=(yyvsp[0].atributos).es_direccion;
		}
#line 2335 "y.tab.c" /* yacc.c:1646  */
    break;

  case 66:
#line 793 "gramatica.y" /* yacc.c:1646  */
    {printf(";R88:\t<exp> ::= <identificador> ( <lista_expresiones> )\n");
			if(NULL==(auxdato=(hash_find_table(tlocal,tglobal, (yyvsp[-3].atributos).lexema)))){
				fprintf(stderr,"****ERROR semantico en lin %d: Acceso a variable no declarada(%s).\n",ln,(yyvsp[-3].atributos).lexema);
				return;

			}
			
			if((auxdato->nparam==num_parametros_llamada_actual)&&(en_explist==1)){
				(yyval.atributos).tipo=auxdato->tipo;
				(yyval.atributos).es_direccion=0;
			}
			en_explist=0;
			fprintf(nasm, "call _%s\n", (yyvsp[-3].atributos).lexema);
			
			fprintf(nasm, "add esp,%d\n",4*num_parametros_llamada_actual);
			fprintf(nasm, "push dword eax\n");

		}
#line 2358 "y.tab.c" /* yacc.c:1646  */
    break;

  case 67:
#line 811 "gramatica.y" /* yacc.c:1646  */
    {
			if(NULL==(auxdato=(hash_find_table(tlocal,tglobal, (yyvsp[0].atributos).lexema)))){
				fprintf(stderr,"****ERROR semantico en lin %d: Acceso a variable no declarada(%s).\n",ln,(yyvsp[0].atributos).lexema);
				return;
			}
			if(auxdato->categoria!=FUNCION){
				fprintf(stderr,"****ERROR semantico en lin %d: Se esperaba una funcion.\n",ln);
				return;
			}
			if(en_explist==0){
				en_explist=1;
				num_parametros_llamada_actual =0;
				(yyval.atributos).lexema=(yyvsp[0].atributos).lexema;
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Llamada a funcion invalida.\n",ln);
				return;
			}


		}
#line 2384 "y.tab.c" /* yacc.c:1646  */
    break;

  case 68:
#line 833 "gramatica.y" /* yacc.c:1646  */
    {printf(";R89:\t<lista_expresiones> ::= <exp> <resto_lista_expresiones>\n");
			num_parametros_llamada_actual++;
			if(tlocal==NULL){
				fprintf(nasm, "push dword [_%s]\n", (yyvsp[-1].atributos).lexema);
			}
			else{
				if(NULL==(auxdato=hash_find_table(NULL, tlocal, (yyvsp[-1].atributos).lexema))){
					fprintf(stderr,"****ERROR semantico en lin %d: Acceso a variable no declarada (%s).\n",ln,(yyvsp[-1].atributos).lexema);
					return;
				}

				fprintf(nasm, "lea eax, [ebp+%d]\n",4+4*( auxdato->pparam));
				fprintf(nasm, "push eax\n");
			}
			l_exp=0;
		}
#line 2405 "y.tab.c" /* yacc.c:1646  */
    break;

  case 70:
#line 850 "gramatica.y" /* yacc.c:1646  */
    {
			l_exp=1;
		}
#line 2413 "y.tab.c" /* yacc.c:1646  */
    break;

  case 71:
#line 854 "gramatica.y" /* yacc.c:1646  */
    {printf(";R91:\t<resto_lista_expresiones> ::= , <exp> <resto_lista_expresiones>\n");
			num_parametros_llamada_actual++;
			if(tlocal==NULL){
				fprintf(nasm, "push dword [_%s]\n", (yyvsp[-1].atributos).lexema);
			}
			else{
				if(NULL==(auxdato=hash_find_table(NULL, tlocal, (yyvsp[-1].atributos).lexema))){
					fprintf(stderr,"****ERROR semantico en lin %d: Acceso a variable no declarada (%s).\n",ln,(yyvsp[-1].atributos).lexema);
					return;
				}

				fprintf(nasm, "lea eax, [ebp+%d]\n",4+4*( auxdato->pparam));
				fprintf(nasm, "push eax\n");
			}
		}
#line 2433 "y.tab.c" /* yacc.c:1646  */
    break;

  case 72:
#line 870 "gramatica.y" /* yacc.c:1646  */
    {printf(";R92:\t<resto_lista_expresiones> ::=\n");}
#line 2439 "y.tab.c" /* yacc.c:1646  */
    break;

  case 73:
#line 872 "gramatica.y" /* yacc.c:1646  */
    {printf(";R93:\t<comparacion> ::= <exp> == <exp>\n");
			if(((yyvsp[-2].atributos).tipo=INT)&&((yyvsp[0].atributos).tipo=INT)){
				(yyval.atributos).tipo=BOOL;
				(yyval.atributos).es_direccion=0;
				
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Comparacion con operandos boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			gc_igual((yyvsp[-2].atributos).es_direccion,(yyvsp[0].atributos).es_direccion,etiqueta);
		}
#line 2457 "y.tab.c" /* yacc.c:1646  */
    break;

  case 74:
#line 886 "gramatica.y" /* yacc.c:1646  */
    {printf(";R94:\t<comparacion> ::= <exp> != <exp>\n");
			if(((yyvsp[-2].atributos).tipo=INT)&&((yyvsp[0].atributos).tipo=INT)){
				(yyval.atributos).tipo=BOOL;
				(yyval.atributos).es_direccion=0;
				
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Comparacion con operandos boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			gc_distinto((yyvsp[-2].atributos).es_direccion,(yyvsp[0].atributos).es_direccion,etiqueta);

		}
#line 2476 "y.tab.c" /* yacc.c:1646  */
    break;

  case 75:
#line 901 "gramatica.y" /* yacc.c:1646  */
    {printf(";R95:\t<comparacion> ::= <exp> <= <exp>\n");
			if(((yyvsp[-2].atributos).tipo=INT)&&((yyvsp[0].atributos).tipo=INT)){
				(yyval.atributos).tipo=BOOL;
				(yyval.atributos).es_direccion=0;
				
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Comparacion con operandos boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			gc_menorigual((yyvsp[-2].atributos).es_direccion,(yyvsp[0].atributos).es_direccion,etiqueta);
		}
#line 2494 "y.tab.c" /* yacc.c:1646  */
    break;

  case 76:
#line 915 "gramatica.y" /* yacc.c:1646  */
    {printf(";R96:\t<comparacion> ::= <exp> >= <exp>\n");
			if(((yyvsp[-2].atributos).tipo=INT)&&((yyvsp[0].atributos).tipo=INT)){
				(yyval.atributos).tipo=BOOL;
				(yyval.atributos).es_direccion=0;
				
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Comparacion con operandos boolean.\n",ln);
				return;;
			}
			/*generacion de codigo*/
			gc_mayorigual((yyvsp[-2].atributos).es_direccion,(yyvsp[0].atributos).es_direccion,etiqueta);
		}
#line 2512 "y.tab.c" /* yacc.c:1646  */
    break;

  case 77:
#line 929 "gramatica.y" /* yacc.c:1646  */
    {printf(";R97:\t<comparacion> ::= <exp> < <exp>\n");
			if(((yyvsp[-2].atributos).tipo=INT)&&((yyvsp[0].atributos).tipo=INT)){
				(yyval.atributos).tipo=BOOL;
				(yyval.atributos).es_direccion=0;
				
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Comparacion con operandos boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			gc_menor((yyvsp[-2].atributos).es_direccion,(yyvsp[0].atributos).es_direccion,etiqueta);
		}
#line 2530 "y.tab.c" /* yacc.c:1646  */
    break;

  case 78:
#line 943 "gramatica.y" /* yacc.c:1646  */
    {printf(";R98:\t<comparacion> ::= <exp> > <exp>\n");
			if(((yyvsp[-2].atributos).tipo=INT)&&((yyvsp[0].atributos).tipo=INT)){
				(yyval.atributos).tipo=BOOL;
				(yyval.atributos).es_direccion=0;
				
			}
			else{
				fprintf(stderr,"****ERROR semantico en lin %d: Comparacion con operandos boolean.\n",ln);
				return;
			}
			/*generacion de codigo*/
			gc_mayor((yyvsp[-2].atributos).es_direccion,(yyvsp[0].atributos).es_direccion,etiqueta);
		}
#line 2548 "y.tab.c" /* yacc.c:1646  */
    break;

  case 79:
#line 957 "gramatica.y" /* yacc.c:1646  */
    {printf(";R99:\t<constante> ::= <constante_logica>\n");
			(yyval.atributos).tipo=(yyvsp[0].atributos).tipo;
			(yyval.atributos).es_direccion=(yyvsp[0].atributos).es_direccion;
		}
#line 2557 "y.tab.c" /* yacc.c:1646  */
    break;

  case 80:
#line 962 "gramatica.y" /* yacc.c:1646  */
    {printf(";R100:\t<constante> ::= <constante_entera>\n");
			(yyval.atributos).tipo=(yyvsp[0].atributos).tipo;
			(yyval.atributos).es_direccion=(yyvsp[0].atributos).es_direccion;
		}
#line 2566 "y.tab.c" /* yacc.c:1646  */
    break;

  case 81:
#line 967 "gramatica.y" /* yacc.c:1646  */
    {printf(";R102:\t<constante_logica> ::= true\n");
			(yyval.atributos).tipo=BOOL;
			(yyval.atributos).es_direccion=0;

			/*generacion de codigo*/

			fprintf(nasm," \t\t\t;empieza constante logica\n");
			fprintf(nasm,";numero_linea %d \n",ln);
			fprintf(nasm,"\tpush dword 1 \n");
			fprintf(nasm," \t\t\t;termina constante logica\n");
		}
#line 2582 "y.tab.c" /* yacc.c:1646  */
    break;

  case 82:
#line 979 "gramatica.y" /* yacc.c:1646  */
    {printf(";R103:\t<constante_logica> ::= false\n");
			(yyval.atributos).tipo=BOOL;
			(yyval.atributos).es_direccion=0;

			/*generacion de codigo*/
			fprintf(nasm," \t\t\t;empieza constante logica\n");
			fprintf(nasm,";numero_linea %d \n",ln);
			fprintf(nasm,"\tpush dword 0 \n");
			fprintf(nasm," \t\t\t;termina constante logica\n");
		}
#line 2597 "y.tab.c" /* yacc.c:1646  */
    break;

  case 83:
#line 990 "gramatica.y" /* yacc.c:1646  */
    {printf(";R104:\t<constante_entera> ::= TOK_CONSTANTE_ENTERA\n");
			(yyval.atributos).tipo=INT;
			(yyval.atributos).es_direccion=0;

			/*generacion de codigo*/
			(yyval.atributos).valor_entero=(yyvsp[0].atributos).valor_entero;
			fprintf(nasm," \t\t\t;empieza constante entera\n");
			if(l_exp==0){
				fprintf(nasm,";numero_linea %d \n",ln);
				fprintf(nasm,"\tpush dword %d\n", (yyvsp[0].atributos).valor_entero);
				fprintf(nasm," \t\t\t;termina constante entera\n");
			}
		}
#line 2615 "y.tab.c" /* yacc.c:1646  */
    break;

  case 84:
#line 1003 "gramatica.y" /* yacc.c:1646  */
    {
			printf(";R108_1:\t<identificador> ::= TOK_IDENTIFICADOR == %s\n", (yyvsp[0].atributos).lexema);
				(yyval.atributos).es_direccion=1;
				(yyval.atributos).lexema=(yyvsp[0].atributos).lexema;
			}
#line 2625 "y.tab.c" /* yacc.c:1646  */
    break;

  case 85:
#line 1010 "gramatica.y" /* yacc.c:1646  */
    {printf(";R108_2:\t<decidentificador> ::= TOK_IDENTIFICADOR == %s\n", (yyvsp[0].atributos).lexema);
			if (NULL!=hash_find_table(tlocal,tglobal,(yyvsp[0].atributos).lexema)){
				printf ("%s: redefinido\n", (yyvsp[0].atributos).lexema);
				return;
			}
			auxdato=(nodo*)malloc(sizeof(nodo));
			auxdato->lexema=(yyvsp[0].atributos).lexema;
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
    		(yyval.atributos).lexema=(yyvsp[0].atributos).lexema;		
			pos_variable_local_actual++;
			num_variables_local_actual++;
			if(NULL==hash_insert_table(tlocal,tglobal, (yyvsp[0].atributos).lexema,auxdato)){
				fprintf(stderr,"****ERROR semantico en lin %d: Declaracion duplicada.\n",ln);
				return;
			}
			decvar[nvar]=(char*)malloc(sizeof(char)*strlen((yyvsp[0].atributos).lexema));
			strcpy(decvar[nvar],(yyvsp[0].atributos).lexema);
			nvar++;
			printf("\t\tse crea: %s\n",(yyvsp[0].atributos).lexema);
			printf("\t\tlexema:%s tipo:%d\n",auxdato->lexema, auxdato->tipo);
			//printf("lexema:%s ,categoria:%d ,tipo:%d ,clase:%d ,tam:%d ,nparam:%d ,pparm:%d ,nvar:%d ,pvar:%d \n",dato.lexema,dato.categoria,dato.tipo,dato.clase,dato.tam,dato.nparam,dato.pparam,dato.nvar,dato.pvar);
		}
#line 2665 "y.tab.c" /* yacc.c:1646  */
    break;

  case 86:
#line 1046 "gramatica.y" /* yacc.c:1646  */
    {printf(";R108:\t<ipdf> ::= TOK_IDENTIFICADOR == %s\n", (yyvsp[0].atributos).lexema);
			if (NULL!=hash_find_table(tlocal,tglobal,(yyvsp[0].atributos).lexema)){
				printf ("%s: redefinido\n", (yyvsp[0].atributos).lexema);
				return;
			}
			auxdato=(nodo*)malloc(sizeof(nodo));
			auxdato->lexema=(yyvsp[0].atributos).lexema;
			auxdato->categoria=PARAM;
			auxdato->tipo=tipo_actual;
			auxdato->clase = clase_actual;
			if(clase_actual==VECTOR){
				fprintf(stderr,"****ERROR semantico en lin %d: no se pudo insertar %s.\n",ln,(yyvsp[0].atributos).lexema);
				return;
			}
			else{
				auxdato->tam=0;
			}
			auxdato->nparam=0;		
    		auxdato->pparam=pos_variable_local_actual++;		
    		auxdato->nvar=num_variables_local_actual;		
    		auxdato->pvar=pos_variable_local_actual;
			if(NULL==hash_insert_table(tlocal,tglobal, (yyvsp[0].atributos).lexema,auxdato)){
				fprintf(stderr,"****ERROR semantico en lin %d: no se pudo insertar %s.\n",ln,(yyvsp[0].atributos).lexema);
				return;
			}
			pos_parametro_actual++;
			num_parametros_actual++;
			fprintf(nasm, ";num_variables_local_actual=%d,pos_variable_local_actual=%d \n",num_variables_local_actual,pos_variable_local_actual);
		}
#line 2699 "y.tab.c" /* yacc.c:1646  */
    break;


#line 2703 "y.tab.c" /* yacc.c:1646  */
      default: break;
    }
  /* User semantic actions sometimes alter yychar, and that requires
     that yytoken be updated with the new translation.  We take the
     approach of translating immediately before every use of yytoken.
     One alternative is translating here after every semantic action,
     but that translation would be missed if the semantic action invokes
     YYABORT, YYACCEPT, or YYERROR immediately after altering yychar or
     if it invokes YYBACKUP.  In the case of YYABORT or YYACCEPT, an
     incorrect destructor might then be invoked immediately.  In the
     case of YYERROR or YYBACKUP, subsequent parser actions might lead
     to an incorrect destructor call or verbose syntax error message
     before the lookahead is translated.  */
  YY_SYMBOL_PRINT ("-> $$ =", yyr1[yyn], &yyval, &yyloc);

  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);

  *++yyvsp = yyval;

  /* Now 'shift' the result of the reduction.  Determine what state
     that goes to, based on the state we popped back to and the rule
     number reduced by.  */

  yyn = yyr1[yyn];

  yystate = yypgoto[yyn - YYNTOKENS] + *yyssp;
  if (0 <= yystate && yystate <= YYLAST && yycheck[yystate] == *yyssp)
    yystate = yytable[yystate];
  else
    yystate = yydefgoto[yyn - YYNTOKENS];

  goto yynewstate;


/*--------------------------------------.
| yyerrlab -- here on detecting error.  |
`--------------------------------------*/
yyerrlab:
  /* Make sure we have latest lookahead translation.  See comments at
     user semantic actions for why this is necessary.  */
  yytoken = yychar == YYEMPTY ? YYEMPTY : YYTRANSLATE (yychar);

  /* If not already recovering from an error, report this error.  */
  if (!yyerrstatus)
    {
      ++yynerrs;
#if ! YYERROR_VERBOSE
      yyerror (YY_("syntax error"));
#else
# define YYSYNTAX_ERROR yysyntax_error (&yymsg_alloc, &yymsg, \
                                        yyssp, yytoken)
      {
        char const *yymsgp = YY_("syntax error");
        int yysyntax_error_status;
        yysyntax_error_status = YYSYNTAX_ERROR;
        if (yysyntax_error_status == 0)
          yymsgp = yymsg;
        else if (yysyntax_error_status == 1)
          {
            if (yymsg != yymsgbuf)
              YYSTACK_FREE (yymsg);
            yymsg = (char *) YYSTACK_ALLOC (yymsg_alloc);
            if (!yymsg)
              {
                yymsg = yymsgbuf;
                yymsg_alloc = sizeof yymsgbuf;
                yysyntax_error_status = 2;
              }
            else
              {
                yysyntax_error_status = YYSYNTAX_ERROR;
                yymsgp = yymsg;
              }
          }
        yyerror (yymsgp);
        if (yysyntax_error_status == 2)
          goto yyexhaustedlab;
      }
# undef YYSYNTAX_ERROR
#endif
    }



  if (yyerrstatus == 3)
    {
      /* If just tried and failed to reuse lookahead token after an
         error, discard it.  */

      if (yychar <= YYEOF)
        {
          /* Return failure if at end of input.  */
          if (yychar == YYEOF)
            YYABORT;
        }
      else
        {
          yydestruct ("Error: discarding",
                      yytoken, &yylval);
          yychar = YYEMPTY;
        }
    }

  /* Else will try to reuse lookahead token after shifting the error
     token.  */
  goto yyerrlab1;


/*---------------------------------------------------.
| yyerrorlab -- error raised explicitly by YYERROR.  |
`---------------------------------------------------*/
yyerrorlab:

  /* Pacify compilers like GCC when the user code never invokes
     YYERROR and the label yyerrorlab therefore never appears in user
     code.  */
  if (/*CONSTCOND*/ 0)
     goto yyerrorlab;

  /* Do not reclaim the symbols of the rule whose action triggered
     this YYERROR.  */
  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);
  yystate = *yyssp;
  goto yyerrlab1;


/*-------------------------------------------------------------.
| yyerrlab1 -- common code for both syntax error and YYERROR.  |
`-------------------------------------------------------------*/
yyerrlab1:
  yyerrstatus = 3;      /* Each real token shifted decrements this.  */

  for (;;)
    {
      yyn = yypact[yystate];
      if (!yypact_value_is_default (yyn))
        {
          yyn += YYTERROR;
          if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYTERROR)
            {
              yyn = yytable[yyn];
              if (0 < yyn)
                break;
            }
        }

      /* Pop the current state because it cannot handle the error token.  */
      if (yyssp == yyss)
        YYABORT;


      yydestruct ("Error: popping",
                  yystos[yystate], yyvsp);
      YYPOPSTACK (1);
      yystate = *yyssp;
      YY_STACK_PRINT (yyss, yyssp);
    }

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END


  /* Shift the error token.  */
  YY_SYMBOL_PRINT ("Shifting", yystos[yyn], yyvsp, yylsp);

  yystate = yyn;
  goto yynewstate;


/*-------------------------------------.
| yyacceptlab -- YYACCEPT comes here.  |
`-------------------------------------*/
yyacceptlab:
  yyresult = 0;
  goto yyreturn;

/*-----------------------------------.
| yyabortlab -- YYABORT comes here.  |
`-----------------------------------*/
yyabortlab:
  yyresult = 1;
  goto yyreturn;

#if !defined yyoverflow || YYERROR_VERBOSE
/*-------------------------------------------------.
| yyexhaustedlab -- memory exhaustion comes here.  |
`-------------------------------------------------*/
yyexhaustedlab:
  yyerror (YY_("memory exhausted"));
  yyresult = 2;
  /* Fall through.  */
#endif

yyreturn:
  if (yychar != YYEMPTY)
    {
      /* Make sure we have latest lookahead translation.  See comments at
         user semantic actions for why this is necessary.  */
      yytoken = YYTRANSLATE (yychar);
      yydestruct ("Cleanup: discarding lookahead",
                  yytoken, &yylval);
    }
  /* Do not reclaim the symbols of the rule whose action triggered
     this YYABORT or YYACCEPT.  */
  YYPOPSTACK (yylen);
  YY_STACK_PRINT (yyss, yyssp);
  while (yyssp != yyss)
    {
      yydestruct ("Cleanup: popping",
                  yystos[*yyssp], yyvsp);
      YYPOPSTACK (1);
    }
#ifndef yyoverflow
  if (yyss != yyssa)
    YYSTACK_FREE (yyss);
#endif
#if YYERROR_VERBOSE
  if (yymsg != yymsgbuf)
    YYSTACK_FREE (yymsg);
#endif
  return yyresult;
}
#line 1076 "gramatica.y" /* yacc.c:1906  */


void yyerror(char *s){
	if(err==0)
		printf("\n **** Error sintáctico en [lin %d, col %d] \n",ln,col);
	err=0;
}
