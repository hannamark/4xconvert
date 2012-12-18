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
package gov.nih.nci.po.service; // NOPMD

import static gov.nih.nci.po.service.OrganizationSearchSortEnum.CR;
import static gov.nih.nci.po.service.OrganizationSearchSortEnum.FAMILY;
import static gov.nih.nci.po.service.OrganizationSearchSortEnum.HCF_CTEP_ID;
import static gov.nih.nci.po.service.OrganizationSearchSortEnum.ID;
import static gov.nih.nci.po.service.OrganizationSearchSortEnum.NAME;
import static gov.nih.nci.po.service.OrganizationSearchSortEnum.PENDING_HCF;
import static gov.nih.nci.po.service.OrganizationSearchSortEnum.PENDING_RO;
import static gov.nih.nci.po.service.OrganizationSearchSortEnum.RO_CTEP_ID;
import static gov.nih.nci.po.service.OrganizationSearchSortEnum.STATUS;
import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.OversightCommittee;
import gov.nih.nci.po.data.bo.Patient;
import gov.nih.nci.po.data.bo.PlayedRole;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.bo.ScopedRole;
import gov.nih.nci.po.service.external.CtepOrganizationImporter;
import gov.nih.nci.po.util.MergeOrganizationHelper;
import gov.nih.nci.po.util.MergeOrganizationHelperImpl;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.UsOrCanadaPhoneHelper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;

