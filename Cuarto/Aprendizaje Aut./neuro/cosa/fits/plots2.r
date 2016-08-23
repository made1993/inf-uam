

problema<-"Nums"

c1000_5000<-read.table(paste("genetico", problema, "1000-5000" , ".data", sep=""))


min<- min(min(c1000_5000[,1]), min(c1000_5000[,2]), min(c1000_5000[,3]))
max<- max(max(c1000_5000[,1]), max(c1000_5000[,2]), max(c1000_5000[,3]))


par(mfrow =c(1,1))

plot(c1000_5000[,1], ylim=c(min, max), col= "green", type ="l", xlab="epoca", ylab="fit", main="fits 1000 Nums.data")
lines(c1000_5000[,2], col= "orange")
lines(c1000_5000[,3], col= "red")

legend(x= 3500 , y=(max-min)*0.4 +min,lty=c(1,1),lwd=c(2.5,2.5),col=c("red","orange", "green"), legend=c( "peor", "medio", "mejor"))
