To set up development environment:

    - Check out COPPA project from svn (http://gforge.nci.nih.gov/svnroot/coppa/trunk).
    - Install latest Postgres.
    - Install PA application suite (see code/pa/readme.txt for instructions).
    - Install JBoss 4.0.5 using jems installer w/support for ejb3.
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
    - Create build.properties file using build.properties.template.
    - Run command ant ivy-get.
    - Run command .ant deploy.
    - Start JBoss and test login at http://localhost:8080/accrual.
    - Install the latest version of Eclipse.
    - Install Sublipse (http://subclipse.tigris.org/update_1.6.x).
    - Install PMD plugin (http://pmd.sourceforge.net/eclipse).
    - Install Checkstyle plugin (http://eclipse-cs.sf.net/update).
    - Import Accrual project into workspace.
