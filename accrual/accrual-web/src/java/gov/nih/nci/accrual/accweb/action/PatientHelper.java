/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The accrual
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This accrual Software License (the License) is between NCI and You. You (or 
 * Your) shall mean a person or an entity, and all other entities that control, 
 * are controlled by, or are under common control with the entity. Control for 
 * purposes of this definition means (i) the direct or indirect power to cause 
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares, 
 * or (iii) beneficial ownership of such entity. 
 *
 * This License is granted provided that You agree to the conditions described 
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up, 
 * no-charge, irrevocable, transferable and royalty-free right and license in 
 * its rights in the accrual Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the accrual Software; (ii) distribute and 
 * have distributed to and by third parties the accrual Software and any 
 * modifications and derivative works thereof; and (iii) sublicense the 
 * foregoing rights set out in (i) and (ii) to third parties, including the 
 * right to license such rights to further third parties. For sake of clarity, 
 * and not by way of limitation, NCI shall have no right of accounting or right 
 * of payment from You or Your sub-licensees for the rights granted under this 
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the 
 * above copyright notice, this list of conditions and the disclaimer and 
 * limitation of liability of Article 6, below. Your redistributions in object 
 * code form must reproduce the above copyright notice, this list of conditions 
 * and the disclaimer of Article 6 in the documentation and/or other materials 
 * provided with the distribution, if any. 
 *
 * Your end-user documentation included with the redistribution, if any, must 
 * include the following acknowledgment: This product includes software 
 * developed by 5AM and the National Cancer Institute. If You do not include 
 * such end-user documentation, You shall include this acknowledgment in the 
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM" 
 * to endorse or promote products derived from this Software. This License does 
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the 
 * terms of this License. 
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this 
 * Software into Your proprietary programs and into any third party proprietary 
 * programs. However, if You incorporate the Software into third party 
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software 
 * into such third party proprietary programs and for informing Your 
 * sub-licensees, including without limitation Your end-users, of their 
 * obligation to secure any required permissions from such third parties before 
 * incorporating the Software into such third party proprietary software 
 * programs. In the event that You fail to obtain such permissions, You agree 
 * to indemnify NCI for any claims against NCI by such third parties, except to 
 * the extent prohibited by law, resulting from Your failure to obtain such 
 * permissions. 
 *
 * For sake of clarity, and not by way of limitation, You may add Your own 
 * copyright statement to Your modifications and to the derivative works, and 
 * You may provide additional or different license terms and conditions in Your 
 * sublicenses of modifications of the Software, or any derivative works of the 
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY, 
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO 
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR 
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.accrual.accweb.action;

import gov.nih.nci.accrual.accweb.dto.util.PatientWebDto;
import gov.nih.nci.accrual.dto.PerformedSubjectMilestoneDto;
import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.dto.util.PatientDto;
import gov.nih.nci.pa.enums.EligibleGenderCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.PatientGenderCode;
import gov.nih.nci.pa.iso.dto.ICD9DiseaseDTO;
import gov.nih.nci.pa.iso.dto.SDCDiseaseDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author moweis
 *
 */
public class PatientHelper {

    private final PatientAction action;
    private static String deletedStatusCode = FunctionalRoleStatusCode.TERMINATED.getCode();
    private static final Logger LOG = Logger.getLogger(PatientHelper.class);
    
    /**
     * Takes the action to support.
     * @param action patient action
     */
    public PatientHelper(PatientAction action) {
        this.action = action;
    }
    
    /**
     * Validate patient action.
     */
    public void validate() {
        PatientWebDto.validate(action.getPatient(), action);
        if (!action.hasActionErrors()) {
            validateNoPatientDuplicates();
            validateUnitedStatesRules();
            validateEligibilityCriteria();
            if (StringUtils.isEmpty(action.getPatient().getRegistrationDate())) {
                action.addActionError("Registration Date is required.");
            }
        }
    }

