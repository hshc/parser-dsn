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
 * The persistent class for the T51 database table.
 * 
 */
@Entity
public class T51 implements Serializable {
	private static final long	serialVersionUID	= 1L;

	@Id
	@Column(name = "id_remuneration")
	@TableGenerator(name = "T51_gen",
	table = "MULTIPLE_HILO_GEN",
	pkColumnName = "GENERATOR_ID",
	valueColumnName = "HI_VALUE",
	pkColumnValue = "T51_pk",
	allocationSize = 20)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "T51_gen")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long				idRemuneration;

	@Column(name = "F001")
	private String				f001;
	@Column(name = "F002")
	private String				f002;
	@Column(name = "F010")
	private String				f010;
	@Column(name = "F011")
	private String				f011;
	@Column(name = "F013")
	private String				f013;
	// bi-directional many-to-one association to T53
	@OneToMany(mappedBy = "t51")
	private List<T53>			t53s;
	private int					invalid;

	private int					recycle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_verstIndividu")
	private T50					t50;

	public Long getIdRemuneration() {
		return idRemuneration;
	}

	public void setIdRemuneration(Long idRemuneration) {
		this.idRemuneration = idRemuneration;
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

	public String getF010() {
		return f010;
	}

	public void setF010(String f010) {
		this.f010 = f010;
	}

	public String getF011() {
		return f011;
	}

	public void setF011(String f011) {
		this.f011 = f011;
	}

	public String getF013() {
		return f013;
	}

	public void setF013(String f013) {
		this.f013 = f013;
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

	public List<T53> getT53s() {
		return t53s;
	}

	public void setT53s(List<T53> t53s) {
		this.t53s = t53s;
	}

}