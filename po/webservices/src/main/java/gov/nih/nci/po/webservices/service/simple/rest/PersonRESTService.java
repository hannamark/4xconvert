package gov.nih.nci.po.webservices.service.simple.rest;

import gov.nih.nci.po.webservices.service.exception.EntityNotFoundException;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.Person;
import gov.nih.nci.po.webservices.types.PersonList;
import gov.nih.nci.po.webservices.types.PersonRole;
import gov.nih.nci.po.webservices.types.PersonRoleList;
import gov.nih.nci.po.webservices.types.PersonSearchCriteria;
import gov.nih.nci.po.webservices.types.PersonSearchResult;
import gov.nih.nci.po.webservices.types.PersonSearchResultList;
import gov.nih.nci.po.webservices.util.PoWSUtil;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * This is implementation class for PersonService(REST Version).
 * 
 * @author Rohit Gupta
 * 
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.ExcessiveClassLength" })
@Service("personServiceSimpleRestEndpoint")
public class PersonRESTService {

    private static final QName PERSON_QNAME = new QName(
            "gov.nih.nci.po.webservices.types", "person");
    private static final QName PER_ROLE_QNAME = new QName(
            "gov.nih.nci.po.webservices.types", "personRole");

    private static final String APPLICATION_XML = "application/xml";
    private static final String APPLICATION_JSON = "application/json";
    private static final String TXT_PLAIN = "text/plain";
    private static final String ACCEPT = "Accept";
    private static final String PERSON_NOT_FOUND = "Person is not found in the database.";
    private static final String ROLE_NOT_FOUND = "PersonRole is not found in the database.";

    @Autowired
    @Qualifier("personServiceProxy")
    private gov.nih.nci.po.webservices.service.simple.PersonService perServImpl;

    @Context
    private MessageContext context;

    /**
     * This method is used to create a Person.
     * 
     * @param inPerson
     *            Person to be created
     * @return Response-Created(containing Person) if request is processed
     *         successfully otherwise Response-404 or Response-500.
     * @throws URISyntaxException
     *             URISyntaxException
     */
    @POST
    @Path("/person")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    @Consumes({ APPLICATION_XML, APPLICATION_JSON })
    public Response createPerson(Person inPerson) throws URISyntaxException {
        Person createdPerson = null;
        try {
            // call the service to create the person
            createdPerson = perServImpl.createPerson(inPerson);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // code 500
        }

        // build & return the response 201
        return generateCreatePersonResponse(createdPerson);
    }

    /**
     * This method is used to generate the response for createPerson().
     * 
     * @param createdPerson
     *            created person.
     */
    private Response generateCreatePersonResponse(Person createdPerson)
            throws URISyntaxException {
        String uriString = "/person/" + createdPerson.getId();

        HttpServletRequest req = context.getHttpServletRequest();
        String contentType = req.getHeader(ACCEPT);
        if (APPLICATION_XML.equals(contentType)) {
            return Response
                    .created(new URI(uriString))
                    .entity(new JAXBElement<Person>(PERSON_QNAME, Person.class,
                            null, createdPerson)).build(); // code 201
        } else {
            return Response.created(new URI(uriString)).entity(createdPerson)
                    .build(); // code 201
        }
    }

    /**
     * This method is used to update a Person for given personId.
     * 
     * @param id
     *            personId
     * @param inPerson
     *            person with updated details
     * @return Response-OK(containing updated Person) if request is processed
     *         successfully otherwise Response-400, Response-404 or
     *         Response-500.
     */
    @PUT
    @Path("/person/{id}")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    @Consumes({ APPLICATION_XML, APPLICATION_JSON })
    public Response updatePerson(@PathParam("id") long id, Person inPerson) {
        Person upPerson = null;
        try {
            // check if the DB "Ids" are same
            if (inPerson.getId() != null) {
                boolean isSame = PoWSUtil.isIdSame(id, inPerson.getId());
                if (!isSame) {
                    return Response.status(Status.BAD_REQUEST)
                            .entity("URL & Payload PersonIds are not same.")
                            .type(TXT_PLAIN).build(); // code 400
                }
            }

            // use the "Id" present in the URL
            inPerson.setId(id);

            // call the service to update the person
            upPerson = perServImpl.updatePerson(inPerson);
        } catch (EntityNotFoundException e) {
            return Response.status(Status.NOT_FOUND).entity(PERSON_NOT_FOUND)
                    .type(TXT_PLAIN).build(); // 404
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // 500
        }

        // build & return the response 200
        return generateUpdatePersonResponse(upPerson);
    }

