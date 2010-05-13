package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.coppa.services.outcomes.User;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.STTransformerTest;
import gov.nih.nci.outcomes.svc.dto.UserSvcDto;

public class UserTransformerTest extends
     AbstractTransformerTestBase <UserTransformer , User ,UserSvcDto> {
   

    @Override
    public UserSvcDto makeDtoSimple() {
        UserSvcDto returnVal = new UserSvcDto();
        returnVal.setIdentifier(new IITransformerTest().makeDtoSimple());
        returnVal.setAddress(new STTransformerTest().makeDtoSimple());
        returnVal.setAffiliateOrg(new STTransformerTest().makeDtoSimple());
        returnVal.setCity(new STTransformerTest().makeDtoSimple());
        returnVal.setCountry(new STTransformerTest().makeDtoSimple());
        returnVal.setFirstName(new STTransformerTest().makeDtoSimple());
        returnVal.setLastName(new STTransformerTest().makeDtoSimple());
        returnVal.setMiddleName(new STTransformerTest().makeDtoSimple());
        returnVal.setPassword(new STTransformerTest().makeDtoSimple());
        returnVal.setPhone(new STTransformerTest().makeDtoSimple());
        returnVal.setPostalCode(new STTransformerTest().makeDtoSimple());
        returnVal.setPrsOrg(new STTransformerTest().makeDtoSimple());
        returnVal.setState(new STTransformerTest().makeDtoSimple());
        returnVal.setIdentity(new STTransformerTest().makeDtoSimple());
        returnVal.setPhysicianIdentifier(new IITransformerTest().makeDtoSimple());
        returnVal.setTreatmentSiteIdentifier(new IITransformerTest().makeDtoSimple());
        returnVal.setEmail(new STTransformerTest().makeDtoSimple());
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
        returnVal.setMiddleName(new STTransformerTest().makeXmlSimple());
        returnVal.setPassword(new STTransformerTest().makeXmlSimple());
        returnVal.setPhone(new STTransformerTest().makeXmlSimple());
        returnVal.setPostalCode(new STTransformerTest().makeXmlSimple());
        returnVal.setPrsOrg(new STTransformerTest().makeXmlSimple());
        returnVal.setState(new STTransformerTest().makeXmlSimple());
        returnVal.setIdentity(new STTransformerTest().makeXmlSimple());
        returnVal.setPhysicianIdentifier(new IITransformerTest().makeXmlSimple());
        returnVal.setTreatmentSiteIdentifier(new IITransformerTest().makeXmlSimple());
        returnVal.setEmail(new STTransformerTest().makeXmlSimple());
        return returnVal;
    }

    @Override
    public void verifyDtoSimple(UserSvcDto x) {
        new IITransformerTest().verifyDtoSimple(x.getIdentifier());
        new STTransformerTest().verifyDtoSimple(x.getAddress());
        new STTransformerTest().verifyDtoSimple(x.getAffiliateOrg());
        new STTransformerTest().verifyDtoSimple(x.getCity());
        new STTransformerTest().verifyDtoSimple(x.getCountry());
        new STTransformerTest().verifyDtoSimple(x.getFirstName());
        new STTransformerTest().verifyDtoSimple(x.getLastName());
        new STTransformerTest().verifyDtoSimple(x.getMiddleName());
        new STTransformerTest().verifyDtoSimple(x.getPassword());
        new STTransformerTest().verifyDtoSimple(x.getPhone());
        new STTransformerTest().verifyDtoSimple(x.getPostalCode());
        new STTransformerTest().verifyDtoSimple(x.getPrsOrg());
        new STTransformerTest().verifyDtoSimple(x.getState());
        new STTransformerTest().verifyDtoSimple(x.getIdentity());
        new IITransformerTest().verifyDtoSimple(x.getPhysicianIdentifier());
        new IITransformerTest().verifyDtoSimple(x.getTreatmentSiteIdentifier());
        new STTransformerTest().verifyDtoSimple(x.getEmail());
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
        new STTransformerTest().verifyXmlSimple(x.getMiddleName());
        new STTransformerTest().verifyXmlSimple(x.getPassword());
        new STTransformerTest().verifyXmlSimple(x.getPhone());
        new STTransformerTest().verifyXmlSimple(x.getPostalCode());
        new STTransformerTest().verifyXmlSimple(x.getPrsOrg());
        new STTransformerTest().verifyXmlSimple(x.getState());
        new STTransformerTest().verifyXmlSimple(x.getIdentity());
        new IITransformerTest().verifyXmlSimple(x.getPhysicianIdentifier());
        new IITransformerTest().verifyXmlSimple(x.getTreatmentSiteIdentifier());
        new STTransformerTest().verifyXmlSimple(x.getEmail());
    }

}
