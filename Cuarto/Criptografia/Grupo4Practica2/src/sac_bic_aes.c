#include <getopt.h>

int main (int argc, char **argv){

	int long_index=0;
	char opt;
	char* text=NULL,* buff=NULL;
	FILE* fout =stdout;
	int size=0;
	int fk = 0, end=0, ic=0, i=0,j=0;
	static int flagC=0,flagD=0, flagB=0, flagS=0;
	uint64_t k=0, k2=0, c, m=0;
	char strout[9],* strout2;
	static struct option options[] = {
		{"C", no_argument, &flagC, 1},
	    {"D", no_argument, &flagD, 1},
	    {"S", no_argument, &flagS, 1},
	    {"B", no_argument, &flagB, 1},
	    {"o", required_argument, 0, '7'},
	    {0 , 0, 0, 0}
	};

	
	while ((opt = getopt_long_only(argc, argv,"7:", options, &long_index )) != -1){
		switch(opt){
			case '7':
				printf ("o\n");
				fout=fopen (optarg, "wb");
				

			break;
			case'?':
				printf("%s {-C | -D} {-S | -B} [-o file out ] \n", argv[0]);
				return 1;
			break;

		}
	}


	if( !((flagC ^ flagD) && (flagS ^ flagB)) ){
		printf("Error se esperaba:\n");
		printf("%s {-C | -D} {-S | -B} [-o file out ] \n", argv[0]);
		return 1;

	}
	if (flagS){
		sac(flagC, fout);
	}
	else{
		bic(flagC, fout);
	}
	return 1;
}