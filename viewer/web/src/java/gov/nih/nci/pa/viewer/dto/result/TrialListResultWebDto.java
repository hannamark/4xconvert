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
package gov.nih.nci.pa.viewer.dto.result;

import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.report.dto.result.TrialListResultDto;
import gov.nih.nci.pa.report.enums.SubmissionTypeCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugh Reinhart
 * @since 05/06/2009
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
public final class TrialListResultWebDto {

    private static final String TOTAL_ORIG = "Total of original submissions";
    private static final String TOTAL_AMEND = "Total of amendments";
    private static final String TOTAL_BOTH = "Total all submission";
    private static final String TOTAL = "Total";

    private static final String SUB_TYPE_ORIG = "Original";
    private static final String SUB_TYPE_AMEND = "Amendment";
    private static final String SUB_TYPE_BOTH = "All";

    private String assignedIdentifier;
    private String submissionType;
    private String submitterOrg;
    private String leadOrgTrialIdentifier;
    private String leadOrg;
    private String dateLastCreated;
    private String dws;
    private String dwsDate;
    private String milestone;
    private String milestoneDate;
    private String adminMilestone;
    private String adminMilestoneDate;
    private String scientificMilestone;
    private String scientificMilestoneDate;

    /**
     * Static method for generating a list of web dto's from a list of service dto's.
     * @param serviceDtoList service dto list
     * @param subTypeCriteria submission type(s) being reported upon
     * @param isCurrentMilestoneReport Is this a current milestone report.
     * @return web dto list
     */
    public static List<TrialListResultWebDto> getWebList(List<TrialListResultDto> serviceDtoList,
            SubmissionTypeCode subTypeCriteria, boolean isCurrentMilestoneReport) {
        List<TrialListResultWebDto> resultList = new ArrayList<TrialListResultWebDto>();
        int original = 0;
        int amendment = 0;
        for (TrialListResultDto dto : serviceDtoList) {
            int submissionNumber = IntConverter.convertToInteger(dto.getSubmissionNumber());
            if (submissionNumber == 1) {
                original++;
            } else {
                amendment++;
            }
            resultList.add(new TrialListResultWebDto(dto));
        }
        if (includeOriginalTotal(subTypeCriteria)) {
            TrialListResultWebDto webDto = new TrialListResultWebDto();
            webDto.setAssignedIdentifier(TOTAL_ORIG);
            webDto.setSubmissionType(SUB_TYPE_ORIG);
            webDto.setSubmitterOrg(Integer.toString(original));
            resultList.add(webDto);
        }
        if (includeAmendmentTotal(subTypeCriteria)) {
            TrialListResultWebDto webDto = new TrialListResultWebDto();
            webDto.setAssignedIdentifier(TOTAL_AMEND);
            webDto.setSubmissionType(SUB_TYPE_AMEND);
            webDto.setSubmitterOrg(Integer.toString(amendment));
            resultList.add(webDto);
        }
        if (includeOverallTotal(subTypeCriteria)) {
            TrialListResultWebDto webDto = new TrialListResultWebDto();
            webDto.setAssignedIdentifier(TOTAL_BOTH);
            webDto.setSubmissionType(SUB_TYPE_BOTH);
            webDto.setSubmitterOrg(Integer.toString(serviceDtoList.size()));
            resultList.add(webDto);
        }
        if (isCurrentMilestoneReport) {
            TrialListResultWebDto webDto = new TrialListResultWebDto();
            webDto.setAssignedIdentifier(TOTAL);
            webDto.setSubmissionType(Integer.toString(serviceDtoList.size()));
            resultList.add(webDto);
        }
        return resultList;
    }

    private static boolean includeOriginalTotal(SubmissionTypeCode code) {
        return code != null && (SubmissionTypeCode.ORIGINAL.equals(code) || SubmissionTypeCode.BOTH.equals(code));
    }

    private static boolean includeAmendmentTotal(SubmissionTypeCode code) {
        return code != null && (SubmissionTypeCode.AMENDMENT.equals(code) || SubmissionTypeCode.BOTH.equals(code));
    }

    private static boolean includeOverallTotal(SubmissionTypeCode code) {
        return code != null && SubmissionTypeCode.BOTH.equals(code);
    }

    /**
     * Default constructor.
     */
    private TrialListResultWebDto() {
    }

