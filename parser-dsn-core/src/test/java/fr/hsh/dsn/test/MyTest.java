package fr.hsh.dsn.test;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
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
import fr.hsh.dsn.parser.handler.NoOpContentHandler;
import fr.hsh.utils.DateUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyTest {
	
	@Before
	public void setUp() throws Exception {
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testLoadGrammarInDB() {
		String database = ClassLoader.getSystemResource("HSQLDB").getPath()+"/DB";
		

		try {
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		
		try(
				InputStream inputStream = getClass().getResourceAsStream("/dsnstructurebyversion.sql");
				Connection conn = DriverManager.getConnection("jdbc:hsqldb:file:"+database+";shutdown=true", "sa",  "")
			) {
			SqlFile sqlFile = new SqlFile(new InputStreamReader(inputStream), "init", System.out, "UTF-8", true, new File("."));
			sqlFile.setConnection(conn);
			sqlFile.execute();
			
			Statement statement = conn.createStatement() ;
			ResultSet tupples = statement.executeQuery("SELECT * FROM dsnstructurebyversion");
			if (tupples.next()) {
				System.out.println(tupples.getString("versionNorme")+", "+ tupples.getString("numOrdre"    )+", "+tupples.getString("rubriqueName"));
			}
//			statement.executeUpdate("CREATE TABLE IF NOT EXISTS test (key INT , name VARCHAR(50))");
//			
//			statement.executeUpdate("INSERT INTO test (key, name) VALUES (1, 'DUPONT')");
//			
//			ResultSet tupples = statement.executeQuery("SELECT * FROM test WHERE key=1");
//			while(tupples.next()){
//				System.out.println(tupples.getInt("key")+", "+tupples.getString("name")) ;
//			}
//
//			statement.executeQuery("SHUTDOWN");
			statement.close();
			conn.close();    // if there are no other open connection
		} catch (IOException e) {
			fail(e.getMessage());
			e.printStackTrace();
		} catch (SqlToolError e) {
			fail(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testParseDSN() throws URISyntaxException {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		// print logback's internal status
		StatusPrinter.print(lc);
		
		ErrorsManager.initialize();
		if (ErrorsManager.isInitialized()) {
			DateUtils.initialize("yyyyMMdd");
		}
		
		String lDsnVersion = "V01";
		String lFileName = "tstDSN_NTA_3";
		
		DSNParserFactory lParserFactory = null;
		DSNParser lParser = null;
		IContentHandler lHandler = new NoOpContentHandler();
		
		try {
			String lDatabase = "jdbc:hsqldb:file:"+ClassLoader.getSystemResource("HSQLDB").getPath()+"/DB";
			
			Map<String, String> lConfigurationOverrides = new HashMap<>();
			lConfigurationOverrides.put("hibernate.connection.url", lDatabase);
			EntityManagerFactory lEmf = Persistence.createEntityManagerFactory("DSNstructures-PU", lConfigurationOverrides);
			GrammarFactory lGrammarFactory = new GrammarFactory(lEmf);
			lParserFactory = new DSNParserFactory(lGrammarFactory.getGrammar(lDsnVersion));
			lEmf.close();
			lParser = lParserFactory.newDSNParser();
		} catch (NoGrammarFoundException e) {
			e.printStackTrace();
		}
		
		try {
			lParser.parse(Paths.get(ClassLoader.getSystemResource(lFileName).toURI()), "UTF-8", lHandler);
		} catch (GrammarViolationException | ParseException e) {
			e.printStackTrace();
		}
		
	}
}
