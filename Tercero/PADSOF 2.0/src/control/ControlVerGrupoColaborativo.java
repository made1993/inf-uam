/**
 * 
 */
package control;

import grupo.*;
import gui.*;

import java.awt.event.*;

import javax.swing.JOptionPane;

import mailUam.MailUam;
import mensaje.MensajeColaborativo;

/**
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 *         Clase ControlVerGrupoColaborativo
 */
public class ControlVerGrupoColaborativo implements ActionListener {
	private MailUam modelo;
	private Ventana v;

	/**
	 * Constructor de ControlVerGrupoColaborativo
	 * @param v ventana de la aplicacion
	 * @param modelo modelo dela aplicacion
	 */
	public ControlVerGrupoColaborativo(Ventana v, MailUam modelo) {
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
		GUIVerGrupoColaborativo verGrupoColaborativo = v
				.getVerGrupoColaborativo();

		if (source.equals(verGrupoColaborativo.getBotonMensajes())) {
			GUIMensaje menuMensaje = getV().getMensajes();
			menuMensaje.setValores();
			System.out.println("Cambiando a Mensajes");
			v.cambiarPanelMenuMensajes();
		} else if (source.equals(verGrupoColaborativo.getBotonGrupos())) {
			System.out.println("Cambiando a Grupos");
			GUIMenuGrupo menuGrupo = v.getMenuGrupos();
			menuGrupo.setValores();
			v.cambiarPanelMenuGrupos();
		} else if (source.equals(verGrupoColaborativo.getBotonVerPerfil())) {
			System.out.println("Cambiando a Perfil");
			GUIVerPerfil verPerfil = v.getPerfil();
			verPerfil.setValores(modelo.getLogged());
			v.cambiarPanelPerfil();
		} else if (source.equals(verGrupoColaborativo.getBotonSalir())) {
			System.out.println("Cambiando a Salir");
			modelo.logout();
			v.cambiarPanelLogin();
		} else if (source.equals(verGrupoColaborativo.getBotonEntrar())) {
			System.out.println("Cambiando a Ver Grupo(Entrar)");
			MensajeColaborativo mensaje = null;
			System.out.println(verGrupoColaborativo.getTablaMensajes()
					.getSelectedRow());
			if (verGrupoColaborativo.getTablaMensajes().getSelectedRow() < 0) {
				JOptionPane.showMessageDialog(verGrupoColaborativo,
						"Seleccione un mensaje por favor", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (verGrupoColaborativo.isPrincipal()) {
				getModelo().cargarGrupos();
				Grupo g = getModelo()
						.buscarGrupo(
								verGrupoColaborativo.getGrupoColaborativo()
										.getNombre());
				mensaje = (MensajeColaborativo) g.getListaMensajes().get(
						verGrupoColaborativo.getTablaMensajes()
								.getSelectedRow());
			} else {
				getModelo().cargarGrupos();
				mensaje = verGrupoColaborativo
						.getMensajeColaborativo()
						.getRespuestas()
						.get(verGrupoColaborativo.getTablaMensajes()
								.getSelectedRow());
			}
			verGrupoColaborativo.setValores(mensaje);
		} else if (source.equals(verGrupoColaborativo.getBotonEnviar())) {
			System.out.println("Cambiando a ver Grupo(Enviar)");
			String text = verGrupoColaborativo.getCampoEscribir().getText();
			if (text == "") {
				JOptionPane.showMessageDialog(verGrupoColaborativo,
						"Escriba un mensaje por favor", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			getModelo().cargarGrupos();
			getModelo().cargarUsuarios();
			getModelo().actualizarLogged();
			MensajeColaborativo mensaje = new MensajeColaborativo(text,
					getModelo().getLogged());
			System.out.println("Imprimiendo mensaje: " + mensaje);
			Grupo grupo = getModelo().buscarGrupo(
					verGrupoColaborativo.getGrupoColaborativo().getNombre());
			if (verGrupoColaborativo.isPrincipal()) {
				System.out.println("Enviando principal");
				grupo.addMensaje(mensaje);
				getModelo().guardarGrupos();
				getModelo().cargarGrupos();
				System.out.println(grupo.getListaMensajes().size());
				verGrupoColaborativo.setValores((GrupoColaborativo) grupo);
			} else {
				System.out.println("Enviando a mensaje");
				int id = verGrupoColaborativo.getMensajeColaborativo()
						.getIdMensaje();
				verGrupoColaborativo
						.setMensajeColaborativo(((GrupoColaborativo) grupo)
								.buscarMensaje(id));
				if (verGrupoColaborativo.getMensajeColaborativo().isCerrado()) {
					JOptionPane.showMessageDialog(verGrupoColaborativo,
							"El mensaje ha terminado su colaboracion", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				verGrupoColaborativo.getMensajeColaborativo().addRespuesta(
						mensaje);
				getModelo().guardarGrupos();
				System.out.println("Mensaje Colaborativo size "
						+ verGrupoColaborativo.getMensajeColaborativo()
								.getRespuestas().size());
				verGrupoColaborativo.setValores(verGrupoColaborativo
						.getMensajeColaborativo());
			}
			getModelo().guardarUsuarios();
			return;
		} else if (source.equals(verGrupoColaborativo.getBotonTerminar())) {
			System.out.println("Cambiando a ver Grupo(Terminar Subscripcion)");
			MensajeColaborativo mensaje = null;
			System.out.println(verGrupoColaborativo.getTablaMensajes()
					.getSelectedRow());
			if (verGrupoColaborativo.getTablaMensajes().getSelectedRow() < 0) {
				JOptionPane.showMessageDialog(verGrupoColaborativo,
						"Seleccione un mensaje por favor", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (verGrupoColaborativo.isPrincipal()) {
				getModelo().cargarGrupos();
				Grupo g = getModelo()
						.buscarGrupo(
								verGrupoColaborativo.getGrupoColaborativo()
										.getNombre());
				mensaje = (MensajeColaborativo) g.getListaMensajes().get(
						verGrupoColaborativo.getTablaMensajes()
								.getSelectedRow());
				mensaje.setCerrado(true);
				getModelo().guardarGrupos();
			} else {
				getModelo().cargarGrupos();
				mensaje = verGrupoColaborativo
						.getMensajeColaborativo()
						.getRespuestas()
						.get(verGrupoColaborativo.getTablaMensajes()
								.getSelectedRow());
				mensaje.setCerrado(true);
				getModelo().guardarGrupos();
			}
			return;
		}
	}

}
