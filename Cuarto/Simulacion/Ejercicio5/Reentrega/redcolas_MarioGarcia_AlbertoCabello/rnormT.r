#!/usr/bin/env Rscript

# genera valores de la normal pero truncados para
# que no de valores negativos
#
# Entrada:
#
#	n: numero de repeticiones
#	mean: media de la dsitribucion
#	desv: desviacion tipica de la muestra
#	
# Retorno:
#
#	Devuelve los valores de la distribucion normal
#

rnormT <- function (n, mean,desv=1){

	ret <- c()
	for( x in 1:n){
		ret <- c(ret,rnorm(1, mean,desv))
		while (0>ret[x]){
			ret <- ret[-x] 
			ret <- c(ret,rnorm(1, mean,desv))
		}
	}
	return (ret)
}

rnormT(1000,5,20)
warnings()