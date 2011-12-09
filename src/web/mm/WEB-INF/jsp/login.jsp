<%@ include file="/mm/WEB-INF/jsp/mmCartTldHeader.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<shopping:page title="University Stores" htmlFormAction="login">

	<div id="login_container">
		<div id="login_errors">
			<kul:errors keyMatch="*"/>
		</div>
		<shopping:infoEntry title="Log in" id="login_box" canHide="false" >
			<div id="login_labels">
				<p class="entryData" >
					<span class="entryLabel">User ID:&nbsp;&nbsp;&nbsp;</span>
					<html:text property="username" alt="User Name" title="Enter your user name."  size="15" />
				</p>
				<p class="entryData" >
					<span class="entryLabel">Password:&nbsp;</span>
					<html:password property="userpass" alt="Password" title="Enter your password." size="15" />
				</p>
			</div>
			<div id="login_controls">
				<html:image styleClass="imageButton" src="${ConfigProperties.kmm.externalizable.images.url}button-login.gif" property="methodToCall.login" value="Log in" title="Log in" alt="Log in"/>
<!--				<span style="vertical-align:top; margin-left:1em;"><a class="blue" href="" alt="Go to University Log in" title="Login for Internal MSU Users">University Login</a></span>-->
			</div>
		</shopping:infoEntry>
	</div>

</shopping:page>