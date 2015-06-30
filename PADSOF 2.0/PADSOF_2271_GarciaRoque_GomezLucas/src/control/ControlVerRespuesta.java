package control;

import estadisticas.PieChartSample;
import gui.*;

import java.awt.event.*;

import mailUam.MailUam;

/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase ControlVerRespuesta
 */
public class ControlVerRespuesta implements ActionListener {
	

	private MailUam modelo;
	private Ventana v;
	
	/**
	 * Constructor de la clase CntrolVerRespuesta
	 * @param v ventana de la aplicacion
	 * @param modelo Aplicacion donde estan todos los datos
	 */
	public ControlVerRespuesta(Ventana v, MailUam modelo) {
		this.modelo = modelo;
		this.v = v;
	}

	
	
	/**
	 * @return the modelo
	 */
	public MailUam getModelo() {
		return modelo;
	}



	/**
	 * @param modelo the modelo to set
	 */
	public void setModelo(MailUam modelo) {
		this.modelo = modelo;
	}



	/**
	 * @return the v
	 */
	public Ventana getV() {
		return v;
	}



	/**
	 * @param v the v to set
	 */
	public void setV(Ventana v) {
		this.v = v;
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {

		Object source = arg0.getSource();
		System.out.println("Activando controlador: " + getClass());
		GUIVerRespuesta verRespuesta = getV().getVerRespuesta();
		if (source.equals(verRespuesta.getBotonMensajes())) {
			GUIMensaje menuMensaje = getV().getMensajes();
			menuMensaje.setValores();
			System.out.println("Cambiando a Mensajes");
			v.cambiarPanelMenuMensajes();
		} else if (source.equals(verRespuesta.getBotonGrupos())) {
			System.out.println("Cambiando a Grupos");
			GUIMenuGrupo menuGrupo = getV().getMenuGrupos();
			menuGrupo.setValores();
			v.cambiarPanelMenuGrupos();
		} else if (source.equals(verRespuesta.getBotonVerPerfil())) {
			System.out.println("Cambiando a Perfil");
			GUIVerPerfil verPerfil = v.getPerfil();
			verPerfil.setValores(modelo.getLogged());
			v.cambiarPanelPerfil();
		} else if (source.equals(verRespuesta.getBotonSalir())) {
			System.out.println("Cambiando a Salir");
			modelo.logout();
			v.cambiarPanelLogin();
		}else if(source.equals(verRespuesta.getButtonAtras())){
			v.cambiarPanelListarPregunta();
		}else if(source.equals(verRespuesta.getButtonEstadisticas())){
			int tot =getV().getListarPreguntas().getGrupo().getListaUsuarios().size() -1;
			System.out.println(getV().getListarPreguntas().getGrupo().getListaUsuarios());
			int recibido=verRespuesta.getPregunta().getListaRespuestas().size();
			PieChartSample.muestraGrafico("Respuestas", recibido, tot - recibido);
		}
	}

}
