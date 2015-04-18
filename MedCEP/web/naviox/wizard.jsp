
<jsp:useBean id="wizardHelper" class="org.medcep.wizard.WizardHelper" scope="session" />


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
	<div style="margin: 40px auto; width: 800px;">
		<div id="medcep-wizard">
			<h2>Novo Projeto</h2>
				<fieldset name="testes">
					<legend style="font-size: 25px; font-weight: bold; color: blue;">Selecionar Projeto</legend>
					<input id="projeto_medCEP" name="projeto_medCEP" type="radio"><label for="projeto_medCEP">Projeto MedCEP</label>
					<p>
					
					</p>
					<input id="projeto_TAIGA" name="projeto_TAIGA" type="radio"><label for="projeto_TAIGA">Projeto TAIGA</label>	
				</fieldset>
			<h2>Login Account</h2>
			
			<fieldset>
				<label for="URL">URL da API Taiga *</label>
	            <input id="URL" name="URL" type="text" class="required"/>
	            <label for="nome">Usuário *</label>
	            <input id="nome" name="nome" type="text" class="required"/>
	            <label for="password">Password *</label>
	            <input id="password" name="password" type="text" class="required"/>
	            <button>Send an HTTP POST request to a page and get the result back</button>
			</fieldset>

			<h2>Selecione o Projeto</h2>
				<fieldset>
					<input id="acceptTerms-2" name="acceptTerms" type="checkbox" class="required"> <label for="acceptTerms-2">I agree with the Terms and Conditions.</label>
		   		</fieldset>
		</div>
	</div>

	<script type='text/javascript' src="js/jquery.js"></script>
	<script type='text/javascript' src="js/jquery-ui.js"></script>
	<script type='text/javascript' src='js/jquery.steps.js'></script>

	<script>
	
		
		$(document).ready(function() {
			$("button").click(function() {
				$.post("wizard.do", {
					name : "Donald Duck",
					city : "Duckburg"
				}, function(data, status) {
					alert("Data: " + data + "\nStatus: " + status);
				});
			});
		});
		$("#medcep-wizard").steps({
			headerTag : "h2",
			bodyTag : "fieldset",
			transitionEffect : "slideLeft",
			autoFocus : true

		});
	</script>
			

</body>
</html>
