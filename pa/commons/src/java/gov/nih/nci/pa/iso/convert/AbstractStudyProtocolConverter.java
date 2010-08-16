/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
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
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
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
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.AbstractStudyProtocol;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.ObservationalStudyProtocol;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.iso.dto.AbstractStudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.PAException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Convert AbstractStudyProtocol domain to DTO, and vice versa.
 *
 * @author moweis
 *
 */
public class AbstractStudyProtocolConverter {
    private static final Logger LOG = Logger.getLogger(AbstractStudyProtocolConverter.class);
    private static CSMUserUtil csmUserUtil = null;

    /**
     * @return the csmUserUtil
     */
    public static CSMUserUtil getCsmUserUtil() {
        return csmUserUtil;
    }

    /**
     * @param csmUserUtil the csmUserUtil to set
     */
    public static void setCsmUserUtil(CSMUserUtil csmUserUtil) {
        AbstractStudyProtocolConverter.csmUserUtil = csmUserUtil;
    }

    /**
     *
     * @param abstractStudyProtocol domain
     * @param abstractStudyProtocolDTO DTO
     * @return spDTO
     */
    public static AbstractStudyProtocolDTO convertFromDomainToDTO(AbstractStudyProtocol abstractStudyProtocol,
            AbstractStudyProtocolDTO abstractStudyProtocolDTO) {
        abstractStudyProtocolDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter
                .convertToBl(abstractStudyProtocol.getDataMonitoringCommitteeAppointedIndicator()));
        abstractStudyProtocolDTO.setDelayedpostingIndicator(BlConverter.convertToBl(abstractStudyProtocol
                .getDelayedpostingIndicator()));

        abstractStudyProtocolDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(abstractStudyProtocol
                .getFdaRegulatedIndicator()));
        abstractStudyProtocolDTO.setOfficialTitle(StConverter.convertToSt(abstractStudyProtocol.getOfficialTitle()));
        abstractStudyProtocolDTO.setPhaseCode(CdConverter.convertToCd(abstractStudyProtocol.getPhaseCode()));
        abstractStudyProtocolDTO.setPhaseAdditionalQualifierCode(CdConverter.convertToCd(abstractStudyProtocol
                .getPhaseAdditionalQualifierCode()));
        abstractStudyProtocolDTO.setPrimaryCompletionDate(TsConverter.convertToTs(abstractStudyProtocol
                .getPrimaryCompletionDate()));
        abstractStudyProtocolDTO.setPrimaryCompletionDateTypeCode(CdConverter.convertToCd(abstractStudyProtocol
                .getPrimaryCompletionDateTypeCode()));
        abstractStudyProtocolDTO.setPrimaryPurposeCode(CdConverter.convertToCd(abstractStudyProtocol
                .getPrimaryPurposeCode()));
        abstractStudyProtocolDTO.setPrimaryPurposeAdditionalQualifierCode(CdConverter.convertToCd(
                abstractStudyProtocol.getPrimaryPurposeAdditionalQualifierCode()));
        abstractStudyProtocolDTO.setSection801Indicator(BlConverter.convertToBl(abstractStudyProtocol
                .getSection801Indicator()));
        abstractStudyProtocolDTO.setStartDate(TsConverter.convertToTs(abstractStudyProtocol.getStartDate()));
        abstractStudyProtocolDTO.setStartDateTypeCode(CdConverter.convertToCd(abstractStudyProtocol
                .getStartDateTypeCode()));
        if (abstractStudyProtocol instanceof ObservationalStudyProtocol) {
            abstractStudyProtocolDTO.setStudyProtocolType(StConverter.convertToSt("ObservationalStudyProtocol"));
        } else if (abstractStudyProtocol instanceof InterventionalStudyProtocol) {
            abstractStudyProtocolDTO.setStudyProtocolType(StConverter.convertToSt("InterventionalStudyProtocol"));
        } else {
            abstractStudyProtocolDTO.setStudyProtocolType(StConverter.convertToSt(abstractStudyProtocol.getClass()
                    .getName()));
        }
        if (abstractStudyProtocol.getUserLastCreated() != null) {
            abstractStudyProtocolDTO.setUserLastCreated(StConverter.convertToSt(abstractStudyProtocol
                    .getUserLastCreated().getLoginName()));
        }
        abstractStudyProtocolDTO
                .setProgramCodeText(StConverter.convertToSt(abstractStudyProtocol.getProgramCodeText()));
        abstractStudyProtocolDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(abstractStudyProtocol
                .getProprietaryTrialIndicator()));
        abstractStudyProtocolDTO.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(abstractStudyProtocol
                .getCtgovXmlRequiredIndicator()));
        return abstractStudyProtocolDTO;
    }

    /**
     *
     * @param abstractStudyProtocolDTO DTO
     * @param abstractStudyProtocol domain
     * @return domain
     */
    public static AbstractStudyProtocol convertFromDTOToDomain(AbstractStudyProtocolDTO abstractStudyProtocolDTO,
            AbstractStudyProtocol abstractStudyProtocol) {

        abstractStudyProtocol.setDataMonitoringCommitteeAppointedIndicator(BlConverter
                .convertToBoolean(abstractStudyProtocolDTO.getDataMonitoringCommitteeAppointedIndicator()));
        abstractStudyProtocol.setDelayedpostingIndicator(BlConverter.convertToBoolean(abstractStudyProtocolDTO
                .getDelayedpostingIndicator()));
        abstractStudyProtocol.setFdaRegulatedIndicator(BlConverter.convertToBoolean(abstractStudyProtocolDTO
                .getFdaRegulatedIndicator()));
        abstractStudyProtocol
                .setOfficialTitle(StConverter.convertToString(abstractStudyProtocolDTO.getOfficialTitle()));
        if (abstractStudyProtocolDTO.getPhaseCode() != null) {
            abstractStudyProtocol.setPhaseCode(PhaseCode.getByCode(abstractStudyProtocolDTO.getPhaseCode().getCode()));
        }
        if (abstractStudyProtocolDTO.getPhaseAdditionalQualifierCode() != null) {
            abstractStudyProtocol.setPhaseAdditionalQualifierCode(PhaseAdditionalQualifierCode
                    .getByCode(abstractStudyProtocolDTO.getPhaseAdditionalQualifierCode().getCode()));
        }
        setPrimaryFields(abstractStudyProtocolDTO, abstractStudyProtocol);

        abstractStudyProtocol.setSection801Indicator(BlConverter.convertToBoolean(abstractStudyProtocolDTO
                .getSection801Indicator()));

        if (abstractStudyProtocolDTO.getStartDate() != null) {
            abstractStudyProtocol.setStartDate(TsConverter.convertToTimestamp(abstractStudyProtocolDTO.getStartDate()));
        }
        if (abstractStudyProtocolDTO.getStartDateTypeCode() != null) {
            abstractStudyProtocol.setStartDateTypeCode(ActualAnticipatedTypeCode.getByCode(abstractStudyProtocolDTO
                    .getStartDateTypeCode().getCode()));

        }
        if (abstractStudyProtocolDTO.getProgramCodeText() != null) {
            abstractStudyProtocol.setProgramCodeText(StConverter.convertToString(abstractStudyProtocolDTO
                    .getProgramCodeText()));
        }
        abstractStudyProtocol.setProprietaryTrialIndicator(BlConverter.convertToBoolean(abstractStudyProtocolDTO
                .getProprietaryTrialIndicator()));
        setUserLastCreated(abstractStudyProtocolDTO, abstractStudyProtocol);
        abstractStudyProtocol.setCtgovXmlRequiredIndicator(BlConverter.convertToBoolean(abstractStudyProtocolDTO
                .getCtgovXmlRequiredIndicator()));
        return abstractStudyProtocol;
    }

    private static void setPrimaryFields(AbstractStudyProtocolDTO abstractStudyProtocolDTO,
            AbstractStudyProtocol abstractStudyProtocol) {
        if (abstractStudyProtocolDTO.getPrimaryCompletionDate() != null) {
            abstractStudyProtocol.setPrimaryCompletionDate(TsConverter.convertToTimestamp(abstractStudyProtocolDTO
                    .getPrimaryCompletionDate()));
        }
        if (abstractStudyProtocolDTO.getPrimaryCompletionDateTypeCode() != null) {
            abstractStudyProtocol.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode
                    .getByCode(abstractStudyProtocolDTO.getPrimaryCompletionDateTypeCode().getCode()));
        }
        if (abstractStudyProtocolDTO.getPrimaryPurposeCode() != null) {
            abstractStudyProtocol.setPrimaryPurposeCode(PrimaryPurposeCode.getByCode(abstractStudyProtocolDTO
                    .getPrimaryPurposeCode().getCode()));
        }
        if (abstractStudyProtocolDTO.getPrimaryPurposeAdditionalQualifierCode() != null) {
            abstractStudyProtocol.setPrimaryPurposeAdditionalQualifierCode(PrimaryPurposeAdditionalQualifierCode
                    .getByCode(abstractStudyProtocolDTO.getPrimaryPurposeAdditionalQualifierCode().getCode()));
        }
    }

    private static void setUserLastCreated(AbstractStudyProtocolDTO abstractStudyProtocolDTO,
            AbstractStudyProtocol abstractStudyProtocol) {
        // this for change of ownership
        String isoStUserLastCreated = StConverter.convertToString(abstractStudyProtocolDTO.getUserLastCreated());
        if (StringUtils.isNotEmpty(isoStUserLastCreated) && abstractStudyProtocol.getUserLastCreated() != null
                && StringUtils.isNotEmpty(abstractStudyProtocol.getUserLastCreated().getLoginName())
                && !isoStUserLastCreated.equals(abstractStudyProtocol.getUserLastCreated().getLoginName())) {
            try {
                abstractStudyProtocol.setUserLastCreated(getCsmUserUtil().getCSMUser(isoStUserLastCreated));
            } catch (PAException e) {
                LOG.info("Exception in setting userLastCreated for Study Protocol: "
                        + abstractStudyProtocolDTO.getIdentifier() + ", for username: " + isoStUserLastCreated, e);
            }
        }
    }
}
