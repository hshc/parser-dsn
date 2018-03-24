package fr.hsh.dsn.parser.handler;

import fr.hsh.dsn.exception.ParseException;
import fr.hsh.dsn.parser.grammar.metamodel.Bloc;
import fr.hsh.dsn.parser.grammar.metamodel.Section;

public class NoOpContentHandler implements IContentHandler {

	@Override
	public void startDocument() throws ParseException {
	}

	@Override
	public void startElement(Bloc pBloc) throws ParseException {
	}

	@Override
	public void compute(Section pSection, String pValue) throws ParseException {
	}

	@Override
	public void endElement(Bloc pBloc) throws ParseException {
	}

	@Override
	public void endDocument() throws ParseException {
	}

	@Override
	public void handleError(Throwable pException) {
	}

	@Override
	public void handleUnreferencedSection(String pSectionName, String pPayload) {
	}

}
