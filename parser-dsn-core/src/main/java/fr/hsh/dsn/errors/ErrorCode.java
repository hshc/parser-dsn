package fr.hsh.dsn.errors;

public enum ErrorCode {
	CODE_RETURN_OK("0000"),
	CODE_ERROR_0001("0001"),
	CODE_ERROR_0002("0002"),
	CODE_ERROR_0003("0003"),
	CODE_ERROR_0004("0004"),
	CODE_ERROR_0005("0005"),
	CODE_ERROR_0006("0006"),
	CODE_ERROR_0007("0007"),
	CODE_ERROR_0008("0008"), 
	CODE_ERROR_0009("0009"),
	CODE_ERROR_0010("0010");

	private String	name	= "";

	ErrorCode(final String pName) {
		this.name = pName;
	}

	@Override
	public String toString() {
		return this.name;
	}
}