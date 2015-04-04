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
*/--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.1
-- Dumped by pg_dump version 9.4.1
-- Started on 2015-04-04 17:24:42

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

--
-- TOC entry 2806 (class 0 OID 170720)
-- Dependencies: 196
-- Data for Name: equipe; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2823 (class 0 OID 170853)
-- Dependencies: 213
-- Data for Name: papelrecursohumano; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2838 (class 0 OID 170963)
-- Dependencies: 228
-- Data for Name: recursohumano; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2782 (class 0 OID 170538)
-- Dependencies: 172
-- Data for Name: alocacaoequipe; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2840 (class 0 OID 170978)
-- Dependencies: 230
-- Data for Name: tipodeentidademensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipodeentidademensuravel (id, descricao, nome) VALUES ('4028b8814c81ea3c014c820b344d0001', 'Atividade comum.', 'Atividade');
INSERT INTO tipodeentidademensuravel (id, descricao, nome) VALUES ('4028b8814c8274ee014c827a509e0000', 'Código fonte de um software', 'Código fonte');


--
-- TOC entry 2805 (class 0 OID 170710)
-- Dependencies: 195
-- Data for Name: entidademensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2792 (class 0 OID 170607)
-- Dependencies: 182
-- Data for Name: atividadepadrao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2787 (class 0 OID 170575)
-- Dependencies: 177
-- Data for Name: atividadeinstanciada; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2786 (class 0 OID 170570)
-- Dependencies: 176
-- Data for Name: atividadedeprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2841 (class 0 OID 170988)
-- Dependencies: 231
-- Data for Name: tipoelementomensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipoelementomensuravel (id, nome) VALUES ('1', 'Elemento Diretamente Mensurável');
INSERT INTO tipoelementomensuravel (id, nome) VALUES ('2', 'Elemento Indiretamente Mensurável');


--
-- TOC entry 2804 (class 0 OID 170700)
-- Dependencies: 194
-- Data for Name: elementomensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO elementomensuravel (id, descricao, nome, tipoelementomensuravel_id) VALUES ('4028b8814c81ea3c014c820afe9c0000', 'Diferença entre o início e o fim de alguma atividade/tarefa/etc.', 'Duração', '1');
INSERT INTO elementomensuravel (id, descricao, nome, tipoelementomensuravel_id) VALUES ('4028b8814c8274ee014c827aea970001', 'Tamanho', 'Tamanho', '1');
INSERT INTO elementomensuravel (id, descricao, nome, tipoelementomensuravel_id) VALUES ('4028b8814c852268014c857551940001', 'Complexidade', 'Complexidade', '2');
INSERT INTO elementomensuravel (id, descricao, nome, tipoelementomensuravel_id) VALUES ('4028b8814c852268014c85764de80003', 'Estrutura', 'Estrutura', '1');
INSERT INTO elementomensuravel (id, descricao, nome, tipoelementomensuravel_id) VALUES ('4028b8814c852268014c85759ab70002', 'Documentação', 'Documentação', '2');


--
-- TOC entry 2842 (class 0 OID 170998)
-- Dependencies: 232
-- Data for Name: tipoescala; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipoescala (id, nome) VALUES ('4028b8814c8214f4014c821754160001', 'Intervalo');
INSERT INTO tipoescala (id, nome) VALUES ('4028b8814c8214f4014c8217925e0003', 'Ordinal');
INSERT INTO tipoescala (id, nome) VALUES ('4028b8814c85a7d1014c85b088db0000', 'Racional');


