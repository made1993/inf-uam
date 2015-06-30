	make clean
	make
	array_thr=(1 2 3 4)
	array_num=(1000 2000 3000 4000 5000 6000 7000 8000 9000 10000 20000 30000 40000 50000 60000 70000 80000 90000 100000)
	rm *.dat *.txt
	mkdir tiempos
	for i in $(seq 1 2500 100001)
	do 
		echo ./pescalar_serie ${array_num[$i]}
		./pescalar_serie $i	>>serie.txt
		grep Tiempo: serie.txt>serie.dat
		echo $i >> tam.dat
	done
	paste tam.dat serie.dat > tiempos/tserie.dat
	for i in $(seq 0 1 3)
	do 
		for d in $(seq 1 2500 100001)
		do 
			echo ./pescalar_par2 ${array_thr[$i]} $d
			./pescalar_par2 ${array_thr[$i]} $d	>>par${array_thr[$i]}.txt
			grep Tiempo: par${array_thr[$i]}.txt>par${array_thr[$i]}.dat
		done
		paste tam.dat par${array_thr[$i]}.dat > tiempos/tpar${array_thr[$i]}.dat
	done
	rm *.dat *.txt
	gnuplot <<- EOF
	set term png
	set output "tiempos.png"
	plot "tiempos/tserie.dat" u 1:3 w l ,"tiempos/tpar1.dat" u 1:3 w l , "tiempos/tpar2.dat" u 1:3 w l, "tiempos/tpar3.dat" u 1:3 w l, "tiempos/tpar4.dat" u 1:3 w l

	f(x)=a*x*100/b
	fit f(x) 'tiempos/tserie.dat' u 1:3 via a,b
	f1(x)=a1*x*100/b1
	fit f1(x) 'tiempos/tpar1.dat' u 1:3 via a1,b1
	f2(x)=a2*x*100/b2
	fit f2(x) 'tiempos/tpar2.dat' u 1:3 via a2,b2
	f3(x)=a3*x*100/b3
	fit f3(x) 'tiempos/tpar3.dat' u 1:3 via a3,b3
	f4(x)=a4*x*100/b4
	fit f4(x) 'tiempos/tpar4.dat' u 1:3 via a4,b4
	g1(x)=f(x)/f1(x)	
	g2(x)=f(x)/f2(x)
	g3(x)=f(x)/f3(x)
	g4(x)=f(x)/f4(x)
	set xrange [0:100001]
	set term png
	set output "aceleracion.png"
	plot g1(x),g2(x),g3(x),g4(x)
	EOF