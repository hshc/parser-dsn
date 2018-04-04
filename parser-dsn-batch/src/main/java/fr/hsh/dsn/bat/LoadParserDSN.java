package fr.hsh.dsn.bat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import fr.hsh.dsn.bat.task.DsnStockJob;
import fr.hsh.dsn.bat.task.DsnStockJob.JobExecutionReport;
import fr.hsh.dsn.bat.task.WorkerFactory;
import fr.hsh.dsn.errors.ErrorsManager;
import fr.hsh.dsn.exception.NoGrammarFoundException;
import fr.hsh.dsn.orm.persistence.MultiPersistenceUnitManager;
import fr.hsh.dsn.parser.handler.IContentHandler;
import fr.hsh.dsn.parser.handler.writer.ContentHandlerFactory;
import fr.hsh.utils.ApplicativeProperties;
import fr.hsh.utils.DateUtils;
import fr.hsh.utils.FolderUnzipper;

public class LoadParserDSN {

	private static final Logger 			logger 					= LoggerFactory.getLogger(LoadParserDSN.class);
	private static final Logger 			loggerReport 			= LoggerFactory.getLogger("reporting");

	private static final String			sDateFormat			= "yyyyMMdd";

	private final Configuration			config;

	public LoadParserDSN(final String pConfigFilePath) throws ConfigurationException, FileNotFoundException, IOException {
		this(new PropertiesConfiguration(pConfigFilePath));
	}

	public LoadParserDSN(final Configuration pConfigFile) {
		// assume SLF4J is bound to logback in the current environment
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		// print logback's internal status
		StatusPrinter.print(lc);
		this.config = pConfigFile;
	}

	public static ExitValue run(final String pConfPath) {
		logger.info("> run()");

		LoadParserDSN parserDsnLoader = null;
		try {
			parserDsnLoader = new LoadParserDSN(pConfPath);
		} catch (ConfigurationException e) {
			e.printStackTrace();
			logger.error("Impossible de charger de fichier de configuration '{}'", pConfPath);
			System.exit(ExitValue.CONFIG_FILE_UNLOADABLE.value);
		}  catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("Impossible de charger de fichier de configuration '{}'", pConfPath);
			System.exit(ExitValue.CONFIG_FILE_UNLOADABLE.value);
		}  catch (IOException e) {
			e.printStackTrace();
			logger.error("Impossible de charger de fichier de configuration '{}'", pConfPath);
			System.exit(ExitValue.CONFIG_FILE_UNLOADABLE.value);
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error("Erreur technique indéterminée", e);
			System.exit(ExitValue.UNDEFINED.value);
		}

		initialize(parserDsnLoader);

