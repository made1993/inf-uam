#!/usr/bin/env Rscript

x = read.table("afits500_100.data")
plot ( x[, "V1"], type ="l",ylim=c(0,1), xlim=c(0, length( x[,"V1"] )), xlab="generaciones", ylab="fit", main="fits 500_100")
lines( x[, "V1"], col="blue")
lines( x[, "V2"], col="red")
lines( x[, "V3"], col =" green")
legend(x=  6 * length( x[,"V1"] ) / 10 , y=0.25,lty=c(1,1),lwd=c(2.5,2.5),col=c("blue","red", "green"), legend=c("Mejor Fitness", "Medio Fitness", "Peor Fitness"))


