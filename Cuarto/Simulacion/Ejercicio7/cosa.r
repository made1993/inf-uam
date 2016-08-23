ml <- function (x){
	sum(x)/length(x)
}
mu <- function (x, var,media){
	res<-1/(2*3.14*var)**(length(x)/2)
	res<- res*exp(-1/(2*var)*sum(x-media)**2)
	res<- res*1/(sqrt(2*3.14))*exp(-(media-1)**2/(2*3.14))
}
mn<- function(x, n,var){
	(var)/(n+var)+(n*ml(x))/(n+var)
}
varn<- function(n,var){
	1/(1+n/var)
}

p<- function(x,var,media){
	varn<-varn(length(x),var)
	print(varn)
	mn<-mn(x,length(x),var)
	print(mn)
	res<- 1/sqrt(2*3.14*varn)
	res<-res*exp(-(media-mn)**2/(2*varn))
}