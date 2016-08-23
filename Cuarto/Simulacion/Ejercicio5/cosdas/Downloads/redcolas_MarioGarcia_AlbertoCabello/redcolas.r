

#DES application: M/M/1 queue,arrival rate 0.5,servicerate 1.0 


# initializes global variables specific to this app

mm1initglbls <- function(apppars) {
  mm1glbls <<- list()
  
  #simulation parameters
  mm1glbls$oldpercent <<- 0.4
  #Ventanilla, distrib normal, media 5 desviacion tipica 5
  mm1glbls$srv1avg <<- 5
  mm1glbls$srv1dsv <<- 5
  #Vehiculos viejos exp ratio
  mm1glbls$srv2rate <<- 0.1
  #Todos los vehículos, normal truncada, cambian parametros segun clientes
  mm1glbls$srv3avg <<- 12
  mm1glbls$srv3dsv <<- 3
  mm1glbls$srv3limiteclientes <<- 5
  mm1glbls$srv3avglimite <<- 8
  mm1glbls$srv3dsvlimite <<- 2

  #Entrada a ventanilla. Poisson
  mm1glbls$arrvrate <<- apppars$arrvrate #10
  mm1glbls$srvrate  <<- apppars$srvrate  #10
 
  # server queue, consisting of arrival times of queued job
  mm1glbls$srvq <<- vector(length=0)
  mm1glbls$oldq <<- vector(length=0)
  mm1glbls$newq <<- vector(length=0)


  # statistics 
  mm1glbls$q1size <<- 0
  mm1glbls$q2size <<- 0
  mm1glbls$q3size <<- 0
  
  mm1glbls$njobsdone <<- 0   # jobs done so far
  mm1glbls$totwait   <<- 0.0 # total wait time so far
  mm1glbls$acabados <<- 0
  # set up first event, an arrival; the application-specifc data for each event 
  # will consist of its arrival time, which we need to record in order to 
  #  calculate the job's residence time in the system 
 
  # First client arrivel time
  #arrvtime <- rpois(1, mm1glbls$arrvrate)
  #schedevnt(arrvtime,"arrv",list(arrvtime=arrvtime))
  schednewarrv()
}
#Generate arrival to server 1
schednewarrv <- function() {

  arrvtime <- sim$currtime + rpois(1,mm1glbls$arrvrate)
  schedevnt(arrvtime,"arrv",list(arrvtime=arrvtime))
}

#Generate arrival to servers 2&3 
schednewarrv2 <- function(head) {


  arrvtime <- sim$currtime + rpois(1,mm1glbls$arrvrate)

  #Generate number 0-1, test if greater than oldpercent
  if(runif(1, 0, 1) > mm1glbls$oldpercent) { #NEW
    #print("Generado new")
    type <- "new"
    # if server free, start service, else add to queue (added to queue
    # even if empty, for convenience)
    if(length(mm1glbls$newq) == 0) {
      mm1glbls$newq <<- head$arrvtime
      srvdonetime <- sim$currtime + timeserver3()
      schedevnt(srvdonetime, "srvdone3", list(arrvtime=head$arrvtime))
    }
    else mm1glbls$newq <<- c(mm1glbls$newq, head$arrvtime)
  }
  else { #OLD
    type <- "old"
    #print("Generado old")
    # if server free, start service, else add to queue (added to queue
    # even if empty, for convenience)
    if(length(mm1glbls$oldq) == 0) {
      mm1glbls$oldq <<- head$arrvtime
      srvdonetime <- sim$currtime + rexp(1, mm1glbls$srv2rate)
      schedevnt(srvdonetime, "srvdone2", list(arrvtime=head$arrvtime))
    }
    else mm1glbls$oldq <<- c(mm1glbls$oldq, head$arrvtime)
    #print(type)
  }

}

#Calculate time spent in server 3
timeserver3 <- function() {
  #print(length(mm1glbls$newq))
  if(length(mm1glbls$newq) >= mm1glbls$srv3limiteclientes) { #Si supera el limite de clientes, mas rapido
    #print("limite de clientes superado")
    #print(length(mm1glbls$newq))
    time <- rnormT(1, mm1glbls$srv3avglimite, mm1glbls$srv3dsvlimite)
  } else {
    time <- rnormT(1, mm1glbls$srv3avg, mm1glbls$srv3dsv)
  }
  
  return(time)
}
#React to arrival to server #1
reactarrival <- function(head) {
  # if server free, start service, else add to queue (added to queue
  # even if empty, for convenience)

  if (length(mm1glbls$srvq) == 0) {
    mm1glbls$srvq <<- head$arrvtime
    srvdonetime <- sim$currtime + rnormT(1,mm1glbls$srv1avg, mm1glbls$srv1dsv)
    schedevnt(srvdonetime,"srvdone",list(arrvtime=head$arrvtime))
  } 
  else mm1glbls$srvq <<- c(mm1glbls$srvq,head$arrvtime)
  # generate next arrival
  schednewarrv()
}

