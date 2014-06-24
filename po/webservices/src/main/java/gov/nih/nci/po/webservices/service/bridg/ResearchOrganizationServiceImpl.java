package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import gov.nih.nci.coppa.po.ResearchOrganization;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationSortCriterion;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.webservices.convert.bridg.ResearchOrganizationTransformer;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
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


    @Override
    protected Transformer<ResearchOrganization, ResearchOrganizationDTO> getTransformer() {
        return ResearchOrganizationTransformer.INSTANCE;
    }

    @Override
    protected GenericStructrualRoleServiceLocal<gov.nih.nci.po.data.bo.ResearchOrganization> getEjbService() {
        return PoRegistry.getInstance().getServiceLocator().getResearchOrganizationService();
    }

    @Override
    protected SortCriterion<gov.nih.nci.po.data.bo.ResearchOrganization> getSortCriterion() {
        return ResearchOrganizationSortCriterion.ID;
    }
}
