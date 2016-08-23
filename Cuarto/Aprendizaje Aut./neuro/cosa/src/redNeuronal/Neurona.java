package redNeuronal;

import java.util.ArrayList;
import java.util.Random;

public class Neurona {

	private ArrayList<Double> pesos;
	private ArrayList<Neurona> entradas;
	private double salida;
	private double entrada;
	private double delta;
	public Neurona( ArrayList<Neurona> entradas, double salida) {
		
		this.entradas = entradas;
		this.entrada=0;
		this.salida = salida;
		this.delta =0;
		ArrayList<Double> pesos= new ArrayList<>();
		if(entradas!=null){
			
			Random r= new Random();
			for(int i=0; i<entradas.size(); i++){
				pesos.add(r.nextDouble()*2-1.0);
				//pesos.add(1.0);
			}
		}

		this.pesos=pesos;
	}
	/*public void actualizaSalida(){
		this.entrada=0;
		for (int i=0; i<this.entradas.size();i++){
			this.entrada+= this.pesos.get(i) * this.entradas.get(i).salida;
		}
		if(this.entrada>0){
			this.salida=Math.log(1+this.entrada);
		}
		else if(this.entrada<0){
			this.salida=-Math.log(1-this.entrada);
		}
		else
			this.salida=0;
			
		
	}
	
	public void calculaDeltaSalida(Double t){
		if(this.entrada>0)
			this.delta=(t-this.salida)*Math.log(1.0/(1+this.entrada));
		else if(this.entrada<0)
			this.delta=(t-this.salida)*Math.log(1.0/(1-this.entrada));
		else
			this.delta=0;
	}
	
	public void calculaDeltaOculta(Double d){
		if(this.entrada>0)
			this.delta=(d)*Math.log(1.0/(1+this.entrada));
		else if(this.entrada<0)
			this.delta=(d)*Math.log(1.0/(1-this.entrada));
		else
			this.delta=0;
	}*/
	
	public void actualizaSalida(){
		this.entrada=0;
		for (int i=0; i<this.entradas.size();i++){
			this.entrada+= this.pesos.get(i) * this.entradas.get(i).salida;
		}
		
		this.salida=1+Math.exp(-this.entrada) ;
		this.salida=2/this.salida -1;
		
	}
	public void calculaDeltaSalida(Double t){
		this.delta=(t-this.salida)*(1/2.0)*(1+this.salida)*(1-this.salida);
	}
	
	public void calculaDeltaOculta(Double d){
		this.delta=(d)*(1/2.0)*(1+this.salida)*(1-this.salida);
	}
	
	public void actulizaPesos(double tasa){
		for(int i=0; i<this.pesos.size(); i++){
			double peso=0;
			peso= this.pesos.get(i);
			peso+=tasa*this.delta*this.entradas.get(i).getSalida();
			this.pesos.set(i, peso);
			
		}
	}
	
	public ArrayList<Double> getPesos() {
		return pesos;
	}


	public void setPesos(ArrayList<Double> pesos) {
		this.pesos = pesos;
	}


	public ArrayList<Neurona> getEntradas() {
		return entradas;
	}


	public void setEntradas(ArrayList<Neurona> entradas) {
		this.entradas = entradas;
	}


	public double getSalida() {
		return salida;
	}


	public void setSalida(double salida) {
		this.salida = salida;
	}

	public double getEntrada() {
		return entrada;
	}

	public void setEntrada(double entrada) {
		this.entrada = entrada;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	public void printPesos(){
		if(pesos==null)
			return;
		for(Double p:pesos){
			System.out.print(p+" ");
		}
		System.out.println();
	}


}
