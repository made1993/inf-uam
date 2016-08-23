#!/usr/bin/env Rscript

# Proyecto Simulacion - Procesos infecciosos.
# Mario Garcia
# Alberto Cabello
# 2015-2016

# ppdlid(x,1, 1.65)
# pnorm(x, 13,10)

library("stpp")
library("poweRlaw")
#si hay algun problema con las librerias ejectuar
# install.packages("...") con el nombre de la libreria que falle
source("ficheros.r")



#parametros de configuracion

dbg <<- FALSE


#Funciones Auxiliares

dyn.load("dist.so")
dist2 <- function(pobl) {
	 mx <- matrix(0, nrow(pobl), nrow(pobl))
	 result <- .C("dist", nrow=nrow(pobl), ncol=nrow(pobl), matrix=as.double(mx), pobl=as.double(pobl))
	out <- matrix(result$matrix, nrow(pobl), nrow(pobl))
	return(out)
 }


infeccionAleatoria <- function (poblacion)  {
		
	poblacion[sample(1:nrow(poblacion), 1),3] <- 1
	poblacion
}

getMuertos <- function(pobl) {
	subset(pobl, pobl[,3] ==2)
}
countMuertos <- function(pobl){
	nrow(getMuertos(pobl))
}


getInfectados <- function(pobl) {
	subset(pobl, pobl[,3] ==1)
}
countInfect <- function(pobl){
	nrow(getInfectados(pobl))
}

dist <- function (pobl){
	x<- matrix(0, nrow=nrow(pobl), ncol=nrow(pobl))
	for (i in 1:nrow(pobl)){
		for (j in i:nrow(pobl)){
			d <- dEucl(pobl[i,], pobl[j,])
			x[i,j]=d
			x[j,i]=d
		}
	}
	return (x)
}

	#d_E(P_1,P_2)=\sqrt{(x_2-x_1)^2+(y_2-y_1)^2}
dEucl <- function (a, b) {
	sqrt( (a[1]-b[1])**2 + (a[2]-b[2])**2 )
	
}

#Funciones graficas

plotGraficas <- function(){
	par(mfrow = c(1, 1))
	print(c("pasa de", countInfect(pIni)))
	print(c("a", countInfect(pFin)))
	plot(pIni[,1], y=pIni[,2], ylab="eje y", xlab="eje x", main = "Mapa")
	points(pFin[,1:2], col = 'green')
	points(getInfectados(pFin)[,1], y=getInfectados(pFin)[,2], col ='red')
	points(getMuertos(pFin)[,1], y=getMuertos(pFin)[,2], col = 'black')
	points(getInfectados(pIni)[,1], y=getInfectados(pIni)[,2], col = 'blue', pch = 24, cex = 1.5, lwd = 2)
	

}
plotGraficas2 <- function(){
	par(mfrow = c(2,1))
	plot(nInf, type='l', col='red', main='Infectados en cada instante', xlab="epoca")
	plot(nInfT, type='l', col='blue', main='Aumento de infectados en cada instante', xlab="epoca")


	
}

plotGraficas3 <- function(){
	par(mfrow = c(2,1))
	plot(nMrt, type='l', col='red', main='Muertos totales en cada instante', xlab="epoca")
	plot(nMrtT, type='l', col='blue', main='Aumento de muertos en cada instante', xlab="epoca")

	
}

plotGraficas4 <- function (){
	par(mfrow = c(1,1))
	plot(totalInfecciones, type='l', col='red', main='Evolucion de infectados', xlab="epoca")
	
}

plotGraficas5 <- function (){
	par(mfrow = c(1,1))
	plot(totalInfecciones,ylim=c(0, nrow(pIni)),  type='l', col='red', main='Evolucion de la poblacion', xlab="epoca", ylab ="individuos")
	lines(nMrt, type='l', col='black')
	lines(nInf, type='l', col='orange')
	lines(nrow(pIni)-(nInf+nMrt), type='l', col='green')
	legend(length(totalInfecciones)*0.5, nrow(pIni)*0.2,col=c("red", "black", "orange","green"),lty=c(1,1),lwd=c(2.5,2.5), legend = c("muertos e infectados","muertos", "infectados", "sanos"))

}