		String lDsnVersion = "P02V02";
		//		String lDsnVersion = "V01";
		FileInputStream fstream = null;
		try {
			fstream = new FileInputStream("/home/nta/Documents/DSN/03 - Conception/ExemplesDSN/Analyse DSN Nantes V02.txt");
			//			fstream = new FileInputStream("/home/nta/views/git/DSN/parser-dsn/src/main/resources/exemple N4Ds prévoyance aforsys_Prévoyance-1.txt");
			//			fstream = new FileInputStream("/home/nta/Documents/DSN/03 - Conception/ExemplesDSN/BUG_21_DSN Recette-SFD520 -Valo29.txt");
			//			fstream = new FileInputStream("/home/nta/Documents/DSN/03 - Conception/ExemplesDSN/BUG_23_DSN Recette-SFD520 -Valo32.txt");
			//			fstream = new FileInputStream("/home/nta/Documents/DSN/03 - Conception/ExemplesDSN/BUG_25_DSN Recette-SFD520 -Valo44.txt");
			//			fstream = new FileInputStream("/home/nta/workspaces/workspace_commun/parser-dsn/src/test/resources/tstDSN_NTA_2");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}



		//		DSNParserFactory parserFactory = null;
		//		DSNParser parser = null;
		//		try {
		//			// Create the parser
		//			parserFactory = DSNParserFactory.newInstance(lDsnVersion);
		//			parser = parserFactory.newDSNParser();
		//
		//			// Create the content handler
		//			IContentHandler xmlWriter = ContentHandlerFactory.getXmlWriter();
		//			IContentHandler dbWriter = ContentHandlerFactory.getDatabaseWriter(lDsnVersion);
		//			IContentHandler chainWriter = ContentHandlerFactory.getChainWriterWrapper(dbWriter);
		//			//			IContentHandler chainWriter = ContentHandlerFactory.getChainWriterWrapper(xmlWriter/*, dbWriter*/);
		//
		//			// Parse the file with the specify content handler
		//			long t = System.currentTimeMillis();
		//			parser.parse(fstream, "UTF-8", chainWriter);
		//			System.out.println(System.currentTimeMillis()-t);
		//
		//		} catch (GrammarViolationException e) {
		//			logger.error("{} - {} - {}",  new Object[]{e.getLogId().toString(), e.getCode(), e.getMessage()});
		//			e.printStackTrace();
		//			return ExitValue.FONCTIONNAL_ERROR;
		//		} catch (ParseException e) {
		//			e.printStackTrace();
		//			logger.error("{} - {} - {}",  new Object[]{e.getLogId().toString(), e.getCode(), e.getMessage()});
		//			e.printStackTrace();
		//			return ExitValue.TECHNICAL_ERROR;
		//		} catch (NoGrammarFoundException e) {
		//			logger.error("{} - {} - {}",  new Object[]{e.getLogId().toString(), e.getCode(), e.getMessage()});
		//			e.printStackTrace();
		//			return ExitValue.FONCTIONNAL_ERROR;
		//		}



		ExecutorService executorService = Executors.newFixedThreadPool(4, new WorkerFactory());
		Set<DsnStockJob> callables = new HashSet<DsnStockJob>();
		URL fileUrl = null;
		try {

			fileUrl = new File("/home/nta/views/git/DSN/parser-dsn/src/main/resources/Analyse DSN Nantes V02.txt").toURI().toURL();
			// Create the content handler
			IContentHandler xmlWriter = ContentHandlerFactory.getXmlWriter();
			IContentHandler dbWriter = ContentHandlerFactory.getDatabaseWriter(lDsnVersion);
			IContentHandler chainWriter = ContentHandlerFactory.getChainWriterWrapper(dbWriter);
			callables.add(new DsnStockJob("Job 1", lDsnVersion, fileUrl, chainWriter));

			fileUrl = new File("/home/nta/views/git/DSN/parser-dsn/src/main/resources/Copy of Analyse DSN Nantes V02.txt").toURI().toURL();
			// Create the content handler
			xmlWriter = ContentHandlerFactory.getXmlWriter();
			dbWriter = ContentHandlerFactory.getDatabaseWriter(lDsnVersion);
			chainWriter = ContentHandlerFactory.getChainWriterWrapper(dbWriter);
			callables.add(new DsnStockJob("Job 2", lDsnVersion, fileUrl, chainWriter));

			fileUrl = new File("/home/nta/views/git/DSN/parser-dsn/src/main/resources/Copy (2) of Analyse DSN Nantes V02.txt").toURI().toURL();
			// Create the content handler
			xmlWriter = ContentHandlerFactory.getXmlWriter();
			dbWriter = ContentHandlerFactory.getDatabaseWriter(lDsnVersion);
			chainWriter = ContentHandlerFactory.getChainWriterWrapper(dbWriter);
			callables.add(new DsnStockJob("Job 3", lDsnVersion, fileUrl, chainWriter));

			fileUrl = new File("/home/nta/views/git/DSN/parser-dsn/src/main/resources/Copy (3) of Analyse DSN Nantes V02.txt").toURI().toURL();
			// Create the content handler
			xmlWriter = ContentHandlerFactory.getXmlWriter();
			dbWriter = ContentHandlerFactory.getDatabaseWriter(lDsnVersion);
			chainWriter = ContentHandlerFactory.getChainWriterWrapper(dbWriter);
			callables.add(new DsnStockJob("Job 4", lDsnVersion, fileUrl, chainWriter));



			long t = System.currentTimeMillis();

			List<Future<JobExecutionReport>> futures = null;
			try {
				//				futures = executorService.invokeAll(callables, 5000, TimeUnit.MILLISECONDS);
				futures = executorService.invokeAll(callables);
				for (Future<JobExecutionReport> future : futures) {
					boolean isThreadCancelled = false;
					if (isThreadCancelled = future.isCancelled()) {
						System.out.println("un future "+future.toString()+" a été cancellé");
					} else {
						try {
							String msg = "'" + future.get().getJobName()+"' pris en charge par '"+future.get().getWorkerName() + "' arrive a terme avec le status '" + future.get().getExitValue().name() + "'";
							logger.info(msg);
							loggerReport.info(msg);
						} catch (ExecutionException e) {
							future.cancel(Boolean.TRUE);
							logger.error(e.getMessage(),e);
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				if (null != futures) {
					for (final Future<JobExecutionReport> future : futures) {
						if (!future.isCancelled()) {
							future.cancel(Boolean.TRUE);
							try {
								logger.info("Le job '{}' est finalisé avec succès", future.get().getJobName());
							} catch (InterruptedException e) {
								logger.error(e.getMessage(),e);
							} catch (ExecutionException e) {
								logger.error(e.getMessage(),e);
							}
						}
					}
				}
				executorService.shutdown();
				executorService.shutdownNow();
			}

			System.out.println(System.currentTimeMillis()-t);
		} catch (NoGrammarFoundException e) {
			logger.error("{} - {} - {}",  new Object[]{e.getLogId().toString(), e.getCode(), e.getMessage()});
			return ExitValue.FONCTIONNAL_ERROR;
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(),e);
		}

		logger.info("< run()");
		return ExitValue.OK;
	}

	/**
	 * Description:
	 * 
	 * @param parserDsnLoader
	 */
	private static void initialize(LoadParserDSN parserDsnLoader) {
		String lErrorMsgPath = parserDsnLoader.config.getString(ApplicativeProperties.MSG_ERREURS_PATH.toString());
		String lFormatDate = parserDsnLoader.config.getString(ApplicativeProperties.DATE_FORMAT.toString());
		if (lFormatDate == null) {
			lFormatDate = sDateFormat;
		}

		ErrorsManager.initialize(lErrorMsgPath);
		if (ErrorsManager.isInitialized()) {
			DateUtils.initialize(lFormatDate);
		}
		if (!MultiPersistenceUnitManager.isInitialized()) {
			String hibernateLocation = LoadParserDSN.class.getClassLoader().getResource("hibernate.properties").toString();
			MultiPersistenceUnitManager.initialize(hibernateLocation);
		}
	}

	private static boolean isFileEmpty(final File lFile) {
		InputStream is;
		try {
			is = new FileInputStream(lFile.getAbsolutePath());
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String str = br.readLine();
			if ((str == null) || (str.length() == 0)) {
				return true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return true;
		}
		return false;
	}

	private static void unzipDsnZipFile(final String pZipFilePath, final String pUnzipFolder, final boolean pDeleteUnzipFolderOnExit) throws FileNotFoundException {
		FolderUnzipper folderUnzipper = new FolderUnzipper(pZipFilePath, pDeleteUnzipFolderOnExit);
		File unzipFolder = new File(pUnzipFolder);
		folderUnzipper.unzipToFolder(unzipFolder);
	}
}