    /**
     * This method is used to generate the response for updatePerson().
     * 
     * @param upPerson
     *            updated person.
     */
    private Response generateUpdatePersonResponse(Person upPerson) {
        HttpServletRequest req = context.getHttpServletRequest();
        String contentType = req.getHeader(ACCEPT);
        if (APPLICATION_XML.equals(contentType)) {
            return Response.ok(
                    new JAXBElement<Person>(PERSON_QNAME, Person.class, null,
                            upPerson)).build(); // code 200
        } else {
            return Response.ok(upPerson).build(); // code 200
        }
    }

    /**
     * This method is used to change the status of the person for given
     * personId.
     * 
     * @param id
     *            personId
     * @param status
     *            new status
     * @return Response-OK(containing updated status) if request is processed
     *         successfully otherwise Response-404 or Response-500.
     */
    @PUT
    @Path("/person/{id}/status")
    @Produces(TXT_PLAIN)
    @Consumes(TXT_PLAIN)
    public Response changePersonStatus(@PathParam("id") long id, String status) {
        Person upPerson = null;
        try {
            // get the corresponding EntityStatus
            EntityStatus etyStatus = PoWSUtil.getEntityStatus(status);

            // call the service to change the person status
            upPerson = perServImpl.changePersonStatus(id, etyStatus);
        } catch (EntityNotFoundException e) {
            return Response.status(Status.NOT_FOUND).entity(PERSON_NOT_FOUND)
                    .type(TXT_PLAIN).build(); // 404

        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // 500
        }

        // build & return the response 200
        return Response.ok(upPerson.getStatus().value()).build(); // code 200
    }

    /**
     * This method is used to get the person on the basis of DB Id.
     * 
     * @param id
     *            personId
     * @return Response-OK(containing Person) if request is processed
     *         successfully otherwise Response-404 or Response-500.
     */
    @GET
    @Path("/person/{id}")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    public Response getPerson(@PathParam("id") long id) {
        Person retPerson = null;
        try {
            // call the service to get the person
            retPerson = perServImpl.getPerson(id);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // code 500
        }

        if (retPerson == null) {
            // 404 because caller implies that a person with given ID exists
            return Response.status(Status.NOT_FOUND).entity(PERSON_NOT_FOUND)
                    .type(TXT_PLAIN).build(); // code 404
        } else {
            // build & return the response 200
            return generateGetPersonResponse(retPerson);
        }
    }

    /**
     * This method is used to generate the response for getPerson().
     * 
     * @param retPerson
     *            person returned from the database.
     */
    private Response generateGetPersonResponse(Person retPerson) {
        HttpServletRequest req = context.getHttpServletRequest();
        String contentType = req.getHeader(ACCEPT);
        if (APPLICATION_XML.equals(contentType)) {
            return Response.ok(
                    new JAXBElement<Person>(PERSON_QNAME, Person.class, null,
                            retPerson)).build(); // code 200
        } else {
            return Response.ok(retPerson).build(); // code 200
        }
    }

    /**
     * This method is used to get the Persons for the given CtepId.
     * 
     * @param id
     *            ctepId
     * @return Response-OK(containing Person List)if request is processed
     *         successfully otherwise Response-404 or Response-500.
     */
    @GET
    @Path("/person/ctep/{id}")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    public Response getPersonsByCtepId(@PathParam("id") String id) {
        List<Person> retPeople = null;
        try {
            retPeople = perServImpl.getPersonsByCtepId(id);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // code 500
        }

        if (CollectionUtils.isEmpty(retPeople)) {
            // 404-caller implies that person(s) with given CTEP ID exists
            return Response.status(Status.NOT_FOUND).entity(PERSON_NOT_FOUND)
                    .type(TXT_PLAIN).build(); // code 404
        } else {
            PersonList personList = new PersonList();
            personList.getPerson().addAll(retPeople);
            // build & return the response
            return Response.ok(personList).build(); // code 200
        }

    }

