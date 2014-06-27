package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import gov.nih.nci.coppa.po.IdentifiedPerson;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.po.service.IdentifiedPersonSortCriterion;
import gov.nih.nci.po.webservices.convert.bridg.IdentifiedPersonTransformer;
import gov.nih.nci.po.webservices.service.bo.IdentifiedPersonBoService;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Service("identifiedPersonBridgService")
public class IdentifiedPersonServiceImpl extends AbstractRoleService
            <
                    IdentifiedPerson,
                    IdentifiedPersonDTO,
                    gov.nih.nci.po.data.bo.IdentifiedPerson
            > {

    /**
     * Constructor.
     * @param boService The BO service to delegate to.
     */
    @Autowired
    protected IdentifiedPersonServiceImpl(IdentifiedPersonBoService boService) {
        super(boService);
    }

    @Override
    protected Transformer<IdentifiedPerson, IdentifiedPersonDTO> getTransformer() {
        return IdentifiedPersonTransformer.INSTANCE;
    }

    @Override
    protected SortCriterion<gov.nih.nci.po.data.bo.IdentifiedPerson> getSortCriterion() {
        return IdentifiedPersonSortCriterion.ID;
    }
}
