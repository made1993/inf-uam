package clasesAlgoritmoGentecio;

import java.util.*;

public class Poblacion {
	ArrayList<Individuo> individuos;

	public Poblacion(){
		individuos= new ArrayList<Individuo>();
	}
	public Poblacion(int n, int nreg,ArrayList<Integer> natr) {
		individuos = new ArrayList<Individuo>();
		for (int i = 0; i < n; i++) {
			individuos.add( new Individuo(nreg, natr));
		}
	}
	
	public double sumFit() {
		double sum = 0.0;
		for (int i = 0; i < individuos.size(); i++) {
			sum += individuos.get(i).getFit();
		}
		return sum;
	}

	
	
	public ArrayList<Individuo> getIndividuos() {
		return individuos;
	}

	public void setIndividuos(ArrayList<Individuo> individuos) {
		for(Individuo i: individuos){
			this.individuos.add(i.copiaInv());
		}
	}
	
	public void addIndividuo(Individuo i){		
		individuos.add(i.copiaInv());
	}
	
	public void extractIndividuo(Individuo i){
		individuos.remove(i);
	}

}
