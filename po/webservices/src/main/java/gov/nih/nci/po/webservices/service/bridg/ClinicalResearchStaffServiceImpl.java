package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import gov.nih.nci.coppa.po.ClinicalResearchStaff;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.po.service.ClinicalResearchStaffSortCriterion;
import gov.nih.nci.po.webservices.convert.bridg.ClinicalResearchStaffTransformer;
import gov.nih.nci.po.webservices.service.bo.ClinicalResearchStaffBoService;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * Constructor.
     * @param boService The BO service to delegate to.
     */
    @Autowired
    protected ClinicalResearchStaffServiceImpl(ClinicalResearchStaffBoService boService) {
        super(boService);
    }

    @Override
    protected SortCriterion<gov.nih.nci.po.data.bo.ClinicalResearchStaff> getSortCriterion() {
        return ClinicalResearchStaffSortCriterion.ID;
    }


    @Override
    protected Transformer<ClinicalResearchStaff, ClinicalResearchStaffDTO> getTransformer() {
        return ClinicalResearchStaffTransformer.INSTANCE;
    }

}