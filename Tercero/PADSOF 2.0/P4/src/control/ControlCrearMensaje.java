package control;

import gui.*;

import java.awt.event.*;

import javax.swing.JOptionPane;

import usuario.Usuario;
import mailUam.MailUam;
import mensaje.MensajeUsuario;

/**
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 *         Clase ControlCrearMensaje
 */
public class ControlCrearMensaje implements ActionListener {

	private MailUam modelo;
	private Ventana v;

	/**
	 * Constructor de ControlCrearMensaje
	 * 
	 * @param v
	 *            ventana de la GUI
	 * @param modelo
	 *            modelo de la aplicacion
	 */
	public ControlCrearMensaje(Ventana v, MailUam modelo) {
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
	 * @param modelo
	 *            the modelo to set
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
	 * @param v
	 *            the v to set
	 */
	public void setV(Ventana v) {
		this.v = v;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		System.out.println("Activando controlador: " + getClass());
		GUICrearMensaje crearMensaje = v.getCrearMensaje();
		if (source.equals(crearMensaje.getBotonMensajes())) {
			System.out.println("Cambiando a Mensajes");
			GUIMensaje menuMensaje = v.getMensajes();
			menuMensaje.setValores();
			v.cambiarPanelMenuMensajes();
		} else if (source.equals(crearMensaje.getBotonGrupos())) {
			System.out.println("Cambiando a Grupos");
			GUIMenuGrupo menuGrupo = v.getMenuGrupos();
			menuGrupo.setValores();
			v.cambiarPanelMenuGrupos();
		} else if (source.equals(crearMensaje.getBotonVerPerfil())) {
			System.out.println("Cambiando a Perfil");
			GUIVerPerfil verPerfil = v.getPerfil();
			verPerfil.setValores(modelo.getLogged());
			v.cambiarPanelPerfil();
		} else if (source.equals(crearMensaje.getBotonSalir())) {
			System.out.println("Cambiando a Salir");
			modelo.logout();
			v.cambiarPanelLogin();
		} else if (source.equals(crearMensaje.getBotonEnviar())) {
			Usuario dest = null;
			getModelo().cargarUsuarios();
			if (crearMensaje.getTextCorreoText().equals("")) {
				JOptionPane.showMessageDialog(crearMensaje,
						"No se especifico ningun destinatario", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			dest = getModelo().buscarUsuario(crearMensaje.getTextCorreoText());
			if (dest == null) {
				JOptionPane.showMessageDialog(crearMensaje,
						"El usuario especificado no existe", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			MensajeUsuario msg = new MensajeUsuario(dest,
					crearMensaje.getTextMensajeText(),
					crearMensaje.getTextAsuntoText(), getModelo().getLogged());
			dest.addMensajeBuzon(msg);
			System.out.println(dest.getBuzon().getMensajes());
			getModelo().guardarUsuarios();
			getModelo().cargarUsuarios();
			v.getMensajes().setValores();
			v.cambiarPanelMenuMensajes();
		}
	}

}
