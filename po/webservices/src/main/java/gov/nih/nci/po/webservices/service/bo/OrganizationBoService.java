package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.AbstractOrganization;
import gov.nih.nci.po.data.bo.Alias;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.data.bo.Overridable;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.OrganizationSearchCriteria;
import gov.nih.nci.po.service.OrganizationSearchDTO;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.util.PoConstants;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoServiceUtil;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * Wrapper around EJB service to implement business logic
 * for web services without breaking legacy code contracts.
 *
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 * @author Rohit Gupta
 */
@Service("organizationBoService")
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.TooManyMethods", "PMD.AvoidThrowingRawExceptionTypes" })
public class OrganizationBoService implements OrganizationServiceLocal {

    @Override
    public long create(Organization org) throws EntityValidationException, JMSException {
        User user = null;

        try {
            user = SecurityServiceProvider.getUserProvisioningManager("po")
                    .getUser(UsernameHolder.getUser());
        } catch (CSException e) {
            throw new RuntimeException(e);
        }

        org.setCreatedBy(user);

        return PoRegistry.getOrganizationService().create(org);
    }
    

    @Override
    public long create(Organization org, String ctepId)
            throws EntityValidationException, JMSException {
        User user = null;

        try {
            user = SecurityServiceProvider.getUserProvisioningManager("po")
                    .getUser(UsernameHolder.getUser());
        } catch (CSException e) {
            throw new RuntimeException(e);
        }

        org.setCreatedBy(user);

        return PoRegistry.getOrganizationService().create(org, ctepId);
    }

    @Override
    public Organization getById(long id) {
        return PoRegistry.getOrganizationService().getById(id);
    }

