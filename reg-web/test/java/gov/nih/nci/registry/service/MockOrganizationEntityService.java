/**
 * 
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnOn;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author Vrushali
 *
 */
public class MockOrganizationEntityService implements
        OrganizationEntityServiceRemote {
    static List<OrganizationDTO> orgDtoList;
    static {
        orgDtoList = new ArrayList<OrganizationDTO>();
        OrganizationDTO dto = new OrganizationDTO();
        dto.setFamilyOrganizationRelationships(new DSet<Ii>());
        dto.getFamilyOrganizationRelationships().setItem(new HashSet<Ii>());
        dto.setIdentifier(IiConverter.convertToIi("1"));
        dto.setName(EnOnConverter.convertToEnOn("OrgName"));
        dto.setStatusCode(CdConverter.convertStringToCd("code"));
        dto.setPostalAddress(AddressConverterUtil.
                create("streetAddressLine", "deliveryAddressLine", 
                        "cityOrMunicipality", "stateOrProvince",
                        "postalCode", "USA"));
        orgDtoList.add(dto);
    }
    /**
     * {@inheritDoc}
     */
    public Ii createOrganization(OrganizationDTO arg0)
            throws EntityValidationException {
        return IiConverter.convertToIi("1");
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationDTO getOrganization(Ii arg0)
            throws NullifiedEntityException {
        for(OrganizationDTO dto:orgDtoList){
            if(dto.getIdentifier().getExtension().equals(arg0.getExtension())){
                return dto;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List<OrganizationDTO> search(OrganizationDTO arg0) {
        List<OrganizationDTO> matchingDtosList = new ArrayList<OrganizationDTO>();
        String inputName = EnOnConverter.convertEnOnToString(arg0.getName());
        for(OrganizationDTO dto:orgDtoList){
            String dtoName = EnOnConverter.convertEnOnToString(dto.getName());
            if(dtoName .equals(inputName)){
                matchingDtosList.add(dto);
            }
        }
        return matchingDtosList ;
    }

    /**
     * {@inheritDoc}
     */
    public void updateOrganization(OrganizationDTO arg0)
            throws EntityValidationException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    public void updateOrganizationStatus(Ii arg0, Cd arg1)
            throws EntityValidationException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    public Map<String, String[]> validate(OrganizationDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<OrganizationDTO> search(OrganizationDTO arg0, LimitOffset arg1)
            throws TooManyResultsException {
        List<OrganizationDTO> matchingDtosList = new ArrayList<OrganizationDTO>();
        if (arg0.getName() != null) {
            String inputName = EnOnConverter.convertEnOnToString(arg0.getName());
            for(OrganizationDTO dto:orgDtoList){
                String dtoName = EnOnConverter.convertEnOnToString(dto.getName());
                if(dtoName .equals(inputName)){
                    matchingDtosList.add(dto);
                }
            }
        }
        if (arg0.getIdentifier() != null && arg0.getIdentifier().getExtension() != null ) {
            for(OrganizationDTO dto:orgDtoList){
                if(dto.getIdentifier().getExtension().equals(arg0.getIdentifier().getExtension())){
                    matchingDtosList.add(dto);
                }
            }
        }
        return matchingDtosList ;

    }

    /**
     * {@inheritDoc}
     */
    public List<OrganizationDTO> search(OrganizationDTO arg0, EnOn arg1, LimitOffset arg2)
            throws TooManyResultsException {
        return this.search(arg0, arg2);
    }

}
