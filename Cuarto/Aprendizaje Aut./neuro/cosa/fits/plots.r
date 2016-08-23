

problema<-"Nums"

c50_500<-read.table(paste("genetico", problema, "50-500" , ".data", sep=""))
c100_500<-read.table(paste("genetico", problema, "100-500" , ".data", sep=""))
c200_500<-read.table(paste("genetico", problema, "200-500" , ".data", sep=""))


min<- min(min(c50_500[,1]), min(c100_500[,1]), min(c200_500[,1]))
max<- max(max(c50_500[,1]), max(c100_500[,1]), max(c200_500[,1]))


par(mfrow =c(3,1))

plot(c50_500[,1], ylim=c(min, max), col= "red", type ="l", xlab="epoca", ylab="fit", main="mejor fit Nums.data")
lines(c100_500[,1], col= "orange")
lines(c200_500[,1], col= "green")

legend(x= 400 , y=(max-min)*0.4 +min,lty=c(1,1),lwd=c(2.5,2.5),col=c("red","orange", "green"), legend=c( "50_500", "100_500", "200_500"))


min<- min(min(c50_500[,2]), min(c100_500[,2]), min(c200_500[,2]))
max<- max(max(c50_500[,2]), max(c100_500[,2]), max(c200_500[,2]))

plot(c50_500[,2], ylim=c(min, max), col= "red", type ="l", xlab="epoca", ylab="fit", main="medio fit Nums.data")
lines(c100_500[,2], col= "orange")
lines(c200_500[,2], col= "green")
legend(x= 400 , y=(max-min)*0.4 +min,lty=c(1,1),lwd=c(2.5,2.5),col=c("red","orange", "green"), legend=c( "50_500", "100_500", "200_500"))


min<- min(min(c50_500[,3]), min(c100_500[,3]), min(c200_500[,3]))
max<- max(max(c50_500[,3]), max(c100_500[,3]), max(c200_500[,3]))

plot(c50_500[,3], ylim=c(min, max), col= "red", type ="l", xlab="epoca", ylab="fit", main=" peor fit Nums.data")
lines(c100_500[,3], col= "orange")
lines(c200_500[,3], col= "green")
legend(x= 400 , y=(max-min)*0.4 +min,lty=c(1,1),lwd=c(2.5,2.5),col=c("red","orange", "green"), legend=c( "50_500", "100_500", "200_500"))
