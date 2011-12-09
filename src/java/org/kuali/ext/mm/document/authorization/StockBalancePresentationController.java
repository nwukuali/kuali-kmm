package org.kuali.ext.mm.document.authorization;

import java.util.Set;

import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.document.authorization.MaintenanceDocumentPresentationControllerBase;

public class StockBalancePresentationController extends MaintenanceDocumentPresentationControllerBase {

	@Override
	public Set<String> getConditionallyReadOnlyPropertyNames(
			MaintenanceDocument document) {
		// TODO Auto-generated method stub
		return super.getConditionallyReadOnlyPropertyNames(document);
	}
}
