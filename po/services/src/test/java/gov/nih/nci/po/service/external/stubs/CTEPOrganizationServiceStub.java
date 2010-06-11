package gov.nih.nci.po.service.external.stubs;

import gov.nih.nci.common.exceptions.CTEPEntException;
import gov.nih.nci.coppa.domain.Organization;
import gov.nih.nci.coppa.services.OrganizationService;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.List;

public class CTEPOrganizationServiceStub implements OrganizationService {

    private final OrganizationDTO org;
    private final Ii orgId;
    private final HealthCareFacilityDTO hcf;
    private final ResearchOrganizationDTO ro;

    public OrganizationDTO getOrg() {
        return org;
    }

    public Ii getOrgId() {
        return orgId;
    }

    public HealthCareFacilityDTO getHcf() {
        return hcf;
    }

    public ResearchOrganizationDTO getRo() {
        return ro;
    }

    public CTEPOrganizationServiceStub(OrganizationDTO org, HealthCareFacilityDTO hcf, ResearchOrganizationDTO ro) {
        this.org = org;
        this.orgId = (org != null) ? org.getIdentifier() : null;
        this.hcf = hcf;
        this.ro = ro;
    }

    public HealthCareFacilityDTO getHealthCareFacility(Ii arg0) throws CTEPEntException {
        if (hcf == null) {
            throw new CTEPEntException(0, "Ii not for a ResearchOrganization");
        }
        return hcf;
    }

    public OrganizationDTO getOrganizationById(Ii arg0) throws CTEPEntException {
        return org;
    }

    public List<OrganizationDTO> getOrganizationsByCriteria(OrganizationDTO arg0) throws CTEPEntException {
        throw new CTEPEntException(0, "Doesn't work");
    }

    public List<Organization> getOrganizationsByCriteria(Organization arg0) throws CTEPEntException {
        throw new CTEPEntException(0, "Doesn't work");
    }

    public ResearchOrganizationDTO getResearchOrganization(Ii arg0) throws CTEPEntException {
        if (ro == null) {
            throw new CTEPEntException(0, "Ii not for a ResearchOrganization");
        }
        return ro;
    }


}
