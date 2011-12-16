/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package gov.nih.nci.pa.viewer.action;

import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.report.dto.result.SummaryByInstitutionResultDto;
import gov.nih.nci.pa.report.enums.SubmissionTypeCode;
import gov.nih.nci.pa.report.service.SubmissionByInstitutionReportLocal;
import gov.nih.nci.pa.report.service.SubmitterOrganizationLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.viewer.dto.criteria.InstitutionCriteriaWebDto;
import gov.nih.nci.pa.viewer.dto.result.SubmissionByInstitutionResultWebDto;
import gov.nih.nci.pa.viewer.util.ViewerServiceLocator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.Preparable;

/**
 * @author Hugh Reinhart
 * @since 4/16/2009
 */
public class SubmissionByInstitutionAction extends
        AbstractReportAction<InstitutionCriteriaWebDto, SubmissionByInstitutionResultWebDto> implements Preparable {

    private static final long serialVersionUID = 7044286786372431982L;
    
    private static final Set<SubmissionTypeCode> ORIGINAL_CODES = EnumSet.of(SubmissionTypeCode.BOTH,
                                                                             SubmissionTypeCode.ORIGINAL);
    private static final Set<SubmissionTypeCode> AMENDMENT_CODES = EnumSet.of(SubmissionTypeCode.BOTH,
                                                                              SubmissionTypeCode.AMENDMENT);
    private static final Set<SubmissionTypeCode> OVERALL_CODES = EnumSet.of(SubmissionTypeCode.BOTH);
    private static final String TOTAL_ORIG = "Total of original submissions";
    private static final String TOTAL_AMEND = "Total of amendments";
    private static final String TOTAL_BOTH = "Total all submission";
    private static final String TOTAL = "Total";
    private static final String SUB_TYPE_ORIG = "Original";
    private static final String SUB_TYPE_AMEND = "Amendment";
    private static final String SUB_TYPE_BOTH = "All";
    
    private SubmissionByInstitutionReportLocal submissionByInstitutionReportService;
    private SubmitterOrganizationLocal submitterOrganizationReportService;

    private InstitutionCriteriaWebDto criteria;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        ViewerServiceLocator locator = ViewerServiceLocator.getInstance();
        setSubmissionByInstitutionReportService(locator.getSubmissionByInstitutionReporttService());
        setSubmitterOrganizationReportService(locator.getSubmitterOrganizationReportService());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute() {
        setCriteria(new InstitutionCriteriaWebDto());
        return super.execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getReport() {
        try {
            List<SummaryByInstitutionResultDto> isoList =
                    submissionByInstitutionReportService.get(criteria.getIsoDto());
            setResultList(getWebList(isoList, SubmissionTypeCode.valueOf(getCriteria().getSubmissionType()), false));
            return super.getReport();
        } catch (PAException e) {
            addActionError(e.getMessage());
            return super.execute();
        }
    }
    
    /**
     * Generates a list of web dto's from a list of service dto's.
     * @param serviceDtoList service dto list
     * @param subTypeCriteria submission type(s) being reported upon
     * @param isCurrentMilestoneReport Is this a current milestone report.
     * @return web dto list
     */
    List<SubmissionByInstitutionResultWebDto> getWebList(List<SummaryByInstitutionResultDto> serviceDtoList,
            SubmissionTypeCode subTypeCriteria, boolean isCurrentMilestoneReport) {
        List<SubmissionByInstitutionResultWebDto> resultList = new ArrayList<SubmissionByInstitutionResultWebDto>();
        int original = 0;
        for (SummaryByInstitutionResultDto dto : serviceDtoList) {
            if (dto.isOriginal()) {
                original++;
            }
            resultList.add(new SubmissionByInstitutionResultWebDto(dto));
        }
        addSummaryResults(resultList, subTypeCriteria, isCurrentMilestoneReport, original, serviceDtoList.size());
        return resultList;
    }

    /**
     * Adds the summary results at the end of the report.
     * @param resultList The result list
     * @param subTypeCriteria submission type(s) being reported upon
     * @param isCurrentMilestoneReport Is this a current milestone report.
     * @param original Number of original submissions
     * @param total Total number of submissions
     */
    void addSummaryResults(List<SubmissionByInstitutionResultWebDto> resultList, SubmissionTypeCode subTypeCriteria,
            boolean isCurrentMilestoneReport, int original, int total) {
        if (ORIGINAL_CODES.contains(subTypeCriteria)) {
            SubmissionByInstitutionResultWebDto webDto =
                    new SubmissionByInstitutionResultWebDto(TOTAL_ORIG, SUB_TYPE_ORIG, Integer.toString(original));
            resultList.add(webDto);
        }
        if (AMENDMENT_CODES.contains(subTypeCriteria)) {
            SubmissionByInstitutionResultWebDto webDto =
                    new SubmissionByInstitutionResultWebDto(TOTAL_AMEND, SUB_TYPE_AMEND, Integer.toString(total
                            - original));
            resultList.add(webDto);
        }
        if (OVERALL_CODES.contains(subTypeCriteria)) {
            SubmissionByInstitutionResultWebDto webDto =
                    new SubmissionByInstitutionResultWebDto(TOTAL_BOTH, SUB_TYPE_BOTH, Integer.toString(total));
            resultList.add(webDto);
        }
        if (isCurrentMilestoneReport) {
            SubmissionByInstitutionResultWebDto webDto =
                    new SubmissionByInstitutionResultWebDto(TOTAL, Integer.toString(total), null);
            resultList.add(webDto);
        }
    }

    /**
     * @return the submitterOrganizations
     */
    public List<String> getSubmitterOrganizations() {
        List<String> submitterOrganizations = new ArrayList<String>();
        try {
            for (St iso : submitterOrganizationReportService.get()) {
                submitterOrganizations.add(StConverter.convertToString(iso));
            }
        } catch (PAException e) {
            addActionError(e.getMessage());
        }
        return submitterOrganizations;
    }

    /**
     * @return list of institutions for display
     */
    public String getSelectedInstitutions() {
        String result = null;
        if (criteria.getInstitutions().contains("1")) {
            result = "All";
        } else {
            List<String> iList = new ArrayList<String>();
            iList.addAll(criteria.getInstitutions());
            Collections.sort(iList);
            result = StringUtils.join(iList, ", ");
        }
        return result;
    }

    /**
     * @return the criteria
     */
    public InstitutionCriteriaWebDto getCriteria() {
        return criteria;
    }

    /**
     * @param criteria the criteria to set
     */
    public void setCriteria(InstitutionCriteriaWebDto criteria) {
        this.criteria = criteria;
    }

    /**
     * @param submissionByInstitutionReportService the submissionByInstitutionReportService to set
     */
    public void setSubmissionByInstitutionReportService(
            SubmissionByInstitutionReportLocal submissionByInstitutionReportService) {
        this.submissionByInstitutionReportService = submissionByInstitutionReportService;
    }

    /**
     * @param submitterOrganizationReportService the submitterOrganizationReportService to set
     */
    public void setSubmitterOrganizationReportService(SubmitterOrganizationLocal submitterOrganizationReportService) {
        this.submitterOrganizationReportService = submitterOrganizationReportService;
    }

   

}
