package gov.nih.nci.coppa.services.structuralroles.identifiedorganization.common;

import javax.xml.namespace.QName;


public interface IdentifiedOrganizationConstants {
	public static final String SERVICE_NS = "http://enterpriseservices.nci.nih.gov/structuralroles/IdentifiedOrganization";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "IdentifiedOrganizationKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "IdentifiedOrganizationResourceProperties");

	//Service level metadata (exposed as resouce properties)
	
}
