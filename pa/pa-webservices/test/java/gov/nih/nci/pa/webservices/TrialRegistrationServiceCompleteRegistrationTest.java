/**
 * 
 */
package gov.nih.nci.pa.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EntityNamePartType;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.NihInstituteCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.TrialRegistrationServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.AbstractMockitoTest;
import gov.nih.nci.pa.util.MockPoJndiServiceLocator;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.pomock.MockOrganizationEntityService;
import gov.nih.nci.pa.util.pomock.MockPersonEntityService;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.Grant;
import gov.nih.nci.pa.webservices.types.INDIDE;
import gov.nih.nci.pa.webservices.types.InterventionalTrialDesign;
import gov.nih.nci.pa.webservices.types.NonInterventionalTrialDesign;
import gov.nih.nci.pa.webservices.types.ObjectFactory;
import gov.nih.nci.pa.webservices.types.Organization;
import gov.nih.nci.pa.webservices.types.Person;
import gov.nih.nci.pa.webservices.types.ResponsibleParty;
import gov.nih.nci.pa.webservices.types.ResponsiblePartyType;
import gov.nih.nci.pa.webservices.types.TrialDocument;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import junit.framework.Assert;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.SimpleLayout;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.xml.sax.SAXException;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author dkrylov
 * 
 */
