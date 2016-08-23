package gui;

import grupo.Grupo;

import java.awt.event.ActionListener;

import javax.swing.*;

import usuario.Usuario;
import mailUam.*;

public class GUIAnadirParticipante extends GUIMenu {

	private Grupo grupo;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textBuscar;
	private JButton buttonBuscar;
	private JButton buttonFinalizar;
	private JPanel panelParticipantes;
	private JLabel labelParticipantes;

	public GUIAnadirParticipante(MailUam modelo) {
		super(modelo);
		setLabelTituloText("Añadir Participantes");
		textBuscar = new JTextField(20);
		buttonBuscar = new JButton("Buscar");
		buttonFinalizar = new JButton("Finalizar");
		labelParticipantes = new JLabel("Participantes");
		panelParticipantes = new JPanel();
		
		JPanel p1= new JPanel();
		p1.add(textBuscar);
		p1.add(buttonBuscar);
		p1.add(buttonFinalizar);
		p1.add(labelParticipantes);
		p1.add(panelParticipantes);
		add(p1);
	}
	
	
	/**
	 * @return the grupo
	 */
	public Grupo getGrupo() {
		return grupo;
	}


	/**
	 * @param grupo the grupo to set
	 */
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}


	/**
	 * @return the textBuscar
	 */
	public JTextField getTextBuscar() {
		return textBuscar;
	}


	/**
	 * @param textBuscar the textBuscar to set
	 */
	public void setTextBuscar(JTextField textBuscar) {
		this.textBuscar = textBuscar;
	}


	/**
	 * @return the buttonBuscar
	 */
	public JButton getButtonBuscar() {
		return buttonBuscar;
	}


	/**
	 * @param buttonBuscar the buttonBuscar to set
	 */
	public void setButtonBuscar(JButton buttonBuscar) {
		this.buttonBuscar = buttonBuscar;
	}


	/**
	 * @return the buttonFinalizar
	 */
	public JButton getButtonFinalizar() {
		return buttonFinalizar;
	}


	/**
	 * @param buttonFinalizar the buttonFinalizar to set
	 */
	public void setButtonFinalizar(JButton buttonFinalizar) {
		this.buttonFinalizar = buttonFinalizar;
	}


	/**
	 * @return the panelParticipantes
	 */
	public JPanel getPanelParticipantes() {
		return panelParticipantes;
	}


	/**
	 * @param panelParticipantes the panelParticipantes to set
	 */
	public void setPanelParticipantes(JPanel panelParticipantes) {
		this.panelParticipantes = panelParticipantes;
	}


	/**
	 * @return the labelParticipantes
	 */
	public JLabel getLabelParticipantes() {
		return labelParticipantes;
	}


	/**
	 * @param labelParticipantes the labelParticipantes to set
	 */
	public void setLabelParticipantes(JLabel labelParticipantes) {
		this.labelParticipantes = labelParticipantes;
	}


	@Override
	public void setControlador(ActionListener c) {
				super.setControlador(c);
				buttonBuscar.addActionListener(c);
				buttonFinalizar.addActionListener(c);
	}

	public void setValores(Grupo grupo) {
		this.grupo= grupo;
		panelParticipantes.removeAll();
		for(Usuario u: grupo.getListaUsuarios()){
			panelParticipantes.add(new JLabel(u.getCorreo()));
		}
		repaint();
		
	}


	public String getTextBuscarText() {
		return textBuscar.getText();
	}

}
