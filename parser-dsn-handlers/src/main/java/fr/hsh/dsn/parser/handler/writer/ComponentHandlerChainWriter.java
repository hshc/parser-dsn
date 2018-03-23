package fr.hsh.dsn.parser.handler.writer;


public abstract class ComponentHandlerChainWriter {

	protected ComponentHandlerChainWriter next;

	public abstract void startElement();
	public abstract void startChildElement();
	public abstract void compute();
	public abstract void endChildElement();
	public abstract void endElement();

	public void _startElement() {

	}

	public void _compute() {

	}

	public void _endElement() {

	}
}
