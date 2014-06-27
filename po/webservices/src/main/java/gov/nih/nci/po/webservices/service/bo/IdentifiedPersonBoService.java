package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.AbstractIdentifiedPerson;
import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.IdentifiedPersonCR;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import org.springframework.stereotype.Service;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Service("identifiedPersonBoService")
public class IdentifiedPersonBoService extends AbstractRoleBoService<IdentifiedPerson, IdentifiedPersonCR> {
    @Override
    protected GenericStructrualRoleServiceLocal<IdentifiedPerson> getCorrelationService() {
        return PoRegistry.getInstance().getServiceLocator().getIdentifiedPersonService();
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal<IdentifiedPersonCR> getCrService() {
        return PoRegistry.getInstance().getServiceLocator().getIdentifiedPersonCRService();
    }

    @Override
    protected boolean hasChanges(IdentifiedPersonCR cr) {
        return !cr.isNoChange();
    }

    @Override
    protected IdentifiedPersonCR createCr(IdentifiedPerson currentInstance, IdentifiedPerson updatedInstance) {
        IdentifiedPersonCR cr = new IdentifiedPersonCR(currentInstance);

        IdentifiedPersonDTO curatedInstanceDto
                = (IdentifiedPersonDTO) PoXsnapshotHelper.createSnapshot(updatedInstance);
        curatedInstanceDto.setIdentifier(null);
        PoXsnapshotHelper.copyIntoAbstractModel(curatedInstanceDto, cr, AbstractIdentifiedPerson.class);
        cr.setId(null);

        return cr;
    }
}
