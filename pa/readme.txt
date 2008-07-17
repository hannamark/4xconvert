Directory Structure
-------------------

  * dbscripts/
    Contains database scripts used to set up a subset of the ctods schema used by PA along
    with additional application specific tables (e.g. table for persistant session info).
  * lib/
    Contains dependent jars.
  * pa-ear/
    Sub-project that packages all PA services and components for deployement in JBoss
  * pa-ejb/
    Sub-project that build the EJB based PA code
  * pa-web/
    Sub-project for web-based UI for abstracting protocols
  * resources/
    Contains additional resources available to all applications.
  * build.properties.example
    File to override default build (Ant) properties. To use rename to 'build.properties' 
    and alter values within renamed file (Note: Do not commit your local 'build.properties' file to CM repository)
  * .checkstyle, .classpath, .project
    Project specific files used by the eclipse IDE
  * build.xml
    Master build script  
  * README.txt
    <this file>


Development Environment
-----------------------

Build tool - Ant 1.7.0

Database - Postgres 8.2.5

App Server - JBoss 4.0.5 w/ ejb3:
    1. Download the JBoss 4.0.5 JEMS Installer (1.2.0)
        http://sourceforge.net/project/downloading.php?group_id=22866&filename=jems-installer-1.2.0.GA.jar&70038359
    2. Run jems-installer-1.2.0.GA.jar as an executable Java JAR.
    2.1 Double click the jar file, it will open an excutable window, if it doesn't popup a window execute this command in 
    	the directory of the jems-installer-1.2.0.jar (java -jar jems-installer-1.2.0.GA.jar)
    3. Specify C:/dev/app_servers/jboss-4.0.5.GA as the installation location.
    4. Choose "ejb3" as the installation profile type, click Next.
    5. Leave the "default packs" selected, click Next.
    6. Choose "Standard" installation, click Next.
    7. Add a system environment variable called JBOSS_HOME and set it to /dev/app_servers/jboss-4.0.5.GA (Windows) 
        or /Applications/jboss-4.0.5.GA (Mac).
        
IDE - Eclipse
    1. Plugins:
              * Subclipse - http://subclipse.tigris.org/update_1.2.x
              * PMD - http://pmd.sourceforge.net/eclipse
              * Checkstyle - http://eclipse-cs.sourceforge.net/update
    2. Disable tabs:
        Select Windows > General > Editor > Text Editor > 
	  Displayed tab width = 4
	  check insert spaces for tab
	  check show print margin
	  set print margin column as 80
	  

Build/Deployment Steps
----------------------

Setup database:
    Download
    1. download & install from http://wwwmaster.postgresql.org/download/mirrors-ftp?file=%2Fbinary%2Fv8.3.0%2Fwin32%2Fpostgresql-8.3.0-1.zip
    1.  Create "ctods" database
    2.  Create "ctods" login as a superuser with the password "ctods"
    3.  Run the following scripts
        - dbscripts/csmCreateSchema.sql
        - dbscripts/csmBootstrapData.sql
        - dbscripts/PGctods.sql
        - dbscripts/PGctodsInsert.sql

Build and deploy applications:
    1.  copy build.properties using build.properties.example as a template.
    	1.1 right click the file/TortoiseSVN/Add to ignore list, this will make we are not adding our local property file by mistake
    2.  Run command "ant deploy" to build and deploy applications
    3.  Start JBoss
    4.  Run command "ant test-integration" to test that ejb's are running
    5.  Point browser at http://localhost:8080/pa to test web application (userid/password = curator/pass)
    
    
Setting up the Eclipse IDE
--------------------------

-Set up a variable in the Eclipse.Go to Preferences->Java->Build Path->Classpath Variables
-Click New
-Enter the name as JBOSS_HOME
-Enter the path for the JBOSS_HOME directory

To run selenium tests:
----------------------
Change the jboss port number in the build.properties
Install Selenium IDE in Firefox as addon.
Execute a workflow
Save it as a java file with 'MANDATORY' name  xxxxSeleniumxxx.java
use ant clean run-selenium 
	- The jboss will start
	- Selenium server will start
	- Browser window(s) open (Wait for the tests to execute)
    
        
        
Setting the CSM to enable Login:
---------------------------------
 1) Download CSM from:  
    https://gforge.nci.nih.gov/frs/?group_id=12
    Package: CSM_API_4_0_Source.zip
 2) Add the following entries to your build.properties:
 test.java.security.login.config=D:/project/coppa/trunk/code/pa/pa-web/src/test/resources/login.config
     
 In order to deploy csm-upt:
 ----------------------------
 1) Download UPT package from: 
 	https://gforge.nci.nih.gov/frs/?group_id=12
 	Package: CSM_UPT_4_0_Source.zip
 	
 
 2) Add the following entries to your login-config.xml:
 
  <!-- The configuration for CSMUPT -->
        <application-policy name = "csmupt">
         <authentication>
       		<login-module code = "gov.nih.nci.security.authentication.loginmodules.RDBMSLoginModule" flag = "sufficient">
       			<module-option name="driver">org.postgresql.Driver</module-option>
       			<module-option name="url">jdbc:postgresql://localhost:5432/ctods</module-option>
       			<module-option name="user">ctods</module-option>
       			<module-option name="passwd">ctods</module-option>
       			<module-option name="query">SELECT * FROM csm_user WHERE login_name=? and password=?</module-option>
       			<module-option name="encryption-enabled">YES</module-option>
       		</login-module>
         </authentication>
    </application-policy>
  
 3) The default dialect in upt.war is set to MYSQL so in order to make in work on PostgreSQL database 
    change the file: csmupt.csm.new.hibernate.cfg.xml (in upt.war)  With the following line: 
    <property name="dialect">org.hibernate.dialect.PostgreSQLDialect</property>
 4) In order to execute the JUnit test for login we need to:
 	Set the build.property file with the following entry:
 	test.java.security.login.config=/location of top resource folder/login.config
 	

Continuous Integration     
----------------------
1)  Current build targets on CI box
	- deploy
2)  Url is http://nci-reinharh-1.nci.nih.gov:48080/hudson/job/PA/   