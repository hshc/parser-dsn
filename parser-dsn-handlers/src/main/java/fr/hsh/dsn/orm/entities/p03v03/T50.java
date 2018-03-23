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

import fr.hsh.dsn.orm.entities.p02v02.T30;


/**
 * The persistent class for the T50 database table.
 * 
 */
@Entity
public class T50 implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "T50_gen",
	table = "MULTIPLE_HILO_GEN",
	pkColumnName = "GENERATOR_ID",
	valueColumnName = "HI_VALUE",
	pkColumnValue = "T50_pk",
	allocationSize = 20)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "T50_gen")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_verstIndividu")
	private long id_verstIndividu;

	@Column(name="F001")
	private String f001;
	
	@Column(name="F002")
	private String f002;
	
	@Column(name="F003")
	private String f003;
	
	private int invalid;

	private int recycle;

	//bi-directional many-to-one association to T30
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_individu")
	private T30 t30;

	//bi-directional many-to-one association to T78
	@OneToMany(mappedBy="t50")
	private List<T78> t78s;
	
	// bi-directional many-to-one association to T51
	@OneToMany(mappedBy = "t50")
	private List<T51>			t51s;
	
	// bi-directional many-to-one association to T52
	@OneToMany(mappedBy = "t50")
	private List<T52>			t52s;

	@OneToMany(mappedBy = "t50")
	private List<T54>			t54s;
	
	public T50() {
	}

	public long getId_verstIndividu() {
		return id_verstIndividu;
	}

	public void setId_verstIndividu(long id_verstIndividu) {
		this.id_verstIndividu = id_verstIndividu;
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

	public T30 getT30() {
		return t30;
	}

	public void setT30(T30 t30) {
		this.t30 = t30;
	}

	public List<T78> getT78s() {
		return t78s;
	}

	public void setT78s(List<T78> t78s) {
		this.t78s = t78s;
	}
	
	public List<T52> getT52s() {
		return t52s;
	}

	public void setT52s(List<T52> t52s) {
		this.t52s = t52s;
	}

	public List<T51> getT51s() {
		return t51s;
	}

	public void setT51s(List<T51> t51s) {
		this.t51s = t51s;
	}

	public List<T54> getT54s() {
		return t54s;
	}

	public void setT54s(List<T54> t54s) {
		this.t54s = t54s;
	}
}