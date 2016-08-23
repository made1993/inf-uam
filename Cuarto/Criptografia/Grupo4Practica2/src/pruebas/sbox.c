#include <stdio.h>
#include <stdlib.h>

#include <stdint.h>
/* Constantes para el DES */
#define NUM_S_BOXES 8
#define ROWS_PER_SBOX 4
#define COLUMNS_PER_SBOX 16
#define SBOX_BLOCK_SIZE 6
/* cajas S */
static const unsigned short S_BOXES[NUM_S_BOXES][ROWS_PER_SBOX][COLUMNS_PER_SBOX] = {
	{	{ 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
		{ 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
		{ 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
		{ 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } 	},
	{
		{ 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 },
		{ 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
		{ 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
		{ 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 }	},

	{	{ 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
		{ 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
		{ 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
		{ 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 }	},

	{	{ 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
		{ 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
		{ 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
		{ 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 }	},
	{
		{ 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
		{ 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
		{ 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
		{ 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 }	},
	{	
		{ 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
		{ 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
		{ 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
		{ 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 }	},
	{
 		{ 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
		{ 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
		{ 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
		{ 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 }	},
	{
		{ 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
		{ 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
		{ 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
		{ 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 }	}
};

void * sbox(uint64_t entrada){
	
	int i;
	int desplazamiento;	
	uint64_t temporal;
	uint64_t mascara = 0xFC0000000000;
	uint64_t mascaraF1 = 0x800000000000;
	uint64_t mascaraF2 = 0x040000000000;
	uint64_t mascaraC = 0x7C0000000000;
	uint64_t salida = 0x0;
	uint64_t fila, fila1, fila2, columna;

	for(i = 0; i < NUM_S_BOXES; i++) {
		

		temporal = entrada & mascara;
		
		/*Obtener fila*/
		fila1 = temporal & mascaraF1;							//Bit de mas peso 				 X _ _ _ _ _
		fila2 = temporal & mascaraF2;							//Bit de menor peso 			 _ _ _ _ _ Y
		fila1 >>= 4; 											//Desplazar para juntar los bits _ _ _ _ X _
		fila = (fila1 | fila2) >> SBOX_BLOCK_SIZE * (7 - i); 	//And + desplazo				 _ _ _ _ X Y


		columna = temporal & mascaraC; 							//Obtener columna				 _ W X Y Z _
		columna >>= SBOX_BLOCK_SIZE * (7 - i) + 1;		   		//Desplazar al principio

		//printf("%lx, %lx\n", fila, columna);
		//printf("%lx\n", S_BOXES[i][fila][columna]);
		salida |= S_BOXES[i][fila][columna];					//Or a la salida del resultado de la sbox
		//printf("Salida %lx\n", salida);
		salida <<= 4;								//desplazar la salida (hacer hueco siguiente)

		/*Ajustar mascaras para la siguiente iteracion*/
		mascara >>= SBOX_BLOCK_SIZE;
		mascaraF1 >>= SBOX_BLOCK_SIZE;
		mascaraF2 >>= SBOX_BLOCK_SIZE;
		mascaraC >>= SBOX_BLOCK_SIZE;
			
	}
	salida >>= 4;									//Deshacer ultimo desplazamiento
	return salida;
}

int main() {
				  //011000 010001 011110 111010 100001 100110 010100 100111.
	uint64_t in = 0b011000010001011110111010100001100110010100100111;
	uint64_t out = 0x0;

	uint64_t c;	
	c = sbox(in);
	printf("SBOX\n");
	printf("Entrada:    %lx\n", in);
	printf("Salida:     %lx\n", c);
	printf("Salida esp: %lx\n\n", 0b01011100100000101011010110010111);



}
