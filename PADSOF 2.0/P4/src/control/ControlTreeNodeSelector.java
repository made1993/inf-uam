package control;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

public class ControlTreeNodeSelector implements TreeSelectionListener{
	
	private final JTree arbol; 
	
	public ControlTreeNodeSelector(JTree arbol) {
		this.arbol=arbol;
	}
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		 // Para obtener el nodo seleccionado en el �rbol:
		 // - si el modo de selecci�n es SINGLE_TREE_SELECTION, usamos getLastSelectedPathComponent
		 Object nodo = arbol.getLastSelectedPathComponent();
		 // - si se pueden seleccionar varios nodos, usamos getSelectionPaths / getSelectionRows
		 int[] indiceNodosSeleccionados = arbol.getSelectionRows();
		 TreePath[] pathNodosSeleccionados = arbol.getSelectionPaths();
	}
	
}
