import java.util.Map;
import java.util.List;
import java.util.Set;
public class Klass{
	Klass superKlass;
	boolean isReference;
	String name;
	Map<String, Variable> fields;
	Map<String, Method> methods;
	Klass componentType;
	private class Variable{
		String name;
		Klass type;
		Number value;
	}
	private class Method{
		String name;
		List<Variable> parameters;
		Variable returnType;
		Klass owner;
		Block body;
	}
	private class Block{
		List<Statement> statements;
		Set<Variable> locals;
	}
}