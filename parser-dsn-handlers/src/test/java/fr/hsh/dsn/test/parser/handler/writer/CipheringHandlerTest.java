package fr.hsh.dsn.test.parser.handler.writer;

import static org.junit.Assert.fail;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.NoSuchPaddingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.hsh.dsn.errors.ErrorsManager;
import fr.hsh.dsn.exception.GrammarViolationException;
import fr.hsh.dsn.exception.NoGrammarFoundException;
import fr.hsh.dsn.exception.ParseException;
import fr.hsh.dsn.parser.DSNParser;
import fr.hsh.dsn.parser.DSNParserFactory;
import fr.hsh.dsn.parser.grammar.metamodel.GrammarFactory;
import fr.hsh.dsn.parser.handler.IContentHandler;
import fr.hsh.dsn.parser.handler.writer.CipheringHandler;
import fr.hsh.dsn.parser.handler.writer.XmlWriter;
import fr.hsh.utils.Cipherer;
import fr.hsh.utils.DateUtils;

public class CipheringHandlerTest {

	private static final Logger	logger	= LoggerFactory.getLogger(CipheringHandlerTest.class);
	private DSNParser			parser;
	private Cipherer			ciph	= null;

	@Before
	public void setUp() throws Exception {
		logger.debug("> setUp()");

		try {
			this.ciph =  new Cipherer("toto", "my8lSalt".getBytes());
		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidAlgorithmParameterException e) {
			fail(e.getMessage());
		}
		
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
			parser = lParserFactory.newDSNParser();
		} catch (NoGrammarFoundException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		
		logger.debug("< setUp()");
	}

	@After
	public void tearDown() throws Exception {
		parser = null;
	}

	@Test
	public void testParseDSN_NTA_1() throws URISyntaxException {
		logger.debug("> testParseDSN_NTA_1()");
		String lFileName = "tstDSN_NTA_1";
		IContentHandler lHandler = new CipheringHandler(new XmlWriter(), this.ciph).addCipheringForField("G03.002");
		
		try {
			this.parser.parse(Paths.get(ClassLoader.getSystemResource(lFileName).toURI()), "UTF-8", lHandler);
		} catch (GrammarViolationException | ParseException e) {
			fail(e.getMessage());
		}
		logger.debug("< testParseDSN_NTA_1()");
	}
	

}
