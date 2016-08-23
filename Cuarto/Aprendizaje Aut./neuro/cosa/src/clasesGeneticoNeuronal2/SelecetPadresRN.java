package clasesGeneticoNeuronal2;

import java.util.ArrayList;
import java.util.Random;

import clasesGeneticoNeuronal2.*;
import interfacesAlgoritmoGenetico.SelectPadres;

public class SelecetPadresRN  implements SelectPadres{

	@Override
	public Poblacion selectPadres(Poblacion p) {
		double fit=0.0;
		
		
		Poblacion h= new Poblacion(p.nIndvs, p.capas);;
		Random r= new Random();
		double prob=0.0;
		h.individuos= new ArrayList<Individuo>();
		for (int j=0 ; j<p.getIndividuos().size(); j++){	
			prob=r.nextDouble();
			fit=0.0;
			double sum=p.sumFit();
			for(Individuo i : p.getIndividuos()){
				fit+=(double)i.getFit()/sum;
				if(fit>prob){
					h.individuos.add(i);
					break;
				}
			}
			
		}
		return h;
	}
	

}
