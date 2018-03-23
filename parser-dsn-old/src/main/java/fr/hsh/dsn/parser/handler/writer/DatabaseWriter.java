package fr.hsh.dsn.parser.handler.writer;

import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayDeque;
import java.util.Deque;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.hibernate.jmx.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.hsh.dsn.orm.constants.INeodesBlocBinder;
import fr.hsh.dsn.orm.constants.INeodesSectionBinder;
import fr.hsh.dsn.orm.constants.NeodesBinder;
import fr.hsh.dsn.orm.persistence.MultiPersistenceUnitManager;
import fr.hsh.dsn.orm.persistence.MultiPersistenceUnitManager.PUcode;
import fr.hsh.dsn.parser.grammar.ComputedGrammar;
import fr.hsh.dsn.parser.grammar.metamodel.Bloc;
import fr.hsh.dsn.parser.grammar.metamodel.Section;
import fr.hsh.dsn.parser.handler.IContentHandler;
import fr.hsh.utils.Cipherer;

public class DatabaseWriter implements IContentHandler {

	private static final Logger logger = LoggerFactory.getLogger(DatabaseWriter.class);
	private static final EntityManagerFactory emf = MultiPersistenceUnitManager.getEntityManagerFactory(PUcode.DSN_STOCK);
	static {
		// mise en place JMX
		SessionFactory sf = ((HibernateEntityManagerFactory)emf).getSessionFactory();
		StatisticsService statsMBean = new StatisticsService();
		statsMBean.setSessionFactory(sf);
		statsMBean.setStatisticsEnabled(true);
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		try {
			mBeanServer.registerMBean(statsMBean, new ObjectName("Hibernate:application=Statistics"));
		} catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException | MalformedObjectNameException e) {
			e.printStackTrace();
		}
		// fin JMX
	}

	private final String dsnVersion;
	private EntityManager em = null;
	private final Deque<Object> dtoStack = new ArrayDeque<Object>();
	private final int maxFlushSize;
	private int nbEntities;

	private Cipherer ciph = null;

	public DatabaseWriter(final String pDsnVersion, final int pMaxFlushSize) {
		this.dsnVersion = pDsnVersion;
		this.maxFlushSize = pMaxFlushSize;
		try {
			this.ciph =  new Cipherer("toto", "my8lSalt".getBytes());
		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void startDocument() {
		this.em = emf.createEntityManager();
		this.em.getTransaction().begin();
	}

	@Override
	public void startElement(Bloc pBloc) {
		switch (pBloc.getName()) {
		case ComputedGrammar.BOF: break;
		case ComputedGrammar.ENVELOPE: break;
		case ComputedGrammar.DSN: break;
		default:
			Bloc lParentBloc = (Bloc)pBloc.getParent();
			Class parentTableClass = null;
			Object parentTable = null;
			Object table = null;
			INeodesBlocBinder blocBinder = NeodesBinder.getBlocBinder(this.dsnVersion, pBloc.getName());
			INeodesBlocBinder parentBlocBinder = null;
			if (blocBinder != null) {
				try {
					table = blocBinder.getTableClass().newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}

				// si il existe un parent
				// on le persiste (TODO: si pas deja fait)
				if ((parentTable = this.dtoStack.peek()) != null) {
					String parentTableName = parentTable.getClass().getSimpleName();
					switch (parentTableName) {
					case "T05":
					case "T06":
					case "T11":
						parentTable = this.dtoStack.pop();
						this.persist(parentTable);
						parentTable = this.dtoStack.peek();
					default:
						this.persist(parentTable);
						parentTableClass = parentTable.getClass();
						// on lie la table parente a la table fille
						String parentTableSetterMethodName = null;
						parentTableSetterMethodName = "set"+parentTableClass.getSimpleName();
						try {
							methodCall(table, parentTableSetterMethodName, new Class[]{parentTableClass}, new Object[]{parentTable});
						} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
				this.dtoStack.push(table);
			} else {
				logger.error("Le bloc '{}' n'a pas été déclaré programmatiquement pour la version '{}' de la norme NEODES", pBloc.getName(), this.dsnVersion);
			}
		}
	}

	@Override
	public void compute(Section pSection, String pValue) {
		if ("S21.G00.30.001".equals(pSection.getName())) {
			if (this.ciph != null) {
				try {
					pValue = this.ciph.aesEncode(pValue);
				} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
					e.printStackTrace();
				}
			}
		}
		INeodesSectionBinder sb = NeodesBinder.getSectionBinder(this.dsnVersion, pSection.getName());
		if (sb != null) {
			Class tableClass = sb.getTableClass();
			Object table = this.dtoStack.peek();
			if (tableClass == table.getClass()) {
				String methodName = "set"+sb.getFieldName();
				try {
					methodCall(table, methodName, new Class[]{String.class}, new Object[]{pValue});
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			} else {
				// KO
			}
		} else {
			logger.error("La rubrique '{}' n'a pas été déclarée programmatiquement pour la version '{}' de la norme NEODES", pSection.getName(), this.dsnVersion);
		}
	}

	@Override
	public void endElement(Bloc pBloc) {
		INeodesBlocBinder blocBinder = NeodesBinder.getBlocBinder(this.dsnVersion, pBloc.getName());
		Object table = null;
		if (ComputedGrammar.DSN.equals(pBloc.getName())) {
			table = this.dtoStack.pop();
			this.persist(table);
		} else {
			switch (blocBinder.getTableClass().getSimpleName()) {
			case "T00":
			case "T05":
			case "T06":
			case "T11":
				break;
			default:
				table = this.dtoStack.pop();
				this.persist(table);
			}
		}
	}

	@Override
	public void endDocument() {
		this.em.getTransaction().commit();
		this.em.close();
	}

	public static Object methodCall(final Object pObject, final String pMethodName, final Class[] pParamsType, final Object[] pParams) throws NoSuchMethodException, SecurityException, IllegalAccessException,
	IllegalArgumentException, InvocationTargetException {
		Class aClass = pObject.getClass();
		Method aMethod = aClass.getMethod(pMethodName, pParamsType);
		Object aReturnedObject = aMethod.invoke(pObject, pParams);
		return aReturnedObject;
	}

	/**
	 * Description:
	 * 
	 * @param pTable
	 */
	private void persist(final Object pTable) {
		this.em.persist(pTable);
		if ((++this.nbEntities % this.maxFlushSize) ==0) {
			//flush a batch of inserts and release memory:
			logger.info("before flush");
			this.em.flush();
			logger.info("after flush");
			//			this.em.clear();
		}
	}
}