    /**
     * This method is used to create a personRole.
     * 
     * @param inPerRole
     *            PersonRole to be created
     * @return Response-Created(containing PersonRole) if request is processed
     *         successfully otherwise Response-404 or Response-500.
     * @throws URISyntaxException
     *             URISyntaxException
     */
    @POST
    @Path("/role")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    @Consumes({ APPLICATION_XML, APPLICATION_JSON })
    public Response createPersonRole(PersonRole inPerRole)
            throws URISyntaxException {
        PersonRole createdPerRole = null;
        try {
            // call the service to create the person role
            createdPerRole = perServImpl.createPersonRole(inPerRole);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // code 500
        }

        // build & return the response 201
        return generateCreatePersonRoleResponse(createdPerRole);
    }

    /**
     * This method is used to generate the response for createPersonRole().
     * 
     * @param createdPerRole
     *            created person role.
     */
    private Response generateCreatePersonRoleResponse(PersonRole createdPerRole)
            throws URISyntaxException {
        String uriString = "/role/" + createdPerRole.getClass().getSimpleName()
                + "/" + createdPerRole.getId();

        HttpServletRequest req = context.getHttpServletRequest();
        String contentType = req.getHeader(ACCEPT);
        if (APPLICATION_XML.equals(contentType)) {
            return Response
                    .created(new URI(uriString))
                    .entity(new JAXBElement<PersonRole>(PER_ROLE_QNAME,
                            PersonRole.class, null, createdPerRole)).build(); // 201
        } else {
            return Response.created(new URI(uriString)).entity(createdPerRole)
                    .build(); // code 201
        }
    }

    /**
     * This method is used to update a personRole.
     * 
     * @param type
     *            personRole type
     * @param id
     *            personRoleId
     * @param inPerRole
     *            personRole
     * @return Response-OK(containing updated PersonRole) if request is
     *         processed successfully otherwise Response-400, Response-404 or
     *         Response-500.
     */
    @PUT
    @Path("/role/{type}/{id}")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    @Consumes({ APPLICATION_XML, APPLICATION_JSON })
    public Response updatePersonRole(@PathParam("type") String type,
            @PathParam("id") long id, PersonRole inPerRole) {
        PersonRole upPerRole = null;
        try {
            // check if the DB "Ids" are same
            if (inPerRole.getId() != null) {
                boolean isSame = PoWSUtil.isIdSame(id, inPerRole.getId());
                if (!isSame) {
                    return Response
                            .status(Status.BAD_REQUEST)
                            .entity("URL & Payload PersonRoleIds are not same.")
                            .type(TXT_PLAIN).build(); // code 400
                }
            }

            // check if the personrole 'type' are same
            if (!PoWSUtil.isRoleTypeSame(type, inPerRole)) {
                return Response.status(Status.BAD_REQUEST)
                        .entity("URL & Payload PersonRole type are not same.")
                        .type(TXT_PLAIN).build(); // code 400
            }

            // use the "Id" present in the URL
            inPerRole.setId(id);

            // call the service to update the person role
            upPerRole = perServImpl.updatePersonRole(inPerRole);
        } catch (EntityNotFoundException e) {
            return Response.status(Status.NOT_FOUND).entity(ROLE_NOT_FOUND)
                    .type(TXT_PLAIN).build(); // 404
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // 500
        }

        // build & return the response 200
        return generateUpdatePersonRoleResponse(upPerRole);
    }

    /**
     * This method is used to generate the response for updatePersonRole().
     * 
     * @param upPerRole
     *            updated person role.
     */
    private Response generateUpdatePersonRoleResponse(PersonRole upPerRole) {
        HttpServletRequest req = context.getHttpServletRequest();
        String contentType = req.getHeader(ACCEPT);
        if (APPLICATION_XML.equals(contentType)) {
            return Response.ok(
                    new JAXBElement<PersonRole>(PER_ROLE_QNAME,
                            PersonRole.class, null, upPerRole)).build(); // code
                                                                         // 200
        } else {
            return Response.ok(upPerRole).build(); // code 200
        }
    }

