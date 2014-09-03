To set up development environment:

    - Install PA application suite (see code/pa/readme.txt for instructions).
    - Run command ant deploy.
    - Start JBoss and test login at http://localhost:8080/accrual.
    - Install the latest version of Eclipse.
        - Install Sublipse (http://subclipse.tigris.org/update_1.6.x).
        - Install PMD plugin (http://pmd.sourceforge.net/eclipse).
        - Install Checkstyle plugin (http://eclipse-cs.sf.net/update).
        - Create a classpath variable called ACCRUAL_LIB and set it to <coppa checkout>/code/target/accrual/lib
        - Import Accrual project into workspace.


To run selenium tests:
----------------------

Note: the following commands WILL DROP and re-create your PA database!

- Modify build-pa\tier-properties\build-lite-LOCAL.properties: set mock.po=true. PA will use a PO mock rather than calling PO's Remote EJBs.
- On Windows, ensure psexec.exe is available on your PATH. This tool is used to start JBoss process.
- Stop PO & PA JBoss, if running.
- Run "ant test-integration". This will drop, re-create and re-initialize the database, deploy into JBoss, start JBoss, run Selenium tests in Firefox, and stop JBoss.
- You can set up Eclipse to run individual Selenium tests as well.
