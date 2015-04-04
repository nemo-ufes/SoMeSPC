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
*/
--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.1
-- Dumped by pg_dump version 9.4.1
-- Started on 2015-04-04 15:31:04

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

--
-- TOC entry 2806 (class 0 OID 169169)
-- Dependencies: 196
-- Data for Name: equipe; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2823 (class 0 OID 169302)
-- Dependencies: 213
-- Data for Name: papelrecursohumano; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2838 (class 0 OID 169412)
-- Dependencies: 228
-- Data for Name: recursohumano; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2782 (class 0 OID 168987)
-- Dependencies: 172
-- Data for Name: alocacaoequipe; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2840 (class 0 OID 169427)
-- Dependencies: 230
-- Data for Name: tipodeentidademensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipodeentidademensuravel (id, descricao, nome) VALUES ('4028b8814c81ea3c014c820b344d0001', 'Atividade comum.', 'Atividade');
INSERT INTO tipodeentidademensuravel (id, descricao, nome) VALUES ('4028b8814c8274ee014c827a509e0000', 'Código fonte de um software', 'Código fonte');


--
-- TOC entry 2805 (class 0 OID 169159)
-- Dependencies: 195
-- Data for Name: entidademensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2792 (class 0 OID 169056)
-- Dependencies: 182
-- Data for Name: atividadepadrao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2787 (class 0 OID 169024)
-- Dependencies: 177
-- Data for Name: atividadeinstanciada; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2786 (class 0 OID 169019)
-- Dependencies: 176
-- Data for Name: atividadedeprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2841 (class 0 OID 169437)
-- Dependencies: 231
-- Data for Name: tipoelementomensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipoelementomensuravel (id, nome) VALUES ('1', 'Elemento Diretamente Mensurável');
INSERT INTO tipoelementomensuravel (id, nome) VALUES ('2', 'Elemento Indiretamente Mensurável');


--
-- TOC entry 2804 (class 0 OID 169149)
-- Dependencies: 194
-- Data for Name: elementomensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO elementomensuravel (id, descricao, nome, tipoelementomensuravel_id) VALUES ('4028b8814c81ea3c014c820afe9c0000', 'Diferença entre o início e o fim de alguma atividade/tarefa/etc.', 'Duração', '1');
INSERT INTO elementomensuravel (id, descricao, nome, tipoelementomensuravel_id) VALUES ('4028b8814c8274ee014c827aea970001', 'Tamanho', 'Tamanho', '1');
INSERT INTO elementomensuravel (id, descricao, nome, tipoelementomensuravel_id) VALUES ('4028b8814c852268014c857551940001', 'Complexidade', 'Complexidade', '2');
INSERT INTO elementomensuravel (id, descricao, nome, tipoelementomensuravel_id) VALUES ('4028b8814c852268014c85759ab70002', 'Documentação', 'Documentação', '1');
INSERT INTO elementomensuravel (id, descricao, nome, tipoelementomensuravel_id) VALUES ('4028b8814c852268014c85764de80003', 'Estrutura', 'Estrutura', '1');


--
-- TOC entry 2842 (class 0 OID 169447)
-- Dependencies: 232
-- Data for Name: tipoescala; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipoescala (id, nome) VALUES ('4028b8814c8214f4014c821754160001', 'Intervalo');
INSERT INTO tipoescala (id, nome) VALUES ('4028b8814c8214f4014c8217925e0003', 'Ordinal');
INSERT INTO tipoescala (id, nome) VALUES ('4028b8814c85a7d1014c85b088db0000', 'Racional');


--
-- TOC entry 2807 (class 0 OID 169179)
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
-- TOC entry 2843 (class 0 OID 169457)
-- Dependencies: 233
-- Data for Name: tipomedida; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipomedida (id, nome) VALUES ('4028b8814c84ef28014c84f337850000', 'Medida Base');
INSERT INTO tipomedida (id, nome) VALUES ('4028b8814c84ef28014c84f3541b0001', 'Medida Derivada');


