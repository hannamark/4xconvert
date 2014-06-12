package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import gov.nih.nci.coppa.po.ResearchOrganization;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.po.data.bo.AbstractResearchOrganization;
import gov.nih.nci.po.data.bo.CorrelationChangeRequest;
import gov.nih.nci.po.data.bo.ResearchOrganizationCR;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationSortCriterion;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.webservices.convert.bridg.ResearchOrganizationTransformer;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class ResearchOrganizationServiceImpl extends AbstractRoleService
        <
                ResearchOrganization,
                ResearchOrganizationDTO,
                gov.nih.nci.po.data.bo.ResearchOrganization
        > {
    @Override
    protected Class<?> getAbstractModelClass() {
        return AbstractResearchOrganization.class;
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal getCrService() {
        return PoRegistry.getInstance().getServiceLocator().getResearchOrganizationCRService();
    }

    @Override
    protected CorrelationChangeRequest<gov.nih.nci.po.data.bo.ResearchOrganization> buildChangeRequest(
            gov.nih.nci.po.data.bo.ResearchOrganization instance) {
        return new ResearchOrganizationCR(instance);
    }

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
