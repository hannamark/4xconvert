/***
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Clinical Trials Protocol Application
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
package gov.nih.nci.registry.decorator;

import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.displaytag.decorator.TableDecorator;

/**
 * tag decorator registry.
 *
 * @author Bala Nair
 *
 */
public class RegistryDisplayTagDecorator extends TableDecorator {

    /**
     * @return formated date
     */
    public String getStudyStatusDate() {
        Date studyStatusDate = ((StudyProtocolQueryDTO) this.getCurrentRowObject()).getStudyStatusDate();
        if (studyStatusDate != null) {
            return FastDateFormat.getInstance("MM/dd/yyyy").format(studyStatusDate);
        }
        return "";
    }

    /**
     * @return link
     */
    public String getAmend() {
        Long studyProtocolId = ((StudyProtocolQueryDTO) this.getCurrentRowObject()).getStudyProtocolId();
        String loginUser = ((HttpServletRequest) getPageContext().getRequest()).getRemoteUser();
        boolean isProprietaryTrial = ((StudyProtocolQueryDTO) this.getCurrentRowObject()).isProprietaryTrial();


        DocumentWorkflowStatusCode dwfs = ((StudyProtocolQueryDTO) this.getCurrentRowObject())
            .getDocumentWorkflowStatusCode();
        StudyStatusCode statusCode = ((StudyProtocolQueryDTO) this.getCurrentRowObject()).getStudyStatusCode();

        if (!isProprietaryTrial && isAmendDWFS(dwfs) && isOwner(studyProtocolId, loginUser)
                && isAmendStatus(statusCode)) {
            return "Amend";
        }
        return "";
    }

    private boolean isAmendStatus(StudyStatusCode statusCode) {
        return !(StudyStatusCode.DISAPPROVED.equals(statusCode)
                || StudyStatusCode.WITHDRAWN.equals(statusCode)
                || StudyStatusCode.COMPLETE.equals(statusCode)
                || StudyStatusCode.ADMINISTRATIVELY_COMPLETE.equals(statusCode));
    }

    private boolean isAmendDWFS(DocumentWorkflowStatusCode dwfs) {
        return DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE.equals(dwfs)
                || DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.equals(dwfs);
    }

    /**
     * @param studyProtocolId
     * @param loginUser
     * @return
     */
    private boolean isOwner(Long studyProtocolId, String loginUser) {
        boolean owner;
        try {
            owner = PaRegistry.getRegisterUserService().hasTrialAccess(loginUser, studyProtocolId);
        } catch (PAException e) {
            owner = false;
        }
        return owner;
    }

    /**
     *
     * @return formated date
     */
    public String getDocumentWorkflowStatusDate() {
        Date documentWfStatusDate = ((StudyProtocolQueryDTO) this.getCurrentRowObject())
                .getDocumentWorkflowStatusDate();
        if (documentWfStatusDate != null) {
            return FastDateFormat.getInstance("MM/dd/yyyy").format(documentWfStatusDate);
        }
        return "";
    }

    /**
     *
     * @return masked processing status
     */
    public DocumentWorkflowStatusCode getDocumentWorkflowStatusCode() {
        String loginUser = null;
        DocumentWorkflowStatusCode documentWorkflowStatusCode = ((StudyProtocolQueryDTO) this.getCurrentRowObject())
                .getDocumentWorkflowStatusCode();
        Long studyProtocolId = ((StudyProtocolQueryDTO) this.getCurrentRowObject()).getStudyProtocolId();
        loginUser = ((HttpServletRequest) getPageContext().getRequest()).getRemoteUser();
        if (loginUser != null && isOwner(studyProtocolId, loginUser)) {
            return documentWorkflowStatusCode;
        }
        return null;
    }

    /**
     *
     * @return IND/IDE Expanded Access Indicator
     */
    public String getExpandedAccessIndicator() {

        Bl indicator = ((StudyIndldeDTO) this.getCurrentRowObject())
                                    .getExpandedAccessIndicator();
        if (indicator != null && (indicator.getValue()).booleanValue()) {
            return "Yes";
        }
        return "No";
    }

    /**
     *
     * @return NIH Institute/NCI Div Program code
     */
    public String getInstProgramCode() {
        String instProgramCode = null;
        Cd holderTypeCode = ((StudyIndldeDTO) this.getCurrentRowObject()).getHolderTypeCode();

        if (holderTypeCode != null) {
            if (holderTypeCode.getCode().equals("NIH")) {
                instProgramCode = getNIHProgramCode();
            } else if (holderTypeCode.getCode().equals("NCI")) {
                instProgramCode = getNCIProgramCode();
            }
        }
        return instProgramCode;
    }

    private String getNCIProgramCode() {
        String instProgramCode = null;
        Cd nciPrgCode = ((StudyIndldeDTO) this.getCurrentRowObject()).getNciDivProgHolderCode();
        if (nciPrgCode != null) {
            instProgramCode = nciPrgCode.getCode();
        }
        return instProgramCode;
    }

    private String getNIHProgramCode() {
        String instProgramCode = null;
        Cd nihInstCode = ((StudyIndldeDTO) this.getCurrentRowObject()).getNihInstHolderCode();
        if (nihInstCode != null) {
            instProgramCode = nihInstCode.getCode();
        }
        return instProgramCode;
    }

    /**
     *
     * @return s
     */
    public String getProprietaryTypeCode() {
       String typeCode =  ((TrialDocumentWebDTO) this.getCurrentRowObject()).getTypeCode();
       if (typeCode.equalsIgnoreCase("Protocol Document")) {
           typeCode = "Proprietary Template";
       }
        return typeCode;
    }

    /**
     *
     * @return st
     */
    public String getCompletePartialSubmission() {
        Long studyProtocolId = ((StudyProtocolQueryDTO) this.getCurrentRowObject()).getStudyProtocolId();
        String loginUser = ((HttpServletRequest) getPageContext().getRequest()).getRemoteUser();
        String nciNumber =  ((StudyProtocolQueryDTO) this.getCurrentRowObject()).getNciIdentifier();
        String retComplete = "";
        if (isOwner(studyProtocolId, loginUser) && StringUtils.isEmpty(nciNumber)) {
            retComplete = "Complete";
        }
        return retComplete;
    }

    /**
     *
     * @return st
     */
    public String getDeletePartialSubmission() {
        Long studyProtocolId = ((StudyProtocolQueryDTO) this.getCurrentRowObject()).getStudyProtocolId();
        String loginUser = ((HttpServletRequest) getPageContext().getRequest()).getRemoteUser();
        String nciNumber =  ((StudyProtocolQueryDTO) this.getCurrentRowObject()).getNciIdentifier();
        String retComplete = "";
        if (isOwner(studyProtocolId, loginUser) && StringUtils.isEmpty(nciNumber)) {
            retComplete = "Delete";
        }
        return retComplete;
    }

    /**
     *
     * @return st
     */
    public String getTrialCategory() {
        String trialCat = "";

        boolean isProprietaryTrial =
            ((StudyProtocolQueryDTO) this.getCurrentRowObject()).isProprietaryTrial();
        if (isProprietaryTrial) {
            trialCat = "Proprietary Trial";
        } else {
            trialCat = "Non Proprietary Trial";
        }
        return trialCat;
    }
}
