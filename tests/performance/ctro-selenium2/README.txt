This project leverages JUnit4 and Selenium 2 to act as a test harness for the CTRO viewer application. It specifically tests the "Ad Hoc" and "Summary 4 Type" reports with a combination of functional and non-functional scenarios. The test harness assumes a database seeded with sample data known to return varying sets of records.

Notable settings:

Target URL:
To change the URL modify the HomePage.java load() method. The URL should be the root of the application (ex: "http://192.168.0.100:8080/viewer").

Selenium 2 browser driver:
The FirefoxDriver for Selenium 2 is currently used in each test class. Selenium 2 offers a variety of possible browser drivers. To change browser drivers the test class @Before method must be modified with the name of the desired driver class (ex: "HtmlUnitDriver(true)" versus "FirefoxDriver()").  

Execution:

Eclipse IDE
Eclipse project file(s) and all dependencies to execute this project are checked in. To execute a individual test via Eclipse open a test class and execute the JUnit4 annotated method. Each test method may be executed/debugged individually.

Ant
The default target is test which will execute all classes that end with "**Test.java". Output is placed in target/test-reports.

