package clasesGeneticoNeuronal2;

import java.util.*;

import interfacesAlgoritmoGenetico.Mutacion;
import redNeuronal.Neurona;

public class MutacionRN implements Mutacion{
	private double mutacion;
	public MutacionRN(double mutacion){
		this.mutacion=mutacion;
	}
	@Override
	public void mutar(Poblacion p) {
		Random r= new Random();

		for(Individuo i: p.getIndividuos()){
			for(Neurona n: i.getRed().getRed().getNeuronas()){
				if(n.getPesos()==null)
					continue;
				if(r.nextDouble()<mutacion){
					for(int j=0; j<n.getPesos().size(); j++){
						n.getPesos().set(j, n.getPesos().get(j)+ r.nextDouble()-0.5);
					}
				}
					
			}
		}
	}

}
