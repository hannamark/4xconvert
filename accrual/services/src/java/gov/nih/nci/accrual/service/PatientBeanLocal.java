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
package gov.nih.nci.accrual.service;

import gov.nih.nci.accrual.convert.Converters;
import gov.nih.nci.accrual.convert.PatientConverter;
import gov.nih.nci.accrual.dto.util.POPatientDTO;
import gov.nih.nci.accrual.dto.util.PatientDto;
import gov.nih.nci.accrual.service.util.AccrualCsmUtil;
import gov.nih.nci.accrual.service.util.CountryService;
import gov.nih.nci.accrual.service.util.POPatientService;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.accrual.util.PoRegistry;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.Patient;
import gov.nih.nci.pa.enums.PatientRaceCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.DSetEnumConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.zip.DataFormatException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since Aug 18, 2009
 */
@Stateless
@Interceptors(PaHibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PatientBeanLocal implements PatientServiceLocal {

    private static final Logger LOG  = Logger.getLogger(PatientBeanLocal.class);
    @EJB
    private CountryService countryService;
    @EJB
    private POPatientService patientCorrelationSvc;

    /**
     * {@inheritDoc}
     */
    public PatientDto create(PatientDto dto) throws PAException {
        if (!ISOUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Update method should be used to modify existing.");
        }
        return createOrUpdate(dto);
    }
    
    /**
     * @param dto
     * @throws PAException
     */
    void enforceBusinessRules(PatientDto dto) throws PAException {
        Set<String> raceCodes = DSetEnumConverter.convertDSetToSet(dto.getRaceCode());
        boolean containsUnique = false;
        for (String raceCode : raceCodes) {
            if (PatientRaceCode.getByCode(raceCode).isUnique()) {
                containsUnique = true;
            }
        }
        if (raceCodes.size() > 1 && containsUnique) {
           throw new PAException("Business rule is violated. No multiple selection"
                + " when race code is Not Reported or Unknown.");
        }
    }

    /**
     * {@inheritDoc}
     */
    public PatientDto get(Ii ii) throws PAException {
        if (ISOUtil.isIiNull(ii)) {
            throw new PAException("Called get() with Ii == null.");
        }
        Patient bo = null;
        PatientDto resultDto = null;
        Session session = null;
        try {
            session = PaHibernateUtil.getCurrentSession();
            bo = (Patient) session.get(Patient.class, IiConverter.convertToLong(ii));
            if (bo == null) {
                LOG.error("Object not found using get() for id = "
                        + IiConverter.convertToString(ii) + ".");
                return resultDto;
            }
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in get().", hbe);
        }
        try {
            resultDto = Converters.get(PatientConverter.class).convertFromDomainToDto(bo);
        } catch (DataFormatException e) {
            throw new PAException("Iso conversion exception in get().", e);
        }
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public PatientDto update(PatientDto dto) throws PAException {
        if (ISOUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Create method should be used to create new.");
        }
        return createOrUpdate(dto);
    }

    private PatientDto createOrUpdate(PatientDto dto) throws PAException {
        enforceBusinessRules(dto);
        Patient bo = convertDtoToDomain(dto);
        bo = setDomainAuditFields(dto, bo);
        return convertDomainToDTO(bo);
    }

    private PatientDto convertDomainToDTO(Patient bo) throws PAException {
        PatientDto resultDto = null;
        try {
            resultDto = Converters.get(PatientConverter.class).convertFromDomainToDto(bo);
        } catch (DataFormatException e) {
            throw new PAException("Iso conversion exception in createOrUpdate().", e);
        }
        return resultDto;
    }

    private Patient setDomainAuditFields(PatientDto dto, Patient bo) throws PAException {
        Session session = null;
        Patient returnBo = null;
        try {
            session = PaHibernateUtil.getCurrentSession();
            bo.setUserLastUpdated(AccrualCsmUtil.getInstance().getCSMUser(CaseSensitiveUsernameHolder.getUser()));
            bo.setDateLastUpdated(new Date());
            if (ISOUtil.isIiNull(dto.getIdentifier())) {
                bo.setUserLastCreated(bo.getUserLastUpdated());
                bo.setDateLastCreated(bo.getDateLastUpdated());
            } else {
                Patient oldBo = (Patient) session.get(Patient.class, IiConverter.convertToLong(dto.getIdentifier()));
                bo.setDateLastCreated(oldBo.getDateLastCreated());
                bo.setUserLastCreated(oldBo.getUserLastCreated());
                session.evict(oldBo);
            }
            returnBo = (Patient) session.merge(bo);
            session.flush();
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in createOrUpdate().", hbe);
        }
        return returnBo;
    }

    private Patient convertDtoToDomain(PatientDto dto) throws PAException {
        Patient bo = null;
        try {
            updatePOPatientCorrelation(dto);
            bo = Converters.get(PatientConverter.class).convertFromDtoToDomain(dto);
            bo.setIdentifier(IiConverter.convertToString(dto.getAssignedIdentifier()));
           //PO generated Player identifier
            bo.setPersonIdentifier(IiConverter.convertToString(dto.getPersonIdentifier()));
            updatePOPatientDetails(dto);
            bo.setStatusCode(StructuralRoleStatusCode.PENDING);
            bo.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        } catch (DataFormatException e) {
            throw new PAException("Iso conversion exception in createOrUpdate().", e);
        }
        return bo;
    }

    private void updatePOPatientDetails(PatientDto dto) {
        gov.nih.nci.services.person.PersonDTO poPersonDTO = new gov.nih.nci.services.person.PersonDTO();
        try {
            PersonEntityServiceRemote peService = PoRegistry.getPersonEntityService();
             Ii personID = IiConverter.convertToPoPersonIi(dto.getPersonIdentifier().getExtension());
             poPersonDTO = peService.getPerson(personID);
        } catch (NullifiedEntityException e) {
            LOG.info("This Person is nullified " + dto.getPersonIdentifier().getExtension());
        }

        poPersonDTO.setBirthDate(dto.getBirthDate());
        poPersonDTO.setRaceCode(dto.getRaceCode());
        poPersonDTO.setSexCode(dto.getGenderCode());
        List<Cd> cds = new ArrayList<Cd>();
        cds.add(dto.getEthnicCode());
        poPersonDTO.setEthnicGroupCode(DSetConverter.convertCdListToDSet(cds));
       try {
            PoRegistry.getPersonEntityService().updatePerson(poPersonDTO);
        } catch (EntityValidationException e) {
            LOG.info("EntityValidationException in updatePOPatientDetails:  " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void updatePOPatientCorrelation(PatientDto dto) throws PAException {
        POPatientDTO popDTO = new POPatientDTO();
        Ii scoper = IiConverter.convertToPoOrganizationIi(
                IiConverter.convertToString(dto.getOrganizationIdentifier()));
        scoper.setReliability(IdentifierReliability.ISS);
        popDTO.setScoperIdentifier(scoper);

        Country country = countryService.getCountry(dto.getCountryIdentifier());
        String zip = ISOUtil.isStNull(dto.getZip()) ? "11111" : StConverter.convertToString(dto.getZip());

        Ad ad = AddressConverterUtil.create("Street", null, "City", "VA", zip, country.getAlpha3());
        popDTO.setPostalAddress(new DSet<Ad>());
        popDTO.getPostalAddress().setItem(Collections.singleton(ad));

        POPatientDTO poPatientDTO = null;
            if (dto.getAssignedIdentifier() != null && dto.getAssignedIdentifier().getExtension() != null) {
                poPatientDTO = patientCorrelationSvc.get(IiConverter.convertToPOPatientIi(
                    IiConverter.convertToLong(dto.getAssignedIdentifier())));
                poPatientDTO.setScoperIdentifier(scoper);
                poPatientDTO.setPostalAddress(popDTO.getPostalAddress());
                popDTO = patientCorrelationSvc.update(poPatientDTO);
            } else {
                popDTO = patientCorrelationSvc.create(popDTO);
            }
        dto.setAssignedIdentifier(popDTO.getIdentifier());
        dto.setPersonIdentifier(popDTO.getPlayerIdentifier());
    }

    /**
     * @param countryService the countryService to set
     */
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * @param patientCorrelationSvc the patientCorrelationSvc to set
     */
    public void setPatientCorrelationSvc(POPatientService patientCorrelationSvc) {
        this.patientCorrelationSvc = patientCorrelationSvc;
    }
}
