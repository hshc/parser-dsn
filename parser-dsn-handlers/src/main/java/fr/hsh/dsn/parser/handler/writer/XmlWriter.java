package fr.hsh.dsn.parser.handler.writer;

import fr.hsh.dsn.exception.ParseException;
import fr.hsh.dsn.parser.grammar.metamodel.Bloc;
import fr.hsh.dsn.parser.grammar.metamodel.Section;
import fr.hsh.dsn.parser.handler.IContentHandler;


public class XmlWriter extends ComponentHandlerChainWriter {
	//	private String xml;
	
	public XmlWriter() {
		super();
	}

	public XmlWriter(final IContentHandler pNext) {
		super(pNext);
	}

	@Override
	public void startElement(final Bloc pComponent) throws ParseException {
		//		this.xml+="<"+pComponent.getName()+">";
		System.out.println("<"+pComponent.getName()+">");
		super.startElement(pComponent);
	}

	@Override
	public void compute(final Section pComponent, final String pValue) throws ParseException {
		//		this.xml+="<"+pComponent.getName()+" value='"+pValue+"' />";
		System.out.println("<"+pComponent.getName()+" value='"+pValue+"'/>");
		super.compute(pComponent, pValue);
	}

	@Override
	public void endElement(final Bloc pComponent) throws ParseException {
		//		this.xml+="</"+pComponent.getName()+">";
		System.out.println("</"+pComponent.getName()+">");
		super.endElement(pComponent);
	}
}
