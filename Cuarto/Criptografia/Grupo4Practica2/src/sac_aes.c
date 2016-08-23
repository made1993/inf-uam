

//SBOX AES: entrada: 8 bits. Salida: 8bits.
/*Criterio SAC: un bit input cambiado debe producir un cambio en cada bit de salida con probabilidad 1/2*/
void sac(int flagC, FILE * fout) {

	uint8_t in = 0;
	uint8_t maskBase = 1;
	uint8_t mask;
	int i, j, n;
	int changes[32] = {0};
	uint8_t out;
	uint8_t (*ptrSbox)(uint8_t);

	/*Puntero a funcion para obtener valores de la SBOX correspondiente*/
	if(flagC) {
		ptrSbox = &getSBox;
	}
	else {
		ptrSbox = &getInvSBox;
	}
	for(n = 0; n < NUMBER_OF_TESTS; n++) {

		/*Por cada sbox, complementar un bit cada vez*/
		for(i = 0, mask = maskBase; i < 8; i++, mask <<= 1) {
			//printf("%x\n", n ^ mask);
			out = (*ptrSbox)(n ^ mask);
			//printf("%x\n", out);
			/*Comprobar que bits son 1*/
			for(j = 0; j < 8; j++) {
				//printf("%x\n", out >> j & 1);
				if((out >> j & 1) == 1){
					changes[j]++;
				}
			}
		}

	}

	/*Mostrar resultados*/

	for(i = 0; i < 8; i++) {

		printf("bit %d %d 1's (%05.3f %%)\n", i, changes[i], (float) changes[i] / 8 * 100 / NUMBER_OF_TESTS);

	}
	printf("\nLos datos obtenidos representan el numero de unos en cada bit de salida por el complemento de un solo bit a la entrada de cada sbox.\nSegun el SAC, cada bit output debe cambiar con probabilidad 1/2 cuando un solo bit entrada de la sbox se complementa.\n");
	

	return;
}