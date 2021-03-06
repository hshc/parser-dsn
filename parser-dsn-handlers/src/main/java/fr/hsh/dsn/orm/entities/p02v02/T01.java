package fr.hsh.dsn.orm.entities.p02v02;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;


/**
 * The persistent class for the T01 database table.
 * 
 */
@Entity
public class T01 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@Column(name="id_emetteur")
	private int idEmetteur;

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

	private int invalid;

	private int recycle;

	//bi-directional one-to-one association to T00
	//	@OneToOne(mappedBy="t01", fetch=FetchType.LAZY)

	//	@OneToOne(fetch=FetchType.LAZY, optional=false)
	//	@JoinColumn(name="id_emetteur", referencedColumnName="id_envoi")

	//	@MapsId
	//	@OneToOne(mappedBy = "t01")
	//	@JoinColumn(name = "id_emetteur")

	//	@MapsId
	//	@OneToOne
	//	@JoinColumn(name = "id_emetteur")

	//	@OneToOne(fetch=FetchType.EAGER, optional = false)
	//	@PrimaryKeyJoinColumn

	@MapsId 
	@OneToOne(mappedBy = "t01")
	@JoinColumn(name = "id_emetteur")
	private T00 t00;

	public T01() {
	}

	public int getIdEmetteur() {
		return this.idEmetteur;
	}

	public void setIdEmetteur(int idEmetteur) {
		this.idEmetteur = idEmetteur;
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