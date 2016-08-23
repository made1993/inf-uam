
png("idf.png" , width = 720, height = 720)

f1k<- read.table("salida1K.data")
f10k<- read.table("salida10K.data")

plot(log2(10000/sort(f10k[,3], decreasing= TRUE)), type="l", col="blue", main="idf", ylab="prob", xlab="palabra")
lines(log2(1000/sort(f1k[,3], decreasing=TRUE)), col="red")
legend(x=  7 * length(f10k[,3]) / 10 , y= log2(10000/sort(f10k[nrow(f10k),3], decreasing=TRUE))*0.5, lty=c(1,1),lwd=c(2.5,2.5),col=c("blue","red"), legend=c( "10k", "1k"))
rect(1, 5, 3, 7, col = "white")
dev.off()
