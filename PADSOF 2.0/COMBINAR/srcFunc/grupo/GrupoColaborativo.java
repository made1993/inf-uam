package grupo;


import usuario.*;
import mensaje.*;



public class GrupoColaborativo extends Grupo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	public GrupoColaborativo(int idGrupo, String nombre, boolean privado, Usuario moderado) {
		super(idGrupo, nombre, privado, moderado);
		setSubGrupos(null);
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isGrupoColaborativo() {
		return true;
	}
	
	
	/**
	 * Termina la colaboracion de un mensaje
	 * @param m mensaje para colaborar
	 * @return
	 */
	public boolean terminarColaboracion(MensajeColaborativo m){
		m.setCerrado(true);
		return true;
	}
	
	public boolean anadirRespuesta(MensajeColaborativo m,MensajeColaborativo r){
		if(m.isCerrado()){
			return false;
		}else{
			return m.addRespuesta(r);
		}
	}
	
	
}
