/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The PA Grid
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This PA Grid Software License (the License) is between NCI and You. You (or
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
 * its rights in the PA Grid Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the PA Grid Software; (ii) distribute and
 * have distributed to and by third parties the PA Grid Software and any
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
package gov.nih.nci.coppa.services.pa.grid.dto.pa;

import gov.nih.nci.coppa.services.pa.StudyProtocol;
import gov.nih.nci.coppa.services.pa.StudyProtocolType;
import gov.nih.nci.coppa.services.pa.grid.dto.BLTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.CDTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.DtoTransformException;
import gov.nih.nci.coppa.services.pa.grid.dto.IITransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.INTTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.STTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.TSTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.Transformer;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;

/**
 * Transforms StudyProtocol instances.
 * @author mshestopalov
 *
 * @param <STDP> xml
 * @param <Stdp> dto
 */
public abstract class AbstractStudyProtocolTransformer<STDP extends StudyProtocolType, Stdp extends StudyProtocolDTO>
    implements Transformer<STDP, Stdp> {


    protected AbstractStudyProtocolTransformer() { }

    /**
     * @return newly constructed xml object.
     */
    protected abstract STDP newXml();

    /**
     * @return newly constructed dto object.
     */
    protected abstract Stdp newDto();

    /**
     *
     * @param input xml
     * @return study dto
     * @throws DtoTransformException same as toDto
     */
    public Stdp transformBaseDto(StudyProtocolType input) throws DtoTransformException {
        if (input == null) {
            return null;
        }

        Stdp result = newDto();
        //ST
        result.setAcronym(STTransformer.INSTANCE.toDto(input.getAcronym()));
        result.setAmendmentNumber(STTransformer.INSTANCE.toDto(input.getAmendmentNumber()));
        result.setKeywordText(STTransformer.INSTANCE.toDto(input.getKeywordText()));
        result.setOfficialTitle(STTransformer.INSTANCE.toDto(input.getOfficialTitle()));
        result.setPhaseOtherText(STTransformer.INSTANCE.toDto(input.getPhaseOtherText()));
        result.setPrimaryPurposeOtherText(STTransformer.INSTANCE.toDto(input.getPrimaryPurposeOtherText()));
        result.setPublicDescription(STTransformer.INSTANCE.toDto(input.getPublicDescription()));
        result.setPublicTitle(STTransformer.INSTANCE.toDto(input.getPublicTitle()));
        result.setScientificDescription(STTransformer.INSTANCE.toDto(input.getScientificDescription()));
        result.setStudyProtocolType(STTransformer.INSTANCE.toDto(input.getStudyProtocolType()));
        result.setUserLastCreated(STTransformer.INSTANCE.toDto(input.getUserLastCreated()));
        //BL
        result.setAcceptHealthyVolunteersIndicator(BLTransformer.INSTANCE.toDto(input.getAcceptHealthyVolunteersIndicator()));
        result.setDataMonitoringCommitteeAppointedIndicator(BLTransformer.INSTANCE.toDto(input.getDataMonitoringCommitteeAppointedIndicator()));
        result.setDelayedpostingIndicator(BLTransformer.INSTANCE.toDto(input.getDelayedpostingIndicator()));
        result.setExpandedAccessIndicator(BLTransformer.INSTANCE.toDto(input.getExpandedAccessIndicator()));
        result.setFdaRegulatedIndicator(BLTransformer.INSTANCE.toDto(input.getFdaRegulatedIndicator()));
        result.setReviewBoardApprovalRequiredIndicator(BLTransformer.INSTANCE.toDto(input.getReviewBoardApprovalRequiredIndicator()));
        result.setSection801Indicator(BLTransformer.INSTANCE.toDto(input.getSection801Indicator()));
        //CD
        result.setAccrualReportingMethodCode(CDTransformer.INSTANCE.toDto(input.getAccrualReportingMethodCode()));
        result.setAmendmentReasonCode(CDTransformer.INSTANCE.toDto(input.getAmendmentReasonCode()));
        result.setPhaseCode(CDTransformer.INSTANCE.toDto(input.getPhaseCode()));
        result.setPrimaryCompletionDateTypeCode(CDTransformer.INSTANCE.toDto(input.getPrimaryCompletionDateTypeCode()));
        result.setPrimaryPurposeCode(CDTransformer.INSTANCE.toDto(input.getPrimaryPurposeCode()));
        result.setStartDateTypeCode(CDTransformer.INSTANCE.toDto(input.getStartDateTypeCode()));
        result.setStatusCode(CDTransformer.INSTANCE.toDto(input.getStatusCode()));
        //II
        result.setAssignedIdentifier(IITransformer.INSTANCE.toDto(input.getAssignedIdentifier()));
        result.setIdentifier(IITransformer.INSTANCE.toDto(input.getIdentifier()));
        //TS
        result.setAmendmentDate(TSTransformer.INSTANCE.toDto(input.getAmendmentDate()));
        result.setPrimaryCompletionDate(TSTransformer.INSTANCE.toDto(input.getPrimaryCompletionDate()));
        result.setRecordVerificationDate(TSTransformer.INSTANCE.toDto(input.getRecordVerificationDate()));
        result.setStartDate(TSTransformer.INSTANCE.toDto(input.getStartDate()));
        result.setStatusDate(TSTransformer.INSTANCE.toDto(input.getStatusDate()));
        //INT
        result.setMaximumTargetAccrualNumber(INTTransformer.INSTANCE.toDto(input.getMaximumTargetAccrualNumber()));
        result.setSubmissionNumber(INTTransformer.INSTANCE.toDto(input.getSubmissionNumber()));

        return result;
    }

    /**
     * @param input dto
     * @return xml
     * @throws DtoTransformException same as toXml
     */
    public STDP transformBaseXml(StudyProtocolDTO input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        STDP result = newXml();
        //ST
        result.setAcronym(STTransformer.INSTANCE.toXml(input.getAcronym()));
        result.setAmendmentNumber(STTransformer.INSTANCE.toXml(input.getAmendmentNumber()));
        result.setKeywordText(STTransformer.INSTANCE.toXml(input.getKeywordText()));
        result.setOfficialTitle(STTransformer.INSTANCE.toXml(input.getOfficialTitle()));
        result.setPhaseOtherText(STTransformer.INSTANCE.toXml(input.getPhaseOtherText()));
        result.setPrimaryPurposeOtherText(STTransformer.INSTANCE.toXml(input.getPrimaryPurposeOtherText()));
        result.setPublicDescription(STTransformer.INSTANCE.toXml(input.getPublicDescription()));
        result.setPublicTitle(STTransformer.INSTANCE.toXml(input.getPublicTitle()));
        result.setScientificDescription(STTransformer.INSTANCE.toXml(input.getScientificDescription()));
        result.setStudyProtocolType(STTransformer.INSTANCE.toXml(input.getStudyProtocolType()));
        result.setUserLastCreated(STTransformer.INSTANCE.toXml(input.getUserLastCreated()));
        //BL
        result.setAcceptHealthyVolunteersIndicator(BLTransformer.INSTANCE.toXml(input.getAcceptHealthyVolunteersIndicator()));
        result.setDataMonitoringCommitteeAppointedIndicator(BLTransformer.INSTANCE.toXml(input.getDataMonitoringCommitteeAppointedIndicator()));
        result.setDelayedpostingIndicator(BLTransformer.INSTANCE.toXml(input.getDelayedpostingIndicator()));
        result.setExpandedAccessIndicator(BLTransformer.INSTANCE.toXml(input.getExpandedAccessIndicator()));
        result.setFdaRegulatedIndicator(BLTransformer.INSTANCE.toXml(input.getFdaRegulatedIndicator()));
        result.setReviewBoardApprovalRequiredIndicator(BLTransformer.INSTANCE.toXml(input.getReviewBoardApprovalRequiredIndicator()));
        result.setSection801Indicator(BLTransformer.INSTANCE.toXml(input.getSection801Indicator()));
        //CD
        result.setAccrualReportingMethodCode(CDTransformer.INSTANCE.toXml(input.getAccrualReportingMethodCode()));
        result.setAmendmentReasonCode(CDTransformer.INSTANCE.toXml(input.getAmendmentReasonCode()));
        result.setPhaseCode(CDTransformer.INSTANCE.toXml(input.getPhaseCode()));
        result.setPrimaryCompletionDateTypeCode(CDTransformer.INSTANCE.toXml(input.getPrimaryCompletionDateTypeCode()));
        result.setPrimaryPurposeCode(CDTransformer.INSTANCE.toXml(input.getPrimaryPurposeCode()));
        result.setStartDateTypeCode(CDTransformer.INSTANCE.toXml(input.getStartDateTypeCode()));
        result.setStatusCode(CDTransformer.INSTANCE.toXml(input.getStatusCode()));
        //II
        result.setAssignedIdentifier(IITransformer.INSTANCE.toXml(input.getAssignedIdentifier()));
        result.setIdentifier(IITransformer.INSTANCE.toXml(input.getIdentifier()));
        //TS
        result.setAmendmentDate(TSTransformer.INSTANCE.toXml(input.getAmendmentDate()));
        result.setPrimaryCompletionDate(TSTransformer.INSTANCE.toXml(input.getPrimaryCompletionDate()));
        result.setRecordVerificationDate(TSTransformer.INSTANCE.toXml(input.getRecordVerificationDate()));
        result.setStartDate(TSTransformer.INSTANCE.toXml(input.getStartDate()));
        result.setStatusDate(TSTransformer.INSTANCE.toXml(input.getStatusDate()));
        //INT
        result.setMaximumTargetAccrualNumber(INTTransformer.INSTANCE.toXml(input.getMaximumTargetAccrualNumber()));
        result.setSubmissionNumber(INTTransformer.INSTANCE.toXml(input.getSubmissionNumber()));
        return result;
    }
}
