package gov.nih.nci.qa.selenium.Cucumber.Login;

import org.junit.runner.RunWith;

import cucumber.junit.Cucumber;
import cucumber.junit.Feature;

@RunWith(Cucumber.class)
@Feature("Login.feature")
public class LoginTest {
	// See
	// http://thomassundberg.wordpress.com/2011/10/31/why-separate-test-definitions-from-a-driving-junit-class/
	// for the reasoning why the test and the steps are in different classes.
}
