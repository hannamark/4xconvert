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
package gov.nih.nci.outcomes.svc.util;

import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.dto.util.PatientDto;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.outcomes.svc.dto.PatientSvcDto;
import gov.nih.nci.outcomes.svc.dto.TreatmentRegimenSvcDto;
import gov.nih.nci.outcomes.svc.dto.TumorMarkerSvcDto;
import gov.nih.nci.outcomes.svc.exception.OutcomesException;
import gov.nih.nci.outcomes.svc.exception.OutcomesFieldException;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Hugh Reinhart
 * @since Feb 19, 2010
 */
@SuppressWarnings({"PMD.CyclomaticComplexity" })
public class PatientSvcBean extends AbstractOutcomesBusSvcBean<PatientSvcDto> {

    private static List<Country> listOfCountries = null;
    private static PatientSvcBean instance = new PatientSvcBean();
    /** The Constant validStatusCodes. */
    private static List<String> validStatusCodes;
    static {
        validStatusCodes = new ArrayList<String>();
        validStatusCodes.add(FunctionalRoleStatusCode.PENDING.getCode());
        validStatusCodes.add(FunctionalRoleStatusCode.ACTIVE.getCode());
    }

    /**
     * @return the instance
     */
    public static PatientSvcBean getInstance() {
        return instance;
    }

