package fr.hsh.dsn.errors;

public enum ErrorCode {
	CODE_RETOUR_OK("0000"),
	CODE_ERREUR_0001("0001"),
	CODE_ERREUR_0002("0002"),
	CODE_ERREUR_0003("0003"),
	CODE_ERREUR_0004("0004"),
	CODE_ERREUR_0005("0005"),
	CODE_ERREUR_0006("0006"),
	CODE_ERREUR_0007("0007"),
	CODE_ERREUR_0008("0008"), 
	CODE_ERREUR_0009("0009");

	private String	name	= "";

	ErrorCode(final String pName) {
		this.name = pName;
	}

	@Override
	public String toString() {
		return this.name;
	}
}