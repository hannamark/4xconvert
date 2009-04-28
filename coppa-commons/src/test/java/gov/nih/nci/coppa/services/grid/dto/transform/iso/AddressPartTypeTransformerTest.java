package gov.nih.nci.coppa.services.grid.dto.transform.iso;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.AddressPartType;

public class AddressPartTypeTransformerTest extends AbstractTransformerTestBase<AddressPartTypeTransformer,org.iso._21090.AddressPartType,AddressPartType>{

	@Override
	public AddressPartType makeDtoSimple() {
		AddressPartType dto = AddressPartType.ZIP;
		return dto;
	}

	@Override
	public org.iso._21090.AddressPartType makeXmlSimple() {
		org.iso._21090.AddressPartType xml = org.iso._21090.AddressPartType.ZIP;
		return xml;
	}

	@Override
	public void verifyDtoSimple(AddressPartType x) {
		assertEquals(x.ZIP, AddressPartType.ZIP);
		
	}

	@Override
	public void verifyXmlSimple(org.iso._21090.AddressPartType x) {
		assertEquals(x.ZIP, org.iso._21090.AddressPartType.ZIP);
		
	}

}
