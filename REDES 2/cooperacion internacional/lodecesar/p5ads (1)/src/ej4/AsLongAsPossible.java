package ej4;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import ej3.Rule;

public class AsLongAsPossible<G> extends Strategy<G> {

	public AsLongAsPossible() {
		super();
	}

	@Override
	public void ejec( List<G> list, HashSet<Rule<G>> reglas) {
		int flag = 0;
		List<G> listAux;
		for (Rule<G> r : reglas) {
			while (flag == 0) {
				listAux = list.stream().filter(r.getWhen()).peek(r.getExec())
						.collect(Collectors.toList());
				if (!listAux.isEmpty()) {
					for (G n : listAux) {
						list.set(list.indexOf(n), n);
					}
				} else {
					flag = 1;
				}
			}
		}
	}

}
