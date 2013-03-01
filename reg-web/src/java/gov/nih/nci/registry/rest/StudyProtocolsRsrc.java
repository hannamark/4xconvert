package gov.nih.nci.registry.rest;

import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.rest.domain.StudyProtocolXmlLean;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;

/**
 * @author Hugh Reinhart
 * @since Feb 11, 2013
 */
@Path("/studyProtocols")
@Produces(MediaType.APPLICATION_XML)
public class StudyProtocolsRsrc {
    private static final Logger LOG = Logger.getLogger(StudyProtocolsRsrc.class);

    /**
     * Query study protocols returning minimal data for each trial.
     * @param agentNsc agent used in study
     * @return the study protocol list
     */
    @Path("/")
    @Wrapped(element = "studyProtocols")
    @GET
    public Response getStudiesXML(@QueryParam("agentNsc") String agentNsc) {
        if (agentNsc == null) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        try {
            List<StudyProtocolQueryDTO> results = 
                    PaRegistry.getProtocolQueryService().getStudyProtocolByAgentNsc(agentNsc);
            List<StudyProtocolXmlLean> xmlList = StudyProtocolXmlLean.getList(results);
            GenericEntity<List<StudyProtocolXmlLean>> ge = new GenericEntity<List<StudyProtocolXmlLean>>(xmlList) { };
            return Response.ok(ge).build();
        } catch (Exception e) {
            LOG.error(e);
            return Response.serverError().build();
        }
    }
}
