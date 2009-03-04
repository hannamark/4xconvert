
package gov.nih.nci.coppa.po.grid.ser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.DeserializerImpl;
import org.apache.axis.message.MessageElement;
import org.xml.sax.SAXException;

/**
 *
 * @author gax
 */
public class JaxbDeserializer
        extends DeserializerImpl
        implements Deserializer {

    public QName xmlType;
    public Class javaType;

    public JaxbDeserializer(Class javaType, QName xmlType) {
        this.xmlType = xmlType;
        this.javaType = javaType;
    }

    /**
     * Return something even if no characters were found.
     */
    public void onEndElement(
            String namespace,
            String localName,
            DeserializationContext context)
            throws SAXException {
        try {
            MessageElement msgElem = context.getCurElement();
            if (msgElem != null) {
                // Unmarshall the nested XML element into a jaxb object of type 'javaType'
                JAXBContext jc = JAXBContext.newInstance(javaType.getPackage().getName());
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                value = unmarshaller.unmarshal(msgElem.getAsDOM());
            }
        } catch (Exception e) {
            throw new SAXException(e);
        }
    }
}