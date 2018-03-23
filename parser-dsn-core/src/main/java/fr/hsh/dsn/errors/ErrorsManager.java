package fr.hsh.dsn.errors;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorsManager {

	private static final Logger				logger		= LoggerFactory.getLogger(ErrorsManager.class);

	private static ErrorsManager			sSingleton	= null;
	private static boolean					sInit		= false;

	private final PropertyResourceBundle	mBundle;

	private ErrorsManager(final PropertyResourceBundle pBundle) {
		this.mBundle = pBundle;
	}

	public static synchronized void initialize(final String pMsgErreursPath) {
		logger.trace(" > initialize()");

		if (sSingleton == null) {
			final ClassLoader lClassLoader = Thread.currentThread().getContextClassLoader();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println(lClassLoader.getResource(pMsgErreursPath).getPath().toString());
			System.out.println();
			System.out.println();
			System.out.println();
			final InputStream lInputStream = lClassLoader.getResourceAsStream(pMsgErreursPath);
			
			if (lInputStream != null) {
				try {
					sSingleton = new ErrorsManager(new PropertyResourceBundle(lInputStream));
					sInit = true;
				} catch (final IOException e) {
					logger.error("Error on initializing error management stack '" + pMsgErreursPath + "'", e);
				}
			} else {
				logger.error("File '" + pMsgErreursPath + "' unreachable");
			}
		}

		logger.trace(" < initialize()");
	}

	public static boolean isInitialized() {
		return sInit;
	}

	public static ErrorsManager getInstance() {
		return sSingleton;
	}

	public ResourceBundle getBundle() {
		return this.mBundle;
	}

	public String getMessageErreur(final ErrorCode pErreur, final Object... pArgs) {
		logger.trace(" > getMessageErreur()");
		String lOutput = null;
		ResourceBundle lMessages = this.getBundle();
		if (lMessages == null) {
			lOutput = pErreur.toString();
		} else {
			MessageFormat lFormatter = new MessageFormat("");
			lFormatter.applyPattern(lMessages.getString(pErreur.toString()));
			lOutput = lFormatter.format(pArgs);
		}
		logger.trace(" < getMessageErreur()");
		return lOutput;
	}

	public static void main(String[] args) {
		ErrorsManager.initialize("error_messages.properties");
		System.out.println(ErrorsManager.getInstance().getMessageErreur(ErrorCode.CODE_ERREUR_0001, "test"));
		System.out.println(ErrorsManager.getInstance().getMessageErreur(ErrorCode.CODE_RETOUR_OK));
	}
}
