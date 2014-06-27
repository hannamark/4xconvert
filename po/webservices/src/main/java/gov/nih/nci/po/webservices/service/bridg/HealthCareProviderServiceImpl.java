package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import gov.nih.nci.coppa.po.HealthCareProvider;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.po.service.HealthCareProviderSortCriterion;
import gov.nih.nci.po.webservices.convert.bridg.HealthCareProviderTransformer;
import gov.nih.nci.po.webservices.service.bo.HealthCareProviderBoService;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * Constructor.
     * @param boService The BO service to delegate to.
     */
    @Autowired
    protected HealthCareProviderServiceImpl(HealthCareProviderBoService boService) {
        super(boService);
    }

    @Override
    protected Transformer<HealthCareProvider, HealthCareProviderDTO> getTransformer() {
        return HealthCareProviderTransformer.INSTANCE;
    }

    @Override
    protected SortCriterion<gov.nih.nci.po.data.bo.HealthCareProvider> getSortCriterion() {
        return HealthCareProviderSortCriterion.ID;
    }
}
