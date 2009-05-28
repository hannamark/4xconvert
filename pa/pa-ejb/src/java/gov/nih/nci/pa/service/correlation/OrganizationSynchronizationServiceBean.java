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
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyParticipationServiceLocal;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
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
 * Syncrhonization service bean for organization and its structural roles.
 *
 * @author Naveen Amiruddin
 * @since 07/07/2007 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@SuppressWarnings({ "PMD.TooManyMethods" , "PMD.CyclomaticComplexity",  "PMD.NPathComplexity" , 
    "PMD.PreserveStackTrace" })
 
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrganizationSynchronizationServiceBean implements OrganizationSynchronizationServiceRemote {

    private static final Logger LOG  = Logger.getLogger(OrganizationSynchronizationServiceBean.class);
    private static CorrelationUtils cUtils = new CorrelationUtils();
    private SessionContext ejbContext;
    @EJB
    StudyParticipationServiceLocal spsLocal = null;

    @Resource
    void setSessionContext(SessionContext ctx) {
    this.ejbContext = ctx;
    }

    /**
     *
     * @param orgIdentifer ii of organization
     * @throws PAException on error
     */
    public void synchronizeOrganization(Ii orgIdentifer) throws PAException {

        OrganizationDTO orgDto = null;
        OrganizationDTO nullifiedOrg = null;
        LOG.debug("Entering synchronizeOrganization");
        try {
            orgDto = PoRegistry.getOrganizationEntityService().getOrganization(orgIdentifer);
            updateOrganization(orgIdentifer , orgDto , nullifiedOrg);
        } catch (NullifiedEntityException e) {
           LOG.error("This Organization is nullified " + orgIdentifer.getExtension());
//           Map<Ii , Ii > ii = e.getNullifiedEntities();
//           LOG.info(" ii is " + ii.get(orgIdentifer));
//           Ii duplicateIi = e.getNullifiedEntities().get(orgIdentifer);
//           LOG.info("This Organization is nullified " + duplicateIi.getExtension());
           try {
               nullifiedOrg = PoRegistry.getOrganizationEntityService().getOrganization(
                       IiConverter.converToPoOrganizationIi("584"));
            } catch (NullifiedEntityException e1) {
                // TODO refactor the code to handle chain of nullified entities ... Naveen Amiruddin
                throw new PAException("This scenario is currrently not hanndled .... " 
                        + "Duplicate Ii of nullified is also nullified" , e1);
            }
           updateOrganization(orgIdentifer , orgDto , nullifiedOrg);
        }
        LOG.debug("Leaving synchronizeOrganization");
    }

    /***
     *
     * @param oscIdentifer po OversightCommittee identifier
     * @return List list of sp ids
     * @throws PAException on error
     */
    public List<Long> synchronizeOversightCommittee(Ii oscIdentifer) throws PAException {

        OversightCommitteeDTO oscDto = null;
        LOG.debug("Entering synchronizeOversightCommittee");
        try {
            oscDto = PoRegistry.getOversightCommitteeCorrelationService().getCorrelation(oscIdentifer);
            updateOversightCommittee(oscIdentifer , oscDto);
        } catch (NullifiedRoleException e) {
           LOG.info("This OversightCommittee is nullified " + oscIdentifer.getExtension());
           updateOversightCommittee(oscIdentifer , null);
        }
        LOG.debug("Leaving synchronizeOversightCommittee");
        return null;
    }


    /***
     *
     * @param hcfIdentifer po HealthCareFacility identifier
     * @return List list of sp ids
     * @throws PAException on error
     */
    public List<Long> synchronizeHealthCareFacility(Ii hcfIdentifer) throws PAException {

        HealthCareFacilityDTO hcfDto = null;
        LOG.debug("Entering synchronizeHealthCareFacility");
        List<Long> spIds = null;
        try {
            hcfDto = PoRegistry.getHealthCareFacilityCorrelationService().getCorrelation(hcfIdentifer);
            updateHealthCareFacility(hcfIdentifer , hcfDto);
        } catch (NullifiedRoleException e) {
           LOG.error("This HealthCareFacility is nullified " + hcfIdentifer.getExtension());
           updateHealthCareFacility(hcfIdentifer , null);
        }
        LOG.debug("Leaving synchronizeOrganization");
        return spIds;
    }

    /***
     *
     * @param roIdentifier po ResearchOrganization identifier
     * @return List list of sp ids
     * @throws PAException on error
     */
    public List<Long> synchronizeResearchOrganization(Ii roIdentifier) throws PAException {

        ResearchOrganizationDTO roDto = null;
        LOG.debug("Entering synchronizeResearchOrganization");
        try {
            roDto = PoRegistry.getResearchOrganizationCorrelationService().getCorrelation(roIdentifier);
            updateResearchOrganization(roIdentifier , roDto);
        } catch (NullifiedRoleException e) {
           LOG.info("This ResearchOrganization is nullified " + roIdentifier.getExtension());
           updateResearchOrganization(roIdentifier , null);
        }
        LOG.debug("Leaving synchronizeResearchOrganization");
        return null;
    }

    private void updateResearchOrganization(Ii ii , ResearchOrganizationDTO roDto) throws PAException {
        LOG.debug("Entering updateResearchOrganization");
        Session session = null;
        ResearchOrganization ro = new ResearchOrganization();
        ro.setIdentifier(ii.getExtension());
        ro = cUtils.getPAResearchOrganization(ro);
        boolean cascadeRole = false;
        if (ro != null) {
            if (roDto == null 
                    || !ro.getStatusCode().equals(cUtils.convertPORoleStatusToPARoleStatus(roDto.getStatus()))) {
                cascadeRole = true;
            }
            session = HibernateUtil.getCurrentSession();
            if (roDto == null) {
                ro.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
                
            } else {
                ro.setStatusCode(cUtils.convertPORoleStatusToPARoleStatus(roDto.getStatus()));
            }
            ro.setDateLastUpdated(new Timestamp((new Date()).getTime()));
            session.update(ro);
            if (cascadeRole) {
                spsLocal.cascadeRoleStatus(ii , CdConverter.convertToCd(ro.getStatusCode()));
                
            }
        }
        LOG.debug("Leaving updateResearchOrganization");

    }
    
    private void updateOversightCommittee(Ii ii , OversightCommitteeDTO ocDto) throws PAException {
        Session session = null;
        OversightCommittee oc = new OversightCommittee();
        oc.setIdentifier(ii.getExtension());
        oc = cUtils.getPAOversightCommittee(oc);
        if (oc != null) {
          session = HibernateUtil.getCurrentSession();
          if (ocDto == null) {
              oc.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
          } else {
              oc.setStatusCode(cUtils.convertPORoleStatusToPARoleStatus(ocDto.getStatus()));
          }
          oc.setDateLastUpdated(new Timestamp((new Date()).getTime()));
          session.update(oc);
          if (ocDto == null 
               || !oc.getStatusCode().equals(cUtils.convertPORoleStatusToPARoleStatus(ocDto.getStatus()))) {
              spsLocal.cascadeRoleStatus(ii , CdConverter.convertToCd(oc.getStatusCode()));
          }
        }
    }

    private void updateHealthCareFacility(Ii ii , HealthCareFacilityDTO hcfDto) throws PAException {
        Session session = null;
      HealthCareFacility hcf = new HealthCareFacility();
      hcf.setIdentifier(ii.getExtension());
      hcf = cUtils.getPAHealthCareFacility(hcf);
      boolean cascadeRole = false;
      if (hcf != null) {
        try {
            session = HibernateUtil.getCurrentSession();
            if (hcfDto == null 
                    || !hcf.getStatusCode().equals(cUtils.convertPORoleStatusToPARoleStatus(hcfDto.getStatus()))) {
                cascadeRole = true;
            }

            if (hcfDto == null) {
                hcf.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
            } else {
                hcf.setStatusCode(cUtils.convertPORoleStatusToPARoleStatus(hcfDto.getStatus()));
            }
            hcf.setDateLastUpdated(new Timestamp((new Date()).getTime()));
            session.update(hcf);
            if (cascadeRole) {
                spsLocal.cascadeRoleStatus(ii , CdConverter.convertToCd(hcf.getStatusCode()));
            }
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception while updating HealthCareFacility for id = "
                    + hcf.getId() , hbe);
        }
        LOG.debug("Leaving updateHealthCareFacility");
      }
    }
    
    private void updateOrganization(Ii ii , OrganizationDTO orgDto  , OrganizationDTO duplicateOrg) throws PAException {
        LOG.debug("Entering updateOrganization");
        Organization paOrg = cUtils.getPAOrganizationByIndetifers(null, ii.getExtension());
        if (paOrg != null) {
            // update the organization
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                Organization organization = (Organization) session.get(Organization.class, paOrg.getId());
                if (duplicateOrg == null) {
                   // that means its not nullified
                    paOrg = cUtils.convertPOToPAOrganization(orgDto);
                    organization.setCity(paOrg.getCity());
                    organization.setCountryName(paOrg.getCountryName());
                    organization.setName(paOrg.getName());
                    organization.setPostalCode(paOrg.getPostalCode());
                    organization.setState(paOrg.getState());
                    organization.setStatusCode(paOrg.getStatusCode());
                } else {
                    // its means its nullified
                    organization.setStatusCode(EntityStatusCode.NULLIFIED);
                    
                }
                organization.setDateLastUpdated(new Timestamp((new Date()).getTime()));
                if (ejbContext != null) {
                    organization.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
                }
                session.update(organization);
                if (duplicateOrg != null) {
                    cascadeDuplicateEntity(paOrg , duplicateOrg);
                }
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception while deleting Organization for id = " + paOrg.getId(), hbe);
            }
        }
        LOG.debug("Leaving updateOrganization");
    }
    
    private void cascadeDuplicateEntity(Organization nullfiedOrg  , OrganizationDTO duplicateOrg) throws PAException {
        // Step 1: Check if the duplicate org has an entry in pa, if not create one 
        Organization dupPaOrg = 
            cUtils.getPAOrganizationByIndetifers(null, duplicateOrg.getIdentifier().getExtension());
        if (dupPaOrg == null) {
            dupPaOrg = cUtils.createPAOrganization(duplicateOrg);
        }
        updateHealtcareFacility(nullfiedOrg  ,  duplicateOrg);
    }
    private void updateHealtcareFacility(Organization nullfiedOrg  , OrganizationDTO duplicateOrg) throws PAException {
        Long hcfDuplicateId = null;
        Long hcfNullifiedId = null;
        
        // Step 2 : find if the nullified org has any structural roles ? if yes, check the duplicate org has 
        // that structural role, if it does not have an structural create one
        HealthCareFacility nhcf = new HealthCareFacility();
        nhcf.setOrganization(nullfiedOrg);
        nhcf = cUtils.getPAHealthCareFacility(nhcf);
        if (nhcf != null) {
            hcfNullifiedId = nhcf.getId();
            // create a Structural role the for the duplicate 
            OrganizationCorrelationServiceBean ocb = new OrganizationCorrelationServiceBean();
            hcfDuplicateId = ocb.createHealthCareFacilityCorrelations(duplicateOrg.getIdentifier().getExtension());
            String sql = "update STUDY_PARTICIPATION set healthcare_facility_identifier = " + hcfDuplicateId 
            + " where healthcare_facility_identifier = " + hcfNullifiedId;
            Session session = HibernateUtil.getCurrentSession();
            int i = session.createSQLQuery(sql).executeUpdate();
            LOG.info("nullified hcf indentifier is " + hcfNullifiedId);
            LOG.info("duplicate hcf indentifier is " + hcfDuplicateId);
            LOG.info("total records got update in STUDY_PARTICIPATION us " + i);  
            if (i > 0) {
                spsLocal.cascadeRoleStatus(IiConverter.converToPoHealthCareFacilityIi(hcfDuplicateId.toString()) , 
                                CdConverter.convertToCd(nhcf.getStatusCode()));
            }
        }
        
    }
    
}
