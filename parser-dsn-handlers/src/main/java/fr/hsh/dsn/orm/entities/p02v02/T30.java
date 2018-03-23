package fr.hsh.dsn.orm.entities.p02v02;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;


/**
 * The persistent class for the T30 database table.
 * 
 */
@Entity
public class T30 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "T30_gen",
	table = "MULTIPLE_HILO_GEN",
	pkColumnName = "GENERATOR_ID",
	valueColumnName = "HI_VALUE",
	pkColumnValue = "T30_pk",
	allocationSize = 20)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "T30_gen")
	@Column(name="id_individu")
	private int idIndividu;

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

	@Column(name="F009")
	private String f009;

	@Column(name="F010")
	private String f010;

	@Column(name="F011")
	private String f011;

	@Column(name="F012")
	private String f012;

	@Column(name="F014")
	private String f014;

	@Column(name="F015")
	private String f015;

	@Column(name="F016")
	private String f016;

	@Column(name="F017")
	private String f017;

	@Column(name="F018")
	private String f018;

	@Column(name="F019")
	private String f019;

	@Column(name="F020")
	private String f020;

	private int invalid;

	private int recycle;

	//bi-directional many-to-one association to T00
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_etablissement")
	private T00 t00;

	//bi-directional many-to-one association to T31
	@OneToMany(mappedBy="t30")
	private List<T31> t31s;

	//bi-directional many-to-one association to T40
	@OneToMany(mappedBy="t30")
	private List<T40> t40s;

	public T30() {
	}

	public int getIdIndividu() {
		return this.idIndividu;
	}

	public void setIdIndividu(int idIndividu) {
		this.idIndividu = idIndividu;
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

	public String getF009() {
		return this.f009;
	}

	public void setF009(String f009) {
		this.f009 = f009;
	}

	public String getF010() {
		return this.f010;
	}

	public void setF010(String f010) {
		this.f010 = f010;
	}

	public String getF011() {
		return this.f011;
	}

	public void setF011(String f011) {
		this.f011 = f011;
	}

	public String getF012() {
		return this.f012;
	}

	public void setF012(String f012) {
		this.f012 = f012;
	}

	public String getF014() {
		return this.f014;
	}

	public void setF014(String f014) {
		this.f014 = f014;
	}

	public String getF015() {
		return this.f015;
	}

	public void setF015(String f015) {
		this.f015 = f015;
	}

	public String getF016() {
		return this.f016;
	}

	public void setF016(String f016) {
		this.f016 = f016;
	}

	public String getF017() {
		return this.f017;
	}

	public void setF017(String f017) {
		this.f017 = f017;
	}

	public String getF018() {
		return this.f018;
	}

	public void setF018(String f018) {
		this.f018 = f018;
	}

	public String getF019() {
		return this.f019;
	}

	public void setF019(String f019) {
		this.f019 = f019;
	}

	public String getF020() {
		return this.f020;
	}

	public void setF020(String f020) {
		this.f020 = f020;
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

	public T00 getT00() {
		return this.t00;
	}

	public void setT00(T00 t00) {
		this.t00 = t00;
	}

	public List<T31> getT31s() {
		return this.t31s;
	}

	public void setT31s(List<T31> t31s) {
		this.t31s = t31s;
	}

	public List<T40> getT40s() {
		return this.t40s;
	}

	public void setT40s(List<T40> t40s) {
		this.t40s = t40s;
	}

}