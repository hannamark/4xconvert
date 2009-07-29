package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.PAOrganizationalContactDTO;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;

/**
 * 
 * @author NAmiruddin
 *
 */
public class OrganizationalContactConverter 
    extends AbstractPoConverter <PAOrganizationalContactDTO , OrganizationalContactDTO ,  OrganizationalContact > {

    /**
     * {@inheritDoc}
     */
    @Override
    public OrganizationalContact convertToDomain(
            PAOrganizationalContactDTO dto , Organization org , Person per) throws PAException {

        OrganizationalContact oc = new OrganizationalContact();
        oc.setPerson(per);
        oc.setOrganization(org);
        oc.setIdentifier(dto.getIdentifier());
        oc.setStatusCode(StructuralRoleStatusCode.PENDING);
        return oc;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public OrganizationalContactDTO convertFromPADtoToPoDto(PAOrganizationalContactDTO dto)
            throws PAException {
        OrganizationalContactDTO poOcDto = null;
        if (dto != null) {
            poOcDto = new OrganizationalContactDTO();
            poOcDto.setPlayerIdentifier(IiConverter.converToPoPersonIi(dto.getPersonIdentifier()));
            poOcDto.setScoperIdentifier(IiConverter.converToPoOrganizationIi(dto.getOrganizationIdentifier()));
        }
        return poOcDto;

    }
    
    /**
     * {@inheritDoc}
     */
    public OrganizationalContact convertFromPODtoToPADto(
            OrganizationalContactDTO dto) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }


}
