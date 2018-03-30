package fr.hsh.dsn.orm.entities.p02v02;

import java.io.Serializable;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.TableGenerator;

import fr.hsh.dsn.orm.persistence.MultiPersistenceUnitManager;
import fr.hsh.dsn.orm.persistence.MultiPersistenceUnitManager.PUcode;

/**
 * The persistent class for the T00 database table.
 * 
 */
@Entity
public class T00 implements Serializable {
	private static final long	serialVersionUID	= 1L;

	@Id
	@TableGenerator(name = "T00_gen",
	table = "MULTIPLE_HILO_GEN",
	pkColumnName = "GENERATOR_ID",
	valueColumnName = "HI_VALUE",
	pkColumnValue = "T00_pk",
	allocationSize = 50)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "T00_gen")
	@Column(name="id_envoi")
	private int idEnvoi;

	@Column(name="F001")
	private String f001;

	@Column(name="F002")
	private String f002;

	@Column(name="F003")
	private String f003;

	@Column(name="F004")
	private String f004;

	@Column(name="F005")
	private String f005;

	@Column(name="F006")
	private String f006;

	@Column(name="F007")
	private String f007;

	@Column(name="F008")
	private String f008;

	private int invalid;

	private int recycle;

	//	@OneToOne(mappedBy="t00", cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private T01 t01;

	//bi-directional many-to-one association to T02
	@OneToMany(mappedBy="t00")
	private List<T02> t02s;

	//bi-directional one-to-one association to T03
	//	@OneToOne(mappedBy="t00", cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private T03 t03;

	//bi-directional one-to-one association to T05
	//	@OneToOne(mappedBy="t00", cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private T05 t05;

	//bi-directional one-to-one association to T06
	//	@OneToOne(mappedBy="t00", cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private T06 t06;

	//bi-directional one-to-one association to T11
	//	@OneToOne(mappedBy="t00", cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private T11 t11;

	//bi-directional many-to-one association to T15
	@OneToMany(mappedBy="t00")
	private List<T15> t15s;

	//bi-directional many-to-one association to T30
	@OneToMany(mappedBy="t00")
	private List<T30> t30s;

	//bi-directional many-to-one association to T85
	@OneToMany(mappedBy="t00")
	private List<T85> t85s;

	//bi-directional one-to-one association to T90
	//	@OneToOne(mappedBy="t00", cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private T90 t90;

	public T00() {
	}

	public int getIdEnvoi() {
		return this.idEnvoi;
	}

	public void setIdEnvoi(int idEnvoi) {
		this.idEnvoi = idEnvoi;
	}

	public String getF001() {
		return this.f001;
	}

	public void setF001(String f001) {
		this.f001 = f001;
	}

	public String getF002() {
		return this.f002;
	}

	public void setF002(String f002) {
		this.f002 = f002;
	}

	public String getF003() {
		return this.f003;
	}

	public void setF003(String f003) {
		this.f003 = f003;
	}

	public String getF004() {
		return this.f004;
	}

	public void setF004(String f004) {
		this.f004 = f004;
	}

	public String getF005() {
		return this.f005;
	}

	public void setF005(String f005) {
		this.f005 = f005;
	}

	public String getF006() {
		return this.f006;
	}

	public void setF006(String f006) {
		this.f006 = f006;
	}

	public String getF007() {
		return this.f007;
	}

	public void setF007(String f007) {
		this.f007 = f007;
	}

	public String getF008() {
		return this.f008;
	}

	public void setF008(String f008) {
		this.f008 = f008;
	}

	public int getInvalid() {
		return this.invalid;
	}

	public void setInvalid(int invalid) {
		this.invalid = invalid;
	}

	public int getRecycle() {
		return this.recycle;
	}

	public void setRecycle(int recycle) {
		this.recycle = recycle;
	}

	public T01 getT01() {
		return this.t01;
	}

	public void setT01(T01 t01) {
		this.t01 = t01;
	}

	public List<T02> getT02s() {
		return this.t02s;
	}

	public void setT02s(List<T02> t02s) {
		this.t02s = t02s;
	}

	public T03 getT03() {
		return this.t03;
	}

	public void setT03(T03 t03) {
		this.t03 = t03;
	}

	public T05 getT05() {
		return this.t05;
	}

	public void setT05(T05 t05) {
		this.t05 = t05;
	}

	public T06 getT06() {
		return this.t06;
	}

	public void setT06(T06 t06) {
		this.t06 = t06;
	}

	public T11 getT11() {
		return this.t11;
	}

	public void setT11(T11 t11) {
		this.t11 = t11;
	}

	public List<T15> getT15s() {
		return this.t15s;
	}

	public void setT15s(List<T15> t15s) {
		this.t15s = t15s;
	}

	public List<T30> getT30s() {
		return this.t30s;
	}

	public void setT30s(List<T30> t30s) {
		this.t30s = t30s;
	}

	public List<T85> getT85s() {
		return this.t85s;
	}

	public void setT85s(List<T85> t85s) {
		this.t85s = t85s;
	}

	public T90 getT90() {
		return this.t90;
	}

	public void setT90(T90 t90) {
		this.t90 = t90;
	}

	public static void main(String[] args) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, MalformedObjectNameException {
		EntityManagerFactory emf = MultiPersistenceUnitManager.getEntityManagerFactory(PUcode.DSN_STOCK);
		EntityManager entityManager = emf.createEntityManager();

		long t = System.currentTimeMillis();
		entityManager.getTransaction().begin();

		//		T00 t00 = new T00();
		//		T01 t01 = new T01();
		//		T03 t03 = new T03();
		//		T05 t05 = new T05();
		//		T06 t06 = new T06();
		//		T11 t11 = new T11();
		//		T90 t90 = new T90();
		//		t00.setF001("test_1");
		//		t01.setF001("test_1");
		//		t03.setF001("test_1");
		//		t05.setF001("01");
		//		t06.setF001("test_1");
		//		t11.setF001("test1");
		//		t90.setF001("test_1");
		//
		//		t00.setT01(t01);
		//		t00.setT03(t03);
		//		t00.setT05(t05);
		//		t00.setT06(t06);
		//		t00.setT11(t11);
		//		t00.setT90(t90);
		//
		//		t01.setT00(t00);
		//		t03.setT00(t00);
		//		t05.setT00(t00);
		//		t06.setT00(t00);
		//		t11.setT00(t00);
		//		t90.setT00(t00);
		//
		//		entityManager.persist(t00);

		T00 t00 = new T00();
		T01 t01 = new T01();
		T02 t02 = new T02();
		t00.setF001("test_1");
		t01.setF001("test_1");
		t02.setF001("A");

		entityManager.persist(t00);

		t01.setT00(t00);
		//		t00.setT01(t01);
		entityManager.persist(t01);

		t02.setT00(t00);
		entityManager.persist(t02);

		entityManager.flush();
		entityManager.clear();

		entityManager.getTransaction().commit();
		entityManager.close();
		emf.close();
		System.out.println(("Done in " + Long.toString(System.currentTimeMillis() - t)));



		//		// mise en place JMX
		//		SessionFactory sf = ((HibernateEntityManagerFactory)emf).getSessionFactory();
		//		StatisticsService statsMBean = new StatisticsService();
		//		statsMBean.setSessionFactory(sf);
		//		statsMBean.setStatisticsEnabled(true);
		//		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		//		mBeanServer.registerMBean(statsMBean, new ObjectName("Hibernate:application=Statistics"));
		//		// fin JMX
		//
		//		long t = System.currentTimeMillis();
		//		//		entityManager.setFlushMode(FlushModeType.COMMIT);
		//		entityManager.getTransaction().begin();
		//		for (int i = 0; i<100000; i++) {
		//			T00 a = new T00();
		//			a.setF001("test_"+i);
		//			entityManager.persist(a);
		//			if ( (i % 50) == 0 ) { //50, same as the JDBC batch size
		//				//flush a batch of inserts and release memory:
		//				entityManager.flush();
		//				entityManager.clear();
		//			}
		//		}
		//		entityManager.getTransaction().commit();
		//		entityManager.close();
		//		emf.close();
		//		System.out.println(("Done in "+Long.toString(System.currentTimeMillis()-t)));
	}
}