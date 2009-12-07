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

import gov.nih.nci.accrual.dto.PerformedSubstanceAdministrationDto;
import gov.nih.nci.accrual.web.action.AbstractAccrualAction;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Pq;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.util.PAUtil;

import java.io.Serializable;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;

/**
 * The Class DrugBiologicsWebDto.
 * 
 * @author lhebel
 * @since 10/28/2009
 */
@SuppressWarnings({"PMD.CyclomaticComplexity" , "PMD.TooManyFields", 
    "PMD.NPathComplexity", "PMD.ExcessiveMethodLength" })
public class DrugBiologicsWebDto implements Serializable {

    private static final long serialVersionUID = -1395879742701837870L;
    private static final String NUMERICMESSAGE = "Please Enter Numeric Value";

    private Ii id; 
    private St drugName;
    private Pq dose; 
    private Cd doseRoute;  
    private Cd doseFreq;    
    private Pq doseDur;  
    private Pq height; 
    private Pq weight;   
    private Pq bsa;    
    private Pq doseTotal;    
    private Cd doseModType;
    private Ii interventionId;
    private Ii doseFreqId;

    /**
     * Instantiates a new drug biologics web dto.
     */
    public DrugBiologicsWebDto() {
        // default constructor
    }
    
    /**
     * Instantiates a new drug biologics web dto.
     * 
     * @param psaDto the psa dto
     * @param webDto the web dto
     */
    public DrugBiologicsWebDto(PerformedSubstanceAdministrationDto psaDto,
            DrugBiologicsWebDto webDto) {
        id = psaDto.getIdentifier();
        drugName = webDto.getDrugName();
        dose = psaDto.getDose();
        doseRoute = psaDto.getRouteOfAdministrationCode();
        //doseFreq = psaDto.getDoseFrequencyCode();    
        doseFreq = webDto.getDoseFreq();
        doseFreqId = webDto.getDoseFreqId();
        doseDur = psaDto.getDoseDuration();
        height = webDto.getHeight();
        weight = webDto.getWeight();
        bsa = webDto.getBsa();
        doseTotal = psaDto.getDoseTotal();
        doseModType = psaDto.getDoseModificationType();
        interventionId = webDto.getInterventionId();        
    }

    /**
     * Validate.
     * 
     * @param dto the dto
     * @param action the action
     */
    public static void validate(DrugBiologicsWebDto dto, AbstractAccrualAction action) {
        if (dto.getDose() == null || dto.getDose().getValue() == null) {
            action.addFieldError("drugBiologic.dose.value", "Please enter Dose Value.");
        }
        if (dto.getDose().getValue() != null && !PAUtil.isNumber(dto.getDose().getValue().toString())) {
            action.addFieldError("drugBiologic.dose.value", NUMERICMESSAGE);
        }
        if (dto.getDose() == null || dto.getDose().getUnit().equals("")) {
            action.addFieldError("drugBiologic.dose.unit", "Please select Dose UOM.");
        }
        if (dto.getDoseDur().getValue() == null &&  !(dto.getDoseDur().getUnit().equals(""))) {
            action.addFieldError("drugBiologic.doseDur.value", "Please enter Duration Value.");
        }
        if (dto.getDoseDur() != null && dto.getDoseDur().getValue() != null 
                && !PAUtil.isNumber(dto.getDoseDur().getValue().toString())) {
            action.addFieldError("drugBiologic.doseDur.value", NUMERICMESSAGE);
        }
        if (dto.getDoseDur().getUnit().equals("") && dto.getDoseDur().getValue() != null) {
            action.addFieldError("drugBiologic.doseDur.unit", "Please select Duration UOM.");
        }
        if (dto.getHeight() == null || dto.getHeight().getValue() == null) {
            action.addFieldError("drugBiologic.height.value", "Please enter Height Value.");
        }
        if (dto.getHeight().getValue() != null && !PAUtil.isNumber(dto.getHeight().getValue().toString())) {
            action.addFieldError("drugBiologic.height.value", NUMERICMESSAGE);
        }
        if (dto.getHeight() == null || dto.getHeight().getUnit().equals("")) {
            action.addFieldError("drugBiologic.height.unit", "Please select Height UOM.");
        }
        if (dto.getWeight() == null || dto.getWeight().getValue() == null) {
            action.addFieldError("drugBiologic.weight.value", "Please enter Weight Value.");
        }
        if (dto.getWeight().getValue() != null && !PAUtil.isNumber(dto.getWeight().getValue().toString())) {
            action.addFieldError("drugBiologic.weight.value", NUMERICMESSAGE);
        }
        if (dto.getWeight() == null || dto.getWeight().getUnit().equals("")) {
            action.addFieldError("drugBiologic.weight.unit", "Please select Weight UOM.");  
        }
        if (dto.getBsa() != null && dto.getBsa().getValue() != null 
                && !PAUtil.isNumber(dto.getBsa().getValue().toString())) {
            action.addFieldError("drugBiologic.bsa.value", NUMERICMESSAGE);  
        }
        if (dto.getDoseTotal() == null || dto.getDoseTotal().getValue() == null) { 
            action.addFieldError("drugBiologic.doseTotal.value", "Please enter Dose Total Value.");
        }
        if (dto.getDoseTotal().getValue() != null && !PAUtil.isNumber(dto.getDoseTotal().getValue().toString())) {
            action.addFieldError("drugBiologic.doseTotal.value", NUMERICMESSAGE);
        }
        if (dto.getDoseTotal() == null || dto.getDoseTotal().getUnit().equals("")) {
            action.addFieldError("drugBiologic.doseTotal.unit", "Please select Dose Total UOM.");
        }
    }