    /**
     * This method is used to change the personrole status.
     * 
     * @param type
     *            personrole type
     * @param id
     *            personrole id
     * @param status
     *            new status
     * @return Response-OK(containing updated status) if request is processed
     *         successfully otherwise Response-404 or Response-500.
     */
    @SuppressWarnings("unchecked")
    @PUT
    @Path("/role/{type}/{id}/status")
    @Produces(TXT_PLAIN)
    @Consumes(TXT_PLAIN)
    public Response changePersonRoleStatus(@PathParam("type") String type,
            @PathParam("id") long id, String status) {
        PersonRole upPerRole = null;
        try {
            // get the corresponding EntityStatus to be passed
            EntityStatus etyStatus = PoWSUtil.getEntityStatus(status);

            // call the service to change the personrole status
            upPerRole = perServImpl.changePersonRoleStatus(
                    PoWSUtil.getPersonRoleClass(type), id, etyStatus);
        } catch (EntityNotFoundException e) {
            return Response.status(Status.NOT_FOUND).entity(ROLE_NOT_FOUND)
                    .type(TXT_PLAIN).build(); // code 404
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // 500
        }

        // build & return the response 200
        return Response.ok(upPerRole.getStatus().value()).build(); // code 200
    }

    /**
     * This method is used to get the personRole for given personId.
     * 
     * @param id
     *            personId
     * @return Response-OK(containing PersonRole List)if request is processed
     *         successfully otherwise Response-404 or Response-500.
     */
    @GET
    @Path("/person/{id}/roles")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    public Response getPersonRolesByPersonId(@PathParam("id") long id) {
        List<PersonRole> retPerRoles = null;
        try {
            retPerRoles = perServImpl.getPersonRolesByPersonId(id);
        } catch (EntityNotFoundException e) {
            // 404 if person not found
            return Response.status(Status.NOT_FOUND).entity(PERSON_NOT_FOUND)
                    .type(TXT_PLAIN).build(); // 404
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // 500
        }

        PersonRoleList perRoleList = new PersonRoleList();
        perRoleList.getPersonRole().addAll(retPerRoles);
        // build & return the response 200
        return Response.ok(perRoleList).build(); // code 200

    }

    /**
     * This method is used to get the personRole for given roleId.
     * 
     * @param type
     *            personRole type
     * @param id
     *            personrole "db" id
     * @return Response-OK(containing PersonRole)if request is processed
     *         successfully otherwise Response-404 or Response-500.
     */
    @SuppressWarnings("unchecked")
    @GET
    @Path("/role/{type}/{id}")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    public Response getPersonRoleById(@PathParam("type") String type,
            @PathParam("id") long id) {
        PersonRole retPerRole = null;
        try {
            // call the service to get the person role
            retPerRole = perServImpl.getPersonRoleById(
                    PoWSUtil.getPersonRoleClass(type), id);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // code 500
        }

        if (retPerRole == null) {
            // 404-caller implies that a person role with given ID exists
            return Response.status(Status.NOT_FOUND).entity(ROLE_NOT_FOUND)
                    .type(TXT_PLAIN).build(); // code 404
        } else {
            // build & return the response 200
            return generateGetPersonRoleResponse(retPerRole);
        }
    }

    /**
     * This method is used to generate the response for getPersonRoleById().
     * 
     * @param retPerRole
     *            personRole returned from the database.
     */
    private Response generateGetPersonRoleResponse(PersonRole retPerRole) {
        HttpServletRequest req = context.getHttpServletRequest();
        String contentType = req.getHeader(ACCEPT);
        if (APPLICATION_XML.equals(contentType)) {
            return Response.ok(
                    new JAXBElement<PersonRole>(PER_ROLE_QNAME,
                            PersonRole.class, null, retPerRole)).build(); // 200
        } else {
            return Response.ok(retPerRole).build(); // code 200
        }
    }

