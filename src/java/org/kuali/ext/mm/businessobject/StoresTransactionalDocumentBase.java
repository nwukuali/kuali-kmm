package org.kuali.ext.mm.businessobject;

import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.document.TransactionalDocumentBase;

import javax.persistence.Column;
import java.sql.Timestamp;

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

	protected void prePersist() {
		super.prePersist();
		this.setLastUpdateDate(new Timestamp(new java.util.Date().getTime()));
	}

	protected void preUpdate() {
		super.preUpdate();
		this.setLastUpdateDate(new Timestamp(new java.util.Date().getTime()));
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
