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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.hsh.dsn.errors.ErrorCode;
import fr.hsh.dsn.errors.ErrorsManager;
import fr.hsh.dsn.exception.GrammarViolationException;
import fr.hsh.dsn.exception.NoGrammarFoundException;
import fr.hsh.dsn.exception.ParseException;
import fr.hsh.dsn.exception.SocleException;
import fr.hsh.dsn.parser.grammar.ComputedGrammar;
import fr.hsh.dsn.parser.grammar.metamodel.Bloc;
import fr.hsh.dsn.parser.grammar.metamodel.GrammarFactory;
import fr.hsh.dsn.parser.grammar.metamodel.Section;
import fr.hsh.dsn.parser.handler.IContentHandler;
import fr.hsh.dsn.parser.handler.NoOpContentHandler;

/**
 *  Description: Sax like parser for DSN files<br>
 *  
 *  Rq: This class is not thread safe.
 *  @version 
 *  @author
 */
public class DSNParser {
	private static final Logger	logger					= LoggerFactory.getLogger(DSNParser.class);
	private static final String	DEFAULT_CHARSET			= "ISO-8859-1";

	private final String		separator;
	private ComputedGrammar		grammar					= null;
	private LineWrapper			previousLine			= null;
	
	//	private final EventHandler		eventHandler;

	private static List<String> unreferencedSectionList = new ArrayList<>();
	static {
		unreferencedSectionList.add("S10.G00.95.001");
		unreferencedSectionList.add("S10.G00.95.002");
		unreferencedSectionList.add("S10.G00.95.003");
		unreferencedSectionList.add("S10.G00.95.006");
		unreferencedSectionList.add("S10.G00.95.007");
		unreferencedSectionList.add("S10.G00.95.008");
		unreferencedSectionList.add("S10.G00.95.900");
		unreferencedSectionList.add("S10.G00.95.901");
		unreferencedSectionList.add("S20.G00.96.902");
		unreferencedSectionList.add("S21.G00.06.903");
		unreferencedSectionList.add("S21.G00.11.904");
		unreferencedSectionList.add("S21.G00.80.003");
		unreferencedSectionList.add("S21.G00.85.850");
		unreferencedSectionList.add("S10.G00.95.009");
		unreferencedSectionList.add("S21.G00.11.110");
		unreferencedSectionList.add("S21.G00.11.111");
		unreferencedSectionList.add("S21.G00.11.112");
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
		BufferedReader lBuffReader = null;
		Deque<ParsingEvent> lEventDeque = new ArrayDeque<>();
		EventHandler lEventHandler = new EventHandler(pIContentHandler);
		
		try {
			this.previousLine = new LineWrapper(this.grammar.getSection(BOF).getName()+",''");
		} catch (GrammarViolationException e) {
			// can never append
			logger.error(e.getMessage());
		}
		
		try {
			lBuffReader = new BufferedReader(new InputStreamReader(pIs, pCharset != null?pCharset:DEFAULT_CHARSET));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return;
		}
		
		String lNewLine = null;
		int numLine = 0;
		pIContentHandler.startDocument();
		try {
			while ((lNewLine = lBuffReader.readLine()) != null) {
				// checkIgnoreList
				// checkBlackList
				LineWrapper lCurrentLine = new LineWrapper(lNewLine);
				if (unreferencedSectionList.contains(lCurrentLine.sectionName)) {
					logger.info("Unreferenced Section {}", lCurrentLine.sectionName);
					lEventDeque.push(new ParsingEvent(lCurrentLine.sectionName, lCurrentLine.payload));
				} else {
					lEventDeque = this.computeLine(this.previousLine, lCurrentLine);
					this.previousLine = lCurrentLine;
				}
				lEventHandler.handleEvents(lEventDeque);
				numLine++;
			}
			lEventDeque = this.computeLine(this.previousLine, new LineWrapper(EOF + ",''"));
			lEventDeque.pollLast();
			lEventHandler.handleEvents(lEventDeque);
		} catch (IOException e) {
			String lError = ErrorsManager.getInstance().getErrorMessage(ErrorCode.CODE_ERROR_0010, numLine);
			ParseException lParseException = new ParseException(ErrorCode.CODE_ERROR_0010.toString(), lError, e);
			logger.error("{} - Erreur ligne {}: {}", new Object[] {lParseException.getLogId().toString(), numLine, e.getMessage()});
			throw lParseException;
		} catch (GrammarViolationException e) {
			logger.error("{} - Erreur ligne {}: {}", new Object[] {e.getLogId().toString(), numLine, e.getMessage()});
			throw (GrammarViolationException) e.prefixMessage("Erreur ligne - '" + numLine + "': ");
		} finally {
			try {
				lBuffReader.close();
			} catch (IOException e) {
				logger.error("Error on closing BufferedReader - ", e);
			}
		}
		pIContentHandler.endDocument();
	}
	
