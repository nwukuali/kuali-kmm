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

<channel:portalChannelTop channelTitle="Transactions" />
<div class="body">

	<strong>Materiel Management</strong><br />
    <ul class="chan">    	
		<li><portal:portalLink displayTitle="true" title="Agreement Mass Maintenance" url="massUpdateAgreement.do?methodToCall=docHandler&command=initiate&docTypeName=SAMU" /></li>
    	<li><a class="portal_link" href="portal.do?channelTitle=Catalog Item - Create&channelUrl=kr/lookup.do?businessObjectClassName=org.kuali.ext.mm.businessobject.Catalog&conversionFields=catalogId:catalogId,fromItemMaint:fromItemMaint&returnLocation=portal.do&docFormKey=88888888"  title="Catalog Item Create">Catalog Item - Create</a></li>
    	<li><portal:portalLink displayTitle="true" title="Catalog Item - Edit" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.CatalogItem&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>
    	<li><portal:portalLink displayTitle="true" title="Check In" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.CheckInReceivable&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
		<li><portal:portalLink displayTitle="true" title="Receipt Correction" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.CheckInCorrection&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>		
		<li><portal:portalLink displayTitle="true" title="Reorder" url="reorderLookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.ReorderItem&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
		<li><portal:portalLink displayTitle="true" title="Returns From Customer" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.OrderReturnDetail&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
		<li><portal:portalLink displayTitle="true" title="Returns To Vendor" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.OrderReturnDetailForVendor&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
    	<li><portal:portalLink displayTitle="true" title="Count Worksheets - Create/Print" url="initiateWorksheetDoc.do" /></li>
        <li><portal:portalLink displayTitle="true" title="Count Worksheets - Enter/Print " url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.WorksheetCountDocumentLookable&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
        <li><portal:portalLink displayTitle="true" title="Pick List - Generate" url="pickList.do?methodToCall=docHandler&command=initiate&docTypeName=SPKL" /></li>
        <li><portal:portalLink displayTitle="true" title="Pick List - Verify" url="pickVerify.do?methodToCall=docHandler&command=initiate&docTypeName=SPKV" /></li>
        <li><portal:portalLink displayTitle="true" title="Pick Tickets" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.PickTicket&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
		<li><portal:portalLink displayTitle="true" title="Backorders" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.BackOrder&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>
        <li><portal:portalLink displayTitle="true" title="Stock - History Lookup" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.StockHistory&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>
        <li><portal:portalLink displayTitle="true" title="Stock - Manual Adjustment" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.Stock&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>
        <li><a href="${ConfigProperties.application.url}/mm/index.jsp">Shopping Cart</a></li>  
        <!-- Commented out for deployment 
        <li><portal:portalLink displayTitle="true" title="Credit Memo Expected" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.CreditMemoExpected&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li> 
        <li><portal:portalLink displayTitle="true" title="Upload Catalog" url="uploadCatalog.do?methodToCall=docHandler&command=initiate&docTypeName=SUPC" /></li>
        <li><portal:portalLink displayTitle="true" title="Approve Catalog" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.CatalogPendingHelper&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>
        -->     
    </ul>
</div>
<channel:portalChannelBottom />
