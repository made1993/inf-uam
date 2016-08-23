#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
#include <string.h>
#include <limits.h>
#include <float.h>

int euclidesExtendido(int a, int b) {

  int resp =0;
  int x = 0, y = 0, d = 0;
  int x2, x1, y2, y1, q, r;
  if (b==0) {
    resp= a;
  }
  else {
    x2 = 1;
    x1 = 0;
    y2 = 0;
    y1 = 1;
    q = 0;
    r = 0;

    while(b>0) {
      q = (a/b);
      r = a - q*b;
      x = x2-q*x1;
      y = y2 - q*y1;
      a = b;
      b = r;
      x2 = x1;
      x1 = x;
      y2 = y1;
      y1 = y; 
    }

    resp= a;
  }
  return resp;
}
double absolute(double value){
	if(value<0){
		return value*(-1.0);
	}
	return value;
}

char* ICcalve(char* text, int size, int clen){
	char* key=(char*)malloc(sizeof(char)*(clen+1));
	char* str= text;
	int i=0, j=0,** frecs=NULL, max=0;
	long int den=0, num=0;
	float aux=0.0, best=100.0,ref=0.0665384615;

	memset(key, 0, sizeof(char)*clen);
	frecs=(int**)malloc(sizeof(int*)*clen);
	for(i=0;i<clen;i++){
		frecs[i]=(int*)malloc(sizeof(int)*26);
		memset(frecs[i],0,sizeof(int)*26);
	}
	for(i=0;i<clen;i++){
		for(j=0;j<26;j++){
		}
	}
	for(i=0;i<size/clen;i++, str+=clen){
		for(j=0;j<clen;j++){
			frecs[j][str[j]-97]+=1;
		}
	}

	for(i=0;i<clen;i++, best=100.0, max=0){
		for(j=0;j<26;j++){
			den=(long int)size/clen*(size/clen-1);
			num=(long int)frecs[i][j]*(frecs[i][j]-1);
			aux=1.0*num/den;

			if(best>absolute(ref-aux)){
				best=absolute(ref-aux);
				max=j+93;
				if (max<97)
					max+=26;
			}			
		}
		key[i]=max;
		free(frecs[i]);
		frecs[i]=NULL;
	}

	free(frecs);
	key[i]='\0';
	return key;

}

int IC(char* text, int size, int fLimit){
	int ret=0, i=1,** frecs=0, j=0,k=0;
	char* str=NULL;
	float ref=0.06653846153846153L, best=DBL_MAX,ic=0.0,* ics=NULL,aux=0.0;
	long int den=0, num=0;
	for(str=text;i< (fLimit ? size: 32);i++,str=text){
		frecs=(int**)malloc(sizeof(int*)*i);
		for (j=0; j < i; j++){
			frecs[j]=(int*)malloc(sizeof(int)*26);
			memset(frecs[j], 0, sizeof(int)*26);
		}
		
		ics=(float*)realloc(ics, i*sizeof(float));
		memset(ics, 0, sizeof(float)*i);

		for(j=0;j<size/i;j++,str+=i){
			for(k=0;k<i;k++){
				frecs[k][str[k]-97]+=1;
			}
		}
		
		for(k=0;k<i;k++){
			for(j=0;j<26;j++){
				den=(long int)size/i*(size/i-1);
				num=(long int)frecs[k][j]*(frecs[k][j]-1);
				aux=1.0*num/den;
				ics[k]= ics[k]+aux;
			}
			free(frecs[k]);
			frecs[k]=NULL;
		}
		for(j=0, ic=0.0;j<i;j++){
			ic+=ics[j];
			
		}

		ic/=i;
		
		if(best>absolute(ref-ic)){
			best=absolute(ref-ic);
			ret= j;
		}
		free(frecs);
		frecs= NULL;
	}
	free(ics);
	return ret;

}

