package ej1;

import java.util.*;


public class Graph<N,V> implements Collection<Node<N>>{

	private ArrayList<Edge<V,N>> conexiones;
	private ArrayList<Node<N>> nodos;
	private int id;
	public Graph(){
		conexiones = new ArrayList<Edge<V,N>>();
		nodos = new ArrayList<Node<N>>();		
		this.id = 0;
	}
	
	public ArrayList<Edge<V, N>> getConexiones() {
		return conexiones;
	}

	public ArrayList<Node<N>> getNodos() {
		return nodos;
	}

	public int getId() {
		return id;
	}

	public boolean connect(Node<N> nodo1, V valor, Node<N> nodo2){
		Edge<V,N> c = new Edge<V,N>(nodo1,valor,nodo2);
		nodo1.conectar(nodo2, c);
		return conexiones.add(c);
	}
	@Override
	public boolean add(Node<N> n) {		
		if(nodos.contains(n))
			return false;
		n.setId(id);
		this.id++;
		nodos.add(n);
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean addAll(Collection c) {
		List<Node<N>> aux = new ArrayList<>(c);
		for(Node<N> n : aux){
			add(n);
		}
		return true;
	}

	@Override
	public void clear() {
		nodos.clear();
	}

	@Override
	public boolean contains(Object o) {
		return nodos.contains(o);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean containsAll(Collection c) {
		return nodos.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return nodos.isEmpty();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Iterator iterator() {
		return nodos.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return nodos.remove(o);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean removeAll(Collection c) {		
		return nodos.removeAll(c);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean retainAll(Collection c) {
		return nodos.retainAll(c);
	}

	@Override
	public int size() {
		return nodos.size();
	}

	@Override
	public Object[] toArray() {
		return nodos.toArray();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] toArray(Object[] a) {
		return nodos.toArray(a);
	}

	@Override
	public String toString(){
		String cad;
		cad = "Nodes:\n";
		for(int i = 0; i < nodos.size(); i++){
			cad+= " "+nodos.get(i).toString()+"\n";
		}
		cad +="Edges: \n";
		for(int i = 0; i < conexiones.size(); i++){
			cad+= " ( "+conexiones.get(i).getN1().getId()+" --"+conexiones.get(i).getValor()+"--> "+conexiones.get(i).getN2().getId()+" )\n";
		}
		return cad;
	}
	
}