    /**
     * Constructor using service dto.
     * @param dto the service iso dto
     */
    private TrialListResultWebDto(TrialListResultDto dto) {
        if (dto == null) { return; }
        assignedIdentifier = StConverter.convertToString(dto.getAssignedIdentifier());
        Integer submissionNumber = IntConverter.convertToInteger(dto.getSubmissionNumber());
        if (submissionNumber != null) {
            if (submissionNumber == 1) {
                submissionType = SUB_TYPE_ORIG;
            } else if (submissionNumber > 1) {
                submissionType = SUB_TYPE_AMEND;
            }
        }
        submitterOrg = StConverter.convertToString(dto.getSubmitterOrg());
        leadOrgTrialIdentifier = StConverter.convertToString(dto.getLeadOrgTrialIdentifier());
        leadOrg = StConverter.convertToString(dto.getLeadOrg());
        dateLastCreated = TsConverter.convertToString(dto.getDateLastCreated());
        dws = DocumentWorkflowStatusCode.valueOf(CdConverter.convertCdToString(dto.getDws())).getCode();
        dwsDate = TsConverter.convertToString(dto.getDwsDate());
        milestone = CdConverter.convertCdToString(dto.getMilestoneResult().getMilestone());
        milestoneDate = TsConverter.convertToString(dto.getMilestoneResult().getMilestoneDate());
        adminMilestone = CdConverter.convertCdToString(dto.getMilestoneResult().getAdminMilestone());
        adminMilestoneDate = TsConverter.convertToString(dto.getMilestoneResult().getAdminMilestoneDate());
        scientificMilestone = CdConverter.convertCdToString(dto.getMilestoneResult().getScientificMilestone());
        scientificMilestoneDate = TsConverter.convertToString(dto.getMilestoneResult().getScientificMilestoneDate());
        
    }

    /**
     * @return the assignedIdentifier
     */
    public String getAssignedIdentifier() {
        return assignedIdentifier;
    }
    /**
     * @param assignedIdentifier the assignedIdentifier to set
     */
    public void setAssignedIdentifier(String assignedIdentifier) {
        this.assignedIdentifier = assignedIdentifier;
    }
    /**
     * @return the submissionType
     */
    public String getSubmissionType() {
        return submissionType;
    }
    /**
     * @param submissionType the submissionType to set
     */
    public void setSubmissionType(String submissionType) {
        this.submissionType = submissionType;
    }
    /**
     * @return the submitterOrg
     */
    public String getSubmitterOrg() {
        return submitterOrg;
    }
    /**
     * @param submitterOrg the submitterOrg to set
     */
    public void setSubmitterOrg(String submitterOrg) {
        this.submitterOrg = submitterOrg;
    }
    /**
     * @return the leadOrgTrialIdentifier
     */
    public String getLeadOrgTrialIdentifier() {
        return leadOrgTrialIdentifier;
    }
    /**
     * @param leadOrgTrialIdentifier the leadOrgTrialIdentifier to set
     */
    public void setLeadOrgTrialIdentifier(String leadOrgTrialIdentifier) {
        this.leadOrgTrialIdentifier = leadOrgTrialIdentifier;
    }
    /**
     * @return the leadOrg
     */
    public String getLeadOrg() {
        return leadOrg;
    }
    /**
     * @param leadOrg the leadOrg to set
     */
    public void setLeadOrg(String leadOrg) {
        this.leadOrg = leadOrg;
    }
    /**
     * @return the dateLastCreated
     */
    public String getDateLastCreated() {
        return dateLastCreated;
    }
    /**
     * @param dateLastCreated the dateLastCreated to set
     */
    public void setDateLastCreated(String dateLastCreated) {
        this.dateLastCreated = dateLastCreated;
    }
    /**
     * @return the dws
     */
    public String getDws() {
        return dws;
    }
    /**
     * @param dws the dws to set
     */
    public void setDws(String dws) {
        this.dws = dws;
    }
    /**
     * @return the dwsDate
     */
    public String getDwsDate() {
        return dwsDate;
    }
    /**
     * @param dwsDate the dwsDate to set
     */
    public void setDwsDate(String dwsDate) {
        this.dwsDate = dwsDate;
    }
    /**
     * @return the milestone
     */
    public String getMilestone() {
        return milestone;
    }
    /**
     * @param milestone the milestone to set
     */
    public void setMilestone(String milestone) {
        this.milestone = milestone;
    }
    /**
     * @return the milestoneDate
     */
    public String getMilestoneDate() {
        return milestoneDate;
    }
    /**
     * @param milestoneDate the milestoneDate to set
     */
    public void setMilestoneDate(String milestoneDate) {
        this.milestoneDate = milestoneDate;
    }

    /**
     * @param adminMilestone the adminMilestone to set
     */
    public void setAdminMilestone(String adminMilestone) {
        this.adminMilestone = adminMilestone;
    }

    /**
     * @return the adminMilestone
     */
    public String getAdminMilestone() {
        return adminMilestone;
    }

    /**
     * @param adminMilestoneDate the adminMilestoneDate to set
     */
    public void setAdminMilestoneDate(String adminMilestoneDate) {
        this.adminMilestoneDate = adminMilestoneDate;
    }

    /**
     * @return the adminMilestoneDate
     */
    public String getAdminMilestoneDate() {
        return adminMilestoneDate;
    }

    /**
     * @param scientificMilestone the scientificMilestone to set
     */
    public void setScientificMilestone(String scientificMilestone) {
        this.scientificMilestone = scientificMilestone;
    }

    /**
     * @return the scientificMilestone
     */
    public String getScientificMilestone() {
        return scientificMilestone;
    }

    /**
     * @param scientificMilestoneDate the scientificMilestoneDate to set
     */
    public void setScientificMilestoneDate(String scientificMilestoneDate) {
        this.scientificMilestoneDate = scientificMilestoneDate;
    }

    /**
     * @return the scientificMilestoneDate
     */
    public String getScientificMilestoneDate() {
        return scientificMilestoneDate;
    }
}
