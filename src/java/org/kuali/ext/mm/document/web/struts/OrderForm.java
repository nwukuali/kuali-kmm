package org.kuali.ext.mm.document.web.struts;

import org.apache.struts.action.ActionMapping;
import org.kuali.ext.mm.businessobject.Accounts;
import org.kuali.ext.mm.businessobject.MMCapitalAssetInformation;
import org.kuali.ext.mm.businessobject.PunchOutVendor;
import org.kuali.ext.mm.businessobject.RecurringOrder;
import org.kuali.ext.mm.common.sys.MMConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.service.RecurringOrderService;
import org.kuali.rice.core.web.format.FormatException;
import org.kuali.rice.core.web.format.Formatter;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;
import org.kuali.rice.krad.util.GlobalVariables;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OrderForm extends KualiTransactionalDocumentFormBase {

	/**
     *
     */
    private static final long serialVersionUID = -2039079726080060931L;

    private Accounts newAccountingLine;

	private List<Accounts> newOrderDetailAccountingLines;

	private List<MMCapitalAssetInformation> newCapitalAssetInfo;

	private String returnToSenderUrl;
	
	private Map<Integer, String> frequencyMap;
	
	private PunchOutVendor punchOutVendor;
	

//	private String[] approvedItems;

	private boolean hideDetails;

	private RecurringOrder recurringOrder;

	public OrderForm() {
    	super();
    	setNewOrderDetailAccountingLines(new ArrayList<Accounts>());
    	setNewCapitalAssetInfo(new ArrayList<MMCapitalAssetInformation>());
    	setFrequencyMap(SpringContext.getBean(RecurringOrderService.class).getOrderFrequencyMap());
    }

	@Override
    protected String getDefaultDocumentTypeName() {
        return "SORD";
    }

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);

//		setApprovedItems(new String[] {});
	}

    @Override
    public void populate(HttpServletRequest request) {
        super.populate(request);



    }

    @Override
    public Object formatValue(Object value, String keypath, Class type) {

        Formatter formatter = getFormatter(keypath, type);

        if(value instanceof String[])
        	return value;

        try {
            return Formatter.isSupportedType(type) ? formatter.formatForPresentation(value) : value;
        }
        catch (FormatException e) {
            GlobalVariables.getMessageMap().putError(keypath, e.getErrorKey(), e.getErrorArgs());
            return value.toString();
        }
    }


    /**
     * This method gets the Pick List document
     *
     * @return OrderDocument
     */
    public OrderDocument getOrderDocument() {
        return (OrderDocument) getDocument();
    }

	/**
	 * @see org.kuali.rice.kns.web.struts.form.KualiDocumentFormBase#addRequiredNonEditableProperties()
	 */
	@Override
	public void addRequiredNonEditableProperties() {
	    super.addRequiredNonEditableProperties();
	}

	public Accounts getNewAccountingLine() {
		return newAccountingLine;
	}

	public void setNewAccountingLine(Accounts newAccountingLine) {
		this.newAccountingLine = newAccountingLine;
	}

	public void setNewOrderDetailAccountingLines(List<Accounts> newOrderDetailAccountingLines) {
		this.newOrderDetailAccountingLines = newOrderDetailAccountingLines;
	}

	public List<Accounts> getNewOrderDetailAccountingLines() {
		return newOrderDetailAccountingLines;
	}

	public void setNewCapitalAssetInfo(List<MMCapitalAssetInformation> newCapitalAssetInfo) {
		this.newCapitalAssetInfo = newCapitalAssetInfo;
	}

	public List<MMCapitalAssetInformation> getNewCapitalAssetInfo() {
		return newCapitalAssetInfo;
	}

	public String getAmountTypeFixed() {
		return MMConstants.OrderDocument.OPTION_FXD;
	}

	public void setHideDetails(boolean hideDetails) {
		this.hideDetails = hideDetails;
	}

	public boolean isHideDetails() {
		return hideDetails;
	}

	public void setReturnToSenderUrl(String returnToSenderUrl) {
		this.returnToSenderUrl = returnToSenderUrl;
	}

	public String getReturnToSenderUrl() {
		return returnToSenderUrl;
	}

	public void setRecurringOrder(RecurringOrder recurringOrder) {
		this.recurringOrder = recurringOrder;
	}

	public RecurringOrder getRecurringOrder() {
		return recurringOrder;
	}

    public void setFrequencyMap(Map<Integer, String> frequencyMap) {
        this.frequencyMap = frequencyMap;
    }

    public Map<Integer, String> getFrequencyMap() {
        return frequencyMap;
    }

    public void setPunchOutVendor(PunchOutVendor punchOutVendor) {
        this.punchOutVendor = punchOutVendor;
    }

    public PunchOutVendor getPunchOutVendor() {
        return punchOutVendor;
    }


}
