package fr.hsh.dsn.orm.constants;

public interface INeodesBlocBinder {
	public String getDsnCode();

	/**
	 * @return Renvoie tableClass.
	 */
	public Class<?> getTableClass();
}
