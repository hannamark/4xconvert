package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import gov.nih.nci.coppa.po.IdentifiedOrganization;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.po.service.IdentifiedOrganizationSortCriterion;
import gov.nih.nci.po.webservices.convert.bridg.IdentifiedOrganizationTransformer;
import gov.nih.nci.po.webservices.service.bo.IdentifiedOrganizationBoService;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Service("identifiedOrganizationBridgService")
public class IdentifiedOrganizationServiceImpl extends AbstractRoleService
            <
                    IdentifiedOrganization,
                    IdentifiedOrganizationDTO,
                    gov.nih.nci.po.data.bo.IdentifiedOrganization
            > {

    /**
     * Constructor.
     * @param boService The BO service to delegate to.
     */
    @Autowired
    protected IdentifiedOrganizationServiceImpl(IdentifiedOrganizationBoService boService) {
        super(boService);
    }

    @Override
    protected Transformer<IdentifiedOrganization, IdentifiedOrganizationDTO> getTransformer() {
        return IdentifiedOrganizationTransformer.INSTANCE;
    }

    @Override
    protected SortCriterion<gov.nih.nci.po.data.bo.IdentifiedOrganization> getSortCriterion() {
        return IdentifiedOrganizationSortCriterion.ID;
    }
}
