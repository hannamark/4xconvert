package gov.nih.nci.po.webservices.service.bridg.soap;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.HealthCareFacility;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.po.faults.EntityValidationFault;
import gov.nih.nci.coppa.po.faults.NullifiedRoleFault;
import gov.nih.nci.coppa.po.faults.TooManyResultsFault;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.enterpriseservices.structuralroles.healthcarefacility.CreateRequest;
import gov.nih.nci.enterpriseservices.structuralroles.healthcarefacility.CreateResponse;
import gov.nih.nci.enterpriseservices.structuralroles.healthcarefacility.GetByIdRequest;
import gov.nih.nci.enterpriseservices.structuralroles.healthcarefacility.GetByIdResponse;
import gov.nih.nci.enterpriseservices.structuralroles.healthcarefacility.GetByIdsRequest;
import gov.nih.nci.enterpriseservices.structuralroles.healthcarefacility.GetByIdsResponse;
import gov.nih.nci.enterpriseservices.structuralroles.healthcarefacility.GetByPlayerIdsRequest;
import gov.nih.nci.enterpriseservices.structuralroles.healthcarefacility.GetByPlayerIdsResponse;
import gov.nih.nci.enterpriseservices.structuralroles.healthcarefacility.QueryRequest;
import gov.nih.nci.enterpriseservices.structuralroles.healthcarefacility.QueryResponse;
import gov.nih.nci.enterpriseservices.structuralroles.healthcarefacility.UpdateRequest;
import gov.nih.nci.enterpriseservices.structuralroles.healthcarefacility.UpdateResponse;
import gov.nih.nci.enterpriseservices.structuralroles.healthcarefacility.UpdateStatusRequest;
import gov.nih.nci.enterpriseservices.structuralroles.healthcarefacility.UpdateStatusResponse;
import gov.nih.nci.enterpriseservices.structuralroles.healthcarefacility.ValidateRequest;
import gov.nih.nci.enterpriseservices.structuralroles.healthcarefacility.ValidateResponse;
import gov.nih.nci.iso21090.extensions.Cd;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.webservices.convert.bridg.FaultConverter;
import gov.nih.nci.po.webservices.service.bridg.RoleService;
import gov.nih.nci.po.webservices.service.bridg.soap.healthcarefacility.EntityValidationFaultFaultMessage;
import gov.nih.nci.po.webservices.service.bridg.soap.healthcarefacility.HealthCareFacilityPortType;
import gov.nih.nci.po.webservices.service.bridg.soap.healthcarefacility.NullifiedRoleFaultFaultMessage;
import gov.nih.nci.po.webservices.service.bridg.soap.healthcarefacility.TooManyResultsFaultFaultMessage;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@WebService(serviceName = "HealthCareFacilityService",
        portName = "HealthCareFacilityPortTypePort",
        wsdlLocation = "/gov/nih/nci/po/webservices/bridg/HealthCareFacility.wsdl",
        targetNamespace = "http://enterpriseservices.nci.nih.gov/structuralroles/HealthCareFacility",
        endpointInterface =
                "gov.nih.nci.po.webservices.service.bridg.soap.healthcarefacility.HealthCareFacilityPortType"
)
@Service("healthCareFacilityBridgSoapEndpointImpl")
public class HealthCareFacilityPortTypeImpl implements HealthCareFacilityPortType {
    @Autowired
    @Qualifier("healthCareFacilityBridgService")
    private RoleService<HealthCareFacility> service;

    @Override
    public CreateResponse create(CreateRequest request) throws EntityValidationFaultFaultMessage {

        HealthCareFacility definition = request.getHealthCareFacility().getHealthCareFacility();
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
        HealthCareFacility spec = request.getHealthCareFacility().getHealthCareFacility();

        List<HealthCareFacility> results = null;
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
        response.getHealthCareFacility().addAll(results);
        return response;
    }

    @Override
    public UpdateResponse update(UpdateRequest request) throws EntityValidationFaultFaultMessage {
        HealthCareFacility definition = request.getHealthCareFacility().getHealthCareFacility();

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
        HealthCareFacility definition = request.getHealthCareFacility().getHealthCareFacility();

        StringMap results = service.validate(definition);
        ValidateResponse response = new ValidateResponse();
        response.setStringMap(results);
        return response;
    }

    @Override
    public GetByPlayerIdsResponse getByPlayerIds(GetByPlayerIdsRequest request) {
        List<Id> ids = request.getId().getId();

        List<HealthCareFacility> results = null;
        results = service.getByPlayerIds(ids);

        GetByPlayerIdsResponse response = new GetByPlayerIdsResponse();
        response.getHealthCareFacility().addAll(results);
        return response;
    }

    @Override
    public GetByIdResponse getById(GetByIdRequest request) throws NullifiedRoleFaultFaultMessage {
        Id id = request.getId().getId();

        HealthCareFacility result = null;
        try {
            result = service.getById(id);
        } catch (NullifiedRoleException e) {
            NullifiedRoleFault fault = FaultConverter.toFault(new NullifiedRoleFault(), e);
            throw new NullifiedRoleFaultFaultMessage(e.getMessage(), fault, e);
        }
        GetByIdResponse response = new GetByIdResponse();
        response.setHealthCareFacility(result);
        return response;
    }

    @Override
    public GetByIdsResponse getByIds(GetByIdsRequest request) throws NullifiedRoleFaultFaultMessage {
        List<Id> ids = request.getId().getId();

        List<HealthCareFacility> results = null;
        try {
            results = service.getByIds(ids);
        } catch (NullifiedRoleException e) {
            NullifiedRoleFault fault = FaultConverter.toFault(new NullifiedRoleFault(), e);
            throw new NullifiedRoleFaultFaultMessage(e.getMessage(), fault, e);
        }
        GetByIdsResponse response = new GetByIdsResponse();
        response.getHealthCareFacility().addAll(results);
        return response;
    }
}
