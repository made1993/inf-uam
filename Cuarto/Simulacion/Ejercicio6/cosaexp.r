
#!/usr/bin/
func <- function (muestra){

	suma <- sum(muestra)
	suma <- suma/length(muestra)
	return(1/suma)

}


x <-rexp(100,5)

print(func(x))