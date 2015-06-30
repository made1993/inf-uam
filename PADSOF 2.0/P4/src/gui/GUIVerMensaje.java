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
 * Clase GUIVerMensaje
 */
public class GUIVerMensaje extends GUIMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel labelAsunto;
	private JLabel labelRemitente;
	private JLabel labelFecha;
	private JTextArea areaContenido;
	private JButton botonResponder;
	private JButton botonEliminar;
	private MensajeUsuario mensaje;
	
	/**
	 * Constructor de GUIVerMensaje
	 * @param modelo modelo de la aplicacion
	 */
	public GUIVerMensaje(MailUam modelo) {
		super(modelo);
		setLabelTituloText("Ver Mensaje");
		labelAsunto = new JLabel();
		labelRemitente = new JLabel();
		labelFecha = new JLabel();
		areaContenido = new JTextArea();
		botonResponder = new JButton("Responder");
		botonEliminar = new JButton("Eliminar");
		
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		SpringLayout layout = new SpringLayout();
		p2.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		p1.setLayout(new GridBagLayout());
		
		p2.add(labelAsunto);
		p2.add(labelRemitente);
		p2.add(labelFecha);

		p2.setPreferredSize(new Dimension(20,100));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		p1.add(p2,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		p1.add(areaContenido,c);
		p3.add(botonResponder);
		p3.add(botonEliminar);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		p1.add(p3,c);

		SpringUtilities.makeCompactGrid(p2, 3, 1, 6, 6, 6, 6);
		this.setVisible(true);
		
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
	 * Instala los valores en los controladores
	 * @param m mensaje para visualizar el contenido
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
		super.setControlador(c);
		botonEliminar.addActionListener(c);
		botonResponder.addActionListener(c);
	}

}
