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
*
*
*/
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.MilestoneTrialHistoryWebDTO;
import gov.nih.nci.pa.dto.MilestoneWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyMilestoneServicelocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.joda.time.DateTime;
/**
* @author Hugh Reinhart
* @since 1/16/2009
*/
public final class MilestoneAction extends AbstractListEditAction {

    private static final long serialVersionUID = -2837652488778559394L;

    private MailManagerServiceLocal mailManagerService;
    private ProtocolQueryServiceLocal protocolQueryService;
    private StudyMilestoneServicelocal studyMilestoneService;
    private StudyProtocolServiceLocal studyProtocolService;

    private MilestoneWebDTO milestone;
    private List<MilestoneWebDTO> milestoneList;
    private Map<Integer, MilestoneTrialHistoryWebDTO> amendmentMap;
    private Integer submissionNumber;
    private List<String> allowedMilestones;
    private boolean addAllowed;

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() throws PAException {
        super.prepare();
        mailManagerService = PaRegistry.getMailManagerService();
        protocolQueryService = PaRegistry.getProtocolQueryService();
        studyMilestoneService = PaRegistry.getStudyMilestoneService();
        studyProtocolService = PaRegistry.getStudyProtocolService();
    }

    /**
     * @throws PAException exception
     */
    @Override
    protected void loadEditForm() throws PAException {
        milestone = new MilestoneWebDTO();
        allowedMilestones = computeAllowedMilestones();
    }

