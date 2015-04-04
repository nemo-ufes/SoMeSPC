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
-- Started on 2015-04-04 00:55:00

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

--
-- TOC entry 2822 (class 0 OID 161331)
-- Dependencies: 196
-- Data for Name: equipe; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2839 (class 0 OID 161464)
-- Dependencies: 213
-- Data for Name: papelrecursohumano; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2854 (class 0 OID 161574)
-- Dependencies: 228
-- Data for Name: recursohumano; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2798 (class 0 OID 161149)
-- Dependencies: 172
-- Data for Name: alocacaoequipe; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2856 (class 0 OID 161589)
-- Dependencies: 230
-- Data for Name: tipodeentidademensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipodeentidademensuravel VALUES ('4028b8814c81ea3c014c820b344d0001', 'Atividade comum.', 'Atividade');
INSERT INTO tipodeentidademensuravel VALUES ('4028b8814c8274ee014c827a509e0000', 'Código fonte de um software', 'Código fonte');


--
-- TOC entry 2821 (class 0 OID 161321)
-- Dependencies: 195
-- Data for Name: entidademensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO entidademensuravel VALUES ('4028b8814c828537014c828797dc0000', 'Atividade de Projeto', 'Atividade de Projeto', '4028b8814c81ea3c014c820b344d0001');


--
-- TOC entry 2808 (class 0 OID 161218)
-- Dependencies: 182
-- Data for Name: atividadepadrao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2803 (class 0 OID 161186)
-- Dependencies: 177
-- Data for Name: atividadeinstanciada; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2802 (class 0 OID 161181)
-- Dependencies: 176
-- Data for Name: atividadedeprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2857 (class 0 OID 161599)
-- Dependencies: 231
-- Data for Name: tipoelementomensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipoelementomensuravel VALUES ('1', 'Elemento Diretamente Mensurável');
INSERT INTO tipoelementomensuravel VALUES ('2', 'Elemento Indiretamente Mensurável');


--
-- TOC entry 2820 (class 0 OID 161311)
-- Dependencies: 194
-- Data for Name: elementomensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO elementomensuravel VALUES ('4028b8814c81ea3c014c820afe9c0000', 'Diferença entre o início e o fim de alguma atividade/tarefa/etc.', 'Duração', '1');
INSERT INTO elementomensuravel VALUES ('4028b8814c8274ee014c827aea970001', 'Tamanho', 'Tamanho', '1');


--
-- TOC entry 2858 (class 0 OID 161609)
-- Dependencies: 232
-- Data for Name: tipoescala; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipoescala VALUES ('4028b8814c8214f4014c82173df50000', 'Absoluta');
INSERT INTO tipoescala VALUES ('4028b8814c8214f4014c821754160001', 'Intervalo');
INSERT INTO tipoescala VALUES ('4028b8814c8214f4014c821770bf0002', 'Nominal');
INSERT INTO tipoescala VALUES ('4028b8814c8214f4014c8217925e0003', 'Ordinal');
INSERT INTO tipoescala VALUES ('4028b8814c8214f4014c8217a87e0004', 'Taxa');


--
-- TOC entry 2823 (class 0 OID 161341)
-- Dependencies: 197
-- Data for Name: escala; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO escala VALUES ('4028b8814c823828014c82411b4f000e', 'Meses do ano do calendário gregoriano', 'Mensal', '4028b8814c8214f4014c821770bf0002');
INSERT INTO escala VALUES ('4028b8814c823828014c8242c2aa000f', 'Dias da semana do calendário gregoriano.', 'Semanal', '4028b8814c8214f4014c821770bf0002');
INSERT INTO escala VALUES ('4028b8814c823828014c8247beb10036', 'Dias do mês.', 'Diária', '4028b8814c8214f4014c82173df50000');
INSERT INTO escala VALUES ('4028b8814c823828014c824b01360037', 'Escala dos Números Naturais', 'Escala dos Números Naturais', '4028b8814c8214f4014c821754160001');
INSERT INTO escala VALUES ('4028b8814c823828014c824b62f80038', 'Escala dos Números Reais', 'Escala dos Números Reais', '4028b8814c8214f4014c821754160001');


