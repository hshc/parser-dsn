package fr.hsh.dsn.parser.handler.writer;

import fr.hsh.dsn.parser.grammar.metamodel.Bloc;
import fr.hsh.dsn.parser.grammar.metamodel.Section;
import fr.hsh.dsn.parser.handler.IContentHandler;


public class XmlWriter implements IContentHandler {
	//	private String xml;

	@Override
	public void startDocument() {
		// TODO Auto-generated method stub

	}

	@Override
	public void startElement(Bloc pComponent) {
		// TODO Auto-generated method stub
		//		this.xml+="<"+pComponent.getName()+">";
		System.out.println("<"+pComponent.getName()+">");
	}

	@Override
	public void compute(Section pComponent, String pValue) {
		// TODO Auto-generated method stub
		//		this.xml+="<"+pComponent.getName()+" value='"+pValue+"' />";
		System.out.println("<"+pComponent.getName()+" value='"+pValue+"'/>");
	}

	@Override
	public void endElement(Bloc pComponent) {
		// TODO Auto-generated method stub
		//		this.xml+="</"+pComponent.getName()+">";
		System.out.println("</"+pComponent.getName()+">");
	}

	@Override
	public void endDocument() {
		// TODO Auto-generated method stub
		//		System.err.println(this.xml);
	}

	@Override
	public void handleUnreferencedSection(String pSectionName, String pPayload) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleError(Throwable pException) {
		// TODO Auto-generated method stub
		
	}

}
