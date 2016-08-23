package gui;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import usuario.*;
import mailUam.*;


/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase GUIMenu
 */
public class GUIMenu extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelTitulo;
	private JButton botonMensajes;
	private JButton botonGrupos;
	private JButton botonVerPerfil;
	private JButton botonSalir;
	private MailUam m;
	
	/**
	 * Constructor de la clase GUIMenu
	 * @param app los datos de la aplicacion
	 */
	public GUIMenu(MailUam app){
		botonMensajes = new JButton("Mensajes");
		botonGrupos = new JButton("Mis Grupos");
		botonVerPerfil = new JButton("Ver Perfil");
		botonSalir = new JButton("Salir");
		botonVerPerfil.setPreferredSize(botonGrupos.getPreferredSize());
		botonMensajes.setPreferredSize(botonGrupos.getPreferredSize());
		botonSalir.setPreferredSize(botonGrupos.getPreferredSize());
		this.m = app;
		labelTitulo=new JLabel("Bienvenido ");//Introducir nombre de profesor o estudiante
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		
		GridBagLayout layout2 = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		
		JPanel j1 = new JPanel();
		j1.add(labelTitulo);
		
		JPanel j2 = new JPanel();
		j2.setLayout(layout2);
		
		JPanel j21 = new JPanel();
		j21.add(botonMensajes);
		constraints.gridx = 1;
		constraints.gridy = 0;
		layout2.setConstraints(j21, constraints);
		
		JPanel j22 = new JPanel();
		j22.add(botonGrupos);
		constraints.gridx = 1;
		constraints.gridy = 1;
		layout2.setConstraints(j22, constraints);

		JPanel j23 = new JPanel();
		j23.add(botonVerPerfil);
		constraints.gridx = 1;
		constraints.gridy = 2;
		layout2.setConstraints(j23, constraints);

		JPanel j24 = new JPanel();
		j24.add(botonSalir);
		constraints.gridx = 1;
		constraints.gridy = 3;
		layout2.setConstraints(j24, constraints);

		j2.add(j21);
		j2.add(j22);
		j2.add(j23);
		j2.add(j24);
//TODO Cambiar a GUILogin		j2.setVisible(false);
		
		add(j1,BorderLayout.NORTH);
		add(j2,BorderLayout.WEST);
	}
	
	/**
	 * @return the labelTitulo
	 */
	public JLabel getlabelTitulo() {
		return labelTitulo;
	}
	
	

	/**
	 * @param labelTitulo the labelTitulo to set
	 */
	public void setLabelTitulo(JLabel labelTitulo) {
		this.labelTitulo = labelTitulo;
	}
	
	

	/**
	 * @param titulo the labelTitulo to set
	 */
	public void setLabelTituloText(String titulo) {
		this.labelTitulo.setText(titulo);
	}



	/**
	 * @return the botonMensajes
	 */
	public JButton getBotonMensajes() {
		return botonMensajes;
	}



	/**
	 * @param botonMensajes the botonMensajes to set
	 */
	public void setBotonMensajes(JButton botonMensajes) {
		this.botonMensajes = botonMensajes;
	}



	/**
	 * @return the botonGrupos
	 */
	public JButton getBotonGrupos() {
		return botonGrupos;
	}



	/**
	 * @param botonGrupos the botonGrupos to set
	 */
	public void setBotonGrupos(JButton botonGrupos) {
		this.botonGrupos = botonGrupos;
	}



	/**
	 * @return the botonVerPrefil
	 */
	public JButton getBotonVerPerfil() {
		return botonVerPerfil;
	}



	/**
	 * @param botonVerPerfil the botonVerPrefil to set
	 */
	public void setBotonVerPerfil(JButton botonVerPerfil) {
		this.botonVerPerfil = botonVerPerfil;
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
	 * @return the m
	 */
	public MailUam getM() {
		return m;
	}



	/**
	 * @param m the m to set
	 */
	public void setM(MailUam m) {
		this.m = m;
	}

	

	/**
	 * Introduce los valores del usuario que esta logueado
	 */
	public void setValores(){
		if(getM().getLogged().isVisitante()){
			labelTitulo.setText("Bienvenido "+ getM().getLogged().getCorreo());
		}
		else
			labelTitulo.setText("Bienvenido "+ getM().getLogged().getNombre());
	}
	/**
	 * Edtablece los controladores de los botones
	 * @param c un ActionListener
	 */
	public void setControlador(ActionListener c) {
		botonMensajes.addActionListener(c);
		botonGrupos.addActionListener(c);
		botonVerPerfil.addActionListener(c);
		botonSalir.addActionListener(c);
	}
}
