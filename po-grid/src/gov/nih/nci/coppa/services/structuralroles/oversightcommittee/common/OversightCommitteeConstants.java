package gov.nih.nci.coppa.services.structuralroles.oversightcommittee.common;

import javax.xml.namespace.QName;


public interface OversightCommitteeConstants {
	public static final String SERVICE_NS = "http://enterpriseservices.nci.nih.gov/structuralroles/OversightCommittee";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "OversightCommitteeKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "OversightCommitteeResourceProperties");

	//Service level metadata (exposed as resouce properties)
	
}
