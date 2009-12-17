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

import gov.nih.nci.accrual.dto.PerformedClinicalResultDto;
import gov.nih.nci.accrual.dto.PerformedDiagnosisDto;
import gov.nih.nci.accrual.dto.PerformedHistopathologyDto;
import gov.nih.nci.accrual.dto.PerformedImageDto;
import gov.nih.nci.accrual.dto.PerformedLesionDescriptionDto;
import gov.nih.nci.accrual.dto.PerformedMedicalHistoryResultDto;
import gov.nih.nci.accrual.dto.PerformedObservationDto;
import gov.nih.nci.accrual.dto.PerformedObservationResultDto;
import gov.nih.nci.accrual.service.PerformedObservationResultService;
import gov.nih.nci.accrual.web.enums.AutopsyPerformed;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Ivl;
import gov.nih.nci.coppa.iso.Pq;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.pa.enums.AutopsyDeathCause;
import gov.nih.nci.pa.enums.DeathCause;
import gov.nih.nci.pa.enums.PerformedObservationResultTypeCode;
import gov.nih.nci.pa.enums.StagingSystemCode;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * @author Kalpana Guthikonda
 * @since Nov 11, 2009
 */
public class MockPerformedObservationResultBean implements PerformedObservationResultService {

    private static HashMap<String, PerformedDiagnosisDto> hmPd = new HashMap<String, PerformedDiagnosisDto>();
    private static List<PdPa> listPa = new ArrayList<PdPa>();
    private static HashMap<String, PerformedClinicalResultDto> hmCr = new HashMap<String, PerformedClinicalResultDto>();
    private static List<CrPa> listCr = new ArrayList<CrPa>();
    private static int key = 2000;

