package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import gov.nih.nci.coppa.po.OversightCommittee;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.service.OversightCommitteeSortCriterion;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.webservices.convert.bridg.OversightCommitteeTransformer;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import org.springframework.stereotype.Service;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Service("oversightCommitteeBridgService")
public class OversightCommitteeServiceImpl extends AbstractRoleService
        <
                OversightCommittee,
                OversightCommitteeDTO,
                gov.nih.nci.po.data.bo.OversightCommittee
        > {


    @Override
    protected Transformer<OversightCommittee, OversightCommitteeDTO> getTransformer() {
        return OversightCommitteeTransformer.INSTANCE;
    }

    @Override
    protected GenericStructrualRoleServiceLocal<gov.nih.nci.po.data.bo.OversightCommittee> getEjbService() {
        return PoRegistry.getInstance().getServiceLocator().getOversightCommitteeService();
    }

    @Override
    protected SortCriterion<gov.nih.nci.po.data.bo.OversightCommittee> getSortCriterion() {
        return OversightCommitteeSortCriterion.ID;
    }
}
