package fr.hsh.dsn.exception;

import fr.hsh.dsn.errors.ErrorCode;
import fr.hsh.dsn.errors.ErrorsManager;
import fr.hsh.socle.exception.core.SocleFonctionnalException;

public class NoGrammarFoundException extends SocleFonctionnalException {
	public NoGrammarFoundException(final String pGrammarVersion) {
		super(ErrorCode.CODE_ERROR_0007.toString(), ErrorsManager.getInstance().getErrorMessage(ErrorCode.CODE_ERROR_0007, pGrammarVersion));
	}
}
