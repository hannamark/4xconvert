package gov.nih.nci.po.webservices.convert.simple;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This is the test class for AddressConverter
 * 
 * @author Rohit Gupta
 * 
 */
public class AddressConverterTest extends AbstractConverterTest {

    private gov.nih.nci.po.webservices.types.Address address;

    private gov.nih.nci.po.data.bo.Address addressBo;

    @Before
    public void setUp() {
        address = getJaxbAddressList().get(0);
        addressBo = getBoAddressList().get(0);
        super.setUpMockObjects();
    }

    @Test
    public void testConvertJaxbToBO() {
        AddressConverter aConverter = new AddressConverter();
        gov.nih.nci.po.data.bo.Address retAddBo = aConverter
                .convertFromJaxbToBO(address);

        Assert.assertEquals(address.getLine1(), retAddBo.getStreetAddressLine());
        Assert.assertEquals(address.getLine2(),
                retAddBo.getDeliveryAddressLine());
        Assert.assertEquals(address.getCity(), retAddBo.getCityOrMunicipality());
        Assert.assertEquals(address.getStateOrProvince(),
                retAddBo.getStateOrProvince());
        Assert.assertEquals(address.getCountry().name(), retAddBo.getCountry()
                .getAlpha3());
    }

    @Test
    public void testConvertJaxbToBOForNullAddress() {
        AddressConverter aConverter = new AddressConverter();
        // Address is Null
        gov.nih.nci.po.data.bo.Address retAddBo = aConverter
                .convertFromJaxbToBO(null);

        Assert.assertEquals(null, retAddBo);
    }

    @Test
    public void testConvertBOToJaxb() {
        AddressConverter aConverter = new AddressConverter();
        gov.nih.nci.po.webservices.types.Address retAddress = aConverter
                .convertFromBOToJaxB(addressBo);

        Assert.assertEquals(addressBo.getStreetAddressLine(),
                retAddress.getLine1());
        Assert.assertEquals(addressBo.getDeliveryAddressLine(),
                retAddress.getLine2());
        Assert.assertEquals(addressBo.getCityOrMunicipality(),
                retAddress.getCity());
        Assert.assertEquals(addressBo.getStateOrProvince(),
                retAddress.getStateOrProvince());
        Assert.assertEquals(addressBo.getCountry().getAlpha3(), retAddress
                .getCountry().name());
    }

    @Test
    public void testConvertBOToJaxbForNullAddress() {
        AddressConverter aConverter = new AddressConverter();
        // Address is Null
        gov.nih.nci.po.webservices.types.Address retAddress = aConverter
                .convertFromBOToJaxB(null);

        Assert.assertEquals(null, retAddress);
    }
}
