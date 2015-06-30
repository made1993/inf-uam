package ej3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class RuleSet<T> {

	private HashSet<Rule<T>> reglas;
	private List<T> list;

	public RuleSet() {
		this.reglas = new HashSet<Rule<T>>();
		this.list = new ArrayList<T>();
	}

	public HashSet<Rule<T>> getReglas() {
		return reglas;
	}

	public List<T> getList() {
		return list;
	}

	public RuleSet<T> add(Rule<T> regla) {
		this.reglas.add(regla);
		return this;
	}

	public void setExecContext(List<T> lista) {
		this.list = lista;
	}

	public void process() {
		List<T> listAux;
		for (Rule<T> r : reglas) {
			listAux = list.stream().filter(r.getWhen()).peek(r.getExec())
					.collect(Collectors.toList());
			for (T n : listAux) {
				list.set(list.indexOf(n), n);
			}
		}
	}
}
