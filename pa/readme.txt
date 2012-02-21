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
    1.  After install add ant-contrib-1.0b3.jar to the lib direcory.

Database - Postgres 8.4
    With Postgres 8.4, you'll have to set max_prepared_transactions to 5 in postgresql.conf (on a Mac, that file is located in /Library/PostgreSQL/8.4/data/);
    you'll need to restart Postgres after changing the value.

App Server - Install JBoss
    Installation of JBoss is handled by build-pa.
    Please read build-pa/readme.txt for setup instructions.
    From build-pa, run: ant deploy:local:install
    Note location of jboss install. If it is not the default location used in build-pa/install.properties
    set location as the jboss.home property in your pa/local.properties file.
    Add a system environment variable called JBOSS_HOME and set it to the location created if you wish to
    stop/start the jboss instance manually in the future.

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

BDA
    1. Initialize BDA jars: cd to <coppa>\code\build-pa and run "ant init"

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
    etc. The db settings in build.properties set by default can be overwritten in your local.properties. These
    setting include:
    db.username
    db.password
    jdbc.driver
    jdbc.url

    1.  download & install 8.4+
    2.  Create database (any name can be used, must match build.properties file).
    3.  Create corresponding superuser account (any password can be used, must match build.properties file).
    4.  Run the following scripts
        - dbscripts/db-install/postgresql/csmCreateSchema.sql
        - dbscripts/db-install/postgresql/csmBootstrapData.sql
        - dbscripts/db-install/postgresql/PG_CTODS_PA.sql
        - dbscripts/db-install/postgresql/PG_CTODS_PA_INSERT.sql

Build and deploy applications:
    Note: If building w/out build-pa, reg-web, viewer, and accrual pust be build before pa.
    But, you cannot run "ant deploy" in pa before building reg-web, and you cannot run "any deploy" in reg-web,
    before building pa. Therefore you must simpy run "ant" in reg-web, viewer, and accrual, and then run "ant deploy"
    in pa.
    1.  copy build.properties using build.properties.example as a template.
    2.  Run command "ant deploy" (or deploy-notest to skip tests and dependency checking) to build and deploy applications
    3.  Copy the generated application policy from
            <project root>/pa-ear/target/login-config.pa
        into the JBoss server's login-config.xml file.
    4.  Start JBoss
    5.  Run command "ant test-integration" to test that ejb's are running
    6.  Point browser at http://localhost:39480/pa to test web application (userid/password = curator/pass)


To run selenium tests:
----------------------
Ensure that selenium.server.port is set in build.properties and change the db.name property if you want to preserve
your old data.

- Change db.name to point to db you wish the selenium tests to run against.
- Run the following ant command if you wish to initialize the db anew: ant init-test-db populate-test-db
- Ensure that the pa jboss instance is up and running; Running ant start-jboss-server will start it for you.
- Run the selenium tests with the following: ant start-selenium-server run-selenium-tests stop-selenium-server
- Shutdown the pa jboss instance with: ant stop-jboss-server
- Alternately, you can run ant test-integration if you want to automatic the initialization of the db, starting of jboss,
  running of the tests and stopping the jboss instance.
- Tests run the order they are declared in AllSeleniumTests.java. If you need to add a new test, make sure you add
  it there or it will no be run automatically.

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
