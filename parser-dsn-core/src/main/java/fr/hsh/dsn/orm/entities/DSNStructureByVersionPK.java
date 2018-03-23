package fr.hsh.dsn.orm.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The primary key class for the DSNStructureByVersion database table.
 * 
 */
@Embeddable
public class DSNStructureByVersionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long	serialVersionUID	= 1L;
	private int					numOrdre;
	private String				versionNorme;
	private String				natureDeclaration;
	
    public DSNStructureByVersionPK() {
    }

	public int getNumOrdre() {
		return this.numOrdre;
	}
	public void setNumOrdre(int numOrdre) {
		this.numOrdre = numOrdre;
	}

	public String getVersionNorme() {
		return this.versionNorme;
	}
	public void setVersionNorme(String versionNorme) {
		this.versionNorme = versionNorme;
	}

	public String getNatureDeclaration() {
		return natureDeclaration;
	}

	public void setNatureDeclaration(String pNatureDeclaration) {
		this.natureDeclaration = pNatureDeclaration;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DSNStructureByVersionPK)) {
			return false;
		}
		DSNStructureByVersionPK castOther = (DSNStructureByVersionPK)other;
		return 
			(this.numOrdre == castOther.numOrdre)
			&& this.versionNorme.equals(castOther.versionNorme);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.numOrdre;
		hash = hash * prime + this.versionNorme.hashCode();
		
		return hash;
    }
}