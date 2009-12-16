package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.accrual.dto.util.PatientDto;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.DSETCDTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.TSTransformerTest;
import gov.nih.nci.coppa.services.outcomes.Patient;

public class PatientTransformerTest extends
     AbstractTransformerTestBase <PatientTransformer , Patient , PatientDto> {

    @Override
    public PatientDto makeDtoSimple() {
        PatientDto returnVal = new PatientDto();
        returnVal.setIdentifier(new IITransformerTest().makeDtoSimple());
        returnVal.setAssignedIdentifier(new IITransformerTest().makeDtoSimple());
        returnVal.setBirthDate(new TSTransformerTest().makeDtoSimple());
        returnVal.setCountryIdentifier(new IITransformerTest().makeDtoSimple());
        returnVal.setEthnicCode(new CDTransformerTest().makeDtoSimple());
        returnVal.setGenderCode(new CDTransformerTest().makeDtoSimple());
        returnVal.setOrganizationIdentifier(new IITransformerTest().makeDtoSimple());
        returnVal.setPersonIdentifier(new IITransformerTest().makeDtoSimple());
        returnVal.setRaceCode(new DSETCDTransformerTest().makeDtoSimple());
        returnVal.setStatusCode(new CDTransformerTest().makeDtoSimple());
        returnVal.setStatusDateRangeLow(new TSTransformerTest().makeDtoSimple());
        returnVal.setZip(new STTransformerTest().makeDtoSimple());
        return returnVal;
    }

    @Override
    public Patient makeXmlSimple() {
        Patient returnVal = new Patient();
        returnVal.setIdentifier(new IITransformerTest().makeXmlSimple());
        returnVal.setAssignedIdentifier(new IITransformerTest().makeXmlSimple());
        returnVal.setBirthDate(new TSTransformerTest().makeXmlSimple());
        returnVal.setCountryIdentifier(new IITransformerTest().makeXmlSimple());
        returnVal.setEthnicCode(new CDTransformerTest().makeXmlSimple());
        returnVal.setGenderCode(new CDTransformerTest().makeXmlSimple());
        returnVal.setOrganizationIdentifier(new IITransformerTest().makeXmlSimple());
        returnVal.setPersonIdentifier(new IITransformerTest().makeXmlSimple());
        returnVal.setRaceCode(new DSETCDTransformerTest().makeXmlSimple());
        returnVal.setStatusCode(new CDTransformerTest().makeXmlSimple());
        returnVal.setStatusDateRangeLow(new TSTransformerTest().makeXmlSimple());
        returnVal.setZip(new STTransformerTest().makeXmlSimple());
        return returnVal;
    }

    @Override
    public void verifyDtoSimple(PatientDto x) {
        new IITransformerTest().verifyDtoSimple(x.getIdentifier());
        new IITransformerTest().verifyDtoSimple(x.getAssignedIdentifier());
        new TSTransformerTest().verifyDtoSimple(x.getBirthDate());
        new IITransformerTest().verifyDtoSimple(x.getCountryIdentifier());
        new CDTransformerTest().verifyDtoSimple(x.getEthnicCode());
        new CDTransformerTest().verifyDtoSimple(x.getGenderCode());
        new IITransformerTest().verifyDtoSimple(x.getOrganizationIdentifier());
        new IITransformerTest().verifyDtoSimple(x.getPersonIdentifier());
        new DSETCDTransformerTest().verifyDtoSimple(x.getRaceCode());
        new CDTransformerTest().verifyDtoSimple(x.getStatusCode());
        new TSTransformerTest().verifyDtoSimple(x.getStatusDateRangeLow());
        new STTransformerTest().verifyDtoSimple(x.getZip());
    }

    @Override
    public void verifyXmlSimple(Patient x) {
        new IITransformerTest().verifyXmlSimple(x.getIdentifier());
        new IITransformerTest().verifyXmlSimple(x.getAssignedIdentifier());
        new TSTransformerTest().verifyXmlSimple(x.getBirthDate());
        new IITransformerTest().verifyXmlSimple(x.getCountryIdentifier());
        new CDTransformerTest().verifyXmlSimple(x.getEthnicCode());
        new CDTransformerTest().verifyXmlSimple(x.getGenderCode());
        new IITransformerTest().verifyXmlSimple(x.getOrganizationIdentifier());
        new IITransformerTest().verifyXmlSimple(x.getPersonIdentifier());
        new DSETCDTransformerTest().verifyXmlSimple(x.getRaceCode());
        new CDTransformerTest().verifyXmlSimple(x.getStatusCode());
        new TSTransformerTest().verifyXmlSimple(x.getStatusDateRangeLow());
        new STTransformerTest().verifyXmlSimple(x.getZip());
    }
}
