package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.accrual.dto.UserDto;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformerTest;
import gov.nih.nci.coppa.services.outcomes.User;

public class UserTransformerTest extends
     AbstractTransformerTestBase <UserTransformer , User ,UserDto> {
   

    @Override
    public UserDto makeDtoSimple() {
        UserDto returnVal = new UserDto();
        returnVal.setIdentifier(new IITransformerTest().makeDtoSimple());
        returnVal.setAddress(new STTransformerTest().makeDtoSimple());
        returnVal.setAffiliateOrg(new STTransformerTest().makeDtoSimple());
        returnVal.setCity(new STTransformerTest().makeDtoSimple());
        returnVal.setCountry(new STTransformerTest().makeDtoSimple());
        returnVal.setFirstName(new STTransformerTest().makeDtoSimple());
        returnVal.setLastName(new STTransformerTest().makeDtoSimple());
        returnVal.setLoginName(new STTransformerTest().makeDtoSimple());
        returnVal.setMiddleName(new STTransformerTest().makeDtoSimple());
        returnVal.setPassword(new STTransformerTest().makeDtoSimple());
        returnVal.setPhone(new STTransformerTest().makeDtoSimple());
        returnVal.setPoOrganizationIdentifier(new IITransformerTest().makeDtoSimple());
        returnVal.setPoPersonIdentifier(new IITransformerTest().makeDtoSimple());
        returnVal.setPostalCode(new STTransformerTest().makeDtoSimple());
        returnVal.setPrsOrg(new STTransformerTest().makeDtoSimple());
        returnVal.setState(new STTransformerTest().makeDtoSimple());
        return returnVal;
    }

    @Override
    public User makeXmlSimple() {
        User returnVal = new User();
        returnVal.setIdentifier(new IITransformerTest().makeXmlSimple());
        returnVal.setAddress(new STTransformerTest().makeXmlSimple());
        returnVal.setAffiliateOrg(new STTransformerTest().makeXmlSimple());
        returnVal.setCity(new STTransformerTest().makeXmlSimple());
        returnVal.setCountry(new STTransformerTest().makeXmlSimple());
        returnVal.setFirstName(new STTransformerTest().makeXmlSimple());
        returnVal.setLastName(new STTransformerTest().makeXmlSimple());
        returnVal.setLoginName(new STTransformerTest().makeXmlSimple());
        returnVal.setMiddleName(new STTransformerTest().makeXmlSimple());
        returnVal.setPassword(new STTransformerTest().makeXmlSimple());
        returnVal.setPhone(new STTransformerTest().makeXmlSimple());
        returnVal.setPoOrganizationIdentifier(new IITransformerTest().makeXmlSimple());
        returnVal.setPoPersonIdentifier(new IITransformerTest().makeXmlSimple());
        returnVal.setPostalCode(new STTransformerTest().makeXmlSimple());
        returnVal.setPrsOrg(new STTransformerTest().makeXmlSimple());
        returnVal.setState(new STTransformerTest().makeXmlSimple());
        return returnVal;
    }

    @Override
    public void verifyDtoSimple(UserDto x) {
        new IITransformerTest().verifyDtoSimple(x.getIdentifier());
        new STTransformerTest().verifyDtoSimple(x.getAddress());
        new STTransformerTest().verifyDtoSimple(x.getAffiliateOrg());
        new STTransformerTest().verifyDtoSimple(x.getCity());
        new STTransformerTest().verifyDtoSimple(x.getCountry());
        new STTransformerTest().verifyDtoSimple(x.getFirstName());
        new STTransformerTest().verifyDtoSimple(x.getLastName());
        new STTransformerTest().verifyDtoSimple(x.getLoginName());
        new STTransformerTest().verifyDtoSimple(x.getMiddleName());
        new STTransformerTest().verifyDtoSimple(x.getPassword());
        new STTransformerTest().verifyDtoSimple(x.getPhone());
        new IITransformerTest().verifyDtoSimple(x.getPoOrganizationIdentifier());
        new IITransformerTest().verifyDtoSimple(x.getPoPersonIdentifier());
        new STTransformerTest().verifyDtoSimple(x.getPostalCode());
        new STTransformerTest().verifyDtoSimple(x.getPrsOrg());
        new STTransformerTest().verifyDtoSimple(x.getState());
    }

    @Override
    public void verifyXmlSimple(User x) {
        new IITransformerTest().verifyXmlSimple(x.getIdentifier());
        new STTransformerTest().verifyXmlSimple(x.getAddress());
        new STTransformerTest().verifyXmlSimple(x.getAffiliateOrg());
        new STTransformerTest().verifyXmlSimple(x.getCity());
        new STTransformerTest().verifyXmlSimple(x.getCountry());
        new STTransformerTest().verifyXmlSimple(x.getFirstName());
        new STTransformerTest().verifyXmlSimple(x.getLastName());
        new STTransformerTest().verifyXmlSimple(x.getLoginName());
        new STTransformerTest().verifyXmlSimple(x.getMiddleName());
        new STTransformerTest().verifyXmlSimple(x.getPassword());
        new STTransformerTest().verifyXmlSimple(x.getPhone());
        new IITransformerTest().verifyXmlSimple(x.getPoOrganizationIdentifier());
        new IITransformerTest().verifyXmlSimple(x.getPoPersonIdentifier());
        new STTransformerTest().verifyXmlSimple(x.getPostalCode());
        new STTransformerTest().verifyXmlSimple(x.getPrsOrg());
        new STTransformerTest().verifyXmlSimple(x.getState());
    }

}
