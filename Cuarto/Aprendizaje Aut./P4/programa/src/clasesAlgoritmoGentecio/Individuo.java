package clasesAlgoritmoGentecio;

import java.util.*;

public class Individuo {
	ArrayList<Regla> reglas;
	double fit;
	
	public Individuo(){
		reglas= new ArrayList<Regla>();
	}
	
	public Individuo( int nreg, ArrayList<Integer> natr) {
		reglas = new ArrayList<Regla>();
		for (int i=0; i<nreg; i++){
			reglas.add(new Regla(natr));
		}
		fit =.001;
	}
	
	public ArrayList<Regla> getReglas() {
		return reglas;
	}
	public void setReglas(ArrayList<Regla> reglas) {
		this.reglas = reglas;
	}
	public double getFit() {
		return fit;
	}
	public void setFit(Double fit) {
		this.fit = fit;
	}
	
	public void addRegla(Regla r){
		this.reglas.add(r.copiaRegla());
	}
	public int sizeRegla(){
		return reglas.get(0).regla.size();
	}
	public Individuo copiaInv(){
		Individuo i = new Individuo();
		i.fit=this.fit;
		for(Regla r: reglas){
			i.reglas.add(r.copiaRegla());
		}
		return i;
	}

}
