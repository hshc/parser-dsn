package fr.hsh.dsn.exception;

import fr.hsh.socle.exception.core.SocleApplicativeException;

public class UnmanagedApplictiveCaseException extends SocleApplicativeException {

	/**
	 * Commentaire pour <code>serialVersionUID</code>
	 */
	private static final long	serialVersionUID	= 1L;

	public UnmanagedApplictiveCaseException(String pCode, String pLibelleCourt) {
		super(pCode, pLibelleCourt);
	}
}
