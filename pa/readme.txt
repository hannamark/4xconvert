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
  * .checkstyle, .classpath, .project
    Project specific files used by the eclipse IDE
  * build.xml
    Master build script
  * README.txt
    <this file>


Development Environment
-----------------------

Build tool - Ant 1.8    

Database - Postgres 9.2
    You'll have to set max_prepared_transactions to 5 in postgresql.conf (on a Mac, that file is located in /Library/PostgreSQL/8.4/data/);
    you'll need to restart Postgres after changing the value.

App Server - Install JBoss 6.2 EAP
    Installation of JBoss is handled by build-pa.
    Please read build-pa/readme.txt for setup instructions.    

IDE - Eclipse
    1. Install plugins:
        * Subclipse - http://subclipse.tigris.org/update_1.6.x
        * PMD - http://pmd.sourceforge.net/eclipse
        * Checkstyle - http://eclipse-cs.sourceforge.net/update
    2. Disable tabs:
        Select Windows > General > Editor > Text Editor >
            Displayed tab width = 4
            check insert spaces for tab
            check show print margin
            set print margin column as 120
	3. Modify Java Code Formatter to use spaces instead of tabs as well.


Setting up the Eclipse IDE
--------------------------

-Set up a variable in the Eclipse. Go to Preferences->Java->Build Path->Classpath Variables
-Click New
-Enter the following names and paths (note if the ivy download has not yet been done
 call "ant ivy-get" to force it before setting up eclipse build path variables):
    JBOSS_HOME  - Enter the path for the JBOSS_HOME directory
    PA_LIB - Enter the path of <coppa>\code\target\pa\lib


Manual/testing purposes build and deployment steps (unnecessary when using build-pa)
----------------------

Setup database:
    The build-pa install will handle the creation of a db. You may create a different db for use by unit tests, selenium tests,
    etc.
	
    1.  download & install postgres 9.2
    2.  Create database (any name can be used, must match build.properties file).
    3.  Create corresponding superuser account (any password can be used, must match build.properties file).
    4.  Run the following scripts
        - dbscripts/db-install/postgresql/csmCreateSchema.sql
        - dbscripts/db-install/postgresql/csmBootstrapData.sql
        - dbscripts/db-install/postgresql/PG_CTODS_PA.sql
        - dbscripts/db-install/postgresql/PG_CTODS_PA_INSERT.sql

Build and deploy applications:
    Note: Initial deployment is done via build-pa.	
    
    1.  Run command "ant deploy" (or deploy-notest to skip tests and dependency checking) to build and deploy applications.
	2.  Start JBoss
    3.  Point browser at http://localhost:39480/pa to test web application (userid/password = curator/pass)


To run selenium tests:
----------------------

Note: the following commands WILL DROP and re-create your PA database!

- Modify build-pa\tier-properties\build-lite-LOCAL.properties: set mock.po=true. PA will use a PO mock rather than calling PO's Remote EJBs.
- On Windows, ensure psexec.exe is available on your PATH. This tool is used to start JBoss process.
- Stop PO & PA JBoss, if running.
- Run "ant test-integration". This will drop, re-create and re-initialize the database, deploy PA into JBoss, start JBoss, run Selenium tests in Firefox, and stop JBoss.
- Once you've done "ant test-integration", you can run Selenium tests quicker by skipping the initialization process. Start JBoss manually and in pa\pa-web run "ant run-selenium-tests".
- Tests run the order they are declared in AllSeleniumTests.java. If you need to add a new test, make sure you add
  it there or it will no be run automatically.
- You can set up Eclipse to run individual Selenium tests as well.

Logging into applications:
--------------------------
    Username/passwords use grid based authentication. Users must have a grid account to access any app.
    For Registry, a user must create a registry account by either linking their current grid acount or
    creating a new grid account through the registry app.
    For PA a grid user must be in the Abstractor group to get access.
    Default users to try are:
    abstractor/Coppa#12345
    SuAbstractor/Coppa#12345
    superabstractor/Coppa#12345



Continuous Integration
----------------------
1)  Current build targets on CI box
    - deploy
2)  Url is http://ncias-c477-v.nci.nih.gov:48080/jenkins/
