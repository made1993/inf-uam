all:: chat

chat.o : chat.c
	@echo "\033[32;01mCompilando chat.c\033[00m"
	@gcc -Wall chat.c -c `pkg-config --cflags gtk+-2.0`
ficheros.o: ficheros.c
	@echo "\033[32;01mCompilando funciones.c\033[00m"
	@gcc -Wall  ficheros.c -c

funciones.o : funciones.c
	@echo "\033[32;01mCompilando funciones.c\033[00m"
	@gcc -Wall  funciones.c -c

chat_funcs.o : chat_funcs.c
	@echo "\033[32;01mCompilando chat_funcs.c\033[00m"
	@gcc -Wall -L/usr/lib -lircredes -lpthread chat_funcs.c -c  `pkg-config --cflags gtk+-2.0` `pkg-config --libs gtk+-2.0`


chat : chat.o chat_funcs.o funciones.o ficheros.o
	@echo "\033[32;01mEnlazando\033[00m"
	@gcc  -o chat  chat.o chat_funcs.o funciones.o ficheros.o `pkg-config --libs gtk+-2.0` `pkg-config --cflags gtk+-2.0` -L/usr/lib -lircredes -lpthread

clean:
	rm -f chat *.o