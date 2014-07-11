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
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.service.external.CtepOrganizationImporter;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.services.organization.OrganizationSearchCriteriaDTO;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Criteria class to search for organizations.
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
public class ExtendedOrganizationSearchCriteria extends
        AbstractSearchCriteria<Organization> implements Serializable {

    private static final long serialVersionUID = 1L;
    private final OrganizationSearchCriteriaDTO dto;

    /**
     * @param dto
     *            OrganizationSearchCriteriaDTO
     */
    public ExtendedOrganizationSearchCriteria(OrganizationSearchCriteriaDTO dto) {
        this.dto = dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasOneCriterionSpecified() {
        return StringUtils.isNotBlank(dto.getCity())
                || StringUtils.isNotBlank(dto.getCountry())
                || StringUtils.isNotBlank(dto.getCtepId())
                || StringUtils.isNotBlank(dto.getFamilyName())
                || StringUtils.isNotBlank(dto.getFunctionalRole())
                || StringUtils.isNotBlank(dto.getIdentifier())
                || StringUtils.isNotBlank(dto.getName())
                || StringUtils.isNotBlank(dto.getState())
                || StringUtils.isNotBlank(dto.getStatus())
                || StringUtils.isNotBlank(dto.getZip());
    }

    /**
     * {@inheritDoc}
     */
    protected Class<Organization> getRootObjectType() {
        return Organization.class;
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
        hql.append(" FROM ").append(Organization.class.getName()).append(" p");
        hql.append(" LEFT OUTER JOIN p.familyOrganizationRelationships as fam")
                .append(" LEFT OUTER JOIN p.healthCareFacilities as hcf")
                .append(" LEFT OUTER JOIN p.identifiedOrganizations as io")
                .append(" LEFT OUTER JOIN p.researchOrganizations as ro");

        hql.append(" WHERE p.statusCode <> 'NULLIFIED'");

        if (StringUtils.isNotBlank(dto.getStatus())) {
            hql.append(" AND p.statusCode = '"
                    + StringEscapeUtils
                            .escapeSql(dto.getStatus().toUpperCase()) + "' ");
        }
        if (StringUtils.isNotBlank(dto.getIdentifier())) {
            hql.append(" AND p.id = " + Long.parseLong(dto.getIdentifier())
                    + " ");
        }
        if ("Research Organization".equals(dto.getFunctionalRole())) {
            hql.append(" AND p.researchOrganizations.size > 0 ");
        }
        if ("Healthcare Facility".equals(dto.getFunctionalRole())) {
            hql.append(" AND p.healthCareFacilities.size > 0 ");
        }
        if (StringUtils.isNotBlank(dto.getName())) {
            hql.append(" AND lower(p.name) LIKE :name");
            params.put("name", "%" + dto.getName().toLowerCase() + "%");
        }
        if (StringUtils.isNotBlank(dto.getCtepId())) {
            hql.append(" AND ltrim(io.assignedIdentifier.root) = '")
                    .append(CtepOrganizationImporter.CTEP_ORG_ROOT)
                    .append("' AND lower(io.assignedIdentifier.extension) like :ctepId");
            params.put("ctepId", "%" + dto.getCtepId().toLowerCase() + "%");
        }

        if (StringUtils.isNotBlank(dto.getCity())) {
            hql.append(" AND lower(p.postalAddress.cityOrMunicipality) LIKE :city");
            params.put("city", "%" + dto.getCity().toLowerCase() + "%");
        }

        if (StringUtils.isNotBlank(dto.getCountry())) {
            hql.append(" AND lower(p.postalAddress.country.alpha3) LIKE :country");
            params.put("country", dto.getCountry().toLowerCase());
        }

        if (StringUtils.isNotBlank(dto.getState())) {
            hql.append(" AND lower(p.postalAddress.stateOrProvince) LIKE :state");
            params.put("state", "%" + dto.getState().toLowerCase() + "%");
        }

        if (StringUtils.isNotBlank(dto.getZip())) {
            hql.append(" AND lower(p.postalAddress.postalCode) LIKE :zip");
            params.put("zip", "%" + dto.getZip().toLowerCase() + "%");
        }
        if (StringUtils.isNotBlank(dto.getFamilyName())) {
            hql.append(" AND lower(fam.family.name) LIKE :familyName");
            params.put("familyName", "%" + dto.getFamilyName().toLowerCase()
                    + "%");
        }

        if (!isCountOnly) {
            hql.append(orderByProperty);
        }
        Query query = PoHibernateUtil.getCurrentSession().createQuery(
                hql.toString());
        for (Map.Entry<String, String> e : params.entrySet()) {
            query.setString(e.getKey(), e.getValue());
        }
        return query;
    }

    /**
     * {@inheritDoc}
     */
    public Query getQuery(String orderByProperty, String leftJoinClause,
            boolean isCountOnly) {
        if (leftJoinClause != null && StringUtils.isNotBlank(leftJoinClause)) {
            throw new IllegalArgumentException(
                    "The use of the left join clause is currently not supported.");
        }

        return getQuery(orderByProperty, isCountOnly);
    }

}
