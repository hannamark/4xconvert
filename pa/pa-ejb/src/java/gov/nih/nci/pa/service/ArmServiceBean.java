/**
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

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Arm;
import gov.nih.nci.pa.iso.convert.ArmConverter;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
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

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since 11/05/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Stateless
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@SuppressWarnings("PMD.CyclomaticComplexity")
public class ArmServiceBean extends AbstractStudyIsoService<ArmDTO, Arm, ArmConverter>
        implements ArmServiceRemote , ArmServiceLocal {
    
    @EJB
    PlannedActivityServiceLocal plannedActivityService = null;
    
    /**
     * @param ii index of planned activity
     * @return list of arms associated w/planned activity
     * @throws PAException exception
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ArmDTO> getByPlannedActivity(Ii ii) throws PAException {
        if (PAUtil.isIiNull(ii)) {
            throw new PAException("Check the Ii value; null found.  ");
        }
        getLogger().info("Entering getByPlannedActivity.  ");

        Session session = null;
        List<Arm> queryList = new ArrayList<Arm>();
        session = HibernateUtil.getCurrentSession();
        Query query = null;

        // step 1: form the hql
        String hql = "select ar "
            + "from Arm ar "
            + "join ar.interventions pa "
            + "where pa.id = :plannedActivityId "
            + "order by ar.id ";
        getLogger().info("query Arm = " + hql + ".  ");

        // step 2: construct query object
        query = session.createQuery(hql);
        query.setParameter("plannedActivityId", IiConverter.convertToLong(ii));

        // step 3: query the result
        queryList = query.list();
        ArrayList<ArmDTO> resultList = new ArrayList<ArmDTO>();
        for (Arm bo : queryList) {
            resultList.add(convertFromDomainToDto(bo));
        }
        getLogger().info("Leaving getByArm, returning " + resultList.size() + " object(s).  ");
        return resultList;
    }

    private void businessRules(ArmDTO dto)  throws PAException {
        if (dto == null) {
            return;
        }
        if (PAUtil.isEmpty(StConverter.convertToString(dto.getName()))) {
            throw new PAException("The arm/group label (name) must be set.  ");
        }
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
    
}
