package fr.hsh.dsn.parser.grammar.metamodel;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.hsh.dsn.exception.GrammarViolationException;
import fr.hsh.dsn.parser.ParsingEvent;

public abstract class Component {
	private static final Logger logger = LoggerFactory.getLogger(Component.class);

	protected final String name;
	protected final boolean mandatory;
	protected final boolean loopable;
	protected final List<Component> orderedNestedComponentList;
	protected Component parent;

	Component(final String pName, final boolean pMandatory, final boolean pLoopable) {
		super();
		this.name = pName;
		this.mandatory = pMandatory;
		this.loopable = pLoopable;
		this.orderedNestedComponentList = new ArrayList<Component>();
	}

	Component(final String pName, final boolean pMandatory) {
		this(pName, pMandatory, false);
	}

	void addNestedComponent(final Component pNestedComponent) {
		pNestedComponent.setParent(this);
		this.orderedNestedComponentList.add(pNestedComponent);
	}

	/**
	 * @return Renvoie name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return Renvoie mandatory.
	 */
	protected boolean isMandatory() {
		return this.mandatory;
	}

	/**
	 * @return Renvoie loopable.
	 */
	protected boolean isLoopable() {
		return this.loopable;
	}

	/**
	 * @return Renvoie parent.
	 */
	public Component getParent() {
		return this.parent;
	}

	/**
	 * @param pParentBloc parent à définir.
	 */
	protected void setParent(final Component pParent) {
		this.parent = pParent;
	}

	public int indexInParent() {
		if (this.parent != null) {
			return this.parent.orderedNestedComponentList.indexOf(this);
		} else {
			return -1;
		}
	}

	protected int level() {
		if (this.parent == null) {
			return 0;
		} else {
			return this.parent.level()+1;
		}
	}

	/**
	 * Starting from <code>this</code> component and considering that :
	 * <ul>
	 * <li> it can has sub-component of type Component,</li>
	 * <li> it holds an ordered list of possible sub-component,</li>
	 * <li> this ordered list is compute according to the normalized standard written in DB</li>
	 * </ul>
	 * <p>
	 * , this function's goal is to find, in this component hierarchy (up and down), the "<code>pTargetComponent</code>" corresponding to the type of new line identified in the DSN file.
	 * Depending on the "mandatory", "looping", "optional" elements on the path, it will raise a stack of event elements.
	 * 
	 * @param pStepCompareComponentStartIndex : the index in the sub-component list of <code>this</code> component in which we try to find the target document <code>pTargetComponent</code>
	 * @param pTargetComponent : the component corresponding to the new line identified in the DSN file.
	 * @return Deque<ParsingEvent> : the stack of event elements
	 * @throws GrammarViolationException
	 */
	public Deque<ParsingEvent> findComponent(final int pStepCompareComponentStartIndex, final Component pTargetComponent) throws GrammarViolationException {
		
		String tab = "";
		if (logger.isTraceEnabled()) {
			for (int i = this.level(); i > 0; i--) {
				tab += "  ";
			}
		}
		logger.trace("Check {}    -> {}{}", new Object[]{this.getType().toString(), tab, this.toString()});

		if (pStepCompareComponentStartIndex < this.orderedNestedComponentList.size()) {
			Component lCurrentStepComponent = this.orderedNestedComponentList.get(pStepCompareComponentStartIndex);
			if (pTargetComponent.isInAncestor(lCurrentStepComponent)) {
				return lCurrentStepComponent.checkInComponent(pTargetComponent); // childhood scan
			} else {
				// check if the current component can by bypass without missing any mandatory bloc or section
				if (lCurrentStepComponent.isMandatory()) {
					// deal with specific error management whether the component is a mandatory section or a mandatory bloc
					lCurrentStepComponent.throwError(pTargetComponent);
				}
				return this.findComponent(pStepCompareComponentStartIndex+1, pTargetComponent); // brotherhood scan
			}
		} else {
			return this.checkOutComponent(pTargetComponent); // parenthood scan
		}
	}


	abstract protected ComponentType getType();
	abstract protected Deque<ParsingEvent> checkInComponent(final Component pTargetComponent) throws GrammarViolationException;
	abstract protected Deque<ParsingEvent> checkOutComponent(final Component pTargetComponent) throws GrammarViolationException;
	/**
	 * Check if <code>pComponent</code> is in the parent/dominator tree of <code>this</code> component
	 * @param pComponent
	 * @return true if <code>pComponent</code> is in the parent/dominator tree of <code>this</code> component, false otherwise
	 * @throws GrammarViolationException
	 */
	abstract protected boolean isInAncestor(final Component pComponent) throws GrammarViolationException;
	abstract protected void throwError(Component pTargetComponent) throws GrammarViolationException;
}
