package gov.nih.nci.coppa.services.entities.person.common;

import javax.xml.namespace.QName;


public interface PersonConstants {
	public static final String SERVICE_NS = "http://enterpriseservices.nci.nih.gov/CoreServices/Context";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "PersonKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "PersonResourceProperties");

	//Service level metadata (exposed as resouce properties)
	
}
