
means<-c()
for (i in seq(1, 15,by =1)){
	x<- scan(paste("error", i, ".data", sep =""))
	means<-rbind(means, c(i,mean(x)))

}
