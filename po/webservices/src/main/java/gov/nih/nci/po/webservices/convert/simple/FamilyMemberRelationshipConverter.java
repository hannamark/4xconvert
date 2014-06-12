package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.data.bo.FamilyHierarchicalType;
import gov.nih.nci.po.data.bo.OrganizationRelationship;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.webservices.types.FamilyMemberRelationship;
import gov.nih.nci.po.webservices.types.FamilyMemberRelationshipType;

/**
 * This is Converter class for FamilyMemberRelationship.
 * 
 * @author Rohit Gupta
 */
public class FamilyMemberRelationshipConverter
        extends
        AbstractConverter<gov.nih.nci.po.data.bo.OrganizationRelationship, FamilyMemberRelationship> {

    /**
     * This method is used to convert JaxB object into corresponding BO.
     * 
     *
     * @param fmr
     *            -object to be mapped
     * @return mapped BO Family
     */
    public gov.nih.nci.po.data.bo.OrganizationRelationship convertFromJaxbToBO(
            FamilyMemberRelationship fmr) {

        gov.nih.nci.po.data.bo.OrganizationRelationship orgRelBo = null;
        if (fmr != null) {
            orgRelBo = new gov.nih.nci.po.data.bo.OrganizationRelationship();

            // Set the basic attributes
            orgRelBo.setId(fmr.getId());
            orgRelBo.setFamily(PoRegistry.getFamilyService().getById(
                    fmr.getFamilyId()));
            orgRelBo.setOrganization(PoRegistry.getOrganizationService()
                    .getById(fmr.getOrganizationId()));
            orgRelBo.setRelatedOrganization(PoRegistry.getOrganizationService()
                    .getById(fmr.getRelatedToOrganizationId()));
            orgRelBo.setHierarchicalType(FamilyHierarchicalType.valueOf(fmr
                    .getType().value()));
            orgRelBo.setStartDate(toDate(fmr.getStartDate()));
        }

        return orgRelBo;
    }

    /**
     * This method is used to convert BO object into corresponding JaxB object.
     * 
     *
     * @param orgRelBo
     *            - object to be mapped
     * @return mapped JaxB object
     */
    public gov.nih.nci.po.webservices.types.FamilyMemberRelationship convertFromBOToJaxB(
            OrganizationRelationship orgRelBo) {

        gov.nih.nci.po.webservices.types.FamilyMemberRelationship fmr = null;
        if (orgRelBo != null) {
            fmr = new FamilyMemberRelationship();

            // Set the basic attributes
            fmr.setId(orgRelBo.getId());
            fmr.setFamilyId(orgRelBo.getFamily().getId());
            fmr.setOrganizationId(orgRelBo.getOrganization().getId());
            fmr.setRelatedToOrganizationId(orgRelBo.getRelatedOrganization()
                    .getId());
            fmr.setStartDate(toXMLGregorianCalendar(orgRelBo.getStartDate()));
            fmr.setType(FamilyMemberRelationshipType.fromValue(orgRelBo
                    .getHierarchicalType().name()));
        }

        return fmr;
    }

}
