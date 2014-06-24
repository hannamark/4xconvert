package gov.nih.nci.po.webservices.service.bridg.soap;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.OrganizationalContact;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.po.faults.EntityValidationFault;
import gov.nih.nci.coppa.po.faults.NullifiedRoleFault;
import gov.nih.nci.coppa.po.faults.TooManyResultsFault;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.enterpriseservices.structuralroles.organizationalcontact.CreateRequest;
import gov.nih.nci.enterpriseservices.structuralroles.organizationalcontact.CreateResponse;
import gov.nih.nci.enterpriseservices.structuralroles.organizationalcontact.GetByIdRequest;
import gov.nih.nci.enterpriseservices.structuralroles.organizationalcontact.GetByIdResponse;
import gov.nih.nci.enterpriseservices.structuralroles.organizationalcontact.GetByIdsRequest;
import gov.nih.nci.enterpriseservices.structuralroles.organizationalcontact.GetByIdsResponse;
import gov.nih.nci.enterpriseservices.structuralroles.organizationalcontact.GetByPlayerIdsRequest;
import gov.nih.nci.enterpriseservices.structuralroles.organizationalcontact.GetByPlayerIdsResponse;
import gov.nih.nci.enterpriseservices.structuralroles.organizationalcontact.QueryRequest;
import gov.nih.nci.enterpriseservices.structuralroles.organizationalcontact.QueryResponse;
import gov.nih.nci.enterpriseservices.structuralroles.organizationalcontact.UpdateRequest;
import gov.nih.nci.enterpriseservices.structuralroles.organizationalcontact.UpdateResponse;
import gov.nih.nci.enterpriseservices.structuralroles.organizationalcontact.UpdateStatusRequest;
import gov.nih.nci.enterpriseservices.structuralroles.organizationalcontact.UpdateStatusResponse;
import gov.nih.nci.enterpriseservices.structuralroles.organizationalcontact.ValidateRequest;
import gov.nih.nci.enterpriseservices.structuralroles.organizationalcontact.ValidateResponse;
import gov.nih.nci.iso21090.extensions.Cd;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.webservices.convert.bridg.FaultConverter;
import gov.nih.nci.po.webservices.service.bridg.RoleService;
import gov.nih.nci.po.webservices.service.bridg.soap.organizationalcontact.EntityValidationFaultFaultMessage;
import gov.nih.nci.po.webservices.service.bridg.soap.organizationalcontact.NullifiedRoleFaultFaultMessage;
import gov.nih.nci.po.webservices.service.bridg.soap.organizationalcontact.OrganizationalContactPortType;
import gov.nih.nci.po.webservices.service.bridg.soap.organizationalcontact.TooManyResultsFaultFaultMessage;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@WebService(serviceName = "OrganizationalContactService",
        portName = "OrganizationalContactPortTypePort",
        wsdlLocation = "/gov/nih/nci/po/webservices/bridg/OrganizationalContact.wsdl",
        targetNamespace = "http://enterpriseservices.nci.nih.gov/structuralroles/OrganizationalContact",
        endpointInterface =
                "gov.nih.nci.po.webservices.service.bridg.soap.organizationalcontact.OrganizationalContactPortType"
)
@Service("organizationalContactBridgSoapEndpointImpl")
public class OrganizationalContactPortTypeImpl implements OrganizationalContactPortType {
    @Autowired
    @Qualifier("organizationalContactBridgService")
    private RoleService<OrganizationalContact> service;

    @Override
    public CreateResponse create(CreateRequest request) throws EntityValidationFaultFaultMessage {

        OrganizationalContact definition = request.getOrganizationalContact().getOrganizationalContact();
        Id id = null;
        try {
            id = service.create(definition);
        } catch (EntityValidationException e) {
            EntityValidationFault fault = FaultConverter.toFault(new EntityValidationFault(), e);
            throw new EntityValidationFaultFaultMessage(e.getMessage(), fault, e);
        }

        CreateResponse response = new CreateResponse();
        response.setId(id);
        return response;
    }

