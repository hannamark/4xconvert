package gov.nih.nci.po.util;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.AbstractRole;
import gov.nih.nci.po.data.bo.Alias;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Overridable;
import gov.nih.nci.po.service.OrganizationSearchCriteria;
import gov.nih.nci.po.service.OrganizationSearchDTO;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * This is a Utility class for po-service project which has common utility
 * methods.
 * 
 * @author Rohit Gupta
 * 
 */
public class PoServiceUtil {

    private static final Logger LOG = Logger.getLogger(PoServiceUtil.class);
    
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
        String boCtepId = null;
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
            name = StringUtils.isBlank(user.getLastName()) && StringUtils.isBlank(user.getFirstName())
                    ? CsmUserUtil.getGridIdentityUsername(user.getLoginName())
                        : (user.getFirstName() + " " + user.getLastName());
        }
        return name;
    }
    
    /**
     * This method returns true if an entity is editable for the given Username.
     * 
     * @param userName - Username 
     * @param createdBy
     *            user who created the entity
     * @param overriddenBy
     *            user who overrode the entity
     * @return true if entity is editable
     */    
    @SuppressWarnings("PMD.CollapsibleIfStatements")
    public static boolean isEntityEditableByUser(String userName, User createdBy, User overriddenBy) {
        boolean isEditable = false;
        try {
            // get the user corresponds to given userName
            User user = SecurityServiceProvider.getUserProvisioningManager("po").getUser(userName);
            if (overriddenBy != null) {
                // i.e. entity is overridden
                if (overriddenBy.getUserId().longValue() == user.getUserId().longValue()) {
                    // i.e. user overrode the entity
                    isEditable = true; // editable  
                }
            } else {
                // i.e. entity is not overridden
                if (createdBy != null) {
                    if (createdBy.getUserId().longValue() == user.getUserId().longValue()) {
                        // i.e. user created the entity
                        isEditable = true; // editable  
                    }
                }
            }        
        } catch (CSException e) {
            LOG.error("CSException while getting the logged-in user. The exception is:" + e);
            isEditable = false;
        }       
        
        // User didn't Overrode/Create the entity, so its not Editable
        return isEditable; 
    }
    
    
    /**
     * This method is used to create a new IdentifiedOrganization object using the
     * passed parameters.
     * @param ctepId CTEP ID
     * @param organization Organization
     * @param setCtepId true if CTEP ID is to be set
     * @return IdentifiedOrganization new IdentifiedOrganization object 
     */
    public static IdentifiedOrganization getNewIdentifiedOrganizationObject(String ctepId,
            gov.nih.nci.po.data.bo.Organization organization,
            boolean setCtepId) {
        
        // get the Organization representing "CTEP"
        Organization ctepOrg = getCtepOrganization();
        
        gov.nih.nci.iso21090.Ii assIden = new gov.nih.nci.iso21090.Ii();
        assIden.setRoot(PoConstants.ORG_CTEP_ID_ROOT);
        assIden.setIdentifierName(PoConstants.ORG_CTEP_ID_IDENTIFIER_NAME);
        if (setCtepId) {
            assIden.setExtension(ctepId);
        }
        IdentifiedOrganization idenOrg = new IdentifiedOrganization();
        idenOrg.setAssignedIdentifier(assIden);
        idenOrg.setPlayer(organization);
        idenOrg.setScoper(ctepOrg);
        // set bi-directional association b/w org & idenorg
        organization.getIdentifiedOrganizations().add(idenOrg);

        return idenOrg;
    }

    /**
     *
     * @param role A role.
     * @return Returns true if the given item is created by the current user, false otherwise.
     */
    public static boolean isCreatedByCurrentUser(AbstractRole role) {
        return role.getCreatedBy() != null
                && StringUtils.equalsIgnoreCase(UsernameHolder.getUser(), role.getCreatedBy().getLoginName());
    }
    
    /**
    *
    * @param organization An organization.
    * @return Returns true if the given organization is created by the current user, false otherwise.
    */
   public static boolean isOrgCreatedByCurrentUser(Organization organization) {
       return organization.getCreatedBy() != null
               && StringUtils.equalsIgnoreCase(UsernameHolder.getUser(), organization.getCreatedBy().getLoginName());
   }

    /**
     *
     * @param overridable An overridable.
     * @return Returns true if the given item is overridden by the current user, false otherwise.
     */
    public static boolean isOverriddenByCurrentUser(Overridable overridable) {
        return overridable.getOverriddenBy() != null
              && StringUtils.equalsIgnoreCase(UsernameHolder.getUser(), overridable.getOverriddenBy().getLoginName());
    }
    
    /**
     * This method is used to check if the Alias list contains the name(case insensitive).
     * @param aliasList AliasList
     * @param name AliasName
     * @return true if the name if not present in the list.
     */
    public static boolean aliasIsNotPresent(List<Alias> aliasList, String name) {
        boolean result = true;
        for (Alias alias : aliasList) {
            if (alias.getValue().equalsIgnoreCase(name)) {
                result = false;
                break;
            }
        }
        return result;
    }
}
