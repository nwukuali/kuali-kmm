package org.kuali.ext.mm.businessobject;

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class MMPersistableBusinessObjectBase extends StoresPersistableBusinessObject {

	/**
     *
     */
    private static final long serialVersionUID = 4160165944905610126L;
    @Column(name = "ACTV_IND", nullable = false, length = 1)
	protected boolean active;

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean activeInd) {
		this.active = activeInd;
	}

	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}

}
