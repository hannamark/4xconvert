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
package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAConstants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;


/** 
* Bean implementation for providing access to the client.
* 
* @author Naveen Amiruddin
* @since 06/26/2008
* copyright NCI 2007.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@Stateless
@SuppressWarnings({  "PMD.CyclomaticComplexity" , "PMD.ExcessiveMethodLength" })
public class PAOrganizationServiceBean implements 
    PAOrganizationServiceRemote {
    
    private static final Logger LOG  = Logger.getLogger(PAOrganizationServiceRemote.class);
    
    /**
     * returns distinct organization that have been associated with a protocol.
     * @return OrganizationDTO
     * @param organizationType Organization Type
     * @throws PAException pa exception
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<PaOrganizationDTO> getOrganizationsAssociatedWithStudyProtocol(String organizationType) 
    throws PAException {
        LOG.debug("Entering getOrganizationsAssociatedWithStudyProtocol");
        List<Organization> organizations = generateDistinctOrganizationQuery(organizationType);
        List<PaOrganizationDTO> organizationDTOs = createOrganizationDTO(organizations);
        LOG.debug("Leaving getOrganizationsAssociatedWithStudyProtocol");
        return organizationDTOs;  
            
    }
    
    /**
     * This expects only id and identifier.
     * @param organization organization
     * @return Organization
     * @throws PAException PAException
     */
    public Organization getOrganizationByIndetifers(Organization organization) throws PAException {
        Organization org = null;
        
        if (organization.getId() == null && organization.getIdentifier() == null) {
            LOG.error(" Id or poIdentifer should not be null ");
            throw new PAException(" Id or poIdentifer should not be null ");
        }
        LOG.info("Entering getOrganizationByIndetifers");
        Session session = null;
        List<Organization> queryList = new ArrayList<Organization>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;
            
            // step 1: form the hql
            StringBuffer hql = new StringBuffer();
            hql.append(" select org from Organization org  where 1 = 1 ");
            if (organization.getId() != null) {
                hql.append(" and org.id = ").append(organization.getId());
            }
            if (organization.getIdentifier() != null) {
                hql.append(" and org.identifier = '").append(organization.getIdentifier()).append('\'');
            }

           LOG.info(" query getOrganizationByPoIndetifer = " + hql);
            
            // step 2: construct query object
            query = session.createQuery(hql.toString());
            queryList = query.list();
            
            if (queryList.size() > 1) {
                LOG.error(" Organization  should not be more than 1 record for a Po Indetifer = "  
                        + organization.getIdentifier());
                throw new PAException(" Organization  should not be more than 1 " 
                        + "record for a Po Indetifer = " + organization.getIdentifier());
                
            }
        }  catch (HibernateException hbe) {
            LOG.error(" Organization  should not be more than 1 record for a Po Indetifer = "  
                    + organization.getIdentifier());
            throw new PAException(" Organization  should not be more than 1 " 
                    + "record for a Po Indetifer = " + organization.getIdentifier(), hbe);
        } finally {
            session.flush();
        }
        
        if (!queryList.isEmpty()) {
            org = queryList.get(0);
        }
        LOG.info("Leaving getOrganizationByIndetifers");
        return org;
    }
    

    
    @SuppressWarnings("PMD.ConsecutiveLiteralAppends")
    private List<Organization> generateDistinctOrganizationQuery(String organizationType)  
    throws PAException {
        LOG.debug("Entering generateDistinctOrganizationQuery ");

        Session session = null;
        List<Organization> organizations = null;
        List<Organization> sortedOrganizations = new ArrayList<Organization>();
        Set<Long> orgSet = new  HashSet<Long>();
        
        try {
            session = HibernateUtil.getCurrentSession();
            StringBuffer hql = new StringBuffer();
            if (organizationType.equalsIgnoreCase(PAConstants.LEAD_ORGANIZATION)) {
                hql.append(
                        " Select o from Organization o  " 
                      + " join o.researchOrganizations as ros " 
                      + " join ros.studyParticipations as sps " 
                      + " join sps.studyProtocol as sp " 
                      + "  where sps.functionalCode = '"  
                      +   StudyParticipationFunctionalCode.LEAD_ORAGANIZATION  + "'"
                      +  " order by o.name");
            } else if (organizationType.equalsIgnoreCase(PAConstants.PARTICIPATING_SITE)) {
                hql.append(
                        " Select o from Organization o  " 
                      + " join o.healthCareFacilities as hcf " 
                      + " join hcf.studyParticipations as sps " 
                      + " join sps.studyProtocol as sp " 
                      + "  where sps.functionalCode = '"  
                      +   StudyParticipationFunctionalCode.TREATING_SITE  + "'"
                      +  " order by o.name");
            }
            organizations =  session.createQuery(hql.toString()).list();
            for (Organization o : organizations) {
                if (orgSet.add(o.getId())) {
                    sortedOrganizations.add(o);
                }
            }
            
        } catch (HibernateException hbe) {
            LOG.error("  Hibernate exception while fetching " 
                     + "getOrganizationsAssociatedWithStudyProtocol ", hbe);
            throw new PAException(" Hibernate exception while fetching " 
                    + "getOrganizationsAssociatedWithStudyProtocol ", hbe);
        } finally {
            LOG.debug("Leaving generateDistinctOrganizationQuery ");
        }
        return sortedOrganizations;
    }
    
    private List<PaOrganizationDTO> createOrganizationDTO(List<Organization> organizations) {
        LOG.debug("Entereing createOrganizationDTO");
        
        List<PaOrganizationDTO> organizationDTOs = new ArrayList<PaOrganizationDTO>();
        PaOrganizationDTO oganizationDTO = null;
        for (int i = 0; i < organizations.size(); i++) {
            oganizationDTO = new PaOrganizationDTO();
            oganizationDTO.setId(((Organization) organizations.get(i)).getId().toString());
            oganizationDTO.setName(((Organization) organizations.get(i)).getName());
            organizationDTOs.add(oganizationDTO);
        }
        LOG.debug("Leaving createOrganizationDTO");
        return organizationDTOs;
    }
}
