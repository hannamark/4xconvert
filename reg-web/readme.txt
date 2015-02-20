Setup instructions for pa-reg-web
---------------------------------

- Installation is handled by build-pa. Take a look at build-pa\README.txt.
- "ant deploy" will redeploy
- "ant deploy-notest" will skip tests and redeploy
- Point browser at url http://localhost:<port>/registry
- Valid account is curator/pass

To run selenium tests:
----------------------

Note: the following commands WILL DROP and re-create your PA database!

- Modify your JBoss start-up script to include the following JVM option: -Dctrp.env.ci=true
-- Example: set JAVA_OPTS=-Dctrp.env.ci=true -Xmx1303M -XX:MaxPermSize=256M -Djava.net.preferIPv4Stack=true -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=9700 -Xdebug -Dorg.apache.catalina.connector.URI_ENCODING=UTF-8
- Modify build-pa\tier-properties\build-lite-LOCAL.properties: set mock.po=true. PA/Registry will use a PO mock rather than calling PO's Remote EJBs.
- On Windows, ensure psexec.exe is available on your PATH. This tool is used to start JBoss process.
- Stop PO & PA JBoss, if running.
- Run "ant test-integration". This will drop, re-create and re-initialize the database, deploy PA/Registry into JBoss, start JBoss, run Selenium tests in Firefox, and stop JBoss.
- Once you've done "ant test-integration", you can run Selenium tests quicker by skipping the initialization process. Start JBoss manually and run "ant run-selenium-tests".
- Tests run the order they are declared in AllSeleniumTests.java. If you need to add a new test, make sure you add
  it there or it will no be run automatically.
- You can set up Eclipse to run individual Selenium tests as well.

Logging into applications:
--------------------------
    Username/passwords use grid based authentication. Users must have a grid account to access any app.
    For Registry, a user must create a registry account by either linking their current grid acount or
    creating a new grid account through the registry app.    
    Default users to try are:
    abstractor/Coppa#12345
    SuAbstractor/Coppa#12345
    superabstractor/Coppa#12345





