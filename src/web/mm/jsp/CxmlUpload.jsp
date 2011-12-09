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
<kul:page showDocumentInfo="false" htmlFormAction="postCxml" renderMultipart="true" showTabButtons="true" docTitle="Upload CXML" transactionalDocument="false" headerDispatch="true"
	headerTabActive="true" sessionDocument="false" headerMenuBar="" feedbackKey="true" defaultMethodToCall="start">
	<kul:tabTop tabTitle="CXML Upload" defaultOpen="true">
			<div class="tab-container" align=center>
			<h3>CXML File Upload</h3>
			<table cellpadding="0" cellspacing="0" class="datatable">
				<tr>
					<th class="grid" width="50%" align="right">Upload CXML:</th>
					<td class="grid"><html:file property="cxmlFile" size="30" styleId="cxmlFile" value="" /></td>
				</tr>
				<c:if test="${KualiForm.cxmlResponse != null}">
				<tr><td colspan="2" class="grid">
					<div style="text-align:left;overflow:auto;">${KualiForm.cxmlResponse}</div>
				</td></tr>
				</c:if>
			</table>
			</div>
	</kul:tabTop>
	<kul:panelFooter />
	<div id="globalbuttons" class="globalbuttons">
		<html:image src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_submit.gif" property="methodToCall.postCxml" title="Submit" alt="Submit" />
		<html:image src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_close.gif" styleClass="globalbuttons" property="methodToCall.close" title="Close" alt="Close" />
	</div>
</kul:page>
