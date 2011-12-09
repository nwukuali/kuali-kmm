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
<kul:page showDocumentInfo="false" htmlFormAction="catalogImage" renderMultipart="true" showTabButtons="true" docTitle="Catalog Image Upload" transactionalDocument="false" headerDispatch="true"
	headerTabActive="true" sessionDocument="false" headerMenuBar="" feedbackKey="true" defaultMethodToCall="start">
	<kul:tabTop tabTitle="Image Upload" defaultOpen="true">
			<div class="tab-container" align=center>
			<h3>Catalog Image Upload</h3>
			<table cellpadding="0" cellspacing="0" class="datatable">
				<tr>
					<th class="grid" width="50%" align="right">Upload Image(jpg,gif,png...) or multiple Images(zip):</th>
					<td class="grid"><html:file property="imageFile" size="30" styleId="imageFile" value="" /></td>
				</tr>
			</table>
			</div>
		<div class="tab-container" align=left>
		<h3>Catalog Image(s) Review</h3>
		<table cellpadding="0" cellspacing="0" class="datatable">
			<tr>
				<td><c:forEach var="imageLink" items="${KualiForm.uploadedImages}" varStatus="rowCounter">
					<div style="position: relative; float: left; border: 1px solid gray">
						<div class="clear" align="center">${imageLink}</div>
						<div class="clear" align="center">
							<a href="mm/item.do?methodToCall=streamImage&imagePath=${imageLink}" target="_blank">					
							<img class="itemPreviewImg" src="mm/item.do?methodToCall=streamImage&imagePath=${imageLink}" height="75" width="75" /> </a>
						</div>
						<div class="clear" align="center">
							<a href="catalogImage.do?methodToCall=delete&imageName=${imageLink}">Delete</a>
						</div>
					</div>
				</c:forEach></td>
			</tr>
		</table>
		</div>
	</kul:tabTop>
	<kul:panelFooter />
	<div id="globalbuttons" class="globalbuttons">
		<html:image src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_submit.gif" property="methodToCall.upload" title="Submit" alt="Submit" />
		<html:image src="${ConfigProperties.kr.externalizable.images.url}buttonsmall_close.gif" styleClass="globalbuttons" property="methodToCall.close" title="Close" alt="Close" /></div>
</kul:page>