--
-- TOC entry 2846 (class 0 OID 169485)
-- Dependencies: 236
-- Data for Name: treeitemplanomedicaobase; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2847 (class 0 OID 169493)
-- Dependencies: 237
-- Data for Name: unidadedemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2811 (class 0 OID 169215)
-- Dependencies: 201
-- Data for Name: medida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2824 (class 0 OID 169312)
-- Dependencies: 214
-- Data for Name: periodicidade; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2828 (class 0 OID 169345)
-- Dependencies: 218
-- Data for Name: procedimento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2829 (class 0 OID 169355)
-- Dependencies: 219
-- Data for Name: procedimentodeanalisedemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2830 (class 0 OID 169360)
-- Dependencies: 220
-- Data for Name: procedimentodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2802 (class 0 OID 169131)
-- Dependencies: 192
-- Data for Name: definicaooperacionaldemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2825 (class 0 OID 169322)
-- Dependencies: 215
-- Data for Name: planodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2812 (class 0 OID 169225)
-- Dependencies: 202
-- Data for Name: medidaplanodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2784 (class 0 OID 169003)
-- Dependencies: 174
-- Data for Name: analisedemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2798 (class 0 OID 169097)
-- Dependencies: 188
-- Data for Name: contextodebaselinededesempenhodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2835 (class 0 OID 169393)
-- Dependencies: 225
-- Data for Name: processopadrao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2796 (class 0 OID 169079)
-- Dependencies: 186
-- Data for Name: baselinededesempenhodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2803 (class 0 OID 169141)
-- Dependencies: 193
-- Data for Name: desempenhodeprocessoespecificado; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2797 (class 0 OID 169089)
-- Dependencies: 187
-- Data for Name: capacidadedeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2783 (class 0 OID 168995)
-- Dependencies: 173
-- Data for Name: analisedecomportamentodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2799 (class 0 OID 169105)
-- Dependencies: 189
-- Data for Name: contextodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2837 (class 0 OID 169407)
-- Dependencies: 227
-- Data for Name: projeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2850 (class 0 OID 169518)
-- Dependencies: 240
-- Data for Name: valormedido; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2810 (class 0 OID 169207)
-- Dependencies: 200
-- Data for Name: medicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2852 (class 0 OID 169531)
-- Dependencies: 242
-- Data for Name: analisedemedicao_medicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2839 (class 0 OID 169422)
-- Dependencies: 229
-- Data for Name: tipodeartefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2785 (class 0 OID 169011)
-- Dependencies: 175
-- Data for Name: artefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2789 (class 0 OID 169038)
-- Dependencies: 179
-- Data for Name: atividadeinstanciada_dependede_atividadeinstanciada; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2790 (class 0 OID 169044)
-- Dependencies: 180
-- Data for Name: atividadeinstanciada_produz_artefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2788 (class 0 OID 169032)
-- Dependencies: 178
-- Data for Name: atividadeinstanciada_realizadopor_recursohumano; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2791 (class 0 OID 169050)
-- Dependencies: 181
-- Data for Name: atividadeinstanciada_requer_artefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2793 (class 0 OID 169061)
-- Dependencies: 183
-- Data for Name: atividadepadrao_dependede_atividadepadrao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2794 (class 0 OID 169067)
-- Dependencies: 184
-- Data for Name: atividadepadrao_produz_tipoartefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2795 (class 0 OID 169073)
-- Dependencies: 185
-- Data for Name: atividadepadrao_requer_tipoartefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2853 (class 0 OID 169537)
-- Dependencies: 243
-- Data for Name: baselinededesempenhodeprocesso_medicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2808 (class 0 OID 169189)
-- Dependencies: 198
-- Data for Name: formuladecalculodemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2814 (class 0 OID 169238)
-- Dependencies: 204
-- Data for Name: modelodedesempenhodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2854 (class 0 OID 169543)
-- Dependencies: 244
-- Data for Name: baselinededesempenhodeprocesso_modelodedesempenhodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2800 (class 0 OID 169113)
-- Dependencies: 190
-- Data for Name: criterio; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2801 (class 0 OID 169123)
-- Dependencies: 191
-- Data for Name: criteriodeprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2816 (class 0 OID 169256)
-- Dependencies: 206
-- Data for Name: objetivo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2844 (class 0 OID 169467)
-- Dependencies: 234
-- Data for Name: tipoobjetivodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2817 (class 0 OID 169266)
-- Dependencies: 207
-- Data for Name: objetivodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2855 (class 0 OID 169549)
-- Dependencies: 245
-- Data for Name: definicaooperacionaldemedida_objetivodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2856 (class 0 OID 169555)
-- Dependencies: 246
-- Data for Name: elementomensuravel_tipodeentidademensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO elementomensuravel_tipodeentidademensuravel (tipodeentidademensuravel_id, elementomensuravel_id) VALUES ('4028b8814c81ea3c014c820b344d0001', '4028b8814c81ea3c014c820afe9c0000');
INSERT INTO elementomensuravel_tipodeentidademensuravel (tipodeentidademensuravel_id, elementomensuravel_id) VALUES ('4028b8814c8274ee014c827a509e0000', '4028b8814c852268014c857551940001');
INSERT INTO elementomensuravel_tipodeentidademensuravel (tipodeentidademensuravel_id, elementomensuravel_id) VALUES ('4028b8814c8274ee014c827a509e0000', '4028b8814c852268014c85759ab70002');
INSERT INTO elementomensuravel_tipodeentidademensuravel (tipodeentidademensuravel_id, elementomensuravel_id) VALUES ('4028b8814c8274ee014c827a509e0000', '4028b8814c852268014c85764de80003');
INSERT INTO elementomensuravel_tipodeentidademensuravel (tipodeentidademensuravel_id, elementomensuravel_id) VALUES ('4028b8814c8274ee014c827a509e0000', '4028b8814c8274ee014c827aea970001');


