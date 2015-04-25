
<div id="painel2" class="container" ng-controller=AgendamentosController>
	<table st-table="tabela-agendamentos" class="table table-striped">
		<thead>
			<tr>
				<th>Agendamento</th>
				<th>Última Execução</th>
				<th>Próxima Execução</th>
				<th>Situação</th>
				<th>Controles</th>
			</tr>
		</thead>
		<tbody>
			<tr ng-class="{ 'success' : agendamento.estado_agendamento == 'INICIADO',
							'active'  : agendamento.estado_agendamento == 'PAUSADO'}" ng-repeat="agendamento in agendamentos">
				<td>
					<div class="row">
						<p>
							<b>{{agendamento.nome_agendamento}} </b> ({{agendamento.grupo_agendamento}})
						</p>
						<p>
							<b>Job:</b> {{agendamento.nome_job}}
						</p>
					</div>
				</td>
				<td>{{agendamento.execucao_anterior | date:'dd/MM/yyyy HH:mm:ss'}}</td>
				<td>{{agendamento.proxima_execucao | date:'dd/MM/yyyy HH:mm:ss'}}</td>
				<td><b>{{agendamento.estado_agendamento}}</b></td>
				<td>
					<button type="button" ng-disabled="agendamento.estado_agendamento == 'INICIADO' 
													|| agendamento.estado_agendamento == 'EM ESPERA'" ng-click="iniciarAgendamento(agendamento)" class="btn btn-sm btn-success">
						<i class="glyphicon glyphicon-play"></i>
					</button>
					<button type="button" ng-disabled="agendamento.estado_agendamento == 'PAUSADO'" ng-click="pausarAgendamento(agendamento)" class="btn btn-sm btn-primary">
						<i class="glyphicon glyphicon-pause"></i>
					</button>
					<button type="button" ng-click="excluirAgendamento(agendamento)" class="btn btn-sm btn-danger">
						<i class="glyphicon glyphicon-remove"></i>
					</button>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<link rel='stylesheet' href='<%=request.getContextPath()%>/naviox/style/painel_agendamentos.css' />
