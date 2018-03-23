package fr.hsh.dsn.orm.entities.p03v03;

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

import fr.hsh.dsn.orm.entities.p02v02.T70;

/**
 * The persistent class for the T73 database table.
 * 
 */
@Entity
public class T73 implements Serializable {
	private static final long	serialVersionUID	= 1L;

	@Id
	@Column(name = "id_ayantDroit")
	@TableGenerator(name = "T73_gen",
	table = "MULTIPLE_HILO_GEN",
	pkColumnName = "GENERATOR_ID",
	valueColumnName = "HI_VALUE",
	pkColumnValue = "T73_pk",
	allocationSize = 20)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "T73_gen")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long				idAyantDroit;

	@Column(name = "F001")
	private String				f001;

	@Column(name = "F002")
	private String				f002;

	@Column(name = "F003")
	private String				f003;

	@Column(name = "F004")
	private String				f004;

	@Column(name = "F005")
	private String				f005;

	@Column(name = "F006")
	private String				f006;

	@Column(name = "F007")
	private String				f007;

	@Column(name = "F008")
	private String				f008;
	@Column(name = "F009")
	private String				f009;

	@Column(name = "F010")
	private String				f010;

	@Column(name = "F011")
	private String				f011;

	private int					invalid;

	private int					recycle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_affil")
	private T70					t70;

	public Long getIdAyantDroit() {
		return idAyantDroit;
	}

	public void setIdAyantDroit(Long idAyantDroit) {
		this.idAyantDroit = idAyantDroit;
	}

	public String getF001() {
		return f001;
	}

	public void setF001(String f001) {
		this.f001 = f001;
	}

	public String getF002() {
		return f002;
	}

	public void setF002(String f002) {
		this.f002 = f002;
	}

	public String getF003() {
		return f003;
	}

	public void setF003(String f003) {
		this.f003 = f003;
	}

	public String getF004() {
		return f004;
	}

	public void setF004(String f004) {
		this.f004 = f004;
	}

	public String getF005() {
		return f005;
	}

	public void setF005(String f005) {
		this.f005 = f005;
	}

	public String getF006() {
		return f006;
	}

	public void setF006(String f006) {
		this.f006 = f006;
	}

	public String getF007() {
		return f007;
	}

	public void setF007(String f007) {
		this.f007 = f007;
	}

	public String getF008() {
		return f008;
	}

	public void setF008(String f008) {
		this.f008 = f008;
	}

	public String getF009() {
		return f009;
	}

	public void setF009(String f009) {
		this.f009 = f009;
	}

	public String getF010() {
		return f010;
	}

	public void setF010(String f010) {
		this.f010 = f010;
	}

	public String getF011() {
		return f011;
	}

	public void setF011(String f011) {
		this.f011 = f011;
	}

	public int getInvalid() {
		return invalid;
	}

	public void setInvalid(int invalid) {
		this.invalid = invalid;
	}

	public int getRecycle() {
		return recycle;
	}

	public void setRecycle(int recycle) {
		this.recycle = recycle;
	}

	public T70 getT70() {
		return t70;
	}

	public void setT70(T70 t70) {
		this.t70 = t70;
	}
}