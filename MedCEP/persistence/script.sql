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
-- Started on 2015-04-03 22:30:58

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

--
-- TOC entry 2822 (class 0 OID 158173)
-- Dependencies: 196
-- Data for Name: equipe; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2839 (class 0 OID 158306)
-- Dependencies: 213
-- Data for Name: papelrecursohumano; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2854 (class 0 OID 158416)
-- Dependencies: 228
-- Data for Name: recursohumano; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2798 (class 0 OID 157991)
-- Dependencies: 172
-- Data for Name: alocacaoequipe; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2856 (class 0 OID 158431)
-- Dependencies: 230
-- Data for Name: tipodeentidademensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipodeentidademensuravel VALUES ('4028b8814c81ea3c014c820b344d0001', 'Atividade comum.', 'Atividade');


--
-- TOC entry 2821 (class 0 OID 158163)
-- Dependencies: 195
-- Data for Name: entidademensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO entidademensuravel VALUES ('4028b8814c81ea3c014c820ba1770002', 'Atividade de Projeto', 'Atividade de Projeto', '4028b8814c81ea3c014c820b344d0001');


--
-- TOC entry 2808 (class 0 OID 158060)
-- Dependencies: 182
-- Data for Name: atividadepadrao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2803 (class 0 OID 158028)
-- Dependencies: 177
-- Data for Name: atividadeinstanciada; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2802 (class 0 OID 158023)
-- Dependencies: 176
-- Data for Name: atividadedeprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2857 (class 0 OID 158441)
-- Dependencies: 231
-- Data for Name: tipoelementomensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipoelementomensuravel VALUES ('1', 'Elemento Diretamente Mensurável');
INSERT INTO tipoelementomensuravel VALUES ('2', 'Elemento Indiretamente Mensurável');


--
-- TOC entry 2820 (class 0 OID 158153)
-- Dependencies: 194
-- Data for Name: elementomensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO elementomensuravel VALUES ('4028b8814c81ea3c014c820afe9c0000', 'Diferença entre o início e o fim de alguma atividade/tarefa/etc.', 'Duração', '1');


