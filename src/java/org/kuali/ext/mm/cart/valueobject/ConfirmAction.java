package org.kuali.ext.mm.cart.valueobject;

import java.util.HashMap;
import java.util.Map;

public class ConfirmAction {

	private String message;

	private String confirmAction;

	private String declineAction;

	private Map<String, String> confirmParameters;

	private Map<String, String> declineParameters;

	public ConfirmAction() {
		setConfirmParameters(new HashMap<String, String>());
		setDeclineParameters(new HashMap<String, String>());
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getConfirmAction() {
		return this.confirmAction;
	}

	public void setConfirmAction(String confirmAction) {
		this.confirmAction = confirmAction;
	}

	public String getDeclineAction() {
		return this.declineAction;
	}

	public void setDeclineAction(String declineAction) {
		this.declineAction = declineAction;
	}

	public Map<String, String> getConfirmParameters() {
		return this.confirmParameters;
	}

	public void setConfirmParameters(Map<String, String> confirmParameters) {
		this.confirmParameters = confirmParameters;
	}

	public Map<String, String> getDeclineParameters() {
		return this.declineParameters;
	}

	public void setDeclineParameters(Map<String, String> declineParameters) {
		this.declineParameters = declineParameters;
	}

}
