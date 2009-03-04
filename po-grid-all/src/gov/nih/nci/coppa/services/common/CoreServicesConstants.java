package gov.nih.nci.coppa.services.common;

import javax.xml.namespace.QName;


public interface CoreServicesConstants {
	public static final String SERVICE_NS = "http://enterpriseservices.nci.nih.gov/CoreServices";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "CoreServicesKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "CoreServicesResourceProperties");

	//Service level metadata (exposed as resouce properties)
	public static final QName SERVICEMETADATA = new QName("gme://caGrid.caBIG/1.0/gov.nih.nci.cagrid.metadata", "ServiceMetadata");
	
}
