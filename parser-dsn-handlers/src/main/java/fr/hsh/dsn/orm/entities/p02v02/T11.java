package fr.hsh.dsn.orm.entities.p02v02;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;


/**
 * The persistent class for the T11 database table.
 * 
 */
@Entity
public class T11 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@Column(name="id_etablissement")
	private int idEtablissement;

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

	@Column(name="F015")
	private String f015;

	@Column(name="F016")
	private String f016;

	private int invalid;

	private int recycle;

	//bi-directional one-to-one association to T00
	@MapsId 
	@OneToOne(mappedBy = "t11")
	@JoinColumn(name = "id_etablissement")
	private T00 t00;

	public T11() {
	}

	public int getIdEtablissement() {
		return this.idEtablissement;
	}

	public void setIdEtablissement(int idEtablissement) {
		this.idEtablissement = idEtablissement;
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

}