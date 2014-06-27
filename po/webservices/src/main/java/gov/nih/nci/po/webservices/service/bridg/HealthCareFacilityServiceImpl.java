package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import gov.nih.nci.coppa.po.HealthCareFacility;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.po.service.HealthCareFacilitySortCriterion;
import gov.nih.nci.po.webservices.convert.bridg.HealthCareFacilityTransformer;
import gov.nih.nci.po.webservices.service.bo.HealthCareFacilityBoService;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Service("healthCareFacilityBridgService")
public class HealthCareFacilityServiceImpl extends AbstractRoleService
            <
                    HealthCareFacility,
                    HealthCareFacilityDTO,
                    gov.nih.nci.po.data.bo.HealthCareFacility
            > {

    /**
     * Constructor.
     * @param boService The BO service to delegate to.
     */
    @Autowired
    protected HealthCareFacilityServiceImpl(HealthCareFacilityBoService boService) {
        super(boService);
    }

    @Override
    protected Transformer<HealthCareFacility, HealthCareFacilityDTO> getTransformer() {
        return HealthCareFacilityTransformer.INSTANCE;
    }

    @Override
    protected SortCriterion<gov.nih.nci.po.data.bo.HealthCareFacility> getSortCriterion() {
        return HealthCareFacilitySortCriterion.ID;
    }
}
