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

<channel:portalChannelTop channelTitle="Useful KFS Links" />
<div class="body">
  <strong>KFS Links</strong><br />
    <ul class="chan">
		<li><portal:portalLink displayTitle="true" title="General Ledger Entry" url="${ConfigProperties.finance.system.url}/glModifiedInquiry.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.gl.businessobject.Entry&docFormKey=88888888&returnLocation=portal.do&hideReturnLink=true" /></li>
		<li><portal:portalLink displayTitle="true" title="General Ledger Pending Entry" url="${ConfigProperties.finance.system.url}/glModifiedInquiry.do?methodToCall=start&businessObjectClassName=org.kuali.kfs.sys.businessobject.GeneralLedgerPendingEntry&docFormKey=88888888&returnLocation=portal.do&hideReturnLink=true" /></li>
		<li><portal:portalLink displayTitle="true" title="Search Internal Billing" url="${ConfigProperties.finance.system.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.rice.kew.docsearch.DocSearchCriteriaDTO&docFormKey=88888888&hideReturnLink=true&docTypeFullName=IB" /></li>
		<li><portal:portalLink displayTitle="true" title="Search Requisitions" url="${ConfigProperties.finance.system.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.rice.kew.docsearch.DocSearchCriteriaDTO&docFormKey=88888888&hideReturnLink=true&docTypeFullName=REQS" /></li>
		<li><portal:portalLink displayTitle="true" title="Search Purchase Orders" url="${ConfigProperties.finance.system.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.rice.kew.docsearch.DocSearchCriteriaDTO&docFormKey=88888888&hideReturnLink=true&docTypeFullName=PO" /></li>
		<li><portal:portalLink displayTitle="true" title="Search Payment Requests" url="${ConfigProperties.finance.system.url}/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.rice.kew.docsearch.DocSearchCriteriaDTO&docFormKey=88888888&hideReturnLink=true&docTypeFullName=PREQ" /></li>
    </ul>
 </div>
<channel:portalChannelBottom />
