package control;

import gui.*;

import java.awt.event.*;
import java.util.ArrayList;

import usuario.Usuario;
import mailUam.MailUam;

public class ControlAnadirParticipante implements ActionListener {
	

	private MailUam modelo;
	private Ventana v;
	
	public ControlAnadirParticipante(Ventana v, MailUam modelo) {
		this.modelo = modelo;
		this.v = v;
	}

	
	
	public MailUam getModelo() {
		return modelo;
	}



	public void setModelo(MailUam modelo) {
		this.modelo = modelo;
	}



	public Ventana getV() {
		return v;
	}



	public void setV(Ventana v) {
		this.v = v;
	}

	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		System.out.println("Activando controlador: "+getClass());
		GUIAnadirParticipante anadirParticipantes = v.getAnadirParticipante();
		if(source.equals(anadirParticipantes.getBotonMensajes())){
			GUIMensaje mensaje = v.getMensajes();
			mensaje.setValores();
			System.out.println("Cambiando a Mensajes");
			v.cambiarPanelMenuMensajes();
		}else if(source.equals(anadirParticipantes.getBotonGrupos())){
			System.out.println("Cambiando a Grupos");
			GUIMenuGrupo menuGrupo = v.getMenuGrupos();
			menuGrupo.setValores();
			v.cambiarPanelMenuGrupos();
		}else if(source.equals(anadirParticipantes.getBotonVerPrefil())){
			System.out.println("Cambiando a Perfil");
			GUIVerPerfil verPerfil = v.getPerfil();
			verPerfil.setValores(modelo.getLogged());
			v.cambiarPanelPerfil();
		}else if(source.equals(anadirParticipantes.getBotonSalir())){
			System.out.println("Cambiando a Salir");
			modelo.logout();
			v.cambiarPanelLogin();
		}
		else if (source.equals(anadirParticipantes.getButtonBuscar())){
			System.out.println("Cambiando buscar usuario");
			getModelo().cargarUsuarios();
			v.getBuscarUsuarios().setValores(getModelo().buscarUsuarios(v.getAnadirParticipante().
					getTextBuscarText()), v.getAnadirParticipante().getGrupo());
			v.cambiarPanelBuscarUsuarios();
		}
		else if (source.equals(anadirParticipantes.getButtonFinalizar())){
			System.out.println("Cambiando a menu");
			v.cambiarPanelMenu();
		}
	}

}
