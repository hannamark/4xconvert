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

import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.RegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOnholdDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyRelationshipDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A registry of Transformer(s).
 *
 * @author smatyas
 *
 */
public final class TransformerRegistry {

    private static final Map<Class<?>, Transformer<?, ?>> VALUES = new HashMap<Class<?>, Transformer<?, ?>>();

    static {
        VALUES.put(ArmDTO.class, ArmTransformer.INSTANCE);
        VALUES.put(StudyResourcingDTO.class, StudyResourcingTransformer.INSTANCE);
        VALUES.put(StudyProtocolDTO.class, StudyProtocolTransformer.INSTANCE);
        VALUES.put(InterventionalStudyProtocolDTO.class, InterventionalStudyProtocolTransformer.INSTANCE);
        VALUES.put(StudyRegulatoryAuthorityDTO.class, StudyRegulatoryAuthorityTransformer.INSTANCE);
        VALUES.put(StudyRecruitmentStatusDTO.class, StudyRecruitmentStatusTransformer.INSTANCE);
        VALUES.put(StudySiteAccrualStatusDTO.class, StudySiteAccrualStatusTransformer.INSTANCE);
        VALUES.put(StudySiteContactDTO.class, StudySiteContactTransformer.INSTANCE);
        VALUES.put(StudyOutcomeMeasureDTO.class, StudyOutcomeMeasureTransformer.INSTANCE);
        VALUES.put(StudySiteDTO.class, StudySiteTransformer.INSTANCE);
        VALUES.put(StudyOverallStatusDTO.class, StudyOverallStatusTransformer.INSTANCE);
        VALUES.put(StudyDiseaseDTO.class, StudyDiseaseTransformer.INSTANCE);
        VALUES.put(StudyContactDTO.class, StudyContactTransformer.INSTANCE);
        VALUES.put(StudyOnholdDTO.class, StudyOnholdTransformer.INSTANCE);
        VALUES.put(StudyIndldeDTO.class, StudyIndldeTransformer.INSTANCE);
        VALUES.put(StudyRelationshipDTO.class, StudyRelationshipTransformer.INSTANCE);
        VALUES.put(DocumentWorkflowStatusDTO.class, DocumentWorkflowStatusTransformer.INSTANCE);
        VALUES.put(PlannedActivityDTO.class, PlannedActivityTransformer.INSTANCE);
        VALUES.put(PlannedEligibilityCriterionDTO.class, PlannedEligibilityCriterionTransformer.INSTANCE);
        VALUES.put(DocumentDTO.class, DocumentTransformer.INSTANCE);
        VALUES.put(DiseaseDTO.class, DiseaseTransformer.INSTANCE);
        VALUES.put(InterventionDTO.class, InterventionTransformer.INSTANCE);
        VALUES.put(RegulatoryAuthorityDTO.class, RegulatoryAuthorityTransformer.INSTANCE);
    }

    /**
     * Public singleton.
     */
    public static final TransformerRegistry INSTANCE = new TransformerRegistry();

    private TransformerRegistry() {
    }

    /**
     * @param type DTO type to translate
     * @return transformer for the type requested
     */
    public Transformer<?, ?> getTransformer(Class<?> type) {
        Transformer<?, ?> transformer = VALUES.get(type);
        if (transformer == null) {
            throw new RuntimeException("Unable to find Transformer for type " + type);
        }
        return transformer;
    }

    /**
     * @return an unmodifiable version of the registry
     */
    public static Map<Class<?>, Transformer<?, ?>> getRegistry() {
        return Collections.unmodifiableMap(VALUES);
    }
}
