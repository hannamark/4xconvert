package gov.nih.nci.coppa.services.structuralroles.identifiedperson.common;

import javax.xml.namespace.QName;


public interface IdentifiedPersonConstants {
	public static final String SERVICE_NS = "http://enterpriseservices.nci.nih.gov/structuralroles/IdentifiedPerson";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "IdentifiedPersonKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "IdentifiedPersonResourceProperties");

	//Service level metadata (exposed as resouce properties)
	
}