    /**
     * This method is used to search the persons.
     * 
     * @return Response-OK(containing PersonSearchResultList)if request is
     *         processed successfully otherwise Response-404 or Response-500.
     */
    @GET
    @Path("/persons")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    public Response searchPersons() {
        List<PersonSearchResult> psrList = null;
        try {
            // get the search criteria
            PersonSearchCriteria psc = getPersonSearchCriteria();

            // call the service to get the person
            psrList = perServImpl.searchPersons(psc);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // code 500
        }

        // XML Person Search Results or empty collection if no persons found
        PersonSearchResultList list = new PersonSearchResultList();
        list.getPersonSearchResult().addAll(psrList);
        return Response.ok(list).build(); // code 200
    }

    /**
     * This method is used to populate the PersonSearchCriteria.
     * 
     * @throws UnsupportedEncodingException
     */    
    private PersonSearchCriteria getPersonSearchCriteria()
            throws UnsupportedEncodingException {
        PersonSearchCriteria psc = new PersonSearchCriteria();
        HttpServletRequest req = context.getHttpServletRequest();
        req.setCharacterEncoding("utf-8");        

        // populate Person Specific Search Criteria fields
        populatePersonSearchCriteria(psc, req);

        // populate base search criteria fields
        PoWSUtil.populateBaseSearchCriteria(psc, req);        

        return psc;
    }
    
    private void populatePersonSearchCriteria(PersonSearchCriteria psc,
            HttpServletRequest req) {
        
        populateNameEmailFields(psc, req);        

        String affiliation = req.getParameter("affiliation");
        if (StringUtils.isNotBlank(affiliation)) {
            psc.setAffiliation(affiliation);
        }

        populateIdFields(psc, req);

        EntityStatus etyStatus = null;
        if (StringUtils.isNotBlank(req.getParameter("statusCode"))) {
            etyStatus = PoWSUtil
                    .getEntityStatus(req.getParameter("statusCode"));
            psc.setStatusCode(etyStatus);
        }

        populatePendingRoleFields(psc, req);
    }
    
    private void populateIdFields(PersonSearchCriteria psc,
            HttpServletRequest req) {
        
        String ctepID = req.getParameter("ctepID");
        if (StringUtils.isNotBlank(ctepID)) {
            psc.setCtepID(ctepID);
        }

        long id = 0;
        if (req.getParameter("id") != null) {
            id = Long.valueOf(req.getParameter("id"));
            psc.setId(id);
        }     
    }
    
    private void populateNameEmailFields(PersonSearchCriteria psc,
            HttpServletRequest req) {
        String firstName = req.getParameter("firstName");
        if (StringUtils.isNotBlank(firstName)) {
            psc.setFirstName(firstName);
        }

        String lastName = req.getParameter("lastName");
        if (StringUtils.isNotBlank(lastName)) {
            psc.setLastName(lastName);
        }        
        
        String email = req.getParameter("email");
        if (StringUtils.isNotBlank(email)) {
            psc.setEmail(email);
        }
    }
    
    private void populatePendingRoleFields(PersonSearchCriteria psc,
            HttpServletRequest req) {

        boolean hasPendingCrsRoles = false;
        if (req.getParameter("hasPendingCrsRoles") != null) {
            hasPendingCrsRoles = Boolean.valueOf(req
                    .getParameter("hasPendingCrsRoles"));
            psc.setHasPendingCrsRoles(hasPendingCrsRoles);
        }
        boolean hasPendingHcpRoles = false;
        if (req.getParameter("hasPendingHcpRoles") != null) {
            hasPendingHcpRoles = Boolean.valueOf(req
                    .getParameter("hasPendingHcpRoles"));
            psc.setHasPendingHcpRoles(hasPendingHcpRoles);
        }
        boolean hasPendingOcRoles = false;
        if (req.getParameter("hasPendingOcRoles") != null) {
            hasPendingOcRoles = Boolean.valueOf(req
                    .getParameter("hasPendingOcRoles"));
            psc.setHasPendingOcRoles(hasPendingOcRoles);
        }
    }
    
}