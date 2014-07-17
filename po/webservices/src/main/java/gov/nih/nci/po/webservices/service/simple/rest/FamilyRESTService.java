package gov.nih.nci.po.webservices.service.simple.rest;

import gov.nih.nci.po.webservices.service.exception.EntityNotFoundException;
import gov.nih.nci.po.webservices.types.Family;
import gov.nih.nci.po.webservices.types.FamilyList;
import gov.nih.nci.po.webservices.types.FamilyMember;
import gov.nih.nci.po.webservices.types.FamilyMemberRelationship;
import gov.nih.nci.po.webservices.types.FamilyMemberRelationshipList;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
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
 * This is implementation class for FamilyService(REST Version).
 * 
 * @author Rohit Gupta
 * 
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity" })
@Service("familyServiceSimpleRestEndpoint")
public class FamilyRESTService {
    
    private static final QName FAMILY_QNAME = new QName("gov.nih.nci.po.webservices.types", "family");
    private static final QName FAM_MEM_QNAME = new QName("gov.nih.nci.po.webservices.types", "familymember");

    @Autowired
    @Qualifier("familyServiceProxy")
    private gov.nih.nci.po.webservices.service.simple.FamilyService famServImpl;

    @Context
    private MessageContext context;

    private static final String APPLICATION_XML = "application/xml";
    private static final String APPLICATION_JSON = "application/json";
    private static final String TXT_PLAIN = "text/plain";
    private static final String ORG_ID = "organizationId";
    private static final String FAMILY_NOT_FOUND = "Family is not found in the database.";
    private static final String FAM_MEM_NOT_FOUND = "Family Member is not found in the database.";
    private static final String ACCEPT = "Accept";

    /**
     * This method is used to search the families on the basis of FamilyId
     * and/or OrganizationId..
     * 
     * @return Response-OK(containing FamilyList)if request is processed
     *         successfully otherwise Response-404 or Response-500.
     */

    @GET
    @Path("/families")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    public Response searchFamilies() {
        List<Family> famNameList; // result 'by name'
        List<Family> famIdList; // result 'by OrgId'
        HttpServletRequest req = context.getHttpServletRequest();
        try {
            // check if search criteria is passed or not
            boolean isCriteriaPresent = isSearchCriteriaPresent(req);
            if (!isCriteriaPresent) {
                // If not then return 400
                return Response
                        .status(Status.BAD_REQUEST)
                        .entity("Search Criteria (name or/and organizationId) is not specified in the URL.")
                        .type(TXT_PLAIN).build(); // code 400
            }

            famNameList = getFamiliesByName(req); // get Families by Family Name

            famIdList = getFamiliesByOrgId(req); // get Families by OrgId
        } catch (EntityNotFoundException e) {
            return Response.status(Status.NOT_FOUND).entity(e.getMessage())
                    .type(TXT_PLAIN).build(); // 404
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // 500
        }

        FamilyList famList = new FamilyList();
        // check if both criteria passed - used for list population below
        boolean isBothCriteriaPresent = isBothSearchCriteriaPresent(req);
        if (isBothCriteriaPresent) {
            // if both criteria pass then get their common Families
            famList = getCommonFamilyList(famNameList, famIdList);
        } else {
            famList = new FamilyList();
            // add both of them, anyone will have search result
            famList.getFamily().addAll(famNameList);
            famList.getFamily().addAll(famIdList);
        }

        return Response.ok(famList).build(); // code 200
    }
    
    /**
     * This method is used to get the family on the basis of DB Id.
     * 
     * @param id
     *            familyId
     * @return Response-OK(containing Family) if request is processed
     *         successfully otherwise Response-404 or Response-500.
     */
    @GET
    @Path("/family/{id}")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    public Response getFamily(@PathParam("id") long id) {
        Family retFamily = null;
        try {
            // call the service to get the family
            retFamily = famServImpl.getFamily(id);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // code 500
        }

        if (retFamily == null) {
            // 404 because caller implies that a family with given ID exists
            return Response.status(Status.NOT_FOUND).entity(FAMILY_NOT_FOUND)
                    .type(TXT_PLAIN).build(); // code 404
        } else {
            // build & return the response 200
            return generateGetFamilyResponse(retFamily);
        }
    }
    
    /**
     * This method is used to get the family member on the basis of DB Id.
     * 
     * @param id
     *            familyMemberId
     * @return Response-OK(containing FamilyMember) if request is processed
     *         successfully otherwise Response-404 or Response-500.
     */
    @GET
    @Path("/familymember/{id}")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    public Response getFamilyMember(@PathParam("id") long id) {
        FamilyMember retFamMeb = null;
        try {
            // call the service to get the family member
            retFamMeb = famServImpl.getFamilyMember(id);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // code 500
        }

        if (retFamMeb == null) {
            // 404 because caller implies that a family memeber with given ID exists
            return Response.status(Status.NOT_FOUND).entity(FAM_MEM_NOT_FOUND)
                    .type(TXT_PLAIN).build(); // code 404
        } else {
            // build & return the response 200
            return generateGetFamilyMemberResponse(retFamMeb);
        }
    }

