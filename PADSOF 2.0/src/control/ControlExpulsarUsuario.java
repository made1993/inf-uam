package control;

import gui.*;

import java.awt.Component;
import java.awt.event.*;

import javax.swing.JCheckBox;

import usuario.Usuario;
import mailUam.MailUam;

/**
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 *         Clase ControlExpulsarUsuario
 */
public class ControlExpulsarUsuario implements ActionListener {
	private MailUam modelo;
	private Ventana v;

	/**
	 * Constructor de ControlExpulsarUsuario
	 * 
	 * @param v
	 *            ventana de la aplicacion
	 * @param modelo
	 *            modelo de la aplicacion
	 */
	public ControlExpulsarUsuario(Ventana v, MailUam modelo) {
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
		GUIExpulsarUsuario expulsarUsuario = v.getExpulsarUsuario();
		if (source.equals(expulsarUsuario.getBotonMensajes())) {
			GUIMensaje menuMensaje = getV().getMensajes();
			menuMensaje.setValores();
			System.out.println("Cambiando a Mensajes");
			v.cambiarPanelMenuMensajes();
		} else if (source.equals(expulsarUsuario.getBotonGrupos())) {
			System.out.println("Cambiando a Grupos");
			GUIMenuGrupo menuGrupo = v.getMenuGrupos();
			menuGrupo.setValores();
			v.cambiarPanelMenuGrupos();
		} else if (source.equals(expulsarUsuario.getBotonVerPerfil())) {
			System.out.println("Cambiando a Perfil");
			GUIVerPerfil verPerfil = v.getPerfil();
			verPerfil.setValores(modelo.getLogged());
			v.cambiarPanelPerfil();
		} else if (source.equals(expulsarUsuario.getBotonSalir())) {
			System.out.println("Cambiando a Salir");
			modelo.logout();
			v.cambiarPanelLogin();
		} else if (source.equals(expulsarUsuario.getButtonExpulsar())) {
			System.out.println("Expulsando usuarios");
			getModelo().cargarUsuarios();
			getModelo().cargarGrupos();
			getModelo().actualizarLogged();
			expulsarUsuario.setGrupo(getModelo().buscarGrupo(expulsarUsuario.getGrupo().getNombre()));
			for (Component comp : expulsarUsuario.getUsuarios().getComponents()) {
				System.out.println(comp);
				JCheckBox c = (JCheckBox) comp;
				if (c.isSelected()) {
					Usuario u = getModelo().buscarUsuario(c.getText());
					expulsarUsuario.getGrupo().removeUsuario(u);
					u.removeGrupo(expulsarUsuario.getGrupo());
				}
			}
			getModelo().guardarUsuarios();
			getModelo().guardarUsuario();
			getModelo().guardarGrupos();
			getV().getVerGrupos().setValores(expulsarUsuario.getGrupo());
			getV().cambiarPanelVerGrupo();
		}
	}

}
