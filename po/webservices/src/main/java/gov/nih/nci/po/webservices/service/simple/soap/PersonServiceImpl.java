package gov.nih.nci.po.webservices.service.simple.soap;

import gov.nih.nci.po.webservices.Constants;
import gov.nih.nci.po.webservices.service.simple.soap.person.ChangePersonRoleStatusRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.ChangePersonRoleStatusResponse;
import gov.nih.nci.po.webservices.service.simple.soap.person.ChangePersonStatusRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.ChangePersonStatusResponse;
import gov.nih.nci.po.webservices.service.simple.soap.person.CreatePersonRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.CreatePersonResponse;
import gov.nih.nci.po.webservices.service.simple.soap.person.CreatePersonRoleRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.CreatePersonRoleResponse;
import gov.nih.nci.po.webservices.service.simple.soap.person.GetPersonRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.GetPersonResponse;
import gov.nih.nci.po.webservices.service.simple.soap.person.GetPersonRoleByIdRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.GetPersonRoleByIdResponse;
import gov.nih.nci.po.webservices.service.simple.soap.person.GetPersonRolesByPersonIdRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.GetPersonRolesByPersonIdResponse;
import gov.nih.nci.po.webservices.service.simple.soap.person.GetPersonsByCtepIdRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.GetPersonsByCtepIdResponse;
import gov.nih.nci.po.webservices.service.simple.soap.person.PersonService;
import gov.nih.nci.po.webservices.service.simple.soap.person.SearchPersonsRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.SearchPersonsResponse;
import gov.nih.nci.po.webservices.service.simple.soap.person.UpdatePersonRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.UpdatePersonResponse;
import gov.nih.nci.po.webservices.service.simple.soap.person.UpdatePersonRoleRequest;
import gov.nih.nci.po.webservices.service.simple.soap.person.UpdatePersonRoleResponse;
import gov.nih.nci.po.webservices.types.Person;
import gov.nih.nci.po.webservices.types.PersonRole;
import gov.nih.nci.po.webservices.types.PersonSearchCriteria;
import gov.nih.nci.po.webservices.types.PersonSearchResult;
import gov.nih.nci.po.webservices.util.PoWSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

/**
 * This is implementation class for PersonService(SOAP Version).
 * 
 * @author Rohit Gupta
 * 
 */
@WebService(serviceName = "PersonService", portName = "PersonServicePort", wsdlLocation = "/PersonService.wsdl", 
targetNamespace = "http://soap.simple.service.webservices.po.nci.nih.gov/person/", 
endpointInterface = "gov.nih.nci.po.webservices.service.simple.soap.person.PersonService")
@Service("personServiceSimpleSoapEndpoint")
public class PersonServiceImpl implements PersonService {

    @Autowired
    @Qualifier("personServiceProxy")
    private gov.nih.nci.po.webservices.service.simple.PersonService perServImpl;

    @Override
    public CreatePersonResponse createPerson(CreatePersonRequest cpRequest) {

        Person retPerson = perServImpl.createPerson(cpRequest.getPerson());

        CreatePersonResponse cpResponse = new CreatePersonResponse();
        cpResponse.setPerson(retPerson);
        return cpResponse;
    }

    @Override
    public UpdatePersonResponse updatePerson(UpdatePersonRequest upRequest) {

        Person retPerson = perServImpl.updatePerson(upRequest.getPerson());

        UpdatePersonResponse upResponse = new UpdatePersonResponse();
        upResponse.setPerson(retPerson);
        return upResponse;
    }

    @Override
    public ChangePersonStatusResponse changePersonStatus(
            ChangePersonStatusRequest cpsRequest) {

        Person retPerson = perServImpl.changePersonStatus(
                cpsRequest.getPersonID(), cpsRequest.getStatus());

        ChangePersonStatusResponse cpsResponse = new ChangePersonStatusResponse();
        cpsResponse.setPerson(retPerson);
        return cpsResponse;
    }

