package fr.hsh.dsn.bat;

public enum ExitValue {
	OK(0),
	WARNING(4),
	FONCTIONNAL_ERROR(8),
	TECHNICAL_ERROR(12),
	FATAL_ERROR(16),

	CONFIG_FILE_UNLOADABLE(50),
	DATA_FILE_ABSENT(51),
	DATA_FILE_UNREADABLE(52),
	HIBERNATE_EXCEPTION(62),
	UNDEFINED(100);

	public int value;
	ExitValue(int pValue) {
		this.value = pValue;
	}
}