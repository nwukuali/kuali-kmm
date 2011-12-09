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

<channel:portalChannelTop channelTitle="Catalog Management" />
<div class="body">
	<strong>Catalog Actions</strong><br />
	<ul class="chan">
		<li><portal:portalLink displayTitle="true" title="Catalogs" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.Catalog&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>
		<li><portal:portalLink displayTitle="true" title="Upload Catalog" url="uploadCatalog.do?methodToCall=docHandler&command=initiate&docTypeName=SUPC" /></li>
        <li><portal:portalLink displayTitle="true" title="Review & Approve Catalog" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.CatalogPendingHelper&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>
		<li><portal:portalLink displayTitle="true" title="Catalog Item - Create" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.Catalog&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>
<!--		<li><portal:portalLink displayTitle="true" title="Catalog Item - Create" url="kr/lookup.do?businessObjectClassName=org.kuali.ext.mm.businessobject.Catalog&conversionFields=catalogId:catalogId,fromItemMaint:fromItemMaint&returnLocation=portal.do&docFormKey=88888888" /></li>-->
		<li><portal:portalLink displayTitle="true" title="Catalog Item - Edit" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.CatalogItem&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>
		<li><portal:portalLink displayTitle="true" title="Catalog Image" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.CatalogImage&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
		<li><portal:portalLink displayTitle="true" title="Catalog Group" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.CatalogGroup&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>
		<li><portal:portalLink displayTitle="true" title="Catalog Subgroup" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.CatalogSubgroup&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>
		<li><portal:portalLink displayTitle="true" title="Catalog Restrictions" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.CatalogRestriction&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>
		<li><portal:portalLink displayTitle="true" title="Markups" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.Markup&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>
		<li><portal:portalLink displayTitle="true" title="Shopping Front Page" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.ShoppingFrontPage&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
	</ul>    
	<strong>B2B/Punch-Out</strong><br />
	<ul class="chan">
		<li><portal:portalLink displayTitle="true" title="Punch-Out Vendor" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.PunchOutVendor&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
		<li><portal:portalLink displayTitle="true" title="PCard" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.PCard&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
		<li><portal:portalLink displayTitle="true" title="Post - Vendor Invoice Discrepancy" url="discrepancy.do?methodToCall=docHandler&command=initiate&docTypeName=VIRD" /></li>
        <li><portal:portalLink displayTitle="true" title="Undo - Vendor Invoice Discrepancy" url="removeDiscrepancy.do?methodToCall=docHandler&command=initiate&docTypeName=VIRD" /></li>
	</ul>
</div>
<channel:portalChannelBottom />
