#!/usr/bin/env Rscript
#Alberto Cabello y Mario Garcia
#ejercicio 1
x <-rnorm(1000,100,10)
#ejercicio 2
hist(x)

#ejercicio 3
desviacion <- function(x) {
	sqrt(var(x))
}
desviacion(x)

media <- function(x) {
	mean(x)
}

media(x)

percentil <- function(vector, percentil){
	quantile(vector, percentil)
}

percentil(x, 0.7)

mediana <- function(x){
	median(x)
}

mediana(x)

#ejercicio 5

ks.test(x,pnorm)

#ejercicio 6

chisq.test(x)

#ejercicio 8
nNormales<- function (tipo,N=10, n=1000){
	if(tipo==1){
		return (apply(matrix(0,N,n),1,rnorm))
	}
	if(tipo==2){
		return (as.list(data.frame(apply(matrix(0,N,n),1,rnorm))))
	}
	if(tipo==3){
		return (data.frame(apply(matrix(0,N,n),1,rnorm)))
	}
}
y1 <-nNormales(1,5,5)
y1

y2 <- nNormales(2,5,5)
y2

y3 <- nNormales(3,5,5)
y3

#ejercicio 8
y<- nNormales(1)
boxplot(y)

#ejercico 9

apply(y,2,mean)

apply(y,2,median)
