#include <stdio.h>
#include <stdlib.h>
#include <gmp.h>
/*#include "gmp.h"*/



/* PROGRAMA PRINCIPAL */
int main (int argc,char *argv[]) {
  mpz_t a,b,m;
  int modo;

  FILE *entrada,*salida;

  mpz_init (a);
  mpz_init (b);
  mpz_init (m);

  mpz_set_str (a,"123452345234523452352352345112341234213",10);
  mpz_set_str (b,"234562344341234123421341234441234213421",10);

  mpz_add    (m,a,b);

  gmp_printf ("El resultado de la suma es %Zd\n", m);

  mpz_clear (a);
  mpz_clear (b);
  mpz_clear (m);

  return(0);
}