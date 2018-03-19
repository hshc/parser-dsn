package fr.hsh.dsn.exception;

import fr.hsh.socle.exception.core.ISocleException;
import fr.hsh.socle.exception.core.SocleFonctionnalException;

public class GrammarViolationException extends SocleFonctionnalException {

	/**
	 * Commentaire pour <code>serialVersionUID</code>
	 */
	private static final long	serialVersionUID	= 1L;

	public GrammarViolationException(String pCode, String pLibelleCourt) {
		super(pCode, pLibelleCourt);
	}

	public GrammarViolationException(String pCode, String pLibelleCourt, ISocleException pCause) {
		super(pCode, pLibelleCourt, pCause);
	}

}
