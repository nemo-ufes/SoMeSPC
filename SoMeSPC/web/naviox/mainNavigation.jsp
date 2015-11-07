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
		<%
		    if (Is.emptyString(NaviOXPreferences.getInstance()
							.getAutologinUser())) {
						String userName = Users.getCurrent();
						if (userName != null) {
		%>
		<button onclick="toggle();" style="margin: 0px;" class="btn btn-xs btn-menu">
			<i class="glyphicon glyphicon-align-justify"></i> <span style="margin-left: 5px;">Menus</span>
		</button>
		<span style="margin-left: 10px;">SoMeSPC - powerful tool for measurement</span>

		<%
		    } else {
		%>
		<span>SoMeSPC - powerful tool for measurement</span>
		<%
		    }
		%>
	</nobr>
</div>

<span id="main_navigation_right"> <nobr>
		<span id="main_navigation_right_bridge1">&nbsp;&nbsp;&nbsp;</span><span id="main_navigation_right_bridge2">&nbsp;&nbsp;&nbsp;</span><span id="main_navigation_right_content"> <%
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
	function hasClass(elementId, cls) {
		var element = document.getElementById(elementId);
    	return (' ' + element.className + ' ').indexOf(' ' + cls + ' ') > -1;
	}
	
	function toggle() {
		
		var element = document.getElementById('content-row');
		
		if (hasClass('content-row','col-md-7')){
			$('#tree-row').toggle(500, function(){
				element.classList.remove("col-md-7");
				element.classList.add("col-md-11");
				element.classList.add("margin-left");	
			});	
		} else {
			element.classList.remove("col-md-11");
			element.classList.remove("margin-left");
			element.classList.add("col-md-7");
			$('#tree-row').toggle(500);
		}	
	}
</script>