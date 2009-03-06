package gov.nih.nci.coppa.services.structuralroles.organizationalcontact.common;

import javax.xml.namespace.QName;


public interface OrganizationalContactConstants {
	public static final String SERVICE_NS = "http://enterpriseservices.nci.nih.gov/structuralroles/OrganizationalContact";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "OrganizationalContactKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "OrganizationalContactResourceProperties");

	//Service level metadata (exposed as resouce properties)
	
}
