<%@include file="../xava/imports.jsp"%>

<%@page import="java.util.Iterator"%>
<%@page import="org.openxava.application.meta.MetaModule"%>
<%@page import="org.openxava.util.Users"%>
<%@page import="org.openxava.util.Is"%>
<%@page import="com.openxava.naviox.Modules"%>
<%@page import="com.openxava.naviox.util.NaviOXPreferences"%>

<jsp:useBean id="modules" class="com.openxava.naviox.Modules" scope="session" />

&nbsp;
<div id="main_navigation_left">
	<nobr>
		<%--<button onclick="toggle();" class="btn btn-success">Menus</button> --%>
		<span style="color: white; font-size: 14px; font-weight: bold;">MedCEP - A powerful tool to measure</span> &nbsp; <a href="<%=request.getContextPath()%>/naviox/wizard.jsp"><span style="color: white; font-size: 14px; font-weight: bold; margin-left: 50px;">Wizard</span></a>
	</nobr>
</div>

<span id="main_navigation_right"> <nobr>
		<span id="main_navigation_right_bridge1">&nbsp;&nbsp;&nbsp;</span><span id="main_navigation_right_bridge2">&nbsp;&nbsp;&nbsp;</span><span id="main_navigation_right_content"> <%
     if (Is.emptyString(NaviOXPreferences.getInstance()
 					.getAutologinUser())) {
 				String userName = Users.getCurrent();
 				if (userName == null) {
 %> <%
     String selected = "SignIn".equals(request
 							.getParameter("module")) ? "selected" : "";
 %> <a href="<%=request.getContextPath()%>/m/SignIn" class="sign-in <%=selected%>"> <xava:message key="signin" />
		</a> <%
     } else {
 %> <a href="<%=request.getContextPath()%>/naviox/signOut.jsp" class="sign-in"><xava:message key="signout" /> (<%=userName%>)</a> <%
     }
 			}
 %>
		</span>
	</nobr></span>
<script>
	function toggle() {
		$('#menu_tree').toggle(500);
	}
</script>