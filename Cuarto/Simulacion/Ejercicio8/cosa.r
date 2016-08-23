

kstest <- function (x,y,args,gl=0.05){
	val<-list(c(0.01,1.63),c(0.05,1.36),c(0.1,1.22),c(0.15,1.14),c(0.2,1.07))
	h<- hist(x, plot=FALSE,breaks= 1/gl)
	h$density<-h$density/2
	max<-0.0
	for(i in 1:(length(h$density))){
		max <- max(max, abs(h$counts[i]/length(x) - (y(c(args, h$breaks[i+1])) -y(c(args, h$breaks[i])))))
	}


	pos<-0
	for(i in 1:length(val)){
		if(val[[i]][1]==gl){
			pos<-i
		}
	}
	if(max>val[[pos]][2]/sqrt(length(x))){
		print ("hipotesis incorrecta")
		print (paste("D:", max))
		print (paste("Dteorico:", val[[pos]][2]/sqrt(length(x))))
	}
	else{
		print ("no se puede descartar la hipotesis")
		print (paste("D:", max))
		print (paste("p-value:", val[[pos]][2]/sqrt(length(x))))
	}

}

Fexp<- function(x){
	1-exp(-x[2]*x[1])
}

