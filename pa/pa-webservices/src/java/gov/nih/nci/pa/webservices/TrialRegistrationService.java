/**
 * 
 */
package gov.nih.nci.pa.webservices;

import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.webservices.converters.DocumentDTOBuilder;
import gov.nih.nci.pa.webservices.converters.OrganizationDTOBuilder;
import gov.nih.nci.pa.webservices.converters.PersonDTOBuilder;
import gov.nih.nci.pa.webservices.converters.ResponsiblePartyDTOBuilder;
import gov.nih.nci.pa.webservices.converters.StudyIndldeDTOBuilder;
import gov.nih.nci.pa.webservices.converters.StudyOverallStatusDTOBuilder;
import gov.nih.nci.pa.webservices.converters.StudyProtocolDTOBuilder;
import gov.nih.nci.pa.webservices.converters.StudyRegulatoryAuthorityDTOBuilder;
import gov.nih.nci.pa.webservices.converters.StudyResourcingDTOBuilder;
import gov.nih.nci.pa.webservices.converters.StudySiteDTOBuilder;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.CompleteTrialUpdate;
import gov.nih.nci.pa.webservices.types.ObjectFactory;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.cache.NoCache;

/**
 * @author dkrylov
 * 
 */
@Path("/")
@Provider
public class TrialRegistrationService implements ContextResolver<JAXBContext> {

    private static final String APPLICATION_XML = "application/xml";
    private static final String TXT_PLAIN = "text/plain";

    private static final Logger LOG = Logger
            .getLogger(TrialRegistrationService.class);

    private PAServiceUtils paServiceUtils = new PAServiceUtils();

    /**
     * Registers an complete trial.
     * 
     * @param reg
     *            CompleteTrialRegistration
     * @return Response
     */
    @POST
    @Path("/registration/complete")
    @Consumes({ APPLICATION_XML })
    @Produces({ APPLICATION_XML })
    @NoCache
    public Response registerCompleteTrial(
            @Validate CompleteTrialRegistration reg) {
        try {
            StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTOBuilder()
                    .build(reg);
            StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTOBuilder()
                    .build(reg);
            List<StudyIndldeDTO> studyIndldeDTOs = new StudyIndldeDTOBuilder()
                    .build(reg);
            List<StudyResourcingDTO> studyResourcingDTOs = new StudyResourcingDTOBuilder()
                    .build(reg.getGrant());
            List<DocumentDTO> documentDTOs = new DocumentDTOBuilder()
                    .build(reg);
            OrganizationDTO leadOrgDTO = new OrganizationDTOBuilder().build(reg
                    .getLeadOrganization());
            PersonDTO principalInvestigatorDTO = new PersonDTOBuilder()
                    .build(reg.getPi());
            OrganizationDTO sponsorOrgDTO = new OrganizationDTOBuilder()
                    .build(reg.getSponsor());
            ResponsiblePartyDTO partyDTO = new ResponsiblePartyDTOBuilder(
                    principalInvestigatorDTO, sponsorOrgDTO).build(reg);

            StudySiteDTO leadOrgSiteIdDTO = new StudySiteDTO();
            leadOrgSiteIdDTO.setLocalStudyProtocolIdentifier(StConverter
                    .convertToSt(reg.getLeadOrgTrialID()));

            List<StudySiteDTO> studyIdentifierDTOs = new ArrayList<StudySiteDTO>();
            studyIdentifierDTOs.add(new StudySiteDTOBuilder()
                    .buildClinicalTrialsGovIdAssigner(reg
                            .getClinicalTrialsDotGovTrialID()));

            List<OrganizationDTO> summary4orgDTO = new OrganizationDTOBuilder()
                    .build(reg.getSummary4FundingSponsor());

            StudyResourcingDTO summary4studyResourcingDTO = new StudyResourcingDTO();
            summary4studyResourcingDTO.setTypeCode(CdConverter
                    .convertToCd(SummaryFourFundingCategoryCode.getByCode(reg
                            .getCategory().value())));
            StudyRegulatoryAuthorityDTO studyRegAuthDTO = new StudyRegulatoryAuthorityDTOBuilder()
                    .build(reg);

            DSet<Tel> owners = new DSet<>();
            owners.setItem(new LinkedHashSet<Tel>());
            for (String emailAddr : reg.getTrialOwner()) {
                Tel telEmail = new Tel();
                telEmail.setValue(new URI("mailto:" + emailAddr));
                owners.getItem().add(telEmail);
            }

            Ii studyProtocolIi = PaRegistry.getTrialRegistrationService()
                    .createCompleteInterventionalStudyProtocol(
                            studyProtocolDTO, overallStatusDTO,
                            studyIndldeDTOs, studyResourcingDTOs, documentDTOs,
                            leadOrgDTO, principalInvestigatorDTO,
                            sponsorOrgDTO, partyDTO, leadOrgSiteIdDTO,
                            studyIdentifierDTOs, summary4orgDTO,
                            summary4studyResourcingDTO, studyRegAuthDTO,
                            BlConverter.convertToBl(Boolean.FALSE), owners);
            long paTrialID = IiConverter.convertToLong(studyProtocolIi);
            return buildTrialRegConfirmationResponse(paTrialID);
        } catch (Exception e) {
            return handleException(e);
        }

    }