--
-- TOC entry 2858 (class 0 OID 158451)
-- Dependencies: 232
-- Data for Name: tipoescala; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2823 (class 0 OID 158183)
-- Dependencies: 197
-- Data for Name: escala; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2859 (class 0 OID 158461)
-- Dependencies: 233
-- Data for Name: tipomedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2862 (class 0 OID 158489)
-- Dependencies: 236
-- Data for Name: treeitemplanomedicaobase; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2863 (class 0 OID 158497)
-- Dependencies: 237
-- Data for Name: unidadedemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2827 (class 0 OID 158219)
-- Dependencies: 201
-- Data for Name: medida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2840 (class 0 OID 158316)
-- Dependencies: 214
-- Data for Name: periodicidade; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2844 (class 0 OID 158349)
-- Dependencies: 218
-- Data for Name: procedimento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2845 (class 0 OID 158359)
-- Dependencies: 219
-- Data for Name: procedimentodeanalisedemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2846 (class 0 OID 158364)
-- Dependencies: 220
-- Data for Name: procedimentodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2818 (class 0 OID 158135)
-- Dependencies: 192
-- Data for Name: definicaooperacionaldemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2841 (class 0 OID 158326)
-- Dependencies: 215
-- Data for Name: planodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2828 (class 0 OID 158229)
-- Dependencies: 202
-- Data for Name: medidaplanodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2800 (class 0 OID 158007)
-- Dependencies: 174
-- Data for Name: analisedemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2814 (class 0 OID 158101)
-- Dependencies: 188
-- Data for Name: contextodebaselinededesempenhodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2851 (class 0 OID 158397)
-- Dependencies: 225
-- Data for Name: processopadrao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2812 (class 0 OID 158083)
-- Dependencies: 186
-- Data for Name: baselinededesempenhodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2819 (class 0 OID 158145)
-- Dependencies: 193
-- Data for Name: desempenhodeprocessoespecificado; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2813 (class 0 OID 158093)
-- Dependencies: 187
-- Data for Name: capacidadedeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2799 (class 0 OID 157999)
-- Dependencies: 173
-- Data for Name: analisedecomportamentodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2815 (class 0 OID 158109)
-- Dependencies: 189
-- Data for Name: contextodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2853 (class 0 OID 158411)
-- Dependencies: 227
-- Data for Name: projeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2868 (class 0 OID 158535)
-- Dependencies: 242
-- Data for Name: valormedido; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2826 (class 0 OID 158211)
-- Dependencies: 200
-- Data for Name: medicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2870 (class 0 OID 158548)
-- Dependencies: 244
-- Data for Name: analisedemedicao_medicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2855 (class 0 OID 158426)
-- Dependencies: 229
-- Data for Name: tipodeartefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2801 (class 0 OID 158015)
-- Dependencies: 175
-- Data for Name: artefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2805 (class 0 OID 158042)
-- Dependencies: 179
-- Data for Name: atividadeinstanciada_dependede_atividadeinstanciada; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2806 (class 0 OID 158048)
-- Dependencies: 180
-- Data for Name: atividadeinstanciada_produz_artefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2804 (class 0 OID 158036)
-- Dependencies: 178
-- Data for Name: atividadeinstanciada_realizadopor_recursohumano; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2807 (class 0 OID 158054)
-- Dependencies: 181
-- Data for Name: atividadeinstanciada_requer_artefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2809 (class 0 OID 158065)
-- Dependencies: 183
-- Data for Name: atividadepadrao_dependede_atividadepadrao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2810 (class 0 OID 158071)
-- Dependencies: 184
-- Data for Name: atividadepadrao_produz_tipoartefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2811 (class 0 OID 158077)
-- Dependencies: 185
-- Data for Name: atividadepadrao_requer_tipoartefato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2871 (class 0 OID 158554)
-- Dependencies: 245
-- Data for Name: baselinededesempenhodeprocesso_medicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2824 (class 0 OID 158193)
-- Dependencies: 198
-- Data for Name: formuladecalculodemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2830 (class 0 OID 158242)
-- Dependencies: 204
-- Data for Name: modelodedesempenhodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2872 (class 0 OID 158560)
-- Dependencies: 246
-- Data for Name: baselinededesempenhodeprocesso_modelodedesempenhodeprocesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2816 (class 0 OID 158117)
-- Dependencies: 190
-- Data for Name: criterio; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2817 (class 0 OID 158127)
-- Dependencies: 191
-- Data for Name: criteriodeprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2832 (class 0 OID 158260)
-- Dependencies: 206
-- Data for Name: objetivo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2860 (class 0 OID 158471)
-- Dependencies: 234
-- Data for Name: tipoobjetivodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2833 (class 0 OID 158270)
-- Dependencies: 207
-- Data for Name: objetivodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2873 (class 0 OID 158566)
-- Dependencies: 247
-- Data for Name: definicaooperacionaldemedida_objetivodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2874 (class 0 OID 158572)
-- Dependencies: 248
-- Data for Name: elementomensuravel_entidademensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO elementomensuravel_entidademensuravel VALUES ('4028b8814c81ea3c014c820afe9c0000', '4028b8814c81ea3c014c820ba1770002');


--
-- TOC entry 2875 (class 0 OID 158578)
-- Dependencies: 249
-- Data for Name: elementomensuravel_tipodeentidademensuravel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO elementomensuravel_tipodeentidademensuravel VALUES ('4028b8814c81ea3c014c820b344d0001', '4028b8814c81ea3c014c820afe9c0000');


