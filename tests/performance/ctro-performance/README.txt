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
Timings for elements on a page. Here's a breakdown of what the columns in the csv output mean.

	name: Returns Simon name. Simon name is always fully qualified and determines also position of the Simon in the monitor hierarchy. Simon name can be null for anonymous Simons.
	total: Returns total sum of all split times in nanoseconds.
	counter: Returns usage count of the stopwatch. Counter is increased by addTime and stop - that means that it's updated every time the next time split is added.
	min: Returns minimal time split value in nanoseconds.
	max: Returns maximal time split value in nanoseconds.
	minTimestamp: Returns ms timestamp when the min value was measured.
	maxTimestamp: Returns ms timestamp when the max value was measured.
	maxActive: Returns peek value of active concurrent splits.
	maxActiveTimestamp: Retruns ms timestamp when the last peek of the active split count occured.
	last: Returns value of the last added split - wheter it was added directly or with stop method.
	mean: Returns mean value (average) of all measured values.
	standardDeviation: Returns standard deviation for all measured values.
	note: Returns note for the Simon. Note enables Simon with an additional information in human readable form.
	firstUsage: Returns ms timestamp of the first usage of this Simon. First and last usage are updated when monitor performs the measuring (start/stop/count/etc). They are not updated when values are obtained from the monitor.
	lastUsage: Returns ms timestamp of the last usage of this Simon. First and last usage are updated when monitor performs the measuring (start/stop/count/etc). They are not updated when values are obtained from the monitor.

table.rows.csv=tableRowCount.csv
Row counts for all the tables.