    /**
     * Updates a complete trial.
     * 
     * @param reg
     *            CompleteTrialRegistration
     * @return Response
     */
    @POST
    @Path("/update/complete")
    @Consumes({ APPLICATION_XML })
    @Produces({ APPLICATION_XML })
    @NoCache
    public Response updateCompleteTrial(@Validate CompleteTrialUpdate reg) {
        try {
            StudyProtocolDTO spDTO = findTrial(reg);
            Long paTrialID = IiConverter.convertToLong(spDTO.getIdentifier());

            List<StudySiteDTO> studyIdentifierDTOs = new ArrayList<StudySiteDTO>();
            if (StringUtils.isBlank(paServiceUtils.getStudyIdentifier(
                    spDTO.getIdentifier(), PAConstants.NCT_IDENTIFIER_TYPE))) {
                studyIdentifierDTOs.add(new StudySiteDTOBuilder()
                        .buildClinicalTrialsGovIdAssigner(reg
                                .getClinicalTrialsDotGovTrialID()));
            }
            new StudyProtocolDTOBuilder().build(spDTO, reg);
            List<StudyResourcingDTO> studyResourcingDTOs = new StudyResourcingDTOBuilder()
                    .build(reg.getGrant());
            StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTOBuilder()
                    .build(spDTO, reg);
            List<DocumentDTO> documentDTOs = new DocumentDTOBuilder().build(
                    spDTO, reg);
            PaRegistry.getTrialRegistrationService().update(spDTO,
                    overallStatusDTO, studyIdentifierDTOs, null,
                    studyResourcingDTOs, documentDTOs, null, null, null, null,
                    null, null, null, null, null,
                    BlConverter.convertToBl(Boolean.FALSE));
            return buildTrialRegConfirmationResponse(paTrialID);
        } catch (Exception e) {
            return handleException(e);
        }

    }

    private StudyProtocolDTO findTrial(CompleteTrialUpdate reg)
            throws PAException {
        return findTrialByAnyIdentifier(reg.getPaTrialID(),
                reg.getNciTrialID(), reg.getCtepTrialID());
    }

    // Ugly method signature, I know. Sorry.
    private StudyProtocolDTO findTrialByAnyIdentifier(Long paTrialID,
            String nciTrialID, String ctepTrialID) throws PAException {
        Ii ii = new Ii();
        if (paTrialID != null) {
            ii = IiConverter.convertToStudyProtocolIi(paTrialID);
        } else if (StringUtils.isNotBlank(nciTrialID)) {
            ii.setExtension(nciTrialID);
            ii.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        } else if (StringUtils.isNotBlank(ctepTrialID)) {
            ii.setExtension(ctepTrialID);
            ii.setRoot(IiConverter.CTEP_STUDY_PROTOCOL_ROOT);
        }
        return PaRegistry.getStudyProtocolService().getStudyProtocol(ii);
    }

