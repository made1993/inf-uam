package clasesAlgoritmoGentecio;

import interfacesAlgoritmoGenetico.EndAlgGen;

public class EndAlgGenTopFit implements EndAlgGen {
	
	int generaciones = 0;
	int generacionesMax;
	double fitTop;
	
	public EndAlgGenTopFit(int maxGen, double fitTop){
		this.fitTop=fitTop;
		generacionesMax=maxGen;
	}

	@Override
	public boolean end(double fit) {
		if(fit>=fitTop){
			System.out.println("Fit deseado alcanzado");
			generaciones=0;
			return true;
		}
		if(generaciones==generacionesMax){
			System.out.println("Max generacion alcanzado sin conseguir fit deseado");
			
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
