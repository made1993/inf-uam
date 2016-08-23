package clasesAlgoritmoGentecio;

import java.util.Random;

import interfacesAlgoritmoGenetico.Recombinar;

public class RecombinarUniformeTicTac implements Recombinar{
	double cruce; 
	public RecombinarUniformeTicTac(double cruce) {
		this.cruce=cruce;
	}
	@Override
	public Poblacion recombinar(Poblacion p) {
		Poblacion rec= new Poblacion();
		Random r= new Random();
		int i1,i2;
		for (int i=0; i < p.getIndividuos().size(); i+=2){
			i1=r.nextInt(p.getIndividuos().size());
			i2=r.nextInt(p.getIndividuos().size());
			if(r.nextDouble()<cruce){				
				rec.addIndividuo(cruceUniforme(p, i1, i2));
				rec.addIndividuo(cruceUniforme(p, i2, i1));

			}
			else{
				rec.addIndividuo(p.getIndividuos().get(i1));
				rec.addIndividuo(p.getIndividuos().get(i2));
			}
		}
		return rec;
	}

	private Individuo cruceUniforme(Poblacion individuos,int i1,int i2){
		Individuo i= new Individuo();
		Individuo in1=individuos.getIndividuos().get(i1);
		Individuo in2=individuos.getIndividuos().get(i2);
		Random rand = new Random(); 
		for(int n=0; n<in1.getReglas().size(); n++){
			Regla r= new Regla();
			for(int j=0; j<in1.sizeRegla(); j++){
				if(rand.nextBoolean()==true)
					r.setRegla(in1.getReglas().get(n).getRegla());
				else
					r.setRegla(in2.getReglas().get(n).getRegla());
			}
			if(rand.nextBoolean()==true)
				r.setPrediccion(in1.getReglas().get(n).getPrediccion());
			else
				r.setPrediccion(in2.getReglas().get(n).getPrediccion());
			i.addRegla(r);	
		}
		return i;
	}
}
