package gov.nih.nci.coppa.po.grid.common;

import javax.xml.namespace.QName;


public interface CoppaPOConstants {
	public static final String SERVICE_NS = "http://grid.po.coppa.nci.nih.gov/CoppaPO";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "CoppaPOKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "CoppaPOResourceProperties");

	//Service level metadata (exposed as resouce properties)
	public static final QName SERVICEMETADATA = new QName("gme://caGrid.caBIG/1.0/gov.nih.nci.cagrid.metadata", "ServiceMetadata");
	
}
