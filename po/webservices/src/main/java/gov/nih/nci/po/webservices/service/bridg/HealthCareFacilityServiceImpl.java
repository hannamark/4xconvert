package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import gov.nih.nci.coppa.po.HealthCareFacility;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole;
import gov.nih.nci.po.data.bo.CorrelationChangeRequest;
import gov.nih.nci.po.data.bo.HealthCareFacilityCR;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.service.HealthCareFacilitySortCriterion;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.webservices.convert.bridg.HealthCareFacilityTransformer;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class HealthCareFacilityServiceImpl extends AbstractRoleService
            <
                    HealthCareFacility,
                    HealthCareFacilityDTO,
                    gov.nih.nci.po.data.bo.HealthCareFacility
            > {

    @Override
    protected Class<?> getAbstractModelClass() {
        return AbstractEnhancedOrganizationRole.class;
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal getCrService() {
        return PoRegistry.getInstance().getServiceLocator().getHealthCareFacilityCRService();
    }

    @Override
    protected CorrelationChangeRequest<gov.nih.nci.po.data.bo.HealthCareFacility> buildChangeRequest(
            gov.nih.nci.po.data.bo.HealthCareFacility instance) {
        return new HealthCareFacilityCR(instance);
    }

    @Override
    protected Transformer<HealthCareFacility, HealthCareFacilityDTO> getTransformer() {
        return HealthCareFacilityTransformer.INSTANCE;
    }

    @Override
    protected GenericStructrualRoleServiceLocal<gov.nih.nci.po.data.bo.HealthCareFacility> getEjbService() {
        return PoRegistry.getInstance().getServiceLocator().getHealthCareFacilityService();
    }

    @Override
    protected SortCriterion<gov.nih.nci.po.data.bo.HealthCareFacility> getSortCriterion() {
        return HealthCareFacilitySortCriterion.ID;
    }
}
