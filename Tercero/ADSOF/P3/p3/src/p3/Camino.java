/**
 * 
 */
package p3;
import p3.Posada;

/**
 * @author
 *
 */
public class Camino {

	private  Posada origen;
	private  Posada destino;
	protected  int energia;
	private static int acumulado;
	
	/**
	 * Constructor de la clase Camino.
	 * @param origen La posada en la que empieza el camino.
	 * @param destino La en la que termina el camino.
	 * @param energia La energia que consume recorrer el camino.
	 */
	public Camino(Posada origen,Posada destino ,int energia) {
		this.origen=origen;
		this.destino=destino;
		this.energia=energia;
	}
	/**
	 * Retorna el valor de acumulada.
	 * @return Un entero con el valor de acumulado.
	 */
	public static int getAcumulado() {
		return acumulado;
	}
	/**
	 * Retorna la posada origen.
	 * @return Un objeto Posada con el vlaor de la posada origen.
	 */
	public Posada getOrigen() {
		return origen;
	}
	/**
	 * Retorna la posada destino.
	 * @return Un objeto Posada con el valor de la posada destino .
	 */
	public Posada getDestino() {
		return destino;
	}
	/**
	 * Retorna la energia que consume recorrer el camino.
	 * @return Un entero con el coste de recorrer el camino. 	
	 */
	public Integer getEnergia() {
		return energia;
	}
	/**
	 * Cambia la posada destino y la energia que custa recorrer el camino.
	 * @param dst Un objeto Posada con la nueva posada destino.
	 * @param enrg Un entero con el valor de recorrer el camino.
	 */
	public void cambiarDestino(Posada dst,int enrg){
		this.destino= dst;
		if(enrg<1){
			this.energia=1;
		}
		else{
			this.energia=enrg;
		}
	}
	/**
	 * Devuelve el coste especial.
	 * @return Entero con el valor del coste especial.
	 */
	public int costeEspecial(){
		return 0;
	}
	/**
	 * Devuelve el coste especial.
	 * @return Entero con el valor del coste real.
	 */
	public int costeReal(){
		return this.costeEspecial()+this.energia;
	}
	/**
	 * Devuelve si el camino es una trampa.
	 * @return Un boleano con el valor de si el camino es una trampa.
	 */
	public boolean esTrampa	(){
		return false;
	}
	/**
	 * Devuelve los valores del objeto como una cadena.
	 * @return String con el valor del objeto convertido a cadena.
	 */
	public String toString() {
		return "("+this.origen.getNombre()+"--"+this.energia+"-->"+this.origen.getNombre();
	}
}
