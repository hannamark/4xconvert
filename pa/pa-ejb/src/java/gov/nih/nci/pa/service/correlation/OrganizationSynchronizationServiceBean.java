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
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OversightCommittee;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * Syncrhonization service bean for organization and its structural roles.
 * 
 * @author Naveen Amiruddin
 * @since 07/07/2007 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.UnusedFormalParameter" })

public class OrganizationSynchronizationServiceBean implements OrganizationSynchronizationServiceRemote {

    private static final Logger LOG  = Logger.getLogger(OrganizationSynchronizationServiceBean.class);
    
    private SessionContext ejbContext;
    
    @Resource
    void setSessionContext(SessionContext ctx) {
    this.ejbContext = ctx;
    }
    
    /**
     * 
     * @param orgIdentifer ii of organization
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void synchronizeOrganization(Ii orgIdentifer) throws PAException {

        OrganizationDTO orgDto = null;
        LOG.debug("Entering synchronizeOrganization");
        try {
            orgDto = PoPaServiceBeanLookup.getOrganizationEntityService().getOrganization(orgIdentifer);
            updateOrganization(orgDto);
        } catch (NullifiedEntityException e) {
           LOG.error("This Organization is nullified " + orgIdentifer.getExtension());
           nulifyOrganization(orgIdentifer);
        }
        LOG.debug("Leaving synchronizeOrganization");
    }
    
    /***
     * 
     * @param oscIdentifer po OversightCommittee identifier
     * @return List list of sp ids
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Long> synchronizeOversightCommittee(Ii oscIdentifer) throws PAException {

        OversightCommitteeDTO oscDto = null;
        LOG.debug("Entering synchronizeOversightCommittee");
        List<Long> spIds = getAffectedStudyProtocolIds("oversightCommittee" , oscIdentifer.getExtension());
        try {
            oscDto = PoPaServiceBeanLookup.getOversightCommitteeCorrelationService().getCorrelation(oscIdentifer);
            updateOversightCommittee(oscDto);
        } catch (NullifiedRoleException e) {
           LOG.error("This OversightCommittee is nullified " + oscIdentifer.getExtension());
           nulifyOversightCommittee(oscIdentifer);
        }
        LOG.debug("Leaving synchronizeOversightCommittee");
        return spIds;
    }
    
    
    /***
     * 
     * @param hcfIdentifer po HealthCareFacility identifier
     * @return List list of sp ids
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Long> synchronizeHealthCareFacility(Ii hcfIdentifer) throws PAException {

        HealthCareFacilityDTO hcfDto = null;
        LOG.debug("Entering synchronizeHealthCareFacility");
        List<Long> spIds = getAffectedStudyProtocolIds("healthCareFacility" , hcfIdentifer.getExtension());
        try {
            hcfDto = PoPaServiceBeanLookup.getHealthCareFacilityCorrelationService().getCorrelation(hcfIdentifer);
            updateHealthCareFacility(hcfDto);
        } catch (NullifiedRoleException e) {
           LOG.error("This HealthCareFacility is nullified " + hcfIdentifer.getExtension());
           nulifyHealthCareFacility(hcfIdentifer);
        }
        LOG.debug("Leaving synchronizeOrganization");
        return spIds;
    }

    /***
     * 
     * @param roIdentifer po ResearchOrganization identifier
     * @return List list of sp ids
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Long> synchronizeResearchOrganization(Ii roIdentifer) throws PAException {

        ResearchOrganizationDTO roDto = null;
        LOG.debug("Entering synchronizeResearchOrganization");
        List<Long> spIds = getAffectedStudyProtocolIds("researchOrganization" , roIdentifer.getExtension());
        try {
            roDto = PoPaServiceBeanLookup.getResearchOrganizationCorrelationService().getCorrelation(roIdentifer);
            updateResearchOrganization(roDto);
        } catch (NullifiedRoleException e) {
           LOG.error("This ResearchOrganization is nullified " + roIdentifer.getExtension());
           nulifyResearchOrganization(roIdentifer);
        }
        LOG.debug("Leaving synchronizeResearchOrganization");
        return spIds;
    } 

    private void nulifyOrganization(Ii organizationIdentifer) throws PAException {
        LOG.debug("Entering nulifyOrganization");
        CorrelationUtils cUtils = new CorrelationUtils();
        Organization org = cUtils.getPAOrganizationByIndetifers(null, organizationIdentifer.getExtension());
        if (org != null) {
            // delete the organization and all of on delete cascade will delete the entire child
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();                
                Organization organization = (Organization) session.get(Organization.class, org.getId());
                session.delete(organization);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while deleting Organization for id = " + org.getId() , hbe);
            }
        }
        LOG.debug("Leaving nulifyOrganization");

    }
    
    private void updateOrganization(OrganizationDTO orgDto) throws PAException {
        LOG.debug("Entering updateOrganization");
        CorrelationUtils cUtils = new CorrelationUtils();
        Organization org = cUtils.getPAOrganizationByIndetifers(null, orgDto.getIdentifier().getExtension());
        if (org != null) {
            // update the organization
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                Organization organization = (Organization) session.get(Organization.class, org.getId());
                org = cUtils.convertPOToPAOrganization(orgDto);
                organization.setCity(org.getCity());
                organization.setCountryName(org.getCountryName());
                organization.setName(org.getName());
                organization.setPostalCode(org.getPostalCode());
                organization.setState(org.getState());
                organization.setStatusCode(org.getStatusCode());
                organization.setDateLastUpdated(new Timestamp((new Date()).getTime()));
                if (ejbContext != null) {
                    organization.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
                }
                session.update(organization);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while deleting Organization for id = " + org.getId() , hbe);
            }
        }
        LOG.debug("Leaving updateOrganization");
    }

    private void nulifyHealthCareFacility(Ii hcIdentifer) throws PAException {
        LOG.debug("Entering nulifyHealthCareFacility");
        HealthCareFacility hcf = new HealthCareFacility();
        CorrelationUtils cUtils = new CorrelationUtils();
        hcf.setIdentifier(hcIdentifer.getExtension());
        hcf = cUtils.getPAHealthCareFacility(hcf);
        if (hcf != null) {
            // delete the hcf and all of on delete cascade will delete the entire child
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                HealthCareFacility healthCareFacility = 
                        (HealthCareFacility) session.get(HealthCareFacility.class, hcf.getId());
                session.delete(healthCareFacility);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while deleting healthCareFacility for id = " 
                        + hcf.getId() , hbe);
            }
        }
        LOG.debug("Leaving nulifyHealthCareFacility");
    }
    
    private void updateHealthCareFacility(HealthCareFacilityDTO hcfDto) throws PAException {
        LOG.debug("Entering updateHealthCareFacility");
        CorrelationUtils cUtils = new CorrelationUtils();
        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setIdentifier(hcfDto.getIdentifier().getExtension());
        hcf = cUtils.getPAHealthCareFacility(hcf);
        if (hcf != null) {
            // update the organization
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                HealthCareFacility hcFacility = (HealthCareFacility) session.get(HealthCareFacility.class, hcf.getId());
                hcFacility.setStatusCode(cUtils.convertPORoleStatusToPARoleStatus(hcfDto.getStatus()));
                hcFacility.setDateLastUpdated(new Timestamp((new Date()).getTime()));
                if (ejbContext != null) {
                    hcFacility.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
                }
                session.update(hcFacility);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while updating HealthCareFacility for id = " 
                        + hcf.getId() , hbe);
            }
        }
        LOG.debug("Leaving updateHealthCareFacility");
    }


    private void nulifyOversightCommittee(Ii oscIdentifer) throws PAException {
        LOG.debug("Entering nulifyOversightCommittee");
        OversightCommittee osc = new OversightCommittee();
        CorrelationUtils cUtils = new CorrelationUtils();
        osc.setIdentifier(oscIdentifer.getExtension());
        osc = cUtils.getPAOversightCommittee(osc);
        if (osc != null) {
            // delete the hcf and all of on delete cascade will delete the entire child
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                OversightCommittee oversightCommittee = 
                        (OversightCommittee) session.get(OversightCommittee.class, osc.getId());
                session.delete(oversightCommittee);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while deleting OversightCommittee for id = " 
                        + osc.getId() , hbe);
            }
        }
        LOG.debug("Leaving nulifyHealthCareFacility");
    }

    private void updateOversightCommittee(OversightCommitteeDTO oscDto) throws PAException {
        LOG.debug("Entering updateOversightCommittee");
        CorrelationUtils cUtils = new CorrelationUtils();
        OversightCommittee osc = new OversightCommittee();
        osc.setIdentifier(oscDto.getIdentifier().getExtension());
        osc = cUtils.getPAOversightCommittee(osc);
        if (osc != null) {
            // update the organization
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                OversightCommittee osComittee = (OversightCommittee) session.get(OversightCommittee.class, osc.getId());
                osComittee.setStatusCode(cUtils.convertPORoleStatusToPARoleStatus(oscDto.getStatus()));
                osComittee.setDateLastUpdated(new Timestamp((new Date()).getTime()));
                if (ejbContext != null) {
                    osComittee.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
                }
                session.update(osComittee);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while updating OversightCommittee for id = " 
                        + osc.getId() , hbe);
            }
        }
        LOG.debug("Leaving updateOversightCommittee");
    }

    private void nulifyResearchOrganization(Ii roIdentifer) throws PAException {
        LOG.debug("Entering nulifyResearchOrganization");
        ResearchOrganization ro = new ResearchOrganization();
        CorrelationUtils cUtils = new CorrelationUtils();
        ro.setIdentifier(roIdentifer.getExtension());
        ro = cUtils.getPAResearchOrganization(ro);
        if (ro != null) {
            // delete the hcf and all of on delete cascade will delete the entire child
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                ResearchOrganization researchOrganization = 
                        (ResearchOrganization) session.get(ResearchOrganization.class, ro.getId());
                session.delete(researchOrganization);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while deleting ResearchOrganization for id = " 
                        + ro.getId() , hbe);
            }
        }
        LOG.debug("Leaving nulifyResearchOrganization");
    }

    private void updateResearchOrganization(ResearchOrganizationDTO roDto) throws PAException {
        LOG.debug("Entering updateResearchOrganization");
        CorrelationUtils cUtils = new CorrelationUtils();
        ResearchOrganization ro = new ResearchOrganization();
        ro.setIdentifier(roDto.getIdentifier().getExtension());
        ro = cUtils.getPAResearchOrganization(ro);
        if (ro != null) {
            // update the organization
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                ResearchOrganization rOg = (ResearchOrganization) session.get(ResearchOrganization.class, ro.getId());
                rOg.setStatusCode(cUtils.convertPORoleStatusToPARoleStatus(roDto.getStatus()));
                rOg.setDateLastUpdated(new Timestamp((new Date()).getTime()));
                if (ejbContext != null) {
                    rOg.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
                }
                session.update(rOg);
                session.flush();
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while updating ResearchOrganization for id = " 
                        + ro.getId() , hbe);
            }
        }
        LOG.debug("Leaving updateResearchOrganization");
    }

    private List<Long> getAffectedStudyProtocolIds(String className , String identifier) throws PAException  {
        return null;
    }
//    private List<Long> getAffectedStudyProtocolIds(String className , String identifier) throws PAException  {
//        Session session = null;
//        List<Long> spIds = null;
//        try {
//            session = HibernateUtil.getCurrentSession();
//            String hql = " Select distinct sp.id from StudyProtocol sp  " 
//                      + " join sp.studyParticipations as sps" 
//                      + " join sps." + className + " as cl where cl.identifier = '" + identifier + "'";
//            spIds =  session.createQuery(hql).list();
//        } catch (HibernateException hbe) {
//            throw new PAException("Hibernate exception while retrieving affected Ids for identifier = " 
//                    + identifier + " for class name " + className , hbe);
//        }
//        return spIds;
//    }
}
