make
gcc -o ecdf ecdf.c 
#ej1,2,3
./practica3 -t traza.pcap
./ecdf tam.dat
gnuplot <<- EOF 
set ylabel "probabilidad"
set xlabel "tamaño"
set term png
set yrange [0:1.1]
set output "ej3.png"
plot "salida.txt" u 1:2 w l title "tamaño"
EOF
#ej4
./practica3 -t traza.pcap -po 80
./ecdf tam.dat
gnuplot <<- EOF 
set ylabel "probabilidad"
set xlabel "tamaño"
set term png
set yrange [0:1.1]
set output "httpsub.png"
plot "salida.txt" u 1:2 w l title "tamaño"
EOF
./practica3 -t traza.pcap -pd 80
./ecdf tam.dat
gnuplot <<- EOF 
set ylabel "probabilidad"
set xlabel "tamaño"
set term png
set yrange [0:1.1]
set output "httpbaj.png"
plot "salida.txt" u 1:2 w l title "tamaño"
EOF
#ej5
./practica3 -t traza.pcap -po 53
./ecdf tam.dat
gnuplot <<- EOF 
set ylabel "probabilidad"
set xlabel "tamaño"
set term png
set yrange [0:1.1]
set output "dnssub.png"
plot "salida.txt" u 1:2 w l title "tamaño"
EOF
./practica3 -t traza.pcap -pd 53
./ecdf tam.dat
gnuplot <<- EOF 
set ylabel "probabilidad"
set xlabel "tamaño"
set term png
set yrange [0:1.1]
set output "dnsbaj.png"
plot "salida.txt" u 1:2 w l title "tamaño"
EOF
#ej6
./practica3 -t traza.pcap -etho 00:1E:4A:E0:52:06
gnuplot <<- EOF 
set ylabel "tam"
set xlabel "seg"
set term png
set output "caudalsub.png"
plot "caudal.dat" u 2:4 w boxes title "tamaño"
EOF
./practica3 -t traza.pcap -ethd 00:1E:4A:E0:52:06
gnuplot <<- EOF 
set ylabel "tam"
set xlabel "seg"
set term png
set output "caudalbaj.png"
plot "caudal.dat" u 2:4 w boxes title "tamaño"
EOF
#ej7
./practica3 -t traza.pcap -po 80 -pd 33903
gnuplot <<- EOF 
set ylabel "seg"
set term png
set output "tmp_paq_tcp.png"
plot "salida.txt" u 1:2 w l title "tamaño"
EOF
#ej
./practica3 -t traza.pcap -po 36969 -pd 4069
gnuplot <<- EOF 
set ylabel "seg"
set term png
set output "tmp_paq_udp.png"
plot "salida.txt" u 1:2 w l title "tamaño"
EOF
rm ecdf
