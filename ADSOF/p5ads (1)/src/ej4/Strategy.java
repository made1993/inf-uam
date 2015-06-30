package ej4;

import java.util.HashSet;
import java.util.List;

import ej3.Rule;

public abstract class Strategy<G> {
	public Strategy() {}

	public void ejec( List<G> list, HashSet<Rule<G>> reglas){} 
	
}
