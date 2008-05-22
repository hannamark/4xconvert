package gov.nih.nci.coppa.pa.service;

public class ProtocolSearchCriteria {    
	private String accessionNumber = "NCI_IDENTIFIER";
	private String officialTitle = "LONG_TITLE_TEXT";
	
	public String getAccessionNumber() {
		return accessionNumber;
	}
	
	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}
	
	public String getOfficialTitle() {
		return officialTitle;
	}
	
	public void setOfficialTitle(String officialTitle) {
		this.officialTitle = officialTitle;
	}
}