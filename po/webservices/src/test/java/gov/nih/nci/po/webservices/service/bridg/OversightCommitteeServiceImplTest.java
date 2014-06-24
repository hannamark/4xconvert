package gov.nih.nci.po.webservices.service.bridg;

import gov.nih.nci.coppa.po.OversightCommittee;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import org.iso._21090.CD;
import org.iso._21090.II;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class OversightCommitteeServiceImplTest extends AbstractRoleServiceTest
        <
            OversightCommittee,
            OversightCommitteeDTO,
            gov.nih.nci.po.data.bo.OversightCommittee
        >{


    @Override
    protected void initService() {
        this.service = new OversightCommitteeServiceImpl();
    }

    @Override
    protected OversightCommittee getBasicModel() {
        II player = new II();
        player.setRoot(IdConverter.ORG_ROOT);
        player.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        player.setExtension("1");

        OversightCommittee oversightCommittee = new OversightCommittee();


        oversightCommittee.setPlayerIdentifier(player);

        CD typeCd = new CD();
        typeCd.setCode("defaultType");
        oversightCommittee.setTypeCode(typeCd);

        oversightCommittee.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        return oversightCommittee;
    }

    @Override
    protected GenericStructrualRoleServiceLocal<gov.nih.nci.po.data.bo.OversightCommittee> getBoService() {
        return serviceLocator.getOversightCommitteeService();
    }

    @Override
    protected void setId(gov.nih.nci.po.data.bo.OversightCommittee instance, long id) {
       instance.setId(id);
    }

    @Override
    protected gov.nih.nci.po.data.bo.OversightCommittee getBoInstance() {
        return new gov.nih.nci.po.data.bo.OversightCommittee();
    }
}