#React to srvdone
reactdone1 <- function(head) {
  # process job that just finished do accounting
  mm1glbls$njobsdone <<- mm1glbls$njobsdone + 1
  mm1glbls$totwait <<- mm1glbls$totwait + sim$currtime - head$arrvtime

  # remove from queue
  mm1glbls$srvq <<- mm1glbls$srvq[-1]

  #Generate arrival to next step
  schednewarrv2(head)

  # more still in the queue?
  if (length(mm1glbls$srvq) > 0) {
  # schedule new service
    srvdonetime <- sim$currtime + rexp(1,mm1glbls$srvrate)
    schedevnt(srvdonetime,"srvdone",list(arrvtime=mm1glbls$srvq[1]))
  }
}
#React to srvdone2
reactdone2 <- function(head) {

  #print(length(mm1glbls$oldq))
  #remove from queue
  mm1glbls$oldq <<- mm1glbls$oldq[-1]
  
  #Generate arrival to next step

  if(length(mm1glbls$newq) == 0) {
    mm1glbls$newq <<- head$arrvtime
    srvdonetime <- sim$currtime + timeserver3()
    schedevnt(srvdonetime, "srvdone3", list(arrvtime=head$arrvtime))
  }
  else mm1glbls$newq <<- c(mm1glbls$newq, head$arrvtime)

  #More still in oldqueue? 
  if (length(mm1glbls$oldq) > 0) {
  # schedule new service
    srvdonetime <- sim$currtime + rexp(1,mm1glbls$srv2rate)
    schedevnt(srvdonetime,"srvdone2",list(arrvtime=mm1glbls$oldq[1]))
  }  
}
#React to srvdone3
reactdone3 <- function(head) {
  #Salir del servidor 3. Terminar.
  #remove from queue
  mm1glbls$newq <<- mm1glbls$newq[-1]

  #More still in newqueue? 
  if (length(mm1glbls$newq) > 0) {
  # schedule new service
    srvdonetime <- sim$currtime + timeserver3()
    schedevnt(srvdonetime,"srvdone3",list(arrvtime=mm1glbls$newq[1]))
  }  

  #Statistics
  mm1glbls$acabados <<- mm1glbls$acabados + 1
}

#application-specific event processing function called by dosim() in the general DES library
# arguments:
#   head: event to be proccesed 
				
mm1reactevnt <- function(head) {

  #if(length(mm1glbls$newq) >=5) {
  #  print(length(mm1glbls$newq))
  #}
  mm1glbls$q1size <<- c(mm1glbls$q1size, length(mm1glbls$srvq))
  mm1glbls$q2size <<- c(mm1glbls$q2size, length(mm1glbls$oldq))
  mm1glbls$q3size <<- c(mm1glbls$q3size, length(mm1glbls$newq))
  if (head$evnttype == "arrv") { # arrival event
    reactarrival(head)
  } 
  else if (head$evnttype == "srvdone"){ # service done event (Ventanilla 1)
    reactdone1(head)
  }
  else if (head$evnttype == "srvdone2"){ #srvdone de servidor viejos
    reactdone2(head)
  }
  else if (head$evnttype == "srvdone3"){#Srvdones nuevos
    reactdone3(head)
  }
  printdebug(head);
}

mm1prntrslts <- function() {
  #print("mean wait:")
  #print(mm1glbls$totwait/mm1glbls$njobsdone)
  print('Clientes atendidos')
  print(mm1glbls$acabados)
  graficos1()
}
graficos1 <- function() {

  par(mfrow=c(3,2))
  plot(mm1glbls$q1size, type='h', main='Evolución de cola en la entrada', xlab='Tiempo', ylab='Tamaño de cola1')
  plot(density(mm1glbls$q1size),main="Funcion de densidad tamaño de cola1")
  plot(mm1glbls$q2size, type='h', main='Evolución de cola en servidor viejos', xlab='Tiempo', ylab='Tamaño de cola2')
  plot(density(mm1glbls$q2size),main="Funcion de densidad tamaño de cola2")
  plot(mm1glbls$q3size, type='h', main='Evolución de cola en servidor general',xlab='Tiempo', ylab='Tamaño de cola3')
  plot(density(mm1glbls$q2size),main="Funcion de densidad tamaño de cola3")


}
printdebug <- function(head) {
  debug <- TRUE
  if(debug == TRUE){
    if (head$evnttype == "arrv") { # arrival event
      print("Arrival servidor 1")
      
      print(length(mm1glbls$srvq))
      print(length(mm1glbls$oldq))
      print(length(mm1glbls$newq))
      
      print(mm1glbls$srvq)
      print(mm1glbls$oldq)
      print(mm1glbls$newq)
    } 
    else if (head$evnttype == "srvdone"){ # service done event (Ventanilla 1)
      print("Salir servidor 1")
      print(length(mm1glbls$srvq))
      print(length(mm1glbls$oldq))
      print(length(mm1glbls$newq))
      print(mm1glbls$srvq)
      print(mm1glbls$oldq)
      print(mm1glbls$newq)
      
    }
    else if (head$evnttype == "srvdone2"){ #srvdone de servidor viejos

      print("Salir servidor 2, entrar 3")

      print(length(mm1glbls$srvq))
      print(length(mm1glbls$oldq))
      print(length(mm1glbls$newq))
      print(mm1glbls$srvq)
      print(mm1glbls$oldq)
      print(mm1glbls$newq)
      
    }
    else if (head$evnttype == "srvdone3"){
      print("Procesando al final3")

      print(length(mm1glbls$srvq))
      print(length(mm1glbls$oldq))
      print(length(mm1glbls$newq))
      print(mm1glbls$srvq)
      print(mm1glbls$oldq)
      print(mm1glbls$newq)
      
    }
  }
}
#################### Ejemplo PROGRAMA PRINCIPAL  
if (TRUE) {
 source("rurtinasRsimulacion.r")  # cargo el script de la libreria DES
 source("rnormT.r")
 # direct output to a file, output also send to terminal. 
 sink("kk.txt", append=FALSE, split=TRUE)

 # Run the simulation: 
 # DES application: 
     # M/M/1 queue, arrival rate 0.5, servicerate 1.0, maxsimtime=100  
 dosim (mm1initglbls, mm1reactevnt, mm1prntrslts, maxsimtime=500.0, 
     list(arrvrate=1,srvrate=10.0), dbg=FALSE)

 # return output to the terminal
 sink()
}

