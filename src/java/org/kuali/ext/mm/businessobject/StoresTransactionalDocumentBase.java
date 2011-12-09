package org.kuali.ext.mm.businessobject;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerException;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.TransactionalDocumentBase;

public class StoresTransactionalDocumentBase extends TransactionalDocumentBase implements
        PersistableBusinessObject {

    /**
     *
     */
    private static final long serialVersionUID = -3783702033544400564L;
    // @Temporal(TemporalType.TIMESTAMP)
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

    @Override
    protected LinkedHashMap toStringMapper() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param nodeName
     * @return
     */
    public boolean answerSplitNodeQuestion(String nodeName) {
        throw new UnsupportedOperationException(
            "Concrete class has to implement this method for this node " + nodeName);
    }
}