    @Override
    public Map<String, String[]> validate(Organization entity) {
        return PoRegistry.getOrganizationService().validate(entity);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void curate(Organization curatedOrg) throws JMSException {

        Organization current = PoRegistry.getOrganizationService().getById(curatedOrg.getId());
        String curOrgName = curatedOrg.getName();

        if (current != null) {
            curatedOrg.setCreatedBy(current.getCreatedBy());
            curatedOrg.setOverriddenBy(current.getOverriddenBy());

            handleOrgNameAndAliases(current, curatedOrg);
        }

        Map<String, String[]> errors = PoRegistry.getOrganizationService().validate(curatedOrg);
        if (!errors.isEmpty()) {
            EntityValidationException validationException = new EntityValidationException(errors);
            throw new ServiceException(validationException);
        }

        OrganizationCR organizationCR = createOrganizationCR(current, curatedOrg);
        organizationCR.setName(curOrgName); // as 'handleOrgNameAndAliases()' will set older name

        if (current != null && organizationCR.isNoChange()) {
            return;
        }

        if (updateDirectly(current)) {
            PoRegistry.getOrganizationService().curate(curatedOrg);
        } else {
            //someone else made it, so create a CR
            try {
                PoRegistry.getInstance().getServiceLocator().getOrganizationCRService().create(organizationCR);
            } catch (EntityValidationException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    @Override
    public void curate(Organization curatedOrg, String ctepId)
            throws EntityValidationException, JMSException {
        Organization current = PoRegistry.getOrganizationService().getById(curatedOrg.getId());
        String curOrgName = curatedOrg.getName();

        if (current != null) {
            curatedOrg.setCreatedBy(current.getCreatedBy());
            curatedOrg.setOverriddenBy(current.getOverriddenBy());

            handleOrgNameAndAliases(current, curatedOrg);
        }

        Map<String, String[]> errors = PoRegistry.getOrganizationService().validate(curatedOrg);
        if (!errors.isEmpty()) {
            EntityValidationException validationException = new EntityValidationException(errors);
            throw new ServiceException(validationException);
        }

        OrganizationCR organizationCR = createOrganizationCR(current, curatedOrg);
        organizationCR.setName(curOrgName); // as 'handleOrgNameAndAliases()' will set older name

        if (current != null && organizationCR.isNoChange() && !isCtepIdChanged(current, ctepId)) {
            return;
        }

        if (updateDirectly(current)) {
            PoRegistry.getOrganizationService().curate(curatedOrg, ctepId);
        } else {
            //someone else made it, so create a CR
            try {
                PoRegistry.getInstance().getServiceLocator().
                    getOrganizationCRService().create(organizationCR, ctepId);                
            } catch (EntityValidationException e) {
                throw new RuntimeException(e);
            }
        }
        
    }
    
    private boolean isCtepIdChanged(Organization current, String curatedOrgCtepId) {
        
        String currentCtepId = null;        
        
        if (CollectionUtils.isNotEmpty(current.getIdentifiedOrganizations())) {
            Iterator<IdentifiedOrganization> iterator = current.getIdentifiedOrganizations().iterator();
            // Iterate and look for the one that has CTEP ID root
            while (iterator.hasNext()) {
                IdentifiedOrganization idenOrg = iterator.next();
                String root = idenOrg.getAssignedIdentifier().getRoot();
                if (PoConstants.ORG_CTEP_ID_ROOT.equalsIgnoreCase(root)) {
                    currentCtepId = idenOrg.getAssignedIdentifier().getExtension();
                 }            
            }
        }        
        
        return !StringUtils.equalsIgnoreCase(currentCtepId, curatedOrgCtepId); 
    }

    /**
     *
     * @param current The current state.
     * @return True if we need to update directly, false if a CR is needed.
     */
    private boolean updateDirectly(Organization current) {
        return current == null || (isCreatedByMe(current) && current.getOverriddenBy() == null);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void override(Overridable overridable, User overriddenBy) {
        PoRegistry.getOrganizationService().override(overridable, overriddenBy);
    }


    private OrganizationCR createOrganizationCR(Organization current, Organization curatedOrganization) {
        OrganizationCR organizationCR = new OrganizationCR(current);

        OrganizationDTO curatedOrganizationDto
                = (OrganizationDTO) PoXsnapshotHelper.createSnapshot(curatedOrganization);

        curatedOrganizationDto.setIdentifier(null);
        PoXsnapshotHelper.copyIntoAbstractModel(curatedOrganizationDto, organizationCR, AbstractOrganization.class);
        organizationCR.getAlias().addAll(curatedOrganization.getAlias());
        organizationCR.setId(null);

        return organizationCR;
    }

    @Override
    public Set<Correlation> getAssociatedPlayedRoles(Organization o) {
        return PoRegistry.getOrganizationService().getAssociatedPlayedRoles(o);
    }

    @Override
    public Set<Correlation> getAssociatedScopedRoles(Organization o) {
        return PoRegistry.getOrganizationService().getAssociatedScopedRoles(o);
    }

    @Override
    public List<OrganizationSearchDTO> search(OrganizationSearchCriteria criteria,
                                              PageSortParams<OrganizationSearchDTO> pageSortParams) {
        return PoRegistry.getOrganizationService().search(criteria, pageSortParams);
    }

    @Override
    public List<OrganizationSearchDTO> getInboxOrgs(PageSortParams<OrganizationSearchDTO> pageSortParams) {
        return PoRegistry.getOrganizationService().getInboxOrgs(pageSortParams);
    }

    @Override
    public long countInboxOrgs() {
        return PoRegistry.getOrganizationService().countInboxOrgs();
    }

    @Override
    public void removeChangeRequest(OrganizationCR cr) {
        PoRegistry.getOrganizationService().removeChangeRequest(cr);
    }

    @Override
    public List<Organization> search(SearchCriteria<Organization> criteria) {
        return PoRegistry.getOrganizationService().search(criteria);
    }

    @Override
    public List<Organization> search(SearchCriteria<Organization> criteria,
                                     PageSortParams<Organization> pageSortParams) {
        return PoRegistry.getOrganizationService().search(criteria, pageSortParams);
    }

    @Override
    public int count(SearchCriteria<Organization> criteria) {
        return PoRegistry.getOrganizationService().count(criteria);
    }

    @Override
    public long count(OrganizationSearchCriteria criteria) {
        return PoRegistry.getOrganizationService().count(criteria);
    }

    private static boolean isCreatedByMe(Organization organization) {
        String createdBy = null;

        if (organization.getCreatedBy() != null) {
            createdBy = organization.getCreatedBy().getLoginName();
        }

        return StringUtils.equals(UsernameHolder.getUser(), createdBy);
    }


    /**
     * This method is used to handle the Org name & aliases. It will check if
     * the incoming Org name is same as Existing Org Name or any of the existing
     * aliases. If not, then it will add the new name to the list of aliases.
     */
    private void handleOrgNameAndAliases(Organization currentInstance, Organization updatedInstance) {

        // set the existing aliases as it was ignored during converter (if they are not already present)
        if (CollectionUtils.isNotEmpty(currentInstance.getAlias())) {
            for (int i = 0; i < currentInstance.getAlias().size(); i++) {
                Alias alias = currentInstance.getAlias().get(i);
                if (PoServiceUtil.aliasIsNotPresent(updatedInstance.getAlias(), alias.getValue())) {                    
                    updatedInstance.getAlias().add(alias);
                }            
            }            
        }        

        // check if existing Org name or aliases has the incoming name
        if (nameHasChanged(currentInstance, updatedInstance)
                && PoServiceUtil.aliasIsNotPresent(currentInstance.getAlias(), updatedInstance.getName())) {
            // if not then add it new name to the list of org aliases
            updatedInstance.getAlias().add(
                    new gov.nih.nci.po.data.bo.Alias(updatedInstance.getName()));
        }

        // set name to the existing name as it might have been overwritten
        // during JAXB-BO converter (set it at the 'end')
        updatedInstance.setName(currentInstance.getName());

    }

    private boolean nameHasChanged(Organization currentInstance, Organization updatedInstance) {
        return !StringUtils.equalsIgnoreCase(currentInstance.getName(), updatedInstance.getName());
    }


    @Override
    public void curateWithoutCRProcessing(Organization curatedOrg)
            throws JMSException {
        PoRegistry.getOrganizationService().curateWithoutCRProcessing(curatedOrg);
        
    }


    @Override
    public Long getDuplicateOfNullifiedOrg(String ctepID) {
        return PoRegistry.getOrganizationService().getDuplicateOfNullifiedOrg(ctepID);
    }


}
