package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole;
import gov.nih.nci.po.data.bo.AbstractResearchOrganization;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Overridable;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.ResearchOrganizationCR;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.po.webservices.service.bo.filter.StatusValidatingRoleFilter;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Service("researchOrganizationBoService")
public class ResearchOrganizationBoService
        extends AbstractEnhancedOrganizationRoleBoService<ResearchOrganization, ResearchOrganizationCR>
        implements ResearchOrganizationServiceLocal {

    /**
     * Default constructor.
     */
    public ResearchOrganizationBoService() {
        super();
        getUpdateFilters().add(new StatusValidatingRoleFilter<ResearchOrganization>());
    }

    @Override
    protected GenericStructrualRoleServiceLocal<ResearchOrganization> getCorrelationService() {
        return PoRegistry.getInstance().getServiceLocator().getResearchOrganizationService();
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal<ResearchOrganizationCR> getCrService() {
        return PoRegistry.getInstance().getServiceLocator().getResearchOrganizationCRService();
    }

    @Override
    protected boolean hasChanges(ResearchOrganizationCR cr) {
        return !cr.isNoChange();
    }

    @Override
    protected ResearchOrganizationCR createCr(ResearchOrganization currentInstance,
                                              ResearchOrganization updatedInstance) {
        ResearchOrganizationCR cr = new ResearchOrganizationCR(currentInstance);

        ResearchOrganizationDTO curatedInstanceDto
                = (ResearchOrganizationDTO) PoXsnapshotHelper.createSnapshot(updatedInstance);
        curatedInstanceDto.setIdentifier(null);
        PoXsnapshotHelper.copyIntoAbstractModel(curatedInstanceDto, cr, AbstractEnhancedOrganizationRole.class);
        PoXsnapshotHelper.copyIntoAbstractModel(curatedInstanceDto, cr, AbstractResearchOrganization.class);
        cr.setId(null);

        return cr;
    }

    @Override
    public int getHotRoleCount(Organization org) {
        return ((ResearchOrganizationServiceLocal) getCorrelationService()).getHotRoleCount(org);
    }

    @Override
    public void curate(ResearchOrganization researchOrganization, String ctepId) throws JMSException {
        super.curate(researchOrganization, ctepId);
    }

    @Override
    public void override(Overridable overridable, User overriddenBy) {
        ((ResearchOrganizationServiceLocal) getCorrelationService()).override(overridable, overriddenBy);
    }

    @Override
    public long create(ResearchOrganization structuralRole) throws EntityValidationException, JMSException {
        if (structuralRole.getStatus() != null && structuralRole.getStatus() != RoleStatus.PENDING) {
            throw new ServiceException(
                    String.format(
                            "Illegal attempt to create a HealthCareFacility with status %s",
                            structuralRole.getStatus().name()
                    )
            );
        }

        return super.create(structuralRole);
    }
}
