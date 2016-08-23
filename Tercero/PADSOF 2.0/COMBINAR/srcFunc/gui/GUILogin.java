package gui;

import java.awt.*;

import java.awt.event.*;

import javax.swing.*;
/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase GUILogin
 */
public class GUILogin extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private JTextField campoCorreo;
    private JPasswordField campoContrasena;
	private JButton botonOk;
	private JButton botonSalir;
	private JButton botonVisitante;
	
	/**
	 * Constructor de GUILogin Diapositiva 1 de maqueta
	 */
	public GUILogin(){
		campoContrasena = new JPasswordField(10);
		campoContrasena.setText("a");
		campoCorreo = new JTextField(20);
		campoCorreo.setText("a.a@mail.gob");
		botonOk = new JButton("Ok");
		botonVisitante = new JButton("Visitante");
		botonSalir = new JButton("Salir de la Aplicacion");
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		this.setLayout(layout);

		JLabel labelCorreo = new JLabel("Correo: ");
		JLabel labelContrasena = new JLabel("Contraseña: ");
		
		JPanel j1 = new JPanel();
		j1.add(labelCorreo);
		j1.add(campoCorreo);
		constraints.gridx = 1;
		constraints.gridy = 0;
		layout.setConstraints(j1, constraints);
		
		JPanel j2 =new JPanel();
		j2.add(labelContrasena);
		j2.add(campoContrasena);
		constraints.gridx = 1;
		constraints.gridy = 1;
		layout.setConstraints(j2, constraints);
		
		JPanel j3 = new JPanel();
		j3.add(botonOk);
		j3.add(botonSalir);
		j3.add(botonVisitante);
		constraints.gridx = 1;
		constraints.gridy = 2;
		layout.setConstraints(j3, constraints);

		this.add(j1);
		this.add(j2);
		this.add(j3);
		
	}

	/**
	 * @return the campoCorreo
	 */
	public JTextField getCampoCorreo() {
		return campoCorreo;
	}

	/**
	 * @param campoCorreo the campoCorreo to set
	 */
	public void setCampoCorreo(JTextField campoCorreo) {
		this.campoCorreo = campoCorreo;
	}

	/**
	 * @return the campoContrasena
	 */
	public JPasswordField getCampoContrasena() {
		return campoContrasena;
	}

	/**
	 * @param campoContrasena the campoContrasena to set
	 */
	public void setCampoContrasena(JPasswordField campoContrasena) {
		this.campoContrasena = campoContrasena;
	}

	/**
	 * @return the botonOk
	 */
	public JButton getBotonOk() {
		return botonOk;
	}

	/**
	 * @param botonOk the botonOk to set
	 */
	public void setBotonOk(JButton botonOk) {
		this.botonOk = botonOk;
	}

	/**
	 * @return the botonSalir
	 */
	public JButton getBotonSalir() {
		return botonSalir;
	}

	/**
	 * @param botonSalir the botonSalir to set
	 */
	public void setBotonSalir(JButton botonSalir) {
		this.botonSalir = botonSalir;
	}
	
	/**
	 * @return the campoCorreo text
	 */
	public String getCampoCorreoText() {
		return campoCorreo.getText();
	}
	
	/**
	 * @return the campoContrasena text
	 */
	public String getCampoContrasenaText() {
		return campoContrasena.getText();
	}
	

	/**
	 * @return the botonVisitante
	 */
	public JButton getBotonVisitante() {
		return botonVisitante;
	}

	/**
	 * @param botonVisitante the botonVisitante to set
	 */
	public void setBotonVisitante(JButton botonVisitante) {
		this.botonVisitante = botonVisitante;
	}

	public void setControlador(ActionListener c) {
		this.botonVisitante.addActionListener(c);
		this.botonOk.addActionListener(c);
		this.botonSalir.addActionListener(c);
	}
	
}