    /**
     * Gets the id.
     * @return the id
     */
    public Ii getId() {
        return id;
    }

    /**
     * Sets the id.
     * @param id the new id
     */
    public void setId(Ii id) {
        this.id = id;
    }

    /**
     * Gets the drug name.
     * @return the drug name
     */
    @FieldExpressionValidator(expression = "drugName.value != null && drugName.value.length() > 0", 
            message = "Please select a Drug Name")
    public St getDrugName() {
        return drugName;
    }

    /**
     * Sets the drug name.
     * @param drugName the new drug name
     */
    public void setDrugName(St drugName) {
        this.drugName = drugName;
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
     * Gets the dose route.
     * @return the dose route
     */
    @FieldExpressionValidator(expression = "doseRoute.code != null && doseRoute.code.length() > 0", 
            message = "Please select a Route of Administration")
    public Cd getDoseRoute() {
        return doseRoute;
    }

    /**
     * Sets the dose route.
     * @param doseRoute the new dose route
     */
    public void setDoseRoute(Cd doseRoute) {
        this.doseRoute = doseRoute;
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

    /**
     * Gets the dose dur.
     * @return the dose dur
     */
    public Pq getDoseDur() {
        return doseDur;
    }

    /**
     * Sets the dose dur.
     * @param doseDur the new dose dur
     */
    public void setDoseDur(Pq doseDur) {
        this.doseDur = doseDur;
    }

    /**
     * Gets the height.
     * @return the height
     */
    public Pq getHeight() {
        return height;
    }

    /**
     * Sets the height.
     * @param height the new height
     */
    public void setHeight(Pq height) {
        this.height = height;
    }

    /**
     * Gets the weight.
     * @return the weight
     */
    public Pq getWeight() {
        return weight;
    }

    /**
     * Sets the weight.
     * @param weight the new weight
     */
    public void setWeight(Pq weight) {
        this.weight = weight;
    }

    /**
     * Gets the bsa.
     * @return the bsa
     */
    public Pq getBsa() {
        return bsa;
    }

    /**
     * Sets the bsa.
     * @param bsa the new bsa
     */
    public void setBsa(Pq bsa) {
        this.bsa = bsa;
    }

    /**
     * Gets the dose total.
     * @return the dose total
     */
    public Pq getDoseTotal() {
        return doseTotal;
    }

    /**
     * Sets the dose total.
     * @param doseTotal the new dose total
     */
    public void setDoseTotal(Pq doseTotal) {
        this.doseTotal = doseTotal;
    }

    /**
     * Gets the dose mod type.
     * @return the dose mod type
     */
    public Cd getDoseModType() {
        return doseModType;
    }

    /**
     * Sets the dose mod type.
     * @param doseModType the new dose mod type
     */
    public void setDoseModType(Cd doseModType) {
        this.doseModType = doseModType;
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

    /**
     * Gets the dose freq id.
     * @return the dose freq id
     */
    public Ii getDoseFreqId() {
        return doseFreqId;
    }

    /**
     * Sets the dose freq id.
     * @param doseFreqId the new dose freq id
     */
    public void setDoseFreqId(Ii doseFreqId) {
        this.doseFreqId = doseFreqId;
    }
}
