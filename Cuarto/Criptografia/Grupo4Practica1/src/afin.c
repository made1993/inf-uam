#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
#include <string.h>
#include <gmp.h>


mpz_t* inverso(mpz_t a ,mpz_t base){
	mpz_t x , y, d, a1;
	mpz_t *resp;
	mpz_t x2, x1, y2, y1, q, r,aux,auxbase;
  	resp = (mpz_t*) malloc(3*sizeof(mpz_t));
	mpz_init_set(auxbase,base);
	mpz_init_set(a1,a);
	mpz_init(y);
	mpz_init(d);
	mpz_init(x);
	mpz_init(x1);
	mpz_init(x2);
	mpz_init(y2);
	mpz_init(y1);
	mpz_init(q);
	mpz_init(aux);
	mpz_init(r);
	if (mpz_get_si(auxbase)==0) {
    	mpz_init_set(resp[0], a1);
    	mpz_init_set_si(resp[1], 1);
    	mpz_init(resp[2]);
  	}
	else {
		mpz_set_si(x2, 1);
		mpz_set_si(x1, 0);
		mpz_set_si(y2, 0);
		mpz_set_si(y1, 1);
		mpz_set_si(q, 0);
		mpz_set_si(r, 0);
		while(mpz_get_ui(auxbase)>0) {
			mpz_cdiv_q(q,a1,auxbase);
			
			mpz_mul(aux,q,auxbase);
			mpz_sub(r,a1,aux);

			mpz_mul(aux, q,x1);
			mpz_sub(x,x2,aux);
			
			mpz_mul(aux, q,y1);
			mpz_sub(y,y2,aux);
			
			mpz_set(a1,auxbase);

			mpz_set(auxbase,r);

			mpz_set(x2,x1);

			mpz_set(x1,x);

			mpz_set(y2,y1);
			
			mpz_set(y1,y);
		}
		mpz_init_set(resp[0], a1);
		mpz_init_set(resp[1], x2);
	    mpz_init_set(resp[2], y2);
	}
	mpz_clear(auxbase);
	mpz_clear(a1);
	mpz_clear(y);
	mpz_clear(d);
	mpz_clear(x);
	mpz_clear(x1);
	mpz_clear(x2);
	mpz_clear(y2);
	mpz_clear(y1);
	mpz_clear(q);
	mpz_clear(aux);
	mpz_clear(r);
	return resp;
}

char* afinC(char* text, mpz_t a, mpz_t b,mpz_t zm, int size){
	int i;
	char* c=NULL;
	mpz_t x;
	c = (char*)malloc(size);
	
	i=0;
	mpz_init(x);
	for (;i<size;i++){
		//printf("%c", text[i]);
		mpz_set_si(x,(long)(text[i]-97));
		mpz_mul(x,x,a);
		mpz_add(x,x,b);
		mpz_mod(x,x,zm);
		c[i]=(char)(mpz_get_si(x));
		c[i]+=97;
	}
	mpz_clear(x);
	return c;
}
char* afinD (char * text, mpz_t a, mpz_t b,mpz_t zm, int size){
	
	int i;
	char* d = NULL;
	mpz_t x;
	i=0;
	d = (char*)malloc(size);
	mpz_init(x);
	for (;i<size;i++){
		mpz_set_si(x,(long)(text[i]-97));
		mpz_sub(x,x,b);
		mpz_mod(x,x,zm);
		mpz_mul(x,x,a);
		mpz_mod(x,x,zm);
		d[i]=mpz_get_si(x);
		d[i]+=97;
	}
	mpz_clear(x);
	return d;	
}

