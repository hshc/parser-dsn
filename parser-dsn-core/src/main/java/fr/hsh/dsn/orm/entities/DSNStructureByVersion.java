package fr.hsh.dsn.orm.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * The persistent class for the DSNStructureByVersion database table.
 * 
 */
@Entity
public class DSNStructureByVersion implements Serializable {
	private static final long		serialVersionUID	= 1L;
	private DSNStructureByVersionPK	id;
	private String					beginEndFlag;
	private String					loopingFlag;
	private String					obligatoireFacultatifFlag;
	private String					rubriqueName;
	private String					obligatoireFacultatifBlocFlag;
	private String					dataTypes;
	private String					fieldSize;
	private String					regex;

	public DSNStructureByVersion() {
	}

	@EmbeddedId
	public DSNStructureByVersionPK getId() {
		return this.id;
	}

	public void setId(DSNStructureByVersionPK id) {
		this.id = id;
	}

	public String getBeginEndFlag() {
		return this.beginEndFlag;
	}

	public void setBeginEndFlag(String beginEndFlag) {
		this.beginEndFlag = beginEndFlag;
	}

	public String getObligatoireFacultatifFlag() {
		return this.obligatoireFacultatifFlag;
	}

	public void setObligatoireFacultatifFlag(String obligatoireFacultatifFlag) {
		this.obligatoireFacultatifFlag = obligatoireFacultatifFlag;
	}

	public String getRubriqueName() {
		return this.rubriqueName;
	}

	public void setRubriqueName(String rubriqueName) {
		this.rubriqueName = rubriqueName;
	}

	public String getLoopingFlag() {
		return this.loopingFlag;
	}

	public void setLoopingFlag(String pLoopingFlag) {
		this.loopingFlag = pLoopingFlag;
	}

	public String getObligatoireFacultatifBlocFlag() {
		return this.obligatoireFacultatifBlocFlag;
	}

	public void setObligatoireFacultatifBlocFlag(String pObligatoireFacultatifBlocFlag) {
		this.obligatoireFacultatifBlocFlag = pObligatoireFacultatifBlocFlag;
	}

	public String getDataTypes() {
		return this.dataTypes;
	}

	public void setDataTypes(String pDataTypes) {
		this.dataTypes = pDataTypes;
	}

	public String getFieldSize() {
		return this.fieldSize;
	}

	public void setFieldSize(String pFieldSize) {
		this.fieldSize = pFieldSize;
	}

	public String getRegex() {
		return this.regex;
	}

	public void setRegex(String pRegex) {
		this.regex = pRegex;
	}
}