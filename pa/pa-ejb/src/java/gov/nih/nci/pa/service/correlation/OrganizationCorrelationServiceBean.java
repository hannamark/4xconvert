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

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OversightCommittee;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 * .
 * @author NAmiruddin
 *
 */
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveMethodLength", "PMD.CyclomaticComplexity",
    "PMD.ExcessiveClassLength", "PMD.NPathComplexity" })
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Interceptors(HibernateSessionInterceptor.class)
public class OrganizationCorrelationServiceBean implements OrganizationCorrelationServiceRemote {

    private static final Logger LOG  = Logger.getLogger(OrganizationCorrelationServiceBean.class);
    private static final String IRB_CODE = "Institutional Review Board (IRB)";

    /**
     *
     * @param orgPoIdentifier org id
     * @return Long
     * @throws PAException pe
     */
    public Long createHealthCareFacilityCorrelations(String orgPoIdentifier) throws PAException {
        LOG.debug("Entering createHealthCareFacilityCorrelations");

        CorrelationUtils corrUtils = new CorrelationUtils();
        if (orgPoIdentifier == null) {
            throw new PAException(" Organization PO Identifier is null");
        }
        // Step 1 : get the PO Organization
        OrganizationDTO poOrg = null;
        try {
            poOrg = PoRegistry.getOrganizationEntityService().
                getOrganization(IiConverter.convertToPoOrganizationIi(orgPoIdentifier));
        } catch (NullifiedEntityException e) {
           LOG.error("This Organization is no longer available instead use ");
           throw new PAException("This Organization is no longer available instead use ", e);
        }
        if (poOrg == null) {
            throw new PAException("PO and PA databases out of synchronization.  Error getting "
                    + "organization from PO for id = " + orgPoIdentifier + ".  ");
        }

        // Step 2 : check if PO has hcf correlation if not create one
        HealthCareFacilityDTO hcfDTO = new HealthCareFacilityDTO();
        List<HealthCareFacilityDTO> hcfDTOs = null;
        hcfDTO.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi(orgPoIdentifier));

        hcfDTOs = PoRegistry.getHealthCareFacilityCorrelationService().search(hcfDTO);
        if (hcfDTOs != null && hcfDTOs.size() > 1) {
            throw new PAException("PO hcfDTOs Correlation should not have more than 1  ");
        }
        if (hcfDTOs == null || hcfDTOs.isEmpty()) {
            try {
                Ii ii = PoRegistry.getHealthCareFacilityCorrelationService().createCorrelation(hcfDTO);
                hcfDTO = PoRegistry.getHealthCareFacilityCorrelationService().getCorrelation(ii);
            } catch (NullifiedRoleException e) {
                throw new PAException("NullifiedRoleException exception during get ClinicalResearchStaff " , e);
            } catch (EntityValidationException e) {
                throw new PAException("Validation exception during create ClinicalResearchStaff " , e);
            } catch (CurationException e) {
                throw new PAException("CurationException during create ClinicalResearchStaff " , e);
            }
        } else {
            hcfDTO = hcfDTOs.get(0);
        }


        // Step 3 : check for pa org, if not create one
        Organization paOrg = corrUtils.getPAOrganizationByIi(IiConverter.convertToPoOrganizationIi(orgPoIdentifier));
        if (paOrg == null) {
            paOrg = corrUtils.createPAOrganization(poOrg);
        }

