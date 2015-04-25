
<div id="painel2" class="container" ng-controller=AgendamentosController>
	<table st-table="users" class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Age</th>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="user in users">
				<td>{{user.name}}</td>
				<td>{{user.age}}</td>
				<td>
					<button type="button" ng-click="removeItem(row)" class="btn btn-sm btn-danger">
						<i class="glyphicon glyphicon-remove-circle"> asdfasdf</i>
					</button>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<link rel='stylesheet' href='<%=request.getContextPath()%>/naviox/style/painel_agendamentos.css' />
