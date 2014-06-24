package gov.nih.nci.po.webservices.service.bridg.soap;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.po.faults.EntityValidationFault;
import gov.nih.nci.coppa.po.faults.NullifiedEntityFault;
import gov.nih.nci.coppa.po.faults.TooManyResultsFault;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.enterpriseservices.entities.person.CreateRequest;
import gov.nih.nci.enterpriseservices.entities.person.CreateResponse;
import gov.nih.nci.enterpriseservices.entities.person.GetByIdRequest;
import gov.nih.nci.enterpriseservices.entities.person.GetByIdResponse;
import gov.nih.nci.enterpriseservices.entities.person.QueryRequest;
import gov.nih.nci.enterpriseservices.entities.person.QueryResponse;
import gov.nih.nci.enterpriseservices.entities.person.UpdateRequest;
import gov.nih.nci.enterpriseservices.entities.person.UpdateResponse;
import gov.nih.nci.enterpriseservices.entities.person.UpdateStatusRequest;
import gov.nih.nci.enterpriseservices.entities.person.UpdateStatusResponse;
import gov.nih.nci.enterpriseservices.entities.person.ValidateRequest;
import gov.nih.nci.enterpriseservices.entities.person.ValidateResponse;
import gov.nih.nci.iso21090.extensions.Cd;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.webservices.convert.bridg.FaultConverter;
import gov.nih.nci.po.webservices.service.bridg.EntityService;
import gov.nih.nci.po.webservices.service.bridg.soap.person.EntityValidationFaultFaultMessage;
import gov.nih.nci.po.webservices.service.bridg.soap.person.NullifiedEntityFaultFaultMessage;
import gov.nih.nci.po.webservices.service.bridg.soap.person.PersonPortType;
import gov.nih.nci.po.webservices.service.bridg.soap.person.TooManyResultsFaultFaultMessage;
import gov.nih.nci.services.entity.NullifiedEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@WebService(serviceName = "PersonService",
        portName = "PersonPort",
        wsdlLocation = "/gov/nih/nci/po/webservices/bridg/Person.wsdl",
        targetNamespace = "http://enterpriseservices.nci.nih.gov/entities/Person",
        endpointInterface =
                "gov.nih.nci.po.webservices.service.bridg.soap.person.PersonPortType"
)
@Service("personBridgSoapEndpointImpl")
public class PersonPortTypeImpl implements PersonPortType {

   @Autowired
   @Qualifier("personBridgService")
   private EntityService<Person> service;


    @Override
    public QueryResponse query(QueryRequest request) throws TooManyResultsFaultFaultMessage {
        List<Person> retrieved = null;
        try {
            QueryRequest.LimitOffset offsetWrapper = request.getLimitOffset();
            if (offsetWrapper == null) {
                request.setLimitOffset(new QueryRequest.LimitOffset());
                request.getLimitOffset().setLimitOffset(new LimitOffset());
                request.getLimitOffset().getLimitOffset().setOffset(0);
                request.getLimitOffset().getLimitOffset().setLimit(-1);
            }
            retrieved = service.query(request.getPerson().getPerson(), request.getLimitOffset().getLimitOffset());
        } catch (TooManyResultsException e) {
            TooManyResultsFault fault = FaultConverter.toFault(new TooManyResultsFault(), e);
            throw new TooManyResultsFaultFaultMessage(e.getMessage(), fault, e);
        }
        return new QueryResponse().withPerson(retrieved);
    }

    @Override
    public UpdateResponse update(UpdateRequest request) throws EntityValidationFaultFaultMessage {

        try {
            service.update(request.getPerson().getPerson());
        } catch (EntityValidationException e) {
            EntityValidationFault fault = FaultConverter.toFault(new EntityValidationFault(), e);
            throw new EntityValidationFaultFaultMessage(e.getMessage(), fault, e);
        }

        return new UpdateResponse();
    }

    @Override
    public GetByIdResponse getById(GetByIdRequest request) throws NullifiedEntityFaultFaultMessage {

        Id id = request.getId().getId();
        Person retrieved = null;
        try {
            retrieved = service.getById(id);
        } catch (NullifiedEntityException e) {
            NullifiedEntityFault fault = FaultConverter.toFault(new NullifiedEntityFault(), e);
            throw new NullifiedEntityFaultFaultMessage(e.getMessage(), fault, e);
        }

        return new GetByIdResponse().withPerson(retrieved);
    }

    @Override
    public ValidateResponse validate(ValidateRequest request) {
        StringMap errors = service.validate(request.getPerson().getPerson());
        return new ValidateResponse().withStringMap(errors);
    }

    @Override
    public UpdateStatusResponse updateStatus(UpdateStatusRequest request) throws EntityValidationFaultFaultMessage {

        Id targetId = request.getTargetId().getId();
        Cd newStatus = request.getStatusCode().getCd();

        try {
            service.updateStatus(targetId, newStatus);
        } catch (EntityValidationException e) {
            EntityValidationFault fault = FaultConverter.toFault(new EntityValidationFault(), e);
            throw new EntityValidationFaultFaultMessage(e.getMessage(), fault, e);
        }

        return new UpdateStatusResponse();
    }

    @Override
    public CreateResponse create(CreateRequest request) throws EntityValidationFaultFaultMessage {
        Person person = request.getPerson().getPerson();

        Id id = null;

        try {
            id = service.create(person);
        } catch (EntityValidationException e) {
            EntityValidationFault fault = FaultConverter.toFault(new EntityValidationFault(), e);
            throw new EntityValidationFaultFaultMessage(e.getMessage(), fault, e);
        }

        return new CreateResponse().withId(id);
    }
}
