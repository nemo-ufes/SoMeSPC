
<div id="painel2" class="container" ng-controller=AgendamentosController>
	<table st-table="users" class="table table-striped">
		<thead>
			<tr>
				<th>Agendador</th>
				<th>Nome do Agendamento</th>
				<th>Grupo do Agendamento</th>
				<th>Nome do Job</th>
				<th>Estado do Agendamento</th>
				<th>Controles</th>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="agendamento in agendamentos">
				<td>{{agendamento.nome_agendador}}</td>
				<td>{{agendamento.nome_agendamento}}</td>
				<td>{{agendamento.grupo_agendamento}}</td>
				<td>{{agendamento.nome_job}}</td>
				<td>{{agendamento.estado_agendamento}}</td>
				<td>
					<button type="button" ng-click="iniciarAgendamento(agendamento)" class="btn btn-sm btn-success">
						<i class="glyphicon glyphicon-play"></i>
					</button>
					<button type="button" ng-click="pausarAgendamento(agendamento)" class="btn btn-sm btn-primary">
						<i class="glyphicon glyphicon-pause"></i>
					</button>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<link rel='stylesheet' href='<%=request.getContextPath()%>/naviox/style/painel_agendamentos.css' />
