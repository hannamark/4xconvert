package gov.nih.nci.po.webservices.service.bridg.soap;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.po.faults.EntityValidationFault;
import gov.nih.nci.coppa.po.faults.NullifiedEntityFault;
import gov.nih.nci.coppa.po.faults.TooManyResultsFault;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.enterpriseservices.entities.organization.CreateRequest;
import gov.nih.nci.enterpriseservices.entities.organization.CreateResponse;
import gov.nih.nci.enterpriseservices.entities.organization.GetByIdRequest;
import gov.nih.nci.enterpriseservices.entities.organization.GetByIdResponse;
import gov.nih.nci.enterpriseservices.entities.organization.QueryRequest;
import gov.nih.nci.enterpriseservices.entities.organization.QueryResponse;
import gov.nih.nci.enterpriseservices.entities.organization.UpdateRequest;
import gov.nih.nci.enterpriseservices.entities.organization.UpdateResponse;
import gov.nih.nci.enterpriseservices.entities.organization.UpdateStatusRequest;
import gov.nih.nci.enterpriseservices.entities.organization.UpdateStatusResponse;
import gov.nih.nci.enterpriseservices.entities.organization.ValidateRequest;
import gov.nih.nci.enterpriseservices.entities.organization.ValidateResponse;
import gov.nih.nci.iso21090.extensions.Cd;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.webservices.convert.bridg.FaultConverter;
import gov.nih.nci.po.webservices.service.bridg.EntityService;
import gov.nih.nci.po.webservices.service.bridg.soap.organization.EntityValidationFaultFaultMessage;
import gov.nih.nci.po.webservices.service.bridg.soap.organization.NullifiedEntityFaultFaultMessage;
import gov.nih.nci.po.webservices.service.bridg.soap.organization.OrganizationPortType;
import gov.nih.nci.po.webservices.service.bridg.soap.organization.TooManyResultsFaultFaultMessage;
import gov.nih.nci.services.entity.NullifiedEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@WebService(serviceName = "OrganizationService",
        portName = "OrganizationPort",
        wsdlLocation = "/gov/nih/nci/po/webservices/bridg/Organization.wsdl",
        targetNamespace = "http://enterpriseservices.nci.nih.gov/entities/Organization",
        endpointInterface =
                "gov.nih.nci.po.webservices.service.bridg.soap.organization.OrganizationPortType"
)
@Service("organizationBridgSoapEndpointImpl")
public class OrganizationPortTypeImpl implements OrganizationPortType {

    @Autowired
    @Qualifier("organizationBridgService")
    private EntityService<Organization> service;

    @Override
    public UpdateResponse update(UpdateRequest request) throws EntityValidationFaultFaultMessage {
        try {
            service.update(request.getOrganization().getOrganization());
        } catch (EntityValidationException e) {
            EntityValidationFault fault = FaultConverter.toFault(new EntityValidationFault(), e);
            throw new EntityValidationFaultFaultMessage(e.getMessage(), fault, e);
        }

        return new UpdateResponse();
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
    public QueryResponse query(QueryRequest request) throws TooManyResultsFaultFaultMessage {
        List<Organization> retrieved = null;
        try {
            QueryRequest.LimitOffset offsetWrapper = request.getLimitOffset();
            if (offsetWrapper == null) {
                request.setLimitOffset(new QueryRequest.LimitOffset());
                request.getLimitOffset().setLimitOffset(new LimitOffset());
                request.getLimitOffset().getLimitOffset().setOffset(0);
                request.getLimitOffset().getLimitOffset().setLimit(-1);
            }
            retrieved = service.query(
                    request.getOrganization().getOrganization(),
                    request.getLimitOffset().getLimitOffset()
            );
        } catch (TooManyResultsException e) {
            TooManyResultsFault fault = FaultConverter.toFault(new TooManyResultsFault(), e);
            throw new TooManyResultsFaultFaultMessage(e.getMessage(), fault, e);
        }
        return new QueryResponse().withOrganization(retrieved);
    }

    @Override
    public GetByIdResponse getById(GetByIdRequest request) throws NullifiedEntityFaultFaultMessage {
        Id id = request.getId().getId();
        Organization retrieved = null;
        try {
            retrieved = service.getById(id);
        } catch (NullifiedEntityException e) {
            NullifiedEntityFault fault = FaultConverter.toFault(new NullifiedEntityFault(), e);
            throw new NullifiedEntityFaultFaultMessage(e.getMessage(), fault, e);
        }

        return new GetByIdResponse().withOrganization(retrieved);
    }

    @Override
    public CreateResponse create(CreateRequest request) throws EntityValidationFaultFaultMessage {
        Organization organization = request.getOrganization().getOrganization();

        Id id = null;

        try {
            id = service.create(organization);
        } catch (EntityValidationException e) {
            EntityValidationFault fault = FaultConverter.toFault(new EntityValidationFault(), e);
            throw new EntityValidationFaultFaultMessage(e.getMessage(), fault, e);
        }

        return new CreateResponse().withId(id);
    }

    @Override
    public ValidateResponse validate(ValidateRequest request) {
        StringMap errors = service.validate(request.getOrganization().getOrganization());
        return new ValidateResponse().withStringMap(errors);
    }
}