--
-- TOC entry 2876 (class 0 OID 158584)
-- Dependencies: 250
-- Data for Name: equipe_projeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2877 (class 0 OID 158590)
-- Dependencies: 251
-- Data for Name: equipe_recursohumano; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2878 (class 0 OID 158596)
-- Dependencies: 252
-- Data for Name: formuladecalculodemedida_usa_formuladecalculodemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2879 (class 0 OID 158602)
-- Dependencies: 253
-- Data for Name: formuladecalculodemedida_usa_medida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2825 (class 0 OID 158203)
-- Dependencies: 199
-- Data for Name: images; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2880 (class 0 OID 158608)
-- Dependencies: 254
-- Data for Name: medida_correlatas; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2881 (class 0 OID 158614)
-- Dependencies: 255
-- Data for Name: medida_derivade; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2831 (class 0 OID 158250)
-- Dependencies: 205
-- Data for Name: necessidadedeinformacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2882 (class 0 OID 158620)
-- Dependencies: 256
-- Data for Name: medida_necessidadedeinformacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2883 (class 0 OID 158626)
-- Dependencies: 257
-- Data for Name: medida_objetivo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2829 (class 0 OID 158237)
-- Dependencies: 203
-- Data for Name: metodoanalitico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2884 (class 0 OID 158632)
-- Dependencies: 258
-- Data for Name: objetivo_identifica_necessidade; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2885 (class 0 OID 158638)
-- Dependencies: 259
-- Data for Name: objetivo_projeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2836 (class 0 OID 158290)
-- Dependencies: 210
-- Data for Name: objetivodesoftware; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2834 (class 0 OID 158278)
-- Dependencies: 208
-- Data for Name: objetivodemedicao_baseadoem_objetivodesoftware; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2838 (class 0 OID 158301)
-- Dependencies: 212
-- Data for Name: objetivoestrategico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2835 (class 0 OID 158284)
-- Dependencies: 209
-- Data for Name: objetivodemedicao_baseadoem_objetivoestrategico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2837 (class 0 OID 158295)
-- Dependencies: 211
-- Data for Name: objetivodesoftware_baseadoem_objetivoestrategico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2886 (class 0 OID 158644)
-- Dependencies: 260
-- Data for Name: planodemedicao_necessidadedeinformacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2887 (class 0 OID 158650)
-- Dependencies: 261
-- Data for Name: planodemedicao_objetivodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2888 (class 0 OID 158656)
-- Dependencies: 262
-- Data for Name: planodemedicao_objetivodesoftware; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2889 (class 0 OID 158662)
-- Dependencies: 263
-- Data for Name: planodemedicao_objetivoestrategico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2842 (class 0 OID 158336)
-- Dependencies: 216
-- Data for Name: planodemedicaodaorganizacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2843 (class 0 OID 158341)
-- Dependencies: 217
-- Data for Name: planodemedicaodoprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2890 (class 0 OID 158668)
-- Dependencies: 264
-- Data for Name: procedimentodeanalisedemedicao_metodoanalitico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2891 (class 0 OID 158674)
-- Dependencies: 265
-- Data for Name: procedimentodemedicao_formuladecalculodemedida; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2849 (class 0 OID 158383)
-- Dependencies: 223
-- Data for Name: processoinstanciado; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2847 (class 0 OID 158369)
-- Dependencies: 221
-- Data for Name: processodeprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2848 (class 0 OID 158377)
-- Dependencies: 222
-- Data for Name: processodeprojeto_atividadedeprojeto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2850 (class 0 OID 158391)
-- Dependencies: 224
-- Data for Name: processoinstanciado_atividadeinstanciada; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2852 (class 0 OID 158405)
-- Dependencies: 226
-- Data for Name: processopadrao_atividadepadrao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2892 (class 0 OID 158680)
-- Dependencies: 266
-- Data for Name: recursohumano_planodemedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2893 (class 0 OID 158686)
-- Dependencies: 267
-- Data for Name: subelemento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2894 (class 0 OID 158692)
-- Dependencies: 268
-- Data for Name: subnecessidade; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2895 (class 0 OID 158698)
-- Dependencies: 269
-- Data for Name: subobjetivo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2861 (class 0 OID 158481)
-- Dependencies: 235
-- Data for Name: treeitemplanomedicao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2864 (class 0 OID 158507)
-- Dependencies: 238
-- Data for Name: valoralfanumerico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2865 (class 0 OID 158512)
-- Dependencies: 239
-- Data for Name: valordeescala; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2866 (class 0 OID 158522)
-- Dependencies: 240
-- Data for Name: valordeescalaalfanumerico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2867 (class 0 OID 158530)
-- Dependencies: 241
-- Data for Name: valordeescalanumerico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2869 (class 0 OID 158543)
-- Dependencies: 243
-- Data for Name: valornumerico; Type: TABLE DATA; Schema: public; Owner: postgres
--



-- Completed on 2015-04-03 22:30:59

--
-- PostgreSQL database dump complete
--

