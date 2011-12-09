<%--
 Copyright 2005-2007 The Kuali Foundation.
 
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
<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp"%>

<%@ attribute name="title" required="true" %>
<%@ attribute name="htmlFormAction" required="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${title}</title>
<script>var jsContextPath = "${pageContext.request.contextPath}";</script>
<script language="JavaScript">
</script>
<c:forEach items="${fn:split(ConfigProperties.cart.css.files, ',')}" var="cssFile">
	<link href="${ConfigProperties.application.url}/${cssFile}" rel="stylesheet" type="text/css" />
</c:forEach>

</head>

<body topmargin="0" leftmargin="0" marginheight="0" marginwidth="0" >
	<div id="content_container">
		<html:form action="/${htmlFormAction}.do" method="post" >
			<div id="topBanner">
				<div id="topBanner_left">
					<div id="logo">						
						<img src="${ConfigProperties.kmm.externalizable.images.url}USBanner_new.jpg" alt="University Stores Logo" title="Go to University Stores Home" border="0" height="130px" />						
					</div>
				</div>
			</div>
			<div id="searchBanner">
				&nbsp;	
			</div>			
			<div id="doBody_container" OnKeyPress="return disableEnterKey(event)">
		
				<jsp:doBody/>
				<div class="clear"></div>
					
			</div>
			<shopping:cartBottom />
		</html:form>
	</div>	
</body>
</html>
