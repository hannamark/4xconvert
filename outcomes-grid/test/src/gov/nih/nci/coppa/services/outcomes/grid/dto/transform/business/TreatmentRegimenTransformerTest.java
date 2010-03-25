/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The OutcomesServices
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This OutcomesServices Software License (the License) is between NCI and You. You (or
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
 * its rights in the OutcomesServices Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the OutcomesServices Software; (ii) distribute and
 * have distributed to and by third parties the OutcomesServices Software and any
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
package gov.nih.nci.coppa.services.outcomes.grid.dto.transform.business;

import static junit.framework.Assert.assertEquals;
import gov.nih.nci.coppa.services.outcomes.business.Cycle;
import gov.nih.nci.coppa.services.outcomes.business.DiseaseEvaluation;
import gov.nih.nci.coppa.services.outcomes.business.LesionAssessment;
import gov.nih.nci.coppa.services.outcomes.business.OffTreatment;
import gov.nih.nci.coppa.services.outcomes.business.TreatmentRegimen;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.STTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.TSTransformerTest;
import gov.nih.nci.outcomes.svc.dto.CycleSvcDto;
import gov.nih.nci.outcomes.svc.dto.DiseaseEvaluationSvcDto;
import gov.nih.nci.outcomes.svc.dto.LesionAssessmentSvcDto;
import gov.nih.nci.outcomes.svc.dto.OffTreatmentSvcDto;
import gov.nih.nci.outcomes.svc.dto.TreatmentRegimenSvcDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkanchinadam
 *
 */
public class TreatmentRegimenTransformerTest extends AbstractTransformerTestBase<TreatmentRegimenTransformer, TreatmentRegimen, TreatmentRegimenSvcDto>{

    private CycleTransformerTest cycleTransformerTest = new CycleTransformerTest();
    private DiseaseEvaluationTransformerTest diseaseEvaluationTransformerTest = new DiseaseEvaluationTransformerTest();
    private LesionAssessmentTransformerTest lesionAssessmentTransformerTest = new LesionAssessmentTransformerTest();


    /* (non-Javadoc)
     * @see gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase#makeDtoSimple()
     */
    @Override
    public TreatmentRegimenSvcDto makeDtoSimple() throws DtoTransformException {
        TreatmentRegimenSvcDto dto = new TreatmentRegimenSvcDto();
        dto.setAction(new CDTransformerTest().makeDtoSimple());
        dto.setDescription(new STTransformerTest().makeDtoSimple());
        dto.setName(new STTransformerTest().makeDtoSimple());
        dto.setOffTreatment(getOffTreatmentSvcDto());

        dto.setCycles(getCycleDtos(3));
        dto.setDiseaseEvaluations(getDiseaseEvaluationDtos(5));
        dto.setLesionAssessments(getLesionAssessmentDtos(7));

        return dto;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase#makeXmlSimple()
     */
    @Override
    public TreatmentRegimen makeXmlSimple() throws DtoTransformException {
        TreatmentRegimen xml = new TreatmentRegimen();
        xml.setAction(new CDTransformerTest().makeXmlSimple());
        xml.setDescription(new STTransformerTest().makeXmlSimple());
        xml.setName(new STTransformerTest().makeXmlSimple());
        xml.setOffTreatment(getOffTreatmentXml());

        xml.getCycles().addAll(getCycles(2));
        xml.getDiseaseEvaluations().addAll(getDiseaseEvaluations(4));
        xml.getLesionAssessments().addAll(getLesionAssessments(6));

        return xml;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase#verifyDtoSimple(java.lang.Object)
     */
    @Override
    public void verifyDtoSimple(TreatmentRegimenSvcDto dto) throws DtoTransformException {
        new CDTransformerTest().verifyDtoSimple(dto.getAction());
        new STTransformerTest().verifyDtoSimple(dto.getDescription());
        new STTransformerTest().verifyDtoSimple(dto.getName());
        // verify offtreatmentsvcdto
        new CDTransformerTest().verifyDtoSimple(dto.getOffTreatment().getAction());
        new TSTransformerTest().verifyDtoSimple(dto.getOffTreatment().getLastTreatmentDate());
        new CDTransformerTest().verifyDtoSimple(dto.getOffTreatment().getOffTreatmentReason());

        assertEquals(2, dto.getCycles().size());
        for(CycleSvcDto cycleDto: dto.getCycles()) {
            cycleTransformerTest.verifyDtoSimple(cycleDto);
        }

        assertEquals(4, dto.getDiseaseEvaluations().size());
        for(DiseaseEvaluationSvcDto deDto: dto.getDiseaseEvaluations()) {
            diseaseEvaluationTransformerTest.verifyDtoSimple(deDto);
        }

        assertEquals(6, dto.getLesionAssessments().size());
        for(LesionAssessmentSvcDto leDto: dto.getLesionAssessments()) {
            lesionAssessmentTransformerTest.verifyDtoSimple(leDto);
        }
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase#verifyXmlSimple(java.lang.Object)
     */
    @Override
    public void verifyXmlSimple(TreatmentRegimen xml) throws DtoTransformException {
        new CDTransformerTest().verifyXmlSimple(xml.getAction());
        new STTransformerTest().verifyXmlSimple(xml.getDescription());
        new STTransformerTest().verifyXmlSimple(xml.getName());
        // verify offtreatment
        new CDTransformerTest().verifyXmlSimple(xml.getOffTreatment().getAction());
        new TSTransformerTest().verifyXmlSimple(xml.getOffTreatment().getLastTreatmentDate());
        new CDTransformerTest().verifyXmlSimple(xml.getOffTreatment().getOffTreatmentReason());

        assertEquals(3, xml.getCycles().size());
        for(Cycle cycle: xml.getCycles()) {
            cycleTransformerTest.verifyXmlSimple(cycle);
        }

        assertEquals(5, xml.getDiseaseEvaluations().size());
        for(DiseaseEvaluation de: xml.getDiseaseEvaluations()) {
            diseaseEvaluationTransformerTest.verifyXmlSimple(de);
        }

        assertEquals(7, xml.getLesionAssessments().size());
        for(LesionAssessment la: xml.getLesionAssessments()) {
            lesionAssessmentTransformerTest.verifyXmlSimple(la);
        }
    }

    private OffTreatmentSvcDto getOffTreatmentSvcDto() {
        OffTreatmentSvcDto dto = new OffTreatmentSvcDto();
        dto.setAction(new CDTransformerTest().makeDtoSimple());
        dto.setLastTreatmentDate(new TSTransformerTest().makeDtoSimple());
        dto.setOffTreatmentReason(new CDTransformerTest().makeDtoSimple());
        return dto;
    }

    private OffTreatment getOffTreatmentXml() {
        OffTreatment xml = new OffTreatment();
        xml.setAction(new CDTransformerTest().makeXmlSimple());
        xml.setLastTreatmentDate(new TSTransformerTest().makeXmlSimple());
        xml.setOffTreatmentReason(new CDTransformerTest().makeXmlSimple());
        return xml;
    }

    private List<CycleSvcDto> getCycleDtos(int countOfObjects) throws DtoTransformException {
        List<CycleSvcDto> dtos = new ArrayList<CycleSvcDto>();
        for (int i=0; i<countOfObjects; i++) {
            dtos.add(cycleTransformerTest.makeDtoSimple());
        }
        return dtos;
    }

    private List<Cycle> getCycles(int countOfObjects) throws DtoTransformException {
        List<Cycle> xmls = new ArrayList<Cycle>();
        for (int i=0; i<countOfObjects; i++) {
            xmls.add(cycleTransformerTest.makeXmlSimple());
        }
        return xmls;
    }

    private List<DiseaseEvaluationSvcDto> getDiseaseEvaluationDtos(int countOfObjects) throws DtoTransformException {
        List<DiseaseEvaluationSvcDto> dtos = new ArrayList<DiseaseEvaluationSvcDto>();
        for (int i=0; i<countOfObjects; i++) {
            dtos.add(diseaseEvaluationTransformerTest.makeDtoSimple());
        }
        return dtos;
    }

    private List<DiseaseEvaluation> getDiseaseEvaluations(int countOfObjects) throws DtoTransformException {
        List<DiseaseEvaluation> xmls = new ArrayList<DiseaseEvaluation>();
        for (int i=0; i<countOfObjects; i++) {
            xmls.add(diseaseEvaluationTransformerTest.makeXmlSimple());
        }
        return xmls;
    }

    private List<LesionAssessmentSvcDto> getLesionAssessmentDtos(int countOfObjects) throws DtoTransformException {
        List<LesionAssessmentSvcDto> dtos = new ArrayList<LesionAssessmentSvcDto>();
        for (int i=0; i<countOfObjects; i++) {
            dtos.add(lesionAssessmentTransformerTest.makeDtoSimple());
        }
        return dtos;
    }

    private List<LesionAssessment> getLesionAssessments(int countOfObjects) throws DtoTransformException {
        List<LesionAssessment> xmls = new ArrayList<LesionAssessment>();
        for (int i=0; i<countOfObjects; i++) {
            xmls.add(lesionAssessmentTransformerTest.makeXmlSimple());
        }
        return xmls;
    }
}
