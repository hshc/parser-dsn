package fr.hsh.dsn.exception;

public class ParseException extends SocleException {

	public ParseException(String pCode, String pLibelleCourt, SocleException pCause) {
		super(pCode, pLibelleCourt, pCause);
	}

	public ParseException(String pCode, String pLibelleCourt, String pMessage, SocleException pCause) {
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
