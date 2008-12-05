package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;

/**
 * 
 * @author NAmiruddin
 * 
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.CyclomaticComplexity", "PMD.ExcessiveClassLength",
        "PMD.NPathComplexity", "PMD.ExcessiveMethodLength" })
public class PARelationServiceBean {
    /**
     * 
     * @param orgPoIdentifier org id
     * @param studyProtocolId protocol
     * @param localSpIdentifier local protocol id
     * @throws PAException e
     */
    public void createLeadOrganizationRelations(String orgPoIdentifier, Long studyProtocolId, String localSpIdentifier)
            throws PAException {
        if (orgPoIdentifier == null) {
            throw new PAException("Organization Identifer is null");
        }
        if (studyProtocolId == null) {
            throw new PAException("Study Protocol Identifer is null");
        }
        if (localSpIdentifier == null) {
            throw new PAException("Local StudyProtocol Identifer is null");
        }
        StudyProtocolDTO spDTO = PoPaServiceBeanLookup.getStudyProtocolService().getStudyProtocol(
                IiConverter.convertToIi(studyProtocolId));
        if (spDTO == null) {
            throw new PAException("No Study Protocol found for = " + studyProtocolId);
        }
        OrganizationCorrelationServiceBean oc = new OrganizationCorrelationServiceBean();
        Long roId = oc.createResearchOrganizationCorrelations(orgPoIdentifier);
        StudyParticipationDTO studyPartDTO = new StudyParticipationDTO();
        studyPartDTO.setFunctionalCode(CdConverter
                .convertStringToCd(StudyParticipationFunctionalCode.LEAD_ORAGANIZATION.getCode()));
        studyPartDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt(localSpIdentifier));
        studyPartDTO.setResearchOrganizationIi(IiConverter.convertToIi(roId));
        studyPartDTO.setStudyProtocolIdentifier(spDTO.getIdentifier());
        studyPartDTO.setStatusCode(CdConverter.convertToCd(StatusCode.PENDING));
        studyPartDTO = PoPaServiceBeanLookup.getStudyParticipationService().create(studyPartDTO);
    }

    /**
     * 
     * @param orgPoIdentifier org id
     * @param studyProtocolId protocol
     * @throws PAException e
     */
    public void createSponsorRelations(String orgPoIdentifier, Long studyProtocolId)
            throws PAException {
        if (orgPoIdentifier == null) {
            throw new PAException("Organization Identifer is null");
        }
        if (studyProtocolId == null) {
            throw new PAException("Study Protocol Identifer is null");
        }
        StudyProtocolDTO spDTO = PoPaServiceBeanLookup.getStudyProtocolService().getStudyProtocol(
                IiConverter.convertToIi(studyProtocolId));
        if (spDTO == null) {
            throw new PAException("No Study Protocol found for = " + studyProtocolId);
        }
        OrganizationCorrelationServiceBean oc = new OrganizationCorrelationServiceBean();
        Long roId = oc.createResearchOrganizationCorrelations(orgPoIdentifier);
        StudyParticipationDTO studyPartDTO = new StudyParticipationDTO();
        studyPartDTO.setFunctionalCode(CdConverter
                .convertStringToCd(StudyParticipationFunctionalCode.SPONSOR.getCode()));
        studyPartDTO.setResearchOrganizationIi(IiConverter.convertToIi(roId));
        studyPartDTO.setStudyProtocolIdentifier(spDTO.getIdentifier());
        studyPartDTO.setStatusCode(CdConverter.convertToCd(StatusCode.PENDING));
        studyPartDTO = PoPaServiceBeanLookup.getStudyParticipationService().create(studyPartDTO);
    }
    
    /**
     * 
     * @param orgPoIdentifier org id
     * @param personPoIdentifer personPoIdentifer
     * @param studyProtocolId protocol
     * @param studyTypeCode studyTypeCode
     * @throws PAException e
     */
    public void createPrincipalInvestigatorRelations(String orgPoIdentifier, String personPoIdentifer,
            Long studyProtocolId, StudyTypeCode studyTypeCode) throws PAException {
        if (orgPoIdentifier == null) {
            throw new PAException(" Organization PO Identifier is null");
        }
        if (personPoIdentifer == null) {
            throw new PAException(" Person PO Identifier is null");
        }
        if (studyProtocolId == null) {
            throw new PAException("Study Protocol Identifer is null");
        }
        if (studyTypeCode == null) {
            throw new PAException(" Study Protocol type is null");
        }
        StudyProtocolDTO spDTO = PoPaServiceBeanLookup.getStudyProtocolService().getStudyProtocol(
                IiConverter.convertToIi(studyProtocolId));
        if (spDTO == null) {
            throw new PAException("No Study Protocol found for = " + studyProtocolId);
        }
        ClinicalResearchStaffCorrelationServiceBean crs = new ClinicalResearchStaffCorrelationServiceBean();
        HealthCareProviderCorrelationBean hcp = new HealthCareProviderCorrelationBean();
        Long crsId = crs.createClinicalResearchStaffCorrelations(orgPoIdentifier, personPoIdentifer);
        Long hcpId = null;
        if (StudyTypeCode.INTERVENTIONAL.equals(studyTypeCode)) {
            hcpId = hcp.createHealthCareProviderCorrelationBeans(orgPoIdentifier, personPoIdentifer);
        }
        StudyContactDTO scDTO = new StudyContactDTO();
        scDTO.setClinicalResearchStaffIi(IiConverter.convertToIi(crsId));
        scDTO.setHealthCareProviderIi(IiConverter.convertToIi(hcpId));
        scDTO.setRoleCode(CdConverter.convertStringToCd(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.getCode()));
        scDTO.setStudyProtocolIi(spDTO.getIdentifier());
        scDTO.setStatusCode(CdConverter.convertStringToCd(StatusCode.PENDING.getCode()));
        PoPaServiceBeanLookup.getStudyContactService().create(scDTO);
    }

    /**
     * 
     * @param orgPoIdentifier orgPoIdentifier
     * @param personPoIdentifer personPoIdentifer
     * @param studyProtocolId studyProtocolId
     * @param email as String
     * @param phone as String
     * @throws PAException pe
     */
    public void createPIAsResponsiblePartyRelations(String orgPoIdentifier, String personPoIdentifer,
            Long studyProtocolId, String email, String phone) throws PAException {
        if (orgPoIdentifier == null) {
            throw new PAException(" Organization PO Identifier is null");
        }
        if (personPoIdentifer == null) {
            throw new PAException(" Person PO Identifier is null");
        }
        if (studyProtocolId == null) {
            throw new PAException("Study Protocol Identifer is null");
        }
        StudyProtocolDTO spDTO = PoPaServiceBeanLookup.getStudyProtocolService().getStudyProtocol(
                IiConverter.convertToIi(studyProtocolId));
        if (spDTO == null) {
            throw new PAException("No Study Protocol found for = " + studyProtocolId);
        }
        ClinicalResearchStaffCorrelationServiceBean crs = new ClinicalResearchStaffCorrelationServiceBean();
        Long crsId = crs.createClinicalResearchStaffCorrelations(orgPoIdentifier, personPoIdentifer);
        StudyContactDTO scDTO = new StudyContactDTO();
        scDTO.setClinicalResearchStaffIi(IiConverter.convertToIi(crsId));
        scDTO.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR));
        scDTO.setStatusCode(CdConverter.convertStringToCd(StatusCode.PENDING.getCode()));
        scDTO.setStudyProtocolIi(spDTO.getIdentifier());
        // set DSET
        // set the DSET
        ArrayList<String> emailList = new ArrayList<String>();
        ArrayList<String> telList = new ArrayList<String>();
        emailList.add(email);
        telList.add(phone);
        DSet<Tel> list = new DSet<Tel>();
        list = DSetConverter.convertListToDSet(emailList, "EMAIL", list);
        list = DSetConverter.convertListToDSet(telList, "PHONE", list);
        scDTO.setTelecomAddresses(list);
        PoPaServiceBeanLookup.getStudyContactService().create(scDTO);
    }

    /**
     * 
     * @param orgPoIdentifier orgPoIdentifier
     * @param personPoIdentifer personPoIdentifer
     * @param studyProtocolId studyProtocolId
     * @param email as String
     * @param phone as String
     * @throws PAException pe
     */
    public void createSponsorAsPrimaryContactRelations(String orgPoIdentifier, String personPoIdentifer,
            Long studyProtocolId, String email, String phone) throws PAException {
        if (orgPoIdentifier == null) {
            throw new PAException(" Organization PO Identifier is null");
        }
        if (personPoIdentifer == null) {
            throw new PAException(" Person PO Identifier is null");
        }
        if (studyProtocolId == null) {
            throw new PAException("Study Protocol Identifer is null");
        }
        StudyProtocolDTO spDTO = PoPaServiceBeanLookup.getStudyProtocolService().getStudyProtocol(
                IiConverter.convertToIi(studyProtocolId));
        if (spDTO == null) {
            throw new PAException("No Study Protocol found for = " + studyProtocolId);
        }
        OrganizationCorrelationServiceBean ocs = new OrganizationCorrelationServiceBean();
        Long roId = ocs.createResearchOrganizationCorrelations(orgPoIdentifier);
        if (roId == null) {
            throw new PAException("Research Organization could not be created");
        }
        // create study participation
        StudyParticipationDTO studyPartDTO = new StudyParticipationDTO();
        studyPartDTO.setFunctionalCode(CdConverter.convertStringToCd(
                StudyParticipationFunctionalCode.RESPONSIBLE_PARTY_SPONSOR.getCode()));
        studyPartDTO.setResearchOrganizationIi(IiConverter.convertToIi(roId));
        studyPartDTO.setStudyProtocolIdentifier(spDTO.getIdentifier());
        studyPartDTO.setStatusCode(CdConverter.convertStringToCd(StatusCode.PENDING.getCode()));
        studyPartDTO = PoPaServiceBeanLookup.getStudyParticipationService().create(studyPartDTO);
        // now create study participation contact as responsible party
        OrganizationalContactCorrelationServiceBean oc = new OrganizationalContactCorrelationServiceBean();
        Long ocId = oc.createOrganizationalContactCorrelations(orgPoIdentifier, personPoIdentifer);
        StudyParticipationContactDTO spcDTO = new StudyParticipationContactDTO();
        // set the DSET
        ArrayList<String> emailList = new ArrayList<String>();
        ArrayList<String> telList = new ArrayList<String>();
        emailList.add(email);
        telList.add(phone);
        DSet<Tel> list = new DSet<Tel>();
        list = DSetConverter.convertListToDSet(emailList, "EMAIL", list);
        list = DSetConverter.convertListToDSet(telList, "PHONE", list);
        spcDTO.setTelecomAddresses(list);
        //
        spcDTO.setOrganizationalContactIi(IiConverter.convertToIi(ocId));
        spcDTO.setRoleCode(CdConverter.convertToCd(
                StudyParticipationContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT));
        spcDTO.setStudyParticipationIi(studyPartDTO.getIdentifier());
        spcDTO.setStudyProtocolIdentifier(spDTO.getIdentifier());
        spcDTO.setStatusCode(CdConverter.convertStringToCd(StatusCode.PENDING.getCode()));
        PoPaServiceBeanLookup.getStudyParticipationContactService().create(spcDTO);
    }

    /**
     * 
     * @param orgPoIdentifier poIdentifer
     * @param summartFourSorceCode summary4source
     * @param studyProtocolId studyProtocolId
     * @throws PAException pa
     */
    public void createSummary4ReportedSource(String orgPoIdentifier,
            SummaryFourFundingCategoryCode summartFourSorceCode, Long studyProtocolId) throws PAException {
        CorrelationUtils corrUtils = new CorrelationUtils();
        if (orgPoIdentifier == null) {
            throw new PAException(" Organization PO Identifier is null");
        }
       
        if (studyProtocolId == null) {
            throw new PAException("Study Protocol Identifer is null");
        }
        StudyProtocolDTO spDTO = PoPaServiceBeanLookup.getStudyProtocolService().getStudyProtocol(
                IiConverter.convertToIi(studyProtocolId));
        if (spDTO == null) {
            throw new PAException("No Study Protocol found for = " + studyProtocolId);
        }
        // Step 1 : get the PO Organization
        OrganizationDTO poOrg = null;
        try {
            poOrg = PoPaServiceBeanLookup.getOrganizationEntityService().getOrganization(
                    IiConverter.converToPoOrganizationIi(orgPoIdentifier));
        } catch (NullifiedEntityException e) {
            // Map m = e.getNullifiedEntities();
            // LOG.error("This Organization is no longer available instead use
            // ");
            throw new PAException("This Organization is no longer available instead use ", e);
        }
        // Step 3 : check for pa org, if not create one
        Organization paOrg = corrUtils.getPAOrganizationByIndetifers(null, orgPoIdentifier);
        if (paOrg == null) {
            paOrg = corrUtils.createPAOrganization(poOrg);
        }
        StudyResourcingDTO summary4ResoureDTO = new StudyResourcingDTO();
        summary4ResoureDTO.setStudyProtocolIi(spDTO.getIdentifier());
        summary4ResoureDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.TRUE));
        if (summartFourSorceCode != null) {
            summary4ResoureDTO.setTypeCode(CdConverter.convertToCd(summartFourSorceCode));
        }
        summary4ResoureDTO.setOrganizationIdentifier(IiConverter.convertToIi(paOrg.getId()));
        PoPaServiceBeanLookup.getStudyResourcingService().createStudyResourcing(summary4ResoureDTO);
    }
}
