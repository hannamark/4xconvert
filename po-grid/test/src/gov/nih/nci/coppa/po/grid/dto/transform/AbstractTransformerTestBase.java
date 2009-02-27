package gov.nih.nci.coppa.po.grid.dto.transform;

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.ParameterizedType;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gax
 */
public abstract class AbstractTransformerTestBase <T extends Transformer<XML, DTO>, XML, DTO>{

    protected final Class<T> tClass;
    {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        tClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }
    protected T transformer;

    public abstract XML makeXml();
    public abstract DTO makeDto();
    public abstract void verifyXml(XML x);
    public abstract void verifyDto(DTO x);

    @Before
    public void initTransformer() {
        try {
            transformer = tClass.newInstance();
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    @Test
    public void toIsoNull() throws DtoTransformException {
        XML x = null;
        DTO d = transformer.toDto(x);
        assertNull(d);
    }

    @Test
    public void toIso() throws DtoTransformException {
        XML x = makeXml();
        DTO d = transformer.toDto(x);
        verifyDto(d);
    }

    @Test
    public void toXmlNull() throws DtoTransformException {
        DTO d = null;
        XML x = transformer.toXml(d);
        assertNull(x);
    }

    @Test
    public void toXml() throws DtoTransformException {
        DTO d = makeDto();
        XML x = transformer.toXml(d);
        verifyXml(x);
    }

    @Test
    public void roundTrip() throws Exception {
        DTO d = makeDto();
        XML x = transformer.toXml(d);
        JAXBContext jaxbContext = JAXBContext.newInstance(x.getClass().getPackage().getName());

        StringWriter sw = new StringWriter();
        jaxbContext.createMarshaller().marshal(new JAXBElement(new QName("foo"), x.getClass(), x), sw);
        String xml = sw.getBuffer().toString();
        StreamSource s = new StreamSource(new StringReader(xml));
        XML x2 = (XML) jaxbContext.createUnmarshaller().unmarshal(s, x.getClass()).getValue();

        verifyXml(x2);
        DTO d2 = transformer.toDto(x2);
        verifyDto(d2);
    }
    
}
