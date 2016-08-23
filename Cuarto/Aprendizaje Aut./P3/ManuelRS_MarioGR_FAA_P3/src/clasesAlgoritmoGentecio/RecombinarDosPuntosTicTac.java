package clasesAlgoritmoGentecio;

import java.util.Random;

import interfacesAlgoritmoGenetico.*;

public class RecombinarDosPuntosTicTac implements Recombinar{

double cruce;
	
	public RecombinarDosPuntosTicTac(double cruce) {
		this.cruce=cruce;
	}

	@Override
	public Poblacion recombinar(Poblacion p) {
		Poblacion rec= new Poblacion();
		Random r= new Random();
		int i1,i2, corte, corte2;
		for (int i=0; i < p.getIndividuos().size(); i+=2){
			i1=r.nextInt(p.getIndividuos().size());
			i2=r.nextInt(p.getIndividuos().size());
			if(r.nextDouble()<cruce){
				corte=r.nextInt(p.getIndividuos().get(0).sizeRegla()-2)+1;
				
				do{
					corte2= r.nextInt(p.getIndividuos().get(0).sizeRegla()-2)+1;
				}while(corte == corte2);
				
				rec.addIndividuo (cruceEnDosPuntos(p, i1, i2, corte, corte2));
				rec.addIndividuo(cruceEnDosPuntos(p, i2, i1, corte, corte2));
			
			}
			else{
				rec.addIndividuo(p.getIndividuos().get(i1));
				rec.addIndividuo(p.getIndividuos().get(i2));
			}
		}
		return rec;
	}

	private Individuo cruceEnDosPuntos(Poblacion individuos,int i1,int i2, int corte, int corte2){
		Individuo i= new Individuo();
		Individuo in1=individuos.getIndividuos().get(i1);
		Individuo in2=individuos.getIndividuos().get(i2);
		Random rand = new Random(); 
		for(int n=0; n<in1.getReglas().size(); n++){
			Regla r= new Regla();
			
			for(int j=0; j<in1.sizeRegla(); j++){
				if(j < corte)
					r.regla.add(in1.getReglas().get(n).getRegla().get(j));
				
				else if(j>=corte && j< corte2)
					r.regla.add(in2.getReglas().get(n).getRegla().get(j));
				else
					r.regla.add(in1.getReglas().get(n).getRegla().get(j));
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
