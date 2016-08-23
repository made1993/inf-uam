::
:: Practicas de Arquitectura DE Ordenadores (3º Grado en Ing. Informatica) Curso 2013/2014
::
:: ESTE SCRIPT LLAMA AL ESAMBLADOR Y LUEGO UTILIZANDO OBJDUMP Y UN SCRIPT AWK
:: GENERA LOS FICHEROS NECESARIOS PARA INICIALIZAR LAS MEMORIAS DEL PROCESADOR
::
tools\as.exe programa.asm -g2 -mips32 -o programa.o
del datos
del instrucciones
tools\objdump.exe --full-contents programa.o |tools\awk.exe -f tools\compila.awk
del programa.o
