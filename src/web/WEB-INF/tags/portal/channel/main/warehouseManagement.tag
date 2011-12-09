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

<channel:portalChannelTop channelTitle="Warehouse Management" />
<div class="body">
	<ul class="chan">
		<li><portal:portalLink displayTitle="true" title="Bin" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.Bin&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>
		<li><portal:portalLink displayTitle="true" title="Zone" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.Zone&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>		
		<li><portal:portalLink displayTitle="true" title="Warehouse" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.Warehouse&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>
		<li><portal:portalLink displayTitle="true" title="Warehouse Account Type" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.WarehouseAccountType&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>
		<li><portal:portalLink displayTitle="true" title="Warehouse Object Type" url="kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.ext.mm.businessobject.WarehouseObjectType&docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"  /></li>		
	</ul>
 </div>
<channel:portalChannelBottom />