--
-- TOC entry 2807 (class 0 OID 170730)
-- Dependencies: 197
-- Data for Name: escala; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO escala (id, descricao, nome, tipoescala_id) VALUES ('4028b8814c823828014c824b01360037', 'Escala dos Números Naturais', 'Escala dos Números Naturais', '4028b8814c8214f4014c821754160001');
INSERT INTO escala (id, descricao, nome, tipoescala_id) VALUES ('4028b8814c823828014c8247beb10036', 'Dias do mês.', 'Diária', '4028b8814c8214f4014c821754160001');
INSERT INTO escala (id, descricao, nome, tipoescala_id) VALUES ('4028b8814c823828014c824b62f80038', 'Escala dos Números Reais', 'Escala dos Números Reais', '4028b8814c85a7d1014c85b088db0000');
INSERT INTO escala (id, descricao, nome, tipoescala_id) VALUES ('4028b8814c823828014c82411b4f000e', 'Meses do ano do calendário gregoriano', 'Mensal', '4028b8814c8214f4014c821754160001');
INSERT INTO escala (id, descricao, nome, tipoescala_id) VALUES ('4028b8814c852268014c8577a2ae0004', 'Percentual', 'Percentual', '4028b8814c8214f4014c821754160001');
INSERT INTO escala (id, descricao, nome, tipoescala_id) VALUES ('4028b8814c823828014c8242c2aa000f', 'Dias da semana do calendário gregoriano.', 'Semanal', '4028b8814c8214f4014c821754160001');


--
-- TOC entry 2843 (class 0 OID 171008)
-- Dependencies: 233
-- Data for Name: tipomedida; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipomedida (id, nome) VALUES ('4028b8814c84ef28014c84f337850000', 'Medida Base');
INSERT INTO tipomedida (id, nome) VALUES ('4028b8814c84ef28014c84f3541b0001', 'Medida Derivada');


--
-- TOC entry 2846 (class 0 OID 171036)
-- Dependencies: 236
-- Data for Name: treeitemplanomedicaobase; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO treeitemplanomedicaobase (id, nome) VALUES ('4028b8814c85e10e014c85e42e0a0000', 'Total de Linhas');
INSERT INTO treeitemplanomedicaobase (id, nome) VALUES ('4028b8814c85e10e014c85ec16de0002', 'Total de Linhas de Comentários');
INSERT INTO treeitemplanomedicaobase (id, nome) VALUES ('4028b8814c85e10e014c85ee9bb40003', 'Percentual de Linhas Comentadas por Total de Linhas');


--
-- TOC entry 2847 (class 0 OID 171044)
-- Dependencies: 237
-- Data for Name: unidadedemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO unidadedemedida (id, descricao, nome) VALUES ('4028b8814c85b78f014c85baf5dc0000', 'Linhas de um arquivo', 'Linhas');
INSERT INTO unidadedemedida (id, descricao, nome) VALUES ('4028b8814c860623014c860a03430000', 'Pontos Percentuais', 'Pontos Percentuais');


--
-- TOC entry 2811 (class 0 OID 170766)
-- Dependencies: 201
-- Data for Name: medida; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO medida (descricao, mnemonico, nome, id, elementomensuravel_id, escala_id, tipodeentidademedida_id, tipomedida_id, unidadedemedida_id) VALUES (NULL, 'TLIN', 'Total de Linhas', '4028b8814c85e10e014c85e42e0a0000', '4028b8814c8274ee014c827aea970001', '4028b8814c823828014c824b01360037', '4028b8814c8274ee014c827a509e0000', '4028b8814c84ef28014c84f337850000', '4028b8814c85b78f014c85baf5dc0000');
INSERT INTO medida (descricao, mnemonico, nome, id, elementomensuravel_id, escala_id, tipodeentidademedida_id, tipomedida_id, unidadedemedida_id) VALUES (NULL, 'TCOM', 'Total de Linhas de Comentários', '4028b8814c85e10e014c85ec16de0002', '4028b8814c852268014c85759ab70002', '4028b8814c823828014c824b01360037', '4028b8814c8274ee014c827a509e0000', '4028b8814c84ef28014c84f337850000', '4028b8814c85b78f014c85baf5dc0000');
INSERT INTO medida (descricao, mnemonico, nome, id, elementomensuravel_id, escala_id, tipodeentidademedida_id, tipomedida_id, unidadedemedida_id) VALUES (NULL, 'PCOM', 'Percentual de Linhas Comentadas por Total de Linhas', '4028b8814c85e10e014c85ee9bb40003', '4028b8814c852268014c85759ab70002', '4028b8814c823828014c8242c2aa000f', '4028b8814c8274ee014c827a509e0000', '4028b8814c84ef28014c84f3541b0001', '4028b8814c860623014c860a03430000');


