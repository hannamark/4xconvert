package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.AbstractRole;
import gov.nih.nci.po.data.bo.ChangeRequest;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.Overridable;
import gov.nih.nci.po.data.bo.PersonRole;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.webservices.service.bo.filter.CrCreationFilter;
import gov.nih.nci.po.webservices.service.bo.filter.CreatedByFilter;
import gov.nih.nci.po.webservices.service.bo.filter.RoleUpdateFilter;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import org.apache.commons.lang.StringUtils;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @param <TYPE>    The BO type
 * @param <CR_TYPE> The CR type
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.AvoidThrowingRawExceptionTypes", "PMD.TooManyMethods" })
public abstract class AbstractRoleBoService<TYPE extends Correlation, CR_TYPE extends ChangeRequest<TYPE>>
        implements GenericStructrualRoleServiceLocal<TYPE> {


    private final List<RoleUpdateFilter<TYPE>> updateFilters;
    private final List<CrCreationFilter<TYPE, CR_TYPE>> crCreationFilters;

    /**
     * Default constructor.  Sets up filter to pick up
     * createdBY field on updates.
     */
    public AbstractRoleBoService() {
        updateFilters = new ArrayList<RoleUpdateFilter<TYPE>>();
        updateFilters.add(new CreatedByFilter<TYPE>());

        crCreationFilters = new ArrayList<CrCreationFilter<TYPE, CR_TYPE>>();
    }

    /**
     * @return The EJB service from the Services module.
     */
    protected abstract GenericStructrualRoleServiceLocal<TYPE> getCorrelationService();

    /**
     * @return The EJB service for creating CRs from the Services module.
     */
    protected abstract GenericStructrualRoleCRServiceLocal<CR_TYPE> getCrService();

    /**
     * @param cr A CR.
     * @return True if the CR reflects a set of changes, or false if no changes are present.
     */
    protected abstract boolean hasChanges(CR_TYPE cr);

    /**
     * @param currentInstance The current instance.
     * @param updatedInstance The proposed state.
     * @return A CR for the change
     */
    protected abstract CR_TYPE createCr(TYPE currentInstance, TYPE updatedInstance);

    @Override
    public long create(TYPE structuralRole) throws EntityValidationException, JMSException {
        User user = null;

        try {
            user = SecurityServiceProvider.getUserProvisioningManager("po")
                    .getUser(UsernameHolder.getUser());
        } catch (CSException e) {
            throw new RuntimeException(e);
        }

        ((AbstractRole) structuralRole).setCreatedBy(user);

        return getCorrelationService().create(structuralRole);
    }


    @Override
    public TYPE getById(long id) {
        return getCorrelationService().getById(id);
    }

    @Override
    public List<TYPE> getByIds(Long[] ids) {
        return getCorrelationService().getByIds(ids);
    }

    @Override
    public List<TYPE> getByPlayerIds(Long[] pids) {
        return getCorrelationService().getByPlayerIds(pids);
    }

    @Override
    public void curate(TYPE updatedInstance) throws JMSException {

        TYPE currentInstance = getCorrelationService().getById(updatedInstance.getId());

        applyUpdateFilters(currentInstance, updatedInstance);

        CR_TYPE cr = createCr(currentInstance, updatedInstance);
        applyCrCreateFilters(updatedInstance, cr);


        if (currentInstance != null && !hasChanges(cr)) {
            return;
        }

        if (updateDirectly(currentInstance)) {
            getCorrelationService().curate(updatedInstance);
        } else {
            try {
                getCrService().create(cr);
            } catch (EntityValidationException e) {
                throw new RuntimeException(e);
            }
        }
    }   
    
    /**
     * apply created_by
     * @param updatedInstance updatedInstance
     * @param cr CR
     */
    protected void applyCrCreateFilters(TYPE updatedInstance, CR_TYPE cr) {
        for (CrCreationFilter<TYPE, CR_TYPE> crCreationFilter : crCreationFilters) {
            crCreationFilter.handle(updatedInstance, cr);
        }
    }

    /**
     * 
     * @param currentInstance currentInstance
     * @param updatedInstance updatedInstance
     */
    protected void applyUpdateFilters(TYPE currentInstance, TYPE updatedInstance) {
        for (RoleUpdateFilter<TYPE> filter : updateFilters) {
            filter.handle(currentInstance, updatedInstance);
        }
    }

    /**
     * 
     * @param currentInstance currentInstance
     * @return true if entity can be updated directly
     */
    protected boolean updateDirectly(TYPE currentInstance) {
        return currentInstance == null
                || (isCreatedByMe(currentInstance) && !isOverridden(currentInstance))
                || currentInstance instanceof PersonRole;
    }

    /**
     *
     * @param currentInstance
     * @return True if the current instance is overridden, otherwise return false.
     */
    private boolean isOverridden(TYPE currentInstance) {
        boolean result = false;

        if (currentInstance instanceof Overridable) {
            result = ((Overridable) currentInstance).getOverriddenBy() != null;
        }

        return result;
    }


    @Override
    public Map<String, String[]> validate(TYPE entity) {
        return getCorrelationService().validate(entity);
    }

    @Override
    public List<TYPE> search(SearchCriteria<TYPE> criteria) {
        return getCorrelationService().search(criteria);
    }

    @Override
    public List<TYPE> search(SearchCriteria<TYPE> criteria, PageSortParams<TYPE> pageSortParams) {
        return getCorrelationService().search(criteria, pageSortParams);
    }

    @Override
    public int count(SearchCriteria<TYPE> criteria) {
        return getCorrelationService().count(criteria);
    }

    /**
     * @param currentInstance The current instance of the model.
     * @return True if the current user created the currentInstance, false otherwise.
     */
    protected boolean isCreatedByMe(TYPE currentInstance) {
        String loginName = null;

        User createdBy = ((AbstractRole) currentInstance).getCreatedBy();

        if (createdBy != null) {
            loginName = createdBy.getLoginName();
        }

        return StringUtils.equals(UsernameHolder.getUser(), loginName);
    }

    /**
     * Getter for the update filters.
     *
     * @return The update filter list.
     */
    public List<RoleUpdateFilter<TYPE>> getUpdateFilters() {
        return updateFilters;
    }


    /**
     * Getter for the CR Creation filters.
     *
     * @return The CR Creation filter list.
     */
    public List<CrCreationFilter<TYPE, CR_TYPE>> getCrCreationFilters() {
        return crCreationFilters;
    }
}
