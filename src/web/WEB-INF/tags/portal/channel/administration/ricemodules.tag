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

<channel:portalChannelTop channelTitle="Rice Modules" />
<div class="body">
    <ul class="chan">
	    <li><portal:portalLink displayTitle="true" title="Kuali Enterprise Workflow" url="${ConfigProperties.kmm.url}/kew/"/></li>
		<li><portal:portalLink displayTitle="true" title="Kuali Service Bus" url="${ConfigProperties.ksb.server.url}/ksb/"/></li>
		<li><portal:portalLink displayTitle="true" title="Kuali Identity Management" url="${ConfigProperties.kmm.url}/kim/"/></li>
		<li><portal:portalLink displayTitle="true" title="Kuali Enterprise Notification" url="${ConfigProperties.kmm.url}/ken/"/></li>		
	</ul>
</div>
<channel:portalChannelBottom />
                
