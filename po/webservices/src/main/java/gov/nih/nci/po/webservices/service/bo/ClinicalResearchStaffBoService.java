package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.AbstractPersonRole;
import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.ClinicalResearchStaffCR;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.service.ClinicalResearchStaffServiceLocal;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Service("clinicalResearchStaffBoService")
public class ClinicalResearchStaffBoService
        extends AbstractRoleBoService<ClinicalResearchStaff, ClinicalResearchStaffCR>
        implements ClinicalResearchStaffServiceLocal {

    @Override
    protected GenericStructrualRoleServiceLocal<ClinicalResearchStaff> getCorrelationService() {
        return PoRegistry.getInstance().getServiceLocator().getClinicalResearchStaffService();
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal<ClinicalResearchStaffCR> getCrService() {
        return PoRegistry.getInstance().getServiceLocator().getClinicalResearchStaffCRService();
    }

    @Override
    protected boolean hasChanges(ClinicalResearchStaffCR cr) {
        return !cr.isNoChange();
    }

    @Override
    protected ClinicalResearchStaffCR createCr(ClinicalResearchStaff currentInstance,
                                               ClinicalResearchStaff updatedInstance) {
        ClinicalResearchStaffCR cr = new ClinicalResearchStaffCR(currentInstance);

        ClinicalResearchStaffDTO curatedInstanceDto
                = (ClinicalResearchStaffDTO) PoXsnapshotHelper.createSnapshot(updatedInstance);
        curatedInstanceDto.setIdentifier(null);
        PoXsnapshotHelper.copyIntoAbstractModel(curatedInstanceDto, cr, AbstractPersonRole.class);
        cr.setId(null);

        return cr;
    }


    @Override
    public int getHotRoleCount(Person per) {
        return ((ClinicalResearchStaffServiceLocal) getCorrelationService()).getHotRoleCount(per);
    }

    @Override
    public long createActiveWithFallback(ClinicalResearchStaff structuralRole)
            throws EntityValidationException, JMSException {
        return ((ClinicalResearchStaffServiceLocal) getCorrelationService()).createActiveWithFallback(structuralRole);
    }


}
