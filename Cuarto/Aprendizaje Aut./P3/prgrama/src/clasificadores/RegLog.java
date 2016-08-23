package clasificadores;

import java.util.*;

import datos.Datos;

public class RegLog extends Clasificador{
	int epocas;
	double tasa;
	ArrayList<Double> w;
	ArrayList<Double> res;
	public RegLog(int epocas, double tasa){
		this.epocas=epocas;
		this.tasa=tasa;
	}
	public ArrayList<Double> generaW(int n){
		ArrayList<Double> w= new ArrayList<Double>();
		for(int i = 0; i < n; i++){
			Random r = new Random();
			double randomValue = -1 + (1 - (-1)) * r.nextDouble();
			w.add(randomValue);
		}
		return w;
	}
	@Override
	public void entrenamiento(Datos datosTrain) {
		w= generaW(datosTrain.getSizeCountAtributos());
		Enumeration<Double>  keys= datosTrain.getClases().keys();
		double key =keys.nextElement();
		for (int i=0; i < epocas; i++){
			for(int j=0; j < datosTrain.getNumDatos(); j++){
				double sigmoide=0.0;
				
				for(int k=0; k<w.size()-1; k++){
					sigmoide+=w.get(k)*datosTrain.getDato(j, k);
				}
				sigmoide+=w.get(w.size()-1);
				sigmoide = 1/(1+Math.exp((-1)*sigmoide));
				
				double t= datosTrain.getDato(j, datosTrain.getSizeCountAtributos()-1);
				int k=0;
				
				for(k=0; k<w.size()-1; k++){
					w.set(k, w.get(k) -tasa*(sigmoide- (key==t?1:0))*datosTrain.getDato(j, k));
					
				}
				w.set(k, w.get(k) -tasa*(sigmoide-(key==t?1:0))*1);
			}
		}
		
	}

	@Override
	public ArrayList<Double> clasifica(Datos datosTest) {
		Enumeration<Double> keys= datosTest.getClases().keys();
		Double c1=keys.nextElement();
		Double c2=keys.nextElement();
		res=new ArrayList<Double>();
		for(int i=0; i<datosTest.getNumDatos(); i++){
			double sigmoide=0.0;
			for(int j=0; j<w.size()-1; j++){
				sigmoide+=w.get(j)*datosTest.getDato(i, j);
			}
			sigmoide+=w.get(w.size()-1);
			sigmoide = 1/(1+Math.exp((-1)*sigmoide));

			if(sigmoide>0.5){
				res.add(c1);
			}
			else{
				res.add(c2);
			}
		}
		
		return res;
	}

	@Override
	public ArrayList<Double> getResultado() {
		return res;
	}
	
}