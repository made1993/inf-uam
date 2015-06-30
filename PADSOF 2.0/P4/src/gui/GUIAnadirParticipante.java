package gui;

import grupo.Grupo;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

import usuario.Usuario;
import layout.SpringUtilities;
import mailUam.*;

/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase GUIAnadirParticipante
 */
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
		JPanel p2 = new JPanel();
		

		GridBagConstraints c = new GridBagConstraints();
		p1.setLayout(new GridBagLayout());
		
		p2.add(textBuscar);
		p2.add(buttonBuscar);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		p1.add(p2,c);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 1;
		p1.add(labelParticipantes,c);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 2;
		p1.add(panelParticipantes,c);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 3;
		p1.add(buttonFinalizar,c);
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
	 * @return the textBuscarText
	 */
	public String getTextBuscarText() {
		return textBuscar.getText();
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

	/**
	 * Instala los valores en la GUI
	 * @param grupo grupo con el que visualizara los participantes
	 */
	public void setValores(Grupo grupo) {
		this.grupo= grupo;
		panelParticipantes.removeAll();
		SpringLayout layout = new SpringLayout();
		panelParticipantes.setLayout(layout);
		for(Usuario u: grupo.getListaUsuarios()){
			panelParticipantes.add(new JLabel(u.getCorreo()));
		}
		SpringUtilities.makeCompactGrid(panelParticipantes, grupo.getListaUsuarios().size(),1, 6, 6, 6, 6);
		validate();
	}

}
