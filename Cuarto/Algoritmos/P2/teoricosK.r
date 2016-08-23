teoricoK <- function (v, sparse=0.25){
	
	v**2*sparse*log(v)*2 + v*2 + v**2*sparse *3
}

teoricoB <- function (v,sparse=0.25){
	v**2*sparse*log(v)+v**2*sparse
}
x<-c()
y<- c()
for( i in 1:100){
	x<-rbind(x,c(i, teoricoK(i)))
	y<-rbind(y,c(i, teoricoB(i))) 
}


x

plot(x,col="blue", type="l")
lines(y,col="purple", type="l") 

