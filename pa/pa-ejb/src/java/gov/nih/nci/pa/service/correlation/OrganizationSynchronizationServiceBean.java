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
import gov.nih.nci.pa.service.StudySiteServiceLocal;
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
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
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
    StudySiteServiceLocal spsLocal = null;

    @Resource
    void setSessionContext(SessionContext ctx) {
    this.ejbContext = ctx;
    }

    /**
     *
     * @param orgIdentifer ii of organization
     * @throws PAException on error
     */
    public void synchronizeOrganization(final Ii orgIdentifer) throws PAException {

        OrganizationDTO orgDto = null;
        LOG.debug("Entering synchronizeOrganization");
        try {
            orgDto = PoRegistry.getOrganizationEntityService().getOrganization(orgIdentifer);
            updateOrganization(orgIdentifer , orgDto);
        } catch (NullifiedEntityException e) {
           LOG.info("This Organization is nullified " + orgIdentifer.getExtension());
           updateOrganization(orgIdentifer , null);
        }
        LOG.debug("Leaving synchronizeOrganization");
    }

    /***
    *
    * @param hcfIdentifer po HealthCareFacility identifier
    * @throws PAException on error
    */
   public void synchronizeHealthCareFacility(final Ii hcfIdentifer) throws PAException {

       HealthCareFacilityDTO hcfDto = null;
       LOG.debug("Entering synchronizeHealthCareFacility");
       try {
           hcfDto = PoRegistry.getHealthCareFacilityCorrelationService().getCorrelation(hcfIdentifer);
           updateHealthCareFacility(hcfIdentifer , hcfDto);
       } catch (NullifiedRoleException e) {
          LOG.info("This HealthCareFacility is nullified " + hcfIdentifer.getExtension());
          updateHealthCareFacility(hcfIdentifer , null);
       }
       LOG.debug("Leaving synchronizeOrganization");
   }
   
   /***
   *
   * @param oscIdentifer po OversightCommittee identifier
   * @throws PAException on error
   */
   public void synchronizeOversightCommittee(final Ii oscIdentifer) throws PAException {
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
   }

   /***
   *
   * @param roIdentifier po ResearchOrganization identifier
   * @throws PAException on error
   */
   public void synchronizeResearchOrganization(final Ii roIdentifier) throws PAException {
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
   }

   private void updateOrganization(final Ii ii , final OrganizationDTO orgDto) throws PAException {
       LOG.debug("Entering updateOrganization");
       Organization paOrg = cUtils.getPAOrganizationByIndetifers(null, ii.getExtension());

       if (paOrg != null) {
           Session session = null;
           session = HibernateUtil.getCurrentSession();
           // update the organization
           Organization organization = (Organization) session.get(Organization.class, paOrg.getId());

           if (orgDto == null) {
               // its nullified
               organization.setStatusCode(EntityStatusCode.NULLIFIED);
           } else {
               // that means its not nullified
               paOrg = cUtils.convertPOToPAOrganization(orgDto);
               organization.setCity(paOrg.getCity());
               organization.setCountryName(paOrg.getCountryName());
               organization.setName(paOrg.getName());
               organization.setPostalCode(paOrg.getPostalCode());
               organization.setState(paOrg.getState());
               organization.setStatusCode(paOrg.getStatusCode());
           }
           organization.setDateLastUpdated(new Timestamp((new Date()).getTime()));
           if (ejbContext != null) {
               organization.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
           }
           session.update(organization);
       }
       LOG.debug("Leaving updateOrganization");
   }
   
   private void updateResearchOrganization(final Ii roIdentifier , final ResearchOrganizationDTO roDto) 
   throws PAException {
       Session session = null;
       ResearchOrganization ro = new ResearchOrganization();
       ro.setIdentifier(roIdentifier.getExtension());
       ro = cUtils.getPAResearchOrganization(ro);
       StructuralRoleStatusCode newRoleCode = null;
       Ii roCurrentIi = roIdentifier;
       if (ro != null) {
           session = HibernateUtil.getCurrentSession();
           if (roDto == null) {
               // this is a nullified scenario .....
               // check if it does have an valid organization 
               Long paOrgId = ro.getOrganization().getId();
               String poOrgId = cUtils.getPAOrganizationByIndetifers(paOrgId, null).getIdentifier();
               OrganizationDTO organizationDto = getPoOrganization(poOrgId);
               if (organizationDto != null) {
                   OrganizationCorrelationServiceBean osb = new OrganizationCorrelationServiceBean();
                   Long duplicateRoId = osb.createResearchOrganizationCorrelations(
                           organizationDto.getIdentifier().getExtension());
                   ResearchOrganization dupRo = new ResearchOrganization();
                   dupRo.setId(duplicateRoId);
                   dupRo = cUtils.getPAResearchOrganization(dupRo);
                   newRoleCode = dupRo.getStatusCode();
                   roCurrentIi = IiConverter.convertToPoResearchOrganizationIi(duplicateRoId.toString());
                   replaceStudySiteIdentifiers(
                           IiConverter.convertToPoResearchOrganizationIi(ro.getId().toString()),  roCurrentIi);     
                   ro.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
               } else {
                   // this is nullified scenario with no org 
                   ro.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
                   newRoleCode = StructuralRoleStatusCode.NULLIFIED;
               }
           } else if (!ro.getStatusCode().equals(cUtils.convertPORoleStatusToPARoleStatus(roDto.getStatus()))) {
               // this is a update scenario with a status change
               newRoleCode = cUtils.convertPORoleStatusToPARoleStatus(roDto.getStatus());
               ro.setStatusCode(newRoleCode);
           }
           ro.setDateLastUpdated(new Timestamp((new Date()).getTime()));
           session.update(ro);
           spsLocal.cascadeRoleStatus(roCurrentIi, CdConverter.convertToCd(newRoleCode));           
       }
   }

   private void updateOversightCommittee(final Ii oscIdentifier , final OversightCommitteeDTO oscDto) 
   throws PAException {
       Session session = null;
       OversightCommittee osc = new OversightCommittee();
       osc.setIdentifier(oscIdentifier.getExtension());
       osc = cUtils.getPAOversightCommittee(osc);
       StructuralRoleStatusCode newRoleCode = null;
       Ii hcfCurrentIi = oscIdentifier;
       if (osc != null) {
           session = HibernateUtil.getCurrentSession();
           if (oscDto == null) {
               // this is a nullified scenario .....
               // check if it does have an valid organization 
               Long paOrgId = osc.getOrganization().getId();
               
               String poOrgId = cUtils.getPAOrganizationByIndetifers(paOrgId, null).getIdentifier();
               OrganizationDTO organizationDto = getPoOrganization(poOrgId);
               
               if (organizationDto != null) {
                   OrganizationCorrelationServiceBean osb = new OrganizationCorrelationServiceBean();
                   Long duplicateOscId = osb.createOversightCommitteeCorrelations(
                           organizationDto.getIdentifier().getExtension());
                   
                   OversightCommittee dupOsc = new OversightCommittee();
                   dupOsc.setId(duplicateOscId);
                   dupOsc = cUtils.getPAOversightCommittee(dupOsc);
                   newRoleCode = dupOsc.getStatusCode();
                   hcfCurrentIi = IiConverter.convertToPoOversightCommitteeIi(duplicateOscId.toString());
                   replaceStudySiteIdentifiers(
                           IiConverter.convertToPoOversightCommitteeIi(osc.getId().toString()),  hcfCurrentIi);     
                   osc.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
               } else {
                   // this is nullified scenario with no org 
                   osc.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
                   newRoleCode = StructuralRoleStatusCode.NULLIFIED;
               }
           } else if (!osc.getStatusCode().equals(cUtils.convertPORoleStatusToPARoleStatus(oscDto.getStatus()))) {
               // this is a update scenario with a status change
               newRoleCode = cUtils.convertPORoleStatusToPARoleStatus(oscDto.getStatus());
               osc.setStatusCode(newRoleCode);
           }
           osc.setDateLastUpdated(new Timestamp((new Date()).getTime()));
           session.update(osc);
           spsLocal.cascadeRoleStatus(hcfCurrentIi, CdConverter.convertToCd(newRoleCode));           
       }
   }
   
   private void updateHealthCareFacility(final Ii hcfIdentifier , final HealthCareFacilityDTO hcfDto) 
   throws PAException {
       Session session = null;
       HealthCareFacility hcf = new HealthCareFacility();
       hcf.setIdentifier(hcfIdentifier.getExtension());
       hcf = cUtils.getPAHealthCareFacility(hcf);
       StructuralRoleStatusCode newRoleCode = null;
       Ii hcfCurrentIi = hcfIdentifier;
       if (hcf != null) {
           session = HibernateUtil.getCurrentSession();
           if (hcfDto == null) {
               // this is a nullified scenario .....
               // check if it does have an valid organization 
               Long paOrgId = hcf.getOrganization().getId();
               
               String poOrgId = cUtils.getPAOrganizationByIndetifers(paOrgId, null).getIdentifier();
               OrganizationDTO organizationDto = getPoOrganization(poOrgId);
               
               if (organizationDto != null) {
                   OrganizationCorrelationServiceBean osb = new OrganizationCorrelationServiceBean();
                   Long duplicateHcfId = osb.createHealthCareFacilityCorrelations(
                           organizationDto.getIdentifier().getExtension());
                   
                   HealthCareFacility dupHcf = new HealthCareFacility();
                   dupHcf.setId(duplicateHcfId);
                   dupHcf = cUtils.getPAHealthCareFacility(dupHcf);
                   newRoleCode = dupHcf.getStatusCode();
                   hcfCurrentIi = IiConverter.convertToPoHealthCareFacilityIi(duplicateHcfId.toString());
                   replaceStudySiteIdentifiers(
                           IiConverter.convertToPoHealthCareFacilityIi(hcf.getId().toString()),  hcfCurrentIi);     
                   hcf.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
               } else {
                   // this is nullified scenario with no org 
                   hcf.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
                   newRoleCode = StructuralRoleStatusCode.NULLIFIED;
               }
           } else if (!hcf.getStatusCode().equals(cUtils.convertPORoleStatusToPARoleStatus(hcfDto.getStatus()))) {
               // this is a update scenario with a status change
               newRoleCode = cUtils.convertPORoleStatusToPARoleStatus(hcfDto.getStatus());
               hcf.setStatusCode(newRoleCode);
           }
           hcf.setDateLastUpdated(new Timestamp((new Date()).getTime()));
           session.update(hcf);
           spsLocal.cascadeRoleStatus(hcfCurrentIi, CdConverter.convertToCd(newRoleCode));           
       }
   }
       
   private void replaceStudySiteIdentifiers(final Ii from  , final Ii to) {

       String sql = null;
       if (IiConverter.HEALTH_CARE_FACILITY_IDENTIFIER_NAME.equals(from.getIdentifierName())) {    
           sql = "update STUDY_SITE set healthcare_facility_identifier = " + to.getExtension() 
           + " where healthcare_facility_identifier = " + from.getExtension();
       }
       if (IiConverter.RESEARCH_ORG_IDENTIFIER_NAME.equals(from.getIdentifierName())) {
           sql = "update STUDY_SITE set research_organization_identifier = " + to.getExtension() 
           + " where research_organization_identifier = " + from.getExtension();
       }
       if (IiConverter.OVERSIGHT_COMMITTEE_IDENTIFIER_NAME.equals(from.getIdentifierName())) {    
           sql = "update STUDY_SITE set oversight_committee_identifier = " + to.getExtension() 
           + " where oversight_committee_identifier = " + from.getExtension();
       }

       int i = HibernateUtil.getCurrentSession().createSQLQuery(sql).executeUpdate();
       LOG.info("Sql for update " + sql);
       LOG.info("total records got update in STUDY_SITE IS " + i);  
   }    
       
     
   private OrganizationDTO getPoOrganization(final String poOrgIdentifier) throws PAException {
       OrganizationDTO organizationDto = null;
       Ii organizationIi = null;
       try {
           organizationDto = PoRegistry.getOrganizationEntityService().getOrganization(
                   IiConverter.convertToPoOrganizationIi(poOrgIdentifier));
       } catch (NullifiedEntityException e) {
            // org is nullified, find out if it has any duplicates
           Ii nullfiedIi = null;
           Map<Ii, Ii> nullifiedEntities = e.getNullifiedEntities();
           for (Ii tmp : nullifiedEntities.keySet()) {
               if (tmp.getExtension().equals(poOrgIdentifier)) {
                   nullfiedIi = tmp;
               }
           }
           if (nullfiedIi != null) {
              organizationIi = nullifiedEntities.get(nullfiedIi);
           }
            //organizationIi = IiConverter.converToPoPersonIi("584");
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
    
}
