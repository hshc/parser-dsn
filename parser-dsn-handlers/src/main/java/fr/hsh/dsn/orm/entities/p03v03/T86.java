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

import fr.hsh.dsn.orm.entities.p02v02.T30;

/**
 * The persistent class for the T86 database table.
 * 
 */
@Entity
public class T86 implements Serializable {
	private static final long	serialVersionUID	= 1L;

	@Id
	@Column(name = "id_anciennete")
	@TableGenerator(name = "T86_gen",
	table = "MULTIPLE_HILO_GEN",
	pkColumnName = "GENERATOR_ID",
	valueColumnName = "HI_VALUE",
	pkColumnValue = "T86_pk",
	allocationSize = 20)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "T86_gen")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long				idAnciennete;

	@Column(name = "F001")
	private String				f001;
	@Column(name = "F002")
	private String				f002;
	@Column(name = "F003")
	private String				f003;
	@Column(name = "F005")
	private String				f005;
	private int					invalid;
	private int					recycle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_individu")
	private T30					t30;

	public Long getIdAnciennete() {
		return idAnciennete;
	}

	public void setIdAnciennete(Long idAnciennete) {
		this.idAnciennete = idAnciennete;
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

	public String getF005() {
		return f005;
	}

	public void setF005(String f005) {
		this.f005 = f005;
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

	public T30 getT30() {
		return t30;
	}

	public void setT30(T30 t30) {
		this.t30 = t30;
	}


}