        // Step 4 : Check of PA has hcf , if not create one
        HealthCareFacility hcf = corrUtils.getStructuralRoleByIi(DSetConverter.convertToIi(hcfDTO.getIdentifier()));
        if (hcf == null) {
            // create a new crs
            hcf = new HealthCareFacility();
            hcf.setOrganization(paOrg);
            hcf.setIdentifier(DSetConverter.convertToIi(hcfDTO.getIdentifier()).getExtension());
            hcf.setStatusCode(corrUtils.convertPORoleStatusToPARoleStatus(hcfDTO.getStatus()));
            corrUtils.createPADomain(hcf);
        }
        LOG.debug("Leaving createClinicalResearchStaffCorrelation");
        return hcf.getId();

    }


    /**
     * @param orgPoIdentifier org id
     * @return Long
     * @throws PAException pe
     */
    public Long createResearchOrganizationCorrelations(String orgPoIdentifier) throws PAException {
        LOG.debug("Entering createResearchOrganizationCorrelations");

        CorrelationUtils corrUtils = new CorrelationUtils();
        if (orgPoIdentifier == null) {
            throw new PAException(" Organization PO Identifier is null");
        }
        // Step 1 : get the PO Organization
        OrganizationDTO poOrg = null;
        try {
            poOrg = PoRegistry.getOrganizationEntityService().
                getOrganization(IiConverter.convertToPoOrganizationIi(orgPoIdentifier));
        } catch (NullifiedEntityException e) {
           LOG.error("This Organization is no longer available instead use ");
           throw new PAException("This Organization is no longer available instead use ", e);
        }
        if (poOrg == null) {
            throw new PAException("PO and PA databases out of synchronization.  Error getting "
                    + "organization from PO for id = " + orgPoIdentifier + ".  ");
        }

        // Step 2 : check if PO has hcf correlation if not create one
        ResearchOrganizationDTO roDTO = new ResearchOrganizationDTO();
        List<ResearchOrganizationDTO> roDTOs = null;
        roDTO.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi(orgPoIdentifier));
