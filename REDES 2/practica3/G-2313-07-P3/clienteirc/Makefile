ob= obj/
pref=G-2313-07-P3
lib= `pkg-config --libs gtk+-2.0` `pkg-config --cflags gtk+-2.0` -lircredes -lssl -lcrypto
usrlib= -Llib -l$(pref)conexion
all : libreria
	@echo "\033[32;01mEnlazando\033[00m"
	@gcc  -o client  src/$(pref)chat.c obj/*.o $(usrlib) $(lib)
chat2:
	gcc  -o $(pref)chat  src/$(pref)chat.c obj/*.o   -Llib -l$(pref)conexion `pkg-config --libs gtk+-2.0` `pkg-config --cflags gtk+-2.0` -lssl -lcrypto  -lpthread   -lircredes


libreria: $(ob)$(pref)ficheros.o $(ob)$(pref)sslconexion.o $(ob)$(pref)chat_funcs.o
	@echo "\033[32;01mCreando librerias\033[00m"
	ar r lib/lib$(pref)conexion.a $(ob)$(pref)sslconexion.o 
	rm $(ob)$(pref)sslconexion.o 
$(ob)$(pref)ficheros.o: srclib/$(pref)ficheros.c
	@echo "\033[32;01mCompilando ficheros.c\033[00m"
	@gcc -Wall  srclib/$(pref)ficheros.c -c -o $(ob)$(pref)ficheros.o -lssl -lcrypto

$(ob)$(pref)sslconexion.o : srclib/$(pref)sslconexion.c
	@echo "\033[32;01mCompilando conexion.c\033[00m"
	@gcc -Wall  srclib/$(pref)sslconexion.c -c -o $(ob)$(pref)sslconexion.o -lssl -lcrypto

$(ob)$(pref)chat_funcs.o : srclib/$(pref)chat_funcs.c  
	@echo "\033[32;01mCompilando chat_funcs.c\033[00m"
	@gcc -Wall -lircredes -lpthread srclib/$(pref)chat_funcs.c -c -o $(ob)$(pref)chat_funcs.o `pkg-config --cflags gtk+-2.0` `pkg-config --libs gtk+-2.0` -lssl -lcrypto

comprimir:
	tar -cvf $(pref).tar ../$(pref)
	
mrProper:
	rm -f $(ob)*.o lib/*.a client
