package gov.nih.nci.po.webservices.service.bridg.soap;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.IdentifiedOrganization;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.po.faults.EntityValidationFault;
import gov.nih.nci.coppa.po.faults.NullifiedRoleFault;
import gov.nih.nci.coppa.po.faults.TooManyResultsFault;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.enterpriseservices.structuralroles.identifiedorganization.CreateRequest;
import gov.nih.nci.enterpriseservices.structuralroles.identifiedorganization.CreateResponse;
import gov.nih.nci.enterpriseservices.structuralroles.identifiedorganization.GetByIdRequest;
import gov.nih.nci.enterpriseservices.structuralroles.identifiedorganization.GetByIdResponse;
import gov.nih.nci.enterpriseservices.structuralroles.identifiedorganization.GetByIdsRequest;
import gov.nih.nci.enterpriseservices.structuralroles.identifiedorganization.GetByIdsResponse;
import gov.nih.nci.enterpriseservices.structuralroles.identifiedorganization.GetByPlayerIdsRequest;
import gov.nih.nci.enterpriseservices.structuralroles.identifiedorganization.GetByPlayerIdsResponse;
import gov.nih.nci.enterpriseservices.structuralroles.identifiedorganization.QueryRequest;
import gov.nih.nci.enterpriseservices.structuralroles.identifiedorganization.QueryResponse;
import gov.nih.nci.enterpriseservices.structuralroles.identifiedorganization.UpdateRequest;
import gov.nih.nci.enterpriseservices.structuralroles.identifiedorganization.UpdateResponse;
import gov.nih.nci.enterpriseservices.structuralroles.identifiedorganization.UpdateStatusRequest;
import gov.nih.nci.enterpriseservices.structuralroles.identifiedorganization.UpdateStatusResponse;
import gov.nih.nci.enterpriseservices.structuralroles.identifiedorganization.ValidateRequest;
import gov.nih.nci.enterpriseservices.structuralroles.identifiedorganization.ValidateResponse;
import gov.nih.nci.iso21090.extensions.Cd;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.webservices.convert.bridg.FaultConverter;
import gov.nih.nci.po.webservices.service.bridg.RoleService;
import gov.nih.nci.po.webservices.service.bridg.soap.identifiedorganization.EntityValidationFaultFaultMessage;
import gov.nih.nci.po.webservices.service.bridg.soap.identifiedorganization.IdentifiedOrganizationPortType;
import gov.nih.nci.po.webservices.service.bridg.soap.identifiedorganization.NullifiedRoleFaultFaultMessage;
import gov.nih.nci.po.webservices.service.bridg.soap.identifiedorganization.TooManyResultsFaultFaultMessage;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@WebService(serviceName = "IdentifiedOrganizationService",
        portName = "IdentifiedOrganizationPortTypePort",
        wsdlLocation = "/gov/nih/nci/po/webservices/bridg/IdentifiedOrganization.wsdl",
        targetNamespace = "http://enterpriseservices.nci.nih.gov/structuralroles/IdentifiedOrganization",
        endpointInterface =
                "gov.nih.nci.po.webservices.service.bridg.soap.identifiedorganization.IdentifiedOrganizationPortType"
)
@Service("identifiedOrganizationBridgSoapEndpointImpl")
public class IdentifiedOrganizationPortTypeImpl implements IdentifiedOrganizationPortType {
    @Autowired
    @Qualifier("identifiedOrganizationBridgService")
    private RoleService<IdentifiedOrganization> service;

    @Override
    public CreateResponse create(CreateRequest request) throws EntityValidationFaultFaultMessage {

        IdentifiedOrganization definition = request.getIdentifiedOrganization().getIdentifiedOrganization();
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
        IdentifiedOrganization spec = request.getIdentifiedOrganization().getIdentifiedOrganization();

        List<IdentifiedOrganization> results = null;
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
        response.getIdentifiedOrganization().addAll(results);
        return response;
    }

    @Override
    public UpdateResponse update(UpdateRequest request) throws EntityValidationFaultFaultMessage {
        IdentifiedOrganization definition = request.getIdentifiedOrganization().getIdentifiedOrganization();

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
        IdentifiedOrganization definition = request.getIdentifiedOrganization().getIdentifiedOrganization();

        StringMap results = service.validate(definition);
        ValidateResponse response = new ValidateResponse();
        response.setStringMap(results);
        return response;
    }

    @Override
    public GetByPlayerIdsResponse getByPlayerIds(GetByPlayerIdsRequest request) {
        List<Id> ids = request.getId().getId();

        List<IdentifiedOrganization> results = null;
        results = service.getByPlayerIds(ids);

        GetByPlayerIdsResponse response = new GetByPlayerIdsResponse();
        response.getIdentifiedOrganization().addAll(results);
        return response;
    }

    @Override
    public GetByIdResponse getById(GetByIdRequest request) throws NullifiedRoleFaultFaultMessage {
        Id id = request.getId().getId();

        IdentifiedOrganization result = null;
        try {
            result = service.getById(id);
        } catch (NullifiedRoleException e) {
            NullifiedRoleFault fault = FaultConverter.toFault(new NullifiedRoleFault(), e);
            throw new NullifiedRoleFaultFaultMessage(e.getMessage(), fault, e);
        }
        GetByIdResponse response = new GetByIdResponse();
        response.setIdentifiedOrganization(result);
        return response;
    }

    @Override
    public GetByIdsResponse getByIds(GetByIdsRequest request) throws NullifiedRoleFaultFaultMessage {
        List<Id> ids = request.getId().getId();

        List<IdentifiedOrganization> results = null;
        try {
            results = service.getByIds(ids);
        } catch (NullifiedRoleException e) {
            NullifiedRoleFault fault = FaultConverter.toFault(new NullifiedRoleFault(), e);
            throw new NullifiedRoleFaultFaultMessage(e.getMessage(), fault, e);
        }
        GetByIdsResponse response = new GetByIdsResponse();
        response.getIdentifiedOrganization().addAll(results);
        return response;
    }
}
