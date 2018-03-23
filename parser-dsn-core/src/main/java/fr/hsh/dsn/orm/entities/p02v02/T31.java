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
 * The persistent class for the T31 database table.
 * 
 */
@Entity
public class T31 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "T31_gen",
	table = "MULTIPLE_HILO_GEN",
	pkColumnName = "GENERATOR_ID",
	valueColumnName = "HI_VALUE",
	pkColumnValue = "T31_pk",
	allocationSize = 20)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "T31_gen")
	@Column(name="id_changementIndividu")
	private int idChangementIndividu;

	@Column(name="F001")
	private String f001;

	@Column(name="F008")
	private String f008;

	@Column(name="F009")
	private String f009;

	@Column(name="F010")
	private String f010;

	@Column(name="F011")
	private String f011;

	private int invalid;

	private int ordre;

	private int recycle;

	//bi-directional many-to-one association to T30
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_individu")
	private T30 t30;

	public T31() {
	}

	public int getIdChangementIndividu() {
		return this.idChangementIndividu;
	}

	public void setIdChangementIndividu(int idChangementIndividu) {
		this.idChangementIndividu = idChangementIndividu;
	}

	public String getF001() {
		return this.f001;
	}

	public void setF001(String f001) {
		this.f001 = f001;
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

	public T30 getT30() {
		return this.t30;
	}

	public void setT30(T30 t30) {
		this.t30 = t30;
	}

}