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
 * The persistent class for the T72 database table.
 * 
 */
@Entity
public class T72 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "T72_gen",
	table = "MULTIPLE_HILO_GEN",
	pkColumnName = "GENERATOR_ID",
	valueColumnName = "HI_VALUE",
	pkColumnValue = "T72_pk",
	allocationSize = 20)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "T72_gen")
	@Column(name="id_chgtAffilPrev")
	private int idChgtAffilPrev;

	@Column(name="F001")
	private String f001;

	@Column(name="F002")
	private String f002;

	@Column(name="F003")
	private String f003;

	private int invalid;

	private int ordre;

	private int recycle;

	//bi-directional many-to-one association to T70
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_affil")
	private T70 t70;

	public T72() {
	}

	public int getIdChgtAffilPrev() {
		return this.idChgtAffilPrev;
	}

	public void setIdChgtAffilPrev(int idChgtAffilPrev) {
		this.idChgtAffilPrev = idChgtAffilPrev;
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

	public T70 getT70() {
		return this.t70;
	}

	public void setT70(T70 t70) {
		this.t70 = t70;
	}

}