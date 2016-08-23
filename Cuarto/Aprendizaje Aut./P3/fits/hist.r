#!/usr/bin/env Rscript

col <- "V1"
lname <- "Mejor"
gens <- "1000"
png(paste ("f",lname,gens,".png" ), width = 720, height = 720)	

x1 <- read.table("afits10_1000.data")
x2 <- read.table("bfits10_1000.data")
x3 <- read.table("cfits10_1000.data")
y1 <- read.table("afits100_1000.data")
y2 <- read.table("bfits100_1000.data")
y3 <- read.table("cfits100_1000.data")
z1 <- read.table("afits500_1000.data")
z2 <- read.table("bfits500_1000.data")
z3 <- read.table("cfits500_1000.data")
plot ( x1[, col], type ="l",ylim=c(0,1), xlim=c(0, length( x1[,"V1"] )), xlab="generaciones", ylab="fit", main=paste("fitness", lname))
lines( x1[, col], col="blue")
lines( x2[, col], col="blue")
lines( x3[, col], col="blue")
lines( y1[, col], col="red")
lines( y2[, col], col="red")
lines( y3[, col], col="red")
lines( z1[, col], col =" green")
lines( z2[, col], col =" green")
lines( z3[, col], col =" green")

lname1 <- paste(lname, "Fitness 10_", gens)
lname2 <- paste(lname, "Fitness 100_", gens)
lname3 <- paste(lname, "Fitness 500_", gens)
legend(x=  5 * length( x1[,col] ) / 10 , y=0.25,lty=c(1,1),lwd=c(2.5,2.5),col=c("blue","red", "green"), legend=c( lname1, lname2, lname3))
rect(1, 5, 3, 7, col = "white")
dev.off()
