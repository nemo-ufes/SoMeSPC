<html>
<head>
<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/jquery-1.11.2.min.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/jquery-ui.min.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/jquery.steps.min.js'></script>
<link rel='stylesheet' href='<%=request.getContextPath()%>/naviox/style/jquery-ui.min.css' />
<link rel='stylesheet' href='<%=request.getContextPath()%>/naviox/style/theme.css' />


</head>
<body>

<div id="example-basic">
    <h3>Keyboard</h3>
    <section>
        <p>Try the keyboard navigation by clicking arrow left or right!</p>
    </section>
    <h3>Effects</h3>
    <section>
        <p>Wonderful transition effects.</p>
    </section>
    <h3>Pager</h3>
    <section>
        <p>The next and previous buttons help you to navigate through your content.</p>
    </section>
</div>

<script>
 $("#example-basic").steps({
    headerTag: "h3",
    bodyTag: "section",
    transitionEffect: "slideLeft",
    autoFocus: true
});
</script>


</body>
</html>
