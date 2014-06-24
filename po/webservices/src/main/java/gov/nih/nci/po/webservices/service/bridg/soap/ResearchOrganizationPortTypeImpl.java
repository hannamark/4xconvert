package gov.nih.nci.po.webservices.service.bridg.soap;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.ResearchOrganization;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.po.faults.EntityValidationFault;
import gov.nih.nci.coppa.po.faults.NullifiedRoleFault;
import gov.nih.nci.coppa.po.faults.TooManyResultsFault;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.enterpriseservices.structuralroles.researchorganization.CreateRequest;
import gov.nih.nci.enterpriseservices.structuralroles.researchorganization.CreateResponse;
import gov.nih.nci.enterpriseservices.structuralroles.researchorganization.GetByIdRequest;
import gov.nih.nci.enterpriseservices.structuralroles.researchorganization.GetByIdResponse;
import gov.nih.nci.enterpriseservices.structuralroles.researchorganization.GetByIdsRequest;
import gov.nih.nci.enterpriseservices.structuralroles.researchorganization.GetByIdsResponse;
import gov.nih.nci.enterpriseservices.structuralroles.researchorganization.GetByPlayerIdsRequest;
import gov.nih.nci.enterpriseservices.structuralroles.researchorganization.GetByPlayerIdsResponse;
import gov.nih.nci.enterpriseservices.structuralroles.researchorganization.QueryRequest;
import gov.nih.nci.enterpriseservices.structuralroles.researchorganization.QueryResponse;
import gov.nih.nci.enterpriseservices.structuralroles.researchorganization.UpdateRequest;
import gov.nih.nci.enterpriseservices.structuralroles.researchorganization.UpdateResponse;
import gov.nih.nci.enterpriseservices.structuralroles.researchorganization.UpdateStatusRequest;
import gov.nih.nci.enterpriseservices.structuralroles.researchorganization.UpdateStatusResponse;
import gov.nih.nci.enterpriseservices.structuralroles.researchorganization.ValidateRequest;
import gov.nih.nci.enterpriseservices.structuralroles.researchorganization.ValidateResponse;
import gov.nih.nci.iso21090.extensions.Cd;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.webservices.convert.bridg.FaultConverter;
import gov.nih.nci.po.webservices.service.bridg.RoleService;
import gov.nih.nci.po.webservices.service.bridg.soap.researchorganization.EntityValidationFaultFaultMessage;
import gov.nih.nci.po.webservices.service.bridg.soap.researchorganization.NullifiedRoleFaultFaultMessage;
import gov.nih.nci.po.webservices.service.bridg.soap.researchorganization.ResearchOrganizationPortType;
import gov.nih.nci.po.webservices.service.bridg.soap.researchorganization.TooManyResultsFaultFaultMessage;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@WebService(serviceName = "ResearchOrganizationService",
        portName = "ResearchOrganizationPortTypePort",
        wsdlLocation = "/gov/nih/nci/po/webservices/bridg/ResearchOrganization.wsdl",
        targetNamespace = "http://enterpriseservices.nci.nih.gov/structuralroles/ResearchOrganization",
        endpointInterface =
                "gov.nih.nci.po.webservices.service.bridg.soap.researchorganization.ResearchOrganizationPortType"
)
@Service("researchOrganizationBridgSoapEndpointImpl")
public class ResearchOrganizationPortTypeImpl implements ResearchOrganizationPortType {
    @Autowired
    @Qualifier("researchOrganizationBridgService")
    private RoleService<ResearchOrganization> service;

    @Override
    public CreateResponse create(CreateRequest request) throws EntityValidationFaultFaultMessage {

        ResearchOrganization definition = request.getResearchOrganization().getResearchOrganization();
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
        ResearchOrganization spec = request.getResearchOrganization().getResearchOrganization();

        List<ResearchOrganization> results = null;
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
        response.getResearchOrganization().addAll(results);
        return response;
    }

    @Override
    public UpdateResponse update(UpdateRequest request) throws EntityValidationFaultFaultMessage {
        ResearchOrganization definition = request.getResearchOrganization().getResearchOrganization();

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
        ResearchOrganization definition = request.getResearchOrganization().getResearchOrganization();

        StringMap results = service.validate(definition);
        ValidateResponse response = new ValidateResponse();
        response.setStringMap(results);
        return response;
    }

    @Override
    public GetByPlayerIdsResponse getByPlayerIds(GetByPlayerIdsRequest request) {
        List<Id> ids = request.getId().getId();

        List<ResearchOrganization> results = null;
        results = service.getByPlayerIds(ids);

        GetByPlayerIdsResponse response = new GetByPlayerIdsResponse();
        response.getResearchOrganization().addAll(results);
        return response;
    }

    @Override
    public GetByIdResponse getById(GetByIdRequest request) throws NullifiedRoleFaultFaultMessage {
        Id id = request.getId().getId();

        ResearchOrganization result = null;
        try {
            result = service.getById(id);
        } catch (NullifiedRoleException e) {
            NullifiedRoleFault fault = FaultConverter.toFault(new NullifiedRoleFault(), e);
            throw new NullifiedRoleFaultFaultMessage(e.getMessage(), fault, e);
        }
        GetByIdResponse response = new GetByIdResponse();
        response.setResearchOrganization(result);
        return response;
    }

    @Override
    public GetByIdsResponse getByIds(GetByIdsRequest request) throws NullifiedRoleFaultFaultMessage {
        List<Id> ids = request.getId().getId();

        List<ResearchOrganization> results = null;
        try {
            results = service.getByIds(ids);
        } catch (NullifiedRoleException e) {
            NullifiedRoleFault fault = FaultConverter.toFault(new NullifiedRoleFault(), e);
            throw new NullifiedRoleFaultFaultMessage(e.getMessage(), fault, e);
        }
        GetByIdsResponse response = new GetByIdsResponse();
        response.getResearchOrganization().addAll(results);
        return response;
    }
}