animacionMuertos <- function (t){
	
	 animation(tablademuertos, runtime=t)
}


animacionInfectados <- function (t){
	
	 animation(tablademalitos, runtime=t)
}
#Funciones de probabilidad
rlesc <- function(n, min, max, tasa){
	x <- seq(min,max, by = (max-min)/n)
	x <-(tasa-1)/min*(x/min)**(-tasa)
	return (x)	


}


#Funciones de muerte
#
#	Estas funciones cumplen el siguiente modelo de funcion
#
#	function(x, t)
#
#	x: posicion del individuo x que se va analizar
#	en la poblacion
#
#	t: momento actual
#  
# x: c(x, y, estado)

muerte1 <- function (x, t){
	if(x[4]-t>4){
		return (1)
	}	
	return (0)
}

muerte2 <- function(x, t){
	return(0)
}
muerte3 <- function (x, t){
	return (ppldis(t-x[5],0, 1.1))	

}

muerte4 <- function (x, t){
	return (pexp(t-x[5], 1))	
}

muerte5 <- function (x, t){
	return (dnorm(t-x[5], 1,2))	
}
muerte6 <- function (x, t){
	return (dexp(t-x[5], 3.5))	
}


#Funciones de infeccion
#
#	Estas funciones cumplen el siguiente modelo de funcion
#
#	function(pobl, x, t)
#
#	pobl: toda la poblacion de la simulacion
#	
#	x: posicion del individuo x que se va analizar
#	en la poblacion
#
#	t: parametro opcional????
#  
# x: c(x, y, estado)

prcInfcc3<- function(pobl, x, t){
	max<-0.0
	# Buscar sobre los ya infectados
	for( i in 1:nrow(pobl)){
		if(pobl[i,3]==1){
			max<- max(max, dpldis(distancias[x,i],0, 1.1))  
		}	
	}
	return (max)	



}
prcInfcc <-function(pobl, x, t){
	max<-0.0
	# Buscar sobre los ya infectados
	for( i in 1:nrow(pobl)){
		if(pobl[i,3]==1){
			max<- max(max, dexp(distancias[x,i], 0.1))  
		}	
	}
	return (max)	

}
prcInfcc2 <-function(pobl, x, t){
	prob<-0.0
	# Buscar sobre los ya infectados
	infcts <- getInfectados(pobl)
	for( i in 1:nrow(infcts)){
		#	print(i)
		#print(dEucl(x, c(infcts[i,1] , infcts[i,2])))
		#max<- max(max, dexp(dEucl(x, c(infcts[i,1] , infcts[i,2])), 0.1))  
		prob <- prob + 1/(100*dEucl(x, c(infcts[i,1] , infcts[i,2]))**2)
	}
	
	return (prob)	

}
prcInfcc4 <-function(pobl, x, t){
	max<-0.0
	# Buscar sobre los ya infectados
	for( i in 1:nrow(pobl)){
		if(pobl[i,3]==1){
			max<- max(max, dexp(distancias[x,i], 1))  
		}	
	}
	return (max)	

}

#Funciones para generar la poblacion

generarNHabitantes <- function(n, x, y, desvx, desvy) {
	x2 <- rnorm(n, x, desvx)
	y2 <- rnorm(n, y, desvy)
	cbind(as.numeric(x2), as.numeric(y2),0)

}

