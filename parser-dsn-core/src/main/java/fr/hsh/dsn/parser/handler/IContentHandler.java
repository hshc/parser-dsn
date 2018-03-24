package fr.hsh.dsn.parser.handler;

import fr.hsh.dsn.exception.ParseException;
import fr.hsh.dsn.parser.grammar.metamodel.Bloc;
import fr.hsh.dsn.parser.grammar.metamodel.Section;

public interface IContentHandler {
	void startDocument() throws ParseException;
	void startElement(final Bloc pBloc) throws ParseException;
	void compute(final Section pSection, final String pValue) throws ParseException;
	void endElement(final Bloc pBloc) throws ParseException;
	void endDocument() throws ParseException;
	void handleUnreferencedSection(final String pSectionName, final String pPayload);
	void handleError(final Throwable pException);
}
