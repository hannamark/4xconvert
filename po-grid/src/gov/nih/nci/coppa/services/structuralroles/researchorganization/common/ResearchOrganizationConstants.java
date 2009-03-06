package gov.nih.nci.coppa.services.structuralroles.researchorganization.common;

import javax.xml.namespace.QName;


public interface ResearchOrganizationConstants {
	public static final String SERVICE_NS = "http://enterpriseservices.nci.nih.gov/structuralroles/ResearchOrganization";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "ResearchOrganizationKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "ResearchOrganizationResourceProperties");

	//Service level metadata (exposed as resouce properties)
	
}
