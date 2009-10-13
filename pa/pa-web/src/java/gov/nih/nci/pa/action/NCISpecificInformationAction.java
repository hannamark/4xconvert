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

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.NCISpecificInformationWebDTO;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.organization.OrganizationDTO;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author gnaveh
 *
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength" })
public class NCISpecificInformationAction extends ActionSupport {
    private static final long serialVersionUID = -5560377425534113809L;
    private static final String DISPLAY_ORG_FLD = "displayOrgFld";
    private static final Logger LOG = Logger.getLogger(NCISpecificInformationAction.class);
    private NCISpecificInformationWebDTO nciSpecificInformationWebDTO = new NCISpecificInformationWebDTO();
    private String chosenOrg;

    /**
     * @return result
     */
    @Override
    public String execute() {
        return SUCCESS;
    }

    /**
     * @return result
     */
    public String query() {
        LOG.info("Entering query");
        try {
            // Step 1 : get from StudyProtocol
            StudyProtocolDTO studyProtocolDTO = getStudyProtocol();
            // Step 2 : get from StudyResourcing
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                    Constants.STUDY_PROTOCOL_II);
            StudyResourcingDTO studyResourcingDTO = PaRegistry.getStudyResourcingService().getsummary4ReportedResource(
                    studyProtocolIi);
            nciSpecificInformationWebDTO = setNCISpecificDTO(studyProtocolDTO, studyResourcingDTO);
            if (studyResourcingDTO != null && studyResourcingDTO.getOrganizationIdentifier() != null) {
                // get the name of the organization using primary id
                Organization o = new Organization();
                o.setId(Long.valueOf(studyResourcingDTO.getOrganizationIdentifier().getExtension()));
                Organization org = PaRegistry.getPAOrganizationService().getOrganizationByIndetifers(o);
                // set the name
                nciSpecificInformationWebDTO.setOrganizationName(org.getName());
                // set the organization identifer
                nciSpecificInformationWebDTO.setOrganizationIi(org.getIdentifier());
            }
            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }

