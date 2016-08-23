package es.uam.eps.bmi.redessociales;

import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import edu.uci.ics.jung.algorithms.generators.random.ErdosRenyiGenerator;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.util.Pair;

import java.io.*;
import java.util.HashSet;
import org.apache.commons.collections15.Factory;

public class GeneradorGrafos {

	private Factory<String> getVertexFactory() {
		Factory<String> vertexFactory = new Factory<String>() {
			Integer count = 1;

			@Override
			public String create() {
				count++;
				return count.toString();
			}
		};
		return vertexFactory;
	}

	private Factory<Integer> getEdgeFactory() {

		Factory<Integer> edgeFactory = new Factory<Integer>() {
			Integer count = 1;

			@Override
			public Integer create() {
				return count++;
			}
		};

		return edgeFactory;
	}

	private HashSet<String> getSeedVertices(int nodosIniciales) {
		HashSet<String> seedVertices = new HashSet<String>();

		for (int i = 0; i < nodosIniciales; i++)
			seedVertices.add(String.valueOf(i));

		return seedVertices;
	}

	public Graph<String, Integer> generarGrafoBarabasiAlbert(int nodosIniciales, int numEpocas) {

		Factory<Graph<String, Integer>> graphFactory = SparseGraph.getFactory();
		Factory<String> vertexFactory = this.getVertexFactory();
		Factory<Integer> edgeFactory = this.getEdgeFactory();
		HashSet<String> seedVertices = this.getSeedVertices(nodosIniciales);

		BarabasiAlbertGenerator<String, Integer> barabasialbert = new BarabasiAlbertGenerator<String, Integer>(
				graphFactory, vertexFactory, edgeFactory, seedVertices.size(), 1, seedVertices);

		barabasialbert.evolveGraph(numEpocas);
		Graph<String, Integer> grafo = barabasialbert.create();
		return grafo;
	}

	public Graph<String, Integer> generarGrafoErdosRenyi(int nodosIniciales) {

		Factory<UndirectedGraph<String, Integer>> graphFactory = UndirectedSparseGraph.getFactory();
		Factory<String> vertexFactory = this.getVertexFactory();
		Factory<Integer> edgeFactory = this.getEdgeFactory();
		HashSet<String> seedVertices = this.getSeedVertices(nodosIniciales);

		ErdosRenyiGenerator<String, Integer> erdosrenyi = new ErdosRenyiGenerator<String, Integer>(graphFactory,
				vertexFactory, edgeFactory, seedVertices.size(), 0.001);

		return erdosrenyi.create();
	}

	public void escribirGrafo(String ruta, Graph<String, Integer> grafo) {

		UndirectedSparseGraph<String, Integer> grafoaux = new UndirectedSparseGraph<String, Integer>();
		for (Integer arista : grafo.getEdges()) {
			Pair<String> endpoints = grafo.getEndpoints(arista);
			grafoaux.addEdge(grafoaux.getEdgeCount(), endpoints.getFirst(), endpoints.getSecond());
		}

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(ruta));

			for (Integer arista : grafoaux.getEdges()) {
				Pair<String> par = grafoaux.getEndpoints(arista);
				bw.write(par.getFirst() + "," + par.getSecond() + "\n");
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {

		GeneradorGrafos gg = new GeneradorGrafos();
		gg.escribirGrafo("GrafoBarabasiAlbert.csv", gg.generarGrafoBarabasiAlbert(1, 1000));
		gg.escribirGrafo("GrafoErdosRenyi.csv", gg.generarGrafoErdosRenyi(1000));
	}
}
