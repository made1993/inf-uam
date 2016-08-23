package gui;

import grupo.Grupo;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import mailUam.MailUam;
import layout.*;

/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase GUIMenuGrupo
 */
public class GUIMenuGrupo extends GUIMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton crearGrupo;
	private JButton ok;
	private JTextField busqueda;
	private JButton salirGrupo;
	private JButton ver;
	private DefaultMutableTreeNode raiz;
	private JTree arbol;
	private JPanel panelArbol;
	private TreeSelectionListener treeSelectionListener;

	/**
	 * Constructor de GUIMenuGrupo Diapositiva 3 de maqueta
	 * @param app EL modelo de la aplicacion
	 */
	public GUIMenuGrupo(MailUam app) {
		super(app);
		setLabelTituloText("Menu Grupo");
		crearGrupo = new JButton("Crear Grupo");
		ok = new JButton("OK");
		busqueda = new JTextField("Buscar...");
		salirGrupo = new JButton("Salir Grupo");
		ver = new JButton("Ver Grupo");
		raiz = new DefaultMutableTreeNode("Mis Grupos");
		panelArbol = new JPanel();
		raiz.add(new DefaultMutableTreeNode("asds"));
		treeSelectionListener = null;

		JScrollPane scroll = new JScrollPane(arbol);

		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		SpringLayout layout = new SpringLayout();
		p2.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		p1.setLayout(new GridBagLayout());
		
		p2.add(crearGrupo);
		p2.add(busqueda);
		p2.add(ok);
		p2.add(salirGrupo);
		p2.add(ver);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		p1.add(p2,c);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 5;
		c.weighty = 4;
		p1.add(panelArbol,c);

		SpringUtilities.makeCompactGrid(p2, 1, 5, 6, 6, 6, 6);
		this.setVisible(true);

		add(p1);
	}

	/**
	 * @return the crearGrupo
	 */
	public JButton getCrearGrupo() {
		return crearGrupo;
	}

	/**
	 * @param crearGrupo
	 *            the crearGrupo to set
	 */
	public void setCrearGrupo(JButton crearGrupo) {
		this.crearGrupo = crearGrupo;
	}

	/**
	 * @return the ok
	 */
	public JButton getOk() {
		return ok;
	}

	/**
	 * @param ok
	 *            the ok to set
	 */
	public void setOk(JButton ok) {
		this.ok = ok;
	}

	/**
	 * @return the busqueda
	 */
	public JTextField getBusqueda() {
		return busqueda;
	}
	

	/**
	 * @return the busquedaText
	 */
	public String getBusquedaText() {
		return busqueda.getText();
	}

	/**
	 * @param busqueda
	 *            the busqueda to set
	 */
	public void setBusqueda(JTextField busqueda) {
		this.busqueda = busqueda;
	}

	/**
	 * @return the salirGrupo
	 */
	public JButton getSalirGrupo() {
		return salirGrupo;
	}

	/**
	 * @param salirGrupo
	 *            the salirGrupo to set
	 */
	public void setSalirGrupo(JButton salirGrupo) {
		this.salirGrupo = salirGrupo;
	}

	/**
	 * @return the ver
	 */
	public JButton getVer() {
		return ver;
	}

	/**
	 * @param ver
	 *            the ver to set
	 */
	public void setVer(JButton ver) {
		this.ver = ver;
	}

	/**
	 * @return the raiz
	 */
	public DefaultMutableTreeNode getRaiz() {
		return raiz;
	}

	/**
	 * @param raiz
	 *            the raiz to set
	 */
	public void setRaiz(DefaultMutableTreeNode raiz) {
		this.raiz = raiz;
	}

	/**
	 * @return the arbol
	 */
	public JTree getArbol() {
		return arbol;
	}

	/**
	 * Instala los valores en la GUI
	 */
	public void setValores() {
		raiz.removeAllChildren();
		getM().cargarUsuarios();
		getM().actualizarLogged();
		getM().cargarGrupos();
		for (Grupo g : getM().getLogged().getListaGrupos()) {
			raiz.add(new DefaultMutableTreeNode(g.getNombre()));
		}

		arbol = new JTree(raiz);
		arbol.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		panelArbol.removeAll();
		panelArbol.add(arbol);
		arbol.removeTreeSelectionListener(treeSelectionListener);
		treeSelectionListener = new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				Object nodo = arbol.getLastSelectedPathComponent();
				int[] indiceNodosSeleccionados = arbol.getSelectionRows();
				TreePath[] pathNodosSeleccionados = arbol.getSelectionPaths();
			}
		};
		arbol.addTreeSelectionListener(treeSelectionListener);
		validate();
	};

	@Override
	public void setControlador(ActionListener c) {
		super.setControlador(c);
		crearGrupo.addActionListener(c);
		ok.addActionListener(c);
		salirGrupo.addActionListener(c);
		ver.addActionListener(c);
	}
}
