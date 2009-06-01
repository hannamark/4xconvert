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

import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.AdxpAl;
import gov.nih.nci.coppa.iso.AdxpCnt;
import gov.nih.nci.coppa.iso.AdxpCty;
import gov.nih.nci.coppa.iso.AdxpSta;
import gov.nih.nci.coppa.iso.AdxpZip;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.ContactWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Action class for IRB.
 *
 * @author Hugh Reinhart
 * @since 11/21/2008 
 */
@SuppressWarnings({ "PMD.SignatureDeclareThrowsException", "PMD.CyclomaticComplexity"
        , "PMD.NPathComplexity", "PMD.TooManyFields" })
public class IrbAction extends ActionSupport implements Preparable {
    private static final long serialVersionUID = 1230909090L;

    private StudyParticipationServiceRemote sPartService;
    private StudyProtocolServiceRemote sProtService;
    private OrganizationCorrelationServiceBean orgCorrService;
    private CorrelationUtils correlationUtils;
    private Ii spIdIi;

    private String approvalStatus;
    private String approvalNumber;
    private String siteRelated;
    
    private ContactWebDTO ct = new ContactWebDTO();
    
    private String contactAffiliation;
    private String boardChanged;
    private String newOrgId;
    private String newOrgName;

    /**
     * @throws Exception exception
     */
    public void prepare() throws Exception {
        sProtService = PaRegistry.getStudyProtocolService();
        sPartService = PaRegistry.getStudyParticipationService();
        orgCorrService = new OrganizationCorrelationServiceBean();
        correlationUtils = new CorrelationUtils();
        StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext
            .getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);
        spIdIi = IiConverter.convertToIi(spDTO.getStudyProtocolId());
      }

    /**
     * @return action
     * @throws Exception exception
     */
    @Override
    public String execute() throws Exception {
        loadForm();
        return SUCCESS;
    }
    
    /**
     * @return action
     * @throws Exception exception
     */
    public String fromPO() throws Exception {
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        loadOrg(orgId);
        return SUCCESS;
    }
    

    /**
     * @return action
     * @throws Exception exception
     */
    public String save() throws Exception {
        businessRules();
        if (hasActionErrors()) {
            return SUCCESS;
        }
        try {
            if (ReviewBoardApprovalStatusCode.SUBMISSION_NOT_REQUIRED.equals(getApprovalStatusEnum())) {
                saveSubmissionNotRequired();
            } else if (ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.equals(getApprovalStatusEnum()) 
                    || ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.equals(getApprovalStatusEnum())) {
                saveSubmissionRequired();
            }
        } catch (PAException e) {
            addActionError(e.getMessage());
             return SUCCESS;
        }
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        loadForm();
        return SUCCESS;
    }
    
    /**
     * @return the approvalStatus
     */
    public String getApprovalStatus() {
        return approvalStatus;
    }

    /**
     * @param approvalStatus the approvalStatus to set
     */
    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    /**
     * @return the approvalNumber
     */
    public String getApprovalNumber() {
        return approvalNumber;
    }

    /**
     * @param approvalNumber the approvalNumber to set
     */
    public void setApprovalNumber(String approvalNumber) {
        this.approvalNumber = approvalNumber;
    }

    /**
     * @return the siteRelated
     */
    public String getSiteRelated() {
        return siteRelated;
    }

    /**
     * @param siteRelated the siteRelated to set
     */
    public void setSiteRelated(String siteRelated) {
        this.siteRelated = siteRelated;
    }

    /**
     * @return the contactAffiliation
     */
    public String getContactAffiliation() {
        return contactAffiliation;
    }

    /**
     * @param contactAffiliation the contactAffiliation to set
     */
    public void setContactAffiliation(String contactAffiliation) {
        this.contactAffiliation = contactAffiliation;
    }

    /**
     * @return the boardChanged
     */
    public String getBoardChanged() {
        return boardChanged;
    }

    /**
     * @param boardChanged the boardChanged to set
     */
    public void setBoardChanged(String boardChanged) {
        this.boardChanged = boardChanged;
    }

    /**
     * @return the ct
     */
    public ContactWebDTO getCt() {
        return ct;
    }

    /**
     * @param ct the ct to set
     */
    public void setCt(ContactWebDTO ct) {
        this.ct = ct;
    }

    /**
     * @return the newOrgId
     */
    public String getNewOrgId() {
        return newOrgId;
    }

    /**
     * @param newOrgId the newOrgId to set
     */
    public void setNewOrgId(String newOrgId) {
        this.newOrgId = newOrgId;
    }

    /**
     * @return the newOrgName
     */
    public String getNewOrgName() {
        return newOrgName;
    }

    /**
     * @param newOrgName the newOrgName to set
     */
    public void setNewOrgName(String newOrgName) {
        this.newOrgName = newOrgName;
    }

    private void businessRules() {
        if (PAUtil.isEmpty(getApprovalStatus())) {
            addActionError("Must select an approval status.  ");
        }
        if (ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.equals(getApprovalStatusEnum())
            || ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.equals(getApprovalStatusEnum())) {
            if (getContactAffiliation() == null) {
                addActionError("Must Enter a Board Affliation.  ");
            }
            if (PAUtil.isEmpty(ct.getName())) {
                addActionError("The organziation name must be selected.  ");
            } else {
                if (PAUtil.isEmpty(ct.getAddress())) {
                    addActionError("Address must be set; use PO Curation tool.  ");
                }
                if (PAUtil.isEmpty(ct.getCity())) {
                    addActionError("City must be set; use PO Curation tool.  ");
                }
                if (PAUtil.isEmpty(ct.getZip())) {
                    addActionError("Zip/postal code must be set; use PO Curation tool.  ");
                }
                if (PAUtil.isEmpty(ct.getCountry())) {
                    addActionError("Country must be set; use PO Curation tool.  ");
                }
                if (PAUtil.isEmpty(ct.getEmail())) {
                    addActionError("A contact e-mail address must be set; use PO Curation tool.  ");
                }
            }
        }
        
    }

    private void saveSubmissionNotRequired() throws Exception {
        StudyProtocolDTO dto = sProtService.getStudyProtocol(spIdIi);
        dto.setReviewBoardApprovalRequiredIndicator(BlConverter.convertToBl(false));
        sProtService.updateStudyProtocol(dto);
        
        List<StudyParticipationDTO> spList = sPartService.getByStudyProtocol(spIdIi);
        for (StudyParticipationDTO sp : spList) {
             if (StudyParticipationFunctionalCode.STUDY_OVERSIGHT_COMMITTEE.getCode().equals(
                    sp.getFunctionalCode().getCode())) {
                        sPartService.delete(sp.getIdentifier());
            }
        }
    }

    private void saveSubmissionRequired() throws Exception {
        Ii sPartToUpdate = this.getStudyParticipationToUpdate();
        String poOrgId = getCt().getId();
        if (PAUtil.isEmpty(poOrgId)) {
            throw new PAException("Board name must be set for '" + getApprovalStatus() + "'.  ");
        }
        
        Long oversightCommitteeId = orgCorrService.createOversightCommitteeCorrelations(poOrgId);
        StudyParticipationDTO partDto  = null;
        boolean newFlag = false;
        if (sPartToUpdate != null) {
         partDto = sPartService.get(sPartToUpdate);
        } else {
            newFlag = true;
            partDto = new StudyParticipationDTO();
            partDto.setStudyProtocolIdentifier(spIdIi);
            partDto.setFunctionalCode(
                    CdConverter.convertToCd(StudyParticipationFunctionalCode.STUDY_OVERSIGHT_COMMITTEE));
        }
        partDto.setOversightCommitteeIi(IiConverter.convertToIi(oversightCommitteeId));
        partDto.setReviewBoardApprovalNumber(StConverter.convertToSt(getApprovalNumber()));
        partDto.setReviewBoardApprovalStatusCode(CdConverter.convertToCd(getApprovalStatusEnum()));
        partDto.setBoardAffiliation(StConverter.convertToSt(getContactAffiliation()));
        partDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(null));
        partDto.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
        if (newFlag) {
         sPartService.create(partDto);
        } else {
            sPartService.update(partDto);
        }
            
        //update the study protocol
        StudyProtocolDTO studyDto = sProtService.getStudyProtocol(spIdIi);
        studyDto.setReviewBoardApprovalRequiredIndicator(BlConverter.convertToBl(true));
        sProtService.updateStudyProtocol(studyDto);
    
        
        }
    
    
    private Ii getStudyParticipationToUpdate() throws Exception {
        Ii sPartToUpdate = null;
        List<StudyParticipationDTO> spList = sPartService.getByStudyProtocol(spIdIi);
            for (StudyParticipationDTO sp : spList) {
                if (StudyParticipationFunctionalCode.STUDY_OVERSIGHT_COMMITTEE.getCode().equals(
                        sp.getFunctionalCode().getCode())) {
                    sPartToUpdate = sp.getIdentifier();
                }
            }
                   
        return sPartToUpdate;
    }

   

    private void loadForm() throws Exception {
        StudyProtocolDTO study = sProtService.getStudyProtocol(spIdIi);
        Boolean b = BlConverter.covertToBoolean(study.getReviewBoardApprovalRequiredIndicator());
        if (b == null || !b) {
            setApprovalStatus((b == null) ? null : ReviewBoardApprovalStatusCode.SUBMISSION_NOT_REQUIRED.getCode());
            setApprovalNumber(null);
            setContactAffiliation(null);
            ct.setName(null);
            ct.setAddress(null);
            ct.setCity(null);
            ct.setState(null);
            ct.setZip(null);
            ct.setCountry(null);
            ct.setPhone(null);
            ct.setEmail(null);
           } else {
            List<StudyParticipationDTO> partList = sPartService.getByStudyProtocol(spIdIi);
            for (StudyParticipationDTO part : partList) {
                if (ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode().equals(
                        part.getReviewBoardApprovalStatusCode().getCode())
                    || ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getCode().equals(
                            part.getReviewBoardApprovalStatusCode().getCode())) {
                    
                  setApprovalStatus(part.getReviewBoardApprovalStatusCode().getCode());
                  setApprovalNumber(StConverter.convertToString(part.getReviewBoardApprovalNumber()));
                  setContactAffiliation(StConverter.convertToString(part.getBoardAffiliation()));
                  if (PAUtil.isIiNull(part.getOversightCommitteeIi())) {
                      loadOrg(null);
                  } else {
                      Organization paOrg = correlationUtils.getPAOrganizationByPAOversightCommitteeId(
                              IiConverter.convertToLong(part.getOversightCommitteeIi()));
                      loadOrg(paOrg.getIdentifier());
                  }
               }
            }
        }
    }

    private void loadOrg(String poOrgId) throws Exception {
        if (PAUtil.isEmpty(poOrgId)) {
              ct = new ContactWebDTO();
            return;
        }
        OrganizationDTO poOrg = PoRegistry.getOrganizationEntityService().
            getOrganization(IiConverter.converToPoOrganizationIi(poOrgId));
        if (poOrg == null) {
            throw new PAException("Error getting organization data from PO for id = " + poOrgId
                    + ".  Check that PO service is running and databases are synchronized.  ");
        }
        newOrgId = poOrgId;
        newOrgName = EnOnConverter.convertEnOnToString(poOrg.getName());
        ct = new ContactWebDTO();
        ct.setId(newOrgId);
        ct.setName(newOrgName);
        List<Adxp> adxpList = poOrg.getPostalAddress().getPart();
        for (Adxp adxp : adxpList) {
            if (adxp instanceof AdxpAl) {
                ct.setAddress(adxp.getValue());
            }
            if (adxp instanceof AdxpCty) {
                ct.setCity(adxp.getValue());
            }
            if (adxp instanceof AdxpSta) {
                ct.setState(adxp.getValue());
            }
            if (adxp instanceof AdxpZip) {
                ct.setZip(adxp.getValue());
            }
            if (adxp instanceof AdxpCnt) {
                ct.setCountry(adxp.getCode());
            }
        }
        Object[] telList = poOrg.getTelecomAddress().getItem().toArray();
        boolean eMailSet = false;
        boolean phoneSet = false;
        for (Object tel : telList) {
            if (!eMailSet && tel instanceof TelEmail) {
                ct.setEmail(((TelEmail) tel).getValue().getSchemeSpecificPart());
                eMailSet = true;
            }
            if (!phoneSet && tel instanceof TelPhone) {
                ct.setPhone(((TelPhone) tel).getValue().getSchemeSpecificPart());
                phoneSet = true;
            }
        }
    }
    
    private ReviewBoardApprovalStatusCode getApprovalStatusEnum() {
        return ReviewBoardApprovalStatusCode.getByCode(getApprovalStatus());
    }
}