--
-- TOC entry 2824 (class 0 OID 170863)
-- Dependencies: 214
-- Data for Name: periodicidade; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2828 (class 0 OID 170896)
-- Dependencies: 218
-- Data for Name: procedimento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2829 (class 0 OID 170906)
-- Dependencies: 219
-- Data for Name: procedimentodeanalisedemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2830 (class 0 OID 170911)
-- Dependencies: 220
-- Data for Name: procedimentodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2802 (class 0 OID 170682)
-- Dependencies: 192
-- Data for Name: definicaooperacionaldemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO definicaooperacionaldemedida (id, data, descricao, nome, medida_id, momentodeanalisedemedicao_id, momentodemedicao_id, periodicidadedeanalisedemedicao_id, periodicidadedemedicao_id, procedimentodeanalisedemedicao_id, procedimentodemedicao_id, responsavelpelaanalisedemedicao_id, responsavelpelamedicao_id) VALUES ('4028b8814c85e10e014c85e5d49a0001', '2015-04-04 00:00:00', 'Definição Operacional para obtenção do Total de Linhas (TLIN)', 'Definição Operacional para TLIN', '4028b8814c85e10e014c85e42e0a0000', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2825 (class 0 OID 170873)
-- Dependencies: 215
-- Data for Name: planodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2812 (class 0 OID 170776)
-- Dependencies: 202
-- Data for Name: medidaplanodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2784 (class 0 OID 170554)
-- Dependencies: 174
-- Data for Name: analisedemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2798 (class 0 OID 170648)
-- Dependencies: 188
-- Data for Name: contextodebaselinededesempenhodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2835 (class 0 OID 170944)
-- Dependencies: 225
-- Data for Name: processopadrao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2796 (class 0 OID 170630)
-- Dependencies: 186
-- Data for Name: baselinededesempenhodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2803 (class 0 OID 170692)
-- Dependencies: 193
-- Data for Name: desempenhodeprocessoespecificado; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2797 (class 0 OID 170640)
-- Dependencies: 187
-- Data for Name: capacidadedeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2783 (class 0 OID 170546)
-- Dependencies: 173
-- Data for Name: analisedecomportamentodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2799 (class 0 OID 170656)
-- Dependencies: 189
-- Data for Name: contextodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2837 (class 0 OID 170958)
-- Dependencies: 227
-- Data for Name: projeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2850 (class 0 OID 171069)
-- Dependencies: 240
-- Data for Name: valormedido; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2810 (class 0 OID 170758)
-- Dependencies: 200
-- Data for Name: medicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2852 (class 0 OID 171082)
-- Dependencies: 242
-- Data for Name: analisedemedicao_medicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2839 (class 0 OID 170973)
-- Dependencies: 229
-- Data for Name: tipodeartefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2785 (class 0 OID 170562)
-- Dependencies: 175
-- Data for Name: artefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2789 (class 0 OID 170589)
-- Dependencies: 179
-- Data for Name: atividadeinstanciada_dependede_atividadeinstanciada; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2790 (class 0 OID 170595)
-- Dependencies: 180
-- Data for Name: atividadeinstanciada_produz_artefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2788 (class 0 OID 170583)
-- Dependencies: 178
-- Data for Name: atividadeinstanciada_realizadopor_recursohumano; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2791 (class 0 OID 170601)
-- Dependencies: 181
-- Data for Name: atividadeinstanciada_requer_artefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2793 (class 0 OID 170612)
-- Dependencies: 183
-- Data for Name: atividadepadrao_dependede_atividadepadrao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2794 (class 0 OID 170618)
-- Dependencies: 184
-- Data for Name: atividadepadrao_produz_tipoartefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2795 (class 0 OID 170624)
-- Dependencies: 185
-- Data for Name: atividadepadrao_requer_tipoartefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2853 (class 0 OID 171088)
-- Dependencies: 243
-- Data for Name: baselinededesempenhodeprocesso_medicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2808 (class 0 OID 170740)
-- Dependencies: 198
-- Data for Name: formuladecalculodemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO formuladecalculodemedida (id, data, formula, nome, calcula_id) VALUES ('4028b8814c85e10e014c85ef920f0004', '2015-04-04 00:00:00', 'TCOM / TLIN', 'Fórmula do Percentual de Linhas Comentadas por Total de Linhas', '4028b8814c85e10e014c85ee9bb40003');


--
-- TOC entry 2814 (class 0 OID 170789)
-- Dependencies: 204
-- Data for Name: modelodedesempenhodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2854 (class 0 OID 171094)
-- Dependencies: 244
-- Data for Name: baselinededesempenhodeprocesso_modelodedesempenhodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2800 (class 0 OID 170664)
-- Dependencies: 190
-- Data for Name: criterio; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2801 (class 0 OID 170674)
-- Dependencies: 191
-- Data for Name: criteriodeprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2816 (class 0 OID 170807)
-- Dependencies: 206
-- Data for Name: objetivo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2844 (class 0 OID 171018)
-- Dependencies: 234
-- Data for Name: tipoobjetivodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2817 (class 0 OID 170817)
-- Dependencies: 207
-- Data for Name: objetivodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2855 (class 0 OID 171100)
-- Dependencies: 245
-- Data for Name: definicaooperacionaldemedida_objetivodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2856 (class 0 OID 171106)
-- Dependencies: 246
-- Data for Name: elementomensuravel_tipodeentidademensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO elementomensuravel_tipodeentidademensuravel (elementomensuravel_id, tipodeentidademensuravel_id) VALUES ('4028b8814c81ea3c014c820afe9c0000', '4028b8814c81ea3c014c820b344d0001');
INSERT INTO elementomensuravel_tipodeentidademensuravel (elementomensuravel_id, tipodeentidademensuravel_id) VALUES ('4028b8814c852268014c857551940001', '4028b8814c8274ee014c827a509e0000');
INSERT INTO elementomensuravel_tipodeentidademensuravel (elementomensuravel_id, tipodeentidademensuravel_id) VALUES ('4028b8814c852268014c85759ab70002', '4028b8814c8274ee014c827a509e0000');
INSERT INTO elementomensuravel_tipodeentidademensuravel (elementomensuravel_id, tipodeentidademensuravel_id) VALUES ('4028b8814c852268014c85764de80003', '4028b8814c8274ee014c827a509e0000');
INSERT INTO elementomensuravel_tipodeentidademensuravel (elementomensuravel_id, tipodeentidademensuravel_id) VALUES ('4028b8814c8274ee014c827aea970001', '4028b8814c8274ee014c827a509e0000');


--
-- TOC entry 2857 (class 0 OID 171112)
-- Dependencies: 247
-- Data for Name: equipe_projeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2858 (class 0 OID 171118)
-- Dependencies: 248
-- Data for Name: equipe_recursohumano; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2849 (class 0 OID 171059)
-- Dependencies: 239
-- Data for Name: valordeescala; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c8214f4014c82279a740005', false, 'Alto');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c8214f4014c8227b1e10006', false, 'Médio');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c8214f4014c8227c6c90007', false, 'Baixo');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c823d9a770002', false, 'Janeiro');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c823da9860003', false, 'Fevereiro');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c823e3345000d', false, 'Dezembro');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c823dfbf90009', false, 'Agosto');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c823dc2c90005', false, 'Abril');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c823e094c000a', false, 'Setembro');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c823e1a6b000b', false, 'Outubro');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c823e26ab000c', false, 'Novembro');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c823dd8010006', false, 'Maio');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c823de4090007', false, 'Junho');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c823defd50008', false, 'Julho');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c823db88e0004', false, 'Março');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c824329630011', false, 'Terça-feira');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c824354140014', false, 'Sexta-feira');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c824312650010', false, 'Segunda-feira');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c824368b90015', false, 'Sábado');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c824346520013', false, 'Quinta-feira');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c8243396a0012', false, 'Quarta-feira');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c824374320016', false, 'Domingo');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c823cef990000', true, 'Números Naturais');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c823d30e40001', true, 'Números Reais');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c82452d34002b', true, '21');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c8244c61d001c', true, '6');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c8244d875001f', true, '9');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c8244d335001e', true, '8');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c8244ccae001d', true, '7');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c8244c04b001b', true, '5');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c8244bb97001a', true, '4');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c82457db10035', true, '31');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c824576810034', true, '30');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c8244b4130019', true, '3');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c82456bf90033', true, '29');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c824565c10032', true, '28');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c82455ea00031', true, '27');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c824557d00030', true, '26');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c82454f32002f', true, '25');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c82454022002e', true, '24');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c82453a65002d', true, '23');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c824532d3002c', true, '22');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c82452800002a', true, '20');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c8244a21f0018', true, '2');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c824521df0029', true, '19');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c82451bb00028', true, '18');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c8245131e0027', true, '17');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c82450ce60026', true, '16');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c824504690025', true, '15');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c8244fe4e0024', true, '14');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c8244f75d0023', true, '13');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c8244f10f0022', true, '12');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c8244e4340021', true, '11');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c8244df170020', true, '10');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c823828014c82449a240017', true, '1');
INSERT INTO valordeescala (id, numerico, valor) VALUES ('4028b8814c852268014c8578d6a80005', true, 'Números Reais entre 0 e 100');


