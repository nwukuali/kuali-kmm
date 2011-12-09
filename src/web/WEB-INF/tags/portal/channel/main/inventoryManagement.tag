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

<channel:portalChannelTop channelTitle="Inventory Management" />
<div class="body">
	<strong>Inventory Actions</strong><br />
	<ul class="chan">
		<li><portal:portalLink displayTitle="true" title="Reorder" url="reorderLookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.ReorderItem&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
		<li><portal:portalLink displayTitle="true" title="Check In" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.CheckInReceivable&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
		<li><portal:portalLink displayTitle="true" title="Receipt Correction" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.CheckInCorrection&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
        <li><portal:portalLink displayTitle="true" title="Stock - Manual Adjustment" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.Stock&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>
        <li><portal:portalLink displayTitle="true" title="Stock - History Lookup" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.StockHistory&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>
        <li><portal:portalLink displayTitle="true" title="Agreement" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.Agreement&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
        <li><portal:portalLink displayTitle="true" title="Agreement Mass Maintenance" url="massUpdateAgreement.do?methodToCall=docHandler&command=initiate&docTypeName=SAMU" /></li> 		
	</ul>
	   
	<strong>Counting</strong><br />
	<ul class="chan">
		<li><portal:portalLink displayTitle="true" title="Count Worksheets - Create/Print" url="initiateWorksheetDoc.do" /></li>
        <li><portal:portalLink displayTitle="true" title="Count Worksheets - Enter/Print " url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.WorksheetCountDocumentLookable&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
        <li><portal:portalLink displayTitle="true" title="Cycle Count Codes" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.CycleCount&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>		
	</ul>
	
	<strong>Rentals</strong>
	<ul class="chan">
		<li><portal:portalLink displayTitle="true" title="Rental Tracking" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.Rental&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
	</ul>
 </div>
<channel:portalChannelBottom />
