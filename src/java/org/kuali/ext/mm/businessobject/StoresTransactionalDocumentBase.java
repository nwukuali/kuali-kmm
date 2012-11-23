package org.kuali.ext.mm.businessobject;

import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.action.ActionTaken;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.document.TransactionalDocumentBase;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	protected Set<Person> getPriorApprovers(WorkflowDocument workflowDocument) {
		PersonService personService = KimApiServiceLocator.getPersonService();
		List<ActionTaken> actionsTaken = workflowDocument.getActionsTaken();
		Set<String> principalIds = new HashSet<String>();
		Set<Person> persons = new HashSet<Person>();

		for (ActionTaken actionTaken : actionsTaken) {
			if (KewApiConstants.ACTION_TAKEN_APPROVED_CD.equals(actionTaken.getActionTaken())) {
				String principalId = actionTaken.getPrincipalId();
				if (!principalIds.contains(principalId)) {
					principalIds.add(principalId);
					persons.add(personService.getPerson(principalId));
				}
			}
		}
		return persons;
	}
}
