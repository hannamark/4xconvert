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
package gov.nih.nci.coppa.pa.grid.dto.transform.pa;

import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.BLTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.INTTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IVLINTTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.TSTransformerTest;
import gov.nih.nci.coppa.services.pa.StudyProtocol;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyProtocolTransformer;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;

public class StudyProtocolTransformerTest extends AbstractTransformerTestBase<StudyProtocolTransformer, StudyProtocol, StudyProtocolDTO> {

    @Override
    public StudyProtocolDTO makeDtoSimple() {
        StudyProtocolDTO result = new StudyProtocolDTO();
        //ST
        result.setAcronym(new STTransformerTest().makeDtoSimple());
        result.setAmendmentNumber(new STTransformerTest().makeDtoSimple());
        result.setKeywordText(new STTransformerTest().makeDtoSimple());
        result.setOfficialTitle(new STTransformerTest().makeDtoSimple());
        result.setPhaseOtherText(new STTransformerTest().makeDtoSimple());
        result.setPrimaryPurposeOtherText(new STTransformerTest().makeDtoSimple());
        result.setProgramCodeText(new STTransformerTest().makeDtoSimple());
        result.setPublicDescription(new STTransformerTest().makeDtoSimple());
        result.setPublicTitle(new STTransformerTest().makeDtoSimple());
        result.setScientificDescription(new STTransformerTest().makeDtoSimple());
        result.setStudyProtocolType(new STTransformerTest().makeDtoSimple());
        result.setUserLastCreated(new STTransformerTest().makeDtoSimple());
        //BL
        result.setAcceptHealthyVolunteersIndicator(new BLTransformerTest().makeDtoSimple());
        result.setDataMonitoringCommitteeAppointedIndicator(new BLTransformerTest().makeDtoSimple());
        result.setDelayedpostingIndicator(new BLTransformerTest().makeDtoSimple());
        result.setExpandedAccessIndicator(new BLTransformerTest().makeDtoSimple());
        result.setFdaRegulatedIndicator(new BLTransformerTest().makeDtoSimple());
        result.setReviewBoardApprovalRequiredIndicator(new BLTransformerTest().makeDtoSimple());
        result.setSection801Indicator(new BLTransformerTest().makeDtoSimple());
        result.setProprietaryTrialIndicator(new BLTransformerTest().makeDtoSimple());
        //CD
        result.setAccrualReportingMethodCode(new CDTransformerTest().makeDtoSimple());
        result.setAmendmentReasonCode(new CDTransformerTest().makeDtoSimple());
        result.setPhaseCode(new CDTransformerTest().makeDtoSimple());
        result.setPrimaryCompletionDateTypeCode(new CDTransformerTest().makeDtoSimple());
        result.setPrimaryPurposeCode(new CDTransformerTest().makeDtoSimple());
        result.setStartDateTypeCode(new CDTransformerTest().makeDtoSimple());
        result.setStatusCode(new CDTransformerTest().makeDtoSimple());
        //II
        result.setAssignedIdentifier(new IITransformerTest().makeDtoSimple());
        result.setIdentifier(new IITransformerTest().makeDtoSimple());
        //TS
        result.setAmendmentDate(new TSTransformerTest().makeDtoSimple());
        result.setPrimaryCompletionDate(new TSTransformerTest().makeDtoSimple());
        result.setRecordVerificationDate(new TSTransformerTest().makeDtoSimple());
        result.setStartDate(new TSTransformerTest().makeDtoSimple());
        result.setStatusDate(new TSTransformerTest().makeDtoSimple());
        //INT
        result.setSubmissionNumber(new INTTransformerTest().makeDtoSimple());
        //IVLINT
        result.setTargetAccrualNumber(new IVLINTTransformerTest().makeDtoSimple());

        return result;
    }