@SuppressWarnings("deprecation")
public class TrialRegistrationServiceCompleteRegistrationTest extends
        AbstractMockitoTest {

    static {
        try {
            final ConsoleAppender appender = new ConsoleAppender(
                    new SimpleLayout());
            appender.setThreshold(Priority.INFO);
            Logger.getRootLogger().addAppender(appender);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private TrialRegistrationService service;

    private TrialRegistrationServiceLocal trialRegistrationServiceLocal;

    @SuppressWarnings("unchecked")
    @Before
    public void before() throws PAException {
        service = new TrialRegistrationService();

        PAServiceUtils paServiceUtils = mock(PAServiceUtils.class);
        when(paServiceUtils.getTrialNciId(any(Long.class))).thenReturn(
                "NCI-2014-00001");
        service.setPaServiceUtils(paServiceUtils);

        trialRegistrationServiceLocal = mock(TrialRegistrationServiceLocal.class);
        when(
                trialRegistrationServiceLocal
                        .createCompleteInterventionalStudyProtocol(
                                any(StudyProtocolDTO.class),
                                any(StudyOverallStatusDTO.class),
                                any(List.class), any(List.class),
                                any(List.class), any(OrganizationDTO.class),
                                any(PersonDTO.class),
                                any(OrganizationDTO.class),
                                any(ResponsiblePartyDTO.class),
                                any(StudySiteDTO.class), any(List.class),
                                any(List.class), any(StudyResourcingDTO.class),
                                any(StudyRegulatoryAuthorityDTO.class),
                                any(Bl.class), any(DSet.class))).thenReturn(
                IiConverter.convertToIi(999L));
        when(
                PaRegistry.getInstance().getServiceLocator()
                        .getTrialRegistrationService()).thenReturn(
                trialRegistrationServiceLocal);

        PoRegistry.getInstance().setPoServiceLocator(
                new MockPoJndiServiceLocator());

        when(
                PaRegistry.getInstance().getServiceLocator()
                        .getOrganizationCorrelationService()).thenReturn(
                new OrganizationCorrelationServiceBean());

        UsernameHolder.setUser("jdoe01");

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterComplete() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/register_complete.xml";
        registerFromXmlFileAndVerify(filename);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterCompleteValidationException()
            throws JAXBException, SAXException, PAException,
            NullifiedEntityException {
        final String filename = "/register_complete.xml";
        CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile(filename);

        when(
                trialRegistrationServiceLocal
                        .createCompleteInterventionalStudyProtocol(
                                any(StudyProtocolDTO.class),
                                any(StudyOverallStatusDTO.class),
                                any(List.class), any(List.class),
                                any(List.class), any(OrganizationDTO.class),
                                any(PersonDTO.class),
                                any(OrganizationDTO.class),
                                any(ResponsiblePartyDTO.class),
                                any(StudySiteDTO.class), any(List.class),
                                any(List.class), any(StudyResourcingDTO.class),
                                any(StudyRegulatoryAuthorityDTO.class),
                                any(Bl.class), any(DSet.class))).thenThrow(
                new PAValidationException("Validation Exception: error."));

        Response r = service.registerCompleteTrial(reg);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), r.getStatus());
        assertEquals("Validation Exception: error.", r.getEntity().toString());

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterCompleteGeneralPAError()
            throws JAXBException, SAXException, PAException,
            NullifiedEntityException {
        final String filename = "/register_complete.xml";
        CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile(filename);

        when(
                trialRegistrationServiceLocal
                        .createCompleteInterventionalStudyProtocol(
                                any(StudyProtocolDTO.class),
                                any(StudyOverallStatusDTO.class),
                                any(List.class), any(List.class),
                                any(List.class), any(OrganizationDTO.class),
                                any(PersonDTO.class),
                                any(OrganizationDTO.class),
                                any(ResponsiblePartyDTO.class),
                                any(StudySiteDTO.class), any(List.class),
                                any(List.class), any(StudyResourcingDTO.class),
                                any(StudyRegulatoryAuthorityDTO.class),
                                any(Bl.class), any(DSet.class))).thenThrow(
                new PAException("error"));

        Response r = service.registerCompleteTrial(reg);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                r.getStatus());
        assertEquals("error", r.getEntity().toString());

    }
    
    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterCompleteGeneralError()
            throws JAXBException, SAXException, PAException,
            NullifiedEntityException {
        final String filename = "/register_complete.xml";
        CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile(filename);

        when(
                trialRegistrationServiceLocal
                        .createCompleteInterventionalStudyProtocol(
                                any(StudyProtocolDTO.class),
                                any(StudyOverallStatusDTO.class),
                                any(List.class), any(List.class),
                                any(List.class), any(OrganizationDTO.class),
                                any(PersonDTO.class),
                                any(OrganizationDTO.class),
                                any(ResponsiblePartyDTO.class),
                                any(StudySiteDTO.class), any(List.class),
                                any(List.class), any(StudyResourcingDTO.class),
                                any(StudyRegulatoryAuthorityDTO.class),
                                any(Bl.class), any(DSet.class))).thenThrow(
                new RuntimeException("error"));

        Response r = service.registerCompleteTrial(reg);
        assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                r.getStatus());
        assertEquals("error", r.getEntity().toString());

    }


    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterCompleteOrgNotFound() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/register_complete.xml";
        CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile(filename);
        reg.getLeadOrganization().getExistingOrganization().setPoID(999999999L);
        Response r = service.registerCompleteTrial(reg);
        assertEquals(Status.NOT_FOUND.getStatusCode(), r.getStatus());
        assertEquals(
                "Organization with PO ID of 999999999 cannot be found in PO.",
                r.getEntity().toString());

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterCompletePersonNotFound()
            throws JAXBException, SAXException, PAException,
            NullifiedEntityException {
        final String filename = "/register_complete.xml";
        CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile(filename);
        reg.getPi().getExistingPerson().setPoID(9999999L);
        Response r = service.registerCompleteTrial(reg);
        assertEquals(Status.NOT_FOUND.getStatusCode(), r.getStatus());
        assertEquals("Person with PO ID of 9999999 cannot be found in PO.", r
                .getEntity().toString());

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterPIAsResponsibleParty() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/register_complete_RespPartyPI.xml";
        registerFromXmlFileAndVerify(filename);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterNonCtGov() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/register_complete_nonctgov.xml";
        registerFromXmlFileAndVerify(filename);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterNonInterventional() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/register_complete_noninterventional.xml";
        registerFromXmlFileAndVerify(filename);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterWithNewOrgAndPerson() throws JAXBException,
            SAXException, PAException, NullifiedEntityException {
        final String filename = "/register_complete_newOrgsAndPersons.xml";
        registerFromXmlFileAndVerify(filename);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterWithOrgsPersonsIdentifiedByCtepID()
            throws JAXBException, SAXException, PAException,
            NullifiedEntityException {
        final String filename = "/register_complete_OrgPersonWithCtepID.xml";
        registerFromXmlFileAndVerify(filename);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterSponsorInvestigatorAsResponsibleParty()
            throws JAXBException, SAXException, PAException,
            NullifiedEntityException {
        final String filename = "/register_complete_RespPartySponsorInvestigator.xml";
        registerFromXmlFileAndVerify(filename);

    }

    /**
     * @param filename
     * @throws JAXBException
     * @throws SAXException
     * @throws PAException
     * @throws NullifiedEntityException
     */
    private void registerFromXmlFileAndVerify(final String filename)
            throws JAXBException, SAXException, PAException,
            NullifiedEntityException {
        CompleteTrialRegistration reg = readCompleteTrialRegistrationFromFile(filename);
        registerAndVerify(reg);
    }

    /**
     * @param reg
     * @throws PAException
     * @throws NullifiedEntityException
     */
    private void registerAndVerify(CompleteTrialRegistration reg)
            throws PAException, NullifiedEntityException {
        Response r = service.registerCompleteTrial(reg);
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        JAXBElement<TrialRegistrationConfirmation> el = (JAXBElement<TrialRegistrationConfirmation>) r
                .getEntity();
        TrialRegistrationConfirmation conf = el.getValue();
        assertEquals("NCI-2014-00001", conf.getNciTrialID());
        assertEquals(999, conf.getPaTrialID());

        captureAndVerifyArguments(reg);
    }

    /**
     * @param reg
     * @throws PAException
     * @throws NullifiedEntityException
     */
    private void captureAndVerifyArguments(CompleteTrialRegistration reg)
            throws PAException, NullifiedEntityException {
        ArgumentCaptor<StudyProtocolDTO> studyProtocolDTO = ArgumentCaptor
                .forClass(StudyProtocolDTO.class);
        ArgumentCaptor<StudyOverallStatusDTO> overallStatusDTO = ArgumentCaptor
                .forClass(StudyOverallStatusDTO.class);
        ArgumentCaptor<List> studyIndldeDTOs = (ArgumentCaptor
                .forClass(List.class));
        ArgumentCaptor<List> studyResourcingDTOs = ArgumentCaptor
                .forClass(List.class);
        ArgumentCaptor<List> documentDTOs = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<OrganizationDTO> leadOrganizationDTO = ArgumentCaptor
                .forClass(OrganizationDTO.class);
        ArgumentCaptor<PersonDTO> principalInvestigatorDTO = ArgumentCaptor
                .forClass(PersonDTO.class);
        ArgumentCaptor<OrganizationDTO> sponsorOrganizationDTO = ArgumentCaptor
                .forClass(OrganizationDTO.class);
        ArgumentCaptor<ResponsiblePartyDTO> partyDTO = ArgumentCaptor
                .forClass(ResponsiblePartyDTO.class);
        ArgumentCaptor<StudySiteDTO> leadOrganizationSiteIdentifierDTO = ArgumentCaptor
                .forClass(StudySiteDTO.class);
        ArgumentCaptor<List> studyIdentifierDTOs = ArgumentCaptor
                .forClass(List.class);
        ArgumentCaptor<List> summary4organizationDTO = ArgumentCaptor
                .forClass(List.class);
        ArgumentCaptor<StudyResourcingDTO> summary4studyResourcingDTO = ArgumentCaptor
                .forClass(StudyResourcingDTO.class);
        ArgumentCaptor<StudyRegulatoryAuthorityDTO> studyRegAuthDTO = ArgumentCaptor
                .forClass(StudyRegulatoryAuthorityDTO.class);
        ArgumentCaptor<Bl> isBatchMode = ArgumentCaptor.forClass(Bl.class);
        ArgumentCaptor<DSet> owners = ArgumentCaptor.forClass(DSet.class);
        verify(PaRegistry.getTrialRegistrationService())
                .createCompleteInterventionalStudyProtocol(
                        studyProtocolDTO.capture(), overallStatusDTO.capture(),
                        studyIndldeDTOs.capture(),
                        studyResourcingDTOs.capture(), documentDTOs.capture(),
                        leadOrganizationDTO.capture(),
                        principalInvestigatorDTO.capture(),
                        sponsorOrganizationDTO.capture(), partyDTO.capture(),
                        leadOrganizationSiteIdentifierDTO.capture(),
                        studyIdentifierDTOs.capture(),
                        summary4organizationDTO.capture(),
                        summary4studyResourcingDTO.capture(),
                        studyRegAuthDTO.capture(), isBatchMode.capture(),
                        owners.capture());

        verifyStudyProtocol(reg, studyProtocolDTO);
        verifyOverallStatus(reg, overallStatusDTO);
        verifyStudyIndldeDTOs(reg, studyIndldeDTOs);
        verifyStudyResourcingDTOs(reg, studyResourcingDTOs);
        verifyDocumentDTOs(reg, documentDTOs);
        verifyLeadOrganization(reg, leadOrganizationDTO);
        verifyPI(reg, principalInvestigatorDTO);
        verifySponsor(reg, sponsorOrganizationDTO);
        verifyPartyDTO(reg, partyDTO);
        verifyLeadOrganizationSiteIdentifierDTO(reg,
                leadOrganizationSiteIdentifierDTO);
        verifyCtGovIdentifierAssignerSite(reg, studyIdentifierDTOs);
        verifySummary4OrganizationDTO(reg, summary4organizationDTO);
        verifySummary4studyResourcingDTO(reg, summary4studyResourcingDTO);
        verifyStudyRegAuthDTO(reg, studyRegAuthDTO);
        verifyBatchMode(reg, isBatchMode);
        verifyOwners(reg, owners);
    }

    private void verifyStudyRegAuthDTO(CompleteTrialRegistration reg,
            ArgumentCaptor<StudyRegulatoryAuthorityDTO> captor) {
        StudyRegulatoryAuthorityDTO dto = captor.getValue();
        if (!reg.isClinicalTrialsDotGovXmlRequired()) {
            assertNull(dto);
        } else
            assertEquals("1", dto.getRegulatoryAuthorityIdentifier()
                    .getExtension());
    }

    private void verifySummary4studyResourcingDTO(
            CompleteTrialRegistration reg,
            ArgumentCaptor<StudyResourcingDTO> captor) {
        StudyResourcingDTO dto = captor.getValue();
        assertEquals(reg.getCategory().value(), dto.getTypeCode().getCode());

    }

    private void verifySummary4OrganizationDTO(CompleteTrialRegistration reg,
            ArgumentCaptor<List> captor) throws NullifiedEntityException {
        List<OrganizationDTO> listDTO = captor.getValue();
        List<Organization> listWs = reg.getSummary4FundingSponsor();

        for (int i = 0; i < listWs.size(); i++) {
            verifyOrganization(listWs.get(i), listDTO.get(i));
        }
    }

    private void verifyPartyDTO(CompleteTrialRegistration reg,
            ArgumentCaptor<ResponsiblePartyDTO> captor) {
        ResponsiblePartyDTO dto = captor.getValue();
        if (!reg.isClinicalTrialsDotGovXmlRequired()) {
            assertNull(dto.getAffiliation());
            assertNull(dto.getInvestigator());
            assertNull(dto.getType());
        } else {
            ResponsibleParty rp = reg.getResponsibleParty();
            if (ResponsiblePartyType.SPONSOR == rp.getType()) {
                assertEquals(ResponsiblePartyDTO.ResponsiblePartyType.SPONSOR,
                        dto.getType());
                assertNull(dto.getAffiliation());
                assertNull(dto.getInvestigator());
            } else if (ResponsiblePartyType.PRINCIPAL_INVESTIGATOR == rp
                    .getType()) {
                assertEquals(
                        ResponsiblePartyDTO.ResponsiblePartyType.PRINCIPAL_INVESTIGATOR,
                        dto.getType());
                assertEquals(rp.getInvestigatorTitle(), dto.getTitle());
                assertEquals(Long.valueOf(3), IiConverter.convertToLong(dto
                        .getAffiliation().getIdentifier()));
            } else if (ResponsiblePartyType.SPONSOR_INVESTIGATOR == rp
                    .getType()) {
                assertEquals(
                        ResponsiblePartyDTO.ResponsiblePartyType.SPONSOR_INVESTIGATOR,
                        dto.getType());
                assertEquals(rp.getInvestigatorTitle(), dto.getTitle());
                assertEquals(Long.valueOf(1), IiConverter.convertToLong(dto
                        .getInvestigator().getIdentifier()));
            }
        }
    }

    private void verifyCtGovIdentifierAssignerSite(
            CompleteTrialRegistration reg, ArgumentCaptor<List> captor) {
        assertEquals(1, captor.getValue().size());
        StudySiteDTO dto = (StudySiteDTO) captor.getValue().get(0);
        assertEquals(reg.getClinicalTrialsDotGovTrialID(), dto
                .getLocalStudyProtocolIdentifier().getValue());
        assertEquals("1", dto.getResearchOrganizationIi().getExtension());
    }

    private void verifyLeadOrganizationSiteIdentifierDTO(
            CompleteTrialRegistration reg, ArgumentCaptor<StudySiteDTO> captor) {
        assertEquals(reg.getLeadOrgTrialID(), captor.getValue()
                .getLocalStudyProtocolIdentifier().getValue());
    }

    private void verifySponsor(CompleteTrialRegistration reg,
            ArgumentCaptor<OrganizationDTO> captor)
            throws NullifiedEntityException {
        OrganizationDTO dto = captor.getValue();
        Organization org = reg.getSponsor();
        if (!reg.isClinicalTrialsDotGovXmlRequired()) {
            assertNull(dto);
        } else {
            verifyOrganization(org, dto);
        }

    }

    private void verifyPI(CompleteTrialRegistration reg,
            ArgumentCaptor<PersonDTO> captor) throws NullifiedEntityException {
        Person person = reg.getPi();
        PersonDTO dto = captor.getValue();
        verifyPerson(person, dto);

    }

    private void verifyPerson(Person person, PersonDTO dto)
            throws NullifiedEntityException {
        if (person.getExistingPerson() != null) {
            if (person.getExistingPerson().getPoID() != null) {
                assertEquals(person.getExistingPerson().getPoID(),
                        IiConverter.convertToLong(dto.getIdentifier()));
            } else {
                String ctepID = person.getExistingPerson().getCtepID();
                for (Map.Entry<String, String> e : MockPersonEntityService.PO_ID_TO_CTEP_ID
                        .entrySet()) {
                    if (e.getValue().equals(ctepID)) {
                        assertEquals(e.getKey(), dto.getIdentifier()
                                .getExtension());
                        return;
                    }
                }
                Assert.fail();
            }
        } else {
            Long personID = IiConverter.convertToLong(dto.getIdentifier());
            assertNotNull(personID);
            assertEquals(new MockPersonEntityService().getPerson(dto
                    .getIdentifier()), dto);
            assertEquals(person.getNewPerson().getFirstName(),
                    EnPnConverter.getNamePart(dto.getName(),
                            EntityNamePartType.GIV));
            assertEquals(person.getNewPerson().getLastName(),
                    EnPnConverter.getNamePart(dto.getName(),
                            EntityNamePartType.FAM));
            assertEquals(person.getNewPerson().getPrefix(),
                    EnPnConverter.getNamePart(dto.getName(),
                            EntityNamePartType.PFX));
            assertEquals(person.getNewPerson().getSuffix(),
                    EnPnConverter.getNamePart(dto.getName(),
                            EntityNamePartType.SFX));
            assertEquals("1029 N Stuart St, Vienna, VA, 22201 USA",
                    AddressConverterUtil.convertToAddress(dto
                            .getPostalAddress()));
            assertEquals(
                    person.getNewPerson().getContact().get(0).getValue(),
                    DSetConverter.getTelByType(dto.getTelecomAddress(),
                            "mailto:").get(0));
            assertEquals(
                    person.getNewPerson().getContact().get(1).getValue(),
                    DSetConverter.getTelByType(dto.getTelecomAddress(),
                            "x-text-fax:").get(0));
            assertEquals(person.getNewPerson().getContact().get(2).getValue(),
                    DSetConverter.getTelByType(dto.getTelecomAddress(), "tel:")
                            .get(0));
            assertEquals(
                    person.getNewPerson().getContact().get(3).getValue(),
                    DSetConverter.getTelByType(dto.getTelecomAddress(),
                            "x-text-tel:").get(0));

        }

    }

    private void verifyLeadOrganization(CompleteTrialRegistration reg,
            ArgumentCaptor<OrganizationDTO> captor)
            throws NullifiedEntityException {
        OrganizationDTO dto = captor.getValue();
        Organization org = reg.getLeadOrganization();
        verifyOrganization(org, dto);
    }

    private void verifyOrganization(Organization org, OrganizationDTO dto)
            throws NullifiedEntityException {
        if (org.getExistingOrganization() != null) {
            if (org.getExistingOrganization().getPoID() != null) {
                assertEquals(org.getExistingOrganization().getPoID(),
                        IiConverter.convertToLong(dto.getIdentifier()));
            } else {
                String ctepID = org.getExistingOrganization().getCtepID();
                for (Map.Entry<String, String> e : MockOrganizationEntityService.PO_ID_TO_CTEP_ID
                        .entrySet()) {
                    if (e.getValue().equals(ctepID)) {
                        assertEquals(e.getKey(), dto.getIdentifier()
                                .getExtension());
                        return;
                    }
                }
                Assert.fail();
            }
        } else {
            Long newOrgId = IiConverter.convertToLong(dto.getIdentifier());
            assertNotNull(newOrgId);
            assertEquals(
                    new MockOrganizationEntityService().getOrganization(dto
                            .getIdentifier()), dto);
            assertEquals(org.getNewOrganization().getName(),
                    EnOnConverter.convertEnOnToString(dto.getName()));
            assertEquals("1029 N Stuart St, Vienna, VA, 22201 USA",
                    AddressConverterUtil.convertToAddress(dto
                            .getPostalAddress()));
            assertEquals(
                    org.getNewOrganization().getContact().get(0).getValue(),
                    DSetConverter.getTelByType(dto.getTelecomAddress(),
                            "mailto:").get(0));
            assertEquals(
                    org.getNewOrganization().getContact().get(1).getValue(),
                    DSetConverter.getTelByType(dto.getTelecomAddress(),
                            "x-text-fax:").get(0));
            assertEquals(org.getNewOrganization().getContact().get(2)
                    .getValue(),
                    DSetConverter.getTelByType(dto.getTelecomAddress(), "tel:")
                            .get(0));
            assertEquals(
                    org.getNewOrganization().getContact().get(3).getValue(),
                    DSetConverter.getTelByType(dto.getTelecomAddress(),
                            "x-text-tel:").get(0));

        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void verifyDocumentDTOs(CompleteTrialRegistration reg,
            ArgumentCaptor<List> captor) {
        List<DocumentDTO> list = captor.getValue();
        verifyDocumentDTO(list, reg.getProtocolDocument(),
                DocumentTypeCode.PROTOCOL_DOCUMENT);
        verifyDocumentDTO(list, reg.getIrbApprovalDocument(),
                DocumentTypeCode.IRB_APPROVAL_DOCUMENT);
        verifyDocumentDTO(list, reg.getParticipatingSitesDocument(),
                DocumentTypeCode.PARTICIPATING_SITES);
        verifyDocumentDTO(list, reg.getInformedConsentDocument(),
                DocumentTypeCode.INFORMED_CONSENT_DOCUMENT);
        verifyDocumentDTO(list, reg.getOtherDocument().get(0),
                DocumentTypeCode.OTHER);

    }

    private void verifyDocumentDTO(List<DocumentDTO> list, TrialDocument doc,
            DocumentTypeCode type) {
        for (DocumentDTO dto : list) {
            if (dto.getTypeCode().getCode().equals(type.getCode())) {
                assertEquals(doc.getFilename(), dto.getFileName().getValue());
                assertEquals(doc.getValue(), dto.getText().getData());
                return;
            }
        }
        Assert.fail();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void verifyStudyResourcingDTOs(CompleteTrialRegistration reg,
            ArgumentCaptor<List> captor) {
        List<StudyResourcingDTO> list = captor.getValue();
        for (int i = 0; i < list.size(); i++) {
            verifyStudyResourcingDTO(list.get(i), reg.getGrant().get(i));
        }
    }

    private void verifyStudyResourcingDTO(StudyResourcingDTO dto, Grant grant) {
        assertEquals(dto.getFundingMechanismCode().getCode(),
                grant.getFundingMechanism());
        assertEquals(dto.getNihInstitutionCode().getCode(),
                grant.getNihInstitutionCode());
        assertEquals(dto.getSerialNumber().getValue(), grant.getSerialNumber());
        assertEquals(dto.getNciDivisionProgramCode().getCode(), grant
                .getNciDivisionProgramCode().value());
        assertEquals(dto.getFundingPercent().getValue().floatValue(), grant
                .getFundingPercentage().floatValue(), 0);
        assertFalse(dto.getSummary4ReportedResourceIndicator().getValue());
    }

    @SuppressWarnings("unchecked")
    private void verifyStudyIndldeDTOs(CompleteTrialRegistration reg,
            ArgumentCaptor<List> studyIndldeDTOs) {

        List<StudyIndldeDTO> list = studyIndldeDTOs.getValue();
        StudyIndldeDTO indDTO = list.get(0);
        StudyIndldeDTO ideDTO = list.get(1);

        INDIDE ind = reg.getInd().get(0);
        INDIDE ide = reg.getIde().get(0);

        verifyStudyIndldeDTO(indDTO, ind);
        verifyStudyIndldeDTO(ideDTO, ide);

    }

    private void verifyStudyIndldeDTO(StudyIndldeDTO ideDTO, INDIDE ide) {
        assertEquals(ide.getExpandedAccessType().value(), ideDTO
                .getExpandedAccessStatusCode().getCode());
        assertEquals(ide.getGrantor().value(), ideDTO.getGrantorCode()
                .getCode());
        assertEquals(ide.getHolderType().value(), ideDTO.getHolderTypeCode()
                .getCode());
        if (ide.getNciDivisionProgramCode() != null) {
            assertEquals(ide.getNciDivisionProgramCode().value(), ideDTO
                    .getNciDivProgHolderCode().getCode());
        }
        if (ide.getNihInstitution() != null) {
            assertEquals(
                    NihInstituteCode.valueOf(ide.getNihInstitution().value())
                            .getCode(), ideDTO.getNihInstHolderCode().getCode());
        }
        assertEquals(ide.getNumber(), ideDTO.getIndldeNumber().getValue());
        assertEquals(ide.isExempt(), ideDTO.getExemptIndicator().getValue()
                .booleanValue());
        assertEquals(ide.isExpandedAccess(), ideDTO
                .getExpandedAccessIndicator().getValue().booleanValue());

    }

    private void verifyOverallStatus(CompleteTrialRegistration reg,
            ArgumentCaptor<StudyOverallStatusDTO> captor) {
        StudyOverallStatusDTO dto = captor.getValue();
        assertEquals(reg.getTrialStatus().value(), dto.getStatusCode()
                .getCode());
        assertEquals(reg.getWhyStopped(), dto.getReasonText().getValue());
        assertEquals(reg.getTrialStatusDate().toGregorianCalendar().getTime(),
                dto.getStatusDate().getValue());
        assertNull(dto.getIdentifier());
        assertNull(dto.getStudyProtocolIdentifier());

    }

    private void verifyStudyProtocol(CompleteTrialRegistration reg,
            ArgumentCaptor<StudyProtocolDTO> captor) {
        StudyProtocolDTO dto = captor.getValue();
        assertNull(dto.getIdentifier());
        assertEquals(reg.isClinicalTrialsDotGovXmlRequired(), dto
                .getCtgovXmlRequiredIndicator().getValue().booleanValue());
        assertEquals(reg.getOtherTrialID().get(0), dto
                .getSecondaryIdentifiers().getItem().iterator().next()
                .getExtension());
        assertEquals(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT, dto
                .getSecondaryIdentifiers().getItem().iterator().next()
                .getRoot());
        assertEquals(reg.getTitle(), dto.getOfficialTitle().getValue());
        assertEquals(reg.getPhase(), dto.getPhaseCode().getCode());

        if (reg.getPhase().equals("NA")) {
            if (reg.getPilot()) {
                assertEquals("Pilot", dto.getPhaseAdditionalQualifierCode()
                        .getCode());
            }
        } else {
            assertNull(dto.getPhaseAdditionalQualifierCode().getCode());
        }

        assertEquals(reg.getAccrualDiseaseTerminology().value(), dto
                .getAccrualDiseaseCodeSystem().getValue());
        assertEquals(reg.getPrimaryPurpose().value(), dto
                .getPrimaryPurposeCode().getCode());
        assertEquals(reg.getPrimaryPurposeOtherDescription(), dto
                .getPrimaryPurposeOtherText().getValue());
        if (reg.getInterventionalDesign() != null) {
            verifyInterventionalDesign(reg, dto);
        } else {
            verifyNonInterventionalDesign(reg, dto);
        }
        assertEquals(reg.getProgramCode(), dto.getProgramCodeText().getValue());
        assertEquals(reg.isFundedByNciGrant(), dto.getNciGrant().getValue()
                .booleanValue());
        assertEquals(reg.getTrialStartDate().getValue().toGregorianCalendar()
                .getTime(), dto.getStartDate().getValue());
        assertEquals(reg.getTrialStartDate().getType(), dto
                .getStartDateTypeCode().getCode());
        assertEquals(reg.getPrimaryCompletionDate().getValue()
                .toGregorianCalendar().getTime(), dto
                .getPrimaryCompletionDate().getValue());
        assertEquals(reg.getPrimaryCompletionDate().getType(), dto
                .getPrimaryCompletionDateTypeCode().getCode());
        assertEquals(reg.getCompletionDate().getValue().toGregorianCalendar()
                .getTime(), dto.getCompletionDate().getValue());
        assertEquals(reg.getCompletionDate().getType(), dto
                .getCompletionDateTypeCode().getCode());

        if (reg.isClinicalTrialsDotGovXmlRequired()) {
            assertEquals(reg.getRegulatoryInformation().isFdaRegulated(), dto
                    .getFdaRegulatedIndicator().getValue().booleanValue());
            assertEquals(reg.getRegulatoryInformation().isSection801(), dto
                    .getSection801Indicator().getValue().booleanValue());
            assertEquals(reg.getRegulatoryInformation().isDelayedPosting(), dto
                    .getDelayedpostingIndicator().getValue().booleanValue());
            assertEquals(reg.getRegulatoryInformation()
                    .isDataMonitoringCommitteeAppointed(), dto
                    .getDataMonitoringCommitteeAppointedIndicator().getValue()
                    .booleanValue());
        } else {
            assertNull(dto.getFdaRegulatedIndicator());
            assertNull(dto.getSection801Indicator());
            assertNull(dto.getDelayedpostingIndicator());
            assertNull(dto.getDataMonitoringCommitteeAppointedIndicator());
        }
        assertFalse(dto.getProprietaryTrialIndicator().getValue()
                .booleanValue());
        assertEquals("REST Service", dto.getStudySource().getCode());
        assertEquals("jdoe01", dto.getUserLastCreated().getValue());

    }

    private void verifyNonInterventionalDesign(CompleteTrialRegistration reg,
            StudyProtocolDTO proto) {
        NonInterventionalTrialDesign design = reg.getNonInterventionalDesign();
        NonInterventionalStudyProtocolDTO dto = (NonInterventionalStudyProtocolDTO) proto;

        assertEquals(design.getStudyModelCode().value(), dto
                .getStudyModelCode().getCode());
        assertEquals(design.getStudyModelCodeOtherDescription(), dto
                .getStudyModelOtherText().getValue());
        assertEquals(design.getTimePerspectiveCode().value(), dto
                .getTimePerspectiveCode().getCode());
        assertEquals(design.getTimePerspectiveCodeOtherDescription(), dto
                .getTimePerspectiveOtherText().getValue());
        assertEquals(design.getTrialType().value(), dto.getStudySubtypeCode()
                .getCode());

    }

    private void verifyInterventionalDesign(CompleteTrialRegistration reg,
            StudyProtocolDTO dto) {
        assertTrue(dto instanceof InterventionalStudyProtocolDTO);
        InterventionalTrialDesign design = reg.getInterventionalDesign();
        assertEquals(design.getSecondaryPurpose().value(), dto
                .getSecondaryPurposes().getItem().iterator().next().getValue());
        assertEquals(design.getSecondaryPurposeOtherDescription(), dto
                .getSecondaryPurposeOtherText().getValue());
        assertEquals(PAConstants.INTERVENTIONAL, dto.getStudyProtocolType()
                .getValue());
    }

    private void verifyOwners(CompleteTrialRegistration reg,
            ArgumentCaptor<DSet> owners) {
        assertEquals("mailto:" + reg.getTrialOwner().get(0), ((Tel) owners
                .getValue().getItem().iterator().next()).getValue().toString());

    }

    private void verifyBatchMode(CompleteTrialRegistration reg,
            ArgumentCaptor<Bl> isBatchMode) {
        assertFalse(isBatchMode.getValue().getValue().booleanValue());
    }

    @SuppressWarnings("unchecked")
    private CompleteTrialRegistration readCompleteTrialRegistrationFromFile(
            String string) throws JAXBException, SAXException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller u = jc.createUnmarshaller();
        URL url = getClass().getResource(string);
        CompleteTrialRegistration o = ((JAXBElement<CompleteTrialRegistration>) u
                .unmarshal(url)).getValue();
        return o;
    }
}
