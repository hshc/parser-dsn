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
 * The persistent class for the T55 database table.
 * 
 */
@Entity
public class T55 implements Serializable {
	private static final long	serialVersionUID	= 1L;

	@Id
	@Column(name = "id_composantVersement")
	@TableGenerator(name = "T55_gen",
	table = "MULTIPLE_HILO_GEN",
	pkColumnName = "GENERATOR_ID",
	valueColumnName = "HI_VALUE",
	pkColumnValue = "T55_pk",
	allocationSize = 20)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "T55_gen")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long				idComposantVersement;

	@Column(name = "F001")
	private String				f001;

	@Column(name = "F002")
	private String				f002;

	@Column(name = "F003")
	private String				f003;

	@Column(name = "F004")
	private String				f004;

	private int					invalid;

	private int					recycle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_versementOPS")
	private T20					t20;

	public Long getIdComposantVersement() {
		return idComposantVersement;
	}

	public void setIdComposantVersement(Long idComposantVersement) {
		this.idComposantVersement = idComposantVersement;
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

	public T20 getT20() {
		return t20;
	}

	public void setT20(T20 t20) {
		this.t20 = t20;
	}
}