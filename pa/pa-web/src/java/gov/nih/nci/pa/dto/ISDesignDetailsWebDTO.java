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
package gov.nih.nci.pa.dto;


/**
 * @author Kalpana Guthikonda
 * @since 10/20/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({ "PMD.TooManyFields" })
public class ISDesignDetailsWebDTO {
    
    private String primaryPurposeCode;
    private String phaseCode;
    private String designConfigurationCode;
    private String numberOfInterventionGroups;
    private String blindingSchemaCode;
    private String allocationCode;
    private String primaryPurposeOtherText;
    private String phaseOtherText;
    private String blindingRoleCode;
    private String minimumTargetAccrualNumber;
    private String studyClassificationCode;
    
    private String name;
    private String timeFrame;
    private String primaryIndicator;
    private String safetyIndicator;
    private String id;

    private String criterionName;
    private String inclusionIndicator;
    private String operator;
    private String valueText;
    private String unit;
    private String textDescription;
    
    private String displayOrder;
    
    private String structuredType;
    private String valueIntegerMin;
    private String valueIntegerMax;
    private String cdePublicIdentifier;
    private String cdeVersionNumber;
    
    
    /**
     * @return the cdePublicIdentifier
     */
     public String getCdePublicIdentifier() {
        return cdePublicIdentifier;
     }
    /**
     * @param cdePublicIdentifier the cdePublicIdentifier to set
     */
     public void setCdePublicIdentifier(String cdePublicIdentifier) {
       this.cdePublicIdentifier = cdePublicIdentifier;
     }
    /**
     * @return the cdeVersionNumber
     */
     public String getCdeVersionNumber() {
       return cdeVersionNumber;
     }
    /**
     * @param cdeVersionNumber the cdeVersionNumber to set
     */
     public void setCdeVersionNumber(String cdeVersionNumber) {
       this.cdeVersionNumber = cdeVersionNumber;
    }
    /**
     * @return the displayOrder
     */
    public String getDisplayOrder() {
      return displayOrder;
    }
    /**
     * @param displayOrder the displayOrder to set
     */
     public void setDisplayOrder(String displayOrder) {
       this.displayOrder = displayOrder;
     }
     /**
     * @return primaryPurposeCode
     */
    public String getPrimaryPurposeCode() {
        return primaryPurposeCode;
    }
    /**
     * @param primaryPurposeCode primaryPurposeCode
     */
    public void setPrimaryPurposeCode(String primaryPurposeCode) {
        this.primaryPurposeCode = primaryPurposeCode;
    }
    /**
     * @return phaseCode
     */
    public String getPhaseCode() {
        return phaseCode;
    }
    /**
     * @param phaseCode phaseCode
     */
    public void setPhaseCode(String phaseCode) {
        this.phaseCode = phaseCode;
    }
    /**
     * @return designConfigurationCode
     */
    public String getDesignConfigurationCode() {
        return designConfigurationCode;
    }
    /**
     * @param designConfigurationCode designConfigurationCode
     */
    public void setDesignConfigurationCode(String designConfigurationCode) {
        this.designConfigurationCode = designConfigurationCode;
    }
    /**
     * @return numberOfInterventionGroups
     */
    public String getNumberOfInterventionGroups() {
        return numberOfInterventionGroups;
    }
    /**
     * @param numberOfInterventionGroups numberOfInterventionGroups
     */
    public void setNumberOfInterventionGroups(String numberOfInterventionGroups) {
        this.numberOfInterventionGroups = numberOfInterventionGroups;
    }
    /**
     * @return blindingSchemaCode
     */
    public String getBlindingSchemaCode() {
        return blindingSchemaCode;
    }
    /**
     * @param blindingSchemaCode blindingSchemaCode
     */
    public void setBlindingSchemaCode(String blindingSchemaCode) {
        this.blindingSchemaCode = blindingSchemaCode;
    }
    /**
     * @return allocationCode
     */
    public String getAllocationCode() {
        return allocationCode;
    }
    /**
     * @param allocationCode allocationCode
     */
    public void setAllocationCode(String allocationCode) {
        this.allocationCode = allocationCode;
    }
    /**
     * @return primaryPurposeOtherText
     */
    public String getPrimaryPurposeOtherText() {
        return primaryPurposeOtherText;
    }
    /**
     * @param primaryPurposeOtherText primaryPurposeOtherText
     */
    public void setPrimaryPurposeOtherText(String primaryPurposeOtherText) {
        this.primaryPurposeOtherText = primaryPurposeOtherText;
    }
    /**
     * @return phaseOtherText
     */
    public String getPhaseOtherText() {
        return phaseOtherText;
    }
    /**
     * @param phaseOtherText phaseOtherText
     */
    public void setPhaseOtherText(String phaseOtherText) {
        this.phaseOtherText = phaseOtherText;
    }
    /**
     * @return blindingRoleCode
     */
    public String getBlindingRoleCode() {
        return blindingRoleCode;
    }
    /**
     * @param blindingRoleCode blindingRoleCode
     */
    public void setBlindingRoleCode(String blindingRoleCode) {
        this.blindingRoleCode = blindingRoleCode;
    }
    
    /**
     * @return the minimumTargetAccrualNumber
     */
    public String getMinimumTargetAccrualNumber() {
        return minimumTargetAccrualNumber;
    }
    /**
     * @param minimumTargetAccrualNumber the minimumTargetAccrualNumber to set
     */
    public void setMinimumTargetAccrualNumber(String minimumTargetAccrualNumber) {
        this.minimumTargetAccrualNumber = minimumTargetAccrualNumber;
    }
    /**
     * @return studyClassificationCode
     */
    public String getStudyClassificationCode() {
        return studyClassificationCode;
    }
    /**
     * @param studyClassificationCode studyClassificationCode
     */
    public void setStudyClassificationCode(String studyClassificationCode) {
        this.studyClassificationCode = studyClassificationCode;
    }
    /**
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return timeFrame
     */
    public String getTimeFrame() {
        return timeFrame;
    }
    /**
     * @param timeFrame timeFrame
     */
    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }
    /**
     * @return primaryIndicator
     */
    public String getPrimaryIndicator() {
        return primaryIndicator;
    }
    /**
     * @param primaryIndicator primaryIndicator
     */
    public void setPrimaryIndicator(String primaryIndicator) {
        this.primaryIndicator = primaryIndicator;
    }
    /**
     * @return safetyIndicator
     */
    public String getSafetyIndicator() {
        return safetyIndicator;
    }
    /**
     * @param safetyIndicator safetyIndicator
     */
    public void setSafetyIndicator(String safetyIndicator) {
        this.safetyIndicator = safetyIndicator;
    }
    /**
     * @return id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return criterionName
     */
    public String getCriterionName() {
        return criterionName;
    }
    /**
     * @param criterionName criterionName
     */
    public void setCriterionName(String criterionName) {
        this.criterionName = criterionName;
    }
    /**
     * @return inclusionIndicator
     */
    public String getInclusionIndicator() {
        return inclusionIndicator;
    }
    /**
     * @param inclusionIndicator inclusionIndicator
     */
    public void setInclusionIndicator(String inclusionIndicator) {
        this.inclusionIndicator = inclusionIndicator;
    }
    /**
     * @return operator
     */
    public String getOperator() {
        return operator;
    }
    /**
     * @param operator operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }    
    /**
     * @return unit
     */
    public String getUnit() {
        return unit;
    }
    /**
     * @param unit unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    /**
     * @return textDescription
     */
    public String getTextDescription() {
      return textDescription;
    }
    /**
     * @param textDescription textDescription
     */
    public void setTextDescription(String textDescription) {
      this.textDescription = textDescription;
    }
    /**
     * @return the valueText
     */
    public String getValueText() {
      return valueText;
    }
    /**
     * @param valueText the valueText to set
     */
     public void setValueText(String valueText) {
       this.valueText = valueText;
     }
    /**
     * @return the structuredType
     */
     public String getStructuredType() {
       return structuredType;
     }
    /**
     * @param structuredType the structuredType to set
     */
     public void setStructuredType(String structuredType) {
       this.structuredType = structuredType;
     }
    /**
     * @return the valueIntegerMin
     */
    public String getValueIntegerMin() {
      return valueIntegerMin;
    }
    /**
     * @param valueIntegerMin the valueIntegerMin to set
     */
     public void setValueIntegerMin(String valueIntegerMin) {
       this.valueIntegerMin = valueIntegerMin;
     }
    /**
     * @return the valueIntegerMax
     */
     public String getValueIntegerMax() {
       return valueIntegerMax;
     }
    /**
     * @param valueIntegerMax the valueIntegerMax to set
     */
     public void setValueIntegerMax(String valueIntegerMax) {
      this.valueIntegerMax = valueIntegerMax;
    }
    
}
