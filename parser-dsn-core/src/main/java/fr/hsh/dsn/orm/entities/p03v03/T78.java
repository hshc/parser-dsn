package fr.hsh.dsn.orm.entities.p03v03;

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
 * The persistent class for the T78 database table.
 * 
 */
@Entity
public class T78 implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@Id
	@TableGenerator(name = "T78_gen",
	table = "MULTIPLE_HILO_GEN",
	pkColumnName = "GENERATOR_ID",
	valueColumnName = "HI_VALUE",
	pkColumnValue = "T78_pk",
	allocationSize = 20)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "T78_gen")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_base_assujettie")
	private long				id_base_assujettie;

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

	// bi-directional many-to-one association to T50
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_verstIndividu")
	private T50					t50;

	// bi-directional many-to-one association to T79
	@OneToMany(mappedBy = "t78")
	private List<T79>			t79s;

	// bi-directional many-to-one association to T81
	@OneToMany(mappedBy = "t78")
	private List<T81>			t81s;

	public T78() {
	}

	public long getId_base_assujettie() {
		return id_base_assujettie;
	}

	public void setId_base_assujettie(long id_base_assujettie) {
		this.id_base_assujettie = id_base_assujettie;
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

	public T50 getT50() {
		return t50;
	}

	public void setT50(T50 t50) {
		this.t50 = t50;
	}

	public List<T79> getT79s() {
		return t79s;
	}

	public void setT79s(List<T79> t79s) {
		this.t79s = t79s;
	}

	public List<T81> getT81s() {
		return t81s;
	}

	public void setT81s(List<T81> t81s) {
		this.t81s = t81s;
	}

}