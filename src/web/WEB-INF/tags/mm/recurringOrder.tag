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
<%@ include file="/mm/jsp/mmTldHeader.jsp"%>

<%@ attribute name="recurringOrder" required="true" type="org.kuali.ext.mm.businessobject.RecurringOrder" %>
<%@ attribute name="recurringOrderPropertyName" required="true" %>
<%@ attribute name="tabErrorKey" required="false" %>
<%@ attribute name="readOnly" required="true" %>

<c:set var="recurringOrderAttributes" value="${DataDictionary.RecurringOrder.attributes}" />	
<kul:tab tabTitle="Recurring Order" defaultOpen="false" tabErrorKey="${tabErrorKey}">
	<div class="tab-container" align=center>
		<h3>Recurring Order Settings</h3>
		<table cellpadding="0" cellspacing="0" class="datatable" title="Order Content" summary="Review order content">
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		
						<kul:htmlAttributeLabel attributeEntry="${recurringOrderAttributes.startDt}" />
					</div>
				</th>
				<td class="datacell-nowrap">
					<c:choose>
						<c:when test="${readOnly}">
							<kul:htmlControlAttribute property="${recurringOrderPropertyName}.startDt" attributeEntry="${recurringOrderAttributes.startDt}" readOnly="${true}" />		
						</c:when>
						<c:otherwise>
							<kul:dateInput property="${recurringOrderPropertyName}.startDt" attributeEntry="${recurringOrderAttributes.startDt}" />	
						</c:otherwise>
					</c:choose>						
				</td>
			</tr>
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		
						<kul:htmlAttributeLabel attributeEntry="${recurringOrderAttributes.endDt}" />
					</div>
				</th>
				<td class="datacell-nowrap">
					<c:choose>
						<c:when test="${readOnly}">
							<kul:htmlControlAttribute property="${recurringOrderPropertyName}.endDt" attributeEntry="${recurringOrderAttributes.endDt}" readOnly="${true}" />
						</c:when>
						<c:otherwise>
							<kul:dateInput property="${recurringOrderPropertyName}.endDt" attributeEntry="${recurringOrderAttributes.endDt}" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		
						<kul:htmlAttributeLabel attributeEntry="${recurringOrderAttributes.noEndDateInd}" />
					</div>
				</th>
				<td class="datacell-nowrap">
					<kul:htmlControlAttribute property="${recurringOrderPropertyName}.noEndDateInd" attributeEntry="${recurringOrderAttributes.noEndDateInd}" readOnly="${readOnly}" />
				</td>
			</tr>
			<tr>
				<th width="50%" class="bord-l-b">
		        	<div align="right">		
						<kul:htmlAttributeLabel attributeEntry="${recurringOrderAttributes.timesPerYr}" />
					</div>
				</th>
				<td class="datacell-nowrap">	
					<c:choose>
						<c:when test="${readOnly}">
							${KualiForm.frequencyMap[recurringOrder.timesPerYr] }
						</c:when>
						<c:otherwise>
							<kul:htmlControlAttribute property="${recurringOrderPropertyName}.timesPerYr" attributeEntry="${recurringOrderAttributes.timesPerYr}" readOnly="${readOnly}" />	
						</c:otherwise>
					</c:choose>
					
				</td>
			</tr>
		</table>
	</div>
</kul:tab>