/*
    MedCEP - A powerful tool for measure
    
    Copyright (C) 2013 Ciro Xavier Maretto
    Copyright (C) 2015 Henrique Néspoli Castro, Vinícius Soares Fonseca                          

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/lgpl.html>.    
--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.1
-- Dumped by pg_dump version 9.4.1
-- Started on 2015-04-07 02:39:34

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

--
-- TOC entry 2812 (class 0 OID 176101)
-- Dependencies: 196
-- Data for Name: equipe; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO equipe VALUES ('4028b8814c91e35c014c91eb1c570000', 'asdf');


--
-- TOC entry 2829 (class 0 OID 176234)
-- Dependencies: 213
-- Data for Name: papelrecursohumano; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO papelrecursohumano VALUES ('4028b8814c91c896014c91c9a7420000', 'Responsável pela aplicação das regras do Scrum.', 'Scrum Master');
INSERT INTO papelrecursohumano VALUES ('4028b8814c91c896014c91ca10f10001', 'Dono do produto. Responsável por definir e priorizar os itens do Product Backlog.', 'Product Owner');
INSERT INTO papelrecursohumano VALUES ('4028b8814c91c896014c91cb2b8d0002', 'Responsável pelo design das telas dos sistemas.', 'Design');
INSERT INTO papelrecursohumano VALUES ('4028b8814c91c896014c91cbaf560003', 'Responsável pelo front end dos sistemas.', 'Front');
INSERT INTO papelrecursohumano VALUES ('4028b8814c91c896014c91cbd2970004', 'Responsável pelo back end dos sistemas.', 'Back');
INSERT INTO papelrecursohumano VALUES ('4028b8814c91c896014c91cc649b0005', 'Interessado no projeto do software.', 'Stakeholder');
INSERT INTO papelrecursohumano VALUES ('4028b8814c91c896014c91ccc4050006', 'Responsável pelas atividades de qualidade.', 'Quality');
INSERT INTO papelrecursohumano VALUES ('4028b8814c91c896014c91cceecd0007', 'Responsável pela documentação.', 'Doc');


--
-- TOC entry 2844 (class 0 OID 176344)
-- Dependencies: 228
-- Data for Name: recursohumano; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO recursohumano VALUES ('4028b8814c91c896014c91cd4a7c0008', 'bsampaio');
INSERT INTO recursohumano VALUES ('4028b8814c91c896014c91cd73470009', 'lagasperazzo');
INSERT INTO recursohumano VALUES ('4028b8814c91c896014c91cd9171000a', 'ana christina');
INSERT INTO recursohumano VALUES ('4028b8814c91c896014c91cdb5d2000b', 'Andre Leao');
INSERT INTO recursohumano VALUES ('4028b8814c91c896014c91cde171000c', 'André Silva Aguiar');
INSERT INTO recursohumano VALUES ('4028b8814c91c896014c91ce0796000d', 'Ivana Amorim Julião');
INSERT INTO recursohumano VALUES ('4028b8814c91c896014c91ce237e000e', 'Jennifer Duarte');
INSERT INTO recursohumano VALUES ('4028b8814c91c896014c91ce4907000f', 'Marcos Dias');
INSERT INTO recursohumano VALUES ('4028b8814c91c896014c91ce606f0010', 'Paulo');
INSERT INTO recursohumano VALUES ('4028b8814c91c896014c91ce78280011', 'Phillipe Lopes');


--
-- TOC entry 2788 (class 0 OID 175919)
-- Dependencies: 172
-- Data for Name: alocacaoequipe; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2846 (class 0 OID 176359)
-- Dependencies: 230
-- Data for Name: tipodeentidademensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipodeentidademensuravel VALUES ('4028b8814c922079014c9223535b0000', 'Projeto', 'Projeto');
INSERT INTO tipodeentidademensuravel VALUES ('4028b8814c919202014c91b714e10009', 'Ocorrência de uma determinada atividade de um projeto. Ex: Acontecimento da Reunião de Planejamento da Sprint 3 do projeto Sincap.', 'Ocorrência de Atividade');
INSERT INTO tipodeentidademensuravel VALUES ('4028b8814c919202014c91b307d40008', 'Atividade Padrão customizada para um determinado projeto. Ex: Reunião de Planejamento da Sprint 3 do projeto Sincap.', 'Atividade de Projeto');
INSERT INTO tipodeentidademensuravel VALUES ('4028b8814c919202014c9194212b0000', 'Processo de Software Padrão da organização. Ex: Scrum + XP. RUP.', 'Processo de Software Padrão');
INSERT INTO tipodeentidademensuravel VALUES ('4028b8814c9228e8014c922d8bfb0000', 'Item criado/usado/alterado durante um processo. Pode ser um documento, modelo, código fonte, etc.', 'Artefato');
INSERT INTO tipodeentidademensuravel VALUES ('4028b8814c9228e8014c922eb3b20001', 'Define o tipo de artefato. Pode ser do tipo Documento, Modelo, Código Fonte, etc.', 'Tipo de Artefato');
INSERT INTO tipodeentidademensuravel VALUES ('4028b8814c92371c014c9248b8260005', 'Ocorrência de atividade.', 'Atividade Instanciada');
INSERT INTO tipodeentidademensuravel VALUES ('4028b8814c919202014c91c0b5bb000c', 'Ocorrência de um determinado Processo de Software em um Projeto. Ex: Execução do processo de testes de software projeto Sincap no Leds.', 'Ocorrência de Processo de Software');
INSERT INTO tipodeentidademensuravel VALUES ('4028b8814c919202014c91bb19f4000a', 'Processo de Software executado nos Projetos da organização. Ex: Gerência de Requisitos, Desenvolvimento, Testes, Gerência de Configuração, Bugtracking, etc.', 'Processo de Software em Projeto');
INSERT INTO tipodeentidademensuravel VALUES ('4028b8814c81ea3c014c820b344d0001', 'Atividade do Processo de software Padrão.', 'Atividade Padrão');


--
-- TOC entry 2811 (class 0 OID 176091)
-- Dependencies: 195
-- Data for Name: entidademensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2798 (class 0 OID 175988)
-- Dependencies: 182
-- Data for Name: atividadepadrao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2793 (class 0 OID 175956)
-- Dependencies: 177
-- Data for Name: atividadeinstanciada; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2792 (class 0 OID 175951)
-- Dependencies: 176
-- Data for Name: atividadedeprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2847 (class 0 OID 176369)
-- Dependencies: 231
-- Data for Name: tipoelementomensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipoelementomensuravel VALUES ('1', 'Elemento Diretamente Mensurável');
INSERT INTO tipoelementomensuravel VALUES ('2', 'Elemento Indiretamente Mensurável');


--
-- TOC entry 2810 (class 0 OID 176081)
-- Dependencies: 194
-- Data for Name: elementomensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO elementomensuravel VALUES ('4028b8814c81ea3c014c820afe9c0000', 'Diferença entre o início e o fim de alguma atividade/tarefa/etc.', 'Duração', '1');
INSERT INTO elementomensuravel VALUES ('4028b8814c8274ee014c827aea970001', 'Tamanho', 'Tamanho', '1');
INSERT INTO elementomensuravel VALUES ('4028b8814c852268014c857551940001', 'Complexidade', 'Complexidade', '2');
INSERT INTO elementomensuravel VALUES ('4028b8814c852268014c85764de80003', 'Estrutura', 'Estrutura', '1');
INSERT INTO elementomensuravel VALUES ('4028b8814c852268014c85759ab70002', 'Documentação', 'Documentação', '2');
INSERT INTO elementomensuravel VALUES ('4028b8814c9143be014c917881770001', 'Taxa de Complexidade das Classes, que é medida pela razão entre a complexidade total pela quantidade de classes.', 'Taxa de Complexidade das Classes', '2');
INSERT INTO elementomensuravel VALUES ('4028b8814c919202014c919afaf90003', 'Geralmente é uma razão entre a quantidade de produção por alguma unidade. Ex: Quantidade de peças fabricadas por hora. Quantidade de pontos por Sprint.', 'Produtividade', '2');
INSERT INTO elementomensuravel VALUES ('4028b8814c919202014c91a1fd3d0007', 'Artefatos produzidos por uma atividade/tarefa/processo. Ex: A atividade de planejamento da sprint produz itens da Sprint Backlog.', 'Artefatos Produzidos', '1');
INSERT INTO elementomensuravel VALUES ('4028b8814c919202014c91a1920f0006', 'Artefatos consumidos por uma atividade/tarefa/processo. Ex: A atividade de planejamento da sprint faz o consumo de itens do Product Backlog.', 'Artefatos Consumidos', '1');
INSERT INTO elementomensuravel VALUES ('4028b8814c919202014c919d1d0f0004', 'Habilidade para atingir um determinado desempenho. Ex: Quantidade de pontos por Sprint produzidos por um programador.', 'Capacidade', '1');
INSERT INTO elementomensuravel VALUES ('4028b8814c919202014c91c412e7000d', 'Recursos envolvidos. Pode ser um recurso humano, material, financeiro, etc.', 'Recurso', '1');


--
-- TOC entry 2848 (class 0 OID 176379)
-- Dependencies: 232
-- Data for Name: tipoescala; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipoescala VALUES ('4028b8814c8214f4014c821754160001', 'Intervalo');
INSERT INTO tipoescala VALUES ('4028b8814c8214f4014c8217925e0003', 'Ordinal');
INSERT INTO tipoescala VALUES ('4028b8814c85a7d1014c85b088db0000', 'Racional');


--
-- TOC entry 2813 (class 0 OID 176111)
-- Dependencies: 197
-- Data for Name: escala; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO escala VALUES ('4028b8814c823828014c824b01360037', 'Escala dos Números Naturais', 'Escala dos Números Naturais', '4028b8814c8214f4014c821754160001');
INSERT INTO escala VALUES ('4028b8814c823828014c8247beb10036', 'Dias do mês.', 'Diária', '4028b8814c8214f4014c821754160001');
INSERT INTO escala VALUES ('4028b8814c823828014c824b62f80038', 'Escala dos Números Reais', 'Escala dos Números Reais', '4028b8814c85a7d1014c85b088db0000');
INSERT INTO escala VALUES ('4028b8814c823828014c82411b4f000e', 'Meses do ano do calendário gregoriano', 'Mensal', '4028b8814c8214f4014c821754160001');
INSERT INTO escala VALUES ('4028b8814c852268014c8577a2ae0004', 'Percentual', 'Percentual', '4028b8814c8214f4014c821754160001');
INSERT INTO escala VALUES ('4028b8814c823828014c8242c2aa000f', 'Dias da semana do calendário gregoriano.', 'Semanal', '4028b8814c8214f4014c821754160001');


--
-- TOC entry 2849 (class 0 OID 176389)
-- Dependencies: 233
-- Data for Name: tipomedida; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipomedida VALUES ('4028b8814c84ef28014c84f337850000', 'Medida Base');
INSERT INTO tipomedida VALUES ('4028b8814c84ef28014c84f3541b0001', 'Medida Derivada');


--
-- TOC entry 2852 (class 0 OID 176417)
-- Dependencies: 236
-- Data for Name: treeitemplanomedicaobase; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO treeitemplanomedicaobase VALUES ('4028b8814c85e10e014c85e42e0a0000', 'Total de Linhas');
INSERT INTO treeitemplanomedicaobase VALUES ('4028b8814c85e10e014c85ec16de0002', 'Total de Linhas de Comentários');
INSERT INTO treeitemplanomedicaobase VALUES ('4028b8814c85e10e014c85ee9bb40003', 'Percentual de Linhas Comentadas por Total de Linhas');
INSERT INTO treeitemplanomedicaobase VALUES ('4028b8814c8a315d014c8a34e0300001', 'SonarQube - Complexidade');
INSERT INTO treeitemplanomedicaobase VALUES ('4028b8814c8a315d014c8a3601a00002', 'SonarQube - Total de Classes');
INSERT INTO treeitemplanomedicaobase VALUES ('4028b8814c9143be014c917cb2670002', 'Complexidade por Classe');
INSERT INTO treeitemplanomedicaobase VALUES ('4028b8814c91e35c014c91ed20800002', 'teste');
INSERT INTO treeitemplanomedicaobase VALUES ('4028b8814c91e35c014c91ed4f840003', 'asdfasdfasdfasdfasdfasdf');


--
-- TOC entry 2853 (class 0 OID 176425)
-- Dependencies: 237
-- Data for Name: unidadedemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO unidadedemedida VALUES ('4028b8814c85b78f014c85baf5dc0000', 'Linhas de um arquivo', 'Linhas');
INSERT INTO unidadedemedida VALUES ('4028b8814c860623014c860a03430000', 'Pontos Percentuais', 'Pontos Percentuais');
INSERT INTO unidadedemedida VALUES ('4028b8814c8a315d014c8a3284330000', 'Unidade padrão', 'Unidade');


--
-- TOC entry 2817 (class 0 OID 176147)
-- Dependencies: 201
-- Data for Name: medida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2830 (class 0 OID 176244)
-- Dependencies: 214
-- Data for Name: periodicidade; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2834 (class 0 OID 176277)
-- Dependencies: 218
-- Data for Name: procedimento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2835 (class 0 OID 176287)
-- Dependencies: 219
-- Data for Name: procedimentodeanalisedemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2836 (class 0 OID 176292)
-- Dependencies: 220
-- Data for Name: procedimentodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2808 (class 0 OID 176063)
-- Dependencies: 192
-- Data for Name: definicaooperacionaldemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2831 (class 0 OID 176254)
-- Dependencies: 215
-- Data for Name: planodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2818 (class 0 OID 176157)
-- Dependencies: 202
-- Data for Name: medidaplanodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2790 (class 0 OID 175935)
-- Dependencies: 174
-- Data for Name: analisedemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2804 (class 0 OID 176029)
-- Dependencies: 188
-- Data for Name: contextodebaselinededesempenhodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2841 (class 0 OID 176325)
-- Dependencies: 225
-- Data for Name: processopadrao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2802 (class 0 OID 176011)
-- Dependencies: 186
-- Data for Name: baselinededesempenhodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2809 (class 0 OID 176073)
-- Dependencies: 193
-- Data for Name: desempenhodeprocessoespecificado; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2803 (class 0 OID 176021)
-- Dependencies: 187
-- Data for Name: capacidadedeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2789 (class 0 OID 175927)
-- Dependencies: 173
-- Data for Name: analisedecomportamentodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2805 (class 0 OID 176037)
-- Dependencies: 189
-- Data for Name: contextodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2843 (class 0 OID 176339)
-- Dependencies: 227
-- Data for Name: projeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2856 (class 0 OID 176450)
-- Dependencies: 240
-- Data for Name: valormedido; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2816 (class 0 OID 176139)
-- Dependencies: 200
-- Data for Name: medicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2858 (class 0 OID 176463)
-- Dependencies: 242
-- Data for Name: analisedemedicao_medicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2845 (class 0 OID 176354)
-- Dependencies: 229
-- Data for Name: tipodeartefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2791 (class 0 OID 175943)
-- Dependencies: 175
-- Data for Name: artefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2795 (class 0 OID 175970)
-- Dependencies: 179
-- Data for Name: atividadeinstanciada_dependede_atividadeinstanciada; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2796 (class 0 OID 175976)
-- Dependencies: 180
-- Data for Name: atividadeinstanciada_produz_artefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2794 (class 0 OID 175964)
-- Dependencies: 178
-- Data for Name: atividadeinstanciada_realizadopor_recursohumano; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2797 (class 0 OID 175982)
-- Dependencies: 181
-- Data for Name: atividadeinstanciada_requer_artefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2799 (class 0 OID 175993)
-- Dependencies: 183
-- Data for Name: atividadepadrao_dependede_atividadepadrao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2800 (class 0 OID 175999)
-- Dependencies: 184
-- Data for Name: atividadepadrao_produz_tipoartefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2801 (class 0 OID 176005)
-- Dependencies: 185
-- Data for Name: atividadepadrao_requer_tipoartefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2859 (class 0 OID 176469)
-- Dependencies: 243
-- Data for Name: baselinededesempenhodeprocesso_medicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2814 (class 0 OID 176121)
-- Dependencies: 198
-- Data for Name: formuladecalculodemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2820 (class 0 OID 176170)
-- Dependencies: 204
-- Data for Name: modelodedesempenhodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2860 (class 0 OID 176475)
-- Dependencies: 244
-- Data for Name: baselinededesempenhodeprocesso_modelodedesempenhodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2806 (class 0 OID 176045)
-- Dependencies: 190
-- Data for Name: criterio; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2807 (class 0 OID 176055)
-- Dependencies: 191
-- Data for Name: criteriodeprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2822 (class 0 OID 176188)
-- Dependencies: 206
-- Data for Name: objetivo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2850 (class 0 OID 176399)
-- Dependencies: 234
-- Data for Name: tipoobjetivodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2823 (class 0 OID 176198)
-- Dependencies: 207
-- Data for Name: objetivodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2861 (class 0 OID 176481)
-- Dependencies: 245
-- Data for Name: definicaooperacionaldemedida_objetivodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2862 (class 0 OID 176487)
-- Dependencies: 246
-- Data for Name: elementomensuravel_entidademensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2863 (class 0 OID 176493)
-- Dependencies: 247
-- Data for Name: elementomensuravel_tipodeentidademensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO elementomensuravel_tipodeentidademensuravel VALUES ('4028b8814c81ea3c014c820afe9c0000', '4028b8814c81ea3c014c820b344d0001');
INSERT INTO elementomensuravel_tipodeentidademensuravel VALUES ('4028b8814c81ea3c014c820afe9c0000', '4028b8814c922079014c9223535b0000');
INSERT INTO elementomensuravel_tipodeentidademensuravel VALUES ('4028b8814c81ea3c014c820afe9c0000', '4028b8814c81ea3c014c820b344d0001');
INSERT INTO elementomensuravel_tipodeentidademensuravel VALUES ('4028b8814c919202014c91a1920f0006', '4028b8814c81ea3c014c820b344d0001');
INSERT INTO elementomensuravel_tipodeentidademensuravel VALUES ('4028b8814c919202014c91a1fd3d0007', '4028b8814c81ea3c014c820b344d0001');
INSERT INTO elementomensuravel_tipodeentidademensuravel VALUES ('4028b8814c852268014c85759ab70002', '4028b8814c9228e8014c922d8bfb0000');
INSERT INTO elementomensuravel_tipodeentidademensuravel VALUES ('4028b8814c852268014c85764de80003', '4028b8814c9228e8014c922d8bfb0000');
INSERT INTO elementomensuravel_tipodeentidademensuravel VALUES ('4028b8814c8274ee014c827aea970001', '4028b8814c9228e8014c922d8bfb0000');
INSERT INTO elementomensuravel_tipodeentidademensuravel VALUES ('4028b8814c852268014c85759ab70002', '4028b8814c9228e8014c922eb3b20001');
INSERT INTO elementomensuravel_tipodeentidademensuravel VALUES ('4028b8814c852268014c85764de80003', '4028b8814c9228e8014c922eb3b20001');
INSERT INTO elementomensuravel_tipodeentidademensuravel VALUES ('4028b8814c8274ee014c827aea970001', '4028b8814c9228e8014c922eb3b20001');
INSERT INTO elementomensuravel_tipodeentidademensuravel VALUES ('4028b8814c919202014c91a1920f0006', '4028b8814c919202014c91c0b5bb000c');
INSERT INTO elementomensuravel_tipodeentidademensuravel VALUES ('4028b8814c919202014c91a1fd3d0007', '4028b8814c919202014c91c0b5bb000c');
INSERT INTO elementomensuravel_tipodeentidademensuravel VALUES ('4028b8814c919202014c919d1d0f0004', '4028b8814c919202014c91c0b5bb000c');
INSERT INTO elementomensuravel_tipodeentidademensuravel VALUES ('4028b8814c852268014c85759ab70002', '4028b8814c919202014c91c0b5bb000c');
INSERT INTO elementomensuravel_tipodeentidademensuravel VALUES ('4028b8814c852268014c85764de80003', '4028b8814c919202014c91c0b5bb000c');
INSERT INTO elementomensuravel_tipodeentidademensuravel VALUES ('4028b8814c919202014c919afaf90003', '4028b8814c919202014c91c0b5bb000c');


--
-- TOC entry 2864 (class 0 OID 176499)
-- Dependencies: 248
-- Data for Name: equipe_projeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2865 (class 0 OID 176505)
-- Dependencies: 249
-- Data for Name: equipe_recursohumano; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2855 (class 0 OID 176440)
-- Dependencies: 239
-- Data for Name: valordeescala; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO valordeescala VALUES ('4028b8814c8214f4014c82279a740005', false, 'Alto');
INSERT INTO valordeescala VALUES ('4028b8814c8214f4014c8227b1e10006', false, 'Médio');
INSERT INTO valordeescala VALUES ('4028b8814c8214f4014c8227c6c90007', false, 'Baixo');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823d9a770002', false, 'Janeiro');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823da9860003', false, 'Fevereiro');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823e3345000d', false, 'Dezembro');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823dfbf90009', false, 'Agosto');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823dc2c90005', false, 'Abril');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823e094c000a', false, 'Setembro');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823e1a6b000b', false, 'Outubro');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823e26ab000c', false, 'Novembro');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823dd8010006', false, 'Maio');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823de4090007', false, 'Junho');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823defd50008', false, 'Julho');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823db88e0004', false, 'Março');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824329630011', false, 'Terça-feira');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824354140014', false, 'Sexta-feira');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824312650010', false, 'Segunda-feira');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824368b90015', false, 'Sábado');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824346520013', false, 'Quinta-feira');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8243396a0012', false, 'Quarta-feira');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824374320016', false, 'Domingo');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823cef990000', true, 'Números Naturais');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823d30e40001', true, 'Números Reais');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82452d34002b', true, '21');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244c61d001c', true, '6');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244d875001f', true, '9');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244d335001e', true, '8');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244ccae001d', true, '7');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244c04b001b', true, '5');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244bb97001a', true, '4');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82457db10035', true, '31');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824576810034', true, '30');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244b4130019', true, '3');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82456bf90033', true, '29');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824565c10032', true, '28');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82455ea00031', true, '27');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824557d00030', true, '26');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82454f32002f', true, '25');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82454022002e', true, '24');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82453a65002d', true, '23');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824532d3002c', true, '22');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82452800002a', true, '20');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244a21f0018', true, '2');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824521df0029', true, '19');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82451bb00028', true, '18');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8245131e0027', true, '17');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82450ce60026', true, '16');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824504690025', true, '15');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244fe4e0024', true, '14');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244f75d0023', true, '13');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244f10f0022', true, '12');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244e4340021', true, '11');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244df170020', true, '10');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82449a240017', true, '1');
INSERT INTO valordeescala VALUES ('4028b8814c852268014c8578d6a80005', true, 'Números Reais entre 0 e 100');


--
-- TOC entry 2866 (class 0 OID 176511)
-- Dependencies: 250
-- Data for Name: escala_valordeescala; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82449a240017');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244df170020');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244e4340021');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244f10f0022');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244f75d0023');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244fe4e0024');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c824504690025');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82450ce60026');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8245131e0027');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82451bb00028');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c824521df0029');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244a21f0018');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82452800002a');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82452d34002b');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c824532d3002c');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82453a65002d');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82454022002e');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82454f32002f');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c824557d00030');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82455ea00031');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c824565c10032');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82456bf90033');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244b4130019');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c824576810034');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82457db10035');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244bb97001a');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244c04b001b');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244c61d001c');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244ccae001d');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244d335001e');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244d875001f');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c824b01360037', '4028b8814c823828014c823cef990000');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c824b62f80038', '4028b8814c823828014c823d30e40001');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823dc2c90005');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823dfbf90009');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823e3345000d');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823da9860003');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823d9a770002');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823defd50008');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823de4090007');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823dd8010006');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823db88e0004');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823e26ab000c');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823e1a6b000b');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823e094c000a');
INSERT INTO escala_valordeescala VALUES ('4028b8814c852268014c8577a2ae0004', '4028b8814c852268014c8578d6a80005');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8242c2aa000f', '4028b8814c823828014c824374320016');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8242c2aa000f', '4028b8814c823828014c8243396a0012');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8242c2aa000f', '4028b8814c823828014c824346520013');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8242c2aa000f', '4028b8814c823828014c824368b90015');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8242c2aa000f', '4028b8814c823828014c824312650010');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8242c2aa000f', '4028b8814c823828014c824354140014');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8242c2aa000f', '4028b8814c823828014c824329630011');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82449a240017');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244df170020');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244e4340021');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244f10f0022');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244f75d0023');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244fe4e0024');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c824504690025');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82450ce60026');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8245131e0027');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82451bb00028');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c824521df0029');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244a21f0018');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82452800002a');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82452d34002b');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c824532d3002c');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82453a65002d');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82454022002e');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82454f32002f');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c824557d00030');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82455ea00031');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c824565c10032');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82456bf90033');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244b4130019');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c824576810034');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82457db10035');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244bb97001a');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244c04b001b');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244c61d001c');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244ccae001d');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244d335001e');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244d875001f');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c824b01360037', '4028b8814c823828014c823cef990000');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c824b62f80038', '4028b8814c823828014c823d30e40001');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823dc2c90005');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823dfbf90009');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823e3345000d');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823da9860003');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823d9a770002');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823defd50008');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823de4090007');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823dd8010006');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823db88e0004');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823e26ab000c');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823e1a6b000b');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823e094c000a');
INSERT INTO escala_valordeescala VALUES ('4028b8814c852268014c8577a2ae0004', '4028b8814c852268014c8578d6a80005');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8242c2aa000f', '4028b8814c823828014c824374320016');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8242c2aa000f', '4028b8814c823828014c8243396a0012');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8242c2aa000f', '4028b8814c823828014c824346520013');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8242c2aa000f', '4028b8814c823828014c824368b90015');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8242c2aa000f', '4028b8814c823828014c824312650010');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8242c2aa000f', '4028b8814c823828014c824354140014');
INSERT INTO escala_valordeescala VALUES ('4028b8814c823828014c8242c2aa000f', '4028b8814c823828014c824329630011');


--
-- TOC entry 2867 (class 0 OID 176517)
-- Dependencies: 251
-- Data for Name: formuladecalculodemedida_usa_medida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2815 (class 0 OID 176131)
-- Dependencies: 199
-- Data for Name: images; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2868 (class 0 OID 176523)
-- Dependencies: 252
-- Data for Name: medida_correlatas; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2869 (class 0 OID 176529)
-- Dependencies: 253
-- Data for Name: medida_derivade; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2821 (class 0 OID 176178)
-- Dependencies: 205
-- Data for Name: necessidadedeinformacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2870 (class 0 OID 176535)
-- Dependencies: 254
-- Data for Name: medida_necessidadedeinformacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2871 (class 0 OID 176541)
-- Dependencies: 255
-- Data for Name: medida_objetivo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2872 (class 0 OID 176547)
-- Dependencies: 256
-- Data for Name: medida_tipodeentidademensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2819 (class 0 OID 176165)
-- Dependencies: 203
-- Data for Name: metodoanalitico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2873 (class 0 OID 176553)
-- Dependencies: 257
-- Data for Name: objetivo_identifica_necessidade; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2874 (class 0 OID 176559)
-- Dependencies: 258
-- Data for Name: objetivo_projeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2826 (class 0 OID 176218)
-- Dependencies: 210
-- Data for Name: objetivodesoftware; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2824 (class 0 OID 176206)
-- Dependencies: 208
-- Data for Name: objetivodemedicao_baseadoem_objetivodesoftware; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2828 (class 0 OID 176229)
-- Dependencies: 212
-- Data for Name: objetivoestrategico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2825 (class 0 OID 176212)
-- Dependencies: 209
-- Data for Name: objetivodemedicao_baseadoem_objetivoestrategico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2827 (class 0 OID 176223)
-- Dependencies: 211
-- Data for Name: objetivodesoftware_baseadoem_objetivoestrategico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2875 (class 0 OID 176565)
-- Dependencies: 259
-- Data for Name: planodemedicao_necessidadedeinformacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2876 (class 0 OID 176571)
-- Dependencies: 260
-- Data for Name: planodemedicao_objetivodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2877 (class 0 OID 176577)
-- Dependencies: 261
-- Data for Name: planodemedicao_objetivodesoftware; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2878 (class 0 OID 176583)
-- Dependencies: 262
-- Data for Name: planodemedicao_objetivoestrategico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2832 (class 0 OID 176264)
-- Dependencies: 216
-- Data for Name: planodemedicaodaorganizacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2833 (class 0 OID 176269)
-- Dependencies: 217
-- Data for Name: planodemedicaodoprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2879 (class 0 OID 176589)
-- Dependencies: 263
-- Data for Name: procedimentodeanalisedemedicao_metodoanalitico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2880 (class 0 OID 176595)
-- Dependencies: 264
-- Data for Name: procedimentodemedicao_formuladecalculodemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2839 (class 0 OID 176311)
-- Dependencies: 223
-- Data for Name: processoinstanciado; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2837 (class 0 OID 176297)
-- Dependencies: 221
-- Data for Name: processodeprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2838 (class 0 OID 176305)
-- Dependencies: 222
-- Data for Name: processodeprojeto_atividadedeprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2840 (class 0 OID 176319)
-- Dependencies: 224
-- Data for Name: processoinstanciado_atividadeinstanciada; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2842 (class 0 OID 176333)
-- Dependencies: 226
-- Data for Name: processopadrao_atividadepadrao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2881 (class 0 OID 176601)
-- Dependencies: 265
-- Data for Name: recursohumano_planodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2882 (class 0 OID 176607)
-- Dependencies: 266
-- Data for Name: subelemento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2883 (class 0 OID 176613)
-- Dependencies: 267
-- Data for Name: subnecessidade; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2884 (class 0 OID 176619)
-- Dependencies: 268
-- Data for Name: subobjetivo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2851 (class 0 OID 176409)
-- Dependencies: 235
-- Data for Name: treeitemplanomedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2854 (class 0 OID 176435)
-- Dependencies: 238
-- Data for Name: valoralfanumerico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2857 (class 0 OID 176458)
-- Dependencies: 241
-- Data for Name: valornumerico; Type: TABLE DATA; Schema: public; Owner: postgres
--



-- Completed on 2015-04-07 02:39:35

--
-- PostgreSQL database dump complete
--