    @Override
    public StudyProtocol makeXmlSimple() {
        StudyProtocol result = new StudyProtocol();
        //ST
        result.setAcronym(new STTransformerTest().makeXmlSimple());
        result.setAmendmentNumber(new STTransformerTest().makeXmlSimple());
        result.setKeywordText(new STTransformerTest().makeXmlSimple());
        result.setOfficialTitle(new STTransformerTest().makeXmlSimple());
        result.setPhaseOtherText(new STTransformerTest().makeXmlSimple());
        result.setPrimaryPurposeOtherText(new STTransformerTest().makeXmlSimple());
        result.setProgramCodeText(new STTransformerTest().makeXmlSimple());
        result.setPublicDescription(new STTransformerTest().makeXmlSimple());
        result.setPublicTitle(new STTransformerTest().makeXmlSimple());
        result.setScientificDescription(new STTransformerTest().makeXmlSimple());
        result.setStudyProtocolType(new STTransformerTest().makeXmlSimple());
        result.setUserLastCreated(new STTransformerTest().makeXmlSimple());
        //BL
        result.setAcceptHealthyVolunteersIndicator(new BLTransformerTest().makeXmlSimple());
        result.setDataMonitoringCommitteeAppointedIndicator(new BLTransformerTest().makeXmlSimple());
        result.setDelayedpostingIndicator(new BLTransformerTest().makeXmlSimple());
        result.setExpandedAccessIndicator(new BLTransformerTest().makeXmlSimple());
        result.setFdaRegulatedIndicator(new BLTransformerTest().makeXmlSimple());
        result.setReviewBoardApprovalRequiredIndicator(new BLTransformerTest().makeXmlSimple());
        result.setSection801Indicator(new BLTransformerTest().makeXmlSimple());
        result.setProprietaryTrialIndicator(new BLTransformerTest().makeXmlSimple());
        //CD
        result.setAccrualReportingMethodCode(new CDTransformerTest().makeXmlSimple());
        result.setAmendmentReasonCode(new CDTransformerTest().makeXmlSimple());
        result.setPhaseCode(new CDTransformerTest().makeXmlSimple());
        result.setPrimaryCompletionDateTypeCode(new CDTransformerTest().makeXmlSimple());
        result.setPrimaryPurposeCode(new CDTransformerTest().makeXmlSimple());
        result.setStartDateTypeCode(new CDTransformerTest().makeXmlSimple());
        result.setStatusCode(new CDTransformerTest().makeXmlSimple());
        //II
        result.setAssignedIdentifier(new IITransformerTest().makeXmlSimple());
        result.setIdentifier(new IITransformerTest().makeXmlSimple());
        //TS
        result.setAmendmentDate(new TSTransformerTest().makeXmlSimple());
        result.setPrimaryCompletionDate(new TSTransformerTest().makeXmlSimple());
        result.setRecordVerificationDate(new TSTransformerTest().makeXmlSimple());
        result.setStartDate(new TSTransformerTest().makeXmlSimple());
        result.setStatusDate(new TSTransformerTest().makeXmlSimple());
        //INT
        result.setSubmissionNumber(new INTTransformerTest().makeXmlSimple());
        //IVLINT
        result.setTargetAccrualNumber(new IVLINTTransformerTest().makeXmlSimple());

        return result;
    }

    @Override
    public void verifyDtoSimple(StudyProtocolDTO x) {
        //ST
        new STTransformerTest().verifyDtoSimple(x.getAcronym());
        new STTransformerTest().verifyDtoSimple(x.getAmendmentNumber());
        new STTransformerTest().verifyDtoSimple(x.getKeywordText());
        new STTransformerTest().verifyDtoSimple(x.getOfficialTitle());
        new STTransformerTest().verifyDtoSimple(x.getPhaseOtherText());
        new STTransformerTest().verifyDtoSimple(x.getPrimaryPurposeOtherText());
        new STTransformerTest().verifyDtoSimple(x.getProgramCodeText());
        new STTransformerTest().verifyDtoSimple(x.getPublicDescription());
        new STTransformerTest().verifyDtoSimple(x.getPublicTitle());
        new STTransformerTest().verifyDtoSimple(x.getScientificDescription());
        new STTransformerTest().verifyDtoSimple(x.getStudyProtocolType());
        new STTransformerTest().verifyDtoSimple(x.getUserLastCreated());
        //BL
        new BLTransformerTest().verifyDtoSimple(x.getAcceptHealthyVolunteersIndicator());
        new BLTransformerTest().verifyDtoSimple(x.getDataMonitoringCommitteeAppointedIndicator());
        new BLTransformerTest().verifyDtoSimple(x.getDelayedpostingIndicator());
        new BLTransformerTest().verifyDtoSimple(x.getExpandedAccessIndicator());
        new BLTransformerTest().verifyDtoSimple(x.getFdaRegulatedIndicator());
        new BLTransformerTest().verifyDtoSimple(x.getReviewBoardApprovalRequiredIndicator());
        new BLTransformerTest().verifyDtoSimple(x.getSection801Indicator());
        new BLTransformerTest().verifyDtoSimple(x.getProprietaryTrialIndicator());
        //CD
        new CDTransformerTest().verifyDtoSimple(x.getAccrualReportingMethodCode());
        new CDTransformerTest().verifyDtoSimple(x.getAmendmentReasonCode());
        new CDTransformerTest().verifyDtoSimple(x.getPhaseCode());
        new CDTransformerTest().verifyDtoSimple(x.getPrimaryCompletionDateTypeCode());
        new CDTransformerTest().verifyDtoSimple(x.getPrimaryPurposeCode());
        new CDTransformerTest().verifyDtoSimple(x.getStartDateTypeCode());
        new CDTransformerTest().verifyDtoSimple(x.getStatusCode());
        //II
        new IITransformerTest().verifyDtoSimple(x.getAssignedIdentifier());
        new IITransformerTest().verifyDtoSimple(x.getIdentifier());
        //TS
        new TSTransformerTest().verifyDtoSimple(x.getAmendmentDate());
        new TSTransformerTest().verifyDtoSimple(x.getPrimaryCompletionDate());
        new TSTransformerTest().verifyDtoSimple(x.getRecordVerificationDate());
        new TSTransformerTest().verifyDtoSimple(x.getStartDate());
        new TSTransformerTest().verifyDtoSimple(x.getStatusDate());
        //INT
        new INTTransformerTest().verifyDtoSimple(x.getSubmissionNumber());
        //IVLINT
        new IVLINTTransformerTest().verifyDtoSimple(x.getTargetAccrualNumber());
    }

