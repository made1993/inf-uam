
rm *.dat

for i in $(seq 1 32 1025 )
	do 
		echo $i >>tam.txt
		echo ./mult $i
		./mult $i |grep Tiempo  >>mult.txt
		echo ./mult1 2 $i
		./mult1 2 $i |grep Tiempo >>mult1.txt
	done

paste tam.txt mult.txt > mult.dat
paste tam.txt mult1.txt > mult1.dat 
rm *.txt
gnuplot <<- EOF
	set term png
	set output "tiempos.png"
	plot "mult.dat" u 1:3 w l ,"mult1.dat" u 1:3 w l 
	f(x)=(a*x**2)*100/b
	fit f(x) 'mult.dat' u 1:3 via a,b
	f1(x)=(a1*x**2)*100/b1
	fit f1(x) 'mult1.dat' u 1:3 via a1,b1
	set output "speedup.png"
	g(x)=f(x)/f1(x)
	plot g(x) 
EOF