	public void parse(final Path pFilePath, final String pCharset, final IContentHandler pIContentHandler) throws GrammarViolationException, ParseException {
		InputStream lInputStream = null;
		try {
			lInputStream = Files.newInputStream(pFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.parse(lInputStream, pCharset, pIContentHandler);
		} catch (GrammarViolationException e) {
			throw (GrammarViolationException) e.prefixMessage("Fichier - '" + pFilePath.toString() + "': ");
		} catch (ParseException e) {
			throw (ParseException) e.prefixMessage("Fichier - '" + pFilePath.toString() + "': ");
		}
	}
	
	private Deque<ParsingEvent> computeLine(final LineWrapper pPreviousLine, final LineWrapper pCurrentLine) throws GrammarViolationException {
		String lPreviousSection = pPreviousLine.sectionName;
		String lCurrentSection = pCurrentLine.sectionName;
		Section lStartSection = this.grammar.getSection(lPreviousSection);
		Section lTargetSection = this.grammar.getSection(lCurrentSection);
		int lStartSearchIndex = lStartSection.indexInParent()+1;
		
		logger.trace("INPUT: [{}, {}]", lPreviousSection, lCurrentSection);
		
		Deque<ParsingEvent> lEventDeque = ((Bloc)lStartSection.getParent()).findComponent(lStartSearchIndex, lTargetSection);
		
		for (ParsingEvent lParsingEvent : lEventDeque) {
			if (lParsingEvent.getEventType() == ParsingEventType.COMPUTE_SECTION) {
				lParsingEvent.setPayload(pCurrentLine.payload);
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
	
	
	private class LineWrapper {
		private final String sectionName;
		private final String payload;
		private LineWrapper(final String pLine) throws GrammarViolationException {
			int lStartPayloadIndex = pLine.indexOf(separator);
			if (lStartPayloadIndex == -1) {
				String error = ErrorsManager.getInstance().getErrorMessage(ErrorCode.CODE_ERROR_0009, separator);
				throw new GrammarViolationException(ErrorCode.CODE_ERROR_0009.toString(), error);
			}
			this.sectionName = pLine.substring(0, lStartPayloadIndex);
			this.payload = pLine.substring(lStartPayloadIndex+separator.length()+1, pLine.length()-1);
		}
	}
	
	public static void main(String[] args) throws GrammarViolationException, NoGrammarFoundException {
		
		String lArguments[] = args;
		FileInputStream lFstream = null;
		try {
			lFstream = new FileInputStream(lArguments[0]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String lDsnVersion = "V01";
		String database = "jdbc:hsqldb:file:"+ClassLoader.getSystemResource("HSQLDB").getPath()+"/DB";
		
		Map<String, String> configurationOverrides = new HashMap<>();
		configurationOverrides.put("hibernate.connection.url", database);
		EntityManagerFactory lEmf = Persistence.createEntityManagerFactory("DSNstructures-PU", configurationOverrides);
		GrammarFactory lGrammarFactory = new GrammarFactory(lEmf);
		DSNParserFactory lParserFactory = new DSNParserFactory(lGrammarFactory.getGrammar(lDsnVersion));
		lEmf.close();
		
		DSNParser lParser = lParserFactory.newDSNParser();
		
		try {
			// Create the content handler
			IContentHandler lNoOpHandler = new NoOpContentHandler();
			
			// Parse the file with the specify content handler
			long t = System.currentTimeMillis();
			lParser.parse(lFstream, "UTF-8", lNoOpHandler);
		} catch (SocleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
