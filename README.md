# MedCEP
<img src="https://github.com/vinnysoft/MedCEP/blob/master/Addons/web/naviox/images/nemo.jpg" alt="Logo NEMO" width="140px" height="120px"/> <img src="https://github.com/vinnysoft/MedCEP/blob/master/Addons/web/naviox/images/ufes.png" alt="Logo UFES" width="140px" height="120px"/>

Ferramenta para Medição de Software e Controle Estatístico de Processos. 
<br/>Baseada na arquitetura de referência de medição de software de MARETTO (2014).

##Requisitos
- Java SDK 7 Update 75 (x64) - [Download] (http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk7-downloads-1880260.html)
- IDE Eclipse Luna for EE Developers (x64) SR2 - [Download] (https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/luna/SR2/eclipse-jee-luna-SR2-win32-x86_64.zip)
- PostgreSQL 9.4.1 (x64) - [Download] (http://www.enterprisedb.com/products-services-training/pgdownload)
- Apache Tomcat 7.0.59 (x64) - [Download] (http://mirrors.koehn.com/apache/tomcat/tomcat-7/v7.0.59/bin/apache-tomcat-7.0.59-windows-x64.zip)

##Instruções para configuração do ambiente
1. Instalar o Java SDK.
2. Instalar o PostgreSQL com usuário e senha <b>"postgres"</b>.
3. Criar um banco de dados no PostgreSQL chamado <b>"medcep"</b> e informar como dono o usuário <b>"postgres"</b>.
4. Descompactar o Apache Tomcat em C:\apache-tomcat-7.0.59 (caminho padrão). Caso queira executar o Apache Tomcat em outro diretório, atualizar a variável <b>tomcat.dir</b> do arquivo <b>MedCEP/properties/openxava.properties</b>. 
5. Descompactar o IDE Eclipse Luna.
6. Executar o Eclipse.
7. Clonar o repositório MedCEP do GitHub.
8. Configurar o Apache Tomcat como servidor web (<b>Window -> Preferences -> Server -> Runtime Environments -> Add...</b>). 
9. Na aba Server (parte inferior do Eclipse), dar duplo clique em <b>Tomcat v7 Server</b>. No arquivo que abrir, selecionar a opção <b>"Use Tomcat Installation (takes control of Tomcat installation)"</b> na área Server Locations. 
10. Iniciar o Apache Tomcat.
11. Abrir a pasta do projeto MedCEP no IDE Eclipse.
12. Procurar pelo arquivo <b>build.xml</b>.
13. Clicar com o botão direito do mouse, selecionar <b>"Run as"</b> e escolher a 3º opção <b>"Ant Build..."</b>.
14. Defina um nome, por exemplo <b>"MedCEP.Implantar"</b>, depois clique em <b>Apply</b>.
15. Clique em <b>Run</b>.
16. Executar a Ant Build <b>MedCEP.Implantar</b>.
17. Abrir a aplicação no browser pela URL: [http://localhost:8080/MedCEP](http://localhost:8080/MedCEP).

Obs.: Caso, após a instrução 17, não tenha sido possível abrir a aplicação através da URL via browser, faça:

1. Pare o Apache Tomcat.
2. Inicie novamente o Apache Tomcat.
3. Execute a Ant Build <b>MedCEP.Implantar</b>.
4. Abra a aplicação no browser pela URL: [http://localhost:8080/MedCEP](http://localhost:8080/MedCEP).

##Configuração do Jenkins
###Requisitos

- Jenkins CI 1.593 - [Download](http://mirrors.jenkins-ci.org/war/1.593/jenkins.war)

##Instruções para configuração do Jenkins

1. Fazer o download do arquivo <b>"jenkins.war"</b> conforme a URL de download acima.
2. Colocar o arquivo <b>"jenkins.war"</b> no diretório <b>webapps</b> do Apache Tomcat (ex: C:\apache-tomcat-7.0.59\webapps).
3. Abrir o Jenkins no browser pela URL: [http://localhost:8080/jenkins/](http://localhost:8080/jenkins/).

##Configuração do Sonar
###Requisitos

- SonarQube 4.5.1 - [Download](http://dist.sonar.codehaus.org/sonarqube-4.5.1.zip)


<!---
##Configuração do Mantis Bug Tracking
###Requisitos
- Wamp Server 2.5 (Windows + Apache + MySQL + PHP server) (x64) - [Download](http://sourceforge.net/projects/wampserver/files/WampServer%202/Wampserver%202.5/wampserver2.5-Apache-2.4.9-Mysql-5.6.17-php5.5.12-64b.exe/download)
- Mantis Bug Tracking 1.2.19 - [Download] (http://sourceforge.net/projects/mantisbt/files/mantis-stable/1.2.19/mantisbt-1.2.19.zip/download)
 
###Instruções para configuração
1. Instalar o Wamp Server 2.5
2. Alterar o idioma para Português (clicar com botão direito do mouse no ícone do Wamp Server 2.5 na bandeja do Windows, depois em <b>"Language -> portuguese"</b>).
2. Abrir o phpMyAdmin (clicar no ícone do Wamp Server 2.5 na bandeja do Windows, depois em <b>"phpMyAdmin"</b>).
3. Clicar em <b>Utilizadores</b> no menu superior.
4. Clicar em <b>Alterar Privilégios</b> para o utilizador <b>root</b> na máquina <b>localhost</b>.
5. No grupo <b>Alterar a palavra-passe</b>, informar a senha root e clicar em <b>Executar</b>.
3. Clicar em <b>New</b> no menu lateral esquerdo, para criar o banco de dados para o Mantis Bug Tracking.
4. Informar o nome <b>"mantis"</b> e clicar em <b>Criar</b>.
5. O banco de dados <b>"mantis"</b> aparecerá na listagem logo abaixo (na mesma tela). Clicar em <b>Verificar Privilégios</b>.
6. Clicar em <b>Adicionar utilizador</b>.
7. Informar User name: mantis, Host: localhost, Palavra-passe: mantis, Re-type: mantis, clicar em <b>"Executar"</b>.
2. Abrir o diretório <b>"www"</b> (clicar no ícone do Wamp Server 2.5 na bandeja do Windows, depois em <b>"diretório www"</b>).
3. Descompactar o arquivo zip do Mantis Bug Tracking no <b>"diretório www"</b>.
4. Renomear a pasta do Mantis Bug Tracking de <b>"mantisbt-1.2.19"</b> para <b>"mantis"</b>.
5. Acessar a URL: [http://localhost/mantis/](http://localhost/mantis/)
6. Informar <b>Type of Database: MySQL (default), Hostname: localhost, Username: mantis, Password: mantis, Database name: mantis</b>, clicar em <b>"Install/Upgrade Database"</b>.
7. Após a instalação, acessar novamente a URL [http://localhost/mantis/](http://localhost/mantis/)
8. Informar o usuário <b>Administrator</b> e senha <b>root</b> para acessar o Mantis Bug Tracking.
-->

##IMPORTANTE
<b>Os passos informados nas instruções de configuração foram baseados em um ambiente de desenvolvimento. Para ambientes de produção, as variáveis como senha, URL e local de instalação deverão ser alteradas de acordo com cada necessidade.</b>

## Referência
C. X. MARETTO and M. P. BARCELLOS, <b>“Uma Arquitetura de Referência para Medição de Software,”</b> in XIII Simpósio Brasileiro de Qualidade de Software (SBQS 2014), 2014.
