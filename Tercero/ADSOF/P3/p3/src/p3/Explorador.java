package p3;

public class Explorador {
	private String nombre;
	private int energia;
	private Posada pos;
	
	/**
	 * Constructor de la clase Explorador.
	 * @param nombre Nombre del explorador.
	 * @param energia Energia del explorador.
	 * @param iniPos Posada en la que se encuentra el explorador.
	 */
	public Explorador(String nombre, int energia, Posada iniPos) {
		super();
		this.nombre = nombre;
		this.energia = energia;
		this.pos = iniPos;
	}

	
	/**
	 * Devuelve el nombre del explorador.
	 * @return String con el nombre del explorador.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Devuelve la energia del explorador.
	 * @return Entero con la energia del explorador.
	 */
	public int getEnergia() {
		return energia;
	}

	/**
	 * Devuelve la porsada donde se encuentra el explorador.
	 * @return Objeto con la posada donde se encuentra el explorador.
	 */
	public Posada getPos() {
		return pos;
	}
	/**
	 * Dado un camino devuelve TRUE si el explorador
	 * puede recorrer el camino y FALSE sino puede recorrerlo.
	 * @param c Camino a recorrer.
	 * @return Un valor boleano con el resultado de la operacion.
	 */
	public boolean recorre(Camino c){
		
		if (c == null || c.getOrigen() != this.pos) 
			return false;
		
		Posada pos_dest = c.getDestino();
			if (this.puedeRecorrerCamino(c) 
					&& this.puedeAlojarseEn(pos_dest)) {
				this.energia-=this.pos.getCamino(c.getDestino()).costeReal();
				this.energia+=this.pos.getCamino(c.getDestino()).getDestino().getEnerRec();
				this.pos=pos_dest;
				return true;
			}
		return false;
	}
	/**
	 * Dada una lista de Posadas recorre caminos hasta llegar a las
	 * posadas indicadas, si puede.
	 * Si no se puede recorrer un camino la funcion devolvera FALSE pero
	 * recorrera todos los caminos hasta llegar a la posada.  
	 * @param ps Una lista con las posadas que queremos atravesar.
	 * @return Un booleano que nos indica si se ha podido recorrer todas las psadas.
	 */
	public boolean recorre(Posada... ps){
		Boolean ret =true;
		for (Posada p: ps){
			if(this.recorre(this.pos.getCamino(p))==false)
				ret=false;
		}
		return ret;
	}
	/**
	 * Metodo que dado un camino devuelve si se puede recorrer el camino o no.
	 * @param c Camino a recorrer
	 * @return Boleano que nos indica si se puede recorrer el camino.
	 */
	public boolean puedeRecorrerCamino(Camino c){
		if(c==null)
			return false;
		if(this.energia>c.costeReal())
			return true;
		return false;
	}
	/**
	 * Metodo que indica si el explorador puede alojarse en una posada.
	 * @param p Posada en la que nos podiramos alojar.
	 * @return Boleano con el valor de si nos podemos alojar o no.
	 */
	public boolean puedeAlojarseEn(Posada p){
		return true;
	}
	/**
	 * Metodo que convierte el objeto a formato String.
	 * @return String con la informacion del explorador.
	 */
	public String toString() {
		return this.nombre+"(e:"+this.energia+") en "+this.pos.getNombre();
	}
}
