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
package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * Syncrhonization service bean for Person and its structural roles.
 *
 * @author Naveen Amiruddin
 * @since 07/07/2007 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@SuppressWarnings({ "PMD.TooManyMethods" , "PMD.PreserveStackTrace" , "PMD.ExcessiveMethodLength" })
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PersonSynchronizationServiceBean implements PersonSynchronizationServiceRemote {

    private static final Logger LOG  = Logger.getLogger(PersonSynchronizationServiceBean.class);
    private static  CorrelationUtils cUtils = new CorrelationUtils();
    private SessionContext ejbContext;
    @EJB
    StudyContactServiceLocal scLocal = null;

    @Resource
    void setSessionContext(SessionContext ctx) {
    this.ejbContext = ctx;
    }

    /**
     *
     * @param perIdentifer ii of Person
     * @throws PAException on error
     */
    
    public void synchronizePerson(Ii perIdentifer) throws PAException {

        PersonDTO personDto = null;
        LOG.debug("Entering synchronizePerson");
        try {
            personDto = PoRegistry.getPersonEntityService().getPerson(perIdentifer);
            updatePerson(personDto);
        } catch (NullifiedEntityException e) {
           LOG.error("This Organization is nullified " + perIdentifer.getExtension());
           //nulifyPerson(perIdentifer);
        }
        LOG.debug("Leaving synchronizePerson");
    }

    /***
     * @return List list of sp ids
     * @param crsIdentifier po ClinicalResearchStaff identifier
     * @throws PAException on error
     */
    public List<Long> synchronizeClinicalResearchStaff(Ii crsIdentifier) throws PAException {

        ClinicalResearchStaffDTO crsDto = null;
        LOG.debug("Entering synchronizeClinicalResearchStaff");
        List<Long> spIds = null;
        try {
            crsDto = PoRegistry.getClinicalResearchStaffCorrelationService().getCorrelation(crsIdentifier);
            updateClinicalResearchStaff(crsIdentifier , crsDto);
        } catch (NullifiedRoleException e) {
           LOG.info("This ClinicalResearchStaff is nullified " + crsIdentifier.getExtension());
           updateClinicalResearchStaff(crsIdentifier , null);
        }
        LOG.debug("Leaving synchronizeClinicalResearchStaff");
        return spIds;
    }
    
    /***
     * @return List list of sp ids
     * @param hcpIdentifier po HealthCareProvider identifier
     * @throws PAException on error
     */
    public List<Long> synchronizeHealthCareProvider(Ii hcpIdentifier) throws PAException {

        HealthCareProviderDTO hcpDto = null;
        LOG.debug("Entering synchronizeHealthCareProvider");
        List<Long> spIds = null;
        try {
            hcpDto = PoRegistry.getHealthCareProviderCorrelationService().getCorrelation(hcpIdentifier);
            updateHealthCareProvider(hcpIdentifier , hcpDto);
        } catch (NullifiedRoleException e) {
           LOG.info("This HealthCareProvider is nullified " + hcpIdentifier.getExtension());
           updateHealthCareProvider(hcpIdentifier , null);

        }
        LOG.debug("Leaving synchronizeHealthCareProvider");
        return spIds;
    }

    private void updateClinicalResearchStaff(Ii crsIdentifier , ClinicalResearchStaffDTO crsDto) throws PAException {
        ClinicalResearchStaff crs = new ClinicalResearchStaff();
        crs.setIdentifier(crsIdentifier.getExtension());
        crs = cUtils.getPAClinicalResearchStaff(crs);
        Session session = null;
        StructuralRoleStatusCode newRoleCode = null;
        if (crs != null) {
            // process only if pa has any clinical research staff records
            session = HibernateUtil.getCurrentSession();
            if (crsDto == null) { 
                // this is a nullified, so treat it in a special manner
                // step 1: get the po person,org identifier (player)
                Long paOrgId = crs.getOrganization().getId();
                Long paPerId = crs.getPerson().getId();
                Long duplicateCrsId = null;
                String poOrgId = cUtils.getPAOrganizationByIndetifers(paOrgId, null).getIdentifier();
                String poPerId = cUtils.getPAPersonByIndetifers(paPerId, null).getIdentifier();
                PersonDTO personDto = getPoPerson(poPerId);
                OrganizationDTO organizationDto = getPoOrganization(poOrgId);
                if (personDto != null && organizationDto != null) {
                    // create a new crs 
                    ClinicalResearchStaffCorrelationServiceBean crsBean = 
                        new ClinicalResearchStaffCorrelationServiceBean();
                    duplicateCrsId = crsBean.createClinicalResearchStaffCorrelations(
                            organizationDto.getIdentifier().getExtension(), personDto.getIdentifier().getExtension());
                    ClinicalResearchStaff dupCrs = new ClinicalResearchStaff();
                    dupCrs.setId(duplicateCrsId);
                    dupCrs = cUtils.getPAClinicalResearchStaff(dupCrs);
                    newRoleCode = dupCrs.getStatusCode();
                    // replace the old, with the new change identifiers
                    replaceStudyContactIdentifiers(
                                IiConverter.converToPoClinicalResearchStaffIi(crs.getId().toString()), 
                                IiConverter.converToPoClinicalResearchStaffIi(duplicateCrsId.toString()));
                    // nullify the current 
                    crs.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
                } else {
                    // this is nullified scenario with no org or person associated, in that case nullify the role
                    crs.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
                    newRoleCode = StructuralRoleStatusCode.NULLIFIED;
                }
            } else if (!crs.getStatusCode().equals(cUtils.convertPORoleStatusToPARoleStatus(crsDto.getStatus()))) {
                // this is a update scenario with a status change
                newRoleCode = cUtils.convertPORoleStatusToPARoleStatus(crsDto.getStatus());
                crs.setStatusCode(newRoleCode);
                
            }
            crs.setDateLastUpdated(new Timestamp((new Date()).getTime()));
            session.update(crs);
            scLocal.cascadeRoleStatus(crsIdentifier, CdConverter.convertToCd(newRoleCode));
        }
        
    }
    
    private void updateHealthCareProvider(Ii hcpIdentifier , HealthCareProviderDTO hcpDto) throws PAException {
        HealthCareProvider hcp = new HealthCareProvider();
        hcp.setIdentifier(hcpIdentifier.getExtension());
        hcp = cUtils.getPAHealthCareProvider(hcp);
        Session session = null;
        StructuralRoleStatusCode newRoleCode = null;
        if (hcp != null) {
            // process only if pa has any clinical research staff records
            session = HibernateUtil.getCurrentSession();
            if (hcpDto == null) { 
                // this is a nullified, so treat it in a special manner
                // step 1: get the po person,org identifier (player)
                Long paOrgId = hcp.getOrganization().getId();
                Long paPerId = hcp.getPerson().getId();
                Long duplicateHcpId = null;
                String poOrgId = cUtils.getPAOrganizationByIndetifers(paOrgId, null).getIdentifier();
                String poPerId = cUtils.getPAPersonByIndetifers(paPerId, null).getIdentifier();
                PersonDTO personDto = getPoPerson(poPerId);
                OrganizationDTO organizationDto = getPoOrganization(poOrgId);
                if (personDto != null && organizationDto != null) {
                    // create a new hcp 
                    HealthCareProviderCorrelationBean hcpBean = new HealthCareProviderCorrelationBean();
                    duplicateHcpId = hcpBean.createHealthCareProviderCorrelationBeans(
                            organizationDto.getIdentifier().getExtension(), personDto.getIdentifier().getExtension());
                    HealthCareProvider dupHcp = new HealthCareProvider();
                    dupHcp.setId(duplicateHcpId);
                    dupHcp = cUtils.getPAHealthCareProvider(dupHcp);
                    newRoleCode = dupHcp.getStatusCode();
                    // replace the old, with the new change identifiers
                    replaceStudyContactIdentifiers(
                                IiConverter.converToPoHealtcareProviderIi(hcp.getId().toString()), 
                                IiConverter.converToPoHealtcareProviderIi(duplicateHcpId.toString()));
                    // nullify the current 
                    hcp.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
                } else {
                    // this is nullified scenario with no org or person associated, in that case nullify the role
                    hcp.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
                    newRoleCode = StructuralRoleStatusCode.NULLIFIED;
                }
            } else if (!hcp.getStatusCode().equals(cUtils.convertPORoleStatusToPARoleStatus(hcpDto.getStatus()))) {
                // this is a update scenario with a status change
                newRoleCode = cUtils.convertPORoleStatusToPARoleStatus(hcpDto.getStatus());
                hcp.setStatusCode(newRoleCode);
            }
            hcp.setDateLastUpdated(new Timestamp((new Date()).getTime()));
            session.update(hcp);
            scLocal.cascadeRoleStatus(hcpIdentifier, CdConverter.convertToCd(newRoleCode));
        }
        
    }

    private void replaceStudyContactIdentifiers(Ii from  , Ii to) {

        String sql = null;
        if (IiConverter.CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME.equals(from.getIdentifierName())) {    
            sql = "update STUDY_CONTACT set clinical_research_staff_identifier = " + to.getExtension() 
            + " where clinical_research_staff_identifier = " + from.getExtension();
            
        }

        Session session = HibernateUtil.getCurrentSession();
        int i = session.createSQLQuery(sql).executeUpdate();
        LOG.info("nullified crs indentifier is " + from.getExtension());
        LOG.info("duplicate hcf indentifier is " + to.getExtension());
        LOG.info("total records got update in STUDY_CONTACT IS " + i);  
    }    
    
    private PersonDTO getPoPerson(String poPersonIdentifier) throws PAException {

        PersonDTO personDto = null;
        Ii personIi = null;
        try {
            personDto = PoRegistry.getPersonEntityService().getPerson(
                     IiConverter.converToPoPersonIi(poPersonIdentifier));
        } catch (NullifiedEntityException e) {
               // org is nullified, find out if it has any duplicates
            //personIi = e.getNullifiedEntities().get(IiConverter.converToPoPersonIi(poPersonIdentifier));
            personIi = IiConverter.converToPoPersonIi("1428");
            if (personIi != null) {
                   try {
                       personDto = PoRegistry.getPersonEntityService().getPerson(personIi);
                   } catch (NullifiedEntityException e1) {
                       // TODO refactor the code to handle chain of nullified entities ... Naveen Amiruddin
                       throw new PAException("This scenario is currrently not hanndled .... " 
                               + "Duplicate Ii of nullified is also nullified" , e1);
                   }
               }
       }
       return personDto; 
    }

    private OrganizationDTO getPoOrganization(String poOrganizationIdentifier) throws PAException {

        OrganizationDTO organizationDto = null;
        Ii organizationIi = null;
        try {
            organizationDto = PoRegistry.getOrganizationEntityService().getOrganization(
                     IiConverter.converToPoOrganizationIi(poOrganizationIdentifier));
        } catch (NullifiedEntityException e) {
               // org is nullified, find out if it has any duplicates
            organizationIi = e.getNullifiedEntities().get(IiConverter.converToPoPersonIi(poOrganizationIdentifier));
            if (organizationIi != null) {
                   try {
                       organizationDto = PoRegistry.getOrganizationEntityService().getOrganization(organizationIi);
                   } catch (NullifiedEntityException e1) {
                       // TODO refactor the code to handle chain of nullified entities ... Naveen Amiruddin
                       throw new PAException("This scenario is currrently not hanndled .... " 
                               + "Duplicate Ii of nullified is also nullified" , e1);
                   }
               }
       }
       return organizationDto; 
    }
    


    /***
     * OrganizationalContact.
     * @return List list of sp ids
     * @param ocIdentifer oc HealthCareProvider identifier
     * @throws PAException on error
     */
    public  List<Long> synchronizeOrganizationalContact(Ii ocIdentifer) throws PAException {

        OrganizationalContactDTO ocDto = null;
        LOG.debug("Entering synchronizeOrganizationalContact");
        List<Long> spIds = null;
        try {
            ocDto = PoRegistry.getOrganizationalContactCorrelationService().getCorrelation(ocIdentifer);
            updateOrganizationalContact(ocDto);
        } catch (NullifiedRoleException e) {
           LOG.error("This OrganizationalContact is nullified " + ocIdentifer.getExtension());
           nulifyOrganizationalContact(ocIdentifer);
        }
        LOG.debug("Leaving synchronizeOrganizationalContact");
        return spIds;
    }

    private void updatePerson(PersonDTO perDto) throws PAException {
        LOG.debug("Entering updatePerson");
        Person per = cUtils.getPAPersonByIndetifers(null, perDto.getIdentifier().getExtension());
        if (per != null) {
            // update the organization
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                Person person = (Person) session.get(Person.class, per.getId());
                per = cUtils.convertPOToPAPerson(perDto);
                person.setFirstName(per.getFirstName());
                person.setLastName(per.getLastName());
                person.setMiddleName(per.getMiddleName());
                person.setStatusCode(per.getStatusCode());
                person.setDateLastUpdated(new Timestamp((new Date()).getTime()));
                if (ejbContext != null) {
                    per.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
                }
                session.update(person);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while updating Person for id = " + per.getId() , hbe);
            }
        }
        LOG.debug("Leaving updateOrganization");
    }


    private void nulifyOrganizationalContact(Ii ocIdentifer) throws PAException {
        LOG.debug("Entering nulifyOrganizationalContact");
        OrganizationalContact oc = new OrganizationalContact();
        oc.setIdentifier(ocIdentifer.getExtension());
        oc = cUtils.getPAOrganizationalContact(oc);
        if (oc != null) {
            // delete the hcf and all of on delete cascade will delete the entire child
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                OrganizationalContact organizationalContact =
                        (OrganizationalContact) session.get(OrganizationalContact.class, oc.getId());
                session.delete(organizationalContact);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while deleting OrganizationalContact for id = "
                        + oc.getId() , hbe);
            }
        }
        LOG.debug("Leaving nulifyOrganizationalContact");
    }

    private void updateOrganizationalContact(OrganizationalContactDTO ocDto) throws PAException {
        LOG.debug("Entering updateOrganizationalContact");
        OrganizationalContact oc = new OrganizationalContact();
        oc.setIdentifier(ocDto.getIdentifier().getExtension());
        oc = cUtils.getPAOrganizationalContact(oc);
        if (oc != null) {
            // update the organization
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                OrganizationalContact organizationalContact = (OrganizationalContact)
                    session.get(OrganizationalContact.class, oc.getId());
                organizationalContact.setStatusCode(cUtils.convertPORoleStatusToPARoleStatus(ocDto.getStatus()));
                organizationalContact.setDateLastUpdated(new Timestamp((new Date()).getTime()));
                if (ejbContext != null) {
                    organizationalContact.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
                }
                session.update(organizationalContact);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while updating OrganizationalContact for id = "
                        + oc.getId() , hbe);
            }
        }
        LOG.debug("Leaving updateOrganizationalContact");
    }


}
