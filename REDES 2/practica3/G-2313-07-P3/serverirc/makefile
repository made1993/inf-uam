slib= srclib/
sr=src/
ob= obj/
pref=G-2313-07-P3
lib= `pkg-config --cflags gtk+-2.0` `pkg-config --libs gtk+-2.0` -lircredes -lssl -lcrypto
usrlib=  -Llib -l$(pref)lista -Llib -l$(pref)semaforos
FALGS= -Wall -ansi -pedantic
all: libreria
	echo  "\033[32;01mserver irc\033[00m"
	@gcc $(FLAGS) -o server src/$(pref)serverIRC.c $(ob)*.o $(lib) $(usrlib)

objetos:
	echo  "\033[32;01mserver irc objetos\033[00m"
	@gcc $(FLAGS) -c -o $(ob)$(pref)demoniochat.o $(sr)$(pref)demoniochat.c $(lib)
	@gcc $(FLAGS) -c -o $(ob)$(pref)linkedList.o $(slib)$(pref)linkedList.c
	@gcc $(FLAGS) -c -o $(ob)$(pref)sslconexion.o $(sr)$(pref)sslconexion.c -lssl -lcrypto
	@gcc $(FLAGS) -c -o $(ob)$(pref)semaforos.o $(slib)$(pref)semaforos.c
	@gcc $(FLAGS) -c -o $(ob)$(pref)client.o $(sr)$(pref)client.c $(lib) 
	@gcc $(FLAGS) -c -o $(ob)$(pref)channeluser.o $(sr)$(pref)channeluser.c $(ob)$(pref)client.o $(lib) 
	@gcc $(FLAGS) -c -o $(ob)$(pref)channel.o $(sr)$(pref)channel.c $(ob)$(pref)channeluser.o $(lib) 
	@gcc $(FLAGS) -c -o $(ob)$(pref)parser.o $(sr)$(pref)parser.c $(ob)$(pref)channel.o $(lib) $(usrlib)

libreria: objetos
	echo  "\033[32;01mserver irc libreria\033[00m"
	@ar r lib/lib$(pref)lista.a $(ob)$(pref)linkedList.o
	@ar r lib/lib$(pref)semaforos.a $(ob)$(pref)semaforos.o
	@rm  $(ob)$(pref)linkedList.o $(ob)$(pref)semaforos.o
comprimir:
	tar -cvf ../$(pref).tar ../$(pref)
	

mrProper:
	rm -f obj/*.o
	rm -f lib/*.a
	rm -f server
