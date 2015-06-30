package ej1;

import java.util.*;




public class Node<N> implements Comparable<Node<N>> {

	private N name;
	private int id;
	private HashMap<Node<N>, ArrayList<Edge<?,N>>> conex;
	
	public Node (N nombre){
		this.name=nombre;
		this.conex= new HashMap<Node<N>, ArrayList<Edge<?,N>>>();
		this.id=0;
	}
	
	public void conectar(Node<N> n,Edge<?,N> c){
		if(conex.containsKey(n))
			conex.get(n).add(c);
		else{
			ArrayList<Edge<?,N>> aux=new ArrayList<Edge<?,N>>();
			aux.add(c);
			conex.put(n, aux);	
		}
	}
	public Boolean isConnectedTo(Node<N> nodo){
		return conex.containsKey(nodo);			
	}
	public Boolean isConnectedTo(N nodo){
		ArrayList<Node<N>> aux = new ArrayList<Node<N>>(conex.keySet());
		for(Node<N> node : aux){
			if(nodo.equals(node.name))
				return true;
		}
		return false;
	}
	public Collection<Node<N>> neighbours(){		
		ArrayList<Node<N>> vecinos = new ArrayList<Node<N>>(conex.keySet());			
		return Collections.unmodifiableCollection(vecinos);
	}

	public ArrayList<Object> getEdgeValues(Node<N> nodo){
		ArrayList<Edge<?,N>> conexiones = new ArrayList<>(conex.get(nodo));
		ArrayList<Object> valores = new ArrayList<>();
		for(Edge<?, N> c : conexiones){
			valores.add(c.getValor());
		}
		Collections.sort(valores, new Comparator<Object>() {
			@Override
			public int compare(Object valor1, Object valor2) {
				int v1=(int) valor1;
				int v2=(int) valor2;
				if(v1>v2)
					return 1;
				else if(v1<v2)
					return -1;
				return 0;
			}
		});
		return valores;
	}
	@Override
	public int compareTo(Node<N> arg0) {
		if(this.id<arg0.getId())
			return -1;
		else if(this.id>arg0.getId())
			return 1;
		return 0;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public HashMap<Node<N>, ArrayList<Edge<?, N>>> getConex() {
		return conex;
	}
	public N getValue(){
		return name;
	}
	public void setValue(N name) {
		this.name = name;
	}
	@Override
	public String toString(){
		return this.id+" ["+this.name.toString()+"]";
	}
	 
}
