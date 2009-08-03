To set up development environment:

    - Check out COPPA project from svn (http://gforge.nci.nih.gov/svnroot/coppa/trunk).
    - Install latest Postgres.
    - Install PA application suite (see code/pa/readme.txt for instructions).
    - Install JBoss 4.0.5 using jems installer w/support for ejb3.
    - Create build.properties file using build.properties.template.
    - Run command ant ivy-get.
    - Run command .ant deploy.
    - Start JBoss and test login at http://localhost:8080/accrual.
    - Install the latest version of Eclipse.
    - Install Sublipse (http://subclipse.tigris.org/update_1.6.x).
    - Install PMD plugin (http://pmd.sourceforge.net/eclipse).
    - Install Checkstyle plugin (http://eclipse-cs.sf.net/update).
    - Import Accrual project into workspace.
