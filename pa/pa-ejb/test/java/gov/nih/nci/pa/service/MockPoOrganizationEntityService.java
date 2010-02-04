package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vrushali
 *
 */
public class MockPoOrganizationEntityService implements
        OrganizationEntityServiceRemote {
    static List<OrganizationDTO> orgDtoList;
    static {
        orgDtoList = new ArrayList<OrganizationDTO>();
        OrganizationDTO dto = new OrganizationDTO();
        dto.setIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
        dto.setName(EnOnConverter.convertToEnOn("OrgName"));
        dto.setStatusCode(CdConverter.convertStringToCd("ACTIVE"));
        dto.setPostalAddress(AddressConverterUtil.
                create("streetAddressLine", "deliveryAddressLine", 
                        "cityOrMunicipality", "stateOrProvince",
                        "postalCode", "USA"));
        orgDtoList.add(dto);
        
        dto = new OrganizationDTO();
        dto.setIdentifier(IiConverter.convertToPoOrganizationIi("abc1"));
        dto.setName(EnOnConverter.convertToEnOn("OrgName"));
        dto.setStatusCode(CdConverter.convertStringToCd("ACTIVE"));
        dto.setPostalAddress(AddressConverterUtil.
                create("streetAddressLine", "deliveryAddressLine", 
                        "cityOrMunicipality", "stateOrProvince",
                        "postalCode", "USA"));
        orgDtoList.add(dto);    
        
        dto = new OrganizationDTO();
        dto.setIdentifier(IiConverter.convertToPoOrganizationIi("1"));
        dto.setName(EnOnConverter.convertToEnOn("OrgName"));
        dto.setStatusCode(CdConverter.convertStringToCd("ACTIVE"));
        dto.setPostalAddress(AddressConverterUtil.
                create("streetAddressLine", "deliveryAddressLine", 
                        "cityOrMunicipality", "stateOrProvince",
                        "postalCode", "USA"));
        orgDtoList.add(dto);
        
        dto = new OrganizationDTO();
        dto.setIdentifier(IiConverter.convertToPoOrganizationIi("584"));
        dto.setName(EnOnConverter.convertToEnOn("OrgName"));
        dto.setStatusCode(CdConverter.convertStringToCd("ACTIVE"));
        dto.setPostalAddress(AddressConverterUtil.
                create("streetAddressLine", "deliveryAddressLine", 
                        "cityOrMunicipality", "stateOrProvince",
                        "postalCode", "USA"));
        orgDtoList.add(dto);
        
    }
    /* (non-Javadoc)
     * @see gov.nih.nci.services.organization.OrganizationEntityServiceRemote#createOrganization(gov.nih.nci.services.organization.OrganizationDTO)
     */
    public Ii createOrganization(OrganizationDTO arg0)
            throws EntityValidationException {
        return IiConverter.convertToPoOrganizationIi("1");
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.organization.OrganizationEntityServiceRemote#getOrganization(gov.nih.nci.iso21090.Ii)
     */
    public OrganizationDTO getOrganization(Ii arg0)
            throws NullifiedEntityException {
        if (NullFlavor.NA.equals(arg0.getNullFlavor())) {
            Map<Ii, Ii> nullifiedEntities = new HashMap<Ii, Ii>();
            nullifiedEntities.put(arg0, IiConverter.convertToPoOrganizationIi("584"));
            throw new NullifiedEntityException(nullifiedEntities);
        }
        
        for(OrganizationDTO dto:orgDtoList){
            if(dto.getIdentifier().getExtension().equals(arg0.getExtension())){
                return dto;
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.organization.OrganizationEntityServiceRemote#search(gov.nih.nci.services.organization.OrganizationDTO)
     */
    @Deprecated
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

    /* (non-Javadoc)
     * @see gov.nih.nci.services.organization.OrganizationEntityServiceRemote#updateOrganization(gov.nih.nci.services.organization.OrganizationDTO)
     */
    public void updateOrganization(OrganizationDTO arg0)
            throws EntityValidationException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.organization.OrganizationEntityServiceRemote#updateOrganizationStatus(gov.nih.nci.iso21090.Ii, gov.nih.nci.iso21090.Cd)
     */
    public void updateOrganizationStatus(Ii arg0, Cd arg1)
            throws EntityValidationException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.services.organization.OrganizationEntityServiceRemote#validate(gov.nih.nci.services.organization.OrganizationDTO)
     */
    public Map<String, String[]> validate(OrganizationDTO arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /* (non-Javadoc)
     * @see gov.nih.nci.services.organization.OrganizationEntityServiceRemote#search(gov.nih.nci.services.organization.OrganizationDTO, gov.nih.nci.coppa.services.LimitOffset)
     */
    public List<OrganizationDTO> search(OrganizationDTO arg0, LimitOffset arg1)
        throws TooManyResultsException {
    	List<OrganizationDTO> matchingDtosList = new ArrayList<OrganizationDTO>();
        String inputName = EnOnConverter.convertEnOnToString(arg0.getName());
        for(OrganizationDTO dto:orgDtoList){
            String dtoName = EnOnConverter.convertEnOnToString(dto.getName());
            if(dtoName .equals(inputName)){
                matchingDtosList.add(dto);
            }
        }

        int fromIndex = (arg1.getOffset() < 0 ? 0 : arg1.getOffset());
        int toIndex = Math.min(fromIndex + arg1.getLimit(), matchingDtosList.size());

        try {
	        matchingDtosList = matchingDtosList.subList(fromIndex, toIndex);
        } catch (IndexOutOfBoundsException e) { // fromIndex > toIndex
	        matchingDtosList.clear();  // return empty list
        }

        if (matchingDtosList.size() > PAConstants.MAX_SEARCH_RESULTS) {
            throw new TooManyResultsException(PAConstants.MAX_SEARCH_RESULTS);
        }

        return matchingDtosList ;
    }

}
