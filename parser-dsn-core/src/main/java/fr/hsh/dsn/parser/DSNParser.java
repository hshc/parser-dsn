package fr.hsh.dsn.parser;

import static fr.hsh.dsn.parser.grammar.ComputedGrammar.BOF;
import static fr.hsh.dsn.parser.grammar.ComputedGrammar.EOF;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.hsh.dsn.errors.ErrorCode;
import fr.hsh.dsn.errors.ErrorsManager;
import fr.hsh.dsn.exception.GrammarViolationException;
import fr.hsh.dsn.exception.NoGrammarFoundException;
import fr.hsh.dsn.exception.ParseException;
import fr.hsh.dsn.parser.grammar.ComputedGrammar;
import fr.hsh.dsn.parser.grammar.metamodel.Bloc;
import fr.hsh.dsn.parser.grammar.metamodel.GrammarFactory;
import fr.hsh.dsn.parser.grammar.metamodel.Section;
import fr.hsh.dsn.parser.handler.IContentHandler;
import fr.hsh.dsn.parser.handler.NoOpContentHandler;
import fr.hsh.socle.exception.core.SocleException;

/**
 *  Description: Sax like parser for DSN files<br>
 *  
 *  Rq: This class is not thread safe.
 *  @version 
 *  @author
 */
public class DSNParser {
	private static final Logger	logger			= LoggerFactory.getLogger(DSNParser.class);
	private static final String	DEFAULT_CHARSET	= "ISO-8859-1";
	
	private final String	separator;
	private ComputedGrammar	grammar			= null;
	private String			previousLine	= null;
	
	//	private final EventHandler		eventHandler;

	private static List<String> ignoreList = new ArrayList<>();
	static {
		ignoreList.add("S10.G00.95.001");
		ignoreList.add("S10.G00.95.002");
		ignoreList.add("S10.G00.95.003");
		ignoreList.add("S10.G00.95.006");
		ignoreList.add("S10.G00.95.007");
		ignoreList.add("S10.G00.95.008");
		ignoreList.add("S10.G00.95.900");
		ignoreList.add("S10.G00.95.901");
		ignoreList.add("S20.G00.96.902");
		ignoreList.add("S21.G00.06.903");
		ignoreList.add("S21.G00.11.904");
		ignoreList.add("S21.G00.80.003");
		ignoreList.add("S21.G00.85.850");
		ignoreList.add("S10.G00.95.009");
		ignoreList.add("S21.G00.11.110");
		ignoreList.add("S21.G00.11.111");
		ignoreList.add("S21.G00.11.112");
	};



	
	
//			###############################################################################################################################
//			####										rubriques infos DSN
//			###############################################################################################################################
//			id.dsn.concentrateur= S20.G00.05.009
//			identifiant.flux =S10.G00.95.900
//			rang.fichier.dsn =S20.G00.96.902
//			type.flux =S10.G00.00.005
//			nature.declaration = S20.G00.05.001
//			dsn.origine =S10.G00.15.007
//			#id.ops=S21.G00.70.002
//			version.dsn=S10.G00.00.006
//			id.ops=P02V05/S21.G00.15.002;P02V05/S21.G00.70.002;P02V01/S21.G00.15.002;P02V01/S21.G00.70.002;P02V04/S21.G00.15.002;P02V04/S21.G00.70.002;P03V03/S21.G00.15.002;P02V2/S21.G00.15.002;P02V2/S21.G00.70.002;P01V2/S21.G00.70.002;P02V00/S21.G00.70.002;P01V2/S21.G00.15.002;P02V00/S21.G00.15.002
			
			
	
	
//			###############################################################################################################################
//			####										rubrique Debut et FIN FFSA
//			###############################################################################################################################
//			ffsa.dsn.start.element=S10.G00.00.001
//			ffsa.dsn.end.element=S90.G00.90.002
//	
//	
//			###############################################################################################################################
//			####										rubriques FFSA 
//			## Préfixé toutes les nouvelles rubriques FFSA par le mot 'RUBRIQUE_FFSA'
//			###############################################################################################################################
//			#S05
//			NIVEAU_LOT.version.protocole.echange =S05.G51.00.001
//			NIVEAU_LOT.identifiant.lot = S05.G51.00.002
//			NIVEAU_LOT.type.lot=S05.G51.00.003
//			NIVEAU_LOT.date.generation.lot=S05.G51.00.004
//			NIVEAU_LOT.heure.generation.lot=S05.G51.00.005
//			NIVEAU_LOT.nombre.objet.lot=S05.G51.00.006
//
//			#S95
//			NIVEAU_LOT.identifiant.lot.2=S95.G51.00.001
//			NIVEAU_LOT.nombre.objet.lot.2=S95.G51.00.002
//
//
//
//			###############################################################################################################################
//			####										rubriques COS 
//			## Préfixé toutes les nouvelles rubriques FFSA par le mot 'RUBRIQUE_FFSA'
//			###############################################################################################################################
//			##NIVEAU_DSN -- rubrique FFSA
//			NIVEAU_DSN.identifiant.dsn.concentrateur.ffsa=S20.G51.00.001
//			NIVEAU_DSN.date.traitement.concentrateur.ffsa=S20.G51.00.002
//			NIVEAU_DSN.heure.traitement.concentrateur.ffsa=S20.G51.00.003
//
//
//			#NIVEAU_DSN -- rubrique COS
//			NIVEAU_DSN.c1=S10.G00.95.001
//			NIVEAU_DSN.c2=S10.G00.95.002
//			NIVEAU_DSN.c3=S10.G00.95.003
//			NIVEAU_DSN.c4=S10.G00.95.006
//			NIVEAU_DSN.c5=S10.G00.95.008
//			NIVEAU_DSN.c6=S10.G00.95.900
//			NIVEAU_DSN.c7=S10.G00.95.901
//			NIVEAU_DSN.c8=S10.G00.95.007
//			NIVEAU_DSN.c9=S20.G00.96.902
//			NIVEAU_DSN.c10=S21.G00.06.903
//			NIVEAU_DSN.c11=S21.G00.11.904
//
//			NIVEAU_DSN.c12=S21.G00.11.111
	
