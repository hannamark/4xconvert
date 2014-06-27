package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import gov.nih.nci.coppa.po.OversightCommittee;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.po.service.OversightCommitteeSortCriterion;
import gov.nih.nci.po.webservices.convert.bridg.OversightCommitteeTransformer;
import gov.nih.nci.po.webservices.service.bo.OversightCommitteeBoService;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * Constructor.
     * @param boService The BO service to delegate to.
     */
    @Autowired
    protected OversightCommitteeServiceImpl(OversightCommitteeBoService boService) {
        super(boService);
    }

    @Override
    protected Transformer<OversightCommittee, OversightCommitteeDTO> getTransformer() {
        return OversightCommitteeTransformer.INSTANCE;
    }

    @Override
    protected SortCriterion<gov.nih.nci.po.data.bo.OversightCommittee> getSortCriterion() {
        return OversightCommitteeSortCriterion.ID;
    }
}