--
-- TOC entry 2859 (class 0 OID 161619)
-- Dependencies: 233
-- Data for Name: tipomedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2862 (class 0 OID 161647)
-- Dependencies: 236
-- Data for Name: treeitemplanomedicaobase; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2863 (class 0 OID 161655)
-- Dependencies: 237
-- Data for Name: unidadedemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2827 (class 0 OID 161377)
-- Dependencies: 201
-- Data for Name: medida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2840 (class 0 OID 161474)
-- Dependencies: 214
-- Data for Name: periodicidade; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2844 (class 0 OID 161507)
-- Dependencies: 218
-- Data for Name: procedimento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2845 (class 0 OID 161517)
-- Dependencies: 219
-- Data for Name: procedimentodeanalisedemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2846 (class 0 OID 161522)
-- Dependencies: 220
-- Data for Name: procedimentodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2818 (class 0 OID 161293)
-- Dependencies: 192
-- Data for Name: definicaooperacionaldemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2841 (class 0 OID 161484)
-- Dependencies: 215
-- Data for Name: planodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2828 (class 0 OID 161387)
-- Dependencies: 202
-- Data for Name: medidaplanodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2800 (class 0 OID 161165)
-- Dependencies: 174
-- Data for Name: analisedemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2814 (class 0 OID 161259)
-- Dependencies: 188
-- Data for Name: contextodebaselinededesempenhodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2851 (class 0 OID 161555)
-- Dependencies: 225
-- Data for Name: processopadrao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2812 (class 0 OID 161241)
-- Dependencies: 186
-- Data for Name: baselinededesempenhodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2819 (class 0 OID 161303)
-- Dependencies: 193
-- Data for Name: desempenhodeprocessoespecificado; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2813 (class 0 OID 161251)
-- Dependencies: 187
-- Data for Name: capacidadedeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2799 (class 0 OID 161157)
-- Dependencies: 173
-- Data for Name: analisedecomportamentodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2815 (class 0 OID 161267)
-- Dependencies: 189
-- Data for Name: contextodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2853 (class 0 OID 161569)
-- Dependencies: 227
-- Data for Name: projeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2868 (class 0 OID 161693)
-- Dependencies: 242
-- Data for Name: valormedido; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2826 (class 0 OID 161369)
-- Dependencies: 200
-- Data for Name: medicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2870 (class 0 OID 161706)
-- Dependencies: 244
-- Data for Name: analisedemedicao_medicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2855 (class 0 OID 161584)
-- Dependencies: 229
-- Data for Name: tipodeartefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2801 (class 0 OID 161173)
-- Dependencies: 175
-- Data for Name: artefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2805 (class 0 OID 161200)
-- Dependencies: 179
-- Data for Name: atividadeinstanciada_dependede_atividadeinstanciada; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2806 (class 0 OID 161206)
-- Dependencies: 180
-- Data for Name: atividadeinstanciada_produz_artefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2804 (class 0 OID 161194)
-- Dependencies: 178
-- Data for Name: atividadeinstanciada_realizadopor_recursohumano; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2807 (class 0 OID 161212)
-- Dependencies: 181
-- Data for Name: atividadeinstanciada_requer_artefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2809 (class 0 OID 161223)
-- Dependencies: 183
-- Data for Name: atividadepadrao_dependede_atividadepadrao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2810 (class 0 OID 161229)
-- Dependencies: 184
-- Data for Name: atividadepadrao_produz_tipoartefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2811 (class 0 OID 161235)
-- Dependencies: 185
-- Data for Name: atividadepadrao_requer_tipoartefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2871 (class 0 OID 161712)
-- Dependencies: 245
-- Data for Name: baselinededesempenhodeprocesso_medicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2824 (class 0 OID 161351)
-- Dependencies: 198
-- Data for Name: formuladecalculodemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2830 (class 0 OID 161400)
-- Dependencies: 204
-- Data for Name: modelodedesempenhodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2872 (class 0 OID 161718)
-- Dependencies: 246
-- Data for Name: baselinededesempenhodeprocesso_modelodedesempenhodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2816 (class 0 OID 161275)
-- Dependencies: 190
-- Data for Name: criterio; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2817 (class 0 OID 161285)
-- Dependencies: 191
-- Data for Name: criteriodeprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2832 (class 0 OID 161418)
-- Dependencies: 206
-- Data for Name: objetivo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2860 (class 0 OID 161629)
-- Dependencies: 234
-- Data for Name: tipoobjetivodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2833 (class 0 OID 161428)
-- Dependencies: 207
-- Data for Name: objetivodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2873 (class 0 OID 161724)
-- Dependencies: 247
-- Data for Name: definicaooperacionaldemedida_objetivodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2874 (class 0 OID 161730)
-- Dependencies: 248
-- Data for Name: elementomensuravel_entidademensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--




