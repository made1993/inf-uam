minsz=1
maxsz=2049
incr=16
size=1024
d=0
rm -r fast_dir
rm -r slow_dir
mkdir slow_dir
mkdir fast_dir
chmod 0777 slow_dir
chmod 0777 fast_dir
for ((d=1024;d<=8192;d=d+d));
do

rm datas.dat
rm dataf.dat
rm datoss.dat
rm datosf.dat
rm tam.dat
rm dats.dat
rm datf.dat
	for i in $(seq $minsz $incr $maxsz)
	do
	valgrind --tool=cachegrind --I1=$d,1,64 --D1=$d,1,64 --LL=8388608,1,64 --cachegrind-out-file=slow_dir/$d\_$i.dat ./slow $i
	echo "$i" >>tam.dat
	cg_annotate slow_dir/$d\_$i.dat | grep -i PROGRAM >> datoss.dat
	valgrind --tool=cachegrind --I1=$d,1,64 --D1=$d,1,64 --LL=8388608,1,64 --cachegrind-out-file=fast_dir/$d\_$i.dat ./fast $i
	cg_annotate fast_dir/$d\_$i.dat | grep -i PROGRAM  >>datosf.dat
	
	done
sed -i 's/,//g' datoss.dat
sed -i 's/,//g' datosf.dat
paste tam.dat datoss.dat >dats.dat
paste tam.dat datosf.dat >datf.dat
awk '1{print $1, $6, $9}' dats.dat >datas.dat
awk '1{print $6, $9}' datf.dat >dataf.dat
paste datas.dat dataf.dat > $d\data.dat
# usar cut -d " " -f columnas <file>
done

rm -r fast_dir
rm -r slow_dir
rm datas.dat
rm dataf.dat
rm datoss.dat
rm datosf.dat
rm tam.dat
rm dats.dat
rm datf.dat
gnuplot <<- EOF 
set ylabel "fallos"
set xlabel "tamaño"
set term png
set output "read.png"
plot "1024data.dat" u 1:2 w l title "1PSlowLectura", "2048data.dat" u 1:2 w l title "2PSlowLectura", "4096data.dat" u 1:2 w l title "4PSlowLectura",  "8192data.dat" u 1:2 w l title "8PSlowLectura", "1024data.dat" u 1:4 w l title "1FastLectura", "2048data.dat" u 1:4 w l title "2PFastLectura", "4096data.dat" u 1:4 w l title "4PFastLectura",  "8192data.dat" u 1:4 w l title "8PFastLectura"
EOF

gnuplot <<- EOF 
set ylabel "fallos"
set xlabel "tamaño"
set term png
set output "write.png"
plot "1024data.dat" u 1:3 w l title "1PSlowEscritura", "2048data.dat" u 1:3 w l title "2PSlowEscritura", "4096data.dat" u 1:3 w l title "4PSlowEscritura",  "8192data.dat" u 1:3 w l title "8PSlowEscritura", "1024data.dat" u 1:5 w l title "1PFastEscritura", "2048data.dat" u 1:5 w l title "2PFastEscritura", "4096data.dat" u 1:5 w l title "4PFastEscritura",  "8192data.dat" u 1:5 w l title "8PFastEscritura"
EOF

