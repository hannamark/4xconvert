package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.dto.OrgFamilyDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.po.data.bo.FamilyFunctionalType;
import gov.nih.nci.po.ws.common.types.Family;
import gov.nih.nci.po.ws.common.types.FamilyMember;
import gov.nih.nci.services.correlation.FamilyOrganizationRelationshipDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Helper class for family functionality.
 * 
 * @author Hugh Reinhart
 * @since Nov 29, 2012
 */
public final class FamilyHelper {

    /**
     * Get a list of all families to which organization belongs.
     * 
     * @param orgId
     *            the PO organization id.
     * @return list of families
     * @throws PAException
     *             exception
     */
    public static List<OrgFamilyDTO> getByOrgId(Long orgId) throws PAException {
        return getByOrgId(orgId, null);
    }

    /**
     * Get a list of all families to which organization belongs with a given
     * functional type.
     * 
     * @param orgId
     *            the PO organization id.
     * @param relationship
     *            the type of organizational relationship, null to get all
     * @return list of families
     * @throws PAException
     *             exception
     */
    @SuppressWarnings("unchecked")
    public static List<OrgFamilyDTO> getByOrgId(Long orgId,
            FamilyFunctionalType relationship) throws PAException {
        List<OrgFamilyDTO> result = new ArrayList<OrgFamilyDTO>();
        if (orgId == null) {
            return result;
        }
        FamilyMember fm = new FamilyMember();
        fm.setOrganizationId(orgId);
        Family family = new Family();
        family.getMember().add(fm);

        List<Family> families = PoRegistry.getFamilyService().search(family);
        if (relationship == null) {
            for (Family tmpF : families) {
                OrgFamilyDTO orgFDto = new OrgFamilyDTO();
                orgFDto.setId(tmpF.getId());
                orgFDto.setName(tmpF.getName());
                orgFDto.setP30SerialNumber(tmpF.getP30SerialNumber());
                result.add(orgFDto);
            }
        } else {
            for (Family tmpF : families) {
                OrgFamilyDTO orgFDto = new OrgFamilyDTO();
                List<FamilyMember> fmList = tmpF.getMember();
                for (FamilyMember familyMember : fmList) {
                    if (familyMember.getType().name().equals(relationship.name())) {
                        orgFDto.setId(tmpF.getId());
                        orgFDto.setName(tmpF.getName());
                        orgFDto.setP30SerialNumber(tmpF.getP30SerialNumber());
                        result.add(orgFDto);
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Get all sibling organizations.
     * 
     * @param orgId
     *            the PO organization id
     * @return list of sibling PO organization ids
     * @throws PAException
     *             exception
     */
    public static List<Long> getAllRelatedOrgs(Long orgId) throws PAException {
        List<OrgFamilyDTO> families = getByOrgId(orgId);
        Set<Long> result = new HashSet<Long>();
        for (OrgFamilyDTO family : families) {
            List<FamilyOrganizationRelationshipDTO> rList = PoRegistry
                    .getFamilyServiceRemote().getActiveRelationships(
                            family.getId());
            for (FamilyOrganizationRelationshipDTO r : rList) {
                result.add(IiConverter.convertToLong(r.getOrgIdentifier()));
            }
        }
        return new ArrayList<Long>(result);
    }

    /**
     * Get all sibling organizations.
     * 
     * @param orgIds
     *            list of PO organization ids
     * @return list of sibling PO organization ids
     * @throws PAException
     *             exception
     */
    public static List<Long> getAllRelatedOrgs(Collection<Long> orgIds)
            throws PAException {
        Set<Long> result = new HashSet<Long>();
        for (Long orgId : orgIds) {
            result.addAll(getAllRelatedOrgs(orgId));
        }
        return new ArrayList<Long>(result);
    }

    /**
     * Get the P30 grant serial number for the Cancer Center associated with the
     * org.
     * 
     * @param orgId
     *            PO organization id
     * @return grant serial number or null if not an organizational member of a
     *         Cancer Center family
     * @throws PAException
     *             exception
     */
    public static String getP30GrantSerialNumber(Long orgId) throws PAException {
        String result = null;
        
        List<OrgFamilyDTO> families = getByOrgId(orgId,
                FamilyFunctionalType.ORGANIZATIONAL);
        if (!families.isEmpty()) {
            for (OrgFamilyDTO family : families) {
                result = family.getP30SerialNumber();
                if (result != null) {
                    break;
                }

            }
        }
        return result;
    }
}
