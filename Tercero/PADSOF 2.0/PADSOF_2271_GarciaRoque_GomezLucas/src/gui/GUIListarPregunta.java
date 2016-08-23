package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.*;

import usuario.*;
import grupo.*;

import javax.swing.*;

import layout.SpringUtilities;
import mailUam.*;
import mensaje.Pregunta;

/**
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 *         Clase GUIListarPregunta
 */
public class GUIListarPregunta extends GUIMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton buttonCrearRespuesta;
	private JButton buttonCrearPregunta;
	private JButton buttonVerRespuestas;
	private ButtonGroup botones;
	private GrupoEstudio grupo;
	private JPanel panel;
	private ArrayList<JRadioButton> listaBotones;

	/**
	 * Constructor de GUIListarPregunta
	 * 
	 * @param modelo
	 *            modelo de la aplicacion
	 */
	public GUIListarPregunta(MailUam modelo) {
		super(modelo);
		buttonCrearPregunta = new JButton("Crear Pregunta");
		buttonCrearRespuesta = new JButton("Crear Respuesta");
		buttonVerRespuestas = new JButton("Ver Respuestas");
		listaBotones = new ArrayList<JRadioButton>();
		botones = new ButtonGroup();
		panel = new JPanel();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		p1.setLayout(new GridBagLayout());
		SpringLayout layout = new SpringLayout();
		p2.setLayout(layout);
		p2.add(buttonCrearPregunta);
		p2.add(buttonCrearRespuesta);
		p2.add(buttonVerRespuestas);
		c.gridx = 0;
		c.gridy = 0;
		p1.add(p2,c);
		c.gridy = 1;
		p1.add(panel,c);
		SpringUtilities.makeCompactGrid(p2, 1, 3, 6, 6, 6, 6);
		
		add(p1);

	}

	/**
	 * @return the buttonCrear
	 */
	public JButton getButtonCrearPregunta() {
		return buttonCrearPregunta;
	}

	/**
	 * @param buttonCrearPregunta the buttonCrearPregunta to set
	 */
	public void setButtonCrearPregunta(JButton buttonCrearPregunta) {
		this.buttonCrearPregunta = buttonCrearPregunta;
	}

	/**
	 * @return the botones
	 */
	public ButtonGroup getBotones() {
		return botones;
	}

	/**
	 * @param botones the botones to set
	 */
	public void setBotones(ButtonGroup botones) {
		this.botones = botones;
	}

	/**
	 * @return the grupo
	 */
	public GrupoEstudio getGrupo() {
		return grupo;
	}

	/**
	 * @param grupo the grupo to set
	 */
	public void setGrupo(GrupoEstudio grupo) {
		this.grupo = grupo;
	}

	/**
	 * @return the panel
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * @param panel the panel to set
	 */
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	/**
	 * @return the buttonVerPreguntas
	 */
	public JButton getButtonCrearRespuesta() {
		return buttonCrearRespuesta;
	}

	/**
	 * @param CrearRespuesta the CrearRespuesta to set
	 */
	public void setButtonCrearRespuesta(JButton CrearRespuesta) {
		this.buttonCrearRespuesta = CrearRespuesta;
	}

	/**
	 * @return the buttonVerRespuestas
	 */
	public JButton getButtonVerRespuestas() {
		return buttonVerRespuestas;
	}

	/**
	 * @param buttonVerRespuestas the buttonVerRespuestas to set
	 */
	public void setButtonVerRespuestas(JButton buttonVerRespuestas) {
		this.buttonVerRespuestas = buttonVerRespuestas;
	}

	/**
	 * @return the listaBotones
	 */
	public ArrayList<JRadioButton> getListaBotones() {
		return listaBotones;
	}

	/**
	 * @param listaBotones the listaBotones to set
	 */
	public void setListaBotones(ArrayList<JRadioButton> listaBotones) {
		this.listaBotones = listaBotones;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public void setControlador(ActionListener c) {
		super.setControlador(c);
		buttonCrearRespuesta.addActionListener(c);
		buttonVerRespuestas.addActionListener(c);
		buttonCrearPregunta.addActionListener(c);
	}

	/**
	 * Instala los valores en la GUI
	 * @param grupo grupo en el que listara sus preguntas
	 */
	public void setValores(GrupoEstudio grupo) {
		this.grupo = grupo;
		System.out.println(grupo.getListaPreguntas());
		panel.removeAll();
		listaBotones.clear();
		botones.clearSelection();
		Integer i = 1;
		if (getM().getLogged() instanceof Profesor) {
			buttonVerRespuestas.setEnabled(true);
			buttonCrearPregunta.setEnabled(true);
			buttonCrearRespuesta.setEnabled(false);
		} else if (getM().getLogged() instanceof Estudiante) {
			buttonVerRespuestas.setEnabled(false);
			buttonCrearPregunta.setEnabled(false);
			buttonCrearRespuesta.setEnabled(true);
		} else {
			buttonVerRespuestas.setEnabled(false);
			buttonCrearPregunta.setEnabled(false);
			buttonCrearRespuesta.setEnabled(false);
		}
		SpringLayout layout = new SpringLayout();
		panel.setLayout(layout);
		for (Pregunta p : grupo.getListaPreguntas()) {
			JRadioButton boton = new JRadioButton("Pregunta" + i.toString());
			botones.add(boton);
			panel.add(boton);
			listaBotones.add(boton);
			i++;
		}
		SpringUtilities.makeCompactGrid(panel, grupo.getListaPreguntas().size(),1, 6, 6, 6, 6);
		validate();
	}
}
