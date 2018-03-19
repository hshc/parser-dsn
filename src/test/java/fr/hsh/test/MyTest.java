package fr.hsh.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MyTest {
	
	@Before
	public void setUp() throws Exception {
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void test() {
		String database = "/home/nta/views/git/DSN/parser-dsn/src/test/resources/HSQLDB/DB";

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
}
