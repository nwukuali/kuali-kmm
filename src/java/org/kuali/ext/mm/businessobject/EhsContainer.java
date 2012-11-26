package org.kuali.ext.mm.businessobject;

// Generated Apr 2, 2009 11:05:56 AM by Hibernate Tools 3.2.2.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EhsContainer generated by hbm2java
 */
@Entity
@Table(name = "MM_EHS_CONTAINER_T")
public class EhsContainer extends MMPersistableBusinessObjectBase implements
		java.io.Serializable {

	private static final long serialVersionUID = 1580718478418659289L;

	@Id
	@Column(name = "EHS_CONTAINER_CD", unique = true, nullable = false, length = 6)
	private String ehsContainerCode;

	@Column(name = "NM", length = 45)
	private String ehsContainerName;

	public EhsContainer() {
	}

	public EhsContainer(String ehsContainerCode) {
		this.ehsContainerCode = ehsContainerCode;
	}

	public String getEhsContainerCode() {
		return this.ehsContainerCode;
	}

	public void setEhsContainerCode(String ehsContainerCode) {
		this.ehsContainerCode = ehsContainerCode;
	}

	public String getEhsContainerName() {
		return this.ehsContainerName;
	}

	public void setEhsContainerName(String ehsContainerName) {
		this.ehsContainerName = ehsContainerName;
	}


}
