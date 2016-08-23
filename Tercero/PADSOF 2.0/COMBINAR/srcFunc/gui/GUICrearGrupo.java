package gui;

import grupo.Grupo;

import java.awt.event.ActionListener;

import javax.swing.*;

import mailUam.*;

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
		campoNombre = new JTextField(20);
		checkSubgrupo = new JCheckBox("Subgrupo");
		comboListaGrupo = new JComboBox();
		String[] array = {"Normal","Estudio","Colaborativo"};
		comboTipoGrupo = new JComboBox(array);
		checkModerado = new JCheckBox("Moderado");
		checkPrivado = new JCheckBox("Privado");
		botonAnadir = new JButton("Añadir Participantes");
		
		JPanel p1 = new JPanel();

		p1.add(labelNombre);
		p1.add(campoNombre);
		p1.add(checkSubgrupo);
		p1.add(comboListaGrupo);
		p1.add(comboTipoGrupo);
		p1.add(checkModerado);
		p1.add(checkPrivado);
		p1.add(botonAnadir);
		
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
		//TODO Auto-generated method stub
		super.setControlador(c);
		botonAnadir.addActionListener(c);
	}


	public String getCampoNombreText() {
		return campoNombre.getText();
	}
	/**
	 * 
	 */
	public void setValores(){
		getM().cargarGrupos();
		for(Grupo g: getM().getListaTodosGrupos()){
			System.out.println(g.getNombre());
			comboListaGrupo.addItem(g.getNombre());
		}
	}
}
