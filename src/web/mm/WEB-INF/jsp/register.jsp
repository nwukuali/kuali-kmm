<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:set var="customerAttributes" value="${DataDictionary.Customer.attributes}" />

<shopping:page title="University Stores" htmlFormAction="register">
	<div id="register_container">
		<div id="register_errors"><kul:errors keyMatch="*"/>&nbsp;</div>
		<shopping:infoEntry title="Register New User" id="register_box" canHide="false">
			<div id="register_labels">
				<p class="entryData" >
					<div class="register_label"><kul:htmlAttributeLabel attributeEntry="${customerAttributes.firstName}" useShortLabel="true"/></div>
					<div class="register_data"><kul:htmlControlAttribute property="registerCustomer.firstName" attributeEntry="${customerAttributes.firstName}" /></div>			
				</p>
				<p class="entryData" >
					<div class="register_label"><kul:htmlAttributeLabel attributeEntry="${customerAttributes.lastName}" useShortLabel="true"/></div>
					<div class="register_data"><kul:htmlControlAttribute property="registerCustomer.lastName" attributeEntry="${customerAttributes.lastName}" /></div>			
				</p>
				<p class="entryData" >
					<div class="register_label">* Email Address: 
						<br /><span style="font-size:8pt;font-weight:normal">(This will be your User ID)</span>&nbsp;
					</div>
					<div class="register_data"><kul:htmlControlAttribute property="registerCustomer.principalName" attributeEntry="${customerAttributes.principalName}" />
						<br />&nbsp;<span style="font-size:9pt;font-style:italic">jsmith@example.com</span>
					</div>			
				</p>				
				<p class="entryData" >
					<div class="register_label"><kul:htmlAttributeLabel attributeEntry="${customerAttributes.customerPassword}" useShortLabel="true"/></div>
					<div class="register_data"><html:password property="registerCustomer.customerPassword" maxlength="45" size="15" />
<!--						<c:if test="${hasErrors}">-->
<!--	 						<kul:fieldShowErrorIcon />-->
<!--  						</c:if>-->
  					</div>			
				</p>
				<p class="entryData" >
					<div class="register_label"><kul:htmlAttributeLabel attributeEntry="${customerAttributes.confirmPassword}" useShortLabel="true"/></div>
					<div class="register_data"><html:password property="registerCustomer.confirmPassword" maxlength="45" size="15" />
<!--						<c:if test="${hasErrors}">-->
<!--	 						<kul:fieldShowErrorIcon />-->
<!--  						</c:if>-->
  					</div>						
				</p>				
			</div>
			<div id="register_controls">
				<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-register.gif" property="methodToCall.register" value="Register user" title="Register user" alt="Register user"/>
				<shopping:buttonLink url="home.do?methodToCall=start" title="Cancel" imageSource="${ConfigProperties.kmm.externalizable.images.url}button-cancel.gif" />
			</div>
		</shopping:infoEntry>
	</div>

</shopping:page>