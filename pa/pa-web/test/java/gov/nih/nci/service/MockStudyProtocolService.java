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
package gov.nih.nci.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.iso.convert.StudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * @author hreinhart
 *
 */
public class MockStudyProtocolService implements StudyProtocolServiceRemote {

    static List<StudyProtocol> list;

    static {
        list = new ArrayList<StudyProtocol>();
        StudyProtocol sp = new StudyProtocol();
        sp.setId(1L);
        sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        sp.setStartDate(PAUtil.dateStringToTimestamp("1/1/2000"));
        sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("4/15/2010"));
        list.add(sp);
    }
    public List<StudyProtocolDTO> search(StudyProtocolDTO spDTO) throws PAException{
    	return null;
    }

    public StudyProtocolDTO getStudyProtocol(Ii ii) throws PAException {
        for (StudyProtocol sp: list) {
            if(sp.getId().equals(IiConverter.convertToLong(ii))) {
                return StudyProtocolConverter.convertFromDomainToDTO(sp);
            }
        }
        return null;
    }

    public StudyProtocolDTO updateStudyProtocol(
            StudyProtocolDTO dto) throws PAException {
        for (StudyProtocol bo : list) {
            if (bo.getId().equals(IiConverter.convertToLong(dto.getIdentifier()))) {
                bo.setStartDateTypeCode(ActualAnticipatedTypeCode.getByCode(dto.getStartDateTypeCode().getCode()));
                bo.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.getByCode(dto.getPrimaryCompletionDateTypeCode().getCode()));
                bo.setStartDate(TsConverter.convertToTimestamp(dto.getStartDate()));
                bo.setPrimaryCompletionDate(TsConverter.convertToTimestamp(dto.getPrimaryCompletionDate()));
                return StudyProtocolConverter.convertFromDomainToDTO(bo);
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyProtocolService#getInterventionalStudyProtocol(gov.nih.nci.coppa.iso.Ii)
     */
    public InterventionalStudyProtocolDTO getInterventionalStudyProtocol(Ii ii)
            throws PAException {
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyProtocolService#getStudyProtocolByCriteria(gov.nih.nci.pa.dto.StudyProtocolQueryCriteria)
     */
    public List<StudyProtocolQueryDTO> getStudyProtocolByCriteria(
            StudyProtocolQueryCriteria sc) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyProtocolService#getTrialSummaryByStudyProtocolId(java.lang.Long)
     */
    public StudyProtocolQueryDTO getTrialSummaryByStudyProtocolId(
            Long studyProtocolId) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyProtocolService#updateInterventionalStudyProtocol(gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO)
     */
    public InterventionalStudyProtocolDTO updateInterventionalStudyProtocol(
            InterventionalStudyProtocolDTO ispDTO) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }
    
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyProtocolService#updateInterventionalStudyProtocol(gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO)
     */
    public ObservationalStudyProtocolDTO getObservationalStudyProtocol(Ii ii) throws PAException {
        return null;
    }
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyProtocolService#updateInterventionalStudyProtocol(gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO)
     */
    
    public Ii createInterventionalStudyProtocol(InterventionalStudyProtocolDTO ispDTO)
    throws PAException {
        return null;
    }

    public Ii createObservationalStudyProtocol(
            ObservationalStudyProtocolDTO ospDTO) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public ObservationalStudyProtocolDTO updateObservationalStudyProtocol(
            ObservationalStudyProtocolDTO ospDTO) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }    
    
    /**
     * deletes protocol and all of its related classes.
     * @param ii ii of study Protocol
     * @throws PAException on any error
     */
    public void deleteStudyProtocol(Ii ii) throws PAException {    
     // TODO Auto-generated method stub
    }
}
