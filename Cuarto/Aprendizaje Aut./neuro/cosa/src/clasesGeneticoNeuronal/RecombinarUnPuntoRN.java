package clasesGeneticoNeuronal;

import java.util.Random;

import interfacesAlgoritmoGenetico.*;

public class RecombinarUnPuntoRN implements Recombinar{
	
	double cruce;
	
	public RecombinarUnPuntoRN(double cruece) {
		this.cruce=cruece;
	}

		@Override
		public Poblacion recombinar(Poblacion p) {
			Poblacion rec= new Poblacion(p.nEntradas, p.nSalidas, p.maxCapas, p.maxNeur, 0, 0);

			Random r= new Random();
			int i1,i2, corte;
			
			
			for (int i=0; i < p.getIndividuos().size(); i+=2){
				i1=r.nextInt(p.individuos.size());
				i2=r.nextInt(p.getIndividuos().size());
				//if(r.nextDouble()<cruce){
					corte=r.nextInt(p.nReglas -2)+1;
					rec.individuos.add(cruceEnPunto(p, i1, i2, corte));
					rec.individuos.add(cruceEnPunto(p, i2, i1, corte));
				/*}
				else{
					System.out.println("no recombina");
					rec.individuos.add(p.individuos.get(i1));
					rec.individuos.add(p.individuos.get(i2));
				}*/
			}

			rec.nReglas=p.nReglas;
			rec.nIndvs=p.nIndvs;
			return rec;
		}

		private Individuo cruceEnPunto(Poblacion individuos,int i1,int i2, int corte){
			
			Individuo in1=individuos.getIndividuos().get(i1);
			Individuo in2=individuos.getIndividuos().get(i2);
			Individuo i=new Individuo(in1.nEntradas, in1.nSalidas, in1.maxCapas, in1.maxNeur, 0);

			for(int n=0; n<in1.getReglas().size(); n++){
				if(n>corte){
					i.reglas.add(in1.reglas.get(n));
					//System.out.println("in1:"+in1.reglas.get(n).getRed());
				}
				else{
					i.reglas.add(in2.reglas.get(n));
					//System.out.println("in2:"+in2.reglas.get(n).getRed());
				}
				
			}
			return i;
		}
		
}



