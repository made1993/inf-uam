#include <time.h>

/*Criterio SAC: un bit input cambiado debe producir un cambio en cada bit de salida con probabilidad 1/2*/
#define NUMBER_OF_TESTS 100000

int main (){

	uint64_t mask;
	uint64_t maskBase = 0b000001000001000001000001000001000001000001000001;
	uint64_t in = 0;
	uint64_t out;
	int changes[32] = {0};
	int cambiosTotales = 0;
	int i, j, n;
	srand(time(NULL));
	for(n = 0; n < NUMBER_OF_TESTS; n++) {
		
		/*Toma un numero aleatorio de 48 bits como entrada base para las sboxes*/
		in = rand();
		in = (in << 32) | rand();
		in &= 0x0000FFFFFFFFFFFF;
		//printf("%lx -> %lx\n", in, base);

		/*Por cada sbox, complementar un bit cada vez*/
		for(i = 0, mask = maskBase; i < 6; i++, mask <<= 1){
			//printf("mask %lx\n", mask);
			//printf("in   %lx\n", in);
			//printf("xor  %lx\n", in ^ mask);
			out = sbox(in ^ mask);
		//	printf("%lx -> %lx\n", in ^ mask, out);

			/*Comprobar que bits cambian de valor*/
			//printf("%lx %lx\n", base, out);
			for(j = 0; j < 32; j++) {
				//if(j == 4) j = 28;
		//		printf("%lx %lx\n", ((base >> j) & 1), ((out >> j) & 1));
				//out ^= base;
				//if((out >> j & 1) == 0){


				if( (out >> j & 1)==1) {
					//printf("%lx %lx\n", (base >> j & 1), (out >> j & 1));
					/*Incrementar el numero de veces que ha cambiado ese bit*/
					changes[j]++;
					cambiosTotales++;
				}


			}
		}
	}

	/*Mostrar resultados*/
	
	for(i = 0; i < 32; i++) {

		printf("bit %d %d 1's (%05.3f %%)\n", i, changes[i], (float) changes[i] / 6 * 100 / NUMBER_OF_TESTS);

	}
	printf("\nLos datos obtenidos representan el numero de 1's en cada bit producidos por el complemento de un solo bit a la entrada de cada sbox.\nSegun el SAC, cada bit output debe cambiar con probabilidad 1/2 cuando un solo bit entrada de la sbox se complementa.\n");
	/*Salida para R:*/
	printf("\nSALIDA PARA R\n");
	printf("cambios = c(");
	for(i = 0; i < 31; i++) {

		printf("%d, ", changes[i]);
	}
	printf("%d)\n", changes[i]);
	printf("porcentajes = c(");
	for(i = 0; i < 31; i++) {

		printf("%05.3f, ", (float) changes[i] / 6 * 100 / NUMBER_OF_TESTS);
	}
	printf("%05.3f)\n", (float) changes[i] / 6 * 100 / NUMBER_OF_TESTS);

	//printf("%lx", des(0xffffffffffffffff,0x133457799BBCDFF1,1));

	return 0;

}
