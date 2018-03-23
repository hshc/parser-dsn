package fr.hsh.dsn.parser.grammar.metamodel;

import static fr.hsh.dsn.parser.grammar.ComputedGrammar.BOF;
import static fr.hsh.dsn.parser.grammar.ComputedGrammar.DSN;
import static fr.hsh.dsn.parser.grammar.ComputedGrammar.ENVELOPE;
import static fr.hsh.dsn.parser.grammar.ComputedGrammar.EOF;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.hsh.dsn.enums.NatureDeclaration;
import fr.hsh.dsn.exception.NoGrammarFoundException;
import fr.hsh.dsn.orm.entities.DSNStructureByVersion;
import fr.hsh.dsn.parser.grammar.ComputedGrammar;

public class GrammarFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(GrammarFactory.class);
	private static final Map<String, ComputedGrammar> grammarMap = new HashMap<>();
	private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(); // read write lock monitor
	
	private final EntityManagerFactory mEntityManagerFactory;
	
	public GrammarFactory(final EntityManagerFactory pEntityManagerFactory) {
		this.mEntityManagerFactory = pEntityManagerFactory;
	}


	public ComputedGrammar getGrammar(final String pGrammarVersion) throws NoGrammarFoundException {
		return getGrammar(pGrammarVersion, null);
	}

	public ComputedGrammar getGrammar(final String pGrammarVersion, final NatureDeclaration pNatureDSN) throws NoGrammarFoundException {
		ComputedGrammar lComputedGrammar = null;
		NatureDeclaration lNatureDSN = (pNatureDSN == null ? NatureDeclaration.MENSUELLE : pNatureDSN);
		readWriteLock.readLock().lock();
		// multiple readers can enter this section
		// if not locked for writing, and not writers waiting
		// to lock for writing.
		String lGrammarKey = pGrammarVersion + "_" + lNatureDSN.toString();
		lComputedGrammar = grammarMap.get(lGrammarKey);
		readWriteLock.readLock().unlock();
		if (lComputedGrammar == null) {
			readWriteLock.writeLock().lock();
			try {
				// only one writer can enter this section,
				// and only if no threads are currently reading.
				if ((lComputedGrammar = grammarMap.get(lGrammarKey))==null) {
					lComputedGrammar = computeGrammarVersion(pGrammarVersion, lNatureDSN);
					grammarMap.put(lGrammarKey, lComputedGrammar);
				}
			} finally {
				readWriteLock.writeLock().unlock();
			}
		}
		return lComputedGrammar;
	}

	private ComputedGrammar computeGrammarVersion(final String pGrammarVersion, final NatureDeclaration pNatureDSN) throws NoGrammarFoundException {
		ComputedGrammar lComputedGrammar = null;
		Map<String, Bloc> lGrammarBlocMap = new HashMap<>();
		Map<String, Section> lGrammarSectionMap = new HashMap<>();
		List<DSNStructureByVersion> lSectionList = null;

		lSectionList = getGrammarDefinitionFromDB(pGrammarVersion, pNatureDSN);

		String lSectionCode = null;
		String lBlocCode = null;
		boolean lMandatorySection;
		boolean lBeginBlocSection;
		boolean lLoopableBloc;
		boolean lMandatoryBloc;
		String lEndingSection = null;
		String lRegExp = "";
		Section lSection = null;

		Deque<Bloc> blocStack = new ArrayDeque<>();
		int beginingCounter = 0;
		int endCounter = 0;

		// DSN Envelope Begin of file
		blocStack.push(new Bloc(ENVELOPE, true, false));
		lSection = new Section(BOF, false, "");
		blocStack.peekFirst().addNestedComponent(lSection);
		lGrammarSectionMap.put(lSection.getName(), lSection);
		beginingCounter++;

		// DSN
		Bloc lDsnEnveloppe = new Bloc(DSN, true, false);
		// Bloc lDsnEnveloppe = new Bloc(DSN, false, false);
		blocStack.peekFirst().addNestedComponent(lDsnEnveloppe);
		blocStack.push(lDsnEnveloppe);
		beginingCounter++;
		// DSN Envelope End of file
		DSNStructureByVersion endingElement = new DSNStructureByVersion();
		endingElement.setRubriqueName(DSN+".END");
		endingElement.setBeginEndFlag("E");
		lSectionList.add(endingElement);

		// DSN Envelope End of file
		endingElement = new DSNStructureByVersion();
		endingElement.setRubriqueName(EOF);
		endingElement.setBeginEndFlag("E");
		lSectionList.add(endingElement);

		for (DSNStructureByVersion lDsnStructureByVersion : lSectionList) {
			lSectionCode = lDsnStructureByVersion.getRubriqueName();
			lBlocCode = lSectionCode.substring(0, lSectionCode.lastIndexOf('.'));
			lMandatorySection = "O".equals(lDsnStructureByVersion.getObligatoireFacultatifFlag()) ;
			if (lBeginBlocSection = lDsnStructureByVersion.getBeginEndFlag().startsWith("B")) {
				lDsnStructureByVersion.setBeginEndFlag(lDsnStructureByVersion.getBeginEndFlag().substring(1));
			}
			lLoopableBloc = "L".equals(lDsnStructureByVersion.getLoopingFlag());
			lMandatoryBloc = "O".equals(lDsnStructureByVersion.getObligatoireFacultatifBlocFlag());
			lEndingSection = null;
			if (lDsnStructureByVersion.getBeginEndFlag().startsWith("E")) {
				lEndingSection = lDsnStructureByVersion.getBeginEndFlag();
			}

			if (lBeginBlocSection) {
				logger.info("NEW BLOC '{}{}'", lBlocCode, lMandatoryBloc?(lLoopableBloc?"+":""):(lLoopableBloc?"*":"?"));
			}
			logger.info("{}{}", lSectionCode, (lMandatorySection?"":"?"));

			if (lBeginBlocSection) {
				Bloc lNewBloc = new Bloc(lBlocCode, lMandatoryBloc, lLoopableBloc);
				if (!blocStack.isEmpty()) {
					blocStack.peekFirst().addNestedComponent(lNewBloc);
				}
				blocStack.push(lNewBloc);
				beginingCounter++;
			}

			lSection = new Section(lSectionCode, lMandatorySection, lRegExp);
			blocStack.peekFirst().addNestedComponent(lSection);
			lGrammarSectionMap.put(lSection.getName(), lSection);

			if (lEndingSection != null) {
				for (int i = lEndingSection.length(); (i > 0) && !blocStack.isEmpty(); i--) {
					Bloc bloc = blocStack.pop();
					lGrammarBlocMap.put(bloc.getName(), bloc);
					logger.info("END BLOC '{}'", lBlocCode);
					endCounter++;
					if (blocStack.isEmpty()) {
						lComputedGrammar = new ComputedGrammar(pGrammarVersion, bloc.getOrderedNestedSectionList().get(0), lGrammarBlocMap, lGrammarSectionMap);
					}
				}
			}
		}
		if (beginingCounter != endCounter) {
			logger.error("beginingCounter: {}, endingCounter: {} - Le nombre de bloc entrant ne correspond pas au nombre de blocs sortant",beginingCounter ,endCounter);
		}
		logger.debug(lComputedGrammar.dumpGrammar());
		return lComputedGrammar;
	}

	/**
	 * Description:
	 * 
	 * @param pGrammarVersion
	 * @return
	 * @throws NoGrammarFoundException 
	 */
	private List<DSNStructureByVersion> getGrammarDefinitionFromDB(final String pGrammarVersion, final NatureDeclaration pNatureDeclaration) throws NoGrammarFoundException {
		List<DSNStructureByVersion> lDbSections;
//		EntityManagerFactory emf = MultiPersistenceUnitManager.getEntityManagerFactory(PUcode.DSN_STRUCTURES);
		
		EntityManager lEntityManager = this.mEntityManagerFactory.createEntityManager();
		lEntityManager.getTransaction().begin();
		Query lQuery = lEntityManager.createQuery("select a from DSNStructureByVersion a where a.id.versionNorme=:pGrammarVersion and a.id.natureDeclaration=:pNatureDeclaration order by a.id.numOrdre");
		lQuery.setParameter("pGrammarVersion", pGrammarVersion);
		lQuery.setParameter("pNatureDeclaration", pNatureDeclaration.toString());

		lDbSections = lQuery.getResultList();

		lEntityManager.flush();
		lEntityManager.clear();
		lEntityManager.getTransaction().commit();
		lEntityManager.close();
//		mEntityManagerFactory.close();
		if (lDbSections.isEmpty()) {
			throw new NoGrammarFoundException(pGrammarVersion);
		}
		return lDbSections;
	}
}