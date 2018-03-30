package fr.hsh.dsn.orm.entities.p02v02;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;


/**
 * The persistent class for the T03 database table.
 * 
 */
@Entity
public class T03 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//	@GeneratedValue(generator="gen")
	//	@GenericGenerator(name="gen", strategy="foreign",
	//	parameters=@Parameter(name="property", value="t00"))
//	@Column(name="id_destinataireCRE")
	private int idDestinataireCRE;

	@Column(name="F001")
	private String f001;

	@Column(name="F002")
	private String f002;

	@Column(name="F003")
	private String f003;

	private int invalid;

	private int recycle;

	//bi-directional one-to-one association to T00
	//	@MapsId("id_envoi")
	//	@OneToOne(cascade={CascadeType.PERSIST}, fetch=FetchType.LAZY)
	//	@JoinColumn(name="id_destinataireCRE")
	@MapsId 
	@OneToOne(mappedBy = "t03")
	@JoinColumn(name = "id_destinataireCRE")
	private T00 t00;

	public T03() {
	}

	public int getIdDestinataireCRE() {
		return this.idDestinataireCRE;
	}

	public void setIdDestinataireCRE(int id_destinataireCRE) {
		this.idDestinataireCRE = id_destinataireCRE;
	}

	public String getF001() {
		return this.f001;
	}

	public void setF001(String f001) {
		this.f001 = f001;
	}

	public String getF002() {
		return this.f002;
	}

	public void setF002(String f002) {
		this.f002 = f002;
	}

	public String getF003() {
		return this.f003;
	}

	public void setF003(String f003) {
		this.f003 = f003;
	}

	public int getInvalid() {
		return this.invalid;
	}

	public void setInvalid(int invalid) {
		this.invalid = invalid;
	}

	public int getRecycle() {
		return this.recycle;
	}

	public void setRecycle(int recycle) {
		this.recycle = recycle;
	}

	public T00 getT00() {
		return this.t00;
	}

	public void setT00(T00 t00) {
		this.t00 = t00;
	}

}