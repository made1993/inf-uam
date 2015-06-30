package reglas;

import java.util.function.*;

/**
 * Clase Rule<T>
 *
 * @author Mario Garcia Roque
 * @author Alejandro Antonio Martin Almansa
 *
 */
public class Rule<T> implements Consumer<T>, Predicate<T> {

	private String nombre;
	private String descripcion;
	private Predicate<T> when;
	private Consumer<T> exec;

	/**
	 * Metodo rule para crear una regla
	 * 
	 * @param name
	 *            nombre de la regla
	 * @param description
	 *            descripcion de la regla
	 * @return Regla creada
	 */
	public static <T> Rule<T> rule(String name, String description) {

		Rule<T> r = new Rule<T>(name, description);

		return r;
	}

	/**
	 * Constructor de la clase Rule
	 * 
	 * @param nombre
	 *            nombre de la regla
	 * @param descripcion
	 *            descripcion de la regla
	 */
	public Rule(String nombre, String descripcion) {

		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	/**
	 * Metodo que asigna una ejecucion a una regla
	 * 
	 * @param c
	 *            ejecucion a asignar
	 * @return regla con la ejecucion
	 */
	public Rule<T> exec(Consumer<T> c) {

		this.exec = c;

		return this;

	}

	/**
	 * Metodo que asigna un predicado a una regla
	 * 
	 * @param p
	 *            predicado a asignar
	 * @return regla con el predicado
	 */
	public Rule<T> when(Predicate<T> p) {

		this.when = p;

		return this;

	}

	/**
	 * Metodo que devuelve el predicado de una regla
	 * 
	 * @return predicado de la regla
	 */
	public Predicate<T> getWhen() {
		return when;
	}

	/**
	 * Metodo para obtener la ejecucion de una regla
	 * 
	 * @return ejecucion de la regla
	 */
	public Consumer<T> getExec() {
		return exec;
	}

	/**
	 * Metodo test
	 * 
	 * @param t
	 *            dato a testear
	 */
	@Override
	public boolean test(T t) {

		return this.when.test(t);
	}

	/**
	 * Metodo accept
	 * 
	 * @param arg0
	 *            dato a comprobar
	 */
	@Override
	public void accept(T arg0) {
		this.exec.accept(arg0);
	}

}
