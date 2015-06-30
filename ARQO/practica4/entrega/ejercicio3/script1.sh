	make clean
	make
	rm *.txt
	echo ./mult	 1000
	./mult 1000 > mult.txt

	for i in $(seq 1 1 4)
	do 
		echo ./mult1 $i 1000
		./mult1 $i 1000 >mult1$i.txt
		echo ./mult2 $i 1000
		./mult2 $i 1000 >mult2$i.txt
		echo ./mult3 $i 1000
		./mult3 $i 1000 >mult3$i.txt
	done

