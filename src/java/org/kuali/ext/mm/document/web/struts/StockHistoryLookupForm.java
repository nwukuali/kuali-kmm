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

import javax.servlet.http.HttpServletRequest;

import org.kuali.ext.mm.businessobject.StockHistoryLookupObject;
import org.kuali.rice.kns.web.struts.form.KualiForm;

/**
 * This class is the struts form for Stock History Lookup Screen.
 */
public class StockHistoryLookupForm extends KualiForm {

	/**
     *
     */
    private static final long serialVersionUID = -5070618464921555046L;
    private String warehouseCode = null ;
    private StockHistoryLookupObject stockHistoryLookupObject = null;
    private String stockId;

    public StockHistoryLookupObject getStockHistoryLookupObject() {
        return this.stockHistoryLookupObject;
    }

    public void setStockHistoryLookupObject(StockHistoryLookupObject stockHistoryLookupObject) {
        this.stockHistoryLookupObject = stockHistoryLookupObject;
    }

    public String getStockId() {
        return this.stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public StockHistoryLookupForm() {
        super();
    }

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

    @Override
    public void populate(HttpServletRequest request) {
        super.populate(request);
    }
}
