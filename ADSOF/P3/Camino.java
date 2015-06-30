/**
 * 
 */
package p3;
import p3.Posada;

/**
 * @author ccpp
 *
 */
public class Camino {

	private  Posada origen;
	private  Posada destino;
	private  int energia;
	private static int acumulado;
	/**
	 * 
	 */
	public Camino(Posada origen,Posada destino ,int energia) {
		this.origen=origen;
		this.destino=destino;
		this.energia=energia;
	}
	
	public static int getAcumulado() {
		return acumulado;
	}

	public Posada getOrigen() {
		return origen;
	}
	public Posada getDestino() {
		return destino;
	}
	public Integer getEnergia() {
		return energia;
	}
	
	public void cambiarDestino(Posada dst,int enrg){
		this.destino= dst;
		if(enrg<1){
			this.energia=1;
		}
		else{
			this.energia=enrg;
		}
	}
	public int costeEspecial(){
		return 0;
	}
	
	public int costeReal(){
		return this.costeEspecial()+this.energia;
	}
	/**
	 * @return
	 */
	public boolean esTrampa	(){
		return false;
	}
	
	public String toString() {
		return "("+this.origen.getNombre()+"--"+this.energia+"-->"+this.origen.getNombre();
	}
}
