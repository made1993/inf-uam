#include <stdio.h>
#include <stdlib.h>

#include <stdint.h>
/* Constantes para el DES */
#define BITS_IN_PC1 56
#define BITS_IN_PC2 48
#define ROUNDS 16
#define BITS_IN_IP 64
#define BITS_IN_E 48
#define BITS_IN_P 32
#define NUM_S_BOXES 8
#define ROWS_PER_SBOX 4
#define COLUMNS_PER_SBOX 16

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
	//printf("%lx\n", primerbit);

	for(i = 0; i < sizeTabla; i++) {
		/*bitsalida[i] = bitEntrada[tabla[i]]*/		
		desplazamiento = (int) tabla[i];
		//numerito == 57
		mascara = primerbit >> (desplazamiento - 1);
	//	printf("%d %d %lx\n", i, desplazamiento, mascara);
	//	printf("entrada&mascara %lx\n", entrada & mascara);
		desplazamiento = desplazamiento -i -1;
		if(desplazamiento > 0) {
	//		printf("entrada&mascara<< %lx\n", (entrada & mascara) << (desplazamiento));
	//		printf("%d\n", desplazamiento -i -1);

			//
	//		printf("%lx\n", salida);
			salida = salida | ((entrada & mascara) << (desplazamiento));
			
		}
		else {
	//		printf("NEGATIVO\n");
	//		printf("entrada&mascara<< %lx\n", (entrada & mascara) >> (-desplazamiento));
			salida = salida | ((entrada & mascara) >> (-desplazamiento));
		}
	//	printf("%lx\n", salida);
	}
	if(sizeEntrada > sizeTabla) {
		salida >>= (sizeEntrada - sizeTabla);
	}
	return salida;
}

int main() {

	uint64_t pc1 = 0b0001001100110100010101110111100110011011101111001101111111110001;
	uint64_t k2 = 0x133457799bbcdff1;
	uint64_t pc2 = 0b11100001100110010101010111111010101011001100111100011110;
	uint64_t ip = 0b0000000100100011010001010110011110001001101010111100110111101111;
	uint64_t e = 0b11110000101010101111000010101010;
	uint64_t p = 0b01011100100000101011010110010111;
	uint64_t invip = 0b0000101001001100110110011001010101000011010000100011001000110100;
	uint64_t c;	
	c = permutacion(pc1, PC1, 64, 56);
	printf("Permutacion PC1\n");
	printf("Entrada:    %lx\n", pc1);
	printf("c:          %lx\n", c);
	printf("c esperado: %lx\n\n", 0b11110000110011001010101011110101010101100110011110001111);

	c = permutacion(pc2, PC2, 56, 48);
	printf("Permutacion PC2\n");
	printf("Entrada:    %lx\n", pc2);
	printf("c:          %lx\n", c);
	printf("c esperado: %lx\n\n", 0b000110110000001011101111111111000111000001110010);
	c =  permutacion(ip, IP, 64, 64);
	printf("Permutacion IP\n");
	printf("Entrada:    %lx\n", ip);
	printf("c:          %lx\n", c);
	printf("c esperado: %lx\n\n", 0b1100110000000000110011001111111111110000101010101111000010101010);
	c =  permutacion(e, E, 32, 48);
	printf("Expansion E\n");
	printf("Entrada:    %lx\n", e);
	printf("c:          %lx\n", c);
	printf("c esperado: %lx\n\n",  0b011110100001010101010101011110100001010101010101);
	c =  permutacion(p, P, 32, 32);
	printf("Permutacion P\n");
	printf("Entrada:    %lx\n", p);
	printf("c:          %lx\n", c);
	printf("c esperado: %lx\n\n",  0b00100011010010101010100110111011);
	c =  permutacion(invip, IP_INV, 64, 64);
	printf("Permutacion IP inversa\n");
	printf("Entrada:    %lx\n", invip);
	printf("c:          %lx\n", c);
	printf("c esperado: %lx\n\n",  0b1000010111101000000100110101010000001111000010101011010000000101);

}
