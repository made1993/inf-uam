all: lex.yy.o
	gcc -o  pruebaMorfo main.c lex.yy.o
lex.yy.o: lex.yy.c
	gcc -c lex.yy.c
lex.yy.c:
	flex morf.l
clean:
	rm -f *.o pruebaMorfo lex.yy.c