package fr.hsh.dsn.orm.persistence;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class MultiPersistenceUnitManager {
	private static String configFilePath = "hibernate.properties";
	private static boolean initialized = false;
	private static final Map<PUcode, Map<String, String>> confMap = new EnumMap<>(PUcode.class);
	private static final Map<PUcode, EntityManagerFactory> emfMap = new EnumMap<>(PUcode.class);
	private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(); // read write lock monitor
	public static synchronized void initialize(final String pConfigFilePath) {
		configFilePath = pConfigFilePath;
		initialized = true;
	}
	public static boolean isInitialized() {
		return initialized;
	}
	private static Map<String, String> getPersistenceUnitConfig(final PUcode pPersistenceUnit) {
		Map<String, String> lPUconfig = null;
		lPUconfig = confMap.get(pPersistenceUnit);
		if (lPUconfig == null) {
			try {
				lPUconfig = new HashMap<String, String>();
				Configuration lConfig = new PropertiesConfiguration(configFilePath);
				lConfig = lConfig.subset(pPersistenceUnit.name);
				Iterator<String> keys = lConfig.getKeys();
				while (keys.hasNext()) {
					String key = keys.next();
					String value = lConfig.getString(key);
					lPUconfig.put(key, value);
				}
				confMap.put(pPersistenceUnit, lPUconfig);
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}
		return lPUconfig;
	}

	/**
	 * Create and return an EntityManagerFactory for the named persistence unit.
	 *
	 * @param pPersistenceUnit The persistence unit enum code
	 *
	 * @return The factory that creates EntityManagers configured according to the specified persistence unit
	 */
	private static EntityManagerFactory createEntityManagerFactory(final PUcode pPersistenceUnit) {
		Map<String, String> configurationOverrides = getPersistenceUnitConfig(pPersistenceUnit);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(pPersistenceUnit.name, configurationOverrides);
		return emf;
	}
	
	public static EntityManagerFactory getEntityManagerFactory(final PUcode pPersistenceUnit) {
		EntityManagerFactory lEmf = null;
		readWriteLock.readLock().lock();
		// multiple readers can enter this section
		// if not locked for writing, and no writers waiting
		// to lock for writing.
		lEmf = emfMap.get(pPersistenceUnit);
		readWriteLock.readLock().unlock();
		if (lEmf == null) {
			readWriteLock.writeLock().lock();
			// only one writer can enter this section,
			// and only if no threads are currently reading.
			if ((lEmf = emfMap.get(pPersistenceUnit))==null) {
				try {
					lEmf = createEntityManagerFactory(pPersistenceUnit);
					emfMap.put(pPersistenceUnit, lEmf);
				} finally {
					readWriteLock.writeLock().unlock();
				}
			}
		}
		return lEmf;
	}
	
	public static EntityManagerFactory reloadAndGetManagerFactory(final PUcode pPersistenceUnit) {
		EntityManagerFactory lEmf = null;
		readWriteLock.writeLock().lock();
		// only one writer can enter this section,
		// and only if no threads are currently reading.
		try {
			lEmf = createEntityManagerFactory(pPersistenceUnit);
			emfMap.put(pPersistenceUnit, lEmf);
		} finally {
			readWriteLock.writeLock().unlock();
		}
		return lEmf;
	}
	
	

	
	public enum PUcode {
		DSN_STRUCTURES("DSNstructures-PU"),
		DSN_VERSION("DSNversion-PU"),
		DSN_STOCK("DSNstock-PU");

		private String	name	= "";

		PUcode(final String pName) {
			this.name = pName;
		}

		@Override
		public String toString() {
			return this.name;
		}
	}
	
	public enum NatureDeclaration {
		MENSUELLE("01"),
		SIG_FIN_CONTRAT("02"),
		SIG_ARRET_TRAVAIL("04"),
		SIG_REPRISE_TRAVAIL("05");

		private String	name	= "";

		NatureDeclaration(final String pName) {
			this.name = pName;
		}

		@Override
		public String toString() {
			return this.name;
		}
	}
	
	
}
