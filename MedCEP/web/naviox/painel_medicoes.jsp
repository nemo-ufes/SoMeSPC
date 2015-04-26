
<div id="painel" class="container" ng-controller=MedicoesController>

	<div class="panel panel-primary" style="margin-top: 20px;">

		<div class="panel-heading">
			<h1 class="panel-title text-center">
				<b>Painel de Medições</b>
			</h1>
		</div>

		<div class="row" style="margin: 20px;">
			<div class="col-md-5">
				<label for="selectProjeto">Projeto</label> <select class="form-control" id="selectProjeto" ng-model="projetoSelecionado" ng-options="projetos[projetos.indexOf(projeto)].nome  for projeto in projetos" ng-change="obterMedidas()">
				</select>
			</div>
			<div class="col-md-5">
				<label for="selectMedida">Medida</label> <select class="form-control" id="selectMedida" ng-model="medidaSelecionada" ng-options="medidas[medidas.indexOf(medida)].nome  for medida in medidas" ng-change="obterMedicoes(1)">
				</select>
			</div>
			<div class="col-md-2">
				<label for="selectTamanhoPagina">Qtde Medições</label> <select class="form-control" id="selectTamanhoPagina" ng-model="numPerPage" ng-options="numPerPage for numPerPage in [5,10,15,20,25,30]" ng-change="obterMedicoes(paginaAtual)">
				</select>
			</div>
		</div>

		<div class="row" style="margin: 20px;">
			<div class="col-md-12">
				<canvas id="line" style="height: 500px;" class="chart chart-line" data="dados" labels="labels" legend="true" series="series" colours="colours" click="onClick">
									</canvas>
				<div class="text-center">
					<pagination boundary-links="true" total-items="totalItems" items-per-page="numPerPage" ng-model="paginaAtual" class="pagination-sm" previous-text="&lsaquo;" next-text="&rsaquo;" first-text="&laquo;" last-text="&raquo;" ng-change="obterMedicoes(paginaAtual)"> </pagination>
				</div>
			</div>
		</div>

	</div>

</div>

<link rel='stylesheet' href='<%=request.getContextPath()%>/naviox/style/painel_medicoes.css' />