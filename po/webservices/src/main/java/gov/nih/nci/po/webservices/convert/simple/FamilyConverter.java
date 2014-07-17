package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.data.bo.FamilyFunctionalType;
import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.bo.FamilyP30;
import gov.nih.nci.po.data.bo.FamilyStatus;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.Family;
import gov.nih.nci.po.webservices.types.FamilyMember;
import gov.nih.nci.po.webservices.types.FamilyMemberType;

import org.apache.commons.collections.CollectionUtils;

import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

/**
 * This is Converter class for Family.
 * 
 * @author Rohit Gupta
 */
public class FamilyConverter
        extends
        AbstractConverter<gov.nih.nci.po.data.bo.Family, gov.nih.nci.po.webservices.types.Family> {

    /**
     * This method is used to convert JaxB object into corresponding BO.
     * 
     *
     * @param family
     *            -object to be mapped
     * @return mapped BO Family
     */
    public gov.nih.nci.po.data.bo.Family convertFromJaxbToBO(
            Family family) {

        gov.nih.nci.po.data.bo.Family familyBo = null;
        if (family != null) {
            familyBo = new gov.nih.nci.po.data.bo.Family();

            // Set the basic attributes
            familyBo.setName(family.getName());
            familyBo.setId(family.getId());
            familyBo.setStartDate(toDate(family.getStartDate()));
            gov.nih.nci.po.data.bo.FamilyP30 familyP30 = new FamilyP30();
            familyP30.setSerialNumber(family.getP30SerialNumber());
            familyBo.setFamilyP30(familyP30);
            familyBo.setStatusCode(FamilyStatus.valueOf(family.getStatus().value()));

            // Set the FamilyMember list
            populateJaxbFamilyMemberListInBo(family, familyBo);
        }

        return familyBo;
    }

    /**
     * This method is used to convert BO object into corresponding JaxB object.
     * 
     *
     * @param familyBo
     *            - object to be mapped
     * @return mapped JaxB object
     */
    public gov.nih.nci.po.webservices.types.Family convertFromBOToJaxB(
            gov.nih.nci.po.data.bo.Family familyBo) {

        gov.nih.nci.po.webservices.types.Family family = null;

        if (familyBo != null) {
            family = new Family();

            // Set the basic attributes
            family.setId(familyBo.getId());
            family.setName(familyBo.getName());
            family.setStartDate(toXMLGregorianCalendar(familyBo.getStartDate()));
            if (familyBo.getFamilyP30() != null) {
                family.setP30SerialNumber(familyBo.getFamilyP30().getSerialNumber());
            }           
            family.setStatus(EntityStatus.fromValue(familyBo.getStatusCode().name()));

            // Set the FamilyMember list
            populateBoFamilyMemberListInJaxb(familyBo, family);
        }

        return family;
    }

    /**
     * This method is used to populate FamilyMemberList from JaxB to BO object.
     */
    private void populateJaxbFamilyMemberListInBo(
            gov.nih.nci.po.webservices.types.Family family,
            gov.nih.nci.po.data.bo.Family familyBo) {

        List<FamilyMember> fmList = family.getMember();
        if (CollectionUtils.isNotEmpty(fmList)) {
            for (FamilyMember familyMember : fmList) {
                FamilyOrganizationRelationship faOrgRel = new FamilyOrganizationRelationship();
                faOrgRel.setFamily(familyBo);
                gov.nih.nci.po.data.bo.Organization organization = PoRegistry
                        .getOrganizationService().getById(
                                familyMember.getOrganizationId());
                faOrgRel.setOrganization(organization);
                faOrgRel.setFunctionalType(FamilyFunctionalType
                        .valueOf(familyMember.getType().value()));
                faOrgRel.setStartDate(toDate(familyMember.getStartDate()));
                faOrgRel.setEndDate(toDate(familyMember.getEndDate()));

                familyBo.getFamilyOrganizationRelationships().add(faOrgRel);
            }
        }
    }

    /**
     * This method is used to populate FamilyMemberList from BO to JaxB object.
     */
    private void populateBoFamilyMemberListInJaxb(
            gov.nih.nci.po.data.bo.Family familyBo,
            gov.nih.nci.po.webservices.types.Family family) {

        SortedSet<FamilyOrganizationRelationship> faOrgRelSet = familyBo
                .getFamilyOrganizationRelationships();
        if (CollectionUtils.isNotEmpty(faOrgRelSet)) {
            Iterator<gov.nih.nci.po.data.bo.FamilyOrganizationRelationship> iterator = faOrgRelSet
                    .iterator();
            while (iterator.hasNext()) {
                gov.nih.nci.po.data.bo.FamilyOrganizationRelationship faOrgRel = iterator
                        .next();
                gov.nih.nci.po.webservices.types.FamilyMember familyMember = new FamilyMember();
                familyMember.setFamilyId(faOrgRel.getFamily().getId());
                familyMember.setOrganizationId(faOrgRel.getOrganization()
                        .getId());
                familyMember.setType(FamilyMemberType.fromValue(faOrgRel
                        .getFunctionalType().name()));
                familyMember.setStartDate(toXMLGregorianCalendar(faOrgRel
                        .getStartDate()));
                familyMember.setEndDate(toXMLGregorianCalendar(faOrgRel
                        .getEndDate()));

                family.getMember().add(familyMember);
            }
        }
    }
}
