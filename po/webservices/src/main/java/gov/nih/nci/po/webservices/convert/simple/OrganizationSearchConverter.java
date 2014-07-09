package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.service.OrganizationSearchDTO;
import gov.nih.nci.po.service.OrganizationSearchDTO.AliasDTO;
import gov.nih.nci.po.webservices.types.OrganizationSearchCriteria;
import gov.nih.nci.po.webservices.types.OrganizationSearchResult;
import gov.nih.nci.po.webservices.util.PoWSUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * This is the converter for Organization Search.
 * 
 * @author Rohit Gupta
 * 
 */
public class OrganizationSearchConverter extends AbstractBaseSearchConverter {

    /**
     * This method is used to convert OrganizationSearchCriteria JaxB into BO.
     * 
     * @param osCriteria
     *            - JaxB OrganizationSearchCriteria
     * @return OrganizationSearchCriteria - converted BO object
     */
    public gov.nih.nci.po.service.OrganizationSearchCriteria convertOSCFromJaxbToBO(
            OrganizationSearchCriteria osCriteria) {

        gov.nih.nci.po.service.OrganizationSearchCriteria oscBo = null;
        if (osCriteria != null) {
            oscBo = new gov.nih.nci.po.service.OrganizationSearchCriteria();

            // call super converter method for BaseSearchCriteria
            oscBo.getOrganization().setPostalAddress(
                    convertFromJaxbToBo(osCriteria));

            // convert other attributes
            if (osCriteria.getId() != null) {
                oscBo.setId(osCriteria.getId().toString());
            }
            oscBo.setCtepID(osCriteria.getCtepID());
            if (osCriteria.getStatusCode() != null) {
                oscBo.setStatusCode(osCriteria.getStatusCode().value());
            }
            oscBo.setName(osCriteria.getOrganizationName());
            oscBo.setFamilyName(osCriteria.getFamilyName());
            oscBo.setHasChangeRequests(osCriteria.isHasChangeRequest());
            oscBo.setHasPendingHcfRoles(osCriteria.isHasPendingHcfRoles());
            oscBo.setHasPendingRoRoles(osCriteria.isHasPendingRoRoles());
            oscBo.setSearchAliases(osCriteria.isSearchAliases());
        }
        return oscBo;
    }

    /**
     * This method is used to convert OrganizationSearchResult BO to JaxB.
     * 
     * @param osDto
     *            OrganizationSearchDTO BO object
     * @return OrganizationSearchResult - converted JaxB object
     */
    public OrganizationSearchResult convertOSRFromBOToJaxB(
            OrganizationSearchDTO osDto) {

        OrganizationSearchResult osResult = null;
        if (osDto != null) {
            osResult = new OrganizationSearchResult();
            // call super converter for BaseSearchResult
            osResult = (OrganizationSearchResult) convertBSRFromBOToJaxB(osDto,
                    osResult);

            // convert other attributes
            osResult.setOrganizationName(osDto.getName());
            osResult.setFamilyName(osDto.getFamilyName());
            osResult.setRoCtepID(osDto.getRoCtepId());
            osResult.setHcfCtepID(osDto.getHcfCtepId());
            osResult.setOrgCtepId(osDto.getIoCtepId());
            osResult.setChangeRequests(osDto.getChangeRequests());
            osResult.setPendingROs(osDto.getPendingROs());
            osResult.setPendingHCFs(osDto.getPendingHCFs());
            osResult.setStatusDate(PoWSUtil.toXMLGregorianCalendar(osDto
                    .getStatusDate()));
            osResult.setTotalROs(osDto.getTotalROs());
            osResult.setTotalHCFs(osDto.getTotalHCFs());
            osResult.setTotalIdOrgs(osDto.getTotalIdOrgs());
            osResult.setTotalOversightCommitees(osDto
                    .getTotalOversightCommitees());
            osResult.setTotalOrgContacts(osDto.getTotalOrgContacts());
            if (CollectionUtils.isNotEmpty(osDto.getAliasDto())) {
                populateAliases(osDto, osResult);
            }
        }
        return osResult;
    }
    
    /**
     * This method is used to populate Aliases in OrganizationSearchResult.
     */
    private void populateAliases(OrganizationSearchDTO osDto,
            OrganizationSearchResult osResult) {
        List<AliasDTO> aliasDtos = osDto.getAliasDto();
        for (AliasDTO alias : aliasDtos) {            
            gov.nih.nci.po.webservices.types.Alias jAllias = new gov.nih.nci.po.webservices.types.Alias();
            jAllias.setValue(alias.getValue());                      
            osResult.getAlias().add(jAllias);
        }
    }

}