//        Cd cd = new Cd();
//        cd.setCode(CANCER_CENTER_CODE);
//        roDTO.setTypeCode(cd);
//        roDTO.setFundingMechanism(CdConverter.convertStringToCd(FUNDING_MECHANISM_CODE));
        roDTOs = PoRegistry.getResearchOrganizationCorrelationService().search(roDTO);
        if (roDTOs != null && roDTOs.size() > 1) {
//            throw new PAException("PO ResearchOrganizationDTOs Correlation should not have more than 1  ");
            LOG.warn("PO ResearchOrganizationDTOs Correlation has more than 1.  Using first.");
        }
        if (roDTOs == null || roDTOs.isEmpty()) {
            try {
                Ii ii = PoRegistry.getResearchOrganizationCorrelationService().createCorrelation(roDTO);
                roDTO = PoRegistry.getResearchOrganizationCorrelationService().getCorrelation(ii);
            } catch (NullifiedRoleException e) {
                throw new PAException("Validation exception during get ClinicalResearchStaff " , e);
            } catch (EntityValidationException e) {
                throw new PAException("Validation exception during create ClinicalResearchStaff " , e);
            } catch (CurationException e) {
                throw new PAException("Curation exception during create ClinicalResearchStaff " , e);
            }
        } else {
            roDTO = roDTOs.get(0);
        }


        // Step 3 : check for pa org, if not create one
        Organization paOrg = corrUtils.getPAOrganizationByIi(IiConverter.convertToPoOrganizationIi(orgPoIdentifier));
        if (paOrg == null) {
            paOrg = corrUtils.createPAOrganization(poOrg);
        }


        // Step 4 : Check of PA has hcf , if not create one
        ResearchOrganization ro = corrUtils.getStructuralRoleByIi(DSetConverter.convertToIi(roDTO.getIdentifier()));
        if (ro == null) {
            // create a new crs
            ro = new ResearchOrganization();
            ro.setOrganization(paOrg);
            ro.setIdentifier(DSetConverter.convertToIi(roDTO.getIdentifier()).getExtension());
            ro.setStatusCode(corrUtils.convertPORoleStatusToPARoleStatus(roDTO.getStatus()));
            corrUtils.createPADomain(ro);
        }
        LOG.debug("Leaving createResearchOrganizationCorrelation");
        return ro.getId();

    }
    /**
     * @param orgPoIdentifier org id
     * @return Long
     * @throws PAException pe
     */
    public Long createOversightCommitteeCorrelations(String orgPoIdentifier) throws PAException {
        LOG.debug("Entering createOversightCommitteeCorrelations().  ");

        CorrelationUtils corrUtils = new CorrelationUtils();
        if (orgPoIdentifier == null) {
            throw new PAException(" Organization PO Identifier is null");
        }
        // Step 1 : get the PO Organization
        OrganizationDTO poOrg = null;
        try {
            poOrg = PoRegistry.getOrganizationEntityService().
                getOrganization(IiConverter.convertToPoOrganizationIi(orgPoIdentifier));
        } catch (NullifiedEntityException e) {
            throw new PAException("This Organization is no longer available instead use ", e);
        }
        if (poOrg == null) {
            throw new PAException("PO and PA databases out of synchronization.  Error getting "
                    + "organization from PO for id = " + orgPoIdentifier + ".  ");
        }

        // Step 2 : check if PO has oc correlation if not create one
        OversightCommitteeDTO ocDTO = new OversightCommitteeDTO();
        List<OversightCommitteeDTO> ocDTOs = null;
        ocDTO.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi(orgPoIdentifier));
        ocDTO.setTypeCode(CdConverter.convertStringToCd(PAConstants.IRB_CODE));
        ocDTOs = PoRegistry.getOversightCommitteeCorrelationService().search(ocDTO);
        if (ocDTOs != null && ocDTOs.size() > 1) {
            throw new PAException("PO OversightCommitteeDTOs Correlation should not have more than 1.  ");
        }
        if (ocDTOs == null || ocDTOs.isEmpty()) {
            try {
                ocDTO.setTypeCode(CdConverter.convertStringToCd(IRB_CODE));
                Ii ii = PoRegistry.getOversightCommitteeCorrelationService().createCorrelation(ocDTO);
                ocDTO = PoRegistry.getOversightCommitteeCorrelationService().getCorrelation(ii);
            } catch (NullifiedRoleException e) {
                throw new PAException("Validation exception during get PO OversightCommittee.  " , e);
            } catch (EntityValidationException e) {
                throw new PAException("Validation exception during create PO OversightCommittee.  " , e);
            } catch (Exception e) {
                throw new PAException("Error thrown during get/create PO OversightCommitte w/type code = "
                        + IRB_CODE + ".  ", e);
            }
        } else {
            ocDTO = ocDTOs.get(0);
        }


        // Step 3 : check for pa org, if not create one
        Organization paOrg = corrUtils.getPAOrganizationByIi(IiConverter.convertToPoOrganizationIi(orgPoIdentifier));
        if (paOrg == null) {
            paOrg = corrUtils.createPAOrganization(poOrg);
        }

        // Step 4 : Check of PA has oc , if not create one
        OversightCommittee oc = corrUtils.getStructuralRoleByIi(DSetConverter.convertToIi(ocDTO.getIdentifier()));
        if (oc == null) {
            // create a new oversight committee
            oc = new OversightCommittee();
            oc.setOrganization(paOrg);
            oc.setIdentifier(DSetConverter.convertToIi(ocDTO.getIdentifier()).getExtension());
            oc.setStatusCode(corrUtils.convertPORoleStatusToPARoleStatus(ocDTO.getStatus()));
            corrUtils.createPADomain(oc);
        }
        LOG.debug("Leaving createOversightCommitteeCorrelations().  ");
        return oc.getId();
    }


    /***
     *
     * @param studyProtocolId sp id
     * @param functionalCode functional code
     * @return List org
     * @throws PAException e
     */
    @SuppressWarnings({ "PMD.ConsecutiveLiteralAppends", "unchecked" })
    public List<Organization> getOrganizationByStudySite(Long studyProtocolId ,
            StudySiteFunctionalCode functionalCode) throws PAException {

        Session session  = HibernateUtil.getCurrentSession();
        StringBuffer sb = new StringBuffer();
        sb.append("select org from Organization as org ");
        if (StudySiteFunctionalCode.TREATING_SITE.equals(functionalCode)) {
            sb.append(" join org.healthCareFacilities as orgRole  ");
        } else if (StudySiteFunctionalCode.COLLABORATORS.equals(functionalCode)) {
            sb.append(" join org.researchOrganizations as orgRole  ");
        } else if (StudySiteFunctionalCode.LEAD_ORGANIZATION.equals(functionalCode)) {
            sb.append(" join org.healthCareFacilities as orgRole  ");
        }

        sb.append(" join org.researchOrganizations as orgRole  "
                + " join orgRole.studySites as sps "
                + " join sps.studyProtocol as sp "
                + " where 1 = 1 and sp.id = " + studyProtocolId);
        if (StudySiteFunctionalCode.TREATING_SITE.equals(functionalCode)) {
            sb.append(" and sps.functionalCode in ('" + StudySiteFunctionalCode.TREATING_SITE + "')");
        } else if (StudySiteFunctionalCode.COLLABORATORS.equals(functionalCode)) {
            sb.append(" and sps.functionalCode in ("
                    + "'" + StudySiteFunctionalCode.FUNDING_SOURCE + "',"
                    + "'" + StudySiteFunctionalCode.LABORATORY + "',"
                    + "'" + StudySiteFunctionalCode.AGENT_SOURCE + "')");
        } else if (StudySiteFunctionalCode.LEAD_ORGANIZATION.equals(functionalCode)) {
            sb.append(" and sps.functionalCode in ('"
                    + StudySiteFunctionalCode.LEAD_ORGANIZATION + "')");
        }
        List<Organization> queryList = new ArrayList<Organization>();
        try {
            Query query = null;
            query = session.createQuery(sb.toString());
            //query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolIi));
            queryList = query.list();
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while retrieving StudyProtocol for id = " + studyProtocolId , hbe);
            throw new PAException(" Hibernate exception while retrieving "
                    + "StudyProtocol for id = " + studyProtocolId , hbe);

        }

        return queryList;

    }

    /**
     *
     * @param poOrg po
     * @return Organization o
     * @throws PAException pe
     */
    public Organization createPAOrganizationUsingPO(OrganizationDTO poOrg) throws PAException {
        return new CorrelationUtils().createPAOrganization(poOrg);
    }

    /**
     *
     * @param studyProtocolIi sp id
     * @param cd functional role code
     * @return Organization
     * @throws PAException onError
     */
    public Organization getOrganizationByFunctionRole(Ii studyProtocolIi , Cd cd) throws PAException {

        StudySiteDTO spart = new StudySiteDTO();
        spart.setFunctionalCode(cd);
        List<StudySiteDTO> spDtos = PoPaServiceBeanLookup.getStudySiteService()
                        .getByStudyProtocol(studyProtocolIi, spart);
        Organization o = null;
        if (spDtos != null && !spDtos.isEmpty()) {
            spart = spDtos.get(0);
            o = new CorrelationUtils().getPAOrganizationByIi(spart.getResearchOrganizationIi());
        }
        return o;
    }

    /**
     * returns the id of the Ct.gov Po id.
     * @return po identifier
     * @throws PAException on error
     */
    public String getCtGovPOIdentifier() throws  PAException {
        OrganizationDTO poOrgDto = new OrganizationDTO();
        poOrgDto.setName(EnOnConverter.convertToEnOn("ClinicalTrials.gov"));
        List<OrganizationDTO> poOrgs = PoRegistry.getOrganizationEntityService().search(poOrgDto);
        String identifier = null;
        if (poOrgs == null || poOrgs.isEmpty()) {
            poOrgDto.setPostalAddress(AddressConverterUtil.create("ct.gov.address", null, "ct.mun", "VA", "20171",
                    "USA"));
            DSet<Tel> telco = new DSet<Tel>();
            telco.setItem(new HashSet<Tel>());
            Tel t = new Tel();
            try {
                t.setValue(new URI("tel", "11111", null));
            telco.getItem().add(t);
            TelEmail telemail = new TelEmail();
            telemail.setValue(new URI("mailto:" + "ct@ct.gov"));

            telco.getItem().add(telemail);
            } catch (URISyntaxException e) {
                throw new PAException(e);
            }

            poOrgDto.setTelecomAddress(telco);
            try {
                Ii ii = PoRegistry.getOrganizationEntityService().createOrganization(poOrgDto);
                identifier = ii.getExtension();
            } catch (EntityValidationException e) {
                throw new PAException(e);
            } catch (CurationException e) {
                throw new PAException(e);
            }

        } else if (poOrgs.size() > 1) {
            throw new PAException(" there cannot be more than 1 record for ClinicalTrials.gov");
        } else {
            identifier = poOrgs.get(0).getIdentifier().getExtension();
        }
        return identifier;

    }


}
