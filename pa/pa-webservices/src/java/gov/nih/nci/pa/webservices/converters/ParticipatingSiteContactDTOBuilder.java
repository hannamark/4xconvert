/**
 * 
 */
package gov.nih.nci.pa.webservices.converters;

import gov.nih.nci.pa.iso.dto.ParticipatingSiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.webservices.types.BaseParticipatingSite;
import gov.nih.nci.pa.webservices.types.BaseParticipatingSite.Investigator;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author dkrylov
 * 
 */
public class ParticipatingSiteContactDTOBuilder {

    private final OrganizationDTO organization;
    private final PAServiceUtils paServiceUtils;

    /**
     * @param organization
     *            OrganizationDTO
     * @param paServiceUtils
     *            PAServiceUtils
     */
    public ParticipatingSiteContactDTOBuilder(OrganizationDTO organization,
            PAServiceUtils paServiceUtils) {
        this.organization = organization;
        this.paServiceUtils = paServiceUtils;
    }

    /**
     * @param ps
     *            ParticipatingSite
     * @return List<ParticipatingSiteContactDTO>
     * @throws PAException
     *             PAException
     */
    public List<ParticipatingSiteContactDTO> build(BaseParticipatingSite ps)
            throws PAException {
        List<ParticipatingSiteContactDTO> list = new ArrayList<>();
        for (Investigator inv : ps.getInvestigator()) {
            list.add(build(inv));
        }
        return list;
    }

    private ParticipatingSiteContactDTO build(Investigator i)
            throws PAException {

        PersonDTO person = new PersonDTOBuilder().build(i.getPerson());

        ParticipatingSiteContactDTO participatingSiteContactDTO = new ParticipatingSiteContactDTO();
        participatingSiteContactDTO.setPersonDTO(person);

        StudySiteContactDTO studySiteContact = new StudySiteContactDTO();
        studySiteContact.setPostalAddress(person.getPostalAddress());
        studySiteContact.setPrimaryIndicator(BlConverter.convertToBl(i
                .isPrimaryContact()));
        studySiteContact
                .setRoleCode(CdConverter.convertStringToCd(i.getRole()));
        studySiteContact.setStatusDateRange(IvlConverter.convertTs()
                .convertToIvl(new Date(), null));
        studySiteContact.setTelecomAddresses(person.getTelecomAddress());
        participatingSiteContactDTO.setStudySiteContactDTO(studySiteContact);

        ClinicalResearchStaffDTO crsDTO = paServiceUtils.getCrsDTO(person
                .getIdentifier(), organization.getIdentifier().getExtension());
        participatingSiteContactDTO.setAbstractPersonRoleDTO(crsDTO);

        return participatingSiteContactDTO;
    }

}
