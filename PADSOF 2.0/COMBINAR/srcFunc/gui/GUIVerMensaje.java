package gui;

import java.awt.event.ActionListener;

import javax.swing.*;

import mailUam.*;
import mensaje.MensajeUsuario;

public class GUIVerMensaje extends GUIMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel labelAsunto;
	private JLabel labelRemitente;
	private JLabel labelDestinatario;
	private JLabel labelFecha;
	private JTextArea areaContenido;
	private JButton botonResponder;
	private JButton botonEliminar;
	private MensajeUsuario mensaje;
	
	public GUIVerMensaje(MailUam modelo) {
		super(modelo);
		setLabelTituloText("Ver Mensaje");
		labelAsunto = new JLabel();
		labelRemitente = new JLabel();
		labelDestinatario = new JLabel();
		labelFecha = new JLabel();
		areaContenido = new JTextArea();
		botonResponder = new JButton("Responder");
		botonEliminar = new JButton("Eliminar");
		
		JPanel p1 = new JPanel();
		p1.add(labelAsunto);
		p1.add(labelRemitente);
		p1.add(labelDestinatario);
		p1.add(labelFecha);
		p1.add(areaContenido);
		p1.add(botonResponder);
		p1.add(botonEliminar);
		
		add(p1);
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
	 * @param label the labelAsunto text to set
	 */
	public void setLabelAsuntoText(String label) {
		this.labelAsunto.setText(label);
	}



	/**
	 * @return the labelRemitente
	 */
	public JLabel getLabelRemitente() {
		return labelRemitente;
	}



	/**
	 * @param labelRemitente the labelRemitente to set
	 */
	public void setLabelRemitente(JLabel labelRemitente) {
		this.labelRemitente = labelRemitente;
	}



	/**
	 * @param label the labelRemitente text to set
	 */
	public void setLabelRemitenteText(String label) {
		this.labelRemitente.setText(label);
	}



	/**
	 * @return the labelDestinatario
	 */
	public JLabel getLabelDestinatario() {
		return labelDestinatario;
	}



	/**
	 * @param labelDestinatario the labelDestinatario to set
	 */
	public void setLabelDestinatario(JLabel labelDestinatario) {
		this.labelDestinatario = labelDestinatario;
	}



	/**
	 * @param label the labelDestinatario text to set
	 */
	public void setLabelDestinatarioText(String label) {
		this.labelDestinatario.setText(label);
	}



	/**
	 * @return the labelFecha
	 */
	public JLabel getLabelFecha() {
		return labelFecha;
	}



	/**
	 * @param labelFecha the labelFecha to set
	 */
	public void setLabelFecha(JLabel labelFecha) {
		this.labelFecha = labelFecha;
	}



	/**
	 * @param label the labelFecha text to set
	 */
	public void setLabelFechaText(String label) {
		this.labelFecha.setText(label);
	}



	/**
	 * @return the areaContenido
	 */
	public JTextArea getAreaContenido() {
		return areaContenido;
	}



	/**
	 * @param areaContenido the areaContenido to set
	 */
	public void setAreaContenido(JTextArea areaContenido) {
		this.areaContenido = areaContenido;
	}



	/**
	 * @param label the labelRemitente text to set
	 */
	public void setAreaContenidoText(String label) {
		this.areaContenido.setText(label);
	}



	/**
	 * @return the botonResponder
	 */
	public JButton getBotonResponder() {
		return botonResponder;
	}



	/**
	 * @param botonResponder the botonResponder to set
	 */
	public void setBotonResponder(JButton botonResponder) {
		this.botonResponder = botonResponder;
	}



	/**
	 * @return the botonEliminar
	 */
	public JButton getBotonEliminar() {
		return botonEliminar;
	}



	/**
	 * @param botonEliminar the botonEliminar to set
	 */
	public void setBotonEliminar(JButton botonEliminar) {
		this.botonEliminar = botonEliminar;
	}

	
	
	/**
	 * @return the mensaje
	 */
	public MensajeUsuario getMensaje() {
		return mensaje;
	}



	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(MensajeUsuario mensaje) {
		this.mensaje = mensaje;
	}



	/**
	 * 
	 * @param m
	 */
	public void setValores(MensajeUsuario m) {
		mensaje = m;
		labelAsunto.setText("Asunto: "+m.getSujeto());
		labelFecha.setText("Fecha: "+m.getFechaImp());
		labelRemitente.setText("Remitente: "+m.getRemitente().getCorreo());
		areaContenido.setText(m.getCuerpo());
		areaContenido.setEditable(false);
	};
	

	@Override
	public void setControlador(ActionListener c) {
		//TODO Auto-generated method stub
		super.setControlador(c);
		botonEliminar.addActionListener(c);
		botonResponder.addActionListener(c);
	}

}
