package gov.nih.nci.po.webservices.service.bo;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import gov.nih.nci.po.data.bo.AbstractRole;
import gov.nih.nci.po.data.bo.ChangeRequest;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.PersonRole;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;
import org.apache.commons.lang.StringUtils;

import javax.jms.JMSException;
import java.util.List;
import java.util.Map;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 *
 * @param <TYPE> The BO type
 * @param <CR_TYPE> The CR type
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.AvoidThrowingRawExceptionTypes" })
public abstract class AbstractRoleBoService<TYPE extends Correlation, CR_TYPE extends ChangeRequest<TYPE>>
        implements GenericStructrualRoleServiceLocal<TYPE> {

    /**
     *
     * @return The EJB service from the Services module.
     */
    protected abstract GenericStructrualRoleServiceLocal<TYPE> getCorrelationService();

    /**
     *
     * @return The EJB service for creating CRs from the Services module.
     */
    protected abstract GenericStructrualRoleCRServiceLocal<CR_TYPE> getCrService();

    /**
     *
     * @param cr A CR.
     * @return True if the CR reflects a set of changes, or false if no changes are present.
     */
    protected abstract boolean hasChanges(CR_TYPE cr);

    /**
     *
     * @param currentInstance The current instance.
     * @param updatedInstance The proposed state.
     * @return A CR for the change
     */
    protected abstract CR_TYPE createCr(TYPE currentInstance, TYPE updatedInstance);

    @Override
    public long create(TYPE structuralRole) throws EntityValidationException, JMSException {
        User user = null;

        try {
            user =  SecurityServiceProvider.getUserProvisioningManager("po")
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
        return  getCorrelationService().getByPlayerIds(pids);
    }

    @Override
    public void curate(TYPE updatedInstance) throws JMSException {

        TYPE currentInstance = getCorrelationService().getById(updatedInstance.getId());

        if (currentInstance != null) {
            ((AbstractRole) updatedInstance).setCreatedBy(((AbstractRole) currentInstance).getCreatedBy());
        }

        CR_TYPE cr = createCr(currentInstance, updatedInstance);

        if (currentInstance != null && !hasChanges(cr)) {
            return;
        }

        if (currentInstance == null || isCreatedByMe(currentInstance) || currentInstance instanceof PersonRole) {
            getCorrelationService().curate(updatedInstance);
        } else {

            try {
                getCrService().create(cr);
            } catch (EntityValidationException e) {
                throw new RuntimeException(e);
            }
        }


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

    private boolean isCreatedByMe(TYPE currentInstance) {
        String loginName = null;

        User createdBy = ((AbstractRole) currentInstance).getCreatedBy();

        if (createdBy != null) {
            loginName = createdBy.getLoginName();
        }

        return StringUtils.equals(UsernameHolder.getUser(), loginName);
    }
}
