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

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnOn;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OversightCommittee;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 * .
 * @author NAmiruddin
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Interceptors(HibernateSessionInterceptor.class)
public class OrganizationCorrelationServiceBean implements OrganizationCorrelationServiceRemote {

    private static final Logger LOG  = Logger.getLogger(OrganizationCorrelationServiceBean.class);
    private static final String IRB_CODE = "Institutional Review Board (IRB)";
    private final PAServiceUtils paServiceUtils = new PAServiceUtils();
    private CorrelationUtils corrUtils = new CorrelationUtils();

    /**
     * {@inheritDoc}
     */
    public Long createHcfWithExistingPoHcf(Ii poHcfIdentifier)
        throws PAException {
        HealthCareFacilityDTO hcfDTO = null;
        try {
            hcfDTO = PoRegistry.getHealthCareFacilityCorrelationService().getCorrelation(poHcfIdentifier);
        } catch (NullifiedRoleException e) {
            throw new PAException("This HealthCareFacility is no longer available.", e);
        }
        if (hcfDTO == null) {
            hcfDTO = getHcfByOtherId(poHcfIdentifier);
        }
        if (hcfDTO == null) {
            throw new PAException("Unable to find HealthCareFacility for identifier: " + poHcfIdentifier);
        }
        HealthCareFacility hcf = getCorrUtils()
                .getStructuralRoleByIi(DSetConverter.convertToIi(hcfDTO.getIdentifier()));
        if (hcf == null) {
            OrganizationDTO poOrgDTO;
            try {
                poOrgDTO = PoRegistry.getOrganizationEntityService().getOrganization(hcfDTO.getPlayerIdentifier());
            } catch (NullifiedEntityException e) {
                throw new PAException(PAUtil.handleNullifiedEntityException(e), e);
            }
            Organization paOrg = getCorrUtils().createPAOrganization(poOrgDTO);
            hcf = new HealthCareFacility();
            hcf.setOrganization(paOrg);
            hcf.setIdentifier(poHcfIdentifier.getExtension());
            hcf.setStatusCode(getCorrUtils().convertPORoleStatusToPARoleStatus(hcfDTO.getStatus()));
            getCorrUtils().createPADomain(hcf);
        }
        return hcf.getId();
    }

    private HealthCareFacilityDTO getHcfByOtherId(Ii poHcfIdentifier) throws PAException {
        HealthCareFacilityDTO hcfDTO = new HealthCareFacilityDTO();
        hcfDTO.setIdentifier(new DSet<Ii>());
        hcfDTO.getIdentifier().setItem(new HashSet<Ii>());
        hcfDTO.getIdentifier().getItem().add(poHcfIdentifier);
        List<HealthCareFacilityDTO> list = PoRegistry.getHealthCareFacilityCorrelationService().search(hcfDTO);
        if (CollectionUtils.isEmpty(list)) {
            throw new PAException("HealthCareFacility not found for identifier: " + poHcfIdentifier);
        } else if (list.size() > 1) {
            throw new PAException("Multiple HealthCareFacilities found for identifier: " + poHcfIdentifier);
        }
        hcfDTO = list.get(0);
        return hcfDTO;
    }

