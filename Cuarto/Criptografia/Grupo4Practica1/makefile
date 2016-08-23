FLTRM= src/filtro.c
FLTRX= src/filtro.l
FLTRL= includes/filtro.h
LLIB= -Llib -lhastable
LINCL= -include includes/jwHash.h
DEFS = -DHASHTEST -DHASHTHREADED
GMP = -lgmp -L.
all: filtro vigenere criptoanalisis transposicion permutacion afin afinmejorado
	@echo compilando

afin: src/afin.c
	gcc -o afin src/afin.c $(GMP)

afinmejorado: src/afinmejorado.c
	gcc -o afinmejorado src/afinmejorado.c $(GMP)

filtro:
	flex -o obj/lex.yy.c $(FLTRX)
	gcc -c obj/lex.yy.c -o obj/lex.yy.o -include $(FLTRL)
	gcc -o filtro $(FLTRM) obj/lex.yy.o -include $(FLTRL)
	@rm obj/*.c
	@echo filtro compilado

vigenere: src/vigenere.c
	gcc -o vigenere src/vigenere.c

criptoanalisis: libreria
	gcc -o criptoanalisis src/criptoanalisis.c $(LINCL) $(LLIB) -lpthread $(DEFS)

libreria:
	gcc -c -o srclib/jwHash.o srclib/jwHash.c $(LINCL) -lpthread
	ar r lib/libhastable.a srclib/jwHash.o
	@rm -f srclib/*.o -v
	@echo "se creo la libreria"

transposicion: src/transposicion.c
	gcc -o transposicion src/transposicion.c

permutacion: src/permutacion.c
	gcc -o permutacion src/permutacion.c


#Ejemplos de uso
ficherotest:
	man gcc > gcc.txt
testafin:
	./afin -C -m 26 -a 17 -b 24 -i gcc.txt -o afinc.txt
	./afin -D -m 26 -a 17 -b 24 -i afinc.txt -o afind.txt
testafinmejoradoc:
	./afinmejorado -C -s 1234567 -i gcc.txt -o afinmejoradoc.txt
	./afinmejorado -D -s 1234567 -i afinmejoradoc.txt -o afinmejoradod.txt

testtransposicion:
	./transposicion -C -p "2 4 0 1 3" -i gcc.txt -o transposicionc.txt
	./transposicion -D -p "2 4 0 1 3" -i transposicionc.txt -o transposiciond.txt

testpermutacion:
	./permutacion -C -k1 "2 4 0 1 3" -k2 "1 2 0" -i gcc.txt -o permutacionc.txt
	./permutacion -D -k1 "2 4 0 1 3" -k2 "1 2 0" -i permutacionc.txt -o permutaciond.txt

testvigenere:
	./vigenere -C -k lalilulelo -i gcc.txt -o vigenerec.txt
	./vigenere -D -k lalilulelo -i vigenerec.txt -o vigenered.txt
	diff -s vigenered.txt Fgcc.txt

testcriptoanalisis:
	./vigenere -C -k lalilulelo -i gcc.txt -o vigenerec.txt
	./criptoanalisis -l 10 -i vigenerec.txt

cleanlib:
	@rm -f lib/* -v
clean:
	@rm -f obj/* -v	
	@rm -f filtro transposicion vigenere criptoanalisis afin permutacion afinmejorado -v