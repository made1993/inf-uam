#script.sh
datos="datos.dat"
minsz=1
maxsz=2049
incr=16
rm *.dat
for i in $(seq $minsz $incr $maxsz)
do
        printf "$i\t">>datos.dat
        ./slow $i >> datos.dat
        ./fast $i >> datos.dat

 done

gnuplot <<- EOF 
set ylabel "tiempo"
set xlabel "tamaño"
set term png
set output "tiempo.png"
plot "datos.dat" u 1:2 w l title "slow", "datos.dat" u 1:3 w l title "fast"
EOF
