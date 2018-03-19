package fr.hsh.dsn.parser.handler.stack;

import fr.hsh.dsn.parser.grammar.metamodel.Component;
import fr.hsh.dsn.parser.grammar.metamodel.Section;

public interface IComponentHandler {
	Component getRelatedComponent();
	void startElement();
	void startChildElement();
	void compute(final Section pSection, final String value);
	void endChildElement();
	void endElement();
}
