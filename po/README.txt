
1.	PO Source Code Structure and Build Process

1.1	Source Tree Structure

  * services /
    Sub-project that packages all PO services for deployment
  * services / common
    Common services, including PO data model, persistence, generated DTO
  * services / grid
    caGrid analytic service
  * services / ear
    deployment archive for the services

  * applications /
    Sub-project for packaging relevant applications for deployment
  * applications / web
    Sub-project for web-based UI for P, O, and correlation services
  * applications / ear
    Sub-project to package the applications in an ear.

  * pom.xml
    Top level Maven project file
  * profiles.xml.example
    File to override default build (Maven) properties. To use rename to 'profiles.xml' and alter values within renamed file (Note: Do not commit your local 'profiles.xml' file to CM repository)
  * README.txt
    <this file>

1.2 Development Environment Setup

  * Install JBoss
    Download JEMS installer
    Install this version of JBoss to C:\dev\app_servers\jboss-4.0.5.GA  (If you choose another location your profiles.xml file will need to be modified)
    Copy the postgres jar in to C:\dev\app_servers\jboss-4.0.5.GA\server\default\lib

Note: the EJB3 deployer used comes from the JEMS installer 1.2.0.GA (http://www.jboss.org/jemsinstaller/downloads/)

  * Eclipse

    Install Eclipse maven plugin: http://m2eclipse.sonatype.org/update/
    Need to add other plugins here as well

1.3 CI build (Maven 2.0.9)
    mvn -Plocal,nuke-db sql:execute
    mvn -Plocal clean install sql:execute
    mvn -Pci,local integration-test
    mvn -Plocal site verify 

1.4 Power-Maven Usage
    Deploy and run your integration tests to an already running container
    mvn -Pci-nostart integration-test
    
    Run your integration tests against an already running container.
    cd client
    mvn -Pci-nostart integration-test
    OR 
    mvn -Pci-nostart integration-test -Dtest=<testclass>  
    
    Run just the Selenium tests
     - Start and deploy to JBoss
    mvn sql:execute
    mvn -Pci-nostart-nodeploy integration-test -Dtest=gov.nih.nci.coppa.test.integration.test.AllSeleniumTests
    
