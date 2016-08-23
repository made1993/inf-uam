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
 *         Clase ControlBUscarUsuario
 */
public class ControlBuscarUsuario implements ActionListener {

	private MailUam modelo;
	private Ventana v;

	/**
	 * COnstructor de ControlBuscarUsuario
	 * 
	 * @param v
	 *            Modelo de las ventanas
	 * @param modelo
	 * 		Aplicacion donde estan los datos
	 */
	public ControlBuscarUsuario(Ventana v, MailUam modelo) {
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
		// TODO Auto-generated method stub
		Object source = arg0.getSource();
		System.out.println("Activando controlador: " + getClass());
		GUIBuscarUsuario buscarUsuario = v.getBuscarUsuarios();
		if (source.equals(buscarUsuario.getBotonMensajes())) {
			GUIMensaje mensaje = v.getMensajes();
			mensaje.setValores();
			System.out.println("Cambiando a Mensajes");
			v.cambiarPanelMenuMensajes();
		} else if (source.equals(buscarUsuario.getBotonGrupos())) {
			System.out.println("Cambiando a Grupos");
			GUIMenuGrupo menuGrupo = v.getMenuGrupos();
			menuGrupo.setValores();
			v.cambiarPanelMenuGrupos();
		} else if (source.equals(buscarUsuario.getBotonVerPerfil())) {
			System.out.println("Cambiando a Perfil");
			GUIVerPerfil verPerfil = v.getPerfil();
			verPerfil.setValores(modelo.getLogged());
			v.cambiarPanelPerfil();
		} else if (source.equals(buscarUsuario.getBotonSalir())) {
			System.out.println("Cambiando a Salir");
			modelo.logout();
			v.cambiarPanelLogin();
		} else if (source.equals(buscarUsuario.getButtonBuscar())) {
			getModelo().cargarUsuarios();
			buscarUsuario.setValores(
					getModelo().buscarUsuarios(
							buscarUsuario.getTextBuscarText()),
					buscarUsuario.getGrupo());
		} else if (source.equals(buscarUsuario.getButtonAnadir())) {
			getModelo().cargarUsuarios();
			getModelo().cargarGrupos();
			buscarUsuario.setGrupo(getModelo().buscarGrupo(
					buscarUsuario.getGrupo().getNombre()));

			for (Component check : buscarUsuario.getPanelResultados()
					.getComponents()) {
				if (check instanceof JCheckBox) {
					JCheckBox jcheck = (JCheckBox) check;
					if (jcheck.isSelected()) {
						Usuario u = getModelo().cargarUsuario(jcheck.getText());
						buscarUsuario.getGrupo().addUsuario(u);
						u.addGrupo(buscarUsuario.getGrupo());
					}
				}

			}
			getModelo().guardarGrupos();
			getModelo().guardarUsuarios();
			v.getAnadirParticipante().setValores(buscarUsuario.getGrupo());
			v.cambiarPanelAnadirParticipanteGrupo();
		}
	}

}
