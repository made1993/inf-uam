package particionado;

import java.util.ArrayList;
import java.util.Stack;

import datos.Datos;

public class ValidacionCruzada implements EstrategiaParticionado {
	
	int numParticiones;
	
	public ValidacionCruzada(int numParticiones) {
		this.numParticiones=numParticiones;
	}

	@Override
	public String getNombreEstrategia() {
		return "Validacion Cruzada";
	}

	@Override
	public int getNumeroParticiones() {
		return numParticiones;
	}

	@Override
	public ArrayList<Particion> crearPartciones(Datos datos) {
		datos.mezclarDatos(); //No es necesario, elegimos indices aleatorios
		int numDatos=datos.getNumDatos();
		Stack <Integer> nums = new Stack <Integer> ();
		int pos;
		ArrayList<ArrayList<Integer>> particiones = new ArrayList<ArrayList<Integer>>();
		int tamParticion=(int)(numDatos/numParticiones);
		int sobrantes=numDatos%numParticiones;
		ArrayList<Particion> res = new ArrayList<Particion>();
		
		/*Generamos las n particiones con indices aleatorios*/
		for(int i=0; i<numDatos-sobrantes; i=i+tamParticion){
			ArrayList<Integer> particion = new ArrayList<Integer>();
			for(int j=0; j<tamParticion; j++){
				pos = (int) Math.floor(Math.random() * numDatos );
				while (nums.contains(pos)) {
					pos = (int) Math.floor(Math.random() * numDatos );
				}
				nums.push(pos);
				particion.add(pos);
			}
			particiones.add(particion);
		}
		
		/*Si la division no fue exacta, repartimos el resto*/
		for(int i=0; i<sobrantes; i++){
			pos = (int) Math.floor(Math.random() * numDatos );
			while (nums.contains(pos)) {
				pos = (int) Math.floor(Math.random() * numDatos );
			}
			nums.push(pos);
			particiones.get(i).add(pos);
		}
				
		/*Creamos los diferentes objetos particion*/
		for(int i=0; i<numParticiones; i++){
			ArrayList<Integer> train = new ArrayList<Integer>();
			ArrayList<Integer> test = new ArrayList<Integer>();
			test=particiones.get(i); //La particion i sera la de test
			/*El resto de particiones se fusion para ser la de train*/
			for(int j=0; j<particiones.size(); j++){
				if(j!=i)
					train.addAll(particiones.get(j));
			}
			/*Añadimos la particion al array de particiones*/
			res.add(new Particion(train, test));
		}
		return res;
	}

}
