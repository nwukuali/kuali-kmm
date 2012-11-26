package org.kuali.ext.mm.businessobject;

// Generated Apr 16, 2009 10:01:00 AM by Hibernate Tools 3.2.4.GA

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CustomerFavHeader generated by hbm2java
 */
@Entity
@Table(name = "MM_CUSTOMER_FAV_HEADER_T")
public class CustomerFavHeader extends MMPersistableBusinessObjectBase
		implements java.io.Serializable {

	private static final long serialVersionUID = 6461295193731257995L;

	@Id
	@Column(name = "CUSTOMER_FAV_ID", nullable = false, length = 12)
	private String customerFavId;

	@Column(name = "PRNCPL_NM", nullable = false, length = 12)
	private String principalName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID", nullable = false, insertable = false, updatable = false)
	private Customer customer;

	@Column(name = "CUSTOMER_FAV_NM", length = 60)
	private String customerFavName;

	@Column(name = "CUSTOMER_FAV_SHARE_IND", length = 1)
	private boolean customerFavShareInd;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customerFavId")
	private List<CustomerFavDetail> customerFavDetails;

	public CustomerFavHeader() {
		setCustomerFavDetails(new ArrayList<CustomerFavDetail>());
	}

	public String getPrincipalName() {
		return this.principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public String getCustomerFavId() {
		return this.customerFavId;
	}

	public void setCustomerFavId(String customerFavId) {
		this.customerFavId = customerFavId;
	}

	//END OJB
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCustomerFavName() {
		return this.customerFavName;
	}

	public void setCustomerFavName(String customerFavName) {
		this.customerFavName = customerFavName;
	}

	public boolean isCustomerFavShareInd() {
		return this.customerFavShareInd;
	}

	public void setCustomerFavShareInd(boolean customerFavShareInd) {
		this.customerFavShareInd = customerFavShareInd;
	}

	public List<CustomerFavDetail> getCustomerFavDetails() {
		return this.customerFavDetails;
	}

	public void setCustomerFavDetails(List<CustomerFavDetail> customerFavDetails) {
		this.customerFavDetails = customerFavDetails;
	}


}
