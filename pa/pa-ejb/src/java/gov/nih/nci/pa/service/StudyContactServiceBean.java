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

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.iso.convert.StudyContactConverter;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;

/**
 * @author Bala nair
 * @since 10/23/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength", "PMD.NPathComplexity" })
@Interceptors(HibernateSessionInterceptor.class)
public class StudyContactServiceBean
        extends AbstractRoleIsoService<StudyContactDTO, StudyContact, StudyContactConverter>
        implements StudyContactServiceRemote, StudyContactServiceLocal {

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyContactDTO> search(StudyContactDTO dto, LimitOffset pagingParams) throws PAException,
    TooManyResultsException {
        if (dto == null) {
            getLogger().error(" StudyContactDTO should not be null ");
            throw new PAException(" StudyContactDTO should not be null ");
        }
        getLogger().info("Entering search");
        Session session = null;
        List<StudyContact> studyContactList = null;
        session = HibernateUtil.getCurrentSession();
        StudyContact exampleDO = new StudyContact();

        Criteria criteria = session.createCriteria(StudyContact.class);
        if (!PAUtil.isIiNull(dto.getIdentifier())) {
            exampleDO.setId(IiConverter.convertToLong(dto.getIdentifier()));
        }
        if (!PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            criteria.createAlias("studyProtocol", "sp")
            .add(Expression.eq("sp.id", IiConverter.convertToLong(dto.getStudyProtocolIdentifier())));
        }
        if (!PAUtil.isIiNull(dto.getHealthCareProviderIi())) {
            if (PAConstants.PA_INTERNAL.equals(dto.getHealthCareProviderIi().getIdentifierName())) { 
                criteria.createAlias("healthCareProvider", "hcp")
                .add(Expression.eq("hcp.id", IiConverter.convertToLong(dto.getHealthCareProviderIi())));
            } else {
                criteria.createAlias("healthCareProvider", "hcp")
                .add(Expression.eq("hcp.identifier", IiConverter.convertToString(dto.getHealthCareProviderIi())));
            }
        }

        if (!PAUtil.isIiNull(dto.getClinicalResearchStaffIi())) {
            if (PAConstants.PA_INTERNAL.equals(dto.getClinicalResearchStaffIi().getIdentifierName())) { 
                criteria.createAlias("clinicalResearchStaff", "crs")
                .add(Expression.eq("crs.id", IiConverter.convertToLong(dto.getClinicalResearchStaffIi())));
            } else {
                criteria.createAlias("clinicalResearchStaff", "crs")
                .add(Expression.eq("crs.identifier", IiConverter.convertToString(dto.getClinicalResearchStaffIi())));
            }
        }

        if (!PAUtil.isIiNull(dto.getOrganizationalContactIi())) {
            if (PAConstants.PA_INTERNAL.equals(dto.getOrganizationalContactIi().getIdentifierName())) { 
                criteria.createAlias("organizationalContact", "oc")
                .add(Expression.eq("oc.id", IiConverter.convertToLong(dto.getOrganizationalContactIi())));
            } else {
                criteria.createAlias("organizationalContact", "oc")
                .add(Expression.eq("oc.identifier", IiConverter.convertToString(dto.getOrganizationalContactIi())));
            }
        }

        if (dto.getStatusCode() != null) {
          exampleDO.setStatusCode(FunctionalRoleStatusCode.getByCode(dto.getStatusCode().getCode()));
        }
        if (dto.getRoleCode() != null) {
            exampleDO.setRoleCode(StudyContactRoleCode.getByCode(dto.getRoleCode().getCode()));            
        }
        if (dto.getTelecomAddresses() != null) {
            List<String> retList = null;            
            retList = DSetConverter.convertDSetToList(dto.getTelecomAddresses(), "EMAIL");
            if (retList != null && !retList.isEmpty()) {
                exampleDO.setEmail(retList.get(0).toString());
            }
            retList = DSetConverter.convertDSetToList(dto.getTelecomAddresses(), "PHONE");
            if (retList != null && !retList.isEmpty()) {
                exampleDO.setPhone(retList.get(0).toString());
            }
        }
        Example example = Example.create(exampleDO);
        criteria.add(example);
        int maxLimit = Math.min(pagingParams.getLimit(), PAConstants.MAX_SEARCH_RESULTS + 1);
        criteria.setMaxResults(maxLimit);
        criteria.setFirstResult(pagingParams.getOffset());
        studyContactList = criteria.list();

        if (studyContactList.size() > PAConstants.MAX_SEARCH_RESULTS) {
            throw new TooManyResultsException(PAConstants.MAX_SEARCH_RESULTS);
        }
        List<StudyContactDTO> studyContactDTOList = convertFromDomainToDTO(studyContactList);
        getLogger().info("Leaving search");
        return studyContactDTOList;
    }
    
    private List<StudyContactDTO> convertFromDomainToDTO(List<StudyContact> studyContactList) throws PAException {
        List<StudyContactDTO> studyContactDTOList = null;
        StudyContactConverter scConverter = new StudyContactConverter();
        if (studyContactList != null) { 
            studyContactDTOList = new ArrayList<StudyContactDTO>();
            for (StudyContact ss : studyContactList) {
                StudyContactDTO studyContactDTO = scConverter.convertFromDomainToDto(ss);
                studyContactDTOList.add(studyContactDTO);
            }
        }
        return studyContactDTOList;
    }

}
