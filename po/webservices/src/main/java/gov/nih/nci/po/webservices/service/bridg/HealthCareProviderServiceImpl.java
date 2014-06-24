package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import gov.nih.nci.coppa.po.HealthCareProvider;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.service.HealthCareProviderSortCriterion;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.webservices.convert.bridg.HealthCareProviderTransformer;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import org.springframework.stereotype.Service;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Service("healthCareProviderBridgService")
public class HealthCareProviderServiceImpl extends AbstractRoleService
            <
                    HealthCareProvider,
                    HealthCareProviderDTO,
                    gov.nih.nci.po.data.bo.HealthCareProvider
            > {


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
