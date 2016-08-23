package gui;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import layout.SpringUtilities;
import mensaje.*;

import javax.swing.*;

import mailUam.*;
/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase GUICrearRespuesta
 */
public class GUICrearRespuesta extends GUIMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelPregunta;
	private JTextArea textRespuesta;
	private Pregunta pregunta;
	private JButton buttonResponder;

	/**
	 * Constructor de la clase GUICrearRespuesta
	 * @param modelo
	 * 		Aplicacion donde estan todos los datos
	 */
	public GUICrearRespuesta(MailUam modelo) {
		super(modelo);
		labelPregunta = new JLabel();
		textRespuesta= new JTextArea(15,10);
		buttonResponder = new JButton("Responder");
		
		SpringLayout layout = new SpringLayout();
		JPanel p1= new JPanel();
		JPanel p2 = new JPanel(layout);
		p2.setPreferredSize(new Dimension(400,250));
		p2.add(labelPregunta);
		p2.add(textRespuesta);
		JPanel p3= new JPanel();
		p3.add(buttonResponder);
		p2.add(p3);
		
		SpringUtilities.makeCompactGrid(p2, 3, 1,6, 6,6, 6);
		p1.add(p2);
		add(p1);
				
	}
	
	
	/**
	 * @return the labelPregunta
	 */
	public JLabel getLabelPregunta() {
		return labelPregunta;
	}


	/**
	 * @param labelPregunta the labelPregunta to set
	 */
	public void setLabelPregunta(JLabel labelPregunta) {
		this.labelPregunta = labelPregunta;
	}


	/**
	 * @return the textRespuesta
	 */
	public JTextArea getTextRespuesta() {
		return textRespuesta;
	}
	
	/**
	 * @return the textRespuesta
	 */
	public String getTextRespuestaText() {
		return textRespuesta.getText();
	}


	/**
	 * @param textRespuesta the textRespuesta to set
	 */
	public void setTextRespuesta(JTextArea textRespuesta) {
		this.textRespuesta = textRespuesta;
	}


	/**
	 * @return the pregunta
	 */
	public Pregunta getPregunta() {
		return pregunta;
	}


	/**
	 * @param pregunta the pregunta to set
	 */
	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}


	/**
	 * @return the buttonResponder
	 */
	public JButton getButtonResponder() {
		return buttonResponder;
	}


	/**
	 * @param buttonResponder the buttonResponder to set
	 */
	public void setButtonResponder(JButton buttonResponder) {
		this.buttonResponder = buttonResponder;
	}


	@Override
	public void setControlador(ActionListener c) {
		super.setControlador(c);
		buttonResponder.addActionListener(c);
	}
	public void setValores(Pregunta pregunta){
		this.pregunta= pregunta;
		labelPregunta.setText(pregunta.getCuerpo());
	}

}
