package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.webservices.types.ClinicalResearchStaff;

/**
 * This is Converter class for ClinicalResearchStaff.
 * 
 * @author Rohit Gupta
 */
public class ClinicalResearchStaffConverter
        extends
        AbstractRoleConverter<gov.nih.nci.po.data.bo.ClinicalResearchStaff, ClinicalResearchStaff> {

    /**
     * This method is used to convert JaxB object into corresponding BO.
     * 
     *
     * @param crs
     *            -object to be mapped
     * @return mapped BO ClinicalResearchStaff
     */
    public gov.nih.nci.po.data.bo.ClinicalResearchStaff convertFromJaxbToBO(
            ClinicalResearchStaff crs) {

        gov.nih.nci.po.data.bo.ClinicalResearchStaff crsBo = null;
        if (crs != null) {
            crsBo = new gov.nih.nci.po.data.bo.ClinicalResearchStaff();
            crsBo.setId(crs.getId());
            crsBo.setStatus(getBORoleStatus(crs.getStatus().value()));

            // populate PersonRole specific attributes into BO object
            populatePersonRoleFromJaxbToBO(crs, crsBo);
        }

        return crsBo;
    }

    /**
     * This method is used to convert BO object into corresponding JaxB object.
     * 
     *
     * @param crsBo
     *            - object to be mapped
     * @return mapped JaxB object
     */
    public gov.nih.nci.po.webservices.types.ClinicalResearchStaff convertFromBOToJaxB(
            gov.nih.nci.po.data.bo.ClinicalResearchStaff crsBo) {

        gov.nih.nci.po.webservices.types.ClinicalResearchStaff crs = null;
        if (crsBo != null) {
            crs = new ClinicalResearchStaff();
            crs.setId(crsBo.getId());
            crs.setStatus(getEntityStatus(crsBo.getStatus().name()));

            // populate PersonRole specific attributes into Jaxb object
            populatePersonRoleBOToJaxB(crsBo, crs);
        }

        return crs;
    }

}
