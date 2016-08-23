package clasesGeneticoNeuronal;

import java.lang.reflect.Array;
import java.util.ArrayList;

import datos.Datos;

public class Poblacion{
	int nEntradas;
	int nSalidas;
	int maxCapas;
	int maxNeur;
	int nIndvs;
	int nReglas;
	ArrayList<Individuo> individuos;
	public Poblacion(int nEntradas, int nSalidas, int maxCapas, int maxNeur, int nIndvs, int nReglas) {
		this.nIndvs=nIndvs;
		this.nEntradas=nEntradas;
		this.nSalidas=nSalidas;
		this.maxCapas=maxCapas;
		this.maxNeur=maxNeur;
		this.nIndvs=nIndvs;
		this.nReglas=nReglas;
		individuos= new ArrayList<>();
		for(int i=0; i<nIndvs; i++){
			individuos.add(new Individuo(nEntradas, nSalidas, maxCapas, maxNeur, nReglas));
		}
	}
	public double sumFit(){
		double sum=0;
		for(Individuo i: this.individuos){
			sum+=i.fit;
		}
		return sum;
	}
	
	
	public int getnEntradas() {
		return nEntradas;
	}
	public void setnEntradas(int nEntradas) {
		this.nEntradas = nEntradas;
	}
	public int getnSalidas() {
		return nSalidas;
	}
	public void setnSalidas(int nSalidas) {
		this.nSalidas = nSalidas;
	}
	public int getMaxCapas() {
		return maxCapas;
	}
	public void setMaxCapas(int maxCapas) {
		this.maxCapas = maxCapas;
	}
	public int getMaxNeur() {
		return maxNeur;
	}
	public void setMaxNeur(int maxNeur) {
		this.maxNeur = maxNeur;
	}
	public int getnIndvs() {
		return nIndvs;
	}
	public void setnIndvs(int nIndvs) {
		this.nIndvs = nIndvs;
	}
	public int getnReglas() {
		return nReglas;
	}
	public void setnReglas(int nReglas) {
		this.nReglas = nReglas;
	}
	public ArrayList<Individuo> getIndividuos() {
		return individuos;
	}
	public void setIndividuos(ArrayList<Individuo> individuos) {
		this.individuos = individuos;
	}
	
	
	
}
