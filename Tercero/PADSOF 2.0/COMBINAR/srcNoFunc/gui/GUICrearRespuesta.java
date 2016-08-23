package gui;

import java.awt.event.ActionListener;

import mensaje.*;

import javax.swing.*;

import test.pruebaMailUam;
import mailUam.*;

public class GUICrearRespuesta extends GUIMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelPregunta;
	private JTextArea textRespuesta;
	private Pregunta pregunta;
	private JButton buttonResponder;

	public GUICrearRespuesta(MailUam modelo) {
		super(modelo);
		labelPregunta = new JLabel();
		textRespuesta= new JTextArea(15,10);
		buttonResponder = new JButton("Responder");
		JPanel p1 = new JPanel();
		p1.add(labelPregunta);
		p1.add(textRespuesta);
		p1.add(buttonResponder);
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
