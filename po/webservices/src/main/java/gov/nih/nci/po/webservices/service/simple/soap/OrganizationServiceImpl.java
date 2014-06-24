package gov.nih.nci.po.webservices.service.simple.soap;

import gov.nih.nci.po.webservices.Constants;
import gov.nih.nci.po.webservices.service.simple.soap.organization.ChangeOrganizationRoleStatusRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.ChangeOrganizationRoleStatusResponse;
import gov.nih.nci.po.webservices.service.simple.soap.organization.ChangeOrganizationStatusRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.ChangeOrganizationStatusResponse;
import gov.nih.nci.po.webservices.service.simple.soap.organization.CreateOrganizationRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.CreateOrganizationResponse;
import gov.nih.nci.po.webservices.service.simple.soap.organization.CreateOrganizationRoleRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.CreateOrganizationRoleResponse;
import gov.nih.nci.po.webservices.service.simple.soap.organization.GetOrganizationRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.GetOrganizationResponse;
import gov.nih.nci.po.webservices.service.simple.soap.organization.GetOrganizationRoleByIdRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.GetOrganizationRoleByIdResponse;
import gov.nih.nci.po.webservices.service.simple.soap.organization.GetOrganizationRolesByCtepIdRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.GetOrganizationRolesByCtepIdResponse;
import gov.nih.nci.po.webservices.service.simple.soap.organization.GetOrganizationRolesByOrgIdRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.GetOrganizationRolesByOrgIdResponse;
import gov.nih.nci.po.webservices.service.simple.soap.organization.OrganizationService;
import gov.nih.nci.po.webservices.service.simple.soap.organization.SearchOrganizationsRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.SearchOrganizationsResponse;
import gov.nih.nci.po.webservices.service.simple.soap.organization.UpdateOrganizationRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.UpdateOrganizationResponse;
import gov.nih.nci.po.webservices.service.simple.soap.organization.UpdateOrganizationRoleRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.UpdateOrganizationRoleResponse;
import gov.nih.nci.po.webservices.types.Organization;
import gov.nih.nci.po.webservices.types.OrganizationRole;
import gov.nih.nci.po.webservices.types.OrganizationSearchCriteria;
import gov.nih.nci.po.webservices.types.OrganizationSearchResult;
import gov.nih.nci.po.webservices.util.PoWSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

/**
 * This is implementation class for OrganizationService(SOAP Version).
 * 
 * @author Rohit Gupta
 * 
 */
