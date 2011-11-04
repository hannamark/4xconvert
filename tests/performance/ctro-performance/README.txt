This project leverages JUnit4 and Selenium 2 to act as a test harness for the CTRO viewer application.

Settings are changed within the config.properties file.

Notable technologies used.

Selenium 2 browser driver:
The FirefoxDriver for Selenium 2 is currently used in each test class. Selenium 2 offers a variety of possible browser drivers. To change browser drivers the test class @Before method must be modified with the name of the desired driver class (ex: "HtmlUnitDriver(true)" versus "FirefoxDriver()").  

Cucumber
This project uses some class implemented using Cucumber (http://cukes.info/) BDD concepts.

Java Simon (Simple monitor)
This project uses a timer called javasimon (http://code.google.com/p/javasimon/).

OpenCSV
Creates CSV files.

Execution:

Eclipse IDE
Eclipse project file(s) and all dependencies to execute this project are checked in. To execute a individual test via Eclipse open a test class and execute the JUnit4 annotated method. Each test method may be executed/debugged individually.

Maven
Use "mvn test" to execute all tests.

Use "mvn test -Dtest=<Test Class Name>" to execute an individual test.

Output:
This project outputs two files configured from the config.properties file.

sample.output.csv=sampleOutput.csv
Timings for page objects.

table.rows.csv=tableRowCount.csv
Row counts for all the tables.
