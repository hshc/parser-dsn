package fr.hsh.dsn.bat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.hibernate.HibernateException;

import fr.hsh.utils.ApplicativeProperties;

public class Main {

	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("Le chemin du fichier de configuration doit être passé en argument.");
			System.exit(ExitValue.CONFIG_FILE_UNLOADABLE.value);
		}

		String lConfPath = args[0];
		Properties lAppProperties = new Properties();
		try {
			lAppProperties.load(new FileInputStream(lConfPath));
			for (Object lName : lAppProperties.keySet()) {
				if (ApplicativeProperties.LOG_CONF_PATH.toString().equals(lName)) {
					String lValue = lAppProperties.getProperty((String)lName);
					System.setProperty((String)lName, lValue);
				}
			}

			System.exit(LoadParserDSN.run(lConfPath).value);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Impossible de charger de fichier de configuration '"+lConfPath+"'");
			System.exit(ExitValue.CONFIG_FILE_UNLOADABLE.value);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Impossible de charger de fichier de configuration '"+lConfPath+"'");
			System.exit(ExitValue.CONFIG_FILE_UNLOADABLE.value);
		} catch (HibernateException e) {
			e.printStackTrace();
			System.exit(ExitValue.HIBERNATE_EXCEPTION.value);
		} catch (Throwable e) {
			e.printStackTrace();
			System.exit(ExitValue.UNDEFINED.value);
		}
	}

}
