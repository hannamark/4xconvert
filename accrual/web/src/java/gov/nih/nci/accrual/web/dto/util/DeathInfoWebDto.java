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

import gov.nih.nci.accrual.web.action.AbstractAccrualAction;
import gov.nih.nci.accrual.web.enums.AutopsyPerformed;
import gov.nih.nci.accrual.web.enums.ResponseInds;
import gov.nih.nci.accrual.web.util.WebUtil;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.pa.enums.AutopsyDeathCause;
import gov.nih.nci.pa.enums.DeathCause;
import gov.nih.nci.pa.util.PAUtil;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;

/**
 * DeathInfoWebDto.
 * 
 * @author lhebel
 * @since 10/28/2009
 */
@SuppressWarnings({"PMD.CyclomaticComplexity" })
public class DeathInfoWebDto implements Serializable {

    private static final long serialVersionUID = 7839457372215390511L;
    
    private Ii id;
    private Cd cause;
    private Ts eventDate;
    private Cd autopsyInd;
    private Cd causeByAutopsy;
    private Cd autopsySite;
    private String treatmentPlanId;
    private String oldTreatmentPlanId;

    /**
     * Instantiates a new death info web dto.
     */
    public DeathInfoWebDto() {
        // default constructor
    }
    
    /**
     * Validate.
     * 
     * @param dto the dto
     * @param action the action
     */
    public static void validate(DeathInfoWebDto dto, AbstractAccrualAction action) {  
        if (dto.getEventDate() != null) {
            boolean validDate = WebUtil.checkValidDate(dto.getEventDate().getValue());
            if (!validDate) {
                action.addFieldError("deathInfo.eventDate", "Please Enter Current or Past Date.");
            }
        }
        if (dto.getAutopsyInd().getCode().equalsIgnoreCase(ResponseInds.YES.getCode())) {
            if (PAUtil.isCdNull(dto.getAutopsySite())) {
                action.addFieldError("deathInfo.autopsySite", "Please provide a Site of Disease Autopsy");
            }
            if (PAUtil.isCdNull(dto.getCauseByAutopsy())) {
                action.addFieldError("deathInfo.causeByAutopsy", 
                        "Please provide a Death Cause As Determined By Autopsy");
            }
        }
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
     * @return the cause
     */
    @FieldExpressionValidator(expression = "cause.code != null && cause.code.length() > 0",
            message = "Please provide a Death Cause")
    public Cd getCause() {
        return cause;
    }

    /**
     * @param cause the cause to set
     */
    public void setCause(Cd cause) {
        this.cause = cause;
    }

    /**
     * @return the eventDate
     */
    @FieldExpressionValidator(expression = "eventDate.value != null",
            message = "Please provide a Death Date")
    public Ts getEventDate() {
        return eventDate;
    }

    /**
     * @param eventDate the eventDate to set
     */
    public void setEventDate(Ts eventDate) {
        this.eventDate = eventDate;
    }

    /**
     * @return the autopsyInd
     */
    @FieldExpressionValidator(expression = "autopsyInd.code != null && autopsyInd.code.length() > 0",
            message = "Please provide an Autopsy Performed Indicator")
    public Cd getAutopsyInd() {
        return autopsyInd;
    }

    /**
     * @param autopsyInd the autopsyInd to set
     */
    public void setAutopsyInd(Cd autopsyInd) {
        this.autopsyInd = autopsyInd;
    }

    /**
     * @return the causeByAutopsy
     */
    public Cd getCauseByAutopsy() {
        return causeByAutopsy;
    }

    /**
     * @param causeByAutopsy the causeByAutopsy to set
     */
    public void setCauseByAutopsy(Cd causeByAutopsy) {
        this.causeByAutopsy = causeByAutopsy;
    }

    /**
     * @return the autopsySite
     */
    public Cd getAutopsySite() {
        return autopsySite;
    }

    /**
     * @param autopsySite the autopsySite to set
     */
    public void setAutopsySite(Cd autopsySite) {
        this.autopsySite = autopsySite;
    }
    
    /**
     * @return the permissible value list for the death causes
     */
    public List<DeathCause> getCauses() {
        return Arrays.asList(DeathCause.values());
    }
    
    /**
     * @return the permissible value list for the autopsy indicators
     */
    public List<AutopsyPerformed> getAutopsyInds() {
        return Arrays.asList(AutopsyPerformed.values());
    }
    
    /**
     * @return the permissible value list for the death causes by autopsy
     */
    public List<AutopsyDeathCause> getCausesByAutopsy() {
        return Arrays.asList(AutopsyDeathCause.values());
    }

    /**
     * Gets the treatment plan id.
     * @return the treatment plan id
     */
    @FieldExpressionValidator(
            expression = "treatmentPlanId != null && treatmentPlanId.length() > 0", 
            message = "Please Select TreatmentPlan")
    public String getTreatmentPlanId() {
        return treatmentPlanId;
    }

    /**
     * Sets the treatment plan id.
     * @param treatmentPlanId the new treatment plan id
     */
    public void setTreatmentPlanId(String treatmentPlanId) {
        this.treatmentPlanId = treatmentPlanId;
    }
    /**
     * Gets the old treatment plan id.
     * @return the old treatment plan id
     */
    public String getOldTreatmentPlanId() {
        return oldTreatmentPlanId;
    }

    /**
     * Sets the old treatment plan id.
     * @param oldTreatmentPlanId the new old treatment plan id
     */
    public void setOldTreatmentPlanId(String oldTreatmentPlanId) {
        this.oldTreatmentPlanId = oldTreatmentPlanId;
    }
}
