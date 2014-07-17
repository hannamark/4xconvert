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

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.Patient;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.service.PersonSearchDTO.Affiliation;
import gov.nih.nci.po.service.PersonSearchDTO.Affiliation.RoleGroup;
import gov.nih.nci.po.util.PoConstants;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoServiceUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static gov.nih.nci.po.service.PersonSearchSortEnum.CITY;
import static gov.nih.nci.po.service.PersonSearchSortEnum.CTEP_ID;
import static gov.nih.nci.po.service.PersonSearchSortEnum.EMAIL;
import static gov.nih.nci.po.service.PersonSearchSortEnum.PERSON_FIRSTNAME;
import static gov.nih.nci.po.service.PersonSearchSortEnum.PERSON_ID;
import static gov.nih.nci.po.service.PersonSearchSortEnum.PERSON_LASTNAME;
import static gov.nih.nci.po.service.PersonSearchSortEnum.STATE;
import static gov.nih.nci.po.service.PersonSearchSortEnum.STATUS;

/**
 * 
 * @author lpower
 * @author Rohit Gupta
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.TooManyMethods", "PMD.ExcessiveClassLength" })
public class PersonServiceBean extends
        AbstractCuratableEntityServiceBean<Person> implements
        PersonServiceLocal {

    private static final String ORDER_BY = " ORDER BY ";
    private static final Logger LOG = Logger.getLogger(PersonServiceBean.class);
    
    @EJB
    private IdentifiedPersonServiceLocal idenPerServ;

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public long create(Person p) throws EntityValidationException, JMSException {
        if (p.getStatusCode() == null) {
            p.setStatusCode(EntityStatus.PENDING);
        }
        String middleName = p.getMiddleName();
        if (StringUtils.isNotEmpty(middleName) 
                && (middleName.contains("(") || middleName.contains(")"))) {
            middleName = middleName.replace("(", "").replace(")", "");
        }
        p.setMiddleName(middleName);
        return super.create(p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Set<Correlation> getAssociatedRoles(Person p, Session s) {
        Set<Correlation> l = new HashSet<Correlation>();
        l.addAll(getAssociatedRoles(p.getId(), ClinicalResearchStaff.class,
                PLAYER_ID, s));
        l.addAll(getAssociatedRoles(p.getId(), Patient.class, PLAYER_ID, s));
        l.addAll(getAssociatedRoles(p.getId(), HealthCareProvider.class,
                PLAYER_ID, s));
        l.addAll(getAssociatedRoles(p.getId(), IdentifiedPerson.class,
                PLAYER_ID, s));
        l.addAll(getAssociatedRoles(p.getId(), OrganizationalContact.class,
                PLAYER_ID, s));
        return l;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void activateCtepRoles(Person e) {
        // NOOP - currently we do not do anything with
        // ctep associated roles for person.
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PersonSearchDTO> search(PersonSearchCriteria criteria,
            PageSortParams<PersonSearchDTO> pageSortParams) {
        Session s = PoHibernateUtil.getCurrentSession();
        List<PersonSearchDTO> results = new ArrayList<PersonSearchDTO>();
        StringBuilder sql = new StringBuilder(s.getNamedQuery(
                "gov.nih.nci.po.service.PersonServiceBean.search")
                .getQueryString());
        appendWhere(sql, criteria);
        appendOrderBy(sql, pageSortParams);
        appendPagination(sql, pageSortParams);
        SQLQuery query = s.createSQLQuery(sql.toString());
        for (Object[] row : (List<Object[]>) query.list()) {
            results.add(convert(row));
        }
        loadAffiliationInfo(results);
        return results;
    }

    @SuppressWarnings("unchecked")
    private void loadAffiliationInfo(List<PersonSearchDTO> results) {
        if (CollectionUtils.isNotEmpty(results)) {
            List<Long> ids = new ArrayList<Long>();
            Map<Long, PersonSearchDTO> resultsMap = new HashMap<Long, PersonSearchDTO>();
            for (PersonSearchDTO dto : results) {
                final Long id = Long.valueOf(dto.getId());
                ids.add(id);
                resultsMap.put(id, dto);
            }

            Session s = PoHibernateUtil.getCurrentSession();
            String sql = s
                    .getNamedQuery(
                            "gov.nih.nci.po.service.PersonServiceBean.search.affiliation")
                    .getQueryString();

            Connection c = s.connection();
            PreparedStatement st = null;
            ResultSet rs = null;
            try {
                st = c.prepareStatement(sql.replace(":ids",
                        StringUtils.join(ids, ',')));
                rs = st.executeQuery();
                while (rs.next()) {
                    // CHECKSTYLE:OFF
                    Object[] row = new Object[9];
                    for (int i = 0; i < row.length; i++) {
                        row[i] = rs.getObject(i + 1);
                    }
                    processPersonAffiliationEntry(row, resultsMap);
                }
            } catch (SQLException e) {
                LOG.error(e, e);
                throw new RuntimeException(e); // NOPMD
            } finally {
                try {
                    st.close();
                } catch (Exception e) { // NOPMD
                    LOG.error(e, e);
                }
            }
        }
    }

    @SuppressWarnings({ "PMD.CyclomaticComplexity" })
    private void processPersonAffiliationEntry(Object[] row,
            Map<Long, PersonSearchDTO> resultsMap) {
        final long pid = ((Number) row[0]).longValue();
        PersonSearchDTO dto = resultsMap.get(pid);
        if (dto.getAffiliation() == null) {
            dto.setAffiliation(new TreeSet<Affiliation>());
        }
        Collection<Affiliation> affiliation = dto.getAffiliation();
       
        if (row[2] != null) {
            affiliation.add(new Affiliation(row[2].toString(),
                    EntityStatus.PENDING.name().equals(row[1]), RoleGroup.CRS));
        }
        if (row[4] != null) {
            affiliation.add(new Affiliation(row[4].toString(),
                    EntityStatus.PENDING.name().equals(row[3]), RoleGroup.HCP));
        }
        if (row[6] != null) {
            affiliation.add(new Affiliation(row[6].toString(),
                    EntityStatus.PENDING.name().equals(row[5]), RoleGroup.OC));
        }
        if (row[8] != null) {
            affiliation.add(new Affiliation(row[8].toString(),
                    EntityStatus.PENDING.name().equals(row[7]), RoleGroup.OPI));
        }
        // CHECKSTYLE:ON
    }

    // CHECKSTYLE:OFF
    private PersonSearchDTO convert(Object[] row) {
        PersonSearchDTO dto = new PersonSearchDTO();
        dto.setId(((Number) row[0]).longValue());
        dto.setStatusCode((String) row[1]);
        dto.setCtepID((String) row[2]);
        dto.setFirstName((String) row[3]);
        dto.setMiddleName((String) row[4]);
        dto.setLastName((String) row[5]);
        dto.setTotalCrs(((Number) row[7]).intValue());
        dto.setTotalHcp(((Number) row[8]).intValue());
        dto.setTotalOc(((Number) row[9]).intValue());
        dto.setTotalOpi(((Number) row[10]).intValue());
        dto.setTotalPending(((Number) row[11]).intValue()
                + ((Number) row[12]).intValue() + ((Number) row[13]).intValue()
                + ((Number) row[14]).intValue());
        dto.setAddress1((String) row[15]);
        dto.setAddress2((String) row[16]);
        dto.setCity((String) row[17]);
        dto.setState((String) row[18]);
        dto.setCountry((String) row[19]);
        dto.setZipCode((String) row[20]);
        dto.setComments((String) row[21]);
        dto.setEmailAddresses((String) row[6]);
        dto.setPhones((String) row[22]);
        dto.setDuplicateOf(((BigInteger) row[23]));
        dto.setCountryCode((String) row[24]);
        return dto;
    }

    // CHECKSTYLE:ON

    @SuppressWarnings("deprecation")
    private void appendOrderBy(StringBuilder sql,
            PageSortParams<PersonSearchDTO> params) {
        if (params.getSortCriterion() != null) {
            throw new RuntimeException(// NOPMD
                    "SortCriterion is not supported for SQL queries."); // NOPMD
        }
        if (CollectionUtils.isNotEmpty(params.getDynamicSortCriteria())) {
            for (String sort : params.getDynamicSortCriteria()) {
                PersonSearchSortEnum sortEnum = PersonSearchSortEnum
                        .valueOf(sort);
                appendOrderBy(sql, sortEnum);
            }
        }
        if (params.isDesc()) {
            sql.append(" DESC");
        }
    }

    @SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
    private void appendOrderBy(StringBuilder sql, PersonSearchSortEnum sortEnum) {
        if (sql.toString().contains(ORDER_BY)) {
            sql.append(", ");
        } else {
            sql.append(ORDER_BY);
        }
        if (PERSON_ID == sortEnum) {
            sql.append("p.id");
        }
        if (CTEP_ID == sortEnum) {
            sql.append("ctepID");
        }
        if (PERSON_FIRSTNAME == sortEnum) {
            sql.append("p.firstname");
        }
        if (PERSON_LASTNAME == sortEnum) {
            sql.append("p.lastname");
        }
        if (EMAIL == sortEnum) {
            sql.append("emailAddresses");
        }
        if (CITY == sortEnum) {
            sql.append("city");
        }
        if (STATE == sortEnum) {
            sql.append("state");
        }
        if (STATUS == sortEnum) {
            sql.append("p.status");
        }
    }

    private void appendPagination(StringBuilder sql,
            PageSortParams<PersonSearchDTO> pageSortParams) {
        sql.append(" LIMIT " + pageSortParams.getPageSize());
        if (pageSortParams.getIndex() > 0) {
            sql.append(" OFFSET " + pageSortParams.getIndex());
        }
    }

    private void appendWhere(StringBuilder sql, PersonSearchCriteria criteria) {
        sql.append(" WHERE p.status <> 'NULLIFIED' ");
        if (!criteria.isEmpty()) {
            appendFirstNameClause(sql, criteria);
            appendLastNameClause(sql, criteria);
            appendEmailClause(sql, criteria);
            appendCtepIdClause(sql, criteria);
            appendPoIdClause(sql, criteria);
            appendStatusClause(sql, criteria);
            appendAffiliationClause(sql, criteria);

            appendPendingRolesClause(sql, criteria);

            appendCountryClause(sql, criteria);
            appendAddr1Clause(sql, criteria);
            appendAddr2Clause(sql, criteria);
            appendCityClause(sql, criteria);
            appendStateClause(sql, criteria);
            appendZipCodeClause(sql, criteria);
        }
    }

    private void appendPendingRolesClause(StringBuilder sql,
            PersonSearchCriteria criteria) {
        StringBuilder subClause = new StringBuilder();
        if (Boolean.TRUE.equals(criteria.getHasPendingCrsRoles())) {
            subClause
                    .append(" (select count(id) from clinicalresearchstaff ro "
                            + "where ro.person_id=p.id and ro.status='PENDING') > 0 OR ");
        }
        if (Boolean.TRUE.equals(criteria.getHasPendingHcpRoles())) {
            subClause
                    .append(" (select count(id) from healthcareprovider ro "
                            + "where ro.person_id=p.id and ro.status='PENDING') > 0 OR ");
        }
        if (Boolean.TRUE.equals(criteria.getHasPendingOpiRoles())) {
            subClause
                    .append(" (select count(id) from identifiedperson ro "
                            + "where ro.player_id=p.id and ro.status='PENDING') > 0 OR ");
        }
        if (Boolean.TRUE.equals(criteria.getHasPendingOcRoles())) {
            subClause
                    .append(" (select count(id) from organizationalcontact ro "
                            + "where ro.person_id=p.id and ro.status='PENDING') > 0 OR ");
        }
        if (subClause.length() > 0) {
            sql.append(" AND (").append(subClause).append("1=2) ");
        }
    }

    /**
     * @param sql
     * @param criteria
     */
    private void appendZipCodeClause(StringBuilder sql,
            PersonSearchCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getPostalCode())) {
            sql.append(String.format(
                    " AND lower(a.postalcode) like '%s' ",
                    "%"
                            + StringEscapeUtils.escapeSql(criteria
                                    .getPostalCode().trim().toLowerCase()) + "%"));
        }
    }

    /**
     * @param sql
     * @param criteria
     */
    private void appendStateClause(StringBuilder sql,
            PersonSearchCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getStateOrProvince())) {
            sql.append(String.format(
                    " AND lower(a.stateorprovince) like '%s' ",
                    "%"
                            + StringEscapeUtils.escapeSql(criteria
                                    .getStateOrProvince().trim().toLowerCase()) + "%"));
        }
    }

    /**
     * @param sql
     * @param criteria
     */
    private void appendCityClause(StringBuilder sql,
            PersonSearchCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getCityOrMunicipality())) {
            sql.append(String.format(
                    " AND lower(a.cityormunicipality) like '%s' ",
                    "%"
                            + StringEscapeUtils.escapeSql(criteria
                                    .getCityOrMunicipality().trim().toLowerCase())
                            + "%"));
        }
    }

    /**
     * @param sql
     * @param criteria
     */
    private void appendAddr2Clause(StringBuilder sql,
            PersonSearchCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getDeliveryAddressLine())) {
            sql.append(String.format(
                    " AND lower(a.deliveryaddressline) like '%s' ",
                    "%"
                            + StringEscapeUtils.escapeSql(criteria
                                    .getDeliveryAddressLine().trim().toLowerCase())
                            + "%"));
        }
    }

    /**
     * @param sql
     * @param criteria
     */
    private void appendAddr1Clause(StringBuilder sql,
            PersonSearchCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getStreetAddressLine())) {
            sql.append(String.format(
                    " AND lower(a.streetaddressline) like '%s' ",
                    "%"
                            + StringEscapeUtils.escapeSql(criteria
                                    .getStreetAddressLine().trim().toLowerCase())
                            + "%"));
        }
    }

    /**
     * @param sql
     * @param criteria
     */
    private void appendCountryClause(StringBuilder sql,
            PersonSearchCriteria criteria) {
        if (criteria.getCountryId() != null) {
            sql.append(String.format(" AND c.id=%s ", criteria.getCountryId()));
        }
    }

   

    private void appendAffiliationClause(StringBuilder sql,
            PersonSearchCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getOrg())) {
            String org = "%"
                    + StringEscapeUtils.escapeSql(criteria.getOrg().trim()
                            .toLowerCase()) + "%";
            sql.append(String
                    .format(" AND ((select count(ro.id) from clinicalresearchstaff ro inner join organization o on "
                            + "o.id=ro.organization_id where ro.person_id=p.id and ro.status <> 'NULLIFIED' and "
                            + "lower(o.name) LIKE '%s')>0 OR "
                            + "(select count(ro.id) from healthcareprovider ro inner join organization o on "
                            + "o.id=ro.organization_id where ro.person_id=p.id and ro.status <> 'NULLIFIED' and "
                            + "lower(o.name) LIKE '%s')>0 OR "
                            + "(select count(ro.id) from organizationalcontact ro inner join organization o "
                            + "on o.id=ro.organization_id where ro.person_id=p.id and ro.status <> 'NULLIFIED'"
                            + " and lower(o.name) LIKE '%s')>0 OR "
                            + "(select count(ro.id) from identifiedperson ro inner join organization o on "
                            + "o.id=ro.scoper_id where ro.player_id=p.id and ro.status <> 'NULLIFIED'"
                            + " and lower(o.name) LIKE '%s')>0) ", org, org,
                            org, org));
        }
    }

    private void appendStatusClause(StringBuilder sql,
            PersonSearchCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getStatusCode())) {
            sql.append(String.format(" AND p.status='%s' ",
                    StringEscapeUtils.escapeSql(criteria.getStatusCode())));
        }

    }

    private void appendPoIdClause(StringBuilder sql,
            PersonSearchCriteria criteria) {
        if (NumberUtils.isDigits(criteria.getId())) {
            sql.append(String.format(" AND p.id=%s ",
                    StringEscapeUtils.escapeSql(criteria.getId())));
        }

    }

    private void appendCtepIdClause(StringBuilder sql,
            PersonSearchCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getCtepID())) {
            sql.append(String
                    .format(" AND exists (select ip.assigned_identifier_extension from identifiedperson "
                            + "ip where ip.player_id=p.id and ip.assigned_identifier_root='2.16.840.1.113883.3.26.6.1' "
                            + "and ip.status <> 'NULLIFIED' and lower(ip.assigned_identifier_extension) LIKE '%s' ) ",
                            "%"
                                    + StringEscapeUtils.escapeSql(
                                            criteria.getCtepID().trim()).toLowerCase()
                                    + "%"));
        }
    }

    private void appendEmailClause(StringBuilder sql,
            PersonSearchCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getEmail())) {
            String email = "%"
                    + StringEscapeUtils.escapeSql(criteria.getEmail().trim()
                            .toLowerCase()) + "%";
            sql.append(String
                    .format(" AND (exists (select e.value from email e inner join crs_email ass on e.id=ass.email_id"
                            + " inner join clinicalresearchstaff rol on rol.id=ass.crs_id where rol.person_id=p.id and"
                            + " lower(e.value) like '%s') OR "
                            + "exists (select e.value from email e inner join hcp_email ass on e.id=ass.email_id inner"
                            + " join healthcareprovider rol on rol.id=ass.hcp_id where rol.person_id=p.id and "
                            + "lower(e.value) like '%s') OR "
                            + "exists (select e.value from email e inner join orgcontact_email ass on e.id=ass.email_id"
                            + " inner join organizationalcontact rol on rol.id=ass.orgcontact_id where "
                            + "rol.person_id=p.id and lower(e.value) like '%s') OR "
                            + "exists (select e.value from email e inner join person_email ass on e.id=ass.email_id "
                            + "where ass.person_id=p.id and lower(e.value) like '%s'))",
                            email, email, email, email));
        }

    }

    private void appendLastNameClause(StringBuilder sql,
            PersonSearchCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getLastName())) {
            sql.append(String.format(
                    " AND lower(p.lastname) like '%s' ",
                    "%"
                            + StringEscapeUtils.escapeSql(criteria
                                    .getLastName().trim().toLowerCase()) + "%"));
        }

    }

    private void appendFirstNameClause(StringBuilder sql,
            PersonSearchCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getFirstName())) {
            sql.append(String.format(
                    " AND lower(p.firstname) like '%s' ",
                    "%"
                            + StringEscapeUtils.escapeSql(criteria
                                    .getFirstName().trim().toLowerCase()) + "%"));
        }

    }

    @Override
    public int count(PersonSearchCriteria criteria) {
        Session s = PoHibernateUtil.getCurrentSession();
        StringBuilder sql = new StringBuilder(s.getNamedQuery(
                "gov.nih.nci.po.service.PersonServiceBean.search")
                .getQueryString());
        appendWhere(sql, criteria);
        SQLQuery query = s.createSQLQuery("select count(*) from ("
                + sql.toString() + ") as cnt");
        return ((Number) query.uniqueResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PersonSearchDTO> getInboxPersons(
            PageSortParams<PersonSearchDTO> pageSortParams) {
        Session s = PoHibernateUtil.getCurrentSession();
        List<PersonSearchDTO> results = new ArrayList<PersonSearchDTO>();
        StringBuilder sql = new StringBuilder(s.getNamedQuery(
                "gov.nih.nci.po.service.PersonServiceBean.getInboxPersons")
                .getQueryString());
        appendOrderBy(sql, pageSortParams);
        appendPagination(sql, pageSortParams);
        SQLQuery query = s.createSQLQuery(sql.toString());
        for (Object[] row : (List<Object[]>) query.list()) {
            results.add(convert(row));
        }
        loadAffiliationInfo(results);
        return results;
    }

    @Override
    public int countInboxPersons() {
        Session s = PoHibernateUtil.getCurrentSession();
        StringBuilder sql = new StringBuilder(s.getNamedQuery(
                "gov.nih.nci.po.service.PersonServiceBean.getInboxPersons")
                .getQueryString());
        SQLQuery query = s.createSQLQuery("select count(*) from ("
                + sql.toString() + ") as cnt");
        return ((Number) query.uniqueResult()).intValue();
    }

    @Override
    public long create(Person person, String ctepId) throws EntityValidationException, JMSException {

        long createdPersonId = 0;
        // Step1: create the person
        createdPersonId = create(person);

        // get the Organization representing "CTEP"
        Organization ctepOrg = PoServiceUtil.getCtepOrganization();

        // Step2: now create Person-CtepId
        if (StringUtils.isNotBlank(ctepId)) {
            IdentifiedPerson idenPerson = getNewIdentifiedPersonObject(ctepId,
                    person, ctepOrg, true);
            // IdentifiedPersonServiceBean is setting status PENDING.
            idenPerServ.create(idenPerson);
        }
        return createdPersonId;
    }

    @Override
    public void curate(Person person, String ctepId) throws EntityValidationException, JMSException {

        // Step1: curate the person
        curate(person);

        // Step2: if CtepId is not blank then update it
        if (StringUtils.isNotBlank(ctepId)) {            
            // get the Organization representing "CTEP"
            Organization ctepOrg = PoServiceUtil.getCtepOrganization();

            // populate SearchCriteria to search the existing CtepId Record
            // don't set CtepId during search
            IdentifiedPerson idenPerson = getNewIdentifiedPersonObject(ctepId,
                    person, ctepOrg, false);
            SearchCriteria<IdentifiedPerson> searchCriteria = new AnnotatedBeanSearchCriteria<IdentifiedPerson>(
                    idenPerson);

            // search for existing CtepId Record
            List<IdentifiedPerson> identifiedPeople = idenPerServ.search(searchCriteria);

            if (CollectionUtils.isEmpty(identifiedPeople)) {
                // call the EJB service method to create CtepId
                idenPerson.getAssignedIdentifier().setExtension(ctepId);
                idenPerServ.create(idenPerson);
            } else if (identifiedPeople.size() > 1) {
                IdentifiedPerson[] identifiedPersonsArray = new IdentifiedPerson[identifiedPeople.size()];
                identifiedPeople.toArray(identifiedPersonsArray);
                for (int i = 0; i < identifiedPersonsArray.length; i++) {
                    PoHibernateUtil.getCurrentSession().delete(identifiedPersonsArray[i]);
                }
                idenPerson.getAssignedIdentifier().setExtension(ctepId);
                idenPerServ.create(idenPerson);
            } else {
                // if existing CtepId record found, then update it
                idenPerson = identifiedPeople.get(0);
                idenPerson.getAssignedIdentifier().setExtension(ctepId);
                idenPerServ.curate(idenPerson);
            }
        }
    }

    /**
     * This method is used to create a new IdentifiedPerson object using the
     * passed parameters.
     */
    private IdentifiedPerson getNewIdentifiedPersonObject(String ctepId,
            gov.nih.nci.po.data.bo.Person personBo,
            gov.nih.nci.po.data.bo.Organization organizationBo,
            boolean setCtepId) {
        gov.nih.nci.iso21090.Ii assIden = new gov.nih.nci.iso21090.Ii();
        assIden.setRoot(PoConstants.PERSON_CTEP_ID_ROOT);
        assIden.setIdentifierName(PoConstants.PERSON_CTEP_ID_IDENTIFIER_NAME);
        if (setCtepId) {
            assIden.setExtension(ctepId);
        }
        IdentifiedPerson idenPerson = new IdentifiedPerson();
        idenPerson.setAssignedIdentifier(assIden);
        idenPerson.setPlayer(personBo);
        idenPerson.setScoper(organizationBo);
        // set bi-directional association b/w person & idenPerson
        personBo.getIdentifiedPersons().add(idenPerson);

        return idenPerson;
    }
    
    /**
     * Setter for IdentifiedPersonServiceLocal.
     * @param idenPerServ IdentifiedPersonServiceLocal
     */
    public void setIdenPerServ(IdentifiedPersonServiceLocal idenPerServ) {
        this.idenPerServ = idenPerServ;
    }

}