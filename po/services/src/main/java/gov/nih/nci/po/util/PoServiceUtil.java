package gov.nih.nci.po.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.service.OrganizationSearchCriteria;
import gov.nih.nci.po.service.OrganizationSearchDTO;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;

/**
 * This is a Utility class for po-service project which has common utility
 * methods.
 * 
 * @author Rohit Gupta
 * 
 */
public class PoServiceUtil {

    /**
     * This method is used to fetch the Organization representing "CTEP".
     * 
     * @return Organization - Organization representing "CTEP".
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static gov.nih.nci.po.data.bo.Organization getCtepOrganization() {
        gov.nih.nci.po.data.bo.Organization organization = null;

        List<String> dynamicSortCriteria = new ArrayList<String>();
        dynamicSortCriteria.add("ID");
        PageSortParams params = new PageSortParams(PoConstants.DEFAULT_SEARCH_LIMIT, 0, null, false,
                dynamicSortCriteria);

        OrganizationSearchCriteria orgSerCriteria = new OrganizationSearchCriteria();
        orgSerCriteria.setName(PoConstants.CTEP_ORG_NAME);

        // search result is returning LIKE search
        List<OrganizationSearchDTO> orgSearchDtoList = PoRegistry
                .getOrganizationService().search(orgSerCriteria, params);
        if (CollectionUtils.isNotEmpty(orgSearchDtoList)) {
            for (OrganizationSearchDTO orgDto : orgSearchDtoList) {
                // iterate thru the list and check for EXACT match
                if (PoConstants.CTEP_ORG_NAME
                        .equalsIgnoreCase(orgDto.getName())) {
                    long orgId = orgDto.getId();
                    organization = PoRegistry.getOrganizationService().getById(
                            orgId);
                    break;
                }
            }
        }

        return organization;
    }
    
    /**
     * This method is used to get the CtepId from the OrgRole BO.
     * 
     * @param aeOrgRoleBo
     *            - BO object - ResearchOrganization or HealthCareFacility
     * @return CtepId
     */
    public static String getOrgRoleBoCtepId(
            gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole aeOrgRoleBo) {
        String boCtepId = "";
        Set<gov.nih.nci.iso21090.Ii> otherIdenSet = aeOrgRoleBo
                .getOtherIdentifiers();

        if (CollectionUtils.isNotEmpty(otherIdenSet)) {
            Iterator<Ii> iterator = otherIdenSet.iterator();
            // Iterate and look for the one that has CTEP ID root
            while (iterator.hasNext()) {
                Ii ii = iterator.next();
                String root = ii.getRoot();
                if (PoConstants.ORG_CTEP_ID_ROOT.equalsIgnoreCase(root)) {
                    boCtepId = ii.getExtension();
                }
            }
        }
        return boCtepId;
    }
    
    /**
     * This method will return the user name.
     * @param user User
     * @return user name
     */
    public static String getUserName(User user) {
        String name = "";
        if (user != null) {
            name = StringUtils
                    .isBlank(user.getLastName() + user.getFirstName()) ? CsmUserUtil
                    .getGridIdentityUsername(user.getLoginName()) : (user
                    .getFirstName() + " " + user.getLastName());
        }
        return name;
    }

}
