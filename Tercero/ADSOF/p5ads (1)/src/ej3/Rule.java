package ej3;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class Rule<T> {

	private String name;
	private String desc;
	private Predicate<T> when;
	private Consumer<T> exec;

	public Rule(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public Predicate<T> getWhen() {
		return when;
	}

	public Consumer<T> getExec() {
		return exec;
	}

	public static <T> Rule<T> rule(String name, String desc) {
		return new Rule<T>(name, desc);
	}

	public Rule<T> when(Predicate<T> pred) {
		this.when = pred;
		return this;
	}

	public Rule<T> exec(Consumer<T> pred) {
		this.exec = pred;
		return this;
	}

	@Override
	public String toString() {
		return "Rule [name=" + name + "\n desc=" + desc + "\n when="
				+ "\n exec=" + exec + "]";
	}
}
