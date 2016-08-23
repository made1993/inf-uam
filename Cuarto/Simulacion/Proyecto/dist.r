# Wrapper function to invoke helloC with two arguments
dyn.load("prueba.so")
dist <- function(pobl) {
	mx <- matrix(0, nrow(pobl), nrow(pobl))
	result <- .C("dist", nrow=nrow(pobl), ncol=nrow(pobl), matrix=as.double(mx), pobl=as.double(pobl))
	return(result)
}

x<-matrix(seq(1,30), 10, 3)

y<-dist(x)

d<- matrix(y$matrix, 10, 10)
