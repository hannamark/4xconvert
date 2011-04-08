/*
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are  controlled by,  or  are under common control  with the
 * entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market, publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the  caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the  foregoing rights set  out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation
 * caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License.  This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
 * Agreement. If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
 * distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below.  Your redistributions in object code
 * form must reproduce the above copyright notice,  this list of  conditions  and the disclaimer  of  Article  6  in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * 2.  Your end-user documentation included with the redistribution, if any, must include the  following acknowledgment:
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
 * party proprietary programs,  You agree  that You are solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation  to  secure  any required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications
 * and to the derivative works, and You may provide  additional  or  different  license  terms  and conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction,  and  distribution of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL  ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO, PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 */
package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.coppa.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OversightCommittee;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;
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

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

/**
 * Synchronization service bean for organization and its structural roles.
 *
 * @author Naveen Amiruddin
 * @since 07/07/2007
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, HibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrganizationSynchronizationServiceBean implements OrganizationSynchronizationServiceRemote {

    private static final Logger LOG = Logger.getLogger(OrganizationSynchronizationServiceBean.class);
    private static CorrelationUtils cUtils = new CorrelationUtils();
    private static PAServiceUtils paServiceUtil = new PAServiceUtils();

    @EJB
    private StudySiteServiceLocal spsLocal;

    /**
     * @param orgIdentifier ii of organization
     * @throws PAException on error
     */
    public void synchronizeOrganization(final Ii orgIdentifier) throws PAException {
        OrganizationDTO orgDto = null;
        try {
            orgDto = PoRegistry.getOrganizationEntityService().getOrganization(orgIdentifier);
            updateOrganization(orgIdentifier, orgDto);
        } catch (NullifiedEntityException e) {
            LOG.info("This organization is nullified " + orgIdentifier.getExtension());
            updateOrganization(orgIdentifier, null);
        }
    }

    /***
     *
     * @param hcfIdentifier po HealthCareFacility identifier
     * @throws PAException on error
     */
    public void synchronizeHealthCareFacility(final Ii hcfIdentifier) throws PAException {

        HealthCareFacilityDTO hcfDto = null;
        try {
            hcfDto = PoRegistry.getHealthCareFacilityCorrelationService().getCorrelation(hcfIdentifier);
            updateHealthCareFacility(hcfIdentifier, hcfDto);
        } catch (NullifiedRoleException e) {
            LOG.info("This HealthCareFacility is nullified " + hcfIdentifier.getExtension());
            updateHealthCareFacility(hcfIdentifier, null);
        }
    }

    /***
     *
     * @param oscIdentifier po OversightCommittee identifier
     * @throws PAException on error
     */
    public void synchronizeOversightCommittee(final Ii oscIdentifier) throws PAException {
        OversightCommitteeDTO oscDto = null;
        try {
            oscDto = PoRegistry.getOversightCommitteeCorrelationService().getCorrelation(oscIdentifier);
            updateOversightCommittee(oscIdentifier, oscDto);
        } catch (NullifiedRoleException e) {
            LOG.info("This OversightCommittee is nullified " + oscIdentifier.getExtension());
            updateOversightCommittee(oscIdentifier, null);
        }
    }

    /***
     *
     * @param roIdentifier po ResearchOrganization identifier
     * @throws PAException on error
     */
    public void synchronizeResearchOrganization(final Ii roIdentifier) throws PAException {
        ResearchOrganizationDTO roDto = null;
        try {
            roDto = PoRegistry.getResearchOrganizationCorrelationService().getCorrelation(roIdentifier);
            updateResearchOrganization(roIdentifier, roDto);
        } catch (NullifiedRoleException e) {
            LOG.info("This ResearchOrganization is nullified " + roIdentifier.getExtension());
            updateResearchOrganization(roIdentifier, null);

        }
    }

    private void updateOrganization(final Ii ii, final OrganizationDTO orgDto) throws PAException {
        Organization paOrg = cUtils.getPAOrganizationByIi(ii);

        if (paOrg != null) {
            Session session = HibernateUtil.getCurrentSession();
            // update the organization

            if (orgDto == null) {
                // its nullified
                paOrg.setStatusCode(EntityStatusCode.NULLIFIED);

                final Ii dupId = paServiceUtil.getDuplicateOrganizationIi(ii);
                if (dupId != null) {
                    paServiceUtil.getOrCreatePAOrganizationByIi(dupId);
                    try {
                        updateRegistryUsers(ii, dupId);
                        updateStudyResourcing(ii, dupId);
                        updateStudyProtocolStage(ii, dupId);
                    } catch (NullifiedEntityException e) {
                        LOG.error("Org was nullified with nullified duplicate.");
                    }
                }
            } else {
                // that means its not nullified
                Organization newOrg = cUtils.convertPOToPAOrganization(orgDto);
                paOrg.setCity(newOrg.getCity());
                paOrg.setCountryName(newOrg.getCountryName());
                paOrg.setName(newOrg.getName());
                paOrg.setPostalCode(newOrg.getPostalCode());
                paOrg.setState(newOrg.getState());
                paOrg.setStatusCode(newOrg.getStatusCode());
            }
            paOrg.setDateLastUpdated(new Timestamp((new Date()).getTime()));
            paOrg.setUserLastUpdated(CSMUserService.getInstance().getCSMUser(CaseSensitiveUsernameHolder.getUser()));
            session.update(paOrg);
            session.flush();
        }
    }

    private void updateRegistryUsers(Ii identifier, Ii dupId) throws NullifiedEntityException, PAException {
        OrganizationDTO poOrg = PoRegistry.getOrganizationEntityService().getOrganization(dupId);

        Session session = HibernateUtil.getCurrentSession();
        Criteria crit = session.createCriteria(RegistryUser.class);
        crit.add(Expression.eq("affiliatedOrganizationId", Long.valueOf(identifier.getExtension())));
        List<RegistryUser> regUsers = crit.list();

        for (RegistryUser ru : regUsers) {
            ru.setAffiliatedOrganizationId(Long.valueOf(dupId.getExtension()));
            ru.setAffiliateOrg(EnOnConverter.convertEnOnToString(poOrg.getName()));
            session.saveOrUpdate(ru);
        }
        session.flush();
    }

    private void updateStudyResourcing(Ii identifier, Ii dupId) throws NullifiedEntityException, PAException {

        Organization oldPaOrg = cUtils.getPAOrganizationByIi(identifier);
        Organization newPaOrg = cUtils.getPAOrganizationByIi(dupId);
        Session session = HibernateUtil.getCurrentSession();
        session.createQuery("update StudyResourcing set organizationIdentifier = :newPaOrgId "
                + "where organizationIdentifier = :oldPaOrgId")
        .setString("newPaOrgId", String.valueOf(newPaOrg.getId()))
        .setString("oldPaOrgId", String.valueOf(oldPaOrg.getId()))
        .executeUpdate();
    }

    private void updateStudyProtocolStage(Ii identifier, Ii dupId) throws NullifiedEntityException, PAException {
        Organization oldPaOrg = cUtils.getPAOrganizationByIi(identifier);
        Organization newPaOrg = cUtils.getPAOrganizationByIi(dupId);
        cUtils.updateItemIdForStudyProtocolStage("leadOrganizationIdentifier", oldPaOrg.getIdentifier(),
                newPaOrg.getIdentifier());
        cUtils.updateItemIdForStudyProtocolStage("sponsorIdentifier",
                oldPaOrg.getIdentifier(), newPaOrg.getIdentifier());
        cUtils.updateItemIdForStudyProtocolStage("summaryFourOrgIdentifier",
                oldPaOrg.getIdentifier(), newPaOrg.getIdentifier());
        cUtils.updateItemIdForStudyProtocolStage("siteSummaryFourOrgIdentifier", oldPaOrg.getIdentifier(),
                newPaOrg.getIdentifier());
        cUtils.updateItemIdForStudyProtocolStage("submitterOrganizationIdentifier", oldPaOrg.getIdentifier(),
                newPaOrg.getIdentifier());
    }

    private void updateResearchOrganization(final Ii roIdentifier, final ResearchOrganizationDTO roDto)
            throws PAException {
        Session session = null;
        ResearchOrganization ro = cUtils.getStructuralRoleByIi(roIdentifier);
        StructuralRoleStatusCode newRoleCode = null;
        Ii roCurrentIi = roIdentifier;
        if (ro != null) {
            session = HibernateUtil.getCurrentSession();
            if (roDto == null) {
                // this is a nullified scenario .....
                Long duplicateRoId = null;
                Ii dupSRIi = paServiceUtil.getDuplicateIiOfNullifiedSR(roIdentifier);
                if (PAUtil.isIiNotNull(dupSRIi)) {
                    // this is nullified scenario with nullified structural role with duplicate
                    ResearchOrganization dupRo = paServiceUtil.getOrCreateOrganizationalStructuralRoleInPA(dupSRIi);
                    duplicateRoId = dupRo.getId();
                    newRoleCode = dupRo.getStatusCode();
                    roCurrentIi = IiConverter.convertToPoResearchOrganizationIi(duplicateRoId.toString());
                    replaceStudySiteIdentifiers(IiConverter.convertToPoResearchOrganizationIi(ro.getId().toString()),
                            roCurrentIi);
                    ro.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
                } else {
                    // this is nullified scenario with nullified org or nullified structural role no duplicate
                    ro.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
                    newRoleCode = StructuralRoleStatusCode.NULLIFIED;
                }
            } else {
                paServiceUtil.updateScoper(roDto.getPlayerIdentifier(), ro);
                if (!ro.getStatusCode().equals(cUtils.convertPORoleStatusToPARoleStatus(roDto.getStatus()))) {
                    // this is a update scenario with a status change
                    newRoleCode = cUtils.convertPORoleStatusToPARoleStatus(roDto.getStatus());
                    ro.setStatusCode(newRoleCode);
                }
            }
            ro.setDateLastUpdated(new Timestamp((new Date()).getTime()));
            session.update(ro);
            session.flush();
            spsLocal.cascadeRoleStatus(roCurrentIi, CdConverter.convertToCd(newRoleCode));
        }
    }

    private void updateOversightCommittee(final Ii oscIdentifier, final OversightCommitteeDTO oscDto)
            throws PAException {
        Session session = null;
        OversightCommittee osc = cUtils.getStructuralRoleByIi(oscIdentifier);
        StructuralRoleStatusCode newRoleCode = null;
        Ii hcfCurrentIi = oscIdentifier;
        if (osc != null) {
            session = HibernateUtil.getCurrentSession();
            if (oscDto == null) {
                // this is a nullified scenario .....
                Ii dupSRIi = paServiceUtil.getDuplicateIiOfNullifiedSR(oscIdentifier);
                if (PAUtil.isIiNotNull(dupSRIi)) {
                    OversightCommittee dupOsc = paServiceUtil.getOrCreateOrganizationalStructuralRoleInPA(dupSRIi);
                    Long duplicateOscId = dupOsc.getId();
                    newRoleCode = dupOsc.getStatusCode();
                    hcfCurrentIi = IiConverter.convertToPoOversightCommitteeIi(duplicateOscId.toString());
                    replaceStudySiteIdentifiers(IiConverter.convertToPoOversightCommitteeIi(osc.getId().toString()),
                            hcfCurrentIi);
                    osc.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
                } else {
                    // this is nullified scenario with no org
                    osc.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
                    newRoleCode = StructuralRoleStatusCode.NULLIFIED;
                }
            } else {
                paServiceUtil.updateScoper(oscDto.getPlayerIdentifier(), osc);
                if (!osc.getStatusCode().equals(cUtils.convertPORoleStatusToPARoleStatus(oscDto.getStatus()))) {
                    // this is a update scenario with a status change
                    newRoleCode = cUtils.convertPORoleStatusToPARoleStatus(oscDto.getStatus());
                    osc.setStatusCode(newRoleCode);
                }
            }
            osc.setDateLastUpdated(new Timestamp((new Date()).getTime()));
            session.update(osc);
            session.flush();
            spsLocal.cascadeRoleStatus(hcfCurrentIi, CdConverter.convertToCd(newRoleCode));
        }
    }

    private void updateHealthCareFacility(final Ii hcfIdentifier, final HealthCareFacilityDTO hcfDto)
            throws PAException {
        Session session = null;
        HealthCareFacility hcf = cUtils.getStructuralRoleByIi(hcfIdentifier);
        StructuralRoleStatusCode newRoleCode = null;
        Ii hcfCurrentIi = hcfIdentifier;
        if (hcf != null) {
            session = HibernateUtil.getCurrentSession();
            if (hcfDto == null) {
                // this is a nullified scenario .....
                // check sr has duplicate
                Ii dupSRIi = paServiceUtil.getDuplicateIiOfNullifiedSR(hcfIdentifier);
                if (PAUtil.isIiNotNull(dupSRIi)) {
                    HealthCareFacility dupHcf = paServiceUtil.getOrCreateOrganizationalStructuralRoleInPA(dupSRIi);
                    Long duplicateHcfId = dupHcf.getId();
                    newRoleCode = dupHcf.getStatusCode();
                    hcfCurrentIi = IiConverter.convertToPoHealthCareFacilityIi(duplicateHcfId.toString());
                    replaceStudySiteIdentifiers(IiConverter.convertToPoHealthCareFacilityIi(hcf.getId().toString()),
                            hcfCurrentIi);
                    hcf.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
                } else {
                    // this is nullified scenario with no org
                    hcf.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
                    newRoleCode = StructuralRoleStatusCode.NULLIFIED;
                }
            } else {
                paServiceUtil.updateScoper(hcfDto.getPlayerIdentifier(), hcf);
                if (!hcf.getStatusCode().equals(cUtils.convertPORoleStatusToPARoleStatus(hcfDto.getStatus()))) {
                    // this is a update scenario with a status change
                    newRoleCode = cUtils.convertPORoleStatusToPARoleStatus(hcfDto.getStatus());
                    hcf.setStatusCode(newRoleCode);
                }
            }
            hcf.setDateLastUpdated(new Timestamp((new Date()).getTime()));
            session.update(hcf);
            session.flush();
            spsLocal.cascadeRoleStatus(hcfCurrentIi, CdConverter.convertToCd(newRoleCode));
        }
    }

    private void replaceStudySiteIdentifiers(final Ii from, final Ii to) {

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
        LOG.debug("Sql for update " + sql);
        LOG.debug("total records got update in STUDY_SITE IS " + i);
    }


    /**
     * @param spsLocal the spsLocal to set
     */
    public void setSpsLocal(StudySiteServiceLocal spsLocal) {
        this.spsLocal = spsLocal;
    }
}
