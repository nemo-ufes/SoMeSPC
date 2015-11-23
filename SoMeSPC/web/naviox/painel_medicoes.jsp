
<div id="painel" ng-controller=MedicoesController>

	<div>
		<button type="button" class="btn btn-sm btn-success" ng-click="obterMedicoes(1)">
			<i class="glyphicon glyphicon-refresh"></i> Atualizar
		</button>
	</div>

	<div class="panel panel-primary" style="margin-top: 20px;">

		<div class="panel-heading">
			<h1 class="panel-title text-center">
				<b>An�lise de Medi��es</b>
			</h1>
		</div>

		<div class="row" style="margin: 20px;">

			<div class="col-md-5">
				<label id="teste">Selecione a Entidade Mensur�vel 1</label>
				<select class="form-control" id="selectPeriodicidades" ng-model="entidade.Selecionada" ng-options="(e.nome+' '+'['+e.nomeTipo+']') for e in entidades" ng-change="obterMedidas()">
					<option value="">-- Selecione --</option>
				</select>
				<label id="teste">Selecione a Entidade Mensur�vel 2</label>
				<select class="form-control" id="selectPeriodicidades2" ng-model="entidade.Selecionada2" ng-options="(e.nome+' '+'['+e.nomeTipo+']') for e in entidades" ng-change="obterMedidas()">
					<option value="">-- Selecione --</option>
				</select>
			</div>
			<div class="col-md-5">
							
				<label for="selectMedida">Medida</label> <select class="form-control" id="selectMedida" ng-model="itemSelecionado" 
				ng-options="item.medida.nome group by item.objetivo for item in itensParaSelecao" ng-change="obterMedicoes(1)">
				</select>
								
				<div class="col-md-6">					
					<label for="dataInicio">Data de In�cio</label>
					<p class="input-group">
              			<input id="dataInicio" type="text" style="background-color: white;" class="form-control" uib-datepicker-popup="dd/MM/yyyy" ng-model="dataInicio" 
              			is-open="statusDataInicio.opened" show-button-bar="false" ng-required="true" ng-disabled="true" ng-change="obterMedicoes(1)"  />
          				<span class="input-group-btn">
            				<button type="button" class="btn btn-default" ng-click="openDataInicio($event)"><i class="glyphicon glyphicon-calendar"></i></button>
          				</span>
            		</p>
				</div>
				<div class="col-md-6">
					<label for="dataFim">Data de Fim</label>
					<p class="input-group">
              			<input id="dataFim" type="text" style="background-color: white;" class="form-control" uib-datepicker-popup="dd/MM/yyyy" ng-model="dataFim" 
              			is-open="statusDataFim.opened" show-button-bar="false" ng-required="true" ng-disabled="true" ng-change="obterMedicoes(1)" />
          				<span class="input-group-btn">
            				<button type="button" class="btn btn-default" ng-click="openDataFim($event)"><i class="glyphicon glyphicon-calendar"></i></button>
          				</span>
            		</p>
				</div>
			</div>
			<div class="col-md-2">
				<label for="selectTamanhoPagina">Qtde Medi��es</label> <select class="form-control" id="selectTamanhoPagina" ng-model="numPerPage" ng-options="numPerPage for numPerPage in [5,10,15,20,25,30]" ng-change="obterMedicoes(paginaAtual)">
				</select>
			</div>
		</div>

		<div class="row" style="margin: 20px;">
			<div class="col-md-12">
				<canvas id="line" style="height: 500px;" class="chart chart-line" chart-data="dados" 
				chart-labels="labels" chart-legend="true" chart-series="series" chart-colours="colours" chart-click="onClick">
				</canvas>
				<div class="text-center">
					<uib-pagination boundary-links="true" total-items="totalItems" items-per-page="numPerPage" ng-model="paginaAtual" class="pagination-sm" previous-text="&lsaquo;" next-text="&rsaquo;" first-text="&laquo;" last-text="&raquo;" ng-change="obterMedicoes(paginaAtual)"> </pagination>
				</div>
			</div>
		</div>

	</div>

</div>

<link rel='stylesheet' href='<%=request.getContextPath()%>/naviox/style/painel_medicoes.css' />