package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import gov.nih.nci.coppa.po.ResearchOrganization;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.po.service.ResearchOrganizationSortCriterion;
import gov.nih.nci.po.webservices.convert.bridg.ResearchOrganizationTransformer;
import gov.nih.nci.po.webservices.service.bo.ResearchOrganizationBoService;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Service("researchOrganizationBridgService")
public class ResearchOrganizationServiceImpl extends AbstractRoleService
        <
                ResearchOrganization,
                ResearchOrganizationDTO,
                gov.nih.nci.po.data.bo.ResearchOrganization
        > {

    /**
     * Constructor.
     * @param boService The BO service to delegate to.
     */
    @Autowired
    protected ResearchOrganizationServiceImpl(ResearchOrganizationBoService boService) {
        super(boService);
    }

    @Override
    protected Transformer<ResearchOrganization, ResearchOrganizationDTO> getTransformer() {
        return ResearchOrganizationTransformer.INSTANCE;
    }

    @Override
    protected SortCriterion<gov.nih.nci.po.data.bo.ResearchOrganization> getSortCriterion() {
        return ResearchOrganizationSortCriterion.ID;
    }
}