    /**
     *
     * @param orgPoIdentifier org id
     * @return Long
     * @throws PAException pe
     */
    public Long createHealthCareFacilityCorrelations(String orgPoIdentifier) throws PAException {
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
                    + "organization from PO for id = " + orgPoIdentifier + ". ");
        }

        // Step 2 : check if PO has hcf correlation if not create one
        HealthCareFacilityDTO hcfDTO = getOrCreatePAHealthCareFacilityCorrelation(orgPoIdentifier);


        // Step 3 : check for pa org, if not create one
        Organization paOrg = getCorrUtils().getPAOrganizationByIi(IiConverter.convertToPoOrganizationIi(
                orgPoIdentifier));
        if (paOrg == null) {
            paOrg = getCorrUtils().createPAOrganization(poOrg);
        }

        // Step 4 : Check of PA has hcf , if not create one
        HealthCareFacility hcf = getCorrUtils().getStructuralRoleByIi(DSetConverter.convertToIi(
                hcfDTO.getIdentifier()));
        if (hcf == null) {
            // create a new crs
            hcf = new HealthCareFacility();
            hcf.setOrganization(paOrg);
            hcf.setIdentifier(DSetConverter.convertToIi(hcfDTO.getIdentifier()).getExtension());
            hcf.setStatusCode(getCorrUtils().convertPORoleStatusToPARoleStatus(hcfDTO.getStatus()));
            getCorrUtils().createPADomain(hcf);
        }
        return hcf.getId();

    }

    private HealthCareFacilityDTO getOrCreatePAHealthCareFacilityCorrelation(String orgPoIdentifier)
    throws PAException {
        HealthCareFacilityDTO hcfDTO = new HealthCareFacilityDTO();
        hcfDTO.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi(orgPoIdentifier));

        List<HealthCareFacilityDTO> hcfDTOs =
            PoRegistry.getHealthCareFacilityCorrelationService().search(hcfDTO);

        if (CollectionUtils.isEmpty(hcfDTOs)) {
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
            if (hcfDTOs.size() > 1) {
                LOG.info("PO HealthCareFacilityDTOs Correlation has more than 1. Using first.  " + orgPoIdentifier);
            }
            hcfDTO = hcfDTOs.get(0);
        }

        return hcfDTO;
    }


    /**
     * @param orgPoIdentifier org id
     * @return Long
     * @throws PAException pe
     */
    public Long createResearchOrganizationCorrelations(String orgPoIdentifier) throws PAException {
        if (orgPoIdentifier == null) {
            throw new PAException(" Organization PO Identifier is null");
        }
        // Step 1 : get the PO Organization
        OrganizationDTO poOrg = null;
        try {
            poOrg = PoRegistry.getOrganizationEntityService().
                getOrganization(IiConverter.convertToPoOrganizationIi(orgPoIdentifier));
        } catch (NullifiedEntityException e) {
           String message = paServiceUtils.handleNullifiedOrganization(e);
           LOG.error(message);
           throw new PAException(message, e);

        }
        if (poOrg == null) {
            throw new PAException("PO and PA databases out of synchronization.  Error getting "
                    + "organization from PO for id = " + orgPoIdentifier + ".  ");
        }

        // Step 2 : check if PO has hcf correlation if not create one
        ResearchOrganizationDTO roDTO = getOrCreatePAResearchOrganizationCorrelation(orgPoIdentifier);

        // Step 3 : check for pa org, if not create one
        Organization paOrg = getCorrUtils().getPAOrganizationByIi(IiConverter.convertToPoOrganizationIi(
                orgPoIdentifier));
        if (paOrg == null) {
            paOrg = getCorrUtils().createPAOrganization(poOrg);
        }


        // Step 4 : Check of PA has hcf , if not create one
        ResearchOrganization ro = getCorrUtils().getStructuralRoleByIi(DSetConverter.convertToIi(
                roDTO.getIdentifier()));
        if (ro == null) {
            // create a new crs
            ro = new ResearchOrganization();
            ro.setOrganization(paOrg);
            ro.setIdentifier(DSetConverter.convertToIi(roDTO.getIdentifier()).getExtension());
            ro.setStatusCode(getCorrUtils().convertPORoleStatusToPARoleStatus(roDTO.getStatus()));
            getCorrUtils().createPADomain(ro);
        }
        return ro.getId();

    }
    /**
     * @param orgPoIdentifier org id
     * @return Long
     * @throws PAException pe
     */
    public Long createOversightCommitteeCorrelations(String orgPoIdentifier) throws PAException {
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
        OversightCommitteeDTO ocDTO = getOrCreatePAOversightCommitteeCorrelation(orgPoIdentifier);

        // Step 3 : check for pa org, if not create one
        Organization paOrg = getCorrUtils().getPAOrganizationByIi(IiConverter.convertToPoOrganizationIi(
                orgPoIdentifier));
        if (paOrg == null) {
            paOrg = getCorrUtils().createPAOrganization(poOrg);
        }

        // Step 4 : Check of PA has oc , if not create one
        return storeOversightCommittee(ocDTO, paOrg);
    }

    private Long storeOversightCommittee(OversightCommitteeDTO ocDTO, Organization paOrg)
    throws PAException {
        OversightCommittee oc =
            getCorrUtils().getStructuralRoleByIi(DSetConverter.convertToIi(ocDTO.getIdentifier()));
        if (oc == null) {
            // create a new oversight committee
            oc = new OversightCommittee();
            oc.setOrganization(paOrg);
            oc.setIdentifier(DSetConverter.convertToIi(ocDTO.getIdentifier()).getExtension());
            oc.setStatusCode(getCorrUtils().convertPORoleStatusToPARoleStatus(ocDTO.getStatus()));
            getCorrUtils().createPADomain(oc);
        }
        return oc.getId();
    }

    private OversightCommitteeDTO getOrCreatePAOversightCommitteeCorrelation(String orgPoIdentifier)
    throws PAException {
        OversightCommitteeDTO ocDTO = new OversightCommitteeDTO();
        List<OversightCommitteeDTO> ocDTOs = null;
        ocDTO.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi(orgPoIdentifier));
        ocDTO.setTypeCode(CdConverter.convertStringToCd(PAConstants.IRB_CODE));
        ocDTOs = PoRegistry.getOversightCommitteeCorrelationService().search(ocDTO);
        if (CollectionUtils.isEmpty(ocDTOs)) {
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
            if (ocDTOs.size() > 1) {
                LOG.info("PO OversightCommitteeDTOs Correlation has more than 1. Using first.  " + orgPoIdentifier);
            }
            ocDTO = ocDTOs.get(0);
        }

        return ocDTO;

    }

    private ResearchOrganizationDTO getOrCreatePAResearchOrganizationCorrelation(String orgPoIdentifier)
    throws PAException {
        ResearchOrganizationDTO roDTO = new ResearchOrganizationDTO();
        List<ResearchOrganizationDTO> roDTOs = null;
        roDTO.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi(orgPoIdentifier));
        roDTOs = PoRegistry.getResearchOrganizationCorrelationService().search(roDTO);

        if (CollectionUtils.isEmpty(roDTOs)) {
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
            if (roDTOs.size() > 1) {
                LOG.info("PO ResearchOrganizationDTOs Correlation has more than 1.  Using first. " + orgPoIdentifier);
            }
            roDTO = roDTOs.get(0);
        }

        return roDTO;
    }

    private String setOrgRoleSql(StudySiteFunctionalCode functionalCode) {
        String returnVal = "";
        if (StudySiteFunctionalCode.TREATING_SITE.equals(functionalCode)) {
            returnVal = " join org.healthCareFacilities as orgRole  ";
        } else if (StudySiteFunctionalCode.COLLABORATORS.equals(functionalCode)) {
            returnVal = " join org.researchOrganizations as orgRole  ";
        } else if (StudySiteFunctionalCode.LEAD_ORGANIZATION.equals(functionalCode)) {
            returnVal = " join org.healthCareFacilities as orgRole  ";
        }
        return returnVal;
    }

    /***
     *
     * @param studyProtocolId sp id
     * @param functionalCode functional code
     * @return List org
     * @throws PAException e
     */
    @SuppressWarnings("unchecked")
    public List<Organization> getOrganizationByStudySite(Long studyProtocolId, StudySiteFunctionalCode functionalCode)
            throws PAException {

        Session session  = HibernateUtil.getCurrentSession();
        StringBuffer sb = new StringBuffer();
        sb.append("select org from Organization as org ");
        sb.append(setOrgRoleSql(functionalCode));
        sb.append(" join org.researchOrganizations as orgRole join orgRole.studySites as sps "
                + "join sps.studyProtocol as sp where 1 = 1 and sp.id = ");
        sb.append(studyProtocolId);
        if (StudySiteFunctionalCode.TREATING_SITE.equals(functionalCode)) {
            sb.append(" and sps.functionalCode in ('" + StudySiteFunctionalCode.TREATING_SITE + "')");
        } else if (StudySiteFunctionalCode.COLLABORATORS.equals(functionalCode)) {
            sb.append(" and sps.functionalCode in ('" + StudySiteFunctionalCode.FUNDING_SOURCE + "', '"
                    + StudySiteFunctionalCode.LABORATORY + "', '" + StudySiteFunctionalCode.AGENT_SOURCE + "')");
        } else if (StudySiteFunctionalCode.LEAD_ORGANIZATION.equals(functionalCode)) {
            sb.append(" and sps.functionalCode in ('"
                    + StudySiteFunctionalCode.LEAD_ORGANIZATION + "')");
        }
        List<Organization> queryList = new ArrayList<Organization>();
        Query query = null;
        query = session.createQuery(sb.toString());
        queryList = query.list();
        return queryList;

    }

    /**
     *
     * @param poOrg po
     * @return Organization o
     * @throws PAException pe
     */
    public Organization createPAOrganizationUsingPO(OrganizationDTO poOrg) throws PAException {
        return getCorrUtils().createPAOrganization(poOrg);
    }

    /**
     *
     * @param studyProtocolIi sp id
     * @param cd functional role code
     * @return Organization
     * @throws PAException onError
     */
    public Organization getOrganizationByFunctionRole(Ii studyProtocolIi , Cd cd) throws PAException {
        Organization o = null;
        Ii roIi = getROByFunctionRole(studyProtocolIi, cd);
        if (PAUtil.isIiNotNull(roIi)) {
            o = getCorrUtils().getPAOrganizationByIi(roIi);
        }
        return o;
    }

    /**
     * {@inheritDoc}
     */
    public Ii getROByFunctionRole(Ii studyProtocolIi, Cd cd) throws PAException {
        Ii returnVal = null;
        StudySiteDTO spart = new StudySiteDTO();
        spart.setFunctionalCode(cd);
        List<StudySiteDTO> spDtos = PaRegistry.getStudySiteService()
                        .getByStudyProtocol(studyProtocolIi, spart);

        if (spDtos != null && !spDtos.isEmpty()) {
            returnVal = spDtos.get(0).getResearchOrganizationIi();
        }

        return returnVal;
    }

    private EnOn convertIdTypeToName(String identifierType) throws PAException {
        EnOn name = null;
        if (identifierType.equalsIgnoreCase(PAConstants.NCT_IDENTIFIER_TYPE)) {
            name = EnOnConverter.convertToEnOn(PAConstants.CTGOV_ORG_NAME);
        } else if (identifierType.equalsIgnoreCase(PAConstants.CTEP_IDENTIFIER_TYPE)) {
            name = EnOnConverter.convertToEnOn(PAConstants.CTEP_ORG_NAME);
        } else if (identifierType.equalsIgnoreCase(PAConstants.DCP_IDENTIFIER_TYPE)) {
            name = EnOnConverter.convertToEnOn(PAConstants.DCP_ORG_NAME);
        }

        if (name == null) {
            throw new PAException("Org name is null");
        }
        return name;
    }

    /**
     * returns the id of the of give type.
     * @param identifierType type
     * @return po identifier
     * @throws PAException on error
     */
    public String getPOOrgIdentifierByIdentifierType(String identifierType) throws PAException {
        OrganizationDTO poOrgDto = new OrganizationDTO();

        poOrgDto.setName(convertIdTypeToName(identifierType));

        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        List<OrganizationDTO> poOrgs = null;
        try {
            poOrgs = PoRegistry.getOrganizationEntityService().search(poOrgDto,
                    limit);
        } catch (TooManyResultsException e) {
            throw new PAException(e);
        }

        String identifier = null;
        if (poOrgs == null || poOrgs.isEmpty()) {
            throw new PAException("No org found");

        } else if (poOrgs.size() > 1) {
            throw new PAException(" there cannot be more than 1 record for"
                    + identifierType);
        } else {
            identifier = poOrgs.get(0).getIdentifier().getExtension();
        }
        return identifier;
    }
    /**
     *
     * @param orgPoIdentifier id
     * @return ROId of PO
     * @throws PAException on error
     */
    public Ii getPoResearchOrganizationByEntityIdentifier(Ii orgPoIdentifier) throws PAException {
        Ii poROIi = null;
        if (PAUtil.isIiNull(orgPoIdentifier)) {
            throw new PAException("Ii Cannot be null");
        }
        // Step 1 : get correlation if there
        ResearchOrganizationDTO criteriaRODTO = new ResearchOrganizationDTO();
        List<ResearchOrganizationDTO> roDTOs = null;
        criteriaRODTO.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi(orgPoIdentifier.getExtension()));
        roDTOs = PoRegistry.getResearchOrganizationCorrelationService().search(criteriaRODTO);
        if (CollectionUtils.isNotEmpty(roDTOs)) {
            poROIi = DSetConverter.convertToIi(roDTOs.get(0).getIdentifier());
        }
        return poROIi;
    }


    /**
     * @param corrUtils the corrUtils to set
     */
    public void setCorrUtils(CorrelationUtils corrUtils) {
        this.corrUtils = corrUtils;
    }


    /**
     * @return the corrUtils
     */
    public CorrelationUtils getCorrUtils() {
        return corrUtils;
    }

}
