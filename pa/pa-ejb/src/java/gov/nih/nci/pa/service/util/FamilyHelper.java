package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.OrgFamilyDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.po.data.bo.FamilyFunctionalType;
import gov.nih.nci.services.correlation.FamilyOrganizationRelationshipDTO;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.family.FamilyP30DTO;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Helper class for family functionality.
 * @author Hugh Reinhart
 * @since Nov 29, 2012
 */
public final class FamilyHelper {

    /**
     * Get a list of all families to which organization belongs.
     * @param orgId the PO organization id.
     * @return list of families
     * @throws PAException exception
     */
    public static List<OrgFamilyDTO> getByOrgId(Long orgId) throws PAException {
        return getByOrgId(orgId, null);
    }

    /**
     * Get a list of all families to which organization belongs with a given functional type.
     * @param orgId the PO organization id.
     * @param relationship the type of organizational relationship, null to get all
     * @return list of families
     * @throws PAException exception
     */
    @SuppressWarnings("unchecked")
    public static List<OrgFamilyDTO> getByOrgId(Long orgId, FamilyFunctionalType relationship) throws PAException {
        List<OrgFamilyDTO> result = new ArrayList<OrgFamilyDTO>();
        if (orgId == null) {
            return result;
        }
        OrganizationDTO orgCriteria = new OrganizationDTO();
        orgCriteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
        LimitOffset limit = new LimitOffset(1, 0);
        try {
            List<OrganizationDTO> orgList = PoRegistry.getOrganizationEntityService().search(orgCriteria, limit);
            if (CollectionUtils.isEmpty(orgList)) {
                return result;
            }
            OrganizationDTO org = orgList.get(0);
            Set<Ii> famOrgRelIiList = new HashSet<Ii>();
            if (CollectionUtils.isNotEmpty(org.getFamilyOrganizationRelationships().getItem())) {
                if (relationship == null) {
                    famOrgRelIiList.addAll(org.getFamilyOrganizationRelationships().getItem());
                } else {
                    for (Ii ii : (Set<Ii>) org.getFamilyOrganizationRelationships().getItem()) {
                        FamilyOrganizationRelationshipDTO forDto = 
                                PoRegistry.getFamilyService().getFamilyOrganizationRelationship(ii);
                        String famOrgRelType = CdConverter.convertCdToString(forDto.getFunctionalType());
                        if (StringUtils.equalsIgnoreCase(relationship.name(), famOrgRelType)) {
                            famOrgRelIiList.add(ii);
                        }
                    }
                }
            }
            Map<Ii, FamilyDTO> familyMap = PoRegistry.getFamilyService().getFamilies(famOrgRelIiList);
            for (FamilyDTO dto : familyMap.values()) {
                OrgFamilyDTO family = new OrgFamilyDTO();
                family.setId(IiConverter.convertToLong(dto.getIdentifier()));
                family.setName(EnOnConverter.convertEnOnToString(dto.getName()));
                result.add(family);
            }
        } catch (TooManyResultsException e) {
            throw new PAException(e);
        }
        return result;
    }

    /**
     * Get all sibling organizations.
     * @param orgId the PO organization id
     * @return list of sibling PO organization ids
     * @throws PAException exception
     */
    public static List<Long> getAllRelatedOrgs(Long orgId) throws PAException {
        List<OrgFamilyDTO> families = getByOrgId(orgId);
        Set<Long> result = new HashSet<Long>();
        for (OrgFamilyDTO family : families) {
            List<FamilyOrganizationRelationshipDTO> rList = PoRegistry.getFamilyService()
                    .getActiveRelationships(family.getId());
            for (FamilyOrganizationRelationshipDTO r : rList) {
                result.add(IiConverter.convertToLong(r.getOrgIdentifier()));
            }
        }
        return new ArrayList<Long>(result);
    }

    /**
     * Get all sibling organizations.
     * @param orgIds list of PO organization ids
     * @return list of sibling PO organization ids
     * @throws PAException exception
     */
    public static List<Long> getAllRelatedOrgs(Collection<Long> orgIds)throws PAException {
        Set<Long> result = new HashSet<Long>();
        for (Long orgId : orgIds) {
            result.addAll(getAllRelatedOrgs(orgId));
        }
        return new ArrayList<Long>(result);
    }

    /**
     * Get the P30 grant serial number for the Cancer Center associated with the org. 
     * @param orgId PO organization id
     * @return grant serial number or null if not an organizational member of a Cancer Center family
     * @throws PAException exception
     */
    public static String getP30GrantSerialNumber(Long orgId) throws PAException {
        String result = null;
        List<OrgFamilyDTO> families = getByOrgId(orgId, FamilyFunctionalType.ORGANIZATIONAL);
        if (!families.isEmpty()) {
            for (OrgFamilyDTO family : families) {
                FamilyP30DTO p30 = PoRegistry.getFamilyService().getP30Grant(family.getId());
                if (p30 != null) {
                    result = EnOnConverter.convertEnOnToString(p30.getSerialNumber());
                    break;
                }
                
            }
        }
        return result; 
    }
}
