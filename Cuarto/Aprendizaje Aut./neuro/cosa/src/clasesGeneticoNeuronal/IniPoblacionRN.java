package clasesGeneticoNeuronal;

import datos.Datos;
import interfacesAlgoritmoGenetico.*;
public class IniPoblacionRN implements IniPoblacion{

	int nIndvs;
	int nReglas;
	int nEntradas;
	int nSalidas;
	int maxCapas;
	int maxNeur;
	Datos datosTrain;
	
	public IniPoblacionRN(int nIndvs, int nReglas, int nEntradas, int nSalidas, int maxCapas, int maxNeur,
			Datos datosTrain) {
		super();
		this.nIndvs = nIndvs;
		this.nReglas = nReglas;
		this.nEntradas = nEntradas;
		this.nSalidas = nSalidas;
		this.maxCapas = maxCapas;
		this.maxNeur = maxNeur;
		this.datosTrain = datosTrain;
	}

	@Override
	public Poblacion crearPoblacion() {
		Poblacion pob= new Poblacion(nEntradas, nSalidas, maxCapas, maxNeur, nIndvs, nReglas);
		int j=0;
		for(Individuo i: pob.individuos){

			System.out.println("Individuo:"+(j++));
			for(Regla r: i.reglas){
				r.red.entrenamiento(datosTrain);
			}
		}
		return pob;
	}

}
