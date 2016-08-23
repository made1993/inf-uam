package ej2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.function.Predicate;

import ej1.Node;



public class BlackBoxComparator<N,V> implements Comparator<ConstrainedGraph<N,V>> {
	
	private HashMap<Criteria, ArrayList<Predicate<Node<N>>>> crits;
	
	public BlackBoxComparator(){
		
		this.crits=new  HashMap<Criteria, ArrayList<Predicate<Node<N>>>>();
	}
	
	public BlackBoxComparator<N,V> addCriteria(Criteria cr, Predicate<Node<N>> pred){
		if(crits.containsKey(cr)){
			crits.get(cr).add(pred);
		}
		else{
			ArrayList<Predicate<Node<N>>> aux=new ArrayList<Predicate<Node<N>>>();
			aux.add(pred);
			crits.put(cr, aux);	
		}
		return this;
	}
	

	@Override
	public int compare(ConstrainedGraph<N,V> o1, ConstrainedGraph<N,V> o2) {
		int contO1=0;
		int contO2=0;
		ArrayList<Predicate<Node<N>>> preds=crits.get(Criteria.Existential);
		for(Predicate<Node<N>> p: preds ){
			if(o1.exists(p))
				contO1++;
			if(o2.exists(p))
				contO2++;
		}
		preds=crits.get(Criteria.Unitary);
		for(Predicate<Node<N>> p: preds ){
			if(o1.one(p))
				contO1++;
			if(o2.one(p))
				contO2++;
		}
		preds=crits.get(Criteria.Universal);
		for(Predicate<Node<N>> p: preds ){
			if(o1.forAll(p))
				contO1++;
			if(o2.forAll(p))
				contO2++;
		}
		if(contO1<contO2)
			return -1;
		if(contO1>contO2)
			return 1;
		return 0;
	}
	
}
