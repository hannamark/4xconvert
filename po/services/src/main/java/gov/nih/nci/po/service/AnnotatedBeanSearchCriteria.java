/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po Software License (the License) is between NCI and You. You (or
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
 * its rights in the po Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po Software; (ii) distribute and
 * have distributed to and by third parties the po Software and any
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

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import com.fiveamsolutions.nci.commons.search.SearchableUtils;
import gov.nih.nci.po.data.bo.CuratableEntity;
import gov.nih.nci.po.data.bo.CuratableRole;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.util.PoHibernateUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Map;

/**
 * @author Scott Miller
 * @param <T> the type.
 */
public class AnnotatedBeanSearchCriteria<T extends PersistentObject>
    extends com.fiveamsolutions.nci.commons.search.AbstractAnnotatedBeanSearchCriteria<T> {

    private boolean disallowNullified;

    /**
     * default constructor.
     * @param o the example.
     */
    public AnnotatedBeanSearchCriteria(T o) {
        this(o, true);
    }

    /**
     * default constructor.
     * @param o the example.
     * @param disallowNullified should we disallow nullified
     */
    public AnnotatedBeanSearchCriteria(T o, boolean disallowNullified) {
        super(o);
        this.disallowNullified = disallowNullified;
    }

    /**
     * Adds the correct helper and calls super.
     * @param orderByProperty order by
     * @param isCountOnly perform count query
     * @return hibernate query
     */
    @Override
    public Query getQuery(String orderByProperty, boolean isCountOnly) {
        return SearchableUtils.getQueryBySearchableFields(getCriteria(), isCountOnly, orderByProperty,
                                                          getSession(), new NullifiedHelper(disallowNullified));
    }

    /**
     * Adds the correct helper and calls super.
     * @param orderByProperty order by
     * @param leftJoinClause the left join clause
     * @param isCountOnly perform count query
     * @return hibernate query
     */
     @Override
     public Query getQuery(String orderByProperty, String leftJoinClause, boolean isCountOnly) {
         return SearchableUtils.getQueryBySearchableFields(getCriteria(), isCountOnly, orderByProperty,
                 leftJoinClause, getSession(), new NullifiedHelper(disallowNullified));
     }

    /**
     * {@inheritDoc}
     */
    @Override
    public Session getSession() {
        return PoHibernateUtil.getCurrentSession();
    }

    /**
     * @return the disallowNullified
     */
    public final boolean isDisallowNullified() {
        return disallowNullified;
    }

    /**
     * Helper that adds nullified checks to searches.
     */
    private static class NullifiedHelper implements SearchableUtils.AfterIterationHelper {
        private static final String NULLIFIED_STATUS_PARAM = "nullifiedStatusParam";
        private final boolean disallowNullified;

        public NullifiedHelper(boolean disallowNullified) {
            this.disallowNullified = disallowNullified;
        }

        /**
         * {@inheritDoc}
         */
        public void afterIteration(Object obj, boolean isCountOnly,
                                   StringBuffer whereClause, Map<String, Object> params) {
            String operator = determineOperator(whereClause);
            if (disallowNullified && obj instanceof CuratableEntity) {
                whereClause.append(String.format("%s %s.statusCode != :%s", operator, SearchableUtils.ROOT_OBJ_ALIAS,
                        NULLIFIED_STATUS_PARAM));
                params.put(NULLIFIED_STATUS_PARAM, EntityStatus.NULLIFIED);
            } else if (disallowNullified && obj instanceof CuratableRole) {
                whereClause.append(String.format("%s %s.status != :%s", operator,
                                                 SearchableUtils.ROOT_OBJ_ALIAS, NULLIFIED_STATUS_PARAM));
                params.put(NULLIFIED_STATUS_PARAM, RoleStatus.NULLIFIED);
            }
        }

        private static String determineOperator(final StringBuffer whereClause) {
            String operator = "";
            if (StringUtils.isBlank(whereClause.toString())) {
                operator = SearchableUtils.WHERE;
            } else if (!SearchableUtils.WHERE.equals(whereClause.toString())) {
                //handle case when no criterion are provided.
                operator = SearchableUtils.AND;
            }
            return operator;
        }
    }

}