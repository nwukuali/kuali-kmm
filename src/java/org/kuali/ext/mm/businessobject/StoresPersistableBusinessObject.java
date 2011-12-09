package org.kuali.ext.mm.businessobject;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerException;
import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;
import org.kuali.rice.kns.service.BusinessObjectService;

@MappedSuperclass
public class StoresPersistableBusinessObject extends PersistableBusinessObjectBase {

	/**
     *
     */
    private static final long serialVersionUID = 7669931434709801896L;
    //@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE_DT", nullable = false, length = 11)
	protected Timestamp lastUpdateDate;

	public Timestamp getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Timestamp lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Override
    public void beforeInsert(PersistenceBroker persistenceBroker) throws PersistenceBrokerException {
        super.beforeInsert(persistenceBroker);

        this.setLastUpdateDate(new Timestamp(new java.util.Date().getTime()));
    }

	@Override
    public void beforeUpdate(PersistenceBroker persistenceBroker) throws PersistenceBrokerException {
        super.beforeUpdate(persistenceBroker);
        this.setLastUpdateDate(new Timestamp(new java.util.Date().getTime()));
    }

    @Override
    @PrePersist
    public void beforeInsert() {
    	super.beforeInsert();

    	this.setLastUpdateDate(new Timestamp(new java.util.Date().getTime()));
    }

    @Override
    @PreUpdate
    public void beforeUpdate() {
        super.beforeUpdate();

        this.setLastUpdateDate(new Timestamp(new java.util.Date().getTime()));
    }

    public static <T extends PersistableBusinessObject> T getObjectByPrimaryKey(Class objClass, Object pkey) {
        BusinessObjectService boService = MMServiceLocator.getBusinessObjectService();
        return (T) boService.findBySinglePrimaryKey(objClass, pkey);
    }

    public void save(){
        BusinessObjectService boService = MMServiceLocator.getBusinessObjectService();
        boService.save(this);
    }

    public void delete(){
        BusinessObjectService boService = MMServiceLocator.getBusinessObjectService();
        boService.delete(this);
    }


	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}

}
