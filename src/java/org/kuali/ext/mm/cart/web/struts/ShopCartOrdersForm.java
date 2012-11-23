package org.kuali.ext.mm.cart.web.struts;

import org.apache.commons.lang.StringUtils;
import org.kuali.ext.mm.businessobject.OrderDetail;
import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.ext.mm.cart.ShopCartKeyConstants;
import org.kuali.ext.mm.common.sys.context.SpringContext;
import org.kuali.ext.mm.document.OrderDocument;
import org.kuali.ext.mm.service.RecurringOrderService;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShopCartOrdersForm extends StoresShoppingFormBase {

	/**
     *
     */
    private static final long serialVersionUID = -6482391020962676288L;

    private List<OrderDocument> orderDocumentList = new ArrayList<OrderDocument>();

	private Map<String, Boolean> showOrderDetails;

	private Map<String, String> orderMessageMap = new HashMap<String, String>();
	
	private Map<Integer, String> frequencyMap;

	private Integer orderDocumentCount;

	private Integer itemsPerPage;

	private Integer currentPage;

	private Integer pageCount;

	/**
	 * @param orderDocumentList
	 */
	public ShopCartOrdersForm() {
		super();
		setFrequencyMap(SpringContext.getBean(RecurringOrderService.class).getOrderFrequencyMap());
	}

	@Override
	public void populate(HttpServletRequest request) {
		super.populate(request);
		List<OrderDocument> documentList = (List<OrderDocument>)request.getSession().getAttribute(ShopCartConstants.Session.ORDER_DOCUMENTS);
        String docId = request.getParameter(ShopCartConstants.ORDER_DOC_ID_PARAMETER);

        if(StringUtils.isNotBlank(docId)) {
        	documentList.clear();
        	try {
				documentList.add((OrderDocument) KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(docId));
			} catch (WorkflowException e) {
				e.printStackTrace();
				throw new RuntimeException("Error retrieving document for Order Summary list.", e);
			}
        }

        setShowOrderDetails((Map<String,Boolean>)request.getSession().getAttribute(ShopCartConstants.Session.SHOW_ORDER_DETAILS_MAP));
		if(getShowOrderDetails() == null)
			setShowOrderDetails(new HashMap<String,Boolean>());

		if(getItemsPerPage() == null || getItemsPerPage() < 1) {
			String itemsPerPage = request.getParameter(ShopCartConstants.ITEMS_PER_PAGE);
			 if(StringUtils.isNotEmpty(itemsPerPage))
	            setItemsPerPage(Math.abs(Integer.parseInt(itemsPerPage)));
			 else
				setItemsPerPage(10);
		}        	
        
        if(documentList != null && !documentList.isEmpty()) {			
			ConfigurationService configService = SpringContext.getBean(ConfigurationService.class);
			String willCallMessage = configService.getPropertyValueAsString(ShopCartKeyConstants.MESSAGE_ORDER_CONTAINS_ITEMS_FOR_WILLCALL);

        	for(OrderDocument document : documentList) {
        		document = (OrderDocument) KRADServiceLocator.getBusinessObjectService().retrieve(document);
        		if(!getShowOrderDetails().containsKey(document.getDocumentNumber()))
        			getShowOrderDetails().put(document.getDocumentNumber(), false);
        		request.getSession().setAttribute(ShopCartConstants.Session.SHOW_ORDER_DETAILS_MAP, getShowOrderDetails());

        		for(OrderDetail detail : document.getOrderDetails()) {
        			if(detail.isWillCall() && !getOrderMessageMap().containsKey(document.getDocumentNumber()))
        				getOrderMessageMap().put(document.getDocumentNumber(), willCallMessage);
        		}
        		getOrderDocumentList().add(document);
        	}
    		request.getSession().setAttribute(ShopCartConstants.Session.ORDER_DOCUMENTS , getOrderDocumentList());

    		Double dblPageCount = Math.ceil(new Double(documentList.size()) / new Double(getItemsPerPage()));
    		setPageCount(dblPageCount.intValue());
    		String page = request.getParameter(ShopCartConstants.ACTION_PARM_PAGE);
            if(StringUtils.isNotEmpty(page))
            	setCurrentPage(Integer.parseInt(page));
    		if(getCurrentPage() == null || getCurrentPage() < 1)
    			setCurrentPage(1);

            if(getCurrentPage() > getPageCount() && getPageCount() != 0)
            	setCurrentPage(getPageCount());
        }    
	}

	public void setOrderDocumentList(List<OrderDocument> orderDocumentList) {
		this.orderDocumentList = orderDocumentList;
	}

	public List<OrderDocument> getOrderDocumentList() {
		return orderDocumentList;
	}

	public void setShowOrderDetails(Map<String, Boolean> showOrderDetails) {
		this.showOrderDetails = showOrderDetails;
	}

	public Map<String, Boolean> getShowOrderDetails() {
		return showOrderDetails;
	}

	public Map<String, String> getOrderMessageMap() {
		return this.orderMessageMap;
	}

	public void setOrderMessageMap(Map<String, String> orderMessageMap) {
		this.orderMessageMap = orderMessageMap;
	}

	public void setOrderDocumentCount(Integer orderDocumentCount) {
		this.orderDocumentCount = orderDocumentCount;
	}

	public Integer getOrderDocumentCount() {
		return orderDocumentCount;
	}

	public void setItemsPerPage(Integer itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public Integer getItemsPerPage() {
		return itemsPerPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getPageCount() {
		return pageCount;
	}

    public void setFrequencyMap(Map<Integer, String> frequencyMap) {
        this.frequencyMap = frequencyMap;
    }

    public Map<Integer, String> getFrequencyMap() {
        return frequencyMap;
    }

}
