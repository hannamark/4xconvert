package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import gov.nih.nci.coppa.po.ClinicalResearchStaff;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.po.data.bo.AbstractPersonRole;
import gov.nih.nci.po.data.bo.ClinicalResearchStaffCR;
import gov.nih.nci.po.data.bo.CorrelationChangeRequest;
import gov.nih.nci.po.service.ClinicalResearchStaffSortCriterion;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.webservices.convert.bridg.ClinicalResearchStaffTransformer;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class ClinicalResearchStaffServiceImpl extends AbstractRoleService
        <
                ClinicalResearchStaff,
                ClinicalResearchStaffDTO,
                gov.nih.nci.po.data.bo.ClinicalResearchStaff
        > {

    @Override
    protected SortCriterion<gov.nih.nci.po.data.bo.ClinicalResearchStaff> getSortCriterion() {
        return ClinicalResearchStaffSortCriterion.ID;
    }

    @Override
    protected Class<?> getAbstractModelClass() {
        return AbstractPersonRole.class;
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal getCrService() {
        return PoRegistry.getInstance().getServiceLocator().getClinicalResearchStaffCRService();
    }

    @Override
    protected CorrelationChangeRequest<gov.nih.nci.po.data.bo.ClinicalResearchStaff> buildChangeRequest(
            gov.nih.nci.po.data.bo.ClinicalResearchStaff instance) {
        return new ClinicalResearchStaffCR(instance);
    }


    @Override
    protected Transformer<ClinicalResearchStaff, ClinicalResearchStaffDTO> getTransformer() {
        return ClinicalResearchStaffTransformer.INSTANCE;
    }

    @Override
    protected GenericStructrualRoleServiceLocal<gov.nih.nci.po.data.bo.ClinicalResearchStaff> getEjbService() {
        return PoRegistry.getInstance().getServiceLocator().getClinicalResearchStaffService();
    }

}