%{
#include <stdio.h> 
%}

%option noyywrap

%%
[A-Z] 		{return TOK_MAYUSCULA;}
[a-z] 		{return TOK_LETRA;}
[á-úÁ-Ú] 	{return TOK_ACENTO;}
.|[\n\r] 			{return TOK_CARACTER;}

%%
