<!doctype html>
<html lang="us">
<head>
<meta charset="utf-8">
<link href="style/jquery-ui.css" rel="stylesheet">
<link href="style/normalize.css" rel="stylesheet">
<link href="style/main.css" rel="stylesheet">
<link href="style/jquery.steps.css" rel="stylesheet">
<title>Wizard - MedCEP</title>
</head>
<body>

	<div>
		<div id="medcep-wizard" style="height: 400px; width: 600px;">
			<h2>Keyboard</h2>
			<section>
				<button id="botao">A button element</button>
			</section>
			<h2>Effects</h2>
			<section>
				<p>Pagina 2</p>
			</section>
			<h2>Pager</h2>
			<section>
				<p>Pagina 3</p>
			</section>
		</div>
	</div>

	<script type='text/javascript' src="js/jquery.js"></script>
	<script type='text/javascript' src="js/jquery-ui.js"></script>
	<script type='text/javascript' src='js/jquery.steps.js'></script>
	
	<script>
		$("#medcep-wizard").steps({
			headerTag : "h2",
			bodyTag : "section",
			transitionEffect : "slideLeft",
			autoFocus : true
		});
	</script>

</body>
</html>
