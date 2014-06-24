package gov.nih.nci.po.webservices.service.bridg.soap;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.HealthCareProvider;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.po.faults.EntityValidationFault;
import gov.nih.nci.coppa.po.faults.NullifiedRoleFault;
import gov.nih.nci.coppa.po.faults.TooManyResultsFault;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.enterpriseservices.structuralroles.healthcareprovider.CreateRequest;
import gov.nih.nci.enterpriseservices.structuralroles.healthcareprovider.CreateResponse;
import gov.nih.nci.enterpriseservices.structuralroles.healthcareprovider.GetByIdRequest;
import gov.nih.nci.enterpriseservices.structuralroles.healthcareprovider.GetByIdResponse;
import gov.nih.nci.enterpriseservices.structuralroles.healthcareprovider.GetByIdsRequest;
import gov.nih.nci.enterpriseservices.structuralroles.healthcareprovider.GetByIdsResponse;
import gov.nih.nci.enterpriseservices.structuralroles.healthcareprovider.GetByPlayerIdsRequest;
import gov.nih.nci.enterpriseservices.structuralroles.healthcareprovider.GetByPlayerIdsResponse;
import gov.nih.nci.enterpriseservices.structuralroles.healthcareprovider.QueryRequest;
import gov.nih.nci.enterpriseservices.structuralroles.healthcareprovider.QueryResponse;
import gov.nih.nci.enterpriseservices.structuralroles.healthcareprovider.UpdateRequest;
import gov.nih.nci.enterpriseservices.structuralroles.healthcareprovider.UpdateResponse;
import gov.nih.nci.enterpriseservices.structuralroles.healthcareprovider.UpdateStatusRequest;
import gov.nih.nci.enterpriseservices.structuralroles.healthcareprovider.UpdateStatusResponse;
import gov.nih.nci.enterpriseservices.structuralroles.healthcareprovider.ValidateRequest;
import gov.nih.nci.enterpriseservices.structuralroles.healthcareprovider.ValidateResponse;
import gov.nih.nci.iso21090.extensions.Cd;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.webservices.convert.bridg.FaultConverter;
import gov.nih.nci.po.webservices.service.bridg.RoleService;
import gov.nih.nci.po.webservices.service.bridg.soap.healthcareprovider.EntityValidationFaultFaultMessage;
import gov.nih.nci.po.webservices.service.bridg.soap.healthcareprovider.HealthCareProviderPortType;
import gov.nih.nci.po.webservices.service.bridg.soap.healthcareprovider.NullifiedRoleFaultFaultMessage;
import gov.nih.nci.po.webservices.service.bridg.soap.healthcareprovider.TooManyResultsFaultFaultMessage;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@WebService(serviceName = "HealthCareProviderService",
        portName = "HealthCareProviderPortTypePort",
        wsdlLocation = "/gov/nih/nci/po/webservices/bridg/HealthCareProvider.wsdl",
        targetNamespace = "http://enterpriseservices.nci.nih.gov/structuralroles/HealthCareProvider",
        endpointInterface =
                "gov.nih.nci.po.webservices.service.bridg.soap.healthcareprovider.HealthCareProviderPortType"
)
@Service("healthCareProviderBridgSoapEndpointImpl")
public class HealthCareProviderPortTypeImpl implements HealthCareProviderPortType {
    @Autowired
    @Qualifier("healthCareProviderBridgService")
    private RoleService<HealthCareProvider> service;

    @Override
    public CreateResponse create(CreateRequest request) throws EntityValidationFaultFaultMessage {

        HealthCareProvider definition = request.getHealthCareProvider().getHealthCareProvider();
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
        HealthCareProvider spec = request.getHealthCareProvider().getHealthCareProvider();

        List<HealthCareProvider> results = null;
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
        response.getHealthCareProvider().addAll(results);
        return response;
    }

    @Override
    public UpdateResponse update(UpdateRequest request) throws EntityValidationFaultFaultMessage {
        HealthCareProvider definition = request.getHealthCareProvider().getHealthCareProvider();

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
        HealthCareProvider definition = request.getHealthCareProvider().getHealthCareProvider();

        StringMap results = service.validate(definition);
        ValidateResponse response = new ValidateResponse();
        response.setStringMap(results);
        return response;
    }

    @Override
    public GetByPlayerIdsResponse getByPlayerIds(GetByPlayerIdsRequest request) {
        List<Id> ids = request.getId().getId();

        List<HealthCareProvider> results = null;
        results = service.getByPlayerIds(ids);

        GetByPlayerIdsResponse response = new GetByPlayerIdsResponse();
        response.getHealthCareProvider().addAll(results);
        return response;
    }

    @Override
    public GetByIdResponse getById(GetByIdRequest request) throws NullifiedRoleFaultFaultMessage {
        Id id = request.getId().getId();

        HealthCareProvider result = null;
        try {
            result = service.getById(id);
        } catch (NullifiedRoleException e) {
            NullifiedRoleFault fault = FaultConverter.toFault(new NullifiedRoleFault(), e);
            throw new NullifiedRoleFaultFaultMessage(e.getMessage(), fault, e);
        }
        GetByIdResponse response = new GetByIdResponse();
        response.setHealthCareProvider(result);
        return response;
    }

    @Override
    public GetByIdsResponse getByIds(GetByIdsRequest request) throws NullifiedRoleFaultFaultMessage {
        List<Id> ids = request.getId().getId();

        List<HealthCareProvider> results = null;
        try {
            results = service.getByIds(ids);
        } catch (NullifiedRoleException e) {
            NullifiedRoleFault fault = FaultConverter.toFault(new NullifiedRoleFault(), e);
            throw new NullifiedRoleFaultFaultMessage(e.getMessage(), fault, e);
        }
        GetByIdsResponse response = new GetByIdsResponse();
        response.getHealthCareProvider().addAll(results);
        return response;
    }
}
