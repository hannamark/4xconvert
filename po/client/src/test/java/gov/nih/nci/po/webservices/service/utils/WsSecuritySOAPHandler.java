package gov.nih.nci.po.webservices.service.utils;

import org.apache.ws.security.WSConstants;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecUsernameToken;
import org.w3c.dom.Document;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class WsSecuritySOAPHandler implements SOAPHandler<SOAPMessageContext> {

    private String username;
    private String password;
    private String passwordType;


    public WsSecuritySOAPHandler(String username, String password) {
        this(username, password, WSConstants.PASSWORD_TEXT);
    }

    public WsSecuritySOAPHandler(String username, String password, String passwordType) {
        this.username = username;
        this.password = password;
        this.passwordType = passwordType;
    }

    @Override
    public Set<QName> getHeaders() {
        final QName securityHeader = new QName(
                WSConstants.WSSE_NS,
                WSConstants.WSSE_LN,
                WSConstants.WSSE_PREFIX);

        final HashSet headers = new HashSet();
        headers.add(securityHeader);

        // notify the runtime that this is handled
        return headers;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {

        Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty.booleanValue()) {

            try {
                SOAPEnvelope envelope = context.getMessage().getSOAPPart().getEnvelope();
                SOAPFactory factory = SOAPFactory.newInstance();


                Source src = context.getMessage().getSOAPPart().getContent();
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer transformer = tf.newTransformer();
                DOMResult result = new DOMResult();
                transformer.transform(src, result);

                Document doc = (Document)result.getNode();
                WSSecHeader wsSecHeader = new WSSecHeader();

                wsSecHeader.insertSecurityHeader(doc);
                WSSecUsernameToken wsToken = new WSSecUsernameToken();
                wsToken.setUserInfo(username, password);
                wsToken.setPasswordType(passwordType);
                wsToken.build(doc, wsSecHeader);

                SOAPElement el = factory.createElement(wsSecHeader.getSecurityHeader());
                SOAPHeader header = envelope.addHeader();
                header.addChildElement(el);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // inbound
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return false;
    }

    @Override
    public void close(MessageContext context) {
        //no op
    }
}
