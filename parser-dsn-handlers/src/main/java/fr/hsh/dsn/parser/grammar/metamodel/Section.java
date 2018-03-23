package fr.hsh.dsn.parser.grammar.metamodel;

import java.util.ArrayDeque;
import java.util.Deque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.hsh.dsn.errors.ErreurCode;
import fr.hsh.dsn.errors.GestionErreurs;
import fr.hsh.dsn.exception.GrammarViolationException;
import fr.hsh.dsn.parser.ParsingEvent;
import fr.hsh.dsn.parser.ParsingEventType;

public class Section extends Component {
	private static final Logger logger = LoggerFactory.getLogger(Section.class);

	private final String regExpControl;

	public Section(final String pName, final boolean pMandatory, final String pRegExpControl) {
		super(pName, pMandatory);
		this.regExpControl = pRegExpControl;
	}

	/**
	 * @return Renvoie regExpControl.
	 */
	public String getRegExpControl() {
		return this.regExpControl;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.Section;
	}

	@Override
	protected boolean isInAncestor(final Component pComponent) throws GrammarViolationException {
		if ((pComponent.getType() == ComponentType.Section)) {
			return (pComponent == this);
		} else {
			return this.getParent().isInAncestor(pComponent);
		}
	}

	@Override
	protected Deque<ParsingEvent> checkInComponent(final Component pTargetComponent) throws GrammarViolationException {
		Deque<ParsingEvent> lEventDeque = new ArrayDeque<ParsingEvent>();
		lEventDeque.push(new ParsingEvent(pTargetComponent, ParsingEventType.COMPUTE_SECTION));
		return lEventDeque;
	}

	@Override
	protected Deque<ParsingEvent> checkOutComponent(Component pTargetComponent) throws GrammarViolationException {
		Deque<ParsingEvent> lEventDeque = new ArrayDeque<ParsingEvent>();
		lEventDeque.push(new ParsingEvent(pTargetComponent, ParsingEventType.COMPUTE_SECTION));
		return lEventDeque;
	}

	@Override
	public String toString() {
		StringBuilder lToStringBuilder = new StringBuilder("");
		lToStringBuilder
		.append(this.name)
		.append(this.mandatory?"":"?");
		return lToStringBuilder.toString();
	}

	@Override
	protected void throwError(Component pTargetComponent) throws GrammarViolationException {
		String error = GestionErreurs.getInstance().getMessageErreur(ErreurCode.CODE_ERREUR_0003, this.getName(), pTargetComponent.getName());
		throw new GrammarViolationException(ErreurCode.CODE_ERREUR_0003.toString(), error);
	}
}
