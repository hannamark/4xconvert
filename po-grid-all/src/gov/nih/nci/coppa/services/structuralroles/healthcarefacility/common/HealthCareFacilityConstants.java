package gov.nih.nci.coppa.services.structuralroles.healthcarefacility.common;

import javax.xml.namespace.QName;


public interface HealthCareFacilityConstants {
	public static final String SERVICE_NS = "http://enterpriseservices.nci.nih.gov/structuralroles/HealthCareFacility";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "HealthCareFacilityKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "HealthCareFacilityResourceProperties");

	//Service level metadata (exposed as resouce properties)
	
}
