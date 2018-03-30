package fr.hsh.dsn.parser.handler.stack;

import java.util.ArrayDeque;
import java.util.Deque;

import fr.hsh.dsn.parser.grammar.metamodel.Bloc;
import fr.hsh.dsn.parser.grammar.metamodel.Section;
import fr.hsh.dsn.parser.handler.IContentHandler;

public class StackComponentHandler implements IContentHandler {

	//	private final static String						ENVOI				= "S10.G00.00";
	//	private final static String						EMETTEUR			= "S10.G00.01";
	//	private final static String						CONTACT_EMETTEUR	= "S10.G00.02";
	//	private final static String						DESTINATAIRE_CRE	= "S10.G00.03";
	//	private final static String						DSN					= "S20.G00.05";
	//	private final static String						ENTREPRISE			= "S21.G00.06";
	//	private final static String						ETABLISSEMENT		= "S21.G00.06";
	//	private final static String						TOTAL				= "S90.G00.90";

	private final Deque<StackableComponentHandler>		stack				= new ArrayDeque<StackableComponentHandler>();
	private final String								dsnVersion;

	public StackComponentHandler(final String pDsnVersion) {
		this.dsnVersion = pDsnVersion;
	}

	@Override
	public void startDocument() {
		// TODO Auto-generated method stub
	}

	@Override
	public void startElement(final Bloc pBloc) {
		StackableComponentHandler a = new DefaultStackableComponentHandler(pBloc);
		if (!this.stack.isEmpty()) {
			this.stack.peek().startChildElement();
		}
		this.stack.push(a);
		a.startElement();
	}

	@Override
	public void endElement(final Bloc pBloc) {
		StackableComponentHandler a = null;
		if (!this.stack.isEmpty()) {
			a = this.stack.pop();
		} if (!this.stack.isEmpty()) {
			this.stack.peek().endChildElement();
		}
		a.endElement();
	}

	@Override
	public void compute(final Section pSection, final String pValue) {
		StackableComponentHandler a = this.stack.peek();
		a.compute(pSection, pValue);
	}

	@Override
	public void endDocument() {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleUnreferencedSection(String pSectionName, String pPayload) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleError(Throwable pException) {
		// TODO Auto-generated method stub
		
	}

	public void push(final StackableComponentHandler pComponentHandler) {
		this.stack.push(pComponentHandler);
	}

	public StackableComponentHandler pop() {
		return this.stack.pop();
	}

}