	/**
	 * Constructor of the class.
	 */
	DSNParser(final ComputedGrammar pGrammar) {
		this.grammar = pGrammar;
		this.separator = ",";
	}
	
	DSNParser(final ComputedGrammar pGrammar, final String pSeparator) {
		this.grammar = pGrammar;
		this.separator = pSeparator;
	}

	/**
	 * @param pIs InputStream to parse
	 * @param pCharset of the input stream (if null, default is "ISO-8859-1")
	 * @throws GrammarViolationException 
	 * @throws ParseException 
	 */
	public void parse(final InputStream pIs, final String pCharset, final IContentHandler pIContentHandler) throws GrammarViolationException, ParseException {
		BufferedReader br = null;
		Deque<ParsingEvent> lEventDeque = new ArrayDeque<>();
		EventHandler eventHandler = new EventHandler(pIContentHandler);

		try {
			this.previousLine = this.grammar.getSection(BOF).getName()+",''";
		} catch (GrammarViolationException e) {
			// can never append
			logger.error(e.getMessage());
		}

		try {
			br = new BufferedReader(new InputStreamReader(pIs, pCharset != null?pCharset:DEFAULT_CHARSET));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return;
		}

		String currentLine = null;
		int nbLine = 0;
		pIContentHandler.startDocument();
		try {
			while ((currentLine = br.readLine()) != null) {
				// checkIgnoreList
				// checkBlackList

				if (ignoreList.contains(currentLine.substring(0,currentLine.lastIndexOf(separator)))) {
					logger.info("Ignore {}", currentLine);
				} else {
					lEventDeque = this.computeLine(this.previousLine, currentLine);
					eventHandler.handleEvents(lEventDeque);
					this.previousLine = currentLine;
				}
				nbLine++;
			}
			lEventDeque = this.computeLine(this.previousLine, EOF + ",''");
			lEventDeque.pollLast();
			eventHandler.handleEvents(lEventDeque);
		} catch (IOException e) {
			logger.error("Erreur ligne {}: {}", nbLine, e.getMessage());
			logger.error("Error when reading BufferedReader new line - ", e);
		} catch (GrammarViolationException e) {
			logger.error("{} - Erreur ligne {}: {}", new Object[] {e.getLogId().toString(), nbLine, e.getMessage()});
			throw (GrammarViolationException) e.prefixMessage("Erreur ligne - '" + nbLine + "'");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.error("Error when closing BufferedReader - ", e);
			}
		}
		pIContentHandler.endDocument();
	}

	private Deque<ParsingEvent> computeLine(final String pPreviousLine, final String pCurrentLine) throws GrammarViolationException {
		String lPreviousSection = null;
		String lCurrentSection = null;
		Section lStartSection = null;
		Section lTargetSection = null;
		String lPayload = null;
		int lStartSearchIndex = 0;

		int lStartPayloadIndex = pCurrentLine.indexOf(separator);
		if (lStartPayloadIndex == -1) {
			String error = ErrorsManager.getInstance().getMessageErreur(ErrorCode.CODE_ERREUR_0009, separator);
			throw new GrammarViolationException(ErrorCode.CODE_ERREUR_0009.toString(), error);
		} 

		lCurrentSection = pCurrentLine.substring(0, lStartPayloadIndex);
		lPayload = pCurrentLine.substring(lStartPayloadIndex+2, pCurrentLine.length()-1);
		lTargetSection = this.grammar.getSection(lCurrentSection);

		lPreviousSection = pPreviousLine.substring(0, pPreviousLine.indexOf(separator));
		lStartSection = this.grammar.getSection(lPreviousSection);
		lStartSearchIndex = lStartSection.indexInParent()+1;

		logger.trace("INPUT: [{}, {}]", lPreviousSection, lCurrentSection);

		Deque<ParsingEvent> lEventDeque = ((Bloc)lStartSection.getParent()).findComponent(lStartSearchIndex, lTargetSection);

		for (ParsingEvent lParsingEvent : lEventDeque) {
			if (lParsingEvent.getEventType() == ParsingEventType.COMPUTE_SECTION) {
				lParsingEvent.setPayload(lPayload);
			}
		}

		if (logger.isTraceEnabled()) {
			StringBuilder lOutputTrace = new StringBuilder();
			lOutputTrace.append("OUTPUT: ");
			for (ParsingEvent lParsingEvent : lEventDeque) {
				lOutputTrace.append(lParsingEvent.toString()).append(", ");
			}

			logger.trace(lOutputTrace.toString());
			logger.trace("");
		}

		return lEventDeque;
	}

	public static void main(String[] args) throws GrammarViolationException, NoGrammarFoundException {
		
		String arguments[] = args;
		FileInputStream fstream = null;
		try {
			fstream = new FileInputStream(arguments[0]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String lDsnVersion = "V01";
		ComputedGrammar grammar = GrammarFactory.getGrammar(lDsnVersion);
		DSNParser parser = new DSNParser(grammar);
		try {

			// Create the content handler
			IContentHandler noOpHandler = new NoOpContentHandler();

			// Parse the file with the specify content handler
			long t = System.currentTimeMillis();
			parser.parse(fstream, "UTF-8", noOpHandler);
		} catch (SocleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
