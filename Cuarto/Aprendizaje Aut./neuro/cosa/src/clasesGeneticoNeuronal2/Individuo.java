package clasesGeneticoNeuronal2;

import java.util.*;

import clasificadores.ClasificadorRetropropagacion;
import datos.Datos;

public class Individuo {
	double fit;
	ClasificadorRetropropagacion red;
	List<Integer> capas;
	public Individuo(double fit, List<Integer> capas) {
		super();
		this.fit = fit;
		this.capas= capas;
		this.red= new ClasificadorRetropropagacion((ArrayList<Integer>) capas, 0.05, 10000);
	}
	public double getFit(){
		return fit;
	}
	public void setFit(double fit){
		this.fit = fit;
	}
	public ClasificadorRetropropagacion getRed(){
		return red;
	}
	public void setRed(ClasificadorRetropropagacion red){
		this.red = red;
	}

	public List<Integer> getCapas() {
		return capas;
	}
	public void setCapas(ArrayList<Integer> capas) {
		this.capas = capas;
	}
	
	
}
