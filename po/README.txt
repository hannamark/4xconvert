
1.    PO Source Code Structure and Build Process

1.1    Source Tree Structure

  * services 
    Sub-project that packages all PO services for deployment
  * ear
    deployment archive for the application
  * web
    Sub-project for web-based UI for P, O, and correlation services
  * client
    Integration tests including Selenium UI tests.  
  * pom.xml
    Top level Maven project file
  * profiles.xml.example
    File to override default build (Maven) properties. To use, rename to 'profiles.xml' and alter values within renamed file (Note: Do not commit your local 'profiles.xml' file to CM repository).
    The default jboss.home value is "/Applications/jboss-4.0.5.GA," the default JBoss location on a Mac.  If you are on a different platform or have JBoss installed to a different location, set
    the value accordingly in profiles.xml.
  * README.txt
    <this file>

1.2 Development Environment Setup

  * Install maven 2.2
  * Install postgres 9.2
    You'll have to set max_prepared_transactions to 5 in postgresql.conf (on a Mac, that file is located in /Library/PostgreSQL/8.4/data/);
    you'll need to restart Postgres after changing the value.

  * Install JBoss 6.2 EAP    
    Please read build-po/readme.txt for setup instructions; you need to execute build-po install at least once.
    From build-po, run: ant deploy:local:install
    Note location of jboss install. If the jboss location in build-po has been changed from the default,
    set the jboss.home property in profiles.xml files to match.

  * Eclipse

    Install Eclipse maven plugin: http://m2eclipse.sonatype.org/update/
    Install plugins:
        * Subclipse - http://subclipse.tigris.org/update_1.6.x
        * PMD - http://pmd.sourceforge.net/eclipse
        * Checkstyle - http://eclipse-cs.sourceforge.net/update
  * Initial DB setup

    You should have created a database during the build-po install. 

1.3 Full CI build (WILL DROP AND RE-CREATE YOUR DATABASE!)
    Modify your JBoss start-up script to include the following JVM option: -Dctrp.env.ci=true
    	Example: set JAVA_OPTS=-Dctrp.env.ci=true -Xmx1303M -XX:MaxPermSize=256M -Djava.net.preferIPv4Stack=true -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=9700 -Xdebug -Dorg.apache.catalina.connector.URI_ENCODING=UTF-8
    Stop JBoss
    mvn clean install
    mvn cargo:deploy
    mvn -Pinit-db sql:execute    
    Start JBoss
    mvn -Pci-nostart-nodeploy -Dtest.selenium.xvfb.skip=true -f client/pom.xml integration-test
	Stop JBoss
	mvn -Pinit-db sql:execute    
	Start JBoss
	mvn -Pci-nostart-nodeploy -Dtest.selenium.xvfb.skip=true -f client/pom.xml -Dtest=gov.nih.nci.coppa.test.integration.test.AllSeleniumTests integration-test
    mvn site

1.4 Useful, non standard mvn targets

    mvn cargo:deploy - deploys to jboss    
    mvn -Pinit-db sql:execute - reinit the db by completely dropping and recreating. WILL DROP AND RE-CREATE YOUR DATABASE!
    
1.5 Power-Maven Usage
    Deploy and run your integration tests to an already running container:
		mvn cargo:deploy
		mvn -Pci-nostart-nodeploy -Dtest.selenium.xvfb.skip=true -f client/pom.xml integration-test

    Run your integration tests against an already running container:
		mvn -Pci-nostart-nodeploy -Dtest.selenium.xvfb.skip=true -f client/pom.xml integration-test
	OR:
		mvn -Pci-nostart-nodeploy -Dtest.selenium.xvfb.skip=true -f client/pom.xml integration-test -Dtest=<testclass>
		

    Run only the Selenium tests:
		Start and deploy to JBoss
		mvn -Pci-nostart-nodeploy -Dtest.selenium.xvfb.skip=true -f client/pom.xml integration-test -Dtest=gov.nih.nci.coppa.test.integration.test.AllSeleniumTests
	
	Run a specific Selenium test:
	    mvn -Pci-nostart-nodeploy -Dtest.selenium.xvfb.skip=true -f client/pom.xml integration-test -Dtest=gov.nih.nci.coppa.test.integration.test.CreatePersonTest

1.6 Peer Review
    We are using NCI Fisheye/Crucible for peer reviews.  Follow the directions on the wiki for setup and use instructions.    

1.7 Pre-commit check
    Verify everything is working properly by running the CI workflow outlined above.

1.8 Logging into applications:
    Username/passwords use grid based authentication. Users must have a grid account to access any app.
    For testing default account it: curator/Coppa#12345