package ej2;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ej1.Graph;
import ej1.Node;

@SuppressWarnings("unchecked")
public class ConstrainedGraph<N,V> extends Graph<N,V> {
	
	private Optional<Node<N>> witness;
	public ConstrainedGraph(){
		super();
		witness=Optional.empty(); 
	}
	
	public Optional<Node<N>> getWitness() {
		return this.witness;
	}

	public boolean forAll(Predicate<Node<N>> pred){
		return this.getNodos().stream().allMatch(pred);		
	}
	public boolean exists(Predicate<Node<N>> pred){
		List<Node<N>> aux=new ArrayList<Node<N>>();
		witness=Optional.empty(); 
		if(this.getNodos().stream().anyMatch(pred)){
			aux=this.getNodos().stream().filter(pred).collect(Collectors.toList());
			witness=Optional.of(aux.get(aux.size()-1));
			return true;
		}
		return false;
	}
	
	public boolean one(Predicate<Node<N>> pred){

		long cumple=this.getNodos().stream().filter(pred).count();		
		if(cumple==1)
			return true;
		return false;
	}
}

