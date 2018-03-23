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
 * The persistent class for the T86 database table.
 * 
 */
@Entity
public class T54 implements Serializable {
	private static final long	serialVersionUID	= 1L;

	@Id
	@Column(name = "id_autreRevenuBrut")
	@TableGenerator(name = "T54_gen",
	table = "MULTIPLE_HILO_GEN",
	pkColumnName = "GENERATOR_ID",
	valueColumnName = "HI_VALUE",
	pkColumnValue = "T54_pk",
	allocationSize = 20)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "T54_gen")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long				idAutreRevenuBrut;

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
	@JoinColumn(name = "id_verstIndividu")
	private T50					t50;

	public Long getIdAutreRevenuBrut() {
		return idAutreRevenuBrut;
	}

	public void setIdAutreRevenuBrut(Long idAutreRevenuBrut) {
		this.idAutreRevenuBrut = idAutreRevenuBrut;
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

	public T50 getT50() {
		return t50;
	}

	public void setT50(T50 t50) {
		this.t50 = t50;
	}
}