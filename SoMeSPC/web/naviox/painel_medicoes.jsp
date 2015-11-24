
<div id="painel" ng-controller=MedicoesController>

	<div class="panel panel-primary" style="margin-top: 20px;">

		<div class="panel-heading">
			<h1 class="panel-title text-center">
				<b>Análise de Medições</b>
			</h1>
		</div>

		<div class="row" style="margin: 20px;">
			<label for="selectMedida">Selecione Objetivo de Medição e Medida</label> 
			<select class="form-control" id="selectMedida" ng-model="itemSelecionado" 
				ng-options="item.medida group by item.objetivo for item in itensParaSelecao" ng-change="obterEntidades()">
					<option value="">-- Selecione --</option>
			</select>
		</div>

		<div class="row" style="margin: 20px;">
			<div class="col-md-6">
				<label id="teste">Selecione a Entidade Mensurável 1</label>
				<select class="form-control" id="selectPeriodicidades" ng-model="entidade.Selecionada" ng-options="e.nome group by e.nomeTipo for e in entidades" ng-change="obterMedicoes(1)">
					<option value="">-- Selecione --</option>
				</select>
				<label id="teste">Selecione a Entidade Mensurável 2</label>
				<select class="form-control" id="selectPeriodicidades2" ng-model="entidade.Selecionada2" ng-options="e.nome group by e.nomeTipo for e in entidades" ng-change="obterMedicoes(1)">
					<option value="">-- Selecione --</option>
				</select>
			</div>
			<div class="col-md-6">		
				<div class="col-md-6">					
					<label for="dataInicio">Data de Início</label>
					<p class="input-group">
              			<input id="dataInicio" type="text" style="background-color: white; min-width: 80px;" class="form-control" uib-datepicker-popup="dd/MM/yyyy" ng-model="dataInicio" 
              			is-open="statusDataInicio.opened" show-button-bar="false" ng-required="true" ng-disabled="true" ng-change="obterMedicoes(1)"  />
          				<span class="input-group-btn">
            				<button type="button" class="btn btn-default" ng-click="openDataInicio($event)"><i class="glyphicon glyphicon-calendar"></i></button>
          				</span>
            		</p>
            		<label for="selectTamanhoPagina">Quantidade</label> <select class="form-control" id="selectTamanhoPagina" ng-model="numPerPage" ng-options="numPerPage for numPerPage in [5,10,15,20,25,30]" ng-change="obterMedicoes(paginaAtual)">
					</select>
				</div>
				<div class="col-md-6">
					<label for="dataFim">Data de Fim</label>
					<p class="input-group">
              			<input id="dataFim" type="text" style="background-color: white; min-width: 80px;" class="form-control" uib-datepicker-popup="dd/MM/yyyy" ng-model="dataFim" 
              			is-open="statusDataFim.opened" show-button-bar="false" ng-required="true" ng-disabled="true" ng-change="obterMedicoes(1)" />
          				<span class="input-group-btn">
            				<button type="button" class="btn btn-default" ng-click="openDataFim($event)"><i class="glyphicon glyphicon-calendar"></i></button>
          				</span>
            		</p>
            		<div class="text-center" style="margin-top: 30px;">
					<button type="button" class="btn btn-primary btn-sm" ng-click="obterMedicoes(1)">
						<i class="glyphicon glyphicon-refresh"></i> Atualizar Gráfico
					</button>
				</div>
				</div>
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