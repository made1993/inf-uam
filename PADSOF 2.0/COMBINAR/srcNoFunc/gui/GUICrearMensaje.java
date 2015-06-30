package gui;

import java.awt.Label;
import java.awt.event.ActionListener;

import javax.swing.*;

import usuario.*;
import mailUam.*;

public class GUICrearMensaje extends GUIMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelCorreo;
	private JLabel labelAsunto;
	private JLabel labelMensaje;
	private JLabel labelContactos;
	private JTextField textCorreo;
	private JTextField textAsunto;
	private JTextArea textMensaje;
	private JList listContactos;
	private JButton botonEnviar;
	private DefaultListModel listaCorreos;

	public GUICrearMensaje(MailUam modelo) {
		super(modelo);
		setLabelTituloText("Crear Mensaje");
		labelCorreo= new JLabel("Correo:");
		labelAsunto= new JLabel("Asunto:");
		labelMensaje= new JLabel("Mensaje:");
		labelContactos= new JLabel("Contactos:");
		textCorreo= new JTextField(15);
		textAsunto= new JTextField(15);
		textMensaje= new JTextArea(20, 20);
		listaCorreos= new DefaultListModel();
		
		listContactos = new JList(listaCorreos);
		botonEnviar= new JButton("Enviar");
		
		JPanel p1= new JPanel();
		p1.add(labelCorreo);
		p1.add(textCorreo);
		p1.add(listContactos);
		p1.add(labelAsunto);
		p1.add(textAsunto);
		p1.add(labelMensaje);
		p1.add(textMensaje);
		p1.add(botonEnviar);
		
		add(p1);
		
		
	}
	
	
	/**
	 * @return the labelCorreo
	 */
	public JLabel getLabelCorreo() {
		return labelCorreo;
	}


	/**
	 * @param labelCorreo the labelCorreo to set
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
	 * @param labelAsunto the labelAsunto to set
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
	 * @param labelMensaje the labelMensaje to set
	 */
	public void setLabelMensaje(JLabel labelMensaje) {
		this.labelMensaje = labelMensaje;
	}


	/**
	 * @return the labelContactos
	 */
	public JLabel getLabelContactos() {
		return labelContactos;
	}


	/**
	 * @param labelContactos the labelContactos to set
	 */
	public void setLabelContactos(JLabel labelContactos) {
		this.labelContactos = labelContactos;
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
	 * @param textCorreo the textCorreo to set
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
	 * @param textAsunto the textAsunto to set
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
	 * @param textMensaje the textMensaje to set
	 */
	public void setTextMensaje(JTextArea textMensaje) {
		this.textMensaje = textMensaje;
	}


	/**
	 * @return the listContactos
	 */
	public JList getListContactos() {
		return listContactos;
	}


	/**
	 * @param listContactos the listContactos to set
	 */
	public void setListContactos(JList listContactos) {
		this.listContactos = listContactos;
	}


	/**
	 * @return the botonEnviar
	 */
	public JButton getBotonEnviar() {
		return botonEnviar;
	}


	/**
	 * @param botonEnviar the botonEnviar to set
	 */
	public void setBotonEnviar(JButton botonEnviar) {
		this.botonEnviar = botonEnviar;
	}


	
	/**
	 * @return the listaCorreos
	 */
	public DefaultListModel getListaCorreos() {
		return listaCorreos;
	}


	/**
	 * @param listaCorreos the listaCorreos to set
	 */
	public void setListaCorreos(DefaultListModel listaCorreos) {
		this.listaCorreos = listaCorreos;
	}


	@Override
	public void setControlador(ActionListener c) {
		//TODO Auto-generated method stub
		super.setControlador(c);
		botonEnviar.addActionListener(c);
	}

	public void setValores(){
		listaCorreos.addElement("");
		for(Usuario u:getM().getLogged().getContactos()){
			listaCorreos.addElement(u.getCorreo());
		}
		listContactos.setSelectedIndex(0);
	}
}
