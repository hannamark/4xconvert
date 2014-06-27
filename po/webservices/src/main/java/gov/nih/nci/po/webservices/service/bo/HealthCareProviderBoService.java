package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.AbstractPersonRole;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.HealthCareProviderCR;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import org.springframework.stereotype.Service;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Service("healthCareProviderBoService")
public class HealthCareProviderBoService extends AbstractRoleBoService<HealthCareProvider, HealthCareProviderCR> {
    @Override
    protected GenericStructrualRoleServiceLocal<HealthCareProvider> getCorrelationService() {
        return PoRegistry.getInstance().getServiceLocator().getHealthCareProviderService();
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal<HealthCareProviderCR> getCrService() {
        return PoRegistry.getInstance().getServiceLocator().getHealthCareProviderCRService();
    }

    @Override
    protected boolean hasChanges(HealthCareProviderCR cr) {
        return !cr.isNoChange();
    }

    @Override
    protected HealthCareProviderCR createCr(HealthCareProvider currentInstance, HealthCareProvider updatedInstance) {
        HealthCareProviderCR cr = new HealthCareProviderCR(currentInstance);

        HealthCareProviderDTO curatedInstanceDto
                = (HealthCareProviderDTO) PoXsnapshotHelper.createSnapshot(updatedInstance);
        curatedInstanceDto.setIdentifier(null);
        PoXsnapshotHelper.copyIntoAbstractModel(curatedInstanceDto, cr, AbstractPersonRole.class);
        cr.setId(null);

        return cr;
    }
}