--
-- TOC entry 2859 (class 0 OID 171124)
-- Dependencies: 249
-- Data for Name: escala_valordeescala; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82449a240017');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244df170020');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244e4340021');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244f10f0022');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244f75d0023');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244fe4e0024');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c824504690025');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82450ce60026');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8245131e0027');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82451bb00028');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c824521df0029');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244a21f0018');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82452800002a');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82452d34002b');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c824532d3002c');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82453a65002d');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82454022002e');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82454f32002f');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c824557d00030');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82455ea00031');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c824565c10032');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82456bf90033');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244b4130019');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c824576810034');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c82457db10035');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244bb97001a');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244c04b001b');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244c61d001c');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244ccae001d');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244d335001e');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8247beb10036', '4028b8814c823828014c8244d875001f');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c824b01360037', '4028b8814c823828014c823cef990000');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c824b62f80038', '4028b8814c823828014c823d30e40001');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823dc2c90005');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823dfbf90009');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823e3345000d');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823da9860003');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823d9a770002');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823defd50008');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823de4090007');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823dd8010006');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823db88e0004');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823e26ab000c');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823e1a6b000b');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c82411b4f000e', '4028b8814c823828014c823e094c000a');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c852268014c8577a2ae0004', '4028b8814c852268014c8578d6a80005');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8242c2aa000f', '4028b8814c823828014c824374320016');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8242c2aa000f', '4028b8814c823828014c8243396a0012');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8242c2aa000f', '4028b8814c823828014c824346520013');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8242c2aa000f', '4028b8814c823828014c824368b90015');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8242c2aa000f', '4028b8814c823828014c824312650010');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8242c2aa000f', '4028b8814c823828014c824354140014');
INSERT INTO escala_valordeescala (escala_id, valordeescala_id) VALUES ('4028b8814c823828014c8242c2aa000f', '4028b8814c823828014c824329630011');


