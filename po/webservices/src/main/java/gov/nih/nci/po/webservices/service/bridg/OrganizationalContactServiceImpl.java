package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import gov.nih.nci.coppa.po.OrganizationalContact;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.po.data.bo.AbstractOrganizationalContact;
import gov.nih.nci.po.data.bo.CorrelationChangeRequest;
import gov.nih.nci.po.data.bo.OrganizationalContactCR;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.service.OrganizationalContactSortCriterion;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.webservices.convert.bridg.OrganizationalContactTransformer;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class OrganizationalContactServiceImpl extends AbstractRoleService
        <
                OrganizationalContact,
                OrganizationalContactDTO,
                gov.nih.nci.po.data.bo.OrganizationalContact
        > {
    @Override
    protected Class<?> getAbstractModelClass() {
        return AbstractOrganizationalContact.class;
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal getCrService() {
        return PoRegistry.getInstance().getServiceLocator().getOrganizationalContactCRService();
    }

    @Override
    protected CorrelationChangeRequest<gov.nih.nci.po.data.bo.OrganizationalContact> buildChangeRequest(
            gov.nih.nci.po.data.bo.OrganizationalContact instance) {
        return new OrganizationalContactCR(instance);
    }

    @Override
    protected Transformer<OrganizationalContact, OrganizationalContactDTO> getTransformer() {
        return OrganizationalContactTransformer.INSTANCE;
    }

    @Override
    protected GenericStructrualRoleServiceLocal<gov.nih.nci.po.data.bo.OrganizationalContact> getEjbService() {
        return PoRegistry.getInstance().getServiceLocator().getOrganizationalContactService();
    }

    @Override
    protected SortCriterion<gov.nih.nci.po.data.bo.OrganizationalContact> getSortCriterion() {
        return OrganizationalContactSortCriterion.ID;
    }
}
