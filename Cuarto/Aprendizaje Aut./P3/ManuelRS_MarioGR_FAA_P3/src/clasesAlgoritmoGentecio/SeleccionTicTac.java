package clasesAlgoritmoGentecio;

import interfacesAlgoritmoGenetico.Seleccion;

public class SeleccionTicTac implements Seleccion{
	
	double elite;

	public SeleccionTicTac(double elite) {
		this.elite=elite;
	}
	@Override
	public Poblacion seleccionar(Poblacion p1, Poblacion p2) {
		Poblacion resultado = new Poblacion();
		double elites = p1.getIndividuos().size()*elite;
		for(int i=0; i<elites; i++){
			Individuo maxInv = null;
			double max =-1;
			for (Individuo inv: p1.getIndividuos()){
				if(inv.getFit()>max){
					max=inv.getFit();
					maxInv=inv;
				}
			}
			resultado.addIndividuo(maxInv);
			p1.extractIndividuo(maxInv);
		}
		for(int i=0; i<p2.getIndividuos().size()-elites; i++){
			resultado.addIndividuo(p2.individuos.get(i));
		}
		return resultado;
	}
}
