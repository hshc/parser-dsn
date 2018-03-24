package fr.hsh.dsn.parser.grammar.metamodel;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.hsh.dsn.errors.ErrorCode;
import fr.hsh.dsn.errors.ErrorsManager;
import fr.hsh.dsn.exception.GrammarViolationException;
import fr.hsh.dsn.parser.ParsingEvent;
import fr.hsh.dsn.parser.ParsingEventType;

public class Bloc extends Component {
	private static final Logger logger = LoggerFactory.getLogger(Bloc.class);

	private final List<Bloc> orderedNestedBlocList;
	private final List<Section> orderedNestedSectionList;

	public Bloc(final String pName, final boolean pMandatory, final boolean pLoopable) {
		super(pName, pMandatory, pLoopable);
		this.orderedNestedBlocList = new ArrayList<>();
		this.orderedNestedSectionList = new ArrayList<>();
	}

	@Override
	void addNestedComponent(final Component pNestedComponent) {
		super.addNestedComponent(pNestedComponent);
		switch (pNestedComponent.getType()) {
		case Bloc:
			this.orderedNestedBlocList.add((Bloc) pNestedComponent);
			break;
		case Section:
			this.orderedNestedSectionList.add((Section) pNestedComponent);
			break;
		}
	}

	/**
	 * @return Renvoie orderedNestedBlocList.
	 */
	public List<Bloc> getOrderedNestedBlocList() {
		return this.orderedNestedBlocList;
	}

	/**
	 * @return Renvoie orderedNestedSectionList.
	 */
	public List<Section> getOrderedNestedSectionList() {
		return this.orderedNestedSectionList;
	}

	private List<Bloc> getSubBlocList() {
		List<Bloc> lBlocScope = null;
		if (this.orderedNestedBlocList.isEmpty()) {
			lBlocScope = new ArrayList<Bloc>();
		} else {
			for(Bloc lSubBloc : this.orderedNestedBlocList) {
				if (lBlocScope == null) {
					lBlocScope = lSubBloc.getSubBlocList();
				} else {
					lBlocScope.addAll(lSubBloc.getSubBlocList());
				}
			}
		}
		lBlocScope.add(this);
		return lBlocScope;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.Bloc;
	}

	@Override
	protected Deque<ParsingEvent> checkInComponent(final Component pTargetComponent) throws GrammarViolationException {
		Deque<ParsingEvent> lEventDeque = this.findComponent(0, pTargetComponent);
		lEventDeque.push(new ParsingEvent(this, ParsingEventType.START_ELEMENT));
		return lEventDeque;
	}

	@Override
	protected Deque<ParsingEvent> checkOutComponent(final Component pTargetComponent) throws GrammarViolationException {
		if (this.getParent() != null) {
			int lThisIndexInParent = this.indexInParent();
			if (this != pTargetComponent.parent) {
				lThisIndexInParent++;
			} else {
				if (this.isLoopable()) {
					// do not increment lComponentIndex since we want to loop again on this bloc
				} else {
					String error = ErrorsManager.getInstance().getErrorMessage(ErrorCode.CODE_ERROR_0008, pTargetComponent.parent.getName());
					throw new GrammarViolationException(ErrorCode.CODE_ERROR_0008.toString(), error);
				}
			}
			Deque<ParsingEvent> lEventDeque = this.getParent().findComponent(lThisIndexInParent, pTargetComponent);
			lEventDeque.push(new ParsingEvent(this, ParsingEventType.END_ELEMENT));
			return lEventDeque;
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder lToStringBuilder = new StringBuilder("");
		lToStringBuilder
		.append(this.name)
		.append(this.mandatory?(this.loopable?"+":""):(this.loopable?"*":"?"));
		int i = 0;
		for (Bloc lBloc : this.orderedNestedBlocList) {
			if (i == 0) {
				lToStringBuilder.append("[");
			}
			lToStringBuilder.append(lBloc.toString());
			i++;
			if (i < this.orderedNestedBlocList.size()) {
				lToStringBuilder.append(", ");
			}
		}
		if (i > 0) {
			lToStringBuilder.append("]");
		}
		return lToStringBuilder.toString();
	}

	@Override
	protected boolean isInAncestor(final Component pComponent) throws GrammarViolationException {
		if (this == pComponent) {
			return true;
		} else if (this.getParent() != null) {
			return this.getParent().isInAncestor(pComponent);
		} else {
			return false;
		}
	}

	@Override
	protected void throwError(Component pTargetComponent) throws GrammarViolationException {
		String error = ErrorsManager.getInstance().getErrorMessage(ErrorCode.CODE_ERROR_0005, this.getName(), pTargetComponent.getName());
		throw new GrammarViolationException(ErrorCode.CODE_ERROR_0005.toString(), error);
	}
}
