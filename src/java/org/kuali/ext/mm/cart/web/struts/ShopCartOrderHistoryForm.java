package org.kuali.ext.mm.cart.web.struts;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.kuali.ext.mm.cart.ShopCartConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;


public class ShopCartOrderHistoryForm extends ShopCartOrdersForm {

	/**
     *
     */
    private static final long serialVersionUID = -242061749420452527L;

    private Integer selectedMonth;

	private Integer selectedYear;

	private Calendar currentCalendar;

	private final String[] monthList = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };

	/**
	 * @param orderDocumentList
	 */
	public ShopCartOrderHistoryForm() {
		super();
		setCurrentCalendar(KNSServiceLocator.getDateTimeService().getCurrentCalendar());
	}

	@Override
	public void populate(HttpServletRequest request) {
	    request.getSession().removeAttribute(ShopCartConstants.Session.ORDER_DOCUMENTS);
		super.populate(request);

		if(getSelectedMonth() == null)
			setSelectedMonth(currentCalendar.get(Calendar.MONTH));
		if(getSelectedYear()  == null)
			setSelectedYear(getCurrentYear());

	}

	public Integer getCurrentYear() {
		return getCurrentCalendar().get(Calendar.YEAR);
	}

	public String[] getMonthList() {
		return monthList;
	}

	public void setSelectedMonth(Integer selectedMonth) {
		this.selectedMonth = selectedMonth;
	}

	public Integer getSelectedMonth() {
		return selectedMonth;
	}

	public void setSelectedYear(Integer selectedYear) {
		this.selectedYear = selectedYear;
	}

	public Integer getSelectedYear() {
		return selectedYear;
	}

	public Calendar getCurrentCalendar() {
		return this.currentCalendar;
	}

	public void setCurrentCalendar(Calendar currentCalendar) {
		this.currentCalendar = currentCalendar;
	}
}
