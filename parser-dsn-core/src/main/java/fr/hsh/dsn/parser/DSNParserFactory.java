package fr.hsh.dsn.parser;

import fr.hsh.dsn.exception.NoGrammarFoundException;
import fr.hsh.dsn.parser.grammar.ComputedGrammar;
import fr.hsh.dsn.parser.grammar.metamodel.GrammarFactory;

public class DSNParserFactory {
	private final ComputedGrammar grammar;

	private DSNParserFactory(final ComputedGrammar pGrammar) {
		this.grammar = pGrammar;
	}

	public static DSNParserFactory newInstance(final String pDsnVersion) throws NoGrammarFoundException {
		return new DSNParserFactory(GrammarFactory.getGrammar(pDsnVersion));
	}

	public DSNParser newDSNParser() {
		return new DSNParser(this.grammar);
	}
}
