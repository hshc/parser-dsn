package fr.hsh.dsn.exception;

import fr.hsh.socle.exception.core.ISocleException;
import fr.hsh.socle.exception.core.SocleApplicativeException;

public class ParseException extends SocleApplicativeException {

	public ParseException(String pCode, String pLibelleCourt, ISocleException pCause) {
		super(pCode, pLibelleCourt, pCause);
	}

	public ParseException(String pCode, String pLibelleCourt, String pMessage, ISocleException pCause) {
		super(pCode, pLibelleCourt, pMessage, pCause);
	}

	public ParseException(String pCode, String pLibelleCourt, String pMessage, Throwable pCause) {
		super(pCode, pLibelleCourt, pMessage, pCause);
	}

	public ParseException(String pCode, String pLibelleCourt, String pMessage) {
		super(pCode, pLibelleCourt, pMessage);
	}

	public ParseException(String pCode, String pLibelleCourt, Throwable pCause) {
		super(pCode, pLibelleCourt, pCause);
	}

	public ParseException(String pCode, String pLibelleCourt) {
		super(pCode, pLibelleCourt);
	}

}
