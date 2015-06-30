minsz=1
maxsz=513
incr=16
size=1024
d=0
rm -r trasp_dir
rm -r mult_dir
mkdir mult_dir
mkdir trasp_dir
chmod 0777 mult
chmod 0777 trasp
chmod 0777 mult_dir
chmod 0777 trasp_dir
for ((d=1024;d<=1024;d=d+d));
do

rm datas.dat
rm dataf.dat
rm datoss.dat
rm datosf.dat
rm tam.dat
rm dats.dat
rm datf.dat
rm tm.dat
rm tt.dat
	for i in $(seq $minsz $incr $maxsz)
	do
	valgrind --tool=cachegrind --I1=$d,1,64 --D1=$d,1,64 --LL=8388608,1,64 --cachegrind-out-file=mult_dir/$d\_$i.dat ./mult $i >>tm.dat
	echo "$i" >>tam.dat
	cg_annotate mult_dir/$d\_$i.dat | grep -i PROGRAM >> datoss.dat
	valgrind --tool=cachegrind --I1=$d,1,64 --D1=$d,1,64 --LL=8388608,1,64 --cachegrind-out-file=trasp_dir/$d\_$i.dat ./trasp $i >> tt.dat
	cg_annotate trasp_dir/$d\_$i.dat | grep -i PROGRAM  >>datosf.dat
	
	done
sed -i 's/,//g' datoss.dat
sed -i 's/,//g' datosf.dat
paste tam.dat tm.dat >dm.dat
paste dm.dat datoss.dat >dats.dat
paste tt.dat datosf.dat >datf.dat
awk '1{print $1, $2, $7, $10}' dats.dat >datas.dat
awk '1{print $1, $6, $9}' datf.dat >dataf.dat
paste datas.dat dataf.dat > $d\data.dat
# usar cut -d " " -f columnas <file>
done

rm -r trasp_dir
rm -r mult_dir
rm datas.dat
rm dataf.dat
rm datoss.dat
rm datosf.dat
rm tam.dat
rm dats.dat
rm datf.dat
gnuplot <<- EOF 
set ylabel "tiempo(s)"
set xlabel "tamaño"
set term png
set output "tiempos.png"
plot "1024data.dat" u 1:2 w l title "tiemposMult", "1024data.dat" u 1:5 w l title "tiemposTrasp"
EOF


gnuplot <<- EOF 
set ylabel "fallos"
set xlabel "tamaño"
set term png
set output "fallos.png"
plot "1024data.dat" u 1:3 w l title "MultD1mr", "1024data.dat" u 1:4 w l title "MultD1mw","1024data.dat" u 1:6 w l title "TraspD1mr", "1024data.dat" u 1:7 w l title "TraspD1mw"
EOF

