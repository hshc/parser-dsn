package fr.hsh.dsn.parser.handler.stack;

import java.util.Map;

import fr.hsh.dsn.parser.grammar.metamodel.Component;


public abstract class StackableComponentHandler implements IComponentHandler {

	protected Map<String, Object> contextualDatas;
	protected Component relatedComponent;

	protected StackableComponentHandler(final Component pRelatedComponent) {
		this.relatedComponent = pRelatedComponent;
	}

	/**
	 * @return Renvoie relatedComponent.
	 */
	public Component getRelatedComponent() {
		return this.relatedComponent;
	}

	public Map<String, Object> getContextualDatas() {
		return this.contextualDatas;
	}
}
