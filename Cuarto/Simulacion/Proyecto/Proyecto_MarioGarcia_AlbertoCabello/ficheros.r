
escribirDatosSim <- function(nombre) {
	
	dir.create("sim", showWarnings = FALSE)
	nombre <- paste("sim", nombre, sep="/") 
	dir.create(nombre, showWarnings = TRUE)
	print(c("archivo", nombre))
	write.table(tablademalitos, paste(nombre, "tablademalitos.txt", sep="/"))
	write.table(tablademuertos, paste(nombre, "tablademuertos.txt", sep="/"))
	write.table(pIni, paste(nombre, "pIni.txt", sep="/"))
	write.table(pFin, paste(nombre, "pFin.txt", sep="/"))

	write(totalInfecciones, paste(nombre, "totalInfecciones.txt", sep="/"))
	write(nInf, paste(nombre, "nInf.txt", sep="/"))
	write(nInfT, paste(nombre, "nInfT.txt", sep="/"))
	write(nMrt, paste(nombre, "nMrt.txt", sep="/"))
	write(nMrtT, paste(nombre, "nMrtT.txt", sep="/"))
} 

leerDatosSim <- function(nombre) {
	
	nombre <- paste("sim", nombre, sep="/") 
	x1 <- read.table(paste(nombre, "tablademalitos.txt", sep="/"))
	x2 <- read.table(paste(nombre, "tablademuertos.txt", sep="/"))
	x3 <- read.table(paste(nombre, "pIni.txt", sep="/"))
	x4 <- read.table(paste(nombre, "pFin.txt", sep="/"))
	
	tablademalitos <<- c()
 	tablademuertos <<- c()
	pIni <<- c()
	pFin <<- c()


	for(i in 1:nrow(x3)){
		pIni <<- rbind(pIni, as.numeric(x3[i,]))
 		pFin <<- rbind(pFin, as.numeric(x4[i,]))

		if(i<=nrow(x1)){
			tablademalitos <<- rbind(tablademalitos, as.numeric(x1[i,]))
		}
		if(i<=nrow(x2)){
			tablademuertos <<- rbind(tablademuertos, as.numeric(x2[i,]))
		}

	}
	
	totalInfecciones <<- scan(paste(nombre, "totalInfecciones.txt", sep="/"), quiet=TRUE)
	nInf <<- scan(paste(nombre, "nInf.txt", sep="/"), quiet=TRUE)
	nInfT <<- scan(paste(nombre, "nInfT.txt", sep="/"), quiet=TRUE)
	nMrt <<- scan(paste(nombre, "nMrt.txt", sep="/"), quiet=TRUE)
	nMrtT <<- scan(paste(nombre, "nMrtT.txt", sep="/"), quiet=TRUE)
} 


plotGraficas6 <- function(){
	par(mfrow = c(2,2))
	min<- 1
	max<-10
	fAfectados<- c()
	fSanos<- c()
	fMuertos<- c()
	for (i in min:max){
		print ("cosas")
		leerDatosSim(paste(i, "sim-IMaxdpldi-min0-alpha1.1-3-MpExp-ts1-3-1500-150-150-0.7", sep=""))
		fAfectados<-c(fAfectados, totalInfecciones[length(totalInfecciones)])
		fMuertos<-c(fMuertos, nMrt[length(nMrt)])
		fSanos<-c(fSanos, nrow(pIni)- totalInfecciones[length(totalInfecciones)])
	}
	boxplot(fAfectados)
	title("individuos afectados")
	boxplot(fMuertos)
	title("individuos muertos")
	boxplot(fSanos)
	title("individuos sanos")
}