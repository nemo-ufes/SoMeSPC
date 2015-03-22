# MedCEP
Ferramenta para Medição de Software e Controle Estatístico de Processos. 
<br/>Baseada na arquitetura de referência de medição de software de MARETTO (2014).

##Configuração do Ambiente

- Java SDK 7 Update 75 (x64) - [Download] (http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk7-downloads-1880260.html)
- IDE Eclipse Luna for EE Developers (x64) SR2 - [Download] (https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/luna/SR2/eclipse-jee-luna-SR2-win32-x86_64.zip)
- PostgreSQL 9.4.1 (x64) - [Download] (http://www.enterprisedb.com/products-services-training/pgdownload)
- Apache Tomcat 7.0.59 (x64) - [Download] (http://mirrors.koehn.com/apache/tomcat/tomcat-7/v7.0.59/bin/apache-tomcat-7.0.59-windows-x64.zip)

##Instruções de instalação
1. Instalar o Java SDK.
2. Instalar o PostgreSQL com usuário e senha <b>"postgres"</b>.
3. Criar um banco de dados no PostgreSQL chamado <b>"medcep"</b> e informar como dono o usuário <b>"postgres"</b>.
4. Descompactar o Apache Tomcat em C:\apache-tomcat-7.0.59 (caminho padrão). Caso queira executar o Apache Tomcat em outro diretório, atualizar a variável <b>tomcat.dir</b> do arquivo <b>MedCEP/properties/openxava.properties</b>. 
5. Descompacter o IDE Eclipse Luna.
6. Executar o Eclipse.
7. Baixar o código fonte do GitHub.
8. Configurar o Apache Tomcat como servidor web (<b>Window -> Preferences -> Server -> Runtime Environments -> Add...</b>). 
9. Na aba Server (parte inferior do Eclipse), dar duplo clique em <b>Tomcat v7 Server</b>. No arquivo que abrir, selecionar a opção <b>"Use Tomcat Installation (takes control of Tomcat installation)"</b> na área Server Locations. 
10. Iniciar o Apache Tomcat.
11. Abrir a pasta do projeto MedCEP no IDE Eclipse.
12. Procurar pelo arquivo <b>"build.xml"</b>.
13. Clicar com o botão direito do mouse, selecionar <b>"Run as"</b> e escolher a 3º opção <b>"Ant Build..."</b>.
14. Defina um nome, por exemplo <b>"MedCEP.Implantar"</b>, depois clique em <b>Apply</b>.
15. Clique em <b>"Run"</b>.
16. Executar a Ant Build <b>"MedCEP.Implantar"</b>.
17. Abrir a aplicação no browser pela URL: [http://localhost:8080/MedCEP](http://localhost:8080/MedCEP).

Obs.: Caso, após a instrução 17, não tenha sido possível abrir a aplicação através da URL via browser, faça:

1. Pare o Apache Tomcat.
2. Inicie novamente o Apache Tomcat.
3. Execute a Ant Build <b>"MedCEP.Implantar"</b>.
4. Abra a aplicação no browser pela URL: [http://localhost:8080/MedCEP](http://localhost:8080/MedCEP).

## Referência
C. X. MARETTO and M. P. BARCELLOS, <b>“Uma Arquitetura de Referência para Medição de Software,”</b> in XIII Simpósio Brasileiro de Qualidade de Software (SBQS 2014), 2014.