    /**
     * This method is used to get the FamilyMemberRelationships on the basis of
     * FamilyId.
     * 
     * @return Response-OK(containing FamilyMemberRelationshipList)if request is
     *         processed successfully otherwise Response-400,, Response-404 or
     *         Response-500.
     */
    @GET
    @Path("/familyMemberRelationships")
    @Produces({ APPLICATION_XML, APPLICATION_JSON })
    public Response getFamilyMemberRelationshipsByFamilyId() {
        HttpServletRequest req = context.getHttpServletRequest();
        if (req.getParameter("familyId") == null) {
            return Response
                    .status(Status.BAD_REQUEST)
                    .entity("Search Criteria (familyId) is not specified in the URL.")
                    .type(TXT_PLAIN).build(); // code 400
        }

        List<FamilyMemberRelationship> retFmrList = null;
        try {
            long familyId = Long.valueOf(req.getParameter("familyId"));
            retFmrList = famServImpl
                    .getFamilyMemberRelationshipsByFamilyId(familyId);
        } catch (EntityNotFoundException e) {
            return Response.status(Status.NOT_FOUND).entity(e.getMessage())
                    .type(TXT_PLAIN).build(); // 404
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).type(TXT_PLAIN).build(); // 500
        }

        FamilyMemberRelationshipList fmrList = new FamilyMemberRelationshipList();
        fmrList.getFamilyMemberRelationship().addAll(retFmrList);
        return Response.ok(fmrList).build(); // code 200
    }

    /**
     * This method is used to check if search criteria is passed or not.
     * 
     * @param req
     *            HttpServletRequest
     * @return true if any criteria passed
     */
    private boolean isSearchCriteriaPresent(HttpServletRequest req) {
        boolean isCriteriaPresent = false;

        if (StringUtils.isNotBlank(req.getParameter("name"))
                || req.getParameter(ORG_ID) != null) {
            isCriteriaPresent = true;
        }

        return isCriteriaPresent;
    }

    /**
     * This method is used to check if both search criteria are passed.
     */
    private boolean isBothSearchCriteriaPresent(HttpServletRequest req) {
        boolean isBothCriteriaPresent = false;

        if (StringUtils.isNotBlank(req.getParameter("name"))
                && req.getParameter(ORG_ID) != null) {
            isBothCriteriaPresent = true;
        }

        return isBothCriteriaPresent;
    }

    /**
     * This method is used to get Families by name.
     */
    private List<Family> getFamiliesByName(HttpServletRequest req) {
        List<Family> famNameList = new ArrayList<Family>(); // result 'by name'

        String name = req.getParameter("name");
        if (StringUtils.isNotBlank(name)) {
            famNameList = famServImpl.searchFamiliesByName(name);
        }

        return famNameList;
    }

    /**
     * This method is used to get Families by OrgId.
     */
    private List<Family> getFamiliesByOrgId(HttpServletRequest req) {
        List<Family> famIdList = new ArrayList<Family>(); // result 'by OrgId'
        long organizationId = 0;
        if (req.getParameter(ORG_ID) != null) {
            organizationId = Long.valueOf(req.getParameter(ORG_ID));
            famIdList = famServImpl.searchFamiliesByOrgId(organizationId);
        }
        return famIdList;
    }

    /**
     * This method is used to get the common Family among the 2 lists.
     * 
     * @param famNameList
     *            - Arraylist having family found using 'family name'
     * @param famIdList
     *            - Arraylist having family found using 'Org Id'
     * @return FamilyList containing common/intersection family.
     */
    @SuppressWarnings({ "PMD.CyclomaticComplexity" })
    private FamilyList getCommonFamilyList(List<Family> famNameList,
            List<Family> famIdList) {
        FamilyList famList = new FamilyList();

        // check if either list is empty
        if (CollectionUtils.isEmpty(famNameList)
                || CollectionUtils.isEmpty(famIdList)) {
            // if yes, then return as no common elements
            return famList;
        }

        // get the smaller list
        List<Family> smallList, bigList;
        if (famNameList.size() < famIdList.size()) {
            smallList = famNameList;
            bigList = famIdList;
        } else {
            smallList = famIdList;
            bigList = famNameList;
        }

        outLoop: for (Family sFam : smallList) {
            for (Family bFam : bigList) {
                if (sFam.getId().longValue() == bFam.getId().longValue()) {
                    famList.getFamily().add(sFam);
                    continue outLoop;
                }
            }
        }

        return famList;
    }
    
    /**
     * This method is used to generate the response for getFamily().
     * 
     * @param retFamily
     *            family returned from the database.
     */
    private Response generateGetFamilyResponse(Family retFamily) {
        HttpServletRequest req = context.getHttpServletRequest();
        String contentType = req.getHeader(ACCEPT);
        if (APPLICATION_XML.equals(contentType)) {
            return Response.ok(
                    new JAXBElement<Family>(FAMILY_QNAME, Family.class, null,
                            retFamily)).build(); // code 200
        } else {
            return Response.ok(retFamily).build(); // code 200
        }
    }
    
    /**
     * This method is used to generate the response for getFamilyMember().
     * 
     * @param retFamMember
     *            familyMember returned from the database.
     */
    private Response generateGetFamilyMemberResponse(FamilyMember retFamMember) {
        HttpServletRequest req = context.getHttpServletRequest();
        String contentType = req.getHeader(ACCEPT);
        if (APPLICATION_XML.equals(contentType)) {
            return Response.ok(
                    new JAXBElement<FamilyMember>(FAM_MEM_QNAME, FamilyMember.class, null,
                            retFamMember)).build(); // code 200
        } else {
            return Response.ok(retFamMember).build(); // code 200
        }
    }
}
