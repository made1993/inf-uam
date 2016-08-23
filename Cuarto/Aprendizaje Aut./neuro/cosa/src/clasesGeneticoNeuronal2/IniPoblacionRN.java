package clasesGeneticoNeuronal2;

import java.util.*;

import datos.Datos;
import interfacesAlgoritmoGenetico.IniPoblacion;

public class IniPoblacionRN implements IniPoblacion{
	int nIndvs;
	List<Integer> capas;
	
	public IniPoblacionRN(int nIndvs, List<Integer> capas) {
		this.nIndvs = nIndvs;
		this.capas = capas;
	}
	
	@Override
	public Poblacion crearPoblacion() {
		Poblacion pob= new Poblacion(nIndvs, capas);
		for(int i =0; i<nIndvs; i++){
			Individuo indv= new Individuo(0, capas);
			pob.getIndividuos().add(indv);
		}
		return pob;
	}

}
