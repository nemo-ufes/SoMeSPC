
<div id="painel3">

	<div class="panel panel-primary" style="margin-top: 20px;">

		<div class="panel-heading">
			<h1 class="panel-title text-center">
				<b>Instruções</b>				
			</h1>
		</div>

		<div class="row" style="margin: 20px;">
		
			<div class="col-md-6" style="text-align: justify;">
				<label id="jobs">Agendamento de Medições</label>
				</br>
				<p>
					Na aba <i>Agendamento de Medições</i> são exibidos os <i>jobs de medição</i> criados automaticamente a partir dos Planos de Medição definidos. 
					<i>Um job de medição</i> é o agendamento de uma medição automática que coleta dados do Taiga ou do SonarQube. 
					Os <i>jobs</i> são executados automaticamente de acordo com as medidas definidas no Plano de Medição e a periodicidade de medição para elas estabelecida.
				</p>	
				
				<p>Para cada <i>job</i> são exibidas as seguintes informações: Nome, Última Execução, Próxima Execução e Situação (Executando, Ativo, Pausado, Bloqueado, Completo e Erro).</p>

				<p>Selecionando-se os botões disponíveis na coluna <i>Controles</i> é possível executar, iniciar, pausar e excluir <i>jobs</i>.</p>
			</div>
			<div class="col-md-6" style="text-align: justify;">
				<label id="medição">Análise de Medições</label>
				</br>
				<p> 
					Na aba <i>Análise de Medições</i> é possível visualizar medições realizadas. 
					Para selecionar os dados a serem apresentados, deve-se selecionar o objetivo de medição e a medida a serem considerados (campo <i>Selecione Objetivo de Medição e Medida</i>), 
					as entidades mensuráveis (campo <i>Selecione a Entidade Mensurável*</i>), as datas de início e fim das medições (campos <i>Data Início e Data Fim</i>) 
					ou a quantidade de medições que se deseja visualizar (serão apresentadas as <i>n</i> últimas medições realizadas, sendo <i>n</i> a quantidade de medições selecionada**). 
				</p>
				<p>
					Após a seleção, deve-se clicar no botão <i>Atualizar</i> para que o gráfico contendo os dados selecionados seja apresentado.
				</p>
				<p>
					*As entidades mensuráveis são apresentadas em ordem alfabética, agrupadas por tipo de entidade.
				</p>
				<p>
					** Para visualizar mais medições do que as exibidas no gráfico pode-se navegar no componente de paginação exibido abaixo do gráfico.
				</p>
			</div>
		</div>

	</div>

</div>

<link rel='stylesheet' href='<%=request.getContextPath()%>/naviox/style/painel_medicoes.css' />