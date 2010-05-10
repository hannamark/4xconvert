package gov.nih.nci.coppa.pa.grid.dto.transform.pa;

import gov.nih.nci.coppa.services.pa.RegulatoryAuthority;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.RegulatoryAuthorityTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.STTransformerTest;
import gov.nih.nci.pa.iso.dto.RegulatoryAuthorityDTO;

/**
 * Test the RegulatoryAuthority transformer.
 */
public class RegulatoryAuthorityTransformerTest extends
        AbstractTransformerTestBase<RegulatoryAuthorityTransformer, RegulatoryAuthority, RegulatoryAuthorityDTO> {

    @Override
    public RegulatoryAuthorityDTO makeDtoSimple() {
        RegulatoryAuthorityDTO result = new RegulatoryAuthorityDTO();
        result.setIdentifier(new IITransformerTest().makeDtoSimple());
        result.setAuthorityName(new STTransformerTest().makeDtoSimple());
        result.setCountryIdentifier(new IITransformerTest().makeDtoSimple());
        return result;
    }

    @Override
    public RegulatoryAuthority makeXmlSimple() {
        RegulatoryAuthority result = new RegulatoryAuthority();
        result.setIdentifier(new IITransformerTest().makeXmlSimple());
        result.setAuthorityName(new STTransformerTest().makeXmlSimple());
        result.setCountryIdentifier(new IITransformerTest().makeXmlSimple());
        return result;
    }

    @Override
    public void verifyDtoSimple(RegulatoryAuthorityDTO input) {
        new IITransformerTest().verifyDtoSimple(input.getIdentifier());
        new STTransformerTest().verifyDtoSimple(input.getAuthorityName());
        new IITransformerTest().verifyDtoSimple(input.getCountryIdentifier());
    }

    @Override
    public void verifyXmlSimple(RegulatoryAuthority input) {
        new STTransformerTest().verifyXmlSimple(input.getAuthorityName());
        new IITransformerTest().verifyXmlSimple(input.getCountryIdentifier());
        new IITransformerTest().verifyXmlSimple(input.getIdentifier());
    }
}
