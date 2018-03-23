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

import fr.hsh.dsn.orm.entities.p02v02.T40;

/**
 * The persistent class for the T71 database table.
 * 
 */
@Entity
public class T71 implements Serializable {
	private static final long	serialVersionUID	= 1L;

	@Id
	@Column(name = "id_retraiteComp")
	@TableGenerator(name = "T71_gen",
	table = "MULTIPLE_HILO_GEN",
	pkColumnName = "GENERATOR_ID",
	valueColumnName = "HI_VALUE",
	pkColumnValue = "T71_pk",
	allocationSize = 20)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "T71_gen")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long				idRetraiteComp;

	@Column(name = "F002")
	private String				f002;

	private int					invalid;

	private int					recycle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_contrat")
	private T40					t40;

	public Long getIdRetraiteComp() {
		return idRetraiteComp;
	}

	public void setIdRetraiteComp(Long idRetraiteComp) {
		this.idRetraiteComp = idRetraiteComp;
	}

	public String getF002() {
		return f002;
	}

	public void setF002(String f002) {
		this.f002 = f002;
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

	public T40 getT40() {
		return t40;
	}

	public void setT40(T40 t40) {
		this.t40 = t40;
	}
}