cd /ruta/a/P2
for i in P1-base P1-ejb P1-ws; do
	cd $i
	ant replegar ; ant unsetup-db limpiar-todo todo
	cd -
done