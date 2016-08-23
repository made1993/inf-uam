package clasesGeneticoNeuronal;

import java.util.ArrayList;

import datos.Datos;

public class Individuo {

	ArrayList<Regla> reglas;
	int nReglas;
	int nEntradas;
	int nSalidas;
	int maxCapas; 
	int maxNeur;
	double fit;
	public Individuo(int nEntradas, int nSalidas, int maxCapas, int maxNeur, int nReglas) {
		reglas = new ArrayList<>();
		this.nEntradas=nEntradas;
		this.nSalidas=nSalidas;
		this.maxCapas=maxCapas; 
		this.maxNeur=maxNeur;
		this.nReglas= nReglas;
		for(int i=0; i< nReglas; i++){
			reglas.add(new Regla(nEntradas, nSalidas, maxCapas,  maxNeur));
		}
		
	}

	public ArrayList<Regla> getReglas() {
		return reglas;
	}

	public void setReglas(ArrayList<Regla> reglas) {
		this.reglas = reglas;
	}

	public int getnReglas() {
		return nReglas;
	}

	public void setnReglas(int nReglas) {
		this.nReglas = nReglas;
	}

	public double getFit() {
		return fit;
	}

	public void setFit(double fit) {
		this.fit = fit;
	}
	
	
}
