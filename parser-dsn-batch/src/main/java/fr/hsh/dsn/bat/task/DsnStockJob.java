package fr.hsh.dsn.bat.task;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.hsh.dsn.bat.ExitValue;
import fr.hsh.dsn.bat.task.DsnStockJob.JobExecutionReport;
import fr.hsh.dsn.exception.GrammarViolationException;
import fr.hsh.dsn.exception.NoGrammarFoundException;
import fr.hsh.dsn.exception.ParseException;
import fr.hsh.dsn.parser.DSNParser;
import fr.hsh.dsn.parser.DSNParserFactory;
import fr.hsh.dsn.parser.grammar.metamodel.GrammarFactory;
import fr.hsh.dsn.parser.handler.IContentHandler;

public class DsnStockJob implements Callable<JobExecutionReport> {

	private static final Logger 			logger 					= LoggerFactory.getLogger(DsnStockJob.class);

	private final DSNParser			parser;
	private final String			jobName;
	private final URL				fileUrl;
	private final IContentHandler	contentHandler;

	public class JobExecutionReport {
		private final ExitValue			exitValue;
		private final String			jobName;
		private final URL				fileUrl;
		private final IContentHandler	contentHandler;
		private final String			workerName	= Thread.currentThread().getName();
		private JobExecutionReport(final ExitValue pExitValue) {
			super();
			this.exitValue = pExitValue;
			this.jobName = DsnStockJob.this.jobName;
			this.fileUrl = DsnStockJob.this.fileUrl;
			this.contentHandler = DsnStockJob.this.contentHandler;
		}
		/**
		 * @return Renvoie exitValue.
		 */
		public ExitValue getExitValue() {
			return this.exitValue;
		}
		/**
		 * @return Renvoie jobName.
		 */
		public String getJobName() {
			return this.jobName;
		}
		/**
		 * @return Renvoie fileUrl.
		 */
		public URL getFileUrl() {
			return this.fileUrl;
		}
		/**
		 * @return Renvoie contentHandler.
		 */
		public IContentHandler getContentHandler() {
			return this.contentHandler;
		}
		/**
		 * @return Renvoie workerName.
		 */
		public String getWorkerName() {
			return this.workerName;
		}
	}

	public DsnStockJob(final String pJobName, final String pDsnVersion, final URL pFileUrl, final IContentHandler pContentHandler) throws NoGrammarFoundException {
		super();
		String lDatabase = "jdbc:hsqldb:file:"+ClassLoader.getSystemResource("HSQLDB").getPath()+"/DB";
		
		Map<String, String> lConfigurationOverrides = new HashMap<>();
		lConfigurationOverrides.put("hibernate.connection.url", lDatabase);
		EntityManagerFactory lEmf = Persistence.createEntityManagerFactory("DSNstructures-PU", lConfigurationOverrides);
		GrammarFactory lGrammarFactory = new GrammarFactory(lEmf);
		DSNParserFactory lParserFactory = new DSNParserFactory(lGrammarFactory.getGrammar(pDsnVersion));

		this.parser = lParserFactory.newDSNParser();
		this.jobName = pJobName;
		this.fileUrl = pFileUrl;
		this.contentHandler = pContentHandler;
	}

	@Override
	public JobExecutionReport call() throws Exception {
		ExitValue exitValue = null;
		InputStream lIs = this.fileUrl.openStream();
		try {
			this.parser.parse(lIs, "UTF-8", this.contentHandler);
		} catch (GrammarViolationException e) {
			logger.error("{} - {} - {}",  new Object[]{e.getLogId().toString(), e.getCode(), e.getMessage()});
			e.printStackTrace();
			exitValue = ExitValue.FONCTIONNAL_ERROR;
		} catch (ParseException e) {
			logger.error("{} - {} - {}",  new Object[]{e.getLogId().toString(), e.getCode(), e.getMessage()});
			e.printStackTrace();
			exitValue = ExitValue.TECHNICAL_ERROR;
		}
		lIs.close();
		exitValue = ExitValue.OK;
		return new JobExecutionReport(exitValue);
	}
}
