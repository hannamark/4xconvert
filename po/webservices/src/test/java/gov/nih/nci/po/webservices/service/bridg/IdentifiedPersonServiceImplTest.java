package gov.nih.nci.po.webservices.service.bridg;

import gov.nih.nci.coppa.po.IdentifiedPerson;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.webservices.service.bo.IdentifiedPersonBoService;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import org.iso._21090.CD;
import org.iso._21090.II;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class IdentifiedPersonServiceImplTest extends AbstractRoleServiceTest
        <
                IdentifiedPerson,
                IdentifiedPersonDTO,
                gov.nih.nci.po.data.bo.IdentifiedPerson
                >{
    @Override
    protected void initService() {
       this.service = new IdentifiedPersonServiceImpl((IdentifiedPersonBoService) this.boService);
    }

    @Override
    protected IdentifiedPerson getBasicModel() {
        II player = new II();
        player.setRoot(IdConverter.PERSON_ROOT);
        player.setIdentifierName(IdConverter.PERSON_IDENTIFIER_NAME);
        player.setExtension("1");

        II scoper = new II();
        scoper.setRoot(IdConverter.ORG_ROOT);
        scoper.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        scoper.setExtension("2");


        II assignedIdentifier = new II();
        assignedIdentifier.setRoot(IdConverter.PERSON_ROOT);
        assignedIdentifier.setIdentifierName(IdConverter.PERSON_IDENTIFIER_NAME);
        assignedIdentifier.setExtension("3");


        IdentifiedPerson identifiedPerson = new IdentifiedPerson();

        identifiedPerson.setPlayerIdentifier(player);
        identifiedPerson.setScoperIdentifier(scoper);
        identifiedPerson.setAssignedId(assignedIdentifier);


        identifiedPerson.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        return identifiedPerson;
    }

    @Override
    protected Class<? extends GenericStructrualRoleServiceLocal<gov.nih.nci.po.data.bo.IdentifiedPerson>> getBoServiceClass() {
        return IdentifiedPersonBoService.class;
    }

    @Override
    protected void setId(gov.nih.nci.po.data.bo.IdentifiedPerson instance, long id) {
        instance.setId(id);
    }

    @Override
    protected gov.nih.nci.po.data.bo.IdentifiedPerson getBoInstance() {
        return new gov.nih.nci.po.data.bo.IdentifiedPerson();
    }



}
