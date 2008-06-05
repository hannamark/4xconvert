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
    1.  Create "ctods" database
    2.  Create "ctods" login as a superuser with the password "ctods"
    3.  Run the following scripts
        - dbscripts/csmCreateSchema.sql
        - dbscripts/csmBootstrapData.sql
        - dbscripts/PGctods.sql
        - dbscripts/PGctodsInsert.sql

Build and deploy applications:
    1.  Create build.properties using build.properties.example as a template.
    2.  Run command "ant deploy" to build and deploy applications
    3.  Start JBoss
    4.  Run command "ant test-integration" to test that ejb's are running
    5.  Point browser at http://localhost:8080/pa to test web application
    
    
        
        
        