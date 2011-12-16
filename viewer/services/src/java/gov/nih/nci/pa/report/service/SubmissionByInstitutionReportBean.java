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

import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.report.dto.criteria.InstitutionCriteriaDto;
import gov.nih.nci.pa.report.dto.result.MilestoneResultDto;
import gov.nih.nci.pa.report.dto.result.SummaryByInstitutionResultDto;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.DAQuery;
import gov.nih.nci.pa.service.util.DataAccessServiceLocal;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.ScrollableResults;

/**
* @author Hugh Reinhart
* @since 4/10/2009
*/
@Stateless
@Interceptors(PaHibernateSessionInterceptor.class)
public class SubmissionByInstitutionReportBean extends
        AbstractStandardReportBean<InstitutionCriteriaDto, SummaryByInstitutionResultDto> implements
        SubmissionByInstitutionReportLocal {

    private static final Logger LOG = Logger.getLogger(SubmissionByInstitutionReportBean.class);
    
    @EJB
    private DataAccessServiceLocal dataAccessService;

    private static final int IDENT_IDX = 0;
    private static final int SUBMISSION_NUM_IDX = 1;
    private static final int SUBMITTER_IDX = 2;
    private static final int SUB_DATE_IDX = 3;
    private static final int DWS_IDX = 4;
    private static final int DWS_DATE_IDX = 5;
    private static final int SP_KEY_IDX = 6;
    private static final int LEAD_ORG_NAME_IDX = 7;
    private static final int LEAD_ORG_TRIALID_IDX = 8;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<SummaryByInstitutionResultDto> get(InstitutionCriteriaDto criteria) throws PAException {
        InstitutionCriteriaDto.validate(criteria);
        try {
            List<SummaryByInstitutionResultDto> result = new ArrayList<SummaryByInstitutionResultDto>();
            Map<Long, SummaryByInstitutionResultDto> resultById = new HashMap<Long, SummaryByInstitutionResultDto>();
            loadStudies(criteria, result, resultById);
            loadMilestones(criteria, resultById);
            LOG.debug("Leaving get(TrialListCriteriaDto), returning " + result.size() + " object(s).");
            return result;
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in " + this.getClass(), hbe);
        }
    }

    /**
     * Load the studies for this report.
     * @param criteria The report criteria
     * @param result The result list
     * @param resultById The result by studyProtocolId map.
     */
    void loadStudies(InstitutionCriteriaDto criteria, List<SummaryByInstitutionResultDto> result,
            Map<Long, SummaryByInstitutionResultDto> resultById) {
        SubmissionByInstitutionQueryBuilder queryBuilder = new SubmissionByInstitutionQueryBuilder(criteria);
        DAQuery query = queryBuilder.getReportQuery();
        List<Object[]> queryList = dataAccessService.findByQuery(query);
        for (Object[] row : queryList) {
            SummaryByInstitutionResultDto rdto = readMainResult(row);
            result.add(rdto);
            long spId = ((Number) row[SP_KEY_IDX]).longValue();
            resultById.put(spId, rdto);
        }
    }

    /**
     * Read the data from the result.
     * @param row The result row to read
     * @return The data read in a SummaryByInstitutionResultDto
     */
    SummaryByInstitutionResultDto readMainResult(Object[] row) {
        SummaryByInstitutionResultDto rdto = new SummaryByInstitutionResultDto();
        rdto.setAssignedIdentifier(StConverter.convertToSt((String) row[IDENT_IDX]));
        rdto.setSubmissionDate(TsConverter.convertToTs((Timestamp) row[SUB_DATE_IDX]));
        rdto.setSubmissionNumber(IntConverter.convertToInt((Integer) row[SUBMISSION_NUM_IDX]));
        rdto.setSubmitterOrg(StConverter.convertToSt((String) row[SUBMITTER_IDX]));
        rdto.setDws(CdConverter.convertStringToCd((String) row[DWS_IDX]));
        rdto.setDwsDate(TsConverter.convertToTs((Timestamp) row[DWS_DATE_IDX]));
        rdto.setLeadOrg(StConverter.convertToSt((String) row[LEAD_ORG_NAME_IDX]));
        rdto.setLeadOrgTrialIdentifier(StConverter.convertToSt((String) row[LEAD_ORG_TRIALID_IDX]));
        return rdto;
    }
    
    /**
     * Load the milestones.
     * @param criteria The report criteria
     * @param resultById The result by studyProtocolId map.
     */
    void loadMilestones(InstitutionCriteriaDto criteria, Map<Long, SummaryByInstitutionResultDto> resultById) {
        MilestoneQueryBuilder milestoneQueryBuilder = new MilestoneQueryBuilder(criteria);
        DAQuery query = milestoneQueryBuilder.getMilestoneQuery();
        ScrollableResults scrollable = null;
        try {
            scrollable = dataAccessService.scrollByQuery(query);
            Long currentSpId = null;
            List<StudyMilestone> milestones = new ArrayList<StudyMilestone>();
            scrollable.beforeFirst();
            while (scrollable.next()) {
                StudyMilestone milestone = (StudyMilestone) scrollable.get()[0];
                if (milestone.getStudyProtocol().getId() != currentSpId) {
                    if (currentSpId != null) {
                        SummaryByInstitutionResultDto resultDto = resultById.get(currentSpId);
                        setMilestones(resultDto, milestones);
                    }
                    currentSpId = milestone.getStudyProtocol().getId();
                    milestones = new ArrayList<StudyMilestone>();
                }
                milestones.add(milestone);
            }
            if (currentSpId != null) {
                SummaryByInstitutionResultDto resultDto = resultById.get(currentSpId);
                setMilestones(resultDto, milestones);
            }
        } finally {
            if (scrollable != null) {
                scrollable.close();
            }
        }
    }

    /**
     * Sets the milestones in the result for a single study.
     * @param resultDto The result to set
     * @param milestones The list of milestones for the study.
     */
    void setMilestones(SummaryByInstitutionResultDto resultDto, List<StudyMilestone> milestones) {
        MilestoneResultDto milestoneResult = new MilestoneResultDto();
        boolean admin = false;
        boolean scientific = false;
        for (int i = milestones.size() - 1; i >= 0; i--) {
            StudyMilestone milestone = milestones.get(i);
            MilestoneCode code = milestone.getMilestoneCode();
            if (code.isAdminMilestone()) {
                setAdminMilestone(milestoneResult, milestone, admin);
                admin = true;
                if (scientific) {
                    break;
                }
                continue;
            }
            if (code.isScientificMilestone()) {
                setScientificMilestone(milestoneResult, milestone, scientific);
                scientific = true;
                if (admin) {
                    break;
                }
                continue;
            }
            setMilestone(milestoneResult, milestone, admin || scientific);
            break;
        }
        resultDto.setMilestoneResult(milestoneResult);
    }

    /**
     * Set the admin milestone if it was not already found.
     * @param milestoneResult The milestone result
     * @param milestone the milstone
     * @param found if it has been found already
     */
    void setAdminMilestone(MilestoneResultDto milestoneResult, StudyMilestone milestone, boolean found) {
        if (!found) {
            milestoneResult.setAdminMilestone(CdConverter.convertToCd(milestone.getMilestoneCode()));
            milestoneResult.setAdminMilestoneDate(TsConverter.convertToTs(milestone.getMilestoneDate()));
        }
    }

    /**
     * Set the scientific milestone if it was not already found.
     * @param milestoneResult The milestone result
     * @param milestone the milstone
     * @param found if it has been found already
     */
    void setScientificMilestone(MilestoneResultDto milestoneResult, StudyMilestone milestone, boolean found) {
        if (!found) {
            milestoneResult.setScientificMilestone(CdConverter.convertToCd(milestone.getMilestoneCode()));
            milestoneResult.setScientificMilestoneDate(TsConverter.convertToTs(milestone.getMilestoneDate()));
        }
    }

    /**
     * Set the regular milestone if no other was already found.
     * @param milestoneResult The milestone result
     * @param milestone the milstone
     * @param found if another has been found already
     */
    void setMilestone(MilestoneResultDto milestoneResult, StudyMilestone milestone, boolean found) {
        if (!found) {
            milestoneResult.setMilestone(CdConverter.convertToCd(milestone.getMilestoneCode()));
            milestoneResult.setMilestoneDate(TsConverter.convertToTs(milestone.getMilestoneDate()));
        }
    }

    /**
     * @param dataAccessService the dataAccessService to set
     */
    public void setDataAccessService(DataAccessServiceLocal dataAccessService) {
        this.dataAccessService = dataAccessService;
    }

}