    private List<String> computeAllowedMilestones() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        boolean adminAbs = BooleanUtils.toBoolean((Boolean) session.getAttribute(Constants.IS_ADMIN_ABSTRACTOR));
        boolean scAbs = BooleanUtils.toBoolean((Boolean) session.getAttribute(Constants.IS_SCIENTIFIC_ABSTRACTOR));
        boolean suAbs = BooleanUtils.toBoolean((Boolean) session.getAttribute(Constants.IS_SU_ABSTRACTOR));
        StudyProtocolQueryDTO spqDTO = (StudyProtocolQueryDTO) session.getAttribute(Constants.TRIAL_SUMMARY);
        MilestoneAccessHelper accessHelper = new MilestoneAccessHelper(adminAbs, scAbs, suAbs, spqDTO);
        return accessHelper.getAllowedMilestones();
    }

    private Ii getTrialIiBySubmissionNumber() {
        Ii spIi = null;
        if (amendmentMap.isEmpty()) {
            spIi = getSpIi();
        } else {
            spIi = amendmentMap.get(submissionNumber).getIdentifier();
        }
        return spIi;
    }

    /**
     * @throws PAException exception
     */
    @Override
    protected void loadListForm() throws PAException {
        loadAmendmentMap();
        Ii spIi = getTrialIiBySubmissionNumber();
        addAllowed = spIi.getExtension().equals(getSpIi().getExtension()) && !computeAllowedMilestones().isEmpty();
        List<StudyMilestoneDTO> smList = studyMilestoneService.getByStudyProtocol(spIi);
        milestoneList = new ArrayList<MilestoneWebDTO>();
        for (StudyMilestoneDTO sm : smList) {
            milestoneList.add(new MilestoneWebDTO(sm));
        }
    }

    private void loadAmendmentMap() throws PAException {
        Ii studyProtocolIi = getSpIi();
        StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
        StudyProtocolDTO toSearchspDTO = new StudyProtocolDTO();
        toSearchspDTO.setSecondaryIdentifiers(DSetConverter.convertIiToDset(PAUtil.getAssignedIdentifier(spDTO)));
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        Map<Integer, MilestoneTrialHistoryWebDTO> spMap = new TreeMap<Integer, MilestoneTrialHistoryWebDTO>();
        try {
            List<StudyProtocolDTO> spList = studyProtocolService.search(toSearchspDTO, limit);
            if (CollectionUtils.isNotEmpty(spList)) {
                for (StudyProtocolDTO sp : spList) {
                    Integer sn = IntConverter.convertToInteger(sp.getSubmissionNumber());
                    spMap.put(sn, new MilestoneTrialHistoryWebDTO(sp));
                    if (submissionNumber == null && studyProtocolIi.equals(sp.getIdentifier())) {
                        submissionNumber = sn;
                    }
                }
            }
        } catch (TooManyResultsException e) {
            throw new PAException(e);
        }
        setAmendmentMap(spMap);
    }

    /**
     * @return action result
     * @throws PAException exception
     */
    @Override
    public String add() throws PAException {
        StudyMilestoneDTO dto = new StudyMilestoneDTO();
        String date = milestone.getDate();
        dto.setCommentText(StConverter.convertToSt(milestone.getComment()));
        dto.setMilestoneCode(CdConverter.convertStringToCd(milestone.getMilestone()));
        Date now = new DateTime().toDate();
        if (StringUtils.isNotEmpty(date) && DateUtils.isSameDay(now, PAUtil.dateStringToDateTime(date))) {
            dto.setMilestoneDate(TsConverter.convertToTs(now));
        } else {
            dto.setMilestoneDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(date)));
        }
        dto.setStudyProtocolIdentifier(getSpIi());
        try {
            studyMilestoneService.create(dto);
            // update the trial summary session bean
            Long spIi = IiConverter.convertToLong(getSpIi());
            StudyProtocolQueryDTO studyProtocolQueryDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(spIi);
            ServletActionContext.getRequest().getSession().setAttribute(Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);
            if (MilestoneCode.SUBMISSION_ACCEPTED.getCode().equalsIgnoreCase(milestone.getMilestone())) {
                StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(getSpIi());
                Integer sn = IntConverter.convertToInteger(spDTO.getSubmissionNumber());
                if (sn > 1) {
                    // send mail
                    mailManagerService.sendAmendAcceptEmail(getSpIi());
                } else {
                    mailManagerService.sendAcceptEmail(getSpIi());
                }
                TrialHelper helper = new TrialHelper();
                ServletActionContext.getRequest().getSession()
                    .setAttribute(Constants.DOC_WFS_MENU, helper.setMenuLinks(DocumentWorkflowStatusCode.ACCEPTED));
            }
        } catch (PAException e) {
            addActionError(e.getMessage());
            return super.create();
        }

        return super.add();
    }

    /**
     * @return the milestone
     */
    public MilestoneWebDTO getMilestone() {
        return milestone;
    }

    /**
     * @param milestone the milestone to set
     */
    public void setMilestone(MilestoneWebDTO milestone) {
        this.milestone = milestone;
    }

    /**
     * @return the milestoneList
     */
    public List<MilestoneWebDTO> getMilestoneList() {
        return milestoneList;
    }

    /**
     * @param milestoneList the milestoneList to set
     */
    public void setMilestoneList(List<MilestoneWebDTO> milestoneList) {
        this.milestoneList = milestoneList;
    }

    /**
     * @return the amendmentMap
     */
    public Map<Integer, MilestoneTrialHistoryWebDTO> getAmendmentMap() {
        return amendmentMap;
    }

    /**
     * @param amendmentMap the amendmentMap to set
     */
    public void setAmendmentMap(Map<Integer, MilestoneTrialHistoryWebDTO> amendmentMap) {
        this.amendmentMap = amendmentMap;
    }

    /**
     * @return the submissionNumber
     */
    public Integer getSubmissionNumber() {
        return submissionNumber;
    }

    /**
     * @param submissionNumber the submissionNumber to set
     */
    public void setSubmissionNumber(Integer submissionNumber) {
        this.submissionNumber = submissionNumber;
    }

    /**
     * @return the addAllowed
     */
    public boolean isAddAllowed() {
        return addAllowed;
    }

    /**
     * @param addAllowed the addAllowed to set
     */
    public void setAddAllowed(boolean addAllowed) {
        this.addAllowed = addAllowed;
    }

    /**
     * @return the allowedMilestones
     */
    public List<String> getAllowedMilestones() {
        return allowedMilestones;
    }

    /**
     * @param allowedMilestones the allowedMilestones to set
     */
    public void setAllowedMilestones(List<String> allowedMilestones) {
        this.allowedMilestones = allowedMilestones;
    }

    /**
     * @param mailManagerService the mailManagerService to set
     */
    public void setMailManagerService(MailManagerServiceLocal mailManagerService) {
        this.mailManagerService = mailManagerService;
    }

    /**
     * @param protocolQueryService the protocolQueryService to set
     */
    public void setProtocolQueryService(ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * @param studyMilestoneService the studyMilestoneService to set
     */
    public void setStudyMilestoneService(StudyMilestoneServicelocal studyMilestoneService) {
        this.studyMilestoneService = studyMilestoneService;
    }

    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

}
