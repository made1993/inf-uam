#!/usr/bin/env Rscript

col <- "V1"
lname <- "Mejor"
gens <- "1000"
png("time.png" , width = 720, height = 720)	

x1 <- read.table("t100000.data")
#x2 <- read.table("t48_42.data")
#x3 <- read.table("t48_43.data")
#x4 <- read.table("t49_41.data")
#x5 <- read.table("t49_42.data")
#x6 <- read.table("t49_43.data")
#x7 <- read.table("t50_41.data")
#x8 <- read.table("t50_42.data")
#x9 <- read.table("t50_43.data")


plot ( x1[, col], ylim = c(0, max(max(x1[, col]), max(x1[, "V2"]))), type ="l", xlab="size", ylab="time(micro S)", main="t")
lines( x1[, col], col="blue")
lines( x1[, "V2"], col="red")

lname1 <- "nosotros"
lname2 <- "gmp"
legend(x=  5 * length( x1[,col] ) / 10 , y=0.25*max(max(x1[, col]), max(x1[, "V2"]))  ,lty=c(1,1),lwd=c(2.5,2.5),col=c("blue","red"), legend=c( lname1, lname2))
rect(1, 5, 3, 7, col = "white")
dev.off()