--
-- TOC entry 2875 (class 0 OID 161736)
-- Dependencies: 249
-- Data for Name: elementomensuravel_tipodeentidademensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO elementomensuravel_tipodeentidademensuravel VALUES ('4028b8814c81ea3c014c820b344d0001', '4028b8814c81ea3c014c820afe9c0000');
INSERT INTO elementomensuravel_tipodeentidademensuravel VALUES ('4028b8814c8274ee014c827a509e0000', '4028b8814c8274ee014c827aea970001');


--
-- TOC entry 2876 (class 0 OID 161742)
-- Dependencies: 250
-- Data for Name: equipe_projeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2877 (class 0 OID 161748)
-- Dependencies: 251
-- Data for Name: equipe_recursohumano; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2878 (class 0 OID 161754)
-- Dependencies: 252
-- Data for Name: formuladecalculodemedida_usa_formuladecalculodemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2879 (class 0 OID 161760)
-- Dependencies: 253
-- Data for Name: formuladecalculodemedida_usa_medida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2825 (class 0 OID 161361)
-- Dependencies: 199
-- Data for Name: images; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2880 (class 0 OID 161766)
-- Dependencies: 254
-- Data for Name: medida_correlatas; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2881 (class 0 OID 161772)
-- Dependencies: 255
-- Data for Name: medida_derivade; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2831 (class 0 OID 161408)
-- Dependencies: 205
-- Data for Name: necessidadedeinformacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2882 (class 0 OID 161778)
-- Dependencies: 256
-- Data for Name: medida_necessidadedeinformacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2883 (class 0 OID 161784)
-- Dependencies: 257
-- Data for Name: medida_objetivo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2829 (class 0 OID 161395)
-- Dependencies: 203
-- Data for Name: metodoanalitico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2884 (class 0 OID 161790)
-- Dependencies: 258
-- Data for Name: objetivo_identifica_necessidade; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2885 (class 0 OID 161796)
-- Dependencies: 259
-- Data for Name: objetivo_projeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2836 (class 0 OID 161448)
-- Dependencies: 210
-- Data for Name: objetivodesoftware; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2834 (class 0 OID 161436)
-- Dependencies: 208
-- Data for Name: objetivodemedicao_baseadoem_objetivodesoftware; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2838 (class 0 OID 161459)
-- Dependencies: 212
-- Data for Name: objetivoestrategico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2835 (class 0 OID 161442)
-- Dependencies: 209
-- Data for Name: objetivodemedicao_baseadoem_objetivoestrategico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2837 (class 0 OID 161453)
-- Dependencies: 211
-- Data for Name: objetivodesoftware_baseadoem_objetivoestrategico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2886 (class 0 OID 161802)
-- Dependencies: 260
-- Data for Name: planodemedicao_necessidadedeinformacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2887 (class 0 OID 161808)
-- Dependencies: 261
-- Data for Name: planodemedicao_objetivodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2888 (class 0 OID 161814)
-- Dependencies: 262
-- Data for Name: planodemedicao_objetivodesoftware; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2889 (class 0 OID 161820)
-- Dependencies: 263
-- Data for Name: planodemedicao_objetivoestrategico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2842 (class 0 OID 161494)
-- Dependencies: 216
-- Data for Name: planodemedicaodaorganizacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2843 (class 0 OID 161499)
-- Dependencies: 217
-- Data for Name: planodemedicaodoprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2890 (class 0 OID 161826)
-- Dependencies: 264
-- Data for Name: procedimentodeanalisedemedicao_metodoanalitico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2891 (class 0 OID 161832)
-- Dependencies: 265
-- Data for Name: procedimentodemedicao_formuladecalculodemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2849 (class 0 OID 161541)
-- Dependencies: 223
-- Data for Name: processoinstanciado; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2847 (class 0 OID 161527)
-- Dependencies: 221
-- Data for Name: processodeprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2848 (class 0 OID 161535)
-- Dependencies: 222
-- Data for Name: processodeprojeto_atividadedeprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2850 (class 0 OID 161549)
-- Dependencies: 224
-- Data for Name: processoinstanciado_atividadeinstanciada; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2852 (class 0 OID 161563)
-- Dependencies: 226
-- Data for Name: processopadrao_atividadepadrao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2892 (class 0 OID 161838)
-- Dependencies: 266
-- Data for Name: recursohumano_planodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2893 (class 0 OID 161844)
-- Dependencies: 267
-- Data for Name: subelemento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2894 (class 0 OID 161850)
-- Dependencies: 268
-- Data for Name: subnecessidade; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2895 (class 0 OID 161856)
-- Dependencies: 269
-- Data for Name: subobjetivo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2861 (class 0 OID 161639)
-- Dependencies: 235
-- Data for Name: treeitemplanomedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2864 (class 0 OID 161665)
-- Dependencies: 238
-- Data for Name: valoralfanumerico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2865 (class 0 OID 161670)
-- Dependencies: 239
-- Data for Name: valordeescala; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO valordeescala VALUES ('4028b8814c8214f4014c82279a740005', false, 'Alto', NULL);
INSERT INTO valordeescala VALUES ('4028b8814c8214f4014c8227b1e10006', false, 'Médio', NULL);
INSERT INTO valordeescala VALUES ('4028b8814c8214f4014c8227c6c90007', false, 'Baixo', NULL);
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823d9a770002', false, 'Janeiro', '4028b8814c823828014c82411b4f000e');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823da9860003', false, 'Fevereiro', '4028b8814c823828014c82411b4f000e');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823e3345000d', false, 'Dezembro', '4028b8814c823828014c82411b4f000e');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823dfbf90009', false, 'Agosto', '4028b8814c823828014c82411b4f000e');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823dc2c90005', false, 'Abril', '4028b8814c823828014c82411b4f000e');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823e094c000a', false, 'Setembro', '4028b8814c823828014c82411b4f000e');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823e1a6b000b', false, 'Outubro', '4028b8814c823828014c82411b4f000e');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823e26ab000c', false, 'Novembro', '4028b8814c823828014c82411b4f000e');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823dd8010006', false, 'Maio', '4028b8814c823828014c82411b4f000e');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823de4090007', false, 'Junho', '4028b8814c823828014c82411b4f000e');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823defd50008', false, 'Julho', '4028b8814c823828014c82411b4f000e');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823db88e0004', false, 'Março', '4028b8814c823828014c82411b4f000e');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824329630011', false, 'Terça-feira', '4028b8814c823828014c8242c2aa000f');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824354140014', false, 'Sexta-feira', '4028b8814c823828014c8242c2aa000f');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824312650010', false, 'Segunda-feira', '4028b8814c823828014c8242c2aa000f');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824368b90015', false, 'Sábado', '4028b8814c823828014c8242c2aa000f');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824346520013', false, 'Quinta-feira', '4028b8814c823828014c8242c2aa000f');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8243396a0012', false, 'Quarta-feira', '4028b8814c823828014c8242c2aa000f');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824374320016', false, 'Domingo', '4028b8814c823828014c8242c2aa000f');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823cef990000', true, 'Números Naturais', '4028b8814c823828014c824b01360037');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c823d30e40001', true, 'Números Reais', '4028b8814c823828014c824b62f80038');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82452d34002b', true, '21', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244c61d001c', true, '6', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244d875001f', true, '9', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244d335001e', true, '8', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244ccae001d', true, '7', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244c04b001b', true, '5', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244bb97001a', true, '4', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82457db10035', true, '31', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824576810034', true, '30', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244b4130019', true, '3', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82456bf90033', true, '29', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824565c10032', true, '28', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82455ea00031', true, '27', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824557d00030', true, '26', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82454f32002f', true, '25', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82454022002e', true, '24', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82453a65002d', true, '23', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824532d3002c', true, '22', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82452800002a', true, '20', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244a21f0018', true, '2', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824521df0029', true, '19', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82451bb00028', true, '18', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8245131e0027', true, '17', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82450ce60026', true, '16', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c824504690025', true, '15', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244fe4e0024', true, '14', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244f75d0023', true, '13', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244f10f0022', true, '12', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244e4340021', true, '11', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c8244df170020', true, '10', '4028b8814c823828014c8247beb10036');
INSERT INTO valordeescala VALUES ('4028b8814c823828014c82449a240017', true, '1', '4028b8814c823828014c8247beb10036');


--
-- TOC entry 2866 (class 0 OID 161680)
-- Dependencies: 240
-- Data for Name: valordeescalaalfanumerico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2867 (class 0 OID 161688)
-- Dependencies: 241
-- Data for Name: valordeescalanumerico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2869 (class 0 OID 161701)
-- Dependencies: 243
-- Data for Name: valornumerico; Type: TABLE DATA; Schema: public; Owner: postgres
--



-- Completed on 2015-04-04 00:55:01

--
-- PostgreSQL database dump complete
--

