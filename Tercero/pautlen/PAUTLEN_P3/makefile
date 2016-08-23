all: y.tab.c y.tab.o lex.yy.o
	gcc -o  pruebaSintactico main.c y.tab.o lex.yy.o
lex.yy.o: lex.yy.c
	gcc -c lex.yy.c
lex.yy.c:
	flex morf.l
y.tab.o: y.tab.c
	gcc -c y.tab.c
y.tab.c:
	yacc -y -d gramatica.y
clean:
	rm -f *.o gramatica lex.yy.c y.*
