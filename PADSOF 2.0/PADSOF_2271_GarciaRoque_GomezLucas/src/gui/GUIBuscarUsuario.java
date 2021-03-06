package gui;

import grupo.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import usuario.Usuario;
import layout.SpringUtilities;
import mailUam.*;

/**
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 *         Clase ControlBuscarGrupo
 */
public class GUIBuscarUsuario extends GUIMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textBuscar;
	private JButton buttonBuscar;
	private JButton buttonAnadir;
	private JPanel panelResultados;
	private Grupo grupo;

	public GUIBuscarUsuario(MailUam modelo) {
		super(modelo);
		setLabelTituloText("Buscar Usuario");
		textBuscar = new JTextField(20);
		buttonBuscar = new JButton("Buscar");
		SpringLayout layout = new SpringLayout();
		panelResultados = new JPanel(layout);
		buttonAnadir = new JButton("Anadir");

		JPanel p1 = new JPanel(new GridBagLayout());
		GridBagConstraints c= new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy =0;
		p1.add(textBuscar,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy =0;
		p1.add(buttonBuscar,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth=2;
		c.gridx = 0;
		c.gridy =1;
		p1.add(panelResultados,c);
		
		c.fill = 0;
		c.gridx = 0;
		c.gridy =2;
		p1.add(buttonAnadir,c);
		add(p1);
		repaint();

	}

	/**
	 * @return the textBuscar
	 */
	public JTextField getTextBuscar() {
		return textBuscar;
	}

	/**
	 * @return the textBuscar
	 */
	public String getTextBuscarText() {
		return textBuscar.getText();
	}

	/**
	 * @param textBuscar
	 *            the textBuscar to set
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
	 * @param buttonBuscar
	 *            the buttonBuscar to set
	 */
	public void setButtonBuscar(JButton buttonBuscar) {
		this.buttonBuscar = buttonBuscar;
	}

	/**
	 * @return the buttonFinalizar
	 */
	public JButton getButtonAnadir() {
		return buttonAnadir;
	}

	/**
	 * @param buttonAnadir the buttonAnadir to set
	 */
	public void setButtonAnadir(JButton buttonAnadir) {
		this.buttonAnadir = buttonAnadir;
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

	/**
	 * Inicializa los valores de la GUIBUscarGrupo
	 * @param resultados
	 * 		Los resutados de la busqueda de usuarios
	 * @param grupo
	 * 		El grupo a donde se añadiran lso usuarios
	 */
	public void setValores(ArrayList<Usuario> resultados, Grupo grupo) {
		this.grupo = grupo;
		panelResultados.removeAll();
		for (Usuario u : resultados) {
			System.out.println("+" + u.getCorreo() + "+");
			panelResultados.add(new JCheckBox(u.getCorreo()));
		}
		SpringUtilities.makeCompactGrid(panelResultados, resultados.size(), 1,6, 6,6, 6);
		System.out.println("size:" + resultados.size());
		validate();
	}

	@Override
	public void setControlador(ActionListener c) {
		super.setControlador(c);
		buttonBuscar.addActionListener(c);
		buttonAnadir.addActionListener(c);
	}

}
