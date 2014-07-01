/**
 * 
 */
package gov.nih.nci.pa.webservices;

import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.webservices.types.ObjectFactory;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;

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
                TrialRegistrationConfirmation conf = new TrialRegistrationConfirmation();
                conf.setNciTrialID(nciID);
                conf.setPaTrialID(newTrialId);
                return Response.ok(
                        new ObjectFactory()
                                .createTrialRegistrationConfirmation(conf))
                        .build();
            }
        } catch (Exception e) {
            response = Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build();
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
            return JAXBContext.newInstance(TrialRegistrationConfirmation.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e); // NOPMD
        }
    }

}
