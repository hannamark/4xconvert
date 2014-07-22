package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.data.bo.ResearchOrganizationType;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.webservices.types.ResearchOrganization;

/**
 * This is Converter class for ResearchOrganization.
 * 
 * @author Rohit Gupta
 */
public class ResearchOrganizationConverter
        extends
        AbstractRoleConverter<gov.nih.nci.po.data.bo.ResearchOrganization, ResearchOrganization> {

    /**
     * This method is used to convert JaxB object into corresponding BO.
     * 
     *
     * @param researchOrg
     *            -object to be mapped
     * @return mapped BO ResearchOrganization
     */
    public gov.nih.nci.po.data.bo.ResearchOrganization convertFromJaxbToBO(
            ResearchOrganization researchOrg) {

        gov.nih.nci.po.data.bo.ResearchOrganization researchOrgBo = null;
        if (researchOrg != null) {
            researchOrgBo = new gov.nih.nci.po.data.bo.ResearchOrganization();

            // Set the basic attributes
            researchOrgBo.setId(researchOrg.getId());
            researchOrgBo.setName(researchOrg.getName());
            if (researchOrg.getType() != null) { // Type is NOT mandatory
                gov.nih.nci.po.data.bo.ResearchOrganizationType roType = PoRegistry
                        .getGenericCodeValueService().getByCode(
                                ResearchOrganizationType.class,
                                researchOrg.getType().value());
                researchOrgBo.setTypeCode(roType);
            }
            if (researchOrg.getFundingMechanism() != null) { // FundingMechanism is NOT mandatory
                gov.nih.nci.po.data.bo.FundingMechanism fundMechanism = PoRegistry
                        .getGenericCodeValueService().getByCode(
                                gov.nih.nci.po.data.bo.FundingMechanism.class,
                                researchOrg.getFundingMechanism().value());
                researchOrgBo.setFundingMechanism(fundMechanism);
            }
            researchOrgBo.setStatus(getBORoleStatus(researchOrg.getStatus().value()));

            // populate CtepId
            populateJaxbCtepIdInBoOrgRole(researchOrg, researchOrgBo);

            // populate OrganizationRole specific attributes into BO object
            populateOrganizationRoleFromJaxbToBO(researchOrg, researchOrgBo);
        }

        return researchOrgBo;
    }

    /**
     * This method is used to convert BO object into corresponding JaxB object.
     * 
     *
     * @param researchOrgBo
     *            - object to be mapped
     * @return mapped JaxB object
     */
    public gov.nih.nci.po.webservices.types.ResearchOrganization convertFromBOToJaxB(
            gov.nih.nci.po.data.bo.ResearchOrganization researchOrgBo) {

        gov.nih.nci.po.webservices.types.ResearchOrganization researchOrg = null;
        if (researchOrgBo != null) {
            researchOrg = new ResearchOrganization();

            // Set the basic attributes
            researchOrg.setId(researchOrgBo.getId());
            researchOrg.setName(researchOrgBo.getName());
            if (researchOrgBo.getTypeCode() != null) { // Type is NOT mandatory
                researchOrg.setType(gov.nih.nci.po.webservices.types.ResearchOrganizationType
                                .fromValue(researchOrgBo.getTypeCode().getCode()));
            }
            if (researchOrgBo.getFundingMechanism() != null) { // FundingMechanism is NOT mandatory
                researchOrg.setFundingMechanism(gov.nih.nci.po.webservices.types.FundingMechanism
                                .fromValue(researchOrgBo.getFundingMechanism().getCode()));
            }
            researchOrg.setStatus(getEntityStatus(researchOrgBo.getStatus().name()));

            // populate CtepId
            populateBoCtepIdInJaxbOrgRole(researchOrgBo, researchOrg);

            // populate OrganizationRole specific attributes into Jaxb object
            populateOrganizationRoleFromBOToJaxb(researchOrgBo, researchOrg);
        }

        return researchOrg;
    }

}
