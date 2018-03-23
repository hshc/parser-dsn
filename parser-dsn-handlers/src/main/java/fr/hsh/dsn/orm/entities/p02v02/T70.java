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
 * The persistent class for the T70 database table.
 * 
 */
@Entity
public class T70 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "T70_gen",
	table = "MULTIPLE_HILO_GEN",
	pkColumnName = "GENERATOR_ID",
	valueColumnName = "HI_VALUE",
	pkColumnValue = "T70_pk",
	allocationSize = 20)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "T70_gen")
	@Column(name="id_affil")
	private int idAffil;

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

	private int invalid;

	private int recycle;

	//bi-directional many-to-one association to T40
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_contrat")
	private T40 t40;

	//bi-directional many-to-one association to T72
	@OneToMany(mappedBy="t70")
	private List<T72> t72s;

	public T70() {
	}

	public int getIdAffil() {
		return this.idAffil;
	}

	public void setIdAffil(int idAffil) {
		this.idAffil = idAffil;
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

	public T40 getT40() {
		return this.t40;
	}

	public void setT40(T40 t40) {
		this.t40 = t40;
	}

	public List<T72> getT72s() {
		return this.t72s;
	}

	public void setT72s(List<T72> t72s) {
		this.t72s = t72s;
	}

}