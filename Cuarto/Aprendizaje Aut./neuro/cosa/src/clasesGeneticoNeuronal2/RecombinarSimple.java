package clasesGeneticoNeuronal2;

import java.util.Random;

import interfacesAlgoritmoGenetico.*;
import redNeuronal.Neurona;

public class RecombinarSimple implements Recombinar{
	
	double cruce;
	
	public RecombinarSimple(double cruece) {
		this.cruce=cruece;
	}

		@Override
		public Poblacion recombinar(Poblacion p) {
			Poblacion rec= new Poblacion(p.getnIndvs(), p.getCapas());

			Random r= new Random();
			int i1,i2;
			
			
			for (int i=0; i < p.getIndividuos().size(); i+=2){
				i1=r.nextInt(p.individuos.size());
				i2=r.nextInt(p.getIndividuos().size());
				rec.getIndividuos().add(cruce(p.getIndividuos().get(i1),p.getIndividuos().get (i2) ));
				rec.getIndividuos().add(cruce(p.getIndividuos().get(i2),p.getIndividuos().get (i1) ));
			}

			return rec;
		}

		private Individuo cruce(Individuo i1, Individuo i2){
			Individuo in= new Individuo(i1.fit, i1.capas);
			Random r= new Random();
			int i=0;
			for(Neurona n: in.getRed().getRed().getNeuronas()){

				n.getPesos().clear();
				if(r.nextDouble()>0.5){
					n.getPesos().addAll(i1.getRed().getRed().getNeuronas().get(i++).getPesos());
				}
				else{
					n.getPesos().addAll(i2.getRed().getRed().getNeuronas().get(i++).getPesos());
				}
			}
			return in;
			
		}
		
}



