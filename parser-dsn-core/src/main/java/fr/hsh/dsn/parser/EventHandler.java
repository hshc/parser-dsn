package fr.hsh.dsn.parser;

import java.util.Deque;

import fr.hsh.dsn.exception.ParseException;
import fr.hsh.dsn.parser.grammar.metamodel.Bloc;
import fr.hsh.dsn.parser.grammar.metamodel.Section;
import fr.hsh.dsn.parser.handler.IContentHandler;

final class  EventHandler {
	private final IContentHandler iContentHandler;

	EventHandler(final IContentHandler pIContentHandler) {
		this.iContentHandler = pIContentHandler;
	}

	public void handleEvents(final Deque<ParsingEvent> pEventStack) throws ParseException {
		ParsingEvent lParseEvt = null;
		while(!pEventStack.isEmpty()) {
			lParseEvt = pEventStack.pop();
			this.handleEvent(lParseEvt);
		}
	}

	public void handleEvent(final ParsingEvent pParseEvent) throws ParseException {
		switch (pParseEvent.getEventType()) {
		case START_ELEMENT:
			this.iContentHandler.startElement((Bloc)pParseEvent.getRelatedComponent());
			break;
		case COMPUTE_SECTION:
			this.iContentHandler.compute((Section)pParseEvent.getRelatedComponent(), pParseEvent.getPayload());
			break;
		case END_ELEMENT:
			this.iContentHandler.endElement((Bloc)pParseEvent.getRelatedComponent());
			break;
		case UNREFERENCED_SECTION:
			this.iContentHandler.handleUnreferencedSection(pParseEvent.getSectionName(), pParseEvent.getPayload());
			break;
		default:
			break;
		}
	}
}