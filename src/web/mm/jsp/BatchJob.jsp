<%--
 Copyright 2006-2007 The Kuali Foundation.
 
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
<kul:page showDocumentInfo="false" htmlFormAction="batchControl" renderMultipart="true" showTabButtons="true" docTitle="Batch Control" transactionalDocument="false" headerDispatch="true"
	headerTabActive="true" sessionDocument="false" headerMenuBar="" feedbackKey="true" defaultMethodToCall="start">
	<kul:tabTop tabTitle="Batch Jobs" defaultOpen="true">
		<div class="tab-container" align=center>
		<h3>Batch Jobs</h3>
		<center>
		<table cellpadding="0" cellspacing="0" class="datatable">
			<c:if test="${KualiForm.message != null}">
				<th class="grid" width="50%" align="right">Message:</th>
				<td class="grid">${KualiForm.message}</td>
			</c:if>
			<tr>
				<th class="grid" width="50%" align="right">Select Job:</th>
				<td class="grid"><select name="jobName">
					<option value="">Select</option>
					<option value="agreementUpdateJob">agreementUpdateJob</option>
					<option value="b2bInvoiceJob">b2bInvoiceJob</option>
					<option value="backOrderReleaseJob">backOrderReleaseJob</option>
					<option value="catalogItemSearchDataBuilderJob">catalogItemSearchDataBuilderJob</option>
					<option value="glCollectorFeedJob">glCollectorFeedJob</option>
					<option value="loadPendingCatalogItemsJob">loadPendingCatalogItemsJob</option>					
					<option value="orderReconciliationJob">orderReconciliationJob</option>
					<option value="publishApprovedCatalogsJob">publishApprovedCatalogsJob</option>
					<option value="recurringOrderJob">recurringOrderJob</option>
					<option value="rentalDemurrageChargeJob">rentalDemurrageChargeJob</option>					
					<option value="stockCountDailyInsertJob">stockCountDailyInsertJob</option>
				</select></td>
			</tr>
			<c:if test="${KualiForm.batchStatus != null}">
				<tr>
					<th class="grid" width="50%" align="right">Job:</th>
					<td class="grid">${KualiForm.jobName}</td>
				</tr>
				<tr>
					<th class="grid" width="50%" align="right">Success:</th>
					<td class="grid">${KualiForm.batchStatus.success}</td>
				</tr>
				<tr>
					<th class="grid" width="50%" align="right">Error Code:</th>
					<td class="grid">${KualiForm.batchStatus.errorCode}</td>
				</tr>
				<tr>
					<th class="grid" width="50%" align="right">Error Message:</th>
					<td class="grid">${KualiForm.batchStatus.errorMessage}</td>
				</tr>		
				<c:if test="${KualiForm.batchStatus.log4jHtmlMessage != null}">
				<tr><td colspan="2" class="grid">
					<div style="text-align:left;height:100px;overflow:auto;">${KualiForm.batchStatus.log4jHtmlMessage}</div>
				</td></tr>
				</c:if>
			</c:if>
		</table>
		</center>
		<br/>
		</div>
	</kul:tabTop>
	<kul:panelFooter />
	<div id="globalbuttons" class="globalbuttons"><html:image src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_submit.gif" property="methodToCall.submit" title="Submit" alt="Submit" />
	<html:image src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_cancel.gif" styleClass="globalbuttons" property="methodToCall.cancel" title="Cancel" alt="Cancel" /></div>
</kul:page>
