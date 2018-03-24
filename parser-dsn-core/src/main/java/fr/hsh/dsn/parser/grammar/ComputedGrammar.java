package fr.hsh.dsn.parser.grammar;
import java.util.Map;

import fr.hsh.dsn.errors.ErrorCode;
import fr.hsh.dsn.errors.ErrorsManager;
import fr.hsh.dsn.exception.GrammarViolationException;
import fr.hsh.dsn.parser.grammar.metamodel.Bloc;
import fr.hsh.dsn.parser.grammar.metamodel.Section;


public class ComputedGrammar {
	public static final String DSN = "DSN";
	public static final String ENVELOPE = "ENVELOPE";
	public static final String BOF = ENVELOPE+".BOF";
	public static final String EOF = ENVELOPE+".EOF";

	private final String version;
	private final Map<String, Bloc> blocMap;
	private final Map<String, Section> sectionMap;
	public ComputedGrammar(final String pVersion, final Section pBootstrap, final Map<String, Bloc> pBlocMap,  final Map<String, Section> pSectionMap) {
		super();
		this.version = pVersion;
		this.blocMap = pBlocMap;
		this.sectionMap = pSectionMap;
	}

	public String getVersion() {
		return this.version;
	}
	public Bloc getBloc(final String pBlocName) throws GrammarViolationException {
		Bloc lBloc = this.blocMap.get(pBlocName);
		if (lBloc == null) {
			String error = ErrorsManager.getInstance().getErrorMessage(ErrorCode.CODE_ERROR_0002, pBlocName, this.version);
			throw new GrammarViolationException(ErrorCode.CODE_ERROR_0002.toString(), error);
		}
		return lBloc;
	}
	public Section getSection(final String pSectionName) throws GrammarViolationException {
		Section lSection = this.sectionMap.get(pSectionName);
		if (lSection == null) {
			String error = ErrorsManager.getInstance().getErrorMessage(ErrorCode.CODE_ERROR_0001, pSectionName, this.version);
			throw new GrammarViolationException(ErrorCode.CODE_ERROR_0001.toString(), error);
		}
		return lSection;
	}
	public String dumpGrammar() {
		StringBuilder lStrBld = new StringBuilder(this.version);
		lStrBld.append("[")
		.append(this.sectionMap.get(BOF).getParent().toString())
		.append(", ")
		.append(this.sectionMap)
		.append("]");
		return lStrBld.toString();
	}
}
