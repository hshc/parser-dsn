package fr.hsh.dsn.exception;

import fr.hsh.dsn.errors.ErrorCode;
import fr.hsh.dsn.errors.ErrorsManager;

public class NoGrammarFoundException extends SocleException {
	public NoGrammarFoundException(final String pGrammarVersion) {
		super(ErrorCode.CODE_ERROR_0007.toString(), ErrorsManager.getInstance().getErrorMessage(ErrorCode.CODE_ERROR_0007, pGrammarVersion));
	}
}
