#!/usr/bin/env Rscript

myrexp <- function(n, lambda=1) {
	
	unif <- runif(n, 0, 1)
	unif <- -unif + 1
	unif <- log(unif)
	unif <- unif/lambda
	unif <- -unif

	return(unif)
}

tfw <- function(n){
	seq<- seq(1,n,1)
	seq<-seq**3
}

tdijs <- function(v,e){
	seq<-seq(1, v, 1)
	res<- seq*((seq+seq**2*e)*log(seq))

}

tdijsl <- function(v){
	seq<-seq(1, v, 1)
	res<- seq*((seq**2/log(seq))*log(seq))

}
mydiscreta <- function(valores, probs, n) {	

	salida <- vector(,n)
	apply(salida, 1, ftoapply, valores, probs)

	return(salida)

}

ftoapply <- function(#valores, probs) {
	){

	valores <- c(1,2,3,4)
	probs <- c(.2,.1,.4,.3)
	s <- probs[1]
	u <- runif(1,0,1)


	for( x in 1:length(valores)) {
		print(x)
		if(u <= s) {
			return(valores[x]) 		
		} else {
			s <- s + probs[x]
			u <- runif(1,0,1)
		}
	}


}
size<-50
plot(tfw(size),col="blue", type="l")
lines(tdijs(size,1),col="purple")  
lines(tdijs(size,0.25), col="red")
lines(tdijs(size,0.5), col="green")
lines(tdijs(10**2,0.75), col="black")
legend(1,10**5,lty=c(1,1),lwd=c(2.5,2.5),col=c("blue","red","green","black","purple"), legend=c("fw", "dijs/0.25", "dijs/0.50", "dijs/0.75", "dijs/1.00"))

