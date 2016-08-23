package clasesAlgoritmoGentecio;
import java.util.ArrayList;

import interfacesAlgoritmoGenetico.*;

public class IniPoblacionTicTac implements IniPoblacion {
	
	int nreg;
	int numP;
	
	public IniPoblacionTicTac(int nreg, int numP) {
		this.nreg = nreg;
		this.numP=numP;
	}

	@Override
	public Poblacion crearPoblacion() {
		
		ArrayList<Integer> natr= new ArrayList<Integer>();
		for(int i=0; i<9; i++){
			natr.add(3);
		}
		return new Poblacion(numP, nreg, natr);
	}

}
