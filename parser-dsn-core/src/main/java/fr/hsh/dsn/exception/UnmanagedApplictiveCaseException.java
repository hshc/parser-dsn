package fr.hsh.dsn.exception;

public class UnmanagedApplictiveCaseException extends SocleException {

	/**
	 * Commentaire pour <code>serialVersionUID</code>
	 */
	private static final long	serialVersionUID	= 1L;

	public UnmanagedApplictiveCaseException(String pCode, String pLibelleCourt) {
		super(pCode, pLibelleCourt);
	}
}
