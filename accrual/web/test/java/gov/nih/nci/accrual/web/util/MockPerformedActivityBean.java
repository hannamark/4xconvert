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

package gov.nih.nci.accrual.web.util;

import gov.nih.nci.accrual.dto.PerformedActivityDto;
import gov.nih.nci.accrual.dto.PerformedImagingDto;
import gov.nih.nci.accrual.dto.PerformedObservationDto;
import gov.nih.nci.accrual.dto.PerformedProcedureDto;
import gov.nih.nci.accrual.dto.PerformedRadiationAdministrationDto;
import gov.nih.nci.accrual.dto.PerformedSubjectMilestoneDto;
import gov.nih.nci.accrual.dto.PerformedSubstanceAdministrationDto;
import gov.nih.nci.accrual.service.PerformedActivityService;
import gov.nih.nci.accrual.web.action.AbstractAccrualActionTest;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Ivl;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivityNameCode;
import gov.nih.nci.pa.enums.OffTreatmentReasonCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * @author Hugh Reinhart
 * @since Sep 26, 2009
 */
public class MockPerformedActivityBean implements PerformedActivityService {

    public static String courseId;
    public static String tpId;
    private List<PerformedActivityDto> paList;
    {
        paList = new ArrayList<PerformedActivityDto>();
        PerformedActivityDto dto = new PerformedActivityDto();
        dto.setIdentifier(IiConverter.convertToIi(getKey()));
        tpId = dto.getIdentifier().getExtension();
        dto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.TREATMENT_PLAN));
        dto.setName(StConverter.convertToSt("TreatmentPlan1"));
        dto.setTextDescription(StConverter.convertToSt("TP description")); 
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        dto.setStudySubjectIdentifier(IiConverter.convertToStudySiteIi(1L));
        paList.add(dto);
        dto = new PerformedActivityDto();
        dto.setIdentifier(IiConverter.convertToIi(getKey()));
        courseId = dto.getIdentifier().getExtension();
        dto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.COURSE));
        dto.setName(StConverter.convertToSt("Course1"));
        Ivl<Ts> courseDate = new Ivl<Ts>();
        courseDate.setLow(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        dto.setActualDateRange(courseDate);
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(2L));
        dto.setStudySubjectIdentifier(IiConverter.convertToStudySiteIi(2L));
        paList.add(dto);
    }
    private List<PerformedObservationDto> poList;
    {
        poList = new ArrayList<PerformedObservationDto>();
        PerformedObservationDto dto = new PerformedObservationDto();
        dto.setIdentifier(IiConverter.convertToIi(getKey()));
        dto.setNameCode(CdConverter.convertToCd(ActivityNameCode.DIAGNOSIS));
        Ivl<Ts> diagnosisDate = new Ivl<Ts>();
        diagnosisDate.setLow(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        dto.setActualDateRange(diagnosisDate);
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        dto.setStudySubjectIdentifier(IiConverter.convertToStudySiteIi(1L));
        poList.add(dto);
    }
    
    private List<PerformedSubjectMilestoneDto> psmList;   
    {
        psmList = new ArrayList<PerformedSubjectMilestoneDto>();
        PerformedSubjectMilestoneDto dto = new PerformedSubjectMilestoneDto();
        dto.setIdentifier(IiConverter.convertToIi(getKey()));
        dto.setNameCode(CdConverter.convertToCd(ActivityNameCode.OFF_TREATMENT));
        dto.setReasonCode(CdConverter.convertToCd(OffTreatmentReasonCode.THREE));
        Ivl<Ts> offTreatmentDate = new Ivl<Ts>();
        offTreatmentDate.setLow(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        dto.setActualDateRange(offTreatmentDate);
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        dto.setStudySubjectIdentifier(IiConverter.convertToStudySiteIi(1L));
        psmList.add(dto);
    }
    private List<PerformedProcedureDto> ppList;
    {
        ppList = new ArrayList<PerformedProcedureDto>();                
        PerformedProcedureDto dto = new PerformedProcedureDto();
        dto.setIdentifier(IiConverter.convertToIi(getKey()));
        dto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.SURGERY));
        dto.setTextDescription(StConverter.convertToSt("Surgery Info"));
        Ivl<Ts> surgeryDate = new Ivl<Ts>();
        surgeryDate.setLow(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        dto.setActualDateRange(surgeryDate);
        dto.setInterventionIdentifier(IiConverter.convertToIi(1L));
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        dto.setStudySubjectIdentifier(IiConverter.convertToStudySiteIi(1L));
        ppList.add(dto);
    }

    private static int key = 1000;
    private static HashMap<String, PerformedObservationDto> hmPo = new HashMap<String, PerformedObservationDto>();
    private static List<PoSs> listPo = new ArrayList<PoSs>();

    private class PoSs {
        public String ss;
        public PerformedObservationDto dto;
        public PoSs(String ss, PerformedObservationDto dto) {
            this.ss = ss;
            this.dto = dto;
        }
    }
    
    {
        if (hmPo.size() > 0) {
            hmPo = new HashMap<String, PerformedObservationDto>();
            listPo = new ArrayList<PoSs>();
        }
        PerformedObservationDto dto;
        String id;

        dto = new PerformedObservationDto();
        dto.setActualDateRange(new Ivl<Ts>());
        dto.getActualDateRange().setLow(new Ts());
        dto.getActualDateRange().getLow().setValue(new Date());
        dto.setNameCode(CdConverter.convertStringToCd(ActivityNameCode.DIAGNOSIS.getCode()));
        dto.setStudySubjectIdentifier(IiConverter.convertToIi(AbstractAccrualActionTest.PARTICIPANT1));
        id = MockPerformedActivityBean.class.getName() + ".hmPo " + getKey();
        dto.setIdentifier(IiConverter.convertToIi(id));
        hmPo.put(id, dto);
        listPo.add(new PoSs(AbstractAccrualActionTest.PARTICIPANT1, dto));

        dto = new PerformedObservationDto();
        List<Cd> cds = new ArrayList<Cd>();
        cds.add(CdConverter.convertStringToCd("Karnofsky"));
        dto.setMethodCode(DSetConverter.convertCdListToDSet(cds));
        dto.setNameCode(CdConverter.convertStringToCd(ActivityNameCode.PERFORMANCE_STATUS.getCode()));
        dto.setStudySubjectIdentifier(IiConverter.convertToIi(AbstractAccrualActionTest.PARTICIPANT1));
        id = MockPerformedActivityBean.class.getName() + ".hmPo " + getKey();
        dto.setIdentifier(IiConverter.convertToIi(id));
        hmPo.put(id, dto);
        listPo.add(new PoSs(AbstractAccrualActionTest.PARTICIPANT1, dto));
    }
    
    private synchronized String getKey() {
        return String.valueOf(++key);
    }
    
    public static PerformedObservationDto getDiagnosisPo() {
        return listPo.get(0).dto;
    }
    
    public static PerformedObservationDto getPerformanceStatusPo() {
        return listPo.get(1).dto;
    }

    public PerformedSubjectMilestoneDto createPerformedSubjectMilestone(
            PerformedSubjectMilestoneDto dto) throws RemoteException {
        return psmList.get(0);
    }

    public List<PerformedActivityDto> getByStudySubject(Ii ii)
            throws RemoteException {
         return paList;
    }

    public PerformedSubjectMilestoneDto getPerformedSubjectMilestone(Ii ii)
            throws RemoteException {
        Long id = IiConverter.convertToLong(ii);
        PerformedSubjectMilestoneDto result = null;
        for (PerformedSubjectMilestoneDto dto : psmList) {
            if (id.equals(IiConverter.convertToLong(dto.getIdentifier()))) {
                result = dto;
            }
        }
        return result;
    }

    public List<PerformedSubjectMilestoneDto> getPerformedSubjectMilestoneByStudySubject(
            Ii ii) throws RemoteException {
        return psmList;
    }

    public PerformedSubjectMilestoneDto updatePerformedSubjectMilestone(
            PerformedSubjectMilestoneDto dto) throws RemoteException {
        Long id = IiConverter.convertToLong(dto.getIdentifier());
        for (PerformedSubjectMilestoneDto psm : psmList) {
            if (id.equals(IiConverter.convertToLong(psm.getIdentifier()))) {
                psm = dto;
            }
        }
        return dto;
    }

    public List<PerformedActivityDto> getByStudyProtocol(Ii ii)
            throws RemoteException {
        return new ArrayList<PerformedActivityDto>();
    }

    public PerformedActivityDto create(PerformedActivityDto dto)
            throws RemoteException {
        return paList.get(0);
    }

    public void delete(Ii ii) throws RemoteException {
    }

    public PerformedActivityDto get(Ii ii) throws RemoteException {
        return new PerformedActivityDto();
    }

    public PerformedActivityDto update(PerformedActivityDto dto)
            throws RemoteException {
        Long id = IiConverter.convertToLong(dto.getIdentifier());
        for (PerformedActivityDto pa : paList) {
            if (id.equals(IiConverter.convertToLong(pa.getIdentifier()))) {
                pa = dto;
            }
        }
        return dto;
    }

    public PerformedImagingDto createPerformedImaging(PerformedImagingDto dto)
            throws RemoteException, DataFormatException {
        return new PerformedImagingDto();
    }

    public PerformedObservationDto createPerformedObservation(
            PerformedObservationDto dto) throws RemoteException,
            DataFormatException {
        
        PerformedObservationDto pod = (dto == null) ? new PerformedObservationDto() : dto;
        String id = getKey();
        pod.setIdentifier(IiConverter.convertToIi(id));
        hmPo.put(id, pod);
        if (pod.getStudySubjectIdentifier() != null
                && pod.getStudySubjectIdentifier().getExtension() != null) {
            hmPo.put(pod.getStudySubjectIdentifier().getExtension(), dto);
        }
        return pod;
    }

    public PerformedProcedureDto createPerformedProcedure(
            PerformedProcedureDto dto) throws RemoteException,
            DataFormatException {
        return ppList.get(0);
    }

    public PerformedRadiationAdministrationDto createPerformedRadiationAdministration(
            PerformedRadiationAdministrationDto dto) throws RemoteException,
            DataFormatException {
        return new PerformedRadiationAdministrationDto();
    }

    public PerformedSubstanceAdministrationDto createPerformedSubstanceAdministration(
            PerformedSubstanceAdministrationDto dto) throws RemoteException,
            DataFormatException {
        return new PerformedSubstanceAdministrationDto();
    }

    public PerformedImagingDto getPerformedImaging(Ii ii)
            throws RemoteException {
        return new PerformedImagingDto();
    }

    public List<PerformedImagingDto> getPerformedImagingByStudySubject(Ii ii)
            throws RemoteException {
        return new ArrayList<PerformedImagingDto>();
    }

    public PerformedObservationDto getPerformedObservation(Ii ii)
            throws RemoteException {
        if (ii == null) {
            throw new RemoteException("NULL argument getPerformedObservation");
        }
        PerformedObservationDto dto = hmPo.get(ii.getExtension());
        return (dto == null) ? new PerformedObservationDto() : dto;
    }

    public PerformedProcedureDto getPerformedProcedure(Ii ii)
            throws RemoteException {
        return new PerformedProcedureDto();
    }

    public List<PerformedProcedureDto> getPerformedProcedureByStudySubject(
            Ii ii) throws RemoteException {
        return ppList;
    }

    public PerformedRadiationAdministrationDto getPerformedRadiationAdministration(
            Ii ii) throws RemoteException {
        return new PerformedRadiationAdministrationDto();
    }

    public List<PerformedRadiationAdministrationDto> getPerformedRadiationAdministrationByStudySubject(
            Ii ii) throws RemoteException {
        return new ArrayList<PerformedRadiationAdministrationDto>();
    }

    public PerformedSubstanceAdministrationDto getPerformedSubstanceAdministration(
            Ii ii) throws RemoteException {
        return new PerformedSubstanceAdministrationDto();
    }

    public List<PerformedSubstanceAdministrationDto> getPerformedSubstanceAdministrationByStudySubject(
            Ii ii) throws RemoteException {
        return new ArrayList<PerformedSubstanceAdministrationDto>();
    }

    public PerformedImagingDto updatePerformedImaging(PerformedImagingDto dto)
            throws RemoteException, DataFormatException {
        return new PerformedImagingDto();
    }

    public PerformedObservationDto updatePerformedObservation(
            PerformedObservationDto dto) throws RemoteException,
            DataFormatException {
        return new PerformedObservationDto();
    }

    public PerformedProcedureDto updatePerformedProcedure(
            PerformedProcedureDto dto) throws RemoteException,
            DataFormatException {
        return new PerformedProcedureDto();
    }

    public PerformedRadiationAdministrationDto updatePerformedRadiationAdministration(
            PerformedRadiationAdministrationDto dto) throws RemoteException,
            DataFormatException {
        return new PerformedRadiationAdministrationDto();
    }

    public PerformedSubstanceAdministrationDto updatePerformedSubstanceAdministration(
            PerformedSubstanceAdministrationDto dto) throws RemoteException,
            DataFormatException {
        return new PerformedSubstanceAdministrationDto();
    }

    public List<PerformedObservationDto> getPerformedObservationByStudySubject(
            Ii ii) throws RemoteException {
        if (ii == null) {
            throw new RemoteException("NULL argument getPerformedObservationByStudySubject");
        }

        List<PerformedObservationDto> list = new ArrayList<PerformedObservationDto>();
        for (PoSs item : listPo) {
            if (item.ss.equals(ii.getExtension())) {
                list.add(item.dto);
            }
        }
        return list;
    }
}
