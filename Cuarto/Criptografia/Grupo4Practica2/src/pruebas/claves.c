#include <stdio.h>
#include <stdlib.h>

#include <stdint.h>
/* Constantes para el DES */
#define NUM_S_BOXES 8
#define ROWS_PER_SBOX 4
#define COLUMNS_PER_SBOX 16
#define SBOX_BLOCK_SIZE 6

#define superior56 0xFFFFFFF0000000
#define inferior56 0x0000000FFFFFFF


/* Constantes para el DES */
#define BITS_IN_PC1 56
#define BITS_IN_PC2 48
#define ROUNDS 16
#define BITS_IN_IP 64
#define BITS_IN_E 48
#define BITS_IN_P 32

/* "permutación" PC1 */
static const unsigned short PC1[BITS_IN_PC1] = { 
	57, 49, 41, 33, 25, 17, 9,
	1, 58, 50, 42, 34, 26, 18,
	10, 2, 59, 51, 43, 35, 27,
	19, 11, 3, 60, 52, 44, 36,
	63, 55, 47, 39, 31, 23, 15,
	7, 62, 54, 46, 38, 30, 22,
	14, 6, 61, 53, 45, 37, 29,
	21, 13, 5, 28, 20, 12, 4
};

/* "permutación" PC2 */
static const unsigned short PC2[BITS_IN_PC2] = {
	14, 17, 11, 24, 1, 5,
	3, 28, 15, 6, 21, 10,
	23, 19, 12, 4, 26, 8,
	16, 7, 27, 20, 13, 2,
	41, 52, 31, 37, 47, 55,
	30, 40, 51, 45, 33, 48,
	44, 49, 39, 56, 34, 53,
	46, 42, 50, 36, 29, 32
};

/* número de bits que hay que rotar cada semiclave según el número de ronda */
static const unsigned short ROUND_SHIFTS[ROUNDS] = {
	1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
};

/* permutación IP */
static const unsigned short IP[BITS_IN_IP] = {
	58, 50, 42, 34, 26, 18, 10, 2,
	60, 52, 44, 36, 28, 20, 12, 4,
	62, 54, 46, 38, 30, 22, 14, 6,
	64, 56, 48, 40, 32, 24, 16, 8,
	57, 49, 41, 33, 25, 17, 9, 1,
	59, 51, 43, 35, 27, 19, 11, 3,
	61, 53, 45, 37, 29, 21, 13, 5,
	63, 55, 47, 39, 31, 23, 15, 7
};

/* inversa de IP */
static const unsigned short IP_INV[BITS_IN_IP] = {
	40, 8, 48, 16, 56, 24, 64, 32,
	39, 7, 47, 15, 55, 23, 63, 31,
	38, 6, 46, 14, 54, 22, 62, 30,
	37, 5, 45, 13, 53, 21, 61, 29,
	36, 4, 44, 12, 52, 20, 60, 28,
	35, 3, 43, 11, 51, 19, 59, 27,
	34, 2, 42, 10, 50, 18, 58, 26,
	33, 1, 41, 9, 49, 17, 57, 25
};

/* expansión E */
static const unsigned short E[BITS_IN_E] = {
	32, 1, 2, 3, 4, 5,
	4, 5, 6, 7, 8, 9,
	8, 9, 10, 11, 12, 13,
	12, 13, 14, 15, 16, 17,
	16, 17, 18, 19, 20, 21,
	20, 21, 22, 23, 24, 25,
	24, 25, 26, 27, 28, 29,
	28, 29, 30, 31, 32, 1
};

