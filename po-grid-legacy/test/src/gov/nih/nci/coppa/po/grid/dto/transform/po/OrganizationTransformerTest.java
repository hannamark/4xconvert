package gov.nih.nci.coppa.po.grid.dto.transform.po;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.grid.dto.transform.po.OrganizationTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.ADTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.DSETTelTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.ENONTransformerTest;
import gov.nih.nci.services.organization.OrganizationDTO;

import org.iso._21090.II;


public class OrganizationTransformerTest extends 
	AbstractTransformerTestBase<OrganizationTransformer, Organization, OrganizationDTO> {
	/**
     * The identifier name for org ii's.
     */
    public static final String ORG_IDENTIFIER_NAME = "NCI organization entity identifier";

    /**
     * The ii root value for orgs.
     */
    public static final String ORG_ROOT = "2.16.840.1.113883.3.26.4.2";
	@Override
	public OrganizationDTO makeDtoSimple() {
		 Ii id = new Ii();
		 id.setRoot(ORG_ROOT);
		 id.setIdentifierName(ORG_IDENTIFIER_NAME);
		 id.setExtension("123");
		 OrganizationDTO dto = new OrganizationDTO();
		 dto.setIdentifier(id);
		 dto.setStatusCode(new CDTransformerTest().makeDtoSimple());
		 dto.setTelecomAddress(new DSETTelTransformerTest().makeDtoSimple());
		 dto.setPostalAddress(new ADTransformerTest().makeDtoSimple());
		 dto.setName(new ENONTransformerTest().makeDtoSimple());
		 return dto;
	}
	@Override
	public Organization makeXmlSimple() {
		 II id = new II();
		 id.setRoot(ORG_ROOT);
		 id.setIdentifierName(ORG_IDENTIFIER_NAME);
		 id.setExtension("123");
		 Organization xml = new Organization();
		 xml.setIdentifier(id);
		 xml.setStatusCode(new CDTransformerTest().makeXmlSimple());
		 xml.setTelecomAddress(new DSETTelTransformerTest().makeXmlSimple());
		 xml.setPostalAddress(new ADTransformerTest().makeXmlSimple());
		 xml.setName(new ENONTransformerTest().makeXmlSimple());
		 return xml;
	}

	@Override
	public void verifyDtoSimple(OrganizationDTO x) {
		assertEquals(x.getIdentifier().getExtension(), "123");
		assertEquals(x.getIdentifier().getIdentifierName(),ORG_IDENTIFIER_NAME);
		new CDTransformerTest().verifyDtoSimple(x.getStatusCode());
		new DSETTelTransformerTest().verifyDtoSimple(x.getTelecomAddress());
		new ADTransformerTest().verifyDtoSimple(x.getPostalAddress());
		new ENONTransformerTest().verifyDtoSimple(x.getName());
	}

	@Override
	public void verifyXmlSimple(Organization x) {
		assertEquals(x.getIdentifier().getExtension(), "123");
		assertEquals(x.getIdentifier().getIdentifierName(),ORG_IDENTIFIER_NAME);
		new CDTransformerTest().verifyXmlSimple(x.getStatusCode());
		new DSETTelTransformerTest().verifyXmlSimple(x.getTelecomAddress());
		new ADTransformerTest().verifyXmlSimple(x.getPostalAddress());
		new ENONTransformerTest().verifyXmlSimple(x.getName());
	}

}
