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
package gov.nih.nci.accrual.accweb.util;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.accrual.util.ServiceLocatorPaInterface;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.iso.dto.ICD9DiseaseDTO;
import gov.nih.nci.pa.iso.dto.SDCDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.ICD9DiseaseServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceRemote;
import gov.nih.nci.pa.service.SDCDiseaseServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;
import gov.nih.nci.pa.service.StudySiteServiceRemote;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceRemote;
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author Hugh Reinhart
 * @since Aug 27, 2009
 */
public class MockPaServiceLocator implements ServiceLocatorPaInterface {

    private final SDCDiseaseServiceRemote diseaseSvc;
    private final PlannedActivityServiceRemote pActivity = new MockPaPlannedActivityServiceBean();
    private final StudyProtocolServiceRemote studyProtocolService = mock(StudyProtocolServiceRemote.class);
    private final MailManagerServiceRemote mailManagerService = mock(MailManagerServiceRemote.class);
    private final RegistryUserServiceRemote registryUserService = mock(RegistryUserServiceRemote.class);
    private final ICD9DiseaseServiceRemote icd9DiseaseSvc = mock(ICD9DiseaseServiceRemote.class);
    private final StudySiteServiceRemote studySiteSvc = mock(StudySiteServiceRemote.class);
    private final StudyResourcingServiceRemote studyResourcingSvc = mock(StudyResourcingServiceRemote.class);
    private final LookUpTableServiceRemote lookUpTableSvc = mock(LookUpTableServiceRemote.class);
    private final StudySiteAccrualStatusServiceRemote accrualStatusSvc = mock(StudySiteAccrualStatusServiceRemote.class);
    
    private static Map<Long, SDCDiseaseDTO> dtos;
    private static Map<Long, StudyProtocolDTO> studyDtos;

    static {
        dtos = new HashMap<Long, SDCDiseaseDTO>();
        SDCDiseaseDTO disease = new SDCDiseaseDTO();
        disease.setIdentifier(IiConverter.convertToIi(1L));
        disease.setDiseaseCode(StConverter.convertToSt("diseaseCode 01"));
        disease.setDisplayName(StConverter.convertToSt("menu 01"));
        disease.setPreferredName(StConverter.convertToSt("perferredName 01"));
        dtos.put(1L, disease);
        disease = new SDCDiseaseDTO();
        disease.setIdentifier(IiConverter.convertToIi(2L));
        disease.setDiseaseCode(StConverter.convertToSt("diseaseCode 02"));
        disease.setDisplayName(StConverter.convertToSt("menu 02"));
        disease.setPreferredName(StConverter.convertToSt("perferredName 02"));
        dtos.put(2L, disease);
        
        studyDtos = new HashMap<Long, StudyProtocolDTO>();
        StudyProtocolDTO dto = new StudyProtocolDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));
        dto.setPrimaryPurposeCode(CdConverter.convertStringToCd(PrimaryPurposeCode.TREATMENT.getCode()));
        studyDtos.put(1L, dto);
        dto = new StudyProtocolDTO();
        dto.setIdentifier(IiConverter.convertToIi(2L));
        dto.setPrimaryPurposeCode(CdConverter.convertStringToCd(PrimaryPurposeCode.PREVENTION.getCode()));
        studyDtos.put(2L, dto);
    }
    
    /**
     * Default constructor.
     */
    public MockPaServiceLocator() throws PAException {
        diseaseSvc = mock(SDCDiseaseServiceRemote.class);
        when(diseaseSvc.get(any(Ii.class))).thenAnswer(new Answer<SDCDiseaseDTO>() {
          public SDCDiseaseDTO answer(InvocationOnMock invocation) throws Throwable {
              Object args[] = invocation.getArguments();
              Ii ii = (Ii) args[0];
              return dtos.get(IiConverter.convertToLong(ii));
          }
        });
        when(diseaseSvc.search(any(SDCDiseaseDTO.class))).thenReturn(new ArrayList<SDCDiseaseDTO>(dtos.values()));
        
        RegistryUser ru = new RegistryUser();
        ru.setId(1L);
        when(registryUserService.getUser(any(String.class))).thenReturn(ru);
        when(icd9DiseaseSvc.get(any(Ii.class))).thenReturn(createICD9DiseaseDTO());
        when(icd9DiseaseSvc.getByName(any(String.class))).thenReturn(createICD9DiseaseDTOList());
        when(studyProtocolService.getStudyProtocol(any(Ii.class))).thenAnswer(new Answer<StudyProtocolDTO>() {
          public StudyProtocolDTO answer(InvocationOnMock invocation) throws Throwable {
              Object args[] = invocation.getArguments();
              Ii ii = (Ii) args[0];              
              return studyDtos.get(IiConverter.convertToLong(ii));
          }
        });
    }
    
    /**
     * {@inheritDoc}
     */
    public SDCDiseaseServiceRemote getDiseaseService() {
        return diseaseSvc;
    }
    
    /**
     * {@inheritDoc}
     */
    public PlannedActivityServiceRemote getPlannedActivityService() {
        return pActivity;
    }
    
    /**
     * {@inheritDoc}
     */
    public StudyProtocolServiceRemote getStudyProtocolService() {
        return studyProtocolService;
    }
    
    /**
     * {@inheritDoc}
     */
    public MailManagerServiceRemote getMailManagerService() {
        return mailManagerService;
    }

    /**
     * {@inheritDoc}
     */
    public RegistryUserServiceRemote getRegistryUserService() {
        return registryUserService;
    }

    /**
     * {@inheritDoc}
     */
    public ICD9DiseaseServiceRemote getICD9DiseaseService() {
        return icd9DiseaseSvc;
    }
    
    private ICD9DiseaseDTO createICD9DiseaseDTO() {
        ICD9DiseaseDTO dto = new ICD9DiseaseDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));
        dto.setPreferredName(StConverter.convertToSt("pname"));
        dto.setDiseaseCode(StConverter.convertToSt("code"));
        dto.setName(StConverter.convertToSt("pname"));
        return dto;
    }
    
    private List<ICD9DiseaseDTO> createICD9DiseaseDTOList() {
        List<ICD9DiseaseDTO> list = new ArrayList<ICD9DiseaseDTO>();
        list.add(createICD9DiseaseDTO());
        return list;
    }
    
    /**
     * {@inheritDoc}
     */
    public StudySiteServiceRemote getStudySiteService() {
        return studySiteSvc;
    }
    
    /**
     * {@inheritDoc}
     */
    public StudyResourcingServiceRemote getStudyResourcingService() {
        return studyResourcingSvc;
    }

	@Override
	public LookUpTableServiceRemote getLookUpTableService() {
		return lookUpTableSvc;
	}

	@Override
	public StudySiteAccrualStatusServiceRemote getStudySiteAccrualStatusService() {
		return accrualStatusSvc;
	}    
}
