package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import gov.nih.nci.coppa.po.OrganizationalContact;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.po.service.OrganizationalContactSortCriterion;
import gov.nih.nci.po.webservices.convert.bridg.OrganizationalContactTransformer;
import gov.nih.nci.po.webservices.service.bo.OrganizationalContactBoService;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Service("organizationalContactBridgService")
public class OrganizationalContactServiceImpl extends AbstractRoleService
        <
                OrganizationalContact,
                OrganizationalContactDTO,
                gov.nih.nci.po.data.bo.OrganizationalContact
        > {

    /**
     * Constructor.
     * @param boService The BO service to delegate to.
     */
    @Autowired
    protected OrganizationalContactServiceImpl(OrganizationalContactBoService boService) {
        super(boService);
    }

    @Override
    protected Transformer<OrganizationalContact, OrganizationalContactDTO> getTransformer() {
        return OrganizationalContactTransformer.INSTANCE;
    }

    @Override
    protected SortCriterion<gov.nih.nci.po.data.bo.OrganizationalContact> getSortCriterion() {
        return OrganizationalContactSortCriterion.ID;
    }
}
