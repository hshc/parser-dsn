package fr.hsh.dsn.exception;

import fr.hsh.dsn.errors.ErreurCode;
import fr.hsh.dsn.errors.GestionErreurs;
import fr.hsh.socle.exception.core.SocleFonctionnalException;

public class NoGrammarFoundException extends SocleFonctionnalException {
	public NoGrammarFoundException(final String pGrammarVersion) {
		super(ErreurCode.CODE_ERREUR_0007.toString(), GestionErreurs.getInstance().getMessageErreur(ErreurCode.CODE_ERREUR_0007, pGrammarVersion));
	}

}
