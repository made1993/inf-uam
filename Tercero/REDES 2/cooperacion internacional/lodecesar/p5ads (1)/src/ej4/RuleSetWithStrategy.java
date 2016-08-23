package ej4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ej2.ConstrainedGraph;
import ej3.Rule;

public class RuleSetWithStrategy<G> {
	 
	private List<G> list;
	private HashSet<Rule<G>> reglas;
	private Strategy<G> s;
	
	public RuleSetWithStrategy(Strategy<G> s){
		this.reglas=new HashSet<Rule<G>>();
		this.list=new ArrayList<G>();
		this.s=s;
	}
	

	public List<G> getList() {
		return list;
	}

	public HashSet<Rule<G>> getReglas() {
		return reglas;
	}

	public Strategy<G> getS() {
		return s;
	}

	@SuppressWarnings("unchecked")
	public void setExecContext(ConstrainedGraph<?,?> grafo){
		
		this.list=(List<G>) grafo.getNodos();
	}

	public RuleSetWithStrategy<G> add(Rule<G> regla) {
		this.reglas.add(regla);
		return this;
	}

	public void process() {
		 s.ejec(this.list,this.reglas);
	}
	
	
}
