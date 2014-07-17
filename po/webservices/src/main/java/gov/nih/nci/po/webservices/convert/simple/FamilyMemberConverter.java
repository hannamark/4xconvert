package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.data.bo.FamilyFunctionalType;
import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.webservices.types.FamilyMember;
import gov.nih.nci.po.webservices.types.FamilyMemberType;
import gov.nih.nci.po.webservices.util.PoWSUtil;

/**
 * This is Converter class for FamilyOrganizationRelationship.
 * 
 * @author Rohit Gupta
 */
public class FamilyMemberConverter {

    /**
     * This method is used to convert JaxB object into corresponding BO.
     * 
     * 
     * @param familyMember
     *            -object to be mapped
     * @return mapped BO Family
     */
    public gov.nih.nci.po.data.bo.FamilyOrganizationRelationship convertFromJaxbToBO(FamilyMember familyMember) {
        
        FamilyOrganizationRelationship famOrgRelBo = null;   
        if (familyMember != null) {
            famOrgRelBo = new FamilyOrganizationRelationship();
            gov.nih.nci.po.data.bo.Organization organization = PoRegistry
                    .getOrganizationService().getById(familyMember.getOrganizationId());
            famOrgRelBo.setOrganization(organization);
            famOrgRelBo.setFunctionalType(FamilyFunctionalType.valueOf(familyMember.getType().value()));
            famOrgRelBo.setStartDate(PoWSUtil.toDate(familyMember.getStartDate()));
            famOrgRelBo.setEndDate(PoWSUtil.toDate(familyMember.getEndDate()));
            gov.nih.nci.po.data.bo.Family family = PoRegistry.getFamilyService().getById(familyMember.getFamilyId());
            famOrgRelBo.setFamily(family);
        }        
        
        return famOrgRelBo;
    }

    /**
     * This method is used to convert BO object into corresponding JaxB object.
     * 
     * 
     * @param famOrgRelBo
     *            - object to be mapped
     * @return mapped JaxB object
     */
    public gov.nih.nci.po.webservices.types.FamilyMember convertFromBOToJaxB(
            gov.nih.nci.po.data.bo.FamilyOrganizationRelationship famOrgRelBo) {

        gov.nih.nci.po.webservices.types.FamilyMember familyMember = null;
        if (famOrgRelBo != null) {
            familyMember = new FamilyMember();
            familyMember.setFamilyId(famOrgRelBo.getFamily().getId());
            familyMember.setOrganizationId(famOrgRelBo.getOrganization().getId());
            familyMember.setType(FamilyMemberType.fromValue(famOrgRelBo.getFunctionalType().name()));
            familyMember.setStartDate(PoWSUtil.toXMLGregorianCalendar(famOrgRelBo.getStartDate()));
            familyMember.setEndDate(PoWSUtil.toXMLGregorianCalendar(famOrgRelBo.getEndDate()));
        }        
        
        return familyMember;
    }

}