--
-- TOC entry 2857 (class 0 OID 169561)
-- Dependencies: 247
-- Data for Name: equipe_projeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2858 (class 0 OID 169567)
-- Dependencies: 248
-- Data for Name: equipe_recursohumano; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2849 (class 0 OID 169508)
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
-- TOC entry 2859 (class 0 OID 169573)
-- Dependencies: 249
-- Data for Name: escala_valordeescala; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c82449a240017', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c8244df170020', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c8244e4340021', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c8244f10f0022', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c8244f75d0023', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c8244fe4e0024', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c824504690025', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c82450ce60026', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c8245131e0027', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c82451bb00028', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c824521df0029', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c8244a21f0018', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c82452800002a', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c82452d34002b', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c824532d3002c', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c82453a65002d', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c82454022002e', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c82454f32002f', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c824557d00030', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c82455ea00031', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c824565c10032', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c82456bf90033', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c8244b4130019', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c824576810034', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c82457db10035', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c8244bb97001a', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c8244c04b001b', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c8244c61d001c', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c8244ccae001d', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c8244d335001e', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c8244d875001f', '4028b8814c823828014c8247beb10036');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c823cef990000', '4028b8814c823828014c824b01360037');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c823d30e40001', '4028b8814c823828014c824b62f80038');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c823dc2c90005', '4028b8814c823828014c82411b4f000e');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c823dfbf90009', '4028b8814c823828014c82411b4f000e');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c823e3345000d', '4028b8814c823828014c82411b4f000e');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c823da9860003', '4028b8814c823828014c82411b4f000e');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c823d9a770002', '4028b8814c823828014c82411b4f000e');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c823defd50008', '4028b8814c823828014c82411b4f000e');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c823de4090007', '4028b8814c823828014c82411b4f000e');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c823dd8010006', '4028b8814c823828014c82411b4f000e');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c823db88e0004', '4028b8814c823828014c82411b4f000e');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c823e26ab000c', '4028b8814c823828014c82411b4f000e');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c823e1a6b000b', '4028b8814c823828014c82411b4f000e');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c823e094c000a', '4028b8814c823828014c82411b4f000e');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c852268014c8578d6a80005', '4028b8814c852268014c8577a2ae0004');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c824374320016', '4028b8814c823828014c8242c2aa000f');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c8243396a0012', '4028b8814c823828014c8242c2aa000f');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c824346520013', '4028b8814c823828014c8242c2aa000f');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c824368b90015', '4028b8814c823828014c8242c2aa000f');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c824312650010', '4028b8814c823828014c8242c2aa000f');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c824354140014', '4028b8814c823828014c8242c2aa000f');
INSERT INTO escala_valordeescala (valordeescala_id, escala_id) VALUES ('4028b8814c823828014c824329630011', '4028b8814c823828014c8242c2aa000f');


