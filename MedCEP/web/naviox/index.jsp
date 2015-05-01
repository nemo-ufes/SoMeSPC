
<%
    Servlets.setCharacterEncoding(request, response);
%>

<%@include file="../xava/imports.jsp"%>

<%@page import="org.openxava.web.servlets.Servlets"%>
<%@page import="org.openxava.util.Locales"%>
<%@page import="com.openxava.naviox.web.NaviOXStyle"%>

<jsp:useBean id="context" class="org.openxava.controller.ModuleContext" scope="session" />
<jsp:useBean id="modules" class="com.openxava.naviox.Modules" scope="session" />

<%
    String app = request.getParameter("application");
			String module = context.getCurrentModule(request);
			Locales.setCurrent(request);
			String sretainOrder = request.getParameter("retainOrder");
			boolean retainOrder = "true".equals(sretainOrder);
			modules.setCurrent(request.getParameter("application"),
					request.getParameter("module"), retainOrder);
			String oxVersion = org.openxava.controller.ModuleManager
					.getVersion();
%>

<!DOCTYPE html>

<head>
<title><%=modules.getCurrentModuleDescription(request)%></title>
<link rel="icon" href="<%=request.getContextPath()%>/naviox/images/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/naviox/images/favicon.ico" type="image/x-icon" />

<link href="<%=request.getContextPath()%>/naviox/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/naviox/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/naviox/style/angular-chart.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/naviox/style/naviox.css" rel="stylesheet" type="text/css">

<script type='text/javascript' src='<%=request.getContextPath()%>/xava/js/dwr-engine.js?ox=<%=oxVersion%>'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/Modules.js?ox=<%=oxVersion%>'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/Folders.js?ox=<%=oxVersion%>'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/angular/shared/angular.min.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/angular/shared/angular-route.min.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/Chart.min.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/angular/shared/angular-chart.min.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/angular/shared/smart-table.min.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/angular/shared/angular-sanitize.min.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/angular/shared/angular-translate.min.js'></script>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no">
</head>

<body <%=NaviOXStyle.getBodyClass(request)%>>

	<div id="main_navigation">
		<jsp:include page="mainNavigation.jsp" />
	</div>
	<table width="100%">
		<tr>

			<td id="modules_list">
				<div id="modules_list_popup">
					<img id="modules_list_corner" src="<%=request.getContextPath()%>/naviox/images/corner.png" />
					<div id="modules_list_outbox">
						<table id="modules_list_box">
							<tr id="modules_list_content">
								<td><jsp:include page="modulesMenu.jsp" /></td>
							</tr>
						</table>
					</div>
				</div>
			</td>

			<td valign="top">
				<%
				    if (!"SignIn".equals(module)) {
				%> <jsp:include page="menus.jsp" /> <%
     }
 %>
			</td>

			<td valign="top">
				<div class="module-wrapper">
					<%
					    if ("SignIn".equals(module)) {
					%>
					<jsp:include page='signIn.jsp' />
					<%
					    } else {
					%>
					<!-- Conteúdo dos módulos vai aqui!!! -->
					<div id="module_description">
						<%=modules.getCurrentModuleDescription(request)%>
						<a href="javascript:naviox.bookmark()" title="<xava:message key='<%=modules.isCurrentBookmarked()
						? "unbookmark_module"
						: "bookmark_module"%>'/>"> <img id="bookmark" src="<%=request.getContextPath()%>/naviox/images/bookmark-<%=modules.isCurrentBookmarked() ? "on" : "off"%>.png" />
						</a>
					</div>
					<jsp:include page='<%="../xava/module.jsp?application=" + app + "&module="
						+ module + "&htmlHead=false"%>' />
					<%
					    }
					%>
				</div>
			</td>
		</tr>
	</table>

	<link rel='stylesheet' href='<%=request.getContextPath()%>/naviox/style/navs.css' />
	<link rel='stylesheet' href='<%=request.getContextPath()%>/naviox/style/dialogs.min.css' />
	<link rel='stylesheet' href='<%=request.getContextPath()%>/naviox/js/themes/default/style.min.css' />
	<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/typewatch.js'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/naviox.js'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/jstree.min.js'></script>

	<script>
		$(function() {
			naviox.init();

			$('#menu_tree').jstree({
				"core" : {
					"themes" : {
						"dots" : false
					}
				},
				"plugins" : [ "state", "types" ]
			});

			var partesUrl = window.location.href.split("/");
			var moduloAtual = partesUrl[partesUrl.length - 1];

			if (moduloAtual == 'painel.jsp')
				$('#menu_tree').jstree('select_node', 'PainelControle');
			else
				$('#menu_tree').jstree('select_node', '#' + moduloAtual);

			$('#menu_tree').on("changed.jstree", function(e, data) {
				var href = data.node.a_attr.href;
				window.location = href;
			});
		});
	</script>
</body>
</html>
