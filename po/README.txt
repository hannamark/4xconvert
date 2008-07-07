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
    