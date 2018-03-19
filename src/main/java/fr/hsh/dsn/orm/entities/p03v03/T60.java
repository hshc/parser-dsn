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

import fr.hsh.dsn.orm.entities.p02v02.T40;

/**
 * The persistent class for the T60 database table.
 * 
 */
@Entity
public class T60 implements Serializable {
	private static final long	serialVersionUID	= 1L;

	@Id
	@Column(name = "id_arretTravail")
	@TableGenerator(name = "T60_gen",
	table = "MULTIPLE_HILO_GEN",
	pkColumnName = "GENERATOR_ID",
	valueColumnName = "HI_VALUE",
	pkColumnValue = "T60_pk",
	allocationSize = 20)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "T60_gen")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long				idArretTravail;

	@Column(name = "F001")
	private String				f001;

	@Column(name = "F002")
	private String				f002;

	@Column(name = "F003")
	private String				f003;

	@Column(name = "F004")
	private String				f004;

	@Column(name = "F005")
	private String				f005;

	@Column(name = "F006")
	private String				f006;

	@Column(name = "F007")
	private String				f007;

	@Column(name = "F008")
	private String				f008;

	@Column(name = "F010")
	private String				f010;

	@Column(name = "F011")
	private String				f011;

	@Column(name = "F012")
	private String				f012;
	@Column(name = "F600")
	private String				f600;

	private int					invalid;

	private int					recycle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_contrat")
	private T40					t40;
	
	@OneToMany(mappedBy="t60")
	private List<T66> t66s;

	public Long getIdArretTravail() {
		return idArretTravail;
	}

	public void setIdArretTravail(Long idArretTravail) {
		this.idArretTravail = idArretTravail;
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

	public String getF005() {
		return f005;
	}

	public void setF005(String f005) {
		this.f005 = f005;
	}

	public String getF006() {
		return f006;
	}

	public void setF006(String f006) {
		this.f006 = f006;
	}

	public String getF007() {
		return f007;
	}

	public void setF007(String f007) {
		this.f007 = f007;
	}

	public String getF008() {
		return f008;
	}

	public void setF008(String f008) {
		this.f008 = f008;
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

	public String getF012() {
		return f012;
	}

	public void setF012(String f012) {
		this.f012 = f012;
	}

	public String getF600() {
		return f600;
	}

	public void setF600(String f600) {
		this.f600 = f600;
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

	public List<T66> getT66s() {
		return t66s;
	}

	public void setT66s(List<T66> t66s) {
		this.t66s = t66s;
	}

}