    @Override
    public QueryResponse query(QueryRequest request) throws TooManyResultsFaultFaultMessage {
        OrganizationalContact spec = request.getOrganizationalContact().getOrganizationalContact();

        List<OrganizationalContact> results = null;
        try {
            QueryRequest.LimitOffset offsetWrapper = request.getLimitOffset();
            if (offsetWrapper == null) {
                request.setLimitOffset(new QueryRequest.LimitOffset());
                request.getLimitOffset().setLimitOffset(new LimitOffset());
                request.getLimitOffset().getLimitOffset().setOffset(0);
                request.getLimitOffset().getLimitOffset().setLimit(-1);
            }
            results = service.query(spec, request.getLimitOffset().getLimitOffset());
        } catch (TooManyResultsException e) {
            TooManyResultsFault fault = FaultConverter.toFault(new TooManyResultsFault(), e);
            throw new TooManyResultsFaultFaultMessage(e.getMessage(), fault, e);
        }

        QueryResponse response = new QueryResponse();
        response.getOrganizationalContact().addAll(results);
        return response;
    }

    @Override
    public UpdateResponse update(UpdateRequest request) throws EntityValidationFaultFaultMessage {
        OrganizationalContact definition = request.getOrganizationalContact().getOrganizationalContact();

        try {
            service.update(definition);
        } catch (EntityValidationException e) {
            EntityValidationFault fault = FaultConverter.toFault(new EntityValidationFault(), e);
            throw new EntityValidationFaultFaultMessage(e.getMessage(), fault, e);
        }

        return new UpdateResponse();
    }

    @Override
    public UpdateStatusResponse updateStatus(UpdateStatusRequest request) throws EntityValidationFaultFaultMessage {
        Cd statusCode = request.getStatusCode().getCd();
        Id targetId = request.getTargetId().getId();

        try {
            service.updateStatus(targetId, statusCode);
        } catch (EntityValidationException e) {
            EntityValidationFault fault = FaultConverter.toFault(new EntityValidationFault(), e);
            throw new EntityValidationFaultFaultMessage(e.getMessage(), fault, e);
        }

        return new UpdateStatusResponse();
    }

    @Override
    public ValidateResponse validate(ValidateRequest request) {
        OrganizationalContact definition = request.getOrganizationalContact().getOrganizationalContact();

        StringMap results = service.validate(definition);
        ValidateResponse response = new ValidateResponse();
        response.setStringMap(results);
        return response;
    }

    @Override
    public GetByPlayerIdsResponse getByPlayerIds(GetByPlayerIdsRequest request) {
        List<Id> ids = request.getId().getId();

        List<OrganizationalContact> results = null;
        results = service.getByPlayerIds(ids);

        GetByPlayerIdsResponse response = new GetByPlayerIdsResponse();
        response.getOrganizationalContact().addAll(results);
        return response;
    }

    @Override
    public GetByIdResponse getById(GetByIdRequest request) throws NullifiedRoleFaultFaultMessage {
        Id id = request.getId().getId();

        OrganizationalContact result = null;
        try {
            result = service.getById(id);
        } catch (NullifiedRoleException e) {
            NullifiedRoleFault fault = FaultConverter.toFault(new NullifiedRoleFault(), e);
            throw new NullifiedRoleFaultFaultMessage(e.getMessage(), fault, e);
        }
        GetByIdResponse response = new GetByIdResponse();
        response.setOrganizationalContact(result);
        return response;
    }

    @Override
    public GetByIdsResponse getByIds(GetByIdsRequest request) throws NullifiedRoleFaultFaultMessage {
        List<Id> ids = request.getId().getId();

        List<OrganizationalContact> results = null;
        try {
            results = service.getByIds(ids);
        } catch (NullifiedRoleException e) {
            NullifiedRoleFault fault = FaultConverter.toFault(new NullifiedRoleFault(), e);
            throw new NullifiedRoleFaultFaultMessage(e.getMessage(), fault, e);
        }
        GetByIdsResponse response = new GetByIdsResponse();
        response.getOrganizationalContact().addAll(results);
        return response;
    }
}
