package fr.hsh.dsn.errors;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorsManager {

	private static final Logger		logger		= LoggerFactory.getLogger(ErrorsManager.class);

	private static ErrorsManager	sSingleton	= null;
	private static boolean			sInit		= false;

	private final ResourceBundle	mBundle;

	private ErrorsManager(final ResourceBundle pBundle) {
		this.mBundle = pBundle;
	}
	
	public static synchronized void initialize(final Locale pLocale) {
		logger.trace(" > initialize(Locale pLocale)");

		if (sSingleton == null) {
			if (pLocale == null) {
				sSingleton = new ErrorsManager(ResourceBundle.getBundle("errors.messages-dsn-core", pLocale));
			} else {
				sSingleton = new ErrorsManager(ResourceBundle.getBundle("errors.messages-dsn-core", Locale.getDefault()));
			}
			sInit = true;
		}
		
		logger.trace(" < initialize(Locale pLocale)");
	}
	public static synchronized void initialize() {
		logger.trace(" > initialize()");

		if (sSingleton == null) {
			sSingleton = new ErrorsManager(ResourceBundle.getBundle("errors.messages-dsn-core", Locale.getDefault()));
			sInit = true;
		}
		
		logger.trace(" < initialize()");
	}
	
	public static synchronized void initialize(final String pErrorsMsgPath) {
		logger.trace(" > initialize(String pErrorsMsgPath)");

		if (sSingleton == null) {
			final ClassLoader lClassLoader = Thread.currentThread().getContextClassLoader();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println(lClassLoader.getResource(pErrorsMsgPath).getPath().toString());
			System.out.println();
			System.out.println();
			System.out.println();
			final InputStream lInputStream = lClassLoader.getResourceAsStream(pErrorsMsgPath);
			
			if (lInputStream != null) {
				try {
					sSingleton = new ErrorsManager(new PropertyResourceBundle(lInputStream));
					sInit = true;
				} catch (final IOException e) {
					logger.error("Error on initializing error management stack '" + pErrorsMsgPath + "'", e);
				}
			} else {
				logger.error("File '" + pErrorsMsgPath + "' unreachable");
			}
		}

		logger.trace(" < initialize(String pErrorsMsgPath)");
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

	public String getErrorMessage(final ErrorCode pErreur, final Object... pArgs) {
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
		ErrorsManager.initialize("errors/messages-dsn-core.properties");
		System.out.println(ErrorsManager.getInstance().getErrorMessage(ErrorCode.CODE_ERROR_0001, "SG01.001.001", "V1"));
		System.out.println(ErrorsManager.getInstance().getErrorMessage(ErrorCode.CODE_RETURN_OK));
		
		ErrorsManager.initialize();
		System.out.println(ErrorsManager.getInstance().getErrorMessage(ErrorCode.CODE_ERROR_0001, "SG01.001.001", "V1"));
		System.out.println(ErrorsManager.getInstance().getErrorMessage(ErrorCode.CODE_RETURN_OK));
	}
}
