package clasesGeneticoNeuronal2;

import java.util.*;

public class Poblacion{
	
	List<Integer> capas;
	int nIndvs;
	ArrayList<Individuo> individuos;
	public Poblacion(int nIndvs, List<Integer> capas){
		this.capas=capas;
		this.nIndvs=nIndvs;
		this.individuos= new ArrayList<>();
		
	}
	public List<Integer> getCapas() {
		return capas;
	}
	public void setCapas(List<Integer> capas) {
		this.capas = capas;
	}
	public int getnIndvs() {
		return nIndvs;
	}
	public void setnIndvs(int nIndvs) {
		this.nIndvs = nIndvs;
	}
	public ArrayList<Individuo> getIndividuos() {
		return individuos;
	}
	public void setIndividuos(ArrayList<Individuo> indvs) {
		this.individuos = indvs;
	}
	public double sumFit() {
		double sum =0;
		for(Individuo i: individuos){
			sum+=i.getFit();
		}
		return sum;
	}
	public double fitMejor(){
		double fitm=0;
		for(Individuo i: individuos){
			if(fitm<i.fit)
				fitm = i.fit;
		}
		return fitm;
	}
	
	public double fitMedio(){
		double fitm=0;
		for(Individuo i: individuos){
			fitm += i.fit;
		}
		return fitm/individuos.size();
	}
	public double fitPeor(){
		double fitp=Double.MAX_VALUE;
		for(Individuo i: individuos){
			if(fitp>i.fit)
				fitp = i.fit;
		}
		return fitp;
	}
	

}
