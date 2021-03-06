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

import gov.nih.nci.coppa.services.pa.StudyResourcing;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyResourcingTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.iso21090.grid.dto.transform.iso.BLTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.REALTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.STTransformerTest;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;

public class StudyResourcingTransformerTest extends
        AbstractTransformerTestBase<StudyResourcingTransformer, StudyResourcing, StudyResourcingDTO> {

    @Override
    public StudyResourcingDTO makeDtoSimple() {
        StudyResourcingDTO result = new StudyResourcingDTO();
        result.setFundingMechanismCode(new CDTransformerTest().makeDtoSimple());
        result.setIdentifier(new IITransformerTest().makeDtoSimple());
        result.setInactiveCommentText(new STTransformerTest().makeDtoSimple());
        result.setNciDivisionProgramCode(new CDTransformerTest().makeDtoSimple());
        result.setNihInstitutionCode(new CDTransformerTest().makeDtoSimple());
        result.setOrganizationIdentifier(new IITransformerTest().makeDtoSimple());
        result.setSerialNumber(new STTransformerTest().makeDtoSimple());
        result.setStudyProtocolIdentifier(new IITransformerTest().makeDtoSimple());
        result.setSummary4ReportedResourceIndicator(new BLTransformerTest().makeDtoSimple());
        result.setTypeCode(new CDTransformerTest().makeDtoSimple());
        result.setFundingPercent(new REALTransformerTest().makeDtoSimple());
        return result;
    }

    @Override
    public StudyResourcing makeXmlSimple() {
        StudyResourcing result = new StudyResourcing();
        result.setFundingMechanismCode(new CDTransformerTest().makeXmlSimple());
        result.setIdentifier(new IITransformerTest().makeXmlSimple());
        result.setInactiveCommentText(new STTransformerTest().makeXmlSimple());
        result.setNciDivisionProgramCode(new CDTransformerTest().makeXmlSimple());
        result.setNihInstitutionCode(new CDTransformerTest().makeXmlSimple());
        result.setOrganizationIdentifier(new IITransformerTest().makeXmlSimple());
        result.setSerialNumber(new STTransformerTest().makeXmlSimple());
        result.setStudyProtocolIdentifier(new IITransformerTest().makeXmlSimple());
        result.setSummary4ReportedResourceIndicator(new BLTransformerTest().makeXmlSimple());
        result.setTypeCode(new CDTransformerTest().makeXmlSimple());
        result.setFundingPercent(new REALTransformerTest().makeXmlSimple());
        return result;
    }

    @Override
    public void verifyDtoSimple(StudyResourcingDTO x) {
        new CDTransformerTest().verifyDtoSimple(x.getFundingMechanismCode());
        new IITransformerTest().verifyDtoSimple(x.getIdentifier());
        new STTransformerTest().verifyDtoSimple(x.getInactiveCommentText());
        new CDTransformerTest().verifyDtoSimple(x.getNciDivisionProgramCode());
        new CDTransformerTest().verifyDtoSimple(x.getNihInstitutionCode());
        new IITransformerTest().verifyDtoSimple(x.getOrganizationIdentifier());
        new STTransformerTest().verifyDtoSimple(x.getSerialNumber());
        new IITransformerTest().verifyDtoSimple(x.getStudyProtocolIdentifier());
        new BLTransformerTest().verifyDtoSimple(x.getSummary4ReportedResourceIndicator());
        new CDTransformerTest().verifyDtoSimple(x.getTypeCode());
        new REALTransformerTest().verifyDtoSimple(x.getFundingPercent());
    }

    @Override
    public void verifyXmlSimple(StudyResourcing x) {
        new CDTransformerTest().verifyXmlSimple(x.getFundingMechanismCode());
        new IITransformerTest().verifyXmlSimple(x.getIdentifier());
        new STTransformerTest().verifyXmlSimple(x.getInactiveCommentText());
        new CDTransformerTest().verifyXmlSimple(x.getNciDivisionProgramCode());
        new CDTransformerTest().verifyXmlSimple(x.getNihInstitutionCode());
        new IITransformerTest().verifyXmlSimple(x.getOrganizationIdentifier());
        new STTransformerTest().verifyXmlSimple(x.getSerialNumber());
        new IITransformerTest().verifyXmlSimple(x.getStudyProtocolIdentifier());
        new BLTransformerTest().verifyXmlSimple(x.getSummary4ReportedResourceIndicator());
        new CDTransformerTest().verifyXmlSimple(x.getTypeCode());
        new REALTransformerTest().verifyXmlSimple(x.getFundingPercent());
    }
}
