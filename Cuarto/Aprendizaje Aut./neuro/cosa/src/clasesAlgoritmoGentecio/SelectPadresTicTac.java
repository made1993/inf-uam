package clasesAlgoritmoGentecio;

import java.util.*;

import interfacesAlgoritmoGenetico.*;

public class SelectPadresTicTac implements SelectPadres{

	
	@Override
	public Poblacion selectPadres(Poblacion p) {
		double fit=0.0;
		Poblacion h= new Poblacion();
		Random r= new Random();
		double prob=0.0;
		h.individuos= new ArrayList<Individuo>();
		for (int j=0 ; j<p.getIndividuos().size(); j++){	
			prob=r.nextDouble();
			fit=0.0;
			
			for(Individuo i : p.getIndividuos()){
				fit+=i.getFit()/p.sumFit();
				if(fit>prob){
					h.addIndividuo(i);
					break;
				}
			}
			
		}
		return h;
	}

}