--
-- TOC entry 2860 (class 0 OID 171130)
-- Dependencies: 250
-- Data for Name: formuladecalculodemedida_usa_formuladecalculodemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2861 (class 0 OID 171136)
-- Dependencies: 251
-- Data for Name: formuladecalculodemedida_usa_medida; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO formuladecalculodemedida_usa_medida (formuladecalculodemedida_id, medida_id) VALUES ('4028b8814c85e10e014c85ef920f0004', '4028b8814c85e10e014c85e42e0a0000');
INSERT INTO formuladecalculodemedida_usa_medida (formuladecalculodemedida_id, medida_id) VALUES ('4028b8814c85e10e014c85ef920f0004', '4028b8814c85e10e014c85ec16de0002');


--
-- TOC entry 2809 (class 0 OID 170750)
-- Dependencies: 199
-- Data for Name: images; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2862 (class 0 OID 171142)
-- Dependencies: 252
-- Data for Name: medida_correlatas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO medida_correlatas (medida_id, medida_id2) VALUES ('4028b8814c85e10e014c85ee9bb40003', '4028b8814c85e10e014c85e42e0a0000');
INSERT INTO medida_correlatas (medida_id, medida_id2) VALUES ('4028b8814c85e10e014c85ee9bb40003', '4028b8814c85e10e014c85ec16de0002');
INSERT INTO medida_correlatas (medida_id, medida_id2) VALUES ('4028b8814c85e10e014c85e42e0a0000', '4028b8814c85e10e014c85ec16de0002');


