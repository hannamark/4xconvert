package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import gov.nih.nci.coppa.po.IdentifiedPerson;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.po.data.bo.AbstractIdentifiedPerson;
import gov.nih.nci.po.data.bo.CorrelationChangeRequest;
import gov.nih.nci.po.data.bo.IdentifiedPersonCR;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.service.IdentifiedPersonSortCriterion;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.webservices.convert.bridg.IdentifiedPersonTransformer;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class IdentifiedPersonServiceImpl extends AbstractRoleService
            <
                    IdentifiedPerson,
                    IdentifiedPersonDTO,
                    gov.nih.nci.po.data.bo.IdentifiedPerson
            > {
    @Override
    protected Class<?> getAbstractModelClass() {
       return AbstractIdentifiedPerson.class;
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal getCrService() {
        return PoRegistry.getInstance().getServiceLocator().getIdentifiedPersonCRService();
    }

    @Override
    protected CorrelationChangeRequest<gov.nih.nci.po.data.bo.IdentifiedPerson> buildChangeRequest(
            gov.nih.nci.po.data.bo.IdentifiedPerson instance) {
        return new IdentifiedPersonCR(instance);
    }

    @Override
    protected Transformer<IdentifiedPerson, IdentifiedPersonDTO> getTransformer() {
        return IdentifiedPersonTransformer.INSTANCE;
    }

    @Override
    protected GenericStructrualRoleServiceLocal<gov.nih.nci.po.data.bo.IdentifiedPerson> getEjbService() {
        return PoRegistry.getInstance().getServiceLocator().getIdentifiedPersonService();
    }

    @Override
    protected SortCriterion<gov.nih.nci.po.data.bo.IdentifiedPerson> getSortCriterion() {
        return IdentifiedPersonSortCriterion.ID;
    }
}