    private void validateNoPatientDuplicates() {
        List<StudySubjectDto> allSss = null;
        try {
            allSss = action.getStudySubjectSvc().getByStudyProtocol(action.getSpIi());
        } catch (PAException e) {
            LOG.error("Error in PatientAction.validateNoPatientDuplicates().", e);
            action.addActionError(e.getLocalizedMessage());
        }
        for (StudySubjectDto ss : allSss) {
            if (isDuplicatePatient(ss)) {
                action.addActionError("This Study Subject Id (" + action.getPatient().getAssignedIdentifier()
                        + ") has already been added to this study.");
            }
        }
    }

    private boolean isDuplicatePatient(StudySubjectDto ss) {
        return StConverter.convertToString(ss.getAssignedIdentifier()).equals(
                action.getPatient().getAssignedIdentifier())
                && (action.getPatient().getStudySubjectId() == null || !action.getPatient().getStudySubjectId()
                        .equals(IiConverter.convertToLong(ss.getIdentifier())))
                && !deletedStatusCode.equals(CdConverter.convertCdToString(ss.getStatusCode()));
    }

    private void validateUnitedStatesRules() {
        if (action.getUnitedStatesId().equals(action.getPatient().getCountryIdentifier())) {
            if (StringUtils.isEmpty(action.getPatient().getZip())) {
                action.addActionError("Zip code is mandatory if country is United States.");
            }
        } else {
            if (StringUtils.isNotEmpty(action.getPatient().getZip())) {
                action.addActionError("Zip code should only be entered if country is United States.");
                if (StringUtils.isNotEmpty(action.getPatient().getPaymentMethodCode())) {
                    action.addActionError("Method of payment should only be entered if country is United States.");
                }
            }
        }
    }

    private void validateEligibilityCriteria() {
        if (EligibleGenderCode.FEMALE.equals(action.getGenderCriterion())
                && action.getPatient().getGenderCode().equals(PatientGenderCode.MALE.getCode())
            || EligibleGenderCode.MALE.equals(action.getGenderCriterion())
                && action.getPatient().getGenderCode().equals(PatientGenderCode.FEMALE.getCode())) {
            action.addActionError("Gender must not be " + action.getPatient().getGenderCode()
                    + " for subjects in this study.");
        }
    }
    
    /**
     * Convert dtos to web dtos.
     * @param dtos dtos to convert
     * @param orgName organization name
     * @return converted web dtos
     * @throws PAException in case of error
     */
    public List<PatientWebDto> convertToWebDTOs(List<StudySubjectDto> dtos, String orgName) throws PAException {
        List<PatientWebDto> results = new ArrayList<PatientWebDto>();
        for (StudySubjectDto dto : dtos) {
            PatientDto pat = action.getPatientSvc().get(dto.getPatientIdentifier());
            SDCDiseaseDTO disease = getSDCDisease(dto);
            ICD9DiseaseDTO icd9Disease = null;

            if (disease == null) {
                icd9Disease = getICD9Disease(dto);
            }

            PerformedSubjectMilestoneDto psmDto = action.getRegistrationDate(dto);
            results
                .add(new PatientWebDto(pat, dto, orgName, psmDto, action.getListOfCountries(), disease, icd9Disease));
        }
        return results;
    }

    private ICD9DiseaseDTO getICD9Disease(StudySubjectDto dto) throws PAException {
        if (ISOUtil.isIiNull(dto.getDiseaseIdentifier())) {
            return null;
        }
        return action.getIcd9DiseaseSvc().get(dto.getIcd9DiseaseIdentifier());
    }

    private SDCDiseaseDTO getSDCDisease(StudySubjectDto dto) throws PAException {
        if (ISOUtil.isIiNull(dto.getDiseaseIdentifier())) {
            return null;
        }
        return action.getSDCDiseaseSvc().get(dto.getDiseaseIdentifier());
    }
}
