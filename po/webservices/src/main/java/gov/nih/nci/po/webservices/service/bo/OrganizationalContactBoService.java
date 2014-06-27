package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.AbstractOrganizationalContact;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.OrganizationalContactCR;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import org.springframework.stereotype.Service;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Service("organizationalContactBoService")
public class OrganizationalContactBoService
        extends AbstractRoleBoService<OrganizationalContact, OrganizationalContactCR> {
    @Override
    protected GenericStructrualRoleServiceLocal<OrganizationalContact> getCorrelationService() {
        return PoRegistry.getInstance().getServiceLocator().getOrganizationalContactService();
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal<OrganizationalContactCR> getCrService() {
        return PoRegistry.getInstance().getServiceLocator().getOrganizationalContactCRService();
    }

    @Override
    protected boolean hasChanges(OrganizationalContactCR cr) {
        return !cr.isNoChange();
    }

    @Override
    protected OrganizationalContactCR createCr(OrganizationalContact currentInstance,
                                               OrganizationalContact updatedInstance) {
        OrganizationalContactCR cr = new OrganizationalContactCR(currentInstance);

        OrganizationalContactDTO curatedInstanceDto
                = (OrganizationalContactDTO) PoXsnapshotHelper.createSnapshot(updatedInstance);
        curatedInstanceDto.setIdentifier(null);
        PoXsnapshotHelper.copyIntoAbstractModel(curatedInstanceDto, cr, AbstractOrganizationalContact.class);
        cr.setId(null);

        return cr;
    }
}
