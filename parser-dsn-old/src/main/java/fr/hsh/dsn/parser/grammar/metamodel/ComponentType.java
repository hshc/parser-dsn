package fr.hsh.dsn.parser.grammar.metamodel;

public enum ComponentType {

	Section("Section"),
	Bloc("Bloc");

	private String	name	= "";

	ComponentType(final String pName) {
		this.name = pName;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
