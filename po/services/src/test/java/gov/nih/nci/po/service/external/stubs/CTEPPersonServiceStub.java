package gov.nih.nci.po.service.external.stubs;

import gov.nih.nci.common.exceptions.CTEPEntException;
import gov.nih.nci.coppa.services.PersonService;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.List;

public class CTEPPersonServiceStub implements PersonService {

    private final PersonDTO per;
    private final Ii perId;
    private final HealthCareProviderDTO hcp;
    private final ClinicalResearchStaffDTO crs;
    private final IdentifiedPersonDTO ip;

    public PersonDTO getPer() {
        return per;
    }

    public Ii getPerId() {
        return perId;
    }

    public HealthCareProviderDTO getHcp() {
        return hcp;
    }

    public IdentifiedPersonDTO getIp() {
        return ip;
    }

    public ClinicalResearchStaffDTO getCrs() {
        return crs;
    }

    public CTEPPersonServiceStub(PersonDTO per, HealthCareProviderDTO hcp, ClinicalResearchStaffDTO crs,
            IdentifiedPersonDTO ip) {
        this.per = per;
        this.perId = (per != null) ? per.getIdentifier() : null;
        this.hcp = hcp;
        this.crs = crs;
        this.ip = ip;
    }

    public HealthCareProviderDTO getHealthCareProvider(Ii arg0) throws CTEPEntException {
        if (hcp == null) {
            throw new CTEPEntException(0, "Ii not for a HealthCareProvicer");
        }
        return hcp;
    }

    public PersonDTO getPersonById(Ii arg0) throws CTEPEntException {
        return per;
    }

    public List<PersonDTO> getPersonByCriteria(PersonDTO arg0) throws CTEPEntException {
        throw new CTEPEntException(0, "Doesn't work");
    }

    public ClinicalResearchStaffDTO getClinicalResearchStaff(Ii arg0) throws CTEPEntException {
        if (crs == null) {
            throw new CTEPEntException(0, "Ii not for a ClinicalResearchStaff");
        }
        return crs;
    }

    public ClinicalResearchStaffDTO getClinicalResearchStaffByPlayerId(Ii arg0) throws CTEPEntException {
        if (crs == null) {
            throw new CTEPEntException(0, "Ii not for a ClinicalResearchStaff");
        }
        return crs;
    }

    public HealthCareProviderDTO getHealthCareProviderByPlayerId(Ii arg0) throws CTEPEntException {
        if (hcp == null) {
            throw new CTEPEntException(0, "Ii not for a HealthCareProvider");
        }
        return hcp;
    }

    public HealthCareProviderDTO getHealthCareProviderByRoleId(Ii arg0) throws CTEPEntException {
        if (hcp == null) {
            throw new CTEPEntException(0, "Ii not for a HealthCareProvider");
        }
        return hcp;
    }

    public IdentifiedPersonDTO getIdentifiedPersonById(Ii arg0) throws CTEPEntException {
        if (ip == null) {
            throw new CTEPEntException(0, "Ii not for a IdentifiedPerson");
        }
        return ip;
    }


}
