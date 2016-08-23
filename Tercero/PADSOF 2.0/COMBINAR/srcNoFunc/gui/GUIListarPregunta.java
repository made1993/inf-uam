package gui;

import java.awt.event.ActionListener;
import java.util.*;
import usuario.*;
import grupo.*;

import javax.swing.*;

import mailUam.*;
import mensaje.Pregunta;

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

	public GUIListarPregunta(MailUam modelo) {
		super(modelo);
		buttonCrearPregunta = new JButton("Crear Pregunta");
		buttonCrearRespuesta = new JButton("Crear Respuesta");
		buttonVerRespuestas = new JButton("Ver Respuesta");
		listaBotones = new ArrayList<JRadioButton>();
		botones = new ButtonGroup();
		panel = new JPanel();
		JPanel p1 = new JPanel();
		p1.add(buttonCrearPregunta);
		p1.add(buttonCrearRespuesta);
		p1.add(buttonVerRespuestas);
		p1.add(panel);
		add(p1);
		
	}

	/**
	 * @return the buttonCrear
	 */
	public JButton getButtonCrearPregunta() {
		return buttonCrearPregunta;
	}

	/**
	 * @param buttonCrear the buttonCrear to set
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
	 * @param buttonVerPreguntas the buttonVerPreguntas to set
	 */
	public void setButtonCrearRespuesta(JButton CrearRespuesta) {
		this.buttonCrearRespuesta= buttonCrearRespuesta;
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
		//TODO Auto-generated method stub
		super.setControlador(c);
		buttonCrearRespuesta.addActionListener(c);
		buttonVerRespuestas.addActionListener(c);
		buttonCrearPregunta.addActionListener(c);
	}
	public void setValores(GrupoEstudio grupo){
		this.grupo= grupo;
		System.out.println(grupo.getListaPreguntas());
		panel.removeAll();
		listaBotones.clear();
		botones.clearSelection();
		Integer i=1;
		if(getM().getLogged() instanceof Profesor){
			buttonVerRespuestas.setEnabled(true);
			buttonCrearPregunta.setEnabled(true);
			buttonCrearRespuesta.setEnabled(false);
		}else if(getM().getLogged() instanceof Estudiante){
			buttonVerRespuestas.setEnabled(false);
			buttonCrearPregunta.setEnabled(false);
			buttonCrearRespuesta.setEnabled(true);
		}else{
			buttonVerRespuestas.setEnabled(false);
			buttonCrearPregunta.setEnabled(false);
			buttonCrearRespuesta.setEnabled(false);
		}
		for(Pregunta p: grupo.getListaPreguntas()){
			JRadioButton boton = new JRadioButton("Pregunta"+i.toString());
			botones.add(boton);
			panel.add(boton);
			listaBotones.add(boton);
			i++;
		}
		
	}
}
