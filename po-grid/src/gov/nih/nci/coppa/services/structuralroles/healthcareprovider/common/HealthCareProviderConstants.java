package gov.nih.nci.coppa.services.structuralroles.healthcareprovider.common;

import javax.xml.namespace.QName;


public interface HealthCareProviderConstants {
	public static final String SERVICE_NS = "http://enterpriseservices.nci.nih.gov/structuralroles/HealthCareProvider";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "HealthCareProviderKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "HealthCareProviderResourceProperties");

	//Service level metadata (exposed as resouce properties)
	
}
