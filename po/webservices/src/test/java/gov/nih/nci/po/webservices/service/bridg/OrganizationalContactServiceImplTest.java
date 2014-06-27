package gov.nih.nci.po.webservices.service.bridg;

import gov.nih.nci.coppa.po.OrganizationalContact;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETADTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETTELTransformer;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.OrganizationalContactType;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.AddressConverter;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.webservices.service.bo.OrganizationalContactBoService;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import org.iso._21090.CD;
import org.iso._21090.II;
import org.junit.Before;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.HashSet;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class OrganizationalContactServiceImplTest extends AbstractRoleServiceTest
        <
                OrganizationalContact,
                OrganizationalContactDTO,
                gov.nih.nci.po.data.bo.OrganizationalContact
                >{

    @Before
    public void setup() throws Exception {
        super.setUp();

        when(serviceLocator.getGenericCodeValueService().getByCode(isA(Class.class), anyString()))
                .thenAnswer(new Answer<OrganizationalContactType>() {
                    @Override
                    public OrganizationalContactType answer(InvocationOnMock invocation) throws Throwable {
                        return new OrganizationalContactType((String) invocation.getArguments()[1]);
                    }
                });
    }


    @Override
    protected void initService() {
        this.service = new OrganizationalContactServiceImpl((OrganizationalContactBoService) this.boService);
    }

    @Override
    protected OrganizationalContact getBasicModel() {
        II player = new II();
        player.setRoot(IdConverter.PERSON_ROOT);
        player.setIdentifierName(IdConverter.PERSON_IDENTIFIER_NAME);
        player.setExtension("1");

        II scoper = new II();
        scoper.setRoot(IdConverter.ORG_ROOT);
        scoper.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        scoper.setExtension("2");

        OrganizationalContact organizationalContact = new OrganizationalContact();


        organizationalContact.setPlayerIdentifier(player);

        organizationalContact.setScoperIdentifier(scoper);

        CD typeCd = new CD();
        typeCd.setCode("defaultContactType");
        organizationalContact.setTypeCode(typeCd);


        DSet<Tel> telSet = new DSet<Tel>();
        telSet.setItem(new HashSet<Tel>());
        ModelUtils.ContactSpec contactSpec = ModelUtils.ContactSpec.newInstance()
                .withEmail("bob@foo.org")
                .withPhone("123-456-7890")
                .withUrl("http://www.example.org");

        try {
            organizationalContact.setTelecomAddress(DSETTELTransformer.INSTANCE.toXml(contactSpec.asDset()));
        } catch (DtoTransformException e) {
            throw new RuntimeException(e);
        }


        Address address = ModelUtils.getBasicAddress();
        AddressConverter.SetConverter converter = new AddressConverter.SetConverter();
        HashSet<Address> addresses = new HashSet<Address>();
        addresses.add(address);
        DSet<Ad> dset = converter.convert(DSet.class, addresses);

        organizationalContact.setPostalAddress(DSETADTransformer.INSTANCE.toXml(dset));
        organizationalContact.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        return organizationalContact;
    }

    @Override
    protected Class<? extends GenericStructrualRoleServiceLocal<gov.nih.nci.po.data.bo.OrganizationalContact>> getBoServiceClass() {
        return OrganizationalContactBoService.class;
    }

    @Override
    protected void setId(gov.nih.nci.po.data.bo.OrganizationalContact instance, long id) {
       instance.setId(id);
    }

    @Override
    protected gov.nih.nci.po.data.bo.OrganizationalContact getBoInstance() {
        return new gov.nih.nci.po.data.bo.OrganizationalContact();
    }
}
