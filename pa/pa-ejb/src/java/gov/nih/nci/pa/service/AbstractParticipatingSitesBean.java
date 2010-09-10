/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This pa Software License (the License) is between NCI and You. You (or 
 * Your) shall mean a person or an entity, and all other entities that control, 
 * are controlled by, or are under common control with the entity. Control for 
 * purposes of this definition means (i) the direct or indirect power to cause 
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares, 
 * or (iii) beneficial ownership of such entity. 
 *
 * This License is granted provided that You agree to the conditions described 
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up, 
 * no-charge, irrevocable, transferable and royalty-free right and license in 
 * its rights in the pa Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, protectedly display, protectedly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and 
 * have distributed to and by third parties the pa Software and any 
 * modifications and derivative works thereof; and (iii) sublicense the 
 * foregoing rights set out in (i) and (ii) to third parties, including the 
 * right to license such rights to further third parties. For sake of clarity, 
 * and not by way of limitation, NCI shall have no right of accounting or right 
 * of payment from You or Your sub-licensees for the rights granted under this 
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the 
 * above copyright notice, this list of conditions and the disclaimer and 
 * limitation of liability of Article 6, below. Your redistributions in object 
 * code form must reproduce the above copyright notice, this list of conditions 
 * and the disclaimer of Article 6 in the documentation and/or other materials 
 * provided with the distribution, if any. 
 *
 * Your end-user documentation included with the redistribution, if any, must 
 * include the following acknowledgment: This product includes software 
 * developed by 5AM and the National Cancer Institute. If You do not include 
 * such end-user documentation, You shall include this acknowledgment in the 
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM" 
 * to endorse or promote products derived from this Software. This License does 
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the 
 * terms of this License. 
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this 
 * Software into Your proprietary programs and into any third party proprietary 
 * programs. However, if You incorporate the Software into third party 
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software 
 * into such third party proprietary programs and for informing Your 
 * sub-licensees, including without limitation Your end-users, of their 
 * obligation to secure any required permissions from such third parties before 
 * incorporating the Software into such third party proprietary software 
 * programs. In the event that You fail to obtain such permissions, You agree 
 * to indemnify NCI for any claims against NCI by such third parties, except to 
 * the extent prohibited by law, resulting from Your failure to obtain such 
 * permissions. 
 *
 * For sake of clarity, and not by way of limitation, You may add Your own 
 * copyright statement to Your modifications and to the derivative works, and 
 * You may provide additional or different license terms and conditions in Your 
 * sublicenses of modifications of the Software, or any derivative works of the 
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY, 
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO 
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR 
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.dto.PAOrganizationalContactDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.convert.OrganizationalContactConverter;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.correlation.ClinicalResearchStaffCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.HealthCareProviderCorrelationBean;
import gov.nih.nci.pa.service.correlation.PABaseCorrelation;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.HashSet;
import java.util.List;

import org.apache.axis.utils.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Helper for ParticipatingSiteBeanLocal.
 * @author mshestopalov
 *
 */
public abstract class AbstractParticipatingSitesBean extends AbstractBaseParticipatingSiteBean {

    /**
     * getStudyProtocolDTOFromNciId.
     * @param studyProtocolIi ii
     * @return dto
     * @throws PAException when error
     * @throws TooManyResultsException when error
     */
    protected StudyProtocolDTO getStudyProtocolDTOFromNciId(Ii studyProtocolIi) 
        throws PAException, TooManyResultsException {
        StudyProtocolDTO studyProtocolDTO = null;
        // this will be the NCI id.
        if (IiConverter.STUDY_PROTOCOL_ROOT.equals(studyProtocolIi.getRoot())) {
            StudyProtocolDTO spDTO = new StudyProtocolDTO();
            spDTO.setSecondaryIdentifiers(new DSet<Ii>());
            spDTO.getSecondaryIdentifiers().setItem(new HashSet<Ii>());
            spDTO.getSecondaryIdentifiers().getItem().add(studyProtocolIi);
            LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS , 0);
            List<StudyProtocolDTO> spList =  getStudyProtocolService().search(spDTO, limit);
            if (spList.isEmpty() || spList.size() > 1) {
                throw new PAException("could not find unique trial with this identifier.");
            }
            studyProtocolDTO = spList.get(0);
        } else if (StringUtils.isEmpty(studyProtocolIi.getRoot()) 
                && !StringUtils.isEmpty(studyProtocolIi.getExtension())) {
            studyProtocolDTO =  getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        } else {
            throw new PAException("unrecognizable trial Ii");
        }
        
