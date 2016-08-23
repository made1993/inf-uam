#!/usr/bin/env Rscript
#DES application: M/M/1 queue,arrival rate 0.5,servicerate 1.0 


#if (TRUE) {
# dosim (mm1initglbls, mm1reactevnt, mm1prntrslts,10000.0, 
#     list(arrvrate=0.5,srvrate=1.0))
#}

# initializes global variables specific to this app

mm1initglbls <- function(apppars) {
  mm1glbls <<- list()
  
  #simulation parameters
  mm1glbls$arrvrate <<- apppars$arrvrate
  mm1glbls$srvrate  <<- apppars$srvrate

  # server queue, consisting of arrival times of queued job
  mm1glbls$srvq <<- vector(length=0)

  # statistics 
  mm1glbls$njobsdone <<- 0   # jobs done so far
  mm1glbls$totwait   <<- 0.0 # total wait time so far
	mm1glbls$servicetime <<- 0.0 #tiempo total de servicio
  mm1glbls$avgwaittime <<- 0.0 #tiempo medio de espera
  mm1glbls$varwaittime <<- 0.0 #varianza del tiempo de espera
  mm1glbls$queuesize <<- 0     #vector con tamaño de cola
  # set up first event, an arrival; the application-specifc data for each event 
  # will consist of its arrival time, which we need to record in order to 
  #  calculate the job's residence time in the system 

  arrvtime <- rexp(1, mm1glbls$arrvrate)
  schedevnt(arrvtime,"arrv",list(arrvtime=arrvtime))
}

mm1reactevnt <- function(head) {
  mm1glbls$queuesize <<- c(mm1glbls$queuesize, length(mm1glbls$srvq))
  if (head$evnttype == "arrv") {  #Arrival

    if (length(mm1glbls$srvq ) == 0) {
      #If server free, start service (generar exponencial, schedule event con tiempo de la exponencial)
      mm1glbls$srvq <<- head$arrvtime
			x <- rexp(1, mm1glbls$srvrate)
			mm1glbls$servicetime <<- mm1glbls$servicetime + x 
			srvdonetime <- sim$currtime + x
      schedevnt(srvdonetime, "srvdone", list(arrvtime=head$arrvtime))
			
    } else mm1glbls$srvq <<- c(mm1glbls$srvq, head$arrvtime)

    arrvtime <- sim$currtime + rexp(1, mm1glbls$arrvrate)
    schedevnt(arrvtime, "arrv", list(arrvtime=arrvtime))

  } else {#Service done

    mm1glbls$njobsdone <<- mm1glbls$njobsdone + 1
    mm1glbls$totwait <<- mm1glbls$totwait + sim$currtime - head$arrvtime
    #Tiempo Medio espera
    mm1glbls$avgwaittime <<- mediaN1(mm1glbls$avgwaittime, sim$currtime - head$arrvtime, mm1glbls$njobsdone)
    mm1glbls$varwaittime <<- varN1(mm1glbls$varwaittime, mm1glbls$avgwaittime, sim$currtime - head$arrvtime, mm1glbls$njobsdone)
    #Remove from queue
    mm1glbls$srvq <<- mm1glbls$srvq[-1]

    #More still in queue?
    
    if (length(mm1glbls$srvq) > 0) {
	  	x <- rexp(1, mm1glbls$srvrate)
			mm1glbls$servicetime <<- mm1glbls$servicetime + x
      srvdonetime <- sim$currtime + x
      schedevnt(srvdonetime, "srvdone", list(arrvtime=mm1glbls$srvq[1]))
    }

  }
}
mm1prntrslts <- function() {
  #print(mm1glbls)
  print("Numero de clientes:")
  print(mm1glbls$njobsdone)
  print("Tiempo medio de espera:")
  print(mm1glbls$avgwaittime)
  print("Varianza del tiempo de espera:")
  print(mm1glbls$varwaittime)
	print("Tiempo total servidor inactivo:")
	print(timesim - mm1glbls$servicetime)
  print("Fraccion del tiempo inactivo frente al total:")
	print(1- mm1glbls$servicetime / timesim)
  print("Tiempo de servicio por cliente")
  print(mm1glbls$servicetime / mm1glbls$njobsdone)
	
}

#################### Ejemplo PROGRAMA PRINCIPAL  
if (TRUE) {
  #timesim <<- 8*60*60
  timesim <<- 10000
  source("rurtinasRsimulacion.r")  # cargo el script de la libreria DES
  source("mediavarianza.r")
  # direct output to a file, output also send to terminal. 
  sink("kk.txt", append=FALSE, split=TRUE)

  # Run the simulation: 
  # DES application: 
     # M/M/1 queue, arrival rate 0.5, servicerate 1.0, maxsimtime=100  
  #dosim (mm1initglbls, mm1reactevnt, mm1prntrslts, maxsimtime=timesim, 
  #   list(arrvrate=1.0,srvrate=.5), dbg=FALSE)
  dosim (mm1initglbls, mm1reactevnt, mm1prntrslts, maxsimtime=timesim, 
     list(arrvrate=1/4,srvrate=1), dbg=FALSE)
  
  #dosim (mm1initglbls, mm1reactevnt, mm1prntrslts, maxsimtime=100.0, 
  #   list(arrvrate=1,srvrate=0.5), dbg=TRUE)
  # return output to the terminal
  plot(mm1glbls$queuesize[1:100], type='b')
  sink()
}



