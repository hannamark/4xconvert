package gov.nih.nci.qa.selenium.enumerations;

public enum AdHocReportMessage {
	
	// If no error is found.
	NO_ERROR_FOUND,
	
	// General error
	GENERAL_ERROR,
	
	// At least one criteria is required.
	CRITERIA_ERROR,
	
	// Results exceed more than 500.
	RESULTS_EXCEEDED_500,
	
	// An error has occurred when searching for trials.
	ERROR_SEARCHING;
	
}
