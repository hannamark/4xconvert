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
import gov.nih.nci.pa.domain.AbstractEntity;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.OversightCommittee;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * A Service class for handing common methods for all correlations.
 *
 * @author Naveen Amiruddin
 * @since 04/11/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveMethodLength", "PMD.CyclomaticComplexity",
        "PMD.ExcessiveClassLength", "PMD.NPathComplexity" })
public class CorrelationUtils implements CorrelationUtilsRemote {
    private static final Logger LOG = Logger.getLogger(CorrelationUtils.class);


    /**
     *
     * @param poIdentifer id
     * @param paIdentifer id
     * @return Person
     * @throws PAException e
     */
    @SuppressWarnings("unchecked")
    public Person getPAPersonByIndetifers(Long paIdentifer, String poIdentifer) throws PAException {
        Person per = null;
        if (poIdentifer == null && paIdentifer == null) {
            LOG.error(" Atleast one identifier must be entered");
            throw new PAException(" Atleast one identifier must be entered");
        }
        if (poIdentifer != null && paIdentifer != null) {
            LOG.error(" Only one identifier must be entered");
            throw new PAException(" Only one identifier must be entered");
        }
        Session session = null;
        List<Person> queryList = new ArrayList<Person>();
        StringBuffer hql = new StringBuffer();
        hql.append(" select per from Person per  where 1 = 1 ");
        if (paIdentifer != null) {
            hql.append(" and per.id = ").append(paIdentifer);
        }
        if (poIdentifer != null) {
            hql.append(" and per.identifier = '").append(poIdentifer).append('\'');
        }
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
            query = session.createQuery(hql.toString());
            queryList = query.list();
            if (queryList.size() > 1) {
                LOG.error(" Person  should not be more than 1 record for a Po Indetifer = " + poIdentifer);
                throw new PAException(" Person  should not be more than 1 " + "record for a Po Indetifer = "
                        + poIdentifer);
            }
        } catch (HibernateException hbe) {
            throw new PAException(" Error while retrieving Organization for id = " + paIdentifer + " PO Identifier = "
                    + poIdentifer, hbe);
        } finally {
            session.flush();
        }
        if (!queryList.isEmpty()) {
            per = queryList.get(0);
        }
        return per;
    }

    /**
     * @param paClinicalResearchStaffId id
     * @return Person
     * @throws PAException e
     */
    public Person getPAPersonByPAClinicalResearchStaffId(Long paClinicalResearchStaffId) throws PAException {
        if (paClinicalResearchStaffId == null) {
            LOG.error("Check the id value.  Null found.  ");
            throw new PAException("Check the id value.  Null found.  ");
        }
        Person person = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            ClinicalResearchStaff clinicalResearchStaff =
                (ClinicalResearchStaff) session.get(ClinicalResearchStaff.class, paClinicalResearchStaffId);
            if (clinicalResearchStaff == null) {
                String errMsg = "Object not found using getPAPersonByPAClinicalResearchStaffId() for id = "
                    + paClinicalResearchStaffId + ".  ";
                LOG.error(errMsg);
                throw new PAException(errMsg);
            }
            person = clinicalResearchStaff.getPerson();
        } catch (HibernateException hbe) {
            LOG.error("Hibernate exception in getPAPersonByPAClinicalResearchStaffId().  ", hbe);
            throw new PAException("Hibernate exception in getPAPersonByPAClinicalResearchStaffId().  ", hbe);
        }
        return person;
    }

    /**
     * @param paOrganizationalContactId id
     * @return Person
     * @throws PAException e
     */
    public Person getPAPersonByPAOrganizationalContactId(Long paOrganizationalContactId) throws PAException {
        if (paOrganizationalContactId == null) {
            LOG.error("Check the id value.  Null found.  ");
            throw new PAException("Check the id value.  Null found.  ");
        }
        Person person = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            OrganizationalContact organizationalContact =
                (OrganizationalContact) session.get(OrganizationalContact.class, paOrganizationalContactId);
            if (organizationalContact == null) {
                String errMsg = "Object not found using getPAPersonByPAOrganizationalContactId() for id = "
                    + paOrganizationalContactId + ".  ";
                LOG.error(errMsg);
                throw new PAException(errMsg);
            }
            person = organizationalContact.getPerson();
        } catch (HibernateException hbe) {
            LOG.error("Hibernate exception in getPAPersonByPAOrganizationalContactId().  ", hbe);
            throw new PAException("Hibernate exception in getPAPersonByPAOrganizationalContact().  ", hbe);
        }
        return person;
    }

    /**
     *
     * @param paIdentifer id
     * @param poIdentifer id
     * @return Organization
     * @throws PAException e
     */
    @SuppressWarnings("unchecked")
    public Organization getPAOrganizationByIndetifers(Long paIdentifer, String poIdentifer) throws PAException {
        Organization org = null;
        if (poIdentifer == null && paIdentifer == null) {
            LOG.error(" Atleast one identifier must be entered");
            throw new PAException(" Atleast one identifier must be entered");
        }
        if (poIdentifer != null && paIdentifer != null) {
            LOG.error(" Only one identifier must be entered");
            throw new PAException(" Only one identifier must be entered");
        }
        Session session = null;
        List<Organization> queryList = new ArrayList<Organization>();
        StringBuffer hql = new StringBuffer();
        hql.append(" select org from Organization org  where 1 = 1 ");
        if (paIdentifer != null) {
            hql.append(" and org.id = ").append(paIdentifer);
        }
        if (poIdentifer != null) {
            hql.append(" and org.identifier = '").append(poIdentifer).append('\'');
        }
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
            query = session.createQuery(hql.toString());
            queryList = query.list();
            if (queryList.size() > 1) {
                LOG.error(" Organization  should not be more than 1 record for a Po Indetifer = " + poIdentifer);
                throw new PAException(" Organization  should not be more than 1 " + "record for a Po Indetifer = "
                        + poIdentifer);
            }
        } catch (HibernateException hbe) {
            throw new PAException(" Error while retrieving Organization for id = " + paIdentifer + " PO Identifier = "
                    + poIdentifer, hbe);
        } finally {
            session.flush();
        }
        if (!queryList.isEmpty()) {
            org = queryList.get(0);
        }
        return org;
    }

    /**
     * @param paResearchOrganizationId id
     * @return Organization
     * @throws PAException e
     */
    public Organization getPAOrganizationByPAResearchOrganizationId(Long paResearchOrganizationId) throws PAException {
        if (paResearchOrganizationId == null) {
            LOG.error("Check the id value.  Null found.  ");
            throw new PAException("Check the id value.  Null found.  ");
        }
        Organization organization = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            ResearchOrganization researchOrg = (ResearchOrganization) session.get(ResearchOrganization.class,
                    paResearchOrganizationId);
            if (researchOrg == null) {
                String errMsg = "Object not found using getPAOrganizationByPAResearchOrganizationId() for id = "
                    + paResearchOrganizationId + ".  ";
                LOG.error(errMsg);
                throw new PAException(errMsg);
            }
            organization = researchOrg.getOrganization();
        } catch (HibernateException hbe) {
            LOG.error("Hibernate exception in  getPAOrganizationByPAResearchOrganizationId().  ", hbe);
            throw new PAException("Hibernate exception in  getPAOrganizationByPAResearchOrganizationId().  ", hbe);
        }
        return organization;
    }

    /**
     * @param paHealthCareFacilityId id
     * @return Organization
     * @throws PAException e
     */
    public Organization getPAOrganizationByPAHealthCareFacilityId(Long paHealthCareFacilityId) throws PAException {
        if (paHealthCareFacilityId == null) {
            LOG.error("Check the id value.  Null found.  ");
            throw new PAException("Check the id value.  Null found.  ");
        }
        Organization organization = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            HealthCareFacility healthCareFac = (HealthCareFacility) session.get(HealthCareFacility.class,
                    paHealthCareFacilityId);
            if (healthCareFac == null) {
                String errMsg = "Object not found using getPAOrganizationByPAHealthCareFacilityId() for id = "
                    + paHealthCareFacilityId + ".  ";
                LOG.error(errMsg);
                throw new PAException(errMsg);
            }
            organization = healthCareFac.getOrganization();
        } catch (HibernateException hbe) {
            LOG.error("Hibernate exception in getPAOrganizationByPAHealthCareFacilityId().  ", hbe);
            throw new PAException("Hibernate exception in getPAOrganizationByPAHealthCareFacilityId().  ", hbe);
        }
        return organization;
    }

    /**
     * @param paOversightCommitteeId id
     * @return Organization
     * @throws PAException e
     */
    public Organization getPAOrganizationByPAOversightCommitteeId(Long paOversightCommitteeId) throws PAException {
        if (paOversightCommitteeId == null) {
            LOG.error("Check the id value.  Null found.  ");
            throw new PAException("Check the id value.  Null found.  ");
        }
        Organization organization = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            OversightCommittee healthCareFac = (OversightCommittee) session.get(OversightCommittee.class,
                    paOversightCommitteeId);
            if (healthCareFac == null) {
                String errMsg = "Object not found using getPAOrganizationByPAOversightCommitteeId() for id = "
                    + paOversightCommitteeId + ".  ";
                LOG.error(errMsg);
                throw new PAException(errMsg);
            }
            organization = healthCareFac.getOrganization();
        } catch (HibernateException hbe) {
            LOG.error("Hibernate exception in getPAOrganizationByPAOversightCommitteeId().  ", hbe);
            throw new PAException("Hibernate exception in getPAOrganizationByPAHealthCareFacilityId().  ", hbe);
        }
        return organization;
    }

    /**
     *
     * @param crs crs
     * @return crs
     * @throws PAException
     */
    @SuppressWarnings("unchecked")
    HealthCareFacility getPAHealthCareFacility(HealthCareFacility hcf)
    throws PAException {
        if (hcf == null) {
            LOG.error("HealthCareFacility Staff cannot be null");
            throw new PAException("HealthCareFacility Staff cannot be null");
        }
        HealthCareFacility hcfOut = null;
        Session session = null;
        List<HealthCareFacility> queryList = new ArrayList<HealthCareFacility>();
        StringBuffer hql = new StringBuffer();
        hql.append(" select hcf from HealthCareFacility hcf  "
                + "join hcf.organization as org where 1 = 1 ");
        if (hcf.getId() != null) {
            hql.append(" and hcf.id = ").append(hcf.getId());
        }
        if (hcf.getOrganization() != null && hcf.getOrganization().getId() != null) {
            hql.append(" and org.id = ").append(hcf.getOrganization().getId());
        }
        if (hcf.getIdentifier() != null) {
            hql.append(" and hcf.identifier = '").append(hcf.getIdentifier()).append('\'');
        }
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;

        query = session.createQuery(hql.toString());
        queryList = query.list();

        if (queryList.size() > 1) {
            LOG.error(" HealthCareFacility should be more than 1 for any given criteria");
            throw new PAException(" HealthCareFacility should be more than 1 for any given criteria");

        }
    }  catch (HibernateException hbe) {
        LOG.error(" Error while retrieving HealthCareFacility" , hbe);
        throw new PAException(" Error while retrieving Clinicial Research Staff" , hbe);
    } finally {
        session.flush();
    }

    if (!queryList.isEmpty()) {
        hcfOut = queryList.get(0);
    }
    return hcfOut;
    }

    /**
     * @param crs crs
     * @return crs
     * @throws PAException
     */
    @SuppressWarnings("unchecked")
    OversightCommittee getPAOversightCommittee(OversightCommittee oc) throws PAException {
        if (oc == null) {
            throw new PAException("OversightCommittee cannot be null.  ");
        }
        OversightCommittee ocOut = null;
        Session session = null;
        List<OversightCommittee> queryList = new ArrayList<OversightCommittee>();
        StringBuffer hql = new StringBuffer();
        hql.append(" select oc from OversightCommittee oc  "
                + "join oc.organization as org where 1 = 1 ");
        if (oc.getId() != null) {
            hql.append(" and oc.id = ").append(oc.getId());
        }
        if (oc.getOrganization() != null && oc.getOrganization().getId() != null) {
            hql.append(" and org.id = ").append(oc.getOrganization().getId());
        }
        if (oc.getIdentifier() != null) {
            hql.append(" and oc.identifier = '").append(oc.getIdentifier()).append('\'');
        }
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;

            query = session.createQuery(hql.toString());
            queryList = query.list();

            if (queryList.size() > 1) {
                throw new PAException("Oversight committee count should not be more than 1 for any given criteria.  ");
            }
        }  catch (HibernateException hbe) {
            throw new PAException(" Error while retrieving OversightCommittee.  " , hbe);
        } finally {
            session.flush();
        }

        if (!queryList.isEmpty()) {
            ocOut = queryList.get(0);
        }
        return ocOut;
    }

    /**
     *
     * @param ro ro
     * @return ro
     * @throws PAException
     */
    @SuppressWarnings("unchecked")
    ResearchOrganization getPAResearchOrganization(ResearchOrganization ro)
    throws PAException {
        if (ro == null) {
            LOG.error("ResearchOrganization Staff cannot be null");
            throw new PAException("ResearchOrganization Staff cannot be null");
        }
        ResearchOrganization roOut = null;
        Session session = null;
        List<ResearchOrganization> queryList = new ArrayList<ResearchOrganization>();
        StringBuffer hql = new StringBuffer();
        hql.append(" select ro from ResearchOrganization ro  "
                + "join ro.organization as org where 1 = 1 ");
        if (ro.getId() != null) {
            hql.append(" and ro.id = ").append(ro.getId());
        }
        if (ro.getOrganization() != null && ro.getOrganization().getId() != null) {
            hql.append(" and org.id = ").append(ro.getOrganization().getId());
        }
        if (ro.getIdentifier() != null) {
            hql.append(" and ro.identifier = '").append(ro.getIdentifier()).append('\'');
        }
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;

        query = session.createQuery(hql.toString());
        queryList = query.list();

        if (queryList.size() > 1) {
            LOG.error(" ResearchOrganization should be more than 1 for any given criteria");
            throw new PAException(" ResearchOrganization should be more than 1 for any given criteria");

        }
    }  catch (HibernateException hbe) {
        LOG.error(" Error while retrieving ResearchOrganization" , hbe);
        throw new PAException(" Error while retrieving ResearchOrganization" , hbe);
    } finally {
        session.flush();
    }

    if (!queryList.isEmpty()) {
        roOut = queryList.get(0);
    }
    return roOut;
    }

    /**
     *
     * @param crs crs
     * @return crs
     * @throws PAException
     */
    @SuppressWarnings("unchecked")
    ClinicalResearchStaff getPAClinicalResearchStaff(ClinicalResearchStaff crs)
    throws PAException {
        if (crs == null) {
            LOG.error("Clinicial Research Staff cannot be null");
            throw new PAException("Clinicial Research Staff cannot be null");
        }
        if (crs.getPerson() != null && crs.getOrganization() == null
            || crs.getPerson() == null && crs.getOrganization() != null) {
            LOG.error("Both person and organization should be specified and it cannot be either");
            throw new PAException("Both person and organization should be specified and it cannot be either");

        }
        ClinicalResearchStaff crsOut = null;
        Session session = null;
        List<ClinicalResearchStaff> queryList = new ArrayList<ClinicalResearchStaff>();
        StringBuffer hql = new StringBuffer();
        hql.append(" select crs from ClinicalResearchStaff crs  "
                + "join crs.person as per "
                + "join crs.organization as org where 1 = 1 ");
        if (crs.getId() != null) {
            hql.append(" and crs.id = ").append(crs.getId());
        }
        if (crs.getPerson() != null && crs.getOrganization()  != null
                && crs.getPerson().getId() != null && crs.getOrganization().getId() != null) {
            hql.append(" and per.id = ").append(crs.getPerson().getId());
            hql.append(" and org.id = ").append(crs.getOrganization().getId());
        }
        if (crs.getIdentifier() != null) {
            hql.append(" and crs.identifier = '").append(crs.getIdentifier()).append('\'');
        }
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;

        query = session.createQuery(hql.toString());
        queryList = query.list();

        if (queryList.size() > 1) {
            LOG.error(" Clinical Reasrch Staff should be more than 1 for any given criteria");
            throw new PAException(" Clinical Reasrch Staff should be more than 1 for any given criteria");

        }
    }  catch (HibernateException hbe) {
        LOG.error(" Error while retrieving Clinicial Research Staff" , hbe);
        throw new PAException(" Error while retrieving Clinicial Research Staff" , hbe);
    } finally {
        session.flush();
    }

    if (!queryList.isEmpty()) {
        crsOut = queryList.get(0);
    }
    return crsOut;
    }

    /**
     *
     * @param hcp HealthCareProvider
     * @return  HealthCareProvider
     * @throws PAException
     */
    @SuppressWarnings("unchecked")
    HealthCareProvider getPAHealthCareProvider(HealthCareProvider hcp)
    throws PAException {
        if (hcp == null) {
            LOG.error("HealthCareProvider  cannot be null");
            throw new PAException("HealthCareProvider cannot be null");
        }
        if (hcp.getPerson() != null && hcp.getOrganization() == null
                || hcp.getPerson() == null && hcp.getOrganization() != null) {
            LOG.error("Both person and organization should be specified and it cannot be either");
            throw new PAException("Both person and organization should be specified and it cannot be either");
        }
        HealthCareProvider hcpOut = null;
        Session session = null;
        List<HealthCareProvider> queryList = new ArrayList<HealthCareProvider>();
        StringBuffer hql = new StringBuffer();
        hql.append(" select hcp from HealthCareProvider hcp  "
                + "join hcp.person as per  "
                + "join hcp.organization as org where 1 = 1 ");
        if (hcp.getId() != null) {
            hql.append(" and hcp.id = ").append(hcp.getId());
        }
        if (hcp.getPerson() != null && hcp.getPerson().getId() != null
                && hcp.getOrganization() != null && hcp.getOrganization().getId() != null) {
            hql.append(" and per.id = ").append(hcp.getPerson().getId());
            hql.append(" and org.id = ").append(hcp.getOrganization().getId());
        }
        if (hcp.getIdentifier() != null) {
            hql.append(" and hcp.identifier = '").append(hcp.getIdentifier()).append('\'');
        }
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;

        query = session.createQuery(hql.toString());
        queryList = query.list();

        if (queryList.size() > 1) {
            LOG.error("HealthCareProvider should not be more than 1 for any given criteria");
            throw new PAException("HealthCareProvider should not be more than 1 for any given criteria");

        }
    }  catch (HibernateException hbe) {
        LOG.error(" Error while retrieving HealthCareProvider" , hbe);
        throw new PAException(" Error while retrieving HealthCareProvider" , hbe);
    } finally {
        session.flush();
    }

    if (!queryList.isEmpty()) {
        hcpOut = queryList.get(0);
    }
    return hcpOut;
    }

    /**
     *
     * @param oc oc
     * @return oc
     * @throws PAException pe
     */

    @SuppressWarnings("unchecked")
    public OrganizationalContact getPAOrganizationalContact(OrganizationalContact oc)
    throws PAException {
        if (oc == null) {
            LOG.error("Clinicial Research Staff cannot be null");
            throw new PAException("Clinicial Research Staff cannot be null");
        }
        if (oc.getPerson() != null && oc.getOrganization() == null
            || oc.getPerson() == null && oc.getOrganization() != null) {
            LOG.error("Both person and organization should be specified and it cannot be either");
            throw new PAException("Both person and organization should be specified and it cannot be either");

        }
        OrganizationalContact ocOut = null;
        Session session = null;
        List<OrganizationalContact> queryList = new ArrayList<OrganizationalContact>();
        StringBuffer hql = new StringBuffer();
        hql.append(" select oc from OrganizationalContact oc  "
                + "join oc.person as per "
                + "join oc.organization as org where 1 = 1 ");
        if (oc.getId() != null) {
            hql.append(" and oc.id = ").append(oc.getId());
        }
        if (oc.getPerson() != null && oc.getOrganization()  != null
                && oc.getPerson().getId() != null && oc.getOrganization().getId() != null) {
            hql.append(" and per.id = ").append(oc.getPerson().getId());
            hql.append(" and org.id = ").append(oc.getOrganization().getId());
        }
        if (oc.getIdentifier() != null) {
            hql.append(" and oc.identifier = '").append(oc.getIdentifier()).append('\'');
        }
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;

        query = session.createQuery(hql.toString());
        queryList = query.list();

        if (queryList.size() > 1) {
            LOG.error(" Clinical Reasrch Staff should be more than 1 for any given criteria");
            throw new PAException(" Clinical Reasrch Staff should be more than 1 for any given criteria");

        }
    }  catch (HibernateException hbe) {
        LOG.error(" Error while retrieving Clinicial Research Staff" , hbe);
        throw new PAException(" Error while retrieving Clinicial Research Staff" , hbe);
    } finally {
        session.flush();
    }

    if (!queryList.isEmpty()) {
        ocOut = queryList.get(0);
    }
    return ocOut;
    }

    /**
     *
     * @param poOrg po
     * @return Organization o
     * @throws PAException pe
     */
    Organization createPAOrganization(OrganizationDTO poOrg) throws PAException {
        return createPAOrganization(convertPOToPAOrganization(poOrg));
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
    StatusCode convertPOEntifyStatusToPAEntityStatus(Cd cd) throws PAException {
        if (cd == null) {
            throw new PAException(" Cd cannot be null");
        }
        if ("ACTIVE".equalsIgnoreCase(cd.getCode())) {
            return StatusCode.ACTIVE;
        } else if ("INACTIVE".equalsIgnoreCase(cd.getCode())) {
            return StatusCode.INACTIVE;
        } else if ("NULLIFIED".equalsIgnoreCase(cd.getCode())) {
            return StatusCode.NULLIFIED;
        } else if ("PENDING".equalsIgnoreCase(cd.getCode())) {
            return StatusCode.PENDING;
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
    StatusCode convertPORoleStatusToPARoleStatus(Cd cd) throws PAException {
        if (cd == null) {
            throw new PAException(" Cd cannot be null");
        }
        if ("ACTIVE".equalsIgnoreCase(cd.getCode())) {
            return StatusCode.ACTIVE;
        } else if ("INACTIVE".equalsIgnoreCase(cd.getCode())) {
            return StatusCode.INACTIVE;
        } else if ("NULLIFIED".equalsIgnoreCase(cd.getCode())) {
            return StatusCode.NULLIFIED;
        } else if ("PENDING".equalsIgnoreCase(cd.getCode())) {
            return StatusCode.PENDING;
        } else if ("SUSPENDED".equalsIgnoreCase(cd.getCode())) {
            return StatusCode.SUSPENDED;
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
            session.save(organization);
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
            session.save(person);
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
