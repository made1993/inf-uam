package gui;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import layout.SpringUtilities;
import mailUam.*;
import mensaje.MensajeUsuario;

/**
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 *         Clase GUICrearMensaje
 */
public class GUICrearMensaje extends GUIMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelCorreo;
	private JLabel labelAsunto;
	private JLabel labelMensaje;
	private JTextField textCorreo;
	private JTextField textAsunto;
	private JTextArea textMensaje;
	private JButton botonEnviar;

	/**
	 * Constructor de GUICrearMensaje
	 * 
	 * @param modelo
	 *            modelo de la aplicacion
	 */
	public GUICrearMensaje(MailUam modelo) {
		super(modelo);
		setLabelTituloText("Crear Mensaje");
		labelCorreo = new JLabel("Correo:");
		labelAsunto = new JLabel("Asunto:");
		labelMensaje = new JLabel("Mensaje:");
		textCorreo = new JTextField(20);
		textAsunto = new JTextField(20);
		textMensaje = new JTextArea(20, 20);

		botonEnviar = new JButton("Enviar");

		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		SpringLayout layout = new SpringLayout();
		p2.setLayout(layout);

		GridBagConstraints c = new GridBagConstraints();
		p1.setLayout(new GridBagLayout());

		p2.add(labelCorreo);
		p2.add(textCorreo);
		p2.add(labelAsunto);
		p2.add(textAsunto);
		c.gridx = 0;
		c.gridy = 0;
		p1.add(p2);
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		p1.add(labelMensaje, c);
		c.gridx = 0;
		c.gridy = 2;
		p1.add(textMensaje, c);
		c.gridx = 0;
		c.gridy = 3;
		p3.add(botonEnviar);
		p1.add(p3, c);
		SpringUtilities.makeCompactGrid(p2, 2, 2, 0, 0, 6, 6);
		add(p1);

	}

	/**
	 * @return the labelCorreo
	 */
	public JLabel getLabelCorreo() {
		return labelCorreo;
	}

	/**
	 * @param labelCorreo
	 *            the labelCorreo to set
	 */
	public void setLabelCorreo(JLabel labelCorreo) {
		this.labelCorreo = labelCorreo;
	}

	/**
	 * @return the labelAsunto
	 */
	public JLabel getLabelAsunto() {
		return labelAsunto;
	}

	/**
	 * @param labelAsunto
	 *            the labelAsunto to set
	 */
	public void setLabelAsunto(JLabel labelAsunto) {
		this.labelAsunto = labelAsunto;
	}

	/**
	 * @return the labelMensaje
	 */
	public JLabel getLabelMensaje() {
		return labelMensaje;
	}

	/**
	 * @param labelMensaje
	 *            the labelMensaje to set
	 */
	public void setLabelMensaje(JLabel labelMensaje) {
		this.labelMensaje = labelMensaje;
	}

	/**
	 * @return the textCorreo
	 */
	public JTextField getTextCorreo() {
		return textCorreo;
	}

	/**
	 * @return the textCorreo
	 */
	public String getTextCorreoText() {
		return textCorreo.getText();
	}

	/**
	 * @param textCorreo
	 *            the textCorreo to set
	 */
	public void setTextCorreo(JTextField textCorreo) {
		this.textCorreo = textCorreo;
	}

	/**
	 * @return the textAsunto
	 */
	public JTextField getTextAsunto() {
		return textAsunto;
	}

	/**
	 * @return the textAsunto
	 */
	public String getTextAsuntoText() {
		return textAsunto.getText();
	}

	/**
	 * @param textAsunto
	 *            the textAsunto to set
	 */
	public void setTextAsunto(JTextField textAsunto) {
		this.textAsunto = textAsunto;
	}

	/**
	 * @return the textMensaje
	 */
	public JTextArea getTextMensaje() {
		return textMensaje;
	}

	/**
	 * @return the textMensaje
	 */
	public String getTextMensajeText() {
		return textMensaje.getText();
	}

	/**
	 * @param textMensaje
	 *            the textMensaje to set
	 */
	public void setTextMensaje(JTextArea textMensaje) {
		this.textMensaje = textMensaje;
	}

	/**
	 * @return the botonEnviar
	 */
	public JButton getBotonEnviar() {
		return botonEnviar;
	}

	/**
	 * @param botonEnviar
	 *            the botonEnviar to set
	 */
	public void setBotonEnviar(JButton botonEnviar) {
		this.botonEnviar = botonEnviar;
	}

	@Override
	public void setControlador(ActionListener c) {
		super.setControlador(c);
		botonEnviar.addActionListener(c);
	}

	/**
	 * Instala los valores en la GUI
	 */
	public void setValores() {
		textAsunto.setText("");
		textMensaje.setText("");
		textCorreo.setText("");
	}

	/**
	 * Instala los valores en la gui
	 * @param m mensaque para crear un mensaje respondido
	 */
	public void setValoresResponder(MensajeUsuario m) {
		textAsunto.setText("RE: " + m.getSujeto());
		textMensaje.setText("");
		textCorreo.setText(m.getRemitente().getCorreo());
	}

	/**
	 * Instala los valores en la gui
	 * @param m mensaque para crear un mensaje reenviado
	 */
	public void setValoresReenviar(MensajeUsuario m) {
		textAsunto.setText(m.getSujeto());
		textMensaje.setText(m.getCuerpo());
		textCorreo.setText(m.getRemitente().getCorreo());
	}
}