long int kasiski(char * text, int size, int n){
	int ks=0,i=1,*s=NULL,pos=0;
	char* str=text;
	char* tok= (char*) malloc(n+2);
	char* b1=NULL,* b2=NULL,* b3=NULL;
	jwHashTable* ht =create_hash(1000000);
	long int mcd =0;
	int  value=0;
	long int values=0, nums;
	long int max=0;
	jwHashEntry* aux= NULL;
	FILE * f=NULL;
	while(pos++ +n< size){
		strncpy(tok, str, n);
		tok[n]='\0';
		b1=str;
		b1=strstr(b1,tok);
		if(NULL ==b1)
			break;
		b2=strstr(b1+n,tok);
		do{
			if(NULL == b2){
				break;
			}

			b3= b2+n;
			b3=strstr(b3,tok);
			do{
				if(NULL ==b3){
					break;
				}
				mcd=euclidesExtendido(b2-b1, b3-b1);
				if(HASHNOTFOUND==get_int_by_int( ht, mcd, &value )){

					add_int_by_int( ht, mcd, 1 );
				}
				else{
					value++;
					values++;
					add_int_by_int( ht, mcd, value);
				}
				nums ++;
				b3=strstr(++b3,tok);
			}while(NULL != b3);

			b2=strstr(++b2,tok);
		}while(NULL != b2);

		str +=1;
	}
	f=fopen("kasiki.dat","w");
	for(aux=ht->bucket[i] ; i < ht->buckets ; i++ , aux=ht->bucket[i]){
		if(aux==NULL)
			continue;
		fprintf(f, "%d\t%d\n",aux->key.intValue,aux->value.intValue );
		if(aux->value.intValue * aux->key.intValue > values){
			if(aux->value.intValue * aux->key.intValue > max){
				max=aux->value.intValue * aux->key.intValue;
				ks=aux->key.intValue;
			}
		}
	}
	fclose(f);
	free(tok);
	return ks;
}
int main (int argc, char **argv){

	int long_index=0;
	char opt;
	char *text=NULL,*buff=NULL;
	FILE  * fout =stdout, *fin=NULL;
	int ngramas=0,size=0;
	int fk = 0, end=0, ic=0;
	static int fLimit=0;

	static struct option options[] = {
	    {"l",required_argument,0,'3'},
	    {"i",required_argument, 0, '6'},
	    {"o",required_argument, 0, '7'},
	    {"nl",no_argument,&fLimit, 1},
	    {0,0,0,0}
	};

	
	while ((opt = getopt_long_only(argc, argv,"3:6:7:", options, &long_index )) != -1){
		switch(opt){
			case '3':
				printf ("ngramas=  %s\n", optarg);
				fk=1;
				ngramas=atoi(optarg);
			break;
			case '6':
				printf ("i\n");
				fin=fopen (optarg, "r");
				if(fin==NULL){
					printf("\nError: el archivo %s no existe\n", optarg);
					return;
				}
			break;
			case '7':
				printf ("o\n");
				fout=fopen (optarg, "w");
				

			break;
			case'?':
				printf("%s {-l} [-i file in ] [-o file out ] [-nl ]\n", argv[0]);
			break;

		}
	}
	if (fk==0){
		printf("%s {-l} [-i file in ] [-o file out ] [-nl ]\n", argv[0] );
		return 0;
	}
	if(fin==NULL)
		fin=stdin;
	text=(char*)malloc(512);
	buff=(char*)malloc(512);
	do{
		if(fin ==stdin)
			fscanf(fin, "%s", text);
		else{
			end=fread(buff,1,512, fin);
			size+=end;
			buff = (char*) realloc(buff,end);
			text = (char*) realloc(text,size);
			strcat(text, buff);
		}
	}while(fin!=stdin&&end==512);
	text = (char*) realloc(text,size+1);
	text[size]='\n';
	//fwrite(text,1,size, fout);
	printf("kasiski empiza, si se esta usando gcc.txt puede tardar\nvarios minutos dependiendo del ordenador\n");
	printf("kasiski=%ld\n",kasiski(text,size > 0 ? size : strlen(text),ngramas));
	printf("IC empieza\n");
	ic=IC(text, size, fLimit);
	printf("ic=%d\n",ic );
	printf("calve=%s\n",ICcalve(text, size,ic));
	return 0;
	//free(text);
	//free(buff);
	//printf("end=%d size =%d strlen=%d\n",size, end, strlen(text) );
}
