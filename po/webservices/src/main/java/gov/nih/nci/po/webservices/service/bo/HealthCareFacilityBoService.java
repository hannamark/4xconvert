package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.HealthCareFacilityCR;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Overridable;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.service.HealthCareFacilityServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.po.webservices.service.bo.filter.StatusValidatingRoleFilter;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Service("healthCareFacilityBoService")
public class HealthCareFacilityBoService
        extends AbstractEnhancedOrganizationRoleBoService<HealthCareFacility, HealthCareFacilityCR>
        implements HealthCareFacilityServiceLocal {

    /**
     * Default constructor.
     */
    public HealthCareFacilityBoService() {
        super();
        getUpdateFilters().add(new StatusValidatingRoleFilter<HealthCareFacility>());
    }
    @Override
    protected GenericStructrualRoleServiceLocal<HealthCareFacility> getCorrelationService() {
        return PoRegistry.getInstance().getServiceLocator().getHealthCareFacilityService();
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal<HealthCareFacilityCR> getCrService() {
        return PoRegistry.getInstance().getServiceLocator().getHealthCareFacilityCRService();
    }

    @Override
    protected boolean hasChanges(HealthCareFacilityCR cr) {
        return !cr.isNoChange();
    }

    @Override
    protected HealthCareFacilityCR createCr(HealthCareFacility currentInstance, HealthCareFacility updatedInstance) {
        HealthCareFacilityCR cr = new HealthCareFacilityCR(currentInstance);

        HealthCareFacilityDTO curatedInstanceDto
                = (HealthCareFacilityDTO) PoXsnapshotHelper.createSnapshot(updatedInstance);
        curatedInstanceDto.setIdentifier(null);
        PoXsnapshotHelper.copyIntoAbstractModel(curatedInstanceDto, cr, AbstractEnhancedOrganizationRole.class);
        cr.setId(null);

        return cr;
    }

    @Override
    public int getHotRoleCount(Organization org) {
        return ((HealthCareFacilityServiceLocal) getCorrelationService()).getHotRoleCount(org);
    }

    @Override
    public void curate(HealthCareFacility inHCFBo, String ctepId) throws JMSException {
        super.curate(inHCFBo, ctepId);
    }

    @Override
    public void override(Overridable overridable, User overriddenBy) {
        ((HealthCareFacilityServiceLocal) getCorrelationService()).override(overridable, overriddenBy);
    }

    @Override
    public long create(HealthCareFacility structuralRole) throws EntityValidationException, JMSException {
        if (structuralRole != null && structuralRole.getStatus() != RoleStatus.PENDING) {
            throw new ServiceException(
                    String.format(
                            "Illegal attempt to create a HealthCareFacility with status %s",
                            structuralRole.getStatus().name()
                    )
            );
        }
        return super.create(structuralRole);
    }
}
