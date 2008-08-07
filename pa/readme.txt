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
    1.  After install add ant-contrib.jar to the lib direcory.

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
    1.  Create database (any name can be used, must match build.properties file).
    2.  Create corresponding superuser account (any password can be used, must match build.properties file).
    3.  Run the following scripts
        - dbscripts/csmCreateSchema.sql
        - dbscripts/csmBootstrapData.sql
        - dbscripts/PGctods.sql
        - dbscripts/PGctodsInsert.sql

Build and deploy applications:
    1.  copy build.properties using build.properties.example as a template.
        1.1 right click the file/TortoiseSVN/Add to ignore list, this will make we are not adding our local property file by mistake
    2.  Run command "ant deploy" to build and deploy applications
    3.  Copy the generated application policy from 
            <project root>/pa-ear/target/login-config.pa
        into the JBoss servers login-config.xml file.
    4.  Start JBoss
    5.  Run command "ant test-integration" to test that ejb's are running
    6.  Point browser at http://localhost:8080/pa to test web application (userid/password = curator/pass)
    
    
Setting up the Eclipse IDE
--------------------------

-Set up a variable in the Eclipse.Go to Preferences->Java->Build Path->Classpath Variables
-Click New
-Enter the following names and paths (note if the ivy download has not yet been done
 call "ant ivy-get" to force it before setting up eclipse build path variables):
    JBOSS_HOME  - Enter the path for the JBOSS_HOME directory
    PA_COMPILE         = <coppa>\code\target\lib\pa-web\compile
    PA_TEST            = <coppa>\code\target\lib\pa-web\test
    PA_TEST_JUNIT      = <coppa>\code\target\lib\pa-web\test-junit
    PA_TEST_SELENIUM   = <coppa>\code\target\lib\pa-web\test-selenium


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
 test.java.security.login.config= "location of pa on local file system" + /pa/pa-web/src/test/resources/login.config
     
 In order to deploy csm-upt:
 ----------------------------
 1) Download UPT package from: 
    https://gforge.nci.nih.gov/frs/?group_id=12
    Package: CSM_UPT_4_0_Source.zip
    
 2) Copy the generated application policy from 
         <project root>/pa-ear/target/login-config.upt
    into the JBoss servers login-config.xml file.
  
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