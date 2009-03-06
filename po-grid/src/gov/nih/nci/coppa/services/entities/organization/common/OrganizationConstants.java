package gov.nih.nci.coppa.services.entities.organization.common;

import javax.xml.namespace.QName;


public interface OrganizationConstants {
	public static final String SERVICE_NS = "http://enterpriseservices.nci.nih.gov/entities/Organization";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "OrganizationKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "OrganizationResourceProperties");

	//Service level metadata (exposed as resouce properties)
	
}