/**
 *
 * @author gax
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@SuppressWarnings({ "PMD.ExcessiveClassLength", "PMD.CyclomaticComplexity",
        "PMD.TooManyMethods" })
public class OrganizationServiceBean extends AbstractCuratableEntityServiceBean<Organization> implements
        OrganizationServiceLocal {
    private static final String ORDER_BY = " ORDER BY ";
    private static final String UNCHECKED = "unchecked";
    private MergeOrganizationHelper mergeOrganizationHelper;

    /**
     * Constructs an {@link OrganizationServiceBean} with the default MergeOrganizationHelper.
     */
    public OrganizationServiceBean() {
        this.mergeOrganizationHelper = new MergeOrganizationHelperImpl();
    }

    /**
     * Constructs an {@link OrganizationServiceBean} with the provided MergeOrganizationHelper instance.
     * @param mergeOrganizationHelper Inject the MergeOrganizationHelper to use
     */
    public OrganizationServiceBean(MergeOrganizationHelper mergeOrganizationHelper) {
        this.mergeOrganizationHelper = mergeOrganizationHelper;
    }

    /**
     * @return the mergeOrganizationHelper
     */
    public MergeOrganizationHelper getMergeOrganizationHelper() {
        return mergeOrganizationHelper;
    }

    /**
     * @param mergeOrganizationHelper the mergeOrganizationHelper to set
     */
    public void setMergeOrganizationHelper(MergeOrganizationHelper mergeOrganizationHelper) {
        this.mergeOrganizationHelper = mergeOrganizationHelper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public long create(Organization org) throws EntityValidationException, JMSException {
        org.setStatusCode(EntityStatus.PENDING);
        return super.create(org);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Set<Correlation> getAssociatedRoles(Organization o, Session s) {
        Set<Correlation> l = new HashSet<Correlation>();
        // played roles
        l.addAll(getAssociatedPlayedRoles(o, s));
        // scoped roles
        l.addAll(getAssociatedScopedRoles(o, s));
        return l;
    }

    /**
     * {@inheritDoc}
     */
    public Set<Correlation> getAssociatedPlayedRoles(Organization o) {
        return getAssociatedPlayedRoles(o, PoHibernateUtil.getCurrentSession());
    }

    private Set<Correlation> getAssociatedPlayedRoles(Organization o, Session s) {
        Set<Correlation> l = new HashSet<Correlation>();
        if (o == null) {
            return l;
        }
        l.addAll(getAssociatedRoles(o.getId(), HealthCareFacility.class, PLAYER_ID, s));
        l.addAll(getAssociatedRoles(o.getId(), IdentifiedOrganization.class, PLAYER_ID, s));
        l.addAll(getAssociatedRoles(o.getId(), OversightCommittee.class, PLAYER_ID, s));
        l.addAll(getAssociatedRoles(o.getId(), ResearchOrganization.class, PLAYER_ID, s));
        return l;
    }

    /**
     * {@inheritDoc}
     */
    public Set<Correlation> getAssociatedScopedRoles(Organization o) {
        return getAssociatedScopedRoles(o, PoHibernateUtil.getCurrentSession());
    }

    private Set<Correlation> getAssociatedScopedRoles(Organization o, Session s) {
        Set<Correlation> l = new HashSet<Correlation>();
        if (o == null) {
            return l;
        }
        l.addAll(getAssociatedRoles(o.getId(), HealthCareProvider.class, SCOPER_ID, s));
        l.addAll(getAssociatedRoles(o.getId(), IdentifiedOrganization.class, SCOPER_ID, s));
        l.addAll(getAssociatedRoles(o.getId(), ClinicalResearchStaff.class, SCOPER_ID, s));
        l.addAll(getAssociatedRoles(o.getId(), IdentifiedPerson.class, SCOPER_ID, s));
        l.addAll(getAssociatedRoles(o.getId(), OrganizationalContact.class, SCOPER_ID, s));
        l.addAll(getAssociatedRoles(o.getId(), Patient.class, SCOPER_ID, s));
        return l;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void cascadeStatusChangeNullified(Organization org, Session s) throws JMSException {
        if (org.getDuplicateOf() == null) {
            super.cascadeStatusChangeNullified(org, s);
        } else {
            mergeCorrelations(org, s);
        }
    }

    private void mergeCorrelations(Organization org, Session s) throws JMSException {
        Organization dup = org.getDuplicateOf();
        Set<Correlation> associatedRoles = getAssociatedRoles(org, s);
        List<Correlation> changes = new ArrayList<Correlation>();
        for (Correlation correlation : associatedRoles) {
            changes.addAll(mergePlayedRoleCorrelation(org, dup, correlation));
            changes.addAll(mergeScopedRoleCorrelation(org, dup, correlation));
        }
        curateMergedCorrelations(changes);
    }

    private List<Correlation> mergeScopedRoleCorrelation(Organization org, Organization dup, Correlation correlation) {
        List<Correlation> changes = new ArrayList<Correlation>();
        if (correlation instanceof ScopedRole && ((ScopedRole) correlation).getScoper().getId().equals(org.getId())) {
            ScopedRole sr = (ScopedRole) correlation;
            sr.setScoper(dup);
            activateRoleStatusByDupStatus(dup, correlation);
            if (isChangeConflicting(correlation)) {
                changes.addAll(mergeOrganizationHelper.handleConflictingScopedRoleCorrelation(org,
                        correlation));
            } else {
                changes.add(correlation);
            }
        }
        return changes;
    }

    private List<Correlation> mergePlayedRoleCorrelation(Organization org, Organization dup, Correlation correlation) {
        List<Correlation> changes = new ArrayList<Correlation>();
        if (correlation instanceof PlayedRole && ((PlayedRole) correlation).getPlayer() instanceof Organization
                && ((PlayedRole) correlation).getPlayer().getId().equals(org.getId())) {
            PlayedRole<Organization> pr = (PlayedRole<Organization>) correlation;
            pr.setPlayer(dup);
            activateRoleStatusByDupStatus(dup, correlation);
            if (isChangeConflicting(correlation)) {
                changes.addAll(mergeOrganizationHelper.handleConflictingPlayedRoleCorrelation(org,
                        correlation));
            } else {
                changes.add(correlation);
            }
        }
        return changes;
    }

    /**
     * @param correlation
     * @return
     */
    private boolean isChangeConflicting(Correlation correlation) {
        GenericStructrualRoleServiceLocal serviceForRole = getServiceForRole(correlation.getClass());
        // validate()'s behavior ensures that all keys are unique
        Map<String, String[]> correlationErrorMsgs = serviceForRole.validate(correlation);

        //Checks if any of the validation errors are due to bad phone data. (PO-3509).
        if (MapUtils.isNotEmpty(correlationErrorMsgs)) {
            for (String key : correlationErrorMsgs.keySet()) {
                for (String error : correlationErrorMsgs.get(key)) {
                    if (UsOrCanadaPhoneHelper.getPhoneFormatErrorMessage().equals(error)) {
                        throw new CurateEntityValidationException(correlationErrorMsgs);
                    }
                }
            }
        }
        return MapUtils.isNotEmpty(correlationErrorMsgs);
    }

    private void curateMergedCorrelations(List<Correlation> changes) throws JMSException {
        for (Correlation correlation : changes) {
            GenericStructrualRoleServiceLocal serviceForRole = getServiceForRole(correlation.getClass());
            serviceForRole.curate(correlation);
        }
    }

    private void activateRoleStatusByDupStatus(Organization dup, Correlation correlation) {
        if (dup.getStatusCode() == EntityStatus.ACTIVE && correlation.getStatus() == RoleStatus.PENDING
                && isCtepRole(correlation)) {
            correlation.setStatus(RoleStatus.ACTIVE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings(UNCHECKED)
    protected void activateCtepRoles(Organization e) throws JMSException {
        Session s = PoHibernateUtil.getCurrentSession();
        for (Correlation x : getAssociatedRoles(e, s)) {
            if (x.getStatus() == RoleStatus.PENDING && isCtepRole(x)) {
                x.setStatus(RoleStatus.ACTIVE);
                GenericStructrualRoleServiceLocal service = getServiceForRole(x.getClass());
                service.curate(x);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OrganizationSearchDTO> search(
            OrganizationSearchCriteria criteria,
            PageSortParams<OrganizationSearchDTO> pageSortParams) {
        Session s = PoHibernateUtil.getCurrentSession();
        List<OrganizationSearchDTO> results = new ArrayList<OrganizationSearchDTO>();        
        StringBuilder sql = new StringBuilder(s.getNamedQuery(
                "gov.nih.nci.po.service.OrganizationServiceBean.search")
                .getQueryString());
        appendWhere(sql, criteria);
        appendOrderBy(sql, pageSortParams);
        appendPagination(sql, pageSortParams);
        SQLQuery query = s.createSQLQuery(sql.toString());
        for (Object[] row : (List<Object[]>) query.list()) {
            results.add(convert(row));
        }
        return results;
    }

    // CHECKSTYLE:OFF
    private OrganizationSearchDTO convert(Object[] row) {
        OrganizationSearchDTO dto = new OrganizationSearchDTO();
        dto.setId(row[0].toString());
        dto.setName((String) row[1]);
        dto.setFamilyName((String) row[2]);
        dto.setRoCtepId((String) row[3]);
        dto.setHcfCtepId((String) row[4]);
        dto.setChangeRequests(((BigInteger) row[5]).intValue());
        dto.setPendingROs(((BigInteger) row[6]).intValue());
        dto.setPendingHCFs(((BigInteger) row[7]).intValue());
        dto.setStatusCode((String) row[8]);
        dto.setStatusDate((Date) row[9]);
        dto.setTotalROs(((BigInteger) row[10]).intValue());
        dto.setTotalHCFs(((BigInteger) row[11]).intValue());
        dto.setTotalIdOrgs(((BigInteger) row[12]).intValue());
        dto.setTotalOversightCommitees(((BigInteger) row[13]).intValue());
        dto.setTotalOrgContacts(((BigInteger) row[14]).intValue());
        dto.setAddress1((String) row[15]);
        dto.setAddress2((String) row[16]);
        dto.setCity((String) row[17]);
        dto.setState((String) row[18]);
        dto.setCountry((String) row[19]);
        dto.setZipCode((String) row[20]);
        dto.setComments((String) row[21]);
        dto.setEmailAddresses((String) row[22]);
        dto.setPhones((String) row[23]);
        dto.setDuplicateOf(((BigInteger) row[24]));
        return dto;
    }
    // CHECKSTYLE:ON

    private void appendPagination(StringBuilder sql,
            PageSortParams<OrganizationSearchDTO> pageSortParams) {
        sql.append(" LIMIT " + pageSortParams.getPageSize());
        if (pageSortParams.getIndex() > 0) {
            sql.append(" OFFSET " + pageSortParams.getIndex());
        }
    }

    @SuppressWarnings("deprecation")
    private void appendOrderBy(StringBuilder sql,
            PageSortParams<OrganizationSearchDTO> params) {
        if (params.getSortCriterion() != null) {
            throw new RuntimeException(//NOPMD
                    "SortCriterion is not supported for SQL queries."); //NOPMD
        }
        if (CollectionUtils.isNotEmpty(params.getDynamicSortCriteria())) {
            for (String sort : params.getDynamicSortCriteria()) {
                OrganizationSearchSortEnum sortEnum = OrganizationSearchSortEnum
                        .valueOf(sort);
                appendOrderBy(sql, sortEnum);
            }
        }
        if (params.isDesc()) {
            sql.append(" DESC");
        }
    }

    @SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
    private void appendOrderBy(StringBuilder sql,
            OrganizationSearchSortEnum sortEnum) {
        if (sql.toString().contains(ORDER_BY)) {
            sql.append(", ");
        } else {
            sql.append(ORDER_BY);
        }        
        if (ID == sortEnum) {
            sql.append("o.id");
        }
        if (NAME == sortEnum) {
            sql.append("o.name");
        }
        if (FAMILY == sortEnum) {
            sql.append("familyName");
        }
        if (RO_CTEP_ID == sortEnum) {
            sql.append("roCtepId");
        }
        if (HCF_CTEP_ID == sortEnum) {
            sql.append("hcfCtepId");
        }
        if (CR == sortEnum) {
            sql.append("changeRequests");
        }
        if (PENDING_RO == sortEnum) {
            sql.append("pendingROs");
        }
        if (PENDING_HCF == sortEnum) {
            sql.append("pendingHCFs");
        }        
        if (STATUS == sortEnum) {
            sql.append("o.status");
        }
    }

    private void appendWhere(StringBuilder sql,
            OrganizationSearchCriteria criteria) {
        sql.append(" WHERE o.status <> 'NULLIFIED' ");
        if (!criteria.isEmpty()) {
            appendCtepIdClause(sql, criteria);
            appendOrgIdClause(sql, criteria);
            appendStatusClause(sql, criteria);
            appendOrgNameClause(sql, criteria);
            appendFamilyClause(sql, criteria);
            appendCrClause(sql, criteria);
            appendPendingHcfClause(sql, criteria);
            appendPendingRoClause(sql, criteria);
            appendCountryClause(sql, criteria);
            appendAddr1Clause(sql, criteria);
            appendAddr2Clause(sql, criteria);
            appendCityClause(sql, criteria);
            appendStateClause(sql, criteria);
            appendZipCodeClause(sql, criteria);
        }
    }
    
    /**
     * @param sql
     * @param criteria
     */
    private void appendZipCodeClause(StringBuilder sql,
            OrganizationSearchCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getPostalCode())) {
            sql.append(String.format(
                    " AND lower(a.postalcode) like '%s' ",
                    "%"
                            + StringEscapeUtils.escapeSql(criteria
                                    .getPostalCode().toLowerCase()) + "%"));
        }
    }
    
    /**
     * @param sql
     * @param criteria
     */
    private void appendStateClause(StringBuilder sql,
            OrganizationSearchCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getStateOrProvince())) {
            sql.append(String.format(
                    " AND lower(a.stateorprovince) like '%s' ",
                    "%"
                            + StringEscapeUtils.escapeSql(criteria
                                    .getStateOrProvince().toLowerCase())
                            + "%"));
        }
    }
    
    /**
     * @param sql
     * @param criteria
     */
    private void appendCityClause(StringBuilder sql,
            OrganizationSearchCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getCityOrMunicipality())) {
            sql.append(String.format(
                    " AND lower(a.cityormunicipality) like '%s' ",
                    "%"
                            + StringEscapeUtils.escapeSql(criteria
                                    .getCityOrMunicipality().toLowerCase())
                            + "%"));
        }
    }
    
    /**
     * @param sql
     * @param criteria
     */
    private void appendAddr2Clause(StringBuilder sql,
            OrganizationSearchCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getDeliveryAddressLine())) {
            sql.append(String.format(
                    " AND lower(a.deliveryaddressline) like '%s' ",
                    "%"
                            + StringEscapeUtils.escapeSql(criteria.getDeliveryAddressLine()
                                    .toLowerCase()) + "%"));
        }
    }

    /**
     * @param sql
     * @param criteria
     */
    private void appendAddr1Clause(StringBuilder sql,
            OrganizationSearchCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getStreetAddressLine())) {
            sql.append(String.format(
                    " AND lower(a.streetaddressline) like '%s' ",
                    "%"
                            + StringEscapeUtils.escapeSql(criteria.getStreetAddressLine()
                                    .toLowerCase()) + "%"));
        }
    }

    /**
     * @param sql
     * @param criteria
     */
    private void appendCountryClause(StringBuilder sql,
            OrganizationSearchCriteria criteria) {
        if (criteria.getCountryId() != null) {
            sql.append(String.format(" AND c.id=%s ", criteria.getCountryId()));
        }
    }

    /**
     * @param sql
     * @param criteria
     */
    private void appendPendingRoClause(StringBuilder sql,
            OrganizationSearchCriteria criteria) {
        if (Boolean.TRUE.equals(criteria.getHasPendingRoRoles())) {
            sql.append(" AND (select count(id) from researchorganization ro where"
                    + " ro.player_id=o.id and ro.status='PENDING') > 0");
        }
    }

    /**
     * @param sql
     * @param criteria
     */
    private void appendPendingHcfClause(StringBuilder sql,
            OrganizationSearchCriteria criteria) {
        if (Boolean.TRUE.equals(criteria.getHasPendingHcfRoles())) {
            sql.append(" AND (select count(id) from healthcarefacility ro "
                    + "where ro.player_id=o.id and ro.status='PENDING') > 0");
        }
    }

    /**
     * @param sql
     * @param criteria
     */
    private void appendCrClause(StringBuilder sql,
            OrganizationSearchCriteria criteria) {
        if (Boolean.TRUE.equals(criteria.getHasChangeRequests())) {
            sql.append(" AND (select count(id) from organizationcr ocr where ocr.target=o.id "
                    + "and ocr.processed=false) > 0");
        }
    }

    /**
     * @param sql
     * @param criteria
     */
    private void appendFamilyClause(StringBuilder sql,
            OrganizationSearchCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getFamilyName())) {
            sql.append(String
                    .format(" AND exists (select fam.name from family fam inner join "
                            + "familyorganizationrelationship rel on "
                            + "rel.family_id=fam.id and rel.organization_id=o.id where rel.enddate is null and "
                            + "fam.statuscode='ACTIVE' and lower(fam.name) like '%s') ",
                            "%"
                                    + StringEscapeUtils.escapeSql(criteria
                                            .getFamilyName().toLowerCase())
                                    + "%"));
        }
    }

    /**
     * @param sql
     * @param criteria
     */
    private void appendOrgNameClause(StringBuilder sql,
            OrganizationSearchCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getName())) {
            sql.append(String.format(
                    " AND lower(o.name) like '%s' ",
                    "%"
                            + StringEscapeUtils.escapeSql(criteria.getName()
                                    .toLowerCase()) + "%"));
        }
    }

    /**
     * @param sql
     * @param criteria
     */
    private void appendStatusClause(StringBuilder sql,
            OrganizationSearchCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getStatusCode())) {
            sql.append(String.format(" AND o.status='%s' ",
                    StringEscapeUtils.escapeSql(criteria.getStatusCode())));
        }
    }

    /**
     * @param sql
     * @param criteria
     */
    private void appendOrgIdClause(StringBuilder sql,
            OrganizationSearchCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getId())) {
            sql.append(String.format(" AND o.id=%s ",
                    StringEscapeUtils.escapeSql(criteria.getId())));
        }
    }

    /**
     * @param sql
     * @param criteria
     */
    private void appendCtepIdClause(StringBuilder sql,
            OrganizationSearchCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getCtepID())) {
            sql.append(String
                    .format(" AND (exists (select ro_oi.extension from ro_otheridentifier ro_oi inner join "
                            + "researchorganization ro on ro_oi.ro_id=ro.id and ro.player_id=o.id and ro.status <> "
                            + "'NULLIFIED' and ro_oi.root='%s' and lower(ro_oi.extension) like '%s') or exists "
                            + "(select hcf_oi.extension from hcf_otheridentifier hcf_oi inner join healthcarefacility "
                            + "hcf on hcf_oi.hcf_id=hcf.id and hcf.player_id=o.id and hcf.status <> 'NULLIFIED' and"
                            + " hcf_oi.root='%s' and lower(hcf_oi.extension) like '%s')) ",
                            CtepOrganizationImporter.CTEP_ORG_ROOT, "%"
                                    + StringEscapeUtils.escapeSql(criteria.getCtepID()).toLowerCase() + "%",
                            CtepOrganizationImporter.CTEP_ORG_ROOT, "%"
                                    + StringEscapeUtils.escapeSql(criteria.getCtepID()).toLowerCase() + "%"));
        }
    }

    @Override
    public long count(OrganizationSearchCriteria criteria) {
        Session s = PoHibernateUtil.getCurrentSession();
        StringBuilder sql = new StringBuilder(s.getNamedQuery(
                "gov.nih.nci.po.service.OrganizationServiceBean.search")
                .getQueryString());
        appendWhere(sql, criteria);
        SQLQuery query = s.createSQLQuery("select count(*) from ("
                + sql.toString() + ") as cnt");
        return ((Number) query.uniqueResult()).longValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OrganizationSearchDTO> getInboxOrgs(
            PageSortParams<OrganizationSearchDTO> pageSortParams) {
        Session s = PoHibernateUtil.getCurrentSession();
        List<OrganizationSearchDTO> results = new ArrayList<OrganizationSearchDTO>();
        StringBuilder sql = new StringBuilder(s.getNamedQuery(
                "gov.nih.nci.po.service.OrganizationServiceBean.getInboxOrgs")
                .getQueryString());
        appendOrderBy(sql, pageSortParams);
        appendPagination(sql, pageSortParams);
        SQLQuery query = s.createSQLQuery(sql.toString());
        for (Object[] row : (List<Object[]>) query.list()) {
            results.add(convert(row));
        }
        return results;
    }

    @Override
    public long countInboxOrgs() {
        Session s = PoHibernateUtil.getCurrentSession();
        StringBuilder sql = new StringBuilder(s.getNamedQuery(
                "gov.nih.nci.po.service.OrganizationServiceBean.getInboxOrgs")
                .getQueryString());
        SQLQuery query = s.createSQLQuery("select count(*) from ("
                + sql.toString() + ") as cnt");
        return ((BigInteger) query.uniqueResult()).longValue();
    }

    @Override
    public void removeChangeRequest(OrganizationCR cr) {
        Session s = PoHibernateUtil.getCurrentSession();
        OrganizationCR crToRemove = (OrganizationCR) s.get(
                OrganizationCR.class, cr.getId());
        crToRemove.setProcessed(true);
        s.update(crToRemove);
    }

}
