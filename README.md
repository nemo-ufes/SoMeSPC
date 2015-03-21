# MedCEP
Ferramenta para Medição de Software e Controle Estatístico de Processos. 
<br/>Baseada na arquitetura de referência de medição de software de MARETTO (2014).

##Ambientes
###Ambiente de Desenvolvimento

- Microsoft Windows 7 x64 SP1
- Java SDK 7 Update 75 (x64) - [Download] (http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk7-downloads-1880260.html)
- IDE Eclipse Luna for EE Developers (x64) SR2 - [Download] (https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/luna/SR2/eclipse-jee-luna-SR2-win32-x86_64.zip)
- PostgreSQL 9.4.1 (x64) - [Download] (http://www.enterprisedb.com/products-services-training/pgdownload)
- Apache Tomcat 7.0.59 (x64) - [Download] (http://mirrors.koehn.com/apache/tomcat/tomcat-7/v7.0.59/bin/apache-tomcat-7.0.59-windows-x64.zip)

##Instruções de instalação
1. Instalar o Java SDK
2. Instalar o PostgreSQL com usuário e senha "postgres"
2. Criar um banco de dados no PostgreSQL chamado "medcep" e informar como dono o usuário "postgres".
2. Descompactar o Apache Tomcat em C:\apache-tomcat-7.0.59
3. Descompactar o Apache Maven em C:\apache-maven-3.3.1
3. Descompacter o IDE Eclipse Luna
4. Executar o Eclipse
5. Baixar o código fonte do GitHub
6. Configurar o Apache Tomcat como servidor web. 
7. Na aba Server (parte inferior do Eclipse), dar duplo clique em Tomcat v7 Server. No arquivo que abrir, selecionar a opção "Use Tomcat Installation (takes control of Tomcat installation)" na área Server Locations. 
8. Iniciar o Apache Tomcat
9. Executar a Ant Build "MedCEP.DeployWar"
10. Abrir a aplicação no browser pela URL: http://localhost:8080/MedCEP

## Referência
C. X. MARETTO and M. P. BARCELLOS, <b>“Uma Arquitetura de Referência para Medição de Software,”</b> in XIII Simpósio Brasileiro de Qualidade de Software (SBQS 2014), 2014.
