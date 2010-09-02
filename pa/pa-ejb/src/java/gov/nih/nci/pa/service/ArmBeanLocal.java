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
package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Arm;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.interceptor.ProprietaryTrialInterceptor;
import gov.nih.nci.pa.iso.convert.ArmConverter;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.search.ArmSortCriterion;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({ HibernateSessionInterceptor.class, ProprietaryTrialInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ArmBeanLocal extends AbstractStudyIsoService<ArmDTO, Arm, ArmConverter> implements ArmServiceLocal {

    @EJB
    private PlannedActivityServiceLocal plannedActivityService;

    /**
     * @param ii index of planned activity
     * @return list of arms associated w/planned activity
     * @throws PAException exception
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ArmDTO> getByPlannedActivity(Ii ii) throws PAException {
        if (PAUtil.isIiNull(ii)) {
            throw new PAException("Cannot call getByPlannedActivity with a null identifier.");
        }

        Arm criteria = new Arm();
        PlannedActivity activity = new PlannedActivity();
        activity.setId(IiConverter.convertToLong(ii));
        criteria.getInterventions().add(activity);

        PageSortParams<Arm> params =
            new PageSortParams<Arm>(PAConstants.MAX_SEARCH_RESULTS, 0, ArmSortCriterion.ARM_ID, false);
        List<Arm> results = search(new AnnotatedBeanSearchCriteria<Arm>(criteria), params);
        return convertFromDomainToDTOs(results);
    }

    private void businessRules(ArmDTO dto)  throws PAException {
        if (dto == null) {
            return;
        }
        if (StringUtils.isEmpty(StConverter.convertToString(dto.getName()))) {
            throw new PAException("The arm/group label (name) must be set.  ");
        }
        enforceNoDuplicate(dto);
    }

    /**
     * @param dto Arm transer object
     * @return created Arm
     * @throws PAException exception
     */
    @Override
    public ArmDTO create(ArmDTO dto) throws PAException {
        businessRules(dto);
        return super.create(dto);
    }

    /**
     * @param dto Arm transer object
     * @return updated Arm
     * @throws PAException exception
     */
    @Override
    public ArmDTO update(ArmDTO dto) throws PAException {
        businessRules(dto);
        return super.update(dto);
    }

    /**
     * creates a new record of arm and arm intervetions by changing to new studyprotocol identifier.
     * @param fromStudyProtocolIi from where the study protocol objects to be copied
     * @param toStudyProtocolIi to where the study protocol objects to be copied
     * @param armMap map of ii
     * @return map
     * @throws PAException on error
     */
    public Map<Ii , Ii> copy(Ii fromStudyProtocolIi , Ii toStudyProtocolIi , Map<Ii, Ii> armMap) throws PAException {
        List<ArmDTO> armDtos = getByStudyProtocol(fromStudyProtocolIi);
        Map<Ii, Ii> map = new HashMap<Ii, Ii>();
        for (ArmDTO armDto : armDtos) {
            Ii fromIi = armDto.getIdentifier();
            armDto.setInterventions(getAssociatedInterventions(armDto.getIdentifier() , armMap));
            armDto.setIdentifier(null);
            armDto.setStudyProtocolIdentifier(toStudyProtocolIi);
            Ii toIi = create(armDto).getIdentifier();
            map.put(fromIi, toIi);
        }
        return map;
    }

    private DSet<Ii> getAssociatedInterventions(Ii armIi , Map<Ii , Ii> armMap) throws PAException {
        List<PlannedActivityDTO> dtos = null;
        Set<Ii> iiSet = new HashSet<Ii>();
        boolean armIntFound = false;
        dtos = plannedActivityService.getByArm(armIi);
        for (PlannedActivityDTO paDto : dtos) {
            Ii value = PAUtil.containsIi(armMap, paDto.getIdentifier());
            if (value != null) {
                armIntFound = true;
                iiSet.add(value);
            }
        }
        DSet<Ii> interventions = null;
        if (armIntFound) {
            interventions = new DSet<Ii>();
            interventions.setItem(iiSet);
        }
        return interventions;
    }

    private void enforceNoDuplicate(ArmDTO dto) throws PAException {
        List<ArmDTO> armList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
        for (ArmDTO armDbDTO : armList) {
            if (isDuplicate(dto, armDbDTO) && (dto.getIdentifier() == null
                    || (!dto.getIdentifier().getExtension().equals(armDbDTO.getIdentifier().getExtension())))) {
                throw new PADuplicateException("Duplicates Arms are not allowed.");
            }
        }
    }

    /**
     * @param sameLabel
     * @param sameType
     * @param sameDesc
     * @param sameInterventions
     * @param dto
     * @throws PADuplicateException
     */
    private boolean isDuplicate(ArmDTO dto, ArmDTO dbDTO) throws PADuplicateException {
        String newLabel = StConverter.convertToString(dto.getName());
        String newType = CdConverter.convertCdToString(dto.getTypeCode());
        String newDescription = StConverter.convertToString(dto.getDescriptionText());
        Set<Ii> newIntervention = DSetConverter.convertDsetToIiSet(dto.getInterventions());
        boolean sameLabel = StringUtils.equalsIgnoreCase(newLabel, StConverter.convertToString(dbDTO.getName()));
        boolean sameType = StringUtils.equalsIgnoreCase(newType, CdConverter.convertCdToString(
                dbDTO.getTypeCode()));
        boolean sameDesc = StringUtils.equalsIgnoreCase(newDescription, StConverter.convertToString(
                dbDTO.getDescriptionText()));
        boolean sameInterventions = false;
        Set<Ii> armInterventions = DSetConverter.convertDsetToIiSet(dbDTO.getInterventions());
        if (CollectionUtils.isEmpty(newIntervention) && CollectionUtils.isEmpty(armInterventions)) {
            sameInterventions = true;
        } else if (CollectionUtils.isNotEmpty(newIntervention) && CollectionUtils.isNotEmpty(armInterventions)) {
            sameInterventions =  CollectionUtils.isEmpty(CollectionUtils.subtract(newIntervention,
                    armInterventions));
        }
        return ((sameType && sameDesc && sameInterventions) || sameLabel);
    }

}