    private List<PerformedObservationResultDto> porList;
    {
        porList = new ArrayList<PerformedObservationResultDto>();
        PerformedObservationResultDto dto = new PerformedObservationResultDto();
        dto.setIdentifier(IiConverter.convertToIi(getKey()));
        dto.setPerformedObservationIdentifier(IiConverter.convertToIi(MockPerformedActivityBean.DEATH_INFORMATIONID));
        dto.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.DEATH_CAUSE));
        dto.setResultCode(CdConverter.convertToCd(DeathCause.NEW_PRIMARY));
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        porList.add(dto);
        
        dto = new PerformedObservationResultDto();
        dto.setIdentifier(IiConverter.convertToIi(getKey()));
        dto.setPerformedObservationIdentifier(IiConverter.convertToIi(MockPerformedActivityBean.DEATH_INFORMATIONID));
        dto.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.DEATH_DATE));
        if (dto.getResultDateRange() == null) {
            dto.setResultDateRange(new Ivl<Ts>());
        }
        dto.getResultDateRange().setLow(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        porList.add(dto);
        
        dto = new PerformedObservationResultDto();
        dto.setIdentifier(IiConverter.convertToIi(getKey()));
        dto.setPerformedObservationIdentifier(IiConverter.convertToIi(MockPerformedActivityBean.AUTOPSY_INFORMATIONID));
        dto.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.AUTOPSY_PERFORMED_INDICATOR));
        dto.setResultCode(CdConverter.convertToCd(AutopsyPerformed.YES));
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        porList.add(dto);
        
        dto = new PerformedObservationResultDto();
        dto.setIdentifier(IiConverter.convertToIi(getKey()));
        dto.setPerformedObservationIdentifier(IiConverter.convertToIi(MockPerformedActivityBean.AUTOPSY_INFORMATIONID));
        dto.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.CAUSE_OF_DEATH_AS_DETERMINED_BY_AUTOPSY));
        dto.setResultCode(CdConverter.convertToCd(AutopsyDeathCause.PROTOCOL_TREATMENT));
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        porList.add(dto);     
        
        Pq pq = new Pq();
        pq.setValue(new BigDecimal("2"));
        pq.setUnit("Years");     
        
        /*dto = new PerformedObservationResultDto();
        dto.setIdentifier(IiConverter.convertToIi(getKey()));
        dto.setPerformedObservationIdentifier(IiConverter.convertToIi(MockPerformedActivityBean.HEIGHTID));
        dto.setResultQuantity(pq);
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        porList.add(dto);
        
        dto = new PerformedObservationResultDto();
        dto.setIdentifier(IiConverter.convertToIi(getKey()));
        dto.setPerformedObservationIdentifier(IiConverter.convertToIi(MockPerformedActivityBean.WEIGHTID));
        dto.setResultQuantity(pq);
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        porList.add(dto);
        
        dto = new PerformedObservationResultDto();
        dto.setIdentifier(IiConverter.convertToIi(getKey()));
        dto.setPerformedObservationIdentifier(IiConverter.convertToIi(MockPerformedActivityBean.BSAID));
        dto.setResultQuantity(pq);
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        porList.add(dto);*/
        
        dto = new PerformedObservationResultDto();
        dto.setIdentifier(IiConverter.convertToIi(getKey()));
        dto.setPerformedObservationIdentifier(IiConverter.convertToIi(MockPerformedActivityBean.TUMORMARKERID));
        dto.setResultQuantity(pq);
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        porList.add(dto);
    }
    
    private List<PerformedLesionDescriptionDto> pldList;
    {
        pldList = new ArrayList<PerformedLesionDescriptionDto>();
        PerformedLesionDescriptionDto dto = new PerformedLesionDescriptionDto();
        dto.setIdentifier(IiConverter.convertToIi(getKey()));
        dto.setPerformedObservationIdentifier(IiConverter.convertToIi(MockPerformedActivityBean.LESION_ASSESSMENTID));
        dto.setLesionNumber(IntConverter.convertToInt("1"));
        dto.setMeasurableIndicator(BlConverter.convertToBl(true));
        dto.setEvaluableIndicator(BlConverter.convertToBl(false));
        Pq longestDiameter = new Pq();
        longestDiameter.setUnit("mm");
        longestDiameter.setValue(new BigDecimal("2"));
        dto.setLongestDiameter(longestDiameter);
        Ivl<Ts> clinicalAssessmentDate = new Ivl<Ts>();
        clinicalAssessmentDate.setLow(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        dto.setResultDateRange(clinicalAssessmentDate);
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        pldList.add(dto);
    }
    private List<PerformedImageDto> piList;
    {
        piList = new ArrayList<PerformedImageDto>();
        PerformedImageDto dto = new PerformedImageDto();
        dto.setIdentifier(IiConverter.convertToIi(getKey()));
        dto.setPerformedObservationIdentifier(IiConverter.convertToIi(MockPerformedActivityBean.LESION_ASSESSMENTID2));
        Ivl<Ts> clinicalAssessmentDate = new Ivl<Ts>();
        clinicalAssessmentDate.setLow(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        dto.setResultDateRange(clinicalAssessmentDate);
        dto.setImageIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        dto.setSeriesIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        piList.add(dto);
    }
    private List<PerformedClinicalResultDto> pcrList;
    {
        pcrList = new ArrayList<PerformedClinicalResultDto>();
        PerformedClinicalResultDto dto = new PerformedClinicalResultDto();
        dto.setIdentifier(IiConverter.convertToIi(getKey()));
        dto.setPerformedObservationIdentifier(IiConverter.convertToIi(MockPerformedActivityBean.STAGINGID));
        dto.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.T));
        dto.setResultText(StConverter.convertToSt("T"));
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        dto.setStageCodingSystem(CdConverter.convertToCd(StagingSystemCode.SEER));
        pcrList.add(dto);
        
        dto = new PerformedClinicalResultDto();
        dto.setIdentifier(IiConverter.convertToIi(getKey()));
        dto.setPerformedObservationIdentifier(IiConverter.convertToIi(MockPerformedActivityBean.STAGINGID));
        dto.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.N));
        dto.setResultText(StConverter.convertToSt("N"));
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        dto.setStageCodingSystem(CdConverter.convertToCd(StagingSystemCode.SEER));
        pcrList.add(dto);
        
        dto = new PerformedClinicalResultDto();
        dto.setIdentifier(IiConverter.convertToIi(getKey()));
        dto.setPerformedObservationIdentifier(IiConverter.convertToIi(MockPerformedActivityBean.STAGINGID));
        dto.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.M));
        dto.setResultText(StConverter.convertToSt("M"));
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        dto.setStageCodingSystem(CdConverter.convertToCd(StagingSystemCode.SEER));
        pcrList.add(dto);
        
        dto = new PerformedClinicalResultDto();
        dto.setIdentifier(IiConverter.convertToIi(getKey()));
        dto.setPerformedObservationIdentifier(IiConverter.convertToIi(MockPerformedActivityBean.STAGINGID));
        dto.setTypeCode(CdConverter.convertToCd(PerformedObservationResultTypeCode.STAGE));
        dto.setResultText(StConverter.convertToSt("STAGE"));
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        dto.setStageCodingSystem(CdConverter.convertToCd(StagingSystemCode.SEER));
        pcrList.add(dto);
        
    }
    
    private class PdPa {
        public String pa;
        public PerformedDiagnosisDto dto;
        public PdPa(String pa, PerformedDiagnosisDto dto) {
            this.pa = pa;
            this.dto = dto;
        }
    }

    private class CrPa {
        public String pa;
        public PerformedClinicalResultDto dto;
        public CrPa(String pa, PerformedClinicalResultDto dto) {
            this.pa = pa;
            this.dto = dto;
        }
    }
    
    {
        if (hmPd.size() > 0) {
            hmPd = new HashMap<String, PerformedDiagnosisDto>();
            listPa = new ArrayList<PdPa>();
            hmCr = new HashMap<String, PerformedClinicalResultDto>();
            listCr = new ArrayList<CrPa>();
        }
        Ii paId;
        String id;

        PerformedDiagnosisDto pd = new PerformedDiagnosisDto();
        pd.setResultCodeModifiedText(StConverter.convertToSt("Disease"));
        pd.setResultCode(CdConverter.convertStringToCd("PD 1"));
        paId = MockPerformedActivityBean.getDiagnosisPo().getIdentifier();
        pd.setPerformedObservationIdentifier(paId);
        id = MockPerformedObservationResultBean.class.getName() + ".hmPd " + getKey();
        pd.setIdentifier(IiConverter.convertToIi(id));
        hmPd.put(id, pd);
        listPa.add(new PdPa(paId.getExtension(), pd));
        
        PerformedClinicalResultDto cr = new PerformedClinicalResultDto();
        cr.setResultCode(CdConverter.convertStringToCd("50"));
        paId = MockPerformedActivityBean.getPerformanceStatusPo().getIdentifier();
        cr.setPerformedObservationIdentifier(paId);
        id = MockPerformedObservationResultBean.class.getName() + ".hmCr " + getKey();
        cr.setIdentifier(IiConverter.convertToIi(id));
        hmCr.put(id, cr);
        listCr.add(new CrPa(paId.getExtension(), cr));
    }
    
    private synchronized String getKey() {
        return String.valueOf(++key);
    }
    
    public PerformedClinicalResultDto createPerformedClinicalResult(
            PerformedClinicalResultDto dto) throws RemoteException,
            DataFormatException {
        PerformedClinicalResultDto cr = (dto == null) ? new PerformedClinicalResultDto() : dto;
        String id = getKey();
        cr.setIdentifier(IiConverter.convertToIi(id));
        hmCr.put(id, cr);
        listCr.add(new CrPa(cr.getPerformedObservationIdentifier().getExtension(), cr));
        return cr;
    }

    public PerformedDiagnosisDto createPerformedDiagnosis(
            PerformedDiagnosisDto dto) throws RemoteException,
            DataFormatException {
        PerformedDiagnosisDto pdd = (dto == null) ? new PerformedDiagnosisDto() : dto;
        String id = getKey();
        pdd.setIdentifier(IiConverter.convertToIi(id));
        hmPd.put(id, pdd);
        listPa.add(new PdPa(pdd.getPerformedObservationIdentifier().getExtension(), pdd));
        return pdd;
    }

    public PerformedHistopathologyDto createPerformedHistopathology(
            PerformedHistopathologyDto dto) throws RemoteException,
            DataFormatException {
        return new PerformedHistopathologyDto();
    }

    public PerformedImageDto createPerformedImage(PerformedImageDto dto)
            throws RemoteException, DataFormatException {
        return new PerformedImageDto();
    }

    public PerformedLesionDescriptionDto createPerformedLesionDescription(
            PerformedLesionDescriptionDto dto) throws RemoteException,
            DataFormatException {
        return new PerformedLesionDescriptionDto();
    }

    public PerformedMedicalHistoryResultDto createPerformedMedicalHistoryResult(
            PerformedMedicalHistoryResultDto dto) throws RemoteException,
            DataFormatException {
        return new PerformedMedicalHistoryResultDto();
    }

    public PerformedClinicalResultDto getPerformedClinicalResult(Ii ii)
            throws RemoteException {
        if (ii == null) {
            throw new RemoteException("NULL argument getPerformedDiagnosis");
        }
        PerformedClinicalResultDto dto = hmCr.get(ii.getExtension());
        return (dto == null) ? new PerformedClinicalResultDto() : dto;
    }

    public List<PerformedClinicalResultDto> getPerformedClinicalResultByPerformedActivity(
            Ii ii) throws RemoteException {
        ArrayList<PerformedClinicalResultDto> list = new ArrayList<PerformedClinicalResultDto>();
        if (ii == null) {
            throw new RemoteException("NULL argument getPerformedDiagnosisByPerformedActivity");
        }
        for (CrPa item : listCr) {
            if (item.pa.equals(ii.getExtension())) {
                list.add(item.dto);
            }
        }
        for (PerformedClinicalResultDto item : pcrList) {
            if (ii.getExtension().equals(item.getPerformedObservationIdentifier().getExtension())) {
                list.add(item);
            }
        }
        return list;
    }

    public PerformedDiagnosisDto getPerformedDiagnosis(Ii ii)
            throws RemoteException {
        if (ii == null) {
            throw new RemoteException("NULL argument getPerformedDiagnosis");
        }
        PerformedDiagnosisDto dto = hmPd.get(ii.getExtension());
        return (dto == null) ? new PerformedDiagnosisDto() : dto;
    }

    public List<PerformedDiagnosisDto> getPerformedDiagnosisByPerformedActivity(
            Ii ii) throws RemoteException {
        ArrayList<PerformedDiagnosisDto> list = new ArrayList<PerformedDiagnosisDto>();
        if (ii == null) {
            throw new RemoteException("NULL argument getPerformedDiagnosisByPerformedActivity");
        }
        for (PdPa item : listPa) {
            if (item.pa.equals(ii.getExtension())) {
                list.add(item.dto);
            }
        }
        return list;
    }

    public PerformedHistopathologyDto getPerformedHistopathology(Ii ii)
            throws RemoteException {
        return new PerformedHistopathologyDto();
    }

    public List<PerformedHistopathologyDto> getPerformedHistopathologyByPerformedActivity(
            Ii ii) throws RemoteException {
        return new ArrayList<PerformedHistopathologyDto>();
    }

    public PerformedImageDto getPerformedImage(Ii ii) throws RemoteException {
        return new PerformedImageDto();
    }

    public List<PerformedImageDto> getPerformedImageByPerformedActivity(Ii ii)
            throws RemoteException {
        return piList;
    }

    public PerformedLesionDescriptionDto getPerformedLesionDescription(Ii ii)
            throws RemoteException {
        return new PerformedLesionDescriptionDto();
    }

    public List<PerformedLesionDescriptionDto> getPerformedLesionDescriptionByPerformedActivity(
            Ii ii) throws RemoteException {
        return pldList;
    }

    public PerformedMedicalHistoryResultDto getPerformedMedicalHistoryResult(
            Ii ii) throws RemoteException {
        return new PerformedMedicalHistoryResultDto();
    }

    public List<PerformedMedicalHistoryResultDto> getPerformedMedicalHistoryResultByPerformedActivity(
            Ii ii) throws RemoteException {
        return new ArrayList<PerformedMedicalHistoryResultDto>();
    }

    public PerformedClinicalResultDto updatePerformedClinicalResult(
            PerformedClinicalResultDto dto) throws RemoteException,
            DataFormatException {
        return new PerformedClinicalResultDto();
    }

    public PerformedDiagnosisDto updatePerformedDiagnosis(
            PerformedDiagnosisDto dto) throws RemoteException,
            DataFormatException {
        return new PerformedDiagnosisDto();
    }

    public PerformedHistopathologyDto updatePerformedHistopathology(
            PerformedHistopathologyDto dto) throws RemoteException,
            DataFormatException {
        return new PerformedHistopathologyDto();
    }

    public PerformedImageDto updatePerformedImage(PerformedImageDto dto)
            throws RemoteException, DataFormatException {
        return new PerformedImageDto();
    }

    public PerformedLesionDescriptionDto updatePerformedLesionDescription(
            PerformedLesionDescriptionDto dto) throws RemoteException,
            DataFormatException {
        return new PerformedLesionDescriptionDto();
    }

    public PerformedMedicalHistoryResultDto updatePerformedMedicalHistoryResult(
            PerformedMedicalHistoryResultDto dto) throws RemoteException,
            DataFormatException {
        return new PerformedMedicalHistoryResultDto();
    }

    public PerformedObservationResultDto create(
            PerformedObservationResultDto dto) throws RemoteException {
        return new PerformedObservationResultDto();
    }

    public void delete(Ii ii) throws RemoteException {
    }

    public PerformedObservationResultDto get(Ii ii) throws RemoteException {
        return new PerformedObservationResultDto();
    }

    public PerformedObservationResultDto update(
            PerformedObservationResultDto dto) throws RemoteException {
        return new PerformedObservationResultDto();
    }

    public List<PerformedObservationResultDto> getPerformedObservationResultByPerformedActivity(
            Ii ii) throws RemoteException {
        List<PerformedObservationResultDto> result = new ArrayList<PerformedObservationResultDto>();
        for (PerformedObservationResultDto dto : porList) {
            if (ii.getExtension().equals(dto.getPerformedObservationIdentifier().getExtension())) {
                result.add(dto);
            }
        }
        return result;
    }
    
}
