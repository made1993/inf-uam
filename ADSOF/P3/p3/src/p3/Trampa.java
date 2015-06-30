package p3;

public class Trampa extends Camino {
	
	private int factor_coste_extra;
	private int prob_ret_obligado;
	
	/**
	 * Constructor de la clase Trampa
	 * @param origen Posada origen.
	 * @param destino Posada Destino.
	 * @param energia Energia que se pierde al recorrer el camino.
	 * @param factor_c_ext 
	 * @param prob_r_oblig
	 */
	public Trampa(Posada origen, Posada destino, int energia, int factor_c_ext, int prob_r_oblig) {
		super(origen, destino, energia);
		this.factor_coste_extra = factor_c_ext;
		this.prob_ret_obligado = prob_r_oblig;
	}
	/**
	 * Coste de recorrer el camino.
	 * @return Entero con el coste de recorrer el camino.
	 */
	public int costeEspecial(){
		return this.factor_coste_extra*this.energia;
	}

	/**
	 * Metodo que devuelve si es una trampa.
	 * @return Devuelve TRUE.
	 */
	public boolean esTrampa	(){
		return true;
	}
}
