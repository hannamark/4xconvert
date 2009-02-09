package gov.nih.nci.coppa.po.grid.ser;

import gov.nih.nci.cagrid.encoding.AxisContentHandler;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import org.apache.axis.Constants;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.wsdl.fromJava.Types;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

/**
 *
 * @author gax
 */
public class JaxbSerializer implements Serializer {

    /**
     * Serialize a JAXB object.
     *
     * @param name
     * @param attributes
     * @param value      this must be a JAXB object for marshalling
     * @param context
     * @throws IOException for XML schema noncompliance, bad object type, and any IO
     *                     trouble.
     */
    public void serialize(QName name,
                          Attributes attributes,
                          Object value,
                          SerializationContext context)
            throws IOException {
        try {
            AxisContentHandler hand = new AxisContentHandler(context);
            JAXBContext jaxbContext = JAXBContext.newInstance(value.getClass().getPackage().getName());
            Marshaller marshaller = jaxbContext.createMarshaller();
            
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            if (value.getClass().getAnnotation(XmlRootElement.class) == null) {
                marshaller.marshal( new JAXBElement(name, value.getClass(), value ), hand);
            } else {
                marshaller.marshal(value, hand);
            }

//            
            

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public String getMechanismType() {
        return Constants.AXIS_SAX;
    }

    /**
     * Return XML schema for the specified type, suitable for insertion into
     * the &lt;types&gt; element of a WSDL document, or underneath an
     * &lt;element&gt; or &lt;attribute&gt; declaration.
     *
     * @param javaType the Java Class we're writing out schema for
     * @param types    the Java2WSDL Types object which holds the context
     *                 for the WSDL being generated.
     * @return a type element containing a schema simpleType/complexType
     * @see org.apache.axis.wsdl.fromJava.Types
     */
    public Element writeSchema(Class javaType, Types types) throws Exception {
        return null;
    }
}
