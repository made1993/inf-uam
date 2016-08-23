CC = gcc -ansi -pedantic
CFLAGS = -Wall
EXE = prueba_tabla

all : $(EXE)

clean :
	rm -f *.o core $(EXE)

$(EXE) : % : %.o tabsim.o hash.o
