package ej1;

public class Edge<V,N>  {

	private Node<N> n1;
	private Node<N> n2;
	private V valor;
	

	public Edge(Node<N> nodo1, V valor, Node<N> nodo2) {
		this.n1 = nodo1;
		this.n2 = nodo2;
		this.valor = valor;	}

	public Node<N> getN1() {
		return n1;
	}
	public void setN1(Node<N> n1) {
		this.n1 = n1;
	}
	public Node<N> getN2() {
		return n2;
	}
	public void setN2(Node<N> n2) {
		this.n2 = n2;
	}
	public V getValor() {
		return valor;
	}
	public void setValor(V valor) {
		this.valor = valor;
	}
	
   
}
