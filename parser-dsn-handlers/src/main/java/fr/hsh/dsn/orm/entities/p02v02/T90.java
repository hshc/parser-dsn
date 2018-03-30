package fr.hsh.dsn.orm.entities.p02v02;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;


/**
 * The persistent class for the T90 database table.
 * 
 */
@Entity
public class T90 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@Column(name="id_totalEnvoi")
	private int idTotalEnvoi;

	@Column(name="F001")
	private String f001;

	@Column(name="F002")
	private String f002;

	private int invalid;

	private int recycle;

	//bi-directional one-to-one association to T00
	@MapsId 
	@OneToOne(mappedBy = "t90")
	@JoinColumn(name = "id_totalEnvoi")
	private T00 t00;

	public T90() {
	}

	public int getIdTotalEnvoi() {
		return this.idTotalEnvoi;
	}

	public void setIdTotalEnvoi(int id_totalEnvoi) {
		this.idTotalEnvoi = id_totalEnvoi;
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