
means<-c()
for (i in seq(10, 200,by =5)){
	x<- scan(paste("error", i, ".data", sep =""))
	means<-rbind(means, c(i,mean(x)))

}
x<- scan("kmeansweka.txt")
means2<-c()
for (i in seq(1,11, by=1)){
	means2<- rbind(means2, c(i+9, x[i]))
	
}

par(mfrow =c(2,2))
plot(means, type="l", col="blue", xlab="Numero de clusters", ylab="error medio", ylim=c(0,1), main="error")
lines(means2, col="red")
legend(x= 120 , y=0.75,lty=c(1,1),lwd=c(2.5,2.5),col=c("blue","red"), legend=c( "nosotros", "weka"))

plot(means, type="l", col="blue", xlab="Numero de clusters", ylab="error medio", ylim=c(0,1), xlim=c(10,30), main="error")
lines(means2, col="red")
legend(x= 22 , y=0.75,lty=c(1,1),lwd=c(2.5,2.5),col=c("blue","red"), legend=c( "nosotros", "weka"))
