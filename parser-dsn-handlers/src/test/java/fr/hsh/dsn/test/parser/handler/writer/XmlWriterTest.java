package fr.hsh.dsn.test.parser.handler.writer;

import static org.junit.Assert.fail;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import fr.hsh.dsn.errors.ErrorsManager;
import fr.hsh.dsn.exception.GrammarViolationException;
import fr.hsh.dsn.exception.NoGrammarFoundException;
import fr.hsh.dsn.exception.ParseException;
import fr.hsh.dsn.parser.DSNParser;
import fr.hsh.dsn.parser.DSNParserFactory;
import fr.hsh.dsn.parser.grammar.metamodel.GrammarFactory;
import fr.hsh.dsn.parser.handler.IContentHandler;
import fr.hsh.dsn.parser.handler.writer.XmlWriter;
import fr.hsh.utils.DateUtils;

public class XmlWriterTest {

	private static final Logger		logger		= LoggerFactory.getLogger(XmlWriterTest.class);
	private DSNParser parser;

	@Before
	public void setUp() throws Exception {
		logger.debug("> setUp()");
		
		ErrorsManager.initialize();
		if (ErrorsManager.isInitialized()) {
			DateUtils.initialize("yyyyMMdd");
		}
		
		String lDsnVersion = "V01";
		
		DSNParserFactory lParserFactory = null;
		
		try {
			String lDatabase = "jdbc:hsqldb:res:HSQLDB/DB";
			
			Map<String, String> lConfigurationOverrides = new HashMap<>();
			lConfigurationOverrides.put("hibernate.connection.url", lDatabase);
			EntityManagerFactory lEmf = Persistence.createEntityManagerFactory("DSNstructures-PU", lConfigurationOverrides);
			GrammarFactory lGrammarFactory = new GrammarFactory(lEmf);
			lParserFactory = new DSNParserFactory(lGrammarFactory.getGrammar(lDsnVersion));
			lEmf.close();
			this.parser = lParserFactory.newDSNParser();
		} catch (NoGrammarFoundException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		logger.debug("< setUp()");
	}

	@Test
	public void testParseDSN_NTA_1() throws URISyntaxException {
		logger.debug("> testParseDSN_NTA_1()");
		String lFileName = "tstDSN_NTA_1";
		IContentHandler lHandler = new XmlWriter();
		
		try {
			this.parser.parse(Paths.get(ClassLoader.getSystemResource(lFileName).toURI()), "UTF-8", lHandler);
		} catch (GrammarViolationException | ParseException e) {
			fail(e.getMessage());
		}
		logger.debug("< testParseDSN_NTA_1()");
	}
	
	
	@Test
	public void testParseDSN_NTA_3() throws URISyntaxException {
		logger.debug("> testParseDSN_NTA_3()");
		String lFileName = "tstDSN_NTA_3";
		IContentHandler lHandler = new XmlWriter();
		
		try {
			this.parser.parse(Paths.get(ClassLoader.getSystemResource(lFileName).toURI()), "UTF-8", lHandler);
			fail("Should have failed before");
		} catch (GrammarViolationException | ParseException e) {
			// ok designed to fail
		}
		logger.debug("< testParseDSN_NTA_3()");
	}
}