        return studyProtocolDTO;
    }
    /**
     * genIdentifierPerson.
     * @param ctepInvIi person ii
     * @return ii
     * @throws PAException when error
     */
    protected Ii genIdentifierPerson(Ii ctepInvIi) throws PAException {
        IdentifiedPersonDTO criteria = new IdentifiedPersonDTO();
        criteria.setAssignedId(ctepInvIi);
        List<IdentifiedPersonDTO> idps = PoRegistry.getIdentifiedPersonEntityService().search(criteria);
        if (idps.isEmpty()) {
            throw new PAException("Provided Person ctep id but did not find a corresponding"
                    + " Identified Person in PO.");
        } else if (idps.size() > 1) {
            throw new PAException("more than 1 Identified Person found for given ctep id.");
        }
        return idps.get(0).getPlayerIdentifier();
    }
    /**
     * getPersonIiFromCtepId.
     * @param investigatorDTO person dto
     * @return ii
     * @throws EntityValidationException when error
     * @throws CurationException when error
     * @throws PAException when error
     */
    protected Ii getPersonIiFromCtepId(PersonDTO investigatorDTO) 
        throws EntityValidationException, CurationException, PAException {
        Ii investigatorIi = null;
        
        if (investigatorDTO.getIdentifier() == null 
                || investigatorDTO.getIdentifier().getExtension() == null) {
            investigatorIi = PoRegistry.getPersonEntityService().createPerson(investigatorDTO);
        } else if (IiConverter.PERSON_ROOT.equals(investigatorDTO.getIdentifier().getRoot())) {
            investigatorIi = investigatorDTO.getIdentifier();
        } else if (IiConverter.CTEP_PERSON_IDENTIFIER_ROOT.equals(investigatorDTO.getIdentifier().getRoot())) {    
            investigatorIi = genIdentifierPerson(investigatorDTO.getIdentifier());
        } else {
            throw new PAException("Could not determine root of investigator.");
        }
        return investigatorIi;
    }
    
    /**
     * getStudySiteIiByTrialAndOrg.
     * @param studyProtocolIi ii
     * @param orgIi ii
     * @return ii
     * @throws EntityValidationException when error
     * @throws CurationException when error
     * @throws PAException when error
     */
    protected Ii getStudySiteIiByTrialAndOrg(Ii studyProtocolIi, Ii orgIi) 
        throws EntityValidationException, CurationException, PAException {
        
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        Query query = null;
        String hql = "select ss from StudySite ss " 
                + " join ss.healthCareFacility as hcf "
                + " join ss.studyProtocol as sp " 
                + " join sp.documentWorkflowStatuses as dws  "
                + " where sp.id = :StudyProtocolIdentifier "
                + " and hcf.organization.identifier = :OrganizationIdentifier " 
                + " and ss.functionalCode = '" + StudySiteFunctionalCode.TREATING_SITE + "'" 
                + " and dws.statusCode  <> '" + DocumentWorkflowStatusCode.REJECTED + "'" + " and sp.statusCode ='"
                + ActStatusCode.ACTIVE + "'" + " and ( dws.id in (select max(id) from DocumentWorkflowStatus as dws1 "
                + "where dws.studyProtocol = dws1.studyProtocol ) or dws.id is null ) "
                + "order by ss.dateLastUpdated desc";
         
        query = session.createQuery(hql);
        query.setParameter("StudyProtocolIdentifier", Long.valueOf(studyProtocolIi.getExtension()));
        query.setParameter("OrganizationIdentifier", orgIi.getExtension());
        @SuppressWarnings("unchecked")
        List<StudySite> qList = query.list();
        if (qList == null || qList.isEmpty() || qList.get(0).getId() == null) {
            return null;
        } else {
            return IiConverter.convertToStudySiteIi(qList.get(0).getId());
        }
    }
    
    /**
     * generateHcf.
     * @param ctepOrgIi ii
     * @return ii
     * @throws PAException when error
     */
    protected Ii generateHcf(Ii ctepOrgIi) throws PAException {
        HealthCareFacilityDTO criteria = new HealthCareFacilityDTO();
        criteria.setIdentifier(new DSet<Ii>());
        criteria.getIdentifier().setItem(new HashSet<Ii>());
        criteria.getIdentifier().getItem().add(ctepOrgIi);
        List<HealthCareFacilityDTO> hcfs = 
            PoRegistry.getHealthCareFacilityCorrelationService().search(criteria);
        if (hcfs.isEmpty()) {
            throw new PAException("Provided Org ctep id but did not find a corresponding HCF in PO.");
        } else if (hcfs.size() > 1) {
            throw new PAException("more than 1 HCF found for given ctep id.");
        }             
        return hcfs.get(0).getPlayerIdentifier();
    }
    
    /**
     * getOrganizationIiFromCtepId.
     * @param organizationDTO dto
     * @return ii
     * @throws EntityValidationException when error
     * @throws CurationException when error
     * @throws PAException when error
     */
    protected Ii getOrganizationIiFromCtepId(OrganizationDTO organizationDTO) 
        throws EntityValidationException, CurationException, PAException {
        Ii orgIi = null;
        if (organizationDTO.getIdentifier() == null 
                || organizationDTO.getIdentifier().getExtension() == null) {
            orgIi = PoRegistry.getOrganizationEntityService().createOrganization(organizationDTO);
        } else if (IiConverter.ORG_ROOT.equals(organizationDTO.getIdentifier().getRoot())) {
            orgIi = organizationDTO.getIdentifier();
        } else if (IiConverter.CTEP_ORG_IDENTIFIER_ROOT.equals(organizationDTO.getIdentifier().getRoot())) {
            orgIi = generateHcf(organizationDTO.getIdentifier());
        } else {
            throw new PAException("Root of org Id provided is incorrect.");
        }
        return orgIi;
    }
    
    /**
     * updateStudyParticationContactRecord.
     * @param trialType string
     * @param contactDTOList dto
     * @param poOrgId ii
     * @param selectedPersId ii
     * @throws PAException when error
     */
    protected void updateStudyParticationContactRecord(String trialType,
        List<StudySiteContactDTO> contactDTOList, String poOrgId, String selectedPersId) throws PAException {

        StudySiteContactDTO participationContactDTO = contactDTOList.get(0);
        Long clinicalStfid = new ClinicalResearchStaffCorrelationServiceBean().
        createClinicalResearchStaffCorrelations(poOrgId, selectedPersId);
        Long healthCareProviderIi = null;
        if (trialType.startsWith("Interventional")) {
            healthCareProviderIi = new HealthCareProviderCorrelationBean().createHealthCareProviderCorrelationBeans(
                    poOrgId, selectedPersId);
        }
        participationContactDTO.setClinicalResearchStaffIi(IiConverter.convertToIi(clinicalStfid.toString()));
        participationContactDTO.setHealthCareProviderIi(IiConverter.convertToIi(healthCareProviderIi));

        getStudySiteContactService().update(participationContactDTO);
    }        
    
    /**
     * createGenericContactRecord.
     * @param participationContactDTO dto
     * @param orgIi ii
     * @param personIi ii
     * @throws PAException when error
     */
    protected void createGenericContactRecord(StudySiteContactDTO participationContactDTO, 
            Ii orgIi, Ii personIi) throws PAException {
        if (IiConverter.ORGANIZATIONAL_CONTACT_ROOT.equalsIgnoreCase(personIi.getRoot())) {
            //means title is selected for contact
            // now create study SITE contact as
            PABaseCorrelation<PAOrganizationalContactDTO , OrganizationalContactDTO , OrganizationalContact ,
                OrganizationalContactConverter> oc = new PABaseCorrelation<PAOrganizationalContactDTO ,
            OrganizationalContactDTO , OrganizationalContact , OrganizationalContactConverter>(
            PAOrganizationalContactDTO.class, OrganizationalContact.class, OrganizationalContactConverter.class);

            PAOrganizationalContactDTO orgContacPaDto = new PAOrganizationalContactDTO();
            orgContacPaDto.setOrganizationIdentifier(IiConverter.convertToPoOrganizationIi(orgIi.getExtension()));
            orgContacPaDto.setIdentifier(personIi);
            Long ocId = oc.create(orgContacPaDto);
            participationContactDTO.setOrganizationalContactIi(IiConverter.convertToIi(ocId));
        }
    }
    
    /**
     * createPersonContactRecord.
     * @param participationContactDTO dto
     * @param trialType string
     * @param orgIi ii
     * @param personIi ii
     * @throws PAException when error
     */
    protected void createPersonContactRecord(StudySiteContactDTO participationContactDTO, 
            String trialType, Ii orgIi, Ii personIi) throws PAException {
        if (IiConverter.PERSON_ROOT.equalsIgnoreCase(personIi.getRoot())) {
            
            Long clinicalStfid = new ClinicalResearchStaffCorrelationServiceBean().
                createClinicalResearchStaffCorrelations(orgIi.getExtension(),
                        personIi.getExtension());
            Long healthCareProviderIi = null;
            if (trialType.startsWith("Interventional")) {
                healthCareProviderIi = new HealthCareProviderCorrelationBean()
                .createHealthCareProviderCorrelationBeans(orgIi.getExtension(),
                        personIi.getExtension());
            }

            participationContactDTO.setClinicalResearchStaffIi(IiConverter.convertToIi(clinicalStfid));
            if (healthCareProviderIi != null) {
                participationContactDTO.setHealthCareProviderIi(IiConverter.convertToIi(healthCareProviderIi));
            }
        }
    
    }
    
    /**
     * recreatePrimaryContactRecord.
     * @param participationContactDTO dto
     * @param studySiteIi ii
     * @throws PAException when error
     */
    protected void recreatePrimaryContactRecord(StudySiteContactDTO participationContactDTO, 
            Ii studySiteIi) throws PAException {
     // if a old record exists delete it and create a new one
        StudySiteContactDTO siteConDto = new StudySiteContactDTO();
        List<StudySiteContactDTO> siteContactDtos = getStudySiteContactService().getByStudySite(studySiteIi);
        for (StudySiteContactDTO cDto : siteContactDtos) {
            if (StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().
                    equalsIgnoreCase(cDto.getRoleCode().getCode())) {
                siteConDto = cDto;
            }
        }
        if (siteConDto != null && siteConDto.getIdentifier() != null) {
            getStudySiteContactService().delete(siteConDto.getIdentifier());
        }
        getStudySiteContactService().create(participationContactDTO);
    }
    
    /**
     * createStudyParticationContactRecord.
     * @param orgIi ii
     * @param studyProtocolIi ii
     * @param studySiteIi ii
     * @param isPrimaryContact bool
     * @param roleCode string
     * @param personIi ii
     * @param trialType string
     * @return bool
     * @throws PAException when error
     */
    @SuppressWarnings({"PMD.ExcessiveParameterList" })
    // CHECKSTYLE:OFF More than 7 Parameters
    protected boolean createStudyParticationContactRecord(Ii orgIi, Ii studyProtocolIi, Ii studySiteIi,
            boolean isPrimaryContact, String roleCode, Ii personIi, String trialType) throws PAException {
        // CHECKSTYLE:ON
        StudySiteContactDTO participationContactDTO = new StudySiteContactDTO();
        
        createPersonContactRecord(participationContactDTO, trialType, orgIi, personIi); 
        createGenericContactRecord(participationContactDTO, orgIi, personIi); 
        
        if (!isPrimaryContact) {
            participationContactDTO.setRoleCode(CdConverter.convertStringToCd(roleCode));
        } else {
            participationContactDTO.setRoleCode(CdConverter.convertToCd(
                    StudySiteContactRoleCode.PRIMARY_CONTACT));
        }
        participationContactDTO.setStudyProtocolIdentifier(studyProtocolIi);
        participationContactDTO.setStatusCode(CdConverter.convertStringToCd(
                FunctionalRoleStatusCode.PENDING.getCode()));
        participationContactDTO.setStudySiteIi(studySiteIi);
        if (isPrimaryContact) {
            recreatePrimaryContactRecord(participationContactDTO, studySiteIi);
        }
        if (!isPrimaryContact
                && !doesSPCRecordExistforPerson(Long.valueOf(personIi.getExtension()), studySiteIi)) {
            getStudySiteContactService().create(participationContactDTO);
        }
        return true;
    }
    
    
    private boolean iterateSubInvresults(Long persid, List<PaPersonDTO> subInvresults) throws PAException {
        
        for (int i = 0; i < subInvresults.size(); i++) {
            if (subInvresults.get(i).getPaPersonId() != null 
                    && subInvresults.get(i).getPaPersonId().equals(persid)) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean iteratePrincipalInvresults(Long persid, List<PaPersonDTO> principalInvresults) 
    throws PAException {
        
        for (int i = 0; i < principalInvresults.size(); i++) {
            
            if (principalInvresults.get(i).getSelectedPersId() != null 
                && principalInvresults.get(i).getSelectedPersId().equals(persid)) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean doesSPCRecordExistforPerson(Long persid, Ii studfySiteIi) throws PAException {
        List<PaPersonDTO> principalInvresults = PaRegistry.getPAHealthCareProviderService()
                .getPersonsByStudySiteId(Long.valueOf(studfySiteIi.getExtension()),
                        StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getName());
        List<PaPersonDTO> subInvresults = PaRegistry.getPAHealthCareProviderService().getPersonsByStudySiteId(
                Long.valueOf(studfySiteIi.getExtension()), StudySiteContactRoleCode.SUB_INVESTIGATOR.getName());
        return iteratePrincipalInvresults(persid, principalInvresults)
            && iterateSubInvresults(persid, subInvresults);
    }
    
    /*
    // CHECKSTYLE:OFF
    protected Ii createStudySiteParticipantForNonPropTrial(Ii studyProtocolIi, OrganizationDTO organizationDTO,
            StudySiteDTO studySiteDTO, StudySiteAccrualStatusDTO currentStatus, PersonDTO investigatorDTO,
            Cd investigatorRole, Bl investigatorSiteContact, St contactType, PersonDTO primeContact,
            OrganizationalContactDTO genericContactDTO) throws PAException {
        // CHECKSTYLE:ON
        Ii studySiteIi = null;
        try {
            
            StudyProtocolDTO studyProtocolDTO = getStudyProtocolDTOFromNciId(studyProtocolIi);
            
            Ii orgIi = getOrganizationIiFromCtepId(organizationDTO);
            // check prop status
            if (BooleanUtils.isTrue(studyProtocolDTO.getProprietaryTrialIndicator().getValue())) {
                throw new PAException("Not a non-prop trial.");
            }
            
            Timestamp currentTime = new Timestamp(new Date().getTime());
            enforceBusinessRules(currentStatus, currentTime);
          
            studySiteIi = saveNonPropStudySite(true, studySiteDTO, orgIi,
                    studyProtocolDTO.getIdentifier(), currentStatus);
            
            
        } catch (Exception e) {
            ejbContext.setRollbackOnly();
            throw new PAException(e);
        } 
        return studySiteIi;
    }

    */
    
    /*
    protected Ii saveNonPropStudySite(boolean isCreate, StudySiteDTO siteDTO, Ii orgIi,
            Ii studyProtocolIi, StudySiteAccrualStatusDTO currentStatus) throws PAException {
        
        siteDTO.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.ACTIVE));
        StudySiteDTO studySiteDTO = saveStudySiteHelper(isCreate, siteDTO, orgIi,
                studyProtocolIi, currentStatus);
        return studySiteDTO.getIdentifier();
    }
    */
}
