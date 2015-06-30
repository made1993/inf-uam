package es.uam.ads.p2.figuras;
/**
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 * @version 1.0
 *
 * Se describe la clase Rectangulo para compararla con otras figuras
 *
*/
public class Rectangulo extends Figura {
	private double base;
	private double altura;

	public Rectangulo(double base,double altura){
		this.base=base;
		this.altura=altura;
	}

	/**
	 * Se implementa el metodo getBase para obtener la base del rectangulo 
	 *
	 * @return double Devuelve la base del rectangulo
	*/
	public double getBase(){
		return this.base;
	}

	/**
	 * Se implementa el metodo getaltura para obtener la altura del rectangulo 
	 *
	 * @return double Devuelve la altura del rectangulo
	*/
	public double getAltura(){
		return this.altura;
	}

	/**
	 * Se implementa el metodo is Cuadrado para saber si el rectangulo tiene base y altura iguales
	 *
	 * @return boolean Devuelve True en el caso de que sea un cuadrado
	*/
	public boolean isCuadrado(){
		return this.base==this.altura;
	}

	/**
	 * Se implementa el metodo getPerimetro para obtener el perimetro del rectangulo 
	 *
	 * @return double Devuelve el perimetro del rectangulo
	*/
	@Override
	public double getPerimetro() {
		return this.base*2+this.altura*2;
	}

	/**
	 * Se implementa el metodo getArea para obtener el area del rectangulo 
	 * 
	 * @return double Devuelve el area del rectangulo
	*/
	@Override
	public double getArea() {
		return this.base*this.altura;
	}

	public String toString(){
		return "Rectangulo [area="+this.getArea()+" perimetro="+this.getPerimetro()+"]";
	}
}