    /**
     * @return res
     */
    public String update() {
        // Step1 : check for any errors
        if ((PAUtil.isBlNull(getStudyProtocol().getProprietaryTrialIndicator())
              || !getStudyProtocol().getProprietaryTrialIndicator().getValue().booleanValue())
              && PAUtil.isEmpty(nciSpecificInformationWebDTO.getAccrualReportingMethodCode())) {
            addFieldError("nciSpecificInformationWebDTO.accrualReportingMethodCode",
                    getText("error.studyProtocol.accrualReportingMethodCode"));
        }
       /* if (!PAUtil.isNotNullOrNotEmpty(nciSpecificInformationWebDTO.getSummaryFourFundingCategoryCode())) {
            addFieldError("nciSpecificInformationWebDTO.summaryFourFundingCategoryCode",
                    getText("error.studyProtocol.summaryFourFundingCategoryCode"));
        }
        if (!PAUtil.isNotNullOrNotEmpty(nciSpecificInformationWebDTO.getOrganizationName())) {
          addFieldError("nciSpecificInformationWebDTO.organizationName",
                  getText("error.studyProtocol.summaryFourFundingSource"));
       }*/
        if (hasFieldErrors()) {
            return ERROR;
        }
        // Step2 : retrieve the studyprotocol
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        StudyResourcingDTO srDTO = new StudyResourcingDTO();
        try {
            // Step 0 : get the studyprotocol from database
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                    Constants.STUDY_PROTOCOL_II);
            spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            // Step1 : update values to StudyProtocol
            spDTO.setAccrualReportingMethodCode(CdConverter.convertToCd(AccrualReportingMethodCode
                    .getByCode(nciSpecificInformationWebDTO.getAccrualReportingMethodCode())));
            spDTO.setProgramCodeText(StConverter.convertToSt(nciSpecificInformationWebDTO.getProgramCodeText()));
            
            // Step2 : update values to StudyResourcing
            srDTO.setTypeCode(CdConverter.convertToCd(SummaryFourFundingCategoryCode
                    .getByCode(nciSpecificInformationWebDTO.getSummaryFourFundingCategoryCode())));
            srDTO.setStudyProtocolIdentifier(studyProtocolIi);
            // Step3: update studyprotocol
            spDTO = PaRegistry.getStudyProtocolService().updateStudyProtocol(spDTO);
            // Step 4: check if we have an organization for PO id
            String poIdentifer = nciSpecificInformationWebDTO.getOrganizationIi();
            Long orgId = null;
            if (StringUtils.isNotBlank(poIdentifer)) {
                Organization o = new Organization();
                o.setIdentifier(poIdentifer);
                Organization org = PaRegistry.getPAOrganizationService().getOrganizationByIndetifers(o);
                if (org == null) {
                    OrganizationCorrelationServiceBean ocsb = new OrganizationCorrelationServiceBean();
                    OrganizationDTO oDto = PoRegistry.getOrganizationEntityService().getOrganization(
                            IiConverter.convertToPoOrganizationIi(poIdentifer));
                    // create a new org if its null
//                    org = new Organization();
//                    org.setIdentifier(poIdentifer);
//                    org.setName(nciSpecificInformationWebDTO.getOrganizationName());
//                    Organization crOrg = PaRegistry.getPAOrganizationService().createOrganization(org);
//                    ocsb.createPAOrganizationUsingPO(oDto);
                    orgId = ocsb.createPAOrganizationUsingPO(oDto).getId();
                } else {
                    // get the org from the database
                    orgId = org.getId();
                }
            }
            // Step4 : find out if summary 4 records already exists
            StudyResourcingDTO summary4ResoureDTO = PaRegistry.getStudyResourcingService().getsummary4ReportedResource(
                    studyProtocolIi);
            if (summary4ResoureDTO == null) {
                // summary 4 record does not exist,so create a new one
                summary4ResoureDTO = new StudyResourcingDTO();
                summary4ResoureDTO.setStudyProtocolIdentifier(studyProtocolIi);
                summary4ResoureDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.TRUE));
                summary4ResoureDTO.setTypeCode(CdConverter.convertToCd(SummaryFourFundingCategoryCode
                        .getByCode(nciSpecificInformationWebDTO.getSummaryFourFundingCategoryCode())));
                summary4ResoureDTO.setOrganizationIdentifier(IiConverter.convertToIi(orgId));
                PaRegistry.getStudyResourcingService().createStudyResourcing(summary4ResoureDTO);
            } else {
                // summary 4 record does exist,so so do an update
                summary4ResoureDTO.setStudyProtocolIdentifier(studyProtocolIi);
                summary4ResoureDTO.setTypeCode(CdConverter.convertToCd(SummaryFourFundingCategoryCode
                        .getByCode(nciSpecificInformationWebDTO.getSummaryFourFundingCategoryCode())));
                summary4ResoureDTO.setOrganizationIdentifier(IiConverter.convertToIi(orgId));
                PaRegistry.getStudyResourcingService().updateStudyResourcing(summary4ResoureDTO);
            }
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, "Update succeeded.");
        } catch (Exception e) {
            addActionError(e.getMessage());
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            return ERROR;
        }
        // nciSpecificInformationWebDTO = setNCISpecificDTO(spDTO , srDTO);
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        return SUCCESS;
    }

    /**
     *
     * @return nciSpecificInformationWebDTO
     */
    public NCISpecificInformationWebDTO getNciSpecificInformationWebDTO() {
        return nciSpecificInformationWebDTO;
    }

    /**
     *
     * @param nciSpecificInformationWebDTO nciSpecificInformationWebDTO
     */
    public void setNciSpecificInformationWebDTO(NCISpecificInformationWebDTO nciSpecificInformationWebDTO) {
        this.nciSpecificInformationWebDTO = nciSpecificInformationWebDTO;
    }

    // @todo : catch and throw paexception
    private StudyProtocolDTO getStudyProtocol() {
        try {
            return PaRegistry.getStudyProtocolService().getStudyProtocol(
                    (Ii) ServletActionContext.getRequest().getSession().getAttribute(Constants.STUDY_PROTOCOL_II));
        } catch (Exception e) {
            return null;
        }
    }

    private NCISpecificInformationWebDTO setNCISpecificDTO(StudyProtocolDTO spDTO, StudyResourcingDTO srDTO) {
        NCISpecificInformationWebDTO nciSpDTO = new NCISpecificInformationWebDTO();
        // Step2 : Assign the values to the action form
        if (spDTO != null) {
            if (spDTO.getAccrualReportingMethodCode() != null) {
                nciSpDTO.setAccrualReportingMethodCode(spDTO.getAccrualReportingMethodCode().getCode());
            }
            if (spDTO.getProgramCodeText() != null) {
                nciSpDTO.setProgramCodeText(StConverter.convertToString(spDTO.getProgramCodeText()));
            }
        }
        if (srDTO != null) {
            if (srDTO.getTypeCode() != null) {
                nciSpDTO.setSummaryFourFundingCategoryCode(srDTO.getTypeCode().getCode());
            }
            if (srDTO.getOrganizationIdentifier() != null) {
                nciSpDTO.setOrganizationIi(srDTO.getOrganizationIdentifier().getExtension());
            }
        }
        return nciSpDTO;
    }

    /**
     *
     * @return result
     */
    public String displayOrg() {
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        OrganizationDTO criteria = new OrganizationDTO();
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
        LimitOffset limit = new LimitOffset(1, 0);
        OrganizationDTO selectedOrgDTO;
        try {
            selectedOrgDTO = PoRegistry.getOrganizationEntityService().search(criteria, limit).get(0);
        } catch (PAException e) {
            addActionError(e.getMessage());
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            return ERROR;
        } catch (TooManyResultsException e) {
            addActionError(e.getMessage());
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            return ERROR;
        }
        nciSpecificInformationWebDTO.setOrganizationName(selectedOrgDTO.getName().getPart().get(0).getValue());
        nciSpecificInformationWebDTO.setOrganizationIi(orgId);
        return DISPLAY_ORG_FLD;
    }

    /**
     * @return the chosenOrg
     */
    public String getChosenOrg() {
        return chosenOrg;
    }

    /**
     * @param chosenOrg the chosenOrg to set
     */
    public void setChosenOrg(String chosenOrg) {
        this.chosenOrg = chosenOrg;
    }

    /**
     * @return String success or failure
     */
    public String lookup1() {
        return SUCCESS;
    }
}
