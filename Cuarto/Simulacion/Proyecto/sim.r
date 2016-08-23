library("stpp")
data("fmd")
data("northcumbria")
fmd <- as.3dpoints(fmd)

plot(fmd, s.region = northcumbria)
plot(fmd, s.region = northcumbria, pch = 19, mark = TRUE)
animation(fmd, runtime = 10, cex = 0.5, s.region = northcumbria)
# Infeccion uniforme
inf1 <- rinfec(npoints = 100, alpha = 0.1, beta = 0.6, gamma = 0.5, maxrad = c(0.075, 0.5), t.region = c(0, 50), s.distr = "uniform", t.distr = "uniform", h = "step", g = "min", recent = "all", inhibition = TRUE)
animation(inf1$xyt, cex = .8, runtime = 10)

#Infeccion poisson

h <- mse2d(as.points(fmd[, 1:2]), northcumbria, nsmse = 30, range = 3000)
h <- h$h[which.min(h$mse)]
Ls <- kernel2d(as.points(fmd[, 1:2]), northcumbria, h, nx = 50, ny = 50)
inf2 <- rinfec(npoints = 100, alpha = 4, beta = 0.6, gamma = 20, maxrad = c(12000, 20), s.region = northcumbria, t.region = c(1, 2000), s.distr = "poisson", t.distr = "uniform", h = "step", g = "min", recent = 1, lambda = Ls$z, inhibition = FALSE)
image(Ls$x, Ls$y, Ls$z, col = grey((1000:1)/1000))
polygon(northcumbria, lwd = 2)
animation(inf2$xyt, add = TRUE, cex = 0.7, runtime = 15)



# contagioso 1
inh1 <- rinter(npoints = 200, thetas = 0, deltas = 0.05, thetat = 0,deltat = 0.001, inhibition = TRUE)


# contagioso 2

cont1 <- rinter(npoints = 250, s.region = northcumbria, t.region = c(1, 200), thetas = 0, deltas = 7500, thetat = 0, deltat = 10, recent = 1, inhibition = FALSE)
plot(cont1$xyt, pch = 19, s.region = cont1$s.region, mark = TRUE, mark.col = 4)
animation(cont1$xyt, s.region = cont1$s.region, t.region = cont1$t.region, incident = "red", prevalent = "lightgreen", runtime = 15, cex = 0.8)

#contagio 3

hs <- function(d, theta, delta, mus = 0.1) {
	res <- NULL
	a <- (1 - theta)/mus
	b <- theta - a * delta
	for(i in 1:length(d)) {
		if(d[i] <= delta) res <- c(res, theta)
		if(d[i] > (delta + mus)) res <- c(res, 1)
		if(d[i] > delta & d[i] <= (delta + mus)) res <- c(res, a * d[i] + b)
	}
	return(res)
}
ht <- function(d, theta, delta, mut = 0.3) {
	res <- NULL
	a <- (1 - theta)/mut
	b <- theta - a * delta
	for(i in 1:length(d)) {
		if(d[i] <= delta) res <- c(res, theta)
		if(d[i] > (delta + mut)) res <- c(res, 1)
		if(d[i] > delta & d[i] <= (delta + mut)) res <- c(res, a * d[i] + b)
	}
return (res) 
}



d <- seq(0, 1, length = 100)
plot(d, hs(d, 0.2, 0.1, 0.1), xlab = "", ylab = "", type = "l", ylim = c(0, 1), lwd = 2, las = 1)
lines(d, ht(d, 0.1, 0.05, 0.3), col = 2, lwd = 2)
legend("bottomright", col = 1:2, lty = 1, lwd = 2, bty = "n", cex = 2, legend = c(expression(h[s]), expression(h[t])))
inh2 <- rinter(npoints = 100, hs = hs, gs = "min", thetas = 0.2, deltas = 0.1, ht = ht, gt = "min", thetat = 0.1, deltat = 0.05, inhibition = TRUE)
animation(inh2$xyt, runtime = 15, cex = 0.8)

