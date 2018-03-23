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
 * The persistent class for the T53 database table.
 * 
 */
@Entity
public class T53 implements Serializable {
	private static final long	serialVersionUID	= 1L;

	@Id
	@Column(name = "id_activite")
	@TableGenerator(name = "T53_gen",
	table = "MULTIPLE_HILO_GEN",
	pkColumnName = "GENERATOR_ID",
	valueColumnName = "HI_VALUE",
	pkColumnValue = "T53_pk",
	allocationSize = 20)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "T53_gen")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long				idActivite;

	@Column(name = "F001")
	private String				f001;
	@Column(name = "F002")
	private String				f002;
	@Column(name = "F003")
	private String				f003;

	private int					invalid;

	private int					recycle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_remuneration")
	private T51					t51;

	public Long getIdActivite() {
		return idActivite;
	}

	public void setIdActivite(Long idActivite) {
		this.idActivite = idActivite;
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

	public T51 getT51() {
		return t51;
	}

	public void setT51(T51 t51) {
		this.t51 = t51;
	}
}