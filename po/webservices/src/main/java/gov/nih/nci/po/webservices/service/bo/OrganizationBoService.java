package gov.nih.nci.po.webservices.service.bo;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import gov.nih.nci.po.data.bo.AbstractOrganization;
import gov.nih.nci.po.data.bo.Alias;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.OrganizationSearchCriteria;
import gov.nih.nci.po.service.OrganizationSearchDTO;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.services.organization.OrganizationDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Wrapper around EJB service to implement business logic
 * for web services without breaking legacy code contracts.
 *
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Service("organizationBoService")
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.TooManyMethods", "PMD.AvoidThrowingRawExceptionTypes" })
public class OrganizationBoService implements OrganizationServiceLocal {

    @Override
    public long create(Organization org) throws EntityValidationException, JMSException {
        User user = null;

        try {
            user =  SecurityServiceProvider.getUserProvisioningManager("po")
                    .getUser(UsernameHolder.getUser());
        } catch (CSException e) {
            throw new RuntimeException(e);
        }

        org.setCreatedBy(user);

        return PoRegistry.getOrganizationService().create(org);
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

        if (current != null) {
            curatedOrg.setCreatedBy(current.getCreatedBy());
            curatedOrg.setOverriddenBy(current.getOverriddenBy());

            handleOrgNameAndAliases(current, curatedOrg);
        }

        Map<String, String[]> errors = PoRegistry.getOrganizationService().validate(curatedOrg);
        if (!errors.isEmpty()) {
            EntityValidationException validationException =  new EntityValidationException(errors);
            throw new ServiceException(validationException);
        }

        if (noChangesMade(current, curatedOrg)) {
            return;
        }

        if (current == null || isCreatedByMe(current)) {

            PoRegistry.getOrganizationService().curate(curatedOrg);
        } else {
            //someone else made it, so create a CR
            try {
                createOrganizationCR(curatedOrg);
            } catch (EntityValidationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean noChangesMade(Organization current, Organization curatedOrg) {
        boolean result = false;

        if (current != null) {
            OrganizationDTO tmp = (OrganizationDTO) PoXsnapshotHelper.createSnapshot(curatedOrg);
            OrganizationCR cr = new OrganizationCR(current);
            PoXsnapshotHelper.copyIntoAbstractModel(tmp, cr, AbstractOrganization.class);

            result = cr.isNoChange() && CollectionUtils.isEqualCollection(current.getAlias(), curatedOrg.getAlias());
        }

        return result;
    }

    private void createOrganizationCR(Organization curatedOrganization) throws EntityValidationException {
        Organization target = PoRegistry.getOrganizationService().getById(curatedOrganization.getId());

        OrganizationCR organizationCR = new OrganizationCR(target);

        OrganizationDTO curatedOrganizationDto
                = (OrganizationDTO) PoXsnapshotHelper.createSnapshot(curatedOrganization);

        curatedOrganizationDto.setIdentifier(null);
        PoXsnapshotHelper.copyIntoAbstractModel(curatedOrganizationDto, organizationCR, AbstractOrganization.class);
        organizationCR.setId(null);

        PoRegistry.getInstance().getServiceLocator().getOrganizationCRService().create(organizationCR);
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

        // set the existing aliases as it was ignored during converter
        updatedInstance.getAlias().addAll(currentInstance.getAlias());

        // check if existing Org name or aliases has the incoming name
        if (nameHasChanged(currentInstance, updatedInstance)
                && aliasIsNotPresent(currentInstance.getAlias(), updatedInstance.getName())) {
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


    /**
     * This method is used to check if the Alias list contains the name(case
     * insensitive).
     *
     * @return true if the name if not present in the list.
     */
    private boolean aliasIsNotPresent(List<Alias> aliasList, String name) {
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
