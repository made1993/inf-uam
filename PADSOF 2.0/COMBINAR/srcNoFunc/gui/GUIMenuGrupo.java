package gui;


import grupo.Grupo;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;

import control.ControlTreeNodeSelector;
import mailUam.MailUam;

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


		JPanel p1 = new JPanel();
		p1.add(crearGrupo);
		p1.add(busqueda);
		p1.add(ok);
		p1.add(salirGrupo);
		p1.add(ver);
		p1.add(panelArbol);
		p1.add(scroll);
		add(p1);
		// La lista de grupos se crea en el controlador
	}

	

	
	/**
	 * @return the crearGrupo
	 */
	public JButton getCrearGrupo() {
		return crearGrupo;
	}




	/**
	 * @param crearGrupo the crearGrupo to set
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
	 * @param ok the ok to set
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
	 * @param busqueda the busqueda to set
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
	 * @param salirGrupo the salirGrupo to set
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
	 * @param ver the ver to set
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
	 * @param raiz the raiz to set
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




	public void setValores() {
		// Por defecto se pueden seleccionar varios nodos en el árbol. Para poder seleccionar sólo uno, usar setSelectionModel
		raiz.removeAllChildren();
		// Para añadir hijos al nodo raiz (o a otros nodos del árbol) usamos el método add(<nodo>)
		System.out.println("Num grupos: "+getM().getLogged().getListaGrupos().size());
		for(Grupo g:getM().getLogged().getListaGrupos()){
			System.out.println("hoal"+g.getNombre());
//			DefaultMutableTreeNode node = new DefaultMutableTreeNode(g.getNombre());
			raiz.add(new DefaultMutableTreeNode(g.getNombre()));
			//TODO añadir subgrupos...
		}

		arbol = new JTree (raiz);
		arbol.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		panelArbol.removeAll();
		panelArbol.add(arbol);
		arbol.removeTreeSelectionListener(treeSelectionListener);
		treeSelectionListener = new TreeSelectionListener() {
			 public void valueChanged(TreeSelectionEvent e) {
			 // Para obtener el nodo seleccionado en el árbol:
			 // - si el modo de selección es SINGLE_TREE_SELECTION, usamos getLastSelectedPathComponent
			 Object nodo = arbol.getLastSelectedPathComponent();
			 // - si se pueden seleccionar varios nodos, usamos getSelectionPaths / getSelectionRows
			 int[] indiceNodosSeleccionados = arbol.getSelectionRows();
			 TreePath[] pathNodosSeleccionados = arbol.getSelectionPaths();
			 }
			};
		arbol.addTreeSelectionListener(treeSelectionListener);
		
		// Para realizar acciones al seleccionar un nodo del árbol usamos un TreeSelectionListener
		// Es aconsejable crear una barra de scroll para el árbol, por si se supera el tamaño previsto


	};



	@Override
	public void setControlador(ActionListener c) {
		super.setControlador(c);
		crearGrupo.addActionListener(c);
		ok.addActionListener(c);
		salirGrupo.addActionListener(c);
		ver.addActionListener(c);
	}




	public String getBusquedaText() {
		return busqueda.getText();
	}
}
