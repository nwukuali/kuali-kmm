package org.kuali.ext.mm.businessobject;

import org.kuali.ext.mm.document.ReturnDocument;

public class VendorReturnDoc extends ReturnDocument {
    private static final long serialVersionUID = -6683164560976393819L;

    public VendorReturnDoc() {
        super.setCurrDocVendorReturnDoc(true);
    }
}
