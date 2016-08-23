package redNeuronal;

import java.util.ArrayList;
import java.util.Random;

public class RedNeuronal {
	private ArrayList<Neurona> neuronas;
	private ArrayList<Integer> capas;
	private double tasa;
	public RedNeuronal( ArrayList<Integer> capas, double tasa) {
		this.neuronas = new ArrayList<>();
		this.tasa=tasa;
		int i=0;
		this.capas=capas;
	}
	
	
	

	public void iniRed(){
		//System.out.println("Se inicializa la entrada");
		for(int i=0 ;i<this.capas.get(0)-1; i++){
			//System.out.println("Neurona:"+i);
			this.neuronas.add(new Neurona(null, 0));
		}
		//System.out.println("neurona de sesgo entrada");
		this.neuronas.add(new Neurona( null, 1));
		int i=0;
		for( i=1; i<this.capas.size()-1; i++){
			//System.out.println("Capa"+i);
			ArrayList<Neurona> entradas= new ArrayList<>();
			/*Obtenemos las neuronas de entrada*/
			for(int j=0; j<this.capas.get(i-1);j++){
				entradas.add(this.neuronas.get(j+neuronasAnteriores(i-1)));
			}
			/*Creamos las neuronas para una capa*/
			for(int j=0; j<this.capas.get(i)-1;j++){
				//System.out.println("Neurpna:"+j);
				this.neuronas.add(new Neurona(entradas, 0));
			}
			/*Neurona de sesgo*/
			//System.out.println("Neurona de sesgo");
			this.neuronas.add(new Neurona(null, 1));
		}
		//System.out.println("Capa"+i);
		ArrayList<Neurona> entradas= new ArrayList<>();
		/*Obtenemos las neuronas de entrada*/
		for(int j=0; j<this.capas.get(i-1);j++){
			entradas.add(this.neuronas.get(j+neuronasAnteriores(i-1)));
		}
		/*Creamos las neuronas para una capa*/
		for(int j=0; j<this.capas.get(i);j++){
			//System.out.println("Neurpna:"+j);
			this.neuronas.add(new Neurona(entradas, 0));
		}
		
		
	}
	
	public void actualizaSalidaRed(ArrayList<Double> entrada){
		if(this.capas.get(0)-1!=entrada.size()){
			System.out.println("ERROR: tamaños de entrada diferentes");
			System.out.println(this.capas.get(0)-1);
			System.out.println(entrada.size());
		}
		/*Actualiza las neuronas de entrada*/
		for(int i=0; i<this.capas.get(0)-1; i++){
			this.neuronas.get(i).setSalida(entrada.get(i));
		}
		/*Actualiza la capa oculta*/
		int i=0;
		for(i=1; i<this.capas.size()-1; i++){
			//System.out.println(this.capas.get(i)-1);
			for(int j=0; j<this.capas.get(i)-1; j++){
				this.neuronas.get(j+neuronasAnteriores(i)).actualizaSalida();
			}
		}
		/*Actualiza la capa de salida*/
		for(int j=0; j<this.capas.get(i); j++){
			this.neuronas.get(j+neuronasAnteriores(i)).actualizaSalida();
		}
	}
	
	public int neuronasAnteriores(int i){
		int n=0;
		//System.out.println("i:"+i);
		for (int j=0; j<i; j++){
			//System.out.print(" "+this.capas.get(j));
			n+=this.capas.get(j);
		}
		//System.out.println();
		return n;
		
	}
	public void actualizaDeltas(ArrayList<Double> t){
		/*Calcula deltas de las salidas*/
		for(int i=neuronas.size()-1,j=t.size()-1; i>=neuronasAnteriores(capas.size()-1); i--,j--){
			neuronas.get(i).calculaDeltaSalida(t.get(j));
		}
		/*Resto de neuronas*/
		
		for(int i=capas.size()-2; i>=1; i--){
			//System.out.println("i:"+i);
			//System.out.println((neuronasAnteriores(i+1)-1)+" "+neuronasAnteriores(i));
			for(int j=neuronasAnteriores(i+1)-1; j>= neuronasAnteriores(i); j--){
				double delta=0.0;
				int l=neuronasAnteriores(i+2)-1;
				if(i<capas.size()-2)
					l--;
				for( ;l>=neuronasAnteriores(i+1);l--){
					delta+=neuronas.get(l).getDelta()*neuronas.get(l).getPesos().get(j-neuronasAnteriores(i));
				}
				neuronas.get(j).calculaDeltaOculta(delta);
			}
		}
	}
	
	public void actualizaPesos(){
		for(int i=1 ; i<capas.size()-1; i++){
			
			for(int j=neuronasAnteriores(i); j<neuronasAnteriores(i+1)-1; j++){
				neuronas.get(j).actulizaPesos(tasa);
				
			}
		}
	
		for(int j=neuronasAnteriores(capas.size()-1); j<this.neuronas.size(); j++){
			neuronas.get(j).actulizaPesos(tasa);
		}
		
		
	}
	
	public boolean parada(){
		return false;
	}
	
	
	public int neuronaActivada(){
		int indice=0;
		double max=0.0;
		for (int i=neuronasAnteriores(capas.size()-1); i<neuronas.size(); i++){
			if(neuronas.get(i).getSalida()>max){
				max=neuronas.get(i).getSalida();
				indice=i-neuronasAnteriores(capas.size()-1);
			}
		}
		return indice;
		
	}
	
	public void setNeuronas(ArrayList<Neurona> neuronas) {
		this.neuronas = neuronas;
	}
	
	public ArrayList<Neurona> getNeuronas(){
		return this.neuronas;
	}
	public void printPesos(){
		for(Neurona n: neuronas){
			n.printPesos();
		}
		System.out.println();
	}
	public static void main(String [] args){
		ArrayList<Integer> capas= new ArrayList<>();
		capas.add(2);
		capas.add(1);
		capas.add(4);
		capas.add(2);
		RedNeuronal  red= new RedNeuronal(capas, 1);
		red.iniRed();
		ArrayList<Double> entrada= new ArrayList<>();
		entrada.add(1.0);
		entrada.add(1.0);
		red.actualizaSalidaRed(entrada);
		System.out.println(red.getNeuronas().get(10).getSalida());
		System.out.println(red.getNeuronas().get(11).getSalida());
		entrada.set(1, -1.0);
		red.actualizaDeltas(entrada);
		red.actualizaPesos();
		System.out.println(red.neuronaActivada());
	}




	public ArrayList<Integer> getCapas() {
		return capas;
	}




	public void setCapas(ArrayList<Integer> capas) {
		this.capas = capas;
	}




	public double getTasa() {
		return tasa;
	}




	public void setTasa(double tasa) {
		this.tasa = tasa;
	}


}
