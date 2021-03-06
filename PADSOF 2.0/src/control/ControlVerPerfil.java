package control;

import gui.*;

import java.awt.event.*;

import javax.swing.JOptionPane;

import mailUam.MailUam;

/**
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 *         Clase ControlBuscarGrupo
 */
public class ControlVerPerfil implements ActionListener {

	private MailUam modelo;
	private Ventana v;

	/**
	 * Contructor de ControlVerPerfil
	 * 
	 * @param v ventana de la aplicacion
	 * @param modelo modelo de la aplicacion
	 */
	public ControlVerPerfil(Ventana v, MailUam modelo) {
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

		Ventana v = getV();
		MailUam modelo = getModelo();

		GUIVerPerfil verPerfil = v.getPerfil();
		if (source.equals(verPerfil.getBotonMensajes())) {
			GUIMensaje menuMensaje = getV().getMensajes();
			menuMensaje.setValores();
			v.cambiarPanelMenuMensajes();
		} else if (source.equals(verPerfil.getBotonGrupos())) {
			GUIMenuGrupo menuGrupo = v.getMenuGrupos();
			menuGrupo.setValores();
			v.cambiarPanelMenuGrupos();
		} else if (source.equals(verPerfil.getBotonVerPerfil())) {
			verPerfil.setValores(modelo.getLogged());
			v.cambiarPanelPerfil();
		} else if (source.equals(verPerfil.getBotonSalir())) {
			modelo.logout();
			v.cambiarPanelLogin();
		} else if (source.equals(verPerfil.getBotonGuardar())) {
			modelo.getLogged().setNombre(verPerfil.getCampoNombreText());
			modelo.getLogged().setApellido(verPerfil.getCampoApellidoText());
			modelo.getLogged().setPassword(verPerfil.getCampoContrasenaText());
			modelo.guardarUsuario();
			JOptionPane.showMessageDialog(null,
					"Los Datos se han guardado correctamente.");
		}
	}

}
