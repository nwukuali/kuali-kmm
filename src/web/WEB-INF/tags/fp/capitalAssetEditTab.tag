<%--
 Copyright 2005-2009 The Kuali Foundation
 
 Licensed under the Educational Community License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.opensource.org/licenses/ecl2.php
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>

<%@ include file="/mm/jsp/mmTldHeader.jsp"%>
<%@ tag description="render the capital edit tag that contains the given capital asset info"%>

<%@ attribute name="readOnly" required="false" description="Whether the capital asset information should be read only" %>
<%@ attribute name="capitalAssetInfo" required="true"  type="java.lang.Object" %>
<%@ attribute name="capitalAssetInfoName" required="true"  %>
<%@ attribute name="newCapitalAssetInfo" required="true" type="java.lang.Object" %>
<%@ attribute name="newCapitalAssetInfoName" required="true"  %>
<%@ attribute name="detailLineId" required="true" description="Number to identify the detail line for which this capital asset information applies."%>

<kul:tab tabTitle="Capital Edit" defaultOpen="false" useCurrentTabIndexAsKey="true" tabErrorKey="document.orderDetails[${detailLineId}].capitalAssetInformation.*" >
     <div class="tab-container" align="center">	 
	 <c:choose>
	 	<c:when test="${not empty capitalAssetInfo}">
	 		<fp:capitalAssetInfo capitalAssetInfo="${capitalAssetInfo}" capitalAssetInfoName="${capitalAssetInfoName}" readOnly="${readOnly}" detailLineId="${detailLineId}"/>
	 	</c:when>
	 	<c:when test="${not readOnly}">
	 		<fp:capitalAssetInfo capitalAssetInfo="${newCapitalAssetInfo}" capitalAssetInfoName="${newCapitalAssetInfoName}" detailLineId="${detailLineId}"/>
	 	</c:when>
	 </c:choose>
	 </div>	
</kul:tab>	 