    private Response handleException(Exception e) {
        if (e instanceof PoEntityNotFoundException) {
            return logErrorAndPrepareResponse(Status.NOT_FOUND, e);
        } else if (e instanceof TrialDataException
                || e instanceof PoEntityCannotBeCreatedException
                || e instanceof PAValidationException) {
            return logErrorAndPrepareResponse(Status.BAD_REQUEST, e);
        } else if (e instanceof PAException) {
            return StringUtils.startsWithIgnoreCase(e.getMessage(),
                    "Validation Exception") ? logErrorAndPrepareResponse(
                    Status.BAD_REQUEST, e) : logErrorAndPrepareResponse(
                    Status.INTERNAL_SERVER_ERROR, e);
        } else {
            return logErrorAndPrepareResponse(Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    private Response logErrorAndPrepareResponse(Status status, Exception e) {
        LOG.error(e, e);
        return Response.status(status).entity(e.getMessage()).type(TXT_PLAIN)
                .build();
    }

    private Response buildTrialRegConfirmationResponse(long paTrialID) {
        TrialRegistrationConfirmation conf = new TrialRegistrationConfirmation();
        conf.setNciTrialID(paServiceUtils.getTrialNciId(paTrialID));
        conf.setPaTrialID(paTrialID);
        return Response.ok(
                new ObjectFactory().createTrialRegistrationConfirmation(conf))
                .build();
    }

    /**
     * Registers an abbreviated trial by importing it from ClinicalTrials.gov.
     * 
     * @param nct
     *            ClinicalTrials.gov ID
     * @return Response
     */
    @POST
    @Path("/registration/abbreviated/{nct}")
    @Produces({ APPLICATION_XML })
    @NoCache
    public Response registerAbbreviatedTrial(@PathParam("nct") String nct) {
        CTGovSyncServiceLocal ctGovSyncService = PaRegistry
                .getCTGovSyncService();
        StudyProtocolServiceLocal studyProtocolService = PaRegistry
                .getStudyProtocolService();
        Response response;
        try {
            response = validateNctId(nct);
            if (response == null) {
                String nciID = ctGovSyncService.importTrial(nct);
                final Long newTrialId = IiConverter
                        .convertToLong(studyProtocolService.getStudyProtocol(
                                IiConverter
                                        .convertToAssignedIdentifierIi(nciID))
                                .getIdentifier());
                return buildTrialRegConfirmationResponse(newTrialId);
            }
        } catch (Exception e) {
            return logErrorAndPrepareResponse(Status.INTERNAL_SERVER_ERROR, e);
        }
        return response;
    }

    private Response validateNctId(String nct) throws PAException {
        CTGovSyncServiceLocal ctGovSyncService = PaRegistry
                .getCTGovSyncService();
        StudyProtocolServiceLocal studyProtocolService = PaRegistry
                .getStudyProtocolService();
        if (StringUtils.isBlank(nct)) {
            return Response
                    .status(Status.BAD_REQUEST)
                    .entity("Please provide an ClinicalTrials.gov Identifier value.")
                    .type(TXT_PLAIN).build();
        } else if (!StringUtils.isAlphanumericSpace(nct)) {
            return Response
                    .status(Status.BAD_REQUEST)
                    .entity("Provided ClinicalTrials.gov Identifer is invalid.")
                    .type(TXT_PLAIN).build();
        } else if (ctGovSyncService.getAdaptedCtGovStudyByNctId(nct) == null) {
            return Response
                    .status(Status.NOT_FOUND)
                    .entity("A study with the given identifier is not found in ClinicalTrials.gov.")
                    .type(TXT_PLAIN).build();
        } else if (!studyProtocolService.getStudyProtocolsByNctId(// NOPMD
                nct).isEmpty()) {
            return Response
                    .status(Status.PRECONDITION_FAILED)
                    .entity("A study with the given identifier already exists in CTRP.")
                    .type(TXT_PLAIN).build();

        }
        return null;
    }

    @Override
    public JAXBContext getContext(Class<?> arg0) {
        try {
            return JAXBContext.newInstance(ObjectFactory.class);
        } catch (JAXBException e) {
            LOG.error(e, e);
            throw new RuntimeException(e); // NOPMD
        }
    }

    /**
     * @param paServiceUtils
     *            the paServiceUtils to set
     */
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }

}
