
<div id="painel2" class="container" ng-controller=AgendamentosController>
	<table st-table="users" class="table table-striped">
		<thead>
			<tr>
				<th>Agendador</th>
				<th>Nome do Job</th>
				<th>Grupo do Job</th>
				<th>Controles</th>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="agendamento in agendamentos">
				<td>{{agendamento.nome_agendador}}</td>
				<td>{{agendamento.nome_agendamento}}</td>
				<td>{{agendamento.nome_job}}</td>
				<td>
					<button type="button" ng-click="" class="btn btn-sm btn-success">
						<i class="glyphicon glyphicon-play"></i>
					</button>
					<button type="button" ng-click="" class="btn btn-sm btn-danger">
						<i class="glyphicon glyphicon-stop"></i>
					</button>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<link rel='stylesheet' href='<%=request.getContextPath()%>/naviox/style/painel_agendamentos.css' />
