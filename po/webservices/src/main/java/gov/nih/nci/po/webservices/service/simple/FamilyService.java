package gov.nih.nci.po.webservices.service.simple;

import gov.nih.nci.po.webservices.types.Family;
import gov.nih.nci.po.webservices.types.FamilyMemberRelationship;

import java.util.List;

/**
 * Family Service Interface.
 * 
 * @author Rohit Gupta
 * 
 */
public interface FamilyService {

    /**
     * This method is used to get all the Families matching the given family
     * name. This is a case-insensitive LIKE search.
     * 
     *
     * @param name
     *            familyName
     * @return List<Family> - list of family matching the given familyName
     */
    List<Family> searchFamiliesByName(String name);

    /**
     * This method is used to get all the families to which the given
     * Organization belongs.
     * 
     *
     * @param organizationId
     *            - organizationId for which families to be fetched
     * @return List<Family> - list of Family
     */
    List<Family> searchFamiliesByOrgId(long organizationId);

    /**
     * This method is used to get all FamilyMemberRelationship records for a
     * given family.
     * 
     *
     * @param familyId
     *            familyId for which FamilyMemberRelationship to be fetched
     * @return List<FamilyMemberRelationship> list of FamilyMemberRelationship
     */
    List<FamilyMemberRelationship> getFamilyMemberRelationshipsByFamilyId(
            long familyId);

}