@WebService(serviceName = "OrganizationService", portName = "OrganizationServicePort", 
wsdlLocation = "/OrganizationService.wsdl", 
targetNamespace = "http://soap.simple.service.webservices.po.nci.nih.gov/organization/", 
endpointInterface = "gov.nih.nci.po.webservices.service.simple.soap.organization.OrganizationService")
@Service("organizationServiceSimpleSoapEndpoint")
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    @Qualifier("orgServiceProxy")
    private gov.nih.nci.po.webservices.service.simple.OrganizationService orgServImpl;

    @Override
    public CreateOrganizationResponse createOrganization(
            CreateOrganizationRequest coRequest) {
        Organization retOrg = orgServImpl.createOrganization(coRequest
                .getOrganization());
        CreateOrganizationResponse coResponse = new CreateOrganizationResponse();
        coResponse.setOrganization(retOrg);
        return coResponse;
    }

    @Override
    public UpdateOrganizationResponse updateOrganization(
            UpdateOrganizationRequest uoRequest) {
        Organization retOrg = orgServImpl.updateOrganization(uoRequest
                .getOrganization());
        UpdateOrganizationResponse uoResponse = new UpdateOrganizationResponse();
        uoResponse.setOrganization(retOrg);
        return uoResponse;
    }

    @Override
    public GetOrganizationResponse getOrganization(
            GetOrganizationRequest goRequest) {
        Organization retOrg = orgServImpl.getOrganization(goRequest
                .getOrganizationID());
        GetOrganizationResponse goResponse = new GetOrganizationResponse();
        goResponse.setOrganization(retOrg);
        return goResponse;
    }

    @Override
    public ChangeOrganizationStatusResponse changeOrganizationStatus(
            ChangeOrganizationStatusRequest cosRequest) {
        Organization retOrg = orgServImpl.changeOrganizationStatus(
                cosRequest.getOrganizationID(), cosRequest.getStatus());
        ChangeOrganizationStatusResponse cosResponse = new ChangeOrganizationStatusResponse();
        cosResponse.setOrganization(retOrg);
        return cosResponse;
    }

    @Override
    public SearchOrganizationsResponse searchOrganizations(
            SearchOrganizationsRequest request) {
        OrganizationSearchCriteria orgSerCriteria = request
                .getOrganizationSearchCriteria();
        if (orgSerCriteria.getOffset() == null) {
            orgSerCriteria.setOffset(Constants.DEFAULT_OFFSET);
        }
        if (orgSerCriteria.getLimit() == null) {
            orgSerCriteria.setLimit(Constants.DEFAULT_SEARCH_LIMIT);
        }
        List<OrganizationSearchResult> osrList = orgServImpl
                .searchOrganizations(request.getOrganizationSearchCriteria());
        SearchOrganizationsResponse response = new SearchOrganizationsResponse();
        response.getOrganizationSearchResultList().addAll(osrList);
        return response;
    }

    @Override
    public CreateOrganizationRoleResponse createOrganizationRole(
            CreateOrganizationRoleRequest corRequest) {
        OrganizationRole retOrgRole = orgServImpl
                .createOrganizationRole(corRequest.getOrganizationRole());
        CreateOrganizationRoleResponse corResponse = new CreateOrganizationRoleResponse();
        corResponse.setOrganizationRole(retOrgRole);
        return corResponse;
    }

    @Override
    public UpdateOrganizationRoleResponse updateOrganizationRole(
            UpdateOrganizationRoleRequest uorRequest) {
        OrganizationRole retOrgRole = orgServImpl
                .updateOrganizationRole(uorRequest.getOrganizationRole());
        UpdateOrganizationRoleResponse uorResponse = new UpdateOrganizationRoleResponse();
        uorResponse.setOrganizationRole(retOrgRole);
        return uorResponse;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ChangeOrganizationRoleStatusResponse changeOrganizationRoleStatus(
            ChangeOrganizationRoleStatusRequest request) {
        OrganizationRole retOrgRole = orgServImpl.changeOrganizationRoleStatus(
                PoWSUtil.getOrgRoleClass(request.getRoleType().value()),
                request.getOrganizationRoleID(), request.getStatus());
        ChangeOrganizationRoleStatusResponse response = new ChangeOrganizationRoleStatusResponse();
        response.setOrganizationRole(retOrgRole);
        return response;
    }

    @Override
    public GetOrganizationRolesByOrgIdResponse getOrganizationRolesByOrgId(
            GetOrganizationRolesByOrgIdRequest gorRequest) {
        List<OrganizationRole> orgRoleList = orgServImpl
                .getOrganizationRolesByOrgId(gorRequest.getOrganizationID());
        GetOrganizationRolesByOrgIdResponse response = new GetOrganizationRolesByOrgIdResponse();
        response.getOrganizationRoleList().addAll(orgRoleList);
        return response;
    }

    @Override
    public GetOrganizationRolesByCtepIdResponse getOrganizationRolesByCtepId(
            GetOrganizationRolesByCtepIdRequest gorRequest) {
        List<OrganizationRole> orgRoleList = orgServImpl
                .getOrganizationRolesByCtepId(gorRequest.getCtepID());
        GetOrganizationRolesByCtepIdResponse response = new GetOrganizationRolesByCtepIdResponse();
        response.getOrganizationRoleList().addAll(orgRoleList);
        return response;
    }

    @SuppressWarnings("unchecked")
    @Override
    public GetOrganizationRoleByIdResponse getOrganizationRoleById(
            GetOrganizationRoleByIdRequest request) {
        OrganizationRole retOrgRole = orgServImpl.getOrganizationRoleById(
                PoWSUtil.getOrgRoleClass(request.getRoleType().value()),
                request.getOrganizationRoleID());
        GetOrganizationRoleByIdResponse response = new GetOrganizationRoleByIdResponse();
        response.setOrganizationRole(retOrgRole);
        return response;
    }

    

}
