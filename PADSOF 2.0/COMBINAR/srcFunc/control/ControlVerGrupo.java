package control;

import gui.*;

import java.awt.event.*;

import javax.swing.JOptionPane;

import mailUam.MailUam;
import mensaje.MensajeGrupo;

public class ControlVerGrupo implements ActionListener {
	

	private MailUam modelo;
	private Ventana v;
	
	public ControlVerGrupo(Ventana v, MailUam modelo) {
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
		// TODO Auto-generated method stub
		Object source = arg0.getSource();
		System.out.println("Activando controlador: " + getClass());
		GUIVerGrupo menu = getV().getVerGrupos();
		if (source.equals(menu.getBotonMensajes())) {
			GUIMensaje menuMensaje = getV().getMensajes();
			menuMensaje.setValores();
			System.out.println("Cambiando a Mensajes");
			v.cambiarPanelMenuMensajes();
		} else if (source.equals(menu.getBotonGrupos())) {
			System.out.println("Cambiando a Grupos");
			GUIMenuGrupo menuGrupo = getV().getMenuGrupos();
			menuGrupo.setValores();
			v.cambiarPanelMenuGrupos();
		} else if (source.equals(menu.getBotonVerPrefil())) {
			System.out.println("Cambiando a Perfil");
			GUIVerPerfil verPerfil = v.getPerfil();
			verPerfil.setValores(modelo.getLogged());
			v.cambiarPanelPerfil();
		} else if (source.equals(menu.getBotonSalir())) {
			System.out.println("Cambiando a Salir");
			modelo.logout();
			v.cambiarPanelLogin();
		} else if (source.equals(menu.getBotonEnviar())) {
			System.out.println("Enviando Mensaje");
			String cuerpo=menu.getCampoEscribir().getText();
			if(cuerpo==null || cuerpo==""){
				JOptionPane.showMessageDialog(menu,
						"Mensaje Vacio", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			getModelo().cargarGrupo(menu.getGrupo().getNombre());
			menu.getGrupo().addMensaje(new MensajeGrupo(0, cuerpo, getModelo().getLogged()));
			getModelo().guardarGrupo(menu.getGrupo());
			menu.setValores(menu.getGrupo());
			
		} else if (source.equals(menu.getBotonPreguntas())) {
			System.out.println("Cambiando a Preguntas");
			GUIListarPregunta listarPregunta = getV().getListarPreguntas();
			//listarPregunta.setValores(menu.getGrupo());
			v.cambiarPanelListarPregunta();			
		}
	}

}
