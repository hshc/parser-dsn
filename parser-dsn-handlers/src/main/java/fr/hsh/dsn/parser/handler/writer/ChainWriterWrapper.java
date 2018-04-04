package fr.hsh.dsn.parser.handler.writer;

import fr.hsh.dsn.exception.ParseException;
import fr.hsh.dsn.parser.grammar.metamodel.Bloc;
import fr.hsh.dsn.parser.grammar.metamodel.Section;
import fr.hsh.dsn.parser.handler.IContentHandler;

public class ChainWriterWrapper implements IContentHandler {

	private ChainWriterWrapper next = null;
	private final IContentHandler writer;

	ChainWriterWrapper(final IContentHandler pWriter) {
		this.writer = pWriter;
	}

	void setNext(final IContentHandler pNextWriter) {
		this.next = new ChainWriterWrapper(pNextWriter);
	}

	@Override
	public void startDocument() throws ParseException {
		this.writer.startDocument();
		if (this.next != null) {
			this.next.startDocument();
		}
	}

	@Override
	public void startElement(final Bloc pBloc) throws ParseException {
		this.writer.startElement(pBloc);
		if (this.next != null) {
			this.next.startElement(pBloc);
		}
	}

	@Override
	public void compute(final Section pSection, final String pValue) throws ParseException {
		this.writer.compute(pSection, pValue);
		if (this.next != null) {
			this.next.compute(pSection, pValue);
		}
	}

	@Override
	public void endElement(final Bloc pBloc) throws ParseException {
		this.writer.endElement(pBloc);
		if (this.next != null) {
			this.next.endElement(pBloc);
		}
	}

	@Override
	public void endDocument() throws ParseException {
		this.writer.endDocument();
		if (this.next != null) {
			this.next.endDocument();
		}
	}

	@Override
	public void handleUnreferencedSection(final String pSectionName, String pPayload) {
		this.writer.handleUnreferencedSection(pSectionName, pPayload);
		if (this.next != null) {
			this.next.handleUnreferencedSection(pSectionName, pPayload);
		}
	}

	@Override
	public void handleError(Throwable pException) {
		this.writer.handleError(pException);
		if (this.next != null) {
			this.next.handleError(pException);
		}
	}

}
