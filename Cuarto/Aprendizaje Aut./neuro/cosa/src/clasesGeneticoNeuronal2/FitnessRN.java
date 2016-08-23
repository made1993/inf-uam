package clasesGeneticoNeuronal2;

import java.util.*;

import datos.Datos;
import interfacesAlgoritmoGenetico.Fitness;

public class FitnessRN implements Fitness{

	
	
	
	@Override
	public void fit(Poblacion p, Datos datos) {
		for(Individuo i: p.getIndividuos()){
			i.setFit(0.0);
			List<Double> predicc=i.getRed().clasifica(datos);
			for(int n=0; n<datos.getNumDatos(); n++){
				if(predicc.get(n)==datos.getDato(n, datos.getSizeCountAtributos()-1)){
					i.setFit(i.getFit()+1);
				}
			}
			
		}
		
	}

	@Override
	public void generarBitSets(Datos datos) {
		// TODO Auto-generated method stub
		
	}

}
