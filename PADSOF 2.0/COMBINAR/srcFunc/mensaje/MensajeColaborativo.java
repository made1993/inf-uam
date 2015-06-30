package mensaje;

import grupo.*;

import java.util.ArrayList;

import usuario.Usuario;

public class MensajeColaborativo extends MensajeGrupo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<MensajeColaborativo> respuestas;

	private boolean cerrado;
	
	public MensajeColaborativo(int id, String cuerpo, Grupo grupo,
			Usuario remitente) {
		super(id, cuerpo, remitente);
		respuestas = new ArrayList<>();
		cerrado=false;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return estado del mensaje
	 */
	public boolean isCerrado(){
		return cerrado;
	}
	
	/**
	 * 
	 * @param cerrado
	 */
	public void setCerrado(boolean cerrado){
		this.cerrado = cerrado;
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

	public boolean addRespuesta(MensajeColaborativo r) {
		return respuestas.add(r);
	}
	
	
	
	

}
