

#DES application: M/M/N queue,arrival rate 0.5,servicerate 1.0 


# initializes global variables specific to this app

mm1initglbls <- function(apppars) {
  mm1glbls <<- list()
  
  #model parameters
  mm1glbls$nsrv <<-  apppars$nsrv   # servers in parallel
  mm1glbls$arrvrate <<- apppars$arrvrate
  mm1glbls$srvrate  <<- apppars$srvrate

  #system state variables
  mm1glbls$LQ <<- 0                   # queue length
  mm1glbls$LS <<- rep(0, apppars$nsrv) # server state

  # server queue, consisting of arrival times of queued job
  mm1glbls$srvq <<- vector(length=0)

  # statistics 
  mm1glbls$njobsdone <<- 0                     # jobs done so far
  mm1glbls$totwait   <<- 0                     # total waiting time in system
  mm1glbls$timeinqueue  <<- vector(length=0)   # time in queue  for each client (vector)
  mm1glbls$timeinsystem <<- vector(length=0)   # time in system for each client (vector)
  mm1glbls$srvwork <<- rep(0.0, apppars$nsrv)  # total working server time 
  mm1glbls$nqueue <<- NULL         #  Queue size evolution (clients_in_queue, time). 

  # set up first event, an arrival; the application-specifc data for each event 
  # will consist of its arrival time, which we need to record in order to 
  #  calculate the job's residence time in the system 
 
  # First client arrival time
  arrvtime <- rexp(1, mm1glbls$arrvrate)
  schedevnt(arrvtime,"arrv",list(arrvtime=arrvtime, beginfacility=NA, facility=99))
  mm1glbls$srvq <<- arrvtime
}

getServer <- function() {
  v <- 1:mm1glbls$nsrv
  y <- v[mm1glbls$LS == 0]   # get free servers
  if (length(y)>1)  return (sample(y,size=1))
  else return(y)
}

#application-specific event processing function called by dosim() in the general DES library
# arguments:
#   head: event to be proccesed 
					
 mm1reactevnt <- function(head) {
   if (head$evnttype == "arrv") arrivalEvent(head)  
   else departureEvent(head)
}

# Arrival event process function
arrivalEvent <- function(head) {

     if ( any(mm1glbls$LS < 1) ){     # any srv free?
       LS <- getServer()
       mm1glbls$LS[LS] <<- 1 
       srvdonetime <- sim$currtime + rexp(1,mm1glbls$srvrate)
       schedevnt(srvdonetime,"srvdone",list(arrvtime=head$arrvtime, beginfacility=sim$currtime, facility=LS))
       # remove from queue
       mm1glbls$srvq <<- mm1glbls$srvq[-1]
     } 
     else  mm1glbls$LQ <<- mm1glbls$LQ + 1
      	
     # generate next arrival
     arrvtime <- sim$currtime + rexp(1,mm1glbls$arrvrate)
     schedevnt(arrvtime,"arrv",list(arrvtime=arrvtime, beginfacility=NA, facility=99))
     
     # collect statistics
     mm1glbls$srvq <<- c(mm1glbls$srvq, arrvtime)     #insert in queue
     mm1glbls$nqueue <<- rbind( mm1glbls$nqueue, c(mm1glbls$LQ, head$evnttime)) # record queue size 
}

# Departure event process function
departureEvent <- function(head) {
					
     LS <- head$facility
     mm1glbls$LS[LS] <<- 0

     # remove from queue
     #mm1glbls$srvq <<- mm1glbls$srvq[-1]

     # more still in the queue?
     if ( mm1glbls$LQ > 0) {
       mm1glbls$LQ <<- mm1glbls$LQ - 1
       mm1glbls$nqueue <<- rbind( mm1glbls$nqueue, c(mm1glbls$LQ, head$evnttime))
       
       # schedule new service
       LS <- getServer()
       mm1glbls$LS[LS] <<- 1 
       srvdonetime <- sim$currtime + rexp(1,mm1glbls$srvrate)
       schedevnt(srvdonetime,"srvdone",list(arrvtime=mm1glbls$srvq[1], beginfacility=sim$currtime, facility=LS))

       # remove from queue
       mm1glbls$srvq <<- mm1glbls$srvq[-1]
     }
 
     # collect statistics: job that just finished do accounting
     mm1glbls$srvwork[LS] <<-  mm1glbls$srvwork[LS] + sim$currtime -  head$beginfacility
     mm1glbls$njobsdone <<- mm1glbls$njobsdone + 1
     mm1glbls$timeinsystem <<- c(mm1glbls$timeinsystem, sim$currtime - head$arrvtime)
     mm1glbls$timeinqueue <<-  c(mm1glbls$timeinqueue, head$beginfacility - head$arrvtime) 
     mm1glbls$totwait <<- mm1glbls$totwait + sim$currtime-head$arrvtime        
}


mm1prntrslts <- function() {
  print("mean wait:")
  print(mm1glbls$totwait/mm1glbls$njobsdone)
}


graficos1<- function() {

 old.par <- par()
 par(mfrow=c(2,2))
 # Queue size time evolution
 plot(mm1glbls$nqueue[,2], mm1glbls$nqueue[,1], type = "h", xlab="Simulation clock", ylab="Queue size")

 # Kernel Density Plot
 d <- density(mm1glbls$timeinsystem) # returns the density data
 plot(d, main="Time in system")

 
# Cumalitive distribution function
 plot(ecdf(mm1glbls$timeinsystem), pch=46, col="red", xlab="Time")
 lines(ecdf(mm1glbls$timeinqueue), pch=46, col="blue" )
 legend(quantile(mm1glbls$timeinsystem, probs=0.95), 0.6, legend=c("In System", "In Queue"), lwd=1:3, col=c("red", "blue"))
 
 par(old.par)
}


#################### Ejemplo PROGRAMA PRINCIPAL  
if (TRUE) {
 source("rurtinasRsimulacion.r")  # cargo el script de la libreria DES
 
 # direct output to a file, output also send to terminal. 
 sink("kk.txt", append=FALSE, split=TRUE)

 # Run the simulation: 
 # DES application: 
     # M/M/N queue, servers=nsrv, arrival rate = arrvrate, servicerate=srvrate, maxsimtime=1000
 rho= 0.98
 arrvrate = 1
 nsrv = 3
 srvrate = arrvrate / (nsrv*rho)  
 dosim (mm1initglbls, mm1reactevnt, mm1prntrslts, maxsimtime=1000.0, 
     list(arrvrate=arrvrate,srvrate=srvrate, nsrv=nsrv), dbg=TRUE)

 # return output to the terminal
 sink()

 # Plot figures
 graficos1()
}
