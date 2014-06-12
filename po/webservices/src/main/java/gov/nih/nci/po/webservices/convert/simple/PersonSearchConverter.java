package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.service.PersonSearchDTO;
import gov.nih.nci.po.service.PersonSearchDTO.Affiliation;
import gov.nih.nci.po.webservices.types.PersonSearchCriteria;
import gov.nih.nci.po.webservices.types.PersonSearchResult;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;

/**
 * This is the converter for Person Search.
 * 
 * @author Rohit Gupta
 * 
 */
public class PersonSearchConverter extends AbstractBaseSearchConverter {

    /**
     * This method is used to convert PersonSearchCriteria JaxB into BO.
     * 
     * @param psCriteria
     *            - PersonSearchCriteria JaxB object
     * @return PersonSearchCriteria - converted BO object
     */
    public gov.nih.nci.po.service.PersonSearchCriteria convertPSCFromJaxbToBO(
            PersonSearchCriteria psCriteria) {

        gov.nih.nci.po.service.PersonSearchCriteria pscBo = null;
        if (psCriteria != null) {
            pscBo = new gov.nih.nci.po.service.PersonSearchCriteria();

            // call super converter method for BaseSearchCriteria
            pscBo.getPerson().setPostalAddress(convertFromJaxbToBo(psCriteria));

            // convert other attributes
            pscBo.setCtepID(psCriteria.getCtepID());
            pscBo.setEmail(psCriteria.getEmail());
            pscBo.setFirstName(psCriteria.getFirstName());
            pscBo.setHasPendingCrsRoles(psCriteria.isHasPendingCrsRoles());
            pscBo.setHasPendingHcpRoles(psCriteria.isHasPendingHcpRoles());
            pscBo.setHasPendingOcRoles(psCriteria.isHasPendingOcRoles());
            if (psCriteria.getId() != null) {
                pscBo.setId(psCriteria.getId().toString());
            }
            pscBo.setLastName(psCriteria.getLastName());
            pscBo.setOrg(psCriteria.getAffiliation());
            if (psCriteria.getStatusCode() != null) {
                pscBo.setStatusCode(psCriteria.getStatusCode().value());
            }
        }
        return pscBo;
    }

    /**
     * This method is used to convert PersonSearchResult BO to JaxB.
     * 
     * @param psDto
     *            PersonSearchDTO BO object
     * @return PersonSearchResult - converted JaxB object
     */
    public PersonSearchResult convertPSRFromBOToJaxB(PersonSearchDTO psDto) {

        PersonSearchResult psResult = null;
        if (psDto != null) {
            psResult = new PersonSearchResult();
            // call super converter for BaseSearchResult
            psResult = (PersonSearchResult) convertBSRFromBOToJaxB(psDto,
                    psResult);

            // convert other attributes
            psResult.setCtepID(psDto.getCtepID());
            psResult.setFirstName(psDto.getFirstName());
            psResult.setMiddleName(psDto.getMiddleName());
            psResult.setLastName(psDto.getLastName());
            psResult.setTotalCrs(psDto.getTotalCrs());
            psResult.setTotalHcp(psDto.getTotalHcp());
            psResult.setTotalOc(psDto.getTotalOc());
            psResult.setTotalPending(psDto.getTotalPending());
            if (CollectionUtils.isNotEmpty(psDto.getAffiliation())) {
                populateAffiliations(psDto, psResult);
            }

        }

        return psResult;
    }

    /**
     * This method is used to populate Affiliations in PersonSearchResult.
     */
    private void populateAffiliations(PersonSearchDTO psDto,
            PersonSearchResult psResult) {
        Collection<Affiliation> affCollection = psDto.getAffiliation();
        for (Affiliation affiliation : affCollection) {
            gov.nih.nci.po.webservices.types.Affiliation affJaxb = new gov.nih.nci.po.webservices.types.Affiliation();
            affJaxb.setOrgName(affiliation.getOrgName());
            affJaxb.setPending(affiliation.isPending());
            affJaxb.setType(
                    gov.nih.nci.po.webservices.types.RoleType.fromValue(
                            affiliation.getGroup().name()
                    )
            );
            psResult.getAffiliation().add(affJaxb);
        }
    }

}
