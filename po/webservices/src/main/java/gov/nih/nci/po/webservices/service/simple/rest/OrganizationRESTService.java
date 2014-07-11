package gov.nih.nci.po.webservices.service.simple.rest;

import gov.nih.nci.po.webservices.service.exception.EntityNotFoundException;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.Organization;
import gov.nih.nci.po.webservices.types.OrganizationRole;
import gov.nih.nci.po.webservices.types.OrganizationRoleList;
import gov.nih.nci.po.webservices.types.OrganizationSearchCriteria;
import gov.nih.nci.po.webservices.types.OrganizationSearchResult;
import gov.nih.nci.po.webservices.types.OrganizationSearchResultList;
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
 * This is implementation class for OrganizationService(REST Version).
 * 
 * @author Rohit Gupta
 * 
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.ExcessiveClassLength" })
@Service("organizationServiceSimpleRestEndpoint")
public class OrganizationRESTService {

    private static final QName ORG_QNAME = new QName(
            "gov.nih.nci.po.webservices.types", "organization");
    private static final QName ORG_ROLE_QNAME = new QName(
            "gov.nih.nci.po.webservices.types", "organizationRole");

    private static final String APPLICATION_XML = "application/xml";
    private static final String APPLICATION_JSON = "application/json";
    private static final String TXT_PLAIN = "text/plain";
    private static final String ACCEPT = "Accept";
    private static final String ORG_NOT_FOUND = "Organization is not found in the database.";
    private static final String ORG_ROLE_NOT_FOUND = "OrganizationRole is not found in the database.";

    @Autowired
    @Qualifier("orgServiceProxy")
    private gov.nih.nci.po.webservices.service.simple.OrganizationService orgServImpl;

    @Context
    private MessageContext context;

    /**
     * This method is used to create an Organization.
     * 
     * @param organization
     *            Organization to be created
     * @return Response-Created(containing Organization) if request is processed
     *         successfully otherwise Response-404 or Response-500.
     * @throws URISyntaxException
     *             URISyntaxException
     */
    @POST
    @Path("/organization")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    @Consumes({ APPLICATION_XML, APPLICATION_JSON })
    public Response createOrganization(Organization organization)
            throws URISyntaxException {
        Organization createdOrg = null;
        try {
            // call the service to create the organization
            createdOrg = orgServImpl.createOrganization(organization);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // code 500
        }

        // build & return the response 201
        return generateCreateOrganizationResponse(createdOrg);
    }

    /**
     * This method is used to generate the response for createOrganization().
     * 
     * @param createdOrg
     *            created Organization.
     */
    private Response generateCreateOrganizationResponse(Organization createdOrg)
            throws URISyntaxException {
        String uriString = "/organization/" + createdOrg.getId();

        HttpServletRequest req = context.getHttpServletRequest();
        String contentType = req.getHeader(ACCEPT);
        if (APPLICATION_XML.equals(contentType)) {
            return Response
                    .created(new URI(uriString))
                    .entity(new JAXBElement<Organization>(ORG_QNAME,
                            Organization.class, null, createdOrg)).build(); // 201
        } else {
            return Response.created(new URI(uriString)).entity(createdOrg)
                    .build(); // code 201
        }
    }

    /**
     * This method is used to update an Organization for given organizationId.
     * 
     * @param id
     *            OrganizationId
     * @param organization
     *            organization with updated details
     * @return Response-OK(containing updated Organization) if request is
     *         processed successfully otherwise Response-400, Response-404 or
     *         Response-500.
     */
    @PUT
    @Path("/organization/{id}")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    @Consumes({ APPLICATION_XML, APPLICATION_JSON })
    public Response updateOrganization(@PathParam("id") long id,
            Organization organization) {
        Organization upOrg = null;
        try {
            // check if the DB "Ids" are same
            if (organization.getId() != null) {
                boolean isSame = PoWSUtil.isIdSame(id, organization.getId());
                if (!isSame) {
                    return Response.status(Status.BAD_REQUEST)
                            .entity("URL & Payload OrgIds are not same.")
                            .type(TXT_PLAIN).build(); // code 400
                }
            }

            // use the "Id" present in the URL
            organization.setId(id);

            // call the service to update the organization
            upOrg = orgServImpl.updateOrganization(organization);
        } catch (EntityNotFoundException e) {
            return Response.status(Status.NOT_FOUND).entity(ORG_NOT_FOUND)
                    .type(TXT_PLAIN).build(); // 404
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // 500
        }

        // build & return the response 200
        return generateUpdateOrganizationResponse(upOrg);
    }

