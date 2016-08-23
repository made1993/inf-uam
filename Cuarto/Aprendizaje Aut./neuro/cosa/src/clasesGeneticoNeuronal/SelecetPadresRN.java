package clasesGeneticoNeuronal;

import java.util.ArrayList;
import java.util.Random;

import clasesGeneticoNeuronal.*;
import interfacesAlgoritmoGenetico.SelectPadres;

public class SelecetPadresRN  implements SelectPadres{

	@Override
	public Poblacion selectPadres(Poblacion p) {
		double fit=0.0;
		
		
		Poblacion h= new Poblacion(p.nEntradas, p.nSalidas, p.maxCapas, p.maxNeur, p.nIndvs, p.nReglas);
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
