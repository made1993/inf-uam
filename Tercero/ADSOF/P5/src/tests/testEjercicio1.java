package tests;

import grafo.*;
import java.util.*;

/**
 * Clase de prueba del ejercicio 1
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 */
public class testEjercicio1 {
	public static void main(String[] args) {
		Graph<String, Integer> g = new Graph<String, Integer>();
		Node<String> s0 = new Node<>("s0");
		Node<String> s1 = new Node<>("s1");

		g.addAll(Arrays.asList(s0, s1, s0));
		g.connect(s0, 0, s0);
		g.connect(s0, 1, s1);
		g.connect(s0, 0, s1);
		g.connect(s1, 0, s0);
		g.connect(s1, 1, s0);
		System.out.println(g);
		for (Node<String> n : g)
			System.out.println("Nodo " + n);
		List<Node<String>> nodos = new ArrayList<>(g);
		System.out.println(nodos);
		System.out.println("s0 conectado con 's1': " + s0.isConnectedTo("s1"));
		System.out.println("s0 conectado con s1: " + s0.isConnectedTo(s1));
		System.out.println("vecinos de s0: " + s0.neighbours());
		System.out.println("valores de los enlaces desde s0 a s1: "
				+ s0.getEdgeValues(s1));

	}

}
