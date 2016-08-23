package clasesGeneticoNeuronal;

import interfacesAlgoritmoGenetico.EndAlgGen;

public class EndAlgGenBasico implements EndAlgGen{

	int generaciones = 0;
	int generacionesMax;
	
	public EndAlgGenBasico(int max){
		generacionesMax=max;
	}
	
	@Override
	public boolean end(double n) {
		if(generaciones==generacionesMax){
			generaciones=0;
			return true;
		}
		generaciones++;
		if(generaciones%10==0){
			System.out.println("Generacion numero "+generaciones);
		}
		return false;
	}

}
