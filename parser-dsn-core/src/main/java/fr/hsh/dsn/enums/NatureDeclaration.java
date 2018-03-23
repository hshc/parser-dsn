package fr.hsh.dsn.enums;

public enum NatureDeclaration {
	MENSUELLE("01"),
	SIG_FIN_CONTRAT("02"),
	SIG_ARRET_TRAVAIL("04"),
	SIG_REPRISE_TRAVAIL("05");

	private String	name	= "";

	NatureDeclaration(final String pName) {
		this.name = pName;
	}

	@Override
	public String toString() {
		return this.name;
	}
}