package control;

import gui.*;
import usuario.*;

import java.awt.event.*;

import javax.swing.*;

import mailUam.MailUam;

/**
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 *         Clase ControlListarPregunta
 */
public class ControlListarPregunta implements ActionListener {

	private MailUam modelo;
	private Ventana v;

	/**
	 * Constructor de ControlListarPregunta
	 * 
	 * @param v
	 *            ventana de la aplicacion
	 * @param modelo
	 *            modelo de la aplicacion
	 */
	public ControlListarPregunta(Ventana v, MailUam modelo) {
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
		GUIListarPregunta listarPreguntas = v.getListarPreguntas();
		if (source.equals(listarPreguntas.getBotonMensajes())) {
			GUIMensaje menuMensaje = getV().getMensajes();
			menuMensaje.setValores();
			System.out.println("Cambiando a Mensajes");
			v.cambiarPanelMenuMensajes();
		} else if (source.equals(listarPreguntas.getBotonGrupos())) {
			System.out.println("Cambiando a Grupos");
			GUIMenuGrupo menuGrupo = v.getMenuGrupos();
			menuGrupo.setValores();
			v.cambiarPanelMenuGrupos();
		} else if (source.equals(listarPreguntas.getBotonVerPerfil())) {
			System.out.println("Cambiando a Perfil");
			GUIVerPerfil verPerfil = v.getPerfil();
			verPerfil.setValores(modelo.getLogged());
			v.cambiarPanelPerfil();
		} else if (source.equals(listarPreguntas.getBotonSalir())) {
			System.out.println("Cambiando a Salir");
			modelo.logout();
			v.cambiarPanelLogin();
		} else if (source.equals(listarPreguntas.getButtonCrearPregunta())) {
			System.out.println("crear pregunta");
			if (getModelo().getLogged() instanceof Profesor) {
				System.out.println("Grupo listar pregunta:"
						+ listarPreguntas.getGrupo());
				v.getCrearPregunta().setValores(listarPreguntas.getGrupo());
				v.cambiarPanelCrearPregunta();
			} else {
				JOptionPane
						.showMessageDialog(listarPreguntas,
								"Debe ser Profesor", "Error",
								JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else if (source.equals(listarPreguntas.getButtonCrearRespuesta())) {
			System.out.println("crear respuesta");
			if (getModelo().getLogged() instanceof Estudiante) {
				for (JRadioButton b : listarPreguntas.getListaBotones()) {
					if (b.isSelected()) {
						v.getCrearRespuesta().setValores(
								listarPreguntas
										.getGrupo()
										.getListaPreguntas()
										.get(Integer.decode(b.getText().split(
												"a")[1]) - 1));
						v.cambiarPanelCrearRespuesta();
						return;
					}
				}
			} else {
				JOptionPane.showMessageDialog(listarPreguntas,
						"Debe ser Estudiante", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else if (source.equals(listarPreguntas.getButtonVerRespuestas())) {
			System.out.println("ver respuesta");
			getModelo().cargarGrupos();
			System.out.println(listarPreguntas.getGrupo().getListaPreguntas()
					.get(0).getListaRespuestas());
			if (getModelo().getLogged() instanceof Profesor) {
				for (JRadioButton b : listarPreguntas.getListaBotones()) {
					if (b.isSelected()) {
						getModelo().cargarGrupos();
						v.getVerRespuesta().setValores(
								listarPreguntas
										.getGrupo()
										.getListaPreguntas()
										.get(Integer.decode(b.getText().split(
												"a")[1]) - 1));
						v.cambiarPanelVerRespuesta();
						return;
					}
				}

			} else {
				JOptionPane
						.showMessageDialog(listarPreguntas,
								"Debe ser Profesor", "Error",
								JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

	}

}
