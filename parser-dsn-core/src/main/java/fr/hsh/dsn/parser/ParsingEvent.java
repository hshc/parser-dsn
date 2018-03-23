package fr.hsh.dsn.parser;

import fr.hsh.dsn.parser.grammar.metamodel.Component;


public class ParsingEvent {
	private final Component relatedComponent;
	private final ParsingEventType parsingEventType;
	private String payload;

	public ParsingEvent(final Component pRelatedComponent, final ParsingEventType pParsingEventType) {
		super();
		this.relatedComponent = pRelatedComponent;
		this.parsingEventType = pParsingEventType;
	}

	/**
	 * @return Renvoie payload.
	 */
	public String getPayload() {
		return this.payload;
	}

	/**
	 * @param pRelatedLine payload a definir.
	 */
	public void setPayload(final String pPayload) {
		this.payload = pPayload;
	}

	/**
	 * @return Renvoie relatedComponent.
	 */
	public Component getRelatedComponent() {
		return this.relatedComponent;
	}

	/**
	 * @return Renvoie parsingEventType.
	 */
	public ParsingEventType getEventType() {
		return this.parsingEventType;
	}

	@Override
	public String toString() {
		StringBuilder lStr = new StringBuilder(this.parsingEventType.toString());
		lStr.append("[")
		.append(this.relatedComponent.getName());
		if (this.payload !=null) {
			lStr.append(", ").append(this.payload);
		}
		lStr.append("]");

		return lStr.toString();
	}

}