--
-- TOC entry 2860 (class 0 OID 169579)
-- Dependencies: 250
-- Data for Name: formuladecalculodemedida_usa_formuladecalculodemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2861 (class 0 OID 169585)
-- Dependencies: 251
-- Data for Name: formuladecalculodemedida_usa_medida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2809 (class 0 OID 169199)
-- Dependencies: 199
-- Data for Name: images; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2862 (class 0 OID 169591)
-- Dependencies: 252
-- Data for Name: medida_correlatas; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2863 (class 0 OID 169597)
-- Dependencies: 253
-- Data for Name: medida_derivade; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2815 (class 0 OID 169246)
-- Dependencies: 205
-- Data for Name: necessidadedeinformacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2864 (class 0 OID 169603)
-- Dependencies: 254
-- Data for Name: medida_necessidadedeinformacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2865 (class 0 OID 169609)
-- Dependencies: 255
-- Data for Name: medida_objetivo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2813 (class 0 OID 169233)
-- Dependencies: 203
-- Data for Name: metodoanalitico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2866 (class 0 OID 169615)
-- Dependencies: 256
-- Data for Name: objetivo_identifica_necessidade; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2867 (class 0 OID 169621)
-- Dependencies: 257
-- Data for Name: objetivo_projeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2820 (class 0 OID 169286)
-- Dependencies: 210
-- Data for Name: objetivodesoftware; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2818 (class 0 OID 169274)
-- Dependencies: 208
-- Data for Name: objetivodemedicao_baseadoem_objetivodesoftware; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2822 (class 0 OID 169297)
-- Dependencies: 212
-- Data for Name: objetivoestrategico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2819 (class 0 OID 169280)
-- Dependencies: 209
-- Data for Name: objetivodemedicao_baseadoem_objetivoestrategico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2821 (class 0 OID 169291)
-- Dependencies: 211
-- Data for Name: objetivodesoftware_baseadoem_objetivoestrategico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2868 (class 0 OID 169627)
-- Dependencies: 258
-- Data for Name: planodemedicao_necessidadedeinformacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2869 (class 0 OID 169633)
-- Dependencies: 259
-- Data for Name: planodemedicao_objetivodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2870 (class 0 OID 169639)
-- Dependencies: 260
-- Data for Name: planodemedicao_objetivodesoftware; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2871 (class 0 OID 169645)
-- Dependencies: 261
-- Data for Name: planodemedicao_objetivoestrategico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2826 (class 0 OID 169332)
-- Dependencies: 216
-- Data for Name: planodemedicaodaorganizacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2827 (class 0 OID 169337)
-- Dependencies: 217
-- Data for Name: planodemedicaodoprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2872 (class 0 OID 169651)
-- Dependencies: 262
-- Data for Name: procedimentodeanalisedemedicao_metodoanalitico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2873 (class 0 OID 169657)
-- Dependencies: 263
-- Data for Name: procedimentodemedicao_formuladecalculodemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2833 (class 0 OID 169379)
-- Dependencies: 223
-- Data for Name: processoinstanciado; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2831 (class 0 OID 169365)
-- Dependencies: 221
-- Data for Name: processodeprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2832 (class 0 OID 169373)
-- Dependencies: 222
-- Data for Name: processodeprojeto_atividadedeprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2834 (class 0 OID 169387)
-- Dependencies: 224
-- Data for Name: processoinstanciado_atividadeinstanciada; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2836 (class 0 OID 169401)
-- Dependencies: 226
-- Data for Name: processopadrao_atividadepadrao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2874 (class 0 OID 169663)
-- Dependencies: 264
-- Data for Name: recursohumano_planodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2875 (class 0 OID 169669)
-- Dependencies: 265
-- Data for Name: subelemento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2876 (class 0 OID 169675)
-- Dependencies: 266
-- Data for Name: subnecessidade; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2877 (class 0 OID 169681)
-- Dependencies: 267
-- Data for Name: subobjetivo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2845 (class 0 OID 169477)
-- Dependencies: 235
-- Data for Name: treeitemplanomedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2848 (class 0 OID 169503)
-- Dependencies: 238
-- Data for Name: valoralfanumerico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2851 (class 0 OID 169526)
-- Dependencies: 241
-- Data for Name: valornumerico; Type: TABLE DATA; Schema: public; Owner: postgres
--



-- Completed on 2015-04-04 15:31:05

--
-- PostgreSQL database dump complete
--

