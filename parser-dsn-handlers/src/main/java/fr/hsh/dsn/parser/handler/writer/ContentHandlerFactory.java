package fr.hsh.dsn.parser.handler.writer;

import fr.hsh.dsn.parser.handler.IContentHandler;
import fr.hsh.dsn.parser.handler.stack.StackComponentHandler;


public class ContentHandlerFactory {
	public static IContentHandler getStackComponentHandler(final String pDsnVersion) {
		return new StackComponentHandler(pDsnVersion);
	}
	public static IContentHandler getXmlWriter() {
		return new XmlWriter();
	}
	public static IContentHandler getDatabaseWriter(final String pDsnVersion) {
		return new DatabaseWriter(pDsnVersion, 50);
	}

	public static IContentHandler getChainWriterWrapper(final IContentHandler... pWriters) {
		ChainWriterWrapper chain = null;
		for (int i = 0; i < pWriters.length; i++) {
			if (i == 0) {
				chain = new ChainWriterWrapper(pWriters[i]);
			} else {
				chain.setNext(pWriters[i]);
			}
		}
		return chain;
	}
}
