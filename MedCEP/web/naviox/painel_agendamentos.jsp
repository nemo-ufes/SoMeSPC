
<div id="painel2" class="container" ng-controller=AgendamentosController>
	<table st-table="users" class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Age</th>
				<th>Controles</th>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="user in users">
				<td>{{user.name}}</td>
				<td>{{user.age}}</td>
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
