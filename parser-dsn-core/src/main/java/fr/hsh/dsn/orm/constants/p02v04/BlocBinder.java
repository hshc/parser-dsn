package fr.hsh.dsn.orm.constants.p02v04;

import fr.hsh.dsn.orm.constants.INeodesBlocBinder;

public enum BlocBinder implements INeodesBlocBinder {
	S10_G00_00("S10.G00.00", fr.hsh.dsn.orm.entities.p02v02.T00.class),
	S10_G00_01("S10.G00.01", fr.hsh.dsn.orm.entities.p02v02.T01.class),
	S10_G00_02("S10.G00.02", fr.hsh.dsn.orm.entities.p02v02.T02.class),
	S10_G00_03("S10.G00.03", fr.hsh.dsn.orm.entities.p02v02.T03.class),
	S20_G00_05("S20.G00.05", fr.hsh.dsn.orm.entities.p02v02.T05.class),
	S21_G00_06("S21.G00.06", fr.hsh.dsn.orm.entities.p02v02.T06.class),
	S21_G00_11("S21.G00.11", fr.hsh.dsn.orm.entities.p02v02.T11.class),
	S21_G00_15("S21.G00.15", fr.hsh.dsn.orm.entities.p02v02.T15.class),
	S21_G00_30("S21.G00.30", fr.hsh.dsn.orm.entities.p02v02.T30.class),
	S21_G00_31("S21.G00.31", fr.hsh.dsn.orm.entities.p02v02.T31.class),
	S21_G00_40("S21.G00.40", fr.hsh.dsn.orm.entities.p02v02.T40.class),
	S21_G00_41("S21.G00.41", fr.hsh.dsn.orm.entities.p02v02.T41.class),
	S21_G00_62("S21.G00.62", fr.hsh.dsn.orm.entities.p02v02.T62.class),
	S21_G00_70("S21.G00.70", fr.hsh.dsn.orm.entities.p02v02.T70.class),
	S21_G00_72("S21.G00.72", fr.hsh.dsn.orm.entities.p02v02.T72.class),
	S21_G00_85("S21.G00.85", fr.hsh.dsn.orm.entities.p02v02.T85.class),
	S90_G00_90("S90.G00.90", fr.hsh.dsn.orm.entities.p02v02.T90.class);


	private final String		dsnCode;
	private final Class<?>		tableClass;


	BlocBinder(final String pDsnCode, final Class<?> pTableClass) {
		this.dsnCode = pDsnCode;
		this.tableClass = pTableClass;
	}


	@Override
	public String getDsnCode() {
		return this.dsnCode;
	}


	@Override
	public Class<?> getTableClass() {
		return this.tableClass;
	}

	public static INeodesBlocBinder getEnum(final String pDsnCode) {
		INeodesBlocBinder bb = null;
		try {
			bb = BlocBinder.valueOf(pDsnCode.replaceAll("\\.", "_"));
		} catch (Exception lE) {
		}
		return bb;
	}
}
