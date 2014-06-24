package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import gov.nih.nci.coppa.po.ClinicalResearchStaff;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.po.service.ClinicalResearchStaffSortCriterion;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.webservices.convert.bridg.ClinicalResearchStaffTransformer;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import org.springframework.stereotype.Service;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Service("clinicalResearchStaffBridgService")
public class ClinicalResearchStaffServiceImpl extends AbstractRoleService
        <
                ClinicalResearchStaff,
                ClinicalResearchStaffDTO,
                gov.nih.nci.po.data.bo.ClinicalResearchStaff
        > implements RoleService<ClinicalResearchStaff> {

    @Override
    protected SortCriterion<gov.nih.nci.po.data.bo.ClinicalResearchStaff> getSortCriterion() {
        return ClinicalResearchStaffSortCriterion.ID;
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