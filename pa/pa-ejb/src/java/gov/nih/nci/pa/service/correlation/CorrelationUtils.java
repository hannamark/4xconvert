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

import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.AdxpCnt;
import gov.nih.nci.coppa.iso.AdxpCty;
import gov.nih.nci.coppa.iso.AdxpSta;
import gov.nih.nci.coppa.iso.AdxpZip;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.AbstractEntity;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.StructuralRole;
import gov.nih.nci.pa.dto.PAContactDTO;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.List;

import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * A Service class for handing common methods for all correlations.
 *
 * @author Naveen Amiruddin
 * @since 04/11/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Interceptors(HibernateSessionInterceptor.class)
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveMethodLength", "PMD.CyclomaticComplexity",
        "PMD.ExcessiveClassLength", "PMD.NPathComplexity" })
public class CorrelationUtils implements CorrelationUtilsRemote {
    private static final Logger LOG = Logger.getLogger(CorrelationUtils.class);

    /**
     * @param poOrganizationalContactId id
     * @return Person
     * @throws PAException e
     * @throws NullifiedRoleException ex
     */
    public PAContactDTO getContactByPAOrganizationalContactId(Long poOrganizationalContactId) throws PAException,
        NullifiedRoleException {
        if (poOrganizationalContactId == null) {
            LOG.error("Po Organization Identifier is null ");
            throw new PAException("Po Organization Identifier is null  ");
        }
        PAContactDTO returnDto = new PAContactDTO();
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            OrganizationalContact exampleBo  = new OrganizationalContact();
            exampleBo.setIdentifier(poOrganizationalContactId.toString());
            Example example = Example.create(exampleBo);
            Criteria criteria = session.createCriteria(OrganizationalContact.class).add(example);
            List<OrganizationalContact> ocs = criteria.list();
            if (ocs.isEmpty()) {
                String errMsg = "Object not found using getPAPersonByPAOrganizationalContactId() for id = "
                    + poOrganizationalContactId + ".  ";
                LOG.error(errMsg);
                throw new PAException(errMsg);
            }
            OrganizationalContact oc = ocs.get(0);

            if (oc.getStatusCode().getCode().equals(StructuralRoleStatusCode.NULLIFIED.getCode())) {
                returnDto.setTitle(StructuralRoleStatusCode.NULLIFIED.getDisplayName());
            } else {
                Person paPerson = oc.getPerson();
                if (paPerson != null) {
                    returnDto.setFullName(paPerson.getFullName());
                    returnDto.setPersonIdentifier(IiConverter.convertToIi(paPerson.getIdentifier()));
                } else {
                    //this means this is genericOrgContact
                    OrganizationalContactDTO isoDto = PoRegistry.getOrganizationalContactCorrelationService()
                        .getCorrelation(IiConverter.convertToPoOrganizationalContactIi(
                                oc.getIdentifier()));
                    returnDto.setTitle(StConverter.convertToString(isoDto.getTitle()));
                    returnDto.setSrIdentifier(DSetConverter.convertToIi(isoDto.getIdentifier()));
                }
            }
        } catch (HibernateException hbe) {
            LOG.error("Hibernate exception in getPAPersonByPAOrganizationalContactId().  ", hbe);
            throw new PAException("Hibernate exception in getPAPersonByPAOrganizationalContact().  ", hbe);
        }
        return returnDto;
    }


    /**
     * 
     * @param isoIi iso Identifier
     * @return Organization
     * @throws PAException on error
     */
    public Organization getPAOrganizationByIi(Ii isoIi) throws PAException {
        if (PAUtil.isIiNull(isoIi)) {
            throw new PAException("orgStructuralRoleIi is null  ");
        }
        StringBuffer hql = new StringBuffer();
        Organization org = null;
        hql.append(" select org from Organization org ");
        if (IiConverter.HEALTH_CARE_FACILITY_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            hql.append("join org.healthCareFacilities as role where role.identifier = '" + isoIi.getExtension() + "'"); 
        } else if (IiConverter.RESEARCH_ORG_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            hql.append("join org.researchOrganizations as role where role.identifier = '" 
                    + isoIi.getExtension() + "'"); 
        } else if (IiConverter.OVERSIGHT_COMMITTEE_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            hql.append("join org.oversightCommittees as role where role.identifier = '" + isoIi.getExtension() + "'");
        } else if (IiConverter.ORG_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            hql.append(" where org.identifier = '" + isoIi.getExtension() + "'"); 
        } else if (IiConverter.ORG_PA_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            hql.append(" where org.id = ").append(isoIi.getExtension());       
        } else {
            throw new PAException(" unknown identifier name provided  : " + isoIi.getIdentifierName());
        }

        List<Organization> queryList = HibernateUtil.getCurrentSession().createQuery(hql.toString()).list();
        if (queryList.size() > 1) {
            throw new PAException("  Organization  should not be more than 1 record for a Po Identifier  " 
                    + isoIi.getExtension() + isoIi.getIdentifierName());
        }
        if (!queryList.isEmpty()) {
            org = queryList.get(0);
        }
        return org;

    }
    
    /**
     * 
     * @param isoIi iso Identifier
     * @return Organization
     * @throws PAException on error
     */
    public Person getPAPersonByIi(Ii isoIi) throws PAException {
        if (PAUtil.isIiNull(isoIi)) {
            throw new PAException("isoIi is null  ");
        }
        StringBuffer hql = new StringBuffer();
        hql.append(" select per from Person per ");
        if (IiConverter.CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            hql.append("join per.clinicalResearchStaffs as role where role.identifier = '" 
                    + isoIi.getExtension() + "'");
        } else if (IiConverter.HEALTH_CARE_PROVIDER_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            hql.append("join per.healthCareProviders as role where role.identifier = '" + isoIi.getExtension() + "'");
        } else if (IiConverter.PERSON_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            hql.append(" where per.identifier = '" + isoIi.getExtension() + "'"); 
        } else if (IiConverter.PERSON_PA_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            hql.append(" where per.id = ").append(isoIi.getExtension()); 
        } else {
            throw new PAException(" unknown identifier name provided  : " + isoIi.getIdentifierName());
        }

        List<Person> queryList = HibernateUtil.getCurrentSession().createQuery(hql.toString()).list();
        if (queryList.size() > 1) {
            throw new PAException(" Person  should not be more than 1 record for a Po Identifier = " 
                    + isoIi.getExtension());
        }
        return queryList.get(0);

    }
    /**
     * 
     * @param <T> any class extends {@link StructuralRole} 
     * @param isoIi iso identitifier
     * @return StucturalRole class for an correspondong iso ii
     * @throws PAException on error
     */

    public <T extends StructuralRole> T getStructuralRoleByIi(Ii isoIi) throws PAException {
        
        StringBuffer hql = new StringBuffer("select role from ");
        if (IiConverter.HEALTH_CARE_FACILITY_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            hql.append("HealthCareFacility role where role.identifier = '" + isoIi.getExtension() + "'"); 
        } else if (IiConverter.RESEARCH_ORG_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            hql.append("ResearchOrganization role where role.identifier = '" + isoIi.getExtension() + "'");
        } else if (IiConverter.OVERSIGHT_COMMITTEE_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            hql.append("OversightCommittee role where role.identifier = '" + isoIi.getExtension() + "'");
        } else if (IiConverter.CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            hql.append("ClinicalResearchStaff role where role.identifier = '" + isoIi.getExtension() + "'");
        } else if (IiConverter.HEALTH_CARE_PROVIDER_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            hql.append("HealthCareProvider role where role.identifier = '" + isoIi.getExtension() + "'");   
        } else if (IiConverter.ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            hql.append("HealthCareProvider role where role.identifier = '" + isoIi.getExtension() + "'");   
        } else {
            throw new PAException(" unknown identifier name provided  : " + isoIi.getIdentifierName());            
        }
        List<T> queryList = HibernateUtil.getCurrentSession().createQuery(hql.toString()).list();
        T sr = null;
        if (queryList.size() > 1) {
            throw new PAException(" More than 1 structural role found for a given identifier "
                        + isoIi.getIdentifierName() + " " + isoIi.getExtension());
        }

        if (!queryList.isEmpty()) { 
            sr = queryList.get(0);
        }
        
        return sr;
    }
    
    /**
    *
    * @param poOrg po
    * @return Organization o
    * @throws PAException pe
    */
   public Organization createPAOrganization(OrganizationDTO poOrg) throws PAException {
       return createPAOrganization(convertPOToPAOrganization(poOrg));
   }

   
    <T extends StructuralRole> T getStructuralRole(T sr) throws PAException {
        
        Session s = HibernateUtil.getCurrentSession();
        s.load(sr, sr.getId());
        return sr;
    }
    

    Organization convertPOToPAOrganization(OrganizationDTO poOrg) throws PAException {
        if (poOrg == null) {
            throw new PAException(" PO Organization cannot be null");
        }

        Organization paOrg = new Organization();
        paOrg.setName(EnOnConverter.convertEnOnToString(poOrg.getName()));
        paOrg.setStatusCode(convertPOEntifyStatusToPAEntityStatus(poOrg.getStatusCode()));
        paOrg.setIdentifier(poOrg.getIdentifier().getExtension());
        List<Adxp> partList = poOrg.getPostalAddress().getPart();
        for (Adxp part : partList) {
            if (part instanceof AdxpCty) {
                paOrg.setCity(part.getValue());
            }
            if (part instanceof AdxpZip) {
                paOrg.setPostalCode(part.getValue());
            }
            if (part instanceof AdxpCnt) {
                paOrg.setCountryName(part.getCode());
            }
            if (part instanceof AdxpSta) {
                paOrg.setState(part.getValue());
            }
        }
        return paOrg;

    }

    /**
     *
     * method to create pa person from po.
     *
     * @param poOrg
     * @return
     * @throws PAException
     */
    Person createPAPerson(PersonDTO poPerson) throws PAException {
        return createPAPerson(convertPOToPAPerson(poPerson));
    }


    /**
     *
     * method to create pa person from po.
     *
     * @param poOrg
     * @return
     * @throws PAException
     */
    Person convertPOToPAPerson(PersonDTO poPerson) throws PAException {
        if (poPerson == null) {
            throw new PAException(" PO Person cannot be null");
        }
        Person per = new Person();
        try {
            per = EnPnConverter.convertToPaPerson(poPerson);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LOG.error("Error while converting Person ISO " , e);
        }
        per.setStatusCode(convertPOEntifyStatusToPAEntityStatus(poPerson.getStatusCode()));
        per.setIdentifier(poPerson.getIdentifier().getExtension());
        return per;
    }






    /**
     *
     * @param crs ClinicalResearchStaff
     * @return ClinicalResearchStaff
     * @throws PAException PAException
     */
    AbstractEntity createPADomain(AbstractEntity ae) throws PAException {
        if (ae == null) {
            LOG.error(" domain should not be null ");
            throw new PAException(" domaon should not be null ");
        }
        LOG.debug("Entering createPA Domain ");
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.save(ae);
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while creating domain ", hbe);
            throw new PAException(" Hibernate exception while creating domain", hbe);
        } finally {
            session.flush();
        }
        LOG.debug("Leaving create PA Domain ");
        return ae;
    }

    /**
     *
     * @param cd
     * @return
     * @throws PAException
     */
    EntityStatusCode convertPOEntifyStatusToPAEntityStatus(Cd cd) throws PAException {
        if (cd == null) {
            throw new PAException(" Cd cannot be null");
        }
        if ("ACTIVE".equalsIgnoreCase(cd.getCode())) {
            return EntityStatusCode.ACTIVE;
        } else if ("INACTIVE".equalsIgnoreCase(cd.getCode())) {
            return EntityStatusCode.INACTIVE;
        } else if ("NULLIFIED".equalsIgnoreCase(cd.getCode())) {
            return EntityStatusCode.NULLIFIED;
        } else if ("PENDING".equalsIgnoreCase(cd.getCode())) {
            return EntityStatusCode.PENDING;
        } else {
            throw new PAException(" Unsuported PA known status " + cd.getCode());
        }
    }

    /**
     *
     * @param cd
     * @return
     * @throws PAException
     */
    StructuralRoleStatusCode convertPORoleStatusToPARoleStatus(Cd cd) throws PAException {
        if (cd == null) {
            throw new PAException(" Cd cannot be null");
        }
        if ("ACTIVE".equalsIgnoreCase(cd.getCode())) {
            return StructuralRoleStatusCode.ACTIVE;
        } else if ("CANCELLED".equalsIgnoreCase(cd.getCode())) {
            return StructuralRoleStatusCode.CANCELLED;
        } else if ("NULLIFIED".equalsIgnoreCase(cd.getCode())) {
            return StructuralRoleStatusCode.NULLIFIED;
        } else if ("PENDING".equalsIgnoreCase(cd.getCode())) {
            return StructuralRoleStatusCode.PENDING;
        } else if ("SUSPENDED".equalsIgnoreCase(cd.getCode())) {
            return StructuralRoleStatusCode.SUSPENDED;
        } else if ("TERMINATED".equalsIgnoreCase(cd.getCode())) {
            return StructuralRoleStatusCode.TERMINATED;
        } else {
            throw new PAException(" Unsuported PA known status " + cd.getCode());
        }
    }

    /**
     *
     * @param organization Organization
     * @return Organization
     * @throws PAException PAException
     */
    private Organization createPAOrganization(Organization organization) throws PAException {
        if (organization == null) {
            LOG.error(" organization should not be null ");
            throw new PAException(" organization should not be null ");
        }
        LOG.debug("Entering createOrganization ");
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = session.createQuery("select org from Organization org  where org.identifier = :poOrg");
            query.setParameter("poOrg", organization.getIdentifier());
            if (query.uniqueResult() == null) {
                session.saveOrUpdate(organization);    
            } 
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while createOrganization ", hbe);
            throw new PAException(" Hibernate exception while createOrganization ", hbe);
        } finally {
            session.flush();
        }
        LOG.debug("Leaving createStudyResourcing ");
        return organization;
    }


    /**
     *
     * @param person person
     * @return Person
     * @throws PAException PAException
     */
    private Person createPAPerson(Person person) throws PAException {
        if (person == null) {
            LOG.error(" Person should not be null ");
            throw new PAException(" Person should not be null ");
        }
        LOG.debug("Entering createPerson ");
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = session.createQuery("select per from Person per where per.identifier = :poId ");
            query.setParameter("poId", person.getIdentifier());
            if (query.uniqueResult() == null) {
                session.saveOrUpdate(person);
            }
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while createPerson ", hbe);
            throw new PAException(" Hibernate exception while create Person ", hbe);
        } finally {
            session.flush();
        }
        LOG.debug("Leaving create Person ");
        return person;
    }

    
}