#> x <- generarPoblaciones(3, 1000, 100, 100, 0.8)
#tsCd == 
generarPoblaciones <-function (ncl, numHbt, xlimit, ylimit, tsCd){
	desv1 <- runif(ncl, 0, log(xlimit))
	desv2 <- runif(ncl, 0, log(ylimit))
	hs <- c()
	for (i in 1:ncl){
		m1<- runif(1, 1, xlimit)
		m2<- runif(1, 1, ylimit)

		ind<-numHbt*((desv1[i]+desv2[i])/(sum(desv1)+sum(desv2)))*tsCd
		p<- generarNHabitantes(ind, m1, m2, desv1[i], desv2[i])
		hs<- rbind(hs, p)
	}
	hs <- cbind(hs, 1 : nrow(hs))
	maxX<-max(hs[, 1])
	minX<-min(hs[, 1])
	maxY<-max(hs[, 2])
	minY<-min(hs[, 2])
	for (i in 1:(numHbt- nrow(hs))  ){
		x<- runif(1, minX, maxX)
		y<- runif(1, minY, maxY)
		hs<- rbind(hs, c(x, y, 0, 1+nrow(hs)))
	}
	hs <- cbind(hs, rep(0, nrow(hs)))
	return (hs)

}

#Funciones para la evolucion de la simulacion
evolIndv <- function(indv, t, funcionInfeccion, funcionMuerte, pobl){

	if(indv[3]==0){
		infeccion <- funcionInfeccion(pobl, indv[4], i)
		prob <- runif(1, 0, 1)
		if(infeccion > prob) {
			if(dbg==TRUE)
				print(c("Se infecta un pobre desgraciado", t, prob))
			indv[3]=1
			
			tablademalitos<<-rbind(tablademalitos, c(indv[1:2], t))
			indv[5] <- t
		}
	}
	else if(indv[3]==1){
		muerte <- funcionMuerte( indv, t)
		prob <- runif(1, 0, 1)
		if(dbg==TRUE)
			print(c( "muerte:",muerte," prob:", prob))
	
		
		if(muerte > prob) {
			if(dbg==TRUE)
				print(c("Se muere un pobre desgraciado", t, prob))
			indv[3]=2
			tablademuertos<<-rbind(tablademuertos, c(indv[1:2], t))
		}

		

	}

	return (indv)
}
evolTemp <- function(pobl, t, funcionInfeccion, funcionMuerte){

	for (i in 1:t){
		print(paste("epoca", i, "de", t ))
		pobl <- t(apply(pobl, 1, evolIndv, i, funcionInfeccion, funcionMuerte, pobl))
		
		totalInfecciones <<- c(totalInfecciones, nrow(tablademalitos))	
		nInf <<-c(nInf, countInfect(pobl))
		nInfT <<- c(nInfT, nInf[length(nInf)]- nInf[length(nInf)-1])
		
		nMrt <<-c(nMrt, countMuertos(pobl))
		nMrtT <<- c(nMrtT, nMrt[length(nMrt)]- nMrt[length(nMrt)-1])


	}


	return(pobl)
}

#Funciones de la simulacion
nTestPob <- function(ntests) {

	pobl <- generarPoblaciones(3, 500, 50, 50, 0.5)
	for(x in 1:ntests) {
		testpob(20, prcInfcc3, muerte1, pobl)
		escribirDatosSim(paste("sim", x, sep=""))
	}
}

testpob <- function(t, funcionInfeccion, funcionMuerte, pobl) {
	totalInfecciones <<- c()
	nInf <<-c(1)
	nInfT <<- c()
	nMrt <<- c(0)
	nMrtT <<- c()
	tablademalitos<<- c()
	tablademuertos<<- c()
	x <- pobl
	distancias <<- dist2(x) 
	print("distancias entre individuos calculadas")
	y <- infeccionAleatoria(x)
	poblacion <<- x
	#plot(x)
	z <- evolTemp(y, t, funcionInfeccion, funcionMuerte)
	print("evolTmp")	
	pFin <<- z
	pIni <<- y
	#if(t>15 ){
	#	animation(tablademalitos, runtime=15)
	#}
	#else{	
	#	animation(tablademalitos, runtime=t)
	#}

	return (z)
}

testPrueba <-function(){

	testpob(20, prcInfcc3, muerte4, generarPoblaciones(3, 500, 50, 50, 0.5))

}
bateriaPruebas <-function(){

	for(i in 1:3){
		testpob(50, prcInfcc3, muerte6, generarPoblaciones(5, 2000, 200, 200, 1))
		escribirDatosSim(paste(i, "sim-MdExp-ts3.5-5-2000-200-200-1", sep=""))
	}

}


