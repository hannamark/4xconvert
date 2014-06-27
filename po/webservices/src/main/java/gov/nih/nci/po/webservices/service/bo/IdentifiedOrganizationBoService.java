package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.AbstractIdentifiedOrganization;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.IdentifiedOrganizationCR;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import org.springframework.stereotype.Service;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Service("identifiedOrganizationBoService")
public class IdentifiedOrganizationBoService
        extends AbstractRoleBoService<IdentifiedOrganization, IdentifiedOrganizationCR> {
    @Override
    protected GenericStructrualRoleServiceLocal<IdentifiedOrganization> getCorrelationService() {
        return PoRegistry.getInstance().getServiceLocator().getIdentifiedOrganizationService();
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal<IdentifiedOrganizationCR> getCrService() {
        return PoRegistry.getInstance().getServiceLocator().getIdentifiedOrganizationCRService();
    }

    @Override
    protected boolean hasChanges(IdentifiedOrganizationCR cr) {
        return !cr.isNoChange();
    }

    @Override
    protected IdentifiedOrganizationCR createCr(IdentifiedOrganization currentInstance,
                                                IdentifiedOrganization updatedInstance) {
        IdentifiedOrganizationCR cr = new IdentifiedOrganizationCR(currentInstance);

        IdentifiedOrganizationDTO curatedInstanceDto
                = (IdentifiedOrganizationDTO) PoXsnapshotHelper.createSnapshot(updatedInstance);
        curatedInstanceDto.setIdentifier(null);
        PoXsnapshotHelper.copyIntoAbstractModel(curatedInstanceDto, cr, AbstractIdentifiedOrganization.class);
        cr.setId(null);

        return cr;
    }
}
