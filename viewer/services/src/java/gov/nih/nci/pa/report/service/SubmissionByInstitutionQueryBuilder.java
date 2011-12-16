/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The viewer
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This viewer Software License (the License) is between NCI and You. You (or 
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
 * its rights in the viewer Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the viewer Software; (ii) distribute and 
 * have distributed to and by third parties the viewer Software and any 
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
package gov.nih.nci.pa.report.service;

import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.report.dto.criteria.InstitutionCriteriaDto;
import gov.nih.nci.pa.report.enums.SubmissionTypeCode;
import gov.nih.nci.pa.report.util.ReportUtil;
import gov.nih.nci.pa.service.util.DAQuery;

import java.sql.Timestamp;
import java.util.Set;

/**
 * Query builder for the submission by institution report.
 * 
 * @author Michael Visee
 */
public class SubmissionByInstitutionQueryBuilder {

    private static final String MAIN_SQL_START =
            "SELECT oi.extension, sp.submission_number, cm.organization, sp.status_date, "
                    + "dws.status_code, dws.status_date_range_low, sp.identifier, lo.leadOrgName, lo.leadOrgTrialId " 
                    + "FROM study_protocol AS sp "
                    + "LEFT OUTER JOIN study_otheridentifiers as oi ON sp.identifier = oi.study_protocol_id "
                    + "AND oi.root = :spRoot AND oi.identifier_name = :spIdName "
                    + "INNER JOIN document_workflow_status AS dws ON sp.identifier = dws.study_protocol_identifier "
                    + "LEFT OUTER JOIN csm_user AS cm ON sp.user_last_created_id = cm.user_id "
                    + "LEFT OUTER JOIN (SELECT ss.study_protocol_identifier, org.name as leadOrgName, "
                    + "ss.local_sp_indentifier as leadOrgTrialId "
                    + "FROM study_site AS ss "
                    + "JOIN research_organization AS ro ON ss.research_organization_identifier = ro.identifier "
                    + "JOIN organization AS org ON ro.organization_identifier = org.identifier "
                    + "WHERE ss.functional_code = :leadOrgCode) AS lo "
                    + "ON sp.identifier = lo.study_protocol_identifier "
                    + "WHERE sp.status_code = :statusCode  AND "
                    + "sp.date_last_created >= :low AND sp.date_last_created < :high AND "
                    + "dws.identifier in (SELECT max(identifier) FROM document_workflow_status "
                    + " GROUP BY study_protocol_identifier) ";
    
    private static final String ORDER_BY_CLAUSE = "ORDER BY cm.organization, sp.status_date, oi.extension";
    
    private InstitutionCriteriaDto criteria;

    /**
     * Constructor.
     * @param criteria The submission by institution report criteria.
     */
    public SubmissionByInstitutionQueryBuilder(InstitutionCriteriaDto criteria) {
        this.criteria = criteria;
    }

    /**
     * Gets the report query corresponding to the report criteria.
     * @return the report query
     */
    public DAQuery getReportQuery() {
        DAQuery query = new DAQuery();
        query.setSql(true);
        query.addParameter("leadOrgCode", StudySiteFunctionalCode.LEAD_ORGANIZATION.getName());
        query.addParameter("spRoot", IiConverter.STUDY_PROTOCOL_ROOT);
        query.addParameter("spIdName", IiConverter.STUDY_PROTOCOL_IDENTIFIER_NAME);
        query.addParameter("statusCode", ActStatusCode.ACTIVE.name());
        query.addParameter("low", TsConverter.convertToTimestamp(criteria.getTimeInterval().getLow()));
        Long high = TsConverter.convertToDateMidnight(criteria.getTimeInterval().getHigh()).plusDays(1).getMillis();
        query.addParameter("high", new Timestamp(high));
        StringBuilder builder = new StringBuilder(MAIN_SQL_START);
        ctepSql(query, builder);
        submissionTypeSql(builder);
        submitterOrgSql(query, builder);
        builder.append(ORDER_BY_CLAUSE);
        query.setText(builder.toString());
        return query;
    }
    
    /**
     * Adds the ctep criteria to the query.
     * @param query The query under construction
     * @param builder The query text under construction
     */
    void ctepSql(DAQuery query, StringBuilder builder) {
        if (!BlConverter.convertToBool(criteria.getCtep())) {
            builder.append("AND (sp.user_last_created_id IS NULL OR"
                    + "(cm.login_name NOT LIKE '%brownph2' AND cm.login_name NOT LIKE '%pb8593@yahoo.com')) ");
        }
    }

    /**
     * Adds the submission type criteria to the query.
     * @param builder The query text under construction
     */
    void submissionTypeSql(StringBuilder builder) {
        String typeName = CdConverter.convertCdToString(criteria.getSubmissionType());
        switch (SubmissionTypeCode.valueOf(typeName)) {
        case AMENDMENT:
            builder.append("AND sp.submission_number > 1 ");
            break;
        case ORIGINAL:
            builder.append("AND sp.submission_number = 1 ");
            break;
        default:
            break;
        }
    }

    /**
     * Adds the organization criteria to the query.
     * @param query The query under construction
     * @param builder The query text under construction
     */
    void submitterOrgSql(DAQuery query, StringBuilder builder) {
        Set<String> orgs = ReportUtil.convertToString(criteria.getInstitutions());
        if (!orgs.contains(InstitutionCriteriaDto.ALL_ORGANIZATIONS_KEY)) {
            builder.append("AND cm.organization IN (:ORGS) ");
            query.addParameter("ORGS", orgs);
        }
    }

    /**
     * @return the criteria
     */
    public InstitutionCriteriaDto getCriteria() {
        return criteria;
    }

    /**
     * @param criteria the criteria to set
     */
    public void setCriteria(InstitutionCriteriaDto criteria) {
        this.criteria = criteria;
    }
}
