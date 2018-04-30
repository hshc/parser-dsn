package fr.hsh.dsn.exception;

public class GrammarViolationException extends SocleException {

	/**
	 * Commentaire pour <code>serialVersionUID</code>
	 */
	private static final long	serialVersionUID	= 1L;

	public GrammarViolationException(String pCode, String pLibelleCourt) {
		super(pCode, pLibelleCourt);
	}

	public GrammarViolationException(String pCode, String pLibelleCourt, SocleException pCause) {
		super(pCode, pLibelleCourt, pCause);
	}

}
