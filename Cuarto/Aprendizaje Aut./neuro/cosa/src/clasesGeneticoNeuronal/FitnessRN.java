package clasesGeneticoNeuronal;

import java.util.*;
import java.util.Map.Entry;

import datos.Datos;
import interfacesAlgoritmoGenetico.Fitness;
import redNeuronal.RedNeuronal;

public class FitnessRN implements Fitness{

	
	
	
	@Override
	public void fit(Poblacion p, Datos datos) {
		
		
		
		for(Individuo i :p.individuos){
			i.fit=0;
			
			ArrayList<HashMap<Double, Integer>> predics= new ArrayList<HashMap<Double, Integer>>();
			for(Regla r: i.reglas){
				r.red.clasifica(datos);
				ArrayList<Double> predic=r.red.getResultado();
				for(int j=0; j<predic.size(); j++){
					if(predics.size()<=j||predics.get(j)==null){
						HashMap<Double, Integer> val= new HashMap<>();
						val.put(predic.get(j), r.getVoto());
						predics.add(val);
						
					}
					else if(predics.get(j).containsKey(predic.get(j))){

						Integer n=predics.get(j).get(predic.get(j));
						predics.get(j).put(predic.get(j),n+r.getVoto());
					}
					else{
						predics.get(j).put(predic.get(j), r.getVoto());
					}
				}
			}
			for(int j=0; j<datos.getNumDatos(); j++){
				
				Entry<Double, Integer> entMax=null;
				for(Entry<Double, Integer> ent:predics.get(j).entrySet()){
					if(entMax==null){
						entMax=ent;
					}
					else if(entMax.getValue()<ent.getValue()){
						entMax=ent;
					}							
				}
				//System.out.println("predice:"+entMax+" "+entMax.getValue() );
				if(entMax.getKey()==datos.getDato(j, datos.getSizeCountAtributos()-1))
					i.fit++;
			}
		}
	}

	@Override
	public void generarBitSets(Datos datos) {
		// TODO Auto-generated method stub
		
	}

}
