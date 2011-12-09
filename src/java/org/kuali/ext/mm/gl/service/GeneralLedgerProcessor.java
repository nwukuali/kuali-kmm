/*
 * Copyright 2008 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.kuali.ext.mm.gl.service;

import java.util.List;

import org.kuali.ext.mm.gl.GeneralLedgerPostable;
import org.kuali.ext.mm.integration.sys.businessobject.FinancialGeneralLedgerPendingEntry;
import org.kuali.rice.kns.bo.DocumentHeader;

/**
 * @author harsha07
 */
public interface GeneralLedgerProcessor {

   
    /**
     * Saves the list of glpe entries into Finance System
     * 
     * @param entries Entries
     */
    public void saveToFinanceSystem(List<FinancialGeneralLedgerPendingEntry> entries);

    /**
     * Deletes all the pending gl entries for a specific document number
     * 
     * @param documentNumber
     */
    public void delete(String documentNumber);

    /**
     * Performs route status change actions, delete the existing ones, create and save latest entries if approved.
     * 
     * @param postable Postable object
     * @param documentHeader Document Header
     */
    public void doRouteStatusChange(GeneralLedgerPostable postable, DocumentHeader documentHeader);

    /**
     * Retrieves the list of pending glpes from financial system.
     * 
     * @param documentNumber
     * @return
     */
    public List<FinancialGeneralLedgerPendingEntry> getApprovedGeneralLedgerPendingEntries(
            String documentNumber);

    /**
     * Saves the glpe entries locally so that they can be posted later through a batch job
     * 
     * @param entries
     */
    public void saveInternally(List<FinancialGeneralLedgerPendingEntry> entries);

    /**
     * Retrieves the pending entries stored locally
     * 
     * @param documentNumber String
     * @return
     */
    public List<FinancialGeneralLedgerPendingEntry> getApprovedInternalGeneralLedgerPendingEntries(
            String documentNumber);

}
