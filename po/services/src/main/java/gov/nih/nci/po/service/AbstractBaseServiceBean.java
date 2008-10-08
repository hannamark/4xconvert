
/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po-app
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po-app Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the po-app Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po-app Software; (ii) distribute and
 * have distributed to and by third parties the po-app Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.po.service;

import gov.nih.nci.po.util.PoHibernateUtil;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 * @author smatyas
 * @param <T>
 *
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
public abstract class AbstractBaseServiceBean<T extends PersistentObject> {
    private static final String UNCHECKED = "unchecked";
    /**
     * Maximum number of ids in an IN clause.
     */
    public static final int MAX_IN_CLAUSE_SIZE = 500;
    private final Class<T> typeArgument;
    /**
     * message publisher used on update notification.
     */
    @EJB
    private MessageProducerLocal publisher;

    /**
     * @return the publisher
     */
    public MessageProducerLocal getPublisher() {
        return publisher;
    }

    /**
     * @param publisher the publisher to set
     */
    public void setPublisher(MessageProducerLocal publisher) {
        this.publisher = publisher;
    }
    /**
     * default constructor.
     */
    @SuppressWarnings(UNCHECKED)
    public AbstractBaseServiceBean() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        typeArgument = (Class) parameterizedType.getActualTypeArguments()[0];
    }

    /**
     * Get class of the implementation.
     *
     * @return the class
     */
    protected Class<T> getTypeArgument() {
        return typeArgument;
    }

    /**
     * Get the object of type T with the given ID.
     * @param id the id
     * @return the object
     */
    @SuppressWarnings(UNCHECKED)
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public T getById(long id) {
        return (T) PoHibernateUtil.getCurrentSession().get(getTypeArgument(), id);
    }

    /**
     * Get the object of type T with the given IDs.
     * @param ids the ids
     * @return the object
     */
    @SuppressWarnings(UNCHECKED)
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> getByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return Collections.EMPTY_LIST;
        }

        if (ids.length > MAX_IN_CLAUSE_SIZE) {
            throw new IllegalArgumentException("getByIds can only search for " + MAX_IN_CLAUSE_SIZE + " at once.");
        }

        Query q = PoHibernateUtil.getCurrentSession().createQuery("from " + getTypeArgument().getName()
                + " obj where obj.id in (:ids_list)");
        q.setParameterList("ids_list", ids);
        return q.list();
    }

    /**
     * Save the object.
     * @param obj the object
     * @return the id
     * @throws EntityValidationException any validation errors.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public long create(T obj) throws EntityValidationException {
        if (obj.getId() != null) {
            throw new IllegalArgumentException("id must be null on calls to create!");
        }
        ensureValid(obj);
        return ((Long) PoHibernateUtil.getCurrentSession().save(obj)).longValue();
    }

    /**
     * @param sc criteria object to validate
     */
    protected void validateSearchCriteria(SearchCriteria<T> sc) {
        if (sc == null) {
            throw new OneCriterionRequiredException();
        }
        sc.isValid();
    }

    /**
     * @param c Hibernate criteria to set pagination options
     * @param pageSortParams bean containing the options
     */
    protected void setPagination(Criteria c, PageSortParams<?> pageSortParams) {
        if (pageSortParams != null) {
            if (pageSortParams.getPageSize() > 0) {
                c.setMaxResults(pageSortParams.getPageSize());
            }
            if (pageSortParams.getIndex() > 0) {
                c.setFirstResult(pageSortParams.getIndex());
            }
        }
    }

    /**
     * @param c Hibernate criteria to set order by clause
     * @param pageSortParams bean containing the options
     */
    @SuppressWarnings("PMD.AvoidDeeplyNestedIfStmts")
    protected void addOrderBy(Criteria c, PageSortParams<T> pageSortParams) {
        if (pageSortParams != null && pageSortParams.getSortCriterion() != null) {

            StringBuffer orderBy = new StringBuffer("");
            if (CollectionUtils.isNotEmpty(pageSortParams.getSortCriterion())) {
                orderBy.append(" ORDER BY ");
                boolean first = true;
                for (SortCriterion<T> sc : pageSortParams.getSortCriterion()) {
                    if (!first) {
                        orderBy.append(", ");
                    }
                    orderBy.append(sc.getOrderField());
                    orderBy.append((pageSortParams.isDesc() ? " DESC" : " ASC"));
                    first = false;
                }
            }

            String orderByField = orderBy.toString();
            if (pageSortParams.isDesc()) {
                c.addOrder(Order.desc(orderByField));
            } else {
                c.addOrder(Order.asc(orderByField));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> search(SearchCriteria<T> criteria) {
        return search(criteria, null);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(UNCHECKED)
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> search(SearchCriteria<T> criteria,
            PageSortParams<T> pageSortParams) {
        validateSearchCriteria(criteria);
        StringBuffer orderBy = new StringBuffer("");
        if (pageSortParams != null && CollectionUtils.isNotEmpty(pageSortParams.getSortCriterion())) {

            orderBy.append(" ORDER BY ");
            boolean first = true;
            for (SortCriterion<T> sc : pageSortParams.getSortCriterion()) {
                if (!first) {
                    orderBy.append(", ");
                }
                orderBy.append(sc.getOrderField());
                orderBy.append((pageSortParams.isDesc() ? " DESC" : " ASC"));

                first = false;
            }
        }
        Query q = criteria.getQuery(orderBy.toString(), false);

        if (pageSortParams != null) {
            q.setMaxResults(pageSortParams.getPageSize());
            if (pageSortParams.getIndex() > 0) {
                q.setFirstResult(pageSortParams.getIndex());
            }
        }

        return q.list();
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public int count(SearchCriteria<T> criteria) {
        validateSearchCriteria(criteria);
        Query q = criteria.getQuery("", true);
        return ((Number) q.uniqueResult()).intValue();
    }

     /**
      *
      * @param entity the entity to validate
      * @return return validation error messages per invalid field path.
      * @see PoHibernateUtil.validate(entity)
      */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Map<String, String[]> validate(T entity) {
         Map<String, String[]> messages = PoHibernateUtil.validate(entity);
         messages.remove("statusCode");
         return messages;
    }

    /**
     * @param entity the entity to validate
     * @throws EntityValidationException is validation fails
     */
    protected void ensureValid(T entity) throws EntityValidationException {
        Map<String, String[]> errors = validate(entity);
        if (errors != null && !errors.isEmpty()) {
            throw new EntityValidationException(errors);
        }
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void update(T updatedEntity) {
        Session s = PoHibernateUtil.getCurrentSession();
        s.update(updatedEntity);
    }
}