/* permutación P */
static const unsigned short P[BITS_IN_P] = {
	16, 7, 20, 21,
	29, 12, 28, 17,
	1, 15, 23, 26,
	5, 18, 31, 10,
	2, 8, 24, 14,
	32, 27, 3, 9,
	19, 13, 30, 6,
	22, 11, 4, 25
};
void * permutacion(uint64_t entrada, const unsigned short *tabla, int sizeEntrada, int sizeTabla){
	
	int i;
	int desplazamiento;	
	uint64_t primerbit;
	uint64_t mascara;
	uint64_t salida = 0x0;
	//ajustar para expansion:
	if(sizeEntrada < sizeTabla){
		entrada <<= (sizeTabla - sizeEntrada);
		sizeEntrada += (sizeTabla - sizeEntrada);
	}
	primerbit = (uint64_t)1 << (sizeEntrada -1);
	//printf("%llx\n", primerbit);

	for(i = 0; i < sizeTabla; i++) {
		/*bitsalida[i] = bitEntrada[tabla[i]]*/		
		desplazamiento = (int) tabla[i];
		//numerito == 57
		mascara = primerbit >> (desplazamiento - 1);
	//	printf("%d %d %llx\n", i, desplazamiento, mascara);
	//	printf("entrada&mascara %llx\n", entrada & mascara);
		desplazamiento = desplazamiento -i -1;
		if(desplazamiento > 0) {
	//		printf("entrada&mascara<< %llx\n", (entrada & mascara) << (desplazamiento));
	//		printf("%d\n", desplazamiento -i -1);

			//
	//		printf("%llx\n", salida);
			salida = salida | ((entrada & mascara) << (desplazamiento));
			
		}
		else {
	//		printf("NEGATIVO\n");
	//		printf("entrada&mascara<< %llx\n", (entrada & mascara) >> (-desplazamiento));
			salida = salida | ((entrada & mascara) >> (-desplazamiento));
		}
	//	printf("%llx\n", salida);
	}
	//Ajustar hacia la derecha
	if(sizeEntrada > sizeTabla) {
		salida >>= (sizeEntrada - sizeTabla);
	}
	return salida;
}
uint64_t rotate(uint64_t x, int rotation, int size, uint64_t mask) {

	return mask & ((x << rotation) | (x >> (size - rotation)));
}

/*De momento genera una clave medio a mano. Generar todas y devolver en un array
o tal vez ir generando segun vayan haciendo falta, no se*/
void * generarClaves(uint64_t clave){
	
	int i;
	uint64_t k;
	uint64_t c;
	uint64_t d;
	uint64_t rotato1;
	uint64_t rotato2;
	uint64_t *subkeys;

	subkeys = (uint64_t *) malloc(ROUNDS * sizeof(uint64_t));
	//Aplicar permutacion PC1
	k = permutacion(clave, PC1, 64, 56);
	//k = 0b11110000110011001010101011110101010101100110011110001111;
	//Dividir la clave de 56 bits en 2 partes de 28.
	printf("%llx\n", k);
	c = (k & superior56) >> 28;
	d = k & inferior56;
	printf("c, d %llx %llx\n", c, d);
	
	for(i = 0; i < ROUNDS; i++) {

		c = rotate(c, ROUND_SHIFTS[i], 28, inferior56);
		d = rotate(d, ROUND_SHIFTS[i], 28, inferior56);

		printf("%llx == %llx\n", c, 0b1110000110011001010101011111);
		printf("%llx == %llx\n", d, 0b1010101011001100111100011110);

		
		k = c << 28 | d; //Concatenar 28 + 28 -> 56
		printf("%llx == %llx\n", k, 0b11100001100110010101010111111010101011001100111100011110);
		//Permutar PC2
		subkeys[i] = permutacion(k, PC2, 56, 48);
		//k = 0b000110110000001011101111111111000111000001110010;
	}

	return subkeys;
}

int main() {
	int i;
	uint64_t pc1 = 0b0001001100110100010101110111100110011011101111001101111111110001;

	uint64_t keys[ROUNDS] = {0b000110110000001011101111111111000111000001110010,
							 0b011110011010111011011001110110111100100111100101,
							 0b010101011111110010001010010000101100111110011001,
							 0b011100101010110111010110110110110011010100011101, 
							 0b011111001110110000000111111010110101001110101000, 
							 0b011000111010010100111110010100000111101100101111, 
							 0b111011001000010010110111111101100001100010111100, 
							 0b111101111000101000111010110000010011101111111011, 
							 0b111000001101101111101011111011011110011110000001, 
							 0b101100011111001101000111101110100100011001001111,
							 0b001000010101111111010011110111101101001110000110,
							 0b011101010111000111110101100101000110011111101001,
							 0b100101111100010111010001111110101011101001000001,
							 0b010111110100001110110111111100101110011100111010,
							 0b101111111001000110001101001111010011111100001010,
							 0b110010110011110110001011000011100001011111110101}; 
	uint64_t *subkeys;

	
	subkeys = generarClaves(pc1);
	printf("Subclaves:\n");
	for (i = 0; i < ROUNDS; i++){
		if(subkeys[i] != keys[i]){
			printf("------ERROR------\n");
		}
		printf("%llx == %llx\n", subkeys[i], keys[i]);
	}

	free(subkeys);
}
