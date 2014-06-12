package gov.nih.nci.po.webservices.service.simple.soap;

import gov.nih.nci.po.webservices.service.simple.soap.family.FamilyService;
import gov.nih.nci.po.webservices.service.simple.soap.family.GetFamilyMemberRelationshipsByFamilyIdRequest;
import gov.nih.nci.po.webservices.service.simple.soap.family.GetFamilyMemberRelationshipsByFamilyIdResponse;
import gov.nih.nci.po.webservices.service.simple.soap.family.SearchFamiliesByNameRequest;
import gov.nih.nci.po.webservices.service.simple.soap.family.SearchFamiliesByNameResponse;
import gov.nih.nci.po.webservices.service.simple.soap.family.SearchFamiliesByOrgIdRequest;
import gov.nih.nci.po.webservices.service.simple.soap.family.SearchFamiliesByOrgIdResponse;
import gov.nih.nci.po.webservices.types.Family;
import gov.nih.nci.po.webservices.types.FamilyMemberRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.jws.WebService;
import java.util.List;

/**
 * This is implementation class for FamilyService(SOAP Version).
 * 
 * @author Rohit Gupta
 * 
 */
@WebService(serviceName = "FamilyService", portName = "FamilyServicePort", wsdlLocation = "/FamilyService.wsdl", 
targetNamespace = "http://soap.simple.service.webservices.po.nci.nih.gov/family/", 
endpointInterface = "gov.nih.nci.po.webservices.service.simple.soap.family.FamilyService")
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    @Qualifier("familyServiceProxy")
    private gov.nih.nci.po.webservices.service.simple.FamilyService famServImpl;

    @Override
    public SearchFamiliesByNameResponse searchFamiliesByName(
            SearchFamiliesByNameRequest request) {
        // call the service method
        List<Family> familyList = famServImpl.searchFamiliesByName(request
                .getName());

        // prepare & return the response
        SearchFamiliesByNameResponse response = new SearchFamiliesByNameResponse();
        response.getFamilyList().addAll(familyList);
        return response;
    }

    @Override
    public SearchFamiliesByOrgIdResponse searchFamiliesByOrgId(
            SearchFamiliesByOrgIdRequest request) {
        // call the service method
        List<Family> familyList = famServImpl.searchFamiliesByOrgId(request
                .getOrganizationId());

        // prepare & return the response
        SearchFamiliesByOrgIdResponse response = new SearchFamiliesByOrgIdResponse();
        response.getFamilyList().addAll(familyList);
        return response;
    }

    @Override
    public GetFamilyMemberRelationshipsByFamilyIdResponse getFamilyMemberRelationshipsByFamilyId(
            GetFamilyMemberRelationshipsByFamilyIdRequest request) {
        // call the service method
        List<FamilyMemberRelationship> fmrList = famServImpl
                .getFamilyMemberRelationshipsByFamilyId(request.getFamilyId());

        // prepare & return the response
        GetFamilyMemberRelationshipsByFamilyIdResponse response = new GetFamilyMemberRelationshipsByFamilyIdResponse();
        response.getFamilyMemberRelationshipList().addAll(fmrList);
        return response;
    }

}
