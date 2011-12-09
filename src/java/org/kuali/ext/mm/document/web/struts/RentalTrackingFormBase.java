/*
 * Copyright 2006-2007 The Kuali Foundation.
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.ext.mm.document.web.struts;

import java.util.ArrayList;
import java.util.List;

import org.kuali.ext.mm.businessobject.StagingRental;
import org.kuali.ext.mm.document.RentalTrackingDocument;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;

public abstract class RentalTrackingFormBase extends KualiTransactionalDocumentFormBase {

    private List<StagingRental> addSerialNumbers = new ArrayList<StagingRental>();
    private List<String> selectedItems = new ArrayList<String>();
    
    public RentalTrackingDocument getRentalTrackingDocument() {
        return (RentalTrackingDocument)getDocument();
    }

    public List<String> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<String> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public void setAddSerialNumbers(List<StagingRental> newRentals) {
        this.addSerialNumbers = newRentals;
    }

    public List<StagingRental> getAddSerialNumbers() {
        return addSerialNumbers;
    }
}
