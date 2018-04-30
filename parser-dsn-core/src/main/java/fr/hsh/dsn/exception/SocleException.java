package fr.hsh.dsn.exception;

public abstract class SocleException extends Exception {

	private static final long serialVersionUID = 7614802352089716125L;
	
	private final String			mLogId;
	private final String			mCode;
	private final String			mShortLabel;
	private String					mMessage;

	protected SocleException(final String pCode, final String pLibelleCourt, final Throwable pCause) {
		super(pCause.getMessage(), pCause);
		this.mLogId = String.valueOf(System.currentTimeMillis());
		this.mCode = pCode;
		this.mShortLabel = pLibelleCourt;
	}
	protected SocleException(final String pCode, final String pLibelleCourt, final String pMessage, final Throwable pCause) {
		this(pCode, pLibelleCourt, pCause);		
		this.mMessage = pMessage + "( caused by '" + pCause.getMessage() + "')";
	}

	protected SocleException(final String pCode, final String pLibelleCourt, final SocleException pCause) {
		super(pCause.getMessage(), (Throwable)((pCause instanceof Throwable)?pCause:null));
		final SocleException lCause = pCause;
		this.mLogId = lCause.getLogId();
		this.mCode = pCode==null?lCause.getCode():pCode;
		this.mShortLabel = pLibelleCourt==null?lCause.getShortLabel():pLibelleCourt;
	}
	protected SocleException(final String pCode, final String pLibelleCourt, final String pMessage, final SocleException pCause) {
		this(pCode, pLibelleCourt, pCause);
		this.mMessage = pMessage + "( caused by '" + pCause.getMessage() + "')";
	}

	protected SocleException(final String pCode, final String pLibelleCourt) {
		this.mLogId = String.valueOf(System.currentTimeMillis());
		this.mCode = pCode;
		this.mShortLabel = pLibelleCourt;
	}

	protected SocleException(final String pCode, final String pLibelleCourt, final String pMessage) {
		this(pCode, pLibelleCourt);
		this.mMessage = pMessage;
	}

	public String getLogId() {
		return this.mLogId;
	}

	public String getCode() {
		return this.mCode;
	}

	public String getShortLabel() {
		return this.mShortLabel;
	}

	@Override
	public String getMessage() {
		if (this.mMessage == null || this.mMessage.isEmpty()) {
			String lMessage = super.getMessage();
			if (lMessage == null || lMessage.isEmpty()) {
				return this.mShortLabel;
			} else {
				this.mMessage = lMessage;
			}
		} 
		return this.mMessage;
	}
	
	public SocleException prefixMessage(final String pMessage) {
		this.mMessage = pMessage + getMessage();
		return this;
	}
	
	public SocleException postfixMessage(final String pMessage) {
		this.mMessage = getMessage() + pMessage;
		return this;
	}
}