package org.kuali.ext.mm.sys.identity;

import java.util.LinkedHashMap;

public class MMKimAttributes extends org.kuali.rice.kim.bo.impl.KimAttributes {

	/**
     *
     */
    private static final long serialVersionUID = -1874862635800634861L;

    public static final String WORKSHEET_STATUS_CODE = "worksheetStatusCode";

	private String worksheetStatusCode;

	public String getWorksheetStatusCode() {
		return worksheetStatusCode;
	}

	public void setWorksheetStatusCode(String worksheetStatusCode) {
		this.worksheetStatusCode = worksheetStatusCode;
	}

    @SuppressWarnings("unchecked")
    @Override
    protected LinkedHashMap toStringMapper() {
        LinkedHashMap m = new LinkedHashMap();
        return m;
    }


}
