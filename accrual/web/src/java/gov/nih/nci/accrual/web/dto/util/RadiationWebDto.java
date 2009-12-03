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

import gov.nih.nci.accrual.dto.PerformedRadiationAdministrationDto;
import gov.nih.nci.accrual.web.action.AbstractAccrualAction;
import gov.nih.nci.accrual.web.util.AccrualConstants;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Ivl;
import gov.nih.nci.coppa.iso.Pq;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;

import java.io.Serializable;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;

/**
 * The Class RadiationWebDto.
 * 
 * @author lhebel
 * @since 10/28/2009
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
public class RadiationWebDto implements Serializable {

    private static final long serialVersionUID = -3658357689383961868L;
    
    private Ii id;
    private Cd type;
    private Ts radDate;
    private Pq totalDose;
    private Pq duration;
    private Cd machineType;
    private Pq dose; 
    private Cd doseFreq;    

    /**
     * Instantiates a new radiation web dto.
     */
    public RadiationWebDto() {
        // default constructor
    } 
    
    /**
     * Instantiates a new radiation web dto.
     * @param pra the pra
     */
    public RadiationWebDto(PerformedRadiationAdministrationDto pra) {
        id = pra.getIdentifier();
        type = CdConverter.convertStringToCd(pra.getName().getValue());
        radDate = pra.getActualDateRange().getLow();
        totalDose = pra.getDoseTotal();
        duration = pra.getDoseDuration();
        machineType = pra.getMachineTypeCode();
        dose = pra.getDose();
        doseFreq = pra.getDoseFrequencyCode();
    }
    
    /**
     * Gets the performed radiation administration dto.
     * @return the performed radiation administration dto
     */
    public PerformedRadiationAdministrationDto getPerformedRadiationAdministrationDto() {
        PerformedRadiationAdministrationDto praDto = new PerformedRadiationAdministrationDto();
        praDto.setIdentifier(getId());        
        if (praDto.getActualDateRange() == null) {
            praDto.setActualDateRange(new Ivl<Ts>());
        }
        praDto.getActualDateRange().setLow(getRadDate());
        praDto.setName(StConverter.convertToSt(getType().getCode()));
        praDto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.RADIATION));
        praDto.setDoseDuration(getDuration());
        praDto.setDoseTotal(getTotalDose());
        praDto.setMachineTypeCode(getMachineType());
        praDto.setDose(getDose());
        praDto.setDoseFrequencyCode(getDoseFreq());
        praDto.setStudyProtocolIdentifier((Ii) ServletActionContext.getRequest().getSession().getAttribute(
                AccrualConstants.SESSION_ATTR_STUDYPROTOCOL_II));
        praDto.setStudySubjectIdentifier((Ii) ServletActionContext.getRequest().getSession().getAttribute(
                AccrualConstants.SESSION_ATTR_PARTICIPANT_II));
        return praDto;
    }
    
    /**
     * Validate.
     * 
     * @param dto the dto
     * @param action the action
     */
    public static void validate(RadiationWebDto dto, AbstractAccrualAction action) {       
        if (dto.getTotalDose() == null || dto.getTotalDose().getValue() == null) {
            action.addFieldError("radiation.totalDose.value", "Please enter Total Dose Value.");
        }
        if (dto.getTotalDose() == null || dto.getTotalDose().getUnit().equals("")) {
            action.addFieldError("radiation.totalDose.unit", "Please select Total Dose UOM.");
        }
        if (dto.getDuration() == null || dto.getDuration().getValue() == null) {
            action.addFieldError("radiation.duration.value", "Please enter Duration Value.");
        }
        if (dto.getDuration() == null || dto.getDuration().getUnit().equals("")) {
            action.addFieldError("radiation.duration.unit", "Please select Duration UOM.");
        }
        if (dto.getDose() == null || dto.getDose().getValue() == null) {
            action.addFieldError("radiation.dose.value", "Please enter Dose Value.");
        }
        if (dto.getDose() == null || dto.getDose().getUnit().equals("")) {
            action.addFieldError("radiation.dose.unit", "Please select Dose UOM.");
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
     * @return the type
     */
    @FieldExpressionValidator(expression = "type.code != null && type.code.length() > 0", 
            message = "Please select a Radiation Type")
    public Cd getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Cd type) {
        this.type = type;
    }

    /**
     * @return radDate
     */
    @FieldExpressionValidator(expression = "radDate.value != null",
            message = "Please provide a Radiation Date.")
    public Ts getRadDate() {
        return radDate;
    }

    /**
     * @param radDate the radDate to set
     */
    public void setRadDate(Ts radDate) {
        this.radDate = radDate;
    }

    /**
     * @return totalDose
     */
    public Pq getTotalDose() {
        return totalDose;
    }

    /**
     * @param totalDose the totalDose to set
     */
    public void setTotalDose(Pq totalDose) {
        this.totalDose = totalDose;
    }

    /**
     * @return duration
     */
    public Pq getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(Pq duration) {
        this.duration = duration;
    }

    /**
     * @return the machineType
     */
    @FieldExpressionValidator(expression = "machineType.code != null && machineType.code.length() > 0", 
            message = "Please select a Radiation Machine Type")
    public Cd getMachineType() {
        return machineType;
    }

    /**
     * @param machineType the machineType to set
     */
    public void setMachineType(Cd machineType) {
        this.machineType = machineType;
    }

    /**
     * Gets the dose.
     * @return the dose
     */
    public Pq getDose() {
        return dose;
    }

    /**
     * Sets the dose.
     * @param dose the new dose
     */
    public void setDose(Pq dose) {
        this.dose = dose;
    }

    /**
     * Gets the dose freq.
     * @return the dose freq
     */
    @FieldExpressionValidator(expression = "doseFreq.code != null && doseFreq.code.length() > 0", 
            message = "Please select a Frequency")
    public Cd getDoseFreq() {
        return doseFreq;
    }

    /**
     * Sets the dose freq.
     * @param doseFreq the new dose freq
     */
    public void setDoseFreq(Cd doseFreq) {
        this.doseFreq = doseFreq;
    }
}
