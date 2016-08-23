FLTRM= src/filtro.c
FLTRX= src/filtro.l
FLTRL= includes/filtro.h
LLIB= -Llib -lhastable
LINCL= -include includes/jwHash.h
DEFS = -DHASHTEST -DHASHTHREADED
GMP = -lgmp -lm -L.
all: filtro seg-perf des sac-des bic-des rondas-des sac-bic-aes nolinealidadAES nolinealidadDES
	@echo ""

filtro: $(FLTRX) $(FLTRM) $(FLTRL)
	@flex -o obj/lex.yy.c $(FLTRX)
	@gcc -c obj/lex.yy.c -o obj/lex.yy.o -include $(FLTRL)
	@gcc -o filtro $(FLTRM) obj/lex.yy.o -include $(FLTRL)
	@rm obj/*.c
	@echo "filto compilado"


obj/aes.o: src/aes.c
	@gcc -c -o obj/aes.o src/aes.c -include includes/aes.h
	@echo "aes.o compilado"

obj/sac_aes.o: src/sac_aes.c obj/aes.o 
	@gcc  -c -o obj/sac_aes.o src/sac_aes.c -include includes/aes.h
	@echo "sac_aes.o compilado"

obj/bic_aes.o: src/bic_aes.c obj/aes.o
	@gcc  -c -o obj/bic_aes.o src/bic_aes.c -include includes/aes.h
	@echo "bic_aes.o compilado"

sac-bic-aes: obj/sac_aes.o obj/bic_aes.o obj/aes.o
	@gcc -o sac-bic-aes src/sac_bic_aes.c obj/bic_aes.o obj/sac_aes.o obj/aes.o -include includes/aes.h
	@echo "sac-bic-aes generado ejecutable"

seg-perf: src/seg-perf.c filtro
	@gcc -o seg-perf src/seg-perf.c $(GMP)
	@echo "seg-perf generado ejecutable"

obj/des.o: src/des.c
	@gcc -c -o obj/des.o src/des.c -include includes/des.h
	@echo "des compilado"

des: src/maindes.c obj/des.o includes/des.h
	@gcc -o des src/maindes.c obj/des.o -include includes/des.h
	@echo "des generado ejecutable"
	
sac-des:src/sac.c obj/des.o includes/des.h
	@gcc -o sac-des src/sac.c obj/des.o -include includes/des.h
	@echo "sac generado ejecutable"
	
bic-des: src/bic.c obj/des.o includes/des.h
	@gcc -o bic-des src/bic.c obj/des.o -include includes/des.h
	@echo "bic generado ejecutable"

rondas-des: src/rondas-des.c obj/des.o
	@gcc -o rondas-des src/rondas-des.c obj/des.o -include includes/des.h
	@echo "rondas-des generado ejecutable"

nolinealidadDES: src/nolinealidadDES.c
	@gcc -o nolinealidadDES src/nolinealidadDES.c
	@echo "nolinealidadDES generado ejecutable"

nolinealidadAES: src/nolinealidadAES.c obj/aes.o
	@gcc -o nolinealidadAES src/nolinealidadAES.c
	@echo "nolinealidadAES generado ejecutable"

#EJEMPLOS DE USO
testseg-perfU: seg-perf
	@echo "ejecutando testseg-perf"
	./seg-perf -P -i gcc.txt

testseg-perfI: seg-perf
	@echo "ejecutando testseg-perf"
	./seg-perf -P -i gcc.txt


testdes:
	@echo "ejecutando des"
	./des -C -k 133457799BBCDFF1 -i doc/memoP2.pdf -o cifrado
	./des -D -k 133457799BBCDFF1 -i cifrado -o memoP2Des.pdf
testsac-des:
	@echo "ejecutando testsac-des"
	./sac-des
testbic-des:
	@echo "ejecutando testbic-des"
	./bic-des
testrondas-des:
	@echo "ejecutando testrondas-des"
	./rondas-des
testsac-bic-aes:
	@echo "ejecutando testsac-bic-aes"
	./testsac-bic-aes
testnolinealidadDES:
	@echo "ejecutando testnolinealidadDES"
	./nolinealidadDES
testnolinealidadAES:
	@echo "ejecutando testnolinealidadAES"
	./nolinealidadAES
clean:
	@rm -f obj/* -v	
	@rm -f Fgcc.txt filtro sac-bic-aes seg-perf des sac-des bic-des rondas-des nolinealidadAES nolinealidadDES cifrado randDescifrado.png -v