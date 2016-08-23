package clasesAlgoritmoGentecio;

import interfacesAlgoritmoGenetico.EndAlgGen;

public class EndAlgGenNumChanges implements EndAlgGen {
	
	int generacionesNoChange;
	int generacionesMax;
	int count;
	int generaciones;
	double fit;
	
	public EndAlgGenNumChanges(int maxGen, int maxGenNoChanges){
		count=0;
		generaciones=0;
		generacionesMax=maxGen;
		generacionesNoChange=maxGenNoChanges;
	}

	@Override
	public boolean end(double fit) {
		if(fit==this.fit)
			count++;
		else{
			count=0;
			this.fit=fit;
		}
		if(count>=generacionesNoChange){
			System.out.println("Numero de generaciones sin cambios alcanzado");
			generaciones=0;
			return true;
		}
		if(generaciones==generacionesMax){
			System.out.println("Max gen alcanzado");
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