int main (int argc, char **argv){

	int long_index=0, end=0, size=0;
	char opt;
	static int flagD=0,flagC=0;
	char* text=NULL,*c=NULL,*d=NULL,* buff=NULL,fltr[100]="";
	FILE  * fout =stdout, *fin=stdin;
	int fa=0,fb=0,fzm=0,ffin=0;
	mpz_t a, b,zm,inv;
	mpz_t* mcd= NULL;
	static struct option options[] = {
	    {"C",no_argument,&flagC,1},
	    {"D",no_argument,&flagD, 1},
	    {"m",required_argument,0,'3'},
	    {"a",required_argument,0,'4'},
	    {"b",required_argument,0,'5'},
	    {"i",required_argument, 0, '6'},
	    {"o",required_argument, 0, '7'},
	    {0,0,0,0}
	};

	optarg =NULL;
	while ((opt = getopt_long_only(argc, argv,"3:4:5:6:7:", options, &long_index )) != -1){
		switch(opt){
			case '3':
				fzm=1;
				mpz_init_set_str(zm,optarg,	10);
			break;
			case '4':
				fa=1;
				if(-1==mpz_init_set_str(a,optarg, 10)){
					mpz_clear(a);
					printf("%s {-C|-D} {-m |Z m |} {-a N × } {-b N + } [-i f ile in ] [-o f ile out ]\n", argv[0]);
					return;
				}
			break;
			case '5':
				fb=1;
				if(-1==mpz_init_set_str(b,optarg, 10)){
					mpz_clear(b);
					printf("%s {-C|-D} {-m |Z m |} {-a N × } {-b N + } [-i f ile in ] [-o f ile out ]\n", argv[0]);
					return;
				}
			break;
			case '6':
				if(flagC){
					strcpy(fltr,"./filtro ");
					strcat(fltr, optarg);
					strcat(fltr, " F");
					strcat(fltr, optarg);
					printf("oreden=%s\n",fltr );
					if(!system(fltr)){
						return 0;
					}
					strcpy(fltr,"F");	
				}
				strcat(fltr, optarg);
				printf("archivo=%s\n",fltr );
				fin=fopen (fltr, "r");
				if(fin==NULL){
					printf("\nError: el archivo %s no existe\n", optarg);
					return 0;
				}
			break;
			case '7':
				fout=fopen (optarg, "w");
			break;
			case'?':
				printf("%s {-C|-D} {-m |Z m |} {-a N × } {-b N + } [-i f ile in ] [-o f ile out ]\n", argv[0]);
			break;

		}
	}
	if (fb==0 || fa==0|| fzm==0 || flagD==0 && flagC==0 || flagD==1 && flagC==1){
		printf("%s {-C|-D} {-m |Z m |} {-a N × } {-b N + } [-i f ile in ] [-o f ile out ]\n", argv[0] );
		return 0;
	}
	mcd=inverso(a,zm);
	if(mpz_get_ui(mcd[0])!=1){
		printf("mcd=%lu\n", mpz_get_ui(mcd[0]));
		printf("No hay inverso para a=%ld en Z%ld\n", mpz_get_si(a),mpz_get_ui(zm));
		return 0;
	}

	buff = (char*)malloc(512);
					
	do{
		if(fin==stdin){
			text=buff;
			fscanf(fin,"%s",text);
		}else{
			end=fread(buff,1,512, fin);
			size+=end;
			buff = (char*) realloc(buff,end+1);
			text = (char*) realloc(text,size+1);
			strncat(text, buff,end);
		}
	}while(fin!=stdin && end==512);

	if(flagC){
		//printf("size=%d\n",size>0?size:strlen(text) );
		c =afinC(text ,a ,b,zm,size>0?size:strlen(text));
		fwrite(c,sizeof(char),size>0?size:strlen(text),fout);
		free(c);
	}
	else if(flagD){
		mpz_init(inv);
		//Calcular el inverso de a, para poder multiplicar
		mpz_set(inv, mcd[1]);
		if(mpz_sgn(mcd[0]) == -1){
			mpz_sub(inv, inv, mcd[1]);
			mpz_sub(inv, inv, mcd[1]);
			mpz_add(inv, inv, zm);
		}
		d =afinD(text ,inv ,b,zm,size>0?size:strlen(text));
		fwrite(d,1,size>0?size:strlen(text),fout);
		free(d);
		mpz_clear(inv);

	}
	if(fout==stdout)
		printf("\n");
	mpz_clear(mcd[0]);
	mpz_clear(mcd[1]);
	mpz_clear(mcd[2]);
	free(mcd);
	free(text);
	mpz_clear(zm);
	mpz_clear(a);
	mpz_clear(b);
	if(fin !=stdin) free(fin);
	if(fout !=stdout)free(fout);
	return 0;
}