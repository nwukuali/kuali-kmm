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
<c:set var="stockAttributes" value="${DataDictionary.Stock.attributes}" />
<c:set var="massUpdateDocAttributes" value="${DataDictionary.MassUpdateDocument.attributes}" />
<c:set var="readOnly" value="${ ! KualiForm.documentActions[Constants.KUALI_ACTION_CAN_EDIT]}" />

<kul:documentPage showDocumentInfo="true" htmlFormAction="massUpdateAgreement"
	documentTypeName="SAMU" renderMultipart="true" showTabButtons="true">
	<kul:hiddenDocumentFields />	
	<kul:documentOverview editingMode="${KualiForm.editingMode}" />
	<c:set var="fileLoaded" value="${not empty KualiForm.uploadSummary}" />
			
	<kul:tab tabTitle="Agreement Mass Maintenance" defaultOpen="true" tabErrorKey="document.previousAgreementNumber,document.newAgreementNumber,file*">
		<div class="tab-container" align=center>
			<h3>Upload Data</h3>
			<table class="datatable" cellspacing="0" cellpadding="0" title="Agreement Upload Data" summary="Agreement Mass Maintenance Information">
				<tr>
					<th width="50%" class="bord-l-b" align="right"><kul:htmlAttributeLabel attributeEntry="${massUpdateDocAttributes.previousAgreementNumber}" /></th>						
					<td class="datacell-nowrap">
						<kul:htmlControlAttribute property="document.previousAgreementNumber" attributeEntry="${massUpdateDocAttributes.previousAgreementNumber}" readOnly="${readOnly}" />
						<c:if test="${!readOnly}"><kul:lookup boClassName="org.kuali.ext.mm.businessobject.Agreement" fieldConversions="agreementNbr:document.previousAgreementNumber" lookupParameters="document.previousAgreementNumber:agreementNbr" /></c:if>			
					</td>
				</tr>
				<tr>
					<th width="50%" class="bord-l-b" align="right"><kul:htmlAttributeLabel attributeEntry="${massUpdateDocAttributes.newAgreementNumber}" /></th>						
					<td class="datacell-nowrap">
						<kul:htmlControlAttribute property="document.newAgreementNumber" attributeEntry="${massUpdateDocAttributes.newAgreementNumber}" readOnly="${readOnly}" />
						<c:if test="${!readOnly }"><kul:lookup boClassName="org.kuali.ext.mm.businessobject.Agreement" fieldConversions="agreementNbr:document.newAgreementNumber" lookupParameters="document.newAgreementNumber:agreementNbr" /></c:if>			
					</td>
				</tr>
				<tr>
					<th class="grid" width="50%" align="right"><label>* File to upload (CSV):</label></th>
					<td class="grid" width="50%">
						<c:choose>
							<c:when test="${readOnly or fileLoaded}" >
								&nbsp;${KualiForm.document.updateFileName }
								<c:if test="${!readOnly }">
									<html:image property="methodToCall.clear" src="${ConfigProperties.kr.externalizable.images.url}tinybutton-clear1.gif" styleClass="tinybutton" alt="Clear the current file" title="Clear Current File"/>
								</c:if>
							</c:when>
							<c:otherwise>
								&nbsp;<html-el:file styleClass="dataCell" size="50" property="file" />&nbsp;
								<html:image property="methodToCall.loadFile" src="${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif" styleClass="tinybutton" alt="Load File" title="Load File"/>
								</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<c:if test="${fileLoaded }">
					<tr>
						<th width="50%" class="bord-l-b" align="right"><label>Total Lines:</label></th>
						<td class="grid" width="50%">&nbsp;${KualiForm.uploadSummary.lineCount}</td>
					</tr>
					<tr>
						<th width="50%" class="bord-l-b" align="right"><label>Errors:</label></th>
						<td class="grid" width="50%">&nbsp;${KualiForm.uploadSummary.errorCount}</td>
					</tr>
				</c:if>
				<c:if test="${readOnly}">
					<tr>
						<th width="50%" class="bord-l-b" align="right"><label>Stock items updated:</label></th>
						<td class="grid" width="50%">&nbsp;${fn:length(KualiForm.document.massUpdateDetails)}</td>
					</tr>
				</c:if>									
			</table>
			<c:if test="${fileLoaded and !readOnly and KualiForm.uploadSummary.errorCount == 0 }">		
				<div style="text-align:center; font-weight:bold;padding-top:.5em">File loaded successfully.  Submit the document to complete changes.</div>
			</c:if>
		</div>		
	</kul:tab>
	<c:if test="${fileLoaded}">		
		<kul:tab tabTitle="File Data Errors" defaultOpen="false" tabErrorKey="uploadSummary*" >
			<div class="tab-container" align=center>
				&nbsp;
			</div>
		</kul:tab>			
	</c:if>
	<mm:financialGlpes generalLedgerPendingEntries="${KualiForm.document.approvedGeneralLedgerPendingEntries}"/>
	<kul:notes />
	<kul:adHocRecipients />
	<kul:routeLog />
	<kul:panelFooter />
	<kul:documentControls
	    transactionalDocument="${documentEntry.transactionalDocument}" extraButtons="${KualiForm.extraButtons}" />
</kul:documentPage>