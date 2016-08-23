package gui;

import grupo.Grupo;

import java.awt.event.ActionListener;

import javax.swing.*;

import layout.SpringUtilities;
import mailUam.*;

/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase GUICrearGrupo
 */
public class GUICrearGrupo extends GUIMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel labelNombre;
	private JTextField campoNombre;
	private JCheckBox checkSubgrupo;
	private JComboBox comboListaGrupo;
	private JComboBox comboTipoGrupo;
	private JCheckBox checkModerado;
	private JCheckBox checkPrivado;
	private JButton botonAnadir;
	
	public GUICrearGrupo(MailUam modelo) {
		super(modelo);
		setLabelTituloText("Crear Grupo");
		labelNombre = new JLabel("Nombre de Grupo");
		campoNombre = new JTextField(10);
		checkSubgrupo = new JCheckBox("Subgrupo");
		comboListaGrupo = new JComboBox();
		String[] array = {"Normal","Estudio","Colaborativo"};
		comboTipoGrupo = new JComboBox(array);
		checkModerado = new JCheckBox("Moderado");
		checkPrivado = new JCheckBox("Privado");
		botonAnadir = new JButton("Añadir Participantes");

		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		SpringLayout layout = new SpringLayout();
		p2.setLayout(layout);
		SpringLayout layout1 = new SpringLayout();
		p4.setLayout(layout1);

		p3.add(labelNombre);
		p3.add(campoNombre);
		p2.add(p3);
		p4.add(checkSubgrupo);
		p4.add(comboListaGrupo);
		p2.add(p4);
		p2.add(comboTipoGrupo);
		p2.add(checkModerado);
		p2.add(checkPrivado);
		p2.add(botonAnadir);
		p1.add(p2);

		SpringUtilities.makeCompactGrid(p2, 6, 1, 6, 6, 6, 6);
		SpringUtilities.makeCompactGrid(p4, 1, 2, 0, 0, 0, 0);
		p4.setVisible(true);
		
		add(p1);
	}

	
	/**
	 * @return the labelNombre
	 */
	public JLabel getLabelNombre() {
		return labelNombre;
	}




	/**
	 * @param labelNombre the labelNombre to set
	 */
	public void setLabelNombre(JLabel labelNombre) {
		this.labelNombre = labelNombre;
	}




	/**
	 * @return the campoNombre
	 */
	public JTextField getCampoNombre() {
		return campoNombre;
	}


	/**
	 * @return the campoNombreText
	 */
	public String getCampoNombreText() {
		return campoNombre.getText();
	}


	/**
	 * @param campoNombre the campoNombre to set
	 */
	public void setCampoNombre(JTextField campoNombre) {
		this.campoNombre = campoNombre;
	}




	/**
	 * @return the checkSubgrupo
	 */
	public JCheckBox getCheckSubgrupo() {
		return checkSubgrupo;
	}




	/**
	 * @param checkSubgrupo the checkSubgrupo to set
	 */
	public void setCheckSubgrupo(JCheckBox checkSubgrupo) {
		this.checkSubgrupo = checkSubgrupo;
	}




	/**
	 * @return the comboListaGrupo
	 */
	public JComboBox getComboListaGrupo() {
		return comboListaGrupo;
	}




	/**
	 * @param comboListaGrupo the comboListaGrupo to set
	 */
	public void setComboListaGrupo(JComboBox comboListaGrupo) {
		this.comboListaGrupo = comboListaGrupo;
	}




	/**
	 * @return the comboTipoGrupo
	 */
	public JComboBox getComboTipoGrupo() {
		return comboTipoGrupo;
	}




	/**
	 * @param comboTipoGrupo the comboTipoGrupo to set
	 */
	public void setComboTipoGrupo(JComboBox comboTipoGrupo) {
		this.comboTipoGrupo = comboTipoGrupo;
	}




	/**
	 * @return the checkModerado
	 */
	public JCheckBox getCheckModerado() {
		return checkModerado;
	}




	/**
	 * @param checkModerado the checkModerado to set
	 */
	public void setCheckModerado(JCheckBox checkModerado) {
		this.checkModerado = checkModerado;
	}




	/**
	 * @return the checkPrivado
	 */
	public JCheckBox getCheckPrivado() {
		return checkPrivado;
	}




	/**
	 * @param checkPrivado the checkPrivado to set
	 */
	public void setCheckPrivado(JCheckBox checkPrivado) {
		this.checkPrivado = checkPrivado;
	}




	/**
	 * @return the botonAnadir
	 */
	public JButton getBotonAnadir() {
		return botonAnadir;
	}


	/**
	 * @param botonAnadir the botonAnadir to set
	 */
	public void setBotonAnadir(JButton botonAnadir) {
		this.botonAnadir = botonAnadir;
	}

	

	@Override
	public void setControlador(ActionListener c) {
		super.setControlador(c);
		botonAnadir.addActionListener(c);
	}
	
	/**
	 * Instala los valores en la GUI
	 */
	public void setValores(){
		getM().cargarGrupos();
		comboListaGrupo.removeAllItems();
		for(Grupo g: getM().getListaTodosGrupos()){
			comboListaGrupo.addItem(g.getNombre());
		}
	}
}
