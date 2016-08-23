#!/usr/bin/env Rscript

# Calcula la media para el siguiente elmento de la muestra
# meanN: media en n
# xn1: nuevo dato de la muestra
# n1: numero de elementos de la media incluyendo a xn1
# devuelve el valor de la media  
mediaN1 <- function(meanN,xn1,n1){
	if(n1<=0){
		return (NaN)
	}
	else if(n1==1){
		return (n1)
	}
	else{
		meanN<- meanN*(n1-1)
		meanN<- meanN+xn1
		meanN<- meanN/(n1)
		return(meanN)	
	}
}


# Calcula la varianza para el siguiente elmento de la muestra
# varN: el valor de la varianza que teniamos
# meanN1: media en n1
# xn1: nuevo dato de la muestra
# n1: el numero de elementos
# devuelve el valor de la media
varN1 <- function(varN, meanN1, xn1, n1){
	if(n1<=0){
		return (NaN)
	}
	else if(n1 ==1){
		return (0)
	} 
	else{
		mean <- (meanN1*n1-xn1)/(n1-1)
		varN <- (varN+mean^2)*(n1-1)
		varN <- (varN+xn1^2)/n1
		varN <- varN - meanN1^2
		return(varN)
	}
}
x<- c(1,2,3,4,5)

y<- varN1 (0,1.5,0,2)
y

