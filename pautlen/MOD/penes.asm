 			;empieza constante entera
;numero_linea 4 
	push dword 5
 			;termina constante entera
segment .bss
	_vector1 resd 5
	_x resd 1
	_i resd 1
	_suma resd 1

segment .data
	mensaje_1 db	"Indice fuera de rango", 0
	mensaje_2	db "Division por 0", 0
segment .text
global main
extern scan_int, scan_boolean
extern print_int, print_boolean, print_string,print_blank,print_endofline
main:
 			;empieza constante entera
;numero_linea 7 
	push dword 0
 			;termina constante entera
 			;empieza asignacion
;Cargar en eax la parte derecha de la asignacion
pop dword eax
;hacer la asignacion efectiva
mov dword [_i],eax
 			;termina asignacion
 			;empieza while
inicio_while0: ; es etiqueta $$.etiqueta
 			;termina while
mov eax, _i
push eax
 			;empieza constante entera
;numero_linea 9 
	push dword 5
 			;termina constante entera
;cargar la segunda expresion en edx
pop dword edx
;cargar la primera expresion en eax
pop dword eax
mov dword eax, [eax]
;comparar y apilar el resultado
cmp eax, edx
jl near menor1
push dword 0
jmp near fin_menor1
menor1:	push dword 1
fin_menor1:
 			;empieza while_exp
pop eax
cmp eax, 0
je fin_while0
 			;termina while_exp
 			;empieza constante entera
;numero_linea 11 
	push dword 1
 			;termina constante entera
 			;empieza asignacion
;Cargar en eax la parte derecha de la asignacion
pop dword eax
;hacer la asignacion efectiva
mov dword [_x],eax
 			;termina asignacion
mov eax, _i
push eax
 			;empieza elemento_vector
;Cargar el valor del indice en eax
pop dword eax
mov dword eax, [eax]
;Si el indice es menor que 0, error en tiempo de ejecucion
cmp eax, 0
jl near mensaje_1;Si el indice es mayor de lo permitido, error en tiempo de ejecucion
cmp eax, 64
jg near mensaje_1;EN ESTE PUNTO EL INDICE ES CORRECTO
;Y ESTA EN EL REGISTRO eax
;Cargar en eax la direccion de inicio del vector
mov dword edx, _vector1
;Cargar en eax la direccion del elemento indexado
lea eax, [edx +eax*4]
;Aplicar la direccion del elemento indexado
push dword eax
 			;termina elemento_vector
mov eax, _x
push eax
 			;empieza asignacion
;Cargar en eax la parte derecha de la asignacion
pop dword eax
mov dword eax, [eax]
;cargar en edx la parte izquierda de la asignacion
pop dword edx
;Hacer la asignacion efectiva
mov dword [edx], eax
 			;termina asignacion
mov eax, _i
push eax
 			;empieza constante entera
;numero_linea 13 
	push dword 1
 			;termina constante entera
			;empieza gc_sumaenteros
;cargar el segundo operando en edx
pop dword edx
;cargar el primer operando en eax
pop dword eax
mov dword eax, [eax]
;realizar la suma y dejar el reslutado en eax
add eax, edx
;apilar reslutado
push dword eax
 			;empieza asignacion
;Cargar en eax la parte derecha de la asignacion
pop dword eax
;hacer la asignacion efectiva
mov dword [_i],eax
 			;termina asignacion
 			;empieza bucle
jmp near inicio_while0
fin_while0:
 			;termina bucle
 			;empieza constante entera
;numero_linea 15 
	push dword 0
 			;termina constante entera
 			;empieza asignacion
;Cargar en eax la parte derecha de la asignacion
pop dword eax
;hacer la asignacion efectiva
mov dword [_i],eax
 			;termina asignacion
 			;empieza while
inicio_while1: ; es etiqueta $$.etiqueta
 			;termina while
mov eax, _i
push eax
 			;empieza constante entera
;numero_linea 16 
	push dword 5
 			;termina constante entera
;cargar la segunda expresion en edx
pop dword edx
;cargar la primera expresion en eax
pop dword eax
mov dword eax, [eax]
;comparar y apilar el resultado
cmp eax, edx
jl near menor2
push dword 0
jmp near fin_menor2
menor2:	push dword 1
fin_menor2:
 			;empieza while_exp
pop eax
cmp eax, 0
je fin_while1
 			;termina while_exp
mov eax, _suma
push eax
mov eax, _i
push eax
 			;empieza elemento_vector
;Cargar el valor del indice en eax
pop dword eax
mov dword eax, [eax]
;Si el indice es menor que 0, error en tiempo de ejecucion
cmp eax, 0
jl near mensaje_1;Si el indice es mayor de lo permitido, error en tiempo de ejecucion
cmp eax, 64
jg near mensaje_1;EN ESTE PUNTO EL INDICE ES CORRECTO
;Y ESTA EN EL REGISTRO eax
;Cargar en eax la direccion de inicio del vector
mov dword edx, _vector1
;Cargar en eax la direccion del elemento indexado
lea eax, [edx +eax*4]
;Aplicar la direccion del elemento indexado
push dword eax
 			;termina elemento_vector
			;empieza gc_sumaenteros
;cargar el segundo operando en edx
pop dword edx
mov dword edx, [edx]
;cargar el primer operando en eax
pop dword eax
mov dword eax, [eax]
;realizar la suma y dejar el reslutado en eax
add eax, edx
;apilar reslutado
push dword eax
 			;empieza asignacion
;Cargar en eax la parte derecha de la asignacion
pop dword eax
;hacer la asignacion efectiva
mov dword [_suma],eax
 			;termina asignacion
mov eax, _i
push eax
 			;empieza constante entera
;numero_linea 20 
	push dword 1
 			;termina constante entera
			;empieza gc_sumaenteros
;cargar el segundo operando en edx
pop dword edx
;cargar el primer operando en eax
pop dword eax
mov dword eax, [eax]
;realizar la suma y dejar el reslutado en eax
add eax, edx
;apilar reslutado
push dword eax
 			;empieza asignacion
;Cargar en eax la parte derecha de la asignacion
pop dword eax
;hacer la asignacion efectiva
mov dword [_i],eax
 			;termina asignacion
 			;empieza bucle
jmp near inicio_while1
fin_while1:
 			;termina bucle
mov eax, _suma
push eax
 			;empieza printf
pop dword eax
mov dword eax,[eax]
push dword eax
;Si la expresion es de tipo entero
call print_int
add esp, 4
;Salto de linea
call print_endofline
 			;termina printf
ret
error_1:
push dword mensaje_1
call print_string
add esp, 4
jmp near fin
error_2:
push dword mensaje_2
call print_string
add esp, 4
fin:ret
