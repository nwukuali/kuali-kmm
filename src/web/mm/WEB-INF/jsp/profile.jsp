<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:set var="isDefaultProfile" value="${KualiForm.defaultProfile }" />

<c:set var="customerAttributes" value="${DataDictionary.Customer.attributes}" />
<c:set var="profileAttributes" value="${DataDictionary.Profile.attributes}" />
<c:set var="addressAttributes" value="${DataDictionary.Address.attributes}" />

<c:choose>
	<c:when test="${empty KualiForm.profileToEdit.profileId}">
		<c:set var="title" value="Create New Profile" />
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Edit Profile: ${KualiForm.profileToEdit.profileName }" />
	</c:otherwise>
</c:choose>	

<shopping:page title="University Stores" htmlFormAction="profile">
	<div class="sidePanel_container" id="profile_sidePanel">
		<kul:errors keyMatch="*"/>
		<customer:profileList profileList="${KualiForm.customer.profiles}" selectedProfileId="${KualiForm.profileToEdit.profileId }" />
	</div>
	<div id="profile_container">
		<h3>Profile Maintenance</h3>
		<c:if test="${not empty KualiForm.profileMessage}"><p id="profile_message">${KualiForm.profileMessage }</p></c:if>
		<html:hidden property="profileToEdit.profileId" />
		<html:hidden property="profileToEdit.versionNumber" />
		<html:hidden property="profileToEdit.objectId" />
		<c:if test="${KualiForm.hideInternalInfo or not KualiForm.allowsPersonalUse}">
			<html:hidden property="profileToEdit.personalUseIndicator" />
		</c:if>		
		<shopping:infoEntry title="${title}" id="profile_info" canHide="false">		
			<div class="infoEntry_row">
				<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${profileAttributes.principalName}" useShortLabel="true"/></div>
				<div class="profile_data"><kul:htmlControlAttribute property="profileToEdit.principalName" attributeEntry="${profileAttributes.principalName}" readOnly="${true}" />&nbsp;</div>				
			</div>
			<div class="infoEntry_row">
				<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${profileAttributes.profileDefaultIndicator}" /></div>
				<div class="profile_data"><kul:htmlControlAttribute property="profileToEdit.profileDefaultIndicator" attributeEntry="${profileAttributes.profileDefaultIndicator}" readOnly="${isDefaultProfile or fn:length(KualiForm.customer.profiles) eq 0}" /></div>&nbsp;
			</div>			
			<div class="infoEntry_row">
				<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${profileAttributes.active}" /></div>
				<div class="profile_data"><kul:htmlControlAttribute property="profileToEdit.active" attributeEntry="${profileAttributes.active}" readOnly="${KualiForm.profileToEdit.profileDefaultIndicator }"/></div>&nbsp;
			</div>
			<c:if test="${not KualiForm.hideInternalInfo and KualiForm.allowsPersonalUse}">
				<div class="infoEntry_row">
					<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${profileAttributes.personalUseIndicator}" /></div>
					<div class="profile_data"><kul:htmlControlAttribute property="profileToEdit.personalUseIndicator" attributeEntry="${profileAttributes.personalUseIndicator}" readOnly="${false }" onchange="form.submit()"/></div>&nbsp;
				</div>
			</c:if>
			<div class="infoEntry_row">
				<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${profileAttributes.profileName}" /></div>
				<div class="profile_data"><kul:htmlControlAttribute property="profileToEdit.profileName" attributeEntry="${profileAttributes.profileName}" /></div>&nbsp;
			</div>
			<div class="infoEntry_row">
				<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${profileAttributes.profileEmail}" /></div>
				<div class="profile_data"><kul:htmlControlAttribute property="profileToEdit.profileEmail" attributeEntry="${profileAttributes.profileEmail}"  /></div>&nbsp;
			</div>
			<div class="infoEntry_row">
				<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${profileAttributes.notifyIndicator}" /></div>
				<div class="profile_data"><kul:htmlControlAttribute property="profileToEdit.notifyIndicator" attributeEntry="${profileAttributes.notifyIndicator}"  /></div>&nbsp;
			</div>
			<div class="infoEntry_row">
				<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${profileAttributes.profilePhoneNumber}" /></div>
				<div class="profile_data"><kul:htmlControlAttribute property="profileToEdit.profilePhoneNumber" attributeEntry="${profileAttributes.profilePhoneNumber}" /></div>&nbsp;
			</div>
			<c:if test="${not KualiForm.profileToEdit.personalUseIndicator}">			
				<div class="infoEntry_row">
					<div class="profile_label">*<kul:htmlAttributeLabel attributeEntry="${profileAttributes.campusCode}" /></div>
					<div class="profile_data"><kul:htmlControlAttribute property="profileToEdit.campusCode" attributeEntry="${profileAttributes.campusCode}" />
						&nbsp;<kul:lookup boClassName="org.kuali.rice.kns.bo.Campus" fieldConversions="campusCode:profileToEdit.campusCode" lookupParameters="profileToEdit.campusCode:campusCode" />
					</div>&nbsp;
				</div>	
				<div class="infoEntry_row">
					<div class="profile_label">*<kul:htmlAttributeLabel attributeEntry="${profileAttributes.deliveryBuildingCode}" /></div>
					<div class="profile_data"><kul:htmlControlAttribute property="profileToEdit.deliveryBuildingCode" attributeEntry="${profileAttributes.deliveryBuildingCode}" />
						&nbsp;<kul:lookup boClassName="org.kuali.ext.mm.integration.sys.businessobject.FinancialBuilding" fieldConversions="campusCode:profileToEdit.campusCode,buildingCode:profileToEdit.deliveryBuildingCode" lookupParameters="profileToEdit.campusCode:campusCode,profileToEdit.deliveryBuildingCode:buildingCode" />
					</div>&nbsp;
				</div>
				<div class="infoEntry_row">
					<div class="profile_label">*<kul:htmlAttributeLabel attributeEntry="${profileAttributes.deliveryBuildingRoomNumber}" /></div>
					<div class="profile_data"><kul:htmlControlAttribute property="profileToEdit.deliveryBuildingRoomNumber" attributeEntry="${profileAttributes.deliveryBuildingRoomNumber}" />
						&nbsp;<kul:lookup boClassName="org.kuali.ext.mm.integration.sys.businessobject.FinancialRoom" fieldConversions="campusCode:profileToEdit.campusCode,buildingCode:profileToEdit.deliveryBuildingCode,buildingRoomNumber:profileToEdit.deliveryBuildingRoomNumber" lookupParameters="profileToEdit.campusCode:campusCode,profileToEdit.deliveryBuildingCode:buildingCode,profileToEdit.deliveryBuildingRoomNumber:buildingRoomNumber" />
					</div>&nbsp;
				</div>
				<div class="infoEntry_row">
					<div class="profile_label">*<kul:htmlAttributeLabel attributeEntry="${profileAttributes.billingBuildingCode}" /></div>
					<div class="profile_data"><kul:htmlControlAttribute property="profileToEdit.billingBuildingCode" attributeEntry="${profileAttributes.billingBuildingCode}" />
						&nbsp;<kul:lookup boClassName="org.kuali.ext.mm.integration.sys.businessobject.FinancialBuilding" fieldConversions="campusCode:profileToEdit.campusCode,buildingCode:profileToEdit.billingBuildingCode" lookupParameters="profileToEdit.campusCode:campusCode,profileToEdit.billingBuildingCode:buildingCode" />
					</div>&nbsp;
				</div>
			</c:if>
		</shopping:infoEntry>
		<div id="profileAddress_container">
			<shopping:infoEntry title="Default Ship-To Address" id="profile_ShipToAddress" canHide="false">
				<div class="infoEntry_row" >				
					<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressName}" useShortLabel="true" /></div>
					<div class="profile_data"><kul:htmlControlAttribute property="shipToAddress.addressName" attributeEntry="${addressAttributes.addressName}" readOnly="${readOnly}" /></div>
				</div>			
				<div class="infoEntry_row">
					<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressLine1}" useShortLabel="true" /></div>
					<div class="profile_data"><kul:htmlControlAttribute  property="shipToAddress.addressLine1" attributeEntry="${addressAttributes.addressLine1}"  /></div>&nbsp;
				</div>
				<div class="infoEntry_row">
					<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressLine2}" useShortLabel="true"/></div>
					<div class="profile_data"><kul:htmlControlAttribute  property="shipToAddress.addressLine2" attributeEntry="${addressAttributes.addressLine2}" /></div>&nbsp;
				</div>
				<div class="infoEntry_row">
					<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressCityName}" useShortLabel="true" /></div>
					<div class="profile_data"><kul:htmlControlAttribute  property="shipToAddress.addressCityName" attributeEntry="${addressAttributes.addressCityName}" /></div>&nbsp;
				</div>
				<div class="infoEntry_row">
					<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressStateCode}" useShortLabel="true"/></div>
					<div class="profile_data"><kul:htmlControlAttribute  property="shipToAddress.addressStateCode" attributeEntry="${addressAttributes.addressStateCode}" /></div>&nbsp;
				</div>
				<div class="infoEntry_row">
					<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressPostalCode}" useShortLabel="true"/></div>
					<div class="profile_data"><kul:htmlControlAttribute  property="shipToAddress.addressPostalCode" attributeEntry="${addressAttributes.addressPostalCode}" /></div>&nbsp;
				</div>
				<div class="infoEntry_row">
					<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressCountryCode}" useShortLabel="true"/></div>
					<div class="profile_data"><kul:htmlControlAttribute  property="shipToAddress.addressCountryCode" attributeEntry="${addressAttributes.addressCountryCode}" /></div>&nbsp;
				</div>
			</shopping:infoEntry>			
			<shopping:infoEntry title="Default Billing Address" id="profile_BillingAddress" canHide="false">
				<div class="infoEntry_row" >				
					<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressName}" useShortLabel="true" /></div>
					<div class="profile_data"><kul:htmlControlAttribute property="billingAddress.addressName" attributeEntry="${addressAttributes.addressName}" readOnly="${readOnly}" /></div>
				</div>			
				<div class="infoEntry_row">
					<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressLine1}" useShortLabel="true" /></div>
					<div class="profile_data"><kul:htmlControlAttribute  property="billingAddress.addressLine1" attributeEntry="${addressAttributes.addressLine1}"  /></div>&nbsp;
				</div>
				<div class="infoEntry_row">
					<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressLine2}" useShortLabel="true" /></div>
					<div class="profile_data"><kul:htmlControlAttribute  property="billingAddress.addressLine2" attributeEntry="${addressAttributes.addressLine2}" /></div>&nbsp;
				</div>
				<div class="infoEntry_row">
					<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressCityName}" useShortLabel="true" /></div>
					<div class="profile_data"><kul:htmlControlAttribute  property="billingAddress.addressCityName" attributeEntry="${addressAttributes.addressCityName}" /></div>&nbsp;
				</div>
				<div class="infoEntry_row">
					<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressStateCode}" useShortLabel="true"/></div>
					<div class="profile_data"><kul:htmlControlAttribute  property="billingAddress.addressStateCode" attributeEntry="${addressAttributes.addressStateCode}" /></div>&nbsp;
				</div>
				<div class="infoEntry_row">
					<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressPostalCode}" useShortLabel="true"/></div>
					<div class="profile_data"><kul:htmlControlAttribute  property="billingAddress.addressPostalCode" attributeEntry="${addressAttributes.addressPostalCode}" /></div>&nbsp;
				</div>
				<div class="infoEntry_row">
					<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${addressAttributes.addressCountryCode}" useShortLabel="true"/></div>
					<div class="profile_data"><kul:htmlControlAttribute  property="billingAddress.addressCountryCode" attributeEntry="${addressAttributes.addressCountryCode}" /></div>&nbsp;
				</div>
			</shopping:infoEntry>
			<div class="clear"></div>	
		</div>
		<c:if test="${not KualiForm.profileToEdit.personalUseIndicator}">
			<shopping:infoEntry title="Accounting" id="profile_accounting" canHide="false">
				<div class="infoEntry_row">
					<div class="profile_label">*<kul:htmlAttributeLabel attributeEntry="${profileAttributes.finacialChartOfAccountsCode}" /></div>
					<div class="profile_data"><kul:htmlControlAttribute property="profileToEdit.finacialChartOfAccountsCode" attributeEntry="${profileAttributes.finacialChartOfAccountsCode}" />
						&nbsp;<kul:lookup boClassName="org.kuali.ext.mm.integration.coa.businessobject.FinancialChart" fieldConversions="chartOfAccountsCode:profileToEdit.finacialChartOfAccountsCode" lookupParameters="profileToEdit.finacialChartOfAccountsCode:chartOfAccountsCode" />
					</div>&nbsp;
				</div>
				<div class="infoEntry_row">
					<div class="profile_label">*<kul:htmlAttributeLabel attributeEntry="${profileAttributes.organizationCode}" /></div>
					<div class="profile_data"><kul:htmlControlAttribute property="profileToEdit.organizationCode" attributeEntry="${profileAttributes.organizationCode}" />
						&nbsp;<kul:lookup boClassName="org.kuali.ext.mm.integration.coa.businessobject.FinancialOrganization" fieldConversions="chartOfAccountsCode:profileToEdit.finacialChartOfAccountsCode,organizationCode:profileToEdit.organizationCode" lookupParameters="profileToEdit.finacialChartOfAccountsCode:chartOfAccountsCode,profileToEdit.organizationCode:organizationCode" />
					</div>&nbsp;
				</div>
				<div class="infoEntry_row">
					<div class="profile_label">*<kul:htmlAttributeLabel attributeEntry="${profileAttributes.accountNumber}" /></div>
					<div class="profile_data"><kul:htmlControlAttribute property="profileToEdit.accountNumber" attributeEntry="${profileAttributes.accountNumber}" />
						&nbsp;<kul:lookup boClassName="org.kuali.ext.mm.integration.coa.businessobject.FinancialAccount" fieldConversions="chartOfAccountsCode:profileToEdit.finacialChartOfAccountsCode,accountNumber:profileToEdit.accountNumber" lookupParameters="profileToEdit.finacialChartOfAccountsCode:chartOfAccountsCode,profileToEdit.accountNumber:accountNumber" />
					</div>&nbsp;
				</div>
				<div class="infoEntry_row">
					<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${profileAttributes.subAccountNumber}" /></div>
					<div class="profile_data"><kul:htmlControlAttribute property="profileToEdit.subAccountNumber" attributeEntry="${profileAttributes.subAccountNumber}" />
						&nbsp;<kul:lookup boClassName="org.kuali.ext.mm.integration.coa.businessobject.FinancialSubAccount" fieldConversions="chartOfAccountsCode:profileToEdit.finacialChartOfAccountsCode,accountNumber:profileToEdit.accountNumber,subAccountNumber:profileToEdit.subAccountNumber" lookupParameters="profileToEdit.finacialChartOfAccountsCode:chartOfAccountsCode,profileToEdit.accountNumber:accountNumber,profileToEdit.subAccountNumber:subAccountNumber" />
					</div>&nbsp;
				</div>
				<div class="infoEntry_row">
					<div class="profile_label"><kul:htmlAttributeLabel attributeEntry="${profileAttributes.projectCode}" /></div>
					<div class="profile_data"><kul:htmlControlAttribute property="profileToEdit.projectCode" attributeEntry="${profileAttributes.projectCode}" />
						&nbsp;<kul:lookup boClassName="org.kuali.ext.mm.integration.coa.businessobject.FinancialProjectCode" fieldConversions="code:profileToEdit.projectCode" lookupParameters="profileToEdit.projectCode:code" />
					</div>&nbsp;
				</div>
			</shopping:infoEntry>
		</c:if>
		<div id="profile_controls">
			<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-save.gif" property="methodToCall.save" value="Save" title="Save" alt="Save" />
			<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-clear.gif" property="methodToCall.clear" value="Clear" title="Clear" alt="Clear" />
		</div>
	</div>
	<script type="text/javascript">
		var main = document.getElementById("profile_container");
		main.style.height=document.body.parentNode.scrollHeight + "px";
	</script>

</shopping:page>