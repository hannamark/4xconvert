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
package gov.nih.nci.accrual.service.util;

import gov.nih.nci.accrual.convert.Converters;
import gov.nih.nci.accrual.convert.PatientConverter;
import gov.nih.nci.accrual.dto.util.POPatientDTO;
import gov.nih.nci.accrual.dto.util.PatientDto;
import gov.nih.nci.accrual.util.AccrualHibernateSessionInterceptor;
import gov.nih.nci.accrual.util.AccrualHibernateUtil;
import gov.nih.nci.accrual.util.PoRegistry;
import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.Patient;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since Aug 18, 2009
 */
@Stateless
@Interceptors(AccrualHibernateSessionInterceptor.class)
public class PatientBean implements PatientService {

    private static final Logger LOG  = Logger.getLogger(PatientBean.class);
    private SessionContext ejbContext;
    @EJB
    CountryService countryServ;
    @EJB
    PatientServiceRemote patientCorrelationSvc;
    
    @Resource
    void setSessionContext(SessionContext ctx) {
        ejbContext = ctx;
    }

    /**
     * {@inheritDoc}
     */
    public PatientDto create(PatientDto dto) throws RemoteException {
        if (!PAUtil.isIiNull(dto.getIdentifier())) {
            throw new RemoteException("Update method should be used to modify existing.");
        }        
        return createOrUpdate(dto);
    }
    
    /**
     * {@inheritDoc}
     */
    public void enforceBusinessRules(PatientDto dto) throws RemoteException {
        
        throw new RemoteException("Business Rule is violated.");
        
    }

    /**
     * {@inheritDoc}
     */
    public PatientDto get(Ii ii) throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            throw new RemoteException("Called get() with Ii == null.");
        }
        Patient bo = null;
        PatientDto resultDto = null;
        Session session = null;
        try {
            session = AccrualHibernateUtil.getCurrentSession();
            bo = (Patient) session.get(Patient.class, IiConverter.convertToLong(ii));
            if (bo == null) {
                LOG.error("Object not found using get() for id = "
                        + IiConverter.convertToString(ii) + ".");
                return resultDto;
            }
        } catch (HibernateException hbe) {
            throw new RemoteException("Hibernate exception in get().", hbe);
        }
        try {
            resultDto = Converters.get(PatientConverter.class).convertFromDomainToDto(bo);
        } catch (DataFormatException e) {
            throw new RemoteException("Iso conversion exception in get().", e);
        }
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public PatientDto update(PatientDto dto) throws RemoteException {
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            throw new RemoteException("Create method should be used to create new.");
        }        
        return createOrUpdate(dto);
    }

    private PatientDto createOrUpdate(PatientDto dto) throws RemoteException {
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
            throw new RemoteException("Iso conversion exception in createOrUpdate().", e);
        }
        PatientDto resultDto = null;
        Session session = null;
        try {
            session = AccrualHibernateUtil.getCurrentSession();
            bo.setUserLastUpdated(ejbContext != null ? ejbContext.getCallerPrincipal().getName() : "not logged");
            bo.setDateLastUpdated(new Date());
            if (PAUtil.isIiNull(dto.getIdentifier())) {
                bo.setUserLastCreated(bo.getUserLastUpdated());
                bo.setDateLastCreated(bo.getDateLastUpdated());
            } else {
                Patient oldBo = (Patient) session.get(Patient.class, IiConverter.convertToLong(dto.getIdentifier()));
                bo.setDateLastCreated(oldBo.getDateLastCreated());
                bo.setUserLastCreated(oldBo.getUserLastCreated());
                session.evict(oldBo);
            }
            bo = (Patient) session.merge(bo);
            session.flush();
        } catch (HibernateException hbe) {
            throw new RemoteException("Hibernate exception in createOrUpdate().", hbe);
        }
        try {
            resultDto = Converters.get(PatientConverter.class).convertFromDomainToDto(bo);
        } catch (DataFormatException e) {
            throw new RemoteException("Iso conversion exception in createOrUpdate().", e);
        }
        return resultDto;
    }
   
    private void updatePOPatientDetails(PatientDto dto) throws RemoteException {
        
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
    private void updatePOPatientCorrelation(PatientDto dto)
            throws RemoteException {
        POPatientDTO popDTO = new POPatientDTO();
        Ii scoper = IiConverter.convertToPoOrganizationIi(
                IiConverter.convertToString(dto.getOrganizationIdentifier()));
        scoper.setReliability(IdentifierReliability.ISS);
        popDTO.setScoperIdentifier(scoper);

        Country country = countryServ.getCountry(dto.getCountryIdentifier());
        String zip = !dto.getZip().getValue().equals("") ? dto.getZip().getValue() : "11111";   
        
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
}