    @Override
    public void verifyXmlSimple(StudyProtocol x) {
        //ST
        new STTransformerTest().verifyXmlSimple(x.getAcronym());
        new STTransformerTest().verifyXmlSimple(x.getAmendmentNumber());
        new STTransformerTest().verifyXmlSimple(x.getKeywordText());
        new STTransformerTest().verifyXmlSimple(x.getOfficialTitle());
        new STTransformerTest().verifyXmlSimple(x.getPhaseOtherText());
        new STTransformerTest().verifyXmlSimple(x.getPrimaryPurposeOtherText());
        new STTransformerTest().verifyXmlSimple(x.getProgramCodeText());
        new STTransformerTest().verifyXmlSimple(x.getPublicDescription());
        new STTransformerTest().verifyXmlSimple(x.getPublicTitle());
        new STTransformerTest().verifyXmlSimple(x.getScientificDescription());
        new STTransformerTest().verifyXmlSimple(x.getStudyProtocolType());
        new STTransformerTest().verifyXmlSimple(x.getUserLastCreated());
        //BL
        new BLTransformerTest().verifyXmlSimple(x.getAcceptHealthyVolunteersIndicator());
        new BLTransformerTest().verifyXmlSimple(x.getDataMonitoringCommitteeAppointedIndicator());
        new BLTransformerTest().verifyXmlSimple(x.getDelayedpostingIndicator());
        new BLTransformerTest().verifyXmlSimple(x.getExpandedAccessIndicator());
        new BLTransformerTest().verifyXmlSimple(x.getFdaRegulatedIndicator());
        new BLTransformerTest().verifyXmlSimple(x.getReviewBoardApprovalRequiredIndicator());
        new BLTransformerTest().verifyXmlSimple(x.getSection801Indicator());
        new BLTransformerTest().verifyXmlSimple(x.getProprietaryTrialIndicator());
        //CD
        new CDTransformerTest().verifyXmlSimple(x.getAccrualReportingMethodCode());
        new CDTransformerTest().verifyXmlSimple(x.getAmendmentReasonCode());
        new CDTransformerTest().verifyXmlSimple(x.getPhaseCode());
        new CDTransformerTest().verifyXmlSimple(x.getPrimaryCompletionDateTypeCode());
        new CDTransformerTest().verifyXmlSimple(x.getPrimaryPurposeCode());
        new CDTransformerTest().verifyXmlSimple(x.getStartDateTypeCode());
        new CDTransformerTest().verifyXmlSimple(x.getStatusCode());
        //II
        new IITransformerTest().verifyXmlSimple(x.getAssignedIdentifier());
        new IITransformerTest().verifyXmlSimple(x.getIdentifier());
        //TS
        new TSTransformerTest().verifyXmlSimple(x.getAmendmentDate());
        new TSTransformerTest().verifyXmlSimple(x.getPrimaryCompletionDate());
        new TSTransformerTest().verifyXmlSimple(x.getRecordVerificationDate());
        new TSTransformerTest().verifyXmlSimple(x.getStartDate());
        new TSTransformerTest().verifyXmlSimple(x.getStatusDate());
        //INT
        new INTTransformerTest().verifyXmlSimple(x.getSubmissionNumber());
        //IVLINT
        new IVLINTTransformerTest().verifyXmlSimple(x.getTargetAccrualNumber());

    }
}
