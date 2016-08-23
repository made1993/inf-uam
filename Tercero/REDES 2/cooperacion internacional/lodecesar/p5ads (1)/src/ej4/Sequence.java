package ej4;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import ej3.Rule;

public class Sequence<G> extends Strategy<G>{
	
	public Sequence() {
		super();
	}
	
	@Override
	public void ejec( List<G> list, HashSet<Rule<G>> reglas){
		List<G> listAux;
		for(Rule<G> r: reglas){
			listAux=list.stream().filter(r.getWhen()).peek(r.getExec()).collect(Collectors.toList());
			for(G n: listAux){
				list.set(list.indexOf(n), n);
			}			
		}
	}
}
