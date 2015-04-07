<br/>
<div id="menu_tree" style="
position: relative;
-moz-border-radius: 7px;
-webkit-border-radius: 7px;
border-radius: 7px;
">
<%-- 
	Necessário informar o id do elemento li com o mesmo valor 
	do nome do módulo na URL (após o /m/) para funcionar o javascript 
	de reselecionar o módulo no jstree 
--%>

	<ul>
		<li class="jstree-close">Cadastros
			<ul>
				<li class="jstree-close">Organização
					<ul>
						<li id="PapelRecursoHumano"><a href="<%=request.getContextPath()%>/m/PapelRecursoHumano"> Papéis dos Recursos Humanos</a></li>
						<li id="RecursoHumano"><a href="<%=request.getContextPath()%>/m/RecursoHumano"> Recursos Humanos</a></li>
						<li id="Projeto"><a href="<%=request.getContextPath()%>/m/Projeto"> Projeto</a></li>
						<li id="Equipe"><a href="<%=request.getContextPath()%>/m/Equipe"> Equipe</a></li>
						<li id="ObjetivoEstrategico"><a href="<%=request.getContextPath()%>/m/ObjetivoEstrategico"> Objetivo Estratégico</a></li>
						<li id="ObjetivoDeSoftware"><a href="<%=request.getContextPath()%>/m/ObjetivoDeSoftware"> Objetivo de Software</a></li>
						<li id="ObjetivoDeMedicao"><a href="<%=request.getContextPath()%>/m/ObjetivoDeMedicao"> Objetivo de Medição</a></li>						
					</ul>
				</li>
				<li class="jstree-close">Processos
					<ul>
						<li id="ProcessoPadrao"><a href="<%=request.getContextPath()%>/m/ProcessoPadrao"> Processo Padrão</a></li>
						<li id="AtividadePadrao"><a href="<%=request.getContextPath()%>/m/AtividadePadrao"> Atividade Padrão</a></li>
						<li id="ProcessoInstanciado"><a href="<%=request.getContextPath()%>/m/ProcessoInstanciado"> Processo Instanciado</a></li>
						<li id="AtividadeInstanciada"><a href="<%=request.getContextPath()%>/m/AtividadeInstanciada"> Atividade Instanciada</a></li>
						<li id="ProcessoDeProjeto"><a href="<%=request.getContextPath()%>/m/ProcessoDeProjeto"> Processo de Projeto</a></li>
						<li id="AtividadeDeProjeto"><a href="<%=request.getContextPath()%>/m/AtividadeDeProjeto"> Atividade de Projeto</a></li>
						<li id="TipoDeArtefato"><a href="<%=request.getContextPath()%>/m/TipoDeArtefato"> Tipo de Artefato</a></li>
						<li id="Artefato"><a href="<%=request.getContextPath()%>/m/Artefato"> Artefato</a></li>					
						<li id="Procedimento"><a href="<%=request.getContextPath()%>/m/Procedimento"> Procedimento</a></li>	
					</ul>					
				</li>
				<li class="jstree-close">Medição
					<ul>												
						<li id="TipoDeEntidadeMensuravel"><a href="<%=request.getContextPath()%>/m/TipoDeEntidadeMensuravel"> Tipo de Entidade Mensurável</a></li>
						<li id="ElementoMensuravel"><a href="<%=request.getContextPath()%>/m/ElementoMensuravel"> Elemento Mensurável</a></li>						
						<li id="TipoElementoMensuravel"><a href="<%=request.getContextPath()%>/m/TipoElementoMensuravel"> Tipo de Elemento Mensurável</a></li>	
						<li id="Escala"><a href="<%=request.getContextPath()%>/m/Escala"> Escala</a></li>
						<li id="TipoEscala"><a href="<%=request.getContextPath()%>/m/TipoEscala"> Tipo de Escala</a></li>
						<li id="ValorDeEscala"><a href="<%=request.getContextPath()%>/m/ValorDeEscala"> Valores de Escala</a></li>						
						<li id="NecessidadeDeInformacao"><a href="<%=request.getContextPath()%>/m/NecessidadeDeInformacao"> Necessidade de Informação</a></li>
						<li id="Medida"><a href="<%=request.getContextPath()%>/m/Medida"> Medida</a></li>
						<li id="TipoMedida"><a href="<%=request.getContextPath()%>/m/TipoMedida"> Tipo de Medida</a></li>
						<li id="DefinicaoOperacionalDeMedida"><a href="<%=request.getContextPath()%>/m/DefinicaoOperacionalDeMedida"> Definição Operacional de Medida</a></li>
						<li id="UnidadeDeMedida"><a href="<%=request.getContextPath()%>/m/UnidadeDeMedida"> Unidade de Medida</a></li>
						<li id="ProcedimentoDeMedicao"><a href="<%=request.getContextPath()%>/m/ProcedimentoDeMedicao"> Procedimento de Medição</a></li>
						<li id="ProcedimentoDeAnaliseDeMedicao"><a href="<%=request.getContextPath()%>/m/ProcedimentoDeAnaliseDeMedicao"> Procedimento de Análise</a></li>
						<li id="MetodoAnalitico"><a href="<%=request.getContextPath()%>/m/MetodoAnalitico"> Método Análitico</a></li>
					</ul> 
				</li>				
			</ul>		
		</li>		
		<li class="jstree-close">Planejar a Medição
			<ul>
				<li id="PlanoDeMedicaoDaOrganizacao"><a href="<%=request.getContextPath()%>/m/PlanoDeMedicaoDaOrganizacao"> Elaborar Plano de Medição da Organização</a></li>
				<li id="PlanoDeMedicaoDoProjeto"><a href="<%=request.getContextPath()%>/m/PlanoDeMedicaoDoProjeto"> Elaborar Plano de Medição do Projeto</a></li>			
			</ul>	
		</li>
		<li class="jstree-close">Executar Medições
			<ul>
				<li id="Medicao"><a href="<%=request.getContextPath()%>/m/Medicao"> Registrar Medição</a></li>
			</ul>
		</li>
			<li class="jstree-close">Analisar Medições
			<ul>
				<li id="AnaliseDeMedicao"><a href="<%=request.getContextPath()%>/m/AnaliseDeMedicao">Realizar Análise Tradicional de Medições</a></li>
			</ul>
			<ul>
				<li class="jstree-close">Analisar Processos
					<ul>
						<li id="DesempenhoDeProcessoEspecificado"><a href="<%=request.getContextPath()%>/m/DesempenhoDeProcessoEspecificado"> Registrar Desempenho Especificado para Processo</a></li>
						<li id="AnaliseDeComportamentoDeProcesso"><a href="<%=request.getContextPath()%>/m/AnaliseDeComportamentoDeProcesso"> Analisar Comportamento de Processo</a></li>
						<li id="BaselineDeDesempenhoDeProcesso"><a href="<%=request.getContextPath()%>/m/BaselineDeDesempenhoDeProcesso"> Modelo de Desempenho de Processo</a></li>
					</ul>
				</li>	
			</ul>
		</li>
	</ul>
</div>