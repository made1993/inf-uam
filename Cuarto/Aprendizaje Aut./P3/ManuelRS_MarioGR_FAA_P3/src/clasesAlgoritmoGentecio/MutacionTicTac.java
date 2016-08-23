package clasesAlgoritmoGentecio;

import java.util.Random;

import interfacesAlgoritmoGenetico.Mutacion;

public class MutacionTicTac implements Mutacion {
	
	double prob;
	
	public MutacionTicTac(double prob) {
		this.prob=prob;
	}

	@Override
	public void mutar(Poblacion p) {
		Random r= new Random();
		int bit=0, nr=0;
		
		for(Individuo i: p.getIndividuos()){
			for(Regla regla: i.getReglas()){
				if(r.nextDouble()<prob/i.getReglas().size()){
					nr=r.nextInt(i.sizeRegla());
					bit=r.nextInt(i.getReglas().get(nr).getRegla().size());
					
					regla.getRegla().get(nr).set(bit,!regla.getRegla().get(nr).get(bit));
					
				}
			}
		}
	}
}
