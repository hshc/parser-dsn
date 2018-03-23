package fr.hsh.dsn.orm.entities.p02v02;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;


/**
 * The persistent class for the T41 database table.
 * 
 */
@Entity
public class T41 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "T41_gen",
	table = "MULTIPLE_HILO_GEN",
	pkColumnName = "GENERATOR_ID",
	valueColumnName = "HI_VALUE",
	pkColumnValue = "T41_pk",
	allocationSize = 20)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "T41_gen")
	@Column(name="id_changtContrat")
	private int idChangtContrat;

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

	@Column(name="F010")
	private String f010;

	@Column(name="F011")
	private String f011;

	@Column(name="F012")
	private String f012;

	@Column(name="F013")
	private String f013;

	@Column(name="F014")
	private String f014;

	@Column(name="F019")
	private String f019;

	@Column(name="F020")
	private String f020;

	@Column(name="F021")
	private String f021;

	@Column(name="F022")
	private String f022;

	private int invalid;

	private int ordre;

	private int recycle;

	//bi-directional many-to-one association to T40
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_contrat")
	private T40 t40;

	public T41() {
	}

	public int getIdChangtContrat() {
		return this.idChangtContrat;
	}

	public void setIdChangtContrat(int idChangtContrat) {
		this.idChangtContrat = idChangtContrat;
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

	public String getF013() {
		return this.f013;
	}

	public void setF013(String f013) {
		this.f013 = f013;
	}

	public String getF014() {
		return this.f014;
	}

	public void setF014(String f014) {
		this.f014 = f014;
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

	public String getF021() {
		return this.f021;
	}

	public void setF021(String f021) {
		this.f021 = f021;
	}

	public String getF022() {
		return this.f022;
	}

	public void setF022(String f022) {
		this.f022 = f022;
	}

	public int getInvalid() {
		return this.invalid;
	}

	public void setInvalid(int invalid) {
		this.invalid = invalid;
	}

	public int getOrdre() {
		return this.ordre;
	}

	public void setOrdre(int ordre) {
		this.ordre = ordre;
	}

	public int getRecycle() {
		return this.recycle;
	}

	public void setRecycle(int recycle) {
		this.recycle = recycle;
	}

	public T40 getT40() {
		return this.t40;
	}

	public void setT40(T40 t40) {
		this.t40 = t40;
	}

}