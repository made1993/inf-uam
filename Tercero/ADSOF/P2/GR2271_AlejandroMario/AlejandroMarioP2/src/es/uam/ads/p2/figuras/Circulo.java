package es.uam.ads.p2.figuras;
/**
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 * @version 1.0
 *
 * Se describe la clase Circulo para compararla con otras figuras
 *
*/
public class Circulo extends Figura {
	private double radio;
	
	public Circulo(double radio){
		this.radio=radio;
	}
		
	/**
	 * Se implementa el metodo getRadio para obtener el radio del circulo 
	 * 
	 * @return double Devuelve el radio del Circulo
	*/
	public double getRadio(){
		return this.radio;
	}

	/**
	 * Se implementa el metodo getPerimetro para obtener el perimetro, o circunferencia, del circulo 
	 *
	 * @return double Devuelve el perimetro del Circulo
	*/
	@Override
	public double getPerimetro() {
		return this.radio*Math.PI*2;
	}

	/**
	 * Se implementa el metodo getArea para obtener el area del circulo 
	 * 
	 * @return double Devuelve el area del Circulo
	*/
	@Override
	public double getArea() {
		return (this.radio)*(this.radio)*Math.PI;
	}

	public String toString(){
		 return "Circulo [area="+this.getArea()+" perimetro="+this.getPerimetro()+"]";
		
	}

}
