package mensaje;



import java.util.ArrayList;

import usuario.Usuario;

public class MensajeColaborativo extends MensajeGrupo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<MensajeColaborativo> respuestas;

	private boolean cerrado;
	
	public MensajeColaborativo(String cuerpo, Usuario remitente) {
		super(cuerpo, remitente);
		respuestas = new ArrayList<>();
		cerrado=false;
		
	}
	
	/**
	 * @return estado del mensaje
	 */
	public boolean isCerrado(){
		return cerrado;
	}
	
	/**
	 * 
	 * @param cerrado pone el set cerrado a cerrado
	 */
	public void setCerrado(boolean cerrado){
		this.cerrado = cerrado;
		for(MensajeColaborativo m:respuestas){
			m.setCerrado(cerrado);
		}
	}

	/**
	 * @return the respuestas
	 */
	public ArrayList<MensajeColaborativo> getRespuestas() {
		return respuestas;
	}

	/**
	 * @param respuestas the respuestas to set
	 */
	public void setRespuestas(ArrayList<MensajeColaborativo> respuestas) {
		this.respuestas = respuestas;
	}

	/**
	 * Añade una respuesta a al mensaje
	 * @param r el mensaje
	 * @return true si lo anade false si on
	 */
	public boolean addRespuesta(MensajeColaborativo r) {
		if(isCerrado())
			return false;
		return respuestas.add(r);
	}
	
	/**
	 * Busca un mensaje colaborativo por su id
	 * @param id id del mensaje
	 * @return el mensaje que busca
	 */
	public MensajeColaborativo buscarMensaje(int id) {
		for(MensajeColaborativo m:getRespuestas()){
			if(m.getIdMensaje()==id){
				return m;
			}
			MensajeColaborativo ms = m.buscarMensaje(id);
			if(ms!=null){
				return ms;
			}
		}
		return null;
	}
	
	

}
