package gov.nih.nci.qa.selenium.enumerations;

public enum Summary4ReportMessage {
	// No error message is present.
	NO_ERROR_FOUND,
	
	// "An Organization name is required."
	ORG_NAME_REQUIRED,
	
	// "A Start Date is required."
	START_DATE_REQUIRED,
	
	// "An End Date is required."
	END_DATE_REQUIRED,
	
	// If both date fields are left empty.
	START_AND_END_DATE_REQUIRED,
	
	// "Invalid date."
	INVALID_DATE,
	
	// "ERROR: End date must not be in the future."
	INVALID_END_DATE,
	
	// "ERROR: End date must not be before start date."
	DATE_INTERVAL_ERROR;
}
