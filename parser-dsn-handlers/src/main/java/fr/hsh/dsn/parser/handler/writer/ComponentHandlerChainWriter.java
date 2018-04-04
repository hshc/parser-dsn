package fr.hsh.dsn.parser.handler.writer;

import fr.hsh.dsn.exception.ParseException;
import fr.hsh.dsn.parser.grammar.metamodel.Bloc;
import fr.hsh.dsn.parser.grammar.metamodel.Section;
import fr.hsh.dsn.parser.handler.IContentHandler;

public class ComponentHandlerChainWriter implements IContentHandler {

	private final IContentHandler next;

	public ComponentHandlerChainWriter(final IContentHandler pNext) {
		super();
		this.next = pNext;
	}
	
	public ComponentHandlerChainWriter() {
		this(null);
	}

	@Override
	public void startDocument() throws ParseException {
		if (this.next != null) {
			this.next.startDocument();
		}
	}
	@Override
	public void startElement(final Bloc pBloc) throws ParseException {
		if (this.next != null) {
			this.next.startElement(pBloc);
		}
	}
	@Override
	public void compute(final Section pSection, final String pValue) throws ParseException {
		if (this.next != null) {
			this.next.compute(pSection, pValue);
		}
	}
	@Override
	public void endElement(final Bloc pBloc) throws ParseException {
		if (this.next != null) {
			this.next.endElement(pBloc);
		}
	}
	@Override
	public void endDocument() throws ParseException {
		if (this.next != null) {
			this.next.endDocument();
		}
	}
	@Override
	public void handleUnreferencedSection(final String pSectionName, final String pPayload) {
		if (this.next != null) {
			this.next.handleUnreferencedSection(pSectionName, pPayload);
		}
	}
	@Override
	public void handleError(final Throwable pException) {
		if (this.next != null) {
			this.next.handleError(pException);
		}
	}
	
//	public static void main(String[] args) {
//		
//		String myString = null;
//		Optional<String> t = Optional.ofNullable(myString);
////		t = Optional.of(myString);
//		boolean val1 = t.isPresent();
//		String val2 = t.orElse("toto");
//		
//		t = Optional.ofNullable("titi");
//		Optional<String> val4 = t.filter(s -> s.equals("titi"));
//		Optional<Object> val3 = t.map(s -> "Hello "+s);
//		t.ifPresent(s -> System.out.println("tata"));
//		
//	}
}
