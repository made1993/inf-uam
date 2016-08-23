package clasesGeneticoNeuronal;

import java.util.*;

import interfacesAlgoritmoGenetico.Mutacion;
import redNeuronal.Neurona;

public class MutacionRN implements Mutacion{
	private double mutVoto;
	private double mutPeso;
	private double mutIndv;
	public MutacionRN(double mutVoto, double mutPeso, double mutIndv){
		this.mutVoto=mutVoto;
		this.mutPeso=mutPeso;
		this.mutIndv=mutIndv;
	}
	@Override
	public void mutar(Poblacion p) {
		Random rand= new Random();
		
		for(Individuo i: p.individuos){
			for(Regla r: i.reglas){
				if(rand.nextDouble()<mutVoto){
					r.incrVoto();
					
				}
				if(rand.nextDouble()<mutIndv)
					
				for(Neurona n: r.getRed().getRed().getNeuronas()){
					if(rand.nextDouble()<mutPeso){
						
						for(int j=0; n.getPesos()!=null && j<n.getPesos().size() ; j++){
							n.getPesos().set(j, n.getPesos().get(j)+(rand.nextDouble()*2)-1);
						}
					}
				}
			}
		}
	}

}
