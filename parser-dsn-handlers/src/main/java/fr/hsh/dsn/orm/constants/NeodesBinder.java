package fr.hsh.dsn.orm.constants;

public class NeodesBinder {
	public static INeodesSectionBinder getSectionBinder(final String pDsnVersion, final String pDsnCode) {
		switch (pDsnVersion) {
		case "P02V02":
			return fr.hsh.dsn.orm.constants.p02v02.SectionBinder.getEnum(pDsnCode);
		default:
			return null;
		}
	}

	public static INeodesBlocBinder getBlocBinder(final String pDsnVersion, final String pDsnCode) {
		switch (pDsnVersion) {
		case "P02V02":
			return fr.hsh.dsn.orm.constants.p02v02.BlocBinder.getEnum(pDsnCode);
		default:
			return null;
		}
	}
}