    /**
     * Searches the patients.
     * @param cctx SvcContext
     * @return list of PatientSvcDto
     * @throws OutcomesException exception
     */
    public List<PatientSvcDto> search(SvcContext cctx) throws OutcomesException {
        List<PatientSvcDto> resultList = new ArrayList<PatientSvcDto>();
        try {
            List<StudySubjectDto> subList = cctx.getStudySubjectService().getOutcomes(cctx.getName());
            for (StudySubjectDto sub : subList) {
                String statusCode = CdConverter.convertCdToString(sub.getStatusCode());
                if (!validStatusCodes.contains(statusCode)) {
                    continue;
                }
                PatientDto pat = cctx.getPatientService().get(sub.getPatientIdentifier());
                resultList.add(getPatientSvcDto(sub, pat, cctx));
            }
        } catch (RemoteException e) {
            throw new OutcomesException("Error in PatientSvcBean.search().", e);
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PatientSvcDto get(PatientSvcDto dto, SvcContext cctx, Ii parentIi) throws OutcomesException {
        PatientSvcDto result = null;
        if (dto != null && !PAUtil.isIiNull(dto.getIdentifier())) {
            try {
                StudySubjectDto sub = cctx.getStudySubjectService().get(dto.getIdentifier());
                PatientDto pat = cctx.getPatientService().get(sub.getPatientIdentifier());
                result = getPatientSvcDto(sub, pat, cctx);
            } catch (RemoteException e) {
                throw new OutcomesException("Error in PatientSvcBean.get().", e);
            }
            result.setDeathInformation(DeathInformationSvcBean.getInstance().get(dto.getDeathInformation(), cctx,
                    result.getIdentifier()));
            result.setDiagnosis(DiagnosisSvcBean.getInstance().get(dto.getDiagnosis(), cctx, result.getIdentifier()));
            result.setPathology(PathologySvcBean.getInstance().get(dto.getPathology(), cctx, result.getIdentifier()));
            result.setPerformanceStatus(PerformanceStatusSvcBean.getInstance().get(dto.getPerformanceStatus(), cctx,
                    result.getIdentifier()));
            result.setPriorTherapy(PriorTherapySvcBean.getInstance().get(dto.getPriorTherapy(), cctx,
                    result.getIdentifier()));
            result.setStaging(StagingSvcBean.getInstance().get(dto.getStaging(), cctx, result.getIdentifier()));

            if (dto.getTreatmentRegimens() != null) {
                if (dto.getTreatmentRegimens().isEmpty()) {
                    result.setTreatmentRegimens(TreatmentRegimenSvcBean.getInstance().search(cctx,
                            result.getIdentifier()));
                } else {
                    List<TreatmentRegimenSvcDto> trList = new ArrayList<TreatmentRegimenSvcDto>();
                    for (TreatmentRegimenSvcDto trdto : dto.getTreatmentRegimens()) {
                        trList.add(TreatmentRegimenSvcBean.getInstance().get(trdto, cctx, result.getIdentifier()));
                    }
                    result.setTreatmentRegimens(trList);
                }
            }

            if (dto.getTumorMarkers() != null) {
                if (dto.getTumorMarkers().isEmpty()) {
                    result.setTumorMarkers(TumorMarkerSvcBean.getInstance().search(cctx, result.getIdentifier()));
                } else {
                    List<TumorMarkerSvcDto> tmList = new ArrayList<TumorMarkerSvcDto>();
                    for (TumorMarkerSvcDto tm : dto.getTumorMarkers()) {
                        tmList.add(TumorMarkerSvcBean.getInstance().get(tm, cctx, result.getIdentifier()));
                    }
                    result.setTumorMarkers(tmList);
                }
            }
        }
        return result;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public PatientSvcDto put(PatientSvcDto dto, SvcContext cctx, Ii parentIi) throws OutcomesException {
        PatientSvcDto result = super.put(dto, cctx, parentIi);
        if (result != null) {
            result.setDeathInformation(DeathInformationSvcBean.getInstance().put(dto.getDeathInformation(), cctx,
                    result.getIdentifier()));
            result.setDiagnosis(DiagnosisSvcBean.getInstance().put(dto.getDiagnosis(), cctx, result.getIdentifier()));
            result.setPathology(PathologySvcBean.getInstance().put(dto.getPathology(), cctx, result.getIdentifier()));
            result.setPerformanceStatus(PerformanceStatusSvcBean.getInstance().put(dto.getPerformanceStatus(), cctx,
                    result.getIdentifier()));
            result.setPriorTherapy(PriorTherapySvcBean.getInstance().put(dto.getPriorTherapy(), cctx,
                    result.getIdentifier()));
            result.setStaging(StagingSvcBean.getInstance().put(dto.getStaging(), cctx, result.getIdentifier()));

            if (dto.getTreatmentRegimens() != null) {
                List<TreatmentRegimenSvcDto> trList = new ArrayList<TreatmentRegimenSvcDto>();
                for (TreatmentRegimenSvcDto trdto : dto.getTreatmentRegimens()) {
                    trList.add(TreatmentRegimenSvcBean.getInstance().put(trdto, cctx, result.getIdentifier()));
                }
                result.setTreatmentRegimens(trList);
            }

            if (dto.getTumorMarkers() != null) {
                List<TumorMarkerSvcDto> tmList = new ArrayList<TumorMarkerSvcDto>();
                for (TumorMarkerSvcDto tm : dto.getTumorMarkers()) {
                    tmList.add(TumorMarkerSvcBean.getInstance().put(tm, cctx, result.getIdentifier()));
                }
                result.setTumorMarkers(tmList);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PatientSvcDto create(PatientSvcDto dto, SvcContext cctx, Ii parentIi) throws OutcomesException {
        validateNoDuplicateAssignedIdentifier(dto, cctx);
        PatientDto pat = new PatientDto();
        pat.setBirthDate(dto.getBirthDate());
        pat.setCountryIdentifier(getCountryIi(dto.getCountryAlpha3(), cctx));
        pat.setEthnicCode(dto.getEthnicCode());
        pat.setGenderCode(dto.getGenderCode());
        pat.setOrganizationIdentifier(cctx.getTreatingSiteIi());
        pat.setRaceCode(dto.getRaceCode());
        pat.setStatusCode(CdConverter.convertToCd(StructuralRoleStatusCode.PENDING));
        pat.setStatusDateRangeLow(TsConverter.convertToTs(new Timestamp(new Date().getTime())));

        StudySubjectDto ssub = new StudySubjectDto();
        ssub.setAssignedIdentifier(dto.getAssignedIdentifier());
        ssub.setOutcomesLoginName(cctx.getName());
        ssub.setPaymentMethodCode(dto.getPaymentMethodCode());
        ssub.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
        ssub.setStatusDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()), null));
        try {
            ssub.setStudyProtocolIdentifier(cctx.getSearchTrialService().getOutcomesStudyProtocolIi());
            pat = cctx.getPatientService().create(pat);
            ssub.setPatientIdentifier(pat.getIdentifier());
            ssub = cctx.getStudySubjectService().createOutcomes(ssub);
        } catch (RemoteException e) {
            throw new OutcomesException("Error in PatientSvcBean.create().", e);
        }
        dto.setIdentifier(ssub.getIdentifier());
        cctx.setStudySubjectIi(dto.getIdentifier());
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PatientSvcDto delete(PatientSvcDto dto, SvcContext cctx, Ii parentIi) throws OutcomesException {
        StudySubjectDto ssub = null;
        try {
            ssub = cctx.getStudySubjectService().get(dto.getIdentifier());
        } catch (RemoteException e) {
            throw new OutcomesException("Error retrieving before data in PatientSvcBean.delete().", e);
        }
        ssub.setStatusCode(CdConverter.convertStringToCd(FunctionalRoleStatusCode.TERMINATED.getCode()));
        ssub.getStatusDateRange().setHigh(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        try {
            cctx.getStudySubjectService().update(ssub);
        } catch (RemoteException e) {
            throw new OutcomesException("Error updating StudySubject in PatientSvcBean.delete().", e);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PatientSvcDto update(PatientSvcDto dto, SvcContext cctx, Ii parentIi) throws OutcomesException {
        validateNoDuplicateAssignedIdentifier(dto, cctx);
        StudySubjectDto ssub = null;
        PatientDto pat = null;
        try {
            ssub = cctx.getStudySubjectService().get(dto.getIdentifier());
            pat = cctx.getPatientService().get(ssub.getPatientIdentifier());
        } catch (RemoteException e) {
            throw new OutcomesException("Error retrieving before data in PatientSvcBean.update().", e);
        }
        ssub.setAssignedIdentifier(dto.getAssignedIdentifier());
        ssub.setPaymentMethodCode(dto.getPaymentMethodCode());
        pat.setBirthDate(dto.getBirthDate());
        pat.setCountryIdentifier(getCountryIi(dto.getCountryAlpha3(), cctx));
        pat.setEthnicCode(dto.getEthnicCode());
        pat.setGenderCode(dto.getGenderCode());
        pat.setOrganizationIdentifier(cctx.getTreatingSiteIi());
        pat.setRaceCode(dto.getRaceCode());
        try {
            pat = cctx.getPatientService().update(pat);
            ssub = cctx.getStudySubjectService().update(ssub);
        } catch (RemoteException e) {
            throw new OutcomesException("Error updating data in PatientSvcBean.update().", e);
        }
        return dto;
    }

    private Ii getCountryIi(St countryAlpha3, SvcContext cctx) throws OutcomesException {
        Ii result = null;
        String alpha3 = StConverter.convertToString(countryAlpha3);
        if (listOfCountries == null) {
            try {
                listOfCountries = cctx.getCountryService().getCountries();
            } catch (RemoteException e) {
                throw new OutcomesException("Exception returning country list to use for lookup.", e);
            }
        }
        for (Country c : listOfCountries) {
            if (alpha3 != null && alpha3.equals(c.getAlpha3())) {
                result = IiConverter.convertToCountryIi(c.getId());
            }
        }
        return result;
    }

    private St getCountryAlpha3(Ii countryIi, SvcContext cctx) throws OutcomesException {
        St result = null;
        if (listOfCountries == null) {
            try {
                listOfCountries = cctx.getCountryService().getCountries();
            } catch (RemoteException e) {
                throw new OutcomesException("Exception returning country list to use for lookup.", e);
            }
        }
        Long countryId = IiConverter.convertToLong(countryIi);
        for (Country c : listOfCountries) {
            if (countryId != null && countryId.equals(c.getId())) {
                result = StConverter.convertToSt(c.getAlpha3());
            }
        }
        return result;
    }

    private PatientSvcDto getPatientSvcDto(StudySubjectDto ss, PatientDto p, SvcContext cctx) throws OutcomesException {
        PatientSvcDto result = new PatientSvcDto();
        result.setAssignedIdentifier(ss.getAssignedIdentifier());
        result.setBirthDate(p.getBirthDate());
        result.setCountryAlpha3(getCountryAlpha3(p.getCountryIdentifier(), cctx));
        result.setEthnicCode(p.getEthnicCode());
        result.setGenderCode(p.getGenderCode());
        result.setIdentifier(ss.getIdentifier());
        result.setPaymentMethodCode(ss.getPaymentMethodCode());
        result.setRaceCode(p.getRaceCode());
        return result;
    }

    private void validateNoDuplicateAssignedIdentifier(PatientSvcDto dto, SvcContext cctx) throws OutcomesException {
        St newIdent = dto.getAssignedIdentifier();
        Ii newIi = dto.getIdentifier();
        List<PatientSvcDto> pList = search(cctx);
        for (PatientSvcDto p : pList) {
            St existingIdent = p.getAssignedIdentifier();
            Ii existingIi = p.getIdentifier();
            if (existingIdent.getValue().equals(newIdent.getValue())
                    && (PAUtil.isIiNull(newIi) || !existingIi.getExtension().equals(newIi.getExtension()))) {
                throw new OutcomesFieldException(dto.getClass(), "assignedIdentifier",
                        "This Patient Id (" + StConverter.convertToString(newIdent)
                        + ") has already been added to this study.");
             }
        }
    }
}
