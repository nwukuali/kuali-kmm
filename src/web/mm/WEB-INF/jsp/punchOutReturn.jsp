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

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>KMM Shopping Punch-out Return</title>
<c:forEach items="${fn:split(ConfigProperties.cart.css.files, ',')}" var="cssFile">
	<link href="${ConfigProperties.application.url}/${cssFile}" rel="stylesheet" type="text/css" />
</c:forEach>
</head>

<body class="punchOutReturn_body" topmargin="0" leftmargin="0" marginheight="0" marginwidth="0" >
	<input id="returnCount" type="hidden" value="${fn:length(KualiForm.punchOutCart.shopCartDetails)}" />
	<html:form action="/punchOutReturn.do" method="post" >
		<div id="punchOutReturn_cartwrapper">
			<div style="text-align:right;" >
				<shopping:buttonLink title="Continue Shopping" url="${ConfigProperties.application.url}/mm/home.do?methodToCall=start" imageSource="${ConfigProperties.kmm.externalizable.images.url}button-continueShopping.gif" target="_parent" />
			</div>		
			<cart:shoppingCartView displayName="These items have been added to your shopping cart:" shoppingCart="${KualiForm.punchOutCart}" shoppingCartPropertyName="punchOutCart" showControls="${false}" editMode="${false}"/>
		</div>			
	</html:form>
	<script type="text/javascript">
		parent.document.getElementById('itemCount').innerHTML=Number(parent.document.getElementById('itemCount').innerHTML) + Number(document.getElementById('returnCount').value);
	</script>
</body>
</html>