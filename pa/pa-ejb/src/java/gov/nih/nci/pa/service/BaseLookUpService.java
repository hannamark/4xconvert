/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.AbstractLookUpEntity;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.util.HibernateUtil;

import java.util.List;

import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;


/**
 * @author asharma
 *
 * @param <BO>
 */
public class BaseLookUpService<BO extends AbstractLookUpEntity> extends AbstractBaseSearchBean<BO> {
    private static final String UNCHECKED = "unchecked";
    private final Class<BO> typeArgument;

    /**
     *
     * @param typeArgument BO
     */
    public BaseLookUpService(Class<BO> typeArgument) {
        this.typeArgument = typeArgument;
    }

    /**
     * Gets By Id.
     *
     * @param boId the bo id
     *
     * @return the selected
     *
     * @throws PAException the PA exception
     */
    @SuppressWarnings(UNCHECKED)
    public BO getById(Long boId) throws PAException {
        Session session = HibernateUtil.getCurrentSession();
        return (BO) session.get(getTypeArgument(), boId);
    }

    /**
     * Gets By Code.
     *
     * @param bo the bo
     *
     * @return the selected
     *
     * @throws PAException the PA exception
     */
    public BO getByCode(BO bo) throws PAException {
        List<BO> bos = search(new AnnotatedBeanSearchCriteria<BO>(bo));
        return bos.get(0);
    }

    /**
     * @return the typeArgument
     */
    public Class<BO> getTypeArgument() {
        return typeArgument;
    }
}
