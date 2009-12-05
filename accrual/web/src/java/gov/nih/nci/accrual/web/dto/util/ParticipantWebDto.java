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

import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.dto.util.PatientDto;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.web.action.AbstractAccrualAction;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetEnumConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;


/**
 * @author Hugh Reinhart
 * @since Sep 22, 2009
 */
public class ParticipantWebDto implements Serializable {
   
    private static final long serialVersionUID = 1L;
    // from PatientDto
    private Ii patientId;
    private Set<String> raceCode = new HashSet<String>();
    private Cd genderCode;
    private Cd ethnicCode;
    private String birthDate;
    private Ii countryIdentifier;
    private St countryName;
    private Ii poIdentifier;

    // from StudySubjectDto
    private Ii studySubjectIi;
    private Ii studyProtocolIi;
    private Ii identifier;
    private Cd paymentMethodCode;
    private St assignedIdentifier;
    private Cd statusCode;
    private Ii diseaseIdentifier;


   /**
     * Default constructor.
     */
    public ParticipantWebDto() {
        // default constructor
    }

    /**
     * Constructor for new records status is always pending.
     * @param studyProtocolIi study protocol id
     * @param unitedStatesId country id of the United States
     */
    public ParticipantWebDto(Ii studyProtocolIi, Long unitedStatesId) {
        this.studyProtocolIi = studyProtocolIi;
        countryIdentifier = IiConverter.convertToCountryIi(unitedStatesId);
        statusCode = CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING);
    }

    /**
     * Construct using iso dto's from service tier.
     * @param pIsoDto patient iso dto
     * @param ssIsoDto study subject iso dto
     * @param listOfCountries country list
     */
    @SuppressWarnings({"PMD.ExcessiveParameterList" })
    public ParticipantWebDto(PatientDto pIsoDto, StudySubjectDto ssIsoDto, List<Country> listOfCountries) {
        if (pIsoDto != null) {
            patientId = pIsoDto.getIdentifier();
            raceCode =  DSetEnumConverter.convertDSetToSet(pIsoDto.getRaceCode());
            genderCode = pIsoDto.getGenderCode();
            ethnicCode = pIsoDto.getEthnicCode();
            birthDate = AccrualUtil.tsToYearMonthString(pIsoDto.getBirthDate());
            countryIdentifier = pIsoDto.getCountryIdentifier();
            for (Country c : listOfCountries) {
                if (countryIdentifier != null && countryIdentifier.getExtension().equals(c.getId().toString())) {
                    countryName = StConverter.convertToSt(c.getName());
                }
            }
            poIdentifier = pIsoDto.getAssignedIdentifier();
        }

        if (ssIsoDto != null) {
            studySubjectIi = ssIsoDto.getIdentifier();
            studyProtocolIi = ssIsoDto.getStudyProtocolIdentifier();
            identifier = ssIsoDto.getIdentifier();
            paymentMethodCode = ssIsoDto.getPaymentMethodCode();
            assignedIdentifier = ssIsoDto.getAssignedIdentifier();
            statusCode = ssIsoDto.getStatusCode();
            diseaseIdentifier = ssIsoDto.getDiseaseIdentifier();
        }
    }

    /**
     * @return patient iso dto
     */
    public PatientDto getPatientDto() {
        PatientDto pat = new PatientDto();
        pat.setIdentifier(getPatientId());
        pat.setBirthDate(AccrualUtil.yearMonthStringToTs(getBirthDate()));
        pat.setCountryIdentifier(getCountryIdentifier());
        pat.setEthnicCode(getEthnicCode());
        pat.setGenderCode(getGenderCode());
        pat.setRaceCode(DSetEnumConverter.convertSetToDSet(getRaceCode()));
        pat.setStatusCode(CdConverter.convertToCd(StructuralRoleStatusCode.PENDING));
        pat.setStatusDateRangeLow(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        pat.setAssignedIdentifier(getPoIdentifier());
        return pat;
    }

    /**
     * @param loginName outcomes submitter account
     * @return study subject iso dto
     */
    public StudySubjectDto getStudySubjectDto(St loginName) {
        StudySubjectDto ssub = new StudySubjectDto();
        ssub.setIdentifier(getStudySubjectIi());
        ssub.setStudyProtocolIdentifier(getStudyProtocolIi());
        ssub.setPatientIdentifier(getPatientId());
        ssub.setAssignedIdentifier(getAssignedIdentifier());
        ssub.setDiseaseIdentifier(getDiseaseIdentifier());
        ssub.setPaymentMethodCode(getPaymentMethodCode());
        ssub.setStatusCode(getStatusCode());
        ssub.setStatusDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()), null));
        ssub.setOutcomesLoginName(loginName);
        return ssub;
    }
    
    /**
     * @return the patientId
     */
     public Ii getPatientId() {
       return patientId;
     }
    /**
     * @param patientId the patientId to set
     */
     public void setPatientId(Ii patientId) {
       this.patientId = patientId;
     }
    /**
     * @return the genderCode
     */
    @FieldExpressionValidator(expression = "genderCode.code != null && genderCode.code.length() > 0", 
             message = "Gender is required.")
    public Cd getGenderCode() {
      return genderCode;
    }
    /**
     * @param genderCode the genderCode to set
     */
     public void setGenderCode(Cd genderCode) {
      this.genderCode = genderCode;
     }
    /**
     * @return the ethnicCode
     */
     @FieldExpressionValidator(expression = "ethnicCode.code != null && ethnicCode.code.length() > 0", 
             message = "Ethnicity is required.")
     public Cd getEthnicCode() {
       return ethnicCode;
     }
    /**
     * @param ethnicCode the ethnicCode to set
     */
     public void setEthnicCode(Cd ethnicCode) {
        this.ethnicCode = ethnicCode;
     }
    /**
     * @return the birthDate
     */
     @FieldExpressionValidator(expression = "birthDate != null && birthDate.length() > 0 ", 
             message = "Birth date is required.")
     @RegexFieldValidator(expression = "(0[1-9]|1[012])[/]((19|20)\\d\\d)" ,
            message = "Birth date should be in the format MM/YYYY .\n")        
     public String getBirthDate() {
       return birthDate;
     }
    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(String birthDate) {
      this.birthDate = birthDate;
    }
    /**
     * @return the countryIdentifier
     */
    @FieldExpressionValidator(
            expression = "countryIdentifier.extension != null && countryIdentifier.extension.length() > 0", 
            message = "Country is required.")
    public Ii getCountryIdentifier() {
     return countryIdentifier;
    }
   /**
    * @param countryIdentifier the countryIdentifier to set
    */
    public void setCountryIdentifier(Ii countryIdentifier) {
      this.countryIdentifier = countryIdentifier;
    }
    /**
     * @return the countryName
     */
     public St getCountryName() {
      return countryName;
     }
   /**
    * @param countryName the countryName to set
    */
    public void setCountryName(St countryName) {
     this.countryName = countryName;
  }
 /**
  * @return the poIdentifier
  */
  public Ii getPoIdentifier() {
    return poIdentifier;
  }
  /**
   * @param poIdentifier the poIdentifier to set
   */
   public void setPoIdentifier(Ii poIdentifier) {
     this.poIdentifier = poIdentifier;
   }
  /**
   * @return the studySubjectIi
   */
  public Ii getStudySubjectIi() {
     return studySubjectIi;
  }
  /**
   * @param studySubjectIi the studySubjectIi to set
   */
   public void setStudySubjectIi(Ii studySubjectIi) {
     this.studySubjectIi = studySubjectIi;
   }
  /**
   * @return the studyProtocolIi
   */
   public Ii getStudyProtocolIi() {
     return studyProtocolIi;
   }
  /**
   * @param studyProtocolIi the studyProtocolIi to set
   */
   public void setStudyProtocolIi(Ii studyProtocolIi) {
     this.studyProtocolIi = studyProtocolIi;
   }
  /**
   * @return the identifier
   */
   public Ii getIdentifier() {
     return identifier;
   }
   /**
    * @param identifier the identifier to set
    */
    public void setIdentifier(Ii identifier) {
      this.identifier = identifier;
    }
    /**
     * @return the paymentMethodCode
     */
    public Cd getPaymentMethodCode() {
       return paymentMethodCode;
    }
    /**
     * @param paymentMethodCode the paymentMethodCode to set
     */
    public void setPaymentMethodCode(Cd paymentMethodCode) {
      this.paymentMethodCode = paymentMethodCode;
    }
    /**
     * @return the assignedIdentifier
     */
    @FieldExpressionValidator(expression = "assignedIdentifier.value != null && assignedIdentifier.value.length() > 0", 
            message = "Study Subject ID is required.")
    public St getAssignedIdentifier() {
      return assignedIdentifier;
    }
    /**
     * @param assignedIdentifier the assignedIdentifier to set
     */
     public void setAssignedIdentifier(St assignedIdentifier) {
       this.assignedIdentifier = assignedIdentifier;
    }
    /**
     * @return the statusCode
     */
     public Cd getStatusCode() {
       return statusCode;
     }
    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(Cd statusCode) {
        this.statusCode = statusCode;
    }
    /**
     * @return the diseaseIdentifier
     */
    public Ii getDiseaseIdentifier() {
      return diseaseIdentifier;
    }
    /**
    * @param diseaseIdentifier the diseaseIdentifier to set
    */
   public void setDiseaseIdentifier(Ii diseaseIdentifier) {
      this.diseaseIdentifier = diseaseIdentifier;
   }
   /**
    * @return the raceCode
    */ 
   public Set<String> getRaceCode() {
     return raceCode;
   }
   /**
    * @param raceCode the raceCode to set
    */
    public void setRaceCode(Set<String> raceCode) {
      this.raceCode = raceCode;
    }   
 
    /**
     * Validate.
     * 
     * @param dto the dto
     * @param action the action
     */
    public static void validate(ParticipantWebDto dto, AbstractAccrualAction action) {
        if (dto.getBirthDate() != null) {
         Date birthDateEntered = new Date(AccrualUtil.yearMonthStringToTimestamp(dto.getBirthDate()).getTime());
         Date currentDate = new Date();
         if (currentDate.getTime() < birthDateEntered.getTime()) {
           action.addFieldError("participant.birthDate", "BirthDate cannot be in future.");
        }        
      } 
    } 
}
