package fr.hsh.dsn.orm.constants;

public interface INeodesSectionBinder {
	public String getDsnCode();

	/**
	 * @return Renvoie tableClass.
	 */
	public Class<?> getTableClass();

	/**
	 * @return Renvoie fieldName.
	 */
	public String getFieldName();
}
