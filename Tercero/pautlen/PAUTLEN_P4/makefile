all: y.tab.c y.tab.o lex.yy.o hash.o tabsim.o conversor.o
	gcc -o  alfa alfa.c y.tab.o lex.yy.o hash.o tabsim.o conversor.o
conversor.o:
	gcc -c conversor.c
tabsim.o:
	gcc -c tabsim.c hash.o
hash.o:
	gcc -c hash.c
lex.yy.o: lex.yy.c
	gcc -c lex.yy.c
lex.yy.c:
	flex morf.l
y.tab.o: y.tab.c
	gcc -c y.tab.c
y.tab.c:
	bison -v -y -d gramatica.y
clean:
	rm -f alfa code nasm.o conversor.o lex.yy.o y.tab.o gramatica lex.yy.* y.* hash.o tabsim.o 
nasm: nasm.o
	gcc -m32 -o  code nasm.o alfalib.o
nasm.o:
	nasm -g -o nasm.o -f elf32  nasm.asm