--
-- TOC entry 2863 (class 0 OID 171148)
-- Dependencies: 253
-- Data for Name: medida_derivade; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO medida_derivade (medida_id, medida_id2) VALUES ('4028b8814c85e10e014c85ee9bb40003', '4028b8814c85e10e014c85e42e0a0000');
INSERT INTO medida_derivade (medida_id, medida_id2) VALUES ('4028b8814c85e10e014c85ee9bb40003', '4028b8814c85e10e014c85ec16de0002');


--
-- TOC entry 2815 (class 0 OID 170797)
-- Dependencies: 205
-- Data for Name: necessidadedeinformacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2864 (class 0 OID 171154)
-- Dependencies: 254
-- Data for Name: medida_necessidadedeinformacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2865 (class 0 OID 171160)
-- Dependencies: 255
-- Data for Name: medida_objetivo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2813 (class 0 OID 170784)
-- Dependencies: 203
-- Data for Name: metodoanalitico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2866 (class 0 OID 171166)
-- Dependencies: 256
-- Data for Name: objetivo_identifica_necessidade; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2867 (class 0 OID 171172)
-- Dependencies: 257
-- Data for Name: objetivo_projeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2820 (class 0 OID 170837)
-- Dependencies: 210
-- Data for Name: objetivodesoftware; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2818 (class 0 OID 170825)
-- Dependencies: 208
-- Data for Name: objetivodemedicao_baseadoem_objetivodesoftware; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2822 (class 0 OID 170848)
-- Dependencies: 212
-- Data for Name: objetivoestrategico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2819 (class 0 OID 170831)
-- Dependencies: 209
-- Data for Name: objetivodemedicao_baseadoem_objetivoestrategico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2821 (class 0 OID 170842)
-- Dependencies: 211
-- Data for Name: objetivodesoftware_baseadoem_objetivoestrategico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2868 (class 0 OID 171178)
-- Dependencies: 258
-- Data for Name: planodemedicao_necessidadedeinformacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2869 (class 0 OID 171184)
-- Dependencies: 259
-- Data for Name: planodemedicao_objetivodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2870 (class 0 OID 171190)
-- Dependencies: 260
-- Data for Name: planodemedicao_objetivodesoftware; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2871 (class 0 OID 171196)
-- Dependencies: 261
-- Data for Name: planodemedicao_objetivoestrategico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2826 (class 0 OID 170883)
-- Dependencies: 216
-- Data for Name: planodemedicaodaorganizacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2827 (class 0 OID 170888)
-- Dependencies: 217
-- Data for Name: planodemedicaodoprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2872 (class 0 OID 171202)
-- Dependencies: 262
-- Data for Name: procedimentodeanalisedemedicao_metodoanalitico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2873 (class 0 OID 171208)
-- Dependencies: 263
-- Data for Name: procedimentodemedicao_formuladecalculodemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2833 (class 0 OID 170930)
-- Dependencies: 223
-- Data for Name: processoinstanciado; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2831 (class 0 OID 170916)
-- Dependencies: 221
-- Data for Name: processodeprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2832 (class 0 OID 170924)
-- Dependencies: 222
-- Data for Name: processodeprojeto_atividadedeprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2834 (class 0 OID 170938)
-- Dependencies: 224
-- Data for Name: processoinstanciado_atividadeinstanciada; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2836 (class 0 OID 170952)
-- Dependencies: 226
-- Data for Name: processopadrao_atividadepadrao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2874 (class 0 OID 171214)
-- Dependencies: 264
-- Data for Name: recursohumano_planodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2875 (class 0 OID 171220)
-- Dependencies: 265
-- Data for Name: subelemento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2876 (class 0 OID 171226)
-- Dependencies: 266
-- Data for Name: subnecessidade; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2877 (class 0 OID 171232)
-- Dependencies: 267
-- Data for Name: subobjetivo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2845 (class 0 OID 171028)
-- Dependencies: 235
-- Data for Name: treeitemplanomedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2848 (class 0 OID 171054)
-- Dependencies: 238
-- Data for Name: valoralfanumerico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2851 (class 0 OID 171077)
-- Dependencies: 241
-- Data for Name: valornumerico; Type: TABLE DATA; Schema: public; Owner: postgres
--



-- Completed on 2015-04-04 17:24:43

--
-- PostgreSQL database dump complete
--

