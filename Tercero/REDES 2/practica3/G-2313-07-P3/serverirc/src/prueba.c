#include <stdio.h>
#include <stdlib.h>
#include "../includes/G-2313-07-P3demoniochat.h"
int main(int argc, char** argv){

	int irc;
	int ircssl;
	
	irc = fork();
	if(irc == -1){
		return ;
	}else if(irc == 0){
		demonioChat();
		while(1);
	}

	ircssl = fork();
	if(ircssl == -1){
		return ;
	}else if(ircssl == 0){
		demonioChat(); // igual deberia ser uno diferente
		while(1);
	}
	

}