    /**
     * This method is used to generate the response for updateOrganization().
     * 
     * @param upOrg
     *            updated Organization.
     */
    private Response generateUpdateOrganizationResponse(Organization upOrg) {
        HttpServletRequest req = context.getHttpServletRequest();
        String contentType = req.getHeader(ACCEPT);
        if (APPLICATION_XML.equals(contentType)) {
            return Response.ok(
                    new JAXBElement<Organization>(ORG_QNAME,
                            Organization.class, null, upOrg)).build(); // 200
        } else {
            return Response.ok(upOrg).build(); // code 200
        }
    }

    /**
     * This method is used to change the status of the organization for given
     * organizationId.
     * 
     * @param id
     *            organizationId
     * @param status
     *            new status
     * @return Response-OK(containing updated status) if request is processed
     *         successfully otherwise Response-404 or Response-500.
     */
    @PUT
    @Path("/organization/{id}/status")
    @Produces(TXT_PLAIN)
    @Consumes(TXT_PLAIN)
    public Response changeOrganizationStatus(@PathParam("id") long id,
            String status) {
        Organization upOrg = null;
        try {
            // get the corresponding EntityStatus
            EntityStatus etyStatus = PoWSUtil.getEntityStatus(status);

            // call the service to change the organization status
            upOrg = orgServImpl.changeOrganizationStatus(id, etyStatus);
        } catch (EntityNotFoundException e) {
            return Response.status(Status.NOT_FOUND).entity(ORG_NOT_FOUND)
                    .type(TXT_PLAIN).build(); // 404

        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // 500
        }

        // build & return the response 200
        return Response.ok(upOrg.getStatus().value()).build(); // code 200
    }

    /**
     * This method is used to get the Organization on the basis of DB Id.
     * 
     * @param id
     *            OrganizationId
     * @return Response-OK(containing Organization) if request is processed
     *         successfully otherwise Response-404 or Response-500.
     */
    @GET
    @Path("/organization/{id}")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    public Response getOrganization(@PathParam("id") long id) {
        Organization retOrg = null;
        try {
            // call the service to get the Organization
            retOrg = orgServImpl.getOrganization(id);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // code 500
        }

        if (retOrg == null) {
            // 404-caller implies that an Org with given ID exists
            return Response.status(Status.NOT_FOUND).entity(ORG_NOT_FOUND)
                    .type(TXT_PLAIN).build(); // code 404
        } else {
            // build & return the response 200
            return generateGetOrganizationResponse(retOrg);
        }
    }

    /**
     * This method is used to generate the response for getOrganization().
     * 
     * @param retOrg
     *            Organization returned from the database.
     */
    private Response generateGetOrganizationResponse(Organization retOrg) {
        HttpServletRequest req = context.getHttpServletRequest();
        String contentType = req.getHeader(ACCEPT);
        if (APPLICATION_XML.equals(contentType)) {
            return Response.ok(
                    new JAXBElement<Organization>(ORG_QNAME,
                            Organization.class, null, retOrg)).build(); // 200
        } else {
            return Response.ok(retOrg).build(); // code 200
        }
    }

    /**
     * This method is used to search the Organizations.
     * 
     * @return Response-OK(containing OrganizationSearchResultList)if request is
     *         processed successfully otherwise Response-404 or Response-500.
     */
    @GET
    @Path("/organizations")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    public Response searchOrganizations() {
        List<OrganizationSearchResult> osrList = null;
        try {
            // get the search criteria
            OrganizationSearchCriteria osc = getOrganizationSearchCriteria();

            // call the service to get the organization
            osrList = orgServImpl.searchOrganizations(osc);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // code 500
        }

        // XML Organization Search Results or empty collection if no orgs found
        OrganizationSearchResultList list = new OrganizationSearchResultList();
        list.getOrganizationSearchResult().addAll(osrList);
        return Response.ok(list).build(); // code 200
    }

    /**
     * This method is used to create a OrganizationRole.
     * 
     * @param orgRole
     *            OrganizationRole to be created
     * @return Response-Created(containing OrganizationRole) if request is
     *         processed successfully otherwise Response-404 or Response-500.
     * @throws URISyntaxException
     *             URISyntaxException
     */
    @POST
    @Path("/role")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    @Consumes({ APPLICATION_XML, APPLICATION_JSON })
    public Response createOrganizationRole(OrganizationRole orgRole)
            throws URISyntaxException {
        OrganizationRole createdOrgRole = null;
        try {
            // call the service to create the organization role
            createdOrgRole = orgServImpl.createOrganizationRole(orgRole);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // code 500
        }

        // build & return the response 201
        return generateCreateOrganizationRoleResponse(createdOrgRole);
    }

    /**
     * This method is used to generate the response for
     * createOrganizationRole().
     * 
     * @param createdOrgRole
     *            created organization role.
     */
    private Response generateCreateOrganizationRoleResponse(
            OrganizationRole createdOrgRole) throws URISyntaxException {

        String uriString = "/role/" + createdOrgRole.getClass().getSimpleName()
                + "/" + createdOrgRole.getId();

        HttpServletRequest req = context.getHttpServletRequest();
        String contentType = req.getHeader(ACCEPT);
        if (APPLICATION_XML.equals(contentType)) {
            return Response
                    .created(new URI(uriString))
                    .entity(new JAXBElement<OrganizationRole>(ORG_ROLE_QNAME,
                            OrganizationRole.class, null, createdOrgRole))
                    .build(); // 201
        } else {
            return Response.created(new URI(uriString)).entity(createdOrgRole)
                    .build(); // code 201
        }
    }

    /**
     * This method is used to update a OrganizationRole.
     * 
     * @param type
     *            OrganizationRole type
     * @param id
     *            OrganizationRoleId
     * @param orgRole
     *            OrganizationRole
     * @return Response-OK(containing updated OrganizationRole) if request is
     *         processed successfully otherwise Response-400, Response-404 or
     *         Response-500.
     */
    @PUT
    @Path("/role/{type}/{id}")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    @Consumes({ APPLICATION_XML, APPLICATION_JSON })
    public Response updateOrganizationRole(@PathParam("type") String type,
            @PathParam("id") long id, OrganizationRole orgRole) {
        OrganizationRole upOrgRole = null;
        try {
            // check if the DB "Ids" are same
            if (orgRole.getId() != null) {
                boolean isSame = PoWSUtil.isIdSame(id, orgRole.getId());
                if (!isSame) {
                    return Response.status(Status.BAD_REQUEST)
                            .entity("URL & Payload OrgRoleIds are not same.")
                            .type(TXT_PLAIN).build(); // code 400
                }
            }

            // check if the orgrole 'type' are same
            if (!PoWSUtil.isRoleTypeSame(type, orgRole)) {
                return Response.status(Status.BAD_REQUEST)
                        .entity("URL & Payload OrgRole type are not same.")
                        .type(TXT_PLAIN).build(); // code 400
            }

            // use the "Id" present in the URL
            orgRole.setId(id);

            // call the service to update the organization role
            upOrgRole = orgServImpl.updateOrganizationRole(orgRole);
        } catch (EntityNotFoundException e) {
            return Response.status(Status.NOT_FOUND).entity(ORG_ROLE_NOT_FOUND)
                    .type(TXT_PLAIN).build(); // 404
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // 500
        }

        // build & return the response 200
        return generateUpdateOrganizationRoleResponse(upOrgRole);
    }

    /**
     * This method is used to generate the response for
     * createOrganizationRole().
     * 
     * @param upOrgRole
     *            updated organization role.
     */
    private Response generateUpdateOrganizationRoleResponse(
            OrganizationRole upOrgRole) {
        HttpServletRequest req = context.getHttpServletRequest();
        String contentType = req.getHeader(ACCEPT);
        if (APPLICATION_XML.equals(contentType)) {
            return Response.ok(
                    new JAXBElement<OrganizationRole>(ORG_ROLE_QNAME,
                            OrganizationRole.class, null, upOrgRole)).build(); // 200
        } else {
            return Response.ok(upOrgRole).build(); // code 200
        }
    }

    /**
     * This method is used to change the OrganizationRole status.
     * 
     * @param type
     *            OrganizationRole type
     * @param id
     *            OrganizationRole id
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
    public Response changeOrganizationRoleStatus(
            @PathParam("type") String type, @PathParam("id") long id,
            String status) {
        OrganizationRole upOrgRole = null;
        try {
            // get the corresponding EntityStatus to be passed
            EntityStatus etyStatus = PoWSUtil.getEntityStatus(status);

            // call the service to change the OrganizationRole status
            upOrgRole = orgServImpl.changeOrganizationRoleStatus(
                    PoWSUtil.getOrgRoleClass(type), id, etyStatus);
        } catch (EntityNotFoundException e) {
            return Response.status(Status.NOT_FOUND).entity(ORG_ROLE_NOT_FOUND)
                    .type(TXT_PLAIN).build(); // code 404
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // 500
        }

        // build & return the response 200
        return Response.ok(upOrgRole.getStatus().value()).build(); // code 200
    }

    /**
     * This method is used to get the OrganizationRole for given orgId.
     * 
     * @param id
     *            orgId
     * @return Response-OK(containing OrganizationRole List)if request is
     *         processed successfully otherwise Response-404 or Response-500.
     */
    @GET
    @Path("/organization/{id}/roles")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    public Response getOrganizationRolesByOrgId(@PathParam("id") long id) {
        List<OrganizationRole> retorgRoles = null;
        try {
            retorgRoles = orgServImpl.getOrganizationRolesByOrgId(id);
        } catch (EntityNotFoundException e) {
            // 404 if organization not found
            return Response.status(Status.NOT_FOUND).entity(ORG_NOT_FOUND)
                    .type(TXT_PLAIN).build(); // 404
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // 500
        }

        OrganizationRoleList orgRoleList = new OrganizationRoleList();
        orgRoleList.getOrganizationRole().addAll(retorgRoles);
        // build & return the response 200
        return Response.ok(orgRoleList).build(); // code 200

    }

    /**
     * This method is used to get the OrganizationRoles for the given CtepId.
     * 
     * @param id
     *            ctepId
     * @return Response-OK(containing OrganizationRole List)if request is
     *         processed successfully otherwise Response-404 or Response-500.
     */
    @GET
    @Path("/organization/ctep/{id}")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    public Response getOrganizationRolesByCtepId(@PathParam("id") String id) {
        List<OrganizationRole> retOrgRoles = null;
        try {
            retOrgRoles = orgServImpl.getOrganizationRolesByCtepId(id);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // code 500
        }

        if (CollectionUtils.isEmpty(retOrgRoles)) {
            // 404-caller implies that orgRole(s) with given CTEP ID exists
            return Response.status(Status.NOT_FOUND).entity(ORG_ROLE_NOT_FOUND)
                    .type(TXT_PLAIN).build(); // code 404
        } else {
            OrganizationRoleList orgRolList = new OrganizationRoleList();
            orgRolList.getOrganizationRole().addAll(retOrgRoles);
            // build & return the response
            return Response.ok(orgRolList).build(); // code 200
        }
    }

    /**
     * This method is used to get the OrganizationRole for given roleId.
     * 
     * @param type
     *            OrganizationRole type
     * @param id
     *            OrganizationRole "db" id
     * @return Response-OK(containing OrganizationRole)if request is processed
     *         successfully otherwise Response-404 or Response-500.
     */
    @SuppressWarnings("unchecked")
    @GET
    @Path("/role/{type}/{id}")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    public Response getOrganizationRoleById(@PathParam("type") String type,
            @PathParam("id") long id) {
        OrganizationRole retOrgRole = null;
        try {
            // call the service to get the organization role
            retOrgRole = orgServImpl.getOrganizationRoleById(
                    PoWSUtil.getOrgRoleClass(type), id);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // code 500
        }

        if (retOrgRole == null) {
            // 404-caller implies that a org role with given ID exists
            return Response.status(Status.NOT_FOUND).entity(ORG_ROLE_NOT_FOUND)
                    .type(TXT_PLAIN).build(); // code 404
        } else {
            // build & return the response 200
            return generateGetOrganizationRoleResponse(retOrgRole);
        }
    }

    private Response generateGetOrganizationRoleResponse(
            OrganizationRole retOrgRole) {
        HttpServletRequest req = context.getHttpServletRequest();
        String contentType = req.getHeader(ACCEPT);
        if (APPLICATION_XML.equals(contentType)) {
            return Response.ok(
                    new JAXBElement<OrganizationRole>(ORG_ROLE_QNAME,
                            OrganizationRole.class, null, retOrgRole)).build(); // 200
        } else {
            return Response.ok(retOrgRole).build(); // code 200
        }
    }

    /**
     * This method is used to populate the OrganizationSearchCriteria.
     * 
     * @throws UnsupportedEncodingException
     */
    private OrganizationSearchCriteria getOrganizationSearchCriteria()
            throws UnsupportedEncodingException {
        OrganizationSearchCriteria osc = new OrganizationSearchCriteria();
        HttpServletRequest req = context.getHttpServletRequest();
        req.setCharacterEncoding("utf-8");

        // populate Org Specific Search Criteria fields
        populateOrgSearchCriteria(osc, req);

        // populate base search criteria fields
        PoWSUtil.populateBaseSearchCriteria(osc, req);

        return osc;
    }

    /**
     * This method is used to populate Org specific search criteria fields.
     * 
     * @param osc
     *            OSC to be populated
     * @param req
     *            HttpRequest
     */
    private void populateOrgSearchCriteria(OrganizationSearchCriteria osc,
            HttpServletRequest req) {
        
        populateIdFields(osc, req);
        
        populateNameStatus(osc, req);

        populateHasFields(osc, req);
        
        boolean searchAliases = false;
        if (req.getParameter("searchAliases") != null) {
            searchAliases = Boolean.valueOf(req
                    .getParameter("searchAliases"));            
            osc.setSearchAliases(searchAliases);
        }
    }
    
    private void populateIdFields(OrganizationSearchCriteria osc,
            HttpServletRequest req) {
        
        String ctepID = req.getParameter("ctepID");
        if (StringUtils.isNotBlank(ctepID)) {
            osc.setCtepID(ctepID);
        }

        long id = 0;
        if (req.getParameter("id") != null) {
            id = Long.valueOf(req.getParameter("id"));
            osc.setId(id);
        }   
    }
    
    private void populateNameStatus(OrganizationSearchCriteria osc,
            HttpServletRequest req) {
        
        EntityStatus etyStatus = null;
        if (StringUtils.isNotBlank(req.getParameter("statusCode"))) {
            etyStatus = PoWSUtil
                    .getEntityStatus(req.getParameter("statusCode"));
            osc.setStatusCode(etyStatus);
        }

        String organizationName = req.getParameter("organizationName");
        if (StringUtils.isNotBlank(organizationName)) {
            osc.setOrganizationName(organizationName);
        }

        String familyName = req.getParameter("familyName");
        if (StringUtils.isNotBlank(familyName)) {
            osc.setFamilyName(familyName);
        }
    }
    
    private void populateHasFields(OrganizationSearchCriteria osc,
            HttpServletRequest req) {

        boolean hasChangeRequest = false;
        if (req.getParameter("hasChangeRequest") != null) {
            hasChangeRequest = Boolean.valueOf(req
                    .getParameter("hasChangeRequest"));
            osc.setHasChangeRequest(hasChangeRequest);
        }
        boolean hasPendingHcfRoles = false;
        if (req.getParameter("hasPendingHcfRoles") != null) {
            hasPendingHcfRoles = Boolean.valueOf(req
                    .getParameter("hasPendingHcfRoles"));
            osc.setHasPendingHcfRoles(hasPendingHcfRoles);
        }
        boolean hasPendingRoRoles = false;
        if (req.getParameter("hasPendingRoRoles") != null) {
            hasPendingRoRoles = Boolean.valueOf(req
                    .getParameter("hasPendingRoRoles"));
            osc.setHasPendingRoRoles(hasPendingRoRoles);
        }        
    }

}