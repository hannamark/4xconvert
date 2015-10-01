IDE Setup

For Eclipse, install plugins for Maven, PMD, and Checkstyle. Having PMD and Checkstyle plugins will help detect static code analysis problems earlier, before the build does.


Maven Commands

"mvn -Pquick clean install" to build po.ear quickly without running tests.
"mvn clean install" to build po.ear and run all the unit and integration tests.
"mvn -Djboss.home=<po jboss home> cargo:deploy" will copy po.ear into JBoss deployment directory.


Pre-commit Check

- "mvn clean install" must pass before committing. 
- Functional tests must pass as well (see below).


Functional Tests

This WILL DROP AND RE-CREATE YOUR DATABASE!

- Stop JBoss
- mvn -Pquick clean install
- mvn -Djboss.home=<po jboss home> cargo:deploy
- mvn -Pinit-db,local sql:execute   
- Start JBoss
- mvn -Plocal,ci-nostart-nodeploy -Dtest.selenium.xvfb.skip=true -f client/pom.xml integration-test
- Stop JBoss
- mvn -Pinit-db,local sql:execute    
- Start JBoss
- mvn -Plocal,ci-nostart-nodeploy  -f client/pom.xml -Dtest=gov.nih.nci.coppa.test.integration.test.AllSeleniumTests integration-test


JRebel

JRebel greatly reduces deployment overhead for Java EE applications, such as PO. Please install and configure. 

