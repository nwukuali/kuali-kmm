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

<channel:portalChannelTop channelTitle="Order Management" />
<div class="body">
	<strong>Fullfillment</strong><br />
	<ul class="chan">
		<li><portal:portalLink displayTitle="true" title="Pick List - Generate" url="pickList.do?methodToCall=docHandler&command=initiate&docTypeName=SPKL" /></li>
        <li><portal:portalLink displayTitle="true" title="Pick List - Verify" url="pickVerify.do?methodToCall=docHandler&command=initiate&docTypeName=SPKV" /></li>
        <li><portal:portalLink displayTitle="true" title="Pick Tickets" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.PickTicket&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
		<li><portal:portalLink displayTitle="true" title="Backorders" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.BackOrder&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>
		<li><portal:portalLink displayTitle="true" title="Pack List Announcement" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.PackListAnnouncement&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>
	</ul>
	
	<strong>Delivery</strong><br />
	<ul class="chan">
		<li><portal:portalLink displayTitle="true" title="Delivery Label - Generate/Print" url="deliveryLabel.do?methodToCall=docHandler&command=initiate&docTypeName=SPDL" /></li>
        <li><portal:portalLink displayTitle="true" title="Driver Log - Generate/Print" url="deliveryLog.do?methodToCall=docHandler&command=initiate&docTypeName=DLDL" /></li>
        <li><portal:portalLink displayTitle="true" title="Driver Log - Update" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.Delivery&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>              
	</ul>
	
	<strong>Returns</strong><br />
	<ul class="chan">
		<li><portal:portalLink displayTitle="true" title="Returns From Customer" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.OrderReturnDetail&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
		<li><portal:portalLink displayTitle="true" title="Returns To Vendor" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.OrderReturnDetailForVendor&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
	</ul>
	
</div>
<channel:portalChannelBottom />
