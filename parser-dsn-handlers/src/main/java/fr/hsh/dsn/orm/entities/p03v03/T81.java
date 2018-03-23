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


/**
 * The persistent class for the T81 database table.
 * 
 */
@Entity

public class T81 implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "T81_gen",
	table = "MULTIPLE_HILO_GEN",
	pkColumnName = "GENERATOR_ID",
	valueColumnName = "HI_VALUE",
	pkColumnValue = "T81_pk",
	allocationSize = 20)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "T81_gen")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_cotisation_individuelle")
	private long id_cotisation_individuelle;

	@Column(name="F001")
	private String f001;
	
	@Column(name="F004")
	private String f004;
	
	private int invalid;

	private int recycle;

	//bi-directional many-to-one association to T78
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_base_assujettie")
	private T78 t78;

	public T81() {
	}

	public long getId_cotisation_individuelle() {
		return id_cotisation_individuelle;
	}

	public void setId_cotisation_individuelle(long id_cotisation_individuelle) {
		this.id_cotisation_individuelle = id_cotisation_individuelle;
	}

	public String getF001() {
		return f001;
	}

	public void setF001(String f001) {
		this.f001 = f001;
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

	public T78 getT78() {
		return t78;
	}

	public void setT78(T78 t78) {
		this.t78 = t78;
	}

	public String getF004() {
		return f004;
	}

	public void setF004(String f004) {
		this.f004 = f004;
	}
}