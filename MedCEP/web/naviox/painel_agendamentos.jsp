
<div id="painel2" class="container" ng-controller=AgendamentosController>

	<div class="alert alert-success alert-dismissible" role="alert" ng-show="exibirMensagem">
		<button type="button" class="close" aria-label="Close" ng-click="ocultarMensagem()">
			<span aria-hidden="true">&times;</span>
		</button>
		<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> {{mensagem}}
	</div>

	<div>
		<button type="button" class="btn btn-sm btn-success" ng-click="obterAgendamentos()">
			<i class="glyphicon glyphicon-refresh"></i> Atualizar
		</button>
	</div>

	<table st-table="tabelaAgendamentos" st-safe-src="agendamentos" class="table table-striped" >
		<thead popover="Clique no nome da coluna para ordenar!" popover-placement="top" popover-trigger="mouseenter">
			<tr>
				<th st-sort="nome_agendamento">Nome</th>
				<th st-sort="execucao_anterior">Última Execução</th>
				<th st-sort="proxima_execucao">Próx. Execução</th>
				<th st-sort="estado_agendamento">Situação</th>
				<th>Controles</th>
			</tr>
			<th colspan="5"><input st-search placeholder="Buscar..." class="input-md form-control" type="search" /></th>
		</thead>
		<tbody>
			<tr class="agendamento-row" ng-repeat="agendamento in tabelaAgendamentos">
				<td>
					<div class="row">
						<p>
							<b>{{agendamento.nome_agendamento}} </b>
						</p>
						<p>
							<b>Grupo:</b> {{agendamento.grupo_agendamento}}
						</p>
						<p>
							<b>Job:</b> {{agendamento.nome_job}}
						</p>
					</div>
				</td>
				<td style="min-width: 170px;">{{agendamento.execucao_anterior | date:'dd/MM/yyyy HH:mm:ss'}}</td>
				<td style="min-width: 160px;">{{agendamento.proxima_execucao | date:'dd/MM/yyyy HH:mm:ss'}}</td>
				<td style="min-width: 100px;">{{agendamento.estado_agendamento}}</td>
				<td style="min-width: 170px;">
					<button type="button" popover="Executar o Job agora!" popover-placement="bottom" popover-trigger="mouseenter" ng-disabled=" agendamento.estado_agendamento == 'PAUSADO'" ng-click="executarAgendamento(agendamento)" class="btn btn-sm btn-warning">
						<i class="glyphicon glyphicon-cog"></i>
					</button>
					<button type="button" popover="Iniciar o Job" popover-placement="bottom" popover-trigger="mouseenter" ng-disabled="
													agendamento.estado_agendamento == 'EXECUTANDO' 
													|| agendamento.estado_agendamento == 'ATIVO'" ng-click="iniciarAgendamento(agendamento)" class="btn btn-sm btn-success">
						<i class="glyphicon glyphicon-play"></i>
					</button>
					<button type="button" popover="Pausar o Job" popover-placement="bottom" popover-trigger="mouseenter" ng-disabled="agendamento.estado_agendamento == 'PAUSADO'" ng-click="pausarAgendamento(agendamento)" class="btn btn-sm btn-primary">
						<i class="glyphicon glyphicon-pause"></i>
					</button>
					<button type="button" popover="Excluir o Job" popover-placement="bottom" popover-trigger="mouseenter" ng-click="confirmarExclusao(agendamento)" class="btn btn-sm btn-danger">
						<i class="glyphicon glyphicon-remove"></i>
					</button>
				</td>
			</tr>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="5" class="text-center">
					<div st-pagination="" st-items-by-page="10" st-displayed-pages="7"></div>
				</td>
			</tr>
		</tfoot>
	</table>
</div>

<link rel='stylesheet' href='<%=request.getContextPath()%>/naviox/style/painel_agendamentos.css' />
