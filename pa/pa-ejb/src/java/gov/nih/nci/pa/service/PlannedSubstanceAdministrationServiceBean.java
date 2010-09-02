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
package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.PlannedSubstanceAdministration;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.iso.convert.PlannedSubstanceAdministrationConverter;
import gov.nih.nci.pa.iso.dto.PlannedSubstanceAdministrationDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.search.PlannedSubstanceAdministrationSortCriterion;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;


/**
 * The Class PlannedSubstanceAdministrationServiceBean.
 *
 * @author Kalpana Guthikonda
 * @since 21/10/2009
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(HibernateSessionInterceptor.class)
public class PlannedSubstanceAdministrationServiceBean
extends AbstractStudyIsoService<PlannedSubstanceAdministrationDTO, PlannedSubstanceAdministration,
    PlannedSubstanceAdministrationConverter>
    implements PlannedSubstanceAdministrationServiceLocal, PlannedSubstanceAdministrationServiceRemote {

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<PlannedSubstanceAdministrationDTO> getPlannedSubstanceAdministrationByStudyProtocol(Ii ii)
    throws PAException {
        if (PAUtil.isIiNull(ii)) {
            throw new PAException("Check the Ii value; found null.");
        }

        PlannedSubstanceAdministration criteria = new PlannedSubstanceAdministration();
        StudyProtocol sp = new StudyProtocol();
        sp.setId(IiConverter.convertToLong(ii));
        criteria.setStudyProtocol(sp);

        PageSortParams<PlannedSubstanceAdministration> params =
            new PageSortParams<PlannedSubstanceAdministration>(PAConstants.MAX_SEARCH_RESULTS, 0,
                    PlannedSubstanceAdministrationSortCriterion.PLANNED_SUBSTANCE_ADMINISTRATION_ID, false);
        List<PlannedSubstanceAdministration> results =
            search(new AnnotatedBeanSearchCriteria<PlannedSubstanceAdministration>(criteria), params);
        return convertFromDomainToDTOs(results);
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public PlannedSubstanceAdministrationDTO getPlannedSubstanceAdministration(Ii ii) throws PAException {
        return super.get(ii);
    }

    /**
     * {@inheritDoc}
     */
    public PlannedSubstanceAdministrationDTO createPlannedSubstanceAdministration(PlannedSubstanceAdministrationDTO dto)
        throws PAException {
        if (!PAUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Update method should be used to modify existing.");
        }
        if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            throw new PAException("StudyProtocol must be set.");
        }
        return createOrUpdatePlannedSubstanceAdministration(dto);
    }

    /**
     * {@inheritDoc}
     */
    public PlannedSubstanceAdministrationDTO updatePlannedSubstanceAdministration(PlannedSubstanceAdministrationDTO dto)
        throws PAException {
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Create method should be used to modify existing.");
        }
        return createOrUpdatePlannedSubstanceAdministration(dto);
    }

    /**
     * {@inheritDoc}
     */
    public void deletePlannedSubstanceAdministration(Ii ii) throws PAException {
        super.delete(ii);
    }

    private PlannedSubstanceAdministrationDTO createOrUpdatePlannedSubstanceAdministration(
            PlannedSubstanceAdministrationDTO dto) throws PAException {
        PlannedSubstanceAdministration bo = null;
        Session session = HibernateUtil.getCurrentSession();
        PlannedSubstanceAdministrationConverter converter = new PlannedSubstanceAdministrationConverter();
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            bo = converter.convertFromDtoToDomain(dto);
        } else {
            bo = (PlannedSubstanceAdministration) session.get(PlannedSubstanceAdministration.class,
                    IiConverter.convertToLong(dto.getIdentifier()));

            PlannedSubstanceAdministration delta = converter.convertFromDtoToDomain(dto);
            bo.setDoseDescription(delta.getDoseDescription());
            bo.setDoseRegimen(delta.getDoseRegimen());
            bo.setDoseFormCode(delta.getDoseFormCode());
            bo.setDoseFrequencyCode(delta.getDoseFrequencyCode());
            bo.setDoseMinValue(delta.getDoseMinValue());
            bo.setDoseMinUnit(delta.getDoseMinUnit());
            bo.setDoseMaxValue(delta.getDoseMaxValue());
            bo.setDoseMaxUnit(delta.getDoseMaxUnit());
            bo.setDoseTotalMinValue(delta.getDoseTotalMinValue());
            bo.setDoseTotalMinUnit(delta.getDoseTotalMinUnit());
            bo.setDoseTotalMaxValue(delta.getDoseTotalMaxValue());
            bo.setDoseTotalMaxUnit(delta.getDoseTotalMaxUnit());
            bo.setRouteOfAdministrationCode(delta.getRouteOfAdministrationCode());
            bo.setDoseDurationValue(delta.getDoseDurationValue());
            bo.setDoseDurationUnit(delta.getDoseDurationUnit());
            bo.setUserLastUpdated(delta.getUserLastCreated());
        }
        bo.setDateLastUpdated(new Date());
        session.saveOrUpdate(bo);
        return converter.convertFromDomainToDto(bo);
    }
}
