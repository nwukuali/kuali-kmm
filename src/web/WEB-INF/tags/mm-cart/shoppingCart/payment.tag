<%--
 Copyright 2005-2007 The Kuali Foundation.
 
 Licensed under the Educational Community License, Version 1.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.opensource.org/licenses/ecl1.php
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>
<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp"%>

<%@ attribute name="readOnly" required="true" %>

<shopping:infoEntry id="payment" title="Payment Method" canHide="false">
	<div id="changeButton" >
		<shopping:buttonLink title="Change" url="${ConfigProperties.application.url}/mm/manageAddresses.do?methodToCall=start&addressType=${addressType}" imageSource="_blank" />&nbsp;
	</div>
	<p class="cardEntryData">
		<label for="cardType">Card Type:</label>
		<c:if test="${readOnly}">
			<html:text property="cardType" size="15" alt="Card type" readonly="${readOnly}" />
		</c:if>
		<c:if test="${not readOnly}">
			<html:select property="cardType" >
					<html:option value="Mastercard">Mastercard</html:option>
					<html:option value="Visa">MasterCard</html:option>			
			</html:select>
		</c:if>
	&nbsp;
		<label for="cardNumber">Card Number:</label>
		<html:text property="cardNumber" size="16" alt="Card Number" readonly="${readOnly }" />
	
		<label for="cardholderName">Name on card:</label>
		<html:text property="cardholderName" size="20" alt="Card Number" readonly="${readOnly }" />
	&nbsp;
		<label>Exp. Date:</label>
		<c:if test="${readOnly}">
			<html:text property="expireMonth" size="2" alt="Expiration Month" readonly="${readOnly}" />
			<html:text property="expireYear" size="4" alt="Expiration Year" readonly="${readOnly}" />
		</c:if>
		<c:if test="${not readOnly}">
			<html:select property="expireMonth" >
				<html:option value="1">01</html:option>
				<html:option value="2">02</html:option>
				<html:option value="3">03</html:option>
				<html:option value="4">04</html:option>
				<html:option value="5">05</html:option>
				<html:option value="6">06</html:option>
				<html:option value="7">07</html:option>
				<html:option value="8">08</html:option>
				<html:option value="9">09</html:option>
				<html:option value="10">10</html:option>
				<html:option value="11">11</html:option>
				<html:option value="12">12</html:option>
			</html:select>
			<html:select property="expireYear" >
				<c:forEach begin="${KualiForm.currentYear}" end="${KualiForm.currentYear + 20}" var="year" >
					<html:option value="${year}">${year}</html:option>
				</c:forEach>
			</html:select>
		</c:if>
	</p>
</shopping:infoEntry>	
