package es.uam.ads.p2.figuras;
/**
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 * @version 1.0
 *
 * Se describe la clase Figura para comparar figuras 
 *
*/
public abstract class Figura {

	/**
	 * Se implementa el metodo getPerimetro para obtener el perimetro de la figura
	 *
	 * @return double Devuelve el perimetro de la figura
	*/
	public abstract double getPerimetro();

	/**
	 * Se implementa el metodo getArea para obtener el area de la figura
	 *
	 * @return double Devuelve el area de la figura
	*/
	public abstract double getArea();

	/**
	 * Se implementa el metodo esMayor para comparar areas de figuras
	 *
	 * @param figura Figura con la que se va a comparar el area
	 * @return boolean True en el caso de que la figura que nos pasan sea menor que la de la clase desde la que se llama al metodo
	*/
	public boolean esMayor(Figura figura){
		return this.getArea() > figura.getArea();
	}
}
