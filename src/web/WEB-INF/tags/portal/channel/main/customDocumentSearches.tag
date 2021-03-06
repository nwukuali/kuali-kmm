<%--
 Copyright 2007 The Kuali Foundation.
 
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

<channel:portalChannelTop channelTitle="Custom Document Searches" />
<div class="body">
	<portal:portalLink displayTitle="true" title="Financial Processing Transactions" url="${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=FP" /><br />
	<portal:portalLink displayTitle="true" title="Labor Distribution Transactions" url="${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=LD" /><br />
	<portal:portalLink displayTitle="true" title="Purchasing/Accounts Payable Transactions" url="${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=PRAP" /><br /><br />
 	<strong>Accounts Receivable</strong><br/>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title='Customer Invoices' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=INV'/></li>
        <li><portal:portalLink displayTitle="true" title='Customer Credit Memos' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=CRM'/></li>
        <li><portal:portalLink displayTitle="true" title='Customer Invoice Writeoffs' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=INVW'/></li>
        <li><portal:portalLink displayTitle="true" title='Cash Controls' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=CTRL'/></li>
        <li><portal:portalLink displayTitle="true" title='Payment Applications' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=APP'/></li>
    </ul>
    <strong>Capital Asset Management</strong><br/>
    <ul class="chan">
		<li><portal:portalLink displayTitle="true" title='Asset Maintenance' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=CAMM'/></li>
	</ul>
	<strong>Contracts & Grants</strong><br/>
    <ul class="chan">
		<li><portal:portalLink displayTitle="true" title='Proposals' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=PRPL'/></li>
	</ul>
 	<strong>Effort Certification</strong><br/>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title='Effort Certification' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=ECD'/></li>
    </ul>    
	<strong>Financial Processing</strong><br/>
    <ul class="chan">
		<li><portal:portalLink displayTitle="true" title="Disbursement Vouchers" url="${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=DV" /></li>
	</ul>
	<strong>Purchasing/Accounts Payable</strong><br/>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title='Electronic Invoice Rejects' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=EIRT'/></li>
        <li><portal:portalLink displayTitle="true" title='Payment Requests' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=PREQ'/></li>
        <li><portal:portalLink displayTitle="true" title='Purchase Orders' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=PO'/></li>
        <li><portal:portalLink displayTitle="true" title='Receiving' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=RCV'/></li>
        <li><portal:portalLink displayTitle="true" title='Requisitions' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=REQS'/></li>
        <li><portal:portalLink displayTitle="true" title='Vendor Credit Memos' url='${ConfigProperties.workflow.documentsearch.base.url}&docTypeFullName=CM'/></li>
     </ul>
    </div>
<channel:portalChannelBottom />
