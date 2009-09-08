
1.    PO Source Code Structure and Build Process

1.1    Source Tree Structure

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
    File to override default build (Maven) properties. To use, rename to 'profiles.xml' and alter values within renamed file (Note: Do not commit your local 'profiles.xml' file to CM repository).
    The default jboss.home value is "/Applications/jboss-4.0.5.GA," the default JBoss location on a Mac.  If you are on a different platform or have JBoss installed to a different location, set
    the value accordingly in profiles.xml.
  * README.txt
    <this file>

1.2 Development Environment Setup

  * Install maven (if needed)
    PO is not compatible with maven 2.1.0+, so you'll need to use 2.0.10 or earlier.

  * Install JBoss
    Download JEMS installer
    Install this version of JBoss to C:\dev\app_servers\jboss-4.0.5.GA  (If you choose another location your profiles.xml file will need to be modified)
    Copy the postgres jar in to C:\dev\app_servers\jboss-4.0.5.GA\server\default\lib

Note: the EJB3 deployer used comes from the JEMS installer 1.2.0.GA (http://www.jboss.org/jemsinstaller/downloads/)

  * Eclipse

    Install Eclipse maven plugin: http://m2eclipse.sonatype.org/update/
    Need to add other plugins here as well

  * Initial DB setup

    From the services/ directory, run:
        mvn -Plocal,init-db sql:execute

1.3 CI build (Maven 2.0.9)
    mvn -Plocal,init-db sql:execute
    mvn -Plocal clean install
    mvn -Pci,local integration-test
    mvn -Plocal site verify

1.4 Useful, non standard mvn targets

    mvn nci-commons:jboss-undeploy  - removes the deployable from jboss.
    mvn nci-commons:hbm2ddl - generates the hibernate scheam.sql - usefull when writing migration scripts.
    mvn cargo:deploy - deploys to jboss
    mvn -Pinit-db sql:execute - reinit the db by completely dropping and recreating.
    mvn liquibase:update run the liquibase update process, thus creating the schema (if needed) and bring the app in to line with the latest.
        ** Note this is run as part of nearly every build cycle, so there is no need to run it unless
            you are trying to recreate the db without running the full build.  Also, it will ONLY work in the
            services sub directory.

1.5 Power-Maven Usage
    Deploy and run your integration tests to an already running container
    mvn -Pci-nostart integration-test

    Run your integration tests against an already running container.
    cd client
    mvn -Pci-nostart integration-test
    OR
    mvn -Pci-nostart integration-test -Dtest=<testclass>

    Run just the Selenium tests
     - Start and deploy to JBoss

    cd services
    mvn -Pinit-db sql:execute
    mvn liquibase:update
    cd ..

    or on unix,
    pushd services/; mvn -Pinit-db sql:execute && mvn liquibase:update; popd

    cd client
    mvn -Pci-nostart-nodeploy integration-test -Dtest=gov.nih.nci.coppa.test.integration.test.AllSeleniumTests

1.6 Peer Review
    We are using ReviewBoard for peer reviews.  Follow the directions on the wiki for setup and use instructions.
    The ReviewBoard group for all po developers is (no quotes) 'po-all'.

1.7 Pre-commit check
    Verify everything is working properly by running 'mvn -Plocal,nuke-db sql:execute && mvn -Plocal clean install sql:execute && mvn -Pci,local integration-test'
