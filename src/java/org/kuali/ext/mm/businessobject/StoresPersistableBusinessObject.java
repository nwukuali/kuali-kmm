package org.kuali.ext.mm.businessobject;

import org.kuali.ext.mm.service.MMServiceLocator;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;
import org.kuali.rice.krad.service.BusinessObjectService;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;
//import org.kuali.rice.krad.bo.PersistableBusinessObject;
//import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;
//import org.kuali.rice.krad.service.BusinessObjectService;

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

	protected void prePersist() {
		super.prePersist();
		this.setLastUpdateDate(new Timestamp(new java.util.Date().getTime()));
	}

	protected void preUpdate() {
		super.preUpdate();
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


}
