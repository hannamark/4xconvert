/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The COPPA PO
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This COPPA PO Software License (the License) is between NCI and You. You (or
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
 * its rights in the COPPA PO Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the COPPA PO Software; (ii) distribute and
 * have distributed to and by third parties the COPPA PO Software and any
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
package gov.nih.nci.po.util;

import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.IdentifierScope;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.Curatable;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.AbstractHQLSearchCriteria;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Utility functions for searching.
 * 
 * @see Searchable
 */
public final class SearchableUtils {
    /**
     * root object alias name.
     */
    public static final String ROOT_OBJ_ALIAS = "obj";
    private static final Logger LOG = Logger.getLogger(SearchableUtils.class);
    private static final String NULLIFIED_STATUS_PARAM = "nullifiedStatusParam";

    /**
     * Callback interface.
     */
    public static interface AnnotationCallback {
        /**
         * @param m method callback was invoked on
         * @param result result of invoking the method
         * @throws Exception on error
         */
        @SuppressWarnings("PMD.SignatureDeclareThrowsException")
        void callback(Method m, Object result) throws Exception;

        /**
         * @return any callback saved state
         */
        Object getSavedState();
    }

    private SearchableUtils() {
        // prevent instantiation
    }

    /**
     * @param obj object to inspect
     * @param isCountOnly do a count query
     * @param disallowNullified disallow nullified objects
     * @param orderByClause hql order by clause, if none leave blank 
     * @return query object
     */
    public static Query getQueryBySearchableFields(final Object obj, boolean isCountOnly, boolean disallowNullified,
            String orderByClause) {
        final StringBuffer selectClause = new StringBuffer(AbstractHQLSearchCriteria.SELECT);
        final StringBuffer whereClause = new StringBuffer();
        final Map<String, Object> params = new HashMap<String, Object>();

        selectClause.append((isCountOnly ? "COUNT(" + ROOT_OBJ_ALIAS + ") " : ROOT_OBJ_ALIAS));
        selectClause.append(AbstractHQLSearchCriteria.FROM);
        selectClause.append(obj.getClass().getName());
        selectClause.append(' ');
        selectClause.append(ROOT_OBJ_ALIAS);

        AnnotationCallback ac = new SearchCallback(whereClause, selectClause, params);
        SearchableUtils.iterateAnnotatedMethods(obj, ac);

        handleNullifiedStatus(obj, disallowNullified, whereClause, params);

        Session session = PoHibernateUtil.getCurrentSession();
        selectClause.append(whereClause);
        if (StringUtils.isNotBlank(orderByClause)) {
            selectClause.append(orderByClause);
        }
        Query q = session.createQuery(selectClause.toString());
        if (LOG.isDebugEnabled()) {
            LOG.debug(q.getQueryString());
        }
        setQueryParams(params, q);
        return q;
    }

    private static void handleNullifiedStatus(final Object obj, boolean disallowNullified,
            final StringBuffer whereClause, final Map<String, Object> params) {
        String operator = determineOperator(whereClause);
        if (disallowNullified && obj instanceof Curatable) {
            whereClause.append(String.format("%s %s.statusCode != :%s", operator, ROOT_OBJ_ALIAS,
                    NULLIFIED_STATUS_PARAM));
            params.put(NULLIFIED_STATUS_PARAM, EntityStatus.NULLIFIED);
        } else if (disallowNullified && obj instanceof Correlation) {
            whereClause.append(String.format("%s %s.status != :%s", operator, ROOT_OBJ_ALIAS, NULLIFIED_STATUS_PARAM));
            params.put(NULLIFIED_STATUS_PARAM, RoleStatus.NULLIFIED);
        }
    }

    private static String determineOperator(final StringBuffer whereClause) {
        String operator = "";
        if (StringUtils.isBlank(whereClause.toString())) {
            operator = AbstractHQLSearchCriteria.WHERE;
        } else if (!AbstractHQLSearchCriteria.WHERE.equals(whereClause.toString())) {
            //handle case when no criterion are provided. 
            operator = AbstractHQLSearchCriteria.AND;
        }
        return operator;
    }

    private static void setQueryParams(final Map<String, Object> params, Query q) {
        for (String key : params.keySet()) {
            Object value = params.get(key);
            if (value instanceof Collection<?>) {
                q.setParameterList(key, (Collection<?>) value);
            } else if (value instanceof IdentifierReliability || value instanceof IdentifierScope) {
                q.setParameter(key, value.toString());
            } else {
                q.setParameter(key, value);
            }
        }
    }

    /**
     * @param o object to search over
     * @param cb callback to use
     */
    public static void iterateAnnotatedMethods(Object o, AnnotationCallback cb) {
        if (o == null) {
            return;
        }
        for (Method m : o.getClass().getMethods()) {
            if (m.getAnnotation(Searchable.class) != null) {
                if (m.getReturnType().equals(Void.TYPE)) {
                    throw new IllegalArgumentException(String.format(
                            "The @Searchable annotation cannot be applied to %s.%s() [void return]", o.getClass(), m));
                }
                try {
                    cb.callback(m, m.invoke(o));
                } catch (Exception e) {
                    throw new IllegalArgumentException(String.format(
                            "The @Searchable annotation cannot be applied to %s.%s()", o.getClass(), m), e);
                }
            }
        }
    }

    /**
     * Looks through all Searchable methods and determines if the example object has at least one method that
     * participates in the query by example. For each method marked as searchable, the result of invoking the method is
     * examined as follows:
     * <ul>
     * <li>Null results do not participate in query by example.
     * <li>If the result is a <code>PersistentObject</code> and the <code>getId</code> method returns a non-null value,
     * the object is considered to have a criteria.
     * <li>If the result is a <code>Collection</code> and the <code>isEmpty</code> method returns false, the object is
     * considered to have a criterion.
     * <li>Otherwise, if the result is non-null, the object is considered to have a criterion.
     * </ul>
     * 
     * @param o the object to examine
     * @return whether one (or more) methods participate in the criteria
     */
    public static boolean hasSearchableCriterion(final Object o) {

        AnnotationCallback ac = new OneCriterionSpecifiedCallback();

        iterateAnnotatedMethods(o, ac);
        return (Boolean) ac.getSavedState();
    }

}
