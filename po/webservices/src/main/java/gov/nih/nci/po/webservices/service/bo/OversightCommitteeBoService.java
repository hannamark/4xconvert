package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.AbstractOversightCommittee;
import gov.nih.nci.po.data.bo.OversightCommittee;
import gov.nih.nci.po.data.bo.OversightCommitteeCR;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import org.springframework.stereotype.Service;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Service("oversightCommitteeBoService")
public class OversightCommitteeBoService extends AbstractRoleBoService<OversightCommittee, OversightCommitteeCR> {
    @Override
    protected GenericStructrualRoleServiceLocal<OversightCommittee> getCorrelationService() {
        return PoRegistry.getInstance().getServiceLocator().getOversightCommitteeService();
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal<OversightCommitteeCR> getCrService() {
        return PoRegistry.getInstance().getServiceLocator().getOversightCommitteeCRService();
    }

    @Override
    protected boolean hasChanges(OversightCommitteeCR cr) {
        return !cr.isNoChange();
    }

    @Override
    protected OversightCommitteeCR createCr(OversightCommittee currentInstance, OversightCommittee updatedInstance) {
        OversightCommitteeCR cr = new OversightCommitteeCR(currentInstance);

        OversightCommitteeDTO curatedInstanceDto
                = (OversightCommitteeDTO) PoXsnapshotHelper.createSnapshot(updatedInstance);
        curatedInstanceDto.setIdentifier(null);
        PoXsnapshotHelper.copyIntoAbstractModel(curatedInstanceDto, cr, AbstractOversightCommittee.class);
        cr.setTypeCode(updatedInstance.getTypeCode());
        cr.setId(null);

        return cr;
    }
}
