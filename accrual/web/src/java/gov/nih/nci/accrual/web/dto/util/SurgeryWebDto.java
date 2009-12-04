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

package gov.nih.nci.accrual.web.dto.util;

import gov.nih.nci.accrual.dto.PerformedProcedureDto;
import gov.nih.nci.accrual.web.util.AccrualConstants;
import gov.nih.nci.accrual.web.util.SessionEnvManager;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Ivl;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.util.CdConverter;

import java.io.Serializable;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;

/**
 * The Class SurgeryWebDto.
 * 
 * @author lhebel
 * @since 10/28/2009
 */
public class SurgeryWebDto implements Serializable {

    private static final long serialVersionUID = -1169014776715899917L;
    
    private Ii id;
    private St name;
    private Ts createDate;
    private St info;
    private Ii interventionId;

    /**
     * Instantiates a new surgery web dto.
     */
    public SurgeryWebDto() {
        // default constructor
    }
    
   
    /**
     * Instantiates a new surgery web dto.
     * 
     * @param pp the pp
     * @param dto the dto
     */
    public SurgeryWebDto(PerformedProcedureDto pp, InterventionDTO dto) {
        id = pp.getIdentifier();
        name = dto.getName();
        interventionId = dto.getIdentifier();
        createDate = pp.getActualDateRange().getLow();
        info = pp.getTextDescription();
    }

    /**
     * Gets the performed activity dto.
     * @return the performed activity dto
     */
    public PerformedProcedureDto getPerformedProcedureDto() {
        PerformedProcedureDto ppDto = new PerformedProcedureDto();
        ppDto.setIdentifier(getId());        
        if (ppDto.getActualDateRange() == null) {
            ppDto.setActualDateRange(new Ivl<Ts>());
        }
        ppDto.getActualDateRange().setLow(getCreateDate());
        ppDto.setInterventionIdentifier(getInterventionId());
        ppDto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.SURGERY));
        ppDto.setTextDescription(getInfo());
        ppDto.setStudyProtocolIdentifier(
                (Ii) SessionEnvManager.getAttr(AccrualConstants.SESSION_ATTR_STUDYPROTOCOL_II));
        ppDto.setStudySubjectIdentifier(
                (Ii) SessionEnvManager.getAttr(AccrualConstants.SESSION_ATTR_PARTICIPANT_II));
        return ppDto;
    }
    
    /**
     * @return the id
     */
    public Ii getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Ii id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    @FieldExpressionValidator(expression = "name.value != null && name.value.length() > 0", 
            message = "Please select a Surgery Name")
    public St getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(St name) {
        this.name = name;
    }

    /**
     * @return the createDate
     */
    @FieldExpressionValidator(expression = "createDate.value != null",
            message = "Please provide a Surgery Date")
    public Ts getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Ts createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the info
     */
    @FieldExpressionValidator(expression = "info.value != null && info.value.length() > 0", 
            message = "Please enter Additional Information")
    public St getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(St info) {
        this.info = info;
    }

    /**
     * Gets the intervention id.
     * @return the intervention id
     */
    public Ii getInterventionId() {
        return interventionId;
    }

    /**
     * Sets the intervention id.
     * @param interventionId the new intervention id
     */
    public void setInterventionId(Ii interventionId) {
        this.interventionId = interventionId;
    }
}
