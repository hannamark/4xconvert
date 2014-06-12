package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import gov.nih.nci.coppa.po.HealthCareProvider;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.po.data.bo.AbstractHealthCareProvider;
import gov.nih.nci.po.data.bo.CorrelationChangeRequest;
import gov.nih.nci.po.data.bo.HealthCareProviderCR;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.service.HealthCareProviderSortCriterion;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.webservices.convert.bridg.HealthCareProviderTransformer;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class HealthCareProviderServiceImpl extends AbstractRoleService
            <
                    HealthCareProvider,
                    HealthCareProviderDTO,
                    gov.nih.nci.po.data.bo.HealthCareProvider
            > {
    @Override
    protected Class<?> getAbstractModelClass() {
        return AbstractHealthCareProvider.class;
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal getCrService() {
        return PoRegistry.getInstance().getServiceLocator().getHealthCareProviderCRService();
    }

    @Override
    protected CorrelationChangeRequest<gov.nih.nci.po.data.bo.HealthCareProvider> buildChangeRequest(
            gov.nih.nci.po.data.bo.HealthCareProvider instance) {
        return new HealthCareProviderCR(instance);
    }

    @Override
    protected Transformer<HealthCareProvider, HealthCareProviderDTO> getTransformer() {
        return HealthCareProviderTransformer.INSTANCE;
    }

    @Override
    protected GenericStructrualRoleServiceLocal<gov.nih.nci.po.data.bo.HealthCareProvider> getEjbService() {
        return PoRegistry.getInstance().getServiceLocator().getHealthCareProviderService();
    }

    @Override
    protected SortCriterion<gov.nih.nci.po.data.bo.HealthCareProvider> getSortCriterion() {
        return HealthCareProviderSortCriterion.ID;
    }
}
