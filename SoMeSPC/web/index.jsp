<%@page import="com.openxava.phone.web.Browsers"%>
<%@page import="org.openxava.util.Users"%>

<jsp:useBean id="modules" class="com.openxava.naviox.Modules" scope="session"/>

<link rel="icon" href="<%=request.getContextPath()%>/naviox/images/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/naviox/images/favicon.ico" type="image/x-icon" /> 

<%
String module = Users.getCurrent() == null?"SignIn":modules.getCurrent();
String url = Browsers.isMobile(request)?"phone":"m/" + module;
%>

<script type="text/javascript">
window.location="<%=url%>";
</script>