    @Override
    public GetPersonResponse getPerson(GetPersonRequest gpRequest) {

        Person retPerson = perServImpl.getPerson(gpRequest.getPersonID());

        GetPersonResponse gpResponse = new GetPersonResponse();
        gpResponse.setPerson(retPerson);
        return gpResponse;
    }

    @Override
    public GetPersonsByCtepIdResponse getPersonsByCtepId(GetPersonsByCtepIdRequest gpRequest) {

        List<Person> retPerList = perServImpl.getPersonsByCtepId(gpRequest.getCtepID());

        GetPersonsByCtepIdResponse gpResponse = new GetPersonsByCtepIdResponse();
        gpResponse.getPersonList().addAll(retPerList);
        return gpResponse;
    }

    @Override
    public SearchPersonsResponse searchPersons(SearchPersonsRequest spRequest) {

        PersonSearchCriteria psCriteria = spRequest.getPersonSearchCriteria();
        if (psCriteria.getOffset() == null) {
            psCriteria.setOffset(Constants.DEFAULT_OFFSET);
        }
        if (psCriteria.getLimit() == null) {
            psCriteria.setLimit(Constants.DEFAULT_SEARCH_LIMIT);
        }
        List<PersonSearchResult> psrList = perServImpl
                .searchPersons(psCriteria);

        SearchPersonsResponse spResponse = new SearchPersonsResponse();
        spResponse.getPersonSearchResultList().addAll(psrList);
        return spResponse;
    }

    @Override
    public CreatePersonRoleResponse createPersonRole(
            CreatePersonRoleRequest cprRequest) {

        PersonRole retPerRole = perServImpl.createPersonRole(cprRequest
                .getPersonRole());

        CreatePersonRoleResponse cprResponse = new CreatePersonRoleResponse();
        cprResponse.setPersonRole(retPerRole);
        return cprResponse;
    }

    @Override
    public UpdatePersonRoleResponse updatePersonRole(
            UpdatePersonRoleRequest uprRequest) {
        PersonRole retPerRole = perServImpl.updatePersonRole(uprRequest
                .getPersonRole());

        UpdatePersonRoleResponse uprResponse = new UpdatePersonRoleResponse();
        uprResponse.setPersonRole(retPerRole);
        return uprResponse;
    }

    @Override
    public GetPersonRolesByPersonIdResponse getPersonRolesByPersonId(
            GetPersonRolesByPersonIdRequest gprRequest) {
        List<PersonRole> retPerRoleList = perServImpl.getPersonRolesByPersonId(gprRequest
                .getPersonID());

        GetPersonRolesByPersonIdResponse gprResponse = new GetPersonRolesByPersonIdResponse();
        gprResponse.getPersonRoleList().addAll(retPerRoleList);
        return gprResponse;
    }

    @SuppressWarnings("unchecked")
    @Override
    public GetPersonRoleByIdResponse getPersonRoleById(GetPersonRoleByIdRequest gprRequest) {

        PersonRole retPerRole = perServImpl.getPersonRoleById(
                PoWSUtil.getPersonRoleClass(gprRequest.getRoleType().value()),
                gprRequest.getPersonRoleID());

        GetPersonRoleByIdResponse gprResponse = new GetPersonRoleByIdResponse();
        gprResponse.setPersonRole(retPerRole);
        return gprResponse;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ChangePersonRoleStatusResponse changePersonRoleStatus(
            ChangePersonRoleStatusRequest cprsRequest) {

        PersonRole retPerRole = perServImpl.changePersonRoleStatus(
                PoWSUtil.getPersonRoleClass(cprsRequest.getRoleType().value()),
                cprsRequest.getPersonRoleID(), cprsRequest.getStatus());

        ChangePersonRoleStatusResponse cprsResponse = new ChangePersonRoleStatusResponse();
        cprsResponse.setPersonRole(retPerRole);
        return cprsResponse;
    }

   

}
