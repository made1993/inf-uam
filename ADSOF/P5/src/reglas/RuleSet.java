package reglas;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase RuleSet<T>
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 */
public class RuleSet<T> {

	private List<Rule<T>> reglas;
	private List<T> context;

	/**
	 * Constructor de la clase RuleSet
	 */
	public RuleSet() {
		this.reglas = new ArrayList<Rule<T>>();
		this.context = new ArrayList<T>();
	}

	/**
	 * Metodo para anadir una regla a un set de reglas
	 * 
	 * @param rule
	 *            regla a anadir
	 * @return el set al cual se le ha anadido la regla
	 */
	public RuleSet<T> add(Rule<T> rule) {

		this.reglas.add(rule);

		return this;
	}

	/**
	 * Metodo para asignar un contexto de ejecucion a un RuleSet
	 * 
	 * @param ctx
	 *            contexto a asignar
	 */
	public void setExecContext(List<T> ctx) {
		this.context = ctx;
	}

	/**
	 * Metodo para procesar un RuleSet
	 */
	public void process() {

		for (T t : this.context) {
			for (Rule<T> r : this.reglas) {
				if (r.getWhen().test(t) == true) {
					r.getExec().accept(t);
				}
			}
		}

	}

}
