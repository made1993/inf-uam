
FLAGS= -Wall -pedantic
CERT= cert/
PASS= hola
GMP = -lgmp -lm
all: objetos
	gcc $(FLAGS) -o  main src/main.c obj/*.o -lssl -lcrypto

certificado:
	openssl genrsa -out $(CERT)rootkey.pem 2048
	openssl req -new -x509 -key $(CERT)rootkey.pem -out $(CERT)rootcert.pem -subj "/C=ES/ST=Madrid/L=Madrid/O=UAM/CN=CERTIFICATE"
	cat $(CERT)rootkey.pem $(CERT)rootcert.pem > $(CERT)root.pem

claveCliente:
	openssl req -nodes -newkey rsa:2048 -keyout $(CERT)clientkey.pem -out $(CERT)clientreq.pem -subj "/C=ES/ST=Madrid/L=Madrid/O=UAM/CN=CLIENT"
	openssl x509 -req -in $(CERT)clientreq.pem  -CA $(CERT)root.pem -CAkey $(CERT)root.pem -CAcreateserial -out $(CERT)clientcert.pem 
	cat $(CERT)clientcert.pem $(CERT)clientkey.pem $(CERT)rootcert.pem > $(CERT)client.pem

claveServidor:
	openssl req -nodes -newkey rsa:2048 -keyout $(CERT)serverkey.pem -out $(CERT)serverreq.pem -subj "/C=ES/ST=Madrid/L=Madrid/O=UAM/CN=SERVER"
	openssl x509 -req -in $(CERT)serverreq.pem -CA $(CERT)root.pem -CAkey $(CERT)root.pem -CAcreateserial -out $(CERT)servercert.pem
	cat $(CERT)servercert.pem $(CERT)serverkey.pem $(CERT)rootcert.pem > $(CERT)server.pem

pclaveCliente:
	openssl rsa -in $(CERT)clientkey.pem -passin pass:$(PASS) -pubout -out $(CERT)clientpubkey.pem
	cat $(CERT)clientpubkey.pem > $(CERT)temp.pem
	cat $(CERT)clientcert.pem $(CERT)temp.pem > $(CERT)clientpubkey.pem
	rm -f $(CERT)temp.pem

pclaveServidor:
	openssl rsa -in $(CERT)serverkey.pem -passin pass:$(PASS) -pubout -out $(CERT)serverpubkey.pem
	cat $(CERT)serverpubkey.pem > $(CERT)temp.pem
	cat $(CERT)servercert.pem $(CERT)temp.pem > $(CERT)serverpubkey.pem
	rm -f $(CERT)temp.pem

potencia: src/potencia.c
	@gcc -o potencia src/potencia.c $(GMP)
	@echo "potencia generado ejecutable"

miller-rabin: src/miller-rabin.c src/primeros2kprimos.c
	@gcc -o primo src/miller-rabin.c -include src/primeros2kprimos.c $(GMP)
	@echo "primo generado ejecutable"


#EJEMPLOS DE USO
testpotencia: potencia
	@echo "ejecutando potencia 23 11 243"
	./potencia 23 11 243
testprimo: miller-rabin
	@echo "ejecutando primo"
	./primo -b 1024 -t 8
cleanCert:
	@rm -f cert/*
mrProper:
	@rm -vf primo potencia cert/*
