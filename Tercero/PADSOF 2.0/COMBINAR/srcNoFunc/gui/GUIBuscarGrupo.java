package gui;

import grupo.Grupo;

import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

import mailUam.*;

public class GUIBuscarGrupo extends GUIMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel labelInfo;
	private JTextField textBuscar;
	private JButton botonBuscar;
	private ButtonGroup botonGrupo;
	private JButton botonEntrar;
	private ArrayList<Grupo> grupos;
	private JPanel panelResultados;

	public GUIBuscarGrupo(MailUam modelo) {
		super(modelo);
		setLabelTituloText("Buscar Grupo");
		labelInfo = new JLabel();
		textBuscar = new JTextField();
		botonBuscar = new JButton("OK");
		botonGrupo = new ButtonGroup();
		botonEntrar = new JButton("Entrar");
		panelResultados = new JPanel();
		JPanel p1;
		p1 = new JPanel();

		p1.add(labelInfo);
		p1.add(textBuscar);
		p1.add(botonBuscar);
		p1.add(botonEntrar);
		
		p1.add(panelResultados);
		add(p1);
	}

	/**
	 * @return the labelInfo
	 */
	public JLabel getLabelInfo() {
		return labelInfo;
	}

	/**
	 * @param labelInfo the labelInfo to set
	 */
	public void setLabelInfo(JLabel labelInfo) {
		this.labelInfo = labelInfo;
	}

	/**
	 * @return the campoBuscar
	 */
	public JTextField getTextBuscar() {
		return textBuscar;
	}
	
	/**
	 * @return the campoBuscar
	 */
	public String getTextBuscarText() {
		return textBuscar.getText();
	}

	/**
	 * @param campoBuscar the campoBuscar to set
	 */
	public void setTextBuscar(JTextField campoBuscar) {
		this.textBuscar = campoBuscar;
	}

	/**
	 * @return the botonOk
	 */
	public JButton getBotonBuscar() {
		return botonBuscar;
	}

	/**
	 * @param botonOk the botonOk to set
	 */
	public void setBotonBuscar(JButton botonOk) {
		this.botonBuscar = botonOk;
	}

	/**
	 * @return the botonGrupo
	 */
	public ButtonGroup getBotonGrupo() {
		return botonGrupo;
	}

	/**
	 * @param botonGrupo the botonGrupo to set
	 */
	public void setBotonGrupo(ButtonGroup botonGrupo) {
		this.botonGrupo = botonGrupo;
	}




	/**
	 * @return the grupos
	 */
	public ArrayList<Grupo> getGrupos() {
		return grupos;
	}

	/**
	 * @param grupos the grupos to set
	 */
	public void setGrupos(ArrayList<Grupo> grupos) {
		this.grupos = grupos;
	}

	/**
	 * @return the botonEntrar
	 */
	public JButton getBotonEntrar() {
		return botonEntrar;
	}

	/**
	 * @param botonEntrar the botonEntrar to set
	 */
	public void setBotonEntrar(JButton botonEntrar) {
		this.botonEntrar = botonEntrar;
	}


	/**
	 * @return the panelResultados
	 */
	public JPanel getPanelResultados() {
		return panelResultados;
	}

	/**
	 * @param panelResultados the panelResultados to set
	 */
	public void setPanelResultados(JPanel panelResultados) {
		this.panelResultados = panelResultados;
	}

	public void setValores(ArrayList<Grupo> grupos) {
		labelInfo.setText("Se han encontrado " + grupos.size() + " grupos.");
		this.grupos = grupos;
		panelResultados.removeAll();
		for(Grupo g:grupos){
			panelResultados.add(new JCheckBox("["+g.getTipoGrupo()+"]"+g.getNombre()));
		}
		repaint();
		
	}

	@Override
	public void setControlador(ActionListener c) {
		// TODO Auto-generated method stub
		super.setControlador(c);
		botonBuscar.addActionListener(c);
		botonEntrar.addActionListener(c);
	}

}
