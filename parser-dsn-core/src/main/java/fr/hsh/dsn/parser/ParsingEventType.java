package fr.hsh.dsn.parser;

public enum ParsingEventType {
	START_DOCUMENT("START_DOCUMENT"),
	START_ELEMENT("START_ELEMENT"),
	COMPUTE_SECTION("COMPUTE_SECTION"),
	END_ELEMENT("END_ELEMENT"),
	END_DOCUMENT("END_DOCUMENT"),
	UNREFERENCED_SECTION("UNREFERENCED_SECTION");

	private String	name	= "";

	ParsingEventType(final String pName) {
		this.name = pName;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
