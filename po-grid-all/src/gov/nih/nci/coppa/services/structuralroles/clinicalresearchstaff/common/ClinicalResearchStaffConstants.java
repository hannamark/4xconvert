package gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.common;

import javax.xml.namespace.QName;


public interface ClinicalResearchStaffConstants {
	public static final String SERVICE_NS = "http://enterpriseservices.nci.nih.gov/structuralroles/ClinicalResearchStaff";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "ClinicalResearchStaffKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "ClinicalResearchStaffResourceProperties");

	//Service level metadata (exposed as resouce properties)
	
}
