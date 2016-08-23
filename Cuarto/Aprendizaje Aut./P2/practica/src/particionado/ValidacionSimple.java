package particionado;

import java.util.ArrayList;
import java.util.Stack;

import datos.Datos;

public class ValidacionSimple implements EstrategiaParticionado {

	private int porcentaje;
	
	public ValidacionSimple(int porcentaje){
		this.porcentaje=porcentaje;
	}
	
	@Override
	public String getNombreEstrategia() {
		return "Validacion Simple";
	}

	@Override
	public int getNumeroParticiones() {
		return porcentaje;
	}

	@Override
	public ArrayList<Particion> crearPartciones(Datos datos) {
		datos.mezclarDatos(); //No es necesario, elegimos indices aleatorios
		int numDatos=datos.getNumDatos();
		int indice = (int) Math.round(porcentaje*0.01*numDatos);
		Stack <Integer> nums = new Stack <Integer> ();
		ArrayList<Integer> train = new ArrayList<Integer>();
		ArrayList<Integer> test = new ArrayList<Integer>();
		ArrayList<Particion> res = new ArrayList<Particion>();
		
		/*Introducimos los indices de train y test de manera aleatoria*/
		for(int i=0, pos=0; i<datos.getNumDatos(); i++){
			pos = (int) Math.floor(Math.random() * numDatos );
			while (nums.contains(pos)) {
				pos = (int) Math.floor(Math.random() * numDatos );
			}
			nums.push(pos);
			if(i<indice){
				train.add(pos);
			}else{
				test.add(pos);	
			}
		}
		
		/*Creamos la particion*/
		Particion p= new Particion(train, test);
		res.add(p);
		return res;
	}
	
}
