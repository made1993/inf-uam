package p3;

import java.util.*;
public class Explorador {
	private String nombre;
	private int energia;
	private Posada pos;
	
	public Explorador(String nombre, int energia, Posada iniPos) {
		super();
		this.nombre = nombre;
		this.energia = energia;
		this.pos = iniPos;
	}

	
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return the energia
	 */
	public int getEnergia() {
		return energia;
	}

	/**
	 * @return the pos
	 */
	public Posada getPos() {
		return pos;
	}

	public boolean recorre(Camino c){
		int i;
		for(i=0;i<this.pos.getNumCaminos();i++){
			if(c.equals( this.pos.getCamino(i))
					&& this.pudeRecorrerCamino(this.pos.getCamino(i))
					&&this.puedeAlojarseEn(this.pos.getCamino(i).getDestino())){

				this.energia-=this.pos.getCamino(i).costeReal();
				this.energia+=this.pos.getCamino(i).getDestino().getEnerRec();
				this.pos=this.pos.getCamino(i).getDestino();
				return true;
			}
		}
		return false;
	}
	public boolean recorre(Posada... ps){
		Boolean ret =true;
		for (Posada p: ps){
			if(this.recorre(this.pos.getCamino(p))==false)
				ret=false;
		}
		return ret;
	}
	public boolean pudeRecorrerCamino(Camino c){
		if(c==null)
			return false;
		if(this.energia>c.costeReal())
			return true;
		return false;
	}
	public boolean puedeAlojarseEn(Posada p){
		return true;
	}
	public String toString() {
		return this.nombre+"(e:"+this.energia+") en "+this.pos.getNombre();
	}
}
