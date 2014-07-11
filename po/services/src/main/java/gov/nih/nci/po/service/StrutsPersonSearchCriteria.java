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

import com.fiveamsolutions.nci.commons.search.AbstractSearchCriteria;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.service.external.CtepPersonImporter;
import gov.nih.nci.po.util.PoHibernateUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Criteria class to search for people.
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
public class StrutsPersonSearchCriteria extends AbstractSearchCriteria<Person> implements
        Serializable {

    private static final long serialVersionUID = 1L;
    private String firstName, lastName;
    private String email;
    private String org;
    private String ctepId;
    private String role;
    private String status;
    private String id;

    /**
     * @return person's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName person's first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return person's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName person's last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return person's or played roles' email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email person's or played roles' email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return name of scoper org.
     */
    public String getOrg() {
        return org;
    }

    /**
     * @param org name of scoper org.
     */
    public void setOrg(String org) {
        this.org = org;
    }

    /**
     * @return CTEP identifier.
     */
    public String getCtepId() {
        return ctepId;
    }

    /**
     * @param ctepId CTEP identifier.
     */
    public void setCtepId(String ctepId) {
        this.ctepId = ctepId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasOneCriterionSpecified() {
        return StringUtils.isNotBlank(firstName)
                || StringUtils.isNotBlank(lastName)
                || StringUtils.isNotBlank(email) || StringUtils.isNotBlank(org)
                || StringUtils.isNotBlank(ctepId) || StringUtils.isNotBlank(id)
                || StringUtils.isNotBlank(role)
                || StringUtils.isNotBlank(status);
    }

    /**
     * {@inheritDoc}
     */
    protected Class<Person> getRootObjectType() {
        return Person.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRootAlias() {
        return "p";
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "PMD.NPathComplexity", "PMD.ExcessiveMethodLength",
    "PMD.ConsecutiveLiteralAppends", "PMD.UseLocaleWithCaseConversions" })
    public Query getQuery(String orderByProperty, boolean isCountOnly) {
        Map<String, String> params = new HashMap<String, String>();
        StringBuffer hql = new StringBuffer("SELECT ");
        if (isCountOnly) {
            hql.append("COUNT(DISTINCT p)");
        } else {
            hql.append("DISTINCT p");
        }
        hql.append(" FROM ").append(Person.class.getName()).append(" p");
        if (StringUtils.isNotBlank(email) || StringUtils.isNotBlank(org)
                || StringUtils.isNotBlank(ctepId) || StringUtils.isNotBlank(role)) {
            hql.append(" LEFT OUTER JOIN p.organizationalContacts as oc")
                .append(" LEFT OUTER JOIN p.clinicalResearchStaff as crs")
                .append(" LEFT OUTER JOIN p.healthCareProviders as hcp")
                .append(" LEFT OUTER JOIN p.identifiedPersons as ip");
            if (StringUtils.isNotBlank(org)) {
                hql.append(" LEFT OUTER JOIN oc.scoper as ocscoper")
                .append(" LEFT OUTER JOIN crs.scoper as crsscoper")
                .append(" LEFT OUTER JOIN hcp.scoper as hcpscoper")
                .append(" LEFT OUTER JOIN ip.scoper as ipscoper");
            }
        }
        if (StringUtils.isNotBlank(email)) {
            hql.append(" LEFT OUTER JOIN p.email as pemail")
                .append(" LEFT OUTER JOIN oc.email as ocemail")
                .append(" LEFT OUTER JOIN crs.email as crsemail")
                .append(" LEFT OUTER JOIN hcp.email as hcpemail");
        }

        hql.append(" WHERE p.statusCode <> 'NULLIFIED'");
        if (StringUtils.isNotBlank(status)) {
            hql.append(" AND p.statusCode = '" + StringEscapeUtils.escapeSql(status.toUpperCase()) + "' ");
        }
        if (StringUtils.isNotBlank(id)) {
            hql.append(" AND p.id = " + Long.parseLong(id) + " ");
        }   
        if ("Healthcare Provider".equals(role)) {
            hql.append(" AND p.healthCareProviders.size > 0 ");
        }
        if ("Clinical Research Staff".equals(role)) {
            hql.append(" AND p.clinicalResearchStaff.size > 0 ");
        }
        if ("Organizational Contact".equals(role)) {
            hql.append(" AND p.organizationalContacts.size > 0 ");
        }        
        if (StringUtils.isNotBlank(firstName)) {
            hql.append(" AND lower(p.firstName) LIKE :firstName");
            params.put("firstName", "%" + firstName.toLowerCase() + "%");
        }
        if (StringUtils.isNotBlank(lastName)) {
            hql.append(" AND lower(p.lastName) LIKE :lastName");
            params.put("lastName", "%" + lastName.toLowerCase() + "%");
        }
        if (StringUtils.isNotBlank(email)) {
            hql.append(" AND (lower(pemail.value) LIKE :pemail")
                    .append(" OR lower(ocemail.value) LIKE :ocemail")
                    .append(" OR lower(crsemail.value) LIKE :crsemail")
                    .append(" OR lower(hcpemail.value) LIKE :hcpemail")
                    .append(')');
            final String em = "%" + email.toLowerCase() + "%";
            params.put("pemail", em);
            params.put("ocemail", em);
            params.put("crsemail", em);
            params.put("hcpemail", em);
        }
        if (StringUtils.isNotBlank(org)) {
            hql.append(" AND (lower(ocscoper.name) LIKE :ocname")
                    .append(" OR lower(crsscoper.name) LIKE :crsname")
                    .append(" OR lower(hcpscoper.name) LIKE :hcpname")
                    .append(" OR lower(ipscoper.name) LIKE :ipname")
                    .append(')');
            final String on = "%" + org.toLowerCase() + "%";
            params.put("ocname", on);
            params.put("crsname", on);
            params.put("hcpname", on);
            params.put("ipname", on);
        }
        if (StringUtils.isNotBlank(ctepId)) {
            hql.append(" AND ltrim(ip.assignedIdentifier.root) = '").append(CtepPersonImporter.CTEP_PERSON_ROOT)
                    .append("' AND lower(ip.assignedIdentifier.extension) like :ctepId");
            params.put("ctepId", ctepId.toLowerCase());
        }
        if (!isCountOnly) {
            hql.append(orderByProperty);
        }
        Query query = PoHibernateUtil.getCurrentSession().createQuery(hql.toString());
        for (Map.Entry<String, String> e : params.entrySet()) {
            query.setString(e.getKey(), e.getValue());
        }
        return query;
    }

    /**
     * {@inheritDoc}
     */
    public Query getQuery(String orderByProperty, String leftJoinClause, boolean isCountOnly) {
        if (leftJoinClause != null && StringUtils.isNotBlank(leftJoinClause)) {
            throw new IllegalArgumentException("The use of the left join clause is currently not supported."
                    + " Please ref jira issues PO-1115, PO-1116, PO-1118");
        }

        return getQuery(orderByProperty, isCountOnly);
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

}
