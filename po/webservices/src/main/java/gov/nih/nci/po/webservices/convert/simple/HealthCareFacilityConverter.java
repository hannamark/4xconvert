package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.webservices.types.HealthCareFacility;

/**
 * This is Converter class for HealthCareFacility.
 * 
 * @author Rohit Gupta
 */
public class HealthCareFacilityConverter
        extends
        AbstractRoleConverter<gov.nih.nci.po.data.bo.HealthCareFacility, HealthCareFacility> {

    /**
     * This method is used to convert JaxB object into corresponding BO.
     * 
     *
     * @param hcf
     *            -object to be mapped
     * @return mapped BO HealthCareFacility
     */
    public gov.nih.nci.po.data.bo.HealthCareFacility convertFromJaxbToBO(
            HealthCareFacility hcf) {

        gov.nih.nci.po.data.bo.HealthCareFacility hcfBo = null;
        if (hcf != null) {
            hcfBo = new gov.nih.nci.po.data.bo.HealthCareFacility();

            // Set the basic attributes
            hcfBo.setId(hcf.getId());
            hcfBo.setName(hcf.getName());
            hcfBo.setStatus(getBORoleStatus(hcf.getStatus().value()));

            // populate CtepId
            populateJaxbCtepIdInBoOrgRole(hcf, hcfBo);

            // populate HealthCareFacility specific attributes into BO object
            populateOrganizationRoleFromJaxbToBO(hcf, hcfBo);
        }

        return hcfBo;
    }

    /**
     * This method is used to convert BO object into corresponding JaxB object.
     * 
     *
     * @param hcfBo
     *            - object to be mapped
     * @return mapped JaxB object
     */
    public gov.nih.nci.po.webservices.types.HealthCareFacility convertFromBOToJaxB(
            gov.nih.nci.po.data.bo.HealthCareFacility hcfBo) {

        gov.nih.nci.po.webservices.types.HealthCareFacility hcf = null;
        if (hcfBo != null) {
            hcf = new HealthCareFacility();

            // Set the basic attributes
            hcf.setId(hcfBo.getId());
            hcf.setName(hcfBo.getName());
            hcf.setStatus(getEntityStatus(hcfBo.getStatus().name()));

            // populate CtepId
            populateBoCtepIdInJaxbOrgRole(hcfBo, hcf);

            // populate HealthCareFacility specific attributes into Jaxb object
            populateOrganizationRoleFromBOToJaxb(hcfBo, hcf);
        }

        return hcf;
    }

}
