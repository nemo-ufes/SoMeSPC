
<div id="painel3">

	<div class="panel panel-primary" style="margin-top: 20px;">

		<div class="panel-heading">
			<h1 class="panel-title text-center">
				<b>Instruções</b>
			</h1>
		</div>

		<div class="row" style="margin: 20px;">
		
			<div class="col-md-6" style="text-align: justify;">
				<label id="jobs">O que são jobs de medição?</label>
				</br>
				<p> O job de medição é um agendamento de execução de uma medição para determinada ferramenta, Taiga ou SonarQube.</p>
				<p> As informações que ele possui são: <i>Nome, Ultima Execução, Próx. Execução</i> e <i>Situação (Executando, Ativo, Pausado, Bloqueado, Completo e Erro)</i>. </p>
				<p> Os jobs são executados de acordo com a periodicidade selecionado durante a criação do plano de medição.</p>
				<p> Através da aba <b>Agendamento de Medições</b> é possível executar, iniciar, pausar e excluir o Job escolhido.</p>
			</div>
			<div class="col-md-6">
				<label id="medição">Como visualizar as medições?</label>
				</br>
				<p> As medições são exibidas através de um gráfico de linhas. Para visualizar as medições, escolha a aba <b>Análise de Medições</b> e siga os seguintes passos: </p>
				<p>
					<ul>
						<li>Selecione a(s) entidade(s) mensurável(is). O nome da entidade segue o padrão: Nome da Entidade Mensurável (Nome do Projeto) [Tipo de Entidade Mensurável];</li>
						<br/>
						<li>Selecione a medida (são listadas apenas as medidas que possuem medições para a(s) entidade(s) selecionada(s));</li>
						<br/>
						<li>Selecione a data de início e data de fim das medições (usando o botão de calendário);</li>
						<br/>
						<li>Selecione a quantidade de medições;</li>
						<br/>
						<li>O gráfico para análise das medições será exibido abaixo.</li>					
					</ul>	  
				</p>
			</div>
		</div>

	</div>

</div>

<link rel='stylesheet' href='<%=request.getContextPath()%>/naviox/style/painel_medicoes.